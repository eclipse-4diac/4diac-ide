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
package org.eclipse.fordiac.ide.gef.annotation;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.gef.editparts.Abstract4diacEditPartFactory;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.gef.EditPart;

public final class FordiacAnnotationUtil {

	public static boolean showOnTarget(final GraphicalAnnotation annotation) {
		final EStructuralFeature targetFeature = getTargetFeature(annotation);
		return targetFeature == null;
	}

	public static boolean showOnTargetName(final GraphicalAnnotation annotation) {
		final EStructuralFeature targetFeature = getTargetFeature(annotation);
		return targetFeature == LibraryElementPackage.eINSTANCE.getINamedElement_Name();
	}

	public static boolean showOnTargetPosition(final GraphicalAnnotation annotation) {
		final EStructuralFeature targetFeature = getTargetFeature(annotation);
		return targetFeature == LibraryElementPackage.eINSTANCE.getPositionableElement_Position();
	}

	public static boolean showOnTargetType(final GraphicalAnnotation annotation) {
		final EStructuralFeature targetFeature = getTargetFeature(annotation);
		return targetFeature == LibraryElementPackage.eINSTANCE.getTypedConfigureableObject_TypeEntry()
				|| targetFeature == LibraryElementPackage.eINSTANCE.getIInterfaceElement_Type()
				|| targetFeature == LibraryElementPackage.eINSTANCE.getAttribute_Type();
	}

	public static boolean showOnTargetValue(final GraphicalAnnotation annotation) {
		final EStructuralFeature targetFeature = getTargetFeature(annotation);
		return targetFeature == LibraryElementPackage.eINSTANCE.getAttribute_Value()
				|| targetFeature == LibraryElementPackage.eINSTANCE.getVarDeclaration_Value();
	}

	public static EStructuralFeature getTargetFeature(final GraphicalAnnotation annotation)
			throws IllegalArgumentException {
		final String targetFeatureAttribute = (String) annotation.getAttribute(FordiacErrorMarker.TARGET_FEATURE);
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

	public static GraphicalAnnotationModel getAnnotationModel(final EditPart editPart) {
		if (editPart.getViewer().getEditPartFactory() instanceof final Abstract4diacEditPartFactory factory
				&& factory.getEditor() != null) {
			return factory.getEditor().getAdapter(GraphicalAnnotationModel.class);
		}
		return null;
	}

	private FordiacAnnotationUtil() {
		throw new UnsupportedOperationException();
	}
}
