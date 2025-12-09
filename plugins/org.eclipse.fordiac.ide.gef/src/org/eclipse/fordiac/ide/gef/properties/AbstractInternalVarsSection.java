/*******************************************************************************
 * Copyright (c) 2015, 2024 fortiss GmbH, Johannes Kepler University Linz,
 * 							Primetals Technologies Germany GmbH,
 * 							Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prankur Agarwal  - extracted from InternalVarsSection
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.Collections;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.TypeDeclarationEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.model.commands.change.ChangeVariableOrderCommand;
import org.eclipse.fordiac.ide.model.commands.insert.InsertVariableCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderToolbarWidget;
import org.eclipse.fordiac.ide.ui.widget.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;
import org.eclipse.fordiac.ide.ui.widget.IChangeableRowDataProvider;
import org.eclipse.fordiac.ide.ui.widget.ISelectionProviderSection;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.selection.RowPostSelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class AbstractInternalVarsSection extends AbstractSection
		implements I4diacNatTableUtil, ISelectionProviderSection {

	IAction[] defaultCopyPasteCut = new IAction[3];
	private TabbedPropertySheetPage tabbedPropertySheetPage;

	protected NatTable table;
	private AddDeleteReorderToolbarWidget buttons;
	protected IChangeableRowDataProvider<VarDeclaration> provider;
	private RowPostSelectionProvider<VarDeclaration> selectionProvider;

	@Override
	protected BaseFBType getType() {
		return (BaseFBType) type;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		this.tabbedPropertySheetPage = tabbedPropertySheetPage;

		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		buttons = new AddDeleteReorderToolbarWidget();
		buttons.createControls(composite, getWidgetFactory());

		createNatTable(composite);

		buttons.bindToTableViewer(table, this, this::newCreateCommand, this::newDeleteCommand,
				ref -> new ChangeVariableOrderCommand(getVarList(), (VarDeclaration) ref, true),
				ref -> new ChangeVariableOrderCommand(getVarList(), (VarDeclaration) ref, false));

		selectionProvider = new RowPostSelectionProvider<>(table, NatTableWidgetFactory.getSelectionLayer(table),
				provider, false);
	}

	protected void createNatTable(final Composite composite) {
		provider = new ChangeableListDataProvider<>(
				new VarDeclarationColumnAccessor(this, VarDeclarationTableColumn.DEFAULT_COLUMNS));
		final DataLayer dataLayer = new VarDeclarationDataLayer(provider, VarDeclarationTableColumn.DEFAULT_COLUMNS);
		final VarDeclarationConfigLabelAccumulator acc = new VarDeclarationConfigLabelAccumulator(provider,
				this::getAnnotationModel, VarDeclarationTableColumn.DEFAULT_COLUMNS);

		dataLayer.setConfigLabelAccumulator(acc);

		final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(
				VarDeclarationTableColumn.DEFAULT_COLUMNS);
		table = NatTableWidgetFactory.createRowNatTable(composite, dataLayer, columnProvider,
				IEditableRule.ALWAYS_EDITABLE, null, this, false);

		table.addConfiguration(new InitialValueEditorConfiguration(provider));
		table.addConfiguration(new TypeDeclarationEditorConfiguration(provider));
		table.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, this));
		table.configure();
	}

	protected abstract CreationCommand newCreateCommand(Object refElement);

	protected abstract Command newDeleteCommand(Object refElement);

	protected abstract EList<VarDeclaration> getVarList();

	protected Object getEntry(final int index) {
		return getVarList().get(index);
	}

	protected DataType getDataType() {
		final VarDeclaration varConst = getLastSelectedVariable();
		return (null != varConst) ? varConst.getType() : null;
	}

	protected VarDeclaration getLastSelectedVariable() {
		return (VarDeclaration) NatTableWidgetFactory.getLastSelectedVariable(table);
	}

	protected String getName() {
		final VarDeclaration varConst = getLastSelectedVariable();
		return (null != varConst) ? varConst.getName() : null;
	}

	@Override
	public void aboutToBeShown() {
		// this can be removed once copy/paste for old tables is no longer used
		final IActionBars bars = getActionBars();
		if (bars != null) {
			defaultCopyPasteCut[0] = bars.getGlobalActionHandler(ActionFactory.COPY.getId());
			bars.setGlobalActionHandler(ActionFactory.COPY.getId(), null);
			defaultCopyPasteCut[1] = bars.getGlobalActionHandler(ActionFactory.PASTE.getId());
			bars.setGlobalActionHandler(ActionFactory.PASTE.getId(), null);
			defaultCopyPasteCut[2] = bars.getGlobalActionHandler(ActionFactory.CUT.getId());
			bars.setGlobalActionHandler(ActionFactory.CUT.getId(), null);
			bars.updateActionBars();
		}

		super.aboutToBeShown();
	}

	@Override
	public void aboutToBeHidden() {
		// this can be removed once copy/paste for old tables is no longer used
		final IActionBars bars = getActionBars();
		if (bars != null) {
			bars.setGlobalActionHandler(ActionFactory.COPY.getId(), defaultCopyPasteCut[0]);
			bars.setGlobalActionHandler(ActionFactory.PASTE.getId(), defaultCopyPasteCut[1]);
			bars.setGlobalActionHandler(ActionFactory.CUT.getId(), defaultCopyPasteCut[2]);
			bars.updateActionBars();
		}

		super.aboutToBeHidden();
	}

	private IActionBars getActionBars() {
		if (tabbedPropertySheetPage != null && tabbedPropertySheetPage.getSite() != null) {
			return tabbedPropertySheetPage.getSite().getActionBars();
		}
		return null;
	}

	@Override
	protected Object getInputType(final Object input) {
		return BaseFBFilter.getFBTypeFromSelectedElement(input);
	}

	@Override
	protected void setInputInit() {
		final BaseFBType currentType = getType();
		provider.setInput(currentType != null ? getVarList() : Collections.emptyList());
		table.refresh();
	}

	@Override
	protected void setInputCode() {
		// nothing to do here
	}

	@Override
	protected void performRefresh() {
		table.refresh();
	}

	@Override
	protected void performRefreshAnnotations() {
		table.refresh(false);
	}

	@Override
	public void executeCompoundCommand(final CompoundCommand cmd) {
		executeCommand(cmd);
		table.refresh();
	}

	@Override
	public boolean isEditable() {
		return true;
	}

	@Override
	public ISelectionProvider getSelectionProvider() {
		return selectionProvider;
	}

	@Override
	public void dispose() {
		super.dispose();
		if (buttons != null) {
			buttons.dispose();
		}
	}

	@Override
	public void addEntry(final Object entry, final boolean isInput, final int index, final CompoundCommand cmd) {
		if (entry instanceof final VarDeclaration varEntry) {
			cmd.add(new InsertVariableCommand(getType(), getVarList(), varEntry, index));
		}
	}

	protected int getInsertionIndex() {
		final VarDeclaration varConst = getLastSelectedVariable();
		if (null == varConst) {
			return getVarList().size();
		}
		return getVarList().indexOf(varConst) + 1;
	}

}
