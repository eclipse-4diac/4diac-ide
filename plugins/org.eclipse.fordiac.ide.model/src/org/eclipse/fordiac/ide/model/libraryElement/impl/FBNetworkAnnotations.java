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
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.core.resources.IProject;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionDimension;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.FBShapeHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Comment;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.preferences.ModelPreferenceConstants;
import org.eclipse.fordiac.ide.model.preferences.PreferenceProvider;
import org.eclipse.fordiac.ide.model.util.SpatialHash;
import org.eclipse.fordiac.ide.model.validation.ValidationPreferences;

final class FBNetworkAnnotations {
	/**
	 * Cell size for the spatial hash (in units of line height)
	 *
	 * @implNote Should be two times the average element size for optimal results
	 */
	private static final int CELL_SIZE = 10 * 100;

	/**
	 * Minimum grid size
	 */
	private static final int GRID_SIZE_MIN = 10;

	/**
	 * Maximum grid size
	 */
	private static final int GRID_SIZE_MAX = 1000;

	static boolean validateCollisions(final FBNetwork network, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		final Optional<FBNetworkElement> parent = Optional.ofNullable(network.eContainer())
				.filter(FBNetworkElement.class::isInstance).map(FBNetworkElement.class::cast);
		if (parent.filter(p -> p.getTypeEntry() != null).isPresent()) {
			return true; // ignore typed parent
		}

		return validateCollisions(parent.filter(FBNetworkAnnotations::isUnfoldedSubapp), network.getNetworkElements(),
				Predicate.not(FBNetworkElement::isInGroup), diagnostics, context, getRootProject(network));
	}

	static boolean validateCollisions(final Group group, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		return validateCollisions(Optional.of(group), group.getGroupElements(), unused -> true, diagnostics, context,
				getRootProject(group));
	}

	static boolean validateCollisions(final Optional<FBNetworkElement> parent,
			final List<FBNetworkElement> networkElements, final Predicate<FBNetworkElement> filter,
			final DiagnosticChain diagnostics, final Map<Object, Object> context, final IProject project) {
		boolean result = true;

		final double marginLeftRight = getIntPreference(context, ModelPreferenceConstants.MARGIN_LEFT_RIGHT, project, 0)
				* FBShapeHelper.IEC61499_LINE_HEIGHT;
		final double marginTopBottom = getIntPreference(context, ModelPreferenceConstants.MARGIN_TOP_BOTTOM, project, 0)
				* FBShapeHelper.IEC61499_LINE_HEIGHT;
		final int minInterfaceBarSize = getIntPreference(context, ModelPreferenceConstants.MIN_INTERFACE_BAR_SIZE,
				project, 40);
		final int maxInterfaceBarSize = getIntPreference(context, ModelPreferenceConstants.MAX_INTERFACE_BAR_SIZE,
				project, 40);
		// Interface Bar can not be calculated correctly due to TargetInterface
		// only check parent width collision if size is fixed
		final boolean checkParentWidth = minInterfaceBarSize == maxInterfaceBarSize;

		final int gridSize = Math.clamp(networkElements.size(), GRID_SIZE_MIN, GRID_SIZE_MAX);
		final SpatialHash<FBNetworkElement> spatialHash = new SpatialHash<>(CELL_SIZE, gridSize);

		final PrecisionDimension parentSize = parent.map(FBNetworkAnnotations::getParentSize).orElse(null);

		for (final FBNetworkElement element : networkElements) {
			if (element instanceof ErrorMarkerFBNElement || !filter.test(element)) {
				continue;
			}
			final Rectangle elementBounds = getElementBounds(element, marginLeftRight, marginTopBottom);

			// check parent collision
			if (parent.isPresent() && parentSize != null) {
				final boolean parentCollision = checkParentCollision(parent.get(), diagnostics, parentSize, element,
						elementBounds, checkParentWidth);
				result &= parentCollision;
			}
			// check sibling collision
			final Point bottomRight = elementBounds.getBottomRight();
			final Set<FBNetworkElement> collisions = spatialHash.put(elementBounds.x, elementBounds.y, bottomRight.x,
					bottomRight.y, element);
			if (diagnostics != null) {
				collisions.forEach(other -> diagnostics.add(createCollisionDiagnostic(element, other)));
			}
			result &= collisions.isEmpty();
		}
		return result;
	}

