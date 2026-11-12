/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.snapshot;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.fordiac.ide.deployment.debug.Messages;
import org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchData;
import org.eclipse.fordiac.ide.deployment.devResponse.Data;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.model.eval.variable.ECStateVariable;
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

/**
 * Reads the state of a function block instance from the runtime.
 *
 * <p>
 * The reader temporarily adds watches for all events, interface variables and
 * internal variables, reads them once and removes the watches again. Watches
 * that the runtime already has, e.g. from the monitoring of the user, are
 * neither added nor removed, as the runtime does not count watches. The values
 * are written into an FB variable of the type. Internal constants are not read,
 * they come from the type. The ECC state of a basic function block, the
 * variables of internal simple and basic function blocks, also nested ones,
 * and their ECC states are read as well. Internal function blocks that need a
 * runtime are skipped, their state cannot be used by the evaluator.
 * </p>
 */
public final class FBSnapshotReader {

	private static final String NAME_DELIMITER = "."; //$NON-NLS-1$

	/** The name of the ECC state of a basic function block in the runtime. */
	private static final String ECC_STATE = "!ECC"; //$NON-NLS-1$

	/**
	 * A variable of the snapshot.
	 *
	 * @param name      the path relative to the function block, e.g.
	 *                  {@code trig.MEM}
	 * @param watchName the name of its watch relative to the function block
	 * @param variable  the member of the FB variable that receives the value
	 */
	private record Member(String name, String watchName, Variable<?> variable) {
	}

	/**
	 * Read a snapshot of the given function block.
	 *
	 * @param target     the function block to read
	 * @param interactor the connected interactor, usually the one of a running
	 *                   monitoring session
	 * @param monitor    the progress monitor
	 * @return the snapshot
	 * @throws DeploymentException if the runtime rejects a request
	 */
	public static FBSnapshot read(final FBSnapshotTarget target, final IDeviceManagementInteractor interactor,
			final IProgressMonitor monitor) throws DeploymentException {
		final SubMonitor progress = SubMonitor.convert(monitor, 2);
		final Resource resource = target.resource();
		final String prefix = target.resourceRelativeName() + NAME_DELIMITER;
		final FBVariable variable = new FBVariable(target.instance().getName(), target.type());
		final List<Member> members = new ArrayList<>();
		collectMembers("", variable, members); //$NON-NLS-1$
		final List<String> watchNames = Stream
				.concat(getEvents(target.type().getInterfaceList()).map(Event::getName),
						members.stream().map(Member::watchName))
				.map(name -> prefix + name).toList();

		final List<String> addedWatches = new ArrayList<>();
		try {
			// the runtime does not count watches, so the watches it already has, e.g. from
			// the monitoring of the user, are neither added nor removed
			final DeploymentDebugWatchData existing = new DeploymentDebugWatchData(interactor.readWatches());
			for (final String watchName : watchNames) {
				if (existing.getLastData(resource, watchName) != null) {
					continue;
				}
				if (!interactor.addWatch(resource, watchName)) {
					throw new DeploymentException(MessageFormat.format(Messages.AbstractRuntimeWatch_AddWatch, watchName));
				}
				addedWatches.add(watchName);
			}
			progress.worked(1);

			final Map<String, String> rawValues = readValues(interactor, resource, watchNames);
			progress.worked(1);
			// the runtime rejects watches it cannot resolve without an error, but a
			// function block it knows always reports its events
			if (rawValues.values().stream().allMatch(Objects::isNull)) {
				throw new DeploymentException(MessageFormat.format(Messages.FBSnapshotReader_NotInRuntime,
						target.resourceRelativeName(), resource.getQualifiedName()));
			}
			final TypeLibrary typeLibrary = target.type().getTypeLibrary();
			final List<String> missing = new ArrayList<>();
			for (final Member member : members) {
				if (!setValue(member, rawValues.get(prefix + member.watchName()), typeLibrary)) {
					missing.add(member.name());
				}
			}
			return new FBSnapshot(variable, missing);
		} finally {
			removeWatches(interactor, resource, addedWatches);
		}
	}

