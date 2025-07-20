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

package com.traxel.lumbermill;

import com.traxel.lumbermill.desk.MillFrame;
import com.traxel.lumbermill.desk.MillFrameControl;
import com.traxel.lumbermill.event.EventListener;
import com.traxel.lumbermill.event.EventListenerStatus;
import com.traxel.lumbermill.log.Log;

public abstract class Mill
{

	// ------------------------------------------
	// Instance Definition
	// ------------------------------------------

	private final MillFrameControl MILL_FRAME_CONTROL;

	// ------------------------------------------
	// Instance Initialization
	// ------------------------------------------

	public Mill(String name)
	{
		MILL_FRAME_CONTROL = new MillFrameControl(this, name);
	}

	// -------------------------------------------
	// Instance Accessors
	// ------------------------------------------

	public MillFrame getMillFrame()
	{
		return MILL_FRAME_CONTROL.getFrame();
	}

	// ----------------------------------------
	// Instance Convenience Accessors
	// ----------------------------------------

	protected Log getLog()
	{
		return MILL_FRAME_CONTROL.getLog();
	}

	// ----------------------------------------
	// Implementation API
	// ----------------------------------------

	public abstract void closeEventListeners();

	// ----------------------------------------
	// Inherited API
	// ----------------------------------------

	protected void setEventListener(EventListener listener)
	{
		EventListenerStatus status;

		status = new EventListenerStatus(listener);
		getMillFrame().setEventListenerStatus(status);
	}
}
