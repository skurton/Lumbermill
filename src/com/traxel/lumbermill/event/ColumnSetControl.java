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

import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

class ColumnSetControl
{

	// ----------------------------------------------
	// Instance Definition
	// ----------------------------------------------

	private class ColumnAction extends AbstractAction
	{
		// Instance Definition
		private final Column COLUMN;

		// Instance Initialization
		public ColumnAction(Column column)
		{
			super(column.getName());
			COLUMN = column;
		}

		// Action Implementation
		public void actionPerformed(ActionEvent e)
		{
			JCheckBoxMenuItem item;

			item = (JCheckBoxMenuItem) e.getSource();
			if (item.getState())
			{
				COLUMN_SET.addColumn(COLUMN);
			}
			else
			{
				COLUMN_SET.removeColumn(COLUMN);
			}
		}
	}

	private final ColumnSet COLUMN_SET = new ColumnSet();
	private final JMenu MENU = new JMenu("Columns");

	// ----------------------------------------------
	// Instance Initialization
	// ----------------------------------------------

	{
		Iterator it;
		Column column;
		ColumnAction action;
		JMenuItem item;

		it = COLUMN_SET.ALL_COLUMNS.iterator();
		while (it.hasNext())
		{
			column = (Column) it.next();
			action = new ColumnAction(column);
			item = new JCheckBoxMenuItem(action);
			item.setArmed(false);
			if (COLUMN_SET.contains(column))
			{
				item.setSelected(true);
			}
			else
			{
				item.setSelected(false);
			}
			MENU.add(item);
		}
	}

	// ----------------------------------------------
	// Instance Accessors
	// ----------------------------------------------

	public ColumnSet getColumnSet()
	{
		return COLUMN_SET;
	}

	public JMenu getMenu()
	{
		return MENU;
	}
}
