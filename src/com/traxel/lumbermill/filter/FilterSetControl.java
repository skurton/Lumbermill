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
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;

public class FilterSetControl
{

	// ------------------------------------------
	// Instance Definition
	// ------------------------------------------

	private final class FilterCheckBoxAction extends AbstractAction
	{
		// Instance Definition
		private final FilterControl FILTER_CONTROL;

		// Instance Initialization
		public FilterCheckBoxAction(FilterControl control)
		{
			super(control.getFilter().getName());
			FILTER_CONTROL = control;
		}

		// Action Implementation
		public void actionPerformed(ActionEvent e)
		{
			JCheckBoxMenuItem item;

			item = (JCheckBoxMenuItem) e.getSource();
			if (item.getState())
			{
				apply(FILTER_CONTROL);
			}
			else
			{
				unapply(FILTER_CONTROL);
			}
		}
	}

	private final List ALL_FILTER_CONTROLS;
	private final FilterSetView FILTER_SET_VIEW = new FilterSetView();
	private final FilterSet FILTER_SET = new FilterSet();
	private final FilterSetMenu FILTER_SET_MENU;

	// ------------------------------------------
	// Instance Initialization
	// ------------------------------------------

	{
		List filterControls;
		RegexControl regexControl;
		TreeControl treeControl;
		FilterControl filterControl;
		JCheckBoxMenuItem[] filterItems;
		Action action;

		filterControls = new ArrayList();
		regexControl = new RegexControl();
		treeControl = new TreeControl();
		filterControls.add(treeControl);
		filterControls.add(regexControl);
		ALL_FILTER_CONTROLS = Collections.unmodifiableList(filterControls);

		filterItems = new JCheckBoxMenuItem[ALL_FILTER_CONTROLS.size()];
		for (int i = 0; i < ALL_FILTER_CONTROLS.size(); i++)
		{
			filterControl = (FilterControl) ALL_FILTER_CONTROLS.get(i);
			action = new FilterCheckBoxAction(filterControl);
			filterItems[i] = new JCheckBoxMenuItem(action);
			filterItems[i].setState(true);
			apply(filterControl);
		}
		FILTER_SET_MENU = new FilterSetMenu("Filters", filterItems);
	}

	// ------------------------------------------
	// Instance Accessors
	// ------------------------------------------

	public List getFilterControls()
	{
		return ALL_FILTER_CONTROLS;
	}

	public FilterSetView getFilterSetView()
	{
		return FILTER_SET_VIEW;
	}

	public FilterSet getFilterSet()
	{
		return FILTER_SET;
	}

	public FilterSetMenu getFilterSetMenu()
	{
		return FILTER_SET_MENU;
	}

	// ---------------------------------------------
	// Instance API
	// ---------------------------------------------

	private synchronized void apply(FilterControl control)
	{
		FilterView view;
		Filter filter;

		filter = control.getFilter();
		view = control.getFilterView();
		FILTER_SET_VIEW.addTab(filter.getName(), view.getFilterComponent());
		FILTER_SET.add(filter);
	}

	private synchronized void unapply(FilterControl control)
	{
		FilterView view;
		Filter filter;

		filter = control.getFilter();
		view = control.getFilterView();
		FILTER_SET_VIEW.remove(view.getFilterComponent());
		FILTER_SET.remove(filter);
	}
}
