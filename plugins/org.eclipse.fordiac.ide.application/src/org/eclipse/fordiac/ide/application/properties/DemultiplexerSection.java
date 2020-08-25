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
	protected TreeViewer createTreeViewer(Composite parent) {
		CheckboxTreeViewer v = new CheckboxTreeViewer(parent);
		v.setUseHashlookup(true);
		return v;
	}

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		getViewer().setCheckStateProvider(new ICheckStateProvider() {
			@Override
			public boolean isChecked(Object element) {
				if (null != element) {
					TreeNode node = (TreeNode) element;
					return null != getType().getInterfaceElement(node.getPathName());
				}
				return false;
			}

			@Override
			public boolean isGrayed(Object element) {
				return false;
			}

		});
		getViewer().addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				TreeNode node = (TreeNode) event.getElement();
				Command cmd;
				if (event.getChecked()) {
					cmd = new AddDemuxPortCommand(getType(), getCreationName(node));
				} else {
					cmd = new DeleteDemuxPortCommand(getType(), getCreationName(node));
				}
				executeCommand(cmd);
			}

			private String getCreationName(TreeNode node) {
				return node.getPathName();
			}
		});
	}

	private CheckboxTreeViewer getViewer() {
		return (CheckboxTreeViewer) memberVarViewer;
	}

	@Override
	protected Demultiplexer getType() {
		return (Demultiplexer) super.getType();
	}
}
