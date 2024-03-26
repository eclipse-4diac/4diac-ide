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

import org.eclipse.fordiac.ide.model.CheckableStructTreeNode;
import org.eclipse.fordiac.ide.model.commands.create.AddDemuxPortCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteDemuxPortCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
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
					final CheckableStructTreeNode node = (CheckableStructTreeNode) element;
					return node.isChecked() || node.isGrey();
				}
				return false;
			}

			@Override
			public boolean isGrayed(final Object element) {
				final CheckableStructTreeNode node = (CheckableStructTreeNode) element;
				return node.isGrey();
			}

		});

		getViewer().addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(final CheckStateChangedEvent event) {
				final CheckableStructTreeNode node = (CheckableStructTreeNode) event.getElement();
				final Command cmd = createDemuxPortCommand(node);
				if (cmd.canExecute()) {
					executeCommand(cmd);
					selectStructManipulator(cmd);
				} else {
					// reset checkmark as command was not executed
					node.updateNode(!event.getChecked());
				}
			}

			private Command createDemuxPortCommand(final CheckableStructTreeNode node) {
				if (!node.isChecked() || node.isGrayChecked()) {
					return new AddDemuxPortCommand(getType(), node);
				}

				return new DeleteDemuxPortCommand(getType(), node);
			}

		});

	}

	private void selectStructManipulator(final Command cmd) {
		Demultiplexer type = null;

		if (cmd instanceof final AddDemuxPortCommand addCommand) {
			type = addCommand.getType();
		}

		if (cmd instanceof final DeleteDemuxPortCommand deleteCommand) {
			type = deleteCommand.getType();
		}
		setInitTree(false);
		blockRefresh = true;
		updateStructManipulatorFB(type);
		setInitTree(true);
		blockRefresh = false;
	}

	protected void setInitTree(final boolean initTree) {
		this.initTree = initTree;
	}

	private CheckboxTreeViewer getViewer() {
		return (CheckboxTreeViewer) memberVarViewer;
	}

	@Override
	protected Demultiplexer getType() {
		return (Demultiplexer) super.getType();
	}

	@Override
	public void stackChanged(final CommandStackEvent event) {
		if (event.getDetail() == CommandStack.POST_UNDO || event.getDetail() == CommandStack.POST_REDO) {
			final Command command = event.getCommand();
			if (command instanceof AddDemuxPortCommand || command instanceof DeleteDemuxPortCommand) {
				selectStructManipulator(command);
			}
			super.stackChanged(event);
		}
	}
}