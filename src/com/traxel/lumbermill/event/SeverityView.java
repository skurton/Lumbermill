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

// this doesn't belong in the model hierarchy, but it has
// to be here because javax.swing.table.TableColumnModel
// violates M/V separation.
package com.traxel.lumbermill.event;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

import com.traxel.color.model.ScreenWheel;
import com.traxel.color.model.Wheel;

public class SeverityView implements Icon
{

	// -------------------------------------------
	// Class Initialization
	// ------------------------------------------

	private static final int SIZE = 14;
	private static final Wheel WHEEL = new ScreenWheel();

	// ------------------------------------------
	// Class API
	// ------------------------------------------

	public static Color getColor(Severity severity)
	{
		if (Severity.INHERIT.equals(severity))
		{
			return Color.GRAY;
		}
		if (Severity.ALL.equals(severity))
		{
			return Color.WHITE;
		}
		if (Severity.DISABLED.equals(severity))
		{
			return Color.BLACK;
		}
		int angle;
		Color color;
		float ratio;
		float adjustedLevel;

		adjustedLevel = (float) (severity.getLevel() - 1);
		ratio = adjustedLevel / 8f;
		angle = (int) (ratio * 270f) + 30;
		color = WHEEL.getBaseColor(angle);

		return color;
	}

	// -------------------------------------------
	// Instance Initialization
	// -------------------------------------------

	private final Severity _actual;
	private final Severity _effective;

	public SeverityView(Severity actual, Severity effective)
	{
		_actual = actual;
		_effective = effective;
	}

	private Severity getActual()
	{
		return _actual;
	}

	private Severity getEffective()
	{
		return _effective;
	}

	// -------------------------------------------
	// ImageIcon Implementation
	// -------------------------------------------

	public int getIconWidth()
	{
		return SIZE;
	}

	public int getIconHeight()
	{
		return SIZE;
	}

	public void paintIcon(Component parent, Graphics g, int x, int y)
	{
		final Color bg = parent.getBackground();
		final Color color = getColor(getEffective());

		if (Severity.INHERIT.equals(getActual()))
		{
			int newX = x + 2;
			int newY = y + 2;
			int size = SIZE - 4;

			int[] xPoints;
			int[] yPoints;

			xPoints = new int[3];
			yPoints = new int[3];

			xPoints[0] = newX;
			yPoints[0] = newY;
			xPoints[1] = newX + (3 * size / 4);
			yPoints[1] = newY;
			xPoints[2] = newX;
			yPoints[2] = newY + (3 * size / 4);
			g.setColor(color);
			g.fillPolygon(xPoints, yPoints, 3);
			g.setColor(Color.BLACK);
			g.drawPolygon(xPoints, yPoints, 3);

			xPoints = new int[4];
			yPoints = new int[4];
			xPoints[0] = newX + 5;
			yPoints[0] = newY + 5 - 3;
			xPoints[1] = newX + size - 2;
			yPoints[1] = newY + size - 5;
			xPoints[2] = newX + size - 4;
			yPoints[2] = newY + size - 1;
			xPoints[3] = newX + 5 - 3;
			yPoints[3] = newY + 5;
			g.setColor(color);
			g.fillPolygon(xPoints, yPoints, 4);
			g.setColor(Color.BLACK);
			for (int i = 0; i < xPoints.length - 1; i++)
			{
				g.drawLine(xPoints[i], yPoints[i], xPoints[i + 1],
						yPoints[i + 1]);
			}

			xPoints[0] = newX + size - 2;
			yPoints[0] = newY + size - 5;
			xPoints[1] = newX + size + 2;
			yPoints[1] = newY + size - 5;
			xPoints[2] = newX + size + 2;
			yPoints[2] = newY + size - 1;
			xPoints[3] = newX + size - 4;
			yPoints[3] = newY + size - 1;
			g.setColor(color);
			g.fillPolygon(xPoints, yPoints, 4);
			g.setColor(Color.BLACK);
			for (int i = 0; i < xPoints.length - 1; i++)
			{
				g.drawLine(xPoints[i], yPoints[i], xPoints[i + 1],
						yPoints[i + 1]);
			}
		}
		else
		{
			g.setColor(Color.BLACK);
			g.fillOval(x, y, getIconWidth(), getIconHeight());
			g.setColor(color);
			g.fillOval(x + 1, y + 1, getIconWidth() - 2, getIconHeight() - 2);
		}
	}
}
