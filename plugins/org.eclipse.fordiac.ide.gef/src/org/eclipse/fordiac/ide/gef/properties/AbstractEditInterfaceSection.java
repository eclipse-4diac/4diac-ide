/*******************************************************************************
 * Copyright (c) 2017, 2024 fortiss GmbH, Johannes Kepler University Linz,
 *                          Martin Erich Jobst
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
 *                - lock editing for function FBs
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderToolbarWidget;
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;
import org.eclipse.fordiac.ide.ui.widget.IChangeableRowDataProvider;
import org.eclipse.fordiac.ide.ui.widget.ISelectionProviderSection;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.fordiac.ide.ui.widget.SelectionProviderProxy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.selection.RowPostSelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class AbstractEditInterfaceSection<T extends IInterfaceElement> extends AbstractSection
		implements I4diacNatTableUtil, ISelectionProviderSection {

	protected IChangeableRowDataProvider<T> inputProvider;
	protected NatTable inputTable;
	private AddDeleteReorderToolbarWidget inputButtons;
	private RowPostSelectionProvider<T> inputSelectionProvider;

	protected IChangeableRowDataProvider<T> outputProvider;
	protected NatTable outputTable;
	private AddDeleteReorderToolbarWidget outputButtons;
	private RowPostSelectionProvider<T> outputSelectionProvider;

	private SelectionProvider selectionProvider;

	protected abstract CreationCommand newCreateCommand(IInterfaceElement selection, boolean isInput);

	protected abstract CreationCommand newInsertCommand(IInterfaceElement selection, boolean isInput, int index);

	protected abstract DeleteInterfaceCommand newDeleteCommand(IInterfaceElement selection);

	protected abstract ChangeInterfaceOrderCommand newOrderCommand(IInterfaceElement selection, boolean moveUp);

	protected abstract void setupOutputTable(Group outputsGroup);

	protected abstract void setupInputTable(Group inputsGroup);

	@Override
	protected abstract INamedElement getInputType(Object input);

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

		inputButtons = new AddDeleteReorderToolbarWidget();
		outputButtons = new AddDeleteReorderToolbarWidget();

		if (isShowTableEditButtons()) {
			inputButtons.createControls(inputsGroup, getWidgetFactory());
			outputButtons.createControls(outputsGroup, getWidgetFactory());
		}

		setupInputTable(inputsGroup);
		setupOutputTable(outputsGroup);

		if (isShowTableEditButtons()) {
			configureButtonList(inputButtons, inputTable, true);
			configureButtonList(outputButtons, outputTable, false);
		}

		inputSelectionProvider = new RowPostSelectionProvider<>(inputTable,
				NatTableWidgetFactory.getSelectionLayer(inputTable), inputProvider, false);
		outputSelectionProvider = new RowPostSelectionProvider<>(outputTable,
				NatTableWidgetFactory.getSelectionLayer(outputTable), outputProvider, false);
		selectionProvider = new SelectionProvider();
		inputTable.addFocusListener(selectionProvider);
		outputTable.addFocusListener(selectionProvider);
	}

	private void configureButtonList(final AddDeleteReorderListWidget buttons, final NatTable table,
			final boolean inputs) {
		buttons.bindToTableViewer(table, this, ref -> newCreateCommand((IInterfaceElement) ref, inputs),
				ref -> newDeleteCommand((IInterfaceElement) ref), ref -> newOrderCommand((IInterfaceElement) ref, true),
				ref -> newOrderCommand((IInterfaceElement) ref, false));
	}

	@Override
	public void dispose() {
		super.dispose();
		if (inputButtons != null) {
			inputButtons.dispose();
		}
		if (outputButtons != null) {
			outputButtons.dispose();
		}
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
	protected void performRefresh() {
		setTableInput();
		inputTable.refresh();
		outputTable.refresh();
	}

	@Override
	protected void performRefreshAnnotations() {
		inputTable.refresh(false);
		outputTable.refresh(false);
	}

	protected void setTableInput() {
		setTableInput(getInterface());
		if (isShowTableEditButtons()) {
			inputButtons.setEnabled(isEditable());
			outputButtons.setEnabled(isEditable());
		}
	}

	protected abstract void setTableInput(final InterfaceList il);

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

	private final Adapter interfaceAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			notifiyRefresh();
		}
	};

	@Override
	protected void addContentAdapter() {
		super.addContentAdapter();
		if (isEditable()) {
			final InterfaceList interfaceList = getInterface();
			if (interfaceList != null) {
				interfaceList.eAdapters().add(interfaceAdapter);
			}
		}
	}

	@Override
	protected void removeContentAdapter() {
		super.removeContentAdapter();
		if (isEditable()) {
			final InterfaceList interfaceList = getInterface();
			if (interfaceList != null) {
				getInterface().eAdapters().remove(interfaceAdapter);
			}
		}
	}

	protected abstract InterfaceList getInterface();

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

	@SuppressWarnings("static-method") // subclasses may override
	public Command onNameChange(final IInterfaceElement rowObject, final String newValue) {
		return ChangeNameCommand.forName(rowObject, newValue);
	}

	@Override
	public SelectionProvider getSelectionProvider() {
		return selectionProvider;
	}

	private final IEditableRule sectionEditableRule = new IEditableRule() {

		@Override
		public boolean isEditable(final int columnIndex, final int rowIndex) {
			return AbstractEditInterfaceSection.this.isEditable();
		}

		@Override
		public boolean isEditable(final ILayerCell cell, final IConfigRegistry configRegistry) {
			return AbstractEditInterfaceSection.this.isEditable();
		}
	};

	private class SelectionProvider extends SelectionProviderProxy implements FocusListener {

		@Override
		public void focusGained(final FocusEvent e) {
			if (e.widget == inputTable) {
				setDelegate(inputSelectionProvider);
			} else if (e.widget == outputTable) {
				setDelegate(outputSelectionProvider);
			}
		}

		@Override
		public void focusLost(final FocusEvent e) {
			// do nothing
		}
	}
}
