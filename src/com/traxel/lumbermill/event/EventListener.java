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

package com.traxel.lumbermill.event;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.traxel.lumbermill.log.Log;

public abstract class EventListener extends Thread
{

	// ------------------------------------------
	// Instance Definition
	// ------------------------------------------

	private final Log SINK;
	private final Set<PropertyChangeListener> LISTENERS = Collections.synchronizedSet(new HashSet());
	public static final String STATUS_PROPERTY = "_status", NA = "N/A";

	// ------------------------------------------
	// Instance Initialization
	// ------------------------------------------

	protected EventListener(Log sink)
	{
		SINK = sink;
	}

	// ------------------------------------------
	// Implementation API
	// ------------------------------------------

	public abstract void close();

	public abstract String getHost();

	public abstract String getPortString();

	public abstract String getType();

	public abstract String getStatus();

	// ------------------------------------------
	// Public Instance API
	// ------------------------------------------

	public void addPropertyListener(PropertyChangeListener l)
	{
		synchronized (LISTENERS)
		{
			LISTENERS.add(l);
		}
	}

	// ------------------------------------------
	// Inherited Instance API
	// ------------------------------------------

	protected void add(Event event)
	{
		SINK.add(event);
	}

	protected void firePropertyChange(String property, Object oldValue,
			Object newValue)
	{
		synchronized (LISTENERS)
		{
			PropertyChangeListener listener;
			PropertyChangeEvent event;
			Iterator it;

			event = new PropertyChangeEvent(this, property, oldValue, newValue);
			it = LISTENERS.iterator();
			while (it.hasNext())
			{
				listener = (PropertyChangeListener) it.next();
				listener.propertyChange(event);
			}
		}
	}
}
