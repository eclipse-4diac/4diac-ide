/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.widgets;

import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class PinInfoDataWidget extends PinInfoBasicWidget {

	private Text arraySizeText;
	private Text initValueText;

	public PinInfoDataWidget(final Composite parent, final TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, widgetFactory);
	}

	@Override
	protected void createWidget(final Composite parent) {
		super.createWidget(parent);
		widgetFactory.createCLabel(parent, FordiacMessages.ArraySize + ":"); //$NON-NLS-1$
		arraySizeText = createText(parent);

		widgetFactory.createCLabel(parent, FordiacMessages.InitialValue + ":");// $NON-NLS-1$
		initValueText = createText(parent);
	}

	public Text getArraySizeText() {
		return arraySizeText;
	}

	public Text getInitValueText() {
		return initValueText;
	}

	public void setArraySizeText(final Text arraySizeText) {
		this.arraySizeText = arraySizeText;
	}

	public void setInitValueText(final Text initValueText) {
		this.initValueText = initValueText;
	}

	@Override
	public void disableAllFields() {
		super.disableAllFields();
		arraySizeText.setEnabled(false);
		initValueText.setEnabled(false);
	}
}