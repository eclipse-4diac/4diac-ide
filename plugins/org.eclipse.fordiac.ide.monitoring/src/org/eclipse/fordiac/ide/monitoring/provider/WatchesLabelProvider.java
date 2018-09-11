/*******************************************************************************
 * Copyright (c) 2012, 2013, 2015 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Gerd Kainz, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.provider;

import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;


public class WatchesLabelProvider extends LabelProvider implements
		ITableLabelProvider, ITableColorProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof MonitoringElement) {
			return ((MonitoringElement) element).getPort().getPortString();
		}

		return super.getText(element);
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (columnIndex == 0) {
			return getText(element);
		} else if ((columnIndex == 1) && (element instanceof MonitoringElement)) {
			return ((MonitoringElement) element).getCurrentValue();
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {
		if (element instanceof MonitoringElement) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
		}
		return null;
	}

	@Override
	public Color getForeground(Object element, int columnIndex) {
		return null;
	}

}
