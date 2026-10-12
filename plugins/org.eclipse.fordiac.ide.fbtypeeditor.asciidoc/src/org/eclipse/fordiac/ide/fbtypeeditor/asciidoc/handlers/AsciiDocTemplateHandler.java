/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Andrea Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.templates.DocumentTemplateContext;
import org.eclipse.jface.text.templates.GlobalTemplateVariables;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.mylyn.internal.wikitext.ui.WikiTextUiPlugin;
import org.eclipse.mylyn.internal.wikitext.ui.editor.assist.Templates;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

public class AsciiDocTemplateHandler extends AbstractHandler {

	private static final String PARAM_TEMPLATE_NAME = "org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.commands.template.name"; //$NON-NLS-1$

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final String templateName = event.getParameter(PARAM_TEMPLATE_NAME);
		if (templateName == null || templateName.isEmpty()) {
			throw new ExecutionException("Template name parameter is missing."); //$NON-NLS-1$
		}

		final IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
		if (activeEditor == null) {
			return null;
		}

		final ITextEditor textEditor = Adapters.adapt(activeEditor, ITextEditor.class);
		if (textEditor == null) {
			return null;
		}

		final Template template = findTemplate(templateName);
		if (template == null) {
			throw new ExecutionException("Template '" + templateName + "' not found."); //$NON-NLS-1$//$NON-NLS-2$
		}

		final IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
		final ISelection selection = textEditor.getSelectionProvider().getSelection();
		final ITextViewer viewer = Adapters.adapt(textEditor, ITextViewer.class);

		if (document != null && selection instanceof final ITextSelection textSelection && viewer != null) {
			final int offset = textSelection.getOffset();
			final int length = textSelection.getLength();

			final TemplateContextType type = new TemplateContextType(template.getContextTypeId());
			type.addResolver(new GlobalTemplateVariables.Cursor());
			type.addResolver(new GlobalTemplateVariables.WordSelection());
			type.addResolver(new GlobalTemplateVariables.LineSelection());

			final DocumentTemplateContext ctx = new DocumentTemplateContext(type, document, offset, length);
			try {
				final String selectedText = length > 0 ? document.get(offset, length) : ""; //$NON-NLS-1$
				ctx.setVariable("selection", selectedText); //$NON-NLS-1$
				ctx.setVariable("text", selectedText); //$NON-NLS-1$
			} catch (final BadLocationException e) {
				throw new ExecutionException("Bad selection location", e); //$NON-NLS-1$
			}

			final Region region = new Region(offset, length);
			final TemplateProposal proposal = new TemplateProposal(template, ctx, region, null);

			// Apply the template proposal using the resolved text viewer
			proposal.apply(viewer, (char) 0, 0, offset);
		}
		return null;
	}

	@SuppressWarnings("restriction")
	private static Template findTemplate(final String name) {
		final Templates templates = WikiTextUiPlugin.getDefault().getTemplates().get("AsciiDoc"); //$NON-NLS-1$
		if (templates != null) {
			for (final Template t : templates.getTemplate()) {
				if (t.getName().equals(name)) {
					return t;
				}
			}
		}
		return null;
	}

}
