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

import com.traxel.lumbermill.event.Event;

public interface LogListener
{

	// -------------------------------------
	// Implementation API
	// -------------------------------------

	public void added(Log source, Event event);

	/**
	 * No, this is not safe - you can change the contents of events. Don't. This
	 * is for performance.
	 */
	public void removed(Log source, Event[] events);

	public void cleared(Log source);
}
