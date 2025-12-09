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
package org.eclipse.fordiac.ide.globalconstantseditor.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstsSource;
import org.eclipse.fordiac.ide.globalconstantseditor.util.GlobalConstantsPartition;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OriginalSource;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartition;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartitioner;
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
@InjectWith(GlobalConstantsInjectorProvider.class)
class GlobalConstantsPartitionerTest {

	protected static TypeLibrary typeLib;

	@Inject
	private ParseHelper<STGlobalConstsSource> parseHelper;

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
	void testCombine() {
		final GlobalConstants globalConstants = createGlobalConstants();
		final String text = """
				GLOBALCONSTANTS TEST
					VAR_GLOBAL CONSTANT
					// content
					END_VAR
				END_GLOBALCONSTANTS
				""";
		globalConstants.setSource(createOriginalSource(text));
		assertEquals(text, partitioner.combine(globalConstants));
	}

	@Test
	void testCombineNoSource() {
		final GlobalConstants globalConstants = createGlobalConstants();
		assertEquals("""
				GLOBALCONSTANTS TEST
				    VAR_GLOBAL CONSTANT
				    END_VAR
				END_GLOBALCONSTANTS
				""", partitioner.combine(globalConstants));
		globalConstants.getConstants().add(createVarDeclaration("PI", ElementaryTypes.REAL, null, "3.14159"));
		assertEquals("""
				GLOBALCONSTANTS TEST
				    VAR_GLOBAL CONSTANT
				        PI : REAL := 3.14159;
				    END_VAR
				END_GLOBALCONSTANTS
				""", partitioner.combine(globalConstants));
	}

	@Test
	void testPartition() throws Exception {
		assertConstantsEquals(List.of(), partition(""));
		assertConstantsEquals(List.of(), partition("error"));
		assertConstantsEquals(List.of(createVarDeclaration("PI", ElementaryTypes.REAL, null, "3.14159")), partition("""
				GLOBALCONSTANTS TEST
					VAR_GLOBAL CONSTANT
					    PI : REAL := 3.14159;
					END_VAR
				END_GLOBALCONSTANTS
				"""));
		assertConstantsEquals(List.of(createVarDeclaration("PI", ElementaryTypes.REAL, null, "3.14159"),
				createVarDeclaration("ONE", ElementaryTypes.INT, null, "1")), partition("""
						GLOBALCONSTANTS TEST
							VAR_GLOBAL CONSTANT
							    PI : REAL := 3.14159;
							    ONE : INT := 1;
							END_VAR
						END_GLOBALCONSTANTS
								"""));
	}

	private static GlobalConstants createGlobalConstants() {
		final GlobalConstants globalConstants = LibraryElementFactory.eINSTANCE.createGlobalConstants();
		globalConstants.setName("TEST"); //$NON-NLS-1$
		globalConstants.setCompilerInfo(LibraryElementFactory.eINSTANCE.createCompilerInfo());
		return globalConstants;
	}

	private static OriginalSource createOriginalSource(final String text) {
		final OriginalSource source = LibraryElementFactory.eINSTANCE.createOriginalSource();
		source.setText(text);
		return source;
	}

	private static VarDeclaration createVarDeclaration(final String name, final DataType type, final String arraySize,
			final String value) {
		final VarDeclaration variable = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		variable.setName(name);
		variable.setType(type);
		ArraySizeHelper.setArraySize(variable, arraySize);
		if (value != null) {
			variable.setValue(createValue(value));
		}
		return variable;
	}

	private static Value createValue(final String value) {
		final Value result = LibraryElementFactory.eINSTANCE.createValue();
		result.setValue(value);
		return result;
	}

	private GlobalConstantsPartition partition(final String text) throws Exception {
		final Optional<STCorePartition> partition = partitioner
				.partition((XtextResource) parseHelper.parse(text).eResource());
		assertTrue(partition.isPresent());
		assertTrue(partition.get() instanceof GlobalConstantsPartition);
		assertEquals(text, partition.get().getOriginalSource());
		return (GlobalConstantsPartition) partition.get();
	}

	private static void assertConstantsEquals(final List<VarDeclaration> expected,
			final GlobalConstantsPartition actual) {
		assertEquals(expected.size(), actual.getConstants().size(), "Constants size differs");
		assertIterableEquals(expected.stream().map(VarDeclaration::getName).toList(),
				expected.stream().map(VarDeclaration::getName).toList(), "constants names differ");
		assertIterableEquals(expected.stream().map(VarDeclaration::getType).toList(),
				expected.stream().map(VarDeclaration::getType).toList(), "constants types differ");
	}
}
