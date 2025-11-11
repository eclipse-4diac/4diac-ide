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
 *   Felix Schmid - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.issuereport;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.fordiac.ide.Messages;
import org.eclipse.fordiac.ide.issuereport.PreferenceConstants.ReportDestination;
import org.eclipse.fordiac.ide.issuereport.PreferenceConstants.ReportMode;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class GitIssueReportingPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	// UI elements
	private Button noReportButton;
	private Button promptReportButton;
	private Button autoReportButton;
	private Group group;
	private Button gitHubManualButton;
	private Button gitLabButton;
	private Text gitLabURLText;
	private Text gitLabProjectPathText;
	private Text gitLabTokenText;
	private Button gitHubButton;
	private Text gitHubURLText;
	private Text gitHubProjectPathText;
	private Text gitHubTokenText;

	// preferences
	private ReportMode reportMode;
	private ReportDestination reportDestination;

	public GitIssueReportingPage() {
		super(GRID);
		setPreferenceStore(
				new ScopedPreferenceStore(InstanceScope.INSTANCE, PreferenceConstants.P_BUG_REPORT_PREFERENCE_ID));
	}

	@Override
	public void init(final IWorkbench workbench) {
		reportMode = PreferenceConstants.getReportMode();
		reportDestination = PreferenceConstants.getReportDestination();
		getPreferenceStore().setDefault(PreferenceConstants.P_BUG_REPORT_GITHUB_URL, "https://api.github.com/"); //$NON-NLS-1$
	}

	@Override
	protected void performDefaults() {
		noReportButton.setSelection(false);
		promptReportButton.setSelection(true);
		autoReportButton.setSelection(false);
		selectReportMode(ReportMode.PROMPT_REPORT);

		reportDestination = ReportDestination.GITHUB_MANUAL;
		gitHubManualButton.setSelection(true);
		gitLabButton.setSelection(false);
		gitHubButton.setSelection(false);
		selectReportDest(ReportDestination.GITHUB_MANUAL);

		final IPreferenceStore store = getPreferenceStore();
		gitLabURLText.setText(store.getDefaultString(PreferenceConstants.P_BUG_REPORT_GITLAB_URL));
		gitLabProjectPathText.setText(store.getDefaultString(PreferenceConstants.P_BUG_REPORT_GITLAB_PROJECT_PATH));
		gitLabTokenText.setText(store.getDefaultString(PreferenceConstants.P_BUG_REPORT_GITLAB_TOKEN));
		gitHubURLText.setText(store.getDefaultString(PreferenceConstants.P_BUG_REPORT_GITHUB_URL));
		gitHubProjectPathText.setText(store.getDefaultString(PreferenceConstants.P_BUG_REPORT_GITHUB_PROJECT_PATH));
		gitHubTokenText.setText(store.getDefaultString(PreferenceConstants.P_BUG_REPORT_GITHUB_TOKEN));
		super.performDefaults();
	}

	@Override
	public boolean performOk() {
		final IPreferenceStore store = getPreferenceStore();
		store.setValue(PreferenceConstants.P_BUG_REPORT_MODE, reportMode.toString());
		store.setValue(PreferenceConstants.P_BUG_REPORT_DESTINATION, reportDestination.toString());
		store.setValue(PreferenceConstants.P_BUG_REPORT_GITLAB_URL, gitLabURLText.getText());
		store.setValue(PreferenceConstants.P_BUG_REPORT_GITLAB_PROJECT_PATH, gitLabProjectPathText.getText());
		store.setValue(PreferenceConstants.P_BUG_REPORT_GITLAB_TOKEN, gitLabTokenText.getText());
		store.setValue(PreferenceConstants.P_BUG_REPORT_GITHUB_URL, gitHubURLText.getText());
		store.setValue(PreferenceConstants.P_BUG_REPORT_GITHUB_PROJECT_PATH, gitHubProjectPathText.getText());
		store.setValue(PreferenceConstants.P_BUG_REPORT_GITHUB_TOKEN, gitHubTokenText.getText());
		return super.performOk();
	}

	@Override
	protected Control createContents(final Composite parent) {
		final Composite composite = createComposite(parent);
		createReportModeGroup(composite);
		createReportDestinationGroup(composite);
		return composite;
	}

	private void createReportModeGroup(final Composite composite) {
		final Group buttonComposite = new Group(composite, SWT.LEFT);
		final GridLayout layout = new GridLayout();
		buttonComposite.setLayout(layout);
		final GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		buttonComposite.setLayoutData(data);
		buttonComposite.setText(Messages.IssueReporting_ModeGroupLabel);

		noReportButton = createRadioButton(buttonComposite, Messages.IssueReporting_NoReport);
		noReportButton.addSelectionListener(widgetSelectedAdapter(e -> selectReportMode(ReportMode.NO_REPORT)));
		noReportButton.setSelection(reportMode == ReportMode.NO_REPORT);

		promptReportButton = createRadioButton(buttonComposite, Messages.IssueReporting_PromptReport);
		promptReportButton.addSelectionListener(widgetSelectedAdapter(e -> selectReportMode(ReportMode.PROMPT_REPORT)));
		promptReportButton.setSelection(reportMode == ReportMode.PROMPT_REPORT);

		autoReportButton = createRadioButton(buttonComposite, Messages.IssueReporting_AutoReport);
		autoReportButton.addSelectionListener(widgetSelectedAdapter(e -> selectReportMode(ReportMode.AUTO_REPORT)));
		autoReportButton.setSelection(reportMode == ReportMode.AUTO_REPORT);
	}

	private void createReportDestinationGroup(final Composite composite) {
		group = new Group(composite, SWT.LEFT);
		final GridLayout layout = new GridLayout(2, false);
		group.setLayout(layout);
		final GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		group.setLayoutData(data);
		group.setText(Messages.IssueReporting_DestinationGroupLabel);
		group.setVisible(reportMode != ReportMode.NO_REPORT);

		gitHubManualButton = createDestinationButton(group, Messages.IssueReporting_GitHub_Manual,
				ReportDestination.GITHUB_MANUAL);

		final boolean isGitHub = reportDestination == ReportDestination.GITHUB;
		gitHubButton = createDestinationButton(group, Messages.IssueReporting_GitHub, ReportDestination.GITHUB);
		createFieldLabel(group, Messages.IssueReporting_URLInput);
		gitHubURLText = createFieldInput(group, isGitHub, PreferenceConstants.getReportGitHubURL());
		createFieldLabel(group, Messages.IssueReporting_ProjectPathInput);
		gitHubProjectPathText = createFieldInput(group, isGitHub, PreferenceConstants.getReportGitHubProjectPath());
		gitHubProjectPathText.setToolTipText(Messages.IssueReporting_ProjectPathToolTip);
		createFieldLabel(group, Messages.IssueReporting_TokenInput);
		gitHubTokenText = createFieldInput(group, isGitHub, PreferenceConstants.getReportGitHubToken());
		gitHubTokenText.setEchoChar('*');

		final boolean isGitLab = reportDestination == ReportDestination.GITLAB;
		gitLabButton = createDestinationButton(group, Messages.IssueReporting_GitLab, ReportDestination.GITLAB);
		createFieldLabel(group, Messages.IssueReporting_URLInput);
		gitLabURLText = createFieldInput(group, isGitLab, PreferenceConstants.getReportGitLabURL());
		createFieldLabel(group, Messages.IssueReporting_ProjectPathInput);
		gitLabProjectPathText = createFieldInput(group, isGitLab, PreferenceConstants.getReportGitLabProjectPath());
		gitLabProjectPathText.setToolTipText(Messages.IssueReporting_ProjectPathToolTip);
		createFieldLabel(group, Messages.IssueReporting_TokenInput);
		gitLabTokenText = createFieldInput(group, isGitLab, PreferenceConstants.getReportGitLabToken());
		gitLabTokenText.setEchoChar('*');
	}

	private static Composite createComposite(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		return composite;
	}

	private Button createDestinationButton(final Composite parent, final String label,
			final ReportDestination reportDest) {
		final Button button = createRadioButton(parent, label);
		button.addSelectionListener(widgetSelectedAdapter(e -> selectReportDest(reportDest)));
		button.setSelection(reportDestination == reportDest);
		final GridData data = new GridData();
		data.horizontalSpan = 2;
		button.setLayoutData(data);
		return button;
	}

	private static Button createRadioButton(final Composite parent, final String label) {
		final Button button = new Button(parent, SWT.RADIO | SWT.LEFT);
		button.setText(label);
		return button;
	}

	private static void createFieldLabel(final Composite composite, final String text) {
		final Label label = new Label(composite, SWT.NONE);
		label.setText(text);
		final GridData data = new GridData();
		data.horizontalIndent = LayoutConstants.getIndent();
		label.setLayoutData(data);
	}

	private static Text createFieldInput(final Composite composite, final boolean enabled, final String initialText) {
		final Text text = new Text(composite, SWT.SINGLE | SWT.BORDER);
		text.setEnabled(enabled);
		text.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
		if (initialText != null) {
			text.setText(initialText);
		}
		return text;
	}

	private void selectReportMode(final ReportMode mode) {
		reportMode = mode;
		group.setVisible(mode != ReportMode.NO_REPORT);
	}

	private void selectReportDest(final ReportDestination dest) {
		reportDestination = dest;

		final boolean isGitLab = dest == ReportDestination.GITLAB;
		gitLabURLText.setEnabled(isGitLab);
		gitLabProjectPathText.setEnabled(isGitLab);
		gitLabTokenText.setEnabled(isGitLab);

		final boolean isGitHub = dest == ReportDestination.GITHUB;
		gitHubURLText.setEnabled(isGitHub);
		gitHubProjectPathText.setEnabled(isGitHub);
		gitHubTokenText.setEnabled(isGitHub);
	}

	@Override
	protected void createFieldEditors() {
		// No field editors used, only custom controls
	}
}
