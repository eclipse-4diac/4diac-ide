/*******************************************************************************
 * Copyright (c) 2011, 2024 TU Wien ACIN, fortiss GmbH,
 * 							Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   			 - cleaned up issues reported by sonarlint
 *   			 - made the getImageForFile public so it can be used by the
 *                 palette, some code cleanup
 *   Martin Erich Jobst
 *               - avoid loading type by using cached meta-information from type entry
 *   Alois Zoitl - extracted from FBType LabelProvider
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.navigator;

import java.util.Arrays;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class LibraryElementLabelProvider extends AdapterFactoryLabelProvider {

	private final ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources());

	protected Image getDecoratedImage(final IResource element, final ImageDescriptor imageDescriptor) {
		if (imageDescriptor != null) {
			return resourceManager.get(decorateImage(element, imageDescriptor));
		}
		return null;
	}

	/**
	 * If the file has a problem an error/warning overlay is created
	 *
	 * This code is based on
	 * org.eclipse.ui.internal.navigator.resources.workbench.ResourceExtensionLabelProvider,
	 * which can not directly be used as this class is an Eclipse internal class.
	 */
	private static ImageDescriptor decorateImage(final IResource element, final ImageDescriptor imageDescriptor) {
		final ImageDescriptor overlay = getErrorOverlay(element);
		if (overlay != null) {
			return new DecorationOverlayIcon(imageDescriptor, overlay, IDecoration.BOTTOM_LEFT);
		}
		return imageDescriptor;
	}

	private static ImageDescriptor getErrorOverlay(final IResource element) {
		ImageDescriptor overlay = null;
		try {
			final IMarker[] problems = element.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			if (problems.length > 0) {
				final int problemSeverity = Arrays.stream(problems).mapToInt(p -> p.getAttribute(IMarker.SEVERITY, -1))
						.max().orElse(-1);
				switch (problemSeverity) {
				case IMarker.SEVERITY_ERROR:
					overlay = PlatformUI.getWorkbench().getSharedImages()
							.getImageDescriptor(ISharedImages.IMG_DEC_FIELD_ERROR);
					break;
				case IMarker.SEVERITY_WARNING:
					overlay = PlatformUI.getWorkbench().getSharedImages()
							.getImageDescriptor(ISharedImages.IMG_DEC_FIELD_WARNING);
					break;
				default:
					break;
				}
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
		return overlay;
	}

	public LibraryElementLabelProvider(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

}