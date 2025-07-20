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

package com.traxel.lumbermill;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.traxel.lumbermill.event.EventListenerStatus;
import com.traxel.lumbermill.event.EventsPanel;
import com.traxel.lumbermill.event.TableStatus;
import com.traxel.lumbermill.filter.FilterSetView;
import com.traxel.lumbermill.log.LogActiveStateView;
import com.traxel.lumbermill.log.LogStatus;

public class MillView extends JPanel
{
	private static final long serialVersionUID = 1;

	// --------------------------------------
	// Instance Initialization
	// --------------------------------------

	public MillView(LogActiveStateView logActiveStateView, LogStatus logStatus,
			TableStatus tableStatus, EventsPanel eventsPanel,
			FilterSetView filterSetView)
	{
		JSplitPane treeEventsSplit;
		JPanel logStateStatusPanel;

		logStateStatusPanel = new JPanel();
		logStateStatusPanel.add(logActiveStateView);
		logStateStatusPanel.add(logStatus);
		logStateStatusPanel.add(tableStatus);
		treeEventsSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				filterSetView, eventsPanel);
		setLayout(new BorderLayout());
		add(BorderLayout.NORTH, logStateStatusPanel);
		add(BorderLayout.CENTER, treeEventsSplit);
	}

	// -------------------------------------
	// Package API
	// -------------------------------------

	public void setEventListenerStatus(EventListenerStatus status)
	{
		add(BorderLayout.SOUTH, status);
	}
}
