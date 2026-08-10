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

import java.util.Arrays;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class MemberAccessTree {

	private final List<MemberAccessTreeNode> children;
	private final BlockFBNetworkElement blockFBNEl;

	public MemberAccessTree(final BlockFBNetworkElement blockFBNEl, final List<VarDeclaration> rootEntries) {
		this.children = rootEntries.stream().map(varDecl -> MemberAccessTreeNode.createTreeNode(null, varDecl))
				.toList();
		this.blockFBNEl = blockFBNEl;
	}

	public MemberAccessTreeNode getChild(final String path) {
		final String[] nameList = path.split("\\."); //$NON-NLS-1$
		return getChild(children, nameList);
	}

	private static MemberAccessTreeNode getChild(final List<MemberAccessTreeNode> children, final String[] nameList) {
		final MemberAccessTreeNode treeNode = children.stream().filter(n -> n.getName().equalsIgnoreCase(nameList[0]))
				.findFirst().orElse(null);

		if (nameList.length == 1) {
			return treeNode;
		}

		if (!(treeNode instanceof final StructMemberAccessTreeNode structTreeNode)) {
			return null;
		}
		return getChild(structTreeNode.getChildren(), Arrays.copyOfRange(nameList, 1, nameList.length));
	}

	public BlockFBNetworkElement getBlockFBNetworkElement() {
		return blockFBNEl;
	}

	public List<MemberAccessTreeNode> getChildren() {
		return children;
	}

}
