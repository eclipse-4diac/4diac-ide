/**
 * Copyright (c) 2022, 2024 Martin Erich Jobst
 *                          Primetals Technologies Austria GmbH
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
 */
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui;

import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.contentassist.STAlgorithmReferenceProposalCreator;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.document.STAlgorithmDocument;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.document.STAlgorithmDocumentPartitioner;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.document.STAlgorithmDocumentUpdaterChangeAdapterFilter;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded.STAlgorithmEmbeddedEditorActions;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.hyperlinking.STAlgorithmHyperlinkHelper;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.refactoring.ExtractMethodRefactoring;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.validation.STAlgorithmCustomValidatorConfigurationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.ui.cleanup.STCoreCleanupEditorCallback;
import org.eclipse.fordiac.ide.structuredtextcore.ui.cleanup.STCoreSaveActionsPreferences;
import org.eclipse.fordiac.ide.structuredtextcore.ui.codemining.STCoreCodeMiningPreferences;
import org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist.STCoreContentAssistPreferences;
import org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist.STCoreContentProposalPriorities;
import org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist.STCorePrefixMatcher;
import org.eclipse.fordiac.ide.structuredtextcore.ui.document.LibraryElementXtextDocumentUpdater.LibraryElementChangeAdapterFilter;
import org.eclipse.fordiac.ide.structuredtextcore.ui.document.STCoreDocumentPartitioner;
import org.eclipse.fordiac.ide.structuredtextcore.ui.document.STCoreDocumentProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.STCoreEditorPreferences;
import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.STCoreSourceViewer.STCoreSourceViewerFactory;
import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.STCoreURIEditorOpener;
import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.formatting.STCoreWhitespaceInformationProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.occurrences.STCoreOccurrenceComputer;
import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.quickfix.STCoreQuickAssistProcessor;
import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.reconciler.STCoreDocumentReconcileStrategy;
import org.eclipse.fordiac.ide.structuredtextcore.ui.hovering.STCoreHoverDocumentationProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.hovering.STCoreHoverProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.outline.FilterHeadingsContribution;
import org.eclipse.fordiac.ide.structuredtextcore.ui.outline.OutlineTreeContribution;
import org.eclipse.fordiac.ide.structuredtextcore.ui.outline.STCoreOutlinePage;
import org.eclipse.fordiac.ide.structuredtextcore.ui.quickfix.CaseInsensitiveSimilarityMatcher;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.ExtractCallableRefactoring;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreChangeConverter;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreChangeSerializer;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreLinkedPositionGroupCalculator;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreRecordingXtextResourceUpdater;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreRefactoringDocumentProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreReferenceUpdater;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreRegionDiffFormatter;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreRelatedEmfResourceUpdater;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreRelatedXtextResourceUpdater;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreRenameElementProcessor;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreRenameNameValidator;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreRenameStrategy;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreResourceLifecycleManager;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreSimpleNameProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreSyncUtil;
import org.eclipse.fordiac.ide.structuredtextcore.ui.resource.STCoreResourceForIEditorInputFactory;
import org.eclipse.fordiac.ide.structuredtextcore.ui.resource.STCoreResourceUIServiceProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.syntaxcoloring.STCoreAntlrTokenToAttributeIdMapper;
import org.eclipse.fordiac.ide.structuredtextcore.ui.syntaxcoloring.STCoreHighlightingConfiguration;
import org.eclipse.fordiac.ide.structuredtextcore.ui.syntaxcoloring.STCoreSemanticHighlightingCalculator;
import org.eclipse.fordiac.ide.structuredtextcore.ui.validation.STCoreMarkerCreator;
import org.eclipse.fordiac.ide.structuredtextcore.ui.validation.STCoreMarkerTypeProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.validation.STCoreResourceUIValidatorExtension;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.xtext.documentation.impl.AbstractMultiLineCommentProvider;
import org.eclipse.xtext.formatting.IWhitespaceInformationProvider;
import org.eclipse.xtext.ide.editor.syntaxcoloring.ISemanticHighlightingCalculator;
import org.eclipse.xtext.ide.refactoring.IRenameNameValidator;
import org.eclipse.xtext.ide.refactoring.IRenameStrategy2;
import org.eclipse.xtext.ide.serializer.IChangeSerializer;
import org.eclipse.xtext.ide.serializer.impl.RecordingXtextResourceUpdater;
import org.eclipse.xtext.ide.serializer.impl.ReferenceUpdater;
import org.eclipse.xtext.ide.serializer.impl.RegionDiffFormatter;
import org.eclipse.xtext.ide.serializer.impl.RelatedEmfResourceUpdater;
import org.eclipse.xtext.ide.serializer.impl.RelatedXtextResourceUpdater;
import org.eclipse.xtext.ide.serializer.impl.ResourceLifecycleManager;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.resource.containers.StateBasedContainerManager;
import org.eclipse.xtext.ui.LanguageSpecific;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;
import org.eclipse.xtext.ui.editor.IXtextEditorCallback;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.XtextSourceViewer;
import org.eclipse.xtext.ui.editor.contentassist.AbstractJavaBasedContentProposalProvider;
import org.eclipse.xtext.ui.editor.contentassist.IContentProposalPriorities;
import org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher;
import org.eclipse.xtext.ui.editor.contentassist.XtextContentAssistProcessor;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorActions;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.ui.editor.hover.html.IEObjectHoverDocumentationProvider;
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkHelper;
import org.eclipse.xtext.ui.editor.model.IResourceForEditorInputFactory;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;
import org.eclipse.xtext.ui.editor.occurrences.IOccurrenceComputer;
import org.eclipse.xtext.ui.editor.outline.actions.IOutlineContribution;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;
import org.eclipse.xtext.ui.editor.quickfix.ISimilarityMatcher;
import org.eclipse.xtext.ui.editor.quickfix.XtextQuickAssistProcessor;
import org.eclipse.xtext.ui.editor.reconciler.XtextDocumentReconcileStrategy;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.eclipse.xtext.ui.refactoring.ILinkedPositionGroupCalculator;
import org.eclipse.xtext.ui.refactoring.impl.AbstractRenameProcessor;
import org.eclipse.xtext.ui.refactoring.impl.IRefactoringDocument;
import org.eclipse.xtext.ui.refactoring.ui.SyncUtil;
import org.eclipse.xtext.ui.refactoring2.ChangeConverter;
import org.eclipse.xtext.ui.refactoring2.rename.ISimpleNameProvider;
import org.eclipse.xtext.ui.resource.IResourceUIServiceProvider;
import org.eclipse.xtext.ui.shared.Access;
import org.eclipse.xtext.ui.validation.AbstractValidatorConfigurationBlock;
import org.eclipse.xtext.ui.validation.IResourceUIValidatorExtension;
import org.eclipse.xtext.ui.validation.MarkerTypeProvider;

