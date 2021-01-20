/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Germany GmbH, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation and/or documentation
 *               - implemented first version of gotoMarker for FB markers
 *               - extracted breadcrumb based editor to model.ui
 *   Alois Zoitl, Bianca Wiesmayr, Michael Oberlehner, Lukas Wais, Daniel Lindhuber
 *     - initial implementation of breadcrumb navigation location
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.editors;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.model.helpers.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.ui.Activator;
import org.eclipse.fordiac.ide.model.ui.widgets.BreadcrumbWidget;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.INavigationLocationProvider;
import org.eclipse.ui.NavigationLocation;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

public abstract class AbstractBreadCrumbEditor extends MultiPageEditorPart
		implements CommandStackEventListener, ITabbedPropertySheetPageContributor, IGotoMarker,
		INavigationLocationProvider {

	private final Map<Object, Integer> modelToEditorNum = new HashMap<>();

	private BreadcrumbWidget breadcrumb;

	public BreadcrumbWidget getBreadcrumb() {
		return breadcrumb;
	}

	protected Map<Object, Integer> getModelToEditorNumMapping() {
		return modelToEditorNum;
	}

	@Override
	public void createPartControl(final Composite parent) {
		parent.setLayout(GridLayoutFactory.fillDefaults().equalWidth(true).spacing(0, 0).create());
		breadcrumb = new BreadcrumbWidget(parent);
		super.createPartControl(parent);
		((CTabFolder) getContainer()).setTabHeight(0); // we don't want the tabs to be seen
		initializeBreadcrumb();
		setInitialModel(getInitialModel());
	}

	private void initializeBreadcrumb() {
		getBreadcrumb().setContentProvider(createBreadcrumbContentProvider());
		getBreadcrumb().setLabelProvider(createBreadcrumbLabelProvider());
		breadcrumb.addSelectionChangedListener(
				event -> handleBreadCrumbSelection(((StructuredSelection) event.getSelection()).getFirstElement()));
	}

	protected void setInitialModel(Object model) {
		getBreadcrumb().setInput(model);
		final int pagenum = modelToEditorNum.computeIfAbsent(model, this::createEditor);
		if (-1 != pagenum) {
			setActivePage(pagenum);
		}
	}

	@Override
	protected Composite createPageContainer(final Composite parent) {
		final Composite pageContainer = new Composite(parent, SWT.NONE);
		pageContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		return pageContainer;
	}

	void handleBreadCrumbSelection(final Object element) {
		final int pagenum = modelToEditorNum.computeIfAbsent(element, this::createEditor);
		if (-1 != pagenum) {
			setActivePage(pagenum);
			getSite().getPage().getNavigationHistory().markLocation(this);
		}
	}

	private int createEditor(final Object model) {
		final EditorPart part = createEditorPart(model);
		if (null != part) {
			final IEditorInput input = createEditorInput(model);
			try {
				return addPage(part, input);
			} catch (final PartInitException e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
		}
		return -1;
	}



	@Override
	public boolean isDirty() {
		return ((null != getCommandStack()) && getCommandStack().isDirty());
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == CommandStack.class) {
			return adapter.cast(getCommandStack());
		}
		if (adapter == IGotoMarker.class) {
			return adapter.cast(this);
		}
		return super.getAdapter(adapter);
	}

	@Override
	public void stackChanged(final CommandStackEvent event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@Override
	public void dispose() {
		if (null != getCommandStack()) {
			getCommandStack().removeCommandStackEventListener(this);
		}
		super.dispose();
	}


	@Override
	public void gotoMarker(final IMarker marker) {
		try {
			final Map<String, Object> attrs = marker.getAttributes();
			if (FordiacMarkerHelper.markerTargetsFBNetworkElement(attrs)) {
				gotoFBNetworkElement(attrs.get(IMarker.LOCATION));
			}
		} catch (final CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}

	@Override
	public INavigationLocation createEmptyNavigationLocation() {
		return null;
	}

	@Override
	public INavigationLocation createNavigationLocation() {
		final Object modelItem = breadcrumb.getActiveItem().getModel();
		return (modelItem != null) ? new BreadcrumbNavigationLocation(this, modelItem) : null;
	}

	public abstract CommandStack getCommandStack();

	protected abstract void gotoFBNetworkElement(final Object object);

	protected abstract EditorPart createEditorPart(final Object model);

	protected abstract IEditorInput createEditorInput(final Object model);

	protected abstract AdapterFactoryContentProvider createBreadcrumbContentProvider();

	protected abstract AdapterFactoryLabelProvider createBreadcrumbLabelProvider();

	protected abstract Object getInitialModel();

	private static class BreadcrumbNavigationLocation extends NavigationLocation {

		private final Object model;

		protected BreadcrumbNavigationLocation(AbstractBreadCrumbEditor editorPart, Object model) {
			super(editorPart);
			this.model = model;
		}

		@Override
		public String getText() {
			final StringBuilder sb = new StringBuilder();
			generateItemPath(sb, model);
			return sb.substring(1);
		}

		private void generateItemPath(StringBuilder sb, Object model) {
			if (model == null) {
				return;
			}
			generateItemPath(sb, getEditorPart().getBreadcrumb().getContentProvider().getParent(model));
			sb.append("."); //$NON-NLS-1$
			sb.append(getEditorPart().getBreadcrumb().getLabelProvider().getText(model));
		}

		private Object getModel() {
			return model;
		}

		@Override
		protected AbstractBreadCrumbEditor getEditorPart() {
			return (AbstractBreadCrumbEditor) super.getEditorPart();
		}

		@Override
		public void saveState(IMemento memento) {
			// TODO Auto-generated method stub

		}

		@Override
		public void restoreState(IMemento memento) {
			// TODO Auto-generated method stub

		}

		@Override
		public void restoreLocation() {
			final IEditorPart part= getEditorPart();
			if (part instanceof AbstractBreadCrumbEditor) {
				final AbstractBreadCrumbEditor editor= getEditorPart();
				editor.getBreadcrumb().setInput(model);
			}
		}

		@Override
		public boolean mergeInto(INavigationLocation currentLocation) {
			if (currentLocation instanceof BreadcrumbNavigationLocation) {
				final Object model = ((BreadcrumbNavigationLocation) currentLocation).getModel();
				return this.model == model;
			}
			return false;
		}

		@Override
		public void update() {
			// TODO Auto-generated method stub

		}

	}
}
