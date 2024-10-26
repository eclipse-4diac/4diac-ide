/*******************************************************************************
 * Copyright (c) 2023, 2024 Martin Erich Jobst
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

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.Diagnostic;
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
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public final class FordiacErrorMarker {

	public static final String PROBLEM_MARKER = "org.eclipse.fordiac.ide.model.problem"; //$NON-NLS-1$

	public static final String IEC61499_MARKER = "org.eclipse.fordiac.ide.model.iec61499"; //$NON-NLS-1$
	public static final String VALIDATION_MARKER = "org.eclipse.fordiac.ide.model.validation"; //$NON-NLS-1$
	public static final String INITIAL_VALUE_MARKER = "org.eclipse.fordiac.ide.model.initialValue"; //$NON-NLS-1$
	public static final String TYPE_DECLARATION_MARKER = "org.eclipse.fordiac.ide.model.typeDeclaration"; //$NON-NLS-1$
	public static final String IMPORT_MARKER = "org.eclipse.fordiac.ide.model.import"; //$NON-NLS-1$
	public static final String TYPE_LIBRARY_MARKER = "org.eclipse.fordiac.ide.model.typeLibrary"; //$NON-NLS-1$
	public static final String LIBRARY_MARKER = "org.eclipse.fordiac.ide.model.library"; //$NON-NLS-1$
	public static final String TEMPORARY_MARKER = "org.eclipse.fordiac.ide.model.temporary"; //$NON-NLS-1$

	private static final Set<String> MODEL_MARKER_TYPES = Set.of(IEC61499_MARKER, VALIDATION_MARKER,
			INITIAL_VALUE_MARKER, TYPE_DECLARATION_MARKER, IMPORT_MARKER, TYPE_LIBRARY_MARKER, LIBRARY_MARKER,
			TEMPORARY_MARKER);

	/**
	 * The source-specific diagnostic code.
	 *
	 * @see Diagnostic#getCode()
	 */
	public static final String CODE = "org.eclipse.fordiac.ide.model.iec61499.code"; //$NON-NLS-1$

	/**
	 * The diagnostic source.
	 *
	 * @see Diagnostic#getSource()
	 */
	public static final String SOURCE = "org.eclipse.fordiac.ide.model.iec61499.source"; //$NON-NLS-1$

	/**
	 * The target URI to the originating model element.
	 *
	 * @see Diagnostic#getData()
	 */
	public static final String TARGET_URI = "org.eclipse.fordiac.ide.model.iec61499.targetUri"; //$NON-NLS-1$
	/**
	 * The target type of the originating model element.
	 *
	 * @see Diagnostic#getData()
	 */
	public static final String TARGET_TYPE = "org.eclipse.fordiac.ide.model.iec61499.targetType"; //$NON-NLS-1$
	/**
	 * The target feature of the originating model element.
	 *
	 * @see Diagnostic#getData()
	 */
	public static final String TARGET_FEATURE = "org.eclipse.fordiac.ide.model.iec61499.targetFeature"; //$NON-NLS-1$
	/**
	 * The additional data of the diagnostic.
	 *
	 * @see Diagnostic#getData()
	 */
	public static final String DATA = "org.eclipse.fordiac.ide.model.iec61499.data"; //$NON-NLS-1$

	/**
	 * The default source-specific code.
	 *
	 * @see #CODE
	 */
	public static final int DEFAULT_CODE = 0;

	/**
	 * Checks whether the marker type is a model marker type.
	 *
	 * @param markerType The marker type
	 * @return true if yes, false otherwise
	 */
	public static boolean isModelMarkerType(final String markerType) {
		return MODEL_MARKER_TYPES.contains(markerType);
	}

	/**
	 * Get the attribute for the source-specific diagnostic code.
	 *
	 * @param marker The marker
	 * @return The diagnostic code or {@link #DEFAULT_CODE} if no such attribute is
	 *         present.
	 * @see Diagnostic#getCode()
	 */
	public static int getCode(final IMarker marker) {
		return marker.getAttribute(CODE, DEFAULT_CODE);
	}

	/**
	 * Get the attribute identifying the source of the diagnostic.
	 *
	 * @param marker The marker
	 * @return The source or null if no such attribute is present.
	 * @see Diagnostic#getSource()
	 */
	public static String getSource(final IMarker marker) {
		return marker.getAttribute(SOURCE, null);
	}

	/**
	 * Get the attribute for the remaining diagnostic data.
	 *
	 * @param marker The marker
	 * @return The data or an empty array if no such attribute is present.
	 * @see Diagnostic#getData()
	 */
	public static String[] getData(final IMarker marker) {
		return marker.getAttribute(DATA, "").split("\u0000"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Get the attribute for the target URI to the originating model element.
	 *
	 * @param marker The marker
	 * @return The target URI or null if no such attribute is present.
	 * @see Diagnostic#getData()
	 */
	public static URI getTargetUri(final IMarker marker) {
		final String targetUriAttribute = marker.getAttribute(TARGET_URI, null);
		if (targetUriAttribute != null) {
			return URI.createURI(targetUriAttribute);
		}
		return null;
	}

	/**
	 * Get the attribute for the target type of the originating model element.
	 *
	 * @param marker The marker
	 * @return The target type or null if no valid attribute is present.
	 * @see Diagnostic#getData()
	 */
	public static EClass getTargetType(final IMarker marker) {
		final String targetTypeAttribute = marker.getAttribute(TARGET_TYPE, null);
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

	/**
	 * Get the attribute for the target feature of the originating model element.
	 *
	 * @param marker The marker
	 * @return The target feature or null if no valid attribute is present.
	 * @see Diagnostic#getData()
	 */
	public static EStructuralFeature getTargetFeature(final IMarker marker) {
		final String targetFeatureAttribute = marker.getAttribute(TARGET_FEATURE, null);
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

	/**
	 * Get the originating model element from the {@link TypeEntry#getType()}.
	 *
	 * @param marker The marker
	 * @return The target or null if no valid attribute is present.
	 * @see Diagnostic#getData()
	 * @implNote This can be a resource-intensive operation since it may force to
	 *           load the target file.
	 */
	public static EObject getTarget(final IMarker marker) {
		final URI targetUri = FordiacErrorMarker.getTargetUri(marker);
		if (targetUri != null && targetUri.isPlatformResource()) {
			final ResourceSet resourceSet = new ResourceSetImpl();
			return resourceSet.getEObject(targetUri, true);
		}
		return null;
	}

	/**
	 * Get the originating model element from the
	 * {@link TypeEntry#getTypeEditable()}.
	 *
	 * @param marker The marker
	 * @return The target or null if no valid attribute is present.
	 * @see Diagnostic#getData()
	 * @implNote This can be a resource-intensive operation since it may force to
	 *           load the target file.
	 */
	public static EObject getTargetEditable(final IMarker marker) {
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

	/**
	 * Get the originating model element relative to an existing root object.
	 *
	 * @param marker The marker
	 * @param root   The root element
	 * @return The target or null if no valid attribute is present.
	 * @see Diagnostic#getData()
	 */
	public static EObject getTargetRelative(final IMarker marker, final LibraryElement root) {
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

	/**
	 * Test whether the marker originates from a {@link FBNetworkElement}.
	 *
	 * @param marker The marker
	 * @return true if yes, false otherwise
	 */
	public static boolean markerTargetsFBNetworkElement(final IMarker marker) {
		return isTargetOfType(marker, LibraryElementPackage.eINSTANCE.getFBNetworkElement());
	}

	/**
	 * Test whether the marker originates from a {@link ErrorMarkerInterface}.
	 *
	 * @param marker The marker
	 * @return true if yes, false otherwise
	 */
	public static boolean markerTargetsErrorMarkerInterface(final IMarker marker) {
		return isTargetOfType(marker, LibraryElementPackage.eINSTANCE.getErrorMarkerInterface());
	}

	/**
	 * Test whether the marker originates from a {@link Connection}.
	 *
	 * @param marker The marker
	 * @return true if yes, false otherwise
	 */
	public static boolean markerTargetsConnection(final IMarker marker) {
		return isTargetOfType(marker, LibraryElementPackage.eINSTANCE.getConnection());
	}

	/**
	 * Test whether the marker originates from a {@link Value}.
	 *
	 * @param marker The marker
	 * @return true if yes, false otherwise
	 */
	public static boolean markerTargetsValue(final IMarker marker) {
		return isTargetOfType(marker, LibraryElementPackage.eINSTANCE.getValue());
	}

	/**
	 * Test whether the marker originates from specific type or one of its
	 * subclasses.
	 *
	 * @param marker The marker
	 * @param type   The type
	 * @return true if yes, false otherwise
	 */
	public static boolean isTargetOfType(final IMarker marker, final EClass type) {
		final EClass targetType = FordiacErrorMarker.getTargetType(marker);
		if (targetType != null) {
			return type.isSuperTypeOf(targetType);
		}
		return false;
	}

	private FordiacErrorMarker() {
		throw new UnsupportedOperationException();
	}
}
