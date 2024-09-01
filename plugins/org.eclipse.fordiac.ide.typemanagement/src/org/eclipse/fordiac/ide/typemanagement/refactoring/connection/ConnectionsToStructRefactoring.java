/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mathias Garstenauer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.connection;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.CommandCompositeChange;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

/**
 * A Refactoring Operation for replacing multiple data connections with one
 * single Struct connection. Resulting errors due to deleted Pins can be repairs
 * automatically by setting conflictResolution to true.
 */
public class ConnectionsToStructRefactoring extends Refactoring {
	private final URI sourceURI;
	private final URI destinationURI;
	private final Map<String, String> replaceableConMap;

	private URI structURI;
	private String sourceVarName;
	private String destinationVarName;
	private boolean conflictResolution;

	private final Collection<VarDeclaration> vars;

	private final TypeLibrary lib;

	private CompositeChange compChange;

	/**
	 * Creates a Instance
	 *
	 * @param sourceType       FB which represents the Source
	 * @param destinationType  FB which represents the Destination
	 * @param replacableConMap Mapping of the Output Variables to the Input
	 *                         Variables
	 */
	public ConnectionsToStructRefactoring(final FBType sourceType, final FBType destinationType,
			final Map<String, String> replacableConMap) {
		this.sourceURI = EcoreUtil.getURI(Objects.requireNonNull(sourceType));
		this.destinationURI = EcoreUtil.getURI(Objects.requireNonNull(destinationType));
		this.replaceableConMap = Objects.requireNonNull(replacableConMap);

		vars = sourceType.getInterfaceList().getOutputs().filter(port -> replacableConMap.containsKey(port.getName()))
				.filter(VarDeclaration.class::isInstance).map(VarDeclaration.class::cast).toList();

		lib = sourceType.getTypeLibrary();
	}

	@Override
	public final String getName() {
		return Messages.ConnectionsToStructRefactoring_RefactoringTitle;
	}

	/**
	 * Set the additional values needed from the user.
	 *
	 * @param structURI          URI of the
	 *                           {@link org.eclipse.fordiac.ide.model.data.StructuredType
	 *                           StructuredType}
	 * @param sourceVarName      Name of the output variable at the Source FBType
	 * @param destinationVarName Name of the input variable at the Destination
	 *                           FBType
	 * @param conflictResolution Whether errors produced by the refactoring should
	 *                           be resolved by placing StructManipulators
	 * @return
	 */
	public RefactoringStatus setUserConfig(final URI structURI, final String sourceVarName,
			final String destinationVarName, final boolean conflictResolution) {

		this.structURI = structURI;
		this.sourceVarName = sourceVarName;
		this.destinationVarName = destinationVarName;
		this.conflictResolution = conflictResolution;
		return checkFinalConditions();

	}

	@Override
	public RefactoringStatus checkInitialConditions(final IProgressMonitor pm) {
		final RefactoringStatus status = new RefactoringStatus();
		try {
			checkInitialFB(status, sourceURI, true);
			checkInitialFB(status, destinationURI, false);
		} finally {
			pm.done();
		}
		return status;
	}

