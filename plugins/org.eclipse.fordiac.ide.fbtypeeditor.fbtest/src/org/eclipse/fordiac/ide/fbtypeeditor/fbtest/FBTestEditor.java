/*******************************************************************************
 * Copyright (c) 2011 - 2017 TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtest;

import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.FBTypeEditDomain;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtest.commands.StartAutomaticRemoteTest;
import org.eclipse.fordiac.ide.model.Palette.provider.PaletteItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.data.provider.DataItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.provider.LibraryElementItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;

public class FBTestEditor extends GraphicalEditorWithFlyoutPalette implements IFBTEditorPart {
	private FBType type;
	private Composite resultContainer;
	private Document document;
	boolean blockListeners = false;
	private TextViewer resultViewer;
	protected ComposedAdapterFactory adapterFactory;
	private TreeViewer serviceSequencesViewer;
	private CommandStack commandStack;
	
	private final EContentAdapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
		}
	};

	public FBTestEditor() {
		document = new Document();
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input)
			throws PartInitException {
		setInput(input);
		if (input instanceof FBTypeEditorInput) {
			FBTypeEditorInput untypedInput = (FBTypeEditorInput) input;
			type = untypedInput.getContent();
			type.eAdapters().add(adapter);
		}
		setSite(site);
		setEditDomain(new FBTypeEditDomain(this, commandStack));
		setPartName("FBTest");
		setTitleImage(FordiacImage.ICON_FBTest.getImage());
		super.init(site, input);		
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(final Composite parent) {
		parent.setLayout(new GridLayout());	
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createTestControl(parent);
		createResultView(parent);
	}

	protected void createResultView(final Composite parent) {
		GridData testResultGridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		SashForm resform = new SashForm(parent, SWT.HORIZONTAL | SWT.FILL);
		resform.setLayout(new GridLayout());
		resform.setLayoutData(testResultGridData);
		createSequenceTreeViewer(resform);  
		resultContainer = new Composite(resform, SWT.BORDER);		
		resultContainer.setLayout(new GridLayout());
		resultContainer.setLayoutData(testResultGridData);
		resultViewer = new TextViewer(resultContainer, SWT.V_SCROLL);
		resultViewer.getControl().setLayoutData(testResultGridData);
		resultViewer.setDocument(document);
		document.addDocumentListener(listener);
		document.set("");
	}

	protected void createTestControl(final Composite parent) {
		Composite seqContainer = new Composite(parent, SWT.BORDER);
		seqContainer.setLayout(new GridLayout());
		seqContainer.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		Composite buttonContainer = new Composite(seqContainer, SWT.NONE);
		buttonContainer.setLayout(new FillLayout());
		Button addButton = new Button(buttonContainer, SWT.NONE);
		addButton.setImage(FordiacImage.ICON_Download.getImage());
		addButton.setText("Start Test");
		addButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				StartAutomaticRemoteTest cmd = new StartAutomaticRemoteTest(type, document);
				getCommandStack().execute(cmd);
				//setDirty();
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// nothing to do
			}
		});
	}

	private final IDocumentListener listener = new IDocumentListener() {
		@Override
		public void documentChanged(final DocumentEvent event) {
		resultViewer.setDocument(document = new Document(event.getDocument().get()));
			resultViewer.refresh();
		}

		@Override
		public void documentAboutToBeChanged(final DocumentEvent event) {
		}
	};

	@Override
	public void setFocus() {
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part,
			final ISelection selection) {
	}

	@Override
	public void dispose() {
		if (type != null && type.eAdapters().contains(adapter)) {
			type.eAdapters().remove(adapter);
		}
		super.dispose();
	}

	@Override
	public boolean outlineSelectionChanged(Object selectedElement) {
		boolean bRetVal = false;
		return bRetVal;
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		return null;
	}
	
	private void createSequenceTreeViewer(Composite composite) {
		serviceSequencesViewer = new TreeViewer(composite, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData serviceSequenceData = new GridData();
		serviceSequenceData.horizontalAlignment = GridData.FILL;
		serviceSequenceData.verticalAlignment = GridData.FILL;
		serviceSequenceData.grabExcessHorizontalSpace = true;
		serviceSequenceData.grabExcessVerticalSpace = true;
		serviceSequencesViewer.getTree().setLayoutData(serviceSequenceData);
		serviceSequencesViewer.setContentProvider(new AdapterFactoryContentProvider(
				getAdapterFactory()));
//		AdapterFactoryLabelProvider myLP=new AdapterFactoryLabelProvider.ColorProvider(
//				getAdapterFactory(), Display.getCurrent().getSystemColor(SWT.COLOR_RED), Display.getCurrent().getSystemColor(SWT.COLOR_GREEN));	
		ColumnLabelProvider myLP=new TestResultLabelProvider();
//				getAdapterFactory());		
//		myLP.addListener(new ILabelProviderListener() {
//			
//			@Override
//			public void labelProviderChanged(LabelProviderChangedEvent event) {
//			}
//		});
		serviceSequencesViewer.setLabelProvider(myLP);
		new AdapterFactoryTreeEditor(serviceSequencesViewer.getTree(), adapterFactory);
		//createContextMenuFor(serviceSequencesViewer);		
		serviceSequencesViewer.setInput(type.getService());		
//		serviceSequencesViewer.addSelectionChangedListener(new ISelectionChangedListener() {
//			public void selectionChanged(
//					final SelectionChangedEvent event) {
//				if (event.getSelection() instanceof StructuredSelection) {
//					StructuredSelection selection = (StructuredSelection) event
//							.getSelection();										
//					if(!blockGraphicalViewerUpdate){
//						ServiceSequence selectedSequence = null;
//						if(selection.getFirstElement() instanceof ServiceSequence){
//							selectedSequence = (ServiceSequence) selection.getFirstElement();
//						}
//						else if(selection.getFirstElement() instanceof ServiceTransaction){
//							selectedSequence = (ServiceSequence)((ServiceTransaction) selection.getFirstElement()).eContainer();
//						}
//						else if(selection.getFirstElement() instanceof InputPrimitive){
//							selectedSequence = (ServiceSequence)((InputPrimitive) selection.getFirstElement()).eContainer().eContainer();
//						}
//						else if(selection.getFirstElement() instanceof OutputPrimitive){
//							selectedSequence = (ServiceSequence)((OutputPrimitive) selection.getFirstElement()).eContainer().eContainer();
//						}
//						
//						if(null != selectedSequence){
//							((SequenceRootEditPart)getGraphicalViewer().getRootEditPart().getContents()).setSelectedSequence(selectedSequence);							
//						}
//					}
//				}
//			}
//		});
	}

	private ComposedAdapterFactory getAdapterFactory() {		
		if(null == adapterFactory){
			adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
			adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
			adapterFactory.addAdapterFactory(new PaletteItemProviderAdapterFactory());
			adapterFactory.addAdapterFactory(new LibraryElementItemProviderAdapterFactory());
			adapterFactory.addAdapterFactory(new DataItemProviderAdapterFactory());
			adapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
			adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		}		
		return adapterFactory;	
	}

	public class TestResultLabelProvider extends ColumnLabelProvider implements ILabelDecorator{
		private static final int TestOK = 1;
		private static final int TestFailed = -1;
		private static final int NoTest = 0;	
		private AdapterFactoryLabelProvider AFLP;	
		HashMap<Integer,ImageDescriptor> decorators=new HashMap<Integer,ImageDescriptor>();
		
		public TestResultLabelProvider() {
			AFLP = new AdapterFactoryLabelProvider(getAdapterFactory());
			decorators.put(Integer.valueOf(TestFailed), FordiacImage.ICON_TestFailed.getImageDescriptor());
			decorators.put(Integer.valueOf(TestOK), FordiacImage.ICON_TestOK.getImageDescriptor());
			decorators.put(Integer.valueOf(NoTest), FordiacImage.ICON_NoTest.getImageDescriptor());
		}

		@Override
		public Image getImage(Object element) {
			return AFLP.getImage(element);
		}

		@Override
		public String getText(Object element) {
			return AFLP.getText(element);
		}

		private int getStatus(Object object) {
			int retval = 0; 
			if (object instanceof ServiceTransaction) {
				retval = ((ServiceTransaction) object).getTestResult();
			} else if (object instanceof ServiceSequence) {
				retval = (((ServiceSequence) object).getTestResult());
			} else {
				if (object instanceof InputPrimitive) {
//					retval = getStatus(((InputPrimitive)object).eContainer());
					retval=-17;
				}
				if (object instanceof OutputPrimitive) {
					retval = ((OutputPrimitive)object).getTestResult();
				}
			}
				return retval;
		}
		
		@Override
		public Color getBackground(Object object) {
			Color retval = null;
			int status = getStatus(object);	
			switch (status) {
				case -1:
					retval = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
					break;
				case 1:
					retval = Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
					break;
				default:
					break;
				}
			if (null != retval) {
//				return retval;
			}
			return super.getBackground(object);
		}

		public void update(ViewerCell cell) {
			Object element = cell.getElement();
			cell.setText(getText(element));
			Image image = getImage(element);
			Image decImage = decorateImage(image, element);
			cell.setBackground(getBackground(element));
			cell.setForeground(getForeground(element));
			cell.setImage(decImage);
			cell.setFont(getFont(element));
		}

		@Override
		public Image decorateImage(Image image, Object element) {
			ImageDescriptor IDesc = decorators.get(getStatus(element));
			Image retval;
			if (null != IDesc) {			
				DecorationOverlayIcon DOC = new DecorationOverlayIcon(image, IDesc, org.eclipse.jface.viewers.IDecoration.BOTTOM_RIGHT);
				retval = DOC.createImage();
			} else {
				retval = image;
			}
			return retval;
		}

		@Override
		public String decorateText(String text, Object element) {
			return null;
		}	
	}
	
	@Override
	public void setCommonCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
	}
}
