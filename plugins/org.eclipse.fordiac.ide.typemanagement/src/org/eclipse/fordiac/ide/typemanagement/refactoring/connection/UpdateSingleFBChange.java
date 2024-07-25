package org.eclipse.fordiac.ide.typemanagement.refactoring.connection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.typemanagement.refactoring.AbstractCommandChange;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class UpdateSingleFBChange extends AbstractCommandChange<FBNetworkElement> {
	FBNetworkElement instance;

	protected UpdateSingleFBChange(final URI elementURI, final Class<FBNetworkElement> elementClass) {
		super("", elementURI, elementClass);
	}

	@Override
	public void initializeValidationData(final FBNetworkElement element, final IProgressMonitor pm) {
		// TODO Auto-generated method stub

	}

	@Override
	public RefactoringStatus isValid(final FBNetworkElement element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command createCommand(final FBNetworkElement element) {
		return new UpdateFBTypeCommand(element);
	}

}
