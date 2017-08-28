/*******************************************************************************
 * Copyright (c) 2009, 2013 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl 
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.views;

import org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSEntry;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

public class VirtualDNSEntryLabelProvider extends LabelProvider implements
		ITableLabelProvider, ITableColorProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof VirtualDNSEntry) {
			if (columnIndex == 0) {
				return ((VirtualDNSEntry) element).getName();
			} else if (columnIndex == 1) {
				return ((VirtualDNSEntry) element).getValue();
			}
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {
		return null;
	}

	@Override
	public Color getForeground(Object element, int columnIndex) {
		return null;
	}

}
