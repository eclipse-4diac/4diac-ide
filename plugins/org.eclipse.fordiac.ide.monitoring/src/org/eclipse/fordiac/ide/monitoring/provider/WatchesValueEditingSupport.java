/* Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.provider;

import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.views.WatchValueTreeNode;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Tree;

public class WatchesValueEditingSupport extends EditingSupport {
	private final TextCellEditor cellEditor;

	public WatchesValueEditingSupport(final ColumnViewer viewer, final Tree tree) {
		super(viewer);
		cellEditor = new TextCellEditor(tree);
	}

	@Override
	protected void setValue(final Object element, final Object value) {
		if (element instanceof WatchValueTreeNode) {
			final MonitoringBaseElement monitoringBaseElement = ((WatchValueTreeNode) element)
					.getMonitoringBaseElement();
			MonitoringManager.getInstance().writeValue((MonitoringElement) monitoringBaseElement, (String) value);
		}
	}

	@Override
	protected Object getValue(final Object element) {
		if (element instanceof WatchValueTreeNode) {
			return ((WatchValueTreeNode) element).getValue();
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	protected TextCellEditor getCellEditor(final Object element) {
		return cellEditor;
	}

	@Override
	protected boolean canEdit(final Object element) {
		if (element instanceof WatchValueTreeNode) {
			final WatchValueTreeNode watchNode = (WatchValueTreeNode) element;
			// TODO REMOVE that check after forcing is possible
			if (watchNode.isStructNode()) {
				return watchNode.isStructRootNode();
			}
			return !(watchNode.getMonitoringBaseElement().getPort().getInterfaceElement() instanceof Event);
		}
		return false;
	}
}