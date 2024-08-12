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
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

/**
 *
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

	public RefactoringStatus setUserConfig(final URI structURI, final String sourceVarName,
			final String destinationVarName, final boolean conflictResolution) {
		final RefactoringStatus status = new RefactoringStatus();

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
		if (IdentifierVerifier.verifyIdentifier(sourceVarName).isPresent()) {
			status.merge(
					RefactoringStatus.createFatalErrorStatus(Messages.ConnectionsToStructRefactoring_InvalidOutName));
		}
		if ((TypeLibraryManager.INSTANCE.getTypeEntryForURI(sourceURI).getType() instanceof final FBType sourceFB)
				&& (sourceFB.getInterfaceList().getAllInterfaceElements().stream()
						.anyMatch(port -> port.getName().equals(sourceVarName))
						&& !replaceableConMap.containsKey(sourceVarName))) {
			status.merge(
					RefactoringStatus.createFatalErrorStatus(Messages.ConnectionsToStructRefactoring_OutNameExists));
		}
		if (IdentifierVerifier.verifyIdentifier(destinationVarName).isPresent()) {
			status.merge(
					RefactoringStatus.createFatalErrorStatus(Messages.ConnectionsToStructRefactoring_InvalidInName));
		}
		if ((TypeLibraryManager.INSTANCE.getTypeEntryForURI(destinationURI)
				.getType() instanceof final FBType destinationFB)
				&& (destinationFB.getInterfaceList().getAllInterfaceElements().stream()
						.anyMatch(port -> port.getName().equals(destinationVarName))
						&& !replaceableConMap.containsValue(destinationVarName))) {
			status.merge(
					RefactoringStatus.createFatalErrorStatus(Messages.ConnectionsToStructRefactoring_InNameExists));

		}
		if (destinationURI.toString().equals(sourceURI.toString()) && sourceVarName.equals(destinationVarName)) {
			status.merge(
					RefactoringStatus.createFatalErrorStatus(Messages.ConnectionsToStructRefactoring_SameFBSameName));
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
			pm.beginTask(Messages.ConnectionsToStructRefactoring_CheckPreconditions, 1);
			if ((TypeLibraryManager.INSTANCE.getTypeEntryForURI(sourceURI)
					.getType() instanceof final FBType sourceFB)) {
				if (sourceFB instanceof ServiceInterfaceFBType) {
					status.merge(RefactoringStatus
							.createFatalErrorStatus(Messages.ConnectionsToStructRefactoring_SourceIsServiceFB));
				}
				if (replaceableConMap.keySet().stream()
						.anyMatch(varname -> sourceFB.getInterfaceList().getOutput(varname) == null)) {
					status.merge(RefactoringStatus
							.createFatalErrorStatus(Messages.ConnectionsToStructRefactoring_SourceOutputsNoMatch));
				}
				final List<Event> referenceWiths = ((VarDeclaration) sourceFB.getInterfaceList()
						.getOutput(replaceableConMap.keySet().iterator().next())).getWiths().stream()
						.map(With::eContainer).map(Event.class::cast).toList();
				if (replaceableConMap.keySet().stream().map(varname -> sourceFB.getInterfaceList().getOutput(varname))
						.filter(VarDeclaration.class::isInstance).map(VarDeclaration.class::cast)
						.map(VarDeclaration::getWiths)
						.map(withs -> withs.stream().map(With::eContainer).map(Event.class::cast).toList())
						.anyMatch(withlist -> !withlist.containsAll(referenceWiths)
								|| withlist.size() != referenceWiths.size())) {
					status.merge(RefactoringStatus
							.createFatalErrorStatus(Messages.ConnectionsToStructRefactoring_SourceEventConflict));
				}
			} else {
				status.merge(
						RefactoringStatus.createFatalErrorStatus(Messages.ConnectionsToStructRefactoring_SourceNoFB));
			}

			if ((TypeLibraryManager.INSTANCE.getTypeEntryForURI(destinationURI)
					.getType() instanceof final FBType destinationFB)) {
				if (destinationFB instanceof ServiceInterfaceFBType) {
					status.merge(RefactoringStatus
							.createFatalErrorStatus(Messages.ConnectionsToStructRefactoring_DestinationIsServiceFB));
				}
				if (replaceableConMap.values().stream()
						.anyMatch(varname -> destinationFB.getInterfaceList().getInput(varname) == null)) {
					status.merge(RefactoringStatus
							.createFatalErrorStatus(Messages.ConnectionsToStructRefactoring_DestinationOutputsNoMatch));
				}
				final List<Event> referenceWiths = ((VarDeclaration) destinationFB.getInterfaceList()
						.getInput(replaceableConMap.values().iterator().next())).getWiths().stream()
						.map(With::eContainer).map(Event.class::cast).toList();
				if (replaceableConMap.values().stream()
						.map(varname -> destinationFB.getInterfaceList().getInput(varname))
						.filter(VarDeclaration.class::isInstance).map(VarDeclaration.class::cast)
						.map(VarDeclaration::getWiths)
						.map(withs -> withs.stream().map(With::eContainer).map(Event.class::cast).toList())
						.anyMatch(withlist -> !withlist.containsAll(referenceWiths)
								|| withlist.size() != referenceWiths.size())) {
					status.merge(RefactoringStatus
							.createFatalErrorStatus(Messages.ConnectionsToStructRefactoring_DestinationEventConflict));
				}
			} else {
				status.merge(RefactoringStatus
						.createFatalErrorStatus(Messages.ConnectionsToStructRefactoring_DestinationNoFB));
			}

		} finally {
			pm.done();
		}
		return status;
	}

	@Override
	public RefactoringStatus checkFinalConditions(final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		// TODO: implement conditions (basically checked by setUserConfig()
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		compChange = new CompositeChange(Messages.ConnectionsToStructRefactoring_ChangeName);
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
		repairSourceMap.entrySet()
				.forEach(entry -> compChange.add(new SystemRepairBrokenConnectionChange(EcoreUtil.getURI(entry.getKey()),
						structURI, replaceableConMap, entry.getValue(), true)));
		repairDestinationMap.entrySet()
				.forEach(entry -> compChange.add(new SystemRepairBrokenConnectionChange(EcoreUtil.getURI(entry.getKey()),
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
