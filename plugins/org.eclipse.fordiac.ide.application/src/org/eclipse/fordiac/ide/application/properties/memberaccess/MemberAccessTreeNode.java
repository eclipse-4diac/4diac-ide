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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class MemberAccessTreeNode {

	private final MemberAccessTreeNode parent;
	private final VarDeclaration varDecl;
	private boolean visible;

	static MemberAccessTreeNode createTreeNode(final MemberAccessTreeNode parent, final VarDeclaration varDecl) {
		if (varDecl.getType() instanceof StructuredType) {
			return new StructMemberAccessTreeNode(parent, varDecl);
		}
		return new MemberAccessTreeNode(parent, varDecl);
	}

	protected MemberAccessTreeNode(final MemberAccessTreeNode parent, final VarDeclaration varDecl) {
		this.parent = parent;
		this.varDecl = varDecl;
	}

	public String getComment() {
		return varDecl.getComment();
	}

	public List<String> getNamePath() {
		final List<String> path = (parent != null) ? parent.getNamePath() : new ArrayList<>();
		path.add(varDecl.getName());
		return path;
	}

	public String getFullTypeName() {
		return varDecl.getFullTypeName();
	}

	@SuppressWarnings("static-method") // allow children to override
	public boolean hasChildren() {
		return false;
	}

	public boolean isVisible() {
		return visible;
	}

	public String getName() {
		return varDecl.getName();
	}

	public MemberAccessTreeNode getParent() {
		return parent;
	}

	public void setVisible(final boolean visible) {
		this.visible = visible;
	}

}
