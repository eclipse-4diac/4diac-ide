/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University, Linz
 *               2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber, Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *   Muttenthaler Benjamin
 *     - fixed reload of view if file on file system did change
 *     - use new saveType method of AbstractTypeExporter
 *     - replaced DataTypeListener by AdapterImpl
 *     - keep a copy of the datatype object in the view, otherwise the content of the file is changed even the save button was not pressed
 *   Lukas Wais
 *     - enabled Save As
 *******************************************************************************/

package org.eclipse.fordiac.ide.datatypeeditor.editors;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.datatypeedito.wizards.SaveAsStructTypeWizard;
import org.eclipse.fordiac.ide.datatypeeditor.Messages;
import org.eclipse.fordiac.ide.datatypeeditor.widgets.StructEditingComposite;
import org.eclipse.fordiac.ide.gef.annotation.FordiacMarkerGraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.validation.ValidationJob;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.SaveTypeEntryCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateUntypedSubAppInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.dialog.FBTypeUpdateDialog;
import org.eclipse.fordiac.ide.model.search.dialog.StructuredDataTypeDataHandler;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.editors.ITypeEntryEditor;
import org.eclipse.fordiac.ide.model.ui.editors.TypeEntryAdapter;
import org.eclipse.fordiac.ide.typemanagement.util.FBUpdater;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.RedoAction;
import org.eclipse.gef.ui.actions.UndoAction;
import org.eclipse.gef.ui.actions.UpdateAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class DataTypeEditor extends EditorPart implements CommandStackEventListener,
		ITabbedPropertySheetPageContributor, ISelectionListener, ITypeEntryEditor {

	private final CommandStack commandStack = new CommandStack();
	private GraphicalAnnotationModel annotationModel;
	private ValidationJob validationJob;
	private StructEditingComposite structComposite;
	private Composite errorComposite;
	private boolean importFailed;
	private boolean outsideWorkspace;
	private FBTypeUpdateDialog<DataTypeEntry> structSaveDialog;
	private static final int DEFAULT_BUTTON_INDEX = 0; // This would be the "Save" button
	private static final int SAVE_AS_BUTTON_INDEX = 1;
	private static final int CANCEL_BUTTON_INDEX = 2;

	private ActionRegistry actionRegistry;
	private final List<String> selectionActions = new ArrayList<>();
	private final List<String> stackActions = new ArrayList<>();
	private final List<String> propertyActions = new ArrayList<>();

	private DataTypeEntry dataTypeEntry;
	private AnyDerivedType dataTypeEditable;

	private final TypeEntryAdapter adapter = new TypeEntryAdapter(this);

	@Override
	public void stackChanged(final CommandStackEvent event) {
		updateActions(stackActions);
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@Override
	public String getContributorId() {
		return "org.eclipse.fordiac.ide.datatypeeditor.editors.DataTypeEditor"; //$NON-NLS-1$
	}

	@Override
	public void dispose() {
		// get these values here before calling super dispose
		final boolean dirty = isDirty();

		getCommandStack().removeCommandStackEventListener(this);
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);
		getActionRegistry().dispose();
		removeListenerFromDataTypeObj();
		if (validationJob != null) {
			validationJob.dispose();
		}
		if (annotationModel != null) {
			annotationModel.dispose();
		}
		super.dispose();
		if (dirty && dataTypeEntry != null) {
			// purge editable type from type entry after super.dispose() so that no
			// notifiers will be called
			dataTypeEntry.setTypeEditable(null);
		}
	}

	@Override
	protected void firePropertyChange(final int property) {
		super.firePropertyChange(property);
		updateActions(propertyActions);
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		removeListenerFromDataTypeObj();
		loadAllOpenEditors();
		createSaveDialog(monitor);
	}

	private void createSaveDialog(final IProgressMonitor monitor) {
		final String[] labels = { Messages.StructAlteringButton_SaveAndUpdate, Messages.StructAlteringButton_SaveAs,
				SWT.getMessage("SWT_Cancel") }; //$NON-NLS-1$

		structSaveDialog = new FBTypeUpdateDialog<>(null, Messages.StructViewingComposite_Headline, null, "", //$NON-NLS-1$
				MessageDialog.NONE, labels, DEFAULT_BUTTON_INDEX, new StructuredDataTypeDataHandler(dataTypeEntry));

		// Depending on the button clicked:
		switch (structSaveDialog.open()) {
		case DEFAULT_BUTTON_INDEX:
			doSaveInternal(monitor, structSaveDialog.getDataHandler().getCollectedElements());
			break;
		case SAVE_AS_BUTTON_INDEX:
			doSaveAs();
			break;
		case CANCEL_BUTTON_INDEX:
			MessageDialog.openInformation(null, Messages.StructViewingComposite_Headline,
					Messages.WarningDialog_StructNotSaved);
			break;
		default:
			break;
		}
	}

	private void doSaveInternal(final IProgressMonitor monitor, final Set<INamedElement> set) {
		commandStack.markSaveLocation();
		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation(dataTypeEntry.getFile().getParent()) {

			@Override
			protected void execute(final IProgressMonitor monitor)
					throws CoreException, InvocationTargetException, InterruptedException {
				dataTypeEntry.save(dataTypeEditable, monitor);
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
		addListenerToDataTypeObj();
		firePropertyChange(IEditorPart.PROP_DIRTY);
		updateFB(set);
	}

	@Override
	public void doSaveAs() {
		if (dataTypeEditable instanceof final StructuredType structuredType) {
			new WizardDialog(null, new SaveAsStructTypeWizard(structuredType, this)).open();
		}
	}

	private void updateFB(final Set<INamedElement> set) {
		Command cmd = new CompoundCommand();
		cmd = cmd.chain(getUpdateStructTypes(set));
		cmd = cmd.chain(getUpdateStructManipulatorsCommand(set));
		cmd = cmd.chain(getUpdateTypesCommand(set));
		cmd = cmd.chain(getUpdateInstancesCommand(set));
		Display.getDefault().asyncExec(cmd::execute);
	}

	private Command getUpdateTypesCommand(final Set<INamedElement> set) {
		final List<FBType> fbTypes = set.stream().filter(FBType.class::isInstance).map(FBType.class::cast).toList();
		return FBUpdater.createUpdatePinInTypeDeclarationCommand(fbTypes,
				structSaveDialog.getDataHandler().getTypeOfElementList(fbTypes), null);
	}

	private Command getUpdateStructTypes(final Set<INamedElement> set) {
		final List<Command> commands = new ArrayList<>();
		set.stream().filter(StructuredType.class::isInstance).forEach(instance -> {
			if (instance instanceof final StructuredType st) {
				for (final VarDeclaration varDeclaration : st.getMemberVariables()) {
					final String typeName = varDeclaration.getTypeName();
					if (typeName.equals(structSaveDialog.getDataHandler().getTypeOfElement(st).getTypeName())) {
						commands.add(ChangeDataTypeCommand.forDataType(varDeclaration,
								structSaveDialog.getDataHandler().getTypeOfElement(st).getTypeEditable()));
					}
				}
				commands.add(new SaveTypeEntryCommand(st));
			}
		});
		Command cmd = new CompoundCommand();
		for (final Command subCmd : commands) {
			cmd = cmd.chain(subCmd);
		}
		return cmd;
	}

	private Command getUpdateInstancesCommand(final Set<INamedElement> set) {
		final List<Command> commands = new ArrayList<>();
		set.stream().filter(FBNetworkElement.class::isInstance)
				.filter(Predicate.not(StructManipulator.class::isInstance)).map(FBNetworkElement.class::cast).map(x -> {
					if (x instanceof final SubApp subApp && !subApp.isTyped()) {
						return new UpdateUntypedSubAppInterfaceCommand(x,
								structSaveDialog.getDataHandler().getTypeOfElement(x));
					}
					return new UpdateFBTypeCommand(x, structSaveDialog.getDataHandler().getTypeOfElement(x));
				}).forEachOrdered(commands::add);

		Command cmd = new CompoundCommand();
		for (final Command subCmd : commands) {
			cmd = cmd.chain(subCmd);
		}
		return cmd;
	}

	private Command getUpdateStructManipulatorsCommand(final Set<INamedElement> set) {
		final List<StructManipulator> muxes = set.stream().filter(StructManipulator.class::isInstance)
				.map(StructManipulator.class::cast).toList();

		return FBUpdater.createStructManipulatorsUpdateCommand(muxes,
				structSaveDialog.getDataHandler().getTypeOfElementList(muxes));
	}

	private static void loadAllOpenEditors() {
		final IEditorReference[] openEditors = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		for (final IEditorReference iEditorReference : openEditors) {
			try {
				if (iEditorReference.getEditorInput().exists()) {
					iEditorReference.getEditor(true);
				}
			} catch (final PartInitException e) {
				FordiacLogHelper.logError("Error while loading Editor: " + e.getMessage()); //$NON-NLS-1$
			}
		}
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		importType(input);
		setInput(input);
		setSite(site);
		addListenerToDataTypeObj();
		site.getWorkbenchWindow().getSelectionService().addSelectionListener(this);
		getCommandStack().addCommandStackEventListener(this);
		initializeActionRegistry();
		setActionHandlers(site);
		if (input instanceof final IFileEditorInput fileEditorInput) {
			annotationModel = new FordiacMarkerGraphicalAnnotationModel(fileEditorInput.getFile());
			validationJob = new ValidationJob(getPartName(), commandStack, annotationModel);
		}
	}

	@Override
	public String getTitleToolTip() {
		final String tooltip = (dataTypeEntry != null) ? dataTypeEntry.getFullTypeName() + "\n" : ""; //$NON-NLS-1$ //$NON-NLS-2$
		return tooltip + super.getTitleToolTip();
	}

	private void addListenerToDataTypeObj() {
		if (dataTypeEntry != null) {
			dataTypeEntry.eAdapters().add(adapter);
		}
	}

	private void removeListenerFromDataTypeObj() {
		if ((dataTypeEntry != null) && dataTypeEntry.eAdapters().contains(adapter)) {
			dataTypeEntry.eAdapters().remove(adapter);
		}
	}

	private void importType(final IEditorInput input) throws PartInitException {
		if (input instanceof final FileEditorInput fileEditorInput) {
			final IFile file = fileEditorInput.getFile();
			try {
				importFailed = importTypeBasedOnFile(file);
			} catch (final Exception e) {
				throw new PartInitException(e.getMessage(), e);
			}
		} else if (input instanceof FileStoreEditorInput) {
			// is called when files are opened via File -> Open File
			importFailed = true;
			outsideWorkspace = true;
		}
	}

	private boolean importTypeBasedOnFile(final IFile file) throws CoreException {
		file.refreshLocal(IResource.DEPTH_ONE, new NullProgressMonitor());
		// exist anymore!
		if (file.exists()) {
			dataTypeEntry = (DataTypeEntry) TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
			dataTypeEditable = dataTypeEntry.getTypeEditable();
			setPartName(dataTypeEntry.getFile().getName());
			return !(dataTypeEditable instanceof StructuredType);
		}
		return true; // import failed
	}

	private void setActionHandlers(final IEditorSite site) {
		final ActionRegistry registry = getActionRegistry();
		final IActionBars bars = site.getActionBars();
		String id = ActionFactory.UNDO.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = ActionFactory.REDO.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = ActionFactory.DELETE.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		bars.updateActionBars();
	}

	@Override
	public boolean isDirty() {
		return getCommandStack().isDirty();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	@Override
	public void createPartControl(final Composite parent) {
		if (dataTypeEditable instanceof final StructuredType structType && (!importFailed)) {
			structComposite = new StructEditingComposite(parent, commandStack, structType, annotationModel);
			getSite().setSelectionProvider(structComposite.getSelectionProvider());
			structComposite.setTitel(Messages.StructViewingComposite_Headline);
		} else if (importFailed) {
			createErrorComposite(parent, Messages.ErrorCompositeMessage);
			if (outsideWorkspace) {
				MessageDialog.openError(getSite().getShell().getShell(),
						Messages.MessageDialogTitle_OutsideWorkspaceError,
						Messages.MessageDialogContent_OutsideWorkspaceError);
			}
		}
	}

	private void createErrorComposite(final Composite parent, final String errorText) {
		errorComposite = new Composite(parent, SWT.NONE);
		errorComposite.setLayout(new GridLayout(1, false));
		final Label label = new Label(errorComposite, SWT.CENTER);
		label.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.HEADER_FONT));
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		label.setText(errorText);
	}

	@Override
	public void setFocus() {
		if (null == structComposite) {
			errorComposite.setFocus();
		} else {
			structComposite.setFocus();
		}
		adapter.checkFileReload();
	}

	public CommandStack getCommandStack() {
		return commandStack;
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		if (this.equals(getSite().getPage().getActiveEditor())) {
			updateActions(selectionActions);
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}

	private void createActions() {
		final ActionRegistry registry = getActionRegistry();
		IAction action;

		action = new UndoAction(this);
		registry.registerAction(action);
		getStackActions().add(action.getId());

		action = new RedoAction(this);
		registry.registerAction(action);
		getStackActions().add(action.getId());
	}

	@Override
	public <T> T getAdapter(final Class<T> key) {
		if (key == org.eclipse.ui.views.properties.IPropertySheetPage.class) {
			return key.cast(new TabbedPropertySheetPage(this));
		}
		if (key == CommandStack.class) {
			return key.cast(getCommandStack());
		}
		if (key == LibraryElement.class) {
			return key.cast(dataTypeEditable);
		}
		if (key == ActionRegistry.class) {
			return key.cast(getActionRegistry());
		}
		if (key == GraphicalAnnotationModel.class) {
			return key.cast(annotationModel);
		}

		return super.getAdapter(key);
	}

	private List<String> getStackActions() {
		return stackActions;
	}

	private void initializeActionRegistry() {
		createActions();
		updateActions(propertyActions);
		updateActions(stackActions);
	}

	private void updateActions(final List<String> actionIds) {
		final ActionRegistry registry = getActionRegistry();
		actionIds.forEach(id -> {
			final IAction action = registry.getAction(id);
			if (action instanceof final UpdateAction updateAction) {
				updateAction.update();
			}
		});
	}

	private ActionRegistry getActionRegistry() {
		if (null == actionRegistry) {
			actionRegistry = new ActionRegistry();
		}
		return actionRegistry;
	}

	@Override
	public void reloadType() {
		try {
			removeListenerFromDataTypeObj();
			dataTypeEntry.setTypeEditable(null);
			importType(getEditorInput());
			if (dataTypeEditable instanceof final StructuredType structType) {
				structComposite.setStructType(structType);
			}
			commandStack.flush();
			addListenerToDataTypeObj();
		} catch (final PartInitException e) {
			FordiacLogHelper
					.logError("Error during refreshing struct table after file change detection: " + e.toString(), e); //$NON-NLS-1$
		}

	}

	@Override
	public void setInput(final IEditorInput input) {
		setInputWithNotify(input);
	}
}
