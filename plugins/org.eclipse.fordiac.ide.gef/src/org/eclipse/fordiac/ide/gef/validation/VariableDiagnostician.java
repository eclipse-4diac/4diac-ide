/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.fordiac.ide.model.data.AnyType;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;

public class VariableDiagnostician extends CancelableDiagnostician {

	public VariableDiagnostician(final IProgressMonitor progressMonitor) {
		super(progressMonitor);
	}

	@Override
	protected boolean doValidate(final EValidator eValidator, final EClass eClass, final EObject eObject,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		boolean result = super.doValidate(eValidator, eClass, eObject, diagnostics, context);
		if (eObject instanceof final VarDeclaration varDeclaration && varDeclaration.getType() instanceof AnyType) {
			result &= validate(varDeclaration, VariableOperations::validateType, diagnostics,
					varDeclaration.getArraySize());
			result &= validate(varDeclaration, VariableOperations::validateValue, diagnostics,
					varDeclaration.getValue());
		} else if (eObject instanceof final Attribute attribute && attribute.getType() instanceof AnyType
				&& !InternalAttributeDeclarations.isInternalAttribue(attribute.getAttributeDeclaration())) {
			result &= validate(attribute, VariableOperations::validateValue, diagnostics, attribute,
					LibraryElementPackage.Literals.ATTRIBUTE__VALUE);
		}
		return result;
	}

	protected static <T> boolean validate(final T element, final VariableValidator<? super T> validator,
			final DiagnosticChain diagnostics, final EObject context) {
		return validate(element, validator, diagnostics, context, null);
	}

	protected static <T> boolean validate(final T element, final VariableValidator<? super T> validator,
			final DiagnosticChain diagnostics, final EObject context, final EStructuralFeature feature) {
		final List<String> errors = new ArrayList<>();
		final List<String> warnings = new ArrayList<>();
		final List<String> infos = new ArrayList<>();
		final boolean result = validator.validate(element, errors, warnings, infos);
		if (diagnostics != null) {
			createDiagnostics(Diagnostic.ERROR, errors, context, feature, diagnostics);
			createDiagnostics(Diagnostic.WARNING, warnings, context, feature, diagnostics);
			createDiagnostics(Diagnostic.INFO, infos, context, feature, diagnostics);
		}
		return result;
	}

	protected static void createDiagnostics(final int severity, final List<String> messages, final EObject object,
			final EStructuralFeature feature, final DiagnosticChain diagnostics) {
		messages.stream().map(message -> createDiagnostic(severity, message, object, feature))
				.forEachOrdered(diagnostics::add);
	}

	protected static Diagnostic createDiagnostic(final int severity, final String message, final EObject object,
			final EStructuralFeature feature) {
		return new BasicDiagnostic(severity, LibraryElementValidator.DIAGNOSTIC_SOURCE, 0, message,
				FordiacMarkerHelper.getDiagnosticData(object, feature));
	}

	@FunctionalInterface
	public interface VariableValidator<T> {
		boolean validate(T element, List<String> errors, List<String> warnings, List<String> infos);
	}
}
