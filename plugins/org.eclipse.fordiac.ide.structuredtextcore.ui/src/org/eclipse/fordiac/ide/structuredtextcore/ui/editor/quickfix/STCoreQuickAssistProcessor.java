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
package org.eclipse.fordiac.ide.structuredtextcore.ui.editor.quickfix;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolution;
import org.eclipse.xtext.ui.editor.quickfix.XtextQuickAssistProcessor;
import org.eclipse.xtext.validation.Issue;

public class STCoreQuickAssistProcessor extends XtextQuickAssistProcessor {

	@Override
	public Iterable<IssueResolution> getResolutions(final Issue issue, final IXtextDocument document) {
		return getResolutionProvider().getResolutions(issue).stream().map(input -> new IssueResolution(input.getLabel(),
				input.getDescription(), input.getImage(), new IModificationContext() {
					@Override
					public IXtextDocument getXtextDocument(final URI uri) {
						if (uri.trimFragment().equals(document.getResourceURI())) {
							return document;
						}
						return input.getModificationContext().getXtextDocument(uri);
					}

					@Override
					public IXtextDocument getXtextDocument() {
						return document;
					}
				}, input.getModification(), input.getRelevance())).toList();
	}
}
