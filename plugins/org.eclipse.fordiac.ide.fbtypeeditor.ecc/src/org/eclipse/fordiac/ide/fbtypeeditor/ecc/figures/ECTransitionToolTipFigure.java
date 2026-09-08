/*******************************************************************************
 * Copyright (c) 2020, 2025 Johannes Kepler University Linz,
 *                          Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Ernst Blecha
 *     - initial implementation
 *   Virendra Ashiwal
 *   	- extracted common code from ECTransitionToolTipFigure to ECCToolTip
 *   Alexander Lumplecer
 *     - adjusted Label
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.ui.annotation.GraphicalAnnotationModel;

public class ECTransitionToolTipFigure extends ECCToolTip {

	private final Figure annotationContainer = new Figure();

	public ECTransitionToolTipFigure() {
		add(annotationContainer);
		annotationContainer.setLayoutManager(new ToolbarLayout());
		setConstraint(annotationContainer, new GridData(PositionConstants.LEFT, PositionConstants.MIDDLE, true, true));
	}

	public void setECTransition(final ECTransition transition) {
		final ECState des = transition.getDestination();
		final String desName = (des == null) ? null : des.getName();

		setLabel(transition.getSource().getName() + " -> " + desName, transition.getComment()); //$NON-NLS-1$
	}

	public void refreshAnotations(final ECTransition transition, final GraphicalAnnotationModel annotationModel) {
		annotationContainer.removeAll();
		if (transition != null && annotationModel != null) {
			annotationModel.getAnnotations(transition).forEach(annotation -> annotationContainer
					.add(new Label(annotation.getText(), GraphicalAnnotationStyles.getAnnotationImage(annotation))));
		}
	}

}
