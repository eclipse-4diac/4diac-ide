/*******************************************************************************
 * Copyright (c) 2007 - 2015 4DIAC - consortium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class ConnectionSection extends AbstractSection {
	private Text commentText;
	private Text sourceText;
	private Text targetText;
	
	@Override
	protected Connection getType(){
		return (Connection) type;
	}
	
	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);		
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, Messages.ConnectionSection_Source); 
		sourceText = createGroupText(composite, false);
		getWidgetFactory().createCLabel(composite, Messages.ConnectionSection_Target); 
		targetText = createGroupText(composite, false);	
		getWidgetFactory().createCLabel(composite, Messages.ConnectionSection_Comment); 
		commentText = createGroupText(composite, true);
		commentText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
				addContentAdapter();
			}
		});
	}	

	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;		
		if(null != type) {
			commentText.setText(getType().getComment() != null ? getType().getComment() : "");  //$NON-NLS-1$
			if(null != getType().getSource()){
				sourceText.setText(getFBNameFromIInterfaceElement(getType().getSource()) + "." + getType().getSource().getName());  //$NON-NLS-1$
			}
			if(null != getType().getDestination()){
				targetText.setText(getFBNameFromIInterfaceElement(getType().getDestination()) + "." + getType().getDestination().getName());  //$NON-NLS-1$
			}
		} 
		commandStack = commandStackBuffer;
	}
	
	private String getFBNameFromIInterfaceElement(IInterfaceElement element){
		return element.eContainer().eContainer() instanceof FBNetworkElement ? ((FBNetworkElement)element.eContainer().eContainer()).getName() : "";  //$NON-NLS-1$
	}
}
