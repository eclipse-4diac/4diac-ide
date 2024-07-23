package org.eclipse.fordiac.ide.typemanagement.refactoring.connection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.typemanagement.wizards.ConnectionsToStructWizard;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.ui.handlers.HandlerUtil;

public class ConnectionsToStructHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		try {
			if (HandlerUtil.getCurrentSelection(event) instanceof final IStructuredSelection selection) {

				final List<Connection> connections = selection.stream().filter(EditPart.class::isInstance)
						.map(EditPart.class::cast).map(EditPart::getModel).filter(Connection.class::isInstance)
						.map(Connection.class::cast).toList();
				final FBType sourceType = connections.get(0).getSourceElement().getType();
				final FBType destinationType = connections.get(0).getDestinationElement().getType();
				final Map<String, String> replacableConMap = new HashMap<>();
				connections.stream().forEach(
						con -> replacableConMap.put(con.getSource().getName(), con.getDestination().getName()));

				final ConnectionsToStructRefactoring refactoring = new ConnectionsToStructRefactoring(
						connections.getFirst().getFBNetwork(), sourceType, destinationType, replacableConMap);
				final ConnectionsToStructWizard wizard = new ConnectionsToStructWizard(selection, refactoring);
				final RefactoringWizardOpenOperation openOperation = new RefactoringWizardOpenOperation(wizard);
				openOperation.run(HandlerUtil.getActiveShell(event), refactoring.getName());
			}
		} catch (final Exception e) {
			FordiacLogHelper.logError("Error during ReplaceStruct refactoring", e); //$NON-NLS-1$
		}
		return null;
	}
}
