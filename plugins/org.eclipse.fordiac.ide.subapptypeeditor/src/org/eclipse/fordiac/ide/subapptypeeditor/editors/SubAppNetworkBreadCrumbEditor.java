/*******************************************************************************
 * Copyright (c) 2020, 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.subapptypeeditor.editors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.application.editors.SubApplicationEditorInput;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeAndSubAppInstanceViewerInput;
import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeInstanceViewer;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.ui.editors.AbstractBreadCrumbEditor;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.fordiac.ide.subapptypeeditor.providers.TypedSubappProviderAdapterFactory;
import org.eclipse.fordiac.ide.subapptypeeditor.viewer.SubappInstanceViewer;
import org.eclipse.fordiac.ide.typeeditor.TypeEditorInput;
import org.eclipse.fordiac.ide.typemanagement.navigator.FBTypeLabelProvider;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IReusableEditor;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.MultiPageEditorSite;

public class SubAppNetworkBreadCrumbEditor extends AbstractBreadCrumbEditor implements IFBTEditorPart {

	private CommandStack commandStack;
	private MultiPageEditorSite multiPageEditorSite;

	private GraphicalAnnotationModel annotationModel;

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		if (!(input instanceof TypeEditorInput)) {
			throw new IllegalArgumentException("SubAppNetworkBreadCrumbEditor is only suitable for TypeEditorInputs"); //$NON-NLS-1$
		}

		IEditorSite siteToUse = site;
		ISelectionProvider selProvider = null;
		if (siteToUse instanceof final MultiPageEditorSite multiPageEditorSite) {
			this.multiPageEditorSite = multiPageEditorSite;
			siteToUse = (IEditorSite) multiPageEditorSite.getMultiPageEditor().getSite();
			selProvider = siteToUse.getSelectionProvider();
		}

		super.init(siteToUse, input);

		if (selProvider != null) {
			// restore the outer selection provider
			siteToUse.setSelectionProvider(selProvider);
		}

		setTitleImage(FordiacImage.ICON_FB_NETWORK.getImage());
		setPartName("FB Network"); //$NON-NLS-1$
	}

	@Override
	public TypeEditorInput getEditorInput() {
		return (TypeEditorInput) super.getEditorInput();
	}

	@Override
	public SubAppType getType() {
		return (SubAppType) IFBTEditorPart.super.getType();
	}

	@Override
	protected Composite createPageContainer(final Composite parent) {
		final Composite pageContainer = new Composite(parent, SWT.NONE);
		pageContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		return pageContainer;
	}

	@Override
	protected void addPages() {
		try {
			final TypedSubAppNetworkEditor initialEditor = new TypedSubAppNetworkEditor();
			initialEditor.setCommonCommandStack(getCommandStack());
			final int pagenum = addPage(initialEditor, getEditorInput());
			getModelToEditorNumMapping().put(getType(), Integer.valueOf(pagenum)); // need to use the file as
			// reference as this is
			// provided by the content
			// providers
		} catch (final PartInitException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	@Override
	protected EditorPart createEditorPart(final Object model) {
		if (model instanceof final SubApp subApp) {
			if (subApp.isTyped()) {
				return new SubappInstanceViewer();
			}
			final UnTypedSubAppNetworkEditor editor = new UnTypedSubAppNetworkEditor();
			editor.setCommonCommandStack(getCommandStack());
			editor.setTypeLib(getEditorInput().getTypeEntry().getTypeLibrary());
			return editor;
		}

		if (model instanceof CFBInstance) {
			return new CompositeInstanceViewer();
		}

		return null;
	}

	@Override
	protected IEditorInput createEditorInput(final Object model) {
		if (model instanceof final SubApp subApp) {
			if ((subApp.isTyped()) || (subApp.isContainedInTypedInstance())) {
				return new CompositeAndSubAppInstanceViewerInput(subApp);
			}
			return new SubApplicationEditorInput(subApp);
		}

		if (model instanceof CFBInstance) {
			return new CompositeAndSubAppInstanceViewerInput((FB) model);
		}
		return null;
	}

	@Override
	protected AdapterFactoryContentProvider createBreadcrumbContentProvider() {
		return new AdapterFactoryContentProvider(new TypedSubappProviderAdapterFactory());
	}

	@Override
	protected AdapterFactoryLabelProvider createBreadcrumbLabelProvider() {
		return new FBTypeLabelProvider();
	}

	@Override
	protected Object getInitialModel(final String itemPath) {
		// FIXME implement path analysis for typed subapps
		return getType();
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// currently nothing needs to be done here
	}

	@Override
	public void doSaveAs() {
		// currently not allowed
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public CommandStack getCommandStack() {
		return commandStack;
	}

	@Override
	public String getContributorId() {
		return "property.contributor.fb"; //$NON-NLS-1$
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean outlineSelectionChanged(Object selectedElement) {
		if ((selectedElement instanceof final EObject eObj) && (EcoreUtil.isAncestor(getType().getFBNetwork(), eObj))) {
			// an selected element is only valid if it is a child of the FBNetwork of the
			// subapp or the fbnetwork
			if (selectedElement instanceof final FBNetwork fbn) {
				selectedElement = fbn.eContainer();
			}
			if ((selectedElement instanceof FBNetworkElement) || (selectedElement instanceof SubAppType)
					|| (selectedElement instanceof VarDeclaration)) {
				EObject refElement = null;
				if (selectedElement instanceof final FB fb) {
					refElement = fb;
					selectedElement = refElement.eContainer().eContainer();
				} else if (selectedElement instanceof final SubApp subApp) {
					refElement = subApp;
					selectedElement = refElement.eContainer().eContainer();
				} else if (selectedElement instanceof final VarDeclaration varDecl) {
					refElement = varDecl;
					selectedElement = varDecl.eContainer().eContainer();
				}
				getBreadcrumb().setInput(selectedElement);
				if (null != refElement) {
					HandlerHelper.selectElement(refElement, getActiveEditor());
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void setCommonCommandStack(final CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	@Override
	public boolean isMarkerTarget(final IMarker marker) {
		return FordiacErrorMarker.markerTargetsFBNetworkElement(marker)
				|| FordiacErrorMarker.markerTargetsErrorMarkerInterface(marker)
				|| FordiacErrorMarker.markerTargetsConnection(marker) || FordiacErrorMarker.markerTargetsValue(marker);
	}

	@Override
	public void setInput(final IEditorInput input) {
		checkEditorInput(input);
		if (multiPageEditorSite != null) {
			annotationModel = multiPageEditorSite.getMultiPageEditor().getAdapter(GraphicalAnnotationModel.class);
		}
		pages.stream().filter(IReusableEditor.class::isInstance).map(IReusableEditor.class::cast)
				.forEach(e -> e.setInput(e.getEditorInput()));
		super.setInputWithNotify(input);
	}

	@Override
	public void reloadType() {
		if (getType() instanceof final SubAppType subAppType) {
			final String path = getBreadcrumb().serializePath();
			removePage(getActivePage());
			createPages();
			if (!getBreadcrumb().openPath(path, subAppType)) {
				getBreadcrumb().setInput(subAppType);
				showReloadErrorMessage(path, "Showing subapp root.");
			}
		} else {
			EditorUtils.CloseEditor.run(this);
		}

	}

	@Override
	public Object getSelectableObject() {
		final GraphicalViewer viewer = getAdapter(GraphicalViewer.class);
		return viewer.getRootEditPart();
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == GraphicalAnnotationModel.class) {
			return adapter.cast(annotationModel);
		}
		if (adapter == LibraryElement.class) {
			return adapter.cast(getType());
		}
		return super.getAdapter(adapter);
	}

}
