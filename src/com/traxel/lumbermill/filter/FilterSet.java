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

package com.traxel.lumbermill.filter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.traxel.lumbermill.event.Event;

public class FilterSet implements Filter, FilterListener
{

	// ---------------------------------------------
	// Instance Definition
	// ---------------------------------------------

	private final Set FILTERS = Collections.synchronizedSet(new HashSet());
	private final Set LISTENERS = Collections.synchronizedSet(new HashSet());

	// ---------------------------------------------
	// Filter Implementation
	// ---------------------------------------------

	public boolean isVisible(Event event)
	{
		synchronized (FILTERS)
		{
			Iterator it;
			Filter filter;

			it = FILTERS.iterator();
			while (it.hasNext())
			{
				filter = (Filter) it.next();
				if (!filter.isVisible(event))
				{
					return false;
				}
			}
		}

		return true;
	}

	public void addFilterListener(FilterListener listener)
	{
		synchronized (LISTENERS)
		{
			LISTENERS.add(listener);
		}
	}

	public void removeFilterListener(FilterListener listener)
	{
		synchronized (LISTENERS)
		{
			LISTENERS.remove(listener);
		}
	}

	public String getName()
	{
		return "Filter Set";
	}

	// -------------------------------------------
	// FilterListener Implementation
	// -------------------------------------------

	public void filterChange(FilterEvent event)
	{
		fireFilterEvent(event);
	}

	// ---------------------------------------------
	// Public Instance API
	// --------------------------------------------

	public void add(Filter filter)
	{
		synchronized (FILTERS)
		{
			filter.addFilterListener(this);
			FILTERS.add(filter);
			fireFilterAdded(filter);
		}
	}

	public void remove(Filter filter)
	{
		synchronized (FILTERS)
		{
			filter.removeFilterListener(this);
			FILTERS.remove(filter);
			fireFilterRemoved(filter);
		}
	}

	// --------------------------------------------
	// Private Instance API
	// --------------------------------------------

	private void fireFilterAdded(Filter source)
	{
		FilterEvent event;

		event = new FilterEvent(source);
		fireFilterEvent(event);
	}

	private void fireFilterRemoved(Filter source)
	{
		FilterEvent event;

		event = new FilterEvent(source);
		fireFilterEvent(event);
	}

	private void fireFilterEvent(FilterEvent event)
	{
		synchronized (LISTENERS)
		{
			Iterator it;
			FilterListener listener;

			it = LISTENERS.iterator();
			while (it.hasNext())
			{
				listener = (FilterListener) it.next();
				listener.filterChange(event);
			}
		}
	}
}
