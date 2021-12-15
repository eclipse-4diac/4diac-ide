/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - test for forte_ng
 *******************************************************************************/

package org.eclipse.fordiac.ide.test.export;

import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class ExporterTestBasicFBTypeBase extends ExporterTestBase<BasicFBType> {

	@Override
	void setupFunctionBlock() {
		// prepare a function block object including an interface list
		functionBlock = LibraryElementFactory.eINSTANCE.createBasicFBType();
		functionBlock.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		functionBlock.setName(BASICFUNCTIONBLOCK_NAME);

		functionBlock.setECC(LibraryElementFactory.eINSTANCE.createECC());

		preparePaletteWithTypeLib().setType(functionBlock);
	}

}
