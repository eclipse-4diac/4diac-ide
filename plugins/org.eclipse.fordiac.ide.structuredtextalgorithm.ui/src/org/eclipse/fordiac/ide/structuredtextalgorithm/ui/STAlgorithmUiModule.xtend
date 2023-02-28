/*******************************************************************************
 * Copyright (c) 2022-2023 Martin Erich Jobst
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
 *   Hesam Rezaee - registers classes for custom highlighting
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui

import com.google.inject.Binder
import com.google.inject.name.Names
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.document.STAlgorithmDocument
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.document.STAlgorithmDocumentProvider
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded.STAlgorithmURIEditorOpener
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.hyperlinking.STAlgorithmHyperlinkHelper
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.reconciler.STAlgorithmDocumentReconcileStrategy
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.resource.STAlgorithmResourceForIEditorInputFactory
import org.eclipse.fordiac.ide.structuredtextcore.ui.codemining.STCoreCodeMiningPreferences
import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.occurrences.STCoreOccurrenceComputer
import org.eclipse.fordiac.ide.structuredtextcore.ui.hovering.STCoreCommentDocumentationProvider
import org.eclipse.fordiac.ide.structuredtextcore.ui.hovering.STCoreHoverDocumentationProvider
import org.eclipse.fordiac.ide.structuredtextcore.ui.hovering.STCoreHoverProvider
import org.eclipse.fordiac.ide.structuredtextcore.ui.quickfix.CaseInsensitiveSimilarityMatcher
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreRefactoringDocumentProvider
import org.eclipse.fordiac.ide.structuredtextcore.ui.syntaxcoloring.STCoreAntlrTokenToAttributeIdMapper
import org.eclipse.fordiac.ide.structuredtextcore.ui.syntaxcoloring.STCoreHighlightingConfiguration
import org.eclipse.fordiac.ide.structuredtextcore.ui.syntaxcoloring.STCoreSemanticHighlightingCalculator
import org.eclipse.ui.PlatformUI
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider
import org.eclipse.xtext.documentation.impl.AbstractMultiLineCommentProvider
import org.eclipse.xtext.ide.editor.syntaxcoloring.ISemanticHighlightingCalculator
import org.eclipse.xtext.resource.IContainer
import org.eclipse.xtext.resource.containers.StateBasedContainerManager
import org.eclipse.xtext.ui.LanguageSpecific
import org.eclipse.xtext.ui.editor.IURIEditorOpener
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider
import org.eclipse.xtext.ui.editor.hover.html.IEObjectHoverDocumentationProvider
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkHelper
import org.eclipse.xtext.ui.editor.model.XtextDocument
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider
import org.eclipse.xtext.ui.editor.occurrences.IOccurrenceComputer
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer
import org.eclipse.xtext.ui.editor.quickfix.ISimilarityMatcher
import org.eclipse.xtext.ui.editor.reconciler.XtextDocumentReconcileStrategy
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration
import org.eclipse.xtext.ui.refactoring.impl.IRefactoringDocument
import org.eclipse.xtext.ui.shared.Access
import org.eclipse.xtext.ui.editor.contentassist.IContentProposalPriorities
import org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist.STCoreContentProposalPriorities
import org.eclipse.xtext.ui.editor.quickfix.XtextQuickAssistProcessor
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.quickfix.STAlgorithmQuickAssistProcessor

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

	def Class<? extends IRefactoringDocument.Provider> bindIRefactoringDocumentProvider() {
		return STCoreRefactoringDocumentProvider;
	}

	def void configureCodeMinings(Binder binder) {
		binder.bind(IPreferenceStoreInitializer).annotatedWith(Names.named("codeMiningInitializer")).to(
			STCoreCodeMiningPreferences.Initializer);
	}

	def Class<? extends DefaultAntlrTokenToAttributeIdMapper> bindDefaultAntlrTokenToAttributeIdMapper() {
		return STCoreAntlrTokenToAttributeIdMapper;
	}

	def Class<? extends IHighlightingConfiguration> bindIHighlightingConfiguration() {
		return STCoreHighlightingConfiguration;
	}

	def Class<? extends ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator() {
		return STCoreSemanticHighlightingCalculator;
	}

	def Class<? extends IEObjectDocumentationProvider> bindIEObjectDocumentationProvider() {
		return STCoreCommentDocumentationProvider;
	}

	def Class<? extends ISimilarityMatcher> bindISimilarityMatcher() {
		return CaseInsensitiveSimilarityMatcher;
	}

	def Class<? extends IHyperlinkHelper> bindIHyperlinkHelper() {
		return STAlgorithmHyperlinkHelper;
	}

	override void configureLanguageSpecificURIEditorOpener(Binder binder) {
		if (PlatformUI.isWorkbenchRunning()) {
			binder.bind(IURIEditorOpener).annotatedWith(LanguageSpecific).to(STAlgorithmURIEditorOpener)
		}
	}

	def Class<? extends IOccurrenceComputer> bindIOccurrenceComputer() {
		return STCoreOccurrenceComputer;
	}

	def Class<? extends IContentProposalPriorities> bindIContentProposalPriorities() {
		return STCoreContentProposalPriorities;
	}

	def Class<? extends XtextQuickAssistProcessor> bindXtextQuickAssistProcessor() {
		return STAlgorithmQuickAssistProcessor
	}
}
