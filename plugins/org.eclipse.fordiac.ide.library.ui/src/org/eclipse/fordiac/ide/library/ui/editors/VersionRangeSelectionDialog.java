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
 *   Mario Kastner
 *   	- initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.editors;

import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.VersionRange;

public class VersionRangeSelectionDialog extends TitleAreaDialog {

	private Text minVersionText;
	private Text maxVersionText;

	private Button includeMinVersion;
	private Button includeMaxVersion;

	private Label rangePreviewLabel;
	private String versionRange;

	public VersionRangeSelectionDialog(final String versionRange, final Shell parentShell) {
		super(parentShell);
		this.versionRange = versionRange;
	}

	public String getVersionRange() {
		return versionRange;
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		setTitle(Messages.ManifestEditor_VersionRange_Title);
		setMessage(Messages.ManifestEditor_VersionRange_Description);

		final Composite area = (Composite) super.createDialogArea(parent);

		final Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		container.setLayout(new GridLayout(2, true));

		createMinimumGroup(container);
		createMaximumGroup(container);
		createPreviewGroup(container);

		initializeValues();
		addUpdateListeners();
		updatePreview();

		return area;
	}

	private void createMinimumGroup(final Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		group.setText(Messages.ManifestEditor_VersionRange_Min);
		group.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		group.setLayout(new GridLayout());

		minVersionText = new Text(group, SWT.BORDER);
		minVersionText.setMessage("e.g. 1.0.0"); //$NON-NLS-1$
		minVersionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		minVersionText.addVerifyListener(VERSION_VERIFY_LISTENER);

		includeMinVersion = new Button(group, SWT.CHECK);
		includeMinVersion.setText(Messages.ManifestEditor_VersionRange_IncludeInRange);
	}

	private void createMaximumGroup(final Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		group.setText(Messages.ManifestEditor_VersionRange_Max);
		group.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		group.setLayout(new GridLayout());

		maxVersionText = new Text(group, SWT.BORDER);
		maxVersionText.setMessage("e.g. 2.0.0"); //$NON-NLS-1$
		maxVersionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		maxVersionText.addVerifyListener(VERSION_VERIFY_LISTENER);

		includeMaxVersion = new Button(group, SWT.CHECK);
		includeMaxVersion.setText(Messages.ManifestEditor_VersionRange_IncludeInRange);
	}

	private void createPreviewGroup(final Composite parent) {
		final Group previewGroup = new Group(parent, SWT.NONE);
		previewGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		previewGroup.setLayout(new GridLayout());

		rangePreviewLabel = new Label(previewGroup, SWT.CENTER);
		rangePreviewLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		rangePreviewLabel.setFont(JFaceResources.getHeaderFont());

	}

	private void initializeValues() {
		if (versionRange == null || versionRange.isBlank()) {
			return;
		}
		try {
			final VersionRange input = VersionComparator.parseVersionRange(versionRange);

			minVersionText.setText(input.getLeft().toString());
			maxVersionText.setText(input.getRight().toString());

			includeMinVersion.setSelection(input.getLeftType() == VersionRange.LEFT_CLOSED);
			includeMaxVersion.setSelection(input.getRightType() == VersionRange.RIGHT_CLOSED);
		} catch (final IllegalArgumentException e) {
			// ignore invalid initial value
		}
	}

	private void addUpdateListeners() {
		minVersionText.addModifyListener(e -> updatePreview());
		maxVersionText.addModifyListener(e -> updatePreview());

		includeMinVersion.addListener(SWT.Selection, e -> updatePreview());
		includeMaxVersion.addListener(SWT.Selection, e -> updatePreview());
	}

	private void updatePreview() {
		rangePreviewLabel.setText(buildVersionRange());
		rangePreviewLabel.getParent().layout();
	}

	@Override
	protected void okPressed() {
		versionRange = buildVersionRange();
		super.okPressed();
	}

	private String buildVersionRange() {
		final String min = minVersionText.getText().trim();
		final String max = maxVersionText.getText().trim();

		if (min.isBlank()) {
			return max;
		}

		if (max.isBlank() || min.equals(max)) {
			return min;
		}

		return (includeMinVersion.getSelection() ? VersionRange.LEFT_CLOSED : VersionRange.LEFT_OPEN) + min + '-' + max
				+ (includeMaxVersion.getSelection() ? VersionRange.RIGHT_CLOSED : VersionRange.RIGHT_OPEN);
	}

	private static final VerifyListener VERSION_VERIFY_LISTENER = e -> {
		for (final char c : e.text.toCharArray()) {
			if (!Character.isDigit(c) && c != '.') {
				e.doit = false;
				return;
			}
		}
	};

	@Override
	protected boolean isResizable() {
		return true;
	}
}
