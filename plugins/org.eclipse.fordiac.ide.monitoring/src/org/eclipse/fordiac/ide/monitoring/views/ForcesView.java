/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.views;

import java.util.ArrayList;

import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.dialogs.PatternFilter;

public class ForcesView extends WatchesView {

	@Override
	protected PatternFilter getPatternFilter() {
		return new WatchesViewPatternFilter() {

			@Override
			public Object[] filter(final Viewer viewer, final Object parent, final Object[] elements) {
				final ArrayList<Object> returns = new ArrayList<>();
				for (final Object element : elements) {
					if (element instanceof final WatchValueTreeNode tn
							&& tn.getMonitoringBaseElement() instanceof final MonitoringElement me && me.isForce()) {
						returns.add(element);
					}
				}
				return super.filter(viewer, parent, returns.toArray());
			}
		};
	}

	@Override
	protected void fillLocalToolBar(final IToolBarManager manager) {
		super.createExpandItemsInLocalToolBar(manager);
	}

	@Override
	protected void createPartListener() {
		// need no listener
	}

	@Override
	protected void expandItems() {
		filteredTree.getViewer().expandAll();
	}
}
