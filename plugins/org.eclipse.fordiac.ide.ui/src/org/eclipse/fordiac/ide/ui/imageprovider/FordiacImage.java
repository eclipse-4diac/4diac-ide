/*******************************************************************************
 * Copyright (c) 2015 - 2016 fortiss GmbH. Profactor GmbH,
 * 				 2018 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.imageprovider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ListResourceBundle;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public enum FordiacImage {
	// @formatter:off
	MISSING, // Image used for missing images in input streams

	// Part Images

	// Icon Images
	ICON_4DIAC_16,
	ICON_4DIAC_32,
	ICON_4DIAC_FORTE,
	ICON_ADAPTER_LIST,
	ICON_ADAPTER_TYPE, ICON_ADD_STATE, ICON_ALGORITHM, ICON_APPLICATION,
	ICON_ATTRIBUTE_DECLARATION,
	ICON_BASIC_FB,
	ICON_BUILD_FORTE,
	ICON_CLEAR_DEVICE,
	ICON_CLEAR_FORCE,
	ICON_COMPOSITE_FB,
	ICON_DATA,
	ICON_DATA_INPUT,
	ICON_DATA_TYPE,
	ICON_DATA_OUTPUT,
	ICON_DELETE_RESOURCE,
	ICON_DEVICE,
	ICON_DEVICE_RESTART,
	ICON_DISCOVER,
	ICON_DOCUMENTATION_EDITOR,
	ICON_EC_ACTION, ICON_ECC, ICON_EC_STATE,
	ICON_EVENT, ICON_EVENT_INPUT, ICON_EVENT_OUTPUT, ICON_EXPAND_ALL, ICON_EXPORT, ICON_FB, ICON_FB_TYPE,
	ICON_FB_NETWORK,
	ICON_FIRMWARE_RESOURCE,
	ICON_FMU,
	ICON_FORCE_VALUE,
	ICON_FUNCTION,
	ICON_GROUP,
	ICON_HIDE_DATA,
	ICON_HIDE_EVENT,
	ICON_INTERFACE_EDITOR,
	ICON_INTERFACE_LIST,
	ICON_KILL_DEVICE,
	ICON_RUNTIME_LAUNCHER,
	ICON_LEFT_INPUT_PRIMITIVE, ICON_LEFT_OUTPUT_PRIMITIVE, ICON_LINK_OUTPUT, ICON_LINK_INPUT, ICON_LOCKED_STATE,
	ICON_MONITORING_DECORATOR,
	ICON_NEW_4DIAC_PROJECT,
	ICON_NEW_APPLICATION,
	ICON_NEW_FUNCTIONBLOCK, ICON_NEW_SYSTEM,
	ICON_PLUGS, ICON_PROPERTIES, ICON_REFRESH, ICON_REMOVE_WATCH,
	ICON_RESOURCE, ICON_RIGHT_INPUT_PRIMITIVE, ICON_RIGHT_OUTPUT_PRIMITIVE,
	ICON_SEGMENT, ICON_SERVICE,
	ICON_SERVICE_SEQUENCE, ICON_SIFB, ICON_SIMPLE_FB, ICON_SUB_APP, ICON_SUB_APP_TYPE, ICON_SOCKETS, ICON_START,
	ICON_START_MONITORING,
	ICON_STOP,
	ICON_STOP_FORTE,
	ICON_SYSTEM,
	ICON_SYSTEM_CONFIGURATION,
	ICON_SYSTEM_EXPLORER,
	ICON_SYSTEM_PERSPECTIVE,
	ICON_TYPE_NAVIGATOR,
	ICON_TRANSACTION,
	ICON_TRIGGER_EVENT,
	ICON_WATCHES_VIEW,
	ICON_WATCHPOINT,
	ICON_WATCHPOINT_DISABLED,
	ICON_WATCHPOINT_DISABLED_OVERLAY,
	ICON_WATCHPOINT_FORCE_DISABLED_OVERLAY,
	ICON_WATCHPOINT_FORCE_OVERLAY,
	ICON_WATCHPOINT_OVERLAY,
	ICON_WATCH_INTERFACE_ELEMENTS,
	ICON_ZOOM_100,
	ICON_ZOOM_PAGE,

	// to be deleted with removing the tester
	ICON_TesterTemplate,

	// Overlay Images
	OVERLAY_DISTRIBUTED_NATURE;

	// @formatter:on

	private static final String IMAGES_DIRECTORY = "images"; //$NON-NLS-1$
	private static final String FORDIAC_IMAGE_PROPERTIES = "fordiacimages"; //$NON-NLS-1$
	private static ResourceBundle fordiacImageProperties = getFordiacImageProperties();
	private static Bundle bundle = null;

	private static final ResourceBundle getFordiacImageProperties() {
		try {
			return ResourceBundle.getBundle(FORDIAC_IMAGE_PROPERTIES);
		} catch (final MissingResourceException e) {
			FordiacLogHelper.logWarning("Unable to load fordiacimages.properties from image-fragment.", e); //$NON-NLS-1$
			return new ListResourceBundle() {
				@Override
				protected Object[][] getContents() {
					return new Object[][] {};
				}
			};
		}
	}

	private static synchronized Bundle getBundle() {
		if (bundle == null) {
			bundle = FrameworkUtil.getBundle(FordiacImage.class);
		}
		return bundle;
	}

	FordiacImage() {
	}

	public Image getImage() {
		Image image = JFaceResources.getImageRegistry().get(this.name());
		if (image == null) {
			if (addImageDescriptor(this.name())) {
				image = JFaceResources.getImageRegistry().get(this.name());
			} else {
				image = getErrorImage();
			}
		}
		return image;
	}

	public ImageDescriptor getImageDescriptor() {
		ImageDescriptor id = JFaceResources.getImageRegistry().getDescriptor(this.name());
		if (id == null) {
			if (addImageDescriptor(this.name())) {
				id = JFaceResources.getImageRegistry().getDescriptor(this.name());
			} else {
				id = getErrorImageDescriptor();
			}
		}
		return id;
	}

	public InputStream getImageAsInputStream() throws IOException {
		final URL fileLocation = getImageURL(this.name());
		if (null != fileLocation) {
			return fileLocation.openConnection().getInputStream();
		}
		return MISSING.getImageAsInputStream();
	}

	private static boolean addImageDescriptor(final String name) {
		try {
			final URL fileLocation = getImageURL(name);
			final ImageDescriptor id = ImageDescriptor.createFromURL(fileLocation);
			JFaceResources.getImageRegistry().put(name, id);
		} catch (MissingResourceException | IllegalArgumentException e) {
			FordiacLogHelper.logError("Could not load image", e); //$NON-NLS-1$
			return false;
		}
		return true;
	}

	private static URL getImageURL(final String name) {
		final String fileName = fordiacImageProperties.getString(name);
		return FileLocator.find(getBundle(), new Path(IMAGES_DIRECTORY + IPath.SEPARATOR + fileName), null);
	}

	private static Image getErrorImage() {
		final ISharedImages si = PlatformUI.getWorkbench().getSharedImages();
		return si.getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
	}

	private static ImageDescriptor getErrorImageDescriptor() {
		final ISharedImages si = PlatformUI.getWorkbench().getSharedImages();
		return si.getImageDescriptor(ISharedImages.IMG_OBJS_ERROR_TSK);
	}

	public static DecorationOverlayIcon createOverlayImage(final Image image, final ImageDescriptor imageDescriptor) {
		return createOverlayImage(image, imageDescriptor, IDecoration.TOP_LEFT);
	}

	public static DecorationOverlayIcon createOverlayImage(final Image image, final ImageDescriptor imageDescriptor,
			final int quadrant) {
		return new DecorationOverlayIcon(image, imageDescriptor, quadrant);
	}
}
