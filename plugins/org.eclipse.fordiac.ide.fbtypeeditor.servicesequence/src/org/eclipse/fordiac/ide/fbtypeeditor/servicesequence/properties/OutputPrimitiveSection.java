/*******************************************************************************
 * Copyright (c) 2014  fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangeOutputPrimitiveResultCommand;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class OutputPrimitiveSection extends PrimitiveSection {

	private Text testResultText;
	
	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);	
		createOutputPrimitiveSection(leftComposite);	
	}
	
	private void createOutputPrimitiveSection(Composite parent){
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, "Test Result:"); 
		testResultText = createGroupText(composite, true);	
		testResultText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeOutputPrimitiveResultCommand(getType(), testResultText.getText()));
				addContentAdapter();
			}
		});
	}	
	
	@Override
	protected OutputPrimitive getType(){
		return (OutputPrimitive)type;
	}

	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		super.setInput(part, selection);
		if(null == commandStack){ //disable all fields
			testResultText.setEnabled(false);
		}
	}	
	
	@Override
	public void refresh() {
		super.refresh();
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if(null != type) {
			testResultText.setText(Integer.toString(getType().getTestResult()));
		}
		commandStack = commandStackBuffer;
	}
}
