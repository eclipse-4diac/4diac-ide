/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - create dedicated section for StructManipulator to improve
 *                     the usability of the struct handling
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.model.StructTreeNode;
import org.eclipse.fordiac.ide.model.commands.create.AddDemuxPortCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteDemuxPortCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class DemultiplexerSection extends StructManipulatorSection {
	@Override
	protected TreeViewer createTreeViewer(final Composite parent) {
		final CheckboxTreeViewer viewer = new CheckboxTreeViewer(parent);
		viewer.setUseHashlookup(true);
		return viewer;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		getViewer().setCheckStateProvider(new ICheckStateProvider() {
			@Override
			public boolean isChecked(final Object element) {
				if (null != element) {
					final StructTreeNode node = (StructTreeNode) element;
					return node.isChecked() || node.isGrey();
				}
				return false;
			}

			@Override
			public boolean isGrayed(final Object element) {
				final StructTreeNode node = (StructTreeNode) element;
				return node.isGrey();
			}

		});

		getViewer().addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(final CheckStateChangedEvent event) {
				final StructTreeNode node = (StructTreeNode) event.getElement();
				final Command cmd = createDemuxPortCommand(node);
				executeCommand(cmd);
				updateGrayedElements(node, getViewer());
				selectStructManipulator(cmd);

			}

			private void selectStructManipulator(final Command cmd) {
				Demultiplexer type = null;

				if (cmd instanceof AddDemuxPortCommand) {
					final AddDemuxPortCommand addCommand = (AddDemuxPortCommand) cmd;
					type = addCommand.getType();
				}

				if (cmd instanceof DeleteDemuxPortCommand) {
					final DeleteDemuxPortCommand deleteCommand = (DeleteDemuxPortCommand) cmd;
					type = deleteCommand.getType();
				}
				setInitTree(false);
				selectNewStructManipulatorFB(type);
				setInitTree(true);
			}

			private Command createDemuxPortCommand(final StructTreeNode node) {
				if (!node.isChecked()) {
					return new AddDemuxPortCommand(getType(), node);
				}

				return new DeleteDemuxPortCommand(getType(), node);
			}

		});

	}

	protected void setInitTree(final boolean initTree) {
		this.initTree = initTree;
	}

	private static void updateGrayedElements(final StructTreeNode node, final CheckboxTreeViewer viewer) {

		if (node.isGrey()) {
			node.setGrey(false);
		}

		if (node.isChecked()) {
			greyParents(node, viewer);
		} else {
			ungreyParents(node, viewer);
		}
	}

	private static void ungreyParents(final StructTreeNode node, final CheckboxTreeViewer viewer) {
		StructTreeNode parent = node.getParent();
		final StructTreeNode rootNode = node.getRootNode();
		while (parent != rootNode) {
			if (parent.isGrey() && !parent.childIsChecked()) {
				parent.setGrey(false);
				parent.check(false);
				viewer.setGrayChecked(parent, false);
			}
			parent = parent.getParent();
		}
	}

	private static void greyParents(final StructTreeNode node, final CheckboxTreeViewer viewer) {
		if (node.isGrey() && node.isChecked()) {
			node.setGrey(false);
		}


		StructTreeNode parent = node.getParent();
		final StructTreeNode rootNode = node.getRootNode();

		while (parent != rootNode) {
			if (!parent.isChecked()) {
				parent.setGrey(true);
				parent.check(true);
				viewer.setGrayChecked(parent, true);
			}

			parent = parent.getParent();
		}

	}

	private CheckboxTreeViewer getViewer() {
		return (CheckboxTreeViewer) memberVarViewer;
	}

	@Override
	protected Demultiplexer getType() {
		return (Demultiplexer) super.getType();
	}
}