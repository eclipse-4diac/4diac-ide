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
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.ui.UIPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public enum FordiacImage {
	// @formatter:off
	MISSING, // Image used for missing images in input streams

	// Part Images

	// Icon Images
	ICON_4DIAC_16, ICON_4DIAC_32, ICON_ADAPTER, ICON_ADD_STATE, ICON_ALGORITHM, ICON_APPLICATION, ICON_BASIC_FB,
	ICON_CLEAR_DEVICE, ICON_CLEAR_FORCE, ICON_COMPOSITE_FB, ICON_DATA, ICON_DATA_INPUT, ICON_DATA_TYPE,
	ICON_DATA_OUTPUT, ICON_DELETE_RESOURCE, ICON_DEPLOYMENT_CONSOLE, ICON_DEPLOYMENT_PERSPECTIVE, ICON_DEVICE,
	ICON_DISCOVER, ICON_DOWNLOAD, ICON_DOWNLOAD_SELECTION_TREE_VIEW, ICON_EC_ACTION, ICON_ECC, ICON_EC_STATE,
	ICON_EVENT, ICON_EVENT_INPUT, ICON_EVENT_OUTPUT, ICON_EXPAND_ALL, ICON_EXPORT, ICON_FB, ICON_FB_NETWORK,
	ICON_FB_TESTER, ICON_FIRMWARE_RESOURCE, ICON_FMU, ICON_FORCE_VALUE, ICON_HIDE_DATA, ICON_HIDE_EVENT,
	ICON_INTERFACE_EDITOR, ICON_INTERFACE_LIST, ICON_KILL_DEVICE, ICON_RUNTIME_LAUNCHER, ICON_LEFT_INPUT_PRIMITIVE,
	ICON_LEFT_OUTPUT_PRIMITIVE, ICON_LINK_OUTPUT, ICON_LINK_INPUT, ICON_LOCKED_STATE, ICON_MONITORING_DECORATOR,
	ICON_MONITORING_PERSPECTIVE, ICON_OK, ICON_PLUGS, ICON_PROPERTIES, ICON_REFRESH, ICON_REMOVE_WATCH, ICON_RESOURCE,
	ICON_RIGHT_INPUT_PRIMITIVE, ICON_RIGHT_OUTPUT_PRIMITIVE, ICON_RUN_DEBUG, ICON_SEGMENT, ICON_SERVICE,
	ICON_SERVICE_SEQUENCE, ICON_SIFB, ICON_SUB_APP, ICON_SOCKETS, ICON_START, ICON_START_MONITORING, ICON_STOP,
	ICON_STRUCTURED_TEXT, ICON_SYSTEM, ICON_SYSTEM_CONFIGURATION, ICON_SYSTEM_EXPLORER, ICON_SYSTEM_PERSPECTIVE,
	ICON_TYPE_NAVIGATOR, ICON_TRANSACTION, ICON_TRIGGER_EVENT, ICON_WATCHES_VIEW, ICON_WATCH_INTERFACE_ELEMENTS,

	// to be deleted with removing the tester
	ICON_FBTest, ICON_TestFailed, ICON_TestOK, ICON_NoTest, ICON_TesterTemplate,

	// Overlay Images
	OVERLAY_DISTRIBUTED_NATURE;

	// @formatter:on

	private static final String IMAGES_DIRECTORY = "images"; //$NON-NLS-1$
	private static final String FORDIAC_IMAGE_PROPERTIES = "fordiacimages"; //$NON-NLS-1$
	private static ResourceBundle foridacImageProperties = ResourceBundle.getBundle(FORDIAC_IMAGE_PROPERTIES);

	private static Map<Image, Image> errorImages = new HashMap<>();
	private static int count = 0;

	// FIXME: find a better way to handle overlay images
	public static Image getErrorOverlayImage(Image image) {
		if (image == null) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_DEC_FIELD_ERROR);
		}
		if (!errorImages.containsKey(image)) {
			DecorationOverlayIcon overlay = new DecorationOverlayIcon(image,
					PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_DEC_FIELD_ERROR),
					IDecoration.TOP_LEFT);
			count++;
			System.out.println("createErrorOverlayImage " + count);
			errorImages.put(image, overlay.createImage());
		}
		return errorImages.get(image);
	}

	private FordiacImage() {
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
		InputStream ret = null;
		URL fileLocation = getImageURL(this.name());
		if (null != fileLocation) {
			ret = fileLocation.openConnection().getInputStream();
		} else {
			ret = MISSING.getImageAsInputStream();
		}
		return ret;
	}

	private static boolean addImageDescriptor(String name) {
		try {
			URL fileLocation = getImageURL(name);
			ImageDescriptor id = ImageDescriptor.createFromURL(fileLocation);
			JFaceResources.getImageRegistry().put(name, id);
		} catch (MissingResourceException | IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	private static URL getImageURL(String name) {
		String fileName = foridacImageProperties.getString(name);
		return FileLocator.find(UIPlugin.getDefault().getBundle(),
				new Path(IMAGES_DIRECTORY + IPath.SEPARATOR + fileName), null);
	}

	private static Image getErrorImage() {
		ISharedImages si = PlatformUI.getWorkbench().getSharedImages();
		return si.getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
	}

	private static ImageDescriptor getErrorImageDescriptor() {
		ISharedImages si = PlatformUI.getWorkbench().getSharedImages();
		return si.getImageDescriptor(ISharedImages.IMG_OBJS_ERROR_TSK);
	}

	public static DecorationOverlayIcon createOverlayImage(Image image, ImageDescriptor imageDescriptor) {
		return createOverlayImage(image, imageDescriptor, IDecoration.TOP_LEFT);
	}

	public static DecorationOverlayIcon createOverlayImage(Image image, ImageDescriptor imageDescriptor, int quadrant) {
		return new DecorationOverlayIcon(image, imageDescriptor, quadrant);
	}
}
