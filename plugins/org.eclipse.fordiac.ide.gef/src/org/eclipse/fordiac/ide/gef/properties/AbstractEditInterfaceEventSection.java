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

import java.util.Objects;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.gef.nat.InterfaceElementColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.TypedElementConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.TypedElementTableColumn;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.ui.nat.EventTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.EventTypeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.TypeSelectionButton;
import org.eclipse.fordiac.ide.ui.widget.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.swt.widgets.Group;

public abstract class AbstractEditInterfaceEventSection extends AbstractEditInterfaceSection<Event> {

	protected static DataType getLastUsedEventType(final InterfaceList interfaceList, final boolean isInput,
			final IInterfaceElement interfaceElement) {
		if (null != interfaceElement) {
			return interfaceElement.getType();
		}
		final EList<Event> eventList = getEventList(interfaceList, isInput);
		if (!eventList.isEmpty()) {
			return eventList.get(eventList.size() - 1).getType();
		}
		return EventTypeLibrary.getInstance().getType(null);
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
	public void addEntry(final Object entry, final boolean isInput, final int index, final CompoundCommand cmd) {
		if (entry instanceof final Event entry2) {
			cmd.add(newInsertCommand(entry2, isInput, index));
		}
	}

	@Override
	public void removeEntry(final Object entry, final CompoundCommand cmd) {
		if (entry instanceof final Event event) {
			cmd.add(newDeleteCommand(event));
		}
	}

	@Override
	public void setupOutputTable(final Group outputsGroup) {
		outputProvider = new ChangeableListDataProvider<>(new InterfaceElementColumnAccessor<>(this) {
			@Override
			public Command createCommand(final Event rowObject, final TypedElementTableColumn column,
					final Object newValue) {
				return switch (column) {
				case NAME -> onNameChange(rowObject, Objects.toString(newValue, NULL_DEFAULT));
				default -> super.createCommand(rowObject, column, newValue);
				};
			}
		});
		final DataLayer outputDataLayer = new DataLayer(outputProvider);
		outputDataLayer.setConfigLabelAccumulator(
				new TypedElementConfigLabelAccumulator<>(outputProvider, this::getAnnotationModel));
		outputTable = NatTableWidgetFactory.createRowNatTable(
				outputsGroup, outputDataLayer, new NatTableColumnProvider<>(TypedElementTableColumn.DEFAULT_COLUMNS),
				getSectionEditableRule(), new TypeSelectionButton(this::getTypeLibrary,
						EventTypeSelectionContentProvider.INSTANCE, EventTypeSelectionTreeContentProvider.INSTANCE),
				this, false);
	}

	@Override
	public void setupInputTable(final Group inputsGroup) {
		inputProvider = new ChangeableListDataProvider<>(new InterfaceElementColumnAccessor<>(this) {
			@Override
			public Command createCommand(final Event rowObject, final TypedElementTableColumn column,
					final Object newValue) {
				return switch (column) {
				case NAME -> onNameChange(rowObject, Objects.toString(newValue, NULL_DEFAULT));
				default -> super.createCommand(rowObject, column, newValue);
				};
			}
		});
		final DataLayer inputDataLayer = new DataLayer(inputProvider);
		inputDataLayer.setConfigLabelAccumulator(
				new TypedElementConfigLabelAccumulator<>(inputProvider, this::getAnnotationModel));
		inputTable = NatTableWidgetFactory.createRowNatTable(
				inputsGroup, inputDataLayer, new NatTableColumnProvider<>(TypedElementTableColumn.DEFAULT_COLUMNS),
				getSectionEditableRule(), new TypeSelectionButton(this::getTypeLibrary,
						EventTypeSelectionContentProvider.INSTANCE, EventTypeSelectionTreeContentProvider.INSTANCE),
				this, true);
	}

	@Override
	public void setTableInput(final InterfaceList il) {
		inputProvider.setInput(il.getEventInputs());
		outputProvider.setInput(il.getEventOutputs());
	}
}
