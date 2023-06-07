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
package org.eclipse.fordiac.ide.gef.nat;

import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded.STAlgorithmEmbeddedEditorUtil;
import org.eclipse.fordiac.ide.ui.providers.SourceViewerColorProvider;
import org.eclipse.jface.text.contentassist.ContentAssistEvent;
import org.eclipse.jface.text.contentassist.ICompletionListener;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditor;
import org.eclipse.xtext.ui.editor.embedded.IEditedResourceProvider;

@SuppressWarnings("restriction")
public abstract class XtextStyledTextCellEditor extends StyledTextCellEditor {
	private EmbeddedEditor embeddedEditor;
	private boolean proposalPopupOpen;

	protected XtextStyledTextCellEditor() {
		super();
	}

	protected XtextStyledTextCellEditor(final boolean moveSelectionOnEnter) {
		super(false, moveSelectionOnEnter);
	}

	@Override
	protected StyledText createStyledText(final Composite parent, final int style) {
		embeddedEditor = STAlgorithmEmbeddedEditorUtil.getEmbeddedEditorFactory()
				.newEditor(createEditedResourceProvider()).withStyle(style).withParent(parent);
		embeddedEditor.createPartialEditor();
		SourceViewerColorProvider.initializeSourceViewerColors(embeddedEditor.getViewer());
		embeddedEditor.getViewer().getContentAssistantFacade().addCompletionListener(new ICompletionListener() {

			@Override
			public void selectionChanged(final ICompletionProposal proposal, final boolean smartToggle) {
				// ignore
			}

			@Override
			public void assistSessionStarted(final ContentAssistEvent event) {
				proposalPopupOpen = true;
				if (XtextStyledTextCellEditor.this.focusListener instanceof final InlineFocusListener inlineFocusListener) {
					inlineFocusListener.handleFocusChanges = false;
				}
			}

			@Override
			public void assistSessionEnded(final ContentAssistEvent event) {
				proposalPopupOpen = false;
				if (XtextStyledTextCellEditor.this.focusListener instanceof final InlineFocusListener inlineFocusListener) {
					inlineFocusListener.handleFocusChanges = true;
				}
			}
		});
		return (StyledText) embeddedEditor.getViewer().getControl();
	}

	protected abstract IEditedResourceProvider createEditedResourceProvider();

	@Override
	public void setEditorValue(final Object value) {
		super.setEditorValue(value);
		embeddedEditor.getViewer().getUndoManager().reset();
	}

	@Override
	protected boolean isProposalPopupOpen() {
		return proposalPopupOpen;
	}
}
