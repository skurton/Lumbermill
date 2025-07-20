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

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public abstract class AbstractEvent extends Event
{

	// ------------------------------------------
	// Class Definition
	// ------------------------------------------

	private static final long START_TIMESTAMP = new Date().getTime();

	// ------------------------------------------
	// Instance Definition
	// ------------------------------------------

	private List<String> _sourceComponents;

	// ------------------------------------------
	// Event Implementation
	// ------------------------------------------

	public final List<String> getSourceComponents()
	{
		if (_sourceComponents == null)
		{
			String source = getSource();
			String[] components = source.split("\\.");
			_sourceComponents = Collections.unmodifiableList(Arrays
					.asList(components));
		}
		return _sourceComponents;
	}

	public final String getLastSourceComponent()
	{
		List<String> components;

		components = getSourceComponents();

		return (String) components.get(components.size() - 1);
	}

	public boolean hasThrown()
	{
		return getThrown() != null;
	}

	public String getStackTrace()
	{
		if (null == getThrown())
		{
			return null;
		}
		StackTraceElement[] elements;
		StringBuffer buff;

		elements = getThrown().getStackTrace();
		buff = new StringBuffer();
		for (int i = 0; i < elements.length; i++)
		{
			if (i > 0)
			{
				buff.append("\n");
			}
			buff.append(elements[i]);
		}

		return buff.toString();
	}

	public long getElapsedTime()
	{
		return getTimestamp() - START_TIMESTAMP;
	}
}
