/*******************************************************************************
 * Copyright (c) 2008, 2023 Profactor GbmH, fortiss GmbH
 *                          Johannes Kepler University Linz
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added preference driven max width for value edit parts
 *   Lisa Sonnleithner - deleted setting for corner dimensions, replaced with constant
 *   				   - moved max label size into a group
 *   				   - moved creating a group into a method
 *   Prankur Agarwal - added a field to input maximum hidden connection label size
 *   Martin Erich Jobst - added max default value length and externalized strings
 *   Patrick Aigner - added group for Block margins
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.preferences;

import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.model.preferences.ModelPreferenceConstants;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

/** The Class DiagramPreferences. */
public class DiagramPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private boolean changesOnLabelSize = false;

	private static int maxDefaultValueLength = GefPreferenceConstants.STORE
			.getInt(GefPreferenceConstants.MAX_DEFAULT_VALUE_LENGTH);

	/** Instantiates a new diagram preferences. */
	public DiagramPreferencePage() {
		super(GRID);
		setPreferenceStore(GefPreferenceConstants.STORE);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	public void createFieldEditors() {

		// Create a Group to hold the ruler fields
		createGroupRulerGrid();

		// Create a Group to hold label size field
		createGroupLabelSize();

		// Create a Group to hold the interface pin field
		createGroupInterfacePins();

		// Create a Group to hold the layout options field
		createGroupLayoutOptionsPins();

		// Create a Group to hold the block margin fields
		createGroupBlockMargins();

		// Create a Group to hold the expanded interface fields
		createExpandedInterfaceOptionsPins();
	}

	private Group createGroup(final String title) {
		final Group group = new Group(getFieldEditorParent(), SWT.NONE);
		group.setText(title);
		return group;
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
		return sourcePrefName.equalsIgnoreCase(GefPreferenceConstants.MIN_PIN_LABEL_SIZE)
				|| sourcePrefName.equalsIgnoreCase(GefPreferenceConstants.MAX_PIN_LABEL_SIZE)
				|| sourcePrefName.equalsIgnoreCase(GefPreferenceConstants.MAX_TYPE_LABEL_SIZE)
				|| sourcePrefName.equalsIgnoreCase(GefPreferenceConstants.MAX_VALUE_LABEL_SIZE)
				|| sourcePrefName.equalsIgnoreCase(GefPreferenceConstants.MAX_DEFAULT_VALUE_LENGTH)
				|| sourcePrefName.equalsIgnoreCase(GefPreferenceConstants.MAX_HIDDEN_CONNECTION_LABEL_SIZE)
				|| sourcePrefName.equalsIgnoreCase(GefPreferenceConstants.PIN_LABEL_STYLE)
				|| sourcePrefName.equalsIgnoreCase(GefPreferenceConstants.MAX_INTERFACE_BAR_SIZE)
				|| sourcePrefName.equalsIgnoreCase(GefPreferenceConstants.MIN_INTERFACE_BAR_SIZE)
				|| sourcePrefName.equalsIgnoreCase(GefPreferenceConstants.EXPANDED_INTERFACE_EVENTS_TOP)
				|| sourcePrefName.equalsIgnoreCase(GefPreferenceConstants.EXPANDED_INTERFACE_OLD_DIRECT_BEHAVIOUR);
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

		final MessageBox msgBox = new MessageBox(Display.getDefault().getActiveShell(), SWT.YES | SWT.NO);
		Display.getDefault().getActiveShell();
		msgBox.setText("4diac IDE"); //$NON-NLS-1$
		msgBox.setMessage(Messages.DiagramPreferences_Restart);

		switch (msgBox.open()) {
		case SWT.NO:
			break;
		case SWT.YES:
			PlatformUI.getWorkbench().restart();
			break;
		default:
			break;
		}
	}

	private void createGroupLabelSize() {

		final Group labelSize = createGroup(Messages.DiagramPreferences_LabelSize);
		final IntegerFieldEditor integerFieldEditorLabel = new IntegerFieldEditor(
				GefPreferenceConstants.MAX_VALUE_LABEL_SIZE, Messages.DiagramPreferences_MaximumValueLabelSize,
				labelSize);
		integerFieldEditorLabel.setValidRange(0, 120);
		addField(integerFieldEditorLabel);

		final IntegerFieldEditor integerFieldEditorValue = new IntegerFieldEditor(
				GefPreferenceConstants.MAX_DEFAULT_VALUE_LENGTH, Messages.DiagramPreferences_MaximumDefaultValueSize,
				labelSize);
		integerFieldEditorValue.setValidRange(120, 100000);
		addField(integerFieldEditorValue);

		final IntegerFieldEditor integerFieldEditorTypeLabel = new IntegerFieldEditor(
				GefPreferenceConstants.MAX_TYPE_LABEL_SIZE, Messages.DiagramPreferences_MaximumTypeLabelSize,
				labelSize);
		integerFieldEditorTypeLabel.setValidRange(0, 120);
		addField(integerFieldEditorTypeLabel);

		final IntegerFieldEditor integerFieldEditorMinPin = new IntegerFieldEditor(
				GefPreferenceConstants.MIN_PIN_LABEL_SIZE, Messages.DiagramPreferences_MinimumPinLabelSize, labelSize);
		integerFieldEditorMinPin.setValidRange(0, 60);
		addField(integerFieldEditorMinPin);

		final IntegerFieldEditor integerFieldEditorMaxPin = new IntegerFieldEditor(
				GefPreferenceConstants.MAX_PIN_LABEL_SIZE, Messages.DiagramPreferences_MaximumPinLabelSize, labelSize);
		integerFieldEditorMaxPin.setValidRange(0, 60);
		addField(integerFieldEditorMaxPin);

		final IntegerFieldEditor integerFieldEditorMinInterfaceBarWidth = new IntegerFieldEditor(
				GefPreferenceConstants.MIN_INTERFACE_BAR_SIZE, Messages.DiagramPreferences_MinimumInterfaceBarSize,
				labelSize);
		integerFieldEditorMinInterfaceBarWidth.setValidRange(0, 100);
		addField(integerFieldEditorMinInterfaceBarWidth);

		final IntegerFieldEditor integerFieldEditorInterfaceBar = new IntegerFieldEditor(
				GefPreferenceConstants.MAX_INTERFACE_BAR_SIZE, Messages.DiagramPreferences_MaximumInterfaceBarSize,
				labelSize);
		integerFieldEditorInterfaceBar.setValidRange(0, 100);
		addField(integerFieldEditorInterfaceBar);

		final IntegerFieldEditor integerFieldEditorConnection = new IntegerFieldEditor(
				GefPreferenceConstants.MAX_HIDDEN_CONNECTION_LABEL_SIZE,
				Messages.DiagramPreferences_MaximumHiddenConnectionLabelSize, labelSize);
		integerFieldEditorConnection.setValidRange(0, 100);
		addField(integerFieldEditorConnection);

		configGroup(labelSize);
	}

	private void createGroupRulerGrid() {
		final Group group = createGroup(Messages.DiagramPreferences_FieldEditors_RulerAndGrid);
		// Add the fields to the group
		final BooleanFieldEditor showRulers = new BooleanFieldEditor(GefPreferenceConstants.SHOW_RULERS,
				Messages.DiagramPreferences_FieldEditors_ShowRuler, group);
		addField(showRulers);

		final BooleanFieldEditor showGrid = new BooleanFieldEditor(GefPreferenceConstants.SHOW_GRID,
				Messages.DiagramPreferences_FieldEditors_ShowGrid, group);
		addField(showGrid);

		final BooleanFieldEditor snapToGrid = new BooleanFieldEditor(GefPreferenceConstants.SNAP_TO_GRID,
				Messages.DiagramPreferences_FieldEditors_SnapToGrid, group);
		addField(snapToGrid);

		configGroup(group);
	}

	private void createGroupLayoutOptionsPins() {
		final Group group = createGroup(Messages.DiagramPreferences_LayoutOptions);
		final BooleanFieldEditor connectionAutoLayout = new BooleanFieldEditor(
				GefPreferenceConstants.CONNECTION_AUTO_LAYOUT,
				Messages.DiagramPreferences_LayoutConnectionsAutomatically, group);
		addField(connectionAutoLayout);
		configGroup(group);
	}

	private void createGroupInterfacePins() {
		addField(new RadioGroupFieldEditor(GefPreferenceConstants.PIN_LABEL_STYLE,
				Messages.DiagramPreferences_PinLabelText, 1,
				new String[][] {
						{ Messages.DiagramPreferences_ShowPinName, GefPreferenceConstants.PIN_LABEL_STYLE_PIN_NAME },
						{ Messages.DiagramPreferences_ShowPinComment,
								GefPreferenceConstants.PIN_LABEL_STYLE_PIN_COMMENT },
						{ Messages.DiagramPreferences_ShowConnectedOutputPinName,
								GefPreferenceConstants.PIN_LABEL_STYLE_SRC_PIN_NAME } },
				getFieldEditorParent(), true));
	}

	private void createGroupBlockMargins() {
		final Group group = createGroup(Messages.DiagramPreferences_BlockMargins);
		final IPreferenceStore modelStore = ModelPreferenceConstants.STORE;

		final IntegerFieldEditor integerFieldEditorTopBottom = new IntegerFieldEditor(
				ModelPreferenceConstants.MARGIN_TOP_BOTTOM, Messages.DiagramPreferences_TopBottom, group);
		integerFieldEditorTopBottom.setValidRange(0, 1000);
		integerFieldEditorTopBottom.setPreferenceStore(modelStore);
		addField(integerFieldEditorTopBottom);

		final IntegerFieldEditor integerFieldEditorLeftRight = new IntegerFieldEditor(
				ModelPreferenceConstants.MARGIN_LEFT_RIGHT, Messages.DiagramPreferences_LeftRight, group);
		integerFieldEditorLeftRight.setValidRange(0, 1000);
		integerFieldEditorLeftRight.setPreferenceStore(modelStore);
		addField(integerFieldEditorLeftRight);

		configGroup(group);
	}

	private void createExpandedInterfaceOptionsPins() {
		final Group group = createGroup(Messages.DiagramPreferences_ExpandedInterfaceGroupText);
		final BooleanFieldEditor direct = new BooleanFieldEditor(
				GefPreferenceConstants.EXPANDED_INTERFACE_OLD_DIRECT_BEHAVIOUR,
				Messages.DiagramPreferences_ExpandedInterfaceStackPins, group);
		final BooleanFieldEditor events = new BooleanFieldEditor(GefPreferenceConstants.EXPANDED_INTERFACE_EVENTS_TOP,
				Messages.DiagramPreferences_ExpandedInterfaceEvents, group);

		addField(direct);
		addField(events);
		configGroup(group);

		events.setEnabled(
				getPreferenceStore().getBoolean(GefPreferenceConstants.EXPANDED_INTERFACE_OLD_DIRECT_BEHAVIOUR), group);
		direct.getDescriptionControl(group).addListener(SWT.Selection, event -> {
			final var button = (Button) event.widget;
			final boolean selection = button.getSelection();
			final var eventsButton = (Button) events.getDescriptionControl(group);
			events.setEnabled(selection, group);
			eventsButton.setSelection(selection);
		});
	}

	@Override
	public void init(final IWorkbench workbench) {
		// nothing to do here
	}

	public static int getMaxDefaultValueLength() {
		return maxDefaultValueLength;
	}
}