import com.google.inject.Binder;
import com.google.inject.Provider;
import com.google.inject.name.Names;

/**
 * Use this class to register components to be used within the Eclipse IDE.
 */
@SuppressWarnings({ "restriction", "static-method", "java:S100" })
public class STAlgorithmUiModule extends AbstractSTAlgorithmUiModule {

	public STAlgorithmUiModule(final AbstractUIPlugin plugin) {
		super(plugin);
	}

	public Class<? extends IContainer.Manager> bindIContainer$Manager() {
		return StateBasedContainerManager.class;
	}

	@Override
	public Provider<? extends IAllContainersState> provideIAllContainersState() {
		return Access.getWorkspaceProjectsState();
	}

	public Class<? extends XtextDocument> bindXtextDocument() {
		return STAlgorithmDocument.class;
	}

	public Class<? extends XtextDocumentProvider> bindXtextDocumentProvider() {
		return STCoreDocumentProvider.class;
	}

	public Class<? extends STCoreDocumentPartitioner> bindSTCoreDocumentPartitioner() {
		return STAlgorithmDocumentPartitioner.class;
	}

	public void configureSTCoreCleanupEditorCallback(final Binder binder) {
		binder.bind(IXtextEditorCallback.class).annotatedWith(Names.named("STCoreCleanupEditorCallback")) //$NON-NLS-1$
				.to(STCoreCleanupEditorCallback.class);
		binder.bind(IPreferenceStoreInitializer.class).annotatedWith(Names.named("saveActionsInitializer")) //$NON-NLS-1$
				.to(STCoreSaveActionsPreferences.Initializer.class);
	}

	@Override
	public Class<? extends IResourceForEditorInputFactory> bindIResourceForEditorInputFactory() {
		return STCoreResourceForIEditorInputFactory.class;
	}

