/*******************************************************************************
 * Copyright (c) 2015 - 2022 fortiss GmbH, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - extracted from Abstract Section
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class AbstractDoubleColumnSection extends AbstractSection {

	public static final int WIDTH_OF_TEXT_HINT = 150;
	private Composite rightComposite;
	private Composite leftComposite;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(2, true));
		leftComposite = createComposite(parent);
		rightComposite = createComposite(parent);
		GridData gridLayoutData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridLayoutData.widthHint = WIDTH_OF_TEXT_HINT;
		parent.setLayoutData(gridLayoutData);
	}

	protected Composite getLeftComposite() {
		return leftComposite;
	}

	protected Composite getRightComposite() {
		return rightComposite;
	}

}
