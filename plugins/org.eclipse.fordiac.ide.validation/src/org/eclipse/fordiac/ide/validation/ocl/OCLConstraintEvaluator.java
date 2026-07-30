/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.validation.ocl;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.pivot.utilities.OCL;
import org.eclipse.ocl.pivot.values.TupleValue;

public final class OCLConstraintEvaluator {

	private static final String STATUS_FIELD = "status"; //$NON-NLS-1$
	private static final String MESSAGE_FIELD = "message"; //$NON-NLS-1$
	private static final String SEVERITY_FIELD = "severity"; //$NON-NLS-1$
	private static final String MARKER_TARGET_FIELD = "markerTarget"; //$NON-NLS-1$
	private static final String CONTEXT_TARGET = "context"; //$NON-NLS-1$

	private final OCL ocl;
	private final Consumer<ConstraintError> errorHandler;
	private final Set<OCLConstraintDefinition> invalidConstraints = Collections
			.newSetFromMap(new IdentityHashMap<>());

	public OCLConstraintEvaluator(final OCL ocl, final Consumer<ConstraintError> errorHandler) {
		this.ocl = ocl;
		this.errorHandler = errorHandler;
	}

	public Optional<OCLDiagnostic> evaluate(final EObject context, final OCLConstraintDefinition definition) {
		if (invalidConstraints.contains(definition)) {
			return Optional.empty();
		}
		try {
			if (definition.hasTupleDiagnostic()) {
				return evaluateTupleDiagnostic(context, definition);
			}
			return evaluateLegacyDiagnostic(context, definition);
		} catch (final RuntimeException e) {
			reportInvalidConstraint(definition, e);
			return Optional.empty();
		}
	}

	private Optional<OCLDiagnostic> evaluateTupleDiagnostic(final EObject context,
			final OCLConstraintDefinition definition) {
		final OCLTupleDiagnostic tupleDiagnostic = definition.tupleDiagnostic();
		final TupleValue tuple = evaluateTuple(context, tupleDiagnostic);
		if (tupleDiagnostic.getRequiredField(tuple, STATUS_FIELD, Boolean.class).booleanValue()) {
			return Optional.empty();
		}

		final OptionalInt severity = getMarkerSeverity(tuple, tupleDiagnostic);
		if (severity.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(new OCLDiagnostic(getMessage(tuple, definition), severity.getAsInt(),
				getMarkerTarget(tuple, tupleDiagnostic, context)));
	}

	private TupleValue evaluateTuple(final EObject context, final OCLTupleDiagnostic tupleDiagnostic) {
		final Object value = ocl.evaluate(context, tupleDiagnostic.expression());
		if (value instanceof final TupleValue tuple) {
			return tuple;
		}
		throw new IllegalArgumentException("diagnostic expression did not evaluate to a Tuple"); //$NON-NLS-1$
	}

	private Optional<OCLDiagnostic> evaluateLegacyDiagnostic(final EObject context,
			final OCLConstraintDefinition definition) {
		if (ocl.check(context, definition.constraint())) {
			return Optional.empty();
		}
		return Optional.of(new OCLDiagnostic(definition.legacyDiagnostic().getMessage(),
				definition.legacyDiagnostic().getSeverity(), context));
	}

	private static String getMessage(final TupleValue tuple, final OCLConstraintDefinition definition) {
		return definition.tupleDiagnostic().getOptionalField(tuple, MESSAGE_FIELD, String.class,
				definition.legacyDiagnostic().getMessage());
	}

	private static OptionalInt getMarkerSeverity(final TupleValue tuple,
			final OCLTupleDiagnostic tupleDiagnostic) {
		final Number number = tupleDiagnostic.getOptionalField(tuple, SEVERITY_FIELD, Number.class, null);
		if (number == null) {
			return OptionalInt.of(IMarker.SEVERITY_ERROR);
		}
		final double severity = number.doubleValue();
		if (severity < 0) {
			return OptionalInt.of(IMarker.SEVERITY_ERROR);
		}
		return severity > 0 ? OptionalInt.of(IMarker.SEVERITY_WARNING) : OptionalInt.empty();
	}

	private static EObject getMarkerTarget(final TupleValue tuple,
			final OCLTupleDiagnostic tupleDiagnostic, final EObject context) {
		final Object value = tupleDiagnostic.getOptionalValue(tuple, MARKER_TARGET_FIELD);
		return switch (value) {
		case null -> context;
		case final EObject target -> target;
		case final String keyword -> getMarkerTarget(keyword, context);
		default -> throw new IllegalArgumentException(
				"field '" + MARKER_TARGET_FIELD + "' must contain an EObject or a supported keyword"); //$NON-NLS-1$ //$NON-NLS-2$
		};
	}

	private static EObject getMarkerTarget(final String keyword, final EObject context) {
		if (CONTEXT_TARGET.equals(keyword)) {
			return context;
		}
		throw new IllegalArgumentException("Unsupported marker target: " + keyword); //$NON-NLS-1$
	}

	private void reportInvalidConstraint(final OCLConstraintDefinition definition, final RuntimeException exception) {
		if (invalidConstraints.add(definition)) {
			errorHandler.accept(new ConstraintError(definition, createErrorMessage(definition, exception), exception));
		}
	}

	private static String createErrorMessage(final OCLConstraintDefinition definition,
			final RuntimeException exception) {
		return "Invalid OCL diagnostic for constraint '" + definition.constraint().getName() //$NON-NLS-1$
				+ "': " + exception.getMessage(); //$NON-NLS-1$
	}

	public record ConstraintError(OCLConstraintDefinition definition, String message, RuntimeException cause) {
		// empty record body
	}
}