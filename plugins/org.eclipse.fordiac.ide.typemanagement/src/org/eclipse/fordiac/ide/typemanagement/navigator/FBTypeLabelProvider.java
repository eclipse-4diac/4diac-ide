/*******************************************************************************
 * Copyright (c) 2011 - 2017 TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.navigator;

import java.util.Arrays;
import java.util.Scanner;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.IDescriptionProvider;

public class FBTypeLabelProvider extends AdapterFactoryLabelProvider implements IDescriptionProvider {

	ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources());

	public FBTypeLabelProvider() {
		super(FBTypeComposedAdapterFactory.getAdapterFactory());
	}

	@Override
	public Image getImage(final Object element) {
		if (element instanceof final IFile file && file.exists()) {
			ImageDescriptor imageDescriptor = getImageForFile((IFile) element);
			if (imageDescriptor != null) {
				imageDescriptor = decorateImage((IFile) element, imageDescriptor);
				return resourceManager.get(imageDescriptor);
			}
			return null;
		}
		return super.getImage(element);
	}

	public static ImageDescriptor getImageForFile(final IFile element) {
		if (TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())) {
			return FordiacImage.ICON_ADAPTER_TYPE.getImageDescriptor();
		}
		if (TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())) {
			return FordiacImage.ICON_SUB_APP_TYPE.getImageDescriptor();
		}
		if (TypeLibraryTags.FB_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())) {
			return getImageForFBTypeFile(element);
		}
		if (TypeLibraryTags.FC_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())) {
			return FordiacImage.ICON_FUNCTION.getImageDescriptor();
		}
		return null;
	}

	private static ImageDescriptor getImageForFBTypeFile(final IFile element) {
		final FBType type = getFBTypeFromFile(element);

		if (type instanceof BasicFBType) {
			return FordiacImage.ICON_BASIC_FB.getImageDescriptor();
		}
		if (type instanceof CompositeFBType) {
			return FordiacImage.ICON_COMPOSITE_FB.getImageDescriptor();
		}
		if (type instanceof SimpleFBType) {
			return FordiacImage.ICON_SIMPLE_FB.getImageDescriptor();
		}
		if (type instanceof ServiceInterfaceFBType) {
			return FordiacImage.ICON_SIFB.getImageDescriptor();
		}

		// partly load file to determine type
		return checkUnloadedFBType(element);
	}

	private static ImageDescriptor checkUnloadedFBType(final IFile element) {
		try (Scanner scanner = new Scanner(element.getContents())) {
			if (null != scanner.findWithinHorizon("BasicFB", 0)) { //$NON-NLS-1$
				return FordiacImage.ICON_BASIC_FB.getImageDescriptor();
			}

			scanner.reset();
			if (null != scanner.findWithinHorizon("FBNetwork", 0)) { //$NON-NLS-1$
				return FordiacImage.ICON_COMPOSITE_FB.getImageDescriptor();
			}

			scanner.reset();
			if (null != scanner.findWithinHorizon("SimpleFB", 0)) { //$NON-NLS-1$
				return FordiacImage.ICON_SIMPLE_FB.getImageDescriptor();
			}

			return FordiacImage.ICON_SIFB.getImageDescriptor();
		} catch (final Exception e) {
			FordiacLogHelper.logError(e.getMessage(), e);
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
				final int problemSeverity = Arrays.stream(problems).map(p -> p.getAttribute(IMarker.SEVERITY, -1))
						.max(Integer::compare).orElse(-1);

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

	@Override
	public String getText(final Object element) {
		if (element instanceof final IFile file) {
			return getTextForFBFile(file);
		}
		return super.getText(element);
	}

	private static String getTextForFBFile(final IFile element) {
		String text = null;
		if (TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())
				|| TypeLibraryTags.FB_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())
				|| TypeLibraryTags.FC_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())
				|| TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())) {
			text = TypeEntry.getTypeNameFromFile(element);
		}
		return text;
	}

	@Override
	public String getDescription(final Object anElement) {
		if (anElement instanceof final IFile file) {
			return getDescriptionForFBFile(file);
		}
		return null;
	}

	private static String getDescriptionForFBFile(final IFile fbtFile) {
		FBType type = null;
		if (TypeLibraryTags.FB_TYPE_FILE_ENDING.equalsIgnoreCase(fbtFile.getFileExtension())
				|| TypeLibraryTags.FC_TYPE_FILE_ENDING.equalsIgnoreCase(fbtFile.getFileExtension())) {
			type = getFBTypeFromFile(fbtFile);
		} else if (TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING.equalsIgnoreCase(fbtFile.getFileExtension())) {
			type = getAdapterTypeForFile(fbtFile);
		}

		if (null != type) {
			return generateTypeDescriptionString(type);
		}
		return null;
	}

	private static String generateTypeDescriptionString(final FBType type) {
		String description = type.getName() + ": "; //$NON-NLS-1$
		if (null != type.getComment()) {
			description += type.getComment();
		}
		return description;
	}

	private static FBType getAdapterTypeForFile(final IFile file) {
		final TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
		if (entry instanceof final AdapterTypeEntry adpEntry) {
			return adpEntry.getType().getAdapterFBType();
		}
		return null;
	}

	private static FBType getFBTypeFromFile(final IFile file) {
		final TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
		if (entry instanceof final FBTypeEntry fbEntry) {
			return fbEntry.getType();
		}
		return null;
	}

}
