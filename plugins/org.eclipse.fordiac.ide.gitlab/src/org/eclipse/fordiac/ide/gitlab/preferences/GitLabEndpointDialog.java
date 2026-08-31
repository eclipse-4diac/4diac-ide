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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gitlab.preferences;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.fordiac.ide.gitlab.Messages;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

final class GitLabEndpointDialog extends TitleAreaDialog {

	private final GitLabEndpoint initial;
	private final Set<String> reservedNames;

	private Text nameText;
	private Text urlText;
	private Text tokenText;

	private GitLabEndpoint result;

	GitLabEndpointDialog(final Shell parentShell, final GitLabEndpoint initial, final List<String> reservedNames) {
		super(parentShell);
		this.initial = initial;
		this.reservedNames = new HashSet<>(reservedNames != null ? reservedNames : List.of());
	}

	GitLabEndpoint getResult() {
		return result;
	}

	@Override
	public void create() {
		super.create();
		setTitle(initial == null ? Messages.GitLabEndpointDialog_add : Messages.GitLabEndpointDialog_edit);
		setMessage(Messages.GitLabEndpointDialog_configure, IMessageProvider.INFORMATION);
		validate();
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite area = (Composite) super.createDialogArea(parent);
		final Composite root = new Composite(area, SWT.NONE);
		root.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		root.setLayout(new GridLayout(2, false));

		new Label(root, SWT.NONE).setText(Messages.GitLabEndpointDialog_name);
		nameText = new Text(root, SWT.BORDER);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		new Label(root, SWT.NONE).setText(Messages.GitLabEndpointDialog_url);
		urlText = new Text(root, SWT.BORDER);
		urlText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		new Label(root, SWT.NONE).setText(Messages.GitLabEndpointDialog_token);
		tokenText = new Text(root, SWT.BORDER | SWT.PASSWORD);
		tokenText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		tokenText.setEchoChar('*');

		if (initial != null) {
			nameText.setText(Objects.toString(initial.name(), "")); //$NON-NLS-1$
			urlText.setText(Objects.toString(initial.url(), "")); //$NON-NLS-1$
			tokenText.setText(Objects.toString(initial.token(), "")); //$NON-NLS-1$
		}

		nameText.addModifyListener(_ -> validate());
		urlText.addModifyListener(_ -> validate());
		tokenText.addModifyListener(_ -> validate());

		return area;
	}

	private void validate() {
		final String name = Objects.toString(nameText != null ? nameText.getText() : "", "").trim(); //$NON-NLS-1$ //$NON-NLS-2$
		final String url = Objects.toString(urlText != null ? urlText.getText() : "", "").trim(); //$NON-NLS-1$ //$NON-NLS-2$
		final String token = Objects.toString(tokenText != null ? tokenText.getText() : "", "").trim(); //$NON-NLS-1$ //$NON-NLS-2$

		String msg = null;
		if (name.isBlank()) {
			msg = Messages.GitLabEndpointDialog_name_not_empty;
		} else if (!GitLabEndpoint.isValidName(name)) {
			msg = Messages.GitLabEndpointDialog_name_invalid_characters;
		} else if (reservedNames.contains(name)) {
			msg = Messages.GitLabEndpointDialog_name_exists;
		} else if (url.isBlank()) {
			msg = Messages.GitLabEndpointDialog_url_not_empty;
		} else if (token.isBlank()) {
			msg = Messages.GitLabEndpointDialog_token_note_empty;
		}

		setErrorMessage(msg);
		if (getButton(OK) != null) {
			getButton(OK).setEnabled(msg == null);
		}
	}

	@Override
	protected void okPressed() {
		result = new GitLabEndpoint(nameText.getText(), urlText.getText(), tokenText.getText());
		super.okPressed();
	}
}
