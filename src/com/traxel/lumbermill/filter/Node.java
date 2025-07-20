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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import com.traxel.lumbermill.event.Event;
import com.traxel.lumbermill.event.Severity;

@SuppressWarnings("serial")
class Node extends DefaultMutableTreeNode implements Comparable<Object>,
		FilterListener, Filter
{

	// ----------------------------------------
	// Class Definition
	// -----------------------------------------

	static final Comparator<List> LIST_COMPARATOR = new Comparator()
	{
		public int compare(Object a, Object b)
		{
			if (a == b)
			{
				return 0;
			}
			if (a == null)
			{
				return -1;
			}
			if (b == null)
			{
				return 1;
			}

			List<?> listA;
			List<?> listB;
			int minSize;
			int comparison;
			String aVal;
			String bVal;

			listA = (List<?>) a;
			listB = (List<?>) b;
			minSize = listA.size();
			if (listB.size() < minSize)
			{
				minSize = listB.size();
			}
			for (int i = 0; i < minSize; i++)
			{
				aVal = (String) listA.get(i);
				bVal = (String) listB.get(i);
				comparison = aVal.compareTo(bVal);
				if (comparison != 0)
				{
					return comparison;
				}
			}
			return listA.size() - listB.size();
		}
	};

	// -----------------------------------------
	// Instance Definition
	// -----------------------------------------

	private final Set<?> LISTENERS = Collections.synchronizedSet(new HashSet<Object>());

	private final List<?> COMPONENTS;
	private final FilterNotifier NOTIFIER;

	private Severity _severity;

	// -----------------------------------------
	// Instance Initialization
	// -----------------------------------------

	{
		NOTIFIER = new FilterNotifier(this);
	}

	Node(List<?> components)
	{
		COMPONENTS = Collections.unmodifiableList(new ArrayList<Object>(components));
		if (components.size() == 0)
		{
			// root severity is never INHERIT
			_severity = Severity.ALL;
		}
		else
		{
			_severity = Severity.INHERIT;
		}
		setUserObject(this);
	}

	// -----------------------------------------
	// Instance Accessors
	// -----------------------------------------

	public List<?> getSourceComponents()
	{
		return COMPONENTS;
	}

	public Severity getSeverity()
	{
		return _severity;
	}

	// ---------------------------------------
	// DefaultMutableTreeNode Implementation
	// ---------------------------------------

	public void setParent(MutableTreeNode parent)
	{
		Node oldParent;

		oldParent = (Node) getParent();
		if (oldParent != null)
		{
			oldParent.removeFilterListener(this);
		}
		if (parent != null)
		{
			((Node) parent).addFilterListener(this);
		}
		super.setParent(parent);
	}

	// ---------------------------------------
	// FilterListener Implementation
	// ---------------------------------------

	public void filterChange(FilterEvent e)
	{
		if (Severity.INHERIT.equals(getSeverity()))
		{
			fireFilterChange();
		}
	}

	// ---------------------------------------
	// Comparable Implementation
	// ---------------------------------------

	public int compareTo(Object o)
	{
		if (o == null)
		{
			return 1;
		}
		if (this == o)
		{
			return 0;
		}
		Node other = (Node) o;
		List<?> components;
		List<?> otherComponents;

		components = getSourceComponents();
		otherComponents = other.getSourceComponents();

		return LIST_COMPARATOR.compare(components, otherComponents);
	}

	// ---------------------------------------
	// Filter Implementation
	// ---------------------------------------

	public boolean isVisible(Event event)
	{
		if (Severity.INHERIT.equals(getSeverity()))
		{
			return ((Node) getParent()).isVisible(event);
		}
		Severity eventSeverity;

		eventSeverity = event.getSeverity();

		return eventSeverity.compareTo(getSeverity()) >= 0;
	}

	public void addFilterListener(FilterListener listener)
	{
		NOTIFIER.addFilterListener(listener);
	}

	public void removeFilterListener(FilterListener listener)
	{
		NOTIFIER.removeFilterListener(listener);
	}

	public String getName()
	{
		return "Node";
	}

	// ---------------------------------------
	// Object Implementation
	// ---------------------------------------

	public String toString()
	{
		return getSourceComponents().toString();
	}

	// ---------------------------------------
	// Public Instance API
	// ---------------------------------------

	public synchronized void setSeverity(Severity severity)
	{
		if (severity == null)
		{
			// severity is never null
			throw new IllegalArgumentException("attempt to set null severity");
		}
		if (Severity.INHERIT.equals(severity)
				&& getSourceComponents().size() == 0)
		{
			// root severity is never INHERIT
			return;
		}
		if (getSeverity().equals(severity))
		{
			// no need to propagate events when no change
			return;
		}
		_severity = severity;
		fireFilterChange();
	}

	public Severity getEffectiveSeverity()
	{
		if (!Severity.INHERIT.equals(getSeverity()))
		{
			return getSeverity();
		}
		return ((Node) getParent()).getEffectiveSeverity();
	}

	public String getNodeString()
	{
		List<?> components;
		String text;

		components = getSourceComponents();
		if (components.size() == 0)
		{
			text = "root";
		}
		else
		{
			text = (String) components.get(components.size() - 1);
		}

		return text;
	}

	// ---------------------------------------
	// Private Instance API
	// ---------------------------------------

	private void fireFilterChange()
	{
		NOTIFIER.fireFilterChange();
	}
}
