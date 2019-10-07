/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 * 	
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Alois Zoitl - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.fordiac.ide.ui.providers.CommandProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class AddDeleteReorderListWidget extends AddDeleteWidget{
	private Button upButton; 
	private Button downButton; 

	
	@Override
	public void createControls(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		Composite container = createContainer(parent);		
		
		createAddButton(widgetFactory, container); 
		
		upButton = widgetFactory.createButton(container, "", SWT.ARROW | SWT.UP); //$NON-NLS-1$
		upButton.setToolTipText("Move element up"); 
		
		downButton = widgetFactory.createButton(container, "", SWT.ARROW | SWT.DOWN); //$NON-NLS-1$
		downButton.setToolTipText("Move element down"); 
		
		createDeleteButton(widgetFactory, container);

		//initially nothing should be selected therefore deactivate the buttons
		setButtonEnablement(false);
	}
	
	@Override
	public void setButtonEnablement(boolean enable) {
		upButton.setEnabled(enable);
		downButton.setEnabled(enable);
		super.setButtonEnablement(enable);
	}
	
	public void addUpListener(Listener upListener) {
		upButton.addListener(SWT.Selection, upListener);
	}
	
	public void addDownListener(Listener downListener) {
		downButton.addListener(SWT.Selection, downListener);
	}
	
	public void bindToTableViewer(TableViewer viewer, CommandExecutor executor, 
			CommandProvider addCommand, CommandProvider deleteCommand,
			CommandProvider moveUpCommand, CommandProvider moveDownCommand) {		
		super.bindToTableViewer(viewer, executor, addCommand, deleteCommand);
		addUpListener(getSelectionListener(viewer, executor, moveUpCommand)); 		
		addDownListener(getSelectionListener(viewer, executor, moveDownCommand));
	}

}
