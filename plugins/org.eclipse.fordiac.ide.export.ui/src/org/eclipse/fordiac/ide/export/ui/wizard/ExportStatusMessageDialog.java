/*******************************************************************************
 * Copyright (c) 2012, 2022 TU Wien ACIN, fortiss GmbH,
 * 							Primetals Technologies Austria Gmbh
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - fixed resize layouting issues of the error status dialog
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.ui.wizard;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.export.ui.Messages;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class ExportStatusMessageDialog extends ErrorDialog {

	private final List<String> warnings;
	private final List<String> errors;
	private final List<String> infos;

	private StyledText text;

	public ExportStatusMessageDialog(final Shell parentShell, final List<String> warnings, final List<String> errors,
			final List<String> infos) {
		super(parentShell, Messages.ExportStatusMessageDialog_4diacIDETypeExportErrors,
				Messages.ExportStatusMessageDialog_DuringTypeExportTheFollowingIssuesHaveBeenIdentified,
				new Status(IStatus.INFO, "org.eclipse.fordiac.ide.export", //$NON-NLS-1$
						MessageFormat.format(Messages.ExportStatusMessageDialog_ExportStatusMessageDialog,
								Integer.valueOf(errors.size()), Integer.valueOf(warnings.size()),
								Integer.valueOf(infos.size()))),
				IStatus.OK | IStatus.INFO | IStatus.WARNING | IStatus.ERROR);

		this.warnings = warnings;
		this.errors = errors;
		this.infos = infos;
	}

	@Override
	protected void createDialogAndButtonArea(final Composite parent) {
		super.createDialogAndButtonArea(parent);
		// ensure that dialog area also fills in both directions. Otherwise the text
		// area is not resized correctly.
		// introduced to fix Bug #579939.
		GridDataFactory.fillDefaults().grab(true, true).applyTo(dialogArea);
	}

	@Override
	protected Control createMessageArea(final Composite parent) {
		final Control retval = super.createMessageArea(parent);

		addPlaceholderLabel(parent);

		text = new StyledText(parent, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(text);
		text.setEditable(false);

		printMessages();

		return retval;
	}

	@SuppressWarnings("unused")
	private static void addPlaceholderLabel(final Composite parent) {
		new Label(parent, SWT.NONE); // simple placeholder label
	}

	/** writes all the messages ( warnings and errors) in the text field. */
	public void printMessages() {
		text.setText(""); //$NON-NLS-1$
		if (!errors.isEmpty()) {
			printMessageList(Messages.ExportStatusMessageDialog_ErrorsNotEmpty, errors);
		}
		if (!warnings.isEmpty()) {
			printMessageList(Messages.ExportStatusMessageDialog_WarningsNotEmpty, warnings);
		}
		if (!infos.isEmpty()) {
			printMessageList(Messages.ExportStatusMessageDialog_InfosNotEmpty, infos);
		}
	}

	private void printMessageList(final String title, final List<String> messages) {
		final StyleRange titleStyle = new StyleRange();
		titleStyle.start = text.getCharCount();
		titleStyle.length = title.length();
		titleStyle.fontStyle = SWT.BOLD;
		text.append(title);
		text.setStyleRange(titleStyle);
		text.append(messages.stream().filter(Objects::nonNull).map("    - "::concat) //$NON-NLS-1$
				.collect(Collectors.joining("\n", "", "\n\n"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
