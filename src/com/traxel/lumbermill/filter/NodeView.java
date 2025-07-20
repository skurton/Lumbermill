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

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.traxel.lumbermill.event.Severity;
import com.traxel.lumbermill.event.SeverityView;

@SuppressWarnings("serial")
class NodeView extends DefaultTreeCellRenderer
{
	// --------------------------------------
	// TreeCellRenderer API
	// --------------------------------------

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus)
	{
		String text;
		Node node;
		Severity effective;
		Severity actual;

		node = (Node) value;
		text = node.getNodeString();

		this.hasFocus = hasFocus;
		this.selected = selected;
		setText(text);

		if (selected)
		{
			setForeground(getTextSelectionColor());
		}
		else
		{
			setForeground(getTextNonSelectionColor());
		}

		setEnabled(true);
		actual = node.getSeverity();
		effective = node.getEffectiveSeverity();
		setIcon(new SeverityView(actual, effective));
		setComponentOrientation(tree.getComponentOrientation());
		return this;
	}
}
