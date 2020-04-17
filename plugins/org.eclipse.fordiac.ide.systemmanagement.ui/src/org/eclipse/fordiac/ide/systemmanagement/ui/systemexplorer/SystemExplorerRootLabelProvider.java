/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/* This label provider returns mostly null so that other label provers can do their work
 * otherwise the type files will not be nicely shown.
 */
public class SystemExplorerRootLabelProvider extends LabelProvider {

	@Override
	public Image getImage(Object element) {
		if (FordiacProjectSorter.isSystemFile(element)) {
			// provide the icon for the system configuration file,
			// TODO this should in the future provided by a dedicated editor
			return FordiacImage.ICON_SYSTEM_PERSPECTIVE.getImage();
		}

		if (FordiacProjectSorter.isTypeLibFolder(element)) {
			return FordiacImage.ICON_TYPE_NAVIGATOR.getImage();
		}

		return null;
	}

	@Override
	public String getText(Object element) {
		return null;
	}

}
