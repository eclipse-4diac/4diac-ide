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
package org.eclipse.fordiac.ide.structuredtextalgorithm.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmPartition;
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
@InjectWith(STAlgorithmInjectorProvider.class)
class STAlgorithmPartitionerTest {

	protected static TypeLibrary typeLib;

	@Inject
	private ParseHelper<STAlgorithmSource> parseHelper;

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
		final SimpleFBType fbType = createSimpleFBType();
		final String text = """
				ALGORITHM REQ
				END_ALGORITHM
				""";
		fbType.getCallables().add(createSTAlgorithm("REQ", text));
		assertEquals(text, partitioner.combine(fbType));
	}

	@Test
	void testCombineLegacy() {
		final SimpleFBType fbType = createSimpleFBType();
		final String text = """
				ALGORITHM REQ
				// content
				END_ALGORITHM

				""";
		fbType.getCallables().add(createSTAlgorithm("REQ", "// content"));
		assertEquals(text, partitioner.combine(fbType));
	}

	@Test
	void testCombineMethod() {
		final SimpleFBType fbType = createSimpleFBType();
		final String algorithm = """
				ALGORITHM REQ
				END_ALGORITHM
				""";
		fbType.getCallables().add(createSTAlgorithm("REQ", algorithm));
		final String method = """
				METHOD TEST
				END_METHOD
				""";
		fbType.getCallables().add(createSTMethod("TEST", method));
		assertEquals(algorithm + method, partitioner.combine(fbType));
	}

	@Test
	void testPartition() throws Exception {
		assertCallablesEquals(List.of(), partition(""));
		assertCallablesEquals(List.of("error"), partition("error"));
		final String algorithm = """
				ALGORITHM REQ
				END_ALGORITHM
				""";
		assertCallablesEquals(List.of(algorithm), partition(algorithm));
		final String method = """
				METHOD TEST
				END_METHOD
				""";
		assertCallablesEquals(List.of(algorithm.trim(), '\n' + method), partition(algorithm + method));
	}

	private static SimpleFBType createSimpleFBType() {
		final SimpleFBType fbType = LibraryElementFactory.eINSTANCE.createSimpleFBType();
		fbType.setName("TEST"); //$NON-NLS-1$
		fbType.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		fbType.setCompilerInfo(LibraryElementFactory.eINSTANCE.createCompilerInfo());
		return fbType;
	}

	private static STAlgorithm createSTAlgorithm(final String name, final String text) {
		final STAlgorithm algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		algorithm.setName(name);
		algorithm.setText(text);
		return algorithm;
	}

	private static STMethod createSTMethod(final String name, final String text) {
		final STMethod method = LibraryElementFactory.eINSTANCE.createSTMethod();
		method.setName(name);
		method.setText(text);
		return method;
	}

	private STAlgorithmPartition partition(final String text) throws Exception {
		final Optional<STCorePartition> partition = partitioner
				.partition((XtextResource) parseHelper.parse(text).eResource());
		assertTrue(partition.isPresent());
		assertTrue(partition.get() instanceof STAlgorithmPartition);
		assertEquals(text, partition.get().getOriginalSource());
		assertEquals(text, ((STAlgorithmPartition) partition.get()).getCallables().stream()
				.map(STAlgorithmPartitionerTest::getText).collect(Collectors.joining()));
		return (STAlgorithmPartition) partition.get();
	}

	private static void assertCallablesEquals(final List<String> expected, final STAlgorithmPartition actual) {
		assertIterableEquals(expected,
				actual.getCallables().stream().map(STAlgorithmPartitionerTest::getText).toList());
	}

	protected static String getText(final ICallable callable) {
		return switch (callable) {
		case final STAlgorithm algorithm -> algorithm.getText();
		case final STMethod method -> method.getText();
		default -> "";
		};
	}
}
