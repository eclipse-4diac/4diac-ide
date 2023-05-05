/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prankur Agarwal  - initial implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.TypeDeclarationEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationListProvider;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.action.IAction;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.IConfigLabelAccumulator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class AbstractInternalVarsSection extends AbstractSection implements I4diacNatTableUtil {

	IAction[] defaultCopyPasteCut = new IAction[3];
	private TabbedPropertySheetPage tabbedPropertySheetPage;

	protected VarDeclarationListProvider provider;
	protected NatTable table;
	protected Map<String, List<String>> typeSelection = new HashMap<>();

	@Override
	protected BaseFBType getType() {
		return (BaseFBType) type;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createVarControl(parent);
		this.tabbedPropertySheetPage = tabbedPropertySheetPage;
	}

	public abstract void createVarControl(final Composite parent);

	public abstract Object getEntry(final int index);

	protected DataType getDataType() {
		final VarDeclaration varConst = getLastSelectedVariable();
		return (null != varConst) ? varConst.getType() : null;
	}

	protected VarDeclaration getLastSelectedVariable() {
		return (VarDeclaration) provider.getLastSelectedVariable(table);
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
	protected void setInputCode() {
		// nothing to do here
	}

	@Override
	public void refresh() {
		table.refresh();
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

	public void initTypeSelection(final DataTypeLibrary dataTypeLib) {
		final List<String> elementaryTypes = dataTypeLib.getDataTypesSorted().stream()
				.filter(type -> !(type instanceof StructuredType))
				.map(DataType::getName).collect(Collectors.toList());
		typeSelection.put("Elementary Types", elementaryTypes); //$NON-NLS-1$

		final List<String> structuredTypes = dataTypeLib.getDataTypesSorted().stream()
				.filter(StructuredType.class::isInstance).map(DataType::getName).collect(Collectors.toList());
		typeSelection.put("Structured Types", structuredTypes); //$NON-NLS-1$
	}

	protected DataLayer setupDataLayer() {
		final DataLayer dataLayer = new DataLayer(provider);
		final IConfigLabelAccumulator dataLayerLabelAccumulator = dataLayer.getConfigLabelAccumulator();
		dataLayer.setConfigLabelAccumulator((configLabels, columnPosition, rowPosition) -> {
			if (dataLayerLabelAccumulator != null) {
				dataLayerLabelAccumulator.accumulateConfigLabels(configLabels, columnPosition, rowPosition);
			}
			configureLabels(configLabels, columnPosition, rowPosition);
		});

		return dataLayer;
	}

	protected void configureLabels(final LabelStack configLabels, final int columnPosition, final int rowPosition) {
		final VarDeclaration rowItem = provider.getRowObject(rowPosition);
		switch (columnPosition) {
		case I4diacNatTableUtil.TYPE:
			if (rowItem.getType() instanceof ErrorMarkerDataType) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.ERROR_CELL);
			}
			configLabels.addLabel(TypeDeclarationEditorConfiguration.TYPE_DECLARATION_CELL);
			break;
		case I4diacNatTableUtil.INITIAL_VALUE:
			if (rowItem.getValue() != null && rowItem.getValue().hasError()) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.ERROR_CELL);
			}
			configLabels.addLabel(InitialValueEditorConfiguration.INITIAL_VALUE_CELL);
			break;
		case I4diacNatTableUtil.NAME:
		case I4diacNatTableUtil.COMMENT:
			configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
			break;
		default:
			break;
		}
	}

}
