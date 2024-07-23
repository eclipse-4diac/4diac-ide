package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.List;
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
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class EditConnectionsChange extends AbstractCommandChange<FBNetwork> {
	private final Map<String, String> replacableConMap;
	private final URI source;
	private final URI structURI;
	private final boolean isSourceReconnect;

	protected EditConnectionsChange(final URI elementURI, final Class<FBNetwork> elementClass,
			final Map<String, String> replacableConMap, final URI source, final URI structURI,
			final boolean isSourceReconnect) {
		super(elementURI, elementClass);
		this.replacableConMap = replacableConMap;
		this.source = source;
		this.structURI = structURI;
		this.isSourceReconnect = isSourceReconnect;
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
		if (TypeLibraryManager.INSTANCE.getTypeEntryForURI(source).getType() instanceof final FBType type
				&& TypeLibraryManager.INSTANCE.getTypeEntryForURI(structURI)
						.getType() instanceof final StructuredType structType) {
			return editConnections(type, structType, isSourceReconnect);
		}
		return null;
	}

	private Command editConnections(final FBType type, final StructuredType structType, final boolean isSource) {
		final CompoundCommand cmd = new CompoundCommand();
		final Stream<ErrorMarkerInterface> errormarkers = getElementsOfType(type).stream()
				.map(FBNetworkElement::getInterface).map(InterfaceList::getErrorMarker).flatMap(EList::stream);
		final Stream<Connection> connections;
		final Function<Connection, String> connectToVar;

		if (isSource) {
			connections = errormarkers.filter(err -> replacableConMap.containsKey(err.getName()))
					.map(ErrorMarkerInterface::getOutputConnections).flatMap(EList::stream);
			connectToVar = t -> t.getSource().getName();
		} else {
			connections = errormarkers.filter(err -> replacableConMap.containsValue(err.getName()))
					.map(ErrorMarkerInterface::getInputConnections).flatMap(EList::stream);
			connectToVar = t -> replacableConMap.entrySet().stream()
					.filter(entry -> entry.getValue().equals(t.getDestination().getName())).findAny().get().getKey();
		}

		connections.forEach(
				con -> cmd.add(new RepairBrokenConnectionCommand(con, isSource, structType, connectToVar.apply(con))));
		return cmd;
	}

	private static List<FBNetworkElement> getElementsOfType(final FBType type) {
		return new BlockTypeInstanceSearch(type.getTypeEntry()).performSearch().stream()
				.map(FBNetworkElement.class::cast).toList();
	}
}
