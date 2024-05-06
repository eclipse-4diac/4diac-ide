package org.eclipse.fordiac.ide.refactor.handlers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.wizards.ExtractStructTypeWizard;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

public class Connections2StructHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		System.out.println(HandlerUtil.getCurrentSelection(event));
		window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	    if (window != null)
	    {
	        IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
	        Object firstElement = selection.getFirstElement();
			System.out.println(selection);
			
		    List<VarDeclaration> varDecls = selection.stream().map(t -> (VarDeclaration)((ConnectionEditPart)t).getModel().getSource()).collect(Collectors.toList());

		    ExtractStructTypeWizard estw = new ExtractStructTypeWizard(varDecls, getProject(((ConnectionEditPart)firstElement).getModel().getSourceElement()), Messages.ConvertToStructHandler_Title);
			
			Display.getDefault().asyncExec(() -> {
				final WizardDialog dialog = new WizardDialog(editor.getSite().getShell(), estw);
				dialog.create();
				dialog.open();
			});
			
			//getcommandstex execute
			
			selection.forEach(t -> {
				DeleteConnectionCommand dcc = new DeleteConnectionCommand(((ConnectionEditPart) t).getModel());
				dcc.execute();
			});
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
		//setBaseEnabled((part != null) && (part.getAdapter(FBNetwork.class) != null));
		
	}
	
	private static boolean isSelectedConnection() {
		//final ISelection selection = HandlerUtil.selection
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	    if (window != null)
	    {
	    	IInterfaceElement src;
	        IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
	        Optional opt = selection.stream().filter(sel ->	!(sel instanceof ConnectionEditPart)).findAny();
	        if (opt.isEmpty()) {
	        	//selection.stream().forEach(t -> ((ConnectionEditPart)t).getModel().setBrokenConnection(true));
	        	selection.stream().forEach(t -> System.out.println(((ConnectionEditPart)t).getModel().getSource().getClass()));
	        	return selection.stream().map(t -> ((ConnectionEditPart)t).getModel().getSourceElement()).distinct().count() == 1 
	        			&& selection.stream().map(t -> ((ConnectionEditPart)t).getModel().getDestinationElement()).distinct().count() == 1;
	        }
	    }
		return false;
	}
}
