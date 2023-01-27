/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
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
 *   Hesam Rezaee - registers classes for custom highlighting
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui;

import org.eclipse.fordiac.ide.structuredtextcore.ui.codemining.STCoreCodeMiningPreferences;
import org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist.STCoreContentProposalPriorities;
import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.occurrences.STCoreOccurrenceComputer;
import org.eclipse.fordiac.ide.structuredtextcore.ui.hovering.STCoreCommentDocumentationProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.hovering.STCoreHoverDocumentationProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.hovering.STCoreHoverProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.quickfix.CaseInsensitiveSimilarityMatcher;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreRefactoringDocumentProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.syntaxcoloring.STCoreAntlrTokenToAttributeIdMapper;
import org.eclipse.fordiac.ide.structuredtextcore.ui.syntaxcoloring.STCoreHighlightingConfiguration;
import org.eclipse.fordiac.ide.structuredtextcore.ui.syntaxcoloring.STCoreSemanticHighlightingCalculator;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.document.STFunctionDocument;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.eclipse.xtext.documentation.impl.AbstractMultiLineCommentProvider;
import org.eclipse.xtext.ide.editor.syntaxcoloring.ISemanticHighlightingCalculator;
import org.eclipse.xtext.ui.editor.contentassist.IContentProposalPriorities;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.ui.editor.hover.html.IEObjectHoverDocumentationProvider;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.eclipse.xtext.ui.editor.occurrences.IOccurrenceComputer;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;
import org.eclipse.xtext.ui.editor.quickfix.ISimilarityMatcher;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.refactoring.impl.IRefactoringDocument;

import com.google.inject.Binder;
import com.google.inject.name.Names;

/**
 * Use this class to register components to be used within the Eclipse IDE.
 */
@SuppressWarnings("restriction")
public class STFunctionUiModule extends AbstractSTFunctionUiModule {

	public STFunctionUiModule(final AbstractUIPlugin plugin) {
		super(plugin);
	}

	@SuppressWarnings("static-method")
	public Class<? extends XtextDocument> bindXtextDocument() {
		return STFunctionDocument.class;
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

	@SuppressWarnings("static-method")
	public Class<? extends IRefactoringDocument.Provider> bindIRefactoringDocument$Provider() {
		return STCoreRefactoringDocumentProvider.class;
	}

	@SuppressWarnings("static-method")
	public void configureCodeMinings(final Binder binder) {
		binder.bind(IPreferenceStoreInitializer.class).annotatedWith(Names.named("codeMiningInitializer")) //$NON-NLS-1$
				.to(STCoreCodeMiningPreferences.Initializer.class);
	}

	@SuppressWarnings("static-method")
	public Class<? extends IHighlightingConfiguration> bindIHighlightingConfiguration() {
		return STCoreHighlightingConfiguration.class;
	}

	@SuppressWarnings("static-method")
	public Class<? extends ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator() {
		return STCoreSemanticHighlightingCalculator.class;
	}

	@SuppressWarnings("static-method")
	public Class<? extends DefaultAntlrTokenToAttributeIdMapper> bindDefaultAntlrTokenToAttributeIdMapper() {
		return STCoreAntlrTokenToAttributeIdMapper.class;
	}

	@SuppressWarnings("static-method")
	public Class<? extends IEObjectDocumentationProvider> bindIEObjectDocumentationProvider() {
		return STCoreCommentDocumentationProvider.class;
	}

	@SuppressWarnings("static-method")
	public Class<? extends ISimilarityMatcher> bindISimilarityMatcher() {
		return CaseInsensitiveSimilarityMatcher.class;
	}

	@SuppressWarnings("static-method")
	public Class<? extends IOccurrenceComputer> bindIOccurrenceComputer() {
		return STCoreOccurrenceComputer.class;
	}

	@SuppressWarnings("static-method")
	public Class<? extends IContentProposalPriorities> bindIContentProposalPriorities() {
		return STCoreContentProposalPriorities.class;
	}
}
