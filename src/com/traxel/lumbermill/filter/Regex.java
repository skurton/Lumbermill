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

import java.util.regex.Pattern;

import com.traxel.lumbermill.event.Event;

class Regex implements Filter
{

	// -----------------------------------------
	// Class Definition
	// -----------------------------------------

	public static final int MESSAGE = 0;

	// -----------------------------------------
	// Instance Definition
	// -----------------------------------------

	private final FilterNotifier FILTER_NOTIFIER;
	private Pattern _pattern;

	// -----------------------------------------
	// Instance Initialization
	// -----------------------------------------

	{
		FILTER_NOTIFIER = new FilterNotifier(this);
	}

	public Regex(int field)
	{
	}

	// ------------------------------------------
	// Instance Accessors
	// ------------------------------------------

	public void setRegex(String regex)
	{
		if (regex == null || "".equals(regex))
		{
			_pattern = null;
		}
		else
		{
			regex = ".*" + regex + ".*";
			regex = regex.replaceAll("(\\.\\*)+", ".*");
			_pattern = Pattern.compile(regex, Pattern.DOTALL);
		}
		FILTER_NOTIFIER.fireFilterChange();
	}

	private Pattern getPattern()
	{
		return _pattern;
	}

	// -----------------------------------------
	// Filter Implementation
	// -----------------------------------------

	public void addFilterListener(FilterListener listener)
	{
		FILTER_NOTIFIER.addFilterListener(listener);
	}

	public void removeFilterListener(FilterListener listener)
	{
		FILTER_NOTIFIER.addFilterListener(listener);
	}

	public boolean isVisible(Event event)
	{
		if (getPattern() == null)
		{
			return true;
		}
		if (event.getMessage() == null)
		{
			return false;
		}
		return getPattern().matcher(event.getMessage()).matches();
	}

	public String getName()
	{
		return "Regex";
	}
}
