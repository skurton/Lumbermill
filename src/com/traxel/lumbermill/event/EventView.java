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

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

class EventView extends JTextPane
{

	// -------------------------------------------
	// Instance Initialization
	// -------------------------------------------

	private final Style BOLD;
	private final Style REGULAR;

	public EventView()
	{
		StyleContext context;

		setEditable(false);
		context = StyleContext.getDefaultStyleContext();
		REGULAR = context.getStyle(StyleContext.DEFAULT_STYLE);
		addStyle("Regular", REGULAR);
		BOLD = addStyle("Bold", REGULAR);
		StyleConstants.setBold(BOLD, true);
	}

	// -------------------------------------------
	// Public API
	// -------------------------------------------

	public void setEvent(Event event)
	{
		try
		{
			getDocument().remove(0, getDocument().getLength());
		}
		catch (BadLocationException e)
		{
			// this should never happen
			e.printStackTrace();
		}

		if (event != null)
		{
			insert(event, Accessor.MESSAGE);
			
			if (event.getLocation() != null)
				insert("Location", event.getLocation());
			
			insert(event, Accessor.NDC, false);
			
			if (event.getStackTrace() != null)
				insert("Stack Trace", event.getStackTrace());
		}
		
		setCaretPosition(0);
	}

	private void insert(Event event, Accessor accessor, boolean showIfNull)
	{
		if (showIfNull || accessor.getValue(event) != null)
		{
			insert(accessor.getName(), accessor.getString(event));
		}
	}

	private void insert(String header, String message)
	{
		try
		{
			getDocument().insertString(getDocument().getLength(), header + "\n", BOLD);
			getDocument().insertString(getDocument().getLength(), message + "\n\n", REGULAR);
		}
		catch (BadLocationException e)
		{
			// this should never happen
			e.printStackTrace();
		}
	}

	private void insert(Event event, Accessor accessor)
	{
		insert(accessor.getString(event));
	}

	private void insert(String message)
	{
		try
		{
			getDocument().insertString(getDocument().getLength(), message + "\n\n", REGULAR);
		}
		catch (BadLocationException e)
		{
			// this should never happen
			e.printStackTrace();
		}
	}
}
