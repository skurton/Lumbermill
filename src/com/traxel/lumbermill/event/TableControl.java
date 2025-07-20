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

import javax.swing.JMenu;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.traxel.lumbermill.filter.FilterSetControl;
import com.traxel.lumbermill.log.LogControl;

public class TableControl implements ListSelectionListener
{

	// ------------------------------------
	// Class Definition
	// ------------------------------------

	public static final String FIRST_SELECTED_EVENT = "First Selected Event",
			LAST_SELECTED_EVENT = "Last Selected Event";

	// -----------------------------------
	// Instance Definition
	// -----------------------------------

	private final LogControl LOG_CONTROL = new LogControl();
	private final FilterSetControl FILTER_SET_CONTROL = new FilterSetControl();
	private final ColumnSetControl COLUMN_SET_CONTROL = new ColumnSetControl();
	private final TableView TABLE_VIEW = new TableView(LOG_CONTROL.getLog(),
			FILTER_SET_CONTROL.getFilterSet(),
			COLUMN_SET_CONTROL.getColumnSet());
	private final TableStatus TABLE_STATUS = new TableStatus();
	private final Set LISTENERS = Collections.synchronizedSet(new HashSet());
	private Event _previousFirst;
	private Event _previousLast;

	// -------------------------------------
	// Instance Initialization
	// ------------------------------------

	{
		ListSelectionModel model;

		model = getView().getSelectionModel();
		model.addListSelectionListener(this);
	}

	// -----------------------------------
	// Instance Accessors
	// -----------------------------------

	TableView getView()
	{
		return TABLE_VIEW;
	}

	public LogControl getLogControl()
	{
		return LOG_CONTROL;
	}

	public FilterSetControl getFilterSetControl()
	{
		return FILTER_SET_CONTROL;
	}

	public TableStatus getTableStatus()
	{
		return TABLE_STATUS;
	}

	public JMenu getColumnMenu()
	{
		return COLUMN_SET_CONTROL.getMenu();
	}

	// ---------------------------------------
	// ListSelectionListener Implementation
	// ---------------------------------------

	public void valueChanged(ListSelectionEvent e)
	{
		if (!e.getValueIsAdjusting())
		{
			setElapsed();
		}
	}

	// -------------------------------------
	// Public Instance API
	// -------------------------------------

	public void addPropertyListener(PropertyChangeListener l)
	{
		LISTENERS.add(l);
	}

	// -------------------------------------
	// Package Instance API
	// -------------------------------------

	Event getFirstSelectedEvent()
	{
		return TABLE_VIEW.getFirstSelectedEvent();
	}

	Event getLastSelectedEvent()
	{
		return TABLE_VIEW.getLastSelectedEvent();
	}

	// -------------------------------------
	// Private Instance API
	// -------------------------------------

	private synchronized void setElapsed()
	{
		boolean change = false;
		Event newFirst = getFirstSelectedEvent();
		Event newLast = getLastSelectedEvent();
		if (newFirst != _previousFirst && newFirst != null)
		{
			change = true;
			firePropertyChange(FIRST_SELECTED_EVENT, _previousFirst, newFirst);
			_previousFirst = newFirst;
		}
		if (newLast != _previousLast && newLast != null)
		{
			change = true;
			firePropertyChange(LAST_SELECTED_EVENT, _previousLast, newLast);
			_previousLast = newLast;
		}
		if (change)
		{
			TABLE_STATUS.setElapsed(newFirst, newLast);
		}
	}

	private void firePropertyChange(String property, Object oldValue,
			Object newValue)
	{
		synchronized (LISTENERS)
		{
			Iterator it;
			PropertyChangeListener listener;
			PropertyChangeEvent event;

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
