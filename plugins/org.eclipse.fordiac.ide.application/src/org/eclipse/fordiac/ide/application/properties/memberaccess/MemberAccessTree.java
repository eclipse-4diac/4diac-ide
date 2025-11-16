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

	public BlockFBNetworkElement getBlockFBNetworkElement() {
		return blockFBNEl;
	}

	public List<MemberAccessTreeNode> getChildren() {
		return children;
	}

}
