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

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegexView extends JPanel implements FilterView
{

	// ----------------------------------------------
	// Instance Definition
	// ----------------------------------------------

	final Regex REGEX;
	final JTextField FIELD;

	// ----------------------------------------------
	// Instance Initialization
	// ----------------------------------------------

	{
		REGEX = new Regex(Regex.MESSAGE);
		FIELD = new JTextField(10);
		add(FIELD);
	}

	// ----------------------------------------------
	// FilterView Implementation
	// ----------------------------------------------

	public Component getFilterComponent()
	{
		return this;
	}

	public Filter getFilter()
	{
		return REGEX;
	}
}
