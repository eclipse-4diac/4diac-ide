/*******************************************************************************
 * Copyright (c) 2026 Sichuan Qunyuan Technology Co., Ltd.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Zijun Tang - initial API and implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.deployment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.ForteTypeNameCreator;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableMoveFB;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.test.model.typelibrary.FBTypeEntryMock;
import org.junit.jupiter.api.Test;

class ForteTypeNameCreatorTest {

	private static final String FORTE_TYPE_OVERRIDE_ATTRIBUTE = "eclipse4diac::core::ForteTypeOverride"; //$NON-NLS-1$
	private static final String GENERIC_CLASS_NAME_ATTRIBUTE = "eclipse4diac::core::GenericClassName"; //$NON-NLS-1$

	private final ForteTypeNameCreator creator = new ForteTypeNameCreator();

	@Test
	void withoutOverrideUsesConcreteTypeName() throws DeploymentException {
		final BasicFBType type = createFbType("PUBLISH_1", "iec61499::net"); //$NON-NLS-1$ //$NON-NLS-2$

		assertEquals("iec61499::net::PUBLISH_1", creator.getTypeName(new FBTypeEntryMock(type, null, null))); //$NON-NLS-1$
	}

	@Test
	void genericClassNameDoesNotOverrideDeployTypeName() throws DeploymentException {
		final BasicFBType type = createFbType("PUBLISH_1", "iec61499::net"); //$NON-NLS-1$ //$NON-NLS-2$
		addAttribute(type, GENERIC_CLASS_NAME_ATTRIBUTE, "'GEN_PUBLISH'"); //$NON-NLS-1$

		assertEquals("iec61499::net::PUBLISH_1", creator.getTypeName(new FBTypeEntryMock(type, null, null))); //$NON-NLS-1$
	}

	@Test
	void forteTypeOverrideOverridesDeployTypeName() throws DeploymentException {
		final BasicFBType type = createFbType("EL1008", "eclipse4diac::io::ethercat"); //$NON-NLS-1$ //$NON-NLS-2$
		addAttribute(type, FORTE_TYPE_OVERRIDE_ATTRIBUTE, "'eclipse4diac::io::ethercat::ECDevice_2_3'"); //$NON-NLS-1$

		assertEquals("eclipse4diac::io::ethercat::ECDevice_2_3", //$NON-NLS-1$
				creator.getTypeName(new FBTypeEntryMock(type, null, null)));
	}

	@Test
	void unqualifiedForteTypeOverrideOverridesDeployTypeName() throws DeploymentException {
		final BasicFBType type = createFbType("EL1008", "eclipse4diac::io::ethercat"); //$NON-NLS-1$ //$NON-NLS-2$
		addAttribute(type, FORTE_TYPE_OVERRIDE_ATTRIBUTE, "'ECDevice'"); //$NON-NLS-1$

		assertEquals("ECDevice", creator.getTypeName(new FBTypeEntryMock(type, null, null))); //$NON-NLS-1$
	}

	@Test
	void forteTypeOverrideOverridesFbNetworkElementDeployTypeName() throws DeploymentException {
		final BasicFBType type = createFbType("EL1008", "eclipse4diac::io::ethercat"); //$NON-NLS-1$ //$NON-NLS-2$
		addAttribute(type, FORTE_TYPE_OVERRIDE_ATTRIBUTE, "'eclipse4diac::io::ethercat::ECDevice_2_3'"); //$NON-NLS-1$
		final FB fb = createFbInstance("device1", type); //$NON-NLS-1$

		assertEquals("eclipse4diac::io::ethercat::ECDevice_2_3", creator.getTypeName(fb)); //$NON-NLS-1$
	}

	@Test
	void configurableFbIgnoresForteTypeOverride() throws DeploymentException {
		final BasicFBType type = createFbType("STRUCT_MUX", "eclipse4diac::utils"); //$NON-NLS-1$ //$NON-NLS-2$
		addAttribute(type, FORTE_TYPE_OVERRIDE_ATTRIBUTE, "'eclipse4diac::io::ethercat::ShouldNotApply'"); //$NON-NLS-1$
		final ConfigurableMoveFB fb = createConfigurableFbInstance("mux1", type); //$NON-NLS-1$
		final StructuredType dataType = DataFactory.eINSTANCE.createStructuredType();
		dataType.setName("MyStruct"); //$NON-NLS-1$
		PackageNameHelper.setPackageName(dataType, "test::pkg"); //$NON-NLS-1$
		fb.setDataType(dataType);

		assertEquals("eclipse4diac::utils::STRUCT_MUX_1test::pkg::MyStruct", creator.getTypeName(fb)); //$NON-NLS-1$
	}

	@Test
	void emptyForteTypeOverrideKeepsConcreteTypeName() throws DeploymentException {
		final BasicFBType type = createFbType("EL1008", "eclipse4diac::io::ethercat"); //$NON-NLS-1$ //$NON-NLS-2$
		addAttribute(type, FORTE_TYPE_OVERRIDE_ATTRIBUTE, "''"); //$NON-NLS-1$

		assertEquals("eclipse4diac::io::ethercat::EL1008", //$NON-NLS-1$
				creator.getTypeName(new FBTypeEntryMock(type, null, null)));
	}

	@Test
	void invalidForteTypeOverrideThrowsDeploymentException() {
		final BasicFBType type = createFbType("EL1008", "eclipse4diac::io::ethercat"); //$NON-NLS-1$ //$NON-NLS-2$
		addAttribute(type, FORTE_TYPE_OVERRIDE_ATTRIBUTE, "not-a-string-literal"); //$NON-NLS-1$

		assertThrows(DeploymentException.class,
				() -> creator.getTypeName(new FBTypeEntryMock(type, null, null)));
	}

	@Test
	void invalidIdentifierForteTypeOverrideThrowsDeploymentException() {
		final BasicFBType type = createFbType("EL1008", "eclipse4diac::io::ethercat"); //$NON-NLS-1$ //$NON-NLS-2$
		addAttribute(type, FORTE_TYPE_OVERRIDE_ATTRIBUTE, "'1Invalid'"); //$NON-NLS-1$

		assertThrows(DeploymentException.class,
				() -> creator.getTypeName(new FBTypeEntryMock(type, null, null)));
	}

	private static BasicFBType createFbType(final String name, final String packageName) {
		final BasicFBType type = LibraryElementFactory.eINSTANCE.createBasicFBType();
		type.setName(name);
		PackageNameHelper.setPackageName(type, packageName);
		return type;
	}

	private static FB createFbInstance(final String name, final BasicFBType type) {
		final FBTypeEntryMock typeEntry = new FBTypeEntryMock(type, null, null);
		type.setTypeEntry(typeEntry);
		final FB fb = LibraryElementFactory.eINSTANCE.createFB();
		fb.setName(name);
		fb.setTypeEntry(typeEntry);
		return fb;
	}

	private static ConfigurableMoveFB createConfigurableFbInstance(final String name, final BasicFBType type) {
		final FBTypeEntryMock typeEntry = new FBTypeEntryMock(type, null, null);
		type.setTypeEntry(typeEntry);
		final ConfigurableMoveFB fb = LibraryElementFactory.eINSTANCE.createConfigurableMoveFB();
		fb.setName(name);
		fb.setTypeEntry(typeEntry);
		return fb;
	}

	private static void addAttribute(final BasicFBType type, final String name, final String value) {
		final Attribute attribute = LibraryElementFactory.eINSTANCE.createAttribute();
		attribute.setName(name);
		attribute.setValue(value);
		type.getAttributes().add(attribute);
	}
}
