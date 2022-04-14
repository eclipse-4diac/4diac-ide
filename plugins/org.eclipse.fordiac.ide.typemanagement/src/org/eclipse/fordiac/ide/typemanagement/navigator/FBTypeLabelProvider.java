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
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.navigator.IDescriptionProvider;

public class FBTypeLabelProvider extends AdapterFactoryLabelProvider implements IDescriptionProvider {

	public FBTypeLabelProvider() {
		super(FBTypeComposedAdapterFactory.getAdapterFactory());
	}

	@Override
	public Image getImage(final Object element) {
		if (element instanceof IFile && ((IFile) element).exists()) {
			return getImageForFile((IFile) element);
		}
		return super.getImage(element);
	}

	public static Image getImageForFile(final IFile element) {
		Image image = null;

		if (TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())) {
			image = FordiacImage.ICON_ADAPTER_TYPE.getImage();
		} else if (TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())) {
			image = FordiacImage.ICON_SUB_APP_TYPE.getImage();
		} else if (TypeLibraryTags.FB_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())) {
			image = getImageForFBTypeFile(element);
		}

		if (null != image && fileHasProblems(element)) {
			return FordiacImage.getErrorOverlayImage(image);
		}

		return image;
	}

	private static Image getImageForFBTypeFile(final IFile element) {
		final FBType type = getFBTypeFromFile(element);

		if (type instanceof BasicFBType) {
			return FordiacImage.ICON_BASIC_FB.getImage();
		} else if (type instanceof CompositeFBType) {
			return FordiacImage.ICON_COMPOSITE_FB.getImage();
		} else if (type instanceof SimpleFBType) {
			return FordiacImage.ICON_SIMPLE_FB.getImage();
		} else if (type instanceof ServiceInterfaceFBType) {
			return FordiacImage.ICON_SIFB.getImage();
		}

		// partly load file to determine type
		return checkUnloadedFBType(element);
	}

	private static boolean fileHasProblems(final IFile element) {
		IMarker[] problems = null;
		if (element.exists()) {
			try {
				problems = element.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}
		return ((null != problems) && (0 < problems.length));
	}

	private static Image checkUnloadedFBType(final IFile element) {
		Image image = null;
		try (Scanner scanner = new Scanner(element.getContents())) {
			if (null != scanner.findWithinHorizon("BasicFB", 0)) { //$NON-NLS-1$
				image = FordiacImage.ICON_BASIC_FB.getImage();
			} else {
				scanner.reset();
				if (null != scanner.findWithinHorizon("FBNetwork", 0)) { //$NON-NLS-1$
					image = FordiacImage.ICON_COMPOSITE_FB.getImage();
				} else {
					scanner.reset();
					if (null != scanner.findWithinHorizon("SimpleFB", 0)) { //$NON-NLS-1$
						image = FordiacImage.ICON_SIMPLE_FB.getImage();
					} else {
						image = FordiacImage.ICON_SIFB.getImage();
					}
				}
			}
		} catch (final Exception e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}

		return image;
	}

	@Override
	public String getText(final Object element) {
		if (element instanceof IFile) {
			return getTextForFBFile((IFile) element);
		}
		return super.getText(element);
	}

	private static String getTextForFBFile(final IFile element) {
		String text = null;
		if (TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())
				|| TypeLibraryTags.FB_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())
				|| TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())) {
			text = TypeEntry.getTypeNameFromFile(element);
		}
		return text;
	}

	@Override
	public String getDescription(final Object anElement) {
		if (anElement instanceof IFile) {
			return getDescriptionForFBFile((IFile) anElement);
		}
		return null;
	}

	private static String getDescriptionForFBFile(final IFile fbtFile) {
		FBType type = null;
		if (TypeLibraryTags.FB_TYPE_FILE_ENDING.equalsIgnoreCase(fbtFile.getFileExtension())) {
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
		final TypeEntry entry = TypeLibrary.getTypeEntryForFile(file);
		if (entry instanceof AdapterTypeEntry) {
			return ((AdapterTypeEntry) entry).getType().getAdapterFBType();
		}
		return null;
	}

	private static FBType getFBTypeFromFile(final IFile file) {
		final TypeEntry entry = TypeLibrary.getTypeEntryForFile(file);
		if (entry instanceof FBTypeEntry) {
			return ((FBTypeEntry) entry).getType();
		}
		return null;
	}

}
