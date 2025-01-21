/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Johannes Kepler University, Linz,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *        - initial API and implementation and/or initial documentation
 *   Peter Gsellmann - incorporating simple fb
 *   Daniel Lindhuber, Bianca Wiesmayr - cleanup
 *   Patrick Aigner  - change dialog integration
 *   Alois Zoitl     - extracted and generalized from FBTypeEditor
 *******************************************************************************/
package org.eclipse.fordiac.ide.typeeditor;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Objects;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.gef.annotation.FordiacMarkerGraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.validation.ValidationJob;
import org.eclipse.fordiac.ide.model.edit.ITypeEntryEditor;
import org.eclipse.fordiac.ide.model.edit.TypeEntryAdapter;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Method;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.dialog.AbstractTypeEntryDataHandler;
import org.eclipse.fordiac.ide.model.search.dialog.FBTypeUpdateDialog;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typeeditor.internal.TypeEditorPageFactory;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.editors.AbstractCloseAbleFormEditor;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.ide.ui.widget.SelectionTabbedPropertySheetPage;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.xtext.ui.editor.XtextEditor;

public abstract class AbstractTypeEditor extends AbstractCloseAbleFormEditor implements IGotoMarker,
		CommandStackEventListener, ITabbedPropertySheetPageContributor, ITypeEntryEditor, ISelectionListener {

	private static final int DEFAULT_BUTTON_INDEX = 0; // Save Button
	private static final int CANCEL_BUTTON_INDEX = 1;

	private static TypeEditorPageFactory typeEditorPageFactory = new TypeEditorPageFactory();

	private final CommandStack commandStack = new CommandStack();
	private Collection<ITypeEditorPage> editorPages;
	private final TypeEntryAdapter adapter = new TypeEntryAdapter(this);
	private GraphicalAnnotationModel annotationModel;
	private ValidationJob validationJob;

	@Override
	protected void addPages() {
		final TypeEditorInput ei = getTypeEditorInput();
		editorPages = typeEditorPageFactory.getEditors(getType());
		for (final ITypeEditorPage typeEditorPage : editorPages) {
			try {
				// set command stack has to be done before the page is added
				typeEditorPage.setCommonCommandStack(getCommandStack());
				final int index = addPage(typeEditorPage, ei);
				setPageText(index, typeEditorPage.getTitle());
				setPageImage(index, typeEditorPage.getTitleImage());
			} catch (final PartInitException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}
	}

	private TypeEditorInput checkEditorInput(final IEditorInput editorInput) {
		if (editorInput instanceof final TypeEditorInput typeEI) {
			return typeEI;
		}

		if (editorInput instanceof final IFileEditorInput fileEI) {
			if (getTypeEditorInput() != null) {
				// when we already had an input it means the file for our type has changed,
				// provide a new TypeEditorInput with the new file
				return new TypeEditorInput(getTypeEditorInput().getContent(), getTypeEditorInput().getTypeEntry(),
						fileEI.getFile());
			}

			final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(fileEI.getFile());
			final LibraryElement type = (typeEntry != null) ? typeEntry.getTypeEditable() : null;
			// FIXME replace with EcoreUtil.copy(typeEntry.getType()) when type editable is
			// removed
			return new TypeEditorInput(type, typeEntry);
		}

		return null;
	}

	@Override
	public void createPartControl(final Composite parent) {
		if (getType() != null) {
			super.createPartControl(parent);
		} else {
			showLoadErrorMessage(parent);
		}
	}

	@Override
	protected IEditorSite createSite(final IEditorPart editor) {
		if (editor instanceof ITextEditor) {
			return new TypeTextMultiPageEditorSite(this, editor);
		}
		return new TypeMultiPageEditorSite(this, editor);
	}

	protected abstract AbstractTypeEntryDataHandler<? extends TypeEntry> createTypeEntryDataHandler();

	private MessageDialog createTypeUpdateDialog() {
		final String[] labels = { Messages.TypeEditor_TypeUpdateDialog_SaveAndUpdate, SWT.getMessage("SWT_Cancel") }; //$NON-NLS-1$

		return new FBTypeUpdateDialog<>(getSite().getShell(), Messages.TypeEditor_TypeUpdateDialog_Headline,
				Messages.TypeEditor_TypeUpdateDialog_Description, labels, DEFAULT_BUTTON_INDEX,
				createTypeEntryDataHandler());
	}

	@Override
	public void dispose() {
		final TypeEntry typeEntry = getTypeEntry();
		if ((typeEntry != null) && typeEntry.eAdapters().contains(adapter)) {
			typeEntry.eAdapters().remove(adapter);
		}
		if (validationJob != null) {
			validationJob.dispose();
		}
		if (annotationModel != null) {
			annotationModel.dispose();
		}

		// get these values here before calling super dispose
		final boolean dirty = isDirty();

		if (null != getSite()) {
			getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);
		}

		super.dispose();

		if (dirty && typeEntry != null) {
			// purge editable type from typelib after super.dispose() so that no notifiers
			// will be called
			typeEntry.setTypeEditable(null);
		}

		getCommandStack().removeCommandStackEventListener(this);
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		if (null != getTypeEntry()) {
			int result = DEFAULT_BUTTON_INDEX;
			if (dependencyAffectingTypeChange()) {
				result = createTypeUpdateDialog().open();
			}

			switch (result) {
			case DEFAULT_BUTTON_INDEX:
				doSaveInternal(monitor);
				break;
			case CANCEL_BUTTON_INDEX:
				MessageDialog.openInformation(null, Messages.TypeEditor_WarningDialog_Headline,
						Messages.TypeEditor_WarningDialog_NotSaved);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void doSaveAs() {
		// currently we do not allow any save as of types
	}

	private void doSaveInternal(final IProgressMonitor monitor) {
		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation(getTypeEntry().getFile().getParent()) {
			@Override
			protected void execute(final IProgressMonitor monitor)
					throws CoreException, InvocationTargetException, InterruptedException {
				// allow each editor to save back changes before saving to file
				getEditorPages().stream().filter(IEditorPart::isDirty)
						.forEach(editorPart -> SafeRunner.run(() -> editorPart.doSave(monitor)));
				getTypeEntry().save(getType(), monitor);
			}
		};
		try {
			operation.run(monitor);
		} catch (final InvocationTargetException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		} catch (final InterruptedException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			Thread.currentThread().interrupt();
		}

		getCommandStack().markSaveLocation();
	}

	/**
	 * Check if the current changes have an impact on any dependent types.
	 *
	 * @return true if the user shall be presented with a dialog with all affected
	 *         dependent types
	 */
	protected abstract boolean dependencyAffectingTypeChange();

	public CommandStack getCommandStack() {
		return commandStack;
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == LibraryElement.class) {
			return adapter.cast(getType());
		}
		if (adapter == CommandStack.class) {
			return adapter.cast(getCommandStack());
		}
		if (adapter == IPropertySheetPage.class) {
			return adapter.cast(new SelectionTabbedPropertySheetPage(this));
		}
		if (adapter == IGotoMarker.class) {
			return adapter.cast(this);
		}
		if (adapter == GraphicalAnnotationModel.class) {
			return adapter.cast(annotationModel);
		}

		if (isEditorActive()) {
			// we should only call super if the editor is active otherwise we may get
			// disposed errors
			T result = super.getAdapter(adapter);
			if (result == null && shouldCheckAllEditors(adapter)) {
				result = getEditorPages().stream().map(innerEditor -> Adapters.adapt(innerEditor, adapter))
						.filter(Objects::nonNull).findFirst().orElse(null);
			}
			return result;
		}
		return null;
	}

	private TypeEditorInput getTypeEditorInput() {
		return (super.getEditorInput() instanceof final TypeEditorInput typeEI) ? typeEI : null;
	}

	protected Collection<ITypeEditorPage> getEditorPages() {
		return editorPages;
	}

	@Override
	public String getTitleToolTip() {
		final String tooltip = (getTypeEntry() != null) ? getTypeEntry().getFullTypeName() + "\n" : ""; //$NON-NLS-1$ //$NON-NLS-2$
		return tooltip + super.getTitleToolTip();
	}

	protected LibraryElement getType() {
		final TypeEditorInput ei = getTypeEditorInput();
		return (ei != null) ? ei.getContent() : null;
	}

	protected TypeEntry getTypeEntry() {
		final TypeEditorInput ei = getTypeEditorInput();
		return (ei != null) ? ei.getTypeEntry() : null;
	}

	@Override
	public void gotoMarker(final IMarker marker) {
		int i = 0;
		for (final ITypeEditorPage editorPart : getEditorPages()) {
			if (editorPart.isMarkerTarget(marker)) {
				setActivePage(i);
				editorPart.gotoMarker(marker);
				break;
			}
			i++;
		}
	}

	private void handleContentOutlineSelection(final IWorkbenchPart part, final ISelection selection) {
		if ((selection instanceof final IStructuredSelection sel) && !selection.isEmpty()) {
			final Object selectedElement = sel.getFirstElement();
			int i = 0;
			for (final ITypeEditorPage editorPart : getEditorPages()) {
				if (editorPart.outlineSelectionChanged(selectedElement)) {
					if (getActivePage() != i) {
						setActivePage(i);
					}
					// the property sheet needs the original part the selection came from!
					forwardSelectionToPropertySheet(part, selection);
					break;
				}
				i++;
			}
		}
	}

	private void forwardSelectionToPropertySheet(final IWorkbenchPart part, final ISelection selection) {
		final IWorkbenchPart propertySheetPart = getSite().getWorkbenchWindow().getActivePage()
				.findView(IPageLayout.ID_PROP_SHEET);
		if (propertySheetPart instanceof final PropertySheet propertySheet) {
			propertySheet.selectionChanged(part, selection);
		}
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput editorInput) throws PartInitException {
		getCommandStack().addCommandStackEventListener(this);
		super.init(site, editorInput);
		site.getWorkbenchWindow().getSelectionService().addSelectionListener(this);
	}

	private boolean isEditorActive() {
		final int index = getActivePage();
		return index != -1 && !((CTabFolder) getContainer()).getItem(index).isDisposed();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@SuppressWarnings("static-method") // allow subclasses to perform additional checks
	private boolean isValidTypeEditorInput(final TypeEditorInput typeEditorInput) {
		return typeEditorInput != null && typeEditorInput.getContent() != null
				&& typeEditorInput.getTypeEntry() != null;
	}

	@Override
	protected void pageChange(final int newPageIndex) {
		super.pageChange(newPageIndex);
		getSite().getPage().getNavigationHistory().markLocation(this);
	}

	@Override
	public void reloadType() {
		final LibraryElement newFBType = getTypeEntry().getTypeEditable();
		final LibraryElement curType = getType();
		if (newFBType != curType) {
			if ((curType != null) && getTypeEntry().eAdapters().contains(adapter)) {
				getTypeEntry().eAdapters().remove(adapter);
			}
			final TypeEditorInput typeEI = getTypeEditorInput();
			if (typeEI != null) {
				typeEI.setType(newFBType);
			}
			getEditorPages().stream().forEach(ITypeEditorPage::reloadType);
			final IEditorPart activeEditor = getActiveEditor();
			if (activeEditor instanceof final ITypeEditorPage editorPage) {
				Display.getDefault().asyncExec(() -> EditorUtils.refreshPropertySheetWithSelection(this,
						activeEditor.getAdapter(GraphicalViewer.class), editorPage.getSelectableObject()));
			}
			getCommandStack().flush();
			getTypeEntry().eAdapters().add(adapter);
			setPartName(getTypeEntry().getTypeName());
		}
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		if (this.equals(getSite().getPage().getActiveEditor()) && !(part instanceof PropertySheet)) {
			if (selection instanceof final StructuredSelection structSel
					&& structSel.getFirstElement() instanceof final URI uri) {
				final EObject selectedElement = getType().eResource().getEObject(uri.fragment());
				if (selectedElement instanceof FBNetworkElement || selectedElement instanceof Algorithm
						|| selectedElement instanceof Method || selectedElement instanceof VarDeclaration) {
					handleContentOutlineSelection(part, new StructuredSelection(selectedElement));
				}
			} else if (part instanceof ContentOutline) {
				// only update selection if it comes from the outline
				handleContentOutlineSelection(part, selection);
			}
		}
	}

	@Override
	public void setFocus() {
		super.setFocus();
		adapter.checkFileReload();
	}

	@Override
	public void setInput(final IEditorInput input) {
		if (validationJob != null) {
			validationJob.dispose();
			validationJob = null;
		}
		if (annotationModel != null) {
			annotationModel.dispose();
			annotationModel = null;
		}
		if (input != null) {
			setPartName(TypeEntry.getTypeNameFromFileName(input.getName()));
		}

		final TypeEditorInput typeEditorInput = checkEditorInput(input);
		if (isValidTypeEditorInput(typeEditorInput)) {
			typeEditorInput.getTypeEntry().eAdapters().add(adapter);
			annotationModel = new FordiacMarkerGraphicalAnnotationModel(typeEditorInput.getFile(),
					typeEditorInput::getContent);
			validationJob = new ValidationJob(getPartName(), getCommandStack(), annotationModel);
			if (getEditorPages() != null) {
				getEditorPages().forEach(e -> e.setInput(typeEditorInput));
			}
			setInputWithNotify(typeEditorInput);
		} else {
			setInputWithNotify(input);
		}
	}

	public void showLoadErrorMessage(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).applyTo(composite);

		final Image image = Display.getDefault().getSystemImage(SWT.ICON_ERROR);
		final Label imageLabel = new Label(composite, SWT.NULL);
		image.setBackground(imageLabel.getBackground());
		imageLabel.setImage(image);
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.BEGINNING).applyTo(imageLabel);

		final Label messageLabel = new Label(composite, SWT.NONE);
		messageLabel.setText(MessageFormat.format(Messages.TypeEditor_CouldNotLoadType, getEditorInput().getName()));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(messageLabel);
	}

	private static <T> boolean shouldCheckAllEditors(final Class<T> adapter) {
		return (adapter == ITextEditor.class) || (adapter == XtextEditor.class) || (adapter == FBNetworkEditor.class);
	}

	@Override
	public void stackChanged(final CommandStackEvent event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

}
