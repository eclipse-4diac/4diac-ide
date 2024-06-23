package org.eclipse.fordiac.ide.refactor.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.application.actions.DeleteFBNetworkAction;
import org.eclipse.fordiac.ide.application.editparts.AbstractStructManipulatorEditPart;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.DemultiplexerEditPart;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.editparts.MultiplexerEditPart;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectDataConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.StructDataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.impl.DemultiplexerImpl;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IEditorPart;
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
	        Demultiplexer o = ((DemultiplexerEditPart) selection.getFirstElement()).getModel();

			final IEditorPart editor = HandlerUtil.getActiveEditor(event);
			final CommandStack commandStack = editor.getAdapter(CommandStack.class);
			
			FBCreateCommand fbcc = new FBCreateCommand(o.getTypeLibrary().getFBTypeEntry("STRUCT_MUX"), o.getFbNetwork(), ((DemultiplexerEditPart) selection.getFirstElement()).getFigure().getFBBounds().getRight().x()+((DemultiplexerEditPart) selection.getFirstElement()).getFigure().getFBBounds().width(), ((DemultiplexerEditPart) selection.getFirstElement()).getFigure().getFBBounds().y());
			commandStack.execute(fbcc);
			
			FBCreateCommand fbcc1 = new FBCreateCommand(o.getTypeLibrary().getFBTypeEntry("STRUCT_DEMUX"), o.getFbNetwork(), 10, 0);
			commandStack.execute(fbcc1);
			ChangeStructCommand csc = new ChangeStructCommand(o, o.getTypeLibrary().getDataTypeLibrary().getType("structtest"));
			//o.setDataType(o.getTypeLibrary().getDataTypeLibrary().getType("structtest"));
			commandStack.execute(csc);		
			FBNetworkElement on = csc.getNewElement();
			StructManipulator on1 = csc.getNewMux();

			
			
			DemultiplexerEditPart x = ((DemultiplexerEditPart) selection.getFirstElement());
			DemultiplexerImpl y;
			

			BlockTypeInstanceSearch btis = new BlockTypeInstanceSearch(on.getTypeLibrary().getFbTypes().get("fbtest"));
			Connection c = ((FBNetworkElement)btis.performSearch().get(2)).getOutput("DO1").getOutputConnections().get(0);
			ReconnectDataConnectionCommand rdcc = new ReconnectDataConnectionCommand(c, true, on.getInterface().getOutput("DO1"), fbcc.getFBNetwork());
			commandStack.execute(rdcc);
			
//			DeleteConnectionCommand dcc = new DeleteConnectionCommand(c);
//			DataConnectionCreateCommand dccc = new DataConnectionCreateCommand(fbcc.getFBNetwork());
//			dccc.setSource(o.getInterface().getOutput("DO1"));
//			dccc.setDestination(c.getDestination());
//			commandStack.execute(dcc);
//			commandStack.execute(dccc);
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
				if (selObj instanceof AbstractStructManipulatorEditPart) {
					return true;
				}
			}
	    }
	    /*
		
		*/
		return false;
	}
}
