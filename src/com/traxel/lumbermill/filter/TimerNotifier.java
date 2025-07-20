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

import javax.swing.Timer;

/**
 * Sends notifications after a 250 ms delay, aggregates multiple notifications.
 * This is public so people outside the package can use it.
 */
public class TimerNotifier extends FilterNotifier
{

	// -------------------------------------------
	// Instance Definition
	// -------------------------------------------

	private final Timer TIMER;

	private class FilterTimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			superFireFilterChange();
		}
	}

	// ----------------------------------------
	// Instance Initialization
	// ----------------------------------------

	{
		TIMER = new Timer(250, new FilterTimerListener());
		TIMER.setRepeats(false);
	}

	public TimerNotifier(Filter owner)
	{
		super(owner);
	}

	// ----------------------------------------
	// Public Instance API
	// ----------------------------------------

	public synchronized void fireFilterChange()
	{
		if (!TIMER.isRunning())
		{
			TIMER.start();
		}
	}

	// ---------------------------------------
	// Private Instance API
	// ---------------------------------------

	private synchronized void superFireFilterChange()
	{
		super.fireFilterChange();
	}
}
