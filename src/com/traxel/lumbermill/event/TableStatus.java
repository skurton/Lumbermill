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

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TableStatus extends JPanel
{

	// ---------------------------------------------
	// Class Definition
	// ---------------------------------------------

	private static final Format DATE_FORMAT = new SimpleDateFormat("mm:ss.SSS");

	// ----------------------------------------------
	// Instance Definition
	// ---------------------------------------------

	private final JLabel ELAPSED_LABEL = new JLabel("  Elapsed: ",
			JLabel.TRAILING);
	private final JTextField ELAPSED_FIELD = new JTextField(6);

	// ---------------------------------------------
	// Instance Initialization
	// ---------------------------------------------

	{
		ELAPSED_LABEL.setLabelFor(ELAPSED_FIELD);
		ELAPSED_FIELD.setEditable(false);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(ELAPSED_LABEL);
		add(ELAPSED_FIELD);
	}

	// ---------------------------------------------
	// Public API
	// ---------------------------------------------

	public synchronized void setElapsed(Event a, Event b)
	{
		long aTimestamp;
		long bTimestamp;
		long delta;
		Date date;

		aTimestamp = a.getTimestamp();
		bTimestamp = b.getTimestamp();
		if (aTimestamp > bTimestamp)
		{
			delta = aTimestamp - bTimestamp;
		}
		else
		{
			delta = bTimestamp - bTimestamp;
		}
		date = new Date(delta);
		ELAPSED_FIELD.setText(DATE_FORMAT.format(date));
	}
}
