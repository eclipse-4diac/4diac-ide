/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH, Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - multline comments and cleanup
 *   Sebastian Hollersbacher - change to nebula NatTable
 *   Hesam Rezaee - Variable configuration for Global Constants
 *   Martin Jobst - add initial value cell editor support
 *   Dario Romano - fixed renaming bug for instances
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class InstancePropertySection extends AbstractInstanceSection {

	private static final int TWO_COLUMNS = 2;

	private MemberAccessViewer inputDataMemberAccessViewer;
	private MemberAccessViewer outputDataMemberAccessViewer;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		final Composite mainContainer = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(TWO_COLUMNS).equalWidth(true).applyTo(mainContainer);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(mainContainer);

		createFBInfoGroup(mainContainer);
		createTableSection(mainContainer);
	}

	@Override
	protected void performRefresh() {
		if (getType() != null) {
			super.performRefresh();

			if (getType() instanceof TypedSubApp) {
				// Member access for typed subapps currently not implemented
				inputDataMemberAccessViewer.setInput(null);
				outputDataMemberAccessViewer.setInput(null);
			} else {
				inputDataMemberAccessViewer.setInput(getType());
				outputDataMemberAccessViewer.setInput(getType());
			}
		}
	}

	protected void createTableSection(final Composite parent) {
		inputDataMemberAccessViewer = new MemberAccessViewer(true, this);
		inputDataMemberAccessViewer.createControls(parent, getWidgetFactory());

		outputDataMemberAccessViewer = new MemberAccessViewer(false, this);
		outputDataMemberAccessViewer.createControls(parent, getWidgetFactory());

		parent.layout();
	}

	protected void createFBInfoGroup(final Composite parent) {
		final Composite fbInfoGroup = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(TWO_COLUMNS).applyTo(fbInfoGroup);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, false).applyTo(fbInfoGroup);
		createNameInput(fbInfoGroup);

		final Composite fbCommentComp = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(TWO_COLUMNS).applyTo(fbCommentComp);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, false).applyTo(fbCommentComp);
		createCommentInput(fbCommentComp);
	}

	@Override
	protected Object getInputType(final Object input) {
		return InstanceSectionFilter.getFBNetworkElementFromSelectedElement(input);
	}

	protected final Adapter interfaceAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if (!notification.isTouch()) {
				notifiyRefresh();
				inputDataMemberAccessViewer.updateVisibility();
				outputDataMemberAccessViewer.updateVisibility();
			}
		}
	};

	protected final Adapter fbnElementAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			notifiyRefresh();
		}
	};

	@Override
	protected void addContentAdapter() {
		// for performance reasons (we could have many children) do not call super here.
		if (getType() != null) {
			getType().eAdapters().add(fbnElementAdapter);
			getType().getInterface().eAdapters().add(interfaceAdapter);
		}
	}

	@Override
	protected void removeContentAdapter() {
		// for performance reasons (we could have many children) do not call super here.
		if (getType() != null) {
			getType().eAdapters().remove(fbnElementAdapter);
			getType().getInterface().eAdapters().remove(interfaceAdapter);
		}
	}

	@Override
	protected boolean shouldRefresh() {
		// as we have our own adapters we need our own shouldRefresh implementation
		return (null != getType()) && getType().eAdapters().contains(fbnElementAdapter) && !blockRefresh;
	}
}
