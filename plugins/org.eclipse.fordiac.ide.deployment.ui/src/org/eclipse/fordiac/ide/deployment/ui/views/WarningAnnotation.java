/*******************************************************************************
 * Copyright (c) 2012, 2022 Profactor GmbH, fortiss GmbH,
 * 							Johannes Kepler University
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
 *   Alois Zoitl - Harmonized deployment and monitoring
 *               - extracted common baseclass for deployment annotations
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.views;

import org.eclipse.fordiac.ide.deployment.ui.Messages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class WarningAnnotation extends AbstractDeploymentAnnotations {

	public static final String DEPLOYMENT_WARNING_TYPE = "org.eclipse.fordiac.ide.deployment.warning"; //$NON-NLS-1$

	public WarningAnnotation(final int line, final String text) {
		super(DEPLOYMENT_WARNING_TYPE, text, line);
	}

	@Override
	public Image getImage() {
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_DEC_FIELD_WARNING);
	}

	@Override
	public int getLayer() {
		return 4;
	}

	@Override
	public String getTypLabel() {
		return Messages.WarningAnnotation_DeploymentWarning;
	}

}