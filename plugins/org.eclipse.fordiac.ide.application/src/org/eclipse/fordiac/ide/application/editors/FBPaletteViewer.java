/*******************************************************************************
 * Copyright (c) 2015, 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.util.TypeListPatternFilter;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.CommonViewerSorter;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.INavigatorFilterService;
import org.eclipse.ui.navigator.NavigatorContentServiceFactory;

public class FBPaletteViewer extends PaletteViewer {
	private CommonViewer commonViewer;
	private PatternFilter patternFilter = null;
	private final String navigatorId;
	
	public FBPaletteViewer(String navigatorId) {
		super();
		this.navigatorId = navigatorId;
	}

	public void createTypeLibTreeControl(Composite parent,
			IProject project) {

		Composite container = new Composite(parent, SWT.NONE);

		GridLayout layout = new GridLayout(1, false);
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.marginBottom = 0;
		layout.marginTop = 0;
		container.setLayout(layout);

		final Text text = new Text(container, SWT.SEARCH | SWT.ICON_CANCEL);

		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				if (e.detail == SWT.CANCEL) {
					setSearchFilter(""); //$NON-NLS-1$
				} else {
					setSearchFilter(text.getText());
				}
			}

		});
		text.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				setSearchFilter(text.getText());
			}
		});

		createCommonViewer(container, project);

		setControl(container);
		
		setupResourceChangeListener(project);
	}

	private void createCommonViewer(Composite container, IProject project) {
		commonViewer = new CommonViewer(navigatorId,
				container, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);

		INavigatorContentService contentService = NavigatorContentServiceFactory.INSTANCE
				.createContentService(navigatorId, commonViewer);

		contentService.createCommonContentProvider();
		contentService.createCommonLabelProvider();
		
		INavigatorFilterService filterService = commonViewer
				.getNavigatorContentService().getFilterService();
		ViewerFilter[] visibleFilters = filterService.getVisibleFilters(true);
		for (int i = 0; i < visibleFilters.length; i++) {
			commonViewer.addFilter(visibleFilters[i]);
		}

		commonViewer.setSorter(new CommonViewerSorter());
		commonViewer.addFilter(new TypeListPatternFilter());
		
		commonViewer.getControl().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// currently nothing todo here				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				// set the focus on this part on any mouse click, fixes issue in drag and drop
				commonViewer.getControl().forceFocus();
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// currently nothing todo here				
			}
		});

		
		if(project.getName().equals(TypeLibraryTags.TOOL_LIBRARY_PROJECT_NAME)){
			commonViewer.setInput(TypeLibrary.getToolLibFolder());
		}else{
			commonViewer.setInput(project);			
		}

		GridData fillBoth = new GridData();
		fillBoth.horizontalAlignment = GridData.FILL;
		fillBoth.grabExcessHorizontalSpace = true;
		fillBoth.verticalAlignment = GridData.FILL;
		fillBoth.grabExcessVerticalSpace = true;
		commonViewer.getControl().setLayoutData(fillBoth);
	}

	private void setSearchFilter(String string) {
		if (patternFilter == null)	{
			patternFilter = new TypeListPatternFilter();
			commonViewer.addFilter(patternFilter);
		}		
		patternFilter.setPattern(string);
		commonViewer.refresh(false);
	}

	@Override
	protected void hookControl() {
		// do nothing here! Especially do not call super.hookControl!
	}
	
	private void setupResourceChangeListener(final IProject project) {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(new IResourceChangeListener() {			
			@Override
			public void resourceChanged(IResourceChangeEvent event) {
				if (event.getType() != IResourceChangeEvent.POST_CHANGE) {
		            return;
				}
				IResourceDelta rootDelta = event.getDelta();
				IResourceDelta docDelta = rootDelta.findMember(project.getFullPath());
				if (docDelta == null) {
		            return;
				}
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						if(null != commonViewer && !commonViewer.getControl().isDisposed()){
							commonViewer.refresh();
						}						
					}
				});				
			}
		});
	}

}
