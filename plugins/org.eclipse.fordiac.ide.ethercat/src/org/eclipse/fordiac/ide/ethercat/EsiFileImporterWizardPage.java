/*******************************************************************************
 * Copyright (c) 2026 Sichuan Qunyuan Technology Co., Ltd.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sichuan Qunyuan Technology Co., Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ethercat;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class EsiFileImporterWizardPage extends WizardPage {
	private String selectedEsiFileName;

	protected EsiFileImporterWizardPage(final String pageName) {
		super(pageName);
		setDescription(Messages.EsiFileImporterWizardPage_Description);
		setTitle(Messages.EsiFileImporterWizardPage_Title);
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NULL);
		composite.setFont(parent.getFont());
		initializeDialogUnits(parent);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		createEsiFileSourceGroup(composite);
		setPageComplete(true);
		setControl(composite);
	}

	private void createEsiFileSourceGroup(final Composite composite) {
		new GridData(SWT.FILL, SWT.CENTER, true, false);
		final Label esiFileLabel = new Label(composite, SWT.NONE);
		esiFileLabel.setText(Messages.EsiFileImporterWizardPage_SelectEsiFile);

		final Text esiFileText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		esiFileText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Button browserButton = new Button(composite, SWT.PUSH);
		browserButton.setText(Messages.EsiFileImporterWizardPage_Browse);
		browserButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				selectedEsiFileName = null;
				final FileDialog dialog = new FileDialog(getShell(), SWT.OPEN | SWT.SINGLE);
				dialog.setFilterExtensions(new String[] {"*.xml"}); //$NON-NLS-1$
				final String result = dialog.open();
				if(result != null) {
					selectedEsiFileName = dialog.getFilterPath() + "/" + dialog.getFileName(); //$NON-NLS-1$
					esiFileText.setText(selectedEsiFileName);
				}
			}
		});
	}

	public String getEsiFilePath() {
		return selectedEsiFileName;
	}
}
