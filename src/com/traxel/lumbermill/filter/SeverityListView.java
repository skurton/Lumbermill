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

package com.traxel.lumbermill.filter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

class SeverityListView extends JPopupMenu implements ActionListener
{

	// ---------------------------------------
	// ActionListener Implementation
	// ---------------------------------------

	public void actionPerformed(ActionEvent e)
	{
		setVisible(false);
	}

	// ---------------------------------------
	// Public Instance API
	// ---------------------------------------

	public synchronized void setActions(List severityActions)
	{
		Action severityAction;
		Iterator it;
		JMenuItem item;

		removeActions();

		it = severityActions.iterator();
		while (it.hasNext())
		{
			severityAction = (Action) it.next();
			item = add(severityAction);
			item.addActionListener(this);
		}
	}

	private synchronized void removeActions()
	{
		Object[] elements;

		setVisible(false);
		elements = getSubElements();
		if (elements != null)
		{
			for (int i = elements.length - 1; i >= 0; i--)
			{
				remove(i);
			}
		}
	}
}
