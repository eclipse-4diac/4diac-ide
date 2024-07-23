package org.eclipse.fordiac.ide.typemanagement.refactoring.connection;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.refactoring.AbstractCommandChange;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class UpdateFBChange extends AbstractCommandChange<FBNetwork> {
	private final URI typeURI;

	protected UpdateFBChange(final URI elementURI, final Class<FBNetwork> elementClass, final URI typeURI) {
		super(elementURI, elementClass);
		this.typeURI = typeURI;
	}

	@Override
	public void initializeValidationData(final FBNetwork element, final IProgressMonitor pm) {
		// TODO Auto-generated method stub

	}

	@Override
	public RefactoringStatus isValid(final FBNetwork element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command createCommand(final FBNetwork element) {
		final CompoundCommand updateCommands = new CompoundCommand();
		if (TypeLibraryManager.INSTANCE.getTypeEntryForURI(typeURI).getType() instanceof final FBType sourceType) {
			getElementsOfType(sourceType).stream().forEach(fbnelem -> {
				final UpdateFBTypeCommand update = new UpdateFBTypeCommand(fbnelem);
				updateCommands.add(update);
			});
		}
		return updateCommands;
	}

	// TODO: find right place duplicate!
	private static List<FBNetworkElement> getElementsOfType(final FBType type) {
		return new BlockTypeInstanceSearch(type.getTypeEntry()).performSearch().stream()
				.map(FBNetworkElement.class::cast).toList();
	}

}
