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

import java.util.List;
import java.util.logging.LogRecord;

import org.apache.log4j.spi.LoggingEvent;

public abstract class Event
{
	private String appli;

	// ------------------------------------
	// Class API
	// ------------------------------------

	protected static Event create(Object obj, String appli)
	{
		Event event = null;

		if (obj instanceof LogRecord)
		{
			LogRecord record = (LogRecord) obj;
			event = new JavaEvent(record);
		}
		else if (obj instanceof LoggingEvent)
		{
			LoggingEvent log4j = (LoggingEvent) obj;
			event = new Log4jEvent(log4j);
		}
		
		event.appli = appli;

		return event;
	}

	// ------------------------------------
	// Implementation API
	// ------------------------------------

	/**
	 * A number on the range 0 - 1.
	 */
	public abstract Severity getSeverity();

	public abstract String getSource();

	public String getAppli()
	{
		return appli;
	}

	public abstract List<?> getSourceComponents();

	public abstract String getLastSourceComponent();

	public abstract String getMessage();

	public abstract Throwable getThrown();

	public abstract long getTimestamp();

	public abstract long getElapsedTime();

	public abstract boolean hasThrown();

	public abstract String getStackTrace();

	public abstract String getLocation();

	public abstract String getNDC();
	
	private String appli_;
}
