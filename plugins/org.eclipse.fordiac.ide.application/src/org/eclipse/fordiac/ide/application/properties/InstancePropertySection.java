/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH, Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - multline comments and cleanup
 *   Sebastian Hollersbacher - change to nebula NatTable
 *   Hesam Rezaee - Variable configuration for Global Constants
 *   Martin Jobst - add initial value cell editor support
 *   Dario Romano - fixed renaming bug for instances
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationVisibleEditableRule;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.widget.nattable.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.CheckBoxConfigurationNebula;
import org.eclipse.fordiac.ide.ui.widget.nattable.IChangeableRowDataProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.EditableRule;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class InstancePropertySection extends AbstractInstanceSection {

	private static final int ONE_COLUMN = 1;
	private static final int TWO_COLUMNS = 2;

	private NatTable inputTable;
	private NatTable outputTable;

	private IChangeableRowDataProvider<VarDeclaration> inputDataProvider;
	private IChangeableRowDataProvider<VarDeclaration> outputDataProvider;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		final Composite mainContainer = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(TWO_COLUMNS).equalWidth(true).applyTo(mainContainer);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(mainContainer);

		createFBInfoGroup(mainContainer);
		createTableSection(mainContainer);
	}

	@Override
	protected void performRefresh() {
		if (getType() != null) {
			super.performRefresh();

			final List<VarDeclaration> allInputs = new ArrayList<>();
			final InterfaceList fbInterface = getType().getInterface();
			allInputs.addAll(fbInterface.getInputVars());
			allInputs.addAll(fbInterface.getInOutVars());
			inputDataProvider.setInput(allInputs);

			final List<VarDeclaration> allOutputs = new ArrayList<>();
			allOutputs.addAll(fbInterface.getOutputVars());
			allOutputs.addAll(fbInterface.getOutMappedInOutVars());
			outputDataProvider.setInput(allOutputs);

			inputTable.refresh();
			outputTable.refresh();
		}
	}

	@Override
	protected void performRefreshAnnotations() {
		inputTable.refresh(false);
		outputTable.refresh(false);
	}

	protected void createTableSection(final Composite parent) {
		createInputTable(parent);
		createOutputTable(parent);
		parent.layout();
	}

	private void createInputTable(final Composite parent) {
		final Group inputComposite = getWidgetFactory().createGroup(parent, Messages.CommentPropertySection_DataInputs);
		inputComposite.setText(Messages.CommentPropertySection_DataInputs);
		inputComposite.setLayout(new GridLayout(ONE_COLUMN, false));

		final var columns = VarDeclarationTableColumn.DEFAULT_COLUMNS_VISIBLE_VARCONFIG;
		inputDataProvider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this, columns));

		final DataLayer inputDataLayer = new VarDeclarationDataLayer(inputDataProvider, columns);
		inputDataLayer.setConfigLabelAccumulator(
				new VarDeclarationConfigLabelAccumulator(inputDataProvider, this::getAnnotationModel, columns));

		final NatTableColumnProvider<VarDeclarationTableColumn> inputColumnProvider = new NatTableColumnProvider<>(
				columns);

		inputTable = NatTableWidgetFactory.createNatTable(inputComposite, inputDataLayer, inputColumnProvider,
				new VarDeclarationVisibleEditableRule(EditableRule.ALWAYS_EDITABLE, inputDataProvider, columns,
						VarDeclarationTableColumn.DEFAULT_EDITABLE));

		inputTable.addConfiguration(new CheckBoxConfigurationNebula());
		inputTable.addConfiguration(new InitialValueEditorConfiguration(inputDataProvider));
		inputTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(inputColumnProvider, this));
		inputTable.configure();

		GridDataFactory.fillDefaults().grab(true, true).applyTo(inputComposite);
	}

	private void createOutputTable(final Composite parent) {
		final Group outputComposite = getWidgetFactory().createGroup(parent,
				Messages.CommentPropertySection_DataOutputs);
		outputComposite.setText(Messages.CommentPropertySection_DataOutputs);
		outputComposite.setLayout(new GridLayout(ONE_COLUMN, false));

		final var columns = VarDeclarationTableColumn.defaultColumnsWith(VarDeclarationTableColumn.VISIBLE);
		outputDataProvider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this, columns));

		final DataLayer outputDataLayer = new VarDeclarationDataLayer(outputDataProvider, columns);
		outputDataLayer.setConfigLabelAccumulator(
				new VarDeclarationConfigLabelAccumulator(outputDataProvider, this::getAnnotationModel, columns));

		final NatTableColumnProvider<VarDeclarationTableColumn> outputColumnProvider = new NatTableColumnProvider<>(
				columns);

		outputTable = NatTableWidgetFactory.createNatTable(outputComposite, outputDataLayer, outputColumnProvider,
				new VarDeclarationVisibleEditableRule(EditableRule.ALWAYS_EDITABLE, outputDataProvider, columns,
						VarDeclarationTableColumn.DEFAULT_EDITABLE));

		outputTable.addConfiguration(new CheckBoxConfigurationNebula());
		outputTable.addConfiguration(new InitialValueEditorConfiguration(outputDataProvider));
		outputTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(outputColumnProvider, this));
		outputTable.configure();

		GridDataFactory.fillDefaults().grab(true, true).applyTo(outputComposite);
	}

	private void createFBInfoGroup(final Composite parent) {
		final Composite fbNameComp = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(TWO_COLUMNS).applyTo(fbNameComp);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, false).applyTo(fbNameComp);
		createNameInput(fbNameComp);

		final Composite fbCommentComp = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(TWO_COLUMNS).applyTo(fbCommentComp);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, false).applyTo(fbCommentComp);
		createCommentInput(fbCommentComp);
	}

	@Override
	protected Object getInputType(final Object input) {
		return InstanceSectionFilter.getFBNetworkElementFromSelectedElement(input);
	}

	protected final Adapter interfaceAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			notifiyRefresh();
		}
	};

	protected final Adapter fbnElementAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			notifiyRefresh();
		}
	};

	@Override
	protected void addContentAdapter() {
		// for performance reasons (we could have many children) do not call super here.
		if (getType() != null) {
			getType().eAdapters().add(fbnElementAdapter);
			getType().getInterface().eAdapters().add(interfaceAdapter);
		}
	}

	@Override
	protected void removeContentAdapter() {
		// for performance reasons (we could have many children) do not call super here.
		if (getType() != null) {
			getType().eAdapters().remove(fbnElementAdapter);
			getType().getInterface().eAdapters().remove(interfaceAdapter);
		}
	}

	@Override
	protected boolean shouldRefresh() {
		// as we have our own adapters we need our own shouldRefresh implementation
		return (null != getType()) && getType().eAdapters().contains(fbnElementAdapter) && !blockRefresh;
	}
}
