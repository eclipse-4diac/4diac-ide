/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH
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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeConditionEventCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeConditionExpressionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeECTransitionCommentCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECTransitionEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditor;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorFactory;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorModelAccess;
import org.eclipse.xtext.ui.editor.embedded.IEditedResourceProvider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

@SuppressWarnings("restriction")
public class TransitionSection extends AbstractECSection {
	private static final String ONE_CONDITION = "1"; //$NON-NLS-1$
	private static final String LINKING_FILE_EXTENSION = "xtextfbt";   //$NON-NLS-1$
	private Text commentText;
	private Combo eventCombo;
	Composite conditionEditingContainer;
	//the closing braket lable need for puting the xtext editor before it.
	CLabel closingBraket;
	private EmbeddedEditor editor;
	Composite composite;	
	@Inject protected EmbeddedEditorFactory editorFactory;
	@Inject private Provider<XtextResourceSet> resourceSetProvider;
	@Inject @Named(Constants.FILE_EXTENSIONS)
	public String fileExtension;
	EmbeddedEditorModelAccess embeddedEditorModelAccess;
	
	private final IDocumentListener listener = new IDocumentListener() {
		@Override
		public void documentChanged(final DocumentEvent event) {
			executeCommand(new ChangeConditionExpressionCommand(getType(), embeddedEditorModelAccess.getEditablePart()));	
		}

		@Override
		public void documentAboutToBeChanged(final DocumentEvent event) {
			//nothing todo here
		}
	};
	
	@Override
	protected ECTransition getType() {
		return (ECTransition) type;
	}
	
	protected BasicFBType getBasicFBType() {
		return (null != getType().eContainer()) ? (BasicFBType) getType().eContainer().eContainer() : null;
	}
	
