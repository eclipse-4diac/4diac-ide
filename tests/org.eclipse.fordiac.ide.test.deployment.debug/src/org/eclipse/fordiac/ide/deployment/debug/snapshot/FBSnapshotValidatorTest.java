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

import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.FB_NAME;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.addToResource;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.createEdgeFBType;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.createFB;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.createFunctionFBType;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.createSimpleFBType;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.createToggleFBType;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.createVariable;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.withTypeEntry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.fordiac.ide.deployment.debug.Messages;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.junit.jupiter.api.Test;

class FBSnapshotValidatorTest {

	private static final LibraryElementFactory FACTORY = LibraryElementFactory.eINSTANCE;

	@Test
	void internalSimpleAndBasicFBsAreSupported() {
		final SimpleFBType innerType = createEdgeFBType();
		innerType.getInternalFbs().add(createFB("toggle", createToggleFBType())); //$NON-NLS-1$
		final SimpleFBType type = createSimpleFBType();
		type.getInternalFbs().add(createFB("edge", innerType)); //$NON-NLS-1$

		assertTrue(FBSnapshotValidator.validate(createFBInResource(type)).isOK());
	}

	@Test
	void basicFBIsSupported() {
		assertTrue(FBSnapshotValidator.validate(createFBInResource(createToggleFBType())).isOK());
	}

	@Test
	void functionWithoutBodyIsRejected() {
		assertError(createFunctionFBType(), MessageFormat.format(Messages.FBSnapshotValidator_UnsupportedBody, FB_NAME));
	}

	@Test
	void internalServiceInterfaceFBNeedsARuntime() {
		final SimpleFBType type = createSimpleFBType();
		final FB delay = createFB("delay", withTypeEntry(FACTORY.createServiceInterfaceFBType(), "E_DELAY")); //$NON-NLS-1$ //$NON-NLS-2$
		type.getInternalFbs().add(delay);

		final IStatus status = FBSnapshotValidator.validate(createFBInResource(type));

		assertEquals(IStatus.WARNING, status.getSeverity());
		assertEquals(MessageFormat.format(Messages.FBSnapshotValidator_RequiresRuntime, "delay (E_DELAY)"), //$NON-NLS-1$
				status.getMessage());
		assertEquals(List.of(delay), FBSnapshotValidator.getRuntimeFBs(type));
	}

	@Test
	void nestedServiceInterfaceFBAndFunctionWithoutBodyNeedARuntime() {
		final SimpleFBType innerType = createEdgeFBType();
		final FB delay = createFB("delay", withTypeEntry(FACTORY.createServiceInterfaceFBType(), "E_DELAY")); //$NON-NLS-1$ //$NON-NLS-2$
		innerType.getInternalFbs().add(delay);
		final SimpleFBType type = createSimpleFBType();
		type.getInternalFbs().add(createFB("edge", innerType)); //$NON-NLS-1$
		final FB function = createFB("double", createFunctionFBType()); //$NON-NLS-1$
		type.getInternalFbs().add(function);

		assertEquals(IStatus.WARNING, FBSnapshotValidator.validate(createFBInResource(type)).getSeverity());
		assertEquals(List.of(delay, function), FBSnapshotValidator.getRuntimeFBs(type));
	}

	@Test
	void internalCompositeFBIsRejected() {
		final SimpleFBType type = createSimpleFBType();
		type.getInternalFbs().add(createFB("composite", //$NON-NLS-1$
				withTypeEntry(FACTORY.createCompositeFBType(), "SD_Composite"))); //$NON-NLS-1$

		assertError(type, MessageFormat.format(Messages.FBSnapshotValidator_UnsupportedInternalFB,
				FB_NAME + ".composite", "SD_Composite")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	void internalFBWithNativeAlgorithmIsRejected() {
		// like FB_RANDOM: a basic FB whose algorithm is native code, which no evaluator
		// can execute
		final BasicFBType randomType = createToggleFBType();
		final OtherAlgorithm algorithm = FACTORY.createOtherAlgorithm();
		algorithm.setName("REQ"); //$NON-NLS-1$
		algorithm.setLanguage("AnyText"); //$NON-NLS-1$
		algorithm.setText("VAL() = (TForteFloat) (rand() % 32767);"); //$NON-NLS-1$
		randomType.getSourceElements().add(algorithm);
		final SimpleFBType type = createSimpleFBType();
		type.getInternalFbs().add(createFB("random", randomType)); //$NON-NLS-1$

		assertError(type, MessageFormat.format(Messages.FBSnapshotValidator_UnsupportedAlgorithm,
				FB_NAME + ".random", "REQ")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	void variableLengthInOutArrayIsRejected() {
		final SimpleFBType type = createSimpleFBType();
		final VarDeclaration values = createVariable("VALUES", ElementaryTypes.INT, true); //$NON-NLS-1$
		type.getInterfaceList().getInOutVars().add(values);
		ArraySizeHelper.setArraySize(values, "*"); //$NON-NLS-1$

		assertError(type, MessageFormat.format(Messages.FBSnapshotValidator_VariableLengthArray, FB_NAME, "VALUES")); //$NON-NLS-1$
	}

	private static void assertError(final FBType type, final String expectedMessage) {
		final IStatus status = FBSnapshotValidator.validate(createFBInResource(type));
		assertEquals(IStatus.ERROR, status.getSeverity());
		assertEquals(expectedMessage, status.getMessage());
	}

	private static FB createFBInResource(final FBType type) {
		final FB fb = createFB(FB_NAME, type);
		addToResource(fb);
		return fb;
	}
}
