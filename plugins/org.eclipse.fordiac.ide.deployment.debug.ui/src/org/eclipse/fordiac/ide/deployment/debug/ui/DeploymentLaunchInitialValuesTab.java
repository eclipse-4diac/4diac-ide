/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchValue;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueGenericEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueStructuredElementAccessor;
import org.eclipse.fordiac.ide.gef.nat.TypeDeclarationEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.TypeDeclarationParser;
import org.eclipse.fordiac.ide.model.edit.helper.CommentHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.provider.LibraryElementItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.ui.widget.nattable.AbstractColumnAccessor;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnEditableRule;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.IConfigLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.selection.RowPostSelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;

public class DeploymentLaunchInitialValuesTab extends AbstractLaunchConfigurationTab {
	public static final String ID = "org.eclipse.fordiac.ide.deployment.debug.ui.initialValuesTab"; //$NON-NLS-1$

	private final List<DeploymentLaunchValue> values = new ArrayList<>();

	private AutomationSystem system;
	private NatTable valuesTable;
	private RowPostSelectionProvider<DeploymentLaunchValue> valuesSelectionProvider;
	private Button addButton;
	private Button removeButton;

	@Override
	public void createControl(final Composite parent) {
		final Composite comp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(comp);
		setControl(comp);

		final Control valuesControl = createValuesTable(comp);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(valuesControl);

		final Control buttonsControl = createTableButtons(comp);
		GridDataFactory.fillDefaults().grab(false, true).applyTo(buttonsControl);
	}

	protected Control createValuesTable(final Composite parent) {
		final IRowDataProvider<DeploymentLaunchValue> valuesDataProvider = new ListDataProvider<>(
				Collections.unmodifiableList(values),
				new DeploymentLaunchValueColumnAccessor(VarDeclarationTableColumn.DEFAULT_COLUMNS));
		final DataLayer dataLayer = new VarDeclarationDataLayer(valuesDataProvider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS);
		dataLayer.setConfigLabelAccumulator(new DeploymentLaunchValueConfigLabelAccumulator(valuesDataProvider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS));

		final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(
				VarDeclarationTableColumn.DEFAULT_COLUMNS);
		valuesTable = NatTableWidgetFactory.createNatTable(parent, dataLayer, columnProvider,
				new NatTableColumnEditableRule<>(IEditableRule.ALWAYS_EDITABLE,
						VarDeclarationTableColumn.DEFAULT_COLUMNS, Set.of(VarDeclarationTableColumn.INITIAL_VALUE)));
		valuesTable.addConfiguration(new InitialValueGenericEditorConfiguration<>(valuesDataProvider,
				new DeploymentLaunchValueElementAccessor()));
		valuesTable.configure();
		valuesSelectionProvider = new RowPostSelectionProvider<>(valuesTable,
				NatTableWidgetFactory.getSelectionLayer(valuesTable), valuesDataProvider, false);
		valuesSelectionProvider.addSelectionChangedListener(this::handleTableSelectionChanged);
		return valuesTable;
	}

	protected void handleTableSelectionChanged(final SelectionChangedEvent event) {
		final IStructuredSelection selection = event.getStructuredSelection();
		removeButton.setEnabled(selection.stream().anyMatch(values::contains));
	}

	private Control createTableButtons(final Composite parent) {
		final Composite comp = new Composite(parent, SWT.NONE);

		addButton = new Button(comp, SWT.PUSH);
		addButton.setText(Messages.DeploymentLaunchInitialValuesTab_AddButton);
		addButton.setEnabled(false);
		addButton.addSelectionListener(widgetSelectedAdapter(_ -> handleAddButtonSelected()));

		removeButton = new Button(comp, SWT.PUSH);
		removeButton.setText(Messages.DeploymentLaunchInitialValuesTab_RemoveButton);
		removeButton.setEnabled(false);
		removeButton.addSelectionListener(widgetSelectedAdapter(_ -> handleRemoveButtonSelected()));

		GridLayoutFactory.swtDefaults().generateLayout(comp);

		return comp;
	}

