/*******************************************************************************
 * Copyright (c) 2026 MSR Software GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 AND CC0-1.0
 *
 * AI Disclosure: This file was largely generated with Cursor and Anthropic Claude.
 * The AI-generated portions are made available under CC0-1.0.
 *
 * Contributors:
 *   Tim Grocki - initial API and implementation (deploy initial values of
 *                   FB types with ForteTypeOverride)
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.deployment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstantsStandaloneSetup;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmStandaloneSetup;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunctionStandaloneSetup;
import org.eclipse.fordiac.ide.test.model.typelibrary.DataTypeEntryMock;
import org.eclipse.fordiac.ide.test.model.typelibrary.FBTypeEntryMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DeploymentHelperTest {

	private static final String FORTE_TYPE_OVERRIDE_ATTRIBUTE = "eclipse4diac::core::ForteTypeOverride"; //$NON-NLS-1$
	private static final String FORTE_TYPE_OVERRIDE = "'eclipse4diac::io::ethercat::ECDevice_0_0'"; //$NON-NLS-1$
	private static final String INPUT_NAME = "Position"; //$NON-NLS-1$
	private static final String TYPE_INITIAL_VALUE = "5"; //$NON-NLS-1$
	private static final String STRUCT_INITIAL_VALUE = "(Position := 5, ProductCode := 72100946)"; //$NON-NLS-1$
	/** {@link #STRUCT_INITIAL_VALUE} as the deployment sends it */
	private static final String STRUCT_DEPLOYED_VALUE = "(Position:=5,ProductCode:=72100946)"; //$NON-NLS-1$

	private static IProject project;
	private static TypeLibrary typeLib;
	private static StructuredType structType;

	@SuppressWarnings("unused")
	@BeforeAll
	static void setup() {
		// values are evaluated with the structured text parser, as in
		// ResourceDeploymentTest
		new DataTypeLibrary(null);
		GlobalConstantsStandaloneSetup.doSetup();
		STFunctionStandaloneSetup.doSetup();
		STAlgorithmStandaloneSetup.doSetup();
		project = ResourcesPlugin.getWorkspace().getRoot().getProject("test"); //$NON-NLS-1$
		typeLib = TypeLibraryManager.INSTANCE.getTypeLibrary(project);
		structType = createStructType();
	}

	@Test
	void valueEqualToTypeInitialValueIsSkipped() throws DeploymentException {
		final VarDeclaration input = createInput(null, TYPE_INITIAL_VALUE);

		assertNull(DeploymentHelper.getVariableValue(input, input, false));
	}

	@Test
	void valueDifferentFromTypeInitialValueIsDeployed() throws DeploymentException {
		final VarDeclaration input = createInput(null, "7"); //$NON-NLS-1$

		assertEquals("7", DeploymentHelper.getVariableValue(input, input, false)); //$NON-NLS-1$
	}

	@Test
	void inheritedTypeInitialValueIsSkipped() throws DeploymentException {
		final VarDeclaration input = createInput(null, null);

		assertNull(DeploymentHelper.getVariableValue(input, input, false));
	}

	@Test
	void overriddenTypeDeploysValueEqualToTypeInitialValue() throws DeploymentException {
		final VarDeclaration input = createInput(FORTE_TYPE_OVERRIDE, TYPE_INITIAL_VALUE);

		assertEquals(TYPE_INITIAL_VALUE, DeploymentHelper.getVariableValue(input, input, false));
	}

	@Test
	void overriddenTypeDeploysInheritedTypeInitialValue() throws DeploymentException {
		final VarDeclaration input = createInput(FORTE_TYPE_OVERRIDE, null);

		assertEquals(TYPE_INITIAL_VALUE, DeploymentHelper.getVariableValue(input, input, false));
	}

	@Test
	void overriddenTypeWithoutInitialValueDeploysNothing() throws DeploymentException {
		final VarDeclaration input = createInput(FORTE_TYPE_OVERRIDE, null, null);

		assertNull(DeploymentHelper.getVariableValue(input, input, false));
	}

	@Test
	void structValueEqualToTypeInitialValueIsSkipped() throws DeploymentException {
		final VarDeclaration input = createInput(null, structType, STRUCT_INITIAL_VALUE, STRUCT_INITIAL_VALUE);

		assertNull(DeploymentHelper.getVariableValue(input, input, false));
	}

	@Test
	void overriddenTypeDeploysStructValueEqualToTypeInitialValue() throws DeploymentException {
		final VarDeclaration input = createInput(FORTE_TYPE_OVERRIDE, structType, STRUCT_INITIAL_VALUE,
				STRUCT_INITIAL_VALUE);

		assertEquals(STRUCT_DEPLOYED_VALUE, DeploymentHelper.getVariableValue(input, input, false));
	}

	@Test
	void overriddenTypeDeploysInheritedStructTypeInitialValue() throws DeploymentException {
		final VarDeclaration input = createInput(FORTE_TYPE_OVERRIDE, structType, STRUCT_INITIAL_VALUE, null);

		assertEquals(STRUCT_DEPLOYED_VALUE, DeploymentHelper.getVariableValue(input, input, false));
	}

	@Test
	void emptyOverrideIsIgnored() throws DeploymentException {
		final VarDeclaration input = createInput("''", TYPE_INITIAL_VALUE); //$NON-NLS-1$

		assertNull(DeploymentHelper.getVariableValue(input, input, false));
	}

	@Test
	void invalidOverrideDeploysValueEqualToTypeInitialValue() throws DeploymentException {
		final VarDeclaration input = createInput("not-a-string-literal", TYPE_INITIAL_VALUE); //$NON-NLS-1$

		assertEquals(TYPE_INITIAL_VALUE, DeploymentHelper.getVariableValue(input, input, false));
	}

	@Test
	void configurableFbIgnoresOverride() throws DeploymentException {
		final VarDeclaration input = createInput(FORTE_TYPE_OVERRIDE, true, ElementaryTypes.INT, TYPE_INITIAL_VALUE,
				TYPE_INITIAL_VALUE);

		assertNull(DeploymentHelper.getVariableValue(input, input, false));
	}

	private static VarDeclaration createInput(final String forteTypeOverride, final String instanceValue) {
		return createInput(forteTypeOverride, TYPE_INITIAL_VALUE, instanceValue);
	}

	private static VarDeclaration createInput(final String forteTypeOverride, final String typeValue,
			final String instanceValue) {
		return createInput(forteTypeOverride, ElementaryTypes.INT, typeValue, instanceValue);
	}

	private static VarDeclaration createInput(final String forteTypeOverride, final DataType dataType,
			final String typeValue, final String instanceValue) {
		return createInput(forteTypeOverride, false, dataType, typeValue, instanceValue);
	}

	/**
	 * Creates an FB instance (a configurable FB if {@code configurable}) whose type
	 * has {@code forteTypeOverride} as ForteTypeOverride attribute (none if null)
	 * and declares an input of {@code dataType} with {@code typeValue} as initial
	 * value (none if null), and returns the instance's input, set to
	 * {@code instanceValue} (none if null).
	 */
	private static VarDeclaration createInput(final String forteTypeOverride, final boolean configurable,
			final DataType dataType, final String typeValue, final String instanceValue) {
		final BasicFBType type = LibraryElementFactory.eINSTANCE.createBasicFBType();
		type.setName("EK1100"); //$NON-NLS-1$
		PackageNameHelper.setPackageName(type, "eclipse4diac::io::ethercat"); //$NON-NLS-1$
		if (forteTypeOverride != null) {
			final Attribute attribute = LibraryElementFactory.eINSTANCE.createAttribute();
			attribute.setName(FORTE_TYPE_OVERRIDE_ATTRIBUTE);
			attribute.setValue(forteTypeOverride);
			type.getAttributes().add(attribute);
		}

		final InterfaceList typeInterface = LibraryElementFactory.eINSTANCE.createInterfaceList();
		final VarDeclaration typeInput = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		typeInput.setName(INPUT_NAME);
		typeInput.setType(dataType);
		typeInput.setIsInput(true);
		typeInput.setValue(typeValue != null ? createValue(typeValue) : null);
		typeInterface.getInputVars().add(typeInput);
		type.setInterfaceList(typeInterface);

		// type and instance live in a resource of the test project, so that the
		// evaluator finds the project's type library (needed for struct values)
		final FBTypeEntryMock typeEntry = new FBTypeEntryMock(type, typeLib,
				project.getFile(type.getName() + TypeLibraryTags.FB_TYPE_FILE_ENDING_WITH_DOT));
		type.setTypeEntry(typeEntry);
		new ResourceImpl(typeEntry.getURI()).getContents().add(type);
		final FB fb = configurable ? LibraryElementFactory.eINSTANCE.createConfigurableMoveFB()
				: LibraryElementFactory.eINSTANCE.createFB();
		fb.setName("coupler"); //$NON-NLS-1$
		fb.setTypeEntry(typeEntry);
		fb.setInterface(EcoreUtil.copy(typeInterface));
		new ResourceImpl(typeEntry.getURI()).getContents().add(fb);

		final VarDeclaration input = fb.getInterface().getInputVars().getFirst();
		input.setValue(instanceValue != null ? createValue(instanceValue) : null);
		return input;
	}

	/**
	 * Creates a small struct like the {@code ECDeviceConfig} of an EtherCAT device
	 * and adds it to the type library of the test project.
	 */
	private static StructuredType createStructType() {
		final StructuredType result = DataFactory.eINSTANCE.createStructuredType();
		result.setName("DeviceConfig"); //$NON-NLS-1$
		result.getMemberVariables().add(createMember("Position", ElementaryTypes.INT)); //$NON-NLS-1$
		result.getMemberVariables().add(createMember("ProductCode", ElementaryTypes.UDINT)); //$NON-NLS-1$
		final DataTypeEntryMock typeEntry = new DataTypeEntryMock(result, typeLib,
				project.getFile(result.getName() + TypeLibraryTags.DATA_TYPE_FILE_ENDING_WITH_DOT));
		result.setTypeEntry(typeEntry);
		typeLib.addTypeEntry(typeEntry);
		new ResourceImpl(typeEntry.getURI()).getContents().add(result);
		return result;
	}

	private static VarDeclaration createMember(final String name, final DataType dataType) {
		final VarDeclaration member = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		member.setName(name);
		member.setType(dataType);
		return member;
	}

	private static Value createValue(final String value) {
		final Value result = LibraryElementFactory.eINSTANCE.createValue();
		result.setValue(value);
		return result;
	}
}
