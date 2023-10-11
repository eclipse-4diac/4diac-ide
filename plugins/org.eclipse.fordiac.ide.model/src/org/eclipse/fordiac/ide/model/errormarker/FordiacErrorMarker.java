/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.errormarker;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.SegmentSequence;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class FordiacErrorMarker {

	public static final String PROBLEM_MARKER = "org.eclipse.fordiac.ide.model.problem"; //$NON-NLS-1$

	public static final String IEC61499_MARKER = "org.eclipse.fordiac.ide.model.iec61499"; //$NON-NLS-1$
	public static final String VALIDATION_MARKER = "org.eclipse.fordiac.ide.model.validation"; //$NON-NLS-1$
	public static final String INITIAL_VALUE_MARKER = "org.eclipse.fordiac.ide.model.initialValue"; //$NON-NLS-1$
	public static final String TYPE_DECLARATION_MARKER = "org.eclipse.fordiac.ide.model.typeDeclaration"; //$NON-NLS-1$
	public static final String TYPE_LIBRARY_MARKER = "org.eclipse.fordiac.ide.model.typeLibrary"; //$NON-NLS-1$

	public static final String TARGET_URI = "org.eclipse.fordiac.ide.model.iec61499.targetUri"; //$NON-NLS-1$
	public static final String TARGET_TYPE = "org.eclipse.fordiac.ide.model.iec61499.targetType"; //$NON-NLS-1$
	public static final String TARGET_FEATURE = "org.eclipse.fordiac.ide.model.iec61499.targetFeature"; //$NON-NLS-1$

	public static URI getTargetUri(final IMarker marker) throws CoreException, IllegalArgumentException {
		final String targetUriAttribute = (String) marker.getAttribute(TARGET_URI);
		if (targetUriAttribute != null) {
			return URI.createURI(targetUriAttribute);
		}
		return null;
	}

	public static EClass getTargetType(final IMarker marker) throws CoreException, IllegalArgumentException {
		final String targetTypeAttribute = (String) marker.getAttribute(TARGET_TYPE);
		if (targetTypeAttribute != null) {
			final URI targetTypeUri = URI.createURI(targetTypeAttribute);
			final EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(targetTypeUri.trimFragment().toString());
			if (ePackage != null) {
				final Resource eResource = ePackage.eResource();
				if (eResource != null) {
					return (EClass) eResource.getEObject(targetTypeUri.fragment());
				}
			}
		}
		return null;
	}

	public static EStructuralFeature getTargetFeature(final IMarker marker)
			throws CoreException, IllegalArgumentException {
		final String targetFeatureAttribute = (String) marker.getAttribute(TARGET_FEATURE);
		if (targetFeatureAttribute != null) {
			final URI targetFeatureUri = URI.createURI(targetFeatureAttribute);
			final EPackage ePackage = EPackage.Registry.INSTANCE
					.getEPackage(targetFeatureUri.trimFragment().toString());
			if (ePackage != null) {
				final Resource eResource = ePackage.eResource();
				if (eResource != null) {
					return (EStructuralFeature) eResource.getEObject(targetFeatureUri.fragment());
				}
			}
		}
		return null;
	}

	public static EObject getTarget(final IMarker marker) throws IllegalArgumentException, CoreException {
		final URI targetUri = FordiacErrorMarker.getTargetUri(marker);
		if (targetUri != null && targetUri.isPlatformResource()) {
			final ResourceSet resourceSet = new ResourceSetImpl();
			return resourceSet.getEObject(targetUri, true);
		}
		return null;
	}

	public static EObject getTargetEditable(final IMarker marker) throws IllegalArgumentException, CoreException {
		final URI targetUri = FordiacErrorMarker.getTargetUri(marker);
		if (targetUri != null) {
			if (targetUri.isPlatformResource()) {
				final IFile file = ResourcesPlugin.getWorkspace().getRoot()
						.getFile(new Path(targetUri.toPlatformString(true)));
				if (file.exists()) {
					final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
					if (typeEntry != null) {
						final Resource resource = typeEntry.getTypeEditable().eResource();
						if (resource != null) {
							return resource.getEObject(targetUri.fragment());
						}
					}
				}
			}
			final ResourceSet resourceSet = new ResourceSetImpl();
			return resourceSet.getEObject(targetUri, true);
		}
		return null;
	}

	public static EObject getTargetRelative(final IMarker marker, final EObject root)
			throws IllegalArgumentException, CoreException {
		final URI targetUri = FordiacErrorMarker.getTargetUri(marker);
		if (targetUri != null) {
			final SegmentSequence segments = SegmentSequence.create("/", targetUri.fragment()); //$NON-NLS-1$
			if (segments.segmentCount() > 2) {
				final String relativeFragment = String.join("/", segments.subSegments(2)); //$NON-NLS-1$
				return EcoreUtil.getEObject(root, relativeFragment);
			}
		}
		return null;
	}

	public static boolean markerTargetsFBNetworkElement(final IMarker marker) {
		return isTargetOfType(marker, LibraryElementPackage.eINSTANCE.getFBNetworkElement());
	}

	public static boolean markerTargetsErrorMarkerInterface(final IMarker marker) {
		return isTargetOfType(marker, LibraryElementPackage.eINSTANCE.getErrorMarkerInterface());
	}

	public static boolean markerTargetsConnection(final IMarker marker) {
		return isTargetOfType(marker, LibraryElementPackage.eINSTANCE.getConnection());
	}

	public static boolean markerTargetsValue(final IMarker marker) {
		return isTargetOfType(marker, LibraryElementPackage.eINSTANCE.getValue());
	}

	public static boolean isTargetOfType(final IMarker marker, final EClass type) {
		try {
			final EClass targetType = FordiacErrorMarker.getTargetType(marker);
			if (targetType != null) {
				return type.isSuperTypeOf(targetType);
			}
		} catch (IllegalArgumentException | CoreException e) {
			FordiacLogHelper.logWarning("Cannot determine marker target type " + marker.toString(), e); //$NON-NLS-1$
		}
		return false;
	}

	private FordiacErrorMarker() {
		throw new UnsupportedOperationException();
	}
}
