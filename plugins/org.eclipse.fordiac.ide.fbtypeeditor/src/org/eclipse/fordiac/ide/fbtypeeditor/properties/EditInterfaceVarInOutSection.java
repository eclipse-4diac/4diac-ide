/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.gef.nat.FordiacInterfaceListProvider;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.TypeDeclarationEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnProvider;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationListProvider;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.ui.widgets.DataTypeSelectionButton;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.IConfigLabelAccumulator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class EditInterfaceVarInOutSection<T extends IInterfaceElement> extends AbstractSection
		implements I4diacNatTableUtil {

	private ListDataProvider<VarDeclaration> inputProvider;
	private NatTable inputTable;

	protected Map<String, List<String>> typeSelection = new HashMap<>();

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(3, false));
		createInputEdit(parent);
	}

	private void createInputEdit(final Composite parent) {
		final Group inputsGroup = getWidgetFactory().createGroup(parent, "Inputs"); //$NON-NLS-1$
		inputsGroup.setLayout(new GridLayout(2, false));
		inputsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final AddDeleteReorderListWidget inputButtons = new AddDeleteReorderListWidget();

		if (isEditable()) {
			inputButtons.createControls(inputsGroup, getWidgetFactory());
		}
		setupInputTable(inputsGroup);

		if (isEditable()) {
			configureButtonList(inputButtons, inputTable, true);
		}
	}

	public void setupInputTable(final Group inputsGroup) {
		IEditableRule rule = IEditableRule.NEVER_EDITABLE;
		if (isEditable()) {
			rule = IEditableRule.ALWAYS_EDITABLE;
		}
		inputProvider = new VarDeclarationListProvider(new VarDeclarationColumnAccessor(this));
		final DataLayer inputDataLayer = setupDataLayer(inputProvider);
		inputTable = NatTableWidgetFactory.createRowNatTable(inputsGroup, inputDataLayer,
				new VarDeclarationColumnProvider(), rule, new DataTypeSelectionButton(typeSelection), this, true);
		inputTable.addConfiguration(new InitialValueEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new TypeDeclarationEditorConfiguration(inputProvider));
		inputTable.configure();
	}

	protected void configureButtonList(final AddDeleteReorderListWidget buttons, final NatTable table,
			final boolean inputs) {
		buttons.bindToTableViewer(table, this, ref -> newCreateCommand((IInterfaceElement) ref, inputs),
				ref -> newDeleteCommand((IInterfaceElement) ref), ref -> newOrderCommand((IInterfaceElement) ref, true),
				ref -> newOrderCommand((IInterfaceElement) ref, false));
	}

	protected DataLayer setupDataLayer(final ListDataProvider<VarDeclaration> inputProvider2) {
		final DataLayer dataLayer = new DataLayer(inputProvider2);
		final IConfigLabelAccumulator labelAcc = dataLayer.getConfigLabelAccumulator();

		dataLayer.setConfigLabelAccumulator((configLabels, columnPosition, rowPosition) -> {
			if (labelAcc != null) {
				labelAcc.accumulateConfigLabels(configLabels, columnPosition, rowPosition);
			}
			configureLabels(inputProvider2, configLabels, columnPosition, rowPosition);
		});
		return dataLayer;
	}

	protected void configureLabels(final ListDataProvider<VarDeclaration> provider, final LabelStack configLabels,
			final int columnPosition, final int rowPosition) {
		final VarDeclaration rowItem = provider.getRowObject(rowPosition);
		switch (columnPosition) {
		case I4diacNatTableUtil.TYPE:
			if (rowItem.getType() instanceof ErrorMarkerDataType) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.ERROR_CELL);
			}
			if (isEditable()) {

				configLabels.addLabel(NatTableWidgetFactory.PROPOSAL_CELL);
			}
			break;
		case I4diacNatTableUtil.NAME, I4diacNatTableUtil.COMMENT:
			configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
			break;
		default:
			break;
		}
	}

	@Override
	protected FBType getType() {
		return (FBType) type;
	}

	@Override
	protected FBType getInputType(final Object input) {
		return FBTypePropertiesFilter.getFBTypeFromSelectedElement(input);
	}

	@Override
	protected void setInputCode() {
		// TODO Auto-generated method stub
	}

	@Override
	public void refresh() {
		final CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			setTableInput();
		}
		commandStack = commandStackBuffer;
		inputTable.refresh();
	}

	protected void setTableInput() {
		((FordiacInterfaceListProvider<VarDeclaration>) inputProvider)
				.setInput(getType().getInterfaceList().getInOutVars());
		if (isEditable()) {
			initTypeSelection(getDataTypeLib());
		}
	}

	@Override
	protected void setInputInit() {
		((VarDeclarationListProvider) inputProvider).setTypeLib(getDataTypeLib());
	}

	private CreationCommand newCreateCommand(final IInterfaceElement selection, final boolean isInput) {
		return null;

	}

	private CreationCommand newInsertCommand(final IInterfaceElement selection, final boolean isInput,
			final int index) {
		return null;

	}

	private DeleteInterfaceCommand newDeleteCommand(final IInterfaceElement selection) {
		return new DeleteInterfaceCommand(selection);
	}

	private ChangeInterfaceOrderCommand newOrderCommand(final IInterfaceElement selection, final boolean moveUp) {
		return null;

	}

	@Override
	public boolean isEditable() {
		return true;
	}

	@Override
	public void addEntry(final Object entry, final boolean isInput, final int index, final CompoundCommand cmd) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeEntry(final Object entry, final CompoundCommand cmd) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeCompoundCommand(final CompoundCommand cmd) {
		// TODO Auto-generated method stub

	}

	public void initTypeSelection(final DataTypeLibrary dataTypeLib) {
		final List<String> elementaryTypes = dataTypeLib.getDataTypesSorted().stream()
				.filter(type -> !(type instanceof StructuredType)).map(DataType::getName).collect(Collectors.toList());
		typeSelection.put("Elementary Types", elementaryTypes); //$NON-NLS-1$
		final List<String> structuredTypes = dataTypeLib.getDataTypesSorted().stream()
				.filter(StructuredType.class::isInstance).map(DataType::getName).collect(Collectors.toList());
		typeSelection.put("Structured Types", structuredTypes); //$NON-NLS-1$
	}

}
