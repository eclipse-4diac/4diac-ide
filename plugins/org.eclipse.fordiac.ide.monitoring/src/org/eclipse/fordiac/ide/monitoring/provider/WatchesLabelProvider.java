/*******************************************************************************
 * Copyright (c) 2012, 2013, 2015 Profactor GbmH, fortiss GmbH
 * 				2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Gerd Kainz, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Benjamin Muttenthaler
 *     - Display values of data type ANY_BIT (except BOOL) as hex-decimal
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.provider;

import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.monitoring.views.WatchValueTreeNode;
import org.eclipse.fordiac.ide.monitoring.views.WatchValueTreeNodeUtils;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class WatchesLabelProvider extends LabelProvider implements ITableLabelProvider, ITableColorProvider {

	@Override
	public String getText(final Object element) {
		if (element instanceof WatchValueTreeNode) {
			return ((WatchValueTreeNode) element).getWatchedElementString();
		}

		return super.getText(element);
	}


	@Override
	public Image getColumnImage(final Object element, final int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(final Object element, final int columnIndex) {
		if (columnIndex == 0) {
			return getText(element);
		} else if ((columnIndex == 1) && (element instanceof WatchValueTreeNode)) {
			final WatchValueTreeNode tn = (WatchValueTreeNode) element;
			final IInterfaceElement ie = tn.getMonitoringBaseElement().getPort().getInterfaceElement();
			if (tn.isStructNode()) { //if element is of type struct, then convert all strings of that struct which are of type ANY_BIT to 16#xx
				WatchValueTreeNodeUtils.adaptAnyBitValues(tn.getChildren());
			}
			if (WatchValueTreeNodeUtils.isHexDecoractionNecessary(tn.getValue(), ie.getType())) {
				return WatchValueTreeNodeUtils.decorateHexNumber(tn.getValue());
			}
			return (tn.isStructNode() && tn.hasChildren()) ? "" : tn.getValue(); //Hide struct-string in watches view, because it cannot be displayed properly
		}

		return ""; //$NON-NLS-1$
	}

	@Override
	public Color getBackground(final Object element, final int columnIndex) {
		if (element instanceof MonitoringElement) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
		}
		return null;
	}
 
	@Override
	public Color getForeground(final Object element, final int columnIndex) {
		return null;
	}

}