	public Class<? extends XtextDocumentReconcileStrategy> bindXtextDocumentReconcileStrategy() {
		return STCoreDocumentReconcileStrategy.class;
	}

	public Class<? extends LibraryElementChangeAdapterFilter> bindLibraryElementChangeAdapterFilter() {
		return STAlgorithmDocumentUpdaterChangeAdapterFilter.class;
	}

	public Class<? extends IEObjectHoverDocumentationProvider> bindIEObjectHoverDocumentationProvider() {
		return STCoreHoverDocumentationProvider.class;
	}

	public Class<? extends IEObjectHoverProvider> bindIEObjectHoverProvider() {
		return STCoreHoverProvider.class;
	}

	public void configureIEObjectDocumentationProvider(final Binder binder) {
		binder.bindConstant().annotatedWith(Names.named(AbstractMultiLineCommentProvider.START_TAG)).to("[/(]\\*\\*?"); //$NON-NLS-1$
		binder.bindConstant().annotatedWith(Names.named(AbstractMultiLineCommentProvider.END_TAG)).to("\\*[/)]"); //$NON-NLS-1$
	}

	public Class<? extends IRefactoringDocument.Provider> bindIRefactoringDocument$Provider() {
		return STCoreRefactoringDocumentProvider.class;
	}

	public void configureCodeMinings(final Binder binder) {
		binder.bind(IPreferenceStoreInitializer.class).annotatedWith(Names.named("codeMiningInitializer")) //$NON-NLS-1$
				.to(STCoreCodeMiningPreferences.Initializer.class);
	}

	public Class<? extends DefaultAntlrTokenToAttributeIdMapper> bindDefaultAntlrTokenToAttributeIdMapper() {
		return STCoreAntlrTokenToAttributeIdMapper.class;
	}

	public Class<? extends IHighlightingConfiguration> bindIHighlightingConfiguration() {
		return STCoreHighlightingConfiguration.class;
	}

	public Class<? extends ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator() {
		return STCoreSemanticHighlightingCalculator.class;
	}

	public Class<? extends ISimilarityMatcher> bindISimilarityMatcher() {
		return CaseInsensitiveSimilarityMatcher.class;
	}

	public Class<? extends IHyperlinkHelper> bindIHyperlinkHelper() {
		return STAlgorithmHyperlinkHelper.class;
	}

	@Override
	public void configureLanguageSpecificURIEditorOpener(final Binder binder) {
		if (PlatformUI.isWorkbenchRunning()) {
			binder.bind(IURIEditorOpener.class).annotatedWith(LanguageSpecific.class).to(STCoreURIEditorOpener.class);
		}
	}

	public Class<? extends IOccurrenceComputer> bindIOccurrenceComputer() {
		return STCoreOccurrenceComputer.class;
	}

	public Class<? extends IContentProposalPriorities> bindIContentProposalPriorities() {
		return STCoreContentProposalPriorities.class;
	}

	public Class<? extends XtextQuickAssistProcessor> bindXtextQuickAssistProcessor() {
		return STCoreQuickAssistProcessor.class;
	}

	public Class<? extends XtextSourceViewer.Factory> bindXtextSourceViewer$Factory() {
		return STCoreSourceViewerFactory.class;
	}

	public Class<? extends EmbeddedEditorActions.Factory> bindEmbeddedEditorActions$Factory() {
		return STAlgorithmEmbeddedEditorActions.Factory.class;
	}

	public void configureEditor(final Binder binder) {
		binder.bind(IPreferenceStoreInitializer.class).annotatedWith(Names.named("editorInitializer")) //$NON-NLS-1$
				.to(STCoreEditorPreferences.Initializer.class);
	}

	public void configureKeyBindingScope(final Binder binder) {
		binder.bindConstant().annotatedWith(Names.named(XtextEditor.KEY_BINDING_SCOPE))
				.to("org.eclipse.fordiac.ide.structuredtextcore.ui.STCoreEditorScope"); //$NON-NLS-1$
	}

	public Class<? extends ExtractCallableRefactoring> bindExtractCallableRefactoring() {
		return ExtractMethodRefactoring.class;
	}

	public void configureContentAssist(final Binder binder) {
		binder.bind(IPreferenceStoreInitializer.class).annotatedWith(Names.named("contentAssistInitializer")) //$NON-NLS-1$
				.to(STCoreContentAssistPreferences.Initializer.class);
		binder.bind(String.class)
				.annotatedWith(Names.named(XtextContentAssistProcessor.COMPLETION_AUTO_ACTIVATION_CHARS))
				.toProvider(STCoreContentAssistPreferences.CompletionAutoActivationCharsProvider.class);
	}

