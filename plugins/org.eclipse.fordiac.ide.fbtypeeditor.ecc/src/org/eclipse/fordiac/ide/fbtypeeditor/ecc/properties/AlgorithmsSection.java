/*******************************************************************************
 * Copyright (c) 2015, 2024 fortiss GmbH, Johannes Kepler University Linz (JKU)
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Daniel Lindhuber - added copy and paste
 *   Bianca Wiesmayr - flattened hierarchy
 *   Alois Zoitl - turned AlgorithmSection into a read only
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class AlgorithmsSection extends AbstractSection {
	private final AlgorithmGroup algorithmGroup = new AlgorithmGroup();
	private AlgorithmList algorithmList;

	@Override
	protected BasicFBType getType() {
		return (BasicFBType) type;
	}

	@Override
	protected Object getInputType(final Object input) {
		return ECCSection.getECCInputType(input);
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createAlgorithmControls(parent);
	}

	public void createAlgorithmControls(final Composite parent) {
		final SashForm view = new SashForm(parent, SWT.HORIZONTAL | SWT.SMOOTH);
		view.setLayout(new FillLayout());
		algorithmList = new AlgorithmList(view, getWidgetFactory());

		getAlgorithmList().getViewer().addSelectionChangedListener(event -> {
			final Object selection = ((IStructuredSelection) getAlgorithmList().getViewer().getSelection())
					.getFirstElement();
			algorithmGroup.setAlgorithm((selection instanceof final Algorithm alg) ? alg : null);
		});

		final Composite algComposite = getWidgetFactory().createComposite(view);
		algComposite.setLayout(new GridLayout());
		view.setWeights(1, 1);
		view.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		algorithmGroup.createControls(algComposite, getWidgetFactory());
	}

	@Override
	protected void setInputCode() {
		// no action needed
	}

	@Override
	protected void setInputInit() {
		getAlgorithmList().initialize(getType());
	}

	private AlgorithmList getAlgorithmList() {
		return algorithmList;
	}

	@Override
	protected void performRefresh() {
		getAlgorithmList().refresh();
	}

}
