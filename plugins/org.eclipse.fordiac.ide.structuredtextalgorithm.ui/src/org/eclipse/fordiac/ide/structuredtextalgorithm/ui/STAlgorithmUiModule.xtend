/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui

import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.document.STAlgorithmDocumentProvider
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.resource.STAlgorithmResourceForIEditorInputFactory
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.resource.IContainer
import org.eclipse.xtext.resource.containers.StateBasedContainerManager
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider
import org.eclipse.xtext.ui.shared.Access

/**
 * Use this class to register components to be used within the Eclipse IDE.
 */
@FinalFieldsConstructor
class STAlgorithmUiModule extends AbstractSTAlgorithmUiModule {
	def Class<? extends IContainer.Manager> bindIContainer$Manager() {
		return StateBasedContainerManager
	}

	override provideIAllContainersState() {
		return Access::getWorkspaceProjectsState
	}

	def Class<? extends XtextDocumentProvider> bindXtextDocumentProvider() {
		return STAlgorithmDocumentProvider
	}

	override bindIResourceForEditorInputFactory() {
		return STAlgorithmResourceForIEditorInputFactory
	}
}
