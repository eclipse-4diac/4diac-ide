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
package org.eclipse.fordiac.ide.contracts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.ui.utils.ContractScanner;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class DefineContractDecisionDialog extends MessageDialog {

	private final List<String> names;
	private final List<String> templates;
	private int selectedIdx = -1;

	public DefineContractDecisionDialog(final Shell parentShell, final List<String> names,
			final List<String> templates) {
		super(parentShell, Messages.DefineContractDecisionDialog_Title, null,
				Messages.DefineContractDecisionDialog_Info, MessageDialog.INFORMATION, 0);

		if (names.size() != templates.size()) {
			throw new IllegalArgumentException();
		}
		this.names = names;
		this.templates = templates;
	}

	public String getTemplate() {
		if (selectedIdx >= 0 && selectedIdx < templates.size()) {
			return templates.get(selectedIdx);
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		final Group group = new Group(parent, 0);
		group.setLayout(new GridLayout(3, false));

		for (int i = 0; i < names.size(); i++) {
			final Label lbl = new Label(group, 0);
			lbl.setText(names.get(i) + ":"); //$NON-NLS-1$
			lbl.setAlignment(SWT.RIGHT);
			lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

			final StyledText txt = new StyledText(group, SWT.READ_ONLY | SWT.SINGLE);
			txt.setText(templates.get(i));
			txt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			txt.setBackground(new Color(255, 255, 255));

			final ContractScanner scan = new ContractScanner(templates.get(i));
			final List<StyleRange> ranges = new ArrayList<>();
			int idx = 0;

			for (final ContractScanner.Token t : scan) {
				final int len = t.value().length();
				final StyleRange range = new StyleRange(idx, len, null, null);
				idx += len;
				range.foreground = switch (t.type()) {
				case COMMENT -> ContractScanner.COMMENT;
				case KEYWORD -> ContractScanner.KEYWORD;
				default -> ContractScanner.NORMAL;
				};
				if (t.type() == ContractScanner.TokenType.KEYWORD) {
					range.fontStyle = SWT.BOLD;
				}
				ranges.add(range);
			}
			txt.setStyleRanges(ranges.toArray(StyleRange[]::new));
			txt.setEnabled(false);

			final int fi = i;
			final Button btn = new Button(group, 0);
			btn.setText(Messages.DefineContractDecisionDialog_Create);
			btn.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
			btn.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					selectedIdx = fi;
					close();
				}

				@Override
				public void widgetDefaultSelected(final SelectionEvent e) {
					// nothing to do
				}
			});
		}
		return dialogArea;
	}
}
