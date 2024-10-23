/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019, 2020 Johannes Kepler University Linz
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
 *   Daniel Lindhuber - added insert command method
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import java.util.List;
import java.util.Set;

import org.eclipse.fordiac.ide.application.commands.ChangeSubAppInterfaceOrderCommand;
import org.eclipse.fordiac.ide.application.commands.CreateSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.application.commands.ResizeGroupOrSubappCommand;
import org.eclipse.fordiac.ide.application.commands.ResizingSubappInterfaceCreationCommand;
import org.eclipse.fordiac.ide.application.utilities.GetEditPartFromGraficalViewerHelper;
import org.eclipse.fordiac.ide.gef.nat.TypedElementTableColumn;
import org.eclipse.fordiac.ide.gef.properties.AbstractEditInterfaceEventSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnEditableRule;
import org.eclipse.gef.commands.Command;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;

public class EditUntypedSubappInterfaceEventSection extends AbstractEditInterfaceEventSection {
	@Override
	protected CreationCommand newCreateCommand(final IInterfaceElement interfaceElement, final boolean isInput) {
		final DataType last = getLastUsedEventType(getType().getInterface(), isInput, interfaceElement);
		final int pos = getInsertingIndex(interfaceElement, isInput);
		final CreateSubAppInterfaceElementCommand cmd = new CreateSubAppInterfaceElementCommand(last,
				getCreationName(interfaceElement), getType().getInterface(), isInput, pos);
		return ResizingSubappInterfaceCreationCommand.wrapCreateCommand(cmd, getType());
	}

	@Override
	protected CreationCommand newInsertCommand(final IInterfaceElement interfaceElement, final boolean isInput,
			final int index) {
		final CreateSubAppInterfaceElementCommand cmd = new CreateSubAppInterfaceElementCommand(interfaceElement,
				isInput, getType().getInterface(), index);
		return ResizingSubappInterfaceCreationCommand.wrapCreateCommand(cmd, getType());
	}

	@Override
	protected SubApp getInputType(final Object input) {
		return SubappPropertySectionFilter.getFBNetworkElementFromSelectedElement(input);
	}

	@Override
	protected DeleteInterfaceCommand newDeleteCommand(final IInterfaceElement selection) {
		return new DeleteSubAppInterfaceElementCommand(selection);
	}

	@Override
	protected ChangeInterfaceOrderCommand newOrderCommand(final IInterfaceElement selection, final boolean moveUp) {
		return new ChangeSubAppInterfaceOrderCommand(selection, moveUp);
	}

	@Override
	protected SubApp getType() {
		return (SubApp) type;
	}

	@Override
	protected InterfaceList getInterface() {
		return (getType() != null) ? getType().getInterface() : null;
	}

	@Override
	protected IEditableRule getInputTableEditableRule() {
		return new UntypedSubappInterfaceEditableRule(getSectionEditableRule(), TypedElementTableColumn.DEFAULT_COLUMNS,
				inputProvider);
	}

	@Override
	protected IEditableRule getOutputTableEditableRule() {
		return new UntypedSubappInterfaceEditableRule(getSectionEditableRule(), TypedElementTableColumn.DEFAULT_COLUMNS,
				outputProvider);
	}

	@Override
	public Command onNameChange(final IInterfaceElement ie, final String newValue) {
		final ChangeNameCommand nameChangeCmd = ChangeNameCommand.forName(ie, newValue);
		if (getType().isUnfolded()) {
			return new ResizeGroupOrSubappCommand(
					GetEditPartFromGraficalViewerHelper.findAbstractContainerContentEditFromInterfaceElement(ie),
					nameChangeCmd);
		}
		return nameChangeCmd;
	}

	private class UntypedSubappInterfaceEditableRule extends NatTableColumnEditableRule<TypedElementTableColumn> {
		private final IRowDataProvider<Event> dataProvider;

		public static final Set<TypedElementTableColumn> CONNECTED_EDITABLE_COLUMNS = Set
				.of(TypedElementTableColumn.NAME, TypedElementTableColumn.COMMENT);

		public UntypedSubappInterfaceEditableRule(final IEditableRule parent,
				final List<TypedElementTableColumn> columns, final IRowDataProvider<Event> dataProvider) {
			super(parent, columns, TypedElementTableColumn.DEFAULT_EDITABLE);
			this.dataProvider = dataProvider;
		}

		@Override
		public boolean isEditable(final int columnIndex, final int rowIndex) {
			final Event rowItem = dataProvider.getRowObject(rowIndex);
			if (isConnected(rowItem) && !CONNECTED_EDITABLE_COLUMNS.contains(getColumns().get(columnIndex))) {
				return false;
			}
			return super.isEditable(columnIndex, rowIndex);
		}

		private static boolean isConnected(final Event event) {
			return !event.getInputConnections().isEmpty() || !event.getOutputConnections().isEmpty();
		}
	}
}
