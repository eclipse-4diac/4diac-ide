/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerahrd Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.model.commands.change.ChangeBackgroundcolorCommand;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class AppearancePropertySection extends AbstractPropertySection {
	private ColorizableElement colorizableElement;
	private final List<ColorizableElement> selectedViews = new ArrayList<>();
	private Color color;
	protected Label colorLabel;
	protected Button chooseColorBtn;
	protected Group colorsGroup;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		Composite composite = getWidgetFactory().createFlatFormComposite(parent);
		FormLayout layout = (FormLayout) composite.getLayout();
		layout.spacing = 3;
		initializeControls(composite);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		super.setInput(part, selection);
		Assert.isTrue(selection instanceof IStructuredSelection);
		Object input = ((IStructuredSelection) selection).getFirstElement();
		if(input instanceof AbstractViewEditPart && ((AbstractViewEditPart)input).getModel() instanceof ColorizableElement) {
			colorizableElement = (ColorizableElement) ((AbstractViewEditPart)input).getModel();
		}else {
			if(input instanceof ColorizableElement) {
				colorizableElement = (ColorizableElement) input;
			}
		}
		selectedViews.clear();
		for (Iterator iterator = ((IStructuredSelection) selection).iterator(); iterator.hasNext();) {
			Object object = iterator.next();
			if (object instanceof AbstractViewEditPart
					&& ((AbstractViewEditPart) object).getModel() instanceof ColorizableElement) {
				selectedViews.add((ColorizableElement) ((AbstractViewEditPart) object).getModel());
			}
		}
		if (selectedViews.size() <= 1) {
			selectedViews.clear();
		}
	}

	@Override
	public void refresh() {
		if (colorizableElement != null && colorizableElement.getColor() != null) {
			org.eclipse.fordiac.ide.model.libraryElement.Color color = colorizableElement.getColor();
			this.color.dispose();
			this.color = new Color(null, new RGB(color.getRed(), color.getGreen(), color.getBlue()));
			colorLabel.setBackground(this.color);
		} else {
			this.color.dispose();
			this.color = new Color(null, new RGB(255, 255, 255));
			colorLabel.setBackground(this.color);
		}

		super.refresh();
	}

	protected void initializeControls(final Composite parent) {
		createColorsGroup(parent);
	}

	protected void createColorsGroup(final Composite parent) {
		colorsGroup = getWidgetFactory().createGroup(parent, Messages.AppearancePropertySection_LABLE_Color);
		GridLayout layout = new GridLayout(1, false);
		colorsGroup.setLayout(layout);
		// Start with Celtics green
		color = new Color(null, new RGB(255, 255, 255));
		// Use a label full of spaces to show the color
		colorLabel = getWidgetFactory().createLabel(colorsGroup, ""); //$NON-NLS-1$
		colorLabel.setText("          "); //$NON-NLS-1$
		colorLabel.setBackground(color);
		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		colorLabel.setLayoutData(gd);
		chooseColorBtn = new Button(colorsGroup, SWT.PUSH);
		chooseColorBtn.setText(Messages.AppearancePropertySection_LABEL_BackgroundColor);
		chooseColorBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent event) {
				// Create the color-change dialog
				ColorDialog dlg = new ColorDialog(colorsGroup.getShell());
				// Set the selected color in the dialog from
				// user's selected color
				dlg.setRGB(colorLabel.getBackground().getRGB());
				// Change the title bar text
				dlg.setText(Messages.AppearancePropertySection_LABEL_ChooseColor);
				// Open the dialog and retrieve the selected color
				RGB rgb = dlg.open();
				if (rgb != null) {
					// Dispose the old color, create the
					// new one, and set into the label
					Command cmd;
					if (selectedViews.size() > 0) {
						cmd = new CompoundCommand("Change Background Color");
						for (ColorizableElement colorizableElement : selectedViews) {
							ChangeBackgroundcolorCommand tmp = new ChangeBackgroundcolorCommand(colorizableElement,
									rgb);
							((CompoundCommand) cmd).add(tmp);
						}
					} else {
						cmd = new ChangeBackgroundcolorCommand(colorizableElement, rgb);
					}
					if (cmd.canExecute()) {
						SystemManager.INSTANCE.getCommandStack( ((SystemConfiguration) colorizableElement.eContainer()).getAutomationSystem() ).execute(cmd);
						color.dispose();
						color = new Color(null, rgb);
						colorLabel.setBackground(color);
					}
				}
			}
		});
	}
}
