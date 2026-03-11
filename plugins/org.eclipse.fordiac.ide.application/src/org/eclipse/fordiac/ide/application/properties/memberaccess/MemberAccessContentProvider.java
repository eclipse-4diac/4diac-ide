/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.application.properties.memberaccess;

import org.eclipse.jface.viewers.ITreeContentProvider;

public class MemberAccessContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof final MemberAccessTree memAccessTree) {
			return memAccessTree.getChildren().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof final StructMemberAccessTreeNode structTreeNode) {
			return structTreeNode.getChildren().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof final MemberAccessTreeNode memAccessTreeNode) {
			memAccessTreeNode.getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof final MemberAccessTreeNode memAccessTreeNode) {
			return memAccessTreeNode.hasChildren();
		}
		return false;
	}

}
