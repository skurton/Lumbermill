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

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.traxel.lumbermill.log.Log;

public class EventListenerServer extends EventListener
{

	// ------------------------------------------
	// Instance Definition
	// ------------------------------------------

	private final ServerSocket SERVER_SOCKET;
	private final int PORT;
	private int _clientCount;
	private boolean _stopRequested = false;
	private final static int BUFFER = 65536;

	private class SocketListener extends Thread
	{
		// Class Definition
		// Instance Definition
		private final Socket SOCKET;
		private final ObjectInputStream streamObj_;
		private final DataInputStream streamData_;
		private boolean _stopRequested = false;

		// Instance Initialisation
		public SocketListener(Socket socket)
		{
			SOCKET = socket;
			ObjectInputStream streamObj = null;
			DataInputStream streamData = null;

			try
			{
				InputStream rawStream = SOCKET.getInputStream();

				try
				{
					streamObj = new ObjectInputStream(rawStream);
				}
				catch (StreamCorruptedException e)
				{
					streamObj = null;
					streamData = new DataInputStream(rawStream);
				}
			}
			catch (IOException e)
			{
				System.out.print("SocketListener-IOException");
				e.printStackTrace();
			}

			streamObj_ = streamObj;
			streamData_ = streamData;
		}

		private Level asLevel(String s) throws Exception
		{
			Level level = null;

			if (s.equals("Fatal"))
				level = Level.SEVERE;
			else if (s.equals("Error"))
				level = Level.SEVERE;
			else if (s.equals("Warning"))
				level = Level.WARNING;
			else if (s.equals("Info"))
				level = Level.INFO;
			else if (s.equals("Debug"))
				level = Level.FINER;
			else if (s.equals("Trace"))
				level = Level.FINEST;
			else
				throw new Exception("Invalid log level: " + s);

			return level;
		}

		// Thread Implementation
		public void run()
		{
			adjustClientCount(1);

			if (streamObj_ != null)
				runObj();
			else if (streamData_ != null)
				runData();
			else
				System.out.println("Streams are null");

			adjustClientCount(-1);
		}

		private void runObj()
		{
			while (!_stopRequested)
			{
				try
				{
					Event event = Event.create(streamObj_.readObject(), "");
					add(event);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					pleaseStop();
				}
			}
		}

		private void runData()
		{
			String sep = "RW5kT2ZSZWNvcmQ";
			int seplen = sep.length();
			byte[] buffer = new byte[BUFFER];
			String stream = new String();

			while (!_stopRequested)
			{
				try
				{
					sleep(10);
				}
				catch (InterruptedException e)
				{
					System.out.println(e.getMessage());
					pleaseStop();
				}
				
				try
				{
					int len = streamData_.read(buffer, 0, BUFFER);
					
					if (len == -1)
						continue;
					
					stream += new String(buffer, 0, len);
				}
				catch (IOException e)
				{
					System.out.println(e.getMessage());
					System.out.println("Error while reading socket");
					e.printStackTrace();
					break;
				}
				
				int index = stream.indexOf(sep);
				
				while (index != -1)
				{
					try
					{
						String log = stream.substring(0, index);
						processRecord(log);
					}
					catch (Exception e)
					{
						System.out.println(e.getMessage());
						System.out.println("Bad record");
					}
					
					stream = stream.substring(index + seplen);
					index = stream.indexOf(sep);
				}
			}
		}
		
		private void processRecord(String log) throws Exception
		{
			int j = 0;
			
			// Level
			for (j = 0; j < log.length(); ++j)
				if (log.charAt(j) == ';')
					break;

			if (j == log.length())
				throw new Exception("Invalid format (1)");

			Level level = asLevel(log.substring(0, j));
			log = log.substring(j + 1);
			
			// Source
			for (j = 0; j < log.length(); ++j)
				if (log.charAt(j) == ';')
					break;

			if (j == log.length())
				throw new Exception("Invalid format (2)");

			String source = log.substring(0, j);
			log = log.substring(j + 1);
			
			// Appli
			for (j = 0; j < log.length(); ++j)
				if (log.charAt(j) == ';')
					break;

			if (j == log.length())
				throw new Exception("Invalid format (3)");

			String appli = log.substring(0, j);
			log = log.substring(j + 1);
			
			// Rest
			LogRecord record = new LogRecord(level, log);
			record.setLoggerName(source);
			Event event = Event.create(record, appli);
			add(event);
		}

		// Object Implementation
		public void finalize() throws Throwable
		{
			try
			{
				SOCKET.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				if (streamObj_ != null)
					streamObj_.close();

				if (streamData_ != null)
					streamData_.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			super.finalize();
		}
	}

	// ------------------------------------------
	// Instance Initialization
	// ------------------------------------------

	public EventListenerServer(Log sink, int port) throws IOException
	{
		super(sink);

		PORT = port;
		SERVER_SOCKET = openServerSocket(PORT);
		start();
	}

	private ServerSocket openServerSocket(int port) throws IOException
	{
		ServerSocket serverSocket = null;

		try
		{
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(1000);
		}
		catch (IOException e)
		{
			if (serverSocket != null)
			{
				try
				{
					serverSocket.close();
				}
				catch (IOException e2)
				{
					// this socket is dead and about
					// to go out of scope - ignore
					throw e;
				}
			}
			throw e;
		}

		return serverSocket;
	}

	// ---------------------------------------
	// Object Implementation
	// ---------------------------------------

	public void finalize() throws Throwable
	{
		close();
		super.finalize();
	}

	// --------------------------------------
	// Thread Implementation
	// --------------------------------------

	public void run()
	{
		while (!_stopRequested)
		{
			try
			{
				Socket socket = SERVER_SOCKET.accept();
				listen(socket);
			}
			catch (SocketTimeoutException e)
			{
				// this is normal - gives the thread
				// a chance to stop
			}
			catch (SocketException e)
			{
				System.out.print("SocketException");
				e.printStackTrace();
				pleaseStop();
			}
			catch (IOException e)
			{
				System.out.print("IOException");
				e.printStackTrace();
				pleaseStop();
			}
		}
	}

	// --------------------------------------
	// EventListener Implementation
	// --------------------------------------

	public void close()
	{
		pleaseStop();
		if (SERVER_SOCKET != null)
		{
			try
			{
				// wait for the socket to stop
				synchronized (this)
				{
					this.wait(1000);
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				SERVER_SOCKET.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		firePropertyChange(STATUS_PROPERTY, null, getStatus());
	}

	public String getPortString()
	{
		return String.valueOf(PORT);
	}

	public String getHost()
	{
		return "localhost";
	}

	public String getType()
	{
		return "Server";
	}

	public String getStatus()
	{
		if (SERVER_SOCKET.isBound())
			return (_clientCount + " Clients");
		
		return "Closed";
	}

	// --------------------------------------
	// Private Instance API
	// -------------------------------------

	private void pleaseStop()
	{
		System.out.println("Stopped");
		_stopRequested = true;
	}

	private void listen(Socket source)
	{
		new SocketListener(source).start();
	}

	private synchronized void adjustClientCount(int amount)
	{
		_clientCount += amount;
		firePropertyChange(STATUS_PROPERTY, null, getStatus());
	}
}
