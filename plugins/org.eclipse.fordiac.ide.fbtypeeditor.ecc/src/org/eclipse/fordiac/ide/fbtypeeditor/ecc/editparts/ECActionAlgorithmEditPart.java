/*******************************************************************************
 * Copyright (c) 2011 - 2020 TU Wien ACIN, Profactor GmbH, fortiss GmbH,
 * 								Johannes Kepler University Linz (JKU)
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr
 *     -  consistent dropdown menu edit, redesign ECC
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import static org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.FBTypeEditorPreferenceConstants.MARGIN_HORIZONTAL;
import static org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.FBTypeEditorPreferenceConstants.MARGIN_VERTICAL;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Activator;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeAlgorithmCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateAlgorithmCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.DeleteECActionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.ECCContentAndLabelProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures.ECAlgorithmToolTipFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.FBTypeEditorPreferenceConstants;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ComboCellEditorLocator;
import org.eclipse.fordiac.ide.gef.editparts.ComboDirectEditManager;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
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
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.ComboBoxCellEditor;

public class ECActionAlgorithmEditPart extends AbstractDirectEditableEditPart {
	private static final Insets ALG_INSETS = new Insets(MARGIN_VERTICAL, MARGIN_HORIZONTAL, MARGIN_VERTICAL,
			MARGIN_HORIZONTAL);

	// the tooltip to be shown when we have an algorithm
	private ECAlgorithmToolTipFigure algToolTip;

	/** The adapter. */
	private final Adapter adapter = new AdapterImpl() {

		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			refreshAlgLabel();
			refreshAlgorithmToolTip(getFigure());
		}
	};

	/** The algorithm adapter. */
	private final Adapter fbAdapter = new EContentAdapter() {

		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if ((notification.getEventType() == Notification.SET) && (null != getAction().getAlgorithm())
					&& getAction().getAlgorithm().getName().equals(notification.getNewValue())) {
				refreshAlgLabel();
			}
		}
	};

	/** The property change listener. */
	private final IPropertyChangeListener propertyChangeListener = event -> {
		if (event.getProperty().equals(FBTypeEditorPreferenceConstants.P_ECC_ALGORITHM_COLOR)) {
			getFigure().setBackgroundColor(FBTypeEditorPreferenceConstants.getColor(FBTypeEditorPreferenceConstants.P_ECC_ALGORITHM_COLOR));
		}
		if (event.getProperty().equals(FBTypeEditorPreferenceConstants.P_ECC_ALGORITHM_TEXT_COLOR)) {
			getFigure().setForegroundColor(FBTypeEditorPreferenceConstants.getColor(FBTypeEditorPreferenceConstants.P_ECC_ALGORITHM_TEXT_COLOR));
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
				// We don't want that the the base class registers to the algorithm as it is
				// hard to track if algorithm changes
				getINamedElement().eAdapters().remove(getNameAdapter());
			}
			// addapt to the fbtype so that we get informed on alg name changes
			ECCContentAndLabelProvider.getFBType(getAction()).eAdapters().add(fbAdapter);
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
			final FBType fbtype = ECCContentAndLabelProvider.getFBType(getAction());
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
			@Override
			protected Command getDeleteCommand(final GroupRequest request) {
				return new DeleteECActionCommand(getAction());
			}
		});
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new DirectEditPolicy() {
			@Override
			protected Command getDirectEditCommand(final DirectEditRequest request) {
				if (getHost() instanceof AbstractDirectEditableEditPart) {
					final List<Algorithm> algorithms = ECCContentAndLabelProvider
							.getAlgorithms(ECCContentAndLabelProvider.getFBType(getAction()));
					final int selected = ((Integer) request.getCellEditor().getValue()).intValue();
					Algorithm alg = null;
					if (selected < algorithms.size()) {
						alg = algorithms.get(selected);
					}
					return new ChangeAlgorithmCommand(getAction(), alg);
				}
				return null;
			}

			@Override
			protected void showCurrentEditValue(final DirectEditRequest request) {
				// handled by the direct edit manager
			}
		});
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy() {
			@Override
			protected Command getCreateCommand(final CreateRequest request) {
				if ((request != null) && (null != request.getNewObject())) {
					final Object object = request.getNewObject();
					if (getHost() instanceof ECActionAlgorithmEditPart) {
						final ECActionAlgorithmEditPart editPart = (ECActionAlgorithmEditPart) getHost();
						if (object instanceof STAlgorithm) {
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

	@Override
	protected ComboDirectEditManager createDirectEditManager() {
		return new ComboDirectEditManager(this, ComboBoxCellEditor.class, new ComboCellEditorLocator(getNameLabel()),
				getNameLabel());
	}

	/**
	 * performs the directEdit.
	 */
	@Override
	public void performDirectEdit() {
		final List<String> algNames = ECCContentAndLabelProvider
				.getAlgorithmNames(ECCContentAndLabelProvider.getFBType(getAction()));

		final int selected = (getAction().getAlgorithm() != null) ? algNames.indexOf(getAction().getAlgorithm().getName())
				: algNames.size() - 1;

		final ComboDirectEditManager editManager = createDirectEditManager();
		editManager.updateComboData(algNames);
		editManager.setSelectedItem(selected);
		editManager.show();
	}

	public ECActionAlgorithm getCastedModel() {
		return (ECActionAlgorithm) getModel();
	}

	public ECAction getAction() {
		return getCastedModel().getAction();
	}

	public BasicFBType getBFB() {
		return getAction().getECState().getECC().getBasicFBType();
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
		final Label algorithmLabel = new Label();
		algorithmLabel.setBackgroundColor(FBTypeEditorPreferenceConstants.getColor(FBTypeEditorPreferenceConstants.P_ECC_ALGORITHM_COLOR));
		algorithmLabel.setForegroundColor(FBTypeEditorPreferenceConstants.getColor(FBTypeEditorPreferenceConstants.P_ECC_ALGORITHM_TEXT_COLOR));
		algorithmLabel.setOpaque(true);
		algorithmLabel.setText(getAction().getAlgorithm() != null ? getAction().getAlgorithm().getName() : ""); //$NON-NLS-1$
		algorithmLabel.setBorder(new MarginBorder(ALG_INSETS));
		algorithmLabel.setTextAlignment(PositionConstants.LEFT);
		algorithmLabel.setLabelAlignment(PositionConstants.LEFT);

		algToolTip = new ECAlgorithmToolTipFigure();
		refreshAlgorithmToolTip(algorithmLabel);

		return algorithmLabel;
	}

	private void refreshAlgLabel() {
		getNameLabel().setText(getAction().getAlgorithm() != null ? getAction().getAlgorithm().getName() : ""); //$NON-NLS-1$
	}

	private void refreshAlgorithmToolTip(final IFigure iFigure) {
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
