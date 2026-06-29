/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation.preferences;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class OCLValidationPropertyPage extends PropertyPage {

	private BooleanFieldEditor enableOclBuilderEditor;

	@Override
	protected Control createContents(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(composite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(composite);

		enableOclBuilderEditor = new BooleanFieldEditor(PreferenceConstants.ENABLE_OCL_VALIDATION_BUILDER,
				"Enable project OCL validation builder", composite); //$NON-NLS-1$
		enableOclBuilderEditor.setPreferenceStore(getPreferenceStore());
		enableOclBuilderEditor.load();
		return composite;
	}

	@Override
	protected void performDefaults() {
		enableOclBuilderEditor.loadDefault();
		super.performDefaults();
	}

	@Override
	public boolean performOk() {
		enableOclBuilderEditor.store();
		if (getPreferenceStore() instanceof final ScopedPreferenceStore scopedPreferenceStore) {
			try {
				scopedPreferenceStore.save();
			} catch (final IOException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
				return false;
			}
		}
		return super.performOk();
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		final ScopedPreferenceStore store = new ScopedPreferenceStore(new ProjectScope(getProject()),
				PreferenceConstants.VALIDATION_PREFERENCES_ID);
		store.setDefault(PreferenceConstants.ENABLE_OCL_VALIDATION_BUILDER,
				PreferenceConstants.DEFAULT_ENABLE_OCL_VALIDATION_BUILDER);
		return store;
	}

	private IProject getProject() {
		return Adapters.adapt(getElement(), IProject.class);
	}
}
