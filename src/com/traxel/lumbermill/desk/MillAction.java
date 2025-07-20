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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import com.traxel.lumbermill.ClientMill;
import com.traxel.lumbermill.Mill;
import com.traxel.lumbermill.ServerMill;

public abstract class MillAction extends AbstractAction
{

	// ------------------------------------------
	// Class Definition
	// ------------------------------------------

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static class ServerMillAction extends MillAction
	{
		private static final long serialVersionUID = 1L;
		private static final String NAME = "New Server Mill";
		private static final String MESSAGE = "Please enter the port to listen on.\n"
				+ "Range is 1025 - 65535.";
		protected int port_ = 0;

		public ServerMillAction(MillDesktopControl desktopControl)
		{
			super(NAME, desktopControl);
		}

		public ServerMillAction(MillDesktopControl desktopControl, int port)
		{
			super(NAME + " (" + String.valueOf(port) + ")", desktopControl);
			port_ = port;
		}

		public Mill getMill()
		{
			String portString = getSettings(MESSAGE, String.valueOf(port_));
			
			try
			{
				int port = Integer.parseInt(portString);
				return new ServerMill(port);
			}
			catch (NumberFormatException e)
			{
				System.err.println("NumberFormatException");
			}
			
			return null;
		}
	}
	
	public static class ServerPortMillAction extends ServerMillAction
	{
		private static final long serialVersionUID = 1L;

		public ServerPortMillAction(MillDesktopControl desktopControl, int port)
		{
			super(desktopControl, port);
		}

		public Mill getMill()
		{
			return new ServerMill(port_);
		}
	}

	public static class ClientMillAction extends MillAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private static final String NAME = "New Client Mill";
		private static final String MESSAGE = "Please enter the host and port to listen to.\n"
				+ "<host>:<port>, 'apps.server.com:4545',"
				+ " '127.0.0.1:6500'";
		private static final String DEFAULT = "localhost:4545";

		public ClientMillAction(MillDesktopControl desktopControl)
		{
			super(NAME, desktopControl);
		}

		public Mill getMill()
		{
			String hostPort;
			String host;
			String portString;
			String[] parts;

			hostPort = getSettings(MESSAGE, DEFAULT);
			parts = hostPort.split(":");
			if (parts != null && parts.length == 2)
			{
				host = parts[0];
				portString = parts[1];
				try
				{
					int port = Integer.parseInt(portString);
					return new ClientMill(host, port);
				}
				catch (NumberFormatException e)
				{
					System.err.println("NumberFormatException: " + portString);
				}
			}
			else
			{
				System.err
						.println("Didn't split on ':' correctly: " + hostPort);
			}
			return null;
		}
	}

	// ------------------------------------------
	// Instance Definition
	// ------------------------------------------

	private final MillDesktopControl DESKTOP_CONTROL;

	// ------------------------------------------
	// Instance Initialization
	// ------------------------------------------

	public MillAction(String name, MillDesktopControl desktopControl)
	{
		super(name);
		DESKTOP_CONTROL = desktopControl;
	}

	// ------------------------------------------
	// Implementation Interface
	// ------------------------------------------

	public abstract Mill getMill();

	// ------------------------------------------
	// AbstractAction Implementation
	// ------------------------------------------

	public void actionPerformed(ActionEvent e)
	{
		Mill mill;

		mill = getMill();
		if (mill != null)
		{
			DESKTOP_CONTROL.add(mill.getMillFrame());
		}
		else
		{
			System.err.println("Null Mill? Probably unparseable settings.");
		}
	}

	// ------------------------------------------
	// Instance API
	// ------------------------------------------

	protected String getSettings(String message, String defaultValue)
	{
		return JOptionPane.showInputDialog(DESKTOP_CONTROL.getDesktop(),
				message, defaultValue);
	}
}