	private static boolean checkParentCollision(final FBNetworkElement parent, final DiagnosticChain diagnostics,
			final Dimension parentSize, final FBNetworkElement element, final Rectangle elementBounds,
			final boolean checkParentWidth) {
		final boolean widthCollision = checkParentWidth
				&& (elementBounds.x < 0 || elementBounds.x + elementBounds.width > parentSize.width);
		final boolean heightCollision = elementBounds.y < 0
				|| elementBounds.y + elementBounds.height > parentSize.height;
		if (diagnostics != null && (widthCollision || heightCollision)) {
			if (widthCollision) {
				diagnostics.add(createInterfaceBarCollisionDiagnostic(element, parent));
			} else {
				diagnostics.add(createCollisionDiagnostic(element, parent));
			}
		}
		return widthCollision || heightCollision;
	}

	private static PrecisionDimension getParentSize(final FBNetworkElement parent) {
		final PrecisionDimension parentSize = new PrecisionDimension(parent.getVisibleWidth(),
				parent.getVisibleHeight());
		if (parent instanceof SubApp) {
			parentSize.setPreciseHeight(parentSize.preciseHeight() - FBShapeHelper.getSubappHeightAdjust(parent));
			parentSize.setPreciseWidth(parentSize.preciseWidth() - FBShapeHelper.getSubappWidthAdjust(parent));
		}
		return parentSize;
	}

	private static Rectangle getElementBounds(final FBNetworkElement element, final double marginLeftRight,
			final double marginTopBottom) {
		final Position position = element.getPosition();
		final PrecisionRectangle bounds = new PrecisionRectangle(position.getX(), position.getY(),
				element.getVisibleWidth(), element.getVisibleHeight());

		if (!(element instanceof Comment)) {
			bounds.expand(marginLeftRight, marginTopBottom);
		}
		return bounds;
	}

	private static Diagnostic createCollisionDiagnostic(final FBNetworkElement element, final FBNetworkElement other) {
		return new BasicDiagnostic(
				ValidationPreferences.getDiagnosticSeverity(ValidationPreferences.COLLISION_SEVERITY,
						Diagnostic.WARNING, element),
				LibraryElementValidator.DIAGNOSTIC_SOURCE, LibraryElementValidator.FB_NETWORK__VALIDATE_COLLISIONS,
				MessageFormat.format(Messages.FBNetworkAnnotations_CollisionMessage, element.getName(),
						other.getName()),
				FordiacMarkerHelper.getDiagnosticData(element,
						LibraryElementPackage.Literals.POSITIONABLE_ELEMENT__POSITION));
	}

	private static Diagnostic createInterfaceBarCollisionDiagnostic(final FBNetworkElement element,
			final FBNetworkElement other) {
		return new BasicDiagnostic(
				ValidationPreferences.getDiagnosticSeverity(
						ValidationPreferences.RIGHT_INTERFACE_BAR_COLLISION_SEVERITY, Diagnostic.WARNING, element),
				LibraryElementValidator.DIAGNOSTIC_SOURCE, LibraryElementValidator.FB_NETWORK__VALIDATE_COLLISIONS,
				MessageFormat.format(Messages.FBNetworkAnnotations_InterfaceBarCollisionMessage, element.getName(),
						other.getName()),
				FordiacMarkerHelper.getDiagnosticData(element,
						LibraryElementPackage.Literals.POSITIONABLE_ELEMENT__POSITION));
	}

	private static IProject getRootProject(final EObject eObject) {
		if (eObject == null) {
			return null;
		}
		if (EcoreUtil.getRootContainer(eObject) instanceof final LibraryElement libElem
				&& libElem.getTypeLibrary() != null) {
			return libElem.getTypeLibrary().getProject();
		}
		return null;
	}

	private static int getIntPreference(final Map<Object, Object> context, final String name, final IProject project,
			final int defaultValue) {
		return ((Integer) context.computeIfAbsent(name, key -> internalGetIntPreference(key, project, defaultValue)))
				.intValue();
	}

	private static Integer internalGetIntPreference(final Object key, final IProject project, final int defaultValue) {
		return Integer.valueOf(PreferenceProvider.getInt(ModelPreferenceConstants.MODEL_PREFERENCES_ID, (String) key,
				defaultValue, project));
	}

	private static boolean isUnfoldedSubapp(final FBNetworkElement parent) {
		return parent instanceof final SubApp subApp && subApp.isUnfolded();
	}

	private FBNetworkAnnotations() {
		throw new UnsupportedOperationException();
	}
}
