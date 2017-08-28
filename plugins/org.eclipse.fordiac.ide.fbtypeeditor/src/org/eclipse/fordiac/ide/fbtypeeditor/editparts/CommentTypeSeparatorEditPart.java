/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

class CommentTypeSeparatorEditPart extends AbstractGraphicalEditPart implements
EditPart{
	
	@Override
	public CommentTypeField.CommentTypeSeparator getModel() {
		return (CommentTypeField.CommentTypeSeparator) super.getModel();
	}
	
	@Override
	protected IFigure createFigure() {
		return new Label(getModel().getLabel());
	}

	@Override
	protected void createEditPolicies() {
			// no editpart necessary
	}
	
}
