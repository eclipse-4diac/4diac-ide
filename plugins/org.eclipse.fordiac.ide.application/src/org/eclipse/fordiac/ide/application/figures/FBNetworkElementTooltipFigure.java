/*******************************************************************************
 * Copyright (c) 2008, 2009, 2016 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.application.figures;

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
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
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

		
// TODO model refactoring - fix when new mapping is implemented 
//		FBView mappedFB = null;
//		if (fbView.getApplicationFB() != null) {
//			mappedFB = fbView;
//		}
//		if (fbView.getMappedFB() != null) {
//			mappedFB = fbView.getMappedFB();
//		}
//		if (mappedFB != null) {
//			UIResourceEditor uiResEditor = (UIResourceEditor) mappedFB.eContainer();
//			if (uiResEditor != null) {
//				Resource res = uiResEditor.getResourceElement();
//				if (res != null && res.getName() != null) {
//					Device device = res.getDevice();
//					String deviceName = "";
//					if (device != null) {
//						deviceName = device.getName();
//					}
//					Label resourceLabel = new Label(
//							Messages.FBTooltipFigure_LABEL_MappedTo + deviceName + "."
//									+ res.getName());
//					add(resourceLabel);
//					setConstraint(resourceLabel, new GridData(PositionConstants.CENTER,
//							PositionConstants.MIDDLE, true, true));
//				}
//
//			}
//		}
	}

	private void craeteTypeAndVersionLabel(FBNetworkElement element) {
		String labelText = Messages.FBFigure_TYPE_NOT_SET;
		
		LibraryElement type = element.getType();
		if (type != null) {
			labelText = type.getName();
			if (type.getVersionInfo().size() > 0 && null != type.getVersionInfo().get(0)) {
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
