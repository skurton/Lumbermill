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

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.TreePath;

class TreeControl extends MouseAdapter implements FilterControl
{

	// ------------------------------------------------
	// Instance Definition
	// ------------------------------------------------

	private final TreePanel TREE_PANEL;
	private final TreeView TREE_VIEW;
	private final SeverityListView POPUP = new SeverityListView();

	// ------------------------------------------------
	// Instance Initialization
	// ------------------------------------------------

	public TreeControl()
	{
		TREE_PANEL = new TreePanel();
		TREE_VIEW = TREE_PANEL.TREE_VIEW;
		TREE_VIEW.addMouseListener(this);
	}

	// ------------------------------------------------
	// MouseAdapter Implementation
	// ------------------------------------------------

	public void mouseClicked(MouseEvent e)
	{
		TreePath[] paths;
		Collection<Node> nodes;
		Node node;
		List<?> actions;
		Point location;

		POPUP.setVisible(false);
		if (MouseEvent.BUTTON3 == e.getButton())
		{
			paths = TREE_VIEW.getSelectionPaths();
			if (paths == null || paths.length == 0)
			{
				return;
			}
			nodes = new ArrayList<Node>();
			for (int i = 0; i < paths.length; i++)
			{
				node = (Node) paths[i].getLastPathComponent();
				nodes.add(node);
			}
			actions = SeverityAction.getActions(nodes);
			location = getScreenLocation();
			showPopup(actions, (int) (e.getX() + location.getX()),
					(int) (e.getY() + location.getY() + 5));
		}
	}

	// -----------------------------------------------
	// FilterControl Implementation
	// ---------------------------------------------

	public Filter getFilter()
	{
		return getFilterView().getFilter();
	}

	public FilterView getFilterView()
	{
		return TREE_PANEL;
	}

	// -------------------------------------------
	// Private Instance API
	// -------------------------------------------

	private synchronized void showPopup(List<?> actions, int x, int y)
	{
		POPUP.setVisible(false);
		POPUP.setActions(actions);
		POPUP.pack();
		POPUP.setLocation(x, y);
		POPUP.setVisible(true);
	}

	private List<Point> getLocations()
	{
		List<Point> locations;
		Component parent;

		locations = new ArrayList<Point>();
		parent = TREE_VIEW;
		while (parent != null)
		{
			locations.add(parent.getLocation());
			parent = parent.getParent();
		}

		return locations;
	}

	private Point getScreenLocation()
	{
		List<Point> locations;
		Iterator<Point> it;
		Point location;
		int screenX = 0;
		int screenY = 0;

		locations = getLocations();
		it = locations.iterator();
		while (it.hasNext())
		{
			location = (Point) it.next();
			if (location != null)
			{
				screenX += location.getX();
				screenY += location.getY();
			}
		}

		return new Point(screenX, screenY);
	}
}
