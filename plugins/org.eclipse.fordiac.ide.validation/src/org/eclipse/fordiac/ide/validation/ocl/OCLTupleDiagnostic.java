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

import java.util.Objects;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.pivot.ExpressionInOCL;
import org.eclipse.ocl.pivot.LetExp;
import org.eclipse.ocl.pivot.OCLExpression;
import org.eclipse.ocl.pivot.PropertyCallExp;
import org.eclipse.ocl.pivot.TupleLiteralExp;
import org.eclipse.ocl.pivot.ids.IdResolver;
import org.eclipse.ocl.pivot.ids.TuplePartId;
import org.eclipse.ocl.pivot.values.TupleValue;
import org.eclipse.ocl.pivot.values.Value;

public record OCLTupleDiagnostic(ExpressionInOCL expression, IdResolver idResolver) {

	private static final String STATUS_FIELD = "status"; //$NON-NLS-1$

	public OCLTupleDiagnostic {
		Objects.requireNonNull(expression);
		Objects.requireNonNull(idResolver);
	}

	public static OCLTupleDiagnostic from(final ExpressionInOCL specification, final IdResolver idResolver) {
		final ExpressionInOCL diagnosticExpression = EcoreUtil.copy(specification);
		final PropertyCallExp statusAccess = findTupleStatusAccess(diagnosticExpression.getOwnedBody());
		if (statusAccess == null) {
			return null;
		}

		final TupleLiteralExp tuple = (TupleLiteralExp) statusAccess.getOwnedSource();
		statusAccess.setOwnedSource(null);
		EcoreUtil.replace(statusAccess, tuple);
		return new OCLTupleDiagnostic(diagnosticExpression, idResolver);
	}

	private static PropertyCallExp findTupleStatusAccess(final OCLExpression body) {
		OCLExpression expression = body;
		while (expression instanceof final LetExp letExpression) {
			expression = letExpression.getOwnedIn();
		}
		if (expression instanceof final PropertyCallExp propertyCall
				&& propertyCall.getReferredProperty() != null
				&& STATUS_FIELD.equals(propertyCall.getReferredProperty().getName())
				&& propertyCall.getOwnedSource() instanceof TupleLiteralExp) {
			return propertyCall;
		}
		return null;
	}

	public <T> T getRequiredField(final TupleValue tuple, final String field, final Class<T> fieldType) {
		final TuplePartId part = tuple.getTypeId().getPartId(field);
		if (part == null) {
			throw new IllegalArgumentException("missing field '" + field + "'"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return getField(tuple, part, field, fieldType);
	}

	public <T> T getOptionalField(final TupleValue tuple, final String field, final Class<T> fieldType,
			final T defaultValue) {
		final TuplePartId part = tuple.getTypeId().getPartId(field);
		return part != null ? getField(tuple, part, field, fieldType) : defaultValue;
	}

	public Object getOptionalValue(final TupleValue tuple, final String field) {
		final TuplePartId part = tuple.getTypeId().getPartId(field);
		return part != null ? unbox(tuple.getValue(part)) : null;
	}

	private <T> T getField(final TupleValue tuple, final TuplePartId part, final String field,
			final Class<T> fieldType) {
		final Object value = unbox(tuple.getValue(part));
		if (fieldType.isInstance(value)) {
			return fieldType.cast(value);
		}
		throw new IllegalArgumentException(
				"field '" + field + "' must be " + fieldType.getSimpleName()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private Object unbox(final Object value) {
		return value instanceof final Value pivotValue ? pivotValue.asUnboxedObject(idResolver) : value;
	}
}
