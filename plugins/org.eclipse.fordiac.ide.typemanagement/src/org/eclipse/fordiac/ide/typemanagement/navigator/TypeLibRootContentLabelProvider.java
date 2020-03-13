/*******************************************************************************
 * Copyright (c) 2015, 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.navigator;

import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class TypeLibRootContentLabelProvider implements ILabelProvider {

	@Override
	public void addListener(ILabelProviderListener listener) {
		// Nothing todo here
	}

	@Override
	public void dispose() {
		// Nothing todo here
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// Nothing todo here
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// Nothing todo here
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof TypeLibRootElement) {
			return FordiacImage.ICON_TYPE_NAVIGATOR.getImage();
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof TypeLibRootElement) {
			return FordiacMessages.TypeLibrary;
		}
		return null;
	}

}
