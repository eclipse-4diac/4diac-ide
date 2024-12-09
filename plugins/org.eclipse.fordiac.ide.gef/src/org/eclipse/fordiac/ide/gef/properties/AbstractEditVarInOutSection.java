/*******************************************************************************
 * Copyright (c) 2023, 2024 Primetals Technologies Austria GmbH
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
 *   Patrick Aigner - moved functionality to abstract class
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.TypeDeclarationEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderToolbarWidget;
import org.eclipse.fordiac.ide.ui.widget.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;
import org.eclipse.fordiac.ide.ui.widget.IChangeableRowDataProvider;
import org.eclipse.fordiac.ide.ui.widget.ISelectionProviderSection;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.selection.RowPostSelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class AbstractEditVarInOutSection extends AbstractSection
		implements I4diacNatTableUtil, ISelectionProviderSection {
	protected IChangeableRowDataProvider<VarDeclaration> inputProvider;
	protected NatTable inputTable;
	protected AddDeleteReorderToolbarWidget inputButtons;
	private RowPostSelectionProvider<VarDeclaration> inputSelectionProvider;

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

		inputButtons = new AddDeleteReorderToolbarWidget();

		if (isShowTableEditButtons()) {
			inputButtons.createControls(inputsGroup, getWidgetFactory());
		}
		setupInputTable(inputsGroup);

		if (isShowTableEditButtons()) {
			configureButtonList(inputButtons, inputTable);
		}

		inputSelectionProvider = new RowPostSelectionProvider<>(inputTable,
				NatTableWidgetFactory.getSelectionLayer(inputTable), inputProvider, false);
	}

	public void setupInputTable(final Composite parent) {
		inputProvider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this));
		final DataLayer inputDataLayer = new VarDeclarationDataLayer(inputProvider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS);
		inputDataLayer.setConfigLabelAccumulator(
				new VarDeclarationConfigLabelAccumulator(inputProvider, this::getAnnotationModel));
		final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(
				VarDeclarationTableColumn.DEFAULT_COLUMNS);
		inputTable = NatTableWidgetFactory.createRowNatTable(parent, inputDataLayer, columnProvider,
				getSectionEditableRule(), null, this, true);
		inputTable.addConfiguration(new InitialValueEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new TypeDeclarationEditorConfiguration(inputProvider));

		inputTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, this));
		inputTable.configure();
	}

	protected void configureButtonList(final AddDeleteReorderListWidget buttons, final NatTable table) {
		buttons.bindToTableViewer(table, this, ref -> newCreateCommand((IInterfaceElement) ref),
				ref -> newDeleteCommand((IInterfaceElement) ref), ref -> newOrderCommand((IInterfaceElement) ref, true),
				ref -> newOrderCommand((IInterfaceElement) ref, false));
	}

	protected abstract CreationCommand newCreateCommand(final IInterfaceElement ie);

	protected abstract CreationCommand newInsertCommand(final IInterfaceElement ie, final int index);

	protected static DeleteInterfaceCommand newDeleteCommand(final IInterfaceElement selection) {
		return new DeleteInterfaceCommand(selection);
	}

	protected static ChangeInterfaceOrderCommand newOrderCommand(final IInterfaceElement selection,
			final boolean moveUp) {
		return new ChangeInterfaceOrderCommand(selection, moveUp);
	}

	@Override
	protected void setInputCode() {
		// not needed
	}

	@Override
	protected void performRefresh() {
		setTableInput();
		inputTable.refresh();
	}

	@Override
	protected void performRefreshAnnotations() {
		inputTable.refresh(false);
	}

	protected abstract void setTableInput();

	@Override
	protected void setInputInit() {
		// nothing to do
	}

	@Override
	public void dispose() {
		super.dispose();
		if (inputButtons != null) {
			inputButtons.dispose();
		}
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

	protected static EList<VarDeclaration> getVarInOutList(final InterfaceList interfaceList) {
		return interfaceList.getInOutVars();
	}

	@SuppressWarnings("static-method")
	public boolean isShowTableEditButtons() {
		return true;
	}

	@Override
	public boolean isEditable() {
		return !(EcoreUtil.getRootContainer(getType()) instanceof FunctionFBType);
	}

	protected IEditableRule getSectionEditableRule() {
		return sectionEditableRule;
	}

	private final IEditableRule sectionEditableRule = new IEditableRule() {

		@Override
		public boolean isEditable(final int columnIndex, final int rowIndex) {
			return AbstractEditVarInOutSection.this.isEditable();
		}

		@Override
		public boolean isEditable(final ILayerCell cell, final IConfigRegistry configRegistry) {
			return AbstractEditVarInOutSection.this.isEditable();
		}
	};

	@Override
	public ISelectionProvider getSelectionProvider() {
		return inputSelectionProvider;
	}
}
