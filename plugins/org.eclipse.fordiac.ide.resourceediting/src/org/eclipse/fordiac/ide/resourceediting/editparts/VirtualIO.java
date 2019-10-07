/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.resourceediting.editparts;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public class VirtualIO implements Notifier{
	
	private final IInterfaceElement referencedInterfaceElement;

	public IInterfaceElement getReferencedInterfaceElement() {
		return referencedInterfaceElement;
	}

	public VirtualIO(IInterfaceElement referencedInterfaceElement) {
		super();
		this.referencedInterfaceElement = referencedInterfaceElement;
	}

	@Override
	public EList<Adapter> eAdapters() {
		return referencedInterfaceElement.eAdapters();
	}

	@Override
	public boolean eDeliver() {
		return referencedInterfaceElement.eDeliver();
	}

	@Override
	public void eSetDeliver(boolean deliver) {
		referencedInterfaceElement.eSetDeliver(deliver);
		
	}

	@Override
	public void eNotify(Notification notification) {
		referencedInterfaceElement.eNotify(notification);		
	}

}
