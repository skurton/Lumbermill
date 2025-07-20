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

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import com.traxel.lumbermill.Mill;
import com.traxel.lumbermill.event.EventsPanel;
import com.traxel.lumbermill.event.EventsPanelControl;
import com.traxel.lumbermill.event.TableControl;
import com.traxel.lumbermill.event.TableStatus;
import com.traxel.lumbermill.filter.FilterSetControl;
import com.traxel.lumbermill.filter.FilterSetView;
import com.traxel.lumbermill.log.Log;
import com.traxel.lumbermill.log.LogActiveStateView;
import com.traxel.lumbermill.log.LogControl;
import com.traxel.lumbermill.log.LogStatus;

public class MillFrameControl extends InternalFrameAdapter
{

	// ----------------------------------------------
	// Instance Definition
	// ----------------------------------------------

	private class TitleListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if (MouseEvent.BUTTON3 == e.getButton())
			{
				final String newTitle = getTitle();
				if (newTitle != null && !"".equals(newTitle))
				{
					FRAME.setTitle(newTitle);
				}
			}
		}

		private String getTitle()
		{
			return JOptionPane.showInputDialog(FRAME,
					"Enter the new title for this frame:", FRAME.getTitle());
		}
	}

	private final EventsPanelControl EVENTS_PANEL_CONTROL = new EventsPanelControl();
	private final MillFrame FRAME;
	private final Mill MILL;

	// ----------------------------------------------
	// Instance Initialization
	// ----------------------------------------------

	public MillFrameControl(Mill mill, String name)
	{
		JMenuBar menuBar;
		BasicInternalFrameUI ui;
		Component titlePanel;

		MILL = mill;
		FRAME = new MillFrame(name, getLogActiveStateView(), getLogStatus(),
				getTableStatus(), getEventsPanel(), getFilterSetView());
		FRAME.addInternalFrameListener(this);
		
		menuBar = new JMenuBar();
		menuBar.add(getFilterSetMenu());
		menuBar.add(getColumnMenu());
		FRAME.setJMenuBar(menuBar);
		
		ui = (BasicInternalFrameUI) FRAME.getUI();
		titlePanel = ui.getNorthPane();
		titlePanel.addMouseListener(new TitleListener());
	}

	// -------------------------------------------
	// Instance Convenience Accessors
	// ------------------------------------------

	public Log getLog()
	{
		return getLogControl().getLog();
	}

	private LogControl getLogControl()
	{
		return getTableControl().getLogControl();
	}

	private LogActiveStateView getLogActiveStateView()
	{
		return getLogControl().getActiveStateView();
	}

	private LogStatus getLogStatus()
	{
		return getLogControl().getLogStatus();
	}

	private TableStatus getTableStatus()
	{
		return getTableControl().getTableStatus();
	}

	private EventsPanel getEventsPanel()
	{
		return EVENTS_PANEL_CONTROL.getView();
	}

	private FilterSetView getFilterSetView()
	{
		return getFilterSetControl().getFilterSetView();
	}

	private JMenu getFilterSetMenu()
	{
		return getFilterSetControl().getFilterSetMenu();
	}

	private FilterSetControl getFilterSetControl()
	{
		return getTableControl().getFilterSetControl();
	}

	private TableControl getTableControl()
	{
		return EVENTS_PANEL_CONTROL.getTableControl();
	}

	private JMenu getColumnMenu()
	{
		return getTableControl().getColumnMenu();
	}

	// ----------------------------------------------
	// Instance Accessors
	// ----------------------------------------------

	public MillFrame getFrame()
	{
		return FRAME;
	}

	// ----------------------------------------
	// InternalFrameAdapter Implementation
	// ----------------------------------------

	public void internalFrameClosing(InternalFrameEvent e)
	{
		if (FRAME.equals(e.getSource()))
		{
			MILL.closeEventListeners();
		}
	}
}
