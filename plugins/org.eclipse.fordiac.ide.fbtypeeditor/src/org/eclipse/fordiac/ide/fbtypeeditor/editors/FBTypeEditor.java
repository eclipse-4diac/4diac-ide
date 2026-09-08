/*******************************************************************************
 * Copyright (c) 2008, 2025 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *
 *   Peter Gsellmann
 *     - incorporating simple fb
 *
 *   Daniel Lindhuber, Bianca Wiesmayr
 *     - cleanup
 *   Patrick Aigner
 *     - change dialog integration
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.search.dialog.AbstractTypeEntryDataHandler;
import org.eclipse.fordiac.ide.model.search.dialog.FBTypeEntryDataHandler;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typeeditor.AbstractTypeEditor;
import org.eclipse.fordiac.ide.ui.contentoutline.MultiPageEditorContentOutlinePage;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.INavigationLocationProvider;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class FBTypeEditor extends AbstractTypeEditor implements INavigationLocationProvider {

	private IContentOutlinePage contentOutline = null;

	@Override
	public void doSave(final IProgressMonitor monitor) {
		if (null != getTypeEntry()) {
			performPresaveHooks();
			super.doSave(monitor);
		}
	}

	@Override
	protected AbstractTypeEntryDataHandler<? extends TypeEntry> createTypeEntryDataHandler() {
		return new FBTypeEntryDataHandler(getTypeEntry());
	}

	private void performPresaveHooks() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IConfigurationElement[] config = registry
				.getConfigurationElementsFor("org.eclipse.fordiac.ide.fbtypeeditor.fBTEditorValidation"); //$NON-NLS-1$

		for (final IConfigurationElement e : config) {
			try {
				final Object o = e.createExecutableExtension("class"); //$NON-NLS-1$
				if (o instanceof final IFBTValidation fbtValidation) {
					fbtValidation.invokeValidation(getType());
				}
			} catch (final CoreException ex) {
				FordiacLogHelper.logError(ex.getMessage(), ex);
			}
		}
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == IContentOutlinePage.class) {
			return adapter.cast(getOutlinePage());
		}
		if (adapter == FBType.class) {
			return adapter.cast(getType());
		}
		if (adapter == IGotoMarker.class) {
			return adapter.cast(this);
		}
		return super.getAdapter(adapter);
	}

	protected IContentOutlinePage getOutlinePage() {
		if (null == contentOutline) {
			contentOutline = new MultiPageEditorContentOutlinePage(this, new FBTypeContentOutline(getType()));
		}
		return contentOutline;
	}

	@Override
	public String getContributorId() {
		return "property.contributor.fb"; //$NON-NLS-1$
	}

	@Override
	public INavigationLocation createEmptyNavigationLocation() {
		return null;
	}

	@Override
	public INavigationLocation createNavigationLocation() {
		return (getType() != null) ? new FBTypeNavigationLocation(this) : null;
	}

	@Override
	protected FBType getType() {
		return (FBType) super.getType();
	}

}
