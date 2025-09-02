/*******************************************************************************
 * Copyright (c) 2008, 2025 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - upgraded to AbstractSection, fixed refreshes and color drawing.
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.model.commands.change.ChangeBackgroundcolorCommand;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class AppearancePropertySection extends AbstractSection {
	private Color color;
	private Canvas colorLabel;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		final Composite composite = getWidgetFactory().createFlatFormComposite(parent);
		final FormLayout layout = (FormLayout) composite.getLayout();
		layout.spacing = 3;
		initializeControls(parent);
	}

	@Override
	protected void performRefresh() {
		if (getType().getColor() != null) {
			final org.eclipse.fordiac.ide.model.libraryElement.Color col = getType().getColor();
			color = new Color(new RGB(col.getRed(), col.getGreen(), col.getBlue()));
			colorLabel.setBackground(this.color);
		} else {
			color = new Color(new RGB(255, 255, 255));
			colorLabel.setBackground(this.color);
		}
	}

	protected void initializeControls(final Composite parent) {
		createColorsGroup(parent);
	}

	protected void createColorsGroup(final Composite parent) {
		final Group colorsGroup = getWidgetFactory().createGroup(parent,
				Messages.AppearancePropertySection_LABEL_Color);
		final GridLayout layout = new GridLayout(1, false);
		colorsGroup.setLayout(layout);
		// Start with Celtics green
		color = new Color(null, new RGB(255, 255, 255));
		// Use a label full of spaces to show the color
		colorLabel = new Canvas(colorsGroup, SWT.NONE);
		colorLabel.setBackground(color);
		colorLabel.addPaintListener(e -> {
			// directly use our color buffer here instead of the background color of the
			// canvas to avoid strange race conditions.
			e.gc.setBackground(color);
			e.gc.fillRectangle(colorLabel.getClientArea());
		});

		final Button chooseColorBtn = new Button(colorsGroup, SWT.PUSH);
		chooseColorBtn.setText(Messages.AppearancePropertySection_LABEL_BackgroundColor);
		chooseColorBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent event) {
				// Create the color-change dialog
				final ColorDialog dlg = new ColorDialog(colorsGroup.getShell());
				// Set the selected color in the dialog from
				// user's selected color
				dlg.setRGB(color.getRGB());
				// Change the title bar text
				dlg.setText(Messages.AppearancePropertySection_LABEL_ChooseColor);
				// Open the dialog and retrieve the selected color
				final RGB rgb = dlg.open();
				if (rgb != null) {
					executeCommand(new ChangeBackgroundcolorCommand(getType(), rgb));
					performRefresh();
				}
			}
		});

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(chooseColorBtn);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false)
				.hint(SWT.DEFAULT, chooseColorBtn.computeSize(SWT.DEFAULT, SWT.DEFAULT).y).applyTo(colorLabel);
	}

	@Override
	protected ColorizableElement getType() {
		return (ColorizableElement) type;
	}

	@Override
	protected Object getInputType(final Object input) {
		Object inputToUse = input;
		if (inputToUse instanceof final EditPart ep) {
			inputToUse = ep.getModel();
		}
		if (inputToUse instanceof final ColorizableElement colEl) {
			return colEl;
		}
		return null;
	}

	@Override
	protected void setInputCode() {
		// currently nothing to do here
	}

	@Override
	protected void setInputInit() {
		// currently nothing to do here
	}

}
