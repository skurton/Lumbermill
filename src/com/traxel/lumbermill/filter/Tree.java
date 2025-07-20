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

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultTreeModel;

import com.traxel.lumbermill.event.Event;

class Tree extends DefaultTreeModel implements Filter, FilterListener
{

	// ---------------------------------------
	// Instance Definition
	// ---------------------------------------

	private final Map MAP = new HashMap();
	private final TimerNotifier TIMER_NOTIFIER;

	// ---------------------------------------
	// Instance Initialization
	// ---------------------------------------

	{
		TIMER_NOTIFIER = new TimerNotifier(this);
	}

	public Tree()
	{
		super(new Node(Collections.EMPTY_LIST));
	}

	// ---------------------------------------
	// Filter Implementation
	// ---------------------------------------

	public boolean isVisible(Event event)
	{
		Node node;

		node = getNode(event);

		return node.isVisible(event);
	}

	public void addFilterListener(FilterListener listener)
	{
		TIMER_NOTIFIER.addFilterListener(listener);
	}

	public void removeFilterListener(FilterListener listener)
	{
		TIMER_NOTIFIER.addFilterListener(listener);
	}

	public String getName()
	{
		return "Node Tree";
	}

	// ---------------------------------------
	// FilterListener Implementation
	// ---------------------------------------

	public void filterChange(FilterEvent e)
	{
		if (e.getSource() instanceof Node)
		{
			TIMER_NOTIFIER.fireFilterChange();
		}
	}

	// ---------------------------------------
	// Public Instance API
	// ---------------------------------------

	public synchronized Node getNode(Event event)
	{
		return getNode(event.getSourceComponents());
	}

	// ---------------------------------------
	// Private Instance API
	// ---------------------------------------

	private synchronized Node getNode(List components)
	{
		if (components.size() == 0)
		{
			return (Node) getRoot();
		}
		Node node;
		Node parent;
		List parentComponents;

		if (MAP.get(components) != null)
		{
			node = (Node) MAP.get(components);
			return node;
		}
		else
		{
			node = new Node(components);
			node.addFilterListener(this);
			MAP.put(components, node);
		}

		if (components.size() > 1)
		{
			parentComponents = components.subList(0, components.size() - 1);
			parent = getNode(parentComponents);
		}
		else
		{
			parent = (Node) getRoot();
		}
		insertNodeInto(node, parent);

		return node;
	}

	private synchronized void insertNodeInto(Node child, Node parent)
	{
		Iterator it;
		List children;
		Node other;
		int comparison;
		boolean added = false;

		children = Collections.list(parent.children());
		for (int i = 0; i < children.size(); i++)
		{
			other = (Node) children.get(i);
			comparison = child.compareTo(other);
			if (comparison < 0)
			{
				insertNodeInto(child, parent, i);
				added = true;
				break;
			}
		}
		if (!added)
		{
			int[] newIndexs;

			parent.add(child);
			newIndexs = new int[1];
			newIndexs[0] = parent.getIndex(child);
			nodesWereInserted(parent, newIndexs);
		}
	}
}
