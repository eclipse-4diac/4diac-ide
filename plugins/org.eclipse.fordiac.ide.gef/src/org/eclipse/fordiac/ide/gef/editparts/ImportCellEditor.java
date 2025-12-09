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

import java.util.function.Supplier;

import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.widgets.ImportSelectionProposalProvider;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.fieldassist.ContentAssistCommandAdapter;

public class ImportCellEditor extends TextCellEditor {
	private final Supplier<TypeLibrary> supplier;
	private ContentAssistCommandAdapter contentProposalAdapter;

	public ImportCellEditor(final Composite parent, final Supplier<TypeLibrary> supplier) {
		super(parent);
		this.supplier = supplier;
		configureContentProposal();
	}

	protected void configureContentProposal() {
		contentProposalAdapter = new ContentAssistCommandAdapter(text, new TextContentAdapter(),
				new ImportSelectionProposalProvider(supplier), null, null, true);
		contentProposalAdapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
	}

	@Override
	protected void keyReleaseOccured(final KeyEvent keyEvent) {
		if (!contentProposalAdapter.isProposalPopupOpen()) {
			super.keyReleaseOccured(keyEvent);
		}
	}

	@Override
	protected void focusLost() {
		if (!contentProposalAdapter.isProposalPopupOpen()) {
			super.focusLost();
		}
	}

	@Override
	protected boolean dependsOnExternalFocusListener() {
		return false;
	}
}
