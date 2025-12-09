/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.preferences;

import org.eclipse.fordiac.ide.model.ui.Messages;
import org.eclipse.fordiac.ide.model.validation.ValidationPreferences;
import org.eclipse.fordiac.ide.ui.preferences.FordiacPropertyPreferencePage;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;

public class ValidationPreferencePage extends FordiacPropertyPreferencePage {

	public ValidationPreferencePage() {
		super(GRID, ValidationPreferences.QUALIFIER);
	}

	@Override
	protected String getPreferencePageID() {
		return "org.eclipse.fordiac.ide.model.ui.preferences.ValidationPreferences"; //$NON-NLS-1$
	}

	@Override
	protected String getPropertyPageID() {
		return "org.eclipse.fordiac.ide.model.ui.properties.ValidationPreferences"; //$NON-NLS-1$
	}

	@Override
	protected void createFieldEditors() {
		final Group collision = new Group(getFieldEditorParent(), SWT.NONE);
		collision.setText(Messages.Validation_SeverityLabel);

		final String[][] options = { { Messages.Validation_IgnoreLabel, ValidationPreferences.SEVERITY_IGNORE },
				{ Messages.Validation_InfoLabel, ValidationPreferences.SEVERITY_INFO },
				{ Messages.Validation_WarningLabel, ValidationPreferences.SEVERITY_WARNING },
				{ Messages.Validation_ErrorLabel, ValidationPreferences.SEVERITY_ERROR } };

		addField(new ComboFieldEditor(ValidationPreferences.COLLISION_SEVERITY,
				Messages.Validation_CollisionSeverityLabel, options, collision));
		addField(new ComboFieldEditor(ValidationPreferences.RIGHT_INTERFACE_BAR_COLLISION_SEVERITY,
				Messages.Validation_InterfaceCollisionSeverityLabel, options, collision));

		final GridLayout gridLayout = new GridLayout(2, false);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;

		gridData.horizontalSpan = 2;
		collision.setLayout(gridLayout);
		collision.setLayoutData(gridData);
	}

}
