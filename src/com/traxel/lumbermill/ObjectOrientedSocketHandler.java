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

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.ErrorManager;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * This is an object oriented version of java.util.logging.SocketHandler. Some
 * wild-eyed programming theorists are beginning to see the world of programming
 * in terms of objects instead of the strings that Sun's SocketHandler
 * transmits. Hopefully if anyone from Sun reads this they have a sufficient
 * sense of humour to laugh at it. It's a joke, just a little good-natured
 * ribbing.
 */
public class ObjectOrientedSocketHandler extends Handler
{

	// ------------------------------------
	// Instance Definition
	// ------------------------------------

	private final ObjectOutputStream STREAM;

	// ------------------------------------
	// Instance Initialization
	// ------------------------------------

	public ObjectOrientedSocketHandler(String host, int port)
	{
		Socket socket;
		OutputStream outStream;
		ObjectOutputStream stream = null;

		try
		{
			socket = new Socket(host, port);
			outStream = socket.getOutputStream();
			stream = new ObjectOutputStream(outStream);
		}
		catch (IOException e)
		{
			reportError(e.getMessage(), e, ErrorManager.OPEN_FAILURE);
		}

		STREAM = stream;
	}

	// ------------------------------------
	// Handler Implementation
	// ------------------------------------

	public void close()
	{
		try
		{
			STREAM.close();
		}
		catch (IOException e)
		{
			reportError(e.getMessage(), e, ErrorManager.CLOSE_FAILURE);
		}
	}

	public void flush()
	{
		try
		{
			STREAM.flush();
		}
		catch (IOException e)
		{
			reportError(e.getMessage(), e, ErrorManager.FLUSH_FAILURE);
		}
	}

	public void publish(LogRecord record)
	{
		try
		{
			STREAM.writeObject(record);
			STREAM.flush();
		}
		catch (IOException e)
		{
			reportError(e.getMessage(), e, ErrorManager.WRITE_FAILURE);
		}
	}
}
