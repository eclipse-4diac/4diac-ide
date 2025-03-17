/*******************************************************************************
 * Copyright (c) 2025 Johannes Kepler Universität Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.core.runtime.Assert;
import org.eclipse.fordiac.ide.ui.utils.ContractspecResourceProvider;
import org.eclipse.jface.text.contentassist.ContentAssistEvent;
import org.eclipse.jface.text.contentassist.ICompletionListener;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditor;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorModelAccess;

@SuppressWarnings("restriction")
public class ContractCellEditor extends CellEditor {

	private final InstanceContract contract;
	private EmbeddedEditor editor;
	private EmbeddedEditorModelAccess modelAccess;
	private boolean popupOpen = false;

	public ContractCellEditor(final Composite composite, final InstanceContract contract) {
		this.contract = contract;
		setStyle(SWT.MULTI | SWT.WRAP);
		create(composite);
	}

	public ContractCellEditor(final Composite composite, final int style, final InstanceContract contract) {
		this.contract = contract;
		setStyle(style);
		create(composite);
	}

	@Override
	protected Control createControl(final Composite parent) {
		editor = ContractspecResourceProvider.getEmbeddedEditorBuilder(contract.getSubApp()).showLineNumbers()
				.withParent(parent);
		modelAccess = editor.createPartialEditor();

		editor.getViewer().getTextWidget().addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(final FocusEvent e) {
				if (popupOpen) {
					return;
				}
				// if a quickfix is applied, we also loose focus but regain it right after
				// so we wait for 100ms and check if we are still not focused before closing
				// if someone knows a better way, please rewrite this...
				Display.getCurrent().timerExec(100, () -> {
					if (!editor.getViewer().getTextWidget().isFocusControl()) {
						if (!contract.getContract().equals(modelAccess.getEditablePart())) {
							valueChanged(true, true);
						}
						ContractCellEditor.this.focusLost();
					}
				});
			}
		});

		// don't loose focus when clicking on auto complete popup
		editor.getViewer().getContentAssistantFacade().addCompletionListener(new ICompletionListener() {
			@Override
			public void selectionChanged(final ICompletionProposal proposal, final boolean smartToggle) {
				// not relevant
			}

			@Override
			public void assistSessionStarted(final ContentAssistEvent event) {
				popupOpen = true;
			}

			@Override
			public void assistSessionEnded(final ContentAssistEvent event) {
				doSetFocus();
				popupOpen = false;
			}
		});
		return editor.getViewer().getControl();
	}

	@Override
	protected Object doGetValue() {
		return modelAccess.getEditablePart();
	}

	@Override
	protected void doSetValue(final Object value) {
		Assert.isTrue(value instanceof String);
		modelAccess.updateModel((String) value);
	}

	@Override
	protected void doSetFocus() {
		editor.getViewer().getTextWidget().setFocus();
	}

	@Override
	public boolean isUndoEnabled() {
		return true;
	}

	@Override
	public void performUndo() {
		editor.getViewer().getUndoManager().undo();
	}

	@Override
	public boolean isRedoEnabled() {
		return true;
	}

	@Override
	public void performRedo() {
		editor.getViewer().getUndoManager().redo();
	}
}
