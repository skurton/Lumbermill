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

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Accessor
{

	// --------------------------------------------------
	// Class Definition
	// --------------------------------------------------

	@SuppressWarnings("serial")
	private static final Format DEFAULT_TIME_FORMAT = new SimpleDateFormat(
			"HH:mm:ss.SSS"), DECIMAL_SECONDS = new Format()
	{
		public StringBuffer format(Object o, StringBuffer buff,
				FieldPosition pos)
		{
			long value;
			long left;
			long right;

			value = ((Long) o).longValue();
			left = value / 1000;
			buff.append("" + left + ".");
			right = value % 1000;
			if (right == 0)
			{
				buff.append("000");
			}
			else if (right < 10)
			{
				buff.append("00" + right);
			}
			else if (right < 100)
			{
				buff.append("0" + right);
			}
			else
			{
				buff.append("" + right);
			}

			return buff;
		}

		public Object parseObject(String source, ParsePosition pos)
		{
			return Long.valueOf(source.replaceAll("\\.", ""));
		}
	};

	public static final Accessor SEVERITY = new Accessor("Severity")
	{
		public Object getValue(Event event)
		{
			return event.getSeverity();
		}

		public Class<?> getType()
		{
			return Severity.class;
		}
	};

	public static final Accessor TIMESTAMP = new Accessor("Timestamp")
	{
		{
			setFormat(DEFAULT_TIME_FORMAT);
		}

		public Object getValue(Event event)
		{
			return new Date(event.getTimestamp());
		}

		public Class<?> getType()
		{
			return Date.class;
		}
	};

	public static final Accessor ELAPSED_TIME = new Accessor("Elapsed Time",
			"Elapsed")
	{
		{
			setFormat(DECIMAL_SECONDS);
		}

		public Object getValue(Event event)
		{
			return new Long(event.getElapsedTime());
		}

		public Class<?> getType()
		{
			return Long.TYPE;
		}
	};

	public static final Accessor HAS_THROWN = new Accessor("Has Thrown", "T")
	{
		public Object getValue(Event event)
		{
			return Boolean.valueOf(event.hasThrown());
		}

		public Class<?> getType()
		{
			return Boolean.class;
		}
	};

	public static final Accessor SHORT_SOURCE = new Accessor("Short Source",
			"Source")
	{
		public Object getValue(Event event)
		{
			return event.getLastSourceComponent();
		}

		public Class<?> getType()
		{
			return String.class;
		}
	};

	public static final Accessor SOURCE = new Accessor("Source")
	{
		public Object getValue(Event event)
		{
			return event.getSource();
		}

		public Class<?> getType()
		{
			return String.class;
		}
	};

	public static final Accessor APPLI = new Accessor("Appli")
	{
		public Object getValue(Event event)
		{
			return event.getAppli();
		}

		public Class<?> getType()
		{
			return String.class;
		}
	};

	public static final Accessor MESSAGE = new Accessor("Message")
	{
		public Object getValue(Event event)
		{
			return event.getMessage();
		}

		public Class<?> getType()
		{
			return String.class;
		}
	};

	public static final Accessor NDC = new Accessor("NDC (Log4J)", "NDC")
	{
		public Object getValue(Event event)
		{
			return event.getNDC();
		}

		public Class<?> getType()
		{
			return String.class;
		}
	};

	// --------------------------------------------------
	// Instance Definition
	// --------------------------------------------------

	private Format _format;
	private final String _name;
	private final String _shortName;
	private final String _description;

	// --------------------------------------------------
	// Instance Initialization
	// --------------------------------------------------

	public Accessor(String name, String shortName, String description)
	{
		_name = name;
		_shortName = shortName;
		_description = description;
	}

	public Accessor(String name, String shortName)
	{
		this(name, shortName, name);
	}

	public Accessor(String name)
	{
		this(name, name);
	}

	// --------------------------------------------------
	// Instance Accessors
	// --------------------------------------------------

	public String getName()
	{
		return _name;
	}

	public String getShortName()
	{
		return _shortName;
	}

	public String getDescription()
	{
		return _description;
	}

	public void setFormat(Format format)
	{
		_format = format;
	}

	// --------------------------------------------------
	// Implemenatation API
	// --------------------------------------------------

	public abstract Object getValue(Event event);

	public abstract Class<?> getType();

	// --------------------------------------------------
	// Public API
	// --------------------------------------------------

	public String getString(Event event)
	{
		if (_format == null)
		{
			return String.valueOf(getValue(event));
		}
		else
		{
			return _format.format(getValue(event));
		}
	}
}