	@Override
	protected Object getInputType(Object input) {
		if(input instanceof ECTransitionEditPart){
			return ((ECTransitionEditPart) input).getCastedModel();	
		}
		if(input instanceof ECTransition){
			return input;	
		}
		return null;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);		
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, "Condition:"); 
		
		createConditionEditingPlaceHolder(composite);
		
		getWidgetFactory().createCLabel(composite, "Comment:"); 
		commentText = createGroupText(composite, true);	
		commentText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeECTransitionCommentCommand(getType(), commentText.getText()));
				addContentAdapter();
			}
		});
	}
	
	/** creates a composite where the transition condition editing widgets can be placed later on.
	 * 
	 * The reason for having this is that the condition expression editor needs the FB Type for 
	 * context resolution (e.g., allow resolving of data inputs outputs and internal variables).
	 * The type is only available later therefore these widgets will be created when the input is set.
	 * 
	 * @param parent
	 */
	private void createConditionEditingPlaceHolder(Composite parent){
		conditionEditingContainer = getWidgetFactory().createComposite(parent);
		conditionEditingContainer.setLayout(new GridLayout(4,false));
		GridData compositeLayoutData = new GridData(SWT.FILL, 0, true, false);
		compositeLayoutData.verticalIndent = 0;
		conditionEditingContainer.setLayoutData(compositeLayoutData);
		
		eventCombo = new Combo(conditionEditingContainer, SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER);
		eventCombo.addListener( SWT.Selection, event -> {
				removeContentAdapter();
				executeCommand(new ChangeConditionEventCommand(getType(), eventCombo.getText()));
				checkEnablement();
				addContentAdapter();
			});
		
		getWidgetFactory().createCLabel(conditionEditingContainer, "["); //$NON-NLS-1$
		
		closingBraket = getWidgetFactory().createCLabel(conditionEditingContainer, "]"); //$NON-NLS-1$
	}

	@Override
	protected void setInputInit() {
		if(null == editor){
			createTransitionEditor(conditionEditingContainer);
		}
	}

	@Override
	protected void setInputCode() {
		commentText.setEnabled(false);
		eventCombo.removeAll();
		eventCombo.setEnabled(false);
	}	
		
	private void createTransitionEditor(Composite parent) {
		FBType fbType = getBasicFBType();	

		IEditedResourceProvider resourceProvider = new IEditedResourceProvider() {
			
			@Override
			public XtextResource createResource() {
				XtextResourceSet resourceSet = resourceSetProvider.get();
				EcoreUtil.Copier copier = new EcoreUtil.Copier();
				Resource fbResource = resourceSet.createResource(computeUnusedUri(resourceSet, LINKING_FILE_EXTENSION));
				fbResource.getContents().add(copier.copy(EcoreUtil.getRootContainer(fbType)));
				for(AdapterDeclaration adapter : fbType.getInterfaceList().getSockets()) {
					createAdapterResource(resourceSet, copier, adapter);
				}
				for(AdapterDeclaration adapter : fbType.getInterfaceList().getPlugs()) {
					createAdapterResource(resourceSet, copier, adapter);
				}
				copier.copyReferences();
				Resource resource = resourceSet.createResource(computeUnusedUri(resourceSet, fileExtension));
				return (XtextResource) resource;
			}

			private void createAdapterResource(XtextResourceSet resourceSet, EcoreUtil.Copier copier,
					AdapterDeclaration adapter) {
				if(adapter.getType() instanceof AdapterType){
					Resource adapterResource = resourceSet.createResource(computeUnusedUri(resourceSet, LINKING_FILE_EXTENSION));
					copier.copy(adapter.getType());
					adapterResource.getContents().add(copier.copy(EcoreUtil.getRootContainer(((AdapterType)adapter.getType()).getAdapterFBType())));
				}
			}

			protected URI computeUnusedUri(ResourceSet resourceSet, String fileExtension) {
				String name = "__synthetic"; //$NON-NLS-1$
				for (int i = 0; i < Integer.MAX_VALUE; i++) {
					URI syntheticUri = URI.createURI(name + i + "." + fileExtension); //$NON-NLS-1$
					if (resourceSet.getResource(syntheticUri, false) == null)
						return syntheticUri;
				}
				throw new IllegalStateException();
			}
		};

		editor = editorFactory.newEditor(resourceProvider).withParent(parent);
		StyledText conditionText = (StyledText)editor.getViewer().getControl(); 
		conditionText.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		conditionText.setBottomMargin(5);
		conditionText.setTopMargin(5);
		conditionText.setLeftMargin(5);
		conditionText.setRightMargin(5);
		conditionText.moveAbove(closingBraket);

		embeddedEditorModelAccess = editor.createPartialEditor();
		editor.getDocument().addDocumentListener(listener);
		composite.layout();
	}

	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;		
		if(null != type && null != getBasicFBType()) {
			setEventConditionDropdown();	
			commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			updateConditionExpressionText(getType().getConditionExpression());
			if(getType().getConditionExpression() != null && getType().getConditionExpression().equals(ONE_CONDITION)){
				eventCombo.select(1);
			}else{
				eventCombo.select(getType().getConditionEvent() != null ? 
					eventCombo.indexOf(getType().getConditionEvent().getName()) : 0);
			}
			checkEnablement();
		} 
		commandStack = commandStackBuffer;
	}

	private void updateConditionExpressionText(String conditionExpression) {
		if(null != embeddedEditorModelAccess){
			embeddedEditorModelAccess.updateModel((null != conditionExpression) ? conditionExpression : ""); //$NON-NLS-1$
		}
	}

	private void checkEnablement() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if(getType().getConditionExpression() != null && getType().getConditionExpression().equals(ONE_CONDITION)){
			updateConditionExpressionText(""); //$NON-NLS-1$
			editor.getViewer().getControl().setEnabled(false);
		}else{
			editor.getViewer().getControl().setEnabled(true);
		}
		commandStack = commandStackBuffer;
	}
	
	public void setEventConditionDropdown(){
		eventCombo.removeAll();
		eventCombo.add(""); //$NON-NLS-1$
		eventCombo.add(ONE_CONDITION);
		for(Event event : getBasicFBType().getInterfaceList().getEventInputs()){
			eventCombo.add(event.getName());
		}
		for(AdapterDeclaration adapter : getBasicFBType().getInterfaceList().getPlugs()){
			if(adapter.getType() instanceof AdapterType){
				for(Event event : ((AdapterType) adapter.getType()).getInterfaceList().getEventInputs()){
					eventCombo.add(adapter.getName() + "." + event.getName()); //$NON-NLS-1$
				}
			}
		}
		for(AdapterDeclaration adapter : getBasicFBType().getInterfaceList().getSockets()){
			if(adapter.getType() instanceof AdapterType){
				for(Event event : ((AdapterType) adapter.getType()).getInterfaceList().getEventOutputs()){
					eventCombo.add(adapter.getName() + "." + event.getName()); //$NON-NLS-1$
				}
			}
		}
	}
}
