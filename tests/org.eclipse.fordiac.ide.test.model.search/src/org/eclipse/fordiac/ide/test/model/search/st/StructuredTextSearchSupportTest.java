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
package org.eclipse.fordiac.ide.test.model.search.st;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstantsStandaloneSetup;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.resource.FordiacTypeResource;
import org.eclipse.fordiac.ide.model.search.IModelMatcher;
import org.eclipse.fordiac.ide.model.search.ISearchFactory;
import org.eclipse.fordiac.ide.model.search.Match;
import org.eclipse.fordiac.ide.model.search.TextMatch;
import org.eclipse.fordiac.ide.model.search.st.StructuredTextSearchFactory;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmStandaloneSetup;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunctionStandaloneSetup;
import org.eclipse.fordiac.ide.test.model.typelibrary.DataTypeEntryMock;
import org.eclipse.fordiac.ide.test.model.typelibrary.FBTypeEntryMock;
import org.junit.jupiter.api.BeforeAll;

@SuppressWarnings("nls")
abstract class StructuredTextSearchSupportTest {

	protected static TypeLibrary typeLib;

	@BeforeAll
	@SuppressWarnings("unused")
	static void setup() {
		typeLib = TypeLibraryManager.INSTANCE.getTypeLibrary(null);
		GlobalConstantsStandaloneSetup.doSetup();
		STFunctionStandaloneSetup.doSetup();
		STAlgorithmStandaloneSetup.doSetup();
		StructuredTextSearchFactory.register();
	}

	protected static <T extends EObject> void assertNoMatch(final T source, final IModelMatcher matcher) {
		final List<Match> matches = assertDoesNotThrow(() -> ISearchFactory
				.createSearchSupport(source, source.eClass().getInstanceClass()).search(matcher).toList());
		assertTrue(matches.isEmpty(), "expected no match");
	}

	protected static <T extends EObject> void assertMatch(final T source, final IModelMatcher matcher, final int line,
			final int offset, final int length) {
		final List<Match> matches = assertDoesNotThrow(() -> ISearchFactory
				.createSearchSupport(source, source.eClass().getInstanceClass()).search(matcher).toList());
		assertEquals(1, matches.size(), "expected exactly one match");
		assertTrue(matches.getFirst() instanceof TextMatch, "expected a TextMatch");
		final TextMatch match = (TextMatch) matches.getFirst();
		assertEquals(line, match.getLine(), "line");
		assertEquals(offset, match.getOffset(), "offset");
		assertEquals(length, match.getLength(), "length");
	}

	protected static VarDeclaration createVarDeclaration(final String name, final DataType type) {
		final var result = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		result.setName(name);
		result.setType(type);
		return result;
	}

	protected static VarDeclaration createVarDeclaration(final String name, final DataType type, final boolean input) {
		final var result = createVarDeclaration(name, type);
		result.setIsInput(input);
		return result;
	}

	protected static VarDeclaration createVarDeclaration(final String name, final DataType type, final boolean input,
			final String initialValue) {
		final var result = createVarDeclaration(name, type, input);
		final Value value = LibraryElementFactory.eINSTANCE.createValue();
		value.setValue(initialValue);
		result.setValue(value);
		return result;
	}

	protected static Attribute createAttribute(final String name, final DataType type, final String value) {
		final var result = LibraryElementFactory.eINSTANCE.createAttribute();
		result.setName(name);
		result.setType(type);
		result.setValue(value);
		return result;
	}

	protected static DirectlyDerivedType createDirectlyDerivedType(final String name, final DataType type,
			final String defaultValue) {
		final var result = DataFactory.eINSTANCE.createDirectlyDerivedType();
		result.setName(name);
		result.setBaseType(type);
		result.setInitialValue(defaultValue);
		typeLib.addTypeEntry(new DataTypeEntryMock(result, typeLib, null));
		new FordiacTypeResource(URI.createFileURI(name + TypeLibraryTags.DATA_TYPE_FILE_ENDING_WITH_DOT)).getContents()
				.add(result);
		return result;
	}

	protected static SimpleFBType createSimpleFBType(final String name) {
		final var result = LibraryElementFactory.eINSTANCE.createSimpleFBType();
		result.setName(name);
		result.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		typeLib.addTypeEntry(new FBTypeEntryMock(result, typeLib, null));
		new FordiacTypeResource(URI.createFileURI(name + TypeLibraryTags.FB_TYPE_FILE_ENDING_WITH_DOT)).getContents()
				.add(result);
		return result;
	}

	protected static STAlgorithm createAlgorithm(final String name, final String text) {
		final var result = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		result.setName(name);
		result.setText(text);
		return result;
	}

	protected static STMethod createMethod(final String name, final String text) {
		final var result = LibraryElementFactory.eINSTANCE.createSTMethod();
		result.setName(name);
		result.setText(text);
		return result;
	}

	protected static FunctionFBType createFunctionFBType(final String name) {
		final var result = LibraryElementFactory.eINSTANCE.createFunctionFBType();
		result.setName(name);
		result.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		result.setBody(LibraryElementFactory.eINSTANCE.createSTFunctionBody());
		typeLib.addTypeEntry(new FBTypeEntryMock(result, typeLib, null));
		new FordiacTypeResource(URI.createFileURI(name + TypeLibraryTags.FC_TYPE_FILE_ENDING_WITH_DOT)).getContents()
				.add(result);
		return result;
	}

	protected static FunctionFBType createFunctionFBType(final String name, final String text) {
		final var result = createFunctionFBType(name);
		((STFunctionBody) result.getBody()).setText(text);
		return result;
	}

	protected static BasicFBType createBasicFBType(final String name) {
		final var result = LibraryElementFactory.eINSTANCE.createBasicFBType();
		result.setName(name);
		result.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		result.setECC(LibraryElementFactory.eINSTANCE.createECC());
		typeLib.addTypeEntry(new FBTypeEntryMock(result, typeLib, null));
		new FordiacTypeResource(URI.createFileURI(name + TypeLibraryTags.FB_TYPE_FILE_ENDING_WITH_DOT)).getContents()
				.add(result);
		return result;
	}

	protected static ECTransition createECTransition(final String conditionExpression) {
		final var result = LibraryElementFactory.eINSTANCE.createECTransition();
		result.setConditionExpression(conditionExpression);
		return result;
	}
}
