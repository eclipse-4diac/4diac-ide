/*******************************************************************************
 * Copyright (c) 2011 - 2018 TU Wien ACIN, Profactor GmbH, fortiss GmbH, 
 * 								Johannes Kepler University Linz (JKU) 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import java.util.List;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Activator;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeAlgorithmCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateAlgorithmCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.DeleteECActionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures.ECAlgorithmToolTipFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.PreferenceGetter;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ComboCellEditorLocator;
import org.eclipse.fordiac.ide.gef.editparts.ComboDirectEditManager;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.INamedElementRenameEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Display;

public class ECActionAlgorithmEditPart extends AbstractDirectEditableEditPart {

	// the tooltip to be shown when we have an algorithm
	private ECAlgorithmToolTipFigure algToolTip;

	/** The adapter. */
	private final EContentAdapter adapter = new EContentAdapter() {

		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			refreshAlgLabel();
			refreshAlgorithmToolTip(getFigure());
		}
	};

	/** The algorithm adapter. */
	private final EContentAdapter fbAdapter = new EContentAdapter() {

		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			if (notification.getEventType() == Notification.SET && null != getAction().getAlgorithm() && 
					getAction().getAlgorithm().getName().equals(notification.getNewValue())) {
				refreshAlgLabel();
			}
		}
	};

	/** The property change listener. */
	private final IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getProperty().equals(PreferenceConstants.P_ECC_ALGORITHM_COLOR)) {
				getFigure().setBackgroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_ALGORITHM_COLOR));
			}
			if (event.getProperty().equals(PreferenceConstants.P_ECC_ALGORITHM_BORDER_COLOR)) {
				getFigure().setForegroundColor(
						PreferenceGetter.getColor(PreferenceConstants.P_ECC_ALGORITHM_BORDER_COLOR));
			}
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getAction().eAdapters().add(adapter);
			if (getINamedElement() != null) {
				//We don't want that the the base class registers to the algorithm as it is hard to track if algorithm changes
				getINamedElement().eAdapters().remove(getNameAdapter());
			}
			// addapt to the fbtype so that we get informed on alg name changes
			ECActionHelpers.getFBType(getAction()).eAdapters().add(fbAdapter);
			Activator.getDefault().getPreferenceStore().addPropertyChangeListener(propertyChangeListener);
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
			getAction().eAdapters().remove(adapter);
			FBType fbtype = ECActionHelpers.getFBType(getAction());
			if (fbtype != null) {
				fbtype.eAdapters().remove(fbAdapter);
			}
			Activator.getDefault().getPreferenceStore().removePropertyChangeListener(propertyChangeListener);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {
			@Override protected Command getDeleteCommand(final GroupRequest request) {
				return new DeleteECActionCommand(getAction());
			}
		});
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new INamedElementRenameEditPolicy() {
			@Override protected Command getDirectEditCommand(final DirectEditRequest request) {
				if (getHost() instanceof AbstractDirectEditableEditPart) {
					List<Algorithm> algorithms = ECActionHelpers.getAlgorithms(ECActionHelpers.getFBType(getAction()));
					int selected = ((Integer) request.getCellEditor().getValue()).intValue();
					Algorithm alg = null;
					if (selected < algorithms.size()) {
						alg = algorithms.get(selected);
					}
					return new ChangeAlgorithmCommand(getAction(), alg);
				}
				return null;
			}
			@Override protected void showCurrentEditValue(final DirectEditRequest request) {
				// handled by the direct edit manager
			}
		});
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy(){
			@Override protected Command getCreateCommand(final CreateRequest request) {
				if(request != null && null != request.getNewObject()) {
					Object object = request.getNewObject();
					if(getHost() instanceof ECActionAlgorithmEditPart){
						ECActionAlgorithmEditPart editPart = (ECActionAlgorithmEditPart) getHost();
						if(object instanceof STAlgorithm){
							return new CreateAlgorithmCommand(editPart.getBFB(), editPart.getAction());
						}
					}
				}
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractEditPart#performRequest(org.eclipse.gef
	 * .Request)
	 */
	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick

		if (request.getType() == RequestConstants.REQ_OPEN) {
			// transform doubleclick to direct edit
			request.setType(RequestConstants.REQ_DIRECT_EDIT);
		}
		super.performRequest(request);
	}

	/**
	 * Gets the manager.
	 * 
	 * @return the manager
	 */
	@Override
	public DirectEditManager getManager() {
		if (manager == null) {
			manager = new ComboDirectEditManager(this, ComboBoxCellEditor.class,
					new ComboCellEditorLocator(getNameLabel()), getNameLabel());
		}

		return manager;
	}

	/**
	 * performs the directEdit.
	 */
	@Override
	public void performDirectEdit() {
		List<String> algNames = ECActionHelpers.getAlgorithmNames(ECActionHelpers.getFBType(getAction()));		

		int selected = (getAction().getAlgorithm() != null) ? algNames.indexOf(getAction().getAlgorithm().getName())
				: algNames.size() - 1;

		((ComboDirectEditManager) getManager()).updateComboData(algNames);
		((ComboDirectEditManager) getManager()).setSelectedItem(selected);
		getManager().show();
	}

	public ECActionAlgorithm getCastedModel() {
		return (ECActionAlgorithm) getModel();
	}

	public ECAction getAction() {
		return getCastedModel().getAction();
	}
	
	public BasicFBType getBFB() {
		return (BasicFBType)getAction().eContainer().eContainer().eContainer();
	}

	@Override
	public INamedElement getINamedElement() {
		return getAction().getAlgorithm();
	}

	@Override
	public Label getNameLabel() {
		return (Label) getFigure();
	}

	@Override
	protected IFigure createFigure() {
		Label algorithmLabel = new Label() {

			@Override
			protected void paintFigure(Graphics graphics) {
				Display display = Display.getCurrent();
				Rectangle boundingRect = getBounds();

				Point topLeft = boundingRect.getTopLeft();
				Point bottomRight = boundingRect.getBottomRight();

				Color first = FigureUtilities.lighter(getBackgroundColor());
				Pattern pattern = new Pattern(display, topLeft.x, topLeft.y, bottomRight.x, bottomRight.y, first,
						getBackgroundColor());
				graphics.setBackgroundPattern(pattern);
				graphics.fillRectangle(boundingRect);
				graphics.setBackgroundPattern(null);
				pattern.dispose();
				first.dispose();
				graphics.translate(bounds.x, bounds.y);
				graphics.drawText(getSubStringText(), getTextLocation());
				graphics.translate(-bounds.x, -bounds.y);
			}
		};
		algorithmLabel.setBackgroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_ALGORITHM_COLOR));
		algorithmLabel.setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_ALGORITHM_BORDER_COLOR));
		algorithmLabel.setOpaque(true);
		algorithmLabel.setText(getAction().getAlgorithm() != null ? getAction().getAlgorithm().getName() : ""); //$NON-NLS-1$
		algorithmLabel.setBorder(new LineBorder() {
			@Override
			public Insets getInsets(final IFigure figure) {
				return new Insets(3, 6, 3, 6);
			}
		});
		algorithmLabel.setTextAlignment(PositionConstants.LEFT);
		algorithmLabel.setLabelAlignment(PositionConstants.LEFT);

		algToolTip = new ECAlgorithmToolTipFigure();
		refreshAlgorithmToolTip(algorithmLabel);

		return algorithmLabel;
	}

	private void refreshAlgLabel() {
		getNameLabel().setText(getAction().getAlgorithm() != null ? getAction().getAlgorithm().getName() : ""); //$NON-NLS-1$
	}

	private void refreshAlgorithmToolTip(IFigure iFigure) {
		if (null != algToolTip) {
			algToolTip.setAlgorithm(getCastedModel().getAction().getAlgorithm());
			if (null != getCastedModel().getAction().getAlgorithm()) {
				iFigure.setToolTip(algToolTip);
			} else {
				iFigure.setToolTip(null);
			}
		}
	}
}
