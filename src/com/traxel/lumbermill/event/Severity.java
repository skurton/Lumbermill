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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Severity implements Comparable<Object>
{

	// ---------------------------------------
	// Class Definition
	// ---------------------------------------

	private static final List<Severity> LEVELS;
	public static final Severity INHERIT = new Inherit(), ALL = new All(),
			LEVEL_1 = new Severity(1, "Finest"), LEVEL_2 = new Severity(2,
					"Debug"), LEVEL_3 = new Severity(3, "Fine"),
			LEVEL_4 = new Severity(4, "Config"), LEVEL_5 = new Severity(5,
					"Info"), LEVEL_6 = new Severity(6, "Warning"),
			LEVEL_7 = new Severity(7, "Error"), LEVEL_8 = new Severity(8,
					"Fatal"), DISABLED = new Disabled();

	private static class Inherit extends Severity
	{
		public Inherit()
		{
			super(-1, "Inherit");
		}
	}

	private static class All extends Severity
	{
		public All()
		{
			super(0, "All");
		}
	}

	private static class Disabled extends Severity
	{
		public Disabled()
		{
			super(9, "Disabled");
		}
	}

	// ---------------------------------------
	// Class Initialization
	// ---------------------------------------

	static
	{
		List<Severity> levels;

		levels = new ArrayList<Severity>();
		levels.add(INHERIT);
		levels.add(ALL);
		levels.add(LEVEL_1);
		levels.add(LEVEL_2);
		levels.add(LEVEL_3);
		levels.add(LEVEL_4);
		levels.add(LEVEL_5);
		levels.add(LEVEL_6);
		levels.add(LEVEL_7);
		levels.add(LEVEL_8);
		levels.add(DISABLED);

		LEVELS = Collections.unmodifiableList(levels);
	}

	// ---------------------------------------
	// Class API
	// ---------------------------------------

	public static List<Severity> getLevels()
	{
		return LEVELS;
	}

	// --------------------------------------
	// Instance Definition
	// --------------------------------------

	private final int _level;
	private final String _text;

	// --------------------------------------
	// Instance Initialization
	// --------------------------------------

	private Severity(int level, String text)
	{
		_level = level;
		_text = text;
	}

	// --------------------------------------
	// Instance Accessors
	// --------------------------------------

	public int getLevel()
	{
		return _level;
	}

	public String toString()
	{
		return _text;
	}

	// --------------------------------------
	// Comparable Implementation
	// --------------------------------------

	public int compareTo(Object o)
	{
		if (o == this)
		{
			return 0;
		}
		if (o == null)
		{
			return 1;
		}
		Severity other = (Severity) o;
		return getLevel() - other.getLevel();
	}
}
