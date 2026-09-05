/*******************************************************************************
 * Copyright (c) 2026 Franz Höpfinger
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Franz Höpfinger - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.export;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.helpers.BlockInstanceFactory;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.resource.FordiacTypeResourceFactory;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.test.model.typelibrary.DataTypeEntryMock;
import org.eclipse.fordiac.ide.test.model.typelibrary.FBTypeEntryMock;
import org.junit.jupiter.api.Test;

/**
 * Reproduces https://github.com/eclipse-4diac/4diac-ide/issues/2737: a
 * regular (non-adapter) FB instance's struct-typed input variable, given a
 * struct literal value, is exported with an empty right-hand side instead of
 * the actual value.
 */
@SuppressWarnings("nls")
class CompositeFBStructValueExportTest extends ExporterTestBase<CompositeFBType> {

	private static final String STRUCT_TYPE_NAME = "TestScrollStruct";
	private static final String LEAF_FB_NAME = "TestLeafFB";
	private static final String LEAF_INSTANCE_NAME = "LeafInstance";
	private static final String STRUCT_VAR_NAME = "InputEvent";
	private static final String STRUCT_LITERAL_VALUE = "(code := 255, bCyclic := TRUE)";

	@Override
	void setupFunctionBlock() {
		final TypeLibrary typeLib = TypeLibraryManager.INSTANCE.getTypeLibrary(null);

		final StructuredType structType = DataFactory.eINSTANCE.createStructuredType();
		structType.setName(STRUCT_TYPE_NAME);
		structType.getMemberVariables().add(createStructMember("code", ElementaryTypes.DINT));
		structType.getMemberVariables().add(createStructMember("bCyclic", ElementaryTypes.BOOL));
		typeLib.addTypeEntry(new DataTypeEntryMock(structType, typeLib, null));
		FordiacTypeResourceFactory.INSTANCE
				.createResource(URI.createFileURI(STRUCT_TYPE_NAME + TypeLibraryTags.DATA_TYPE_FILE_ENDING_WITH_DOT))
				.getContents().add(structType);

		final SimpleFBType leafType = LibraryElementFactory.eINSTANCE.createSimpleFBType();
		leafType.setName(LEAF_FB_NAME);
		leafType.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		leafType.getInterfaceList().getInputVars().add(createStructMember(STRUCT_VAR_NAME, structType));
		final FBTypeEntry leafEntry = new FBTypeEntryMock(leafType, typeLib, null);
		typeLib.addTypeEntry(leafEntry);
		FordiacTypeResourceFactory.INSTANCE
				.createResource(URI.createFileURI(LEAF_FB_NAME + TypeLibraryTags.FB_TYPE_FILE_ENDING_WITH_DOT))
				.getContents().add(leafType);

		final FB leafInstance = BlockInstanceFactory.createFBInstanceForTypeEntry(leafEntry);
		leafInstance.setName(LEAF_INSTANCE_NAME);
		leafInstance.setInterface(leafEntry.getInterface().instanceCopy());
		leafInstance.setTypeEntry(leafEntry);
		leafInstance.getInterface().getInputVars().stream().filter(v -> STRUCT_VAR_NAME.equals(v.getName()))
				.findFirst().orElseThrow().setValue(createValue(STRUCT_LITERAL_VALUE));

		functionBlock = LibraryElementFactory.eINSTANCE.createCompositeFBType();
		functionBlock.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		functionBlock.setName(COMPOSITEFUNCTIONBLOCK_NAME);
		final FBNetwork fbNetwork = LibraryElementFactory.eINSTANCE.createFBNetwork();
		fbNetwork.getNetworkElements().add(leafInstance);
		functionBlock.setFBNetwork(fbNetwork);
		functionBlock.setTypeEntry(prepareTypeEntryWithTypeLib());
	}

	@Test
	void structLiteralValueOnPlainFbInstanceIsExported() {
		final List<FileObject> files = generateFunctionBlock(functionBlock);
		final FileObject implFile = files.stream().filter(f -> f.getName().endsWith(".cpp")).findFirst()
				.orElseThrow(() -> new AssertionError("No .cpp file was generated"));
		final String content = implFile.getData().toString();
		final String expectedAssignment = "fb_" + LEAF_INSTANCE_NAME + "->var_" + STRUCT_VAR_NAME + " = ";
		final int assignmentIndex = content.indexOf(expectedAssignment);
		assertTrue(assignmentIndex >= 0, () -> "Expected to find \"" + expectedAssignment + "\" in:\n" + content);
		final int lineEnd = content.indexOf(';', assignmentIndex);
		final String rightHandSide = content.substring(assignmentIndex + expectedAssignment.length(), lineEnd).strip();
		assertTrue(!rightHandSide.isEmpty(),
				() -> "Expected a non-empty initializer for " + STRUCT_VAR_NAME + ", but the assignment was empty");
	}

	private static VarDeclaration createStructMember(final String name, final org.eclipse.fordiac.ide.model.data.DataType type) {
		final VarDeclaration member = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		member.setName(name);
		member.setType(type);
		return member;
	}

	private static Value createValue(final String value) {
		final Value result = LibraryElementFactory.eINSTANCE.createValue();
		result.setValue(value);
		return result;
	}
}
