/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *               
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Hesam Rezaee
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnProvider;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.CheckBoxConfigurationNebula;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class VarConfigurationSection extends AbstractSection {

	private static final int ONE_COLUMN = 1;
	private static final int NAME = 0;
	private static final int TYPE = 1;
	private static final int INITIAL_VALUE = 3;
	private static final int COMMENT = 2;
	public static final int VISIBLE = 4;

	public static IColumnAccessor<VarDeclaration> columnAccessor;
	private NatTable inputTable;
	private VarConfigDeclarationListProvider inputDataProvider;
	IAction[] defaultCopyPasteCut = new IAction[3];

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createTableSection(parent);
	}

	private void createTableSection(final Composite parent) {
		final Composite tableSectionComposite = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(ONE_COLUMN).applyTo(tableSectionComposite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tableSectionComposite);

		final Group inputComposite = getWidgetFactory().createGroup(tableSectionComposite,
				Messages.CommentPropertySection_DataInputs);

		inputComposite.setText(Messages.CommentPropertySection_DataInputs);
		inputComposite.setLayout(new GridLayout(ONE_COLUMN, false));

		inputDataProvider = new VarConfigDeclarationListProvider(this, null);

		final DataLayer inputDataLayer = new DataLayer(inputDataProvider);
		configureDataLayerLabels(inputDataLayer, true);

		inputTable = NatTableWidgetFactory.createNatTable(inputComposite, inputDataLayer,
				new VarConfigColumnDataProvider(), inputDataProvider.getEditableRule());

		inputTable.addConfiguration(new CheckBoxConfigurationNebula());
		inputTable.configure();
		GridDataFactory.fillDefaults().grab(true, true).applyTo(inputComposite);

		tableSectionComposite.layout();
	}

	private void configureDataLayerLabels(final DataLayer dataLayer, final boolean isInput) {
		dataLayer.setConfigLabelAccumulator((configLabels, columnPosition, rowPosition) -> {
			VarDeclaration rowItem = null;
			String defaultComment = null;
			if (isInput) {
				rowItem = inputDataProvider.getRowObject(rowPosition);
				if (columnPosition == INITIAL_VALUE && rowItem.getValue().hasError()) {
					configLabels.addLabelOnTop(NatTableWidgetFactory.ERROR_CELL);
				}
			}
			if (columnPosition == INITIAL_VALUE && !InitialValueHelper.hasInitalValue(rowItem)
					|| columnPosition == COMMENT && defaultComment != null
							&& rowItem.getComment().equals(defaultComment)) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.DEFAULT_CELL);
			}
			if (columnPosition == NAME || columnPosition == COMMENT) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
			}
			if (columnPosition == VISIBLE) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.CHECKBOX_CELL);
			}

		});
	}

	@Override
	protected Application getInputType(Object input) {
		if (input instanceof FBNetworkEditPart) {
			return ((FBNetworkEditPart) input).getModel().getApplication();
		}
		return null;
	}

	@Override
	protected EObject getType() {
		if (type instanceof Application) {
			return (EObject) type;
		}
		return null;
	}

	@Override
	protected void setInputCode() {
	}

	@Override
	protected void setInputInit() {
		inputDataProvider.setInput(getType());
		inputTable.refresh();

	}

	private class VarConfigDeclarationListProvider extends ListDataProvider<VarDeclaration> {

		public VarConfigDeclarationListProvider(final AbstractSection section, final List<VarDeclaration> list) {
			super(list, new VarConfigDeclarationColumnAccessor(section));
		}

		@Override
		public int getRowCount() {
			if (this.list != null) {
				return super.getRowCount();
			}
			return 0;
		}

		public void setInput(final Object inputElement) {
			List<VarDeclaration> finallist = new ArrayList<VarDeclaration>();
			if (inputElement instanceof Application) {
				for (FBNetworkElement networkElemnt : ((Application) inputElement).getFBNetwork()
						.getNetworkElements()) {
					if (networkElemnt instanceof FB) {
						VarConfigExtractFunction(finallist, networkElemnt);
					} else if (networkElemnt instanceof SubApp) {
						VarConfigExtractFunction(finallist, networkElemnt);
						for (FBNetworkElement subappNetworkElemnet : ((SubApp) networkElemnt).getSubAppNetwork()
								.getNetworkElements()) {
							VarConfigExtractFunction(finallist, subappNetworkElemnet);
						}
					}
					this.list = finallist;

				}
			}
		}

		public void VarConfigExtractFunction(final List<VarDeclaration> list, final FBNetworkElement networkElemnt) {
			for (VarDeclaration inputVar : ((FBNetworkElement) networkElemnt).getInterface().getInputVars()) {
				if (inputVar.isVarConfig()) {
					list.add(inputVar);
				}
			}
		}

		public IEditableRule getEditableRule() {
			return new IEditableRule() {
				@Override
				public boolean isEditable(final int columnIndex, final int rowIndex) {
					return (columnIndex == INITIAL_VALUE) || columnIndex == COMMENT;
				}

				@Override
				public boolean isEditable(final ILayerCell cell, final IConfigRegistry configRegistry) {
					return (cell.getColumnIndex() == INITIAL_VALUE) || cell.getColumnIndex() == COMMENT;
				}
			};
		}
	}

	private class VarConfigDeclarationColumnAccessor extends VarDeclarationColumnAccessor {

		public VarConfigDeclarationColumnAccessor(AbstractSection section) {
			super(section);
		}

		public Object getDataValue(final VarDeclaration rowObject, final int columnIndex) {
			if (columnIndex == VISIBLE) {
				return rowObject.isVisible();
			} else if (columnIndex == NAME) {
				if (rowObject.eContainer().eContainer() instanceof FB) {
					final FB fb = (FB) rowObject.eContainer().eContainer();
					return fb.getName() + "." + rowObject.getName();
				} else if (rowObject.eContainer().eContainer() instanceof SubApp) {
					final SubApp subapp = (SubApp) rowObject.eContainer().eContainer();
					return subapp.getName() + "." + rowObject.getName();
				} else {
					return null;
				}
			} else {
				return super.getDataValue(rowObject, columnIndex);
			}
		}

		public void setDataValue(final VarDeclaration rowObject, final int columnIndex, final Object newValue) {
			super.setDataValue(rowObject, columnIndex, newValue);
		}
	}

	public class VarConfigColumnDataProvider extends VarDeclarationColumnProvider {

		public Object getDataValue(final int columnIndex, final int rowIndex) {
			if (columnIndex == NAME) {
				return FordiacMessages.Constants;
			} else {
				return super.getDataValue(columnIndex, rowIndex);
			}
		}

	}

}
