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
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded.STAlgorithmTypeDeclarationEditedResourceProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.xtext.ui.editor.embedded.IEditedResourceProvider;

@SuppressWarnings("restriction")
public class TypeDeclarationCellEditor extends XtextStyledTextCellEditor {
	private final VarDeclaration varDeclaration;

	public TypeDeclarationCellEditor(final Composite parent, final VarDeclaration varDeclaration) {
		this.varDeclaration = varDeclaration;
		create(parent);
	}

	public TypeDeclarationCellEditor(final Composite parent, final VarDeclaration varDeclaration, final int style) {
		this.varDeclaration = varDeclaration;
		setStyle(style);
		create(parent);
	}

	@Override
	protected IEditedResourceProvider createEditedResourceProvider() {
		return new STAlgorithmTypeDeclarationEditedResourceProvider(varDeclaration);
	}
}
