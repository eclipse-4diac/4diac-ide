/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

class CommentTypeEditPart extends AbstractGraphicalEditPart implements
EditPart {
	
	/**
	 * The Class CommentTypeContainerFigure for handling the layout of one comment and type
	 * label of an fb interface.
	 */
	private static class CommentTypeContainerFigure extends Figure {

		/**
		 * Instantiates a new variable output container figure.
		 */
		public CommentTypeContainerFigure() {
			GridLayout layout = new GridLayout(3, false);
			layout.horizontalSpacing = 0;
			layout.verticalSpacing = 0;
			layout.marginWidth = 0;
			layout.marginHeight = 0; 
			setLayoutManager(layout);
		}
	}

	@Override
	public CommentTypeField getModel() {
		return (CommentTypeField) super.getModel();
	}

	@Override
	protected IFigure createFigure() {
		return new CommentTypeContainerFigure();
	}

	@Override
	protected void createEditPolicies() {
		// we currently don't have any editpolices for this edit part
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected List getModelChildren() {
		return getModel().getChildren();
	}
}
