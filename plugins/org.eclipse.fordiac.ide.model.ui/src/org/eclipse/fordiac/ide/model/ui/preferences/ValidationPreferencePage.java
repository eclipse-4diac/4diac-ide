/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *   Felix Schmid - change style to be more in line with "Errors/Warnings" pages
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.preferences;

import org.eclipse.fordiac.ide.model.ui.Messages;
import org.eclipse.fordiac.ide.model.validation.ValidationPreferences;
import org.eclipse.fordiac.ide.ui.preferences.FordiacPropertyPreferencePage;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.ExpandableComposite;

public class ValidationPreferencePage extends FordiacPropertyPreferencePage {

	private static final String[][] SEVERITIES = {
			{ Messages.Validation_IgnoreLabel, ValidationPreferences.SEVERITY_IGNORE },
			{ Messages.Validation_InfoLabel, ValidationPreferences.SEVERITY_INFO },
			{ Messages.Validation_WarningLabel, ValidationPreferences.SEVERITY_WARNING },
			{ Messages.Validation_ErrorLabel, ValidationPreferences.SEVERITY_ERROR } };

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
		final Composite parent = getFieldEditorParent();
		final Label header = new Label(parent, SWT.NONE);
		header.setText(Messages.Validation_Severity_Info);
		header.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 2, 1));

		final Composite collisionSection = createSection(Messages.Validation_Section_Collision, parent, true);
		addField(new ComboFieldEditor(ValidationPreferences.COLLISION_SEVERITY,
				Messages.Validation_CollisionSeverityLabel, SEVERITIES, collisionSection));
		addField(new ComboFieldEditor(ValidationPreferences.RIGHT_INTERFACE_BAR_COLLISION_SEVERITY,
				Messages.Validation_InterfaceCollisionSeverityLabel, SEVERITIES, collisionSection));

		final Composite typeSection = createSection(Messages.Validation_Section_TypeManagement, parent, true);
		addField(new ComboFieldEditor(ValidationPreferences.PACKAGENAME_MISMATCH_FOLDER,
				Messages.Validation_PackagenameMismatchFolder, SEVERITIES, typeSection));

		final Composite potentialProblemsSection = createSection(
				Messages.ValidationPreferencePage_PotentialProgrammingProblems, parent, true);
		addField(new ComboFieldEditor(ValidationPreferences.NO_VALUE_FOR_GENERIC_TYPE_VARIABLE,
				Messages.ValidationPreferencePage_NoValueForGenericTypeVariable, SEVERITIES, potentialProblemsSection));
		addField(new ComboFieldEditor(ValidationPreferences.VALUE_FOR_GENERIC_INSTANCE_VARIABLE,
				Messages.ValidationPreferencePage_ValueForGenericInstanceVariable, SEVERITIES,
				potentialProblemsSection));
		addField(new ComboFieldEditor(ValidationPreferences.EVENT_MULTIPLE_OUTPUT_CONNECTIONS,
				Messages.ValidationPreferencePage_EventMultipleOutputConnections, SEVERITIES,
				potentialProblemsSection));
	}

	private static Composite createSection(final String label, final Composite composite, final boolean expanded) {
		final ExpandableComposite excomposite = createStyleSection(composite, label, expanded);
		final Composite inner = new Composite(excomposite, SWT.NONE);
		inner.setFont(composite.getFont());
		inner.setLayout(new GridLayout(1, false));
		excomposite.setClient(inner);
		return inner;
	}

	private static ExpandableComposite createStyleSection(final Composite parent, final String label,
			final boolean expanded) {
		final ExpandableComposite excomposite = new ExpandableComposite(parent, SWT.NONE,
				ExpandableComposite.TWISTIE | ExpandableComposite.CLIENT_INDENT);
		excomposite.setText(label);
		excomposite.setExpanded(expanded);
		excomposite.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		excomposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 2, 1));
		return excomposite;
	}
}
