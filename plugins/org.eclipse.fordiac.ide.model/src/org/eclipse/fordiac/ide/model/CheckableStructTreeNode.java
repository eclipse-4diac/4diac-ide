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

import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.viewers.CheckboxTreeViewer;

public class CheckableStructTreeNode extends StructTreeNode {

	private boolean isChecked = false;
	private boolean isGrey = false;


	private CheckableStructTreeNode(final VarDeclaration memberVariable, final CheckableStructTreeNode structTreeNode) {
		super(memberVariable, structTreeNode);
	}

	private CheckableStructTreeNode() {
		super();
	}

	public static CheckableStructTreeNode createRootNode() {
		return new CheckableStructTreeNode();
	}

	public static CheckableStructTreeNode initTree(final StructManipulator struct, final StructuredType structType) {
		final CheckableStructTreeNode createRootNode = createRootNode();
		buildTree(struct, structType, createRootNode);
		return createRootNode;
	}

	public void updateNode(final boolean check) {
		if (check && isGrayChecked()) {
			setGrey(false);
		}
		check(check);

		updateGreyedElements(this);
	}

	private void check(final boolean isChecked) {
		if (getViewer() != null) {
			getViewer().setChecked(this, isChecked);
		}
		this.isChecked = isChecked;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public boolean isGrayChecked() {
		return isGrey && isChecked;
	}

	public boolean childIsChecked() {
		return childIsChecked(this);
	}

	public static void buildTree(final StructManipulator struct, final StructuredType structType,
			final CheckableStructTreeNode parent) {
		for (final VarDeclaration memberVariable : structType.getMemberVariables()) {
			final CheckableStructTreeNode treeNode = parent.addChild(memberVariable);

			if (struct.getInterfaceElement(treeNode.getPinName()) != null) {
				treeNode.check(true);
			}

			if ((memberVariable.getType() instanceof StructuredType)
					&& (memberVariable.getType() != GenericTypes.ANY_STRUCT)) {
				buildTree(struct, (StructuredType) memberVariable.getType(), treeNode);
			} else if (treeNode.isChecked()) {
				CheckableStructTreeNode.greyParents(treeNode);
			}
		}
	}

	private void updateGreyedElements(final CheckableStructTreeNode node) {
		if (node.isChecked() || node.childIsChecked()) {
			greyParents(node);
		} else {
			ungreyParents(node);
		}
	}

	private void ungreyParents(final CheckableStructTreeNode node) {
		CheckableStructTreeNode parent = node;// node.getParent();
		final StructTreeNode rootNode = node.getRootNode();
		while (parent != rootNode) {
			if (parent.isGrey() && !parent.childIsChecked()) {
				parent.setGrey(false);
				parent.check(false);
				if (getViewer() != null) {
					getViewer().setGrayChecked(parent, false);
				}
			}
			parent = (CheckableStructTreeNode) parent.getParent();
		}
	}

	public static void greyParents(final CheckableStructTreeNode node) {
		CheckableStructTreeNode parent = node;// ; node.getParent();
		final StructTreeNode rootNode = node.getRootNode();
		while (parent != rootNode) {
			if (!parent.isChecked()) {
				parent.setGrey(true);
				parent.check(true);
				parent.setGrayChecked(true);
			}
			parent = (CheckableStructTreeNode) parent.getParent();
		}

	}

	private void setGrayChecked(final boolean b) {
		if (getViewer() != null) {
			getViewer().setGrayChecked(this, b);
		}
		setGrey(b);
		check(b);
	}

	protected boolean childIsChecked(final StructTreeNode parent) {
		for (final StructTreeNode node : parent.getChildren()) {
			if (((CheckableStructTreeNode) node).isChecked) {
				return true;
			}
			if (node.hasChildren()) {
				return childIsChecked(node);
			}
		}

		return false;
	}

	@Override
	public CheckableStructTreeNode addChild(final VarDeclaration memberVariable) {
		final CheckableStructTreeNode treeNode = new CheckableStructTreeNode(memberVariable, this);
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


	private static void serializeTreeToString(final CheckableStructTreeNode parent, final StringBuilder stringBuilder) {
		for (final StructTreeNode n : parent.getChildren()) {
			final CheckableStructTreeNode node = (CheckableStructTreeNode) n;
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

	public CheckboxTreeViewer getViewer() {
		return (CheckboxTreeViewer) root.viewer;
	}

	public CheckableStructTreeNode getRootNode() {
		return (CheckableStructTreeNode) root;
	}

	public boolean isGrey() {
		return isGrey;
	}



	private void setGrey(final boolean isGrey) {
		if (root.viewer != null) {
			getViewer().setGrayed(this, isGrey);
		}
		this.isGrey = isGrey;
	}


}