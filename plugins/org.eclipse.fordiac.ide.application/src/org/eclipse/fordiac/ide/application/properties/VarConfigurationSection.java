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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnProvider;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.CheckBoxConfigurationNebula;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class VarConfigurationSection extends AbstractSection {

	private static final int ONE_COLUMN = 1;
	private static final int NAME = 0;
	private static final int INITIAL_VALUE = 3;
	private static final int COMMENT = 2;
	public static final int VISIBLE = 4;

	private NatTable inputTable;
	private VarConfigDeclarationListProvider inputDataProvider;

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
				new VarConfigColumnDataProvider(), VarConfigDeclarationListProvider.getEditableRule());

		inputTable.addConfiguration(new CheckBoxConfigurationNebula());
		inputTable.configure();
		GridDataFactory.fillDefaults().grab(true, true).applyTo(inputComposite);

		tableSectionComposite.layout();
	}

	private void configureDataLayerLabels(final DataLayer dataLayer, final boolean isInput) {
		dataLayer.setConfigLabelAccumulator((configLabels, columnPosition, rowPosition) -> {
			VarDeclaration rowItem = null;
			final String defaultComment = null;
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
	protected INamedElement getInputType(final Object input) {
		if (input instanceof FBNetworkEditPart) {
			return ((FBNetworkEditPart) input).getModel().getApplication();
		}
		if (input instanceof INamedElement) {
			return ((INamedElement) input);
		}
		return null;
	}

	@Override
	protected INamedElement getType() {
		if ((type instanceof Application) || (type instanceof FB) || (type instanceof SubApp)
				|| (type instanceof CFBInstance)) {
			return (INamedElement) type;
		}
		return null;
	}

	@Override
	protected CommandStack getCommandStack(final IWorkbenchPart part, final Object input) {
		super.getCommandStack(part, input);
		if (input instanceof FBNetworkEditPart) {
			AutomationSystem automationsys = (AutomationSystem) ((FBNetworkEditPart) input).getModel().eContainer()
					.eContainer();
			return automationsys.getCommandStack();
		}
		if (input instanceof EObject) {
			EObject root = EcoreUtil.getRootContainer((EObject) input);
			if (root instanceof AutomationSystem) {
				return ((AutomationSystem) root).getCommandStack();
			}
			if (root instanceof FBType) {
				return null;
			}
		}
		return null;
	}
	
	@Override
	protected void setInputCode() {
		// Nothing for now
	}

	@Override
	protected void setInputInit() {
		inputDataProvider.setInput(getType());
		inputTable.refresh();

	}

	private static class VarConfigDeclarationListProvider extends ListDataProvider<VarDeclaration> {

		public VarConfigDeclarationListProvider(final VarConfigurationSection  section, final List<VarDeclaration> list) {
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
			final List<VarDeclaration> finallist = new ArrayList<>();
			getListVarConfig(finallist, inputElement);
			this.list = finallist;
		}
		private void getListVarConfig(final List<VarDeclaration> list, final Object obj) {
			EcoreUtil.getAllProperContents((EObject) obj, true).forEachRemaining(item -> {
				if ((item instanceof VarDeclaration) && (((VarDeclaration) item).isVarConfig())) {
					list.add((VarDeclaration) item);
				}
			});
		}
		public static IEditableRule getEditableRule() {
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

	public static class VarConfigDeclarationColumnAccessor extends VarDeclarationColumnAccessor {
		private VarConfigurationSection varSection;
		public VarConfigDeclarationColumnAccessor(VarConfigurationSection section) {
			super(section);
			varSection = section;
		}
		@Override
		public int getColumnCount() {
			return 5;
		}
		@Override
		public Object getDataValue(final VarDeclaration rowObject, final int columnIndex) {
			if (columnIndex == VISIBLE) {
				return Boolean.valueOf(rowObject.isVisible());
			} else if (columnIndex == NAME) {
				final INamedElement typeSection = (this.varSection).getType();
				if (typeSection instanceof Application) {
					return rowObject.getQualifiedName();
				} else {
					
					  String name = rowObject.getQualifiedName();
					  String typeName = varSection.getType().getQualifiedName();
					  if(name.startsWith(typeName + ".")) {
					    name = name.substring(typeName.length() + 1);
					  }
					  return name;
				}
			} else {
				return super.getDataValue(rowObject, columnIndex);
			}
		}
	}
	public static class VarConfigColumnDataProvider extends VarDeclarationColumnProvider {
		@Override
		public Object getDataValue(final int columnIndex, final int rowIndex) {
			if (columnIndex == NAME) {
				return FordiacMessages.Constants;
			}
			if (columnIndex == VISIBLE) {
				return FordiacMessages.Visible;
			}
			return super.getDataValue(columnIndex, rowIndex);
		}
	}
}