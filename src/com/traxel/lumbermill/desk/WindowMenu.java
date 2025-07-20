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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.traxel.lumbermill.desk.FrameControl.Cascade;
import com.traxel.lumbermill.desk.FrameControl.MaximizeFrame;
import com.traxel.lumbermill.desk.FrameControl.MinimizeAll;
import com.traxel.lumbermill.desk.FrameControl.RestoreAll;
import com.traxel.lumbermill.desk.FrameControl.TileHorizontal;
import com.traxel.lumbermill.desk.FrameControl.TileVertical;

public class WindowMenu extends JMenu
{

	// --------------------------------------
	// Class Definition
	// --------------------------------------

	private static class TitleChangeListener implements PropertyChangeListener
	{
		// Class Definition
		private static final String PROPERTY = JInternalFrame.TITLE_PROPERTY;
		// Instance Definition
		private final JInternalFrame FRAME;
		private final JMenuItem MENU_ITEM;

		// Instance Initialization
		public TitleChangeListener(JInternalFrame frame, JMenuItem menuItem)
		{
			FRAME = frame;
			MENU_ITEM = menuItem;
			FRAME.addPropertyChangeListener(this);
		}

		// PropertyChange Implementation
		public void propertyChange(PropertyChangeEvent e)
		{
			if (FRAME.equals(e.getSource())
					&& PROPERTY.equals(e.getPropertyName()))
			{
				MENU_ITEM.setText(e.getNewValue().toString());
			}
		}
	}

	// ---------------------------------------
	// Instance Definition
	// ---------------------------------------

	private class FrameControlRemover extends InternalFrameAdapter
	{
		private final JMenuItem MENU_ITEM;

		public FrameControlRemover(JInternalFrame frame, JMenuItem menuItem)
		{
			MENU_ITEM = menuItem;
			frame.addInternalFrameListener(this);
		}

		public void internalFrameClosed(InternalFrameEvent e)
		{
			remove(MENU_ITEM);
		}
	}

	// ---------------------------------------
	// Instance Initialization
	// ---------------------------------------

	public WindowMenu(MillDesktop desktop)
	{
		super("Windows");
		Action action;

		add(new Cascade(desktop));
		add(new TileHorizontal(desktop));
		add(new TileVertical(desktop));
		add(new MinimizeAll(desktop));
		add(new RestoreAll(desktop));
		addSeparator();
	}

	// ---------------------------------------
	// Public API
	// ---------------------------------------

	public void addMillFrame(MillDesktop desktop, MillFrame frame)
	{
		MaximizeFrame maximize;
		JMenuItem menuItem;
		FrameControlRemover remover;

		maximize = new MaximizeFrame(desktop, frame);
		menuItem = add(maximize);
		new TitleChangeListener(frame, menuItem);
		new FrameControlRemover(frame, menuItem);
	}
}
