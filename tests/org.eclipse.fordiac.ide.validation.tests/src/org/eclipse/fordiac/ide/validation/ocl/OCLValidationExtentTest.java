/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.validation.ocl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableMoveFB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.ocl.ecore.OCL;
import org.junit.jupiter.api.Test;

class OCLValidationExtentTest {

	private static final LibraryElementFactory FACTORY = LibraryElementFactory.eINSTANCE;

	@Test
	void includesRootsUnderTheirSuperTypes() {
		final SimpleFBType first = FACTORY.createSimpleFBType();
		final SimpleFBType second = FACTORY.createSimpleFBType();
		final BasicFBType third = FACTORY.createBasicFBType();

		final Map<EClass, Set<EObject>> extent = OCLValidationSession
				.createValidationExtent(List.of(first, second, third));

		assertEquals(Set.of(first, second, third), extent.get(LibraryElementPackage.Literals.BASE_FB_TYPE));
	}

	@Test
	void includesContainedObjectsUnderTheirSuperTypes() {
		final SimpleFBType root = FACTORY.createSimpleFBType();
		final ConfigurableMoveFB configurableFB = FACTORY.createConfigurableMoveFB();
		root.getInternalFbs().add(configurableFB);

		final Map<EClass, Set<EObject>> extent = OCLValidationSession.createValidationExtent(List.of(root));

		assertTrue(extent.get(LibraryElementPackage.Literals.CONFIGURABLE_FB).contains(configurableFB));
		assertTrue(extent.get(LibraryElementPackage.Literals.FB).contains(configurableFB));
		assertTrue(extent.get(LibraryElementPackage.Literals.BLOCK_FB_NETWORK_ELEMENT).contains(configurableFB));
	}

	@Test
	void allInstancesUsesAllValidationRoots() throws CoreException, IOException {
		final BaseFBType first = FACTORY.createSimpleFBType();
		final BaseFBType second = FACTORY.createSimpleFBType();
		final BaseFBType third = FACTORY.createBasicFBType();
		try (OCLTestFile testFile = OCLTestFile.load(
				"OCLValidationExtentTest", "BaseFBTypeExtent.ocl")) { //$NON-NLS-1$ //$NON-NLS-2$
			final OCL ocl = testFile.ocl();
			OCLValidationSession.setValidationExtent(ocl, List.of(first, second, third));
			final OCLConstraintDefinition definition = testFile.parseDefinitions().get(0);

			assertTrue(ocl.check(first, definition.constraint()));
		}
	}
}
