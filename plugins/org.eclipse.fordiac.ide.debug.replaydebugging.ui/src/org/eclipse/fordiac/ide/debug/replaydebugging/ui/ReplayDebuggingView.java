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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.IReplayNavigatorRegistrationListener;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigatorManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;

/**
 * @brief View for replay debugging.
 *
 *        The view listen to replay navigators being registered or unregistered
 *        and creates or removes TimelineWidgets for each one of them. It
 *        organizes the widgets in collapsable composites grouped by devices.
 */
public class ReplayDebuggingView extends ViewPart implements IReplayNavigatorRegistrationListener {

	private final Map<String, CollapsableComposite> devices = new HashMap<>();
	private final Map<ReplayNavigator.Identifier, TimelineWidget> widgets = new HashMap<>();
	private Composite generalContainer;

	@Override
	public void createPartControl(final Composite parent) {
		// allow scrolling if there are too many TimelineWidgets
		final ScrolledComposite scrolledComposite = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.H_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		generalContainer = new Composite(scrolledComposite, SWT.NONE);
		generalContainer.setLayout(new GridLayout(1, false));

		scrolledComposite.setContent(generalContainer);
		scrolledComposite.setMinSize(generalContainer.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		generalContainer.addListener(SWT.Resize,
				e -> scrolledComposite.setMinSize(generalContainer.computeSize(SWT.DEFAULT, SWT.DEFAULT)));

		ReplayNavigatorManager.getDefault().addListener(this);

		generalContainer.addDisposeListener(e -> ReplayNavigatorManager.getDefault().removeListener(this));
	}

	@Override
	public void replayNavigatorRegistered(final ReplayNavigator replayNavigator) {
		Display.getDefault().asyncExec(() -> {

			// Get/create the composite for the device of the replay navigator,
			// which calls layout() to collapse or expand itself and the parent scrolled
			// composite
			final Composite deviceGroup = devices.computeIfAbsent(replayNavigator.getIdentifier().deviceName(),
					deviceName -> new CollapsableComposite(generalContainer, deviceName, collapsed -> {
						generalContainer.layout(true, true); // re-calculate the layout when the device contents are
																// collpased/expanded
						generalContainer.getParent().layout(true, true); // scrolledComposite
					})).getContentsParent();

			// Create a new TimelineWidget for the replay navigator and add it to the device
			// group
			final TimelineWidget widgetToAdd = new TimelineWidget(replayNavigator.getIdentifier().resourceName(),
					replayNavigator, deviceGroup, SWT.NO_BACKGROUND, collapsed -> {
						generalContainer.layout(true, true);
						generalContainer.getParent().layout(true, true); // scrolledComposite
					});
			widgetToAdd.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
			widgets.put(replayNavigator.getIdentifier(), widgetToAdd);

			generalContainer.layout(true, true);
		});
	}

	@Override
	public void replayNavigatorUnregistered(final ReplayNavigator replayNavigator) {
		Display.getDefault().asyncExec(() -> {
			final String deviceName = replayNavigator.getIdentifier().deviceName();

			final TimelineWidget widgetToRemove = widgets.remove(replayNavigator.getIdentifier());
			if (widgetToRemove != null) {
				widgetToRemove.dispose();
			}

			// remove device group if it has no widgets left
			if (devices.containsKey(deviceName) && devices.get(deviceName).isEmpty()) {
				devices.get(deviceName).dispose();
				devices.remove(deviceName);
			}
			generalContainer.layout(true, true);
		});
	}

	@Override
	public void setFocus() {
		// do nothing
	}
}
