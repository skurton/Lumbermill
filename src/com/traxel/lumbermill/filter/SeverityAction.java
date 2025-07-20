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

package com.traxel.lumbermill.filter;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;

import com.traxel.lumbermill.event.Severity;
import com.traxel.lumbermill.event.SeverityView;

class SeverityAction extends AbstractAction
{

	// -------------------------------------------
	// Class API
	// -------------------------------------------

	public static List getActions(Collection nodes)
	{
		List levels;
		Iterator it;
		Severity severity;
		SeverityAction action;
		List actions;

		actions = new ArrayList();
		levels = Severity.getLevels();
		it = levels.iterator();
		while (it.hasNext())
		{
			severity = (Severity) it.next();
			action = new SeverityAction(severity, nodes);
			actions.add(action);
		}

		return actions;
	}

	// -------------------------------------------
	// Instance Definition
	// -------------------------------------------

	private final Severity _severity;
	private final Collection _nodes;

	// -------------------------------------------
	// Instance Initialization
	// -------------------------------------------

	public SeverityAction(Severity severity, Collection nodes)
	{
		super(severity.toString(), new SeverityView(severity, severity));
		_severity = severity;
		_nodes = nodes;
	}

	// -------------------------------------------
	// Instance Accessors
	// -------------------------------------------

	private Collection getNodes()
	{
		return _nodes;
	}

	private Severity getSeverity()
	{
		return _severity;
	}

	// -------------------------------------------
	// Action Implementation
	// -------------------------------------------

	public void actionPerformed(ActionEvent event)
	{
		if (getNodes() == null)
			return;
		
		Iterator<?> it = getNodes().iterator();
		
		while (it.hasNext())
		{
			Node node = (Node) it.next();
			node.setSeverity(getSeverity());
		}
	}
}
