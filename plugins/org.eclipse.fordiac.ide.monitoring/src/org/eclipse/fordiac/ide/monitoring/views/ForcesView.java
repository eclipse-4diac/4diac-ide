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
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.dialogs.PatternFilter;

public class ForcesView extends WatchesView {

	@Override
	protected PatternFilter getPatternFilter() {
		return new PatternFilter() {
			@Override
			public Object[] filter(final Viewer viewer, final Object parent, final Object[] elements) {
				final ArrayList<Object> returns = new ArrayList<>();
				for (final Object element : elements) {
					if (element instanceof WatchValueTreeNode
							&& ((WatchValueTreeNode) element).getMonitoringBaseElement() instanceof MonitoringElement) {
						if (((MonitoringElement) ((WatchValueTreeNode) element).getMonitoringBaseElement()).isForce()) {
							returns.add(element);
						}
					}
				}
				return returns.toArray();
			}
		};
	}

	@Override
	protected void contributeToActionBars() {
		// needs no ActionBar
	}

	@Override
	protected void createPartListener() {
		getSite().getPage().addPartListener(new IPartListener2() {
			@Override
			public void partVisible(final IWorkbenchPartReference ref) {
				final IWorkbenchPart part = ref.getPart(false);
				if (part instanceof ForcesView) {
					expandItems();
				}
			}
		});
	}

	@Override
	protected void expandItems() {
		filteredTree.getViewer().expandAll();
	}
}
