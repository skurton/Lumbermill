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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EventListenerStatus extends JPanel implements
		PropertyChangeListener
{

	// ----------------------------------------------
	// Instance Definition
	// ----------------------------------------------

	private final EventListener LISTENER;
	private final JTextField STATUS_FIELD = new JTextField(10),
			TYPE_FIELD = new JTextField(5), HOST_FIELD = new JTextField(10),
			PORT_FIELD = new JTextField(5);
	private final JLabel STATUS_LABEL = new JLabel("Listener Status: ",
			JLabel.TRAILING), TYPE_LABEL = new JLabel("  Type: ",
			JLabel.TRAILING), HOST_LABEL = new JLabel("  Host: ",
			JLabel.TRAILING), PORT_LABEL = new JLabel("  Port: ",
			JLabel.TRAILING);

	// ---------------------------------------------
	// Instance Initialization
	// ---------------------------------------------

	public EventListenerStatus(EventListener listener)
	{
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		LISTENER = listener;
		STATUS_LABEL.setLabelFor(STATUS_FIELD);
		TYPE_LABEL.setLabelFor(TYPE_FIELD);
		HOST_LABEL.setLabelFor(HOST_FIELD);
		PORT_LABEL.setLabelFor(PORT_FIELD);
		add(STATUS_LABEL);
		add(STATUS_FIELD);
		add(TYPE_LABEL);
		add(TYPE_FIELD);
		add(HOST_LABEL);
		add(HOST_FIELD);
		add(PORT_LABEL);
		add(PORT_FIELD);
		STATUS_FIELD.setEditable(false);
		STATUS_FIELD.setText(LISTENER.getStatus());
		TYPE_FIELD.setEditable(false);
		TYPE_FIELD.setText(LISTENER.getType());
		HOST_FIELD.setEditable(false);
		HOST_FIELD.setText(LISTENER.getHost());
		PORT_FIELD.setEditable(false);
		PORT_FIELD.setText(LISTENER.getPortString());
		LISTENER.addPropertyListener(this);
	}

	// ---------------------------------------------
	// PropertyChangeListener Implementation
	// ---------------------------------------------

	public void propertyChange(PropertyChangeEvent event)
	{
		if (LISTENER.equals(event.getSource()))
		{
			String property;
			Object newValue;

			property = event.getPropertyName();
			newValue = event.getNewValue();
			if (EventListener.STATUS_PROPERTY.equals(property))
			{
				if (newValue == null)
				{
					newValue = "";
				}
				STATUS_FIELD.setText(newValue.toString());
			}
		}
	}
}
