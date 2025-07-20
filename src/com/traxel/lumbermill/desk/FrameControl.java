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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JInternalFrame;

public abstract class FrameControl extends AbstractAction
{

	// ---------------------------------------------
	// Class Definition
	// ---------------------------------------------

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static abstract class Tile extends FrameControl
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		// Class Definition
		public static final int HORIZONTAL = 0, VERTICAL = 1;

		// Instance Initialization
		public Tile(MillDesktop desktop, String name)
		{
			super(desktop, name);
		}

		// Interface
		public abstract int getOrientation();

		// FrameControl Implementation
		public final void layout()
		{
			final int ORIENTATION = getOrientation();
			JInternalFrame frame;
			List<JInternalFrame> frames;
			Iterator<JInternalFrame> it;
			Dimension deskSize;
			Dimension frameSize;
			int x, y, width, height;
			int deskWidth;
			int deskHeight;

			frames = getActiveFrames();
			if (frames.size() == 0)
			{
				return;
			}
			deskSize = DESKTOP.getSize();
			deskWidth = (int) deskSize.getWidth();
			deskHeight = (int) deskSize.getHeight();
			if (ORIENTATION == VERTICAL)
			{
				width = deskWidth / frames.size();
				height = deskHeight;
			}
			else
			{
				width = deskWidth;
				height = deskHeight / frames.size();
			}
			frameSize = new Dimension(width, height);
			x = 0;
			y = 0;
			it = frames.iterator();
			while (it.hasNext())
			{
				frame = (JInternalFrame) it.next();
				try
				{
					adjustFrame(frame, frameSize, x, y);
					if (ORIENTATION == VERTICAL)
					{
						x += width;
					}
					else
					{
						y += height;
					}
				}
				catch (PropertyVetoException e)
				{
					// Shouldn't ever happen.
					e.printStackTrace();
				}
			}
		}
	}

	public static class TileHorizontal extends Tile
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public TileHorizontal(MillDesktop desktop)
		{
			super(desktop, "Tile Horizontal");
		}

		public int getOrientation()
		{
			return HORIZONTAL;
		}
	}

	public static class TileVertical extends Tile
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public TileVertical(MillDesktop desktop)
		{
			super(desktop, "Tile Vertical");
		}

		public int getOrientation()
		{
			return VERTICAL;
		}
	}

	public static class Cascade extends FrameControl
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		// Class Definition
		private static final int INCREMENT = 30;

		// Class Initialization
		public Cascade(MillDesktop desktop)
		{
			super(desktop, "Cascade");
		}

		// FrameControl Implementation
		public void layout()
		{
			JInternalFrame frame;
			List<JInternalFrame> frames;
			Iterator<JInternalFrame> it;
			Dimension deskSize;
			Dimension frameSize;
			int x, y, width, height;
			int totalShrink;

			frames = getActiveFrames();
			if (frames.size() == 0)
			{
				return;
			}
			deskSize = DESKTOP.getSize();
			totalShrink = INCREMENT * frames.size();
			width = (int) (deskSize.getWidth() - totalShrink);
			height = (int) (deskSize.getHeight() - totalShrink);
			frameSize = new Dimension(width, height);
			x = 0;
			y = 0;
			it = frames.iterator();
			while (it.hasNext())
			{
				frame = (JInternalFrame) it.next();
				try
				{
					adjustFrame(frame, frameSize, x, y);
					x += INCREMENT;
					y += INCREMENT;
				}
				catch (PropertyVetoException e)
				{
					// Shouldn't ever happen
					e.printStackTrace();
				}
			}
		}
	}

	public static class MinimizeAll extends FrameControl
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		// Instance Initialization
		public MinimizeAll(MillDesktop desktop)
		{
			super(desktop, "Minimize All");
		}

		// FrameControl Implementation
		public void layout()
		{
			JInternalFrame frame;
			List<JInternalFrame> frames;
			Iterator<JInternalFrame> it;

			frames = getActiveFrames();
			if (frames.size() == 0)
			{
				return;
			}
			it = frames.iterator();
			while (it.hasNext())
			{
				frame = (JInternalFrame) it.next();
				try
				{
					frame.setIcon(true);
				}
				catch (PropertyVetoException e)
				{
					// Shouldn't ever happen
					e.printStackTrace();
				}
			}
		}
	}

	public static class RestoreAll extends FrameControl
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		// Instance Initialization
		public RestoreAll(MillDesktop desktop)
		{
			super(desktop, "Restore All");
		}

		// FrameControl Implementation
		public void layout()
		{
			JInternalFrame frame;
			List<JInternalFrame> frames;
			Iterator<JInternalFrame> it;

			frames = getAllFrames();
			if (frames.size() == 0)
			{
				return;
			}
			it = frames.iterator();
			while (it.hasNext())
			{
				frame = (JInternalFrame) it.next();
				try
				{
					frame.setIcon(false);
					frame.setMaximum(false);
				}
				catch (PropertyVetoException e)
				{
					// Shouldn't ever happen
					e.printStackTrace();
				}
			}
		}
	}

	public static class MaximizeFrame extends FrameControl
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		// Instance Definition
		private final JInternalFrame FRAME;

		// Instance Initialization
		public MaximizeFrame(MillDesktop desktop, JInternalFrame frame)
		{
			super(desktop, frame.getTitle());
			FRAME = frame;
		}

		// FrameControl Implementation
		public void layout()
		{
			try
			{
				FRAME.setMaximum(true);
				FRAME.toFront();
				FRAME.setSelected(true);
			}
			catch (PropertyVetoException e)
			{
				// shouldn't ever happen
				e.printStackTrace();
			}
		}
	}

	// -------------------------------------------
	// Instance Definition
	// -------------------------------------------

	protected final MillDesktop DESKTOP;

	// ---------------------------------------------
	// Instance Initialization
	// ---------------------------------------------

	public FrameControl(MillDesktop desktop, String name)
	{
		super(name);
		DESKTOP = desktop;
	}

	// ---------------------------------------------
	// Implementation Interface
	// ---------------------------------------------

	public abstract void layout();

	// ----------------------------------------------
	// AbstractAction Implementation
	// ---------------------------------------------

	public void actionPerformed(ActionEvent e)
	{
		synchronized (FrameControl.class)
		{
			layout();
		}
	}

	// --------------------------------------------
	// Inherited API
	// --------------------------------------------

	protected List<JInternalFrame> getActiveFrames()
	{
		JInternalFrame[] allFrames;
		JInternalFrame frame;
		List<JInternalFrame> frames;

		allFrames = DESKTOP.getAllFrames();
		if (allFrames == null || allFrames.length == 0)
		{
			return Collections.EMPTY_LIST;
		}
		frames = new ArrayList<JInternalFrame>();
		for (int i = 0; i < allFrames.length; i++)
		{
			frame = allFrames[i];
			if (!frame.isClosed() && !frame.isIcon())
			{
				frames.add(frame);
			}
		}
		return frames;
	}

	protected List<JInternalFrame> getAllFrames()
	{
		return Arrays.asList(DESKTOP.getAllFrames());
	}

	protected void adjustFrame(JInternalFrame frame, Dimension size, int x,
			int y) throws PropertyVetoException
	{
		frame.setMaximum(false);
		frame.setSize(size);
		frame.toFront();
		frame.setSelected(true);
		frame.setLocation(x, y);
	}
}
