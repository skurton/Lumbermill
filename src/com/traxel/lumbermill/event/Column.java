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

import javax.swing.table.TableColumn;

class Column extends TableColumn
{

	// -----------------------------------
	// Instance Definition
	// -----------------------------------

	private final Accessor ACCESSOR;
	private final boolean USE_FORMATTER;

	// -----------------------------------
	// Instance Initialization
	// -----------------------------------

	Column(Accessor accessor, int modelIndex, boolean useFormatter,
			int minWidth, int defaultWidth, int maxWidth)
	{
		super(modelIndex, defaultWidth, CellView.getView(accessor), null);
		ACCESSOR = accessor;
		USE_FORMATTER = useFormatter;
		setMinWidth(minWidth);
		setMaxWidth(maxWidth);
	}

	// -----------------------------------
	// Instance Accessors
	// -----------------------------------

	public final Accessor getAccessor()
	{
		return ACCESSOR;
	}

	public final Object getValue(Event event)
	{
		if (USE_FORMATTER)
		{
			return getAccessor().getString(event);
		}
		else
		{
			return getAccessor().getValue(event);
		}
	}

	public final Class getType()
	{
		return getAccessor().getType();
	}

	public final String getName()
	{
		return getAccessor().getName();
	}

	public final String getShortName()
	{
		return getAccessor().getShortName();
	}

	public final String getDescription()
	{
		return getAccessor().getDescription();
	}

	public final Object getHeaderValue()
	{
		return getShortName();
	}
}
