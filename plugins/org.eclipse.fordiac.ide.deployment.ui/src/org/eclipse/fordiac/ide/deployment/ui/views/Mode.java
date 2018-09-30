/*******************************************************************************
 * Copyright (c) 2008, 2010, 2013, 2015 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.views;

import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.ui.Messages;
import org.eclipse.fordiac.ide.util.ISelectedElementsChangedListener;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

/**
 * The Class Mode.
 */
public class Mode extends ViewPart implements ISelectionListener {

	/** The download button. */
	private Button downloadButton;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	public void createPartControl(final Composite parent) {
		Composite root = new Composite(parent, SWT.None);
		root.setLayout(new GridLayout());

		downloadButton = new Button(root, SWT.NONE);
		downloadButton.setText(Messages.Mode_DownloadButtonLabel);
		downloadButton.setImage(FordiacImage.ICON_Download.getImage());
		downloadButton.setEnabled(false);
		downloadButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				Object[] selected = treeView.getSelectedElements();
				clearDownloadConsole();				
				DeploymentCoordinator.INSTANCE.performDeployment(selected);
			}

			

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// nothing to do!
			}

		});
		
		contributeToActionBars();

		getSite()
				.getPage()
				.addSelectionListener(
						"org.eclipse.fordiac.ide.deployment.ui.views.DownloadSelectionTreeView", //$NON-NLS-1$
						this);
	}
	
	private void clearDownloadConsole() {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewPart view = page.findView("org.eclipse.fordiac.ide.deployment.ui.views.Output"); //$NON-NLS-1$
		
		if ((null != view) && (view instanceof Output)){
			Output output = (Output)view;
			output.clearOutput();
		}
				
	}
	/**
	 * Contribute to action bars.
	 */
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	/**
	 * Fill local toolbar.
	 * 
	 * @param manager
	 *            the manager
	 */
	private void fillLocalToolBar(final IToolBarManager manager) {
		// currently not used
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// nothing to do

	}

	/** The tree view. */
	private DownloadSelectionTreeView treeView = null;
	private ISelectedElementsChangedListener changeListener = null;
	

	private ISelectedElementsChangedListener getChangeListener() {
		if(null == changeListener){
			changeListener = new ISelectedElementsChangedListener() {
					@Override
					public void selectionChanged() {
						if((null != treeView) && (!downloadButton.isDisposed())){						
							downloadButton.setEnabled((treeView.getSelectedElements().length > 0));						
						}
					}
				};
		}
		return changeListener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.
	 * IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(final IWorkbenchPart part,
			final ISelection selection) {
		if (part instanceof DownloadSelectionTreeView) {
			if (treeView != part) {
				treeView = (DownloadSelectionTreeView) part;
				treeView.addSelectedElementsChangedListener(getChangeListener());
				getChangeListener().selectionChanged();
			}
		}
	}

	@Override
	public void dispose() {		
		super.dispose();
		if(null != treeView){
			treeView.removeSelectedElementsChangedListener(getChangeListener());
		}
	}

}
