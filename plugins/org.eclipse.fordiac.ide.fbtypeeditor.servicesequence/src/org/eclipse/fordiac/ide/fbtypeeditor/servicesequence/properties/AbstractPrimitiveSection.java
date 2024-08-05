/*******************************************************************************
 * Copyright (c) 2014, 2024 fortiss GmbH, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - cleaned command stack handling for property sections
 *   Melanie Winter - updated section, use comboboxes, made abstract
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.properties;

import java.util.Arrays;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangePrimitiveEventCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangePrimitiveParameterCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.AbstractPrimitiveEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.InputPrimitiveEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.OutputPrimitiveEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.widgets.InterfaceSelectorButton;
import org.eclipse.fordiac.ide.gef.properties.AbstractDoubleColumnSection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class AbstractPrimitiveSection extends AbstractDoubleColumnSection {

	private Text parametersText;
	private CCombo eventCombo;
	private CCombo dataQualifyingCombo;
	private Button checkBox;
	private Text customEventText;
	private InterfaceSelectorButton interfaceSelector;
	private static final int ASCII_UNDERSCORE = 95;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		final Group primitiveSpecification = getWidgetFactory().createGroup(getLeftComposite(),
				Messages.PrimitiveSection_CreateControls_PrimitiveSpecification);
		primitiveSpecification.setLayout(new GridLayout());
		primitiveSpecification.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		createEventSection(primitiveSpecification);
		createCustomEventSection(primitiveSpecification);
		createPrimitiveSection(primitiveSpecification);
		fillDataQualifyingDropdown();
		dataQualifyingCombo.setToolTipText(Messages.PrimitiveSection_DataQualifyingToolTip);
	}

	protected void createEventSection(final Group parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		composite.pack();

		getWidgetFactory().createCLabel(composite, Messages.PrimitiveSection_CreateEventSection_Event);

		final Composite eventComposite = getWidgetFactory().createComposite(composite);
		eventComposite.setLayout(new GridLayout(1, true));
		eventComposite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		eventCombo = ComboBoxWidgetFactory.createCombo(getWidgetFactory(), eventComposite);
		eventCombo.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		eventCombo.addListener(SWT.Selection, event -> {
			final String newEventName = eventCombo.getText() + dataQualifyingCombo.getText();
			executeCommand(new ChangePrimitiveEventCommand(getType(), newEventName));
			refresh();
		});

		final Composite dataQualifyingComposite = getWidgetFactory().createComposite(composite);
		dataQualifyingComposite.setLayout(new GridLayout(1, true));
		dataQualifyingComposite.setLayoutData(new GridData(SWT.RIGHT, SWT.NONE, false, false));
		dataQualifyingComposite.setSize(STANDARD_LABEL_WIDTH, getMinimumHeight());
		dataQualifyingComposite.setToolTipText(Messages.PrimitiveSection_DataQualifyingToolTip);

		dataQualifyingCombo = ComboBoxWidgetFactory.createCombo(getWidgetFactory(), dataQualifyingComposite);
		dataQualifyingCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		dataQualifyingCombo.addListener(SWT.Selection, event -> {
			final String newEventName = eventCombo.getText() + dataQualifyingCombo.getText();
			executeCommand(new ChangePrimitiveEventCommand(getType(), newEventName));
			refresh();
		});
	}

	private void createCustomEventSection(final Composite parent) {
		final Composite customEventComposite = getWidgetFactory().createComposite(parent);
		customEventComposite.setLayout(new GridLayout(3, false));
		customEventComposite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		getWidgetFactory().createCLabel(customEventComposite, Messages.PrimitiveSection_CustomEvent);

		checkBox = new Button(customEventComposite, SWT.CHECK);
		customEventText = createGroupText(customEventComposite, true);

		if (isCustomEvent()) {
			checkBox.setSelection(true);
		}

		checkBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (checkBox.getSelection()) {
					executeCommand(new ChangePrimitiveEventCommand(getType(), "")); //$NON-NLS-1$
				} else {
					customEventText.setText(""); //$NON-NLS-1$
					eventCombo.select(0);
					executeCommand(new ChangePrimitiveEventCommand(getType(), eventCombo.getText()));
				}
				refresh();
			}
		});

		customEventText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangePrimitiveEventCommand(getType(), customEventText.getText()));
			addContentAdapter();
		});
	}

	private boolean isCustomEvent() {
		if (getType() != null) {
			final FBType fb = getFBType();
			for (final Event event : getRelevantEvents(fb)) {
				if (event.getName().equals(getType().getEvent())) {
					return false;
				}
			}
		}
		return true;
	}

	protected void createPrimitiveSection(final Group parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, Messages.PrimitiveSection_CreatePrimitiveSection_Interface);

		interfaceSelector = new InterfaceSelectorButton(composite, cmd -> {
			executeCommand(cmd);
			refresh();
		});

		getWidgetFactory().createCLabel(composite, Messages.TransactionSection_Parameter);
		parametersText = createGroupText(composite, true, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		parametersText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangePrimitiveParameterCommand(getType(), parametersText.getText()));
			addContentAdapter();
		});
	}

	@Override
	protected Primitive getInputType(final Object input) {
		if ((input instanceof InputPrimitiveEditPart) || (input instanceof OutputPrimitiveEditPart)) {
			return ((AbstractPrimitiveEditPart) input).getModel();
		}
		if ((input instanceof InputPrimitive) || (input instanceof OutputPrimitive)) {
			return ((Primitive) input);
		}
		return null;
	}

	@Override
	protected void setInputCode() {
		parametersText.setEnabled(false);
	}

	@Override
	protected void performRefresh() {
		parametersText.setText(getType().getParameters() != null ? getType().getParameters() : ""); //$NON-NLS-1$
		interfaceSelector.setType(getType());
		setEventDropdown();
		setDataQualifyingDropdown();

		final IInterfaceElement qiData = getType().getService().getFBType().getInterfaceList()
				.getInterfaceElement("QI"); //$NON-NLS-1$

		dataQualifyingCombo.setEnabled(qiData != null && !checkBox.getSelection());
		customEventText.setEnabled(checkBox.getSelection());
		eventCombo.setEnabled(!checkBox.getSelection());
	}

	protected abstract EList<Event> getRelevantEvents(final FBType fb);

	@Override
	protected abstract Primitive getType();

	protected FBType getFBType() {
		return getType().getService().getFBType();
	}

	private void fillDataQualifyingDropdown() {
		dataQualifyingCombo.removeAll();
		dataQualifyingCombo.add(""); //$NON-NLS-1$
		dataQualifyingCombo.add("+"); //$NON-NLS-1$
		dataQualifyingCombo.add("-"); //$NON-NLS-1$

	}

	private void setDataQualifyingDropdown() {
		final String currentEvent = getType().getEvent();
		int index = 0;

		if (!currentEvent.isEmpty()) {
			final String lastChar = String.valueOf(currentEvent.charAt(currentEvent.length() - 1));
			final String[] itemsArray = dataQualifyingCombo.getItems();
			for (int i = 0; i < itemsArray.length; i++) {
				if (lastChar.equals(itemsArray[i])) {
					index = i;
				}
			}
		}
		dataQualifyingCombo.select(index);
	}

	public void setEventDropdown() {
		eventCombo.removeAll();
		final FBType fb = getFBType();
		for (final Event event : getRelevantEvents(fb)) {
			eventCombo.add(event.getName());
		}
		selectCurrentEventInCombo();
	}

	private void selectCurrentEventInCombo() {

		String currentEvent = getType().getEvent();

		// handle qualifier QI
		if (!currentEvent.isEmpty()) {
			final char lastChar = currentEvent.charAt(currentEvent.length() - 1);
			if (!(Character.isLetterOrDigit(lastChar) || (lastChar == ASCII_UNDERSCORE))) {
				currentEvent = currentEvent.substring(0, currentEvent.length() - 1);
			}
		}

		// select event in combobox
		final int index = Arrays.asList(eventCombo.getItems()).indexOf(currentEvent);
		if (index >= 0) {
			eventCombo.select(index);
			checkBox.setSelection(false);
			customEventText.setText(""); //$NON-NLS-1$
		} else {
			// set custom event
			checkBox.setSelection(true);
			customEventText.setText(currentEvent);
		}
	}

	@Override
	protected void setInputInit() {
		// currently nothing to be done here
	}
}
