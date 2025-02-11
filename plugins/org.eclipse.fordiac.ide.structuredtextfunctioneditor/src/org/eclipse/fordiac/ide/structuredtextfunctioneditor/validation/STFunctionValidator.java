/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *   Martin Jobst
 *       - add validators for function FB types
 *       - add control-flow validator
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.validation;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreControlFlowValidator;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreImportValidator;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreTypeUsageCollector;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.Messages;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.resource.STFunctionResource;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.xtext.validation.Check;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class STFunctionValidator extends AbstractSTFunctionValidator {

	@Inject
	private Provider<STCoreTypeUsageCollector> typeUsageCollectorProvider;

	@Inject
	private STCoreImportValidator importValidator;

	public static final String ISSUE_CODE_PREFIX = "org.eclipse.fordiac.ide.structuredtextfunction."; //$NON-NLS-1$
	public static final String FUNCTION_NAME_MISMATCH = ISSUE_CODE_PREFIX + "functionNameMismatch"; //$NON-NLS-1$
	public static final String MULTIPLE_FUNCTIONS = ISSUE_CODE_PREFIX + "multipleFunctions"; //$NON-NLS-1$

	@Check
	public void checkPackageDeclaration(final STFunctionSource source) {
		checkPackageDeclaration(source, STFunctionPackage.Literals.ST_FUNCTION_SOURCE__NAME, source.getName());
	}

	@Check
	public void checkImports(final STFunctionSource source) {
		if (!source.getImports().isEmpty()) {
			importValidator.validateImports(source.getName(), source.getImports(),
					typeUsageCollectorProvider.get().collectUsedTypes(source), this);
		}
	}

	@Check
	public void checkControlFlow(final STFunction function) {
		final STCoreControlFlowValidator controlFlowValidator = new STCoreControlFlowValidator(this);
		controlFlowValidator.validateVariableBlocks(function.getVarDeclarations());
		controlFlowValidator.validateStatements(function.getCode());
	}

	@Check
	public void checkFirstFunctionNameMatchesTypeName(final STFunction function) {
		if (function.eResource() instanceof final STFunctionResource resource
				&& resource.getInternalLibraryElement() instanceof final FunctionFBType fbType
				&& function.eContainer() instanceof final STFunctionSource source
				&& source.getFunctions().indexOf(function) == 0
				&& !Objects.equal(fbType.getName(), function.getName())) {
			error(MessageFormat.format(Messages.STFunctionValidator_FunctionNameMismatch, function.getName(),
					fbType.getName()), LibraryElementPackage.Literals.INAMED_ELEMENT__NAME, FUNCTION_NAME_MISMATCH,
					function.getName(), fbType.getName());

		}
	}

	@Check
	public void checkMultipleFunctions(final STFunction function) {
		if (function.eResource() instanceof final STFunctionResource resource
				&& resource.getInternalLibraryElement() instanceof final FunctionFBType fbType
				&& function.eContainer() instanceof final STFunctionSource source
				&& source.getFunctions().indexOf(function) != 0) {
			error(MessageFormat.format(Messages.STFunctionValidator_MultipleFunctions, fbType.getName()),
					LibraryElementPackage.Literals.INAMED_ELEMENT__NAME, MULTIPLE_FUNCTIONS);
		}
	}
}
