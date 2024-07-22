package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.typemanagement.wizards.ConnectionsToStructWizard;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.core.refactoring.CheckConditionsOperation;
import org.eclipse.ltk.core.refactoring.PerformRefactoringOperation;
import org.eclipse.ui.handlers.HandlerUtil;

public class ConnectionsToStructHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		try {
			if (HandlerUtil.getCurrentSelection(event) instanceof final IStructuredSelection selection) {

				final List<Connection> connections = selection.stream().filter(EditPart.class::isInstance)
						.map(EditPart.class::cast)
						.map((Function<? super EditPart, ? extends Object>) EditPart::getModel)
						.filter(Connection.class::isInstance).map(Connection.class::cast).toList();
				final FBType sourceType = connections.get(0).getSourceElement().getType();
				final FBType destinationType = connections.get(0).getDestinationElement().getType();
				final Map<String, String> replacableConMap = new HashMap<>();
				connections.stream().forEach(
						con -> replacableConMap.put(con.getSource().getName(), con.getDestination().getName()));
				final DataType struct = sourceType.getTypeLibrary().getDataTypeLibrary().getType("structtest");

//				if (elementURI != null && !elementName.isEmpty()) {
				final ReplaceStructRefactoring refactoring = new ReplaceStructRefactoring();
				refactoring.setSourceType(sourceType);
				refactoring.setDestinationType(destinationType);
				refactoring.setNet(connections.getFirst().getFBNetwork());
				refactoring.setStructURI(EcoreUtil.getURI(struct));
				refactoring.setSourceVarName("OUT");
				refactoring.setDestinationVarName("IN");
				refactoring.setReplacableConMap(replacableConMap);
				refactoring.setConflictResolution(true);
				final ConnectionsToStructWizard wizard = new ConnectionsToStructWizard(selection, refactoring);
//				final RefactoringWizardOpenOperation openOperation = new RefactoringWizardOpenOperation(wizard);
//				openOperation.run(HandlerUtil.getActiveShell(event), refactoring.getName());
//				}
				final PerformRefactoringOperation op = new PerformRefactoringOperation(refactoring,
						CheckConditionsOperation.ALL_CONDITIONS);
				op.run(new NullProgressMonitor());
			}
		} catch (final Exception e) {
			FordiacLogHelper.logError("Error during refactoring", e); //$NON-NLS-1$
		}

//		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
//		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
//		final CommandStack commandStack = editor.getAdapter(CommandStack.class);
//
//		window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//
//		if (window != null) {
//			final IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
//
//			// Convert selection to list of Connections
//			final List<Connection> connections = selection.stream().filter(EditPart.class::isInstance)
//					.map(EditPart.class::cast).map((Function<? super EditPart, ? extends Object>) EditPart::getModel)
//					.filter(Connection.class::isInstance).map(Connection.class::cast).toList();
//			final FBType sourceType = connections.get(0).getSourceElement().getType();
//			final FBType destinationType = connections.get(0).getDestinationElement().getType();
//
//			// Generate a map from destination inputs to source outputs (this direction is
//			// unique)
//			final Map<String, String> replacableConMap = new HashMap<>();
//			connections.stream()
//					.forEach(con -> replacableConMap.put(con.getSource().getName(), con.getDestination().getName()));
//
////				try {
////					final ConnectionsToStructChange c2sChange = new ConnectionsToStructChange(
////							EcoreUtil.getURI(connections.getFirst().getSourceElement().getFbNetwork()), FBNetwork.class,
////							sourceType, destinationType, (StructuredType) wizard.getCreatedDataType(),
////							wizard.getSourceName(), wizard.getDestinationName(), replacableConMap,
////							wizard.getConflictResolution());
////					RefactoringCore.getUndoManager().aboutToPerformChange(c2sChange);
////					final Change undo = c2sChange.perform(new NullProgressMonitor());
////					RefactoringCore.getUndoManager().changePerformed(c2sChange, true);
////					RefactoringCore.getUndoManager().addUndo(RefactoringUIMessages.RenameResourceHandler_title, undo);
////				} catch (final CoreException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
//
//			// Execute Command
//			final DataType struct = sourceType.getTypeLibrary().getDataTypeLibrary().getType("structtest");
//			final ConnectionsToStructCommand cmd = new ConnectionsToStructCommand(sourceType, destinationType, struct,
//					"OUT", "IN", replacableConMap, true);
//			if (!cmd.isPossible() && !MessageDialog.openConfirm(Display.getDefault().getActiveShell(),
//					"FBType open in Editor", "Overwrite changes?")) {
//				return null;
//			}
//			commandStack.execute(cmd);
//		}

		return null;
	}

//	@Override
//	public void setEnabled(final Object evaluationContext) {
//		setBaseEnabled(isSelectedConnection());
//	}
//
//	private static boolean isSelectedConnection() {
//		// final ISelection selection = HandlerUtil.selection
//		final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//		if (window != null) {
//			final IInterfaceElement src;
//			final IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
//			final Optional opt = selection.stream()
//					.filter(sel -> !(sel instanceof final EditPart con && con.getModel() instanceof DataConnection))
//					.findAny();
//			if (opt.isEmpty()) {
//				// selection.stream().forEach(t ->
//				// ((ConnectionEditPart)t).getModel().setBrokenConnection(true));
//				return selection.stream().map(t -> ((Connection) ((EditPart) t).getModel()).getSourceElement())
//						.distinct().count() == 1
//						&& selection.stream().map(t -> ((Connection) ((EditPart) t).getModel()).getDestinationElement())
//								.distinct().count() == 1;
//			}
//		}
//		return false;
//	}
}
