/*******************************************************************************
 * Copyright (c) 2008 - 2015 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - extracted from ServiceSequenceEditPart, cleanup
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.swt.SWT;

public class ServiceSequenceFigure extends Layer {
	private final Label nameLabel;
	private final Layer transactionContainer;

	public ServiceSequenceFigure() {
		nameLabel = new Label();
		transactionContainer = new Layer();
	}

	// avoid call of overridable method from constructor
	public void createVisuals() {
		final GridLayout sequenceLayout = new GridLayout();
		setLayoutManager(sequenceLayout);
		sequenceLayout.verticalSpacing = 0;
		nameLabel.setOpaque(true);
		nameLabel.setBackgroundColor(ColorConstants.lightGray);
		nameLabel.setLabelAlignment(PositionConstants.CENTER);

		final GridData nameLayoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
		getLayoutManager().setConstraint(nameLabel, nameLayoutData);
		add(nameLabel);

		final GridLayout containerLayout = new GridLayout();
		transactionContainer.setLayoutManager(containerLayout);
		containerLayout.verticalSpacing = 0;

		final GridData containerData = new GridData(SWT.FILL, SWT.NONE, true, false);
		getLayoutManager().setConstraint(transactionContainer, containerData);
		add(transactionContainer);
	}

	public Label getLabel() {
		return nameLabel;
	}

	public void setLabelText(final String name) {
		this.nameLabel.setText(null != name ? name : ""); //$NON-NLS-1$
	}

	public Layer getTransactionContainer() {
		return transactionContainer;
	}

}
