/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Germany GmbH, Johannes Kepler University Linz
 * 				 2022 Primetals Technologies Austria GmbH
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
 *   Michael Oberlehner, Alois Zoitl
 *               - implemented save and restore state
 *   Daniel Lindhuber - connection auto layout
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.editors;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.model.ConnectionLayoutTagger;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.ui.Messages;
import org.eclipse.fordiac.ide.model.ui.widgets.BreadcrumbWidget;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.editors.AbstractCloseAbleFormEditor;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.INavigationLocationProvider;
import org.eclipse.ui.IPersistableEditor;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

public abstract class AbstractBreadCrumbEditor extends AbstractCloseAbleFormEditor
implements CommandStackEventListener, ITabbedPropertySheetPageContributor, IGotoMarker,
INavigationLocationProvider, IPersistableEditor {

	private static final String TAG_BREADCRUMB_HIERACHY = "FORDIAC_BREADCRUMB_HIERACHY"; //$NON-NLS-1$
	private static final String TAG_GRAPHICAL_VIEWER_ZOOM = "FORDIAC_GRAPHICAL_VIEWER_ZOOM"; //$NON-NLS-1$
	private static final String TAG_GRAPHICAL_VIEWER_HOR_SCROLL = "FORDIAC_GRAPHICAL_VIEWER_HOR_SCROLL"; //$NON-NLS-1$
	private static final String TAG_GRAPHICAL_VIEWER_VER_SCROLL = "FORDIAC_GRAPHICAL_VIEWER_VER_SCROLL"; //$NON-NLS-1$

	@SuppressWarnings("unchecked")
	private final CommandStackEventListener listener = event -> {
		if (event.isPostChangeEvent() && isConnectionLayoutPreferenceTicked()) {
			/*
			 * FBNetworkElementSetPositionCommand
			 * ResizeGroupOrSubappCommand
			 * AbstractConnectionCreateCommand
			 * ToggleSubAppRepresentationCommand
			 * AbstractChangeContainerBoundsCommand
			 * AbstractCreateFBNetworkElementCommand
			 * ChangeFBNetworkElementName
			 * ChangeSubAppIENameCommand
			 * AbstractUpdateFBNElementCommand
			 */
			if (event.getCommand() instanceof ConnectionLayoutTagger
					|| (event.getCommand() instanceof CompoundCommand
							&& ((CompoundCommand) event.getCommand()).getCommands().stream().anyMatch(ConnectionLayoutTagger.class::isInstance))) {
				getActiveEditor().getAdapter(GraphicalViewer.class).flush();
				triggerConnectionLayout();
			}
		}
	};

	private Map<Object, Integer> modelToEditorNum = new HashMap<>();

	private BreadcrumbWidget breadcrumb;
	// the memento we got for recreating the editor state
	private IMemento memento;


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
		String itemPath = null;
		if (null != memento) {
			itemPath = memento.getString(TAG_BREADCRUMB_HIERACHY);
		}
		setInitialModel(getInitialModel(itemPath));
		memento = null;
		// only add the selection change listener when our editor is full up
		breadcrumb.addSelectionChangedListener(
				event -> handleBreadCrumbSelection(((StructuredSelection) event.getSelection()).getFirstElement()));
		getCommandStack().addCommandStackEventListener(listener);
	}

	private void initializeBreadcrumb() {
		getBreadcrumb().setContentProvider(createBreadcrumbContentProvider());
		getBreadcrumb().setLabelProvider(createBreadcrumbLabelProvider());
	}

	protected void setInitialModel(final Object model) {
		getBreadcrumb().setInput(model);
		final int pagenum = modelToEditorNum.computeIfAbsent(model, this::createEditor).intValue();
		if (-1 != pagenum) {
			setActivePage(pagenum);
			final GraphicalViewer viewer = getEditor(pagenum).getAdapter(GraphicalViewer.class);
			if ((null != viewer) && (null != memento)) {
				restoreGraphicalViewerState(viewer, memento);
			}
		}
	}

	@Override
	protected Composite createPageContainer(final Composite parent) {
		final Composite pageContainer = new Composite(parent, SWT.NONE);
		pageContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		return pageContainer;
	}

	protected void handleBreadCrumbSelection(final Object element) {
		final int pagenum = modelToEditorNum.computeIfAbsent(element, this::createEditor).intValue();
		if (-1 != pagenum) {
			setActivePage(pagenum);
			getSite().getPage().getNavigationHistory().markLocation(getNavigationLocationProvider());
		}
	}

	protected IEditorPart getNavigationLocationProvider() {
		// if the breadcrumb editor is embedded into an other editor part this should help
		if (getSite().getPart() instanceof IEditorPart) {
			return (IEditorPart) getSite().getPart();
		}
		return this;
	}

	private int createEditor(final Object model) {
		final EditorPart part = createEditorPart(model);
		if (null != part) {
			final IEditorInput input = createEditorInput(model);
			try {
				return addPage(part, input);
			} catch (final PartInitException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}
		return -1;
	}

	@Override
	public boolean closeChildEditor(final IEditorPart childEditor) {
		final int pageIndex = pages.indexOf(childEditor);
		if (pageIndex != -1) {
			removePage(pageIndex);
			updateEditorMapping(pageIndex);
			return true;
		}
		return false;
	}

	private void updateEditorMapping(final int pageIndex) {
		final Map<Object, Integer> newModelToEditorNum = new HashMap<>();

		modelToEditorNum.forEach((obj, index) -> {
			final int intIndex = index.intValue();
			if (intIndex != pageIndex) {
				if (intIndex < pageIndex) {
					// keep the old index entry
					newModelToEditorNum.put(obj, Integer.valueOf(intIndex));
				} else {
					// we where before the removed entry so our index is one lower
					newModelToEditorNum.put(obj, Integer.valueOf(intIndex - 1));
				}
			}
		});

		modelToEditorNum = newModelToEditorNum;
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
			return adapter.cast(getNavigationLocationProvider());
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
			getCommandStack().removeCommandStackEventListener(listener);
			getCommandStack().removeCommandStackEventListener(this);
		}
		super.dispose();
	}


	@Override
	public void gotoMarker(final IMarker marker) {
		if (FordiacMarkerHelper.markerTargetsFBNetworkElement(marker)) {
			gotoFBNetworkElement(marker.getAttribute(IMarker.LOCATION, null));
		} else if (FordiacMarkerHelper.markerTargetsConnection(marker)) {
			gotoConnection(marker);
		} else if (FordiacMarkerHelper.markerTargetsValue(marker)) {
			gotoValue(marker);
		}
	}

	protected void gotoConnection(final IMarker marker) {
		final ErrorMarkerRef errorRef = ErrorMarkerBuilder.getMarkerRef(marker);
		if (errorRef instanceof ErrorMarkerInterface) {
			final FBNetworkElement parent = ((ErrorMarkerInterface) errorRef).getFBNetworkElement();
			selectErrorRef(errorRef, parent);
		} else if (errorRef instanceof Connection) {
			final EObject toView = errorRef.eContainer().eContainer();
			final IEditorPart editor = HandlerHelper.openEditor(toView);
			final Connection conn = (Connection) errorRef;
			if (conn.getSourceElement() != null) {
				HandlerHelper.selectElement(((Connection) errorRef).getSourceElement(), editor);
			} else if (conn.getDestinationElement() != null) {
				HandlerHelper.selectElement(((Connection) errorRef).getDestinationElement(), editor);
			}
			HandlerHelper.selectElement(errorRef, editor);
		}

	}

	protected void gotoValue(final IMarker marker) {
		final ErrorMarkerRef errorRef = ErrorMarkerBuilder.getMarkerRef(marker);
		final FBNetworkElement parent = errorRef instanceof Value
				? ((Value) errorRef).getParentIE().getFBNetworkElement()
						: null;
		selectErrorRef(errorRef, parent);
	}

	private void selectErrorRef(final ErrorMarkerRef errorRef, final FBNetworkElement parent) {
		if (null != parent) {
			final EObject toView = parent.eContainer().eContainer();
			getBreadcrumb().setInput(toView);
			HandlerHelper.selectElement(errorRef, getActiveEditor());
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

	@Override
	public void saveState(final IMemento memento) {
		final StringBuilder itemPath = new StringBuilder();
		BreadcrumbNavigationLocation.generateItemPath(itemPath, getBreadcrumb().getActiveItem().getModel(),
				getBreadcrumb().getContentProvider(), getBreadcrumb().getLabelProvider());
		memento.putString(TAG_BREADCRUMB_HIERACHY, itemPath.substring(1));

		final GraphicalViewer viewer = getActiveEditor().getAdapter(GraphicalViewer.class);
		if (null != viewer) {
			// we have a graphical viewer store its state
			saveGraphicalViewerState(viewer, memento);
		}
	}

	protected static void saveGraphicalViewerState(final GraphicalViewer viewer, final IMemento memento) {
		if (viewer.getRootEditPart() instanceof ScalableFreeformRootEditPart) {
			memento.putFloat(TAG_GRAPHICAL_VIEWER_ZOOM,
					(float)
					((ScalableFreeformRootEditPart) viewer.getRootEditPart()).getZoomManager().getZoom());
		}

		if (viewer.getControl() instanceof FigureCanvas) {
			final FigureCanvas canvas = (FigureCanvas) viewer.getControl();
			final Point location = canvas.getViewport().getViewLocation();
			memento.putInteger(TAG_GRAPHICAL_VIEWER_HOR_SCROLL, location.x);
			memento.putInteger(TAG_GRAPHICAL_VIEWER_VER_SCROLL, location.y);
		}
	}

	@Override
	public void restoreState(final IMemento memento) {
		// store memento to be used during create part control to setup the editor with the saved state
		this.memento = memento;
	}

	private static void restoreGraphicalViewerState(final GraphicalViewer viewer, final IMemento memento) {
		if (viewer.getRootEditPart() instanceof ScalableFreeformRootEditPart) {
			final Float zoom = memento.getFloat(TAG_GRAPHICAL_VIEWER_ZOOM);
			if (null != zoom) {
				((ScalableFreeformRootEditPart) viewer.getRootEditPart()).getZoomManager().setZoom(zoom.doubleValue());
			}
		}

		if (viewer.getControl() instanceof FigureCanvas) {
			final FigureCanvas canvas = (FigureCanvas) viewer.getControl();
			final Integer xLocation = memento.getInteger(TAG_GRAPHICAL_VIEWER_HOR_SCROLL);
			final Integer yLocation = memento.getInteger(TAG_GRAPHICAL_VIEWER_VER_SCROLL);
			if ((null != xLocation) && (yLocation != null)) {
				// we have to wait to set the scroll position until the editor is drawn and the canvas is setup
				Display.getDefault().asyncExec(() -> {
					if (!canvas.isDisposed()) {
						canvas.scrollTo(xLocation.intValue(), yLocation.intValue());
					}
				});
			}
		}

	}

	private boolean isConnectionLayoutPreferenceTicked() {
		return InstanceScope.INSTANCE.getNode("org.eclipse.fordiac.ide.gef").getBoolean("ConnectionAutoLayout", false);
	}

	private static void triggerConnectionLayout() {
		final IHandlerService handlerService = getHandlerService();
		try {
			handlerService.executeCommand("org.eclipse.fordiac.ide.elk.connectionLayout", null); //$NON-NLS-1$
		} catch (final Exception ex) {
			throw new RuntimeException("Could not execute layout command"); //$NON-NLS-1$
		}
	}

	private static IHandlerService getHandlerService() {
		final IWorkbenchPartSite site = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart().getSite();
		final IHandlerService handlerService = site.getService(IHandlerService.class);
		return handlerService;
	}

	protected void showReloadErrorMessage(final String path, final String whatEditor) {
		String printPath = path.substring(1);// get rid of the leading /
		printPath = printPath.replace('/', '.');
		MessageDialog.openInformation(getSite().getShell(), Messages.AutoReloadError_PathNotFound_Title,
				MessageFormat.format(Messages.AutoReloadError_PathNotFound, printPath, whatEditor));
	}

	public abstract CommandStack getCommandStack();

	protected abstract void gotoFBNetworkElement(final Object object);

	protected abstract EditorPart createEditorPart(final Object model);

	protected abstract IEditorInput createEditorInput(final Object model);

	protected abstract AdapterFactoryContentProvider createBreadcrumbContentProvider();

	protected abstract AdapterFactoryLabelProvider createBreadcrumbLabelProvider();

	/** retrieve the model element that this bread crumb editor should how after creation
	 *
	 * @param itemPath a string representation that may point to an element in a model hierarchy if null then the root
	 *                 model element should be provided
	 * @return the model element for which the first editor should be shown. */
	protected abstract Object getInitialModel(String itemPath);
}
