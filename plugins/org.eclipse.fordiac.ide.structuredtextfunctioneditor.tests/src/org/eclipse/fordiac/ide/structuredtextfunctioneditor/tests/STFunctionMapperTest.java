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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.tests;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreMapper;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;

@SuppressWarnings("nls")
@ExtendWith(InjectionExtension.class)
@InjectWith(STFunctionInjectorProvider.class)
class STFunctionMapperTest {

	@Inject
	private ParseHelper<STFunctionSource> parseHelper;
	@Inject
	private STCoreMapper mapper;

	@Test
	void mapsInterfaceDeclarationsInTheOwningFunction() throws Exception {
		final var source = parseHelper.parse("""
				FUNCTION Helper
				VAR_INPUT
				    InputValue : INT;
				END_VAR
				END_FUNCTION
				FUNCTION FunctionUnderTest : INT
				VAR_INPUT
				    InputValue : INT := 1;
				END_VAR
				VAR_OUTPUT
				    OutputValue : INT := 2;
				END_VAR
				VAR_IN_OUT
				    StateValue : INT;
				END_VAR
				END_FUNCTION
				""");
		final var type = createFunctionType("FunctionUnderTest");
		final var interfaceList = type.getInterfaceList();
		final var input = createVariable("InputValue", true);
		final var output = createVariable("OutputValue", false);
		final var inOut = createVariable("StateValue", true);
		final var returnValue = createVariable("", false);
		interfaceList.getInputVars().add(input);
		interfaceList.getOutputVars().add(returnValue);
		interfaceList.getOutputVars().add(output);
		interfaceList.getInOutVars().add(inOut);

		final var function = source.getFunctions().get(1);
		final var resource = source.eResource();
		assertSame(function.getInputParameters().getFirst(), mapper.fromModel(resource, input));
		assertSame(function.getOutputParameters().getFirst(), mapper.fromModel(resource, output));
		assertSame(function.getInOutParameters().getFirst(), mapper.fromModel(resource, inOut));
		assertSame(function.getInOutParameters().getFirst(), mapper.fromModel(resource, inOut.getInOutVarOpposite()));
		assertSame(function, mapper.fromModel(resource, returnValue));
		final var sourceInput = function.getInputParameters().getFirst();
		assertSame(sourceInput, mapper.fromModel(resource, sourceInput));
	}

	@Test
	void mapsOnlyIntoThePrimaryFunction() throws Exception {
		final var source = parseHelper.parse("""
				FUNCTION Helper
				VAR_INPUT
				    InputValue : INT;
				END_VAR
				END_FUNCTION
				FUNCTION FunctionUnderTest
				VAR_OUTPUT
				    InputValue : INT;
				END_VAR
				END_FUNCTION
				""");
		final var type = createFunctionType("FunctionUnderTest");
		final var input = createVariable("InputValue", true);
		type.getInterfaceList().getInputVars().add(input);
		// the function named like the type has no input of that name
		assertNull(mapper.fromModel(source.eResource(), input));
		// without a function named like the type, STFunctionReconciler takes the first one
		type.setName("RenamedFunction");
		assertSame(source.getFunctions().getFirst().getInputParameters().getFirst(),
				mapper.fromModel(source.eResource(), input));
	}

	private static FunctionFBType createFunctionType(final String name) {
		final var factory = LibraryElementFactory.eINSTANCE;
		final var type = factory.createFunctionFBType();
		type.setName(name);
		type.setInterfaceList(factory.createInterfaceList());
		return type;
	}

	private static VarDeclaration createVariable(final String name, final boolean input) {
		final var variable = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		variable.setName(name);
		variable.setIsInput(input);
		return variable;
	}
}