	private void handleAddButtonSelected() {
		final VarDeclarationTreeSelectionDialog dialog = new VarDeclarationTreeSelectionDialog(getShell());
		dialog.setTitle(Messages.DeploymentLaunchInitialValuesTab_AddDialogTitle);
		dialog.setMessage(Messages.DeploymentLaunchInitialValuesTab_AddDialogMessage);
		dialog.setEmptyListMessage(Messages.DeploymentLaunchInitialValuesTab_AddDialogEmptyListMessage);
		dialog.setValidator(selection -> Stream.of(selection).noneMatch(VarDeclaration.class::isInstance)
				? Status.error(Messages.DeploymentLaunchInitialValuesTab_AddDialogEmptySelectionMessage)
				: Status.OK_STATUS);
		dialog.setStatusLineAboveButtons(true);
		dialog.setAllowMultiple(true);
		dialog.setInput(system);
		if (dialog.open() == Window.OK) {
			Stream.of(dialog.getResult()).filter(VarDeclaration.class::isInstance).map(VarDeclaration.class::cast)
					.map(variable -> new DeploymentLaunchValue(variable.getQualifiedName(), Optional.of(variable), "")) //$NON-NLS-1$
					.forEachOrdered(value -> {
						final int index = Collections.binarySearch(values, value,
								Comparator.comparing(DeploymentLaunchValue::getName));
						if (index < 0) {
							values.add(-index - 1, value);
						}
					});
			valuesTable.refresh();
			updateLaunchConfigurationDialog();
		}
	}

	private void handleRemoveButtonSelected() {
		values.removeAll(((IStructuredSelection) valuesSelectionProvider.getSelection()).toList());
		valuesTable.refresh();
		updateLaunchConfigurationDialog();
	}