	private void checkInitialFB(final RefactoringStatus status, final URI uri, final boolean isSource) {
		final String target = isSource ? Messages.ConnectionsToStructRefactoring_Source
				: Messages.ConnectionsToStructRefactoring_Destination;
		if ((TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri).getType() instanceof final FBType fbType)) {
			final List<VarDeclaration> interfaceList;
			final List<String> varNames;

			if (isSource) {
				interfaceList = fbType.getInterfaceList().getOutputVars();
				varNames = new ArrayList<>(replaceableConMap.keySet());
			} else {
				interfaceList = fbType.getInterfaceList().getInputVars();
				varNames = new ArrayList<>(replaceableConMap.values());
			}

			if (fbType instanceof ServiceInterfaceFBType) {
				status.merge(RefactoringStatus.createFatalErrorStatus(
						MessageFormat.format(Messages.ConnectionsToStructRefactoring_IsServiceFB, target)));
			}
			if (varNames.stream().allMatch(
					name -> interfaceList.stream().map(VarDeclaration::getName).anyMatch(String.class::equals))) {
				status.merge(RefactoringStatus.createFatalErrorStatus(
						MessageFormat.format(Messages.ConnectionsToStructRefactoring_PortsNoMatch, target)));
			}

			final List<Event> referenceWiths = interfaceList.stream()
					.filter(ielement -> ielement.getName().equals(varNames.getFirst())).map(VarDeclaration.class::cast)
					.map(VarDeclaration::getWiths)
					.map(withlist -> withlist.stream().map(With::eContainer).map(Event.class::cast).toList())
					.findFirst().orElse(new ArrayList<>());
			if (interfaceList.stream().filter(varDec -> varNames.contains(varDec.getName()))
					.map(VarDeclaration::getWiths)
					.map(withs -> withs.stream().map(With::eContainer).map(Event.class::cast).toList())
					.anyMatch(withlist -> (!withlist.containsAll(referenceWiths)
							|| withlist.size() != referenceWiths.size()))) {
				status.merge(RefactoringStatus.createFatalErrorStatus(
						MessageFormat.format(Messages.ConnectionsToStructRefactoring_EventConflict, target)));
			}
		} else {
			status.merge(RefactoringStatus.createFatalErrorStatus(
					MessageFormat.format(Messages.ConnectionsToStructRefactoring_NoFB, target)));
		}
	}

	@Override
	public RefactoringStatus checkFinalConditions(final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		return checkFinalConditions();
	}

	private RefactoringStatus checkFinalConditions() {
		final RefactoringStatus status = new RefactoringStatus();

		checkStruct(status);

		checkName(status, sourceVarName, true);
		checkName(status, destinationVarName, false);

		if (destinationURI.toString().equals(sourceURI.toString()) && sourceVarName.equals(destinationVarName)) {
			status.merge(
					RefactoringStatus.createFatalErrorStatus(Messages.ConnectionsToStructRefactoring_SameFBSameName));
		}
		if (conflictResolution
				&& (lib.getFBTypeEntry("STRUCT_DEMUX") == null || lib.getFBTypeEntry("STRUCT_MUX") == null)) {
			status.merge(RefactoringStatus
					.createFatalErrorStatus(Messages.ConnectionsToStructRefactoring_MissingStructManipulator));
		}
		return status;
	}

	private void checkStruct(final RefactoringStatus status) {
		if (structURI != null) {
			final TypeEntry structTypeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForURI(structURI);

			if (structTypeEntry != null && structTypeEntry.getType() instanceof final DataType type) {
				if (type instanceof final StructuredType structType) {
					if (!structType.getMemberVariables().stream().allMatch(
							structvar -> vars.stream().anyMatch(vardec -> vardec.getType().equals(structvar.getType())
									&& vardec.getName().equals(structvar.getName())))) {
						status.merge(RefactoringStatus.createFatalErrorStatus(
								Messages.ConnectionsToStructRefactoring_IncompatibleStructType));
					}
				} else {
					status.merge(RefactoringStatus
							.createFatalErrorStatus(Messages.ConnectionsToStructRefactoring_SelectionIsNoStruct));
				}
			} else if (lib.getDataTypeLibrary().getTypeIfExists(structURI.trimFileExtension().lastSegment()) != null) {
				status.merge(
						RefactoringStatus.createFatalErrorStatus(Messages.ConnectionsToStructRefactoring_StructExists));
			}
		} else {
			status.merge(RefactoringStatus.createFatalErrorStatus(Messages.ConnectionsToStructRefactoring_InvalidType));
		}
	}

	private void checkName(final RefactoringStatus status, final String name, final boolean isSource) {
		final URI fbURI;
		final Collection<String> nameCol;
		final String target;
		if (isSource) {
			fbURI = sourceURI;
			nameCol = replaceableConMap.keySet();
			target = Messages.ConnectionsToStructRefactoring_Output;
		} else {
			fbURI = destinationURI;
			nameCol = replaceableConMap.values();
			target = Messages.ConnectionsToStructRefactoring_Input;
		}

		if (IdentifierVerifier.verifyIdentifier(name).isPresent()) {
			status.merge(RefactoringStatus.createFatalErrorStatus(
					MessageFormat.format(Messages.ConnectionsToStructRefactoring_InvalidName, target)));
		}
		if ((TypeLibraryManager.INSTANCE.getTypeEntryForURI(fbURI).getType() instanceof final FBType fbType)
				&& (fbType.getInterfaceList().getAllInterfaceElements().stream()
						.anyMatch(port -> port.getName().equals(name)) && !nameCol.contains(name))) {
			status.merge(RefactoringStatus.createFatalErrorStatus(
					MessageFormat.format(Messages.ConnectionsToStructRefactoring_NameExists, target)));
		}

	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		compChange = new CommandCompositeChange(Messages.ConnectionsToStructRefactoring_ChangeName);
		pm.beginTask(Messages.ConnectionsToStructRefactoring_ProgressText, 1);

		if (TypeLibraryManager.INSTANCE.getTypeEntryForURI(structURI) == null) {
			compChange.add(new CreateStructChange(structURI, vars));
		}

		compChange.add(new ReplaceVarsWithStructChange(sourceURI, replaceableConMap.keySet(), structURI, sourceVarName,
				false, 0));
		compChange.add(new ReplaceVarsWithStructChange(destinationURI, replaceableConMap.values(), structURI,
				destinationVarName, true, 0));

		if (TypeLibraryManager.INSTANCE.getTypeEntryForURI(sourceURI).getType() instanceof final FBType sourceType
				&& TypeLibraryManager.INSTANCE.getTypeEntryForURI(destinationURI)
						.getType() instanceof final FBType destinationType) {

			update(sourceType, destinationType);
			connect(sourceType, destinationType);
		}
		pm.done();
		return compChange;
	}

	private void update(final FBType sourceType, final FBType destinationType) {
		final Map<AutomationSystem, List<URI>> updateMap = new HashMap<>();
		createUpdateChanges(sourceType, updateMap);
		if (!sourceURI.toString().equals(destinationURI.toString())) {
			createUpdateChanges(destinationType, updateMap);
		}
		updateMap.entrySet().forEach(
				entry -> compChange.add(new SystemUpdateFBChange(EcoreUtil.getURI(entry.getKey()), entry.getValue())));
	}

	private static void createUpdateChanges(final FBType sourceType, final Map<AutomationSystem, List<URI>> updateMap) {
		new BlockTypeInstanceSearch(sourceType.getTypeEntry()).performSearch().stream()
				.map(FBNetworkElement.class::cast).forEach(instance -> addToMap(updateMap, instance));
	}

	private void connect(final FBType sourceType, final FBType destinationType) {
		final Map<AutomationSystem, List<URI>> connectMap = new HashMap<>();
		final Map<FBNetworkElement, FBNetworkElement> correctConnectionMap = new HashMap<>();
		final List<? extends EObject> sourceSearch = new BlockTypeInstanceSearch(sourceType.getTypeEntry())
				.performSearch();
		final List<? extends EObject> destinationSearch = new BlockTypeInstanceSearch(destinationType.getTypeEntry())
				.performSearch();

		destinationSearch.stream().map(FBNetworkElement.class::cast).forEach(instance -> {

			// Collect all correct connections
			final List<Connection> cons = instance.getInterface().getInputs()
					.map(IInterfaceElement::getInputConnections).flatMap(EList::stream)
					.filter(con -> replaceableConMap.containsKey(con.getSource().getName())
							&& replaceableConMap.get(con.getSource().getName()).equals(con.getDestination().getName())
							&& con.getSourceElement().getType().getName().equals(sourceType.getName()))
					.toList();

			// Check if all connections between 2 instances are correct
			if (cons.stream().map(Connection::getSourceElement).distinct().count() == 1
					&& cons.size() == replaceableConMap.size()) {
				correctConnectionMap.put(instance, cons.get(0).getSourceElement());
				addToMap(connectMap, instance);
			}
		});

		connectMap.entrySet()
				.forEach(entry -> compChange.add(new SystemConnectStructChange(EcoreUtil.getURI(entry.getKey()),
						entry.getValue(), replaceableConMap, sourceVarName, destinationVarName)));

		if (conflictResolution) {
			conflictResolution(sourceSearch, destinationSearch, correctConnectionMap);
		}
	}

	private void conflictResolution(final List<? extends EObject> sourceSearch,
			final List<? extends EObject> destinationSearch,
			final Map<FBNetworkElement, FBNetworkElement> correctConnectionMap) {
		final Map<AutomationSystem, List<URI>> repairSourceMap = new HashMap<>();
		final Map<AutomationSystem, List<URI>> repairDestinationMap = new HashMap<>();
		destinationSearch.stream().map(FBNetworkElement.class::cast)
				.filter(instance -> !correctConnectionMap.containsKey(instance)).forEach(instance -> {
					if (instance.getInterface().getInputs()
							.filter(input -> replaceableConMap.containsValue(input.getName()))
							.map(IInterfaceElement::getInputConnections).flatMap(EList::stream).findAny().isPresent()) {
						addToMap(repairDestinationMap, instance);
					}
				});
		sourceSearch.stream().map(FBNetworkElement.class::cast).forEach(instance -> {
			if (instance.getInterface().getOutputs().filter(output -> replaceableConMap.containsKey(output.getName()))
					.map(IInterfaceElement::getOutputConnections).flatMap(EList::stream)
					.anyMatch(con -> !correctConnectionMap.containsKey(con.getDestinationElement()))) {
				addToMap(repairSourceMap, instance);
			}
		});
		repairSourceMap.entrySet().forEach(
				entry -> compChange.add(new SystemRepairBrokenConnectionChange(EcoreUtil.getURI(entry.getKey()),
						structURI, replaceableConMap, entry.getValue(), true)));
		repairDestinationMap.entrySet().forEach(
				entry -> compChange.add(new SystemRepairBrokenConnectionChange(EcoreUtil.getURI(entry.getKey()),
						structURI, replaceableConMap, entry.getValue(), false)));
	}

	private static void addToMap(final Map<AutomationSystem, List<URI>> map, final FBNetworkElement element) {
		Optional.ofNullable(map.get(element.getFbNetwork().getAutomationSystem())).orElseGet(() -> {
			final List<URI> list = new ArrayList<>();
			map.put(element.getFbNetwork().getAutomationSystem(), list);
			return list;
		}).add(EcoreUtil.getURI(element));
	}

	public TypeLibrary getTypeLibrary() {
		return lib;
	}

}
