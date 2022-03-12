/*******************************************************************************
 * Copyright (c) 2008 - 2022 Profactor GmbH, fortiss GmbH,
 *                           Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - separated FBNetworkElement from instance name for better
 *                 direct editing of instance names
 *               - extracted common FB shape for interface and fbn editors
 *               - hide middle part when expanded
 *               - show comment part on top of the subapp figure
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.figures.FBShapeShadowBorder;
import org.eclipse.fordiac.ide.gef.figures.RoundedRectangleShadowBorder;
import org.eclipse.fordiac.ide.model.edit.providers.ResultListLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;

/**
 * The Class SubAppForFbNetworkFigure.
 */
public class SubAppForFbNetworkFigure extends FBNetworkElementFigure {

	private InstanceCommentFigure commentFigure;

	public SubAppForFbNetworkFigure(final SubApp model, final SubAppForFBNetworkEditPart editPart) {
		super(model, editPart);
		updateTypeLabel(model);
		updateExpandedFigure();
	}

	public InstanceCommentFigure getCommentFigure() {
		return commentFigure;
	}

	public void updateTypeLabel(final SubApp model) {
		getTypeLabel().setText(model.isTyped() ? model.getTypeName() : ""); //$NON-NLS-1$
		if (!model.isTyped()) {
			getTypeLabel().setIcon(FordiacImage.ICON_SUB_APP.getImage());
		} else {
			getTypeLabel().setIcon(ResultListLabelProvider.getTypeImage(model.getType()));
		}
	}

	@Override
	protected SubApp getModel() {
		return (SubApp) super.getModel();
	}

	public void refreshComment() {
		if (commentFigure != null) {
			commentFigure.setText(getModel().getComment());
		}
	}

	public final void updateExpandedFigure() {
		if (getModel().isUnfolded()) {
			if (getMiddleContainer().getParent() != null) {
				getFbFigureContainer().remove(getMiddleContainer());
				setBorder(null);
				getFbFigureContainer().setBorder(new RoundedRectangleShadowBorder());
				addComment();
			}
		} else {
			if (getMiddleContainer().getParent() == null) {
				getFbFigureContainer().add(getMiddleContainer(), createMiddleLayoutData(), 1);
				getFbFigureContainer().setBorder(null);
				setBorder(new FBShapeShadowBorder());
				removeComment();
			}
		}
	}

	private void addComment() {
		commentFigure = new InstanceCommentFigure();
		getTop().add(commentFigure, createCommentLayoutData(), 0);
		commentFigure.setBorder(new MarginBorder(2, 2, 2, 2));
		refreshComment();
	}

	private static GridData createCommentLayoutData() {
		final GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		gridData.horizontalSpan = 3;
		return gridData;
	}

	private void removeComment() {
		if (commentFigure != null) {
			commentFigure.getParent().remove(commentFigure);
			commentFigure = null;
		}
	}
}
