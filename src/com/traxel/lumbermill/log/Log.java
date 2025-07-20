/*
  This file is part of Lumbermill.
  
  Lumbermill is free software; you can redistribute it
  and/or modify it under the terms of the GNU General Public
  License as published by the Free Software Foundation;
  either version 2 of the License, or (at your option) any
  later version.
  
  Lumbermill is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied
  warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the GNU General Public License for more
  details.
  
  You should have received a copy of the GNU General Public
  License along with Lumbermill; if not, write to the Free
  Software Foundation, Inc., 59 Temple Place, Suite 330,
  Boston, MA 02111-1307 USA
 */

package com.traxel.lumbermill.log;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.traxel.lumbermill.event.Event;

public class Log
{

	// ---------------------------------------
	// Class Definition
	// ---------------------------------------

	public static final int PLAY = 0, PAUSE = 1, STOP = 2;

	public static final String INFO_PROPERTY_NAME = "_info";

	// ---------------------------------------
	// Instance Definition
	// ---------------------------------------

	private final List<Event> CACHE = Collections.synchronizedList(new ArrayList());
	private final List<Event> EVENTS = Collections.synchronizedList(new ArrayList());
	private final Set<LogListener> LISTENERS = Collections.synchronizedSet(new HashSet());
	private final Set<PropertyChangeListener> PROPERTY_LISTENERS = Collections
			.synchronizedSet(new HashSet());
	private int _minSize = 45000;
	private int _maxSize = 50000;
	private int _activeState = PLAY;

	// ---------------------------------------
	// Instance Accessors
	// ---------------------------------------

	public void setMinMax(int minSize, int maxSize)
	{
		_minSize = minSize;
		_maxSize = maxSize;
	}

	public int getActiveState()
	{
		return _activeState;
	}

	private void setActiveState(int state)
	{
		if (_activeState == state)
			return;
		
		firePropertyChange("_activeState", new Integer(_activeState),
				new Integer(state));
		_activeState = state;
	}

	// --------------------------------------
	// Public Instance API
	// --------------------------------------

	public int size()
	{
		return EVENTS.size();
	}

	public void addPropertyListener(PropertyChangeListener l)
	{
		synchronized (PROPERTY_LISTENERS)
		{
			PROPERTY_LISTENERS.add(l);
		}
	}

	/**
	 * @throws CacheOverflowException
	 *             when PAUSE'd and cache exceeds the maximum size of LOG.
	 */
	public void add(Event e)
	{
		if (e == null)
			return;
		
		synchronized (EVENTS)
		{
			if (PLAY == _activeState)
			{
				EVENTS.add(e);
				fireAdded(e);
				if (EVENTS.size() > _maxSize)
				{
					int firstKept = EVENTS.size() - _minSize;
					Event[] removed = new Event[firstKept];
					
					for (int i = firstKept - 1; i >= 0; i--)
					{
						removed[i] = (Event) EVENTS.get(i);
						EVENTS.remove(i);
					}
					fireRemoved(removed);
				}
			}
			else if (PAUSE == _activeState)
			{
				if (CACHE.size() >= _maxSize)
				{
					stop();
					CACHE.clear();
					firePropertyChange(INFO_PROPERTY_NAME, null,
							"Pause Buffer Overflow");
				}
				else
				{
					CACHE.add(e);
				}
			}
		}
	}

	public Iterator iterator()
	{
		List events;

		synchronized (EVENTS)
		{
			events = new ArrayList(EVENTS);
		}

		return events.iterator();
	}

	public void addLogListener(LogListener l)
	{
		if (l != null)
		{
			synchronized (LISTENERS)
			{
				LISTENERS.add(l);
			}
		}
	}

	public Event[] toArray()
	{
		synchronized (EVENTS)
		{
			return (Event[]) EVENTS.toArray(new Event[0]);
		}
	}

	public void play()
	{
		synchronized (EVENTS)
		{
			Iterator it;

			setActiveState(PLAY);
			it = CACHE.iterator();
			while (it.hasNext())
			{
				add((Event) it.next());
			}
			CACHE.clear();
		}
	}

	public void pause()
	{
		synchronized (EVENTS)
		{
			setActiveState(PAUSE);
		}
	}

	public void stop()
	{
		synchronized (EVENTS)
		{
			setActiveState(STOP);
		}
	}

	public void clear()
	{
		synchronized (EVENTS)
		{
			EVENTS.clear();
			CACHE.clear();
			fireCleared();
		}
	}

	// -------------------------------------
	// Private Instance API
	// -------------------------------------

	private void fireAdded(Event event)
	{
		synchronized (LISTENERS)
		{
			Iterator it = LISTENERS.iterator();
			while (it.hasNext())
			{
				((LogListener) it.next()).added(this, event);
			}
		}
	}

	private void fireRemoved(Event[] events)
	{
		synchronized (LISTENERS)
		{
			Iterator it = LISTENERS.iterator();
			while (it.hasNext())
			{
				((LogListener) it.next()).removed(this, events);
			}
		}
	}

	private void fireCleared()
	{
		synchronized (LISTENERS)
		{
			Iterator it = LISTENERS.iterator();
			while (it.hasNext())
			{
				((LogListener) it.next()).cleared(this);
			}
		}
	}

	private void firePropertyChange(String property, Object oldValue,
			Object newValue)
	{
		PropertyChangeEvent event = new PropertyChangeEvent(this, property,
				oldValue, newValue);

		synchronized (PROPERTY_LISTENERS)
		{
			Iterator it;
			PropertyChangeListener listener;

			it = PROPERTY_LISTENERS.iterator();
			while (it.hasNext())
			{
				listener = (PropertyChangeListener) it.next();
				listener.propertyChange(event);
			}
		}
	}
}
