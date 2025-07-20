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

package com.traxel.lumbermill.desk;

import javax.swing.JInternalFrame;

import com.traxel.lumbermill.MillView;
import com.traxel.lumbermill.event.EventListenerStatus;
import com.traxel.lumbermill.event.EventsPanel;
import com.traxel.lumbermill.event.TableStatus;
import com.traxel.lumbermill.filter.FilterSetView;
import com.traxel.lumbermill.log.LogActiveStateView;
import com.traxel.lumbermill.log.LogStatus;

public class MillFrame extends JInternalFrame
{
	private static final long serialVersionUID = 1L;

	// ---------------------------------------------
	// Instance Definition
	// ---------------------------------------------

	public final MillView MILL_VIEW;

	// ----------------------------------------------
	// Instance Initialization
	// ----------------------------------------------

	public MillFrame(String name, LogActiveStateView logActiveStateView,
			LogStatus logStatus, TableStatus tableStatus,
			EventsPanel eventsPanel, FilterSetView filterSetView)
	{
		super(name, true, true, true, true);
		MILL_VIEW = new MillView(logActiveStateView, logStatus, tableStatus,
				eventsPanel, filterSetView);
		setContentPane(MILL_VIEW);
		pack();
		setLocation(0, 0);
		setVisible(true);
		
		try
		{
//			setMaximum(true);
		}
		catch (Exception e)
		{
		}
	}

	// ---------------------------------------------
	// Package API
	// ---------------------------------------------

	public void setEventListenerStatus(EventListenerStatus status)
	{
		MILL_VIEW.setEventListenerStatus(status);
	}
}
