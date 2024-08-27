/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *
 *   Peter Gsellmann
 *     - incorporating simple fb
 *
 *   Daniel Lindhuber, Bianca Wiesmayr
 *     - cleanup
 *   Patrick Aigner
 *     - change dialog integration
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editors;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.fbtypeeditor.Messages;
import org.eclipse.fordiac.ide.gef.annotation.FordiacMarkerGraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.validation.ValidationJob;
import org.eclipse.fordiac.ide.model.commands.change.AbstractChangeInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.edit.ITypeEntryEditor;
import org.eclipse.fordiac.ide.model.edit.TypeEntryAdapter;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Method;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.dialog.FBTypeEntryDataHandler;
import org.eclipse.fordiac.ide.model.search.dialog.FBTypeUpdateDialog;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.contentoutline.MultiPageEditorContentOutlinePage;
import org.eclipse.fordiac.ide.ui.editors.AbstractCloseAbleFormEditor;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.ide.ui.widget.SelectionTabbedPropertySheetPage;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.gef.commands.CompoundCommand;
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
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.INavigationLocationProvider;
import org.eclipse.ui.IReusableEditor;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.xtext.ui.editor.XtextEditor;

public class FBTypeEditor extends AbstractCloseAbleFormEditor implements ISelectionListener, CommandStackEventListener,
		ITabbedPropertySheetPageContributor, IGotoMarker, ITypeEntryEditor, INavigationLocationProvider {

	private Collection<IFBTEditorPart> editors;
	private TypeEntry typeEntry;
	private FBType fbType;
	private IContentOutlinePage contentOutline = null;
	private final CommandStack commandStack = new CommandStack();
	private GraphicalAnnotationModel annotationModel;
	private ValidationJob validationJob;
	private FBTypeUpdateDialog<TypeEntry> fbSaveDialog;
	private static final int DEFAULT_BUTTON_INDEX = 0; // Save Button
	private static final int CANCEL_BUTTON_INDEX = 1;
	private int interfaceChanges = 0; // number of interface changes happend since the last save

	private final TypeEntryAdapter adapter = new TypeEntryAdapter(this);

	@Override
	public void doSave(final IProgressMonitor monitor) {
		if ((null != typeEntry) && (checkTypeSaveAble())) {
			performPresaveHooks();
			if (interfaceChanges != 0) {
				createSaveDialog(monitor);
			} else {
				doSaveInternal(monitor);
			}
		}
	}

	private void createSaveDialog(final IProgressMonitor monitor) {
		final String[] labels = { Messages.FBTypeEditor_AlteringButton_SaveAndUpdate, // Messages.StructAlteringButton_SaveAs,
				SWT.getMessage("SWT_Cancel") }; //$NON-NLS-1$

		fbSaveDialog = new FBTypeUpdateDialog<>(null, Messages.FBTypeEditor_ViewingComposite_Headline, null, "", //$NON-NLS-1$
				MessageDialog.NONE, labels, DEFAULT_BUTTON_INDEX, new FBTypeEntryDataHandler(typeEntry));

		// Depending on the button clicked:
		switch (fbSaveDialog.open()) {
		case DEFAULT_BUTTON_INDEX:
			doSaveInternal(monitor);
			break;
		case CANCEL_BUTTON_INDEX:
			MessageDialog.openInformation(null, Messages.FBTypeEditor_ViewingComposite_Headline,
					Messages.WarningDialog_FBNotSaved);
			break;
		default:
			break;
		}
	}

	private void performPresaveHooks() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IConfigurationElement[] config = registry
				.getConfigurationElementsFor("org.eclipse.fordiac.ide.fbtypeeditor.fBTEditorValidation"); //$NON-NLS-1$

		for (final IConfigurationElement e : config) {
			try {
				final Object o = e.createExecutableExtension("class"); //$NON-NLS-1$
				if (o instanceof final IFBTValidation fbtValidation) {
					fbtValidation.invokeValidation(fbType);
				}
			} catch (final CoreException ex) {
				FordiacLogHelper.logError(ex.getMessage(), ex);
			}
		}
	}

	protected boolean checkTypeSaveAble() {
		if (fbType instanceof final CompositeFBType cFBType) {
			// only allow to save composite FBs if all contained FBs have types
			for (final FBNetworkElement fb : cFBType.getFBNetwork().getNetworkElements()) {
				if (null == fb.getTypeEntry()) {
					MessageDialog.openInformation(getSite().getShell(),
							Messages.FBTypeEditor_CompositeContainsFunctionBlockWithoutType,
							MessageFormat.format(Messages.FBTypeEditor_CheckTypeSaveAble, fb.getName()));
					return false;
				}
			}
		}
		return true;
	}

	private void doSaveInternal(final IProgressMonitor monitor) {

		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation(typeEntry.getFile().getParent()) {
			@Override
			protected void execute(final IProgressMonitor monitor)
					throws CoreException, InvocationTargetException, InterruptedException {
				// allow each editor to save back changes before saving to file
				editors.stream().filter(IEditorPart::isDirty)
						.forEach(editorPart -> SafeRunner.run(() -> editorPart.doSave(monitor)));
				typeEntry.save(fbType, monitor);
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
		interfaceChanges = 0;
	}

	@Override
	public void doSaveAs() {
		// TODO implement a save as new type method
	}

	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method checks
	 * that the input is an instance of <code>FBTypeEditorInput</code>.
	 *
	 * @param site        the site
	 * @param editorInput the editor input
	 *
	 * @throws PartInitException the part init exception
	 */
	@Override
	public void init(final IEditorSite site, final IEditorInput editorInput) throws PartInitException {
		if (editorInput instanceof final FileEditorInput fileEI) {
			typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(fileEI.getFile());
		} else if (editorInput instanceof final FBTypeEditorInput fbTypeEI) {
			typeEntry = fbTypeEI.getTypeEntry();
		}
		fbType = getFBType(typeEntry);

		if (fbType != null && typeEntry != null) {
			typeEntry.eAdapters().add(adapter);
			annotationModel = new FordiacMarkerGraphicalAnnotationModel(typeEntry.getFile());
			validationJob = new ValidationJob(getPartName(), commandStack, annotationModel);
		}

		site.getWorkbenchWindow().getSelectionService().addSelectionListener(this);
		getCommandStack().addCommandStackEventListener(this);

		super.init(site, editorInput);
	}

	@Override
	public void createPartControl(final Composite parent) {
		if (fbType != null) {
			super.createPartControl(parent);
		} else {
			showLoadErrorMessage(parent);
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
		messageLabel.setText(MessageFormat.format(Messages.FBTypeEditor_CouldNotLoadType, getEditorInput().getName()));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(messageLabel);
	}

	@Override
	public String getTitleToolTip() {
		final String tooltip = (typeEntry != null) ? typeEntry.getFullTypeName() + "\n" : ""; //$NON-NLS-1$ //$NON-NLS-2$
		return tooltip + super.getTitleToolTip();
	}

	@SuppressWarnings("static-method") // allow children to override this method
	protected FBType getFBType(final TypeEntry typeEntry) {
		if (typeEntry instanceof final FBTypeEntry fbtEntry) {
			return fbtEntry.getTypeEditable();
		}
		if (typeEntry instanceof final AdapterTypeEntry adpTypeEntry) {
			return adpTypeEntry.getTypeEditable();
		}
		return null;
	}

	public FBType getFBType() {
		return fbType;
	}

	public CommandStack getCommandStack() {
		return commandStack;
	}

	@Override
	public void dispose() {
		if ((fbType != null) && typeEntry.eAdapters().contains(adapter)) {
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
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	protected void addPages() {
		final SortedMap<Integer, IFBTEditorPart> sortedEditorsMap = getEditorsSorted();

		editors = new ArrayList<>();
		final FBTypeEditorInput editorInput = getFBTypeEditorInput();

		for (final IFBTEditorPart fbtEditorPart : sortedEditorsMap.values()) {
			editors.add(fbtEditorPart);
			try {
				// setCommonCommandStack needs to be called before the editor is added as page
				fbtEditorPart.setCommonCommandStack(commandStack);
				final int index = addPage(fbtEditorPart, editorInput);
				setPageText(index, fbtEditorPart.getTitle());
				setPageImage(index, fbtEditorPart.getTitleImage());
			} catch (final PartInitException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}

		}
	}

	private SortedMap<Integer, IFBTEditorPart> getEditorsSorted() {
		final SortedMap<Integer, IFBTEditorPart> sortedEditorsMap = new TreeMap<>();

		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IExtensionPoint point = registry.getExtensionPoint("org.eclipse.fordiac.ide.fbtypeeditor.fBTEditorTabs"); //$NON-NLS-1$
		final IExtension[] extensions = point.getExtensions();
		for (final IExtension extension : extensions) {
			final IConfigurationElement[] elements = extension.getConfigurationElements();
			for (final IConfigurationElement element : elements) {
				try {
					final Object obj = element.createExecutableExtension("class"); //$NON-NLS-1$
					if (obj instanceof final IFBTEditorPart fbtEditorPart) {
						final String elementType = element.getAttribute("type"); //$NON-NLS-1$
						final Integer sortIndex = Integer.valueOf(element.getAttribute("sortIndex")); //$NON-NLS-1$

						if (checkTypeEditorType(fbType, elementType)) {
							sortedEditorsMap.put(sortIndex, fbtEditorPart);
						}
					}
				} catch (final Exception e) {
					FordiacLogHelper.logError(e.getMessage(), e);
				}
			}
		}
		return sortedEditorsMap;
	}

	/**
	 * Check if the given editor type is a valid editor for the given type
	 *
	 * @param fbType     type to be edited in this type editor
	 * @param editorType editor type string as defined the fBTEditorTabs.exsd
	 * @return true if the editor should be shown otherwise false
	 */
	protected boolean checkTypeEditorType(final FBType fbType, final String editorType) {
		return ((editorType.equals("ForAllTypes")) || //$NON-NLS-1$
				(editorType.equals("ForAllFBTypes")) || //$NON-NLS-1$
				(editorType.equals("ForAllNonAdapterFBTypes") && !(fbType instanceof AdapterType)) || //$NON-NLS-1$
				(editorType.equals("ForAllNonFunctionFBTypes") && !(fbType instanceof FunctionFBType)) || //$NON-NLS-1$
				(editorType.equals("ForInterpretableFBTypes")) && isInterpretableType(fbType) || //$NON-NLS-1$
				((fbType instanceof BaseFBType) && editorType.equals("base")) || //$NON-NLS-1$
				((fbType instanceof BasicFBType) && editorType.equals("basic")) || //$NON-NLS-1$
				((fbType instanceof SimpleFBType) && editorType.equals("simple")) || //$NON-NLS-1$
				((fbType instanceof FunctionFBType) && editorType.equals("function")) || //$NON-NLS-1$
				((fbType instanceof ServiceInterfaceFBType) && editorType.equals("serviceInterface")) || //$NON-NLS-1$
				((fbType instanceof CompositeFBType) && editorType.equals("composite")) //$NON-NLS-1$
		);
	}

	protected static boolean isInterpretableType(final FBType fbType) {
		return fbType instanceof BaseFBType || fbType instanceof FunctionFBType;
	}

	private FBTypeEditorInput getFBTypeEditorInput() {
		return new FBTypeEditorInput(fbType, typeEntry);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.
	 * IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		if (this.equals(getSite().getPage().getActiveEditor()) && !(part instanceof PropertySheet)) {
			if (selection instanceof final StructuredSelection structSel
					&& structSel.getFirstElement() instanceof final URI uri) {
				final EObject selectedElement = fbType.eResource().getEObject(uri.fragment());
				if (selectedElement instanceof FBNetworkElement || selectedElement instanceof Algorithm
						|| selectedElement instanceof Method || selectedElement instanceof VarDeclaration) {
					handleContentOutlineSelection(new StructuredSelection(selectedElement));
				}
			} else {
				handleContentOutlineSelection(selection);
			}
		}
	}

	@Override
	public void setFocus() {
		super.setFocus();
		adapter.checkFileReload();
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == IContentOutlinePage.class) {
			return adapter.cast(getOutlinePage());
		}
		if (adapter == FBType.class || adapter == LibraryElement.class) {
			return adapter.cast(getFBType());
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
				result = editors.stream().map(innerEditor -> Adapters.adapt(innerEditor, adapter))
						.filter(Objects::nonNull).findFirst().orElse(null);
			}
			return result;
		}
		return null;
	}

	protected IContentOutlinePage getOutlinePage() {
		if (null == contentOutline) {
			contentOutline = new MultiPageEditorContentOutlinePage(this, new FBTypeContentOutline(fbType, this));
		}
		return contentOutline;
	}

	private static <T> boolean shouldCheckAllEditors(final Class<T> adapter) {
		return (adapter == ITextEditor.class) || (adapter == XtextEditor.class) || (adapter == FBNetworkEditor.class);
	}

	private boolean isEditorActive() {
		final int index = getActivePage();
		return index != -1 && !((CTabFolder) getContainer()).getItem(index).isDisposed();
	}

	public void handleContentOutlineSelection(final ISelection selection) {
		if ((selection instanceof final IStructuredSelection sel) && !selection.isEmpty()) {
			final Object selectedElement = sel.getFirstElement();
			int i = 0;
			for (final IFBTEditorPart editorPart : editors) {
				if (editorPart.outlineSelectionChanged(selectedElement)) {
					if (getActivePage() != i) {
						setActivePage(i);
					}
					break;
				}
				i++;
			}
		}
	}

	public IEditorActionBarContributor getActionBarContributor() {
		return null;
	}

	@Override
	public void stackChanged(final CommandStackEvent event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);

		if (isInterfaceChangeCommand(event.getCommand())) {
			switch (event.getDetail()) {
			case CommandStack.POST_EXECUTE, CommandStack.POST_REDO:
				interfaceChanges++;
				break;
			case CommandStack.POST_UNDO:
				interfaceChanges--;
				break;
			default:
				break;
			}
		}
	}

	@Override
	public String getContributorId() {
		return "property.contributor.fb"; //$NON-NLS-1$
	}

	@Override
	public void gotoMarker(final IMarker marker) {
		int i = 0;
		for (final IFBTEditorPart editorPart : editors) {
			if (editorPart.isMarkerTarget(marker)) {
				setActivePage(i);
				editorPart.gotoMarker(marker);
				break;
			}
			i++;
		}
	}

	@Override
	public void reloadType() {
		final FBType newFBType = (FBType) typeEntry.getTypeEditable();
		if (newFBType != fbType) {
			if ((fbType != null) && typeEntry.eAdapters().contains(adapter)) {
				typeEntry.eAdapters().remove(adapter);
			}
			fbType = newFBType;
			editors.stream().forEach(e -> e.reloadType(fbType));
			final IEditorPart activeEditor = getActiveEditor();
			if (activeEditor instanceof final IFBTEditorPart fbtEditorPart) {
				Display.getDefault().asyncExec(() -> EditorUtils.refreshPropertySheetWithSelection(this,
						activeEditor.getAdapter(GraphicalViewer.class), fbtEditorPart.getSelectableEditPart()));
			}
			getCommandStack().flush();
			typeEntry.eAdapters().add(adapter);
			setPartName(typeEntry.getTypeName());
		}
	}

	@Override
	protected void pageChange(final int newPageIndex) {
		super.pageChange(newPageIndex);
		getSite().getPage().getNavigationHistory().markLocation(this);
	}

	@Override
	public INavigationLocation createEmptyNavigationLocation() {
		return null;
	}

	@Override
	public INavigationLocation createNavigationLocation() {
		return (fbType != null) ? new FBTypeNavigationLocation(this) : null;
	}

	@Override
	public void setInput(final IEditorInput input) {
		if (input != null) {
			setPartName(TypeEntry.getTypeNameFromFileName(input.getName()));
			if (editors != null) {
				final FBTypeEditorInput subInput = getFBTypeEditorInput();
				editors.stream().filter(IReusableEditor.class::isInstance).map(IReusableEditor.class::cast)
						.forEach(e -> e.setInput(subInput));
			}
		}
		setInputWithNotify(input);
	}

	@Override
	protected IEditorSite createSite(final IEditorPart editor) {
		return new FBTypeMultiPageEditorSite(this, editor);
	}

	private boolean isInterfaceChangeCommand(final Command cmd) {
		if (cmd instanceof final CompoundCommand compoundCmd) {
			for (final Command childCmd : compoundCmd.getCommands()) {
				if (isInterfaceChangeCommand(childCmd)) {
					// for compound commands it is sufficient to know that at least one of the
					// children is an interface command of our type
					return true;
				}
			}
			return false;
		}

		// we need to check not only for the four commands but also if the element is
		// an interface element of the FBtype and not of any child (e.g., pin of a
		// subapp in a typed subapp)
		return ((cmd instanceof final CreateInterfaceElementCommand createIFCmd
				&& fbType.getInterfaceList().equals(createIFCmd.getTargetInterfaceList()))
				|| (cmd instanceof final DeleteInterfaceCommand delIFCmd
						&& fbType.getInterfaceList().equals(delIFCmd.getParent()))
				|| (cmd instanceof final AbstractChangeInterfaceElementCommand changeIFCmd
						&& fbType.getInterfaceList().equals(changeIFCmd.getInterfaceElement().eContainer()))
				|| (cmd instanceof final ChangeNameCommand chgNameCmd
						&& fbType.getInterfaceList().equals(chgNameCmd.getElement().eContainer())));
	}

	@Override
	public LibraryElement getEditedElement() {
		return getFBType();
	}

}
