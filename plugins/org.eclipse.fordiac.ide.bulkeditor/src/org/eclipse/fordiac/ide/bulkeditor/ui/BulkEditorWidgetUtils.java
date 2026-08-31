/*******************************************************************************
 * Copyright (c) 2025, 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.ui;

import java.util.function.Consumer;

import org.eclipse.fordiac.ide.bulkeditor.Messages;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.Twistie;

public class BulkEditorWidgetUtils {

	private BulkEditorWidgetUtils() {
		// utility class
	}

	public static Group createCollapsibleGroup(final Composite parent, final String groupLabel,
			final Consumer<Button> clearButtonProvider) {
		final Composite groupComposite = new Composite(parent, SWT.NONE);
		groupComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		final var groupCompositeLayout = new GridLayout(1, false);
		groupCompositeLayout.verticalSpacing = 0;
		groupCompositeLayout.marginWidth = 0;
		groupComposite.setLayout(groupCompositeLayout);

		final Composite header = new Composite(groupComposite, SWT.NONE);
		header.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		header.setLayout(new GridLayout(clearButtonProvider != null ? 3 : 2, false));

		final Group searchGroup = new Group(groupComposite, SWT.NONE);
		searchGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		searchGroup.setLayout(GridLayoutFactory.swtDefaults().numColumns(1).create());

		final Label titleLabel = new Label(header, SWT.NONE);
		titleLabel.setText(groupLabel);
		final Twistie expandCompositeTwistie = new Twistie(header, SWT.NONE);
		final Button clearButton;
		if (clearButtonProvider != null) {
			clearButton = WidgetFactory.button(SWT.PUSH)
					.image(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ETOOL_CLEAR))
					.tooltip(Messages.ClearFilter).create(header);
			clearButtonProvider.accept(clearButton);
		} else {
			clearButton = null;
		}

		expandCompositeTwistie.setExpanded(true);
		expandCompositeTwistie.addListener(SWT.MouseUp, _ -> {
			final boolean isVisible = expandCompositeTwistie.isExpanded();
			if (clearButton != null) {
				clearButton.setVisible(isVisible);
			}
			updateVisibility(isVisible, searchGroup);
		});

		return searchGroup;
	}

	public static void updateVisibility(final boolean visible, final Composite composite) {
		composite.setVisible(visible);
		((GridData) composite.getLayoutData()).exclude = !visible;

		Composite current = composite.getParent();
		while (current != null && !(current.getParent() instanceof ScrolledComposite)) {
			current = current.getParent();
		}
		if (current != null) {
			final ScrolledComposite scrolledParentComposite = (ScrolledComposite) current.getParent();
			scrolledParentComposite.setMinSize(current.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			current.layout();
		}
		composite.getParent().layout();
	}
}
