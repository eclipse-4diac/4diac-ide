package org.eclipse.fordiac.ide.typemanagement.refactoring.connection;

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.refactoring.EditConnectionsChange;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class ConnectionsToStructRefactoring extends Refactoring {
	private final URI sourceURI;
	private final URI destinationURI;
	private final URI netURI;
	private final Map<String, String> replacableConMap;

	private URI structURI;
	private String sourceVarName;
	private String destinationVarName;
	private boolean conflictResolution;

	private final IPath fullPath;
	private final Collection<VarDeclaration> vars;

	private final TypeLibrary lib;

	public ConnectionsToStructRefactoring(final FBNetwork net, final FBType sourceType, final FBType destinationType,
			final Map<String, String> replacableConMap) {
		this.netURI = EcoreUtil.getURI(net);
		this.sourceURI = EcoreUtil.getURI(sourceType);
		this.destinationURI = EcoreUtil.getURI(destinationType);
		this.replacableConMap = replacableConMap;

		vars = sourceType.getInterfaceList().getOutputs().filter(port -> replacableConMap.containsKey(port.getName()))
				.filter(VarDeclaration.class::isInstance).map(VarDeclaration.class::cast).toList();

		fullPath = sourceType.getTypeEntry().getTypeLibrary().getProject().getFullPath();

		lib = sourceType.getTypeLibrary();
	}

	@Override
	public final String getName() {
		return "Replace multiple Data Connections with Struct";
	}

	public RefactoringStatus setUserConfig(final URI structURI, final String sourceVarName,
			final String destinationVarName, final boolean conflictResolution) {
		// TODO: create generic function for checks
		final RefactoringStatus status = new RefactoringStatus();

		if (structURI != null) {
			final TypeEntry structTypeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForURI(structURI);
			if (structTypeEntry != null) {
				if (structTypeEntry.getType() instanceof final StructuredType structType) {
					if (!structType.getMemberVariables().stream().allMatch(
							structvar -> vars.stream().anyMatch(var -> var.getType().equals(structvar.getType())
									&& var.getName().equals(structvar.getName())))) {
						status.merge(RefactoringStatus.createFatalErrorStatus("Incompatible Structured Type"));
					}
				} else {
					status.merge(RefactoringStatus.createFatalErrorStatus("Selectet type is no Structured Type!"));
				}
			}
		} else {
			status.merge(RefactoringStatus.createFatalErrorStatus("Invalid Structured Type selected!"));
		}
		if (IdentifierVerifier.verifyIdentifier(sourceVarName).isPresent()) {
			status.merge(RefactoringStatus.createFatalErrorStatus("Invalid Output Name!"));
		}
		if ((TypeLibraryManager.INSTANCE.getTypeEntryForURI(sourceURI).getType() instanceof final FBType sourceFB)
				&& (sourceFB.getInterfaceList().getOutputs().anyMatch(port -> port.getName().equals(sourceVarName))
						&& !replacableConMap.containsKey(sourceVarName))) {
			status.merge(RefactoringStatus.createFatalErrorStatus("Output Name already exists!"));
		}
		if (IdentifierVerifier.verifyIdentifier(destinationVarName).isPresent()) {
			status.merge(RefactoringStatus.createFatalErrorStatus("Invalid Input Name!"));
		}
		if ((TypeLibraryManager.INSTANCE.getTypeEntryForURI(destinationURI)
				.getType() instanceof final FBType destinationFB)
				&& (destinationFB.getInterfaceList().getInputs()
						.anyMatch(port -> port.getName().equals(destinationVarName))
						&& !replacableConMap.containsValue(destinationVarName))) {
			status.merge(RefactoringStatus.createFatalErrorStatus("Input Name already exists!"));
		}

		this.structURI = structURI;
		this.sourceVarName = sourceVarName;
		this.destinationVarName = destinationVarName;
		this.conflictResolution = conflictResolution;
		return status;

	}

	@Override
	public RefactoringStatus checkInitialConditions(final IProgressMonitor pm) {
		final RefactoringStatus status = new RefactoringStatus();
		try {
			// TODO: create generic function for checks
			pm.beginTask("Checking preconditions...", 1);
			if ((TypeLibraryManager.INSTANCE.getTypeEntryForURI(sourceURI)
					.getType() instanceof final FBType sourceFB)) {
				if (sourceFB instanceof ServiceInterfaceFBType) {
					status.merge(RefactoringStatus.createFatalErrorStatus("Source FB is Service Interface!"));
				}
				if (replacableConMap.keySet().stream()
						.anyMatch(var -> sourceFB.getInterfaceList().getOutput(var) == null)) {
					status.merge(RefactoringStatus.createFatalErrorStatus("Source Outputs do not match!"));
				}
			} else {
				status.merge(RefactoringStatus.createFatalErrorStatus("Source is no FBType!"));
			}

			if ((TypeLibraryManager.INSTANCE.getTypeEntryForURI(destinationURI)
					.getType() instanceof final FBType destinationFB)) {
				if (destinationFB instanceof ServiceInterfaceFBType) {
					status.merge(RefactoringStatus.createFatalErrorStatus("Destination FB is Service Interface!"));
				}
				if (replacableConMap.values().stream()
						.anyMatch(var -> destinationFB.getInterfaceList().getInput(var) == null)) {
					status.merge(RefactoringStatus.createFatalErrorStatus("Destinaion Inputs do not match!"));
				}
			} else {
				status.merge(RefactoringStatus.createFatalErrorStatus("Destination is no FBType!"));
			}

		} finally {
			pm.done();
		}
		return status;
	}

	@Override
	public RefactoringStatus checkFinalConditions(final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();

		// TODO: implement conditions

		return status;
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final CompositeChange compChange = new CompositeChange("Replace Struct");
		pm.beginTask("Replacing with Struct", 1);
		if (TypeLibraryManager.INSTANCE.getTypeEntryForURI(structURI) == null) {
			compChange.add(new CreateStructChange(structURI, vars));
		}
		compChange.add(new ReplaceVarsWithStructChange(sourceURI, FBType.class, replacableConMap.keySet(), structURI,
				sourceVarName, false, 0));
		compChange.add(new ReplaceVarsWithStructChange(destinationURI, FBType.class, replacableConMap.values(),
				structURI, destinationVarName, true, 0));
		compChange.add(new UpdateFBChange(netURI, FBNetwork.class, sourceURI));
		compChange.add(new UpdateFBChange(netURI, FBNetwork.class, destinationURI));
		compChange.add(new ConnectStructChange(netURI, FBNetwork.class, replacableConMap, sourceURI, destinationURI,
				sourceVarName, destinationVarName));
		if (conflictResolution) {
			compChange.add(
					new EditConnectionsChange(netURI, FBNetwork.class, replacableConMap, sourceURI, structURI, true));
			compChange.add(new EditConnectionsChange(netURI, FBNetwork.class, replacableConMap, destinationURI,
					structURI, false));
		}
		pm.done();
		return compChange;
	}

	public TypeLibrary getTypeLibrary() {
		return lib;
	}

}
