/*******************************************************************************
 * Copyright (c) 2008 - 2017  Profactor GbmH, TU Wien ACIN, fortiss
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Ingo Hengy, Alois Zoitl, Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.SnapFeedbackPolicy;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.jface.util.IPropertyChangeListener;

/**
 * The Class AbstractDiagramEditPart.
 *
 * @author Gerhard Ebenhofer, gerhard.ebenhofer@profactor.at
 */
public abstract class AbstractDiagramEditPart extends AbstractGraphicalEditPart {

	/**
	 * Creates the <code>Figure</code> to be used as this part's <i>visuals</i>.
	 *
	 * @return a figure
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		final IFigure newFigure = new FreeformLayer();
		newFigure.setBorder(new MarginBorder(10));
		newFigure.setLayoutManager(new FreeformLayout());
		newFigure.setOpaque(false);

		final ConnectionLayer connLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
		connLayer.setConnectionRouter(createConnectionRouter(newFigure));

		return newFigure;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		showGrid();
		updateRuler();
	}

	private void updateRuler() {
		getViewer().setProperty(RulerProvider.PROPERTY_RULER_VISIBILITY,
				Boolean.valueOf(GefPreferenceConstants.STORE.getBoolean(GefPreferenceConstants.SHOW_RULERS)));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			if (getPreferenceChangeListener() != null) {
				GefPreferenceConstants.STORE.addPropertyChangeListener(getPreferenceChangeListener());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#deactivate()
	 */
	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			if (getPreferenceChangeListener() != null) {
				GefPreferenceConstants.STORE.removePropertyChangeListener(getPreferenceChangeListener());
			}
		}
	}

	protected void showGrid() {
		getViewer().setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE,
				Boolean.valueOf(GefPreferenceConstants.STORE.getBoolean(GefPreferenceConstants.SHOW_GRID)));
	}

	private IPropertyChangeListener listener;

	/**
	 * Gets the preference change listener.
	 *
	 * @return the preference change listener
	 */
	public IPropertyChangeListener getPreferenceChangeListener() {
		if (listener == null) {
			listener = event -> {
				if (event.getProperty().equals(GefPreferenceConstants.SHOW_GRID)) {
					showGrid();
				}
				if (event.getProperty().equals(GefPreferenceConstants.SHOW_RULERS)) {
					updateRuler();
				}
			};
		}
		return listener;
	}

	@SuppressWarnings("static-method") // allow children to overwrite and create a different router
	protected ConnectionRouter createConnectionRouter(final IFigure figure) {
		return new BendpointConnectionRouter();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getAdapter(java.lang
	 * .Class)
	 */
	@Override
	public <T> T getAdapter(final Class<T> key) {
		if (key == SnapToHelper.class) {
			final List<SnapToGrid> snapStrategies = new ArrayList<>();
			if (GefPreferenceConstants.STORE.getBoolean(GefPreferenceConstants.SNAP_TO_GRID)) {
				snapStrategies.add(new SnapToGrid(this));
			}

			if (snapStrategies.isEmpty()) {
				return null;
			}
			if (snapStrategies.size() == 1) {
				return key.cast(snapStrategies.get(0));
			}

			final SnapToHelper[] ss = new SnapToHelper[snapStrategies.size()];
			snapStrategies.toArray(ss);
			return key.cast(new CompoundSnapToHelper(ss));
		}
		return super.getAdapter(key);
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy("Snap Feedback", new SnapFeedbackPolicy()); //$NON-NLS-1$
	}

}
