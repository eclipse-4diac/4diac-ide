/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *                    Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *   Martin Erich Jobst
 *     - add organize imports button
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.widgets;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.fordiac.ide.gef.annotation.TextualAnnotationStyles;
import org.eclipse.fordiac.ide.gef.editparts.ImportCellEditor;
import org.eclipse.fordiac.ide.gef.provider.PackageContentProvider;
import org.eclipse.fordiac.ide.gef.provider.PackageLabelProvider;
import org.eclipse.fordiac.ide.model.commands.change.ChangeImportNamespaceCommand;
import org.eclipse.fordiac.ide.model.commands.create.AddNewImportCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteImportCommand;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.commands.OrganizeImportsCommand;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ChangePackageNameRefactoring;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteWidget;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class PackageInfoWidget extends TypeInfoWidget {

	private static final String RENAME_ELEMENT_ICON = "icons/full/etool16/tricks.png"; //$NON-NLS-1$

	private final Supplier<GraphicalAnnotationModel> annotationModelSupplier;
	private TableViewer packageViewer;
	private StyledText nameText;
	private AddDeleteWidget buttons;
	private Button changePackageNameButton;
	private Button organizeImportsButton;
	private Image changePackageNameImage;
	Composite composite;

	public PackageInfoWidget(final FormToolkit widgetFactory,
			final Supplier<GraphicalAnnotationModel> annotationModelSupplier) {
		super(widgetFactory);
		this.annotationModelSupplier = annotationModelSupplier;
	}

	@Override
	public void createControls(final Composite leftComposite, final Composite rightComposite) {
		super.createControls(leftComposite, rightComposite);
		createPackageInfoGroup(rightComposite);
	}

	private void createPackageInfoGroup(final Composite parent) {
		final Group packageGroup = createGroup(parent, FordiacMessages.Package);
		packageGroup.setLayout(new GridLayout(1, false));
		packageGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		composite = getWidgetFactory().createComposite(packageGroup, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).margins(0, 0).applyTo(composite);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		changePackageNameButton = getWidgetFactory().createButton(composite, null, SWT.PUSH);
		changePackageNameButton.setToolTipText(Messages.ChangePackageNameRefactoring_Tooltip);
		changePackageNameButton.setImage(getChangePackageNameImage());
		changePackageNameButton.addDisposeListener(e -> disposeChangePackageNameImage());
		changePackageNameButton
				.addSelectionListener(SelectionListener.widgetSelectedAdapter(ev -> openPackageNameRefactoring()));
		GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.CENTER).applyTo(changePackageNameButton);
		getWidgetFactory().createLabel(composite, FordiacMessages.Name + ":"); //$NON-NLS-1$
		nameText = createGroupStyledText(composite);

		final Label importsLabel = new Label(packageGroup, SWT.NONE);
		importsLabel.setText(FordiacMessages.Imports + ":"); //$NON-NLS-1$
		importsLabel.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_TRANSPARENT));

		final Composite compositeBottom = getWidgetFactory().createComposite(packageGroup);
		compositeBottom.setLayout(new GridLayout(2, false));
		compositeBottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		buttons = new AddDeleteWidget();
		buttons.createControls(compositeBottom, getWidgetFactory());

		organizeImportsButton = getWidgetFactory().createButton(buttons.getControl(), null, SWT.PUSH);
		organizeImportsButton.setToolTipText(FordiacMessages.PackageInfoWidget_OrganizeImports);
		organizeImportsButton
				.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ELCL_SYNCED));
		organizeImportsButton.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		organizeImportsButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(ev -> {
			executeCommand(new OrganizeImportsCommand(getType()));
			packageViewer.refresh();
		}));

		final Composite tableComposite = new Composite(compositeBottom, SWT.NONE);
		tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		packageViewer = TableWidgetFactory.createPropertyTableViewer(tableComposite);
		configureImportsTableLayout(packageViewer, tableComposite);
		packageViewer.setContentProvider(new PackageContentProvider());

		buttons.bindToTableViewer(packageViewer, this, ref -> new AddNewImportCommand(getType()),
				ref -> new DeleteImportCommand(getType().getCompilerInfo(), (Import) ref));
	}

	private Image getChangePackageNameImage() {
		if (changePackageNameImage == null) {
			final ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(PlatformUI.PLUGIN_ID,
					RENAME_ELEMENT_ICON);
			if (imageDescriptor != null) {
				changePackageNameImage = imageDescriptor.createImage();
			}
		}
		return changePackageNameImage;
	}

	private void disposeChangePackageNameImage() {
		if (changePackageNameImage != null && !changePackageNameImage.isDisposed()) {
			changePackageNameImage.dispose();
		}
		changePackageNameImage = null;
	}

	private void openPackageNameRefactoring() {
		if (blockListeners || getType() == null) {
			return;
		}

		final TypeEntry typeEntry = getType().getTypeEntry();
		if (typeEntry != null && typeEntry.getFile() != null) {
			ChangePackageNameRefactoring.openWizard(typeEntry, nameText.getShell());
		}
	}

	private void configureImportsTableLayout(final TableViewer viewer, final Composite parentComposite) {
		final TableViewerColumn nameColumn = new TableViewerColumn(viewer, SWT.FILL);
		nameColumn.setLabelProvider(
				new DelegatingStyledCellLabelProvider(new PackageLabelProvider(annotationModelSupplier)));
		nameColumn.setEditingSupport(new ImportsEditingSupport(viewer, this::getTypeLibrary, this));
		final TableColumn nameTableColumn = nameColumn.getColumn();
		nameTableColumn.setText(FordiacMessages.Name);

		final TableColumnLayout tableLayout = new TableColumnLayout();
		tableLayout.setColumnData(nameTableColumn, new ColumnWeightData(100, true));
		parentComposite.setLayout(tableLayout);
	}

	@Override
	public void refresh() {
		super.refresh();
		if (packageViewer != null && !packageViewer.getControl().isDisposed()) {
			final Consumer<Command> commandExecutorBuffer = getCommandExecutor();
			setCommandExecutor(null);
			if ((getType() != null)) {
				updatePackageNameTextState();
				changePackageNameButton.setEnabled(!isReadonly() && canChangePackageName());
				buttons.setEnabled(!isReadonly());
				organizeImportsButton.setEnabled(!isReadonly());
				packageViewer.getTable().setEnabled(!isReadonly());
				packageViewer.setInput(getType());
				refreshAnnotations();
			}
			setCommandExecutor(commandExecutorBuffer);
		}
	}

	public void refreshAnnotations() {
		final Consumer<Command> commandExecutorBuffer = getCommandExecutor();
		setCommandExecutor(null);
		final GraphicalAnnotationModel annotationModel = annotationModelSupplier.get();
		final CompilerInfo compilerInfo = getType().getCompilerInfo();
		final StyledString nameStyledString = new StyledString(PackageNameHelper.getPackageName(getType()),
				annotationModel != null && compilerInfo != null
						? TextualAnnotationStyles.getAnnotationStyle(annotationModel.getAnnotations(compilerInfo))
						: null);

		if (nameText != null && !nameText.isDisposed()) {
			final int caretOffset = nameText.getCaretOffset();
			final Point nameTextSelection = nameText.getSelection();
			nameText.setText(nameStyledString.toString());
			nameText.setStyleRanges(nameStyledString.getStyleRanges());
			nameText.setSelection(nameTextSelection);
			nameText.setCaretOffset(caretOffset);
		}
		if (packageViewer != null && !packageViewer.getControl().isDisposed()) {
			packageViewer.refresh();
		}
		setCommandExecutor(commandExecutorBuffer);
	}

	@Override
	public void setEnabled(final boolean enablement) {
		super.setEnabled(enablement);
		updatePackageNameTextState();
		changePackageNameButton.setEnabled(enablement && canChangePackageName());
		buttons.setVisible(enablement);
		packageViewer.getTable().setEnabled(enablement);
		packageViewer.setCellModifier(null);
	}

	private void updatePackageNameTextState() {
		nameText.setEditable(false);
		nameText.setEnabled(true);
		nameText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
	}

	private boolean canChangePackageName() {
		final LibraryElement type = getType();
		return type != null && type.getTypeEntry() != null && type.getTypeEntry().getFile() != null && !isReadonly();
	}

	private boolean isReadonly() {
		return getType() instanceof FunctionFBType || getType() instanceof GlobalConstants;
	}

	protected TypeLibrary getTypeLibrary() {
		final LibraryElement type = getType();
		if (type != null) {
			return getType().getTypeLibrary();
		}
		return null;
	}

	private StyledText createGroupStyledText(final Composite group) {
		final StyledText text = new StyledText(group, SWT.BORDER | SWT.SINGLE | getWidgetFactory().getOrientation());
		getWidgetFactory().adapt(text, true, false);
		text.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		text.setEditable(false);
		text.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		return text;
	}

	protected static class ImportsEditingSupport extends EditingSupport {

		private final CellEditor cellEditor;
		private final CommandExecutor commandExecutor;

		public ImportsEditingSupport(final TableViewer viewer, final Supplier<TypeLibrary> supplier,
				final CommandExecutor commandExecutor) {
			super(viewer);
			this.commandExecutor = commandExecutor;
			cellEditor = new ImportCellEditor(viewer.getTable(), supplier);
		}

		@Override
		protected CellEditor getCellEditor(final Object element) {
			return cellEditor;
		}

		@Override
		protected boolean canEdit(final Object element) {
			return element instanceof Import;
		}

		@Override
		protected Object getValue(final Object element) {
			return element instanceof final Import imp ? imp.getImportedNamespace() : null;
		}

		@Override
		protected void setValue(final Object element, final Object value) {
			if (element instanceof final Import imp && value instanceof final String importedNamespace) {
				commandExecutor.executeCommand(new ChangeImportNamespaceCommand(imp, importedNamespace));
				getViewer().refresh(element);
			}
		}
	}
}
