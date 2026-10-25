/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner
 *   	- initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.library.ui.editors;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.library.model.library.Exports;
import org.eclipse.fordiac.ide.library.model.library.Library;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.library.ui.editors.operations.AddElementOperation;
import org.eclipse.fordiac.ide.library.ui.editors.operations.RemoveElementOperation;
import org.eclipse.fordiac.ide.library.ui.editors.operations.SetValueOperation;
import org.eclipse.fordiac.ide.library.ui.editors.tabs.AttributeTab;
import org.eclipse.fordiac.ide.library.ui.editors.tabs.ContentTab;
import org.eclipse.fordiac.ide.library.ui.editors.tabs.DependenciesTab;
import org.eclipse.fordiac.ide.library.ui.editors.tabs.TableControl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.wizards.IWizardDescriptor;

public class ManifestEditorLibraryPage extends ManifestEditorPage<Exports> {

	private static final String EXPORT_WIZARD_ID = "org.eclipse.fordiac.ide.library.ui.wizards.LibraryExportWizard"; //$NON-NLS-1$

	private TableControl libraryTable;
	private Text commentText;

	private DependenciesTab dependenciesTab;
	private AttributeTab attributeTab;
	private ContentTab contentTab;

	private Library selectedLibrary;

	protected ManifestEditorLibraryPage(final ManifestEditor editor, final String id, final String title) {
		super(editor, id, title);
	}

	@Override
	protected void createPageContent(final Composite parent, final FormToolkit toolkit, final IManagedForm form) {
		final Composite client = createLibrariesSection(parent, toolkit);
		createDetailSection(client, toolkit);

		updateSelection();
	}

	@Override
	protected void refresh() {
		if (libraryTable == null) {
			return;
		}

		final var viewer = libraryTable.getViewer();
		final EList<Library> libraries = getLibraries();

		if (viewer.getInput() != libraries) {
			viewer.setInput(libraries);
		} else {
			viewer.refresh();
		}

		updateSelection();

		dependenciesTab.refresh();
		attributeTab.refresh();
		contentTab.refresh();
	}

	private Composite createLibrariesSection(final Composite parent, final FormToolkit toolkit) {
		final Section section = toolkit.createSection(parent,
				ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);

		section.setText(Messages.ManifestEditor_Libraries);
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createLibraryActions(section, toolkit);

		final Composite client = toolkit.createComposite(section);
		client.setLayout(new GridLayout(1, false));
		section.setClient(client);

		libraryTable = new TableControl(client, toolkit, ""); //$NON-NLS-1$
		createLibraryColumns();

		libraryTable.setAddHandler(this::addLibrary);
		libraryTable.setRemoveHandler(this::removeSelectedLibrary);
		libraryTable.getViewer().addSelectionChangedListener(_ -> updateSelection());
		libraryTable.setInput(getLibraries());

		return client;
	}

	private void createLibraryColumns() {
		createLibraryColumn(Messages.ManifestEditor_Name, 40, Library::getName,
				(library, value) -> new SetValueOperation<>("Set name", //$NON-NLS-1$
						library::getName, library::setName, value));

		createLibraryColumn(Messages.ManifestEditor_SymbolicName, 60, Library::getSymbolicName,
				(library, value) -> new SetValueOperation<>("Set symbolic name", //$NON-NLS-1$
						library::getSymbolicName, library::setSymbolicName, value));
	}

	private void createLibraryColumn(final String title, final int weight, final Function<Library, String> getter,
			final BiFunction<Library, String, IUndoableOperation> operationFactory) {

		final var column = libraryTable.createColumn(title, weight);

		column.setLabelProvider(TableControl.createLabelProvider(Library.class, getter,
				lib -> targets(getManifestEditor().getValidationResult(), lib)));

		column.setEditingSupport(TableControl.createEditingSupport(libraryTable.getViewer(), this, Library.class,
				getter, operationFactory, new TextCellEditor(libraryTable.getViewer().getTable())));
	}

	private void createLibraryActions(final Section section, final FormToolkit toolkit) {
		final Composite actions = toolkit.createComposite(section);
		GridLayoutFactory.fillDefaults().applyTo(actions);

		final Button exportButton = toolkit.createButton(actions, Messages.ManifestEditor_Export, SWT.PUSH);

		exportButton.addListener(SWT.Selection, _ -> exportLibrary(exportButton.getShell()));

		section.setTextClient(actions);
	}

