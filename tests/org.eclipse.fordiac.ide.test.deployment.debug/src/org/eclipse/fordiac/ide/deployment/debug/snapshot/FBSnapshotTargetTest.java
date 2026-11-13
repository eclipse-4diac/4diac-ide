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
package org.eclipse.fordiac.ide.deployment.debug.snapshot;

import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.APPLICATION_NAME;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.FB_NAME;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.addToApplication;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.addToResource;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.createFB;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.createSimpleFBType;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.map;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.withTypeEntry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.deployment.debug.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.junit.jupiter.api.Test;

class FBSnapshotTargetTest {

	private static final LibraryElementFactory FACTORY = LibraryElementFactory.eINSTANCE;
	private static final String CELL = "Cell"; //$NON-NLS-1$

	@Test
	void simpleFBInResourceIsSupported() throws CoreException {
		final SimpleFBType type = createSimpleFBType();
		final FB fb = createFB(FB_NAME, type);
		final Resource resource = addToResource(fb);

		final FBSnapshotTarget target = FBSnapshotTarget.of(fb);

		assertSame(fb, target.instance());
		assertSame(resource, target.resource());
		assertSame(type, target.type());
		assertEquals(FB_NAME, target.resourceRelativeName());
	}

	@Test
	void mappedFBUsesItsApplicationPath() throws CoreException {
		final SimpleFBType type = createSimpleFBType();
		final FB applicationFB = createFB(FB_NAME, type);
		addToApplication(applicationFB);
		final FB resourceFB = createFB(FB_NAME, type);
		final Resource resource = addToResource(resourceFB);
		map(applicationFB, resourceFB);

		for (final FB selected : List.of(applicationFB, resourceFB)) {
			final FBSnapshotTarget target = FBSnapshotTarget.of(selected);

			assertSame(applicationFB, target.instance());
			assertSame(resource, target.resource());
			assertEquals(APPLICATION_NAME + "." + FB_NAME, target.resourceRelativeName()); //$NON-NLS-1$
		}
	}

	@Test
	void mappedFBKeepsItsApplicationPathIfItEqualsTheResourcePath() throws CoreException {
		final SimpleFBType type = createSimpleFBType();
		final FB applicationFB = createFB(FB_NAME, type);
		final Application application = addToApplication(applicationFB);
		final FB resourceFB = createFB(FB_NAME, type);
		final Resource resource = addToResource(resourceFB);
		map(applicationFB, resourceFB);
		// application and device Cell, function block and resource Counter
		application.setName(CELL);
		resource.setName(FB_NAME);
		resource.getDevice().setName(CELL);
		final SystemConfiguration systemConfiguration = FACTORY.createSystemConfiguration();
		application.getAutomationSystem().setSystemConfiguration(systemConfiguration);
		systemConfiguration.getDevices().add(resource.getDevice());
		assertEquals(applicationFB.getQualifiedName(), resource.getQualifiedName());

		for (final FB selected : List.of(applicationFB, resourceFB)) {
			assertEquals(CELL + "." + FB_NAME, FBSnapshotTarget.of(selected).resourceRelativeName()); //$NON-NLS-1$
		}
	}

	@Test
	void unmappedFBIsRejected() {
		final FB fb = createFB(FB_NAME, createSimpleFBType());
		addToApplication(fb);

		assertRejected(fb, MessageFormat.format(Messages.FBSnapshotValidator_NotMapped, FB_NAME));
	}

	@Test
	void serviceInterfaceFBIsRejected() {
		final FB fb = createFB(FB_NAME, withTypeEntry(FACTORY.createServiceInterfaceFBType(), "E_DELAY")); //$NON-NLS-1$
		addToResource(fb);

		assertRejected(fb, MessageFormat.format(Messages.FBSnapshotValidator_UnsupportedType, FB_NAME, "E_DELAY")); //$NON-NLS-1$
	}

	@Test
	void adapterIsRejected() {
		final SimpleFBType type = createSimpleFBType();
		final AdapterDeclaration socket = FACTORY.createAdapterDeclaration();
		socket.setName("ADP"); //$NON-NLS-1$
		type.getInterfaceList().getSockets().add(socket);
		final FB fb = createFB(FB_NAME, type);
		addToResource(fb);

		assertRejected(fb, MessageFormat.format(Messages.FBSnapshotValidator_Adapters, FB_NAME));
	}

	@Test
	void typeWithoutInputEventsIsRejected() {
		final SimpleFBType type = createSimpleFBType();
		type.getInterfaceList().getEventInputs().clear();
		final FB fb = createFB(FB_NAME, type);
		addToResource(fb);

		assertRejected(fb, MessageFormat.format(Messages.FBSnapshotValidator_NoInputEvents, FB_NAME));
	}

	@Test
	void otherElementsAreRejected() {
		assertRejected("not a function block", Messages.FBSnapshotValidator_NotAFunctionBlock); //$NON-NLS-1$
		assertRejected(null, Messages.FBSnapshotValidator_NotAFunctionBlock);
	}

	private static void assertRejected(final Object element, final String expectedMessage) {
		final CoreException exception = assertThrows(CoreException.class, () -> FBSnapshotTarget.of(element));
		assertEquals(expectedMessage, exception.getStatus().getMessage());
	}
}
