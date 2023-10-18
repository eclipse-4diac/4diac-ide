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
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.contentassist.STAlgorithmReferenceProposalCreator
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.document.STAlgorithmDocument
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.document.STAlgorithmDocumentPartitioner
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.document.STAlgorithmDocumentUpdaterChangeAdapterFilter
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded.STAlgorithmEmbeddedEditorActions
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.hyperlinking.STAlgorithmHyperlinkHelper
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.refactoring.ExtractMethodRefactoring
import org.eclipse.fordiac.ide.structuredtextcore.ui.cleanup.STCoreCleanupEditorCallback
import org.eclipse.fordiac.ide.structuredtextcore.ui.cleanup.STCoreSaveActionsPreferences
import org.eclipse.fordiac.ide.structuredtextcore.ui.codemining.STCoreCodeMiningPreferences
import org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist.STCoreContentAssistPreferences
import org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist.STCoreContentProposalPriorities
import org.eclipse.fordiac.ide.structuredtextcore.ui.document.LibraryElementXtextDocumentUpdater.LibraryElementChangeAdapterFilter
import org.eclipse.fordiac.ide.structuredtextcore.ui.document.STCoreDocumentPartitioner
import org.eclipse.fordiac.ide.structuredtextcore.ui.document.STCoreDocumentProvider
import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.STCoreSourceViewer.STCoreSourceViewerFactory
import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.STCoreURIEditorOpener
import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.occurrences.STCoreOccurrenceComputer
import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.quickfix.STCoreQuickAssistProcessor
import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.reconciler.STCoreDocumentReconcileStrategy
import org.eclipse.fordiac.ide.structuredtextcore.ui.hovering.STCoreHoverDocumentationProvider
import org.eclipse.fordiac.ide.structuredtextcore.ui.hovering.STCoreHoverProvider
import org.eclipse.fordiac.ide.structuredtextcore.ui.quickfix.CaseInsensitiveSimilarityMatcher
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.ExtractCallableRefactoring
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreRefactoringDocumentProvider
import org.eclipse.fordiac.ide.structuredtextcore.ui.resource.STCoreResourceForIEditorInputFactory
import org.eclipse.fordiac.ide.structuredtextcore.ui.resource.STCoreResourceUIServiceProvider
import org.eclipse.fordiac.ide.structuredtextcore.ui.syntaxcoloring.STCoreAntlrTokenToAttributeIdMapper
import org.eclipse.fordiac.ide.structuredtextcore.ui.syntaxcoloring.STCoreHighlightingConfiguration
import org.eclipse.fordiac.ide.structuredtextcore.ui.syntaxcoloring.STCoreSemanticHighlightingCalculator
import org.eclipse.fordiac.ide.structuredtextcore.ui.validation.STCoreMarkerCreator
import org.eclipse.fordiac.ide.structuredtextcore.ui.validation.STCoreMarkerTypeProvider
import org.eclipse.fordiac.ide.structuredtextcore.ui.validation.STCoreResourceUIValidatorExtension
import org.eclipse.ui.PlatformUI
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.documentation.impl.AbstractMultiLineCommentProvider
import org.eclipse.xtext.ide.editor.syntaxcoloring.ISemanticHighlightingCalculator
import org.eclipse.xtext.resource.IContainer
import org.eclipse.xtext.resource.containers.StateBasedContainerManager
import org.eclipse.xtext.ui.LanguageSpecific
import org.eclipse.xtext.ui.editor.IURIEditorOpener
import org.eclipse.xtext.ui.editor.IXtextEditorCallback
import org.eclipse.xtext.ui.editor.XtextEditor
import org.eclipse.xtext.ui.editor.XtextSourceViewer
import org.eclipse.xtext.ui.editor.contentassist.AbstractJavaBasedContentProposalProvider.ReferenceProposalCreator
import org.eclipse.xtext.ui.editor.contentassist.IContentProposalPriorities
import org.eclipse.xtext.ui.editor.contentassist.XtextContentAssistProcessor
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorActions
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider
import org.eclipse.xtext.ui.editor.hover.html.IEObjectHoverDocumentationProvider
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkHelper
import org.eclipse.xtext.ui.editor.model.XtextDocument
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider
import org.eclipse.xtext.ui.editor.occurrences.IOccurrenceComputer
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer
import org.eclipse.xtext.ui.editor.quickfix.ISimilarityMatcher
import org.eclipse.xtext.ui.editor.quickfix.XtextQuickAssistProcessor
import org.eclipse.xtext.ui.editor.reconciler.XtextDocumentReconcileStrategy
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration
import org.eclipse.xtext.ui.editor.validation.MarkerCreator
import org.eclipse.xtext.ui.refactoring.impl.IRefactoringDocument
import org.eclipse.xtext.ui.resource.IResourceUIServiceProvider
import org.eclipse.xtext.ui.shared.Access
import org.eclipse.xtext.ui.validation.IResourceUIValidatorExtension
import org.eclipse.xtext.ui.validation.MarkerTypeProvider

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
		return STCoreDocumentProvider
	}

	def Class<? extends STCoreDocumentPartitioner> bindSTCoreDocumentPartitioner() {
		return STAlgorithmDocumentPartitioner
	}

	def void configureSTCoreCleanupEditorCallback(Binder binder) {
		binder.bind(IXtextEditorCallback).annotatedWith(Names.named("STCoreCleanupEditorCallback")).to(
			STCoreCleanupEditorCallback);
		binder.bind(IPreferenceStoreInitializer).annotatedWith(Names.named("saveActionsInitializer")) // $NON-NLS-1$
		.to(STCoreSaveActionsPreferences.Initializer)
	}

	override bindIResourceForEditorInputFactory() {
		return STCoreResourceForIEditorInputFactory
	}

	def Class<? extends XtextDocumentReconcileStrategy> bindXtextDocumentReconcileStrategy() {
		return STCoreDocumentReconcileStrategy
	}

	def Class<? extends LibraryElementChangeAdapterFilter> bindLibraryElementChangeAdapterFilter() {
		return STAlgorithmDocumentUpdaterChangeAdapterFilter
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

	def Class<? extends ISimilarityMatcher> bindISimilarityMatcher() {
		return CaseInsensitiveSimilarityMatcher;
	}

	def Class<? extends IHyperlinkHelper> bindIHyperlinkHelper() {
		return STAlgorithmHyperlinkHelper;
	}

	override void configureLanguageSpecificURIEditorOpener(Binder binder) {
		if (PlatformUI.isWorkbenchRunning()) {
			binder.bind(IURIEditorOpener).annotatedWith(LanguageSpecific).to(STCoreURIEditorOpener)
		}
	}

	def Class<? extends IOccurrenceComputer> bindIOccurrenceComputer() {
		return STCoreOccurrenceComputer;
	}

	def Class<? extends IContentProposalPriorities> bindIContentProposalPriorities() {
		return STCoreContentProposalPriorities;
	}

	def Class<? extends XtextQuickAssistProcessor> bindXtextQuickAssistProcessor() {
		return STCoreQuickAssistProcessor
	}

	def Class<? extends XtextSourceViewer.Factory> bindXtextSourceViewer$Factory() {
		return STCoreSourceViewerFactory;
	}

	def Class<? extends EmbeddedEditorActions.Factory> bindEmbeddedEditorActions$Factory() {
		return STAlgorithmEmbeddedEditorActions.Factory
	}

	def void configureKeyBindingScope(Binder binder) {
		binder.bindConstant().annotatedWith(Names.named(XtextEditor.KEY_BINDING_SCOPE)).to(
			"org.eclipse.fordiac.ide.structuredtextcore.ui.STCoreEditorScope"); // $NON-NLS-1$
	}

	def Class<? extends ExtractCallableRefactoring> bindExtractCallableRefactoring() {
		return ExtractMethodRefactoring
	}

	def void configureContentAssist(Binder binder) {
		binder.bind(IPreferenceStoreInitializer).annotatedWith(Names.named("contentAssistInitializer")) // $NON-NLS-1$
		.to(STCoreContentAssistPreferences.Initializer);
		binder.bind(String).annotatedWith(Names.named(XtextContentAssistProcessor.COMPLETION_AUTO_ACTIVATION_CHARS)).
			toProvider(STCoreContentAssistPreferences.CompletionAutoActivationCharsProvider);
	}

	def Class<? extends ReferenceProposalCreator> bindAbstractJavaBasedContentProposalProvider$ReferenceProposalCreator() {
		return STAlgorithmReferenceProposalCreator
	}

	def Class<? extends IResourceUIServiceProvider> bindIResourceUIServiceProvider() {
		return STCoreResourceUIServiceProvider
	}

	def Class<? extends MarkerCreator> bindMarkerCreator() {
		return STCoreMarkerCreator;
	}

	override Class<? extends MarkerTypeProvider> bindMarkerTypeProvider() {
		return STCoreMarkerTypeProvider;
	}

	def Class<? extends IResourceUIValidatorExtension> bindIResourceUIValidatorExtension() {
		return STCoreResourceUIValidatorExtension
	}
}
