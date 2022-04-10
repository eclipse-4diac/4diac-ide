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
package org.eclipse.fordiac.ide.typemanagement.navigator;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

public class TypeDecorator implements ILightweightLabelDecorator {

	@Override
	public void addListener(final ILabelProviderListener listener) {
		// currently nothing to do here
	}

	@Override
	public void dispose() {
		// currently nothing to do here
	}

	@Override
	public boolean isLabelProperty(final Object element, final String property) {
		return false;
	}

	@Override
	public void removeListener(final ILabelProviderListener listener) {
		// currently nothing to do here

	}

	@Override
	public void decorate(final Object element, final IDecoration decoration) {
		if (element instanceof IFile) {
			final TypeEntry entry = TypeLibrary.getTypeEntryForFile((IFile) element);
			if (null != entry) {
				decoration.addSuffix(" [" + entry.getTypeEditable().getComment() + "]"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}

}
