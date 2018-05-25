/*******************************************************************************
 * Copyright (c) 2016 - 2018 fortiss GmbH, Johannes Kepler University Linz (JKU)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Activator;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.IAlgorithmEditor;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.IAlgorithmEditorCreator;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeAlgorithmTextCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeAlgorithmTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

class AlgorithmGroup {

	private Group algorithmGroup;
	private CLabel languageLabel;
	private Combo languageCombo;
	private CLabel commentLabel;
	private Text commentText;
	private Composite codeEditors;
	private StackLayout stack;
	private Map<String, IAlgorithmEditor> editors = new HashMap<>();
	private IAlgorithmEditor currentAlgEditor;
	
	private boolean blockUpdates = false;
	
	private final IDocumentListener listener = new IDocumentListener() {
		@Override
		public void documentChanged(final DocumentEvent event) {
			if ((getAlgorithm() != null) && (null != currentAlgEditor)) {
				if(currentAlgEditor.isDocumentValid()){
					executeCommand(new ChangeAlgorithmTextCommand((TextAlgorithm)getAlgorithm(), currentAlgEditor.getAlgorithmText()));	
				}
			}
		}
		@Override
		public void documentAboutToBeChanged(final DocumentEvent event) {
			// nothing todo here
		}
	};
	
	private CommandStack commandStack;
	private Algorithm currentAlgorithm;
	
	private Algorithm getAlgorithm() {
		return currentAlgorithm;
	}
	
	private BasicFBType getBasicFBType() {
		return (BasicFBType) currentAlgorithm.eContainer();
	}
	
	
	AlgorithmGroup(final Composite parent, TabbedPropertySheetWidgetFactory widgetFactory){
		algorithmGroup =  widgetFactory.createGroup(parent, Messages.ECAlgorithmGroup_Title);
		GridData algorithmGroupLayoutData = new GridData(GridData.FILL, GridData.FILL, true, true);
		algorithmGroupLayoutData.horizontalSpan = 2;
		algorithmGroup.setLayoutData(algorithmGroupLayoutData);
		algorithmGroup.setLayout(new GridLayout(1,true));
		
		Composite langAndComents = widgetFactory.createComposite(algorithmGroup);
		langAndComents.setLayout(new GridLayout(4, false));
		langAndComents.setLayoutData(new GridData(GridData.FILL, 0, true, false));
		
		languageLabel = widgetFactory.createCLabel(langAndComents, "Langugage: ");
		languageCombo = new Combo(langAndComents, SWT.SINGLE | SWT.READ_ONLY);
		fillLanguageDropDown();
		languageCombo.addListener(SWT.Selection, event -> executeCommand(new ChangeAlgorithmTypeCommand(getBasicFBType(), getAlgorithm(), languageCombo.getText())));
		
		
		commentLabel = widgetFactory.createCLabel(langAndComents, "Comment:"); 
		commentText = widgetFactory.createText(langAndComents, ""); //$NON-NLS-1$
		commentText.setEditable(true);	
		commentText.setEnabled(true);
		commentText.setLayoutData(new GridData(GridData.FILL, 0, true, false));
		commentText.addListener(SWT.Modify, e -> executeCommand(new ChangeCommentCommand(getAlgorithm(), commentText.getText())) );		
		
		GridData codeEditorsGridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		codeEditorsGridData.horizontalSpan = 1;
		codeEditorsGridData.minimumHeight = 250;
		codeEditors = widgetFactory.createGroup(algorithmGroup, ""); //widgetFactory.createComposite(algorithmGroup, SWT.BORDER); //$NON-NLS-1$
		codeEditors.setLayout(stack = new StackLayout());
		codeEditors.setLayoutData(codeEditorsGridData);
		
		disableAllFields();
	}
	
	private void executeCommand(Command cmd){
		if (null != currentAlgorithm && commandStack != null) {
			blockUpdates = true;
			commandStack.execute(cmd);
			blockUpdates = false;
		}
	}

	void initialize(BasicFBType basicFBType, CommandStack commandStack) {
		this.commandStack = commandStack;
		loadEditors(basicFBType);
	}
	
	private void loadEditors(BasicFBType basicFBType){
		editors.clear();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint("org.eclipse.fordiac.ide.fbtypeeditor.ecc.algorithmEditor"); //$NON-NLS-1$
		IExtension[] extensions = point.getExtensions();
		for(IExtension extension : extensions) {
			IConfigurationElement[] elements = extension.getConfigurationElements();
			for(IConfigurationElement element : elements) {
				Object obj = null;
				try {
					obj = element.createExecutableExtension("class"); //$NON-NLS-1$
				} catch (Exception e) {
					Activator.getDefault().logError(e.getMessage(), e);
				}
				if (obj instanceof IAlgorithmEditorCreator) {
					IAlgorithmEditor editor = ((IAlgorithmEditorCreator) obj).createAlgorithmEditor(codeEditors, basicFBType);
					String lang = element.getAttribute("language"); //$NON-NLS-1$
					editors.put(lang, editor);
				}
			}
		}
	}

	public void setAlgorithm(Algorithm algorithm) {
		if(!blockUpdates){
			//set commandStack to null so that an update will not lead to a changed type
			CommandStack commandStackBuffer = commandStack;
			commandStack = null;
			if(this.currentAlgorithm != algorithm){
				currentAlgorithm = algorithm;
				if(null != currentAlgorithm){
					initializeEditor();
					enableAllFields();
					updateAlgFields();
				} else {
					algorithmGroup.setText(Messages.ECAlgorithmGroup_Title);
					commentText.setText(""); //$NON-NLS-1$
					languageCombo.select(0);
					stack.topControl = null; 
					codeEditors.layout();
					disableAllFields();
				}
			} else if(null != currentAlgorithm){
				//update the content of the algorithm only
				updateAlgFields();
			}
			commandStack = commandStackBuffer;
		}
	}

	private void enableAllFields() {
		languageLabel.setEnabled(true);
		algorithmGroup.setEnabled(true);
		commentLabel.setEnabled(true);
		commentText.setEnabled(true);
		languageCombo.setEnabled(true);
	}
	
	private void disableAllFields() {
		languageLabel.setEnabled(false);
		algorithmGroup.setEnabled(false);
		commentLabel.setEnabled(false);
		commentText.setEnabled(false);
		languageCombo.setEnabled(false);
	}

	private void updateAlgFields() {
		algorithmGroup.setText(Messages.ECAlgorithmGroup_Title + " " + currentAlgorithm.getName());
		commentText.setText(getAlgorithm().getComment());
		languageCombo.select(languageCombo.indexOf(getAlgorithmTypeString(getAlgorithm())));
		if(null != currentAlgEditor){
			currentAlgEditor.setAlgorithmText(((TextAlgorithm)getAlgorithm()).getText());
		}
	}
	
	private void initializeEditor(){	
		if(null != currentAlgEditor){
			currentAlgEditor.removeDocumentListener(listener);
		}
		String algType = getAlgorithmTypeString(getAlgorithm());
		currentAlgEditor = editors.get(algType);
		if(null != currentAlgEditor){
			stack.topControl = currentAlgEditor.getControl();
			currentAlgEditor.addDocumentListener(listener);
		}
		codeEditors.layout();
	}

	
	private static String getAlgorithmTypeString(Algorithm algorithm) {
		if (algorithm instanceof STAlgorithm) {
			return "ST"; //$NON-NLS-1$
		} 
		if (algorithm instanceof TextAlgorithm) {
			return "AnyText"; //$NON-NLS-1$
		}
		return "AnyText";  //per default return any text and show it as generic text //$NON-NLS-1$
	}
	
	private void fillLanguageDropDown(){
		languageCombo.removeAll();
		for(String alg : AbstractECSection.getLanguages()){
			languageCombo.add(alg);
		}
	}
}
