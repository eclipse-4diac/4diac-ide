package org.eclipse.fordiac.ide.typemanagement.refactoring;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.impl.FBImpl;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.handlers.HandlerUtil;

public class RepairBrokenConnectionHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel instanceof final IStructuredSelection ssel && ssel.getFirstElement() instanceof final EditPart part
				&& part.getModel() instanceof final FBImpl libelem) {
			final DataTypeSelectionTreeContentProvider instance = DataTypeSelectionTreeContentProvider.INSTANCE;
			// instance.inputChanged(null, typeSelectionButton, e
			final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(
					HandlerUtil.getActiveShell(event), instance);
			dialog.setInput(libelem.getTypeLibrary());
			if ((dialog.open() == Window.OK) && (dialog.getFirstResult() instanceof final TypeNode typeNode
					&& typeNode.getType() instanceof final StructuredType structType)) {
				System.out.println(structType);
			}
		}

		return null;
	}

}
