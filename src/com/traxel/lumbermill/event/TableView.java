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

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.traxel.lumbermill.filter.FilterSet;
import com.traxel.lumbermill.log.Log;

public class TableView extends JTable
{

	// -------------------------------------
	// Instance Definition
	// -------------------------------------

	private class Unselector implements TableModelListener
	{
		public void tableChanged(TableModelEvent e)
		{
			if (TableModelEvent.INSERT == e.getType())
			{
				if (0 == e.getFirstRow() && 0 == getSelectedRow())
				{
					removeRowSelectionInterval(0, 0);
				}
			}
		}
	}

	// -------------------------------------
	// Instance Initialization
	// -------------------------------------

	public TableView(Log log, FilterSet filterSet, ColumnSet columnSet)
	{
		super(new Table(log, filterSet, columnSet));

		// When a JTable allows multiple selection, and
		// the first row is selected, and a new row is inserted
		// at index 0, the new row gets added to the selection
		// set. As a result, when you have a few hundred
		// rows per second being inserted at index 0, you
		// get a a few hundred listselectionevents per second.
		// This is a Bad Thing.
		// Fix Borken JTable
		getModel().addTableModelListener(new Unselector());

		setColumnModel(getTable().getColumnSet());
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	public Table getTable()
	{
		return (Table) getModel();
	}

	// ------------------------------------
	// Public API
	// ------------------------------------

	public Event getFirstSelectedEvent()
	{
		final int firstSelectedRow = getSelectedRow();
		if (firstSelectedRow > -1)
		{
			return getTable().getEvent(firstSelectedRow);
		}
		return null;
	}

	public Event getLastSelectedEvent()
	{
		final int[] selectedRows = getSelectedRows();
		if (selectedRows != null && selectedRows.length != 0)
		{
			int lastIndex = selectedRows[selectedRows.length - 1];
			return getTable().getEvent(lastIndex);
		}
		return null;
	}
}
