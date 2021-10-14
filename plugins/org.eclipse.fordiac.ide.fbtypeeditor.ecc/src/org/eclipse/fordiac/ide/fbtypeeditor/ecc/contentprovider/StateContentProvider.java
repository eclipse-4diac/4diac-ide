/*******************************************************************************
 * Copyright (c) 2015, 2016 fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider;

import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class StateContentProvider implements ITreeContentProvider {
	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof ECState) {
			return ((ECState) inputElement).getOutTransitions().toArray();
		}
		return new Object[] {};
	}

	@Override
	public void dispose() {
		// currently nothing to be done here
	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		// currently nothing to be done here
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof ECState && null != ((ECState) parentElement).getOutTransitions()) {
			return ((ECState) parentElement).getOutTransitions().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof ECState) {
			return ((ECState) element).getECC();
		}
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof ECState) {
			return ((ECState) element).getOutTransitions().isEmpty();
		}
		return false;
	}
}
