package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class ReplaceStructRefactoring extends Refactoring {
	private FBType sourceType;
	private FBType destinationType;
	private FBNetwork net;
	private URI structURI;
	private String sourceVarName;
	private String destinationVarName;
	private Map<String, String> replacableConMap;
	private boolean conflictResolution;

	@Override
	public String getName() {

		return "Replace multiple Data Connections with Struct";
	}

	public void setStructURI(final URI structURI) {
		this.structURI = structURI;
	}

	public void setSourceType(final FBType sourceType) {
		this.sourceType = sourceType;
	}

	public void setDestinationType(final FBType destinationType) {
		this.destinationType = destinationType;
	}

	public void setNet(final FBNetwork net) {
		this.net = net;
	}

	public void setSourceVarName(final String sourceVarName) {
		this.sourceVarName = sourceVarName;
	}

	public void setDestinationVarName(final String destinationVarName) {
		this.destinationVarName = destinationVarName;
	}

	public void setReplacableConMap(final Map<String, String> replacableConMap) {
		this.replacableConMap = replacableConMap;
	}

	public void setConflictResolution(final boolean conflictResolution) {
		this.conflictResolution = conflictResolution;
	}

	@Override
	public RefactoringStatus checkInitialConditions(final IProgressMonitor pm) {
		final RefactoringStatus status = new RefactoringStatus();
		try {
			pm.beginTask("Checking preconditions...", 1);
//			if (fMethod == null) {
//				status.merge(RefactoringStatus.createFatalErrorStatus("Method has not been specified."));
//			} else if (!fMethod.exists()) {
//				status.merge(RefactoringStatus.createFatalErrorStatus(MessageFormat.format("Method ''{0}'' does not exist.", new Object[] { fMethod.getElementName()})));
//			} else if (!fMethod.isBinary() && !fMethod.getCompilationUnit().isStructureKnown()) {
//				status.merge(RefactoringStatus.createFatalErrorStatus(MessageFormat.format("Compilation unit ''{0}'' contains compile errors.", new Object[] { fMethod.getCompilationUnit().getElementName()})));
//			}
		} finally {
			pm.done();
		}
		return status;
	}

	@Override
	public RefactoringStatus checkFinalConditions(final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		return status;
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final CompositeChange compChange = new CompositeChange("Replace Struct");
		compChange.add(new ReplaceVarsWithStructChange(EcoreUtil.getURI(sourceType), FBType.class,
				replacableConMap.keySet(), structURI, sourceVarName, false, 0));
		compChange.add(new ReplaceVarsWithStructChange(EcoreUtil.getURI(destinationType), FBType.class,
				replacableConMap.values(), structURI, destinationVarName, true, 0));
		compChange.add(new UpdateFBChange(EcoreUtil.getURI(net), FBNetwork.class, EcoreUtil.getURI(sourceType),
				EcoreUtil.getURI(destinationType)));
		compChange.add(new ConnectStructChange(EcoreUtil.getURI(net), FBNetwork.class, replacableConMap,
				EcoreUtil.getURI(sourceType), EcoreUtil.getURI(destinationType)));
		return compChange;
	}

}
