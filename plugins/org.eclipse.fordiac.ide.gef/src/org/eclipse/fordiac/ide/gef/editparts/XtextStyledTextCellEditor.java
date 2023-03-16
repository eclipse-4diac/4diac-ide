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
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded.STAlgorithmEmbeddedEditorUtil;
import org.eclipse.fordiac.ide.ui.providers.SourceViewerColorProvider;
import org.eclipse.fordiac.ide.ui.widget.StyledTextCellEditor;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
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

	/** @see org.eclipse.fordiac.ide.ui.widget.StyledTextCellEditor#StyledTextCellEditor() */
	protected XtextStyledTextCellEditor() {
		super();
	}

	/** @see org.eclipse.fordiac.ide.ui.widget.StyledTextCellEditor#StyledTextCellEditor(Composite) */
	protected XtextStyledTextCellEditor(final Composite parent) {
		super(parent);
	}

	/** @see org.eclipse.fordiac.ide.ui.widget.StyledTextCellEditor#StyledTextCellEditor(Composite, int) */
	protected XtextStyledTextCellEditor(final Composite parent, final int style) {
		super(parent, style);
	}

	@Override
	protected StyledText createStyledText(final Composite parent) {
		embeddedEditor = STAlgorithmEmbeddedEditorUtil.getEmbeddedEditorFactory()
				.newEditor(createEditedResourceProvider()).withStyle(getStyle()).withParent(parent);
		embeddedEditor.createPartialEditor();
		SourceViewerColorProvider.initializeSourceViewerColors(embeddedEditor.getViewer());
		embeddedEditor.getViewer().getDocument().addDocumentListener(new IDocumentListener() {

			@Override
			public void documentChanged(final DocumentEvent event) {
				editOccured(null);
			}

			@Override
			public void documentAboutToBeChanged(final DocumentEvent event) {
				// ignore
			}
		});
		embeddedEditor.getViewer().getContentAssistantFacade().addCompletionListener(new ICompletionListener() {

			@Override
			public void selectionChanged(final ICompletionProposal proposal, final boolean smartToggle) {
				// ignore
			}

			@Override
			public void assistSessionStarted(final ContentAssistEvent event) {
				proposalPopupOpen = true;
			}

			@Override
			public void assistSessionEnded(final ContentAssistEvent event) {
				proposalPopupOpen = false;
			}
		});
		return (StyledText) embeddedEditor.getViewer().getControl();
	}

	protected abstract IEditedResourceProvider createEditedResourceProvider();

	@Override
	protected void doSetValue(final Object value) {
		super.doSetValue(value);
		embeddedEditor.getViewer().getUndoManager().reset();
	}

	@Override
	public boolean isUndoEnabled() {
		return true;
	}

	@Override
	public void performUndo() {
		embeddedEditor.getViewer().getUndoManager().undo();
	}

	@Override
	public boolean isRedoEnabled() {
		return true;
	}

	@Override
	public void performRedo() {
		embeddedEditor.getViewer().getUndoManager().redo();
	}

	@Override
	protected boolean dependsOnExternalFocusListener() {
		return false;
	}

	@Override
	protected boolean isProposalPopupOpen() {
		return proposalPopupOpen;
	}
}
