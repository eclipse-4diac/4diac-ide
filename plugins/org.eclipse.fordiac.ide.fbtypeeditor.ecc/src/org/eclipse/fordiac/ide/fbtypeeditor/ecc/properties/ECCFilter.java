/*******************************************************************************
 * Copyright (c) 2015 - 2016 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECCRootEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.jface.viewers.IFilter;

public class ECCFilter implements IFilter {

	@Override
	public boolean select(Object toTest) {
		if(toTest instanceof ECCRootEditPart){
			return true;
		}
		if(toTest instanceof ECC){
			return true;
		}
		return false;
	}

}
