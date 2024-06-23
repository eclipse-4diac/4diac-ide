package org.eclipse.fordiac.ide.refactor.handlers;

import java.util.ArrayList;
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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.wizards.ExtractStructTypeWizard;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectDataConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInternalVariableCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.StructDataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.WithCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInternalVariableCommand;
import org.eclipse.fordiac.ide.model.data.impl.StructuredTypeImpl;
import org.eclipse.fordiac.ide.model.edit.ITypeEntryEditor;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.search.dialog.FBTypeEntryDataHandler;
import org.eclipse.fordiac.ide.model.search.dialog.FBTypeUpdateDialog;
import org.eclipse.fordiac.ide.model.search.dialog.StructuredDataTypeDataHandler;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
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
			FBNetworkElement fbns = ((ConnectionEditPart) firstElement).getModel().getSourceElement();
			FBNetworkElement fbnd = ((ConnectionEditPart) firstElement).getModel().getDestinationElement();
			FBType les = ((ConnectionEditPart) firstElement).getModel().getSourceElement().getType();
			FBType led = ((ConnectionEditPart) firstElement).getModel().getDestinationElement().getType();
			

			CompoundCommand cc = new CompoundCommand();
			
			String name = "testname";
			selection.forEach(sel -> cc.add(new DeleteConnectionCommand(((ConnectionEditPart) sel).getModel())));
			sourceVarNames.forEach(var -> cc.add(new DeleteInterfaceCommand(les.getInterfaceList().getOutput(var))));
			destinationVarNames.forEach(var -> cc.add(new DeleteInterfaceCommand(led.getInterfaceList().getInput(var))));
			CreateInterfaceElementCommand sourceStruct = new CreateInterfaceElementCommand(les.getTypeLibrary().getDataTypeLibrary().getType(estw.getDatatypeName()), name, les.getInterfaceList(), false, (int)les.getInterfaceList().getOutputs().count());
			CreateInterfaceElementCommand destinationStruct = new CreateInterfaceElementCommand(led.getTypeLibrary().getDataTypeLibrary().getType(estw.getDatatypeName()), name, led.getInterfaceList(), true, (int)led.getInterfaceList().getInputs().count());
			cc.add(sourceStruct);
			cc.add(destinationStruct);
			commandStack.execute(cc);
			
			CompoundCommand cc2 = new CompoundCommand();
			
			varWiths.forEach(eve -> cc2.add(new WithCreateCommand(les.getInterfaceList().getEvent(eve),(VarDeclaration) sourceStruct.getCreatedElement())));
			varWithd.forEach(eve -> cc2.add(new WithCreateCommand(led.getInterfaceList().getEvent(eve),(VarDeclaration) destinationStruct.getCreatedElement())));
			
			commandStack.execute(cc2);
			
			
			final String[] labels = {"Save and Update", //Messages.FBTypeEditor_AlteringButton_SaveAndUpdate, // Messages.StructAlteringButton_SaveAs,
						SWT.getMessage("SWT_Cancel") }; //$NON-NLS-1$
			//FBTypeEditor.Messages
			FBTypeUpdateDialog<TypeEntry> fbSaveDialog = new FBTypeUpdateDialog<TypeEntry>(null, "Function Block Editor", null, "", //$NON-NLS-1$
					MessageDialog.NONE, labels, 0, new FBTypeEntryDataHandler(les.getTypeEntry()));

			switch (fbSaveDialog.open()) {
			case 0:
				try {
					CompoundCommand cc3 = new CompoundCommand();
					les.getTypeEntry().save(les);
					UpdateFBTypeCommand sourceUpdate = new UpdateFBTypeCommand(fbns);
					cc3.add(sourceUpdate);
					led.getTypeEntry().save(led);
					UpdateFBTypeCommand destinationUpdate = new UpdateFBTypeCommand(fbnd);
					cc3.add(destinationUpdate);
					commandStack.execute(cc3);
					fbns = sourceUpdate.getNewElement();
					fbnd = destinationUpdate.getNewElement();
					
//
					StructDataConnectionCreateCommand sdccc = new StructDataConnectionCreateCommand(fbn);
					sdccc.setSource(fbns.getInterface().getOutput(sourceStruct.getCreatedElement().getName()));
					sdccc.setDestination(fbnd.getInterface().getInput(destinationStruct.getCreatedElement().getName()));
					
					commandStack.execute(sdccc);
					
					/*
					 * Display.getDefault().asyncExec(() -> {
					 * 
					 * final WizardDialog dialog = new WizardDialog(editor.getSite().getShell(),
					 * estw); dialog.create(); dialog.open(); });
					 */

					

					BlockTypeInstanceSearch btis = new BlockTypeInstanceSearch(les.getTypeEntry());
//					btis.performSearch().stream().forEach(t -> ((FBNetworkElement) t).setComment("hello"));
//					final String[] labels = { "Save", "Cancel" };
//					FBTypeUpdateDialog<TypeEntry> fbtud1 = new FBTypeUpdateDialog<TypeEntry>(null, "STRUCT Editor", null, "", 0,
//							labels, 0, new FBTypeEntryDataHandler(led.getTypeEntry()));
//					if (fbtud1.open() == 0) {
//						System.out.println("Hello");
//						
//					}
					

					CompoundCommand reconnectupdatecompcmd = new CompoundCommand();
					CompoundCommand connectcompcd = new CompoundCommand();
					new ArrayList<EObject>(btis.performSearch()).stream()
					.filter(t -> (!t.equals(sourceUpdate.getNewElement()) && !t.equals(destinationUpdate.getNewElement()) && t instanceof FBNetworkElement))
					.map(t -> (FBNetworkElement) t)
					.forEach(t -> {
						FBCreateCommand fbcc1 = new FBCreateCommand(t.getTypeLibrary().getFBTypeEntry("STRUCT_DEMUX"), t.getFbNetwork(), (int)(t.getPosition().getX()+t.getWidth()), (int)t.getPosition().getY());
						commandStack.execute(fbcc1);
						ChangeStructCommand csc = new ChangeStructCommand((StructManipulator)fbcc1.getElement(), t.getTypeLibrary().getDataTypeLibrary().getType("structtest"));
						commandStack.execute(csc);
						
//						for (int i = 0; i < sourceVarNames.size(); i++) {
//							EList<Connection> e = t.getOutput(sourceVarNames.get(i)).getOutputConnections();
//							if (e != null && !e.isEmpty()) {
//								for (int j = 0; j < e.size(); j++) {
//									ReconnectDataConnectionCommand rdcc = new ReconnectDataConnectionCommand(e.get(j), true, csc.getNewElement().getOutput(sourceVarNames.get(i)), fbn);
//									commandStack.execute(rdcc);
//								}
//							}
//						}
						
						sourceVarNames.stream().flatMap(outputname -> t.getOutput(outputname).getOutputConnections().stream()).toList().stream().forEach(con -> {
							ReconnectDataConnectionCommand rdcc = new ReconnectDataConnectionCommand(con, true, csc.getNewElement().getOutput(con.getSource().getName()), fbn);
							commandStack.execute(rdcc);//reconnectupdatecompcmd.add(rdcc);
//							commandStack.execute(rdcc);
//							DeleteConnectionCommand dcc = new DeleteConnectionCommand(con);
//							commandStack.execute(dcc);
							System.out.println(con);
						});
						
						UpdateFBTypeCommand updatefb = new UpdateFBTypeCommand(t);
						commandStack.execute(updatefb);//reconnectupdatecompcmd.add(updatefb);
						StructDataConnectionCreateCommand muxcon = new StructDataConnectionCreateCommand(fbn);
						muxcon.setSource(updatefb.getNewElement().getInterface().getOutput(sourceStruct.getCreatedElement().getName()));
						muxcon.setDestination(csc.getNewElement().getInterface().getInput("IN"));
						commandStack.execute(muxcon);//connectcompcd.add(muxcon);
//						sourceVarNames.forEach(x -> {
//							EList<Connection> e = t.getOutput(x).getOutputConnections();
//							e.stream().findAny().ifPresent(z -> System.out.println(z));
//							if (e != null && e.size() > 0) {
//								e.forEach(y -> {
//									ReconnectDataConnectionCommand rdcc = new ReconnectDataConnectionCommand(y, true, csc.getNewElement().getOutput(x), fbn);
//									commandStack.execute(rdcc);
//								});
//							}
//						});
					});
//					((FBNetworkElement)btis.performSearch().get(2)).getOutput("DO1").getOutputConnections().get(0);
					//commandStack.execute(reconnectupdatecompcmd);
					//commandStack.execute(connectcompcd);
//
					
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 1:
				MessageDialog.openInformation(null, "Function Block Editor",
						"The changes have not been saved!");
				break;
			default:
				break;
			}
			
			
			
			
//			UpdateFBTypeCommand ufbtc = new UpdateFBTypeCommand(((ConnectionEditPart) firstElement).getModel().getSourceElement());
//			commandStack.execute(ufbtc);
			
			//InterfaceList ils = ((ConnectionEditPart) firstElement).getModel().getSourceElement().getInterface();
			//InterfaceList ild = ((ConnectionEditPart) firstElement).getModel().getDestinationElement().getInterface();

			
			
			

//			FBCreateCommand fbcc = new FBCreateCommand((FBTypeEntry)fbns.getTypeEntry(), fbns.getFbNetwork(), 0, 0);
//			commandStack.execute(fbcc);
			
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
			Optional opt = selection.stream().filter(sel -> !(sel instanceof ConnectionEditPart con && con.getModel() instanceof DataConnection)).findAny();
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
