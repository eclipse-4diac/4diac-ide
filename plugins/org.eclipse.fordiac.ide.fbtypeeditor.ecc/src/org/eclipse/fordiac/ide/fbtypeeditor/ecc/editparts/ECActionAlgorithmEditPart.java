/*******************************************************************************
 * Copyright (c) 2011 TU Wien ACIN, Profactor GmbH, fortiss GmbH,
 *                    Johannes Kepler University Linz (JKU)
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
 *               -  consistent dropdown menu edit, redesign ECC
 *   Alois Zoitl - modernized and reworked ECC look
 *   Tisya Gupta - extracted common EC action edit part base class
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeAlgorithmCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.ECCContentAndLabelProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures.ECAlgorithmToolTipFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.FBTypeEditorPreferenceConstants;
import org.eclipse.fordiac.ide.gef.editparts.ComboDirectEditManager;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

public class ECActionAlgorithmEditPart extends AbstractECActionElementEditPart {

	// the tooltip to be shown when we have an algorithm
	private ECAlgorithmToolTipFigure algToolTip;

	/** The algorithm adapter. */
	private final Adapter fbAdapter = new EContentAdapter() {

		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if ((notification.getEventType() == Notification.SET) && (null != getAction().getAlgorithm())
					&& getAction().getAlgorithm().equals(notification.getNewValue())) {
				refreshVisuals();
			}
		}
	};

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			// adapt to the fbtype so that we get informed on alg name changes
			final FBType fbType = ECCContentAndLabelProvider.getFBType(getAction());
			if (fbType != null) {
				fbType.eAdapters().add(fbAdapter);
			}
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			final FBType fbtype = ECCContentAndLabelProvider.getFBType(getAction());
			if (fbtype != null) {
				fbtype.eAdapters().remove(fbAdapter);
			}
		}
	}

	@Override
	protected DirectEditPolicy createDirectEditPolicy() {
		return new DirectEditPolicy() {
			@Override
			protected Command getDirectEditCommand(final DirectEditRequest request) {
				final List<Algorithm> algorithms = ECCContentAndLabelProvider
						.getAlgorithms(ECCContentAndLabelProvider.getFBType(getAction()));
				final int selected = ((Integer) request.getCellEditor().getValue()).intValue();
				Algorithm alg = null;
				if (selected < algorithms.size()) {
					alg = algorithms.get(selected);
				}
				return new ChangeAlgorithmCommand(getAction(), alg);
			}

			@Override
			protected void showCurrentEditValue(final DirectEditRequest request) {
				// handled by the direct edit manager
			}
		};
	}

	@Override
	public void performDirectEdit() {
		final List<String> algNames = ECCContentAndLabelProvider
				.getAlgorithmNames(ECCContentAndLabelProvider.getFBType(getAction()));

		final int selected = (getAction().getAlgorithm() != null) ? algNames.indexOf(getAction().getAlgorithm())
				: algNames.size() - 1;

		final ComboDirectEditManager editManager = createDirectEditManager();
		editManager.updateComboData(algNames);
		editManager.setSelectedItem(selected);
		editManager.show();
	}

	public ECActionAlgorithm getCastedModel() {
		return (ECActionAlgorithm) getModel();
	}

	@Override
	public ECAction getAction() {
		return getCastedModel().getAction();
	}

	public BasicFBType getBFB() {
		return getAction().getECState().getECC().getBasicFBType();
	}

	@Override
	protected void setupFigure(final Label label) {
		label.setBackgroundColor(FBTypeEditorPreferenceConstants.getEccAlgorithmColor());
		label.setForegroundColor(FBTypeEditorPreferenceConstants.getEccAlgorithmTextColor());
		label.setText(getAction().getAlgorithm() != null ? getAction().getAlgorithm() : ""); //$NON-NLS-1$

		algToolTip = new ECAlgorithmToolTipFigure();
		refreshAlgorithmToolTip(label);
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshAlgLabel();
		refreshAlgorithmToolTip(getFigure());
	}

	private void refreshAlgLabel() {
		final Label nameLabel = getNameLabel();
		if (nameLabel != null) {
			nameLabel.setText(getAction().getAlgorithm() != null ? getAction().getAlgorithm() : ""); //$NON-NLS-1$
		}
	}

	private void refreshAlgorithmToolTip(final IFigure iFigure) {
		if (null != algToolTip) {
			final Algorithm alg = getCastedModel().getAction().getAlgorithmModel();
			algToolTip.setAlgorithm(alg);
			if (null != alg) {
				iFigure.setToolTip(algToolTip);
			} else {
				iFigure.setToolTip(null);
			}
		}
	}
}
