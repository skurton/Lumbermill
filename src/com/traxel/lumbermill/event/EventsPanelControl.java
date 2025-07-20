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

public class EventsPanelControl implements PropertyChangeListener
{

	// --------------------------------------
	// Instance Definition
	// --------------------------------------

	private final TableControl TABLE_CONTROL = new TableControl();
	private final EventView EVENT_VIEW = new EventView();
	private final EventsPanel EVENTS_PANEL = new EventsPanel(
			TABLE_CONTROL.getView(), EVENT_VIEW);

	// --------------------------------------
	// Instance Initialization
	// --------------------------------------

	{
		TABLE_CONTROL.addPropertyListener(this);
	}

	// --------------------------------------
	// Instance Accessors
	// --------------------------------------

	public TableControl getTableControl()
	{
		return TABLE_CONTROL;
	}

	// --------------------------------------
	// PropertyChangeListener Implementation
	// ---------------------------------------

	public void propertyChange(PropertyChangeEvent e)
	{
		String property = e.getPropertyName();
		if (TableControl.FIRST_SELECTED_EVENT.equals(property))
		{
			EVENT_VIEW.setEvent((Event) e.getNewValue());
		}
	}

	// ---------------------------------------
	// Instance API
	// ---------------------------------------

	public EventsPanel getView()
	{
		return EVENTS_PANEL;
	}
}
