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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeAlgorithmCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeOutputCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithm;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithmEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionHelpers;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionOutputEvent;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionOutputEventEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class ActionSection extends AbstractECSection {
	private Composite actionComposite;
	private Combo algorithmCombo;
	private Combo outputEventCombo;
	private AlgorithmGroup algorithmGroup;
	private AlgorithmList algorithmList;
	
	private Map<String, Event> events = new HashMap<>();
	
	@Override
	protected ECAction getType() {
		return (ECAction) type;
	}

	protected Algorithm getAlgorithm() {
		return getType().getAlgorithm();
	}
		
	protected EList<Event> getOutputEvents() {
		BasicFBType fb = ECActionHelpers.getFBType(getType());
		return (null != fb) ? fb.getInterfaceList().getEventOutputs() : ECollections.emptyEList();
	}

	@Override
	protected Object getInputType(Object input) {
		if(input instanceof ECActionAlgorithmEditPart){
			return ((ECActionAlgorithmEditPart) input).getAction();	
		}
		if(input instanceof ECActionAlgorithm){
			return ((ECActionAlgorithm) input).getAction();	
		}
		if(input instanceof ECActionOutputEventEditPart){
			return ((ECActionOutputEventEditPart) input).getAction();	
		}
		if(input instanceof ECActionOutputEvent){
			return ((ECActionOutputEvent) input).getAction();	
		}
		if(input instanceof ECAction){
			return input;	
		}
		return null;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);		
		parent.setLayout(new GridLayout(3, true));		
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		createActionCombos(parent);		
		algorithmGroup = new AlgorithmGroup(parent, getWidgetFactory());
		createAlgorithmView(parent);
	}
	
	private void createActionCombos(Composite parent) {
		actionComposite = getWidgetFactory().createComposite(parent);
		GridData actionCompositeLayoutData = new GridData(GridData.FILL, 0, true, false);
		actionCompositeLayoutData.horizontalSpan = 3;
		actionComposite.setLayoutData(actionCompositeLayoutData);
		RowLayout layout = new RowLayout();
		layout.fill = true;
		actionComposite.setLayout(layout);
		
		getWidgetFactory().createCLabel(actionComposite, "Algorithm: ");
		algorithmCombo = new Combo(actionComposite, SWT.SINGLE | SWT.READ_ONLY);
		algorithmCombo.addListener(SWT.Selection, event -> {
				removeContentAdapter();
				if(algorithmCombo.indexOf(algorithmCombo.getText()) > 0){
					BasicFBType fb = ECActionHelpers.getFBType(getType());
					if(null != fb){
						executeCommand(new ChangeAlgorithmCommand(getType(), 
							fb.getAlgorithm().get(algorithmCombo.indexOf(algorithmCombo.getText()) - 1)));
					}
				}else{
					executeCommand(new ChangeAlgorithmCommand(getType(), null));
				}
				algorithmGroup.setAlgorithm(getAlgorithm());
				algorithmList.refresh();
				addContentAdapter();
			});
		
		getWidgetFactory().createCLabel(actionComposite, "Output Event: ");
		outputEventCombo = new Combo(actionComposite, SWT.SINGLE | SWT.READ_ONLY);
		outputEventCombo.addListener(SWT.Selection, event -> {
				removeContentAdapter();
				executeCommand(new ChangeOutputCommand(getType(), events.get(outputEventCombo.getText())));
				addContentAdapter();
			});
		
	}

	private void createAlgorithmView(Composite parent) {
		Group algorithmComposite = getWidgetFactory().createGroup(parent, "All Algorithms");
		algorithmComposite.setLayout(new GridLayout(1, false));
		algorithmComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		algorithmList = new AlgorithmList(algorithmComposite, getWidgetFactory());
		algorithmList.getAlgorithmViewer().addSelectionChangedListener(new ISelectionChangedListener() {			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				refresh();
			}
		});
	}
	
	
	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		Assert.isTrue(selection instanceof IStructuredSelection);
		Object input = ((IStructuredSelection) selection).getFirstElement();
		commandStack = getCommandStack(part, input);
		if(null == commandStack){ //disable all fields
			outputEventCombo.removeAll();
			outputEventCombo.setEnabled(false);
			algorithmCombo.removeAll();
			algorithmCombo.setEnabled(false);
		}
		setType(input);
		BasicFBType type = ECActionHelpers.getFBType(getType());
		algorithmGroup.initialize(type, commandStack);
		algorithmList.initialize(type, commandStack);
	}	

	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;		
		if(null != type) {
			setOutputEventDropdown();
			outputEventCombo.select(getType().getOutput() != null ? outputEventCombo.indexOf(getType().getOutput().getName()) : outputEventCombo.indexOf(""));		 //$NON-NLS-1$
			setAlgorithmDropdown();
			algorithmGroup.setAlgorithm(getAlgorithm());
			algorithmList.refresh();
		} 
		commandStack = commandStackBuffer;
	}
	
	private void setOutputEventDropdown(){
		outputEventCombo.removeAll();
		events.clear();
		for(Event e : ECActionHelpers.getOutputEvents(ECActionHelpers.getFBType(getType()))) {
			String name = e.getName();
			outputEventCombo.add(name);
			events.put(name, e);
		}
		outputEventCombo.add(" ");		 //$NON-NLS-1$
	}

	
	private void setAlgorithmDropdown(){
		algorithmCombo.removeAll();
		algorithmCombo.add(""); //$NON-NLS-1$
		BasicFBType fb = ECActionHelpers.getFBType(getType());
		if(null != fb){
			for(Algorithm alg : fb.getAlgorithm()){
				algorithmCombo.add(alg.getName());
			}
		}
		algorithmCombo.select((null == getAlgorithm()) ? 0 : algorithmCombo.indexOf(getAlgorithm().getName()));		
		actionComposite.layout();
	}

	@Override
	protected void setInputCode() {
		//nothing to be done here
	}

	@Override
	protected void setInputInit() {
		//nothing to be done here
	}
}
