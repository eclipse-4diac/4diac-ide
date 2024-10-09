/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search;

import java.util.Map;
import java.util.Set;

import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;

// TODO: implement to the end
public class ModelSearchTreeContentProvider implements ITreeContentProvider, IModelSearchContentProvider {

	private final AbstractTreeViewer treeViewer;
	private Map<Object, Set<Object>> childrenMap;

	private static final Object[] EMPTY_ARR = new Object[0];

	public ModelSearchTreeContentProvider(final ModelSearchResultPage resultPage,
			final AbstractTreeViewer abstractTreeViewer) {
		this.treeViewer = abstractTreeViewer; // Have to see if relevant for us
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		// TODO: parse the inputElement
		return EMPTY_ARR;
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		return EMPTY_ARR;
	}

	@Override
	public Object getParent(final Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		return childrenMap.size() > 0;
	}

	@Override
	public void clear() {
		if (treeViewer != null) {
			treeViewer.refresh();
		}

	}

	@Override
	public void elementsChanged(final Object[] updatedElements) {
		FordiacLogHelper.logInfo("not supported yet"); //$NON-NLS-1$
	}

}
