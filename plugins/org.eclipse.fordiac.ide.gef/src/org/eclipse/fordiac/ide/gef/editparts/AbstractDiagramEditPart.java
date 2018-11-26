/*******************************************************************************
 * Copyright (c) 2008 - 2017  Profactor GbmH, TU Wien ACIN, fortiss 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Ingo Hengy, Alois Zoitl, Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.gef.router.RouterUtil;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.SnapFeedbackPolicy;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

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
		IFigure newFigure = new FreeformLayer();
		newFigure.setBorder(new MarginBorder(10));
		newFigure.setLayoutManager(new FreeformLayout());
		newFigure.setOpaque(false);
		
		updateRouter(newFigure);

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
		updateGrid();
		showGrid();
		updateRuler();
	}

	private void updateRuler() {
		getViewer().setProperty(
				RulerProvider.PROPERTY_RULER_VISIBILITY,
				Boolean.valueOf(Activator.getDefault().getPreferenceStore().getBoolean(DiagramPreferences.SHOW_RULERS)));
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
				Activator
						.getDefault()
						.getPreferenceStore()
						.addPropertyChangeListener(
								getPreferenceChangeListener());
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
				Activator
						.getDefault()
						.getPreferenceStore()
						.removePropertyChangeListener(
								getPreferenceChangeListener());
			}
		}
	}

	protected void showGrid() {
		getViewer().setProperty(
				SnapToGrid.PROPERTY_GRID_VISIBLE,
				Boolean.valueOf(Activator.getDefault().getPreferenceStore().getBoolean(DiagramPreferences.SHOW_GRID)));
	}

	private IPropertyChangeListener listener;

	/**
	 * Gets the preference change listener.
	 * 
	 * @return the preference change listener
	 */
	public IPropertyChangeListener getPreferenceChangeListener() {
		if (listener == null) {
			listener = new IPropertyChangeListener() {
				@Override
				public void propertyChange(final PropertyChangeEvent event) {
					if (event.getProperty().equals(
							DiagramPreferences.SNAP_TO_GRID)) {
						// refresh();

					}
					if (event.getProperty().equals(
							DiagramPreferences.SHOW_GRID)) {
						showGrid();
					}
					if (event.getProperty().equals(
							DiagramPreferences.GRID_SPACING)) {
						updateGrid();
					}
					if (event.getProperty().equals(
							DiagramPreferences.SHOW_RULERS)) {
						updateRuler();
					}
					if (event.getProperty().equals(
							DiagramPreferences.CONNECTION_ROUTER)) {
						updateRouter(getFigure());
					}
				}
			};
		}
		return listener;
	}

	
	protected void updateRouter(IFigure figure) {
		ConnectionLayer connLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
		connLayer.setConnectionRouter(RouterUtil.getConnectionRouter(figure));
	}

	protected void updateGrid() {
		int gridSpacing = Activator.getDefault().getPreferenceStore()
				.getInt(DiagramPreferences.GRID_SPACING);
		
		getViewer().setProperty(SnapToGrid.PROPERTY_GRID_SPACING,
				new Dimension(gridSpacing, gridSpacing));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getAdapter(java.lang
	 * .Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(final Class key) {
		if (key == SnapToHelper.class) {
			List<SnapToGrid> snapStrategies = new ArrayList<SnapToGrid>();
			Boolean val = Activator.getDefault().getPreferenceStore()
					.getBoolean(DiagramPreferences.SNAP_TO_GRID);
			if (val != null && val.booleanValue()) {
				snapStrategies.add(new SnapToGrid(this));
			}

			if (snapStrategies.size() == 0) {
				return null;
			}
			if (snapStrategies.size() == 1) {
				return snapStrategies.get(0);
			}

			SnapToHelper ss[] = new SnapToHelper[snapStrategies.size()];
			for (int i = 0; i < snapStrategies.size(); i++) {
				ss[i] = snapStrategies.get(i);
			}
			return new CompoundSnapToHelper(ss);
		}
		return super.getAdapter(key);
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy("Snap Feedback", new SnapFeedbackPolicy()); //$NON-NLS-1$
	}

}
