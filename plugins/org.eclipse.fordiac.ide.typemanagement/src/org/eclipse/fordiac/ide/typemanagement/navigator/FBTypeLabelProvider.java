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
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.navigator;

import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.model.edit.providers.TypeImageProvider;
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
			ImageDescriptor imageDescriptor = getImageForFile(file);
			if (imageDescriptor != null) {
				imageDescriptor = decorateImage(file, imageDescriptor);
				return resourceManager.get(imageDescriptor);
			}
			return null;
		}
		return super.getImage(element);
	}

	public static ImageDescriptor getImageForFile(final IFile file) {
		final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
		if (typeEntry != null) {
			final FordiacImage fordiacImage = TypeImageProvider.get4diacImageForTypeEntry(typeEntry);
			if (fordiacImage != null) {
				return fordiacImage.getImageDescriptor();
			}
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

	@Override
	public String getText(final Object element) {
		if (element instanceof final IFile file) {
			return getTextForFBFile(file);
		}
		return super.getText(element);
	}

	private static String getTextForFBFile(final IFile element) {
		if (TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())
				|| TypeLibraryTags.FB_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())
				|| TypeLibraryTags.FC_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())
				|| TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())) {
			return TypeEntry.getTypeNameFromFile(element);
		}
		return null;
	}

	@Override
	public String getDescription(final Object anElement) {
		if (anElement instanceof final IFile file) {
			return getDescriptionForFBFile(file);
		}
		return null;
	}

	private static String getDescriptionForFBFile(final IFile element) {
		final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(element);
		if (typeEntry != null) {
			return generateTypeDescriptionString(typeEntry);
		}
		return null;
	}

	private static String generateTypeDescriptionString(final TypeEntry typeEntry) {
		final String typeName = typeEntry.getTypeName();
		final String comment = typeEntry.getComment();
		if (comment != null) {
			return typeName + ": " + comment; //$NON-NLS-1$
		}
		return typeName;
	}

}
