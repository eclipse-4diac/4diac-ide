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

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.ecore.LetExp;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.PropertyCallExp;
import org.eclipse.ocl.ecore.TupleLiteralExp;
import org.eclipse.ocl.util.Tuple;
import org.eclipse.ocl.utilities.ExpressionInOCL;

public record OCLTupleDiagnostic(ExpressionInOCL<EClassifier, EParameter> expression) {

	private static final String STATUS_FIELD = "status"; //$NON-NLS-1$

	public OCLTupleDiagnostic {
		Objects.requireNonNull(expression);
	}

	public static OCLTupleDiagnostic from(final ExpressionInOCL<EClassifier, EParameter> specification) {
		final ExpressionInOCL<EClassifier, EParameter> diagnosticExpression = EcoreUtil.copy(specification);
		final PropertyCallExp statusAccess = findTupleStatusAccess(
				(OCLExpression) diagnosticExpression.getBodyExpression());
		if (statusAccess == null) {
			return null;
		}

		final TupleLiteralExp tuple = (TupleLiteralExp) statusAccess.getSource();
		statusAccess.setSource(null);
		EcoreUtil.replace(statusAccess, tuple);
		return new OCLTupleDiagnostic(diagnosticExpression);
	}

	private static PropertyCallExp findTupleStatusAccess(final OCLExpression body) {
		OCLExpression expression = body;
		while (expression instanceof final LetExp letExpression) {
			expression = (OCLExpression) letExpression.getIn();
		}
		if (expression instanceof final PropertyCallExp propertyCall
				&& propertyCall.getReferredProperty() != null
				&& STATUS_FIELD.equals(propertyCall.getReferredProperty().getName())
				&& propertyCall.getSource() instanceof TupleLiteralExp) {
			return propertyCall;
		}
		return null;
	}

	public <T> T getRequiredField(final Tuple<?, ?> tuple, final String field, final Class<T> fieldType) {
		return getField(tuple, field, fieldType);
	}

	public <T> T getOptionalField(final Tuple<?, ?> tuple, final String field, final Class<T> fieldType,
			final T defaultValue) {
		final Object value = tuple.getValue(field);
		return value != null ? getField(value, field, fieldType) : defaultValue;
	}

	public Object getOptionalValue(final Tuple<?, ?> tuple, final String field) {
		return tuple.getValue(field);
	}

	private static <T> T getField(final Tuple<?, ?> tuple, final String field, final Class<T> fieldType) {
		return getField(tuple.getValue(field), field, fieldType);
	}

	private static <T> T getField(final Object value, final String field, final Class<T> fieldType) {
		if (fieldType.isInstance(value)) {
			return fieldType.cast(value);
		}
		throw new IllegalArgumentException(
				"field '" + field + "' must be " + fieldType.getSimpleName()); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
