package org.eclipse.fordiac.ide.refactor.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.application.actions.DeleteFBNetworkAction;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;

public class SampleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		System.out.println(HandlerUtil.getCurrentSelection(event));
		window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	    if (window != null)
	    {
	        IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
	        FB firstElement = ((FBEditPart) selection.getFirstElement()).getModel();
	        
	        DataConnectionCreateCommand con = new DataConnectionCreateCommand(firstElement.getFbNetwork());
	        con.setSource(firstElement.getInterface().getOutputVars().get(0));
	        con.setDestination(((FBEditPart)selection.toList().get(1)).getModel().getInterface().getInputVars().get(0));
	        con.execute();
	        
	        System.out.println(firstElement.getInterface().getOutputVars().get(0).getOutputConnections().get(0));
	        
	        /*
	        DeleteConnectionCommand dcon = new DeleteConnectionCommand(((FBEditPart) selection.getFirstElement()).getModel().getInterface().getOutputVars().get(0).getOutputConnections().get(0));
	        dcon.execute();
	        */
	        DeleteFBNetworkAction dfbna = new DeleteFBNetworkAction(null);
	        
	        ((FBEditPart) selection.getFirstElement()).getModel();
			System.out.println(selection);
	    }
	    /*
		MessageDialog.openInformation(
				window.getShell(),
				"Refactor",
				"Hello, Eclipse world");
		FBEditPart fbe = new FBEditPart();
		*/
		return null;
	}
	
	@Override
	public void setEnabled(final Object evaluationContext) {
		final IWorkbenchPart part = (IWorkbenchPart) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_EDITOR_NAME);
		setBaseEnabled(isSelectedFBElementEditPart());
		//setBaseEnabled((part != null) && (part.getAdapter(FBNetwork.class) != null));
		
	}
	
	private static boolean isSelectedFBElementEditPart() {
		//final ISelection selection = HandlerUtil.selection
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	    if (window != null)
	    {
	        IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
	        Object firstElement = selection.getFirstElement();
	        if (selection instanceof StructuredSelection) {
				final Object selObj = ((StructuredSelection) selection).getFirstElement();
				System.out.println(selObj.getClass());
				System.out.println(selObj instanceof FBEditPart);
				if (selObj instanceof FBEditPart) {
					return true;
				}
			}
	    }
	    /*
		
		*/
		return false;
	}
}
