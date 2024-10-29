/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles.AnnotationFeedbackBorder;
import org.eclipse.fordiac.ide.gef.annotation.ProblemAnnotationStyler;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;

public class ErrorMarkerFBNeworkElementFigure extends FBNetworkElementFigure {

	public ErrorMarkerFBNeworkElementFigure(final FBNetworkElement model) {
		super(model);
		getFbFigureContainer()
				.setBorder(new AnnotationFeedbackBorder(ProblemAnnotationStyler.getErrorAnnotationColor()));
	}

	public void setErrorMessage(final String text) {
		changeTypeLabelText(text);
	}

}
