/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STFunction;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartition;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartitioner;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.util.STFunctionPartition;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;

@SuppressWarnings("nls")
@ExtendWith(InjectionExtension.class)
@InjectWith(STFunctionInjectorProvider.class)
class STFunctionPartitionerTest {

	protected static TypeLibrary typeLib;

	@Inject
	private ParseHelper<STFunctionSource> parseHelper;

	@Inject
	private STCorePartitioner partitioner;

	@BeforeAll
	static void setup() {
		typeLib = TypeLibraryManager.INSTANCE.getTypeLibrary(null);
	}

	@Test
	void testCombineNull() {
		assertEquals("", partitioner.combine(null));
	}

	@Test
	void testCombineBody() {
		final FunctionFBType fbType = createFunctionFBType();
		final String text = """
				FUNCTION TEST
				// content
				END_FUNCTION
				""";
		fbType.setBody(createSTFunctionBody(text));
		assertEquals(text, partitioner.combine(fbType));
	}

	@Test
	void testCombineNoBody() {
		final FunctionFBType fbType = createFunctionFBType();
		// empty
		assertEquals("""
				FUNCTION TEST
				END_FUNCTION

				""", partitioner.combine(fbType));
		// with input
		fbType.getInterfaceList().getInputVars()
				.add(createVarDeclaration("DI1", ElementaryTypes.INT, null, "17", true));
		assertEquals("""
				FUNCTION TEST
				VAR_INPUT
				    DI1 : INT := 17;
				END_VAR
				END_FUNCTION

				""", partitioner.combine(fbType));
		// with output
		fbType.getInterfaceList().getOutputVars()
				.add(createVarDeclaration("DO1", ElementaryTypes.INT, "0..1", null, false));
		assertEquals("""
				FUNCTION TEST
				VAR_INPUT
				    DI1 : INT := 17;
				END_VAR
				VAR_OUTPUT
				    DO1 : ARRAY [0..1] OF INT;
				END_VAR
				END_FUNCTION

				""", partitioner.combine(fbType));
		// with in/out
		fbType.getInterfaceList().getInOutVars()
				.add(createVarDeclaration("DIO1", ElementaryTypes.INT, null, null, true));
		assertEquals("""
				FUNCTION TEST
				VAR_INPUT
				    DI1 : INT := 17;
				END_VAR
				VAR_IN_OUT
				    DIO1 : INT;
				END_VAR
				VAR_OUTPUT
				    DO1 : ARRAY [0..1] OF INT;
				END_VAR
				END_FUNCTION

				""", partitioner.combine(fbType));
		// with return
		fbType.getInterfaceList().getOutputVars().add(createVarDeclaration("", ElementaryTypes.INT, null, null, false));
		assertEquals("""
				FUNCTION TEST : INT
				VAR_INPUT
				    DI1 : INT := 17;
				END_VAR
				VAR_IN_OUT
				    DIO1 : INT;
				END_VAR
				VAR_OUTPUT
				    DO1 : ARRAY [0..1] OF INT;
				END_VAR
				END_FUNCTION

				""", partitioner.combine(fbType));
		// with package
		fbType.getCompilerInfo().setPackageName("testpkg::test1");
		assertEquals("""
				PACKAGE testpkg::test1;

				FUNCTION TEST : INT
				VAR_INPUT
				    DI1 : INT := 17;
				END_VAR
				VAR_IN_OUT
				    DIO1 : INT;
				END_VAR
				VAR_OUTPUT
				    DO1 : ARRAY [0..1] OF INT;
				END_VAR
				END_FUNCTION

				""", partitioner.combine(fbType));
		// with imports
		fbType.getCompilerInfo().getImports().add(createImport("testpkg::test2"));
		fbType.getCompilerInfo().getImports().add(createImport("testpkg::test3"));
		assertEquals("""
				PACKAGE testpkg::test1;

				IMPORT testpkg::test2;
				IMPORT testpkg::test3;

				FUNCTION TEST : INT
				VAR_INPUT
				    DI1 : INT := 17;
				END_VAR
				VAR_IN_OUT
				    DIO1 : INT;
				END_VAR
				VAR_OUTPUT
				    DO1 : ARRAY [0..1] OF INT;
				END_VAR
				END_FUNCTION

				""", partitioner.combine(fbType));
	}

	@Test
	void testPartition() throws Exception {
		assertFunctionsEquals(List.of(), partition(""));
		assertFunctionsEquals(List.of("error"), partition("error"));
		final String function = """
				FUNCTION TEST
				END_FUNCTION
				""";
		assertFunctionsEquals(List.of(function), partition(function));
		final String function2 = """
				FUNCTION TEST2
				END_FUNCTION
				""";
		assertFunctionsEquals(List.of(function.trim(), '\n' + function2), partition(function + function2));
	}

	private static FunctionFBType createFunctionFBType() {
		final FunctionFBType fbType = LibraryElementFactory.eINSTANCE.createFunctionFBType();
		fbType.setName("TEST"); //$NON-NLS-1$
		fbType.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		fbType.setCompilerInfo(LibraryElementFactory.eINSTANCE.createCompilerInfo());
		return fbType;
	}

	private static STFunctionBody createSTFunctionBody(final String text) {
		final STFunctionBody body = LibraryElementFactory.eINSTANCE.createSTFunctionBody();
		body.setText(text);
		return body;
	}

	private static VarDeclaration createVarDeclaration(final String name, final DataType type, final String arraySize,
			final String value, final boolean input) {
		final VarDeclaration variable = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		variable.setName(name);
		variable.setType(type);
		ArraySizeHelper.setArraySize(variable, arraySize);
		if (value != null) {
			variable.setValue(createValue(value));
		}
		variable.setIsInput(input);
		return variable;
	}

	private static Value createValue(final String value) {
		final Value result = LibraryElementFactory.eINSTANCE.createValue();
		result.setValue(value);
		return result;
	}

	private static Import createImport(final String importedNamespace) {
		final Import result = LibraryElementFactory.eINSTANCE.createImport();
		result.setImportedNamespace(importedNamespace);
		return result;
	}

	private STFunctionPartition partition(final String text) throws Exception {
		final Optional<STCorePartition> partition = partitioner
				.partition((XtextResource) parseHelper.parse(text).eResource());
		assertTrue(partition.isPresent());
		assertTrue(partition.get() instanceof STFunctionPartition);
		assertEquals(text, partition.get().getOriginalSource());
		assertEquals(text, ((STFunctionPartition) partition.get()).getFunctions().stream().map(STFunction::getText)
				.collect(Collectors.joining()));
		return (STFunctionPartition) partition.get();
	}

	private static void assertFunctionsEquals(final List<String> expected, final STFunctionPartition actual) {
		assertIterableEquals(expected, actual.getFunctions().stream().map(STFunction::getText).toList());
	}
}