	public Class<? extends AbstractJavaBasedContentProposalProvider.ReferenceProposalCreator> bindAbstractJavaBasedContentProposalProvider$ReferenceProposalCreator() {
		return STAlgorithmReferenceProposalCreator.class;
	}

	@Override
	public Class<? extends PrefixMatcher> bindPrefixMatcher() {
		return STCorePrefixMatcher.class;
	}

	public Class<? extends IResourceUIServiceProvider> bindIResourceUIServiceProvider() {
		return STCoreResourceUIServiceProvider.class;
	}

	public Class<? extends MarkerCreator> bindMarkerCreator() {
		return STCoreMarkerCreator.class;
	}

	@Override
	public Class<? extends MarkerTypeProvider> bindMarkerTypeProvider() {
		return STCoreMarkerTypeProvider.class;
	}

	public Class<? extends IResourceUIValidatorExtension> bindIResourceUIValidatorExtension() {
		return STCoreResourceUIValidatorExtension.class;
	}

	@Override
	public Class<? extends AbstractRenameProcessor> bindAbstractRenameProcessor() {
		return STCoreRenameElementProcessor.class;
	}

	@Override
	public Class<? extends ILinkedPositionGroupCalculator> bindILinkedPositionGroupCalculator() {
		return STCoreLinkedPositionGroupCalculator.class;
	}

	public Class<? extends ISimpleNameProvider> bindISimpleNameProvider() {
		return STCoreSimpleNameProvider.class;
	}

	@Override
	public Class<? extends IRenameStrategy2> bindIRenameStrategy2() {
		return STCoreRenameStrategy.class;
	}

	public Class<? extends IRenameNameValidator> bindIRenameNameValidator() {
		return STCoreRenameNameValidator.class;
	}

	public Class<? extends IChangeSerializer> bindIChangeSerializer() {
		return STCoreChangeSerializer.class;
	}

	public Class<? extends ChangeConverter.Factory> bindChangeConverter$Factory() {
		return STCoreChangeConverter.Factory.class;
	}

	public Class<? extends RecordingXtextResourceUpdater> bindRecordingXtextResourceUpdater() {
		return STCoreRecordingXtextResourceUpdater.class;
	}

	public Class<? extends RelatedXtextResourceUpdater> bindRelatedXtextResourceUpdater() {
		return STCoreRelatedXtextResourceUpdater.class;
	}

	public Class<? extends RelatedEmfResourceUpdater> bindRelatedEmfResourceUpdater() {
		return STCoreRelatedEmfResourceUpdater.class;
	}

	public Class<? extends ReferenceUpdater> bindReferenceUpdater() {
		return STCoreReferenceUpdater.class;
	}

	public Class<? extends SyncUtil> bindSyncUtil() {
		return STCoreSyncUtil.class;
	}

	public Class<? extends ResourceLifecycleManager> bindResourceLifecycleManager() {
		return STCoreResourceLifecycleManager.class;
	}

	@Override
	public Class<? extends IWhitespaceInformationProvider> bindIWhitespaceInformationProvider() {
		return STCoreWhitespaceInformationProvider.class;
	}

	public Class<? extends RegionDiffFormatter> bindRegionDiffFormatter() {
		return STCoreRegionDiffFormatter.class;
	}

	@Override
	public Class<? extends IContentOutlinePage> bindIContentOutlinePage() {
		return STCoreOutlinePage.class;
	}

	public void configureFilterHeadingsContribution(final Binder binder) {
		binder.bind(IOutlineContribution.class).annotatedWith(Names.named("FilterHeadingsContribution")) //$NON-NLS-1$
				.to(FilterHeadingsContribution.class);
	}

	public void configureOutlineTreeContribution(final Binder binder) {
		binder.bind(IOutlineContribution.class).annotatedWith(Names.named("OutlineTreeContribution")) //$NON-NLS-1$
				.to(OutlineTreeContribution.class);
	}

	@Override
	public Class<? extends AbstractValidatorConfigurationBlock> bindAbstractValidatorConfigurationBlock() {
		return STAlgorithmCustomValidatorConfigurationBlock.class;
	}
}
