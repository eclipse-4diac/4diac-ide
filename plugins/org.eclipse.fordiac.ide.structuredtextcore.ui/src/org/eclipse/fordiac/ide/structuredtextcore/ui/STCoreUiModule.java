/**
 * Copyright (c) 2021 - 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *       - registers hover provider
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui;

import org.eclipse.fordiac.ide.structuredtextcore.ui.hovering.STCoreHoverProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreRefactoringDocumentProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.ui.refactoring.impl.IRefactoringDocument;

/**
 * Use this class to register components to be used within the Eclipse IDE.
 */
@SuppressWarnings("restriction")
public class STCoreUiModule extends AbstractSTCoreUiModule {

	public STCoreUiModule(final AbstractUIPlugin plugin) {
		super(plugin);
	}

	@SuppressWarnings("static-method")
	public Class<? extends IEObjectHoverProvider> bindIEObjectHoverProvider() {
		return STCoreHoverProvider.class;
	}

	@SuppressWarnings("static-method")
	public Class<? extends IRefactoringDocument.Provider> bindIRefactoringDocument$Provider() {
		return STCoreRefactoringDocumentProvider.class;
	}
}
