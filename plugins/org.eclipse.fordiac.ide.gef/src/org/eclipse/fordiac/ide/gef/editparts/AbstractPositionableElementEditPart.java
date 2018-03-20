/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.gef.GraphicalEditPart;

public abstract class AbstractPositionableElementEditPart extends AbstractViewEditPart {
	
	private EContentAdapter contentAdapter = new EContentAdapter(){
		@Override 
		public void notifyChanged(Notification notification) { 
			Object feature = notification.getFeature();
			if (LibraryElementPackage.eINSTANCE.getPositionableElement_X().equals(feature) ||
					LibraryElementPackage.eINSTANCE.getPositionableElement_Y().equals(feature)) {
				refreshPosition();
			}
		}

	};
	
	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshPosition();
	}
	
	protected void refreshPosition() {
		Rectangle bounds =  new Rectangle(getPositionableElement().getX(), getPositionableElement().getY(), -1, -1);
		if (getParent() != null) {
			((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
		}	
	}

	protected abstract PositionableElement getPositionableElement();
	
	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			PositionableElement posElement = getPositionableElement();
			if(null != posElement){
				posElement.eAdapters().add(contentAdapter);
			}
		}
	}
	
	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			PositionableElement posElement = getPositionableElement();
			if(null != posElement){
				posElement.eAdapters().remove(contentAdapter);
			}
		}
	}

}
