/********************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Austria
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Antonio Garmendia,Bianca Wiesmayr
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/

package org.eclipse.fordiac.ide.model.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;

public class FunctionBlockFactory implements Factory {

	@Override
	public Resource createResource(final URI uri) {
		return new FunctionBlockResource(uri);		
	}
	
}

