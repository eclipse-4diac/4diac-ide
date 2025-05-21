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

import org.eclipse.fordiac.ide.model.preferences.ModelPreferenceConstants;
import org.eclipse.fordiac.ide.model.ui.Messages;
import org.eclipse.fordiac.ide.ui.preferences.FordiacPropertyPreferencePage;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;

public class BlockSizePreferencePage extends FordiacPropertyPreferencePage {

	private boolean changesOnLabelSize = false;

	public BlockSizePreferencePage() {
		super(GRID, ModelPreferenceConstants.MODEL_PREFERENCES_ID);
	}

	@Override
	protected String getPreferencePageID() {
		return "org.eclipse.fordiac.ide.model.ui.preferences.BlockSizePreferences"; //$NON-NLS-1$
	}

	@Override
	protected String getPropertyPageID() {
		return "org.eclipse.fordiac.ide.model.ui.properties.BlockSizePreferences"; //$NON-NLS-1$
	}

	@Override
	protected void createFieldEditors() {
		// Create a Group to hold the block margin fields
		createGroupBlockMargins();

		// Create a Group to hold label size field
		createGroupLabelSize();
	}

	private void createGroupBlockMargins() {
		final Group blockMargin = new Group(getFieldEditorParent(), SWT.NONE);
		blockMargin.setText(Messages.BlockSizePreferences_BlockMargins);

		final IntegerFieldEditor integerFieldEditorTopBottom = new IntegerFieldEditor(
				ModelPreferenceConstants.MARGIN_TOP_BOTTOM, Messages.BlockSizePreferences_TopBottom, blockMargin);
		integerFieldEditorTopBottom.setValidRange(0, 1000);
		addField(integerFieldEditorTopBottom);

		final IntegerFieldEditor integerFieldEditorLeftRight = new IntegerFieldEditor(
				ModelPreferenceConstants.MARGIN_LEFT_RIGHT, Messages.BlockSizePreferences_LeftRight, blockMargin);
		integerFieldEditorLeftRight.setValidRange(0, 1000);
		addField(integerFieldEditorLeftRight);

		configGroup(blockMargin);
	}

	private void createGroupLabelSize() {

		final Group labelSize = new Group(getFieldEditorParent(), SWT.NONE);
		labelSize.setText(Messages.BlockSizePreferences_LabelSize);
		final IntegerFieldEditor integerFieldEditorLabel = new IntegerFieldEditor(
				ModelPreferenceConstants.MAX_VALUE_LABEL_SIZE, Messages.BlockSizePreferences_MaximumValueLabelSize,
				labelSize);
		integerFieldEditorLabel.setValidRange(0, 120);
		addField(integerFieldEditorLabel);

		final IntegerFieldEditor integerFieldEditorTypeLabel = new IntegerFieldEditor(
				ModelPreferenceConstants.MAX_TYPE_LABEL_SIZE, Messages.BlockSizePreferences_MaximumTypeLabelSize,
				labelSize);
		integerFieldEditorTypeLabel.setValidRange(0, 120);
		addField(integerFieldEditorTypeLabel);

		final IntegerFieldEditor integerFieldEditorMinPin = new IntegerFieldEditor(
				ModelPreferenceConstants.MIN_PIN_LABEL_SIZE, Messages.BlockSizePreferences_MinimumPinLabelSize,
				labelSize);
		integerFieldEditorMinPin.setValidRange(0, 60);
		addField(integerFieldEditorMinPin);

		final IntegerFieldEditor integerFieldEditorMaxPin = new IntegerFieldEditor(
				ModelPreferenceConstants.MAX_PIN_LABEL_SIZE, Messages.BlockSizePreferences_MaximumPinLabelSize,
				labelSize);
		integerFieldEditorMaxPin.setValidRange(0, 60);
		addField(integerFieldEditorMaxPin);

		final IntegerFieldEditor integerFieldEditorMinInterfaceBarWidth = new IntegerFieldEditor(
				ModelPreferenceConstants.MIN_INTERFACE_BAR_SIZE, Messages.BlockSizePreferences_MinimumInterfaceBarSize,
				labelSize);
		integerFieldEditorMinInterfaceBarWidth.setValidRange(0, 100);
		addField(integerFieldEditorMinInterfaceBarWidth);

		final IntegerFieldEditor integerFieldEditorInterfaceBar = new IntegerFieldEditor(
				ModelPreferenceConstants.MAX_INTERFACE_BAR_SIZE, Messages.BlockSizePreferences_MaximumInterfaceBarSize,
				labelSize);
		integerFieldEditorInterfaceBar.setValidRange(0, 100);
		addField(integerFieldEditorInterfaceBar);

		final IntegerFieldEditor integerFieldEditorConnection = new IntegerFieldEditor(
				ModelPreferenceConstants.MAX_HIDDEN_CONNECTION_LABEL_SIZE,
				Messages.BlockSizePreferences_MaximumHiddenConnectionLabelSize, labelSize);
		integerFieldEditorConnection.setValidRange(0, 100);
		addField(integerFieldEditorConnection);

		configGroup(labelSize);
	}

	private static void configGroup(final Group group) {
		final GridLayout gridLayout = new GridLayout(2, false);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;

		gridData.horizontalSpan = 2;
		group.setLayout(gridLayout);
		group.setLayoutData(gridData);
	}

	@Override
	public void propertyChange(final PropertyChangeEvent event) {
		if (event.getSource() instanceof FieldEditor && matchPreferenceName(event)) {
			changesOnLabelSize = true;
		}
	}

	private static boolean matchPreferenceName(final PropertyChangeEvent event) {
		final String sourcePrefName = ((FieldEditor) event.getSource()).getPreferenceName();
		return sourcePrefName.equalsIgnoreCase(ModelPreferenceConstants.MIN_PIN_LABEL_SIZE)
				|| sourcePrefName.equalsIgnoreCase(ModelPreferenceConstants.MAX_PIN_LABEL_SIZE)
				|| sourcePrefName.equalsIgnoreCase(ModelPreferenceConstants.MAX_TYPE_LABEL_SIZE)
				|| sourcePrefName.equalsIgnoreCase(ModelPreferenceConstants.MAX_VALUE_LABEL_SIZE)
				|| sourcePrefName.equalsIgnoreCase(ModelPreferenceConstants.MAX_HIDDEN_CONNECTION_LABEL_SIZE)
				|| sourcePrefName.equalsIgnoreCase(ModelPreferenceConstants.MAX_INTERFACE_BAR_SIZE)
				|| sourcePrefName.equalsIgnoreCase(ModelPreferenceConstants.MIN_INTERFACE_BAR_SIZE);
	}

	@Override
	public boolean performOk() {
		super.performOk();
		if (changesOnLabelSize) {
			changesOnLabelSize = false;
			showMessageBox();
		}
		return true;
	}

	private static void showMessageBox() {
		final MessageBox msgBox = new MessageBox(Display.getDefault().getActiveShell(), SWT.OK);
		Display.getDefault().getActiveShell();
		msgBox.setText("4diac IDE"); //$NON-NLS-1$
		msgBox.setMessage(Messages.BlockSizePreferences_Refresh);

		msgBox.open();
	}
}
