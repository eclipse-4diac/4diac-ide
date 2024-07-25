package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class RepairBrokenConnectionChange extends AbstractCommandChange<FBNetworkElement> {
	private final URI structURI;
	private final Map<String, String> replaceableConMap;
	private final boolean isSource;

	protected RepairBrokenConnectionChange(final URI elementURI, final Class<FBNetworkElement> elementClass,
			final URI structURI, final Map<String, String> replaceableConMap, final boolean isSource) {
		super("", elementURI, elementClass);
		this.replaceableConMap = replaceableConMap;
		this.structURI = structURI;
		this.isSource = isSource;

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
		final CompoundCommand cmd = new CompoundCommand();
		final Stream<ErrorMarkerInterface> errormarkers = element.getInterface().getErrorMarker().stream();
		final Stream<Connection> connections;
		final Function<Connection, String> connectToVar;

		if (TypeLibraryManager.INSTANCE.getTypeEntryForURI(structURI)
				.getType() instanceof final StructuredType structType) {
			if (isSource) {
				connections = errormarkers.filter(err -> replaceableConMap.containsKey(err.getName()))
						.map(ErrorMarkerInterface::getOutputConnections).flatMap(EList::stream);
				connectToVar = t -> t.getSource().getName();
			} else {
				connections = errormarkers.filter(err -> replaceableConMap.containsValue(err.getName()))
						.map(ErrorMarkerInterface::getInputConnections).flatMap(EList::stream);
				connectToVar = t -> replaceableConMap.entrySet().stream()
						.filter(entry -> entry.getValue().equals(t.getDestination().getName())).findAny().get()
						.getKey();
			}

			connections.forEach(con -> cmd
					.add(new RepairBrokenConnectionCommand(con, isSource, structType, connectToVar.apply(con))));
		}
		return cmd;
	}

}
