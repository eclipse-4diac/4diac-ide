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
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.fordiac.ide.validation.handlers.ConstraintHelper;
import org.eclipse.ocl.pivot.Constraint;
import org.eclipse.ocl.pivot.ExpressionInOCL;
import org.eclipse.ocl.pivot.utilities.OCL;
import org.eclipse.ocl.pivot.utilities.ParserException;

public record OCLConstraintDefinition(Constraint constraint, EClass contextClass,
		OCLTupleDiagnostic tupleDiagnostic, ConstraintHelper legacyDiagnostic, IFile sourceFile) {

	public static OCLConstraintDefinition from(final Constraint constraint, final OCL ocl,
			final IFile sourceFile) {
		final EClass contextClass = getContextClass(constraint, ocl);
		final ExpressionInOCL specification = getSpecification(constraint, ocl);
		return new OCLConstraintDefinition(constraint, contextClass,
				OCLTupleDiagnostic.from(specification, ocl.getIdResolver()),
				createLegacyDiagnostic(constraint), sourceFile);
	}

	public boolean appliesTo(final EClass objectClass) {
		return contextClass.isSuperTypeOf(objectClass);
	}

	public boolean hasTupleDiagnostic() {
		return tupleDiagnostic != null;
	}

	private static EClass getContextClass(final Constraint constraint, final OCL ocl) {
		if (constraint.getContext() instanceof final org.eclipse.ocl.pivot.Class contextClass) {
			final EClass eClass = ocl.getMetamodelManager().getEcoreOfPivot(EClass.class, contextClass);
			if (eClass != null) {
				return getRegisteredClass(eClass);
			}
		}
		throw new IllegalArgumentException("Constraint has no EClass context: " + constraint.getName()); //$NON-NLS-1$
	}

	private static EClass getRegisteredClass(final EClass eClass) {
		final EPackage ePackage = eClass.getEPackage();
		if (ePackage != null && ePackage.getNsURI() != null) {
			final EPackage registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ePackage.getNsURI());
			if (registeredPackage != null
					&& registeredPackage.getEClassifier(eClass.getName()) instanceof final EClass registeredClass) {
				return registeredClass;
			}
		}
		return eClass;
	}

	private static ExpressionInOCL getSpecification(final Constraint constraint, final OCL ocl) {
		try {
			return ocl.getSpecification(constraint);
		} catch (final ParserException e) {
			throw new IllegalArgumentException("Cannot parse constraint: " + constraint.getName(), e); //$NON-NLS-1$
		}
	}

	private static ConstraintHelper createLegacyDiagnostic(final Constraint constraint) {
		final String name = constraint.getName();
		return new ConstraintHelper(name != null ? name : "OCL constraint"); //$NON-NLS-1$
	}
}
