/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.widgets;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorModelAccess;

@SuppressWarnings("restriction")
public class ContractEditorDialog extends MessageDialog {

	private String contract;
	private EmbeddedEditorModelAccess editor;
	private final String select;

	public ContractEditorDialog(final Shell shell, final String contract) {
		super(shell, Messages.ContractEditor_Title, null, "", MessageDialog.CONFIRM, 0, Messages.ContractEditor_OK); //$NON-NLS-1$
		this.contract = contract;
		select = null;
	}

	public ContractEditorDialog(final Shell shell, final String contract, final String info, final String select) {
		super(shell, Messages.ContractEditor_Title, null, info, MessageDialog.INFORMATION, 0,
				Messages.ContractEditor_OK);
		this.contract = contract;
		this.select = select;
	}

	public String getSimplifiedContractRule() {
		return simplifyContract(contract);
	}

	public String getContractRule() {
		return contract;
	}

	private static String simplifyContract(final String contract) {
		// TODO: very crude approach for now to remove default offset
		// can be replaced with more general method once new model is in place
		final String defaultOffset = "with offset 0ms"; //$NON-NLS-1$
		if (contract.endsWith(defaultOffset)) {
			return contract.substring(0, contract.length() - defaultOffset.length());
		}
		return contract;
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		final var handle = ContractspecResourceProvider.getEmbeddedEditorBuilder().showLineNumbers().withParent(parent);
		editor = handle.createPartialEditor();
		editor.updateModel(contract);

		// pre-select the given selection if possible
		if (select != null) {
			final int startIdx = contract.indexOf(select);

			if (startIdx > 0) {
				handle.getViewer().setSelectedRange(startIdx, select.length());
			}
		}
		return dialogArea;
	}

	@Override
	protected void buttonPressed(final int buttonId) {
		contract = editor.getEditablePart();
		super.buttonPressed(buttonId);
	}
}
