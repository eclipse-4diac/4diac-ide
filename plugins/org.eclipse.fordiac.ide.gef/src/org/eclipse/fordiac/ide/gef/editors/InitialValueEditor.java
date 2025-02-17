/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editors;

import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferencePage;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueRefreshJob;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded.STAlgorithmEmbeddedEditorUtil;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded.STAlgorithmInitialValueEditedResourceProvider;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.xtext.ui.editor.XtextSourceViewer;
import org.eclipse.xtext.ui.editor.embedded.IEditedResourceProvider;

@SuppressWarnings("restriction")
public class InitialValueEditor extends XtextEmbeddedFieldEditor {

	private IInterfaceElement interfaceElement;
	private final InitialValueRefreshJob refreshJob;

	public InitialValueEditor(final Composite parent, final int style) {
		super(parent, style);
		refreshJob = new InitialValueRefreshJob(null, this::updateInitialValue);
	}

	@Override
	protected void createControl(final Composite parent, final int style) {
		super.createControl(parent, style);
		final XtextSourceViewer viewer = getEmbeddedEditor().getViewer();
		viewer.addTextPresentationListener(textPresentation -> {
			if (!viewer.getUndoManager().undoable() && !InitialValueHelper.hasInitalValue(interfaceElement)) {
				textPresentation.replaceStyleRange(new StyleRange(textPresentation.getExtent().getOffset(),
						textPresentation.getExtent().getLength(),
						InitialValueHelper.getForegroundColor(interfaceElement), getControl().getBackground()));
			}
		});
		viewer.getControl().addDisposeListener(event -> refreshJob.cancel());
	}

	@Override
	protected IEditedResourceProvider createEditedResourceProvider() {
		return new STAlgorithmInitialValueEditedResourceProvider(null);
	}

	@Override
	public void commit() {
		if (interfaceElement instanceof final VarDeclaration varDeclaration) {
			executeCommand(new ChangeValueCommand(varDeclaration, getModelAccess().getEditablePart()));
		}
		refresh();
	}

	@Override
	public void refresh() {
		refreshJob.cancel();
		final var commandExecutorCache = getCommandExecutor();
		setCommandExecutor(null);
		STAlgorithmEmbeddedEditorUtil.updateEditor(getEmbeddedEditor(), interfaceElement);
		getModelAccess().updateModel(FordiacMessages.ComputingPlaceholderValue);
		getControl().setSelection(0);
		setCommandExecutor(commandExecutorCache);
		refreshJob.schedule();
	}

	protected void updateInitialValue(final String value) {
		if (!getControl().isDisposed()
				&& FordiacMessages.ComputingPlaceholderValue.equals(getModelAccess().getEditablePart())) {
			final var commandExecutorCache = getCommandExecutor();
			setCommandExecutor(null);
			if (value.length() <= DiagramPreferencePage.getMaxDefaultValueLength()) {
				getModelAccess().updateModel(value);
			} else {
				getModelAccess().updateModel(FordiacMessages.ValueTooLarge);
			}
			getControl().setSelection(0);
			setCommandExecutor(commandExecutorCache);
		}
	}

	public IInterfaceElement getInterfaceElement() {
		return interfaceElement;
	}

	public void setInterfaceElement(final IInterfaceElement interfaceElement) {
		this.interfaceElement = interfaceElement;
		refreshJob.setInterfaceElement(interfaceElement);
	}
}
