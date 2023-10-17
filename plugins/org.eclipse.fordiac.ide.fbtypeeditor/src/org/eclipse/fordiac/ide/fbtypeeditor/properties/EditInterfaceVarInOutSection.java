/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *                    Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *   Martin Jobst - lock editing for function FBs
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.TypeDeclarationEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateVarInOutCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteVarInOutCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;
import org.eclipse.fordiac.ide.ui.widget.IChangeableRowDataProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class EditInterfaceVarInOutSection extends AbstractSection implements I4diacNatTableUtil {

	private IChangeableRowDataProvider<VarDeclaration> inputProvider;
	private NatTable inputTable;
	private AddDeleteReorderListWidget inputButtons;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(3, false));
		createInputEdit(parent);
	}

	private void createInputEdit(final Composite parent) {
		final Composite inputsGroup = getWidgetFactory().createComposite(parent);
		inputsGroup.setLayout(new GridLayout(2, false));
		inputsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		inputButtons = new AddDeleteReorderListWidget();

		if (isShowTableEditButtons()) {
			inputButtons.createControls(inputsGroup, getWidgetFactory());
		}
		setupInputTable(inputsGroup);

		if (isShowTableEditButtons()) {
			configureButtonList(inputButtons, inputTable);
		}
	}

	public void setupInputTable(final Composite parent) {
		inputProvider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this));
		final DataLayer inputDataLayer = new VarDeclarationDataLayer(inputProvider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS);
		inputDataLayer.setConfigLabelAccumulator(new VarDeclarationConfigLabelAccumulator(inputProvider));
		inputTable = NatTableWidgetFactory.createRowNatTable(parent, inputDataLayer,
				new NatTableColumnProvider<>(VarDeclarationTableColumn.DEFAULT_COLUMNS), getSectionEditableRule(), null,
				this, true);
		inputTable.addConfiguration(new InitialValueEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new TypeDeclarationEditorConfiguration(inputProvider));
		inputTable.configure();
	}

	protected void configureButtonList(final AddDeleteReorderListWidget buttons, final NatTable table) {
		buttons.bindToTableViewer(table, this, ref -> newCreateCommand((IInterfaceElement) ref),
				ref -> newDeleteCommand((IInterfaceElement) ref), ref -> newOrderCommand((IInterfaceElement) ref, true),
				ref -> newOrderCommand((IInterfaceElement) ref, false));
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
		// not needed
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

	@SuppressWarnings("unchecked")
	protected void setTableInput() {
		inputProvider.setInput(getType().getInterfaceList().getInOutVars());
		if (isShowTableEditButtons()) {
			inputButtons.setCreateButtonEnablement(isEditable());
		}
	}

	@Override
	protected void setInputInit() {
		// nothing to do
	}

	private CreationCommand newCreateCommand(final IInterfaceElement ie) {
		return new CreateVarInOutCommand(getLastUsedDataType(getType().getInterfaceList(), ie),
				getType().getInterfaceList(), getInsertingIndex(ie));
	}

	private CreationCommand newInsertCommand(final IInterfaceElement ie, final int index) {
		return new CreateVarInOutCommand(ie, getType().getInterfaceList(), index);
	}

	private static DeleteInterfaceCommand newDeleteCommand(final IInterfaceElement selection) {
		return new DeleteVarInOutCommand((VarDeclaration) selection);
	}

	private static ChangeInterfaceOrderCommand newOrderCommand(final IInterfaceElement selection,
			final boolean moveUp) {
		return new ChangeInterfaceOrderCommand(selection, moveUp);
	}

	@Override
	public void addEntry(final Object entry, final boolean isInput, final int index, final CompoundCommand cmd) {
		if (entry instanceof final VarDeclaration varDec && varDec.isInOutVar()) {
			cmd.add(newInsertCommand(varDec, index));
		}
	}

	@Override
	public void removeEntry(final Object entry, final CompoundCommand cmd) {
		if (entry instanceof final VarDeclaration varDec && varDec.isInOutVar()) {
			cmd.add(newDeleteCommand((Event) entry));
		}
	}

	@Override
	public void executeCompoundCommand(final CompoundCommand cmd) {
		executeCommand(cmd);
		inputTable.refresh();
	}

	protected static DataType getLastUsedDataType(final InterfaceList interfaceList,
			final IInterfaceElement interfaceElement) {
		if (null != interfaceElement) {
			return interfaceElement.getType();
		}
		final EList<VarDeclaration> dataList = getVarInOutList(interfaceList);
		if (!dataList.isEmpty()) {
			return dataList.get(dataList.size() - 1).getType();
		}
		return IecTypes.ElementaryTypes.BOOL; // bool is default
	}

	protected int getInsertingIndex(final IInterfaceElement interfaceElement) {
		if (null != interfaceElement) {
			final InterfaceList interfaceList = (InterfaceList) interfaceElement.eContainer();
			return getInsertingIndex(interfaceElement, getVarInOutList(interfaceList));
		}
		return -1;
	}

	@SuppressWarnings("static-method")
	protected int getInsertingIndex(final IInterfaceElement interfaceElement,
			final EList<? extends IInterfaceElement> interfaceList) {
		return interfaceList.indexOf(interfaceElement) + 1;
	}

	private static EList<VarDeclaration> getVarInOutList(final InterfaceList interfaceList) {
		return interfaceList.getInOutVars();
	}

	@Override
	public boolean isEditable() {
		return !(EcoreUtil.getRootContainer(getType()) instanceof FunctionFBType);
	}

	@SuppressWarnings("static-method") // should be overridden by subclasses
	public boolean isShowTableEditButtons() {
		return true;
	}

	protected IEditableRule getSectionEditableRule() {
		return sectionEditableRule;
	}

	private final IEditableRule sectionEditableRule = new IEditableRule() {

		@Override
		public boolean isEditable(final int columnIndex, final int rowIndex) {
			return EditInterfaceVarInOutSection.this.isEditable();
		}

		@Override
		public boolean isEditable(final ILayerCell cell, final IConfigRegistry configRegistry) {
			return EditInterfaceVarInOutSection.this.isEditable();
		}
	};
}
