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
 *   Bianca Wiesmayr - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.model.StructTreeLabelProvider;
import org.eclipse.fordiac.ide.model.commands.change.HidePinCommand;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.MemberVarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;

public class MultiplexerSection extends StructManipulatorSection {

	@Override
	protected ICheckStateListener getCheckStateListener() {
		return event -> {
			final Command cmd = createHideCommand(event.getElement(), event.getChecked());
			if (cmd != null && cmd.canExecute()) {
				executeCommand(cmd);
			} else {
				// reset checkmark as command was not executed
				event.getCheckable().setChecked(event.getElement(), !event.getChecked());
			}
		};
	}

	private static Command createHideCommand(final Object selected, final boolean checked) {
		if (selected instanceof final VarDeclaration variable) {
			return new HidePinCommand(variable, checked);
		}
		return null;
	}

	@Override
	public void initTree(final StructManipulator manipulator, final TreeViewer viewer) {
		// nothing to do here
	}

	@Override
	protected ICheckStateProvider getCheckStateProvider() {
		return new ICheckStateProvider() {

			@Override
			public boolean isChecked(final Object element) {
				if (element instanceof final IInterfaceElement iel) {
					return iel.isVisible();
				}
				return false;
			}

			@Override
			public boolean isGrayed(final Object element) {
				return false;
			}
		};
	}

	@Override
	protected ITreeContentProvider getContentProvider() {
		return new ITreeContentProvider() {

			@Override
			public Object[] getElements(final Object inputElement) {
				if (inputElement instanceof final Multiplexer mux) {
					return mux.getInterface().getInputVars().toArray();
				}
				return new Object[0];
			}

			@Override
			public Object[] getChildren(final Object parentElement) {
				return new Object[0];
			}

			@Override
			public Object getParent(final Object element) {
				return null;
			}

			@Override
			public boolean hasChildren(final Object element) {
				return false;
			}

		};
	}

	@Override
	protected LabelProvider getLabelProvider() {
		return new StructTreeLabelProvider() {
			@Override
			public String getColumnText(final Object element, final int columnIndex) {
				if (element instanceof final MemberVarDeclaration varDecl) {
					switch (columnIndex) {
					case 0:
						return varDecl.getName();
					case 1:
						return varDecl.getFullTypeName();
					case 2:
						return varDecl.getComment();
					default:
						break;
					}
				}
				return element.toString();
			}
		};
	}

}