package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.typemanagement.wizards.ConnectionsToStructWizard;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class ConnectionsToStructHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final CommandStack commandStack = editor.getAdapter(CommandStack.class);

		System.out.println(HandlerUtil.getCurrentSelection(event));
		window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		if (window != null) {
			final IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();

			// Convert selection to list of Connections
			final List<Connection> connections = selection.stream().filter(EditPart.class::isInstance)
					.map(EditPart.class::cast).map((Function<? super EditPart, ? extends Object>) EditPart::getModel)
					.filter(Connection.class::isInstance).map(Connection.class::cast).toList();
			final FBType sourceType = connections.get(0).getSourceElement().getType();
			final FBType destinationType = connections.get(0).getDestinationElement().getType();

			final ConnectionsToStructWizard wizard = new ConnectionsToStructWizard(selection);
			final WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().getActiveEditor().getSite().getShell(), wizard);
			dialog.create();

			if (dialog.open() == Window.OK) {
				// Generate a map from destination inputs to source outputs (this direction is
				// unique)
				final Map<String, String> replacableConMap = new HashMap<>();
				connections.stream().forEach(
						con -> replacableConMap.put(con.getSource().getName(), con.getDestination().getName()));

				// Execute Command
				final ConnectionsToStructCommand cmd = new ConnectionsToStructCommand(sourceType, destinationType,
						wizard.getCreatedDataType(), wizard.getSourceName(), wizard.getDestinationName(),
						replacableConMap, wizard.getConflictResolution());
				if (!cmd.isPossible() && !MessageDialog.openConfirm(Display.getDefault().getActiveShell(),
						"FBType open in Editor", "Overwrite changes?")) {
					return null;
				}
				commandStack.execute(cmd);
			}
		}
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
