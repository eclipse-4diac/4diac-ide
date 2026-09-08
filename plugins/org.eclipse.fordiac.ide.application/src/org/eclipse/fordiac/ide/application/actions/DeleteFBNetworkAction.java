/*******************************************************************************
 * Copyright (c) 2012, 2024 fortiss GmbH, Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - added checks that element in groups are not double deleted
 *                 if the parent group is deleted as well
 *	Sebastian Hollersbacher - Added explosion
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.figures.ExplosionFigure;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.ui.editors.AdvancedScrollingGraphicalViewer;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceStoreProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;

/**
 * This special delete object will sort the commands that way that first the
 * connections are added and then the other objects.
 *
 */
public class DeleteFBNetworkAction extends DeleteAction {

	private static final int EXPLOSION_PADDING = 60;

	public DeleteFBNetworkAction(final IWorkbenchPart part) {
		super(part);
	}

	@Override
	public Command createDeleteCommand(final List<EditPart> selObjects) {
		if (selObjects.isEmpty()) {
			return null;
		}

		final HashSet<Group> groups = ((List<?>) selObjects).stream().filter(EditPart.class::isInstance)
				.map(EditPart.class::cast).map(EditPart::getModel).filter(Group.class::isInstance)
				.map(Group.class::cast).collect(Collectors.toCollection(HashSet::new));

		final List<EditPart> list = new ArrayList<>();

		// Resort list such that the connects are before any other edit parts
		for (final EditPart ep : selObjects) {
			if (ep instanceof ConnectionEditPart) {
				list.add(ep);
			}
		}

		for (final EditPart ep : selObjects) {
			if (!(ep instanceof ConnectionEditPart) && !isInGroupToBeDeleted(ep, groups)) {
				list.add(ep);
			}
		}

		return super.createDeleteCommand(list);
	}

	private static boolean isInGroupToBeDeleted(final Object object, final HashSet<Group> groups) {
		if (!(object instanceof final EditPart editPart
				&& editPart.getModel() instanceof final FBNetworkElement element)) {
			return false;
		}

		if (!element.isInGroup()) {
			return false;
		}

		return groups.contains(element.getGroup());
	}

	@Override
	public void run() {
		final var explosionEnabled = isExplosionEnabled(getSelectedEditParts().getFirst());

		if (!explosionEnabled) {
			super.run();
			return;
		}

		final List<Rectangle> explosionTargets = getExplosionTargets();
		super.run();
		createExplosions(explosionTargets);
	}

	private List<Rectangle> getExplosionTargets() {
		final List<Rectangle> explosionTargets = new ArrayList<>();
		for (final Object obj : getSelectedObjects()) {
			if (obj instanceof final AbstractGraphicalEditPart ep) {
				final IFigure figure = ep.getFigure();
				final Rectangle bounds = figure.getBounds().getCopy();
				figure.translateToAbsolute(bounds);
				explosionTargets.add(bounds);
			}
		}
		return explosionTargets;
	}

	private void createExplosions(final List<Rectangle> explosionTargets) {
		final RootEditPart rootEditPart = getSelectedEditParts().getFirst().getRoot();
		if (!explosionTargets.isEmpty() && rootEditPart instanceof final LayerManager layerManager) {
			final IFigure feedbackLayer = layerManager.getLayer(LayerConstants.FEEDBACK_LAYER);
			if (feedbackLayer == null) {
				return;
			}

			final Display display = Display.getCurrent();
			if (display == null || display.isDisposed()) {
				return;
			}

			for (final Rectangle absBounds : explosionTargets) {
				spawnExplosion(display, feedbackLayer, absBounds);
			}
		}
	}

	private static void spawnExplosion(final Display display, final IFigure feedbackLayer,
			final Rectangle absoluteBounds) {
		final Rectangle layerBounds = absoluteBounds.getCopy();
		feedbackLayer.translateToRelative(layerBounds);
		layerBounds.expand(EXPLOSION_PADDING, EXPLOSION_PADDING);

		final ExplosionFigure explosion = new ExplosionFigure(display);
		explosion.setBounds(layerBounds);
		feedbackLayer.add(explosion);

		explosion.start(() -> {
			if (feedbackLayer.getChildren().contains(explosion)) {
				feedbackLayer.remove(explosion);
			}
		});
	}

	private static boolean isExplosionEnabled(final EditPart editPart) {
		if (editPart.getViewer() instanceof final AdvancedScrollingGraphicalViewer viewer) {
			final IPreferenceStore store = PreferenceStoreProvider.getStore(GefPreferenceConstants.GEF_PREFERENCES_ID,
					viewer.getPreferencesCache().getProject());
			return store.getBoolean(GefPreferenceConstants.EXPLOSION_EFFECT_ON_DELETE);
		}
		return false;
	}
}