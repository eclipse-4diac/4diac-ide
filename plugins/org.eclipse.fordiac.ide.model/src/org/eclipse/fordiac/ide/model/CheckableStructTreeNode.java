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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.viewers.CheckboxTreeViewer;

public class CheckableStructTreeNode extends AbstractStructTreeNode {

	private boolean isChecked = false;
	private boolean isGrey = false;

	CheckableStructTreeNode(final VarDeclaration memberVariable, final CheckableStructTreeNode structTreeNode,
			final CheckableStructTree tree) {
		super(memberVariable, structTreeNode, tree);
	}

	// this is used by the tree to create the root
	CheckableStructTreeNode(final CheckableStructTree tree) {
		setTree(tree);
	}

	@Override
	public CheckableStructTree getTree() {
		return (CheckableStructTree) super.getTree();
	}

	@Override
	public CheckableStructTreeNode addChild(final EObject memberVariable) {
		if (memberVariable instanceof final VarDeclaration vardeclaration) {
			final CheckableStructTreeNode treeNode = new CheckableStructTreeNode(vardeclaration, this, getTree());
			getChildren().add(treeNode);
			return treeNode;
		}
		return null;
	}

	public void updateNode(final boolean check) {
		if (check && isGrayChecked()) {
			setGrey(false);
		}
		check(check);

		updateGreyedElements(this);
	}

	void check(final boolean isChecked) {
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

	private void updateGreyedElements(final CheckableStructTreeNode node) {
		if (node.isChecked() || node.childIsChecked()) {
			greyParents(node);
		} else {
			ungreyParents(node);
		}
	}

	private void ungreyParents(final CheckableStructTreeNode node) {
		CheckableStructTreeNode parent = node;
		final AbstractStructTreeNode rootNode = node.getTree().getRoot();
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
		CheckableStructTreeNode parent = node;
		final AbstractStructTreeNode rootNode = node.getTree().getRoot();
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

	protected boolean childIsChecked(final AbstractStructTreeNode parent) {
		for (final AbstractStructTreeNode node : parent.getChildren()) {
			if (((CheckableStructTreeNode) node).isChecked) {
				return true;
			}
			if (node.hasChildren()) {
				return childIsChecked(node);
			}
		}

		return false;
	}

	private static AbstractStructTreeNode find(final AbstractStructTreeNode parent, final String name) {
		for (final AbstractStructTreeNode node : parent.getChildren()) {
			if (node.getPinName().equals(name)) {
				return node;
			}
			if (node.hasChildren()) {
				final AbstractStructTreeNode find = find(node, name);
				if (find != null) {
					return find;
				}
			}
		}
		return null;
	}

	public AbstractStructTreeNode find(final String name) {
		return find(getTree().getRoot(), name);
	}

	private static void serializeTreeToString(final CheckableStructTreeNode parent, final StringBuilder stringBuilder) {
		for (final AbstractStructTreeNode n : parent.getChildren()) {
			final CheckableStructTreeNode node = (CheckableStructTreeNode) n;
			if (node.isChecked && !node.isGrey) {
				stringBuilder.append(node.getPinName());
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
		return getTree().getViewer() instanceof final CheckboxTreeViewer checkboxtreeviewer ? checkboxtreeviewer : null;
	}

	public boolean isGrey() {
		return isGrey;
	}

	private void setGrey(final boolean isGrey) {
		if (getViewer() != null) {
			getViewer().setGrayed(this, isGrey);
		}
		this.isGrey = isGrey;
	}

	public static List<VarDeclaration> getVarDeclarations(final List<String> varDeclNames,
			final CheckableStructTreeNode node) {
		final List<VarDeclaration> vars = new ArrayList<>();
		varDeclNames.forEach(name -> {
			final CheckableStructTreeNode find = (CheckableStructTreeNode) node.find(name);
			VarDeclaration findVarDeclarationInStruct = null;
			if (find != null) {
				findVarDeclarationInStruct = find.getVariable();
				final VarDeclaration varDecl = EcoreUtil.copy(findVarDeclarationInStruct);
				if (null != varDecl) {
					varDecl.setName(name);
					vars.add(varDecl);
				}
			}
		});
		return vars;
	}

	// equals and hash code are used by the treeviewer to maintain the expanded
	// state on updates. For us currently the pinname which contains the full
	// hierarchical name are the main equality criteria.
	@Override
	public int hashCode() {
		return (getPinName() != null) ? getPinName().hashCode() : super.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final CheckableStructTreeNode other = (CheckableStructTreeNode) obj;
		if (getPinName() != null && other.getPinName() != null) {
			return getPinName().equals(other.getPinName());
		}

		return super.equals(obj);
	}

}