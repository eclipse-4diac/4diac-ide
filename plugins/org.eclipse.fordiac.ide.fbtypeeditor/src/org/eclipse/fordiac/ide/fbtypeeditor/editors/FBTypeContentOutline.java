/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.model.data.provider.DataItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.provider.LibraryElementItemProviderAdapterFactory;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class FBTypeContentOutline extends ContentOutlinePage {
	
	private TreeViewer contentOutlineViewer;
	
	private LibraryElementItemProviderAdapterFactory adapterFactory = new LibraryElementItemProviderAdapterFactory();
	private DataItemProviderAdapterFactory dataFactory = new DataItemProviderAdapterFactory();
	
	private ComposedAdapterFactory caf = new ComposedAdapterFactory();
	private CommandStack commandStack;
	private FBType fbType;
	
	
	private final EContentAdapter adapter = new EContentAdapter() {

		@Override
		public void notifyChanged(Notification notification) {
			if(!getTreeViewer().getControl().isDisposed()) {
				Display.getDefault().asyncExec(() -> {
					if(!getTreeViewer().getControl().isDisposed()) {
						getTreeViewer().expandAll();
					}
				});
			}
		}
	};
	
	public CommandStack getCommandStack() {
		return commandStack;
	}
	
	FBTypeContentOutline(FBType fbType, FBTypeEditor editor){
		this.fbType = fbType;
		this.commandStack = editor.getCommandStack();
	}
		
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);

		caf.addAdapterFactory(adapterFactory);
		caf.addAdapterFactory(dataFactory);
		
		
		contentOutlineViewer = getTreeViewer();
		contentOutlineViewer.addSelectionChangedListener(this);
		

		// Set up the tree viewer.
		//
		contentOutlineViewer.setContentProvider(new AdapterFactoryContentProvider(caf));
		contentOutlineViewer.setLabelProvider(new AdapterFactoryLabelProvider(caf));
		contentOutlineViewer.setInput(fbType);
		contentOutlineViewer.expandAll();
		
		fbType.eAdapters().add(adapter);
		
	}
	
	@Override
	public void dispose() {
		caf.removeAdapterFactory(adapterFactory);
		caf.removeAdapterFactory(dataFactory);
		fbType.eAdapters().remove(adapter);
		contentOutlineViewer.removeSelectionChangedListener(this);
		super.dispose();
	}

}
