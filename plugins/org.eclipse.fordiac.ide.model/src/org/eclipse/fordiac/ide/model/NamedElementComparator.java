/********************************************************************************
 * Copyright (c) 2016 - 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model;

import java.text.Collator;
import java.util.Comparator;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public enum NamedElementComparator implements Comparator<INamedElement> {
	
	INSTANCE;

	private Collator col = Collator.getInstance();
	
	@Override
	public int compare(INamedElement o1, INamedElement o2) {
		return col.compare(o1.getName(), o2.getName());
	}
}