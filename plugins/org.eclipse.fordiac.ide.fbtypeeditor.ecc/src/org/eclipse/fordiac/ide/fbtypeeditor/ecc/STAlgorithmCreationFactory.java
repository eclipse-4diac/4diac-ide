/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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