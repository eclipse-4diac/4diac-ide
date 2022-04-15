/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.metrics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.fordiac.ide.metrics.analyzers.CognitiveComplexity;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.structuredtext.StructuredTextStandaloneSetup;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.testmocks.FBTypeEntryMock;
import org.eclipse.fordiac.ide.model.xtext.fbt.FBTypeStandaloneSetup;
import org.junit.jupiter.api.BeforeAll;

// Actual test-cases (multiline-strings) are implemented using xtend in CognitiveComplexityTest.xtend
@SuppressWarnings({ "squid:S1694", "squid:S1118" })
abstract class AbstractCognitiveComplexityTest {

	protected static final String FUNCTIONBLOCK_NAME = "functionblock"; //$NON-NLS-1$
	protected static final String ALGORITHM_NAME = "algorithm"; //$NON-NLS-1$

	@BeforeAll
	/** initialize the Equinox extension registry substitute */
	public static void doSetup() {
		FBTypeStandaloneSetup.doSetup();
		StructuredTextStandaloneSetup.doSetup();
	}

	protected static void verifyCognitiveComplexity(final String algorithm, final double expectedComplexity) {
		final var alg = createSTAlgorithm(ALGORITHM_NAME, algorithm);
		assertEquals(expectedComplexity, CognitiveComplexity.analyzeAlgorithm(alg));
	}

	private static FBTypeEntry preparePaletteWithTypeLib() {
		return new FBTypeEntryMock(null, TypeLibraryManager.INSTANCE.getTypeLibrary(null), null);
	}

	private static STAlgorithm createSTAlgorithm(final String algorithmName, final String algorithmText) {

		// prepare a function block object including an interface list
		final BasicFBType functionBlock = LibraryElementFactory.eINSTANCE.createBasicFBType();
		functionBlock.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		functionBlock.setName(FUNCTIONBLOCK_NAME);

		functionBlock.setECC(LibraryElementFactory.eINSTANCE.createECC());

		functionBlock.setTypeEntry(preparePaletteWithTypeLib());

		final STAlgorithm stAlg = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		stAlg.setName(algorithmName);
		stAlg.setText(algorithmText);

		functionBlock.getCallables().add(stAlg);

		return stAlg;
	}

}
