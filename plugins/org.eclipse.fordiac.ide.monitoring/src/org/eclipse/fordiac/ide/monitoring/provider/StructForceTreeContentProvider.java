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
 *   Daniel Lindhuber - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.provider;

import org.eclipse.fordiac.ide.monitoring.views.WatchValueTreeNode;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class StructForceTreeContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(final Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof final WatchValueTreeNode node) {
			return node.getChildrenAsArray();
		}
		return new Object[] {};
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof final WatchValueTreeNode node) {
			return node.getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof final WatchValueTreeNode node) {
			return !node.getChildren().isEmpty();
		}
		return false;
	}

}
