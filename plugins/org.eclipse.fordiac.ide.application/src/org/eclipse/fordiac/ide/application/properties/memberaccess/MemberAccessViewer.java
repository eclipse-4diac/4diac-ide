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
 *   Alois Zoitl - Extracted from ConfigureableMoveFBSection
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties.memberaccess;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.change.ChangePinVisibilityCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class MemberAccessViewer {

	private final boolean input;
	private final CommandExecutor cmdExecutor;
	private TreeViewer viewer;
	private BlockFBNetworkElement block;

	public MemberAccessViewer(final boolean input, final CommandExecutor cmdExecutor) {
		this.input = input;
		Objects.nonNull(cmdExecutor);
		this.cmdExecutor = cmdExecutor;
	}

	public void createControls(final Composite parent, final TabbedPropertySheetWidgetFactory widgetFactory) {
		final Group group = widgetFactory.createGroup(parent,
				(input) ? FordiacMessages.Inputs : FordiacMessages.Outputs);
		GridLayoutFactory.fillDefaults().applyTo(group);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(group);

		// Wrapper composite to hold the table. This is needed especially for windows
		// that the table is not drawn on top of the group's headline
		final Composite wrapper = widgetFactory.createComposite(group);
		GridLayoutFactory.fillDefaults().applyTo(wrapper);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(wrapper);

		viewer = createMemberAccessViewer(wrapper);
	}

	public void setInput(final BlockFBNetworkElement block) {
		this.block = block;

		if (block == null) {
			viewer.setInput(null);
		} else if (!(viewer.getInput() instanceof final MemberAccessTree memAccessTree)
				|| memAccessTree.getBlockFBNetworkElement() != block) {
			final EList<VarDeclaration> variables = (input) ? block.getInterface().getInputVars()
					: block.getInterface().getOutputVars();
			viewer.setInput(new MemberAccessTree(block, variables));
			updateVisibility();
		}
	}

	public void updateVisibility() {
		if (block != null) {
			updateVisibility(block.getInterface().getAllInterfaceElements()
					.filter(ie -> ie instanceof final VarDeclaration vardecl && ie.isIsInput() == input
							&& !vardecl.isInOutVar()));
		}
	}

	private void updateVisibility(final Stream<IInterfaceElement> vars) {
		final MemberAccessTree memAccessTree = (MemberAccessTree) viewer.getInput();
		vars.forEach(ie -> {
			final MemberAccessTreeNode node = memAccessTree.getChild(ie.getRelativeName(block));
			if (node != null && node.isVisible() != ie.isVisible()) {
				node.setVisible(ie.isVisible());
				viewer.update(node, null);
			}
		});
	}

	private TreeViewer createMemberAccessViewer(final Composite parent) {
		final CheckboxTreeViewer newViewer = new CheckboxTreeViewer(parent);
		newViewer.setUseHashlookup(true);
		newViewer.setAutoExpandLevel(2);
		configureTreeLayout(parent, newViewer);
		newViewer.setContentProvider(new MemberAccessContentProvider());
		newViewer.setLabelProvider(new MemberAccessLabelProvider());
		newViewer.setCheckStateProvider(new ICheckStateProvider() {
			@Override
			public boolean isChecked(final Object element) {
				if (element instanceof final MemberAccessTreeNode memAccessTreeNode) {
					return memAccessTreeNode.isVisible();
				}
				return false;
			}

			@Override
			public boolean isGrayed(final Object element) {
				return false;
			}
		});
		newViewer.addCheckStateListener(getCheckStateListener());
		return newViewer;
	}

	private static void configureTreeLayout(final Composite parent, final TreeViewer viewer) {
		final TreeColumnLayout layout = new TreeColumnLayout();
		parent.setLayout(layout);

		final TreeViewerColumn variableName = new TreeViewerColumn(viewer, SWT.LEFT);
		final TreeViewerColumn variableType = new TreeViewerColumn(viewer, SWT.LEFT);
		final TreeViewerColumn comment = new TreeViewerColumn(viewer, SWT.LEFT);

		viewer.getTree().setHeaderVisible(true);
		variableName.getColumn().setResizable(true);
		variableType.getColumn().setResizable(true);

		variableName.getColumn().setText(FordiacMessages.Name);
		variableType.getColumn().setText(FordiacMessages.Type);
		comment.getColumn().setText(FordiacMessages.Comment);

		layout.setColumnData(variableName.getColumn(), new ColumnWeightData(30, true));
		layout.setColumnData(variableType.getColumn(), new ColumnWeightData(20, true));
		layout.setColumnData(comment.getColumn(), new ColumnWeightData(50, true));
	}

	private ICheckStateListener getCheckStateListener() {
		return event -> {
			final MemberAccessTreeNode node = (MemberAccessTreeNode) event.getElement();
			final Command cmd = createPinVisibilityCommand(node.getNamePath(), event.getChecked());
			if (cmd != null && cmd.canExecute()) {
				cmdExecutor.executeCommand(cmd);
			} else {
				// reset checkmark as command was not executed
				((TreeViewer) event.getSource()).update(node, null);
			}
		};
	}

	private Command createPinVisibilityCommand(final List<String> path, final boolean visible) {
		final IInterfaceElement ie = block.getInterface().getInterfaceElement(path, visible);
		if (ie == null) {
			return null;
		}
		return new ChangePinVisibilityCommand(ie, visible);
	}

}
