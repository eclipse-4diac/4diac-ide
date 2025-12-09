/*******************************************************************************
 * Copyright (c) 2024 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial commit of contract specification editor
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui;

import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.quickfix.STCoreQuickAssistProcessor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.ui.editor.model.IResourceForEditorInputFactory;
import org.eclipse.xtext.ui.editor.model.ResourceForIEditorInputFactory;
import org.eclipse.xtext.ui.editor.quickfix.XtextQuickAssistProcessor;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.eclipse.xtext.ui.resource.SimpleResourceSetProvider;

import com.google.inject.Provider;

/**
 * Use this class to register components to be used within the Eclipse IDE.
 */
public class ContractSpecUiModule extends AbstractContractSpecUiModule {

	public ContractSpecUiModule(final AbstractUIPlugin plugin) {
		super(plugin);
	}

	@Override
	public Class<? extends IResourceForEditorInputFactory> bindIResourceForEditorInputFactory() {
		return ResourceForIEditorInputFactory.class;
	}

	@Override
	public Class<? extends IResourceSetProvider> bindIResourceSetProvider() {
		return SimpleResourceSetProvider.class;
	}

	@Override
	public Provider<IAllContainersState> provideIAllContainersState() {
		return org.eclipse.xtext.ui.shared.Access.getWorkspaceProjectsState();
	}

	// necessary, because the default XtextQuickAssistProcessor does not work with
	// the embedded editor
	// also see: https://github.com/eclipse-xtext/xtext/issues/2427
	@SuppressWarnings("static-method")
	public Class<? extends XtextQuickAssistProcessor> bindXtextQuickAssistProcessor() {
		return STCoreQuickAssistProcessor.class;
	}
}
