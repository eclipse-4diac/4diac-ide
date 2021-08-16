/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GmbH
 *               2019, 2021 Johannes Kepler University Linz
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
 *   Melanie Winter - updated section, use comboboxes
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.properties;

import java.util.Arrays;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangePrimitiveEventCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangePrimitiveInterfaceCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangePrimitiveParameterCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.AbstractPrimitiveEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.InputPrimitiveEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.OutputPrimitiveEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class PrimitiveSection extends AbstractServiceSection {

	private Text parametersText;

	private CCombo serviceInterfaceCombo;
	private CCombo eventCombo;
	private CCombo dataQualifyingCombo;
	private static final int ASCII_UNDERSCORE = 95;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		Group primitiveSpecification;
		primitiveSpecification = getWidgetFactory().createGroup(getLeftComposite(), "Primitive Specification");
		primitiveSpecification.setLayout(new GridLayout());
		primitiveSpecification.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		createEventSection(primitiveSpecification);
		createPrimitiveSection(primitiveSpecification);
		fillDataQualifyingDropdown();
		dataQualifyingCombo.setToolTipText("available if there is a datapin named QI");
	}

	protected void createEventSection(final Group parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		composite.pack();
		getWidgetFactory().createCLabel(composite, Messages.PrimitiveSection_CreateEventSection_Event);

		final Composite eventComposite = getWidgetFactory().createComposite(composite);
		eventComposite.setLayout(new GridLayout(1, true));
		eventComposite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true));

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
		dataQualifyingComposite.setToolTipText("available if there is a datapin named QI");

		dataQualifyingCombo = ComboBoxWidgetFactory.createCombo(getWidgetFactory(), dataQualifyingComposite);
		dataQualifyingCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		dataQualifyingCombo.addListener(SWT.Selection, event -> {
			final String newEventName = eventCombo.getText() + dataQualifyingCombo.getText();
			executeCommand(new ChangePrimitiveEventCommand(getType(), newEventName));
			refresh();
		});
	}

	protected void createPrimitiveSection(final Group parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, Messages.PrimitiveSection_CreatePrimitiveSection_Interface);

		serviceInterfaceCombo = ComboBoxWidgetFactory.createCombo(getWidgetFactory(), composite);
		serviceInterfaceCombo.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		serviceInterfaceCombo.addListener(SWT.Selection, event -> {
			executeCommand(
					new ChangePrimitiveInterfaceCommand((Service) getType().eContainer().eContainer().eContainer(),
							getType(), serviceInterfaceCombo.getText()));
			refresh();
		});

		getWidgetFactory().createCLabel(composite, Messages.PrimitiveSection_CreatePrimitiveSection_Parameters);
		parametersText = createGroupText(composite, true);
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
		serviceInterfaceCombo.removeAll();
	}

	@Override
	public void refresh() {
		final CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			parametersText.setText(getType().getParameters() != null ? getType().getParameters() : ""); //$NON-NLS-1$
			setServiceInterfaceDropdown();
			setEventDropdown();
			setDataQualifyingDropdown();

			final IInterfaceElement qiData = getType().getService().getFBType().getInterfaceList()
					.getInterfaceElement("QI"); //$NON-NLS-1$
			dataQualifyingCombo.setEnabled(qiData != null);
		}
		commandStack = commandStackBuffer;
	}



	@Override
	protected Primitive getType() {
		return (Primitive) type;
	}

	protected FBType getFBType() {
		return getType().getService().getFBType();
	}

	protected boolean isLeftInterfaceSelected() {
		return (serviceInterfaceCombo.getText().equals(serviceInterfaceCombo.getItem(0)));
	}

	private void fillDataQualifyingDropdown() {
		dataQualifyingCombo.removeAll();
		dataQualifyingCombo.add(""); //$NON-NLS-1$
		dataQualifyingCombo.add("+"); //$NON-NLS-1$
		dataQualifyingCombo.add("-"); //$NON-NLS-1$

	}

	private void setDataQualifyingDropdown() {
		final String currentEvent = getType().getEvent();
		final String lastChar = String.valueOf(currentEvent.charAt(currentEvent.length() - 1));
		int index = 0;
		final String[] itemsArray = dataQualifyingCombo.getItems();
		for (int i = 0; i < itemsArray.length; i++) {
			if (lastChar.equals(itemsArray[i])) {
				index = i;
			}
		}
		dataQualifyingCombo.select(index);
	}

	public void setEventDropdown() {
		eventCombo.removeAll();
		final FBType fb = getType().getService().getFBType();
		for (final Event event : getRelevantEvents(fb)) {
			eventCombo.add(event.getName());
		}

		selectCurrentEventInCombo();
	}

	private void selectCurrentEventInCombo() {

		String currentEvent = getType().getEvent();
		int index;

		final char lastChar = currentEvent.charAt(currentEvent.length() - 1);

		if (!(Character.isLetterOrDigit(lastChar) || (lastChar == ASCII_UNDERSCORE))) {
			currentEvent = currentEvent.substring(0, currentEvent.length()-1);
			index = Arrays.asList(eventCombo.getItems()).indexOf(currentEvent);
		} else {
			index = Arrays.asList(eventCombo.getItems()).indexOf(currentEvent);
		}
		if (index >= 0) {
			eventCombo.select(index);
		}
	}

	private EList<Event> getRelevantEvents(final FBType fb) {
		if (getType() instanceof InputPrimitive) {
			return fb.getInterfaceList().getEventInputs();
		}
		return fb.getInterfaceList().getEventOutputs();
	}


	public void setServiceInterfaceDropdown() {
		serviceInterfaceCombo.removeAll();
		final Service s = getType().getService();
		serviceInterfaceCombo.add(s.getLeftInterface().getName());
		serviceInterfaceCombo.add(s.getRightInterface().getName());
		if (serviceInterfaceCombo.getItem(0).equals(getType().getInterface().getName())) {
			serviceInterfaceCombo.select(0);
		} else {
			serviceInterfaceCombo.select(1);
		}
	}

	@Override
	protected void setInputInit() {
		// currently nothing to be done here
	}
}
