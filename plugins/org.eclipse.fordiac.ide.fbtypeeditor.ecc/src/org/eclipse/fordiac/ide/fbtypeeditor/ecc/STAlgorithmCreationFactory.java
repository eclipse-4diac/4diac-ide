/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.gef.requests.CreationFactory;

public class STAlgorithmCreationFactory implements CreationFactory {

	@Override
	public Object getNewObject() {
		return LibraryElementFactory.eINSTANCE.createSTAlgorithm();
	}

	@Override
	public Object getObjectType() {
		return STAlgorithm.class;
	}
}