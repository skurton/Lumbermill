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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class LogActiveStateView extends JPanel implements
		PropertyChangeListener
{

	// ---------------------------------------
	// Instance Definition
	// ---------------------------------------

	private final Log LOG;
	private final ActiveStateIcon PLAY_ICON = new ActiveStateIcon.PlayIcon(),
			PAUSE_ICON = new ActiveStateIcon.PauseIcon(),
			STOP_ICON = new ActiveStateIcon.StopIcon(),
			CLEAR_ICON = new ActiveStateIcon.ClearIcon();

	final JButton PLAY = new JButton(PLAY_ICON),
			PAUSE = new JButton(PAUSE_ICON), STOP = new JButton(STOP_ICON),
			CLEAR = new JButton(CLEAR_ICON);

	// ---------------------------------------
	// Instance Initialization
	// ---------------------------------------

	public LogActiveStateView(Log log)
	{
		LOG = log;
		LOG.addPropertyListener(this);
		if (Log.PLAY == LOG.getActiveState())
		{
			PLAY_ICON.setActive(true);
		}
		else if (Log.PAUSE == LOG.getActiveState())
		{
			PAUSE_ICON.setActive(true);
		}
		else if (Log.STOP == LOG.getActiveState())
		{
			STOP_ICON.setActive(true);
		}
		add(PLAY);
		add(PAUSE);
		add(STOP);
		add(CLEAR);
	}

	// ----------------------------------------
	// PropertyChangeListener Implementation
	// ----------------------------------------

	public void propertyChange(PropertyChangeEvent e)
	{
		if (LOG.equals(e.getSource())
				&& "_activeState".equals(e.getPropertyName()))
		{
			int newValue;

			PLAY_ICON.setActive(false);
			PAUSE_ICON.setActive(false);
			STOP_ICON.setActive(false);
			CLEAR_ICON.setActive(false);
			newValue = ((Integer) e.getNewValue()).intValue();
			if (Log.PLAY == newValue)
			{
				PLAY_ICON.setActive(true);
			}
			else if (Log.PAUSE == newValue)
			{
				PAUSE_ICON.setActive(true);
			}
			else if (Log.STOP == newValue)
			{
				STOP_ICON.setActive(true);
			}
			repaint();
		}
	}
}
