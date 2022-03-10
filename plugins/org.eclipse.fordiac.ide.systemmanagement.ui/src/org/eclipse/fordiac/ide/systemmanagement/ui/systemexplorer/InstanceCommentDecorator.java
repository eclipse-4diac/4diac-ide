/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
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

import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

public class InstanceCommentDecorator implements ILightweightLabelDecorator {

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
		if (element instanceof FBNetworkElement && !(element instanceof Group)) {
			final String comment = ((FBNetworkElement) element).getComment();
			if (null != comment && !comment.isBlank()) {
				decoration.addSuffix(" [" + checkComment(comment) + "]"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}

	private static String checkComment(final String comment) {
		final int i = comment.indexOf('\n');
		if (i != -1) {
			// we have a multi-line comment only return first line
			return comment.substring(0, i) + "..."; //$NON-NLS-1$
		}
		return comment;
	}

}
