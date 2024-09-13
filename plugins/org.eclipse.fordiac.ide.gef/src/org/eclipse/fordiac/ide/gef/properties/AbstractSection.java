/*******************************************************************************
 * Copyright (c) 2015, 2024 fortiss GmbH, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - cleaned command stack handling for property sections
 *               - extracted double column section for cleaner code
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModelListener;
import org.eclipse.fordiac.ide.model.data.provider.DataItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.emf.SingleRecursiveContentAdapter;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.provider.LibraryElementItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class AbstractSection extends AbstractPropertySection implements CommandExecutor {

	protected Object type;
	private CommandStack commandStack;
	private ComposedAdapterFactory adapterFactory;
	private Composite parent;

	private GraphicalAnnotationModel annotationModel;

	// block updates triggered by any command
	protected boolean blockRefresh = false;

	protected abstract EObject getType();

	protected abstract Object getInputType(Object input);

	protected abstract void setInputCode();

	protected abstract void setInputInit();

	/**
	 * Subclasses shall perform all actions to refresh the data in the property
	 * sheet.
	 *
	 * Implementors can assume that getType is not null and execute command has no
	 * effect.
	 */
	protected abstract void performRefresh();

	protected void performRefreshAnnotations() {
		// empty default
	}

	protected void setType(final Object input) {
		// as the property sheet is reused for different selection first remove
		// listening to the old element
		removeContentAdapter();
		type = getInputType(input);
		addContentAdapter();
	}

	public final TypeLibrary getTypeLibrary() {
		final EObject root = EcoreUtil.getRootContainer(getType());

		if (root instanceof final LibraryElement libEl) {
			return libEl.getTypeLibrary();
		}
		throw new IllegalStateException(
				"Could not determine root element for finding the typ lib for given element: " + getType()); //$NON-NLS-1$
	}

	public final DataTypeLibrary getDataTypeLib() {
		return getTypeLibrary().getDataTypeLibrary();
	}

	@SuppressWarnings("static-method") // this method should be overrideable by subclasses
	protected CommandStack getCommandStack(final IWorkbenchPart part, final Object input) {
		return part.getAdapter(CommandStack.class);
	}

	protected final void setCurrentCommandStack(final IWorkbenchPart part, final Object input) {
		this.commandStack = getCommandStack(part, input);
	}

	protected void setCurrentCommandStack(final CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	protected CommandStack getCurrentCommandStack() {
		return commandStack;
	}

	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		Object input = selection;
		if (selection instanceof final IStructuredSelection structSel) {
			input = structSel.getFirstElement();
		}
		setCurrentCommandStack(part, input);
		if (null == commandStack) { // disable all fields
			setInputCode();
		}
		removeAnnotationModelListener();
		annotationModel = part.getAdapter(GraphicalAnnotationModel.class);
		addAnnotationModelListener();
		setType(input);
		setInputInit();
	}

	private final Adapter contentAdapter = new SingleRecursiveContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if (!notification.isTouch()) {
				notifiyRefresh();
			}
		}
	};

	protected final void notifiyRefresh() {
		if (shouldRefresh()) {
			parent.getDisplay().asyncExec(() -> {
				if (!parent.isDisposed()) {
					refresh();
				}
			});
		}
	}

	protected final void notifiyRefreshAnnotations() {
		if (shouldRefresh()) {
			parent.getDisplay().asyncExec(() -> {
				if (!parent.isDisposed()) {
					refreshAnnotations();
				}
			});
		}
	}

	@Override
	public final void refresh() {
		if (getType() != null) {
			final CommandStack commandStackBuffer = commandStack;
			commandStack = null;
			performRefresh();
			commandStack = commandStackBuffer;
		}
	}

	public final void refreshAnnotations() {
		if (getType() != null) {
			final CommandStack commandStackBuffer = commandStack;
			commandStack = null;
			performRefreshAnnotations();
			commandStack = commandStackBuffer;
		}
	}

	protected boolean shouldRefresh() {
		return (null != getType()) && getType().eAdapters().contains(contentAdapter) && !blockRefresh;
	}

	@Override
	public void dispose() {
		removeContentAdapter();
		removeAnnotationModelListener();
		super.dispose();
	}

	protected void removeContentAdapter() {
		if ((getType() != null) && getType().eAdapters().contains(contentAdapter)) {
			getType().eAdapters().remove(contentAdapter);
		}
	}

	protected void addContentAdapter() {
		if ((null != getType()) && !getType().eAdapters().contains(contentAdapter)) {
			getType().eAdapters().add(contentAdapter);
		}
	}

	private final GraphicalAnnotationModelListener annotationModelListener = event -> notifiyRefreshAnnotations();

	protected void removeAnnotationModelListener() {
		if (annotationModel != null) {
			annotationModel.removeAnnotationModelListener(annotationModelListener);
		}
	}

	protected void addAnnotationModelListener() {
		if (annotationModel != null) {
			annotationModel.addAnnotationModelListener(annotationModelListener);
		}
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		this.parent = parent; // store the parent to be used in the content adapter
		parent.setLayout(new GridLayout(1, true));
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
	}

	protected Composite createComposite(final Composite parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		return composite;
	}

	@Override
	public void executeCommand(final Command cmd) {
		if ((null != type) && (null != commandStack) && (null != cmd) && cmd.canExecute()) {
			blockRefresh = true;
			commandStack.execute(cmd);
			blockRefresh = false;
		}
	}

	protected Text createGroupText(final Composite group, final boolean editable) {
		return createGroupText(group, editable, SWT.BORDER);
	}

	protected Text createGroupText(final Composite group, final boolean editable, final int style) {
		final Text text = getWidgetFactory().createText(group, "", style); //$NON-NLS-1$
		text.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		text.setEditable(editable);
		text.setEnabled(editable);
		return text;
	}

	protected ComposedAdapterFactory getAdapterFactory() {
		if (null == adapterFactory) {
			adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
			adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
			adapterFactory.addAdapterFactory(new LibraryElementItemProviderAdapterFactory());
			adapterFactory.addAdapterFactory(new DataItemProviderAdapterFactory());
			adapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
			adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		}
		return adapterFactory;
	}

	public GraphicalAnnotationModel getAnnotationModel() {
		return annotationModel;
	}

}
