/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.commands.change.ChangeSubAppBoundsCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeSubAppSizeLockCommand;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class SubAppPropertySection extends AbstractInstanceSection {

	private Text heightText;
	private Text widthText;
	private Button lockCheckbox;

	private static final int TEXT_INPUT_MAX_LENGTH = 5;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true).applyTo(parent);

		final Composite leftComposite = createComposite(parent);
		final Composite rightComposite = createComposite(parent);
		final GridData gridLayoutData = new GridData(GridData.FILL, GridData.FILL, true, false);
		parent.setLayoutData(gridLayoutData);
		createFBInfoGroup(leftComposite);
		createFBSizeGroup(rightComposite);
	}

	private void createFBInfoGroup(final Composite parent) {
		final Composite fbSizeContainer = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(fbSizeContainer);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(fbSizeContainer);

		createNameInput(fbSizeContainer);
		createCommentInput(fbSizeContainer);
	}

	private void createLockSizeCheckbox(final Composite parent) {
		lockCheckbox = getWidgetFactory().createButton(parent, null, SWT.CHECK);
		lockCheckbox.addSelectionListener(SelectionListener.widgetSelectedAdapter(_ -> {
			if (getType() != null) {
				removeContentAdapter();
				executeCommand(new ChangeSubAppSizeLockCommand(getType(), lockCheckbox.getSelection()));
				addContentAdapter();
			}
		}));
	}

	private void createFBSizeGroup(final Composite parent) {
		final Composite fbSizeContainer = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(fbSizeContainer);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(fbSizeContainer);

		getWidgetFactory().createLabel(fbSizeContainer, FordiacMessages.Height + ":"); //$NON-NLS-1$
		heightText = createGroupText(fbSizeContainer, true);
		heightText.setTextLimit(TEXT_INPUT_MAX_LENGTH);
		heightText.addModifyListener(_ -> {
			if (getType() != null) {
				final double newHeight;
				try {
					newHeight = CoordinateConverter.INSTANCE.screenToIEC61499(Integer.parseInt(heightText.getText()));
				} catch (final NumberFormatException exception) {
					return;
				}

				removeContentAdapter();
				executeCommand(new ChangeSubAppBoundsCommand(getType(), getType().getPosition(), getType().getWidth(),
						newHeight));
				addContentAdapter();
			}
		});

		heightText.addVerifyListener(e -> e.doit = e.text.chars().allMatch(Character::isDigit));

		getWidgetFactory().createLabel(fbSizeContainer, FordiacMessages.Width + ":"); //$NON-NLS-1$
		widthText = createGroupText(fbSizeContainer, true);
		widthText.setTextLimit(TEXT_INPUT_MAX_LENGTH);
		widthText.addModifyListener(_ -> {
			if (getType() != null) {
				final double newWidth;
				try {
					newWidth = CoordinateConverter.INSTANCE.screenToIEC61499(Integer.parseInt(widthText.getText()));
				} catch (final NumberFormatException exception) {
					return;
				}

				removeContentAdapter();
				executeCommand(new ChangeSubAppBoundsCommand(getType(), getType().getPosition(), newWidth,
						getType().getHeight()));
				addContentAdapter();
			}
		});

		widthText.addVerifyListener(e -> e.doit = e.text.chars().allMatch(Character::isDigit));

		getWidgetFactory().createLabel(fbSizeContainer, FordiacMessages.Subapp_Size_DisableAutoResize + ":"); //$NON-NLS-1$
		createLockSizeCheckbox(fbSizeContainer);
	}

	@Override
	protected void performRefresh() {
		super.performRefresh();
		heightText.setText(Integer.toString(CoordinateConverter.INSTANCE.iec61499ToScreen(getType().getHeight())));
		widthText.setText(Integer.toString(CoordinateConverter.INSTANCE.iec61499ToScreen(getType().getWidth())));
		lockCheckbox.setSelection(getType().isLocked());
	}

	@Override
	protected SubApp getType() {
		if (type instanceof final SubApp subApp) {
			return subApp;
		}
		return null;
	}

	@Override
	protected Object getInputType(final Object input) {
		return SubappPropertySectionFilter.getFBNetworkElementFromSelectedElement(input);
	}

}
