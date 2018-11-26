/*******************************************************************************
 * Copyright (c) 2017 -2018 fortiss GmbH, Johannes Kepler University
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Monika Wenger, Alois Zoitl 
 *      - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.fordiac.ide.gef.commands.ChangeProfileCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractDeviceInterfaceSection extends AbstractDevResInterfaceSection {
	protected static String[] profileNames = null;
	protected Combo profile;
	protected Button getResources;
	
	@Override
	protected void createFBInfoGroup(Composite parent) {
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(4, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, "Instance Name:"); 
		nameText = createGroupText(composite, true);
		nameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeNameCommand(getType(), nameText.getText()));
				addContentAdapter();
			}
		});
		
		getWidgetFactory().createCLabel(composite, "Profile:"); 
		profile = new Combo(composite, SWT.SINGLE | SWT.READ_ONLY);
		profile.addListener( SWT.Selection, event -> {
				removeContentAdapter();				
				executeCommand(new ChangeProfileCommand((Device) getType(), profile.getText()));
				refresh();
				addContentAdapter();
			});
		
		getWidgetFactory().createCLabel(composite, "Instance Comment:"); 
		commentText = createGroupText(composite, true);
		GridData gridData = new GridData(SWT.FILL, 0, true, false);
		gridData.horizontalSpan = 2;
		commentText.setLayoutData(gridData);
		commentText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
				addContentAdapter();
			}
		});
		getResources = getWidgetFactory().createButton(composite, "fetch resource", SWT.PUSH); //$NON-NLS-1$
		getResources.setToolTipText("fetch resources");
	}
	
	@Override
	public void refresh() {
		super.refresh();
		if(null != type) {
			setProfile();
			if("DynamicTypeLoad".equals(((Device)getType()).getProfile())) {
				getResources.setEnabled(true);
			}else {
				getResources.setEnabled(false);
			}
		}
	}
	
	private void setProfile() {
		int i = 0;		
		for(String p : profile.getItems()){
			if(p.equals(((Device)getType()).getProfile())){
				profile.select(i);
				break;
			}
			i++;
		}
	}
}
