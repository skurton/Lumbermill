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
import java.awt.Dimension;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;

public class MillDesktop extends JDesktopPane
{

	// -----------------------------------------
	// Instance Initialization
	// -----------------------------------------

	{
		setPreferredSize(new Dimension(800, 600));
	}

	// ----------------------------------------
	// Instance API
	// ----------------------------------------

	protected void addImpl(Component comp, Object constraints, int index)
	{
		super.addImpl(comp, constraints, index);
		if (comp instanceof MillFrame)
		{
			try
			{
				((MillFrame) comp).toFront();
				((MillFrame) comp).setSelected(true);
			}
			catch (PropertyVetoException e)
			{
				// this should never happen
				e.printStackTrace();
			}
		}
	}
}
