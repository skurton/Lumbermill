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

public class LogControl
{

	// -------------------------------------------------
	// Instance Definition
	// -------------------------------------------------

	private final Log LOG = new Log();
	private final LogActiveStateControl ACTIVE_STATE_CONTROL = new LogActiveStateControl(
			LOG);
	private final LogStatus LOG_STATUS = new LogStatus(LOG);

	// --------------------------------------------------
	// Public Instance API
	// -------------------------------------------------

	public Log getLog()
	{
		return LOG;
	}

	public LogActiveStateView getActiveStateView()
	{
		return ACTIVE_STATE_CONTROL.VIEW;
	}

	public LogStatus getLogStatus()
	{
		return LOG_STATUS;
	}
}