	private void createDetailSection(final Composite parent, final FormToolkit toolkit) {
		final Section section = toolkit.createSection(parent,
				ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);

		section.setText(Messages.ManifestEditor_Details);
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Composite client = toolkit.createComposite(section);
		client.setLayout(new GridLayout(1, false));
		section.setClient(client);

		toolkit.createLabel(client, Messages.ManifestEditor_Comment);

		commentText = toolkit.createText(client, "", SWT.SINGLE | SWT.BORDER); //$NON-NLS-1$
		commentText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		commentText.addModifyListener(_ -> updateComment());

		createTabs(client, toolkit);
	}

	private void createTabs(final Composite parent, final FormToolkit toolkit) {
		final CTabFolder folder = new CTabFolder(parent, SWT.BORDER);

		GridDataFactory.fillDefaults().grab(true, true).indent(0, 20).applyTo(folder);

		toolkit.adapt(folder);

		contentTab = new ContentTab(folder, toolkit, this);
		createTab(folder, Messages.ManifestEditor_Contents, contentTab.getControl());

		dependenciesTab = new DependenciesTab(folder, toolkit, this);
		createTab(folder, Messages.ManifestEditor_Dependencies, dependenciesTab.getControl());

		attributeTab = new AttributeTab(folder, toolkit, this);
		createTab(folder, Messages.ManifestEditor_Attributes, attributeTab.getControl());

		folder.setSelection(0);
	}

	private static void createTab(final CTabFolder folder, final String title, final Control control) {
		final CTabItem item = new CTabItem(folder, SWT.NONE);
		item.setText(title);
		item.setControl(control);
	}

	private void addLibrary() {
		final Manifest manifest = getManifestEditor().getManifest();
		final Library library = ManifestHelper.createExportLibrary(manifest);

		if (manifest.getDependencies() != null) {
			library.setDependencies(EcoreUtil.copy(manifest.getDependencies()));
		}

		execute(new AddElementOperation<>(Messages.ManifestEditor_AddLibrary, getLibraries(), library));

		libraryTable.getViewer().setSelection(new StructuredSelection(library), true);
	}

	private void removeSelectedLibrary() {
		if (selectedLibrary == null) {
			return;
		}

		final String message = MessageFormat.format(Messages.ManifestEditor_RemoveLibrary, selectedLibrary.getName());

		execute(new RemoveElementOperation<>(message, getLibraries(), selectedLibrary));
	}

	private void updateSelection() {
		final Library selection = getSelection();

		if (selection == selectedLibrary) {
			return;
		}

		selectedLibrary = selection;

		updateCommentText();

		dependenciesTab.setInput(selectedLibrary);
		contentTab.setInput(selectedLibrary);
		attributeTab.setInput(selectedLibrary);
	}

	private void updateCommentText() {
		final String value = selectedLibrary != null ? Objects.toString(selectedLibrary.getComment(), "") //$NON-NLS-1$
				: ""; //$NON-NLS-1$

		if (!Objects.equals(commentText.getText(), value)) {
			commentText.setText(value);
		}

		final boolean enabled = selectedLibrary != null;
		commentText.setEnabled(enabled);
		commentText.setEditable(enabled);
	}

	private void updateComment() {
		if (selectedLibrary == null) {
			return;
		}

		final String value = commentText.getText();

		if (Objects.equals(selectedLibrary.getComment(), value)) {
			return;
		}

		final Library library = selectedLibrary;

		setValue(Messages.ManifestEditor_Comment, library::getComment, library::setComment, value);
	}

	private Library getSelection() {
		final Object selection = libraryTable.getViewer().getStructuredSelection().getFirstElement();

		return selection instanceof final Library library ? library : null;
	}

	private void exportLibrary(final Shell shell) {
		if (selectedLibrary == null) {
			return;
		}

		final IWizardDescriptor descriptor = PlatformUI.getWorkbench().getExportWizardRegistry()
				.findWizard(EXPORT_WIZARD_ID);

		if (descriptor != null && getEditorInput() instanceof final IFileEditorInput input) {
			try {
				final IExportWizard wizard = (IExportWizard) descriptor.createWizard();
				wizard.init(PlatformUI.getWorkbench(), new StructuredSelection(input.getFile()));

				new WizardDialog(shell, wizard).open();

			} catch (final CoreException e) {
				ILog.get().error(e.getMessage(), e);

				MessageDialog.openError(shell, Messages.LibraryExporter_ErrorTitle,
						MessageFormat.format(Messages.LibraryExporter_ErrorMessage, e.getMessage()));
			}
		}
	}

	private EList<Library> getLibraries() {
		return getModel() != null ? getModel().getLibrary() : ECollections.emptyEList();
	}

	@Override
	protected Exports getModel() {
		return getManifestEditor().getManifest().getExports();
	}
}
