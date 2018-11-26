/*******************************************************************************
 * Copyright (c) 2015 - 2016 fortiss GmbH. Profactor GmbH,
 * 				 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util.imageprovider;

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
import org.eclipse.fordiac.ide.util.Activator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public enum FordiacImage {
	// @formatter:off
	Missing,          //Image used for missing images in input streams
	
	Image_4DIACAbout,
	
	// Part Images

	// Icon Images
	ICON_4DIAC_16,
	ICON_4DIAC_32,
	ICON_Adapter,
	ICON_Algorithm,
	ICON_Application,
	ICON_BasicFB,
	ICON_ClearDevice,
	ICON_ClearForce,
	ICON_CompositeFB,
	ICON_Data,
	ICON_DataInput,
	ICON_DataType,
	ICON_DataOutput,
	ICON_DeleteResource,
	ICON_DeploymentConsole,
	ICON_DeploymentPerspective,
	ICON_Device,
	ICON_Download,
	ICON_DownloadSelectionTreeView,
	ICON_ECAction,
	ICON_ECC,
	ICON_ECState,
	ICON_Event,
	ICON_EventInput,
	ICON_EventOutput,
	ICON_ExpandAll,
	ICON_Export,
	ICON_FB, 
	ICON_FBNetwork, 
	ICON_FBTest,
	ICON_FBTester,
	ICON_FirmwareResource,
	ICON_FMU,
	ICON_ForceValue,
	ICON_HideData,
	ICON_HideEvent,
	ICON_InterfaceEditor,
	ICON_InterfaceList,
	ICON_KillDevice,
	ICON_RuntimeLauncher,
	ICON_LeftInputPrimitive,
	ICON_LeftOutputPrimitive,
	ICON_LinkOutput,
	ICON_LinkInput,
	ICON_LockedState,
	ICON_MonitoringDecorator,
	ICON_MonitoringPerspective,
	ICON_NoTest,
	ICON_OK,
	ICON_Plugs,
	ICON_Properties,
	ICON_Refresh,
	ICON_RemoveWatch,
	ICON_Resource,
	ICON_Resume,
	ICON_RigthInputPrimitive,
	ICON_RigthOutputPrimitive,
	ICON_RundDebug,
	ICON_SaveImage,
	ICON_SaveImageDisabled,
	ICON_Segment,
	ICON_Service,
	ICON_ServiceSequence,
	ICON_SIFB, 
	ICON_SubApp,
	ICON_Sockets,
	ICON_Start,
	ICON_StartMonitoring,
	ICON_Stop,
	ICON_StructuredText,
	ICON_System,
	ICON_SystemConfiguration,
	ICON_SystemExplorer,
	ICON_SystemPerspective,
	ICON_TesterTemplate,
	ICON_TestFailed,
	ICON_TestOK,
	ICON_Tools,
	ICON_TypeNavigator,
	ICON_Transaction,
	ICON_TriggerEvent,
	ICON_Variables,
	ICON_WatchesView,
	ICON_WatchInterfaceElements,

	// Overlay Images
	OVERLAY_DistributedNature;
	
	
	// @formatter:on

	private static final String IMAGES_DIRECTORY = "images"; //$NON-NLS-1$
	private static final String FORDIAC_IMAGE_PROPERTIES = "fordiacimages"; //$NON-NLS-1$
	private static ResourceBundle foridacImageProperties = ResourceBundle
			.getBundle(FORDIAC_IMAGE_PROPERTIES);

	private static Map<Image, Image> errorImages = new HashMap<>();
	private static int count = 0;
	
	//FIXME: find a better way to handle overlay images
	public static Image getErrorOverlayImage(Image image) {
		if (image == null) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_DEC_FIELD_ERROR);
		}
		if (!errorImages.containsKey(image)) {
			DecorationOverlayIcon overlay = new DecorationOverlayIcon(image, 
					PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_DEC_FIELD_ERROR), IDecoration.TOP_LEFT);
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
				id = JFaceResources.getImageRegistry().getDescriptor(
						this.name());
			} else {
				id = getErrorImageDescriptor();
			}
		}
		return id;
	}

	public InputStream getImageAsInputStream() throws IOException {
		InputStream ret = null;
		URL fileLocation = getImageURL(this.name());
		if(null != fileLocation){
			ret = fileLocation.openConnection().getInputStream();
		} else {
			ret = Missing.getImageAsInputStream();
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
		return FileLocator.find(Activator.getDefault().getBundle(), 
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

	public static DecorationOverlayIcon createOverlayImage(Image image,
			ImageDescriptor imageDescriptor) {
		return createOverlayImage(image, imageDescriptor, IDecoration.TOP_LEFT);
	}

	public static DecorationOverlayIcon createOverlayImage(Image image,
			ImageDescriptor imageDescriptor, int quadrant) {
		return new DecorationOverlayIcon(image, imageDescriptor, quadrant);
	}
}
