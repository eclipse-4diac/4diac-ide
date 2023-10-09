/*******************************************************************************
 * Copyright (c) 2011 - 2013, 2016 TU Wien ACIN, Profactor GmbH, fortiss GmbH
 *               2022 Primetals Technologies Austria GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *   Michael Oberlehner -  added Error Marker to Tooltip
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

/**
 * The Class ConnectionTooltipFigure.
 */
public class ConnectionTooltipFigure extends Figure {

	/**
	 * Instantiates a new fB tooltip figure.
	 *
	 * @param connection the fb view
	 */
	public ConnectionTooltipFigure(final Connection connection, final GraphicalAnnotationModel annotationModel) {
		setLayoutManager(new GridLayout());

		final StringBuilder label = new StringBuilder();

		if (null != connection) {
			getEndpointLabel(label, connection.getSource());
			label.append(" -> "); //$NON-NLS-1$
			getEndpointLabel(label, connection.getDestination());
		}

		final Label connNameLabel = new Label(label.toString());
		add(connNameLabel);

		final TextFlow content = new TextFlow(
				connection != null && connection.getComment() != null ? connection.getComment() : ""); //$NON-NLS-1$
		content.setLayoutManager(new ParagraphTextLayout(content, ParagraphTextLayout.WORD_WRAP_HARD));

		final FlowPage fp = new FlowPage();
		fp.add(content);
		if (connection != null && connection.getComment() != null && connection.getComment().length() > 0) {
			add(fp);
			setConstraint(fp, new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, false, true));
		}

		setConstraint(connNameLabel, new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, true, true));

		if (connection != null && annotationModel != null) {
			annotationModel.getAnnotations(connection).stream().forEach(annotation -> add(
					new Label(annotation.getText(), GraphicalAnnotationStyles.getAnnotationImage(annotation))));
		}
	}

	private static void getEndpointLabel(final StringBuilder label, final IInterfaceElement interfaceElement) {
		if (null != interfaceElement) {
			if (null != interfaceElement.getFBNetworkElement()) {
				label.append(interfaceElement.getFBNetworkElement().getName());
				label.append("."); //$NON-NLS-1$
			}
			label.append(interfaceElement.getName());
		}
	}
}
