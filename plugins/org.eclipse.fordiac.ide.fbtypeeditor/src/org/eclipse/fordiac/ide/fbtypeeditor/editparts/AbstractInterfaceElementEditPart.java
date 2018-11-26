/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl;
import org.eclipse.fordiac.ide.model.libraryElement.impl.EventImpl;
import org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl;

abstract class AbstractInterfaceElementEditPart extends AbstractDirectEditableEditPart {
	public abstract IInterfaceElement getCastedModel();	
	protected abstract void update();

	private final EContentAdapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			refresh();
			update();
		}
	};

	@Override
	public void activate() {
		super.activate();
		getCastedModel().eAdapters().add(adapter);
	}

	@Override
	public void deactivate() {
		super.deactivate();
		getCastedModel().eAdapters().remove(adapter);
	}
	
	public boolean isInput() {
		return getCastedModel().isIsInput();
	}

	public boolean isEvent() {
		return getCastedModel() instanceof EventImpl;
	}

	public boolean isVariable() {
		return getCastedModel() instanceof VarDeclarationImpl;
	}
	
	public boolean isAdapter() {
		return getCastedModel() instanceof AdapterDeclarationImpl;
	}
}
