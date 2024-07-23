package org.eclipse.fordiac.ide.typemanagement.refactoring.connection;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.typemanagement.refactoring.AbstractCommandChange;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class ConnectionsToStructChange extends AbstractCommandChange<FBNetwork> {
	private final FBType sourceType;
	private final FBType destinationType;
	private final StructuredType structType;
	private final String sourceVarName;
	private final String destinationVarName;
	private final Map<String, String> replacableConMap;
	private final boolean conflictResolution;

	protected ConnectionsToStructChange(final URI elementURI, final Class<FBNetwork> elementClass,
			final FBType sourceType, final FBType destinationType, final StructuredType structType,
			final String sourceVarName, final String destinationVarName, final Map<String, String> replacableConMap,
			final boolean conflictResolution) {
		super(elementURI, elementClass);
		this.sourceType = sourceType;
		this.destinationType = destinationType;
		this.structType = structType;
		this.sourceVarName = sourceVarName;
		this.destinationVarName = destinationVarName;
		this.replacableConMap = replacableConMap;
		this.conflictResolution = conflictResolution;
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
		return new ConnectionsToStructCommand(sourceType, destinationType, structType, sourceVarName,
				destinationVarName, replacableConMap, conflictResolution);
	}

}
