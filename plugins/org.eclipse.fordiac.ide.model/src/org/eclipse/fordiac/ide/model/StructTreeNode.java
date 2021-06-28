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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.viewers.CheckboxTreeViewer;

public class StructTreeNode {
	private final VarDeclaration variable;
	private final StructTreeNode root;
	private final StructTreeNode parent;
	private final String pinName;
	private final List<StructTreeNode> children;
	private boolean isChecked = false;
	private boolean isGrey = false;
	private CheckboxTreeViewer viewer = null;

	private StructTreeNode(final VarDeclaration variable, final StructTreeNode parent) {
		this.variable = variable;
		this.parent = parent;
		this.pinName = getFullPinName();
		this.children = new ArrayList<>();
		this.root = parent.root;
	}

	private StructTreeNode() {
		// initialize root node
		this.variable = null;
		this.parent = null;
		this.pinName = null;
		this.children = new ArrayList<>();
		this.root = this;
	}

	public static StructTreeNode createRootNode() {
		return new StructTreeNode();
	}

	public static StructTreeNode initTree(final StructManipulator struct, final StructuredType structType) {
		final StructTreeNode createRootNode = createRootNode();
		buildTree(struct, structType, createRootNode);
		return createRootNode;
	}

	private String getFullPinName() {
		if ((null == parent) || (null == parent.pinName)) {
			return getVariableName(variable);
		}
		return (parent.pinName + "." + getVariableName(variable)); //$NON-NLS-1$
	}

	public String getParentVarName() {
		return parent.pinName;
	}

	public String getPinName() {
		return pinName;
	}

	public boolean hasChildren() {
		return !children.isEmpty();
	}

	public List<StructTreeNode> getChildren() {
		return children;
	}

	public void check(final boolean isChecked) {
		if (root.viewer != null) {
			root.viewer.setChecked(this, isChecked);
		}
		this.isChecked = isChecked;
	}

	public boolean isChecked() {
		return isChecked;
	}


	public boolean childIsChecked() {
		return childIsChecked(this);
	}

	public static void buildTree(final StructManipulator struct, final StructuredType structType,
			final StructTreeNode parent) {
		for (final VarDeclaration memberVariable : structType.getMemberVariables()) {
			final StructTreeNode treeNode = parent.addChild(memberVariable);

			if (struct.getInterfaceElement(treeNode.getPinName()) != null) {
				treeNode.check(true);
			}

			if ((memberVariable.getType() instanceof StructuredType)
					&& (memberVariable.getType() != GenericTypes.ANY_STRUCT)) {
				buildTree(struct, (StructuredType) memberVariable.getType(), treeNode);
			} else if (treeNode.isChecked()) {
				greyParents(treeNode);
			}
		}
	}

	public static void greyParents(final StructTreeNode node) {
		StructTreeNode parent = node.getParent();
		final StructTreeNode rootNode = node.getRootNode();
		while (parent != rootNode) {
			if (!parent.isChecked()) {
				parent.setGrey(true);
				parent.check(true);
			}
			parent = parent.getParent();
		}

	}

	protected boolean childIsChecked(final StructTreeNode parent) {
		for (final StructTreeNode node : parent.getChildren()) {
			if (node.isChecked) {
				return true;
			}
			if (node.hasChildren()) {
				return childIsChecked(node);
			}
		}

		return false;
	}

	private static String getVariableName(final VarDeclaration variable) {
		return null == variable ? "" : variable.getName(); //$NON-NLS-1$
	}

	public Object[] getChildrenAsArray() {
		return children.toArray();
	}

	public VarDeclaration getVariable() {
		return variable;
	}

	public StructTreeNode getParent() {
		return parent;
	}

	public StructTreeNode addChild(final VarDeclaration memberVariable) {
		final StructTreeNode treeNode = new StructTreeNode(memberVariable, this);
		this.children.add(treeNode);
		return treeNode;
	}


	private static StructTreeNode find(final StructTreeNode parent, final String name) {
		for (final StructTreeNode node : parent.getChildren()) {
			if (node.pinName.equals(name)) {
				return node;
			}
			if (node.hasChildren()) {
				final StructTreeNode find = find(node, name);
				if (find != null) {
					return find;
				}
			}
		}
		return null;
	}

	public StructTreeNode find(final String name) {
		return find(getRootNode(), name);
	}

	private static void serializeTreeToString(final StructTreeNode parent, final StringBuilder stringBuilder) {
		for (final StructTreeNode node : parent.getChildren()) {
			if (node.isChecked && !node.isGrey) {
				stringBuilder.append(node.pinName);
				stringBuilder.append(LibraryElementTags.VARIABLE_SEPARATOR);
			}
			serializeTreeToString(node, stringBuilder);
		}
	}

	public String visibleToString() {
		final StringBuilder stringBuilder = new StringBuilder();
		serializeTreeToString(this, stringBuilder);

		if (stringBuilder.length() > 0) {
			return stringBuilder.substring(0, stringBuilder.length() - 1);
		}

		return ""; //$NON-NLS-1$
	}

	public StructTreeNode getRootNode() {
		return root;
	}

	public boolean isGrey() {
		return isGrey;
	}



	public void setGrey(final boolean isGrey) {
		if (root.viewer != null) {
			root.viewer.setChecked(this, isGrey);
		}
		this.isGrey = isGrey;
	}

	public void uncheckChildren(final CheckboxTreeViewer viewer) {
		this.isChecked = false;
		viewer.setChecked(this, false);
		children.forEach(c -> {
			c.isChecked = false;
			viewer.setChecked(c, false);
			c.uncheckChildren(viewer);
		});
	}

	public void ungrayChildren(final CheckboxTreeViewer viewer) {
		this.isGrey = false;
		viewer.setGrayChecked(this, false);
		children.forEach(c -> {
			c.isGrey = false;
			viewer.setGrayChecked(parent, false);
			c.ungrayChildren(viewer);
		});
	}

	public void setViewer(final CheckboxTreeViewer viewer) {
		root.viewer = viewer;
	}
}