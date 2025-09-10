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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.helpers.InterfaceListCopier;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarConfigInstance;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.widget.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.CheckBoxConfigurationNebula;
import org.eclipse.fordiac.ide.ui.widget.IChangeableRowDataProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnEditableRule;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
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

	private NatTable inputTable;
	private IChangeableRowDataProvider<VarDeclaration> inputDataProvider;
	private static String separationPoint = "."; //$NON-NLS-1$
	private TypedSubApp rootTSA;
	private final Map<String, VarDeclaration> displayMap = new LinkedHashMap<>();
	private final static Map<VarDeclaration, Boolean> copiedMap = new HashMap<>();

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
		if (input instanceof final SubAppForFBNetworkEditPart safbnEp) {
			return safbnEp.getModel();
		}
		return null;
	}

	@Override
	protected INamedElement getType() {
		if ((type instanceof Application) || (type instanceof FB) || (type instanceof SubApp)
				|| (type instanceof CFBInstance) || (type instanceof TypedSubApp)) {
			return (INamedElement) type;
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
		displayMap.clear();
		final Set<INamedElement> visited = new HashSet<>();
		collectVarConfigsRecursive(getType(), displayMap, visited, ""); //$NON-NLS-1$
		return new ArrayList<>(displayMap.values());
	}

	private void collectVarConfigsRecursive(final INamedElement type, final Map<String, VarDeclaration> result,
			final Set<INamedElement> visited, final String currentPrefix) {

		switch (type) {
		case final TypedSubApp tsa:
			traverseInterfaceElements(tsa, result, visited, currentPrefix);
			traverseFBNetwork(tsa.getType().getFBNetwork().getNetworkElements(), result, visited, currentPrefix);
			break;
		case final UntypedSubApp utsa:
			traverseFBNetwork(utsa.getSubAppNetwork().getNetworkElements(), result, visited, currentPrefix);
			break;
		case final Application app:
			traverseFBNetwork(app.getFBNetwork().getNetworkElements(), result, visited, currentPrefix);
			break;
		default:
			break;
		}
	}

	private static void traverseInterfaceElements(final TypedSubApp tsa, final Map<String, VarDeclaration> result,
			final Set<INamedElement> visited, final String currentPrefix) {
		for (final IInterfaceElement elem : tsa.getInterface().getAllInterfaceElements()) {
			if (elem instanceof final VarDeclaration vd && vd.isVarConfig()) {
				addOriginalElement(vd, currentPrefix, result);
			}
		}
	}

	private void traverseFBNetwork(final Iterable<FBNetworkElement> elements, final Map<String, VarDeclaration> result,
			final Set<INamedElement> visited, final String currentPrefix) {
		for (final FBNetworkElement fbne : elements) {
			final String prefix = currentPrefix + fbne.getName() + separationPoint;
			final boolean shouldCopy = !isPartOfEditedStructure(getType(), fbne);
			addPossibleVarConfigs(fbne, result, prefix, shouldCopy);

			if (fbne instanceof final TypedSubApp tsa) {
				collectVarConfigsRecursive(tsa, result, visited, prefix);
			}
			if (fbne instanceof final UntypedSubApp usa) {
				traverseFBNetwork(usa.getSubAppNetwork().getNetworkElements(), result, visited, prefix);
			}
		}
	}

	private static boolean isPartOfEditedStructure(final INamedElement root, final EObject obj) {
		EObject current = obj;
		while (current != null) {
			if (current == root) {
				return true;
			}
			if ((current instanceof Application || current instanceof SubAppType) && (current != root)) {
				return false;
			}
			current = current.eContainer();
		}
		return false;
	}

	private void addPossibleVarConfigs(final FBNetworkElement fbne, final Map<String, VarDeclaration> result,
			final String prefix, final boolean shouldCopy) {
		for (final IInterfaceElement elem : fbne.getInterface().getAllInterfaceElements()) {
			if (elem instanceof final VarDeclaration vd && vd.isVarConfig()) {
				if (shouldCopy) {
					addAndCopyElement(vd, prefix, result);
				} else {
					addOriginalElement(vd, prefix, result);
				}
			}
		}
	}

	private static void addOriginalElement(final VarDeclaration vd, final String currentPrefix,
			final Map<String, VarDeclaration> result) {
		final String qualifiedName = currentPrefix + vd.getName();
		if (result.containsKey(qualifiedName)) {
			return;
		}
		result.put(qualifiedName, vd);
		copiedMap.put(vd, Boolean.FALSE);
	}

	private void addAndCopyElement(final VarDeclaration vd, final String currentPrefix,
			final Map<String, VarDeclaration> result) {
		final String qualifiedName = currentPrefix + vd.getName();
		if (result.containsKey(qualifiedName)) {
			return;
		}
		switch (getType()) {
		case final Application app ->
			rootTSA = findTypedSubAppByQualifiedNameInNetwork(app.getFBNetwork().getNetworkElements(), qualifiedName);
		case final TypedSubApp tsa -> rootTSA = tsa;
		case final UntypedSubApp usa ->
			rootTSA = findTypedSubAppByQualifiedNameInNetwork(usa.getSubAppNetwork().getNetworkElements(),
					qualifiedName);
		default -> {
			break;
		}
		}

		final String relativeName = rootTSA != null ? getRelativeName(qualifiedName, rootTSA.getName()) : qualifiedName;
		VarDeclaration existing = null;
		existing = (rootTSA != null)
				? rootTSA.getVarConfigParams().stream().filter(v -> v.getName().equals(relativeName)).findFirst()
						.orElse(null)
				: null;

		final VarDeclaration targetVD;
		if (existing != null) {
			targetVD = existing;
		} else {
			final VarConfigInstance vcI = InterfaceListCopier.copyVarConfigInstance(vd, relativeName);
			rootTSA.getVarConfigParams().add(vcI);
			targetVD = vcI;
		}
		result.put(qualifiedName, targetVD);
		copiedMap.put(targetVD, Boolean.TRUE);
		cleanVarConfigParams(rootTSA);
	}

	private void cleanVarConfigParams(final TypedSubApp rootTSA) {
		for (final VarDeclaration vd : rootTSA.getVarConfigParams()) {
			if (!displayMap.values().contains(vd)) {
				rootTSA.getVarConfigParams().remove(vd);
			}
		}
	}

	private static TypedSubApp findTypedSubAppByQualifiedNameInNetwork(final Iterable<FBNetworkElement> roots,
			final String qualifiedName) {

		final int lastDot = qualifiedName.lastIndexOf('.');
		final String path = (lastDot > 0) ? qualifiedName.substring(0, lastDot) : qualifiedName;
		Iterable<FBNetworkElement> current = roots;
		for (final String seg : path.split("\\.")) {
			if (seg.isEmpty()) {
				continue;
			}

			FBNetworkElement elem = null;
			for (final FBNetworkElement fbE : current) {
				if (seg.equals(fbE.getName())) {
					elem = fbE;
					break;
				}
			}
			if (elem == null) {
				return null;
			}
			if (elem instanceof final TypedSubApp tsa) {
				return tsa;
			}

			if (elem instanceof final UntypedSubApp usa) {
				current = usa.getSubAppNetwork().getNetworkElements();
				continue;
			}
		}
		return null;
	}

	private static String getRelativeName(final String qualifiedName, final String rootName) {
		final String[] parts = qualifiedName.split(java.util.regex.Pattern.quote(separationPoint));

		for (int i = 0; i < parts.length; i++) {
			if (rootName.equals(parts[i])) {
				if (i + 1 >= parts.length) {
					return "";
				}
				final StringBuilder sb = new StringBuilder();
				for (int j = i + 1; j < parts.length; j++) {
					if (sb.length() > 0) {
						sb.append(separationPoint);
					}
					sb.append(parts[j]);
				}
				return sb.toString();
			}
		}
		return qualifiedName;
	}

	public String getDisplayName(final VarDeclaration varDecl) {
		return displayMap.entrySet().stream().filter(e -> e.getValue() == varDecl).map(Map.Entry::getKey).findFirst()
				.orElse(varDecl.getName());
	}

	private static class VarConfigDeclarationColumnAccessor extends VarDeclarationColumnAccessor {

		private VarConfigDeclarationColumnAccessor(final VarConfigurationSection section) {
			super(section, VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG);
		}

		@Override
		public Object getDataValue(final VarDeclaration rowObject, final int columnIndex) {
			if (columnIndex == VarDeclarationTableColumn.NAME.ordinal()) {
				return getCommandExecutor().getDisplayName(rowObject);
			}
			return super.getDataValue(rowObject, columnIndex);
		}

		@Override
		protected VarConfigurationSection getCommandExecutor() {
			return (VarConfigurationSection) super.getCommandExecutor();
		}
	}

	private class VarConfigEditableRule implements IEditableRule {

		@Override
		public boolean isEditable(final ILayerCell cell, final IConfigRegistry configRegistry) {
			final Object rowObject = cell.getDataValue();
			final int columnIndex = cell.getColumnIndex();
			return checkEditable(columnIndex, rowObject);
		}

		@Override
		public boolean isEditable(final int columnIndex, final int rowIndex) {
			final Object rowObject = inputDataProvider.getRowObject(rowIndex);
			return checkEditable(columnIndex, rowObject);
		}

		private boolean checkEditable(final int columnIndex, final Object rowObject) {
			if (rowObject instanceof final VarDeclaration vd) {
				final VarDeclarationTableColumn col = VarDeclarationTableColumn.values()[columnIndex];
				if (col == VarDeclarationTableColumn.VISIBLE || col == VarDeclarationTableColumn.VAR_CONFIG) {
					return isInsideOwnNetwork(vd);
				}
			}
			return true;
		}

		private boolean isInsideOwnNetwork(final VarDeclaration vd) {
			return !Boolean.TRUE.equals(copiedMap.get(vd));
		}

	}
}