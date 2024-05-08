/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.utilities;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.handlers.MoveThroughHierarchyHandler.TreeNodeLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

public class SubAppHierarchyDialog {

	final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(Display.getCurrent().getActiveShell(),
			new TreeNodeLabelProvider(), new TreeNodeContentProvider()) {
		@Override
		protected TreeViewer createTreeViewer(final Composite parent) {
			final TreeViewer viewer = super.createTreeViewer(parent);
			viewer.expandAll();
			return viewer;
		}
	};

	public SubAppHierarchyDialog(final FBNetworkElement root, final List<FBNetworkElement> filteredElements) {
		final List<TreeNode> nodeList = buildNodeList(root, filteredElements);
		dialog.setInput(nodeList.toArray(new TreeNode[0]));
		dialog.setTitle(Messages.MoveElementDialogTitle);
		dialog.setAllowMultiple(false);
	}

	public FBNetwork open() {
		dialog.open();
		final Object result = dialog.getResult() != null ? dialog.getResult()[0] : null;
		if (result instanceof final TreeNode node) {
			return switch (node.getValue()) {
			case final SubApp subapp -> subapp.getSubAppNetwork();
			case final Application app -> app.getFBNetwork();
			case final CompositeFBType cfb -> cfb.getFBNetwork();
			default -> null;
			};
		}
		return null;
	}

	private static List<TreeNode> buildNodeList(final FBNetworkElement root, final List<FBNetworkElement> filterList) {
		final List<TreeNode> nodeList = new ArrayList<>();
		final EObject container = EcoreUtil.getRootContainer(root);
		if (container instanceof final AutomationSystem as) {
			as.getApplication().forEach(app -> {
				final TreeNode node = new TreeNode(app);
				nodeList.add(node);
				addFBNetwork(node, app.getFBNetwork(), filterList);
			});
		} else if (container instanceof final CompositeFBType cfb) {
			final TreeNode node = new TreeNode(cfb);
			nodeList.add(node);
			addFBNetwork(node, cfb.getFBNetwork(), filterList);
		}
		return nodeList;
	}

	private static void addFBNetwork(final TreeNode parent, final FBNetwork network,
			final List<FBNetworkElement> filterList) {
		final List<TreeNode> nodeList = new ArrayList<>();
		network.getNetworkElements().forEach(fbnE -> {
			if (fbnE instanceof final UntypedSubApp subapp && !filterList.contains(fbnE)) {
				final TreeNode node = new TreeNode(subapp);
				node.setParent(parent);
				nodeList.add(node);
				addFBNetwork(node, subapp.getSubAppNetwork(), filterList);
			}
		});
		parent.setChildren(nodeList.toArray(new TreeNode[0]));
	}
}
