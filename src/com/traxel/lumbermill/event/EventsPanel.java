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

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class EventsPanel extends JSplitPane
{

	// ---------------------------------------
	// Instance Definition
	// ---------------------------------------

	private final TableView TABLE_VIEW;
	private final EventView EVENT_VIEW;
	private final JScrollPane TABLE_SCROLL;
	private final JScrollPane EVENT_SCROLL;

	// ---------------------------------------
	// Instance Initialization
	// ---------------------------------------

	public EventsPanel(TableView tableView, EventView eventView)
	{
		super(VERTICAL_SPLIT);
		TABLE_VIEW = tableView;
		EVENT_VIEW = eventView;
		TABLE_SCROLL = new JScrollPane(TABLE_VIEW);
		EVENT_SCROLL = new JScrollPane(EVENT_VIEW);
		TABLE_SCROLL
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		TABLE_SCROLL.setPreferredSize(new Dimension(600, 100));
		EVENT_SCROLL
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		EVENT_SCROLL.setPreferredSize(new Dimension(600, 300));
		setLeftComponent(TABLE_SCROLL);
		setRightComponent(EVENT_SCROLL);
		setResizeWeight(0.25d);
		setOneTouchExpandable(true);
	}

	// ---------------------------------------
	// Instance Accessors
	// ---------------------------------------

	public TableView getTableView()
	{
		return TABLE_VIEW;
	}

	EventView getEventView()
	{
		return EVENT_VIEW;
	}
}
