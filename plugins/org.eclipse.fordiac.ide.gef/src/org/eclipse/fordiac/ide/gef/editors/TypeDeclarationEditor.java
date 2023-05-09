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

import org.eclipse.fordiac.ide.model.commands.change.ChangeVarDeclarationTypeCommand;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded.STAlgorithmEmbeddedEditorUtil;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded.STAlgorithmTypeDeclarationEditedResourceProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.xtext.ui.editor.embedded.IEditedResourceProvider;

@SuppressWarnings("restriction")
public class TypeDeclarationEditor extends XtextEmbeddedFieldEditor {

	private IInterfaceElement interfaceElement;

	public TypeDeclarationEditor(final Composite parent, final int style) {
		super(parent, style);
	}

	@Override
	protected IEditedResourceProvider createEditedResourceProvider() {
		return new STAlgorithmTypeDeclarationEditedResourceProvider(null);
	}

	@Override
	public void commit() {
		if (interfaceElement instanceof final VarDeclaration varDeclaration) {
			executeCommand(ChangeVarDeclarationTypeCommand.forTypeDeclaration(varDeclaration,
					getModelAccess().getEditablePart()));
		}
		refresh();
	}

	@Override
	public void refresh() {
		final var commandExecutorCache = getCommandExecutor();
		setCommandExecutor(null);
		STAlgorithmEmbeddedEditorUtil.updateEditor(getEmbeddedEditor(), interfaceElement);
		getModelAccess().updateModel(interfaceElement != null ? interfaceElement.getFullTypeName() : ""); //$NON-NLS-1$
		getControl().setSelection(0);
		setCommandExecutor(commandExecutorCache);
	}

	public IInterfaceElement getInterfaceElement() {
		return interfaceElement;
	}

	public void setInterfaceElement(final IInterfaceElement interfaceElement) {
		this.interfaceElement = interfaceElement;
	}
}
