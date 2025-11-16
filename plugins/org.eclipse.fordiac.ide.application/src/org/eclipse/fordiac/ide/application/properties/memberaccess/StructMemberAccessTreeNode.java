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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class StructMemberAccessTreeNode extends MemberAccessTreeNode {

	private final List<MemberAccessTreeNode> children;

	StructMemberAccessTreeNode(final MemberAccessTreeNode parent, final VarDeclaration varDecl) {
		super(parent, varDecl);
		children = buildChildren((StructuredType) varDecl.getType());
	}

	private final List<MemberAccessTreeNode> buildChildren(final StructuredType structuredType) {
		return structuredType.getMemberVariables().stream()
				.map(memVar -> MemberAccessTreeNode.createTreeNode(this, EcoreUtil.copy(memVar))).toList();
	}

	public List<MemberAccessTreeNode> getChildren() {
		return children;
	}

	@Override
	public boolean hasChildren() {
		return !children.isEmpty();
	}
}
