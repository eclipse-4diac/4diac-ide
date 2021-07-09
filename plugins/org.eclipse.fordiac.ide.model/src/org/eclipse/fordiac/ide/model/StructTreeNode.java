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
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

public class StructTreeNode {

	protected final VarDeclaration variable;
	protected final StructTreeNode root;
	protected final String pinName;
	protected final List<StructTreeNode> children;
	protected final StructTreeNode parent;
	protected TreeViewer viewer = null;

	protected StructTreeNode(final VarDeclaration variable, final StructTreeNode parent) {
		this.variable = variable;
		this.parent = parent;
		this.pinName = getFullPinName();
		this.children = new ArrayList<>();
		this.root = parent.root;
	}

	protected StructTreeNode() {
		// initialize root node
		this.variable = null;
		this.parent = null;
		this.pinName = null;
		this.children = new ArrayList<>();
		this.root = this;
	}

	public static void buildTree(final StructManipulator struct, final StructuredType structType,
			final StructTreeNode parent) {
		for (final VarDeclaration memberVariable : structType.getMemberVariables()) {
			final StructTreeNode treeNode = parent.addChild(memberVariable);

			if ((memberVariable.getType() instanceof StructuredType)
					&& (memberVariable.getType() != GenericTypes.ANY_STRUCT)) {
				buildTree(struct, (StructuredType) memberVariable.getType(), treeNode);
			}
		}
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

	public StructTreeNode getParent() {
		return parent;
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

	private String getFullPinName() {
		if ((null == parent) || (null == parent.pinName)) {
			return getVariableName(variable);
		}
		return (parent.pinName + "." + getVariableName(variable)); //$NON-NLS-1$
	}

	public void setViewer(final TreeViewer viewer) {
		root.viewer = viewer;
	}

	public StructTreeNode addChild(final VarDeclaration memberVariable) {
		final StructTreeNode treeNode = new StructTreeNode(memberVariable, this);
		this.children.add(treeNode);
		return treeNode;
	}

	public static class StructTreeContentProvider implements ITreeContentProvider {

		private StructTreeNode root = null;

		public void setRoot(final StructTreeNode root) {
			this.root = root;
		}

		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof StructManipulator) {
				return getMemberVariableNodes(((StructManipulator) inputElement));
			}
			return new Object[] {};
		}

		private Object[] getMemberVariableNodes(final StructManipulator struct) {
			return root.getChildrenAsArray();
		}

		@Override
		public Object[] getChildren(final Object parentElement) {
			if (parentElement instanceof CheckableStructTreeNode) {
				return ((StructTreeNode) parentElement).getChildrenAsArray();
			}
			return new Object[0];
		}

		@Override
		public Object getParent(final Object element) {
			if (element instanceof CheckableStructTreeNode) {
				return ((StructTreeNode) element).getParent();
			}
			return null;
		}

		@Override
		public boolean hasChildren(final Object element) {
			if (element instanceof CheckableStructTreeNode) {
				return ((CheckableStructTreeNode) element).hasChildren();
			}
			return false;
		}
	}

	public static class StructTreeLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public Image getColumnImage(final Object element, final int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(final Object element, final int columnIndex) {
			if (element instanceof CheckableStructTreeNode) {
				final VarDeclaration memVar = ((StructTreeNode) element).getVariable();
				switch (columnIndex) {
				case 0:
					return memVar.getName();
				case 1:
					return memVar.getTypeName();
				case 2:
					return memVar.getComment();
				default:
					break;
				}
			}
			return element.toString();
		}
	}

}