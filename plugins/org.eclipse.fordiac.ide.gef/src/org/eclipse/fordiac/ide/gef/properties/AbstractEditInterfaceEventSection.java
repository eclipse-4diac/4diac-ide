/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 *               2019, 2020 Johannes Kepler Univeristy Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - create command now has enhanced guess
 *   Daniel Lindhuber - added addEntry method
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.gef.nat.EventColumnProvider;
import org.eclipse.fordiac.ide.gef.nat.EventListProvider;
import org.eclipse.fordiac.ide.gef.nat.FordiacInterfaceListProvider;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.swt.widgets.Group;

public abstract class AbstractEditInterfaceEventSection extends AbstractEditInterfaceSection {


	@Override
	protected String[] fillTypeCombo() {
		final List<String> list = new ArrayList<>();
		for (final DataType dataType : EventTypeLibrary.getInstance().getEventTypes()) {
			list.add(dataType.getName());
		}
		return list.toArray(new String[0]);
	}

	protected DataType getLastUsedEventType(final InterfaceList interfaceList, final boolean isInput,
			final IInterfaceElement interfaceElement) {
		if (null != interfaceElement) {
			return interfaceElement.getType();
		}
		final EList<Event> eventList = getEventList(interfaceList, isInput);
		if (!eventList.isEmpty()) {
			return eventList.get(eventList.size() - 1).getType();
		}
		return EventTypeLibrary.getInstance().getType(fillTypeCombo()[0]);
	}

	@Override
	protected int getInsertingIndex(final IInterfaceElement interfaceElement, final boolean isInput) {
		if (null != interfaceElement) {
			final InterfaceList interfaceList = (InterfaceList) interfaceElement.eContainer();
			return getInsertingIndex(interfaceElement, getEventList(interfaceList, isInput));
		}
		return -1;
	}

	private static EList<Event> getEventList(final InterfaceList interfaceList, final boolean isInput) {
		return isInput ? interfaceList.getEventInputs() : interfaceList.getEventOutputs();
	}

	@Override
	public void addEntry(final Object entry, final int index, final CompoundCommand cmd) {
		if (entry instanceof Event) {
			final Event entry2 = (Event) entry;
			cmd.add(newInsertCommand(entry2, entry2.isIsInput(), index));
		}
	}


	@Override
	public void setupOutputTable(final Group outputsGroup) {
		IEditableRule rule = IEditableRule.NEVER_EDITABLE;
		if (isEditable()) {
			rule = IEditableRule.ALWAYS_EDITABLE;
		}
		outputProvider = new EventListProvider(this, null);
		final DataLayer outputDataLayer = setupDataLayer(outputProvider);
		outputTable = NatTableWidgetFactory.createRowNatTable(outputsGroup, outputDataLayer,
				new EventColumnProvider(), rule, typeSelection, this);
	}

	@Override
	public void setupInputTable(final Group inputsGroup) {
		IEditableRule rule = IEditableRule.NEVER_EDITABLE;
		if (isEditable()) {
			rule = IEditableRule.ALWAYS_EDITABLE;
		}
		inputProvider = new EventListProvider(this, null);
		final DataLayer inputDataLayer = setupDataLayer(inputProvider);
		inputTable = NatTableWidgetFactory.createRowNatTable(inputsGroup, inputDataLayer,
				new EventColumnProvider(), rule, typeSelection, this);
	}

	@Override
	public void setTableInputFbNetworkElement(final FBNetworkElement element) {
		((FordiacInterfaceListProvider) inputProvider).setInput(element.getInterface().getEventInputs());
		((FordiacInterfaceListProvider) outputProvider).setInput(element.getInterface().getEventOutputs());
	}

	@Override
	public void setTableInputFBType(final FBType type) {
		((FordiacInterfaceListProvider) inputProvider).setInput(type.getInterfaceList().getEventInputs());
		((FordiacInterfaceListProvider) outputProvider).setInput(type.getInterfaceList().getEventOutputs());
	}

}
