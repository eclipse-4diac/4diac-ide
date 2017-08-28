/*******************************************************************************
 * Copyright (c) 2008, 2009, 2013, 2014, 2016 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.provider;

import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.jface.viewers.IStructuredContentProvider;

public class CompilerContentProvider implements IStructuredContentProvider {
	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof FBType) {
			return ((FBType)inputElement).getCompilerInfo().getCompiler().toArray();
		}
		return new Object[] {};
	}
}
