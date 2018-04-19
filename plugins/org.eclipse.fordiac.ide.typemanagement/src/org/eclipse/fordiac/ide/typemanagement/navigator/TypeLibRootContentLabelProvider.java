/*******************************************************************************
 * Copyright (c) 2015, 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.navigator;

import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
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
		if(element instanceof TypeLibRootElement){
			return FordiacImage.ICON_TypeNavigator.getImage();
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		if(element instanceof TypeLibRootElement){
			return "Type Library";
		}
		return null;
	}

}