	@Override
	public void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		DeploymentLaunchConfigurationAttributes.setWatches(configuration, null);
	}

	@Override
	public void initializeFrom(final ILaunchConfiguration configuration) {
		try {
			system = DeploymentLaunchConfigurationAttributes.getSystem(configuration);
			values.clear();
			values.addAll(DeploymentLaunchConfigurationAttributes.getValues(configuration));
			valuesTable.refresh();
			addButton.setEnabled(system != null);
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
	}

	@Override
	public void performApply(final ILaunchConfigurationWorkingCopy configuration) {
		DeploymentLaunchConfigurationAttributes.setValues(configuration, values);
	}

	@Override
	public String getName() {
		return Messages.DeploymentLaunchInitialValuesTab_Name;
	}

	@Override
	public Image getImage() {
		return DebugUITools.getImage(IDebugUIConstants.IMG_VIEW_VARIABLES);
	}

	@Override
	public String getId() {
		return ID;
	}

	private class DeploymentLaunchValueColumnAccessor
			extends AbstractColumnAccessor<DeploymentLaunchValue, VarDeclarationTableColumn> {

		public DeploymentLaunchValueColumnAccessor(final List<VarDeclarationTableColumn> columns) {
			super(columns);
		}

		@Override
		public Object getDataValue(final DeploymentLaunchValue rowObject, final VarDeclarationTableColumn column) {
			return switch (column) {
			case NAME -> rowObject.getName();
			case TYPE -> rowObject.getRefElement().map(VarDeclaration::getFullTypeName).orElse(NULL_DEFAULT);
			case COMMENT -> rowObject.getRefElement().map(CommentHelper::getInstanceComment).orElse(NULL_DEFAULT);
			case INITIAL_VALUE -> rowObject.getValue().isEmpty()
					? rowObject.getRefElement().map(VarDeclarationColumnAccessor::getInitialValue).orElse(NULL_DEFAULT)
					: rowObject.getValue();
			default -> throw new IllegalArgumentException("Unexpected value: " + column); //$NON-NLS-1$
			};
		}

		@Override
		public void setDataValue(final DeploymentLaunchValue rowObject, final VarDeclarationTableColumn column,
				final Object newValue) {
			switch (column) {
			case INITIAL_VALUE -> rowObject.setValue(Objects.toString(newValue, NULL_DEFAULT));
			default -> throw new IllegalArgumentException("Unexpected value: " + column); //$NON-NLS-1$
			}
			valuesTable.refresh(false);
			updateLaunchConfigurationDialog();
		}
	}

	private static class DeploymentLaunchValueConfigLabelAccumulator implements IConfigLabelAccumulator {
		private final IRowDataProvider<DeploymentLaunchValue> dataProvider;
		private final List<VarDeclarationTableColumn> columns;

		public DeploymentLaunchValueConfigLabelAccumulator(final IRowDataProvider<DeploymentLaunchValue> dataProvider,
				final List<VarDeclarationTableColumn> columns) {
			this.columns = columns;
			this.dataProvider = dataProvider;
		}

		@Override
		public void accumulateConfigLabels(final LabelStack configLabels, final int columnPosition,
				final int rowPosition) {
			final DeploymentLaunchValue rowItem = dataProvider.getRowObject(rowPosition);
			switch (columns.get(columnPosition)) {
			case NAME -> configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
			case TYPE -> configLabels.addLabel(TypeDeclarationEditorConfiguration.TYPE_DECLARATION_CELL);
			case COMMENT -> {
				configLabels.addLabelOnTop(NatTableWidgetFactory.NONE_NULL);
				configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
				configLabels.addLabelOnTop(NatTableWidgetFactory.DEFAULT_CELL);
			}
			case INITIAL_VALUE -> {
				if (rowItem.getRefElement()
						.filter(refElement -> refElement.getType() instanceof StructuredType || refElement.isArray())
						.isPresent()) {
					configLabels.addLabel(InitialValueEditorConfiguration.INITIAL_VALUE_STRUCTURED_CELL);
				} else {
					configLabels.addLabel(InitialValueEditorConfiguration.INITIAL_VALUE_CELL);
				}
				if (rowItem.getValue().isEmpty()) {
					configLabels.addLabelOnTop(NatTableWidgetFactory.DEFAULT_CELL);
				}
			}
			default -> {
				// do nothing
			}
			}
		}
	}

	private class DeploymentLaunchValueElementAccessor
			implements InitialValueStructuredElementAccessor<DeploymentLaunchValue> {

		@Override
		public LibraryElement getContext(final DeploymentLaunchValue element) {
			return element.getRefElement().map(EcoreUtil::getRootContainer).filter(LibraryElement.class::isInstance)
					.map(LibraryElement.class::cast).orElse(system);
		}

		@Override
		public LibraryElement getType(final DeploymentLaunchValue element) {
			return element.getRefElement()
					.map(varDeclaration -> varDeclaration.isArray()
							? TypeDeclarationParser.parseTypeDeclaration(varDeclaration.getType(),
									varDeclaration.getArraySizeString())
							: varDeclaration.getType())
					.orElse(null);
		}

		@Override
		public ITypedElement getReferenceElement(final DeploymentLaunchValue element) {
			return element.getRefElement().orElse(null);
		}
	}

	private static class VarDeclarationTreeSelectionDialog extends ElementTreeSelectionDialog {

		public VarDeclarationTreeSelectionDialog(final Shell parent) {
			super(parent, new AdapterFactoryLabelProvider(new LibraryElementItemProviderAdapterFactory()),
					new VarDeclarationTreeProvider());
		}

		@Override
		protected TreeViewer doCreateTreeViewer(final Composite parent, final int style) {
			final FilteredTree tree = new FilteredTree(parent, style, new VarDeclarationPatternFilter(), true, true);
			GridDataFactory.fillDefaults().grab(true, true).applyTo(tree);
			applyDialogFont(tree);
			return tree.getViewer();
		}
	}

	public static class VarDeclarationPatternFilter extends PatternFilter {

		@Override
		protected boolean isLeafMatch(final Viewer viewer, final Object element) {
			if (element instanceof final INamedElement namedElement) {
				return wordMatches(namedElement.getQualifiedName());
			}
			return super.isLeafMatch(viewer, element);
		}
	}

	private static class VarDeclarationTreeProvider extends AdapterFactoryContentProvider {

		public VarDeclarationTreeProvider() {
			super(new LibraryElementItemProviderAdapterFactory());
		}

		@Override
		public Object[] getElements(final Object object) {
			return switch (object) {
			case final AutomationSystem system -> system.getApplication().toArray();
			default -> super.getElements(object);
			};
		}

		@Override
		public Object[] getChildren(final Object parentElement) {
			return switch (parentElement) {
			case final FBNetwork network -> network.getBlockFBNetworkElements().toArray();
			case final SubApp subApp -> new Object[] { subApp.getInterface(), subApp.loadSubAppNetwork() };
			case final BlockFBNetworkElement fbne -> new Object[] { fbne.getInterface() };
			case final FBNetworkElement _ -> new Object[0];
			case final InterfaceList interfaceList ->
				Stream.of(interfaceList.getInputVars(), interfaceList.getInOutVars()).flatMap(List::stream).toArray();
			default -> super.getChildren(parentElement);
			};
		}

		@Override
		public boolean hasChildren(final Object element) {
			return switch (element) {
			case final AutomationSystem system -> !system.getApplication().isEmpty();
			case final FBNetwork network -> network.getBlockFBNetworkElements().findAny().isPresent();
			case final BlockFBNetworkElement _ -> true;
			case final FBNetworkElement _ -> false;
			case final InterfaceList interfaceList ->
				!interfaceList.getInputVars().isEmpty() || !interfaceList.getInOutVars().isEmpty();
			default -> super.hasChildren(element);
			};
		}
	}
}
