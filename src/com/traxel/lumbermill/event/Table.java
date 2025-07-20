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

import javax.swing.table.AbstractTableModel;

import com.traxel.lumbermill.filter.FilterEvent;
import com.traxel.lumbermill.filter.FilterListener;
import com.traxel.lumbermill.filter.FilterSet;
import com.traxel.lumbermill.log.Log;
import com.traxel.lumbermill.log.LogListener;

public class Table extends AbstractTableModel implements LogListener,
		FilterListener
{

	// ---------------------------------------
	// Instance Definition
	// ---------------------------------------

	private final List EVENTS = Collections.synchronizedList(new ArrayList());
	private final ColumnSet COLUMN_SET;
	private final Log LOG;
	private final FilterSet FILTER_SET;

	// ---------------------------------------
	// Instance Initialization
	// ---------------------------------------

	public Table(Log log, FilterSet filterSet, ColumnSet columnSet)
	{
		COLUMN_SET = columnSet;
		FILTER_SET = filterSet;
		FILTER_SET.addFilterListener(this);
		LOG = log;
		LOG.addLogListener(this);
	}

	// ---------------------------------------
	// TableModel Implementation
	// ---------------------------------------

	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Column column;
		Event event;

		column = getColumn(columnIndex);
		event = getEvent(rowIndex);

		return column.getValue(event);
	}

	public int getColumnCount()
	{
		return COLUMN_SET.getColumnCount();
	}

	public int getRowCount()
	{
		return EVENTS.size();
	}

	// ---------------------------------------
	// LogListener Implementation
	// ---------------------------------------

	public void added(Log source, Event event)
	{
		if (LOG.equals(source))
		{
			insert(event);
		}
	}

	public void removed(Log source, Event[] events)
	{
		if (LOG.equals(source))
		{
			remove(events);
		}
	}

	public void cleared(Log source)
	{
		if (LOG.equals(source))
		{
			removeAll();
		}
	}

	// ---------------------------------------
	// FilterListener Implemenation
	// ---------------------------------------

	public void filterChange(FilterEvent e)
	{
		synchronized (EVENTS)
		{
			removeAll();
			insertAll(LOG.toArray());
		}
	}

	// ---------------------------------------
	// Public Instance API
	// ---------------------------------------

	public String getColumnName(int columnIndex)
	{
		return getColumn(columnIndex).getName();
	}

	public Class getColumnClass(int columnIndex)
	{
		return getColumn(columnIndex).getType();
	}

	public Event getEvent(int rowIndex)
	{
		return (Event) EVENTS.get(rowIndex);
	}

	/**
	 * events should be given in chronological order, from first occured to last
	 * occured.
	 */
	public void insertAll(Event[] events)
	{
		int count = 0;
		synchronized (EVENTS)
		{
			for (int i = 0; i < events.length; i++)
			{
				if (events[i] != null && FILTER_SET.isVisible(events[i]))
				{
					count++;
					EVENTS.add(0, events[i]);
				}
			}
			if (count > 0)
			{
				fireTableRowsInserted(0, count - 1);
			}
		}
	}

	public void removeAll()
	{
		synchronized (EVENTS)
		{
			int minRow = 0;
			int maxRow = EVENTS.size() - 1;

			EVENTS.clear();
			if (maxRow >= minRow)
			{
				fireTableRowsDeleted(minRow, maxRow);
			}
		}
	}

	// --------------------------------------
	// Package Instance API
	// --------------------------------------

	ColumnSet getColumnSet()
	{
		return COLUMN_SET;
	}

	Column getColumn(int columnIndex)
	{
		return getColumnSet().columnForIndex(columnIndex);
	}

	// ---------------------------------------
	// Private Instance API
	// ---------------------------------------

	private void insert(Event event)
	{
		if (event != null && FILTER_SET.isVisible(event))
		{
			synchronized (EVENTS)
			{
				EVENTS.add(0, event);
				fireTableRowsInserted(0, 0);
			}
		}
	}

	private void remove(Event[] events)
	{
		int minRow = -1;
		int maxRow = -1;
		int row;

		synchronized (EVENTS)
		{
			for (int i = 0; i < events.length; i++)
			{
				row = EVENTS.indexOf(events[i]);
				if (minRow == -1 || row < minRow)
				{
					minRow = row;
				}
				if (maxRow == -1 || row > maxRow)
				{
					maxRow = row;
				}
			}
			for (int i = 0; i < events.length; i++)
			{
				EVENTS.remove(events[i]);
			}
			// Deadlock with this in synch loop?
			if (minRow > -1 && maxRow > -1)
			{
				fireTableRowsDeleted(minRow, maxRow);
			}
		}
	}
}
