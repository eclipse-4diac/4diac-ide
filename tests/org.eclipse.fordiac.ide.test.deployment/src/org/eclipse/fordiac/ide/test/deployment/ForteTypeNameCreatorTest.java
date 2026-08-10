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

import org.eclipse.fordiac.ide.deployment.interactors.ForteTypeNameCreator;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.test.model.typelibrary.FBTypeEntryMock;
import org.junit.jupiter.api.Test;

class ForteTypeNameCreatorTest {

	private final ForteTypeNameCreator creator = new ForteTypeNameCreator();

	@Test
	void shortGenericClassNameDoesNotOverrideConcreteTypeName() {
		final BasicFBType type = createFbType("PUBLISH_1", "iec61499::net"); //$NON-NLS-1$ //$NON-NLS-2$
		addAttribute(type, TypeLibraryTags.GENERIC_CLASS_NAME_ATTRIBUTE_FULL_NAME, "'GEN_PUBLISH'"); //$NON-NLS-1$

		assertEquals("iec61499::net::PUBLISH_1", creator.getTypeName(new FBTypeEntryMock(type, null, null))); //$NON-NLS-1$
	}

	@Test
	void packageQualifiedGenericClassNameOverridesDeployTypeName() {
		final BasicFBType type = createFbType("EL1008", "eclipse4diac::io::ethercat"); //$NON-NLS-1$ //$NON-NLS-2$
		addAttribute(type, TypeLibraryTags.GENERIC_CLASS_NAME_ATTRIBUTE_FULL_NAME,
				"'eclipse4diac::io::ethercat::ECDevice_2_3'"); //$NON-NLS-1$

		assertEquals("eclipse4diac::io::ethercat::ECDevice_2_3", //$NON-NLS-1$
				creator.getTypeName(new FBTypeEntryMock(type, null, null)));
	}

	private static BasicFBType createFbType(final String name, final String packageName) {
		final BasicFBType type = LibraryElementFactory.eINSTANCE.createBasicFBType();
		type.setName(name);
		PackageNameHelper.setPackageName(type, packageName);
		return type;
	}

	private static void addAttribute(final BasicFBType type, final String name, final String value) {
		final Attribute attribute = LibraryElementFactory.eINSTANCE.createAttribute();
		attribute.setName(name);
		attribute.setValue(value);
		type.getAttributes().add(attribute);
	}
}
