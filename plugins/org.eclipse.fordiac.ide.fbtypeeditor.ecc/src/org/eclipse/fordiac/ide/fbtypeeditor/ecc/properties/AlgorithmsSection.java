/*******************************************************************************
 * Copyright (c) 2015 - 2018 fortiss GmbH, Johannes Kepler University Linz (JKU)
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class AlgorithmsSection extends ECCSection {
	private AlgorithmGroup algorithmGroup;
	private AlgorithmList algorithmList;
	

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);	
		createAlgorithmControls(parent);
	}
		
	public void createAlgorithmControls(final Composite parent) {	
		SashForm view = new SashForm(parent, SWT.HORIZONTAL | SWT.SMOOTH);
		view.setLayout(new FillLayout());
		algorithmList = new AlgorithmList(view, getWidgetFactory());
		leftComposite = algorithmList.getComposite();
		
		getAlgorithmList().getAlgorithmViewer().addSelectionChangedListener(new ISelectionChangedListener() {			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				Object selection = ((IStructuredSelection) getAlgorithmList().getAlgorithmViewer().getSelection()).getFirstElement();
				algorithmGroup.setAlgorithm((selection instanceof Algorithm) ? (Algorithm) selection : null);
			}
		});
		
		rightComposite = getWidgetFactory().createComposite(view);
		rightComposite.setLayout(new GridLayout());	
		view.setWeights(new int[] {1, 1});
		view.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));	
						
		algorithmGroup = new AlgorithmGroup(rightComposite, getWidgetFactory());
	}

	@Override
	protected void setInputCode() {
		//algorithmViewer.setCellModifier(null);
	}

	@Override
	protected void setInputInit() {
		algorithmGroup.initialize(getType(), commandStack);
		getAlgorithmList().initialize(getType(), commandStack);
	}
	
	private AlgorithmList getAlgorithmList() {
		return algorithmList;
	}
	
	@Override
	public void refresh() {
		getAlgorithmList().refresh();
	}
}
