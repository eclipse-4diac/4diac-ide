/*******************************************************************************
 * Copyright (c) 2017, 2018 fortiss GmbH
 * 				 2018, 2019, 2020 Johannes Kepler University Linz
 *               2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl - initial implementation
 *   Alois Zoitl - moved group buttons to the top left, multi-line selection in lists,
 *               code clean-up
 *   Bianca Wiesmayr - extract table creation, cleanup, new read-only derived sections
 *   Alois Zoitl - extracted helper for ComboCellEditors that unfold on activation
 *               - cleaned command stack handling for property sections
 *   Daniel Lindhuber - added copy/paste and the context menu
 *   				  - made typedropdown methods overrideable
 *   Martin Jobst - add initial value cell editor support
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.ui.widgets.OpenStructMenu;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.IConfigLabelAccumulator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class AbstractEditInterfaceSection<T extends IInterfaceElement> extends AbstractSection
implements I4diacNatTableUtil {

	protected ListDataProvider<T> inputProvider;
	protected NatTable inputTable;

	protected ListDataProvider<T> outputProvider;
	protected NatTable outputTable;

	protected Map<String, List<String>> typeSelection = new HashMap<>();

	protected abstract CreateInterfaceElementCommand newCreateCommand(IInterfaceElement selection, boolean isInput);

	protected abstract CreateInterfaceElementCommand newInsertCommand(IInterfaceElement selection, boolean isInput,
			int index);

	protected abstract DeleteInterfaceCommand newDeleteCommand(IInterfaceElement selection);

	protected abstract ChangeInterfaceOrderCommand newOrderCommand(IInterfaceElement selection, boolean moveUp);

	protected abstract String[] fillTypeCombo();

	protected abstract void setupOutputTable(Group outputsGroup);

	protected abstract void setupInputTable(Group inputsGroup);

	@Override
	protected abstract INamedElement getInputType(Object input);

	@SuppressWarnings("static-method") // this method allows sub-classes to provide own change type commands, e.g.,
	// subapps
	protected ChangeDataTypeCommand newChangeTypeCommand(final VarDeclaration data, final DataType newType) {
		return new ChangeDataTypeCommand(data, newType);
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(3, false));
		createInputOutputEdit(parent);
	}

	private void createInputOutputEdit(final Composite parent) {
		final Group inputsGroup = getWidgetFactory().createGroup(parent, "Inputs"); //$NON-NLS-1$
		inputsGroup.setLayout(new GridLayout(2, false));
		inputsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Group outputsGroup = getWidgetFactory().createGroup(parent, "Outputs"); //$NON-NLS-1$
		outputsGroup.setLayout(new GridLayout(2, false));
		outputsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final AddDeleteReorderListWidget inputButtons = new AddDeleteReorderListWidget();
		final AddDeleteReorderListWidget outputButtons = new AddDeleteReorderListWidget();

		if (isEditable()) {
			inputButtons.createControls(inputsGroup, getWidgetFactory());
			outputButtons.createControls(outputsGroup, getWidgetFactory());
		}
		setupInputTable(inputsGroup);
		setupOutputTable(outputsGroup);

		if (isEditable()) {
			configureButtonList(inputButtons, inputTable, true);
			configureButtonList(outputButtons, outputTable, false);
		}
	}

	private void configureButtonList(final AddDeleteReorderListWidget buttons, final NatTable table,
			final boolean inputs) {
		buttons.bindToTableViewer(table, this, ref -> newCreateCommand((IInterfaceElement) ref, inputs),
				ref -> newDeleteCommand((IInterfaceElement) ref), ref -> newOrderCommand((IInterfaceElement) ref, true),
				ref -> newOrderCommand((IInterfaceElement) ref, false));
	}

	// can be overridden by subclasses to use a different type dropdown
	protected CellEditor createTypeCellEditor(final TableViewer viewer) {
		return ComboBoxWidgetFactory.createComboBoxCellEditor(viewer.getTable(), fillTypeCombo(), SWT.READ_ONLY);
	}

	// subclasses need to override this method if they use a different type dropdown
	@SuppressWarnings("static-method")
	protected Object getTypeValue(final Object element, final TableViewer viewer, final int TYPE_COLUMN_INDEX) {
		final String type = ((IInterfaceElement) element).getTypeName();
		final List<String> items = Arrays
				.asList(((ComboBoxCellEditor) viewer.getCellEditors()[TYPE_COLUMN_INDEX]).getItems());
		return Integer.valueOf(items.indexOf(type));
	}

	// subclasses need to override this method if they use a different type dropdown
	protected Command createChangeDataTypeCommand(final VarDeclaration data, final Object value, final TableViewer viewer) {
		final String dataTypeName = ((ComboBoxCellEditor) viewer.getCellEditors()[1]).getItems()[((Integer) value).intValue()];
		return newChangeTypeCommand(data, getDataTypeLib().getType(dataTypeName));
	}

	@Override
	protected void setInputCode() {
		// nothing to be done here
	}

	@Override
	protected void setInputInit() {
		// nothing to be done here
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
		outputTable.refresh();
	}

	protected void setTableInput() {
		if (getType() instanceof FBNetworkElement) {
			setTableInputFbNetworkElement((FBNetworkElement) getType());
		}

		if (getType() instanceof FBType) {
			setTableInputFBType((FBType) getType());
		}

		if (isEditable()) {
			initTypeSelection(getDataTypeLib());
		}
	}

	protected abstract void setTableInputFBType(final FBType type);

	protected abstract void setTableInputFbNetworkElement(final FBNetworkElement element);


	@SuppressWarnings("static-method")
	protected int getInsertingIndex(final IInterfaceElement interfaceElement,
			final EList<? extends IInterfaceElement> interfaceList) {
		return interfaceList.indexOf(interfaceElement) + 1;
	}

	protected abstract int getInsertingIndex(IInterfaceElement interfaceElement, boolean isInput);

	@SuppressWarnings("static-method")
	protected String getCreationName(final IInterfaceElement interfaceElement) {
		return (null != interfaceElement) ? interfaceElement.getName() : null;
	}

	@Override
	public void executeCompoundCommand(final CompoundCommand cmd) {
		executeCommand(cmd);
		inputTable.refresh();
		outputTable.refresh();
	}

	public DataLayer setupDataLayer(final ListDataProvider<T> outputProvider) {
		final DataLayer dataLayer = new DataLayer(outputProvider);
		final IConfigLabelAccumulator labelAcc = dataLayer.getConfigLabelAccumulator();

		dataLayer.setConfigLabelAccumulator((configLabels, columnPosition, rowPosition) -> {
			if (labelAcc != null) {
				labelAcc.accumulateConfigLabels(configLabels, columnPosition, rowPosition);
			}
			if (isEditable() && columnPosition == I4diacNatTableUtil.TYPE) {
				configLabels.addLabel(NatTableWidgetFactory.PROPOSAL_CELL);
			} else if (isEditable() && columnPosition == I4diacNatTableUtil.INITIAL_VALUE) {
				configLabels.addLabel(InitialValueEditorConfiguration.INITIAL_VALUE_CELL);
			} else if (columnPosition == I4diacNatTableUtil.NAME || columnPosition == I4diacNatTableUtil.COMMENT) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
			}
		});
		return dataLayer;
	}


	public void initTypeSelection(final DataTypeLibrary dataTypeLib) {
		final List<String> elementaryTypes = new ArrayList<>();
		dataTypeLib.getDataTypesSorted().stream().filter(type -> !(type instanceof StructuredType))
		.forEach(type -> elementaryTypes.add(type.getName()));
		typeSelection.put("Elementary Types", elementaryTypes); //$NON-NLS-1$

		final List<String> structuredTypes = new ArrayList<>();
		dataTypeLib.getDataTypesSorted().stream().filter(StructuredType.class::isInstance)
		.forEach(type -> structuredTypes.add(type.getName()));
		typeSelection.put("Structured Types", structuredTypes); //$NON-NLS-1$
	}

	// TODO reimplement
	private void createContextMenu(final TableViewer viewer) {
		OpenStructMenu.addTo(viewer);
	}
}
