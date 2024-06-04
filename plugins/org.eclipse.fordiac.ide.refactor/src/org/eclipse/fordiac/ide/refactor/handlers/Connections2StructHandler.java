package org.eclipse.fordiac.ide.refactor.handlers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.wizards.ExtractStructTypeWizard;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInternalVariableCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.StructDataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.WithCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInternalVariableCommand;
import org.eclipse.fordiac.ide.model.data.impl.StructuredTypeImpl;
import org.eclipse.fordiac.ide.model.edit.ITypeEntryEditor;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.search.dialog.FBTypeEntryDataHandler;
import org.eclipse.fordiac.ide.model.search.dialog.FBTypeUpdateDialog;
import org.eclipse.fordiac.ide.model.search.dialog.StructuredDataTypeDataHandler;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class Connections2StructHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final CommandStack commandStack = editor.getAdapter(CommandStack.class);

		System.out.println(HandlerUtil.getCurrentSelection(event));
		window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		// final CommandStack commandStack = editor.getAdapter(CommandStack.class);
		// final FBNetworkElement fb = getNetworkElementFromSelectedPins(sel);

		if (window != null) {
			IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
			Object firstElement = selection.getFirstElement();
			FBNetwork fbn = ((ConnectionEditPart) firstElement).getModel().getFBNetwork();
			FBNetworkElement sourceElement = ((ConnectionEditPart) firstElement).getModel().getSourceElement();
			FBNetworkElement destinationElement = ((ConnectionEditPart) firstElement).getModel().getDestinationElement();
			InterfaceList sourceInterface = ((ConnectionEditPart) firstElement).getModel().getSourceElement().getInterface();
			InterfaceList destenationInterface = ((ConnectionEditPart) firstElement).getModel().getDestinationElement().getInterface();
			System.out.println(selection);
			
			
			
			List<String> sourceVarNames = selection.stream().map(t -> ((ConnectionEditPart) t).getModel().getSource().getName()).toList();

			List<String> destinationVarNames = selection.stream().map(t -> ((ConnectionEditPart) t).getModel().getDestination().getName()).toList();
			
			
			

			List<VarDeclaration> varDecls = selection.stream()
					.map(t -> (VarDeclaration) ((ConnectionEditPart) t).getModel().getSource()).toList();
			List<String> varWiths = varDecls.stream().map(t -> t.getWiths()).flatMap(List::stream).map(t -> ((Event)t.eContainer()).getName()).distinct().toList();

			List<VarDeclaration> varDecld = selection.stream()
					.map(t -> (VarDeclaration) ((ConnectionEditPart) t).getModel().getDestination()).toList();
			List<String> varWithd = varDecld.stream().map(t -> t.getWiths()).flatMap(List::stream).map(t -> ((Event)t.eContainer()).getName()).distinct().toList();

			ExtractStructTypeWizard estw = new ExtractStructTypeWizard(varDecls,
					getProject(((ConnectionEditPart) firstElement).getModel().getSourceElement()),
					Messages.ConvertToStructHandler_Title);
			final WizardDialog dialog = new WizardDialog(editor.getSite().getShell(), estw);
			dialog.create();
			dialog.open();
			estw.getDatatypeName();
			FBType les = ((ConnectionEditPart) firstElement).getModel().getSourceElement().getType();
			LibraryElement led = ((ConnectionEditPart) firstElement).getModel().getDestinationElement().getType();
			
//			DeleteInterfaceCommand dic = new DeleteInterfaceCommand(les.getInterfaceList().getOutput(sourceVarNames.get(0)));
//			commandStack.execute(dic);
//			try {
//				les.getTypeEntry().save(les);
//			} catch (CoreException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			UpdateFBTypeCommand ufbtc = new UpdateFBTypeCommand(((ConnectionEditPart) firstElement).getModel().getSourceElement());
//			commandStack.execute(ufbtc);
			
			InterfaceList ils = ((ConnectionEditPart) firstElement).getModel().getSourceElement().getInterface();
			InterfaceList ild = ((ConnectionEditPart) firstElement).getModel().getDestinationElement().getInterface();

			/*
			 * Display.getDefault().asyncExec(() -> {
			 * 
			 * final WizardDialog dialog = new WizardDialog(editor.getSite().getShell(),
			 * estw); dialog.create(); dialog.open(); });
			 */
			// Bloctypeinstancesearch
			// Typeentry
			// FBtypeUPdatedialog

			CompoundCommand cc = new CompoundCommand();
			
			selection.forEach(t -> {
				DeleteConnectionCommand dcc = new DeleteConnectionCommand(((ConnectionEditPart) t).getModel());
				// DeleteInternalVariableCommand sdivc = new
				// DeleteInternalVariableCommand((BaseFBType)((ConnectionEditPart)
				// t).getModel().getSourceElement().getType(),
				// (VarDeclaration)((ConnectionEditPart) t).getModel().getSource());
				// DeleteInternalVariableCommand ddivc = new
				// DeleteInternalVariableCommand((BaseFBType)((ConnectionEditPart)
				// t).getModel().getDestinationElement().getType(),
				// (VarDeclaration)((ConnectionEditPart) t).getModel().getDestination());
				//DeleteInterfaceCommand dic1 = new DeleteInterfaceCommand(
				//		((ConnectionEditPart) t).getModel().getSource());
				//DeleteInterfaceCommand dic2 = new DeleteInterfaceCommand(
				//		((ConnectionEditPart) t).getModel().getDestination());
				cc.add(dcc);
				//cc.add(dic1);
				//cc.add(dic2);
				// cc.add(sdivc);
				// cc.add(ddivc);
				// System.out.println(((ConnectionEditPart)
				// t).getModel().getSourceElement().getType() instanceof BaseFBType);

			});
			commandStack.execute(cc);
			String sourceName = null, destinationName = null;
			String name = JOptionPane.showInputDialog("IO Name");
			final IWorkbench workbench = PlatformUI.getWorkbench();
			if (null != workbench) {
				final IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
				if (null != activeWorkbenchWindow) {
					final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
					final IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
							.getDefaultEditor(les.getTypeEntry().getFile().getName());
					try {
						final ITypeEntryEditor fbeditor = (ITypeEntryEditor)activePage.openEditor(new FileEditorInput(les.getTypeEntry().getFile()), desc.getId());
						final CommandStack fbcommandStack = fbeditor.getAdapter(CommandStack.class);
						InterfaceList sourceInterfaceList = ((FBType)fbeditor.getEditedElement()).getInterfaceList();
						CompoundCommand ccs = new CompoundCommand();
						sourceVarNames.forEach(varName -> ccs.add(new DeleteInterfaceCommand(sourceInterfaceList.getOutput(varName))));
						CreateInterfaceElementCommand createStructInterfaceCommand = new CreateInterfaceElementCommand(((FBType)fbeditor.getEditedElement()).getTypeLibrary().getDataTypeLibrary().getType(estw.getDatatypeName()), name, sourceInterfaceList, false, (int)sourceInterfaceList.getOutputs().count());
						ccs.add(createStructInterfaceCommand);
						fbcommandStack.execute(ccs);
						sourceName = createStructInterfaceCommand.getCreatedElement().getName();
						CompoundCommand ccdw = new CompoundCommand();
						varWiths.forEach(ev -> ccdw.add(new WithCreateCommand(sourceInterfaceList.getEventOutputs().stream().filter(t -> t.getName().equals(ev)).findFirst().get(), (VarDeclaration) createStructInterfaceCommand.getCreatedElement())));
						fbcommandStack.execute(ccdw);
						fbeditor.doSave(new NullProgressMonitor());
					} catch (final Exception e) {
						FordiacLogHelper.logError(e.getMessage(), e);
					}
				}
			}
			final IWorkbench workbench1 = PlatformUI.getWorkbench();
			if (null != workbench) {
				final IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
				if (null != activeWorkbenchWindow) {
					final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
					final IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
							.getDefaultEditor(led.getTypeEntry().getFile().getName());
					
					try {
						final ITypeEntryEditor fbeditor = (ITypeEntryEditor)activePage.openEditor(new FileEditorInput(led.getTypeEntry().getFile()), desc.getId());
						final CommandStack fbcommandStack = fbeditor.getAdapter(CommandStack.class);
						InterfaceList destinationInterfaceList = ((FBType)fbeditor.getEditedElement()).getInterfaceList();
						CompoundCommand ccd = new CompoundCommand();
						destinationVarNames.forEach(varName -> ccd.add(new DeleteInterfaceCommand(destinationInterfaceList.getInput(varName))));
						CreateInterfaceElementCommand createStructInterfaceCommand = new CreateInterfaceElementCommand(((FBType)fbeditor.getEditedElement()).getTypeLibrary().getDataTypeLibrary().getType(estw.getDatatypeName()), name, destinationInterfaceList, true, (int)destinationInterfaceList.getInputs().count());
						ccd.add(createStructInterfaceCommand);
						fbcommandStack.execute(ccd);
						destinationName = createStructInterfaceCommand.getCreatedElement().getName();
						CompoundCommand ccdw = new CompoundCommand();
						varWithd.forEach(ev -> ccdw.add(new WithCreateCommand(destinationInterfaceList.getEventInputs().stream().filter(t -> t.getName().equals(ev)).findFirst().get(), (VarDeclaration) createStructInterfaceCommand.getCreatedElement())));
						fbcommandStack.execute(ccdw);
						fbeditor.doSave(new NullProgressMonitor());
					} catch (final Exception e) {
						FordiacLogHelper.logError(e.getMessage(), e);
					}
				}
			}
			
			
			CompoundCommand cc2 = new CompoundCommand();
//			
//			varWiths.forEach(t -> cc2.add(new WithCreateCommand(t,(VarDeclaration) ciecs.getCreatedElement())));
//			varWithd.forEach(t -> cc2.add(new WithCreateCommand(t,(VarDeclaration) ciecd.getCreatedElement())));
			StructDataConnectionCreateCommand sdccc = new StructDataConnectionCreateCommand(fbn);
			sdccc.setSource(sourceElement.getInterface().getOutput(sourceName));
			sdccc.setDestination(destinationElement.getInterface().getInput(destinationName));
//			commandStack.execute(cc2);
			commandStack.execute(sdccc);
//			try {
//				les.getTypeEntry().save(les);
//			} catch (CoreException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			BlockTypeInstanceSearch btis = new BlockTypeInstanceSearch(les.getTypeEntry());
//			btis.performSearch().stream().forEach(t -> ((FBNetworkElement) t).setComment("hello"));
//			final String[] labels = { "Save", "Cancel" };
//			FBTypeUpdateDialog<TypeEntry> fbtud1 = new FBTypeUpdateDialog<TypeEntry>(null, "STRUCT Editor", null, "", 0,
//					labels, 0, new FBTypeEntryDataHandler(led.getTypeEntry()));
//			if (fbtud1.open() == 0) {
//				System.out.println("Hello");
//				
//			}
		}
		return null;
	}

	private static IProject getProject(final FBNetworkElement fb) {
		final EObject root = EcoreUtil.getRootContainer(fb);
		if (root instanceof final LibraryElement libEl) {
			return libEl.getTypeLibrary().getProject();
		}
		return null;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final IWorkbenchPart part = (IWorkbenchPart) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_EDITOR_NAME);
		setBaseEnabled(isSelectedConnection());
		// setBaseEnabled((part != null) && (part.getAdapter(FBNetwork.class) != null));

	}

	private static boolean isSelectedConnection() {
		// final ISelection selection = HandlerUtil.selection
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			IInterfaceElement src;
			IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
			Optional opt = selection.stream().filter(sel -> !(sel instanceof ConnectionEditPart)).findAny();
			if (opt.isEmpty()) {
				// selection.stream().forEach(t ->
				// ((ConnectionEditPart)t).getModel().setBrokenConnection(true));
				selection.stream()
						.forEach(t -> System.out.println(((ConnectionEditPart) t).getModel().getSource().getClass()));
				return selection.stream().map(t -> ((ConnectionEditPart) t).getModel().getSourceElement()).distinct()
						.count() == 1
						&& selection.stream().map(t -> ((ConnectionEditPart) t).getModel().getDestinationElement())
								.distinct().count() == 1;
			}
		}
		return false;
	}
}
