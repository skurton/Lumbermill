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

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;

class JavaEvent extends AbstractEvent
{

	// -----------------------------------------
	// Class Definition
	// -----------------------------------------

	private static final Map<Level, Severity> SEVERITY_MAP;

	// -------------------------------------------
	// Class Initialization
	// -------------------------------------------

	static
	{
		Map<Level, Severity> severityMap = new HashMap<Level, Severity>();
		severityMap.put(Level.FINEST, Severity.LEVEL_1);
		severityMap.put(Level.FINER, Severity.LEVEL_2);
		severityMap.put(Level.FINE, Severity.LEVEL_3);
		severityMap.put(Level.CONFIG, Severity.LEVEL_4);
		severityMap.put(Level.INFO, Severity.LEVEL_5);
		severityMap.put(Level.WARNING, Severity.LEVEL_6);
		severityMap.put(Level.SEVERE, Severity.LEVEL_8);
		SEVERITY_MAP = Collections.unmodifiableMap(severityMap);
	}

	// ------------------------------------------
	// Class API
	// ------------------------------------------

	private static Severity estimateSeverity(Level observed)
	{
		Iterator<Level> it;
		Level known;
		Level estimated;
		Severity severity;
		boolean moreThanObserved;
		boolean lessThanEstimated;

		estimated = Level.SEVERE;
		it = SEVERITY_MAP.keySet().iterator();
		while (it.hasNext())
		{
			known = (Level) it.next();
			if (estimated == null)
			{
				estimated = known;
			}
			else
			{
				moreThanObserved = known.intValue() > observed.intValue();
				lessThanEstimated = known.intValue() < estimated.intValue();
				if (moreThanObserved && lessThanEstimated)
				{
					estimated = known;
				}
			}
		}

		severity = (Severity) SEVERITY_MAP.get(estimated);
		if (severity == null)
		{
			severity = Severity.LEVEL_8;
		}

		return severity;
	}

	public static Severity getSeverity(Level level)
	{
		Severity severity;

		severity = (Severity) SEVERITY_MAP.get(level);
		if (severity == null)
		{
			severity = estimateSeverity(level);
		}

		return severity;
	}

	// -------------------------------------
	// Instance Definition
	// -------------------------------------

	private final LogRecord RECORD;
	private final long RECEIVE_TIME = new Date().getTime();

	// -------------------------------------
	// Instance Initialization
	// -------------------------------------

	public JavaEvent(LogRecord record)
	{
		RECORD = record;
	}

	// ------------------------------------
	// Event Implementation
	// ------------------------------------

	public Severity getSeverity()
	{
		return getSeverity(RECORD.getLevel());
	}

	public String getSource()
	{
		return RECORD.getLoggerName();
	}

	public String getMessage()
	{
		return RECORD.getMessage();
	}

	public Throwable getThrown()
	{
		return RECORD.getThrown();
	}

	public long getTimestamp()
	{
		return RECEIVE_TIME;
	}

	public String getLocation()
	{
		String clazz = RECORD.getSourceClassName();
		String method = RECORD.getSourceMethodName();

		if (clazz != null || method != null)
		{
			return clazz + "." + method;
		}
		return null;
	}

	public String getNDC()
	{
		return null;
	}
}
