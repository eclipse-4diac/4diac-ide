/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 * 				 2022 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians - registers hover provider and sets regex for
 * 		comments/documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui

import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.document.STAlgorithmDocument
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.document.STAlgorithmDocumentProvider
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.reconciler.STAlgorithmDocumentReconcileStrategy
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.resource.STAlgorithmResourceForIEditorInputFactory
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.resource.IContainer
import org.eclipse.xtext.resource.containers.StateBasedContainerManager
import org.eclipse.xtext.ui.editor.model.XtextDocument
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider
import org.eclipse.xtext.ui.editor.reconciler.XtextDocumentReconcileStrategy
import org.eclipse.xtext.ui.shared.Access
import com.google.inject.Binder
import org.eclipse.fordiac.ide.structuredtextcore.ui.hovering.STCoreHoverProvider
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider
import com.google.inject.name.Names
import org.eclipse.xtext.documentation.impl.AbstractMultiLineCommentProvider
import org.eclipse.fordiac.ide.structuredtextcore.ui.hovering.STCoreHoverDocumentationProvider
import org.eclipse.xtext.ui.editor.hover.html.IEObjectHoverDocumentationProvider

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

	def Class<? extends XtextDocument> bindXtextDocument() {
		return STAlgorithmDocument
	}

	def Class<? extends XtextDocumentProvider> bindXtextDocumentProvider() {
		return STAlgorithmDocumentProvider
	}

	override bindIResourceForEditorInputFactory() {
		return STAlgorithmResourceForIEditorInputFactory
	}

	def Class<? extends XtextDocumentReconcileStrategy> bindXtextDocumentReconcileStrategy() {
		return STAlgorithmDocumentReconcileStrategy
	}
	
	
	def Class<? extends IEObjectHoverDocumentationProvider> bindIEObjectHoverDocumentationProvider() {
		return STCoreHoverDocumentationProvider
	}

	def Class<? extends IEObjectHoverProvider> bindIEObjectHoverProvider() {
		return STCoreHoverProvider
	}

	def configureIEObjectDocumentationProvider(Binder binder) {
		binder.bindConstant().annotatedWith(Names.named(AbstractMultiLineCommentProvider.START_TAG)).to("[/(]\\*\\*?"); // $NON-NLS-1$
		binder.bindConstant().annotatedWith(Names.named(AbstractMultiLineCommentProvider.END_TAG)).to("\\*[/)]"); // $NON-NLS-1$
	}
}
