/*******************************************************************************
 * Copyright (c) 2008, 2009, 2016 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.application.figures;

import java.text.MessageFormat;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.figures.VerticalLineCompartmentFigure;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;

/**
 * The Class FBTooltipFigure.
 */
public class FBNetworkElementTooltipFigure extends Figure {

	/**
	 * Instantiates a new fB tooltip figure.
	 * 
	 * @param fbView the fb view
	 */
	public FBNetworkElementTooltipFigure(final FBNetworkElement element) {
		setLayoutManager(new GridLayout());

		Label instanceNameLabel = new Label(element.getName());
		add(instanceNameLabel);
		setConstraint(instanceNameLabel, new GridData(PositionConstants.CENTER,
				PositionConstants.MIDDLE, true, true));
		
		craeteTypeAndVersionLabel(element);
		
		Figure line = new VerticalLineCompartmentFigure();
		add(line);
		setConstraint(line, new GridData(PositionConstants.CENTER,
				PositionConstants.MIDDLE, true, true));


		if (element.getComment() != null && element.getComment().length() > 0) {
			FlowPage fp = new FlowPage();
			TextFlow content = new TextFlow(element.getComment());
			content.setLayoutManager(new ParagraphTextLayout(content,
					ParagraphTextLayout.WORD_WRAP_HARD));
			fp.add(content);
			line.add(fp);
			line.setConstraint(fp, new GridData(PositionConstants.CENTER,
					PositionConstants.MIDDLE, false, true));
		}

		FBNetwork fbNetwork = (FBNetwork) element.eContainer();

		Application app = (null != fbNetwork) ? (fbNetwork.getApplication()) : null;
		if (app != null
				&& app.eContainer() instanceof org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem) {

			Label system = new Label(Messages.FBTooltipFigure_LABEL_System
					+ ((AutomationSystem) app.eContainer()).getName());
			line.add(system);
			line.setConstraint(system, new GridData(PositionConstants.CENTER,
					PositionConstants.MIDDLE, true, true));
		}
		if (app != null) {
			Label application = new Label(
					Messages.FBTooltipFigure_LABEL_Application + app.getName());
			line.add(application);
			line.setConstraint(application, new GridData(PositionConstants.CENTER,
					PositionConstants.MIDDLE, true, true));
		}
		
		if(element.isMapped()) {
			createMappingInfo(element, line);
		}
	}

	private static void createMappingInfo(final FBNetworkElement element, Figure parent) {
		Figure line = new VerticalLineCompartmentFigure();
		parent.add(line);
		parent.setConstraint(line, new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, true, true));
		
		Resource res = element.getResource();
		Device dev = res.getDevice();
		
		Label mappingLabel = new Label(
				 MessageFormat.format(Messages.FBTooltipFigure_LABEL_MappedTo, dev.getName(), res.getName()));
		line.add(mappingLabel);
		line.setConstraint(mappingLabel, new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, true, true));		
	}

	private void craeteTypeAndVersionLabel(FBNetworkElement element) {
		String labelText = Messages.FBFigure_TYPE_NOT_SET;
		
		LibraryElement type = element.getType();
		if (type != null) {
			labelText = type.getName();
			if (!type.getVersionInfo().isEmpty() && null != type.getVersionInfo().get(0)) {
				VersionInfo versionInfo = type.getVersionInfo().get(0);
				labelText += " / Ver: " + versionInfo.getVersion();
			}
		}
		
		Label typeVersionLabel = new Label(labelText);
		add(typeVersionLabel);
		setConstraint(typeVersionLabel, new GridData(PositionConstants.CENTER,
				PositionConstants.MIDDLE, true, true));
		
	}
}
