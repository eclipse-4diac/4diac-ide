/*******************************************************************************
 * Copyright (c) 2013, 2015, 2016 ,2020 fortiss GmbH, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Muddasir Shakil
 *     - Fixed getting Automation System 
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.decorators;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class SystemMonitoringDecorator implements ILabelDecorator {

	@Override
	public void addListener(ILabelProviderListener listener) {
		// currently nothing to do here
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// currently nothing to do here
	}

	@Override
	public void dispose() {
		if (null != overlayImage) {
			overlayImage.dispose();
		}
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public Image decorateImage(Image image, Object element) {
		Image retval = null;
		if (element instanceof IFile && SystemManager.SYSTEM_FILE_ENDING.equalsIgnoreCase(((IFile)element).getFileExtension())) {
			AutomationSystem system = SystemManager.INSTANCE.getSystem((IFile)element);
			if (MonitoringManager.getInstance().isSystemMonitored(system)) {
				retval = getOverlayImage(image);
			}
		} else {
			retval = image;
		}

		return retval;
	}

	private Image overlayImage = null;

	private Image getOverlayImage(Image image) {
		if (null == overlayImage) {
			DecorationOverlayIcon icon = new DecorationOverlayIcon(image,
					FordiacImage.ICON_MONITORING_DECORATOR.getImageDescriptor(), IDecoration.TOP_LEFT);
			overlayImage = icon.createImage();
		}

		return overlayImage;
	}

	@Override
	public String decorateText(String text, Object element) {
		return null;
	}
}
