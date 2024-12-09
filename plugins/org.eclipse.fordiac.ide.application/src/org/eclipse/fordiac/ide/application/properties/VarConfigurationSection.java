/*******************************************************************************
 * Copyright (c) 2022, 2024 Primetals Technologies Austria GmbH
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

import java.util.List;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.widget.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.CheckBoxConfigurationNebula;
import org.eclipse.fordiac.ide.ui.widget.IChangeableRowDataProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnEditableRule;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class VarConfigurationSection extends AbstractSection {

	private NatTable inputTable;
	private IChangeableRowDataProvider<VarDeclaration> inputDataProvider;

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

		inputDataProvider = new ChangeableListDataProvider<>(new VarConfigDeclarationColumnAccessor(this));

		final DataLayer inputDataLayer = new VarDeclarationDataLayer(inputDataProvider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG);
		inputDataLayer.setConfigLabelAccumulator(new VarDeclarationConfigLabelAccumulator(inputDataProvider,
				this::getAnnotationModel, VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG));

		final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG);
		inputTable = NatTableWidgetFactory.createNatTable(inputComposite, inputDataLayer, columnProvider,
				new NatTableColumnEditableRule<>(IEditableRule.ALWAYS_EDITABLE,
						VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG,
						VarDeclarationTableColumn.DEFAULT_EDITABLE));

		inputTable.addConfiguration(new CheckBoxConfigurationNebula());
		inputTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, this));
		inputTable.configure();
		GridDataFactory.fillDefaults().grab(true, true).applyTo(inputComposite);

		tableSectionComposite.layout();
	}

	@Override
	protected INamedElement getInputType(final Object input) {
		if (input instanceof final FBNetworkEditPart fbnEP) {
			return fbnEP.getModel().getApplication();
		}
		if (input instanceof final INamedElement namedElement) {
			return namedElement;
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
		if (input instanceof final FBNetworkEditPart fbnEditPart) {
			EObject eObject = fbnEditPart.getModel().eContainer();

			while (!(eObject instanceof AutomationSystem)) {
				eObject = eObject.eContainer();
			}

			return ((AutomationSystem) eObject).getCommandStack();
		}
		if (input instanceof final EObject eObj) {
			final EObject root = EcoreUtil.getRootContainer(eObj);
			if (root instanceof final AutomationSystem system) {
				return system.getCommandStack();
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
		inputDataProvider.setInput(collectVarConfigs());
		inputTable.refresh();
	}

	@Override
	protected void performRefresh() {
		inputTable.refresh();
	}

	@Override
	protected void performRefreshAnnotations() {
		inputTable.refresh(false);
	}

	private List<VarDeclaration> collectVarConfigs() {
		return StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(EcoreUtil.getAllProperContents(getType(), true), 0), false)
				.filter(VarDeclaration.class::isInstance).map(VarDeclaration.class::cast)
				.filter(VarDeclaration::isVarConfig).toList();
	}

	private static class VarConfigDeclarationColumnAccessor extends VarDeclarationColumnAccessor {

		private VarConfigDeclarationColumnAccessor(final VarConfigurationSection section) {
			super(section, VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG);
		}

		@Override
		public Object getDataValue(final VarDeclaration rowObject, final int columnIndex) {
			return switch (getColumns().get(columnIndex)) {
			case NAME -> rowObject.getQualifiedName();
			default -> super.getDataValue(rowObject, columnIndex);
			};
		}

		@Override
		protected VarConfigurationSection getCommandExecutor() {
			return (VarConfigurationSection) super.getCommandExecutor();
		}
	}
}
