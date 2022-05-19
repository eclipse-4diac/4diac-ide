/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH
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
 *       - registers hover provider and configures comment regex for documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui;

import org.eclipse.fordiac.ide.structuredtextcore.ui.hovering.STCoreHoverDocumentationProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.hovering.STCoreHoverProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.documentation.impl.AbstractMultiLineCommentProvider;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.ui.editor.hover.html.IEObjectHoverDocumentationProvider;

import com.google.inject.Binder;
import com.google.inject.name.Names;

/**
 * Use this class to register components to be used within the Eclipse IDE.
 */
public class STFunctionUiModule extends AbstractSTFunctionUiModule {

	public STFunctionUiModule(final AbstractUIPlugin plugin) {
		super(plugin);
	}

	@SuppressWarnings("static-method")
	public Class<? extends IEObjectHoverProvider> bindIEObjectHoverProvider() {
		return STCoreHoverProvider.class;
	}

	@SuppressWarnings("static-method")
	public Class<? extends IEObjectHoverDocumentationProvider> bindIEObjectHoverDocumentationProvider() {
		return STCoreHoverDocumentationProvider.class;
	}

	@SuppressWarnings("static-method")
	public void configureIEObjectDocumentationProvider(final Binder binder) {
		binder.bindConstant().annotatedWith(Names.named(AbstractMultiLineCommentProvider.START_TAG)).to("[/(]\\*\\*?"); //$NON-NLS-1$
		binder.bindConstant().annotatedWith(Names.named(AbstractMultiLineCommentProvider.END_TAG)).to("\\*[/)]"); //$NON-NLS-1$
	}
}
