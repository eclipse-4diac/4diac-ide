/*******************************************************************************
 * Copyright (c) 2008, 2009, 2016 Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Kepler University
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
 *   Alois Zoitl - extracted common FB shape for interface and fbn editors
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
import org.eclipse.fordiac.ide.model.annotations.MappingAnnotations;
import org.eclipse.fordiac.ide.model.edit.helper.CommentHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;

/** The Class FBTooltipFigure. */
public class FBNetworkElementTooltipFigure extends Figure {

	/**
	 * Instantiates a new fB tooltip figure.
	 *
	 * @param fbView the fb view
	 */
	public FBNetworkElementTooltipFigure(final FBNetworkElement element) {
		setLayoutManager(new GridLayout());

		final Label instanceNameLabel = new Label(element.getName());
		add(instanceNameLabel);
		setConstraint(instanceNameLabel, new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, true, true));

		createTypeAndVersionLabel(element);

		createPackageLabel(element);

		final Figure line = new VerticalLineCompartmentFigure();
		add(line);
		setConstraint(line, new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, true, true));

		if (CommentHelper.hasComment(element)) {
			final FlowPage fp = new FlowPage();
			final TextFlow content = new TextFlow(element.getComment());
			content.setLayoutManager(new ParagraphTextLayout(content, ParagraphTextLayout.WORD_WRAP_HARD));
			fp.add(content);
			line.add(fp);
			line.setConstraint(fp, new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, false, true));
		}

		final FBNetwork fbNetwork = element.getFbNetwork();

		final Application app = (null != fbNetwork) ? (fbNetwork.getApplication()) : null;
		if (app != null && app
				.eContainer() instanceof final org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem automationSystem) {

			final Label system = new Label(Messages.FBTooltipFigure_LABEL_System + automationSystem.getName());
			line.add(system);
			line.setConstraint(system, new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, true, true));
		}
		if (app != null) {
			final Label application = new Label(Messages.FBTooltipFigure_LABEL_Application + app.getName());
			line.add(application);
			line.setConstraint(application,
					new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, true, true));
		}

		if (element.isMapped()) {
			createMappingInfo(element, line);
		}
	}

	private static void createMappingInfo(final FBNetworkElement element, final Figure parent) {
		final Figure line = new VerticalLineCompartmentFigure();
		parent.add(line);
		parent.setConstraint(line, new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, true, true));

		final Label mappingLabel = new Label(MappingAnnotations.getHierarchicalName(element));
		line.add(mappingLabel);
		line.setConstraint(mappingLabel, new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, true, true));
	}

	private void createTypeAndVersionLabel(final FBNetworkElement element) {
		String labelText = org.eclipse.fordiac.ide.gef.Messages.FBFigure_TYPE_NOT_SET;

		final LibraryElement type = element.getType();
		if (type != null) {
			labelText = type.getName();
			if (!type.getVersionInfo().isEmpty() && null != type.getVersionInfo().get(0)) {
				final VersionInfo versionInfo = type.getVersionInfo().get(0);
				labelText += Messages.FBNetworkElementTooltipFigure_VersionLabel + versionInfo.getVersion();
			}
		}

		final Label typeVersionLabel = new Label(labelText);
		add(typeVersionLabel);
		setConstraint(typeVersionLabel, new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, true, true));

	}

	private void createPackageLabel(final FBNetworkElement element) {
		final LibraryElement type = element.getType();
		if (!PackageNameHelper.getPackageName(type).isBlank()) {
			final Label packageLabel = new Label(PackageNameHelper.getPackageName(type));
			add(packageLabel);
			setConstraint(packageLabel, new GridData(PositionConstants.CENTER, PositionConstants.MIDDLE, true, true));
		}
	}
}
