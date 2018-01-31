/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.fbtypeeditor.Activator;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.dataexport.CommonElementExporter;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.fordiac.ide.typemanagement.util.FBTypeUtils;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class FBTypeEditor extends FormEditor implements
		IResourceChangeListener, ISelectionListener, CommandStackEventListener, ITabbedPropertySheetPageContributor {

	protected Collection<IFBTEditorPart> editors;
	private PaletteEntry paletteEntry;
	private FBType fbType;
	private FBTypeContentOutline contentOutline = null;	
	private CommandStack commandStack = new CommandStack();
	private Palette fbPalette = null;
	
	private final EContentAdapter adapter = new EContentAdapter() {

		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			Object feature = notification.getFeature();
			if(null != feature){
				if (LibraryElementPackage.LIBRARY_ELEMENT__NAME == notification.getFeatureID(feature.getClass())){
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							if(null != paletteEntry){
								setPartName(paletteEntry.getLabel());
							}
						}
					});
					
				}
			}
		}
	};

	public FBTypeEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		if (null != paletteEntry) {
			if(checkTypeSaveAble()){
				//allow each editor to save back changes before saving to file
				for (Iterator<IFBTEditorPart> iterator = editors.iterator(); iterator
						.hasNext();) {
					IFBTEditorPart editorPart = iterator.next();
					editorPart.doSave(monitor);				
				}
				
				getCommandStack().markSaveLocation();
				CommonElementExporter.saveType(paletteEntry);			
				firePropertyChange(IEditorPart.PROP_DIRTY);
			}
		}
	}

	private boolean checkTypeSaveAble() {
		if(fbType instanceof CompositeFBType){
			//only allow to save composite FBs if all contained FBs have types
			for (FBNetworkElement fb : ((CompositeFBType)fbType).getFBNetwork().getNetworkElements()) {
				if(null == fb.getPaletteEntry()){
					MessageDialog.openInformation(getSite().getShell(), "Composite contains Function Block without type", 
							"The composite Function Block you are about to save contains the function block " + fb.getName() +
							" which has no type. Saving is not possible as it may break any connections. Please correct this first!");
					return false;
				}
			}			
		}
		return true;
	}

	@Override
	public void doSaveAs() {

	}

	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>FBTypeEditorInput</code>.
	 * 
	 * @param site
	 *            the site
	 * @param editorInput
	 *            the editor input
	 * 
	 * @throws PartInitException
	 *             the part init exception
	 */
	@Override
	public void init(final IEditorSite site, final IEditorInput editorInput)
			throws PartInitException {

		if (editorInput instanceof FileEditorInput) {
			IFile fbTypeFile = ((FileEditorInput) editorInput).getFile();

			fbPalette = FBTypeUtils.getPalletteForFBTypeFile(fbTypeFile);
			
			if (fbPalette != null) {
				paletteEntry = TypeLibrary.getPaletteEntry(fbPalette, fbTypeFile);
			}
		}
		else if (editorInput instanceof FBTypeEditorInput) {
			paletteEntry = ((FBTypeEditorInput) editorInput).getPaletteEntry();
		}
		
		if(null != paletteEntry){
			setPartName(paletteEntry.getLabel());
			
			fbType = getFBType(paletteEntry);
			if(null != fbType){ 
				fbType.eAdapters().add(adapter);
			}
		}

		site.getWorkbenchWindow().getSelectionService()
				.addSelectionListener(this);
		getCommandStack().addCommandStackEventListener(this);
		
		super.init(site, editorInput);
	}

	protected FBType getFBType(PaletteEntry paletteEntry) {
		if(paletteEntry instanceof FBTypePaletteEntry){
			return ((FBTypePaletteEntry)paletteEntry).getFBType();
		} else if(paletteEntry instanceof AdapterTypePaletteEntry){
			return ((AdapterTypePaletteEntry)paletteEntry).getAdapterType().getAdapterFBType();
		}
		return null;
	}

	public CommandStack getCommandStack() {
		return commandStack;
	}

	@Override
	public void dispose() {
		if (fbType != null && fbType.eAdapters().contains(adapter)) {
			fbType.eAdapters().remove(adapter);
		}
		
		// get these values here before calling super dispose
		boolean dirty = isDirty();
		FBTypeEditorInput input = getFBTypeEditorInput();

		if(null != getSite()){
			getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);
		}
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);

		super.dispose();

		if (dirty) {
			// purge from typelib after super.dispose() so that no notifiers
			// will be called
			if (null != input) {
				input.getPaletteEntry().setType(null);
			}
		}
		
		getCommandStack().removeCommandStackEventListener(this);
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * Closes all project files on project close.
	 * 
	 * @param event
	 *            the event
	 */
	public void resourceChanged(final IResourceChangeEvent event) {
		// if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
		// Display.getDefault().asyncExec(new Runnable() {
		// public void run() {
		// IWorkbenchPage[] pages = getSite().getWorkbenchWindow()
		// .getPages();
		// for (int i = 0; i < pages.length; i++) {
		// if (((FileEditorInput) editor.getEditorInput())
		// .getFile().getProject().equals(
		// event.getResource())) {
		// IEditorPart editorPart = pages[i].findEditor(editor
		// .getEditorInput());
		// pages[i].closeEditor(editorPart, true);
		// }
		// }
		// }
		// });
		// }
	}

	@Override
	protected void addPages() {
		SortedMap<Integer, IFBTEditorPart> sortedEditorsMap = new TreeMap<Integer, IFBTEditorPart>();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry
				.getExtensionPoint("org.eclipse.fordiac.ide.fbtypeeditor.fBTEditorTabs"); //$NON-NLS-1$
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IExtension extension = extensions[i];
			IConfigurationElement[] elements = extension
					.getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				IConfigurationElement element = elements[j];
				try {
					Object obj = element.createExecutableExtension("class"); //$NON-NLS-1$
					if (obj instanceof IFBTEditorPart) {
						String elementType = element.getAttribute("type"); //$NON-NLS-1$
						Integer sortIndex = Integer.valueOf(element.getAttribute("sortIndex")); //$NON-NLS-1$
						
						if(checkTypeEditorType(fbType, elementType)){
							sortedEditorsMap.put(sortIndex, (IFBTEditorPart) obj);
						}
					}
				} catch (Exception e) {
					Activator.getDefault().logError(e.getMessage(), e);
				}
			}
		}

		editors = new ArrayList<IFBTEditorPart>();
		FBTypeEditorInput editorInput = getFBTypeEditorInput();

		for (Iterator<IFBTEditorPart> iterator = sortedEditorsMap.values()
				.iterator(); iterator.hasNext();) {
			IFBTEditorPart fbtEditorPart = iterator.next();
			editors.add(fbtEditorPart);
			try {
				//setCommonCommandStack needs to be called before the editor is added as page
				fbtEditorPart.setCommonCommandStack(commandStack);
				int index = addPage(fbtEditorPart, editorInput);
				setPageText(index, fbtEditorPart.getTitle());
				setPageImage(index, fbtEditorPart.getTitleImage());
			} catch (PartInitException e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}

		}
	}

	/** Check if the given editor type is a valid editor for the given type 
	 * 
	 * @param fbType  type to be edited in this type editor
	 * @param editorType editor type string as defined the fBTEditorTabs.exsd
	 * @return true if the editor should be shown otherwise false
	 */
	protected boolean checkTypeEditorType(FBType fbType, String editorType) {
		return ((editorType.equals("ForAllTypes")) ||  //$NON-NLS-1$
				(editorType.equals("ForAllFBTypes")) || //$NON-NLS-1$
				(editorType.equals("ForAllNonAdapterFBTypes") && !(fbType instanceof AdapterFBType)) || //$NON-NLS-1$
				(fbType instanceof BasicFBType && editorType.equals("basic")) || //$NON-NLS-1$
			    (fbType instanceof ServiceInterfaceFBType && editorType.equals("serviceInterface")) || //$NON-NLS-1$
			    (fbType instanceof CompositeFBType && editorType.equals("composite"))		 //$NON-NLS-1$
		);
	}

	private FBTypeEditorInput getFBTypeEditorInput() {
		return new FBTypeEditorInput(fbType, paletteEntry);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.
	 * IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(final IWorkbenchPart part,
			final ISelection selection) {
		if (this.equals(getSite().getPage().getActiveEditor())) {
			handleContentOutlineSelection(selection);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getAdapter(Class required) {
		if (IContentOutlinePage.class.equals(required)) {
			if (contentOutline == null) {
				contentOutline = new FBTypeContentOutline(fbType, this);
			}
			return contentOutline;
		}
		if(required == CommandStack.class){
			return getCommandStack();
		}
		if(required == IPropertySheetPage.class){
			return new TabbedPropertySheetPage(this);
		}
		if(required == CommandStack.class){
			  return getCommandStack();
		}
		return super.getAdapter(required);
	}

	public void handleContentOutlineSelection(ISelection selection) {
		if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
			Iterator<?> selectedElements = ((IStructuredSelection) selection)
					.iterator();
			if (selectedElements.hasNext()) {
				// Get the first selected element.
				//
				Object selectedElement = selectedElements.next();

				selectedElement.getClass();
				int i = 0;
				for (Iterator<IFBTEditorPart> iterator = editors.iterator(); iterator
						.hasNext(); i++) {
					IFBTEditorPart ifbt = iterator.next();
					if (ifbt.outlineSelectionChanged(selectedElement)) {
						setActivePage(i);
						break;
					}
				}
			}
		}
	}

	public IEditorActionBarContributor getActionBarContributor() {
		return null;
	}
	
	@Override
	public void stackChanged(CommandStackEvent event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}
	
	@Override
	public String getContributorId() {
		return "property.contributor.fb"; //$NON-NLS-1$
	}

}
