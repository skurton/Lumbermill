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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public abstract class ActiveStateIcon implements Icon
{

	// --------------------------------------------
	// Class Definition
	// --------------------------------------------

	private static final int WIDTH = 20;
	private static final int HEIGHT = 14;

	static class PlayIcon extends ActiveStateIcon
	{
		// Icon Implementation
		public void paintIcon(Component c, Graphics g, int x, int y)
		{
			int[] xs;
			int[] ys;
			if (isActive())
			{
				g.setColor(Color.GREEN);
			}
			else
			{
				g.setColor(c.getBackground());
			}
			g.fillRect(x, y, WIDTH, HEIGHT);
			xs = new int[3];
			ys = new int[3];
			xs[0] = x + 2;
			ys[0] = y + 2;
			xs[1] = x + WIDTH - 2;
			ys[1] = y + (HEIGHT / 2);
			xs[2] = x + 2;
			ys[2] = y + HEIGHT - 2;
			g.setColor(Color.BLACK);
			g.fillPolygon(xs, ys, 3);
		}
	}

	static class PauseIcon extends ActiveStateIcon
	{
		public void paintIcon(Component c, Graphics g, int x, int y)
		{
			if (isActive())
			{
				g.setColor(Color.RED);
			}
			else
			{
				g.setColor(c.getBackground());
			}
			g.fillRect(x, y, WIDTH, HEIGHT);
			g.setColor(Color.BLACK);
			g.fillRect(x + 4, y + 2, 5, HEIGHT - 4);
			g.fillRect(x + WIDTH - 4 - 5, y + 2, 5, HEIGHT - 4);
		}
	}

	static class StopIcon extends ActiveStateIcon
	{
		public void paintIcon(Component c, Graphics g, int x, int y)
		{
			if (isActive())
			{
				g.setColor(Color.RED);
			}
			else
			{
				g.setColor(c.getBackground());
			}
			g.fillRect(x, y, WIDTH, HEIGHT);
			g.setColor(Color.BLACK);
			g.fillRect(x + 4, y + 2, WIDTH - 8, HEIGHT - 4);
		}
	}

	static class ClearIcon extends ActiveStateIcon
	{
		public void paintIcon(Component c, Graphics g, int x, int y)
		{
			g.setColor(Color.BLACK);
			g.drawLine(x + 1, y + 1, x + WIDTH - 6, y + HEIGHT - 1);
			g.drawLine(x + 2, y + 1, x + WIDTH - 5, y + HEIGHT - 1);
			g.drawLine(x + 5, y + 1, x + WIDTH - 2, y + HEIGHT - 1);
			g.drawLine(x + 6, y + 1, x + WIDTH - 1, y + HEIGHT - 1);
			g.drawLine(x + WIDTH - 6, y + 1, x + 1, y + HEIGHT - 1);
			g.drawLine(x + WIDTH - 5, y + 1, x + 2, y + HEIGHT - 1);
			g.drawLine(x + WIDTH - 2, y + 1, x + 5, y + HEIGHT - 1);
			g.drawLine(x + WIDTH - 1, y + 1, x + 6, y + HEIGHT - 1);
			g.setColor(Color.RED);
			g.drawLine(x + 3, y + 1, x + WIDTH - 4, y + HEIGHT - 1);
			g.drawLine(x + 4, y + 1, x + WIDTH - 3, y + HEIGHT - 1);
			g.drawLine(x + WIDTH - 4, y + 1, x + 3, y + HEIGHT - 1);
			g.drawLine(x + WIDTH - 3, y + 1, x + 4, y + HEIGHT - 1);
		}
	}

	// --------------------------------------------
	// Instance Definition
	// --------------------------------------------

	private boolean _active = false;

	// -------------------------------------------
	// Instance Accessors
	// -------------------------------------------

	protected boolean isActive()
	{
		return _active;
	}

	public void setActive(boolean active)
	{
		_active = active;
	}

	// --------------------------------------------
	// Icon Implementation
	// --------------------------------------------

	public int getIconWidth()
	{
		return WIDTH;
	}

	public int getIconHeight()
	{
		return HEIGHT;
	}
}
