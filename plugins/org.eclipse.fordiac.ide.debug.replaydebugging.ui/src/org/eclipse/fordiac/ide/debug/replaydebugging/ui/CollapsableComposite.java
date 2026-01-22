/*******************************************************************************
 * Copyright (c) 2025 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.replaydebugging.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

/**
 * @brief A composite that can be collapsed or expanded.
 *
 *        It contains a toggle button to collapse or expand the content. The
 *        content is inside a group which allows setting a title. A callback
 *        must be provided to handle the collapse state change, setting the
 *        layout accordingly in the parent composite.
 */
public class CollapsableComposite {

	@FunctionalInterface
	public interface CollapseListener {
		void onCollapse(boolean collapsed);
	}

	private static final String COLLAPSED_TEXT = "+"; //$NON-NLS-1$
	private static final String EXPANDED_TEXT = "-"; //$NON-NLS-1$

	private final Group group;
	private final Button toggleButton;
	private Composite content;

	public CollapsableComposite(final Composite parent, final String name, final CollapseListener listener) {
		group = new Group(parent, SWT.NONE);
		group.setText(name);
		group.setLayout(new GridLayout(1, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		toggleButton = new Button(group, SWT.TOGGLE);
		toggleButton.setText(EXPANDED_TEXT);
		toggleButton.addListener(SWT.Selection, e -> {
			final boolean collapsed = toggleButton.getSelection();
			for (final Control child : content.getChildren()) {
				child.setVisible(!collapsed);
				((GridData) child.getLayoutData()).exclude = collapsed;
			}
			content.setVisible(!collapsed);
			((GridData) content.getLayoutData()).exclude = collapsed;
			toggleButton.setText(collapsed ? COLLAPSED_TEXT : EXPANDED_TEXT);
			content.layout(true, true);
			group.layout(true, true);
			listener.onCollapse(collapsed);
		});

		content = new Composite(group, SWT.NONE);
		content.setLayout(new GridLayout(1, false));
		content.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
	}

	public boolean isEmpty() {
		return content.getChildren().length == 0;
	}

	public void dispose() {
		group.dispose();
	}

	public Composite getContentsParent() {
		return content;
	}
}