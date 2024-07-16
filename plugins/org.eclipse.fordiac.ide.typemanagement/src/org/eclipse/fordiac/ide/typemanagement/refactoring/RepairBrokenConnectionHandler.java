package org.eclipse.fordiac.ide.typemanagement.refactoring;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class RepairBrokenConnectionHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection sel = HandlerUtil.getCurrentSelection(event);
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final CommandStack commandStack = editor.getAdapter(CommandStack.class);
		if (sel instanceof final IStructuredSelection ssel && ssel.getFirstElement() instanceof final EditPart part
				&& part.getModel() instanceof final ErrorMarkerInterface errormarker) {
			final DataTypeSelectionTreeContentProvider instance = DataTypeSelectionTreeContentProvider.INSTANCE;
			// instance.inputChanged(null, typeSelectionButton, e
			final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(
					HandlerUtil.getActiveShell(event), instance);
			dialog.setInput(errormarker.getFBNetworkElement().getTypeLibrary());
			if ((dialog.open() == Window.OK) && (dialog.getFirstResult() instanceof final TypeNode typeNode
					&& typeNode.getType() instanceof final StructuredType structType)) {
				final CompoundCommand repairCommands = new CompoundCommand();

				(errormarker.isIsInput() ? errormarker.getInputConnections() : errormarker.getOutputConnections())
						.forEach(con -> {
							System.out.println(structType);
							repairCommands.add(new RepairBrokenConnectionCommand(con, !errormarker.isIsInput(),
									structType, "DO1"));

						});
				commandStack.execute(repairCommands);
			}
		}
		return null;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		setBaseEnabled(isRepairable());
	}

	private boolean isRepairable() {
		final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			final IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
			return selection.size() == 1 && selection.getFirstElement() instanceof final EditPart part
					&& part.getModel() instanceof ErrorMarkerInterface;
		}
		return false;
	}
}