	private static Stream<Event> getEvents(final InterfaceList interfaceList) {
		return Stream.concat(interfaceList.getEventInputs().stream(), interfaceList.getEventOutputs().stream());
	}

	static List<VarDeclaration> getVariables(final FBType type) {
		final InterfaceList interfaceList = type.getInterfaceList();
		final List<VarDeclaration> internalVars = type instanceof final BaseFBType baseFBType
				? baseFBType.getInternalVars()
				: List.of();
		return Stream.of(interfaceList.getInputVars(), interfaceList.getInOutVars(), interfaceList.getOutputVars(),
				internalVars).flatMap(List::stream).toList();
	}

	private static void collectMembers(final String path, final FBVariable fbVariable, final List<Member> members) {
		final Map<String, Variable<?>> fbMembers = fbVariable.getMembers();
		for (final VarDeclaration declaration : getVariables(fbVariable.getType())) {
			final String name = path + declaration.getName();
			members.add(new Member(name, name, fbMembers.get(declaration.getName())));
		}
		if (fbMembers.get(ECStateVariable.NAME) instanceof final ECStateVariable state) {
			members.add(new Member(path + ECStateVariable.NAME, path + ECC_STATE, state));
		}
		if (fbVariable.getType() instanceof final BaseFBType baseFBType) {
			for (final FB internalFB : baseFBType.getInternalFbs()) {
				// internal function blocks that need a runtime are skipped
				if (internalFB.getType() instanceof BaseFBType
						&& fbMembers.get(internalFB.getName()) instanceof final FBVariable internalVariable) {
					collectMembers(path + internalFB.getName() + NAME_DELIMITER, internalVariable, members);
				}
			}
		}
	}

	private static Map<String, String> readValues(final IDeviceManagementInteractor interactor,
			final Resource resource, final List<String> watchNames) throws DeploymentException {
		final DeploymentDebugWatchData watchData = new DeploymentDebugWatchData(interactor.readWatches());
		final Map<String, String> values = new HashMap<>();
		for (final String watchName : watchNames) {
			final Data data = watchData.getLastData(resource, watchName);
			values.put(watchName, data != null ? data.getValue() : null);
		}
		return values;
	}

	/**
	 * Write the value from the runtime into the member. The member keeps its
	 * initial value if the value is missing or invalid.
	 *
	 * @return {@code true} if the value was written
	 */
	private static boolean setValue(final Member member, final String rawValue, final TypeLibrary typeLibrary) {
		if (rawValue == null) {
			return false;
		}
		try {
			if (member.variable() instanceof final ECStateVariable state) {
				// the runtime reports the index of the state in the ECC of the type
				state.setValue(state.getType().getECC().getECState().get(Integer.parseInt(rawValue.trim())).getName(),
						typeLibrary);
			} else {
				member.variable().setValue(rawValue, typeLibrary);
			}
			return true;
		} catch (final Exception e) {
			ILog.get().warn(MessageFormat.format(Messages.FBSnapshotReader_InvalidValue, member.name(), rawValue), e);
			return false;
		}
	}

	private static void removeWatches(final IDeviceManagementInteractor interactor, final Resource resource,
			final List<String> watchNames) {
		for (final String watchName : watchNames) {
			try {
				if (!interactor.removeWatch(resource, watchName)) {
					ILog.get().warn(MessageFormat.format(Messages.AbstractRuntimeWatch_RemoveWatch, watchName));
				}
			} catch (final DeploymentException e) {
				ILog.get().warn(MessageFormat.format(Messages.AbstractRuntimeWatch_RemoveWatch, watchName), e);
			}
		}
	}

	private FBSnapshotReader() {
		throw new UnsupportedOperationException();
	}
}
