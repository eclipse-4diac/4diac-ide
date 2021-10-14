/*******************************************************************************
 * Copyright (c) 2009 Profactor GbmH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.views;

import org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSCollection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class VirtualDNSCollectionProvider implements ITreeContentProvider {

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof VirtualDNSCollection) {
			return ((VirtualDNSCollection) parentElement).getVirtualDNSEntries().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(final Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		return false;
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public void dispose() {
		// currently nothing to be done here
	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		// currently nothing to be done here
	}

}
