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

import com.traxel.lumbermill.event.EventListener;
import com.traxel.lumbermill.event.EventListenerClient;

public class ClientMill extends Mill
{

	// -------------------------------------------
	// Instance Definition
	// -------------------------------------------

	private final EventListener EVENT_LISTENER;

	// -------------------------------------------
	// Instance Initialization
	// -------------------------------------------

	public ClientMill(String host, int port)
	{
		super("Client: " + host + ":" + port);
		EventListener listener = null;

		try
		{
			listener = new EventListenerClient(getLog(), host, port);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		EVENT_LISTENER = listener;
		setEventListener(EVENT_LISTENER);
	}

	// ------------------------------------------
	// Mill Implementation
	// ------------------------------------------

	public void closeEventListeners()
	{
		if (EVENT_LISTENER != null)
		{
			EVENT_LISTENER.close();
		}
	}
}
