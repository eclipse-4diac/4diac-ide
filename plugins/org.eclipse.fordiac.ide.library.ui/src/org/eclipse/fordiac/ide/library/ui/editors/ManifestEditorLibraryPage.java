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
import java.util.function.Consumer;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.library.model.library.Exports;
import org.eclipse.fordiac.ide.library.model.library.Library;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.library.ui.editors.operations.AddElementOperation;
import org.eclipse.fordiac.ide.library.ui.editors.operations.RemoveElementOperation;
import org.eclipse.fordiac.ide.library.ui.editors.tabs.AttributeTab;
import org.eclipse.fordiac.ide.library.ui.editors.tabs.ContentTab;
import org.eclipse.fordiac.ide.library.ui.editors.tabs.DependenciesTab;
import org.eclipse.fordiac.ide.library.ui.editors.tabs.TableControl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.StructuredSelection;
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

	private static final String INVALID_NAME_KEY = "manifest.library.symbolicName"; //$NON-NLS-1$

	private Text nameText;
	private Text symbolicNameText;
	private Text commentText;

	private CTabFolder tabFolder;

	private DependenciesTab dependenciesTab;
	private AttributeTab attributeTab;
	private ContentTab contentTab;

	private TableControl libraryTable;
	private Library selectedLibrary;

	protected ManifestEditorLibraryPage(final ManifestEditor editor, final String id, final String title) {
		super(editor, id, title);
	}

	@Override
	protected void createPageContent(final Composite parent, final FormToolkit toolkit, final IManagedForm form) {
		createLibrariesSection(parent, toolkit);
		createDetailSection(parent, toolkit);
		updateSelection();
	}

	private Composite createLibrariesSection(final Composite parent, final FormToolkit toolkit) {
		final Section section = toolkit.createSection(parent,
				ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);
		section.setText("Libraries"); //$NON-NLS-1$
		section.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		createLibraryActions(section, toolkit);

		final Composite client = toolkit.createComposite(section);
		client.setLayout(new GridLayout(1, false));
		section.setClient(client);

		libraryTable = new TableControl(client, toolkit, ""); //$NON-NLS-1$

		final var nameCol = libraryTable.createColumn(Messages.ManifestEditor_Name, 40);
		nameCol.setLabelProvider(TableControl.createLabelProvider(Library.class, Library::getName));

		final var symNameCol = libraryTable.createColumn(Messages.ManifestEditor_SymbolicName, 60);
		symNameCol.setLabelProvider(TableControl.createLabelProvider(Library.class, Library::getSymbolicName));

		libraryTable.setAddHandler(this::addLibrary);
		libraryTable.setRemoveHandler(this::removeSelectedLibrary);
		libraryTable.getViewer().addSelectionChangedListener(_ -> updateSelection());
		libraryTable.setInput(getLibraries());

		return client;
	}

	private void createLibraryActions(final Section section, final FormToolkit toolkit) {
		final Composite actions = toolkit.createComposite(section);
		GridLayoutFactory.fillDefaults().applyTo(actions);

		final Button exportButton = toolkit.createButton(actions, "Export", SWT.PUSH); //$NON-NLS-1$
		exportButton.addListener(SWT.Selection, _ -> exportLibrary(exportButton.getShell()));
		section.setTextClient(actions);
	}

	private void createDetailSection(final Composite parent, final FormToolkit toolkit) {
		final Section section = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR);
		section.setText("Details"); //$NON-NLS-1$
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Composite client = toolkit.createComposite(section);
		client.setLayout(new GridLayout(1, false));
		section.setClient(client);

		final Composite nameComposite = toolkit.createComposite(client);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(nameComposite);
		nameComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		toolkit.createLabel(nameComposite, Messages.ManifestEditor_Name);
		toolkit.createLabel(nameComposite, Messages.ManifestEditor_SymbolicName);

		nameText = toolkit.createText(nameComposite, "", SWT.SINGLE | SWT.BORDER); //$NON-NLS-1$
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		symbolicNameText = toolkit.createText(nameComposite, "", SWT.SINGLE | SWT.BORDER); //$NON-NLS-1$
		symbolicNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		toolkit.createLabel(client, Messages.ManifestEditor_Comment);

		commentText = toolkit.createText(client, "", SWT.SINGLE | SWT.BORDER); //$NON-NLS-1$
		commentText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		nameText.addModifyListener(_ -> updateName());
		symbolicNameText.addModifyListener(_ -> updateSymbolicName());
		commentText.addModifyListener(_ -> updateComment());

		// register validation listener after update/modify
		addControlValidation(symbolicNameText, INVALID_NAME_KEY, "Invalid symbolic name", this::isValidName); //$NON-NLS-1$

		createTabs(client, toolkit);
	}

	private void createTabs(final Composite parent, final FormToolkit toolkit) {
		tabFolder = new CTabFolder(parent, SWT.BORDER);

		GridDataFactory.fillDefaults().grab(true, true).indent(0, 20).applyTo(tabFolder);
		toolkit.adapt(tabFolder);

		contentTab = new ContentTab(tabFolder, toolkit, this);
		createTab(Messages.ManifestEditor_Contents, contentTab.getControl());

		dependenciesTab = new DependenciesTab(tabFolder, toolkit, this);
		createTab(Messages.ManifestEditor_Dependencies, dependenciesTab.getControl());

		attributeTab = new AttributeTab(tabFolder, toolkit, this);
		createTab(Messages.ManifestEditor_Attributes, attributeTab.getControl());

		tabFolder.setSelection(0);
	}

	private void createTab(final String title, final Control control) {
		final CTabItem item = new CTabItem(tabFolder, SWT.NONE);
		item.setText(title);
		item.setControl(control);
	}

	private void addLibrary() {
		final Library library = ManifestHelper.createExportLibrary(getManifestEditor().getManifest());

		execute(new AddElementOperation<>(Messages.ManifestEditor_AddLibrary, getLibraries(), library,
				libraryTable.getViewer()::refresh));

		libraryTable.getViewer().setSelection(new StructuredSelection(library), true);
	}

	private void removeSelectedLibrary() {
		final Library library = getSelection();

		if (library == null) {
			return;
		}

		final String message = MessageFormat.format(Messages.ManifestEditor_RemoveLibrary, library.getName());
		execute(new RemoveElementOperation<>(message, getLibraries(), library, libraryTable.getViewer()::refresh));
	}

	private void updateSelection() {
		selectedLibrary = getSelection();

		updateDetails();
		dependenciesTab.setInput(selectedLibrary);
		contentTab.setInput(selectedLibrary);
		attributeTab.setInput(selectedLibrary);
	}

	private void updateDetails() {
		if (selectedLibrary != null) {
			nameText.setText(Objects.toString(selectedLibrary.getName(), "")); //$NON-NLS-1$
			symbolicNameText.setText(Objects.toString(selectedLibrary.getSymbolicName(), "")); //$NON-NLS-1$
			commentText.setText(Objects.toString(selectedLibrary.getComment(), "")); //$NON-NLS-1$
		} else {
			nameText.setText(""); //$NON-NLS-1$
			symbolicNameText.setText(""); //$NON-NLS-1$
			commentText.setText(""); //$NON-NLS-1$
		}

		final boolean editable = selectedLibrary != null;

		nameText.setEditable(editable);
		symbolicNameText.setEditable(editable);
		commentText.setEditable(editable);

		nameText.setEnabled(editable);
		symbolicNameText.setEnabled(editable);
		commentText.setEnabled(editable);
	}

	private void updateName() {
		if (selectedLibrary == null) {
			return;
		}

		final Library library = selectedLibrary;

		setValue(Messages.ManifestEditor_Name, library::getName,
				value -> setLibraryValue(library, value, library::setName, nameText), nameText.getText());
	}

	private void updateSymbolicName() {
		if (selectedLibrary == null) {
			return;
		}

		final Library library = selectedLibrary;

		setValue(Messages.ManifestEditor_SymbolicName, library::getSymbolicName,
				value -> setLibraryValue(library, value, library::setSymbolicName, symbolicNameText),
				symbolicNameText.getText());
	}

	private void updateComment() {
		if (selectedLibrary == null) {
			return;
		}

		final Library library = selectedLibrary;

		setValue(Messages.ManifestEditor_Comment, library::getComment,
				value -> setLibraryValue(library, value, library::setComment, commentText), commentText.getText());
	}

	private void setLibraryValue(final Library library, final String value, final Consumer<String> setter,
			final Text text) {

		setter.accept(value);
		libraryTable.getViewer().update(library, null);

		if (library == selectedLibrary) {
			final String textValue = Objects.toString(value, ""); //$NON-NLS-1$

			if (Objects.equals(text.getText(), textValue)) {
				return;
			}

			text.setText(textValue);
		}
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
				.findWizard("org.eclipse.fordiac.ide.library.ui.wizards.LibraryExportWizard"); //$NON-NLS-1$

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

	private boolean isValidName(final String name) {
		// should not appear twice, restriction on character set?
		return getLibraries().stream().filter(lib -> Objects.equals(name, lib.getSymbolicName())).count() <= 1;
	}

	private EList<Library> getLibraries() {
		return getModel() != null ? getModel().getLibrary() : ECollections.emptyEList();
	}

	@Override
	protected Exports getModel() {
		return getManifestEditor().getManifest().getExports();
	}

	@Override
	protected boolean isValid() {
		return true;
	}
}
