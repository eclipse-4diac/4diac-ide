/*******************************************************************************
 * Copyright (c) 2011 - 2013, 2016 TU Wien ACIN, Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer 
 *     - initial API and implementation and/or initial documentation
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
	public ConnectionTooltipFigure(final Connection connection) {
		setLayoutManager(new GridLayout());

		StringBuilder label = new StringBuilder();
		
		if(null != connection){
			getEndpointLabel(label, connection.getSource());
			label.append(" -> "); //$NON-NLS-1$
			getEndpointLabel(label, connection.getDestination());
		}
		
		Label connNameLabel = new Label(label.toString());
		add(connNameLabel);

		TextFlow content = new TextFlow(
				connection != null && connection.getComment() != null ? connection.getComment() : ""); //$NON-NLS-1$
		content.setLayoutManager(new ParagraphTextLayout(content,
				ParagraphTextLayout.WORD_WRAP_HARD));

		FlowPage fp = new FlowPage();
		fp.add(content);
		if (connection != null && connection.getComment() != null
				&& connection.getComment().length() > 0) {
			add(fp);
			setConstraint(fp, new GridData(PositionConstants.CENTER,
					PositionConstants.MIDDLE, false, true));
		}

		setConstraint(connNameLabel, new GridData(PositionConstants.CENTER,
				PositionConstants.MIDDLE, true, true));

	}

	private static void getEndpointLabel(StringBuilder label, IInterfaceElement interfaceElement) {
		if(null != interfaceElement){
			if(null != interfaceElement.getFBNetworkElement()){
				label.append(interfaceElement.getFBNetworkElement().getName());
				label.append("."); //$NON-NLS-1$
			}
			label.append(interfaceElement.getName());
		}
	}
}
