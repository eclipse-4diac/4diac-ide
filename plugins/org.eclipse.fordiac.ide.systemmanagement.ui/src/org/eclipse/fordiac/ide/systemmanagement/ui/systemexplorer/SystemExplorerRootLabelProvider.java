/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Reworked system explorer layout
 *   Daniel Lindhuber - Changed getText method to suppress file endings
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

/**
 * The root content provider for the system explorer does not need to provide
 * any labels or images as this is done be the resource content. It is only
 * required so that the navigator framework will not throw any exceptions.
 *
 */
public class SystemExplorerRootLabelProvider implements ILabelProvider {

	@Override
	public void addListener(final ILabelProviderListener listener) {
		// We don't listen to anything
	}

	@Override
	public void dispose() {
		// Currently nothing needs to be done
	}

	@Override
	public boolean isLabelProperty(final Object element, final String property) {
		return false;
	}

	@Override
	public void removeListener(final ILabelProviderListener listener) {
		// We don't listen to anything
	}

	@Override
	public Image getImage(final Object element) {
		return null;
	}

	@Override
	public String getText(final Object element) {
		return null;
	}

}
