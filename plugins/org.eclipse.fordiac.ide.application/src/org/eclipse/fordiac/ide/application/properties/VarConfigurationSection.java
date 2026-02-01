/*******************************************************************************
 * Copyright (c) 2022, 2025 Primetals Technologies Austria GmbH
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
 *   Sebastian Hollersbacher - Changed handling of VarConfigParamter of TypedSubApps
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.helpers.InterfaceListCopier;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarConfigInstance;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.widget.nattable.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.CheckBoxConfigurationNebula;
import org.eclipse.fordiac.ide.ui.widget.nattable.IChangeableRowDataProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnEditableRule;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class VarConfigurationSection extends AbstractSection {
	private static final String SEPARATION_POINT = "."; //$NON-NLS-1$

	private NatTable inputTable;
	private IChangeableRowDataProvider<VarDeclaration> inputDataProvider;
	private EObject selectionRoot;

	// keys are VarDeclarations shown in the table (should not change after init)
	private final LinkedHashMap<VarDeclaration, VDInfo> varConfigInfoMap = new LinkedHashMap<>();

	// every VarDeclaration should have a displayName
	// VarConfigParameter should also store their VarDeclType and TypedSubApp
	record VDInfo(String displayName, VarDeclaration varDeclType, TypedSubApp typedSubApp) {
		private String getSubAppRelativePath(final INamedElement selectedElement) {
			final String selectedElementName = selectedElement.getQualifiedName();
			final String typedSubappName = typedSubApp().getQualifiedName();

			final var cutTypedSubappName = typedSubappName.startsWith(selectedElementName + SEPARATION_POINT)
					? typedSubappName.substring(selectedElementName.length() + 1)
					: typedSubappName;
			return displayName().startsWith(cutTypedSubappName + SEPARATION_POINT)
					? displayName().substring(cutTypedSubappName.length() + 1)
					: displayName();
		}
	}

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
				new NatTableColumnEditableRule<>(new VarConfigEditableRule(),
						VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG,
						VarDeclarationTableColumn.DEFAULT_EDITABLE));

		inputTable.addConfiguration(new CheckBoxConfigurationNebula());
		inputTable.addConfiguration(new InitialValueEditorConfiguration(inputDataProvider));
		inputTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, this));
		inputTable.configure();
		GridDataFactory.fillDefaults().grab(true, true).applyTo(inputComposite);

		tableSectionComposite.layout();
	}

	@Override
	protected INamedElement getInputType(final Object input) {
		return VarConfigurationFilter.getModel(input);
	}

	@Override
	protected INamedElement getType() {
		if (type instanceof final INamedElement namedElement) {
			return namedElement;
		}
		return null;
	}

	@Override
	protected void performRefresh() {
		inputDataProvider.setInput(collectVarConfigs());
		inputTable.refresh();
	}

	@Override
	protected void performRefreshAnnotations() {
		inputTable.refresh(false);
	}

	private List<VarDeclaration> collectVarConfigs() {
		varConfigInfoMap.clear();
		selectionRoot = EcoreUtil.getRootContainer(getType());
		collectVarConfigsRecursive(getType(), "", null); //$NON-NLS-1$
		final var varDeclList = new ArrayList<>(varConfigInfoMap.keySet());
		// sort for consistent order (overrideParams might change order)
		varDeclList.sort((decl1, decl2) -> varConfigInfoMap.get(decl2).displayName()
				.compareTo(varConfigInfoMap.get(decl1).displayName()));
		return varDeclList;
	}

	private void collectVarConfigsRecursive(final INamedElement type, final String currentPrefix,
			final TypedSubApp currentSubappInstance) {
		switch (type) {
		case final Application app -> traverseFBNetwork(app.getFBNetwork(), currentPrefix, null);
		case final SubAppType subappType -> {
			traverseFBNetwork(subappType.getFBNetwork(), currentPrefix, null);
			subappType.getInterfaceList().getInputVars().stream().filter(VarDeclaration::isVarConfig)
					.forEach(varDecl -> {
						final String qualifiedName = currentPrefix + varDecl.getName();
						varConfigInfoMap.put(varDecl, new VDInfo(qualifiedName, null, null));
					});
		}
		case final TypedSubApp tsa -> {
			final boolean copy = EcoreUtil.getRootContainer(tsa) != selectionRoot;
			final TypedSubApp newSubappInstance = copy ? currentSubappInstance : tsa;
			traverseFBNetwork(tsa.getType().getFBNetwork(), currentPrefix, newSubappInstance);
			traverseInterfaceElements(tsa, currentPrefix, copy, newSubappInstance);
			overrideParams(tsa, currentPrefix, copy);
		}
		case final UntypedSubApp utsa -> {
			traverseFBNetwork(utsa.getSubAppNetwork(), currentPrefix, currentSubappInstance);
			traverseInterfaceElements(utsa, currentPrefix, EcoreUtil.getRootContainer(utsa) != selectionRoot,
					currentSubappInstance);
		}
		case final BlockFBNetworkElement bfbne -> traverseInterfaceElements(bfbne, currentPrefix,
				EcoreUtil.getRootContainer(bfbne) != selectionRoot, currentSubappInstance);
		default -> {
			// do nothing
		}
		}
	}

	private void overrideParams(final TypedSubApp typedSubApp, final String currentPrefix, final boolean needCopy) {
		typedSubApp.getVarConfigParams().forEach(varConfigParameter -> {
			final String qualifiedName = currentPrefix + varConfigParameter.getName();
			final VarDeclaration toReplaceVarConfig = varConfigInfoMap.entrySet().stream()
					.filter(e -> e.getValue().displayName.equals(qualifiedName)).map(Map.Entry::getKey).findFirst()
					.orElse(null);
			if (toReplaceVarConfig == null) {
				// VarConfigParamter is saved but is not set anymore
				return;
			}

			// replace default VarConfig with already saved one
			final VDInfo info = varConfigInfoMap.remove(toReplaceVarConfig);
			if (info != null) {
				if (needCopy) {
					final VDInfo newInfo = new VDInfo(info.displayName(), varConfigParameter, info.typedSubApp());
					varConfigInfoMap.put(EcoreUtil.copy(varConfigParameter), newInfo);
				} else {
					varConfigInfoMap.put(varConfigParameter, info);
				}
			}
		});
	}

	private void traverseInterfaceElements(final BlockFBNetworkElement fbne, final String currentPrefix,
			final boolean isParamsCandidate, final TypedSubApp currentSubappInstance) {
		fbne.getInterface().getInputVars().stream().filter(VarDeclaration::isVarConfig).forEach(varConfig -> {
			final String qualifiedName = currentPrefix + varConfig.getName();
			if (isParamsCandidate) {
				final VDInfo info = new VDInfo(qualifiedName, varConfig, currentSubappInstance);
				final String relativePath = info.getSubAppRelativePath(getType());
				varConfigInfoMap.put(InterfaceListCopier.copyVarConfigInstance(varConfig, relativePath), info);
			} else {
				varConfigInfoMap.put(varConfig, new VDInfo(qualifiedName, null, null));
			}
		});
	}

	private void traverseFBNetwork(final FBNetwork network, final String currentPrefix,
			final TypedSubApp currentSubappInstance) {
		network.getBlockFBNetworkElements().forEach(bfbne -> {
			final String prefix = currentPrefix + bfbne.getName() + SEPARATION_POINT;
			collectVarConfigsRecursive(bfbne, prefix, currentSubappInstance);
		});
	}

	@Override
	public void executeCommand(final Command cmd) {
		super.executeCommand(cmd);

		if (cmd instanceof final ChangeValueCommand valueCmd) {
			final VarDeclaration decl = valueCmd.getInterfaceElement();
			processVarConfigChange(decl, InitialValueHelper::getInitialOrDefaultValue);
		}

		if (cmd instanceof final ChangeCommentCommand commentCmd) {
			final Object[] affected = commentCmd.getAffectedObjects().toArray();
			if (affected.length == 0 || !(affected[0] instanceof final VarConfigInstance decl)) {
				return;
			}
			processVarConfigChange(decl, VarDeclaration::getComment);
		}
	}

	private void processVarConfigChange(final VarDeclaration varConfig,
			final Function<VarDeclaration, String> varConfigMapper) {
		final VDInfo info = varConfigInfoMap.get(varConfig);
		if (info == null || info.varDeclType() == null || info.typedSubApp() == null) {
			return;
		}

		final List<VarConfigInstance> varConfigParameter = info.typedSubApp().getVarConfigParams();
		final boolean matchesTyp = varConfigMapper.apply(varConfig).equals(varConfigMapper.apply(info.varDeclType()));

		if (matchesTyp) {
			// remove matching config entries
			final String subAppRelativePath = info.getSubAppRelativePath(getType());
			varConfigParameter.removeIf(param -> param.getName().equals(subAppRelativePath));
		} else if (varConfig instanceof final VarConfigInstance vci && !varConfigParameter.contains(vci)) {
			// add only if not already present
			varConfigParameter.add(vci);
		}
	}

	private class VarConfigDeclarationColumnAccessor extends VarDeclarationColumnAccessor {
		private VarConfigDeclarationColumnAccessor(final VarConfigurationSection section) {
			super(section, VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG);
		}

		@Override
		public Object getDataValue(final VarDeclaration rowObject, final VarDeclarationTableColumn column) {
			if (column == VarDeclarationTableColumn.NAME) {
				return varConfigInfoMap.get(rowObject).displayName;
			}
			return super.getDataValue(rowObject, column);
		}
	}

	private class VarConfigEditableRule implements IEditableRule {
		@Override
		public boolean isEditable(final ILayerCell cell, final IConfigRegistry configRegistry) {
			return isEditable(cell.getColumnIndex(), cell.getRowIndex());
		}

		@Override
		public boolean isEditable(final int columnIndex, final int rowIndex) {
			final VarDeclaration rowObject = inputDataProvider.getRowObject(rowIndex);
			return checkEditable(columnIndex, rowObject);
		}

		private boolean checkEditable(final int columnIndex, final VarDeclaration rowObject) {
			final VarDeclarationTableColumn column = VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG
					.get(columnIndex);
			if (column == VarDeclarationTableColumn.VISIBLE || column == VarDeclarationTableColumn.VAR_CONFIG) {
				return !(rowObject instanceof VarConfigInstance);
			}
			return true;
		}
	}
}