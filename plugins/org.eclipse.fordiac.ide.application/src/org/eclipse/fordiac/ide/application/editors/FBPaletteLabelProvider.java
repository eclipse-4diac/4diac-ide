/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   			 - changed so that type icons are shown for the respective types
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.typemanagement.navigator.FBTypeLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * Label provider for the FB type tree palette used in function block network
 * editors.
 *
 * For the images we'll use the default images for folders and FB type files.
 * For type file names we hide the extension to make the palette look clearer.
 */
public class FBPaletteLabelProvider extends LabelProvider {

	private final WorkbenchLabelProvider wbLabelProvider = new WorkbenchLabelProvider();

	@Override
	public String getText(final Object element) {
		if (element instanceof IFile) {
			// we want to hide the extension of the fb type
			return TypeLibrary.getTypeNameFromFile((IFile) element);
		}
		return wbLabelProvider.getText(element);
	}

	@Override
	public Image getImage(final Object element) {
		if (element instanceof IFile) {
			return FBTypeLabelProvider.getImageForFile((IFile) element);
		}
		return wbLabelProvider.getImage(element);
	}

}
