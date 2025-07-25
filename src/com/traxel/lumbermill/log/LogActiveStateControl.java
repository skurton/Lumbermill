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

package com.traxel.lumbermill.log;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class LogActiveStateControl implements ActionListener
{

	// -------------------------------------------
	// Instance Definition
	// -------------------------------------------

	public final LogActiveStateView VIEW;
	private final Log LOG;
	private final JButton PLAY;
	private final JButton PAUSE;
	private final JButton STOP;
	private final JButton CLEAR;

	// -------------------------------------------
	// Instance Initialization
	// -------------------------------------------

	public LogActiveStateControl(Log log)
	{
		VIEW = new LogActiveStateView(log);
		LOG = log;
		PLAY = VIEW.PLAY;
		PAUSE = VIEW.PAUSE;
		STOP = VIEW.STOP;
		CLEAR = VIEW.CLEAR;
		PLAY.addActionListener(this);
		PAUSE.addActionListener(this);
		STOP.addActionListener(this);
		CLEAR.addActionListener(this);
	}

	// -------------------------------------------
	// ActionListener Implementation
	// -------------------------------------------

	public void actionPerformed(ActionEvent e)
	{
		final Object source = e.getSource();

		if (PLAY.equals(source))
		{
			LOG.play();
		}
		else if (PAUSE.equals(source))
		{
			LOG.pause();
		}
		else if (STOP.equals(source))
		{
			LOG.stop();
		}
		else if (CLEAR.equals(source))
		{
			LOG.clear();
		}
	}
}
