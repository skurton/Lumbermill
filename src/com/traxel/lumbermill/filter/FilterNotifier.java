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

/**
 * Sends notifications immediately - see TimerNotifier one that has a 250 ms
 * delay, aggregates multiple notifications. This is public so people outside
 * the package can use it.
 */
public class FilterNotifier
{

	// -------------------------------------------
	// Instance Definition
	// -------------------------------------------

	private final Set FILTER_LISTENERS = Collections
			.synchronizedSet(new HashSet());
	private final Filter OWNER;

	// ----------------------------------------
	// Instance Initialization
	// ----------------------------------------

	public FilterNotifier(Filter owner)
	{
		OWNER = owner;
	}

	// ----------------------------------------
	// Public API
	// ----------------------------------------

	public final void addFilterListener(FilterListener listener)
	{
		synchronized (FILTER_LISTENERS)
		{
			FILTER_LISTENERS.add(listener);
		}
	}

	public final void removeFilterListener(FilterListener listener)
	{
		synchronized (FILTER_LISTENERS)
		{
			FILTER_LISTENERS.remove(listener);
		}
	}

	public synchronized void fireFilterChange()
	{
		Iterator<FilterListener> it;
		FilterListener listener;

		FilterEvent event = new FilterEvent(OWNER);
		synchronized (FILTER_LISTENERS)
		{
			it = FILTER_LISTENERS.iterator();
			while (it.hasNext())
			{
				listener = (FilterListener) it.next();
				listener.filterChange(event);
			}
		}
	}
}
