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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *   Daniel Lindhuber - refactored class structure
 *******************************************************************************/
package org.eclipse.fordiac.ide.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public abstract class AbstractStructTreeNode {

	private VarDeclaration variable;
	private final String pinName;
	private final List<AbstractStructTreeNode> children;
	private AbstractStructTreeNode parent;
	private AbstractStructTree<? extends AbstractStructTreeNode> tree;

	protected AbstractStructTreeNode(final VarDeclaration variable, final AbstractStructTreeNode parent,
			final AbstractStructTree<? extends AbstractStructTreeNode> tree) {
		this.variable = variable;
		this.parent = parent;
		this.pinName = getFullPinName();
		this.children = new ArrayList<>();
		this.tree = tree;
	}

	// used for the root node so that field null checks hold
	protected AbstractStructTreeNode() {
		this.variable = null;
		this.parent = null;
		this.pinName = null;
		this.children = new ArrayList<>();
		this.tree = null;
	}

	protected static String getVariableName(final VarDeclaration variable) {
		return null == variable ? "" : variable.getName(); //$NON-NLS-1$
	}

	public Object[] getChildrenAsArray() {
		return children.toArray();
	}

	public VarDeclaration getVariable() {
		return variable;
	}

	public AbstractStructTreeNode getParent() {
		return parent;
	}

	public String getParentVarName() {
		return parent.pinName;
	}

	public String getPinName() {
		return pinName;
	}

	public AbstractStructTree<? extends AbstractStructTreeNode> getTree() {
		return tree;
	}

	public void setVariable(final VarDeclaration variable) {
		this.variable = variable;
	}

	public void setParent(final AbstractStructTreeNode parent) {
		this.parent = parent;
	}

	protected void setTree(final AbstractStructTree<? extends AbstractStructTreeNode> tree) {
		this.tree = tree;
	}

	public boolean hasChildren() {
		return !children.isEmpty();
	}

	public List<AbstractStructTreeNode> getChildren() {
		return children;
	}

	private String getFullPinName() {
		if ((null == parent) || (null == parent.pinName)) {
			return getVariableName(variable);
		}
		return (parent.pinName + "." + getVariableName(variable)); //$NON-NLS-1$
	}

	public abstract AbstractStructTreeNode addChild(final EObject obj);

}