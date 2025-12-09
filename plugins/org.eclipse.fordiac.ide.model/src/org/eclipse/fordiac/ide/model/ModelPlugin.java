/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model;

import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class ModelPlugin extends AbstractUIPlugin {

	// The shared instance
	private static ModelPlugin plugin;

	private static synchronized void setPlugin(final ModelPlugin instance) {
		plugin = instance;
	}

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		// defer initialization of CoordinateConverter until the DIAGRAM_FONT is loaded
		final Display display = Display.getDefault();
		if (display != null) {
			display.execute(() -> JFaceResources.getFontRegistry().addListener(new IPropertyChangeListener() {

				@Override
				public void propertyChange(final PropertyChangeEvent event) {
					if (UIPreferenceConstants.DIAGRAM_FONT.equals(event.getProperty())) {
						Display.getDefault().asyncExec(CoordinateConverter.INSTANCE::name);
						// we need an anonymous class instead of a lambda to refer to 'this' here
						JFaceResources.getFontRegistry().removeListener(this);
					}
				}
			}));
		} else {
			CoordinateConverter.INSTANCE.name();
		}
		setPlugin(this);
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		setPlugin(null);
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static ModelPlugin getDefault() {
		return plugin;
	}
}
