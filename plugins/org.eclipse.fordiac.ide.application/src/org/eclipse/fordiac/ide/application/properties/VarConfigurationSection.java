/*******************************************************************************
 * Copyright (c) 2022,2023 Primetals Technologies Austria GmbH
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
 *   Martin Melik Merkumians - remove dependencies and unproper inheritance from
 *   	other concrete classes
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeVarConfigurationCommand;
import org.eclipse.fordiac.ide.model.commands.change.HidePinCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.CheckBoxConfigurationNebula;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class VarConfigurationSection extends AbstractSection {

	private static final int NAME_COL_ID = 0;
	private static final int TYPE_COL_ID = 1;
	private static final int COMMENT_COL_ID = 2;
	private static final int INITIAL_VALUE_COL_ID = 3;
	public static final int VISIBLE_COL_ID = 4;
	public static final int VAR_CONFIG_COL_ID = 5;

	private NatTable inputTable;
	private VarConfigDeclarationListProvider inputDataProvider;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createTableSection(parent);
	}

	private void createTableSection(final Composite parent) {
		final Composite tableSectionComposite = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().applyTo(tableSectionComposite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tableSectionComposite);

		final Group inputComposite = getWidgetFactory().createGroup(tableSectionComposite,
				Messages.VarConfigurationSection_VarConfigs);

		GridLayoutFactory.fillDefaults().applyTo(inputComposite);

		inputDataProvider = new VarConfigDeclarationListProvider(this);

		final DataLayer inputDataLayer = new DataLayer(inputDataProvider);
		configureDataLayerLabels(inputDataLayer);

		inputTable = NatTableWidgetFactory.createNatTable(inputComposite, inputDataLayer,
				new VarConfigColumnDataProvider(), VarConfigDeclarationListProvider.getEditableRule());

		inputTable.addConfiguration(new CheckBoxConfigurationNebula());
		inputTable.configure();
		GridDataFactory.fillDefaults().grab(true, true).applyTo(inputComposite);

		tableSectionComposite.layout();
	}

	private void configureDataLayerLabels(final DataLayer dataLayer) {
		dataLayer.setConfigLabelAccumulator((configLabels, columnPosition, rowPosition) -> {
			final VarDeclaration rowItem = inputDataProvider.getRowObject(rowPosition);
			switch (columnPosition) {
			case NAME_COL_ID:
				configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
				break;
			case COMMENT_COL_ID:
				configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
				if (rowItem.getComment().isBlank()) {
					configLabels.addLabelOnTop(NatTableWidgetFactory.DEFAULT_CELL);
				}
				break;
			case VISIBLE_COL_ID:
				configLabels.addLabelOnTop(NatTableWidgetFactory.CHECKBOX_CELL);
				break;
			case INITIAL_VALUE_COL_ID:
				if (rowItem.getValue().hasError()) {
					configLabels.addLabelOnTop(NatTableWidgetFactory.ERROR_CELL);
				} else if (!InitialValueHelper.hasInitalValue(rowItem)) {
					configLabels.addLabelOnTop(NatTableWidgetFactory.DEFAULT_CELL);
				}
				break;
			case VAR_CONFIG_COL_ID:
				configLabels.addLabelOnTop(NatTableWidgetFactory.CHECKBOX_CELL);
				break;
			default:
				break;
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
			final AutomationSystem automationsys = (AutomationSystem) ((FBNetworkEditPart) input).getModel()
					.eContainer().eContainer();
			return automationsys.getCommandStack();
		}
		if (input instanceof EObject) {
			final EObject root = EcoreUtil.getRootContainer((EObject) input);
			if (root instanceof AutomationSystem) {
				return ((AutomationSystem) root).getCommandStack();
			}
		}
		return null;
	}

	@Override
	protected void setInputCode() {
		// Not needed currently
	}

	@Override
	protected void setInputInit() {
		inputDataProvider.setInput(getType());
		inputTable.refresh();

	}

	private static class VarConfigDeclarationListProvider extends ListDataProvider<VarDeclaration> {

		public VarConfigDeclarationListProvider(final VarConfigurationSection section) {
			super(null, new VarConfigDeclarationColumnAccessor(section));
		}

		@Override
		public int getRowCount() {
			if (this.list != null) {
				return super.getRowCount();
			}
			return 0;
		}

		public void setInput(final Object inputElement) {
			if (inputElement instanceof EObject) {
				this.list = getVarConfigs((EObject) inputElement);
			}
		}

		private static List<VarDeclaration> getVarConfigs(final EObject obj) {
			final List<VarDeclaration> varConfigs = new ArrayList<>();
			EcoreUtil.getAllProperContents(obj, true).forEachRemaining(item -> {
				if ((item instanceof VarDeclaration) && (((VarDeclaration) item).isVarConfig())) {
					varConfigs.add((VarDeclaration) item);
				}
			});

			return varConfigs;
		}

		public static IEditableRule getEditableRule() {
			return EDITABLE_RULE;
		}

		private static final IEditableRule EDITABLE_RULE = new IEditableRule() {
			@Override
			public boolean isEditable(final int columnIndex, final int rowIndex) {
				return columnIndex == INITIAL_VALUE_COL_ID || columnIndex == COMMENT_COL_ID
						|| columnIndex == VISIBLE_COL_ID || columnIndex == VAR_CONFIG_COL_ID;
			}

			@Override
			public boolean isEditable(final ILayerCell cell, final IConfigRegistry configRegistry) {
				return cell.getColumnIndex() == INITIAL_VALUE_COL_ID || cell.getColumnIndex() == COMMENT_COL_ID
						|| cell.getColumnIndex() == VISIBLE_COL_ID || cell.getColumnIndex() == VAR_CONFIG_COL_ID;
			}
		};
	}

	public static class VarConfigDeclarationColumnAccessor implements IColumnAccessor<VarDeclaration> {

		private final VarConfigurationSection section;
		private TypeLibrary library;

		public VarConfigDeclarationColumnAccessor(final VarConfigurationSection section) {
			this.section = section;
		}

		public VarConfigDeclarationColumnAccessor(final VarConfigurationSection section, final TypeLibrary library) {
			this.section = section;
			setLibrary(library);
		}

		protected CommandExecutor getSection() {
			return section;
		}

		protected TypeLibrary getLibrary() {
			return library;
		}

		private void setLibrary(final TypeLibrary library) {
			this.library = library;
		}

		@Override
		public int getColumnCount() {
			return 6;
		}

		@Override
		public Object getDataValue(final VarDeclaration rowObject, final int columnIndex) {
			switch (columnIndex) {
			case NAME_COL_ID:
				final INamedElement sectionType = ((VarConfigurationSection) getSection()).getType();
				final String name = rowObject.getQualifiedName();
				if (!(sectionType instanceof Application)) {
					final String typeName = sectionType.getQualifiedName();
					if (name.startsWith(typeName + ".")) {
						return name.substring(typeName.length() + 1);
					}
				}
				return name;
			case TYPE_COL_ID:
				return rowObject.getTypeName();
			case COMMENT_COL_ID:
				if (!rowObject.getComment().isBlank()) {
					return rowObject.getComment();
				}
				return getTypeComment(rowObject);
			case INITIAL_VALUE_COL_ID:
				return InitialValueHelper.getInitalOrDefaultValue(rowObject);
			case VISIBLE_COL_ID:
				return Boolean.valueOf(rowObject.isVisible());
			case VAR_CONFIG_COL_ID:
				return Boolean.valueOf(rowObject.isVarConfig());
			default:
				return rowObject.getValue() == null ? "" : rowObject.getValue().getValue(); //$NON-NLS-1$
			}
		}

		private static String getTypeComment(final VarDeclaration rowObject) {
			if (rowObject.getFBNetworkElement() != null && rowObject.getFBNetworkElement().getType() != null
					&& rowObject.getFBNetworkElement().getType().getInterfaceList() != null) {
				final var fbTypeInterface = rowObject.getFBNetworkElement().getType().getInterfaceList();
				final var varName = rowObject.getName();
				if (varName != null && fbTypeInterface.getInterfaceElement(varName) != null) {
					final var comment = fbTypeInterface.getInterfaceElement(varName).getComment();
					return comment != null ? comment : ""; //$NON-NLS-1$
				}
			}
			return ""; //$NON-NLS-1$
		}

		@Override
		public void setDataValue(final VarDeclaration rowObject, final int columnIndex, final Object newValue) {
			final String value = newValue instanceof String ? (String) newValue : null;
			Command cmd = null;
			switch (columnIndex) {
			case NAME_COL_ID:
				if (value == null) {
					return;
				}
				cmd = new ChangeNameCommand(rowObject, value);
				break;
			case TYPE_COL_ID:
				DataType dataType = getLibrary().getDataTypeLibrary().getDataTypesSorted().stream()
				.filter(type -> type.getName().equals(value)).findAny().orElse(null);
				if (dataType == null) {
					dataType = getLibrary().getDataTypeLibrary().getType(null);
				}
				cmd = new ChangeDataTypeCommand(rowObject, dataType);
				break;
			case COMMENT_COL_ID:
				cmd = new ChangeCommentCommand(rowObject, value);
				break;
			case INITIAL_VALUE_COL_ID:
				cmd = new ChangeValueCommand(rowObject, value);
				break;
			case VISIBLE_COL_ID:
				if ((rowObject.isIsInput() && rowObject.getInputConnections().isEmpty())
						&& !InstancePropertySection.isExpandedSubAppPinAndConnected(rowObject)) {
					cmd = new HidePinCommand(rowObject, ((Boolean) newValue).booleanValue());
				}
				break;
			case VAR_CONFIG_COL_ID:
				cmd = new ChangeVarConfigurationCommand(rowObject, ((Boolean) newValue).booleanValue());
				break;

			default:
				return;
			}
			getSection().executeCommand(cmd);
		}


	}

	public static class VarConfigColumnDataProvider implements IDataProvider {
		@Override
		public Object getDataValue(final int columnIndex, final int rowIndex) {
			switch (columnIndex) {
			case NAME_COL_ID:
				return FordiacMessages.Name;
			case TYPE_COL_ID:
				return FordiacMessages.Type;
			case COMMENT_COL_ID:
				return FordiacMessages.Comment;
			case INITIAL_VALUE_COL_ID:
				return FordiacMessages.InitialValue;
			case VISIBLE_COL_ID:
				return FordiacMessages.Visible;
			case VAR_CONFIG_COL_ID:
				return FordiacMessages.VarConfig;
			default:
				return FordiacMessages.EmptyField;
			}
		}

		@Override
		public int getColumnCount() {
			return 6;
		}

		@Override
		public void setDataValue(final int columnIndex, final int rowIndex, final Object newValue) {
			// Setting data values to the header is not supported
		}

		@Override
		public int getRowCount() {
			return 1;
		}
	}
}
