/********************************************************************************
 * Copyright (c) 2016 - 2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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

	private final Collator col = Collator.getInstance();

	@Override
	public int compare(final INamedElement o1, final INamedElement o2) {
		if (o1 == null) {
			Activator.getDefault()
					.logError("o1 was null, o2 value: " + o2 + " name: " + ((null != o2) ? o2.getName() : "Also null")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		if (o2 == null) {
			Activator.getDefault()
					.logError("o2 was null, o1 value: " + o1 + " name: " + ((null != o1) ? o1.getName() : "Also null")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		if (col == null) {
			Activator.getDefault().logError("col2 was null"); //$NON-NLS-1$
		}
		return col.compare(o1.getName(), o2.getName());
	}
}