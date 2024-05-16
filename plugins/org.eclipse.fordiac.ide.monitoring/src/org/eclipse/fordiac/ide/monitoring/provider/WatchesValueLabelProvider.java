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

import org.eclipse.fordiac.ide.model.data.RealType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.monitoring.Activator;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.views.WatchValueTreeNode;
import org.eclipse.fordiac.ide.monitoring.views.WatchValueTreeNodeUtils;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceGetter;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class WatchesValueLabelProvider extends ColumnLabelProvider {
	@Override
	public String getText(final Object element) {

		if (element instanceof final WatchValueTreeNode tn) {
			final IInterfaceElement ie = tn.getMonitoringBaseElement().getPort().getInterfaceElement();
			if (ie.getType() instanceof RealType && !tn.getValue().contains(".") //$NON-NLS-1$
					&& (ie instanceof final VarDeclaration varDecl && !varDecl.isArray())) {
				// display integers as decimals
				return tn.getValue() + ".0"; //$NON-NLS-1$

			}
			if (tn.isStructNode()) { // if element is of type struct, then convert all strings of that struct which
										// are
				// of type ANY_BIT to 16#xx
				WatchValueTreeNodeUtils.adaptAnyBitValues(tn.getChildren());
			}
			if (WatchValueTreeNodeUtils.isHexDecorationNecessary(tn.getValue(), ie.getType())) {
				return WatchValueTreeNodeUtils.decorateHexNumber(tn.getValue());
			}
			// Hide struct-string in watches view, because it cannot be displayed properly
			return (tn.isStructNode() && tn.hasChildren()) ? "" : tn.getValue(); //$NON-NLS-1$
		}

		return ""; //$NON-NLS-1$
	}

	@Override
	public Color getBackground(final Object element) {
		if (element instanceof final WatchValueTreeNode tn) {
			final MonitoringElement monitoringElement = (MonitoringElement) MonitoringManager.getInstance()
					.getMonitoringElement(tn.getMonitoringBaseElement().getPort().getInterfaceElement());
			if (monitoringElement != null && monitoringElement.isForce() && !tn.isStructRootNode()) {
				return PreferenceGetter.getColor(Activator.getDefault().getPreferenceStore(),
						org.eclipse.fordiac.ide.monitoring.preferences.PreferenceConstants.P_FORCE_COLOR);
			}
		}
		return Display.getCurrent().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
	}
}
