/*******************************************************************************
 * Copyright (c) 2020, 2023 Johannes Kepler University, Linz,
 *                          Primetals Technologies Austria GmbH
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
 *   Sebastian Hollersbacher
 *     - Changed from DataTypeEditor to AttributeTypeEditor
 *******************************************************************************/

package org.eclipse.fordiac.ide.attributetypeeditor.editors;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.attributetypeeditor.Messages;
import org.eclipse.fordiac.ide.attributetypeeditor.widgets.DirectlyDerivedTypeComposite;
import org.eclipse.fordiac.ide.datatypeeditor.widgets.StructEditingComposite;
import org.eclipse.fordiac.ide.gef.annotation.FordiacMarkerGraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.model.commands.change.ChangeAttributeDeclarationTypeCommand;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.editors.ITypeEntryEditor;
import org.eclipse.fordiac.ide.model.ui.editors.TypeEntryAdapter;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.RedoAction;
import org.eclipse.gef.ui.actions.UndoAction;
import org.eclipse.gef.ui.actions.UpdateAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
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

public class AttributeTypeEditor extends EditorPart implements CommandStackEventListener,
		ITabbedPropertySheetPageContributor, ISelectionListener, ITypeEntryEditor {
	private static final int STRUCT_EDITOR_INDEX = 0;
	private static final int DIRECTLYDERIVEDTYPE_EDITOR_INDEX = 1;

	private final CommandStack commandStack = new CommandStack();
	private GraphicalAnnotationModel annotationModel;
	private StructEditingComposite structComposite;
	private DirectlyDerivedTypeComposite directlyDerivedTypeComposite;
	private Composite errorComposite;
	private Combo comboBox;
	private boolean importFailed;
	private boolean outsideWorkspace;

	private ActionRegistry actionRegistry;
	private final List<String> selectionActions = new ArrayList<>();
	private final List<String> stackActions = new ArrayList<>();
	private final List<String> propertyActions = new ArrayList<>();

	private AttributeTypeEntry attributeTypeEntry;
	private AttributeDeclaration attributeDeclaration;

	private final TypeEntryAdapter typeEntryAdapter = new TypeEntryAdapter(this);
	private final Adapter adapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if (notification.getOldValue() instanceof AnyDerivedType
					&& notification.getNewValue() instanceof AnyDerivedType) {
				if (notification.getNewValue() instanceof StructuredType) {
					comboBox.select(STRUCT_EDITOR_INDEX);
				} else {
					comboBox.select(DIRECTLYDERIVEDTYPE_EDITOR_INDEX);
				}
				changeEditingComposite();
			}
		}
	};

	@Override
	public void stackChanged(final CommandStackEvent event) {
		updateActions(stackActions);
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@Override
	public String getContributorId() {
		return "org.eclipse.fordiac.ide.attributetypeeditor.editors.AttributeTypeEditor"; //$NON-NLS-1$
	}

	@Override
	public void dispose() {
		// get these values here before calling super dispose
		final boolean dirty = isDirty();

		getCommandStack().removeCommandStackEventListener(this);
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);
		getActionRegistry().dispose();
		removeListenerFromAttributeDeclaration();
		if (annotationModel != null) {
			annotationModel.dispose();
		}
		super.dispose();
		if (dirty && attributeTypeEntry != null) {
			// purge editable type from type entry after super.dispose() so that no
			// notifiers will be called
			attributeTypeEntry.setTypeEditable(null);
		}
	}

	@Override
	protected void firePropertyChange(final int property) {
		super.firePropertyChange(property);
		updateActions(propertyActions);
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		removeListenerFromAttributeDeclaration();
		loadAllOpenEditors();
		doSaveInternal(monitor);
	}

	private void doSaveInternal(final IProgressMonitor monitor) {
		commandStack.markSaveLocation();
		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation(
				attributeTypeEntry.getFile().getParent()) {

			@Override
			protected void execute(final IProgressMonitor monitor)
					throws CoreException, InvocationTargetException, InterruptedException {
				attributeTypeEntry.save(attributeDeclaration, monitor);
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
		addListenerToAttributeDeclaration();
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@Override
	public void doSaveAs() {
		// nothing here yet
	}

	private static void loadAllOpenEditors() {
		final IEditorReference[] openEditors = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		for (final IEditorReference iEditorReference : openEditors) {
			iEditorReference.getEditor(true);
		}
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		importType(input);
		setInput(input);
		setSite(site);
		addListenerToAttributeDeclaration();
		site.getWorkbenchWindow().getSelectionService().addSelectionListener(this);
		getCommandStack().addCommandStackEventListener(this);
		initializeActionRegistry();
		setActionHandlers(site);
		if (input instanceof final IFileEditorInput fileEditorInput) {
			annotationModel = new FordiacMarkerGraphicalAnnotationModel(fileEditorInput.getFile());
		}
	}

	@Override
	public String getTitleToolTip() {
		final String tooltip = (attributeTypeEntry != null) ? attributeTypeEntry.getFullTypeName() + "\n" : ""; //$NON-NLS-1$ //$NON-NLS-2$
		return tooltip + super.getTitleToolTip();
	}

	private void addListenerToAttributeDeclaration() {
		if (attributeDeclaration != null) {
			attributeDeclaration.eAdapters().add(adapter);
		}
		if (attributeTypeEntry != null) {
			attributeTypeEntry.eAdapters().add(typeEntryAdapter);
		}
	}

	private void removeListenerFromAttributeDeclaration() {
		if (attributeDeclaration != null && attributeDeclaration.eAdapters().contains(adapter)) {
			attributeDeclaration.eAdapters().remove(adapter);
		}
		if (attributeTypeEntry != null && attributeTypeEntry.eAdapters().contains(typeEntryAdapter)) {
			attributeTypeEntry.eAdapters().remove(typeEntryAdapter);
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
			attributeTypeEntry = (AttributeTypeEntry) TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
			attributeDeclaration = attributeTypeEntry.getTypeEditable();
			setPartName(attributeTypeEntry.getFile().getName());
			return attributeDeclaration == null;
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
		return false;
	}

	@Override
	public void createPartControl(final Composite parent) {
		if (importFailed) {
			createErrorComposite(parent, Messages.ErrorCompositeMessage);
			if (outsideWorkspace) {
				MessageDialog.openError(getSite().getShell().getShell(),
						Messages.MessageDialogTitle_OutsideWorkspaceError,
						Messages.MessageDialogContent_OutsideWorkspaceError);
			}
			return;
		}

		final Composite editorComposite = new Composite(parent, SWT.NONE);
		editorComposite.setLayout(new GridLayout(1, false));
		comboBox = new Combo(editorComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboBox.add(Messages.Editor_ComboBox_StructEditingComposite, STRUCT_EDITOR_INDEX);
		comboBox.add(Messages.Editor_ComboBox_DirectlyDerivedTypeComposite, DIRECTLYDERIVEDTYPE_EDITOR_INDEX);
		comboBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final AnyDerivedType type = attributeDeclaration.getType();
				if (comboBox.getSelectionIndex() == STRUCT_EDITOR_INDEX && type instanceof DirectlyDerivedType
						|| comboBox.getSelectionIndex() == DIRECTLYDERIVEDTYPE_EDITOR_INDEX
								&& type instanceof StructuredType) {
					commandStack.execute(new ChangeAttributeDeclarationTypeCommand(attributeDeclaration));
				}
			}
		});

		final AnyDerivedType type = attributeDeclaration.getType();
		if (type instanceof final StructuredType structType) {
			structComposite = new StructEditingComposite(editorComposite, commandStack, structType, annotationModel);
			getSite().setSelectionProvider(structComposite.getSelectionProvider());
			structComposite.setTitel(Messages.StructViewingComposite_Headline);
			comboBox.select(STRUCT_EDITOR_INDEX);
		} else if (type instanceof final DirectlyDerivedType directlyDerivedType) {
			directlyDerivedTypeComposite = new DirectlyDerivedTypeComposite(editorComposite, directlyDerivedType,
					commandStack);
			getSite().setSelectionProvider(directlyDerivedTypeComposite);
			comboBox.select(DIRECTLYDERIVEDTYPE_EDITOR_INDEX);
		}
	}

	private void changeEditingComposite() {
		final AnyDerivedType type = attributeDeclaration.getType();
		if (structComposite != null && type instanceof final DirectlyDerivedType directlyDerivedType) {
			directlyDerivedTypeComposite = new DirectlyDerivedTypeComposite(structComposite.getParent(),
					directlyDerivedType, commandStack);
			getSite().setSelectionProvider(directlyDerivedTypeComposite);

			if (!structComposite.isDisposed()) {
				structComposite.dispose();
			}
			structComposite = null;
			directlyDerivedTypeComposite.requestLayout();
		} else if (directlyDerivedTypeComposite != null && type instanceof final StructuredType structType) {
			structComposite = new StructEditingComposite(directlyDerivedTypeComposite.getParent(), commandStack,
					structType, annotationModel);
			getSite().setSelectionProvider(structComposite.getSelectionProvider());
			structComposite.setTitel(Messages.StructViewingComposite_Headline);

			if (!directlyDerivedTypeComposite.isDisposed()) {
				directlyDerivedTypeComposite.dispose();
			}
			directlyDerivedTypeComposite = null;
			structComposite.requestLayout();
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
		if (structComposite != null) {
			structComposite.setFocus();
		} else if (directlyDerivedTypeComposite != null) {
			directlyDerivedTypeComposite.setFocus();
		} else if (errorComposite != null) {
			errorComposite.setFocus();
		}
		// we got focus check if the content needs reload
		typeEntryAdapter.checkFileReload();
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
			return key.cast(attributeDeclaration);
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
			removeListenerFromAttributeDeclaration();
			attributeTypeEntry.setTypeEditable(null);
			importType(getEditorInput());
			final AnyDerivedType type = attributeDeclaration.getType();
			if (type instanceof final StructuredType structType) {
				structComposite.setStructType(structType);
			}
			if (type instanceof final DirectlyDerivedType directlyDerivedType) {
				directlyDerivedTypeComposite.setDirectlyDerivedType(directlyDerivedType);
			}
			commandStack.flush();
			addListenerToAttributeDeclaration();
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
