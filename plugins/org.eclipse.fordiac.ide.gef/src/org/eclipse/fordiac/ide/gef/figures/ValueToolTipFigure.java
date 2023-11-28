/*******************************************************************************
 * Copyright (c) 2012, 2014, 2017 Profactor GbmH, fortiss GmbH
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
package org.eclipse.fordiac.ide.gef.figures;

import org.eclipse.draw2d.Label;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.ui.FordiacMessages;

/**
 * The Class ToolTipFigure.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class ValueToolTipFigure extends ToolTipFigure {

	public ValueToolTipFigure(final IInterfaceElement element, final Value value,
			final GraphicalAnnotationModel annotationModel) {
		super(element, null); // pass null here to avoid showing annotations for element
		getLine().add(new Label(FordiacMessages.Value + ": " + value.getValue())); //$NON-NLS-1$
		if (annotationModel != null) {
			annotationModel.getAnnotations(value).stream().forEach(annotation -> getLine()
					.add(new Label(annotation.getText(), GraphicalAnnotationStyles.getAnnotationImage(annotation))));
		}
	}
}
