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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.deployment.debug.Messages;
import org.eclipse.fordiac.ide.model.datatype.helper.TypeDeclarationParser;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * Checks whether a function block instance can be debugged with values read
 * from the runtime.
 *
 * <p>
 * Snapshot debugging supports mapped simple and basic function blocks and
 * functions that the evaluator can execute. Algorithms, methods and function
 * bodies need an evaluator for their language; native algorithms, such as the
 * C++ code in {@code FB_RANDOM}, have none. Internal simple and basic function
 * blocks are checked as well, also nested. Internal function blocks that only
 * a runtime can execute, such as service interface function blocks, are
 * reported as a warning.
 * </p>
 */
public final class FBSnapshotValidator {

	/**
	 * Validate an element for snapshot debugging.
	 *
	 * @param element the selected element
	 * @return an error if snapshot debugging is not possible, a warning if
	 *         internal function blocks need a runtime, otherwise an OK status
	 */
	public static IStatus validate(final Object element) {
		if (!(element instanceof final FB fb) || fb instanceof AdapterFB) {
			return Status.error(Messages.FBSnapshotValidator_NotAFunctionBlock);
		}
		if (fb.isContainedInTypedInstance()) {
			return Status.error(MessageFormat.format(Messages.FBSnapshotValidator_TypedContainer, fb.getName()));
		}
		final FBType type = fb.getType();
		if (!(type instanceof SimpleFBType || type instanceof BasicFBType || type instanceof FunctionFBType)) {
			return Status.error(
					MessageFormat.format(Messages.FBSnapshotValidator_UnsupportedType, fb.getName(), fb.getTypeName()));
		}
		final IStatus evaluation = validateEvaluation(fb.getName(), type);
		if (!evaluation.isOK()) {
			return evaluation;
		}
		final InterfaceList interfaceList = type.getInterfaceList();
		if (!interfaceList.getSockets().isEmpty() || !interfaceList.getPlugs().isEmpty()) {
			return Status.error(MessageFormat.format(Messages.FBSnapshotValidator_Adapters, fb.getName()));
		}
		if (interfaceList.getEventInputs().isEmpty()) {
			return Status.error(MessageFormat.format(Messages.FBSnapshotValidator_NoInputEvents, fb.getName()));
		}
		final Resource resource = fb.getResource();
		if (resource == null || resource.getDevice() == null) {
			return Status.error(MessageFormat.format(Messages.FBSnapshotValidator_NotMapped, fb.getName()));
		}
		final List<FB> runtimeFBs = getRuntimeFBs(type);
		if (!runtimeFBs.isEmpty()) {
			return Status.warning(MessageFormat.format(Messages.FBSnapshotValidator_RequiresRuntime,
					runtimeFBs.stream().map(runtimeFB -> runtimeFB.getName() + " (" + runtimeFB.getTypeName() + ")") //$NON-NLS-1$ //$NON-NLS-2$
							.collect(Collectors.joining(", ")))); //$NON-NLS-1$
		}
		return Status.OK_STATUS;
	}

	/**
	 * Get the internal function blocks, also nested ones, that only a runtime can
	 * execute: service interface function blocks and functions without body.
	 *
	 * @param type the type of the function block
	 * @return the internal function blocks that need a runtime
	 */
	public static List<FB> getRuntimeFBs(final FBType type) {
		if (!(type instanceof final BaseFBType baseFBType)) {
			return List.of();
		}
		return baseFBType.getInternalFbs().stream()
				.flatMap(internalFB -> requiresRuntime(internalFB.getType()) ? Stream.of(internalFB)
						: getRuntimeFBs(internalFB.getType()).stream())
				.toList();
	}

	/** Check whether the evaluator can execute a type and its internal function blocks. */
	private static IStatus validateEvaluation(final String path, final FBType type) {
		if (type instanceof final FunctionFBType functionFBType
				&& (functionFBType.getBody() == null || !hasEvaluator(functionFBType.getBody()))) {
			return Status.error(MessageFormat.format(Messages.FBSnapshotValidator_UnsupportedBody, path));
		}
		if (type instanceof final BaseFBType baseFBType) {
			final ICallable unsupported = Stream
					.concat(baseFBType.getAlgorithm().stream(), baseFBType.getMethods().stream())
					.filter(callable -> !hasEvaluator(callable)).findFirst().orElse(null);
			if (unsupported != null) {
				return Status.error(MessageFormat.format(Messages.FBSnapshotValidator_UnsupportedAlgorithm, path,
						unsupported.getName()));
			}
		}
		// the evaluator creates the variables from the type, where the bounds are
		// unknown
		final VarDeclaration variableLengthArray = type.getInterfaceList().getInOutVars().stream()
				.filter(variable -> variable.isArray()
						&& TypeDeclarationParser.isVariableArrayBounds(variable.getArraySize().getValue()))
				.findFirst().orElse(null);
		if (variableLengthArray != null) {
			return Status.error(MessageFormat.format(Messages.FBSnapshotValidator_VariableLengthArray, path,
					variableLengthArray.getName()));
		}
		if (type instanceof final BaseFBType baseFBType) {
			for (final FB internalFB : baseFBType.getInternalFbs()) {
				final String internalPath = path + '.' + internalFB.getName();
				final FBType internalType = internalFB.getType();
				if (requiresRuntime(internalType)) {
					continue; // see getRuntimeFBs
				}
				if (!isSupportedInternalType(internalType)) {
					return Status.error(MessageFormat.format(Messages.FBSnapshotValidator_UnsupportedInternalFB,
							internalPath, internalFB.getTypeName()));
				}
				final IStatus internalEvaluation = validateEvaluation(internalPath, internalType);
				if (!internalEvaluation.isOK()) {
					return internalEvaluation;
				}
			}
		}
		return Status.OK_STATUS;
	}

	private static boolean hasEvaluator(final EObject source) {
		return EvaluatorFactory.Registry.INSTANCE.getFactory(EvaluatorFactory.DEFAULT_VARIANT,
				source.eClass().getInstanceClass()) != null;
	}

	private static boolean requiresRuntime(final FBType type) {
		return type instanceof ServiceInterfaceFBType
				|| (type instanceof final FunctionFBType functionFBType && functionFBType.getBody() == null);
	}

	private static boolean isSupportedInternalType(final FBType type) {
		if (!(type instanceof SimpleFBType || type instanceof BasicFBType)) {
			return false;
		}
		final InterfaceList interfaceList = type.getInterfaceList();
		return interfaceList.getSockets().isEmpty() && interfaceList.getPlugs().isEmpty();
	}

	private FBSnapshotValidator() {
		throw new UnsupportedOperationException();
	}
}
