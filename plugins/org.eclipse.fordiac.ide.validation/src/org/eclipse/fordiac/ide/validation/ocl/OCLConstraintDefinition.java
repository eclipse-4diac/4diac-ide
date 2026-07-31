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

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.fordiac.ide.validation.handlers.ConstraintHelper;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.expressions.Variable;
import org.eclipse.ocl.utilities.ExpressionInOCL;

public record OCLConstraintDefinition(Constraint constraint, EClass contextClass,
		OCLTupleDiagnostic tupleDiagnostic, ConstraintHelper legacyDiagnostic, IFile sourceFile) {

	public static OCLConstraintDefinition from(final Constraint constraint, final IFile sourceFile) {
		final EClass contextClass = getContextClass(constraint);
		final ExpressionInOCL<EClassifier, EParameter> specification = constraint.getSpecification();
		return new OCLConstraintDefinition(constraint, contextClass,
				OCLTupleDiagnostic.from(specification),
				createLegacyDiagnostic(constraint), sourceFile);
	}

	public boolean appliesTo(final EClass objectClass) {
		return contextClass.isSuperTypeOf(objectClass);
	}

	public boolean hasTupleDiagnostic() {
		return tupleDiagnostic != null;
	}

	private static EClass getContextClass(final Constraint constraint) {
		final Variable<EClassifier, EParameter> contextVariable = constraint.getSpecification().getContextVariable();
		if (contextVariable != null && contextVariable.getType() instanceof final EClass contextClass) {
			return contextClass;
		}
		throw new IllegalArgumentException("Constraint has no EClass context: " + constraint.getName()); //$NON-NLS-1$
	}

	private static ConstraintHelper createLegacyDiagnostic(final Constraint constraint) {
		final String name = constraint.getName();
		return new ConstraintHelper(name != null ? name : "OCL constraint"); //$NON-NLS-1$
	}
}
