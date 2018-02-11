/*******************************************************************************
 * Copyright (c) 2012 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *    - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.fbtester.model.testdata.TestData;
import org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.FBTypeEditDomain;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts.FBInterfaceEditPartFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.TestDataLabelProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.TestElement;
import org.eclipse.fordiac.ide.gef.ZoomUndoRedoContextMenuProvider;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.ruler.FordiacRulerComposite;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.gef.ui.rulers.RulerComposite;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

public class FBTester extends GraphicalEditor implements IFBTEditorPart {
	public static final String EXPECTED_EVENTS = "ExpectedEvents";
	public static final String EVENT = "Event";
	public static final String NAME = "Name";
	public static final String COLUMN_TYPE = "ColumnType";
	public static final String INPUT_VARIABLE = "InputVariable";
	public static final String OUTPUT_VARIABLE = "OutputVariable";
	private FBType type;
	private String path;
	private KeyHandler sharedKeyHandler;
	private StackLayout stack;
	protected Palette palette;
	private final Hashtable<String, IFBTestConfiguration> configurations = new Hashtable<String, IFBTestConfiguration>();
	private Composite configurationParent;
	private TableViewer testDataViewer;
	private CommandStack commandStack;
	private final Hashtable<String, TableColumn> dataColumns = new Hashtable<String, TableColumn>();
	private final ArrayList<TestData> testDataCollection = new ArrayList<TestData>();
	private RulerComposite rulerComp;
	
	EContentAdapter eContentAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			testDataViewer.refresh();
		}
	};

	public FBTester() {
		// empty constructor
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// not used
	}

	@Override
	public void doSaveAs() {
		// not used
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setInput(input);
		if (input instanceof FBTypeEditorInput) {
			FBTypeEditorInput untypedInput = (FBTypeEditorInput) input;
			type = untypedInput.getContent();
			
			EObject group = untypedInput.getPaletteEntry().getGroup();

			while (group.eContainer() != null) {
				group = group.eContainer();
			}
			if (group instanceof Palette) {
				palette = (Palette) group;
			}
		}
		setSite(site);
		setEditDomain(new FBTypeEditDomain(this, commandStack));
		setPartName("FBTester");
		setTitleImage(FordiacImage.ICON_FBTester.getImage());
		super.init(site, input);

	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(final Composite parent) {
		SashForm s = new SashForm(parent, SWT.VERTICAL | SWT.SMOOTH);
		Composite interfaceEditing = new Composite(s, SWT.BORDER);
		interfaceEditing.setLayout(new GridLayout(2, false));
		Label testConfiguraionLabel = new Label(interfaceEditing, SWT.NONE);
		testConfiguraionLabel.setText("Test Configuration");
		Combo configurationCombo = new Combo(interfaceEditing, SWT.BORDER | SWT.READ_ONLY);
		configurationCombo.setItems(getTestConfigurations().toArray(new String[0]));
		GridData configurationComboData = new GridData(SWT.FILL, SWT.TOP, false, false);
		configurationCombo.setLayoutData(configurationComboData);
		configurationCombo.addSelectionListener(new SelectionListener() {
			@Override public void widgetSelected(SelectionEvent e) {
				System.out.println(configurationCombo.getText());
				IFBTestConfiguration testConfiguration = configurations.get(configurationCombo.getText());
				stack.topControl = testConfiguration.getControl();
				configurationParent.layout();
			}
			@Override public void widgetDefaultSelected(SelectionEvent e) {
				// nothing to do!
			}
		});
		GridData configurationParentData = new GridData();
		configurationParentData.horizontalAlignment = GridData.FILL;
		configurationParentData.verticalAlignment = GridData.FILL;
		configurationParentData.grabExcessHorizontalSpace = true;
		configurationParentData.grabExcessVerticalSpace = true;
		configurationParentData.horizontalSpan = 2;
		configurationParent = new Group(interfaceEditing, SWT.NONE);
		configurationParent.setLayout(stack = new StackLayout());
		configurationParent.setLayoutData(configurationParentData);
		createConfigurations(configurationParent);
		SashForm horizontal = new SashForm(s, SWT.HORIZONTAL | SWT.SMOOTH);
		Composite testData = new Composite(horizontal, SWT.NONE);
		testData.setLayout(new GridLayout(1, true));
		createTestData(testData);
		Composite graphicaEditor = new Composite(horizontal, SWT.NONE);
		graphicaEditor.setLayout(new FillLayout());
		s.setWeights(new int[] { 30, 70 });
		horizontal.setWeights(new int[] { 50, 50 });
		super.createPartControl(graphicaEditor);
	}

	private void createTestData(Composite parent) {
		ToolBar tb = new ToolBar(parent, SWT.FLAT | SWT.HORIZONTAL);
		ToolItem runItem = new ToolItem(tb, SWT.NONE);
		runItem.setToolTipText("Run selected Tests");
		runItem.setImage(FordiacImage.ICON_RundDebug.getImage());
		ToolItem stopItem = new ToolItem(tb, SWT.NONE);
		stopItem.setToolTipText("Stop");
		stopItem.setImage(FordiacImage.ICON_Stop.getImage());
		ToolItem saveItem = new ToolItem(tb, SWT.NONE);
		saveItem.setToolTipText("Save Test Data");
		saveItem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ETOOL_SAVE_EDIT));
		saveItem.addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent event) {
						saveTestData();
					}
				});
		ToolItem addItem = new ToolItem(tb, SWT.NONE);
		addItem.setToolTipText("Add Test");
		addItem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
		addItem.addSelectionListener(
			new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					addTest();
				}
			});
		ToolItem deleteItem = new ToolItem(tb, SWT.NONE);
		deleteItem.setToolTipText("Delete Test");
		deleteItem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		deleteItem.addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent event) {
						Object selection = ((StructuredSelection)testDataViewer.getSelection()).getFirstElement();
						testDataCollection.remove(selection);
						testDataViewer.refresh();
					}
				});
		final Table table = new Table(parent, SWT.FULL_SELECTION | SWT.BORDER | SWT.CHECK);
		table.setLinesVisible(true);
		testDataViewer = new TableViewer(table);
		testDataViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				System.out.println(event);
				System.out.println(event.getSelection());
				if (event.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection) event.getSelection();
					if (selection.getFirstElement() instanceof TestData) {
						TestData data = (TestData) selection.getFirstElement();
						Map<String, TestElement> testElements = TestingManager.getInstance().getTestElements(type);
						for(Entry<String, TestElement> entry : testElements.entrySet()) {
							String value= data.getValueFor(entry.getValue().getPortString());
							if (value != null) {
								System.out.println("set value");
								testElements.get(entry.getKey()).setValue(value);
							}
						}
						TestElement eventToTrigger = null;
						Hashtable<String, TestElement> triggerElements = TestingManager.getInstance()
								.getTriggerElements(type);
						Enumeration<String> triggerkeys = triggerElements.keys();
						while (triggerkeys.hasMoreElements()) {
							String key = triggerkeys.nextElement();
							TestElement element = triggerElements.get(key);
							if (element.getInterfaceElement().getName().equals(data.getEvent().getName())) {
								eventToTrigger = element;
							}
						}
						if (eventToTrigger != null) {
							System.out.println(
									"-------------------------------------------------------------------------------- trigger event");
							eventToTrigger.sendEvent();
						}
					}
				}
				System.out.println("double clicked");
			}
		});
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		testDataViewer.getTable().setLayoutData(gridData);
		TableViewerColumn nameColumn = new TableViewerColumn(testDataViewer, SWT.LEFT);
		nameColumn.getColumn().setWidth(80);
		nameColumn.getColumn().setText(NAME);
		nameColumn.getColumn().setData(COLUMN_TYPE, NAME);
		nameColumn.setEditingSupport(new TestDataEditingSupport(testDataViewer, nameColumn.getColumn()));
		if (type != null) {
			if (type.getInterfaceList() != null) {
				createInputVariableColumns(testDataViewer, type.getInterfaceList().getInputVars());
				// createVariableColumns(testData,
				// type.getInterfaceList().getOutputVars());
			}
		}
		TableViewerColumn eventCol = new TableViewerColumn(testDataViewer, SWT.LEFT);
		eventCol.getColumn().setWidth(80);
		eventCol.getColumn().setText(EVENT);
		eventCol.getColumn().setData(COLUMN_TYPE, EVENT);
		TableViewerColumn expectedEvents = new TableViewerColumn(testDataViewer, SWT.LEFT);
		expectedEvents.getColumn().setWidth(100);
		expectedEvents.getColumn().setText("Expected Events");
		expectedEvents.getColumn().setData(COLUMN_TYPE, EXPECTED_EVENTS);
		if (type != null) {
			if (type.getInterfaceList() != null) {
				createOutputVariableColumns(testDataViewer, type.getInterfaceList().getOutputVars());
				// createVariableColumns(testData,
				// type.getInterfaceList().getOutputVars());
			}
		}
		table.setHeaderVisible(true);
		parseTestData();
		testDataViewer.setContentProvider(new IStructuredContentProvider() {
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}

			@Override
			public void dispose() {

			}

			@Override
			public Object[] getElements(Object inputElement) {
				return testDataCollection.toArray();
			}
		});
		testDataViewer.setLabelProvider(new TestDataLabelProvider(type, table));
		testDataViewer.setInput(new Object());
		MenuManager popupMenu = new MenuManager();
		IAction newRowAction = new NewTestDataAction();
		popupMenu.add(newRowAction);
		IAction saveAction = new SaveTestDataAction();
		popupMenu.add(saveAction);
		Menu menu = popupMenu.createContextMenu(table);
		table.setMenu(menu);
	}

	private void saveTestData() {
		if (path != null) {
			File testDataFile = new File(path + File.separatorChar + type.getName() + ".forte");
			try {
				FileOutputStream outStream = new FileOutputStream(testDataFile);
				DataOutputStream dataStream = new DataOutputStream(outStream);
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(dataStream));

				for (TestData testData : testDataCollection) {
					writer.write(testData._getLine());
					writer.newLine();
				}
				writer.flush();
				writer.close();
				dataStream.close();
				outStream.close();
			} catch (FileNotFoundException fnfe) {
			} catch (IOException io) {
			}
		}
	}

	private void parseTestData() {
		if (path != null) {
			File testDataFile = new File(path + File.separatorChar + type.getName() + ".forte");
			if (testDataFile.exists()) {
				FileInputStream fstream;
				BufferedReader data = null;
				try {
					fstream = new FileInputStream(testDataFile);
					DataInputStream in = new DataInputStream(fstream);
					data = new BufferedReader(new InputStreamReader(in));
					String line;
					while ((line = data.readLine()) != null) {
						TestData testData = createTestData(line);
						testDataCollection.add(testData);
					}
				} catch (Exception e) {
					Activator.getDefault().logError(e.getMessage(), e);
				} finally {
					if (null != data) {
						try {
							data.close();
						} catch (IOException e) {
							Activator.getDefault().logError(e.getMessage(), e);
						}
					}
				}
			}
		}
	}

	private TestData createTestData(String line) {
		TestData testData = TestdataFactory.eINSTANCE.createTestData();
		testData.eAdapters().add(eContentAdapter);
		testData.setType(type);
		testData.setLine(line);
		return testData;
	}

	private void createInputVariableColumns(TableViewer testData, List<VarDeclaration> list) {
		for (VarDeclaration input : list) {
			TableViewerColumn column = new TableViewerColumn(testData, SWT.LEFT);
			column.getColumn().setWidth(40);
			column.getColumn().setText(input.getName());
			column.getColumn().setData(COLUMN_TYPE, INPUT_VARIABLE);
			column.getColumn().setData(INPUT_VARIABLE, input);
			column.setEditingSupport(new TestDataEditingSupport(testDataViewer, column.getColumn()));
			dataColumns.put(input.getName(), column.getColumn());
		}
	}

	private void createOutputVariableColumns(TableViewer testData, List<VarDeclaration> list) {
		for (VarDeclaration output : list) {
			TableViewerColumn column = new TableViewerColumn(testData, SWT.LEFT);
			column.getColumn().setWidth(40);
			column.getColumn().setText(output.getName());
			column.getColumn().setData(COLUMN_TYPE, OUTPUT_VARIABLE);
			column.getColumn().setData(OUTPUT_VARIABLE, output);
			column.setEditingSupport(new TestDataEditingSupport(testDataViewer, column.getColumn()));
			dataColumns.put(output.getName(), column.getColumn());
		}
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
	}

	@Override
	protected void initializeGraphicalViewer() {
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setContents(getEditorInput());
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		ScrollingGraphicalViewer viewer = (ScrollingGraphicalViewer) getGraphicalViewer();
		ZoomScalableFreeformRootEditPart root = new ZoomScalableFreeformRootEditPart(getSite(), getActionRegistry());
		viewer.setRootEditPart(root);
		viewer.setEditPartFactory(getEditpartFactory());
		// configure the context menu provider
		ContextMenuProvider cmProvider = new ZoomUndoRedoContextMenuProvider(viewer, root.getZoomManager(),
				getActionRegistry()) {
			@Override
			public void buildContextMenu(IMenuManager menu) {
				super.buildContextMenu(menu);

				IAction action = registry.getAction(ActionFactory.DELETE.getId());
				menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);
			}
		};
		viewer.setContextMenu(cmProvider);
		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1), MouseWheelZoomHandler.SINGLETON);
		KeyHandler viewerKeyHandler = new GraphicalViewerKeyHandler(viewer).setParent(getCommonKeyHandler());
		viewer.setKeyHandler(viewerKeyHandler);
	}

	public ZoomManager getZoomManger() {
		return ((ScalableFreeformRootEditPart) (getGraphicalViewer().getRootEditPart())).getZoomManager();
	}

	protected FBInterfaceEditPartFactory getEditpartFactory() {
		return new FBInterfaceEditPartFactory(this, palette, getZoomManger());
	}

	protected KeyHandler getCommonKeyHandler() {
		if (sharedKeyHandler == null) {
			sharedKeyHandler = new KeyHandler();
			sharedKeyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
					getActionRegistry().getAction(ActionFactory.DELETE.getId()));
			sharedKeyHandler.put(KeyStroke.getPressed(SWT.F2, 0),
					getActionRegistry().getAction(GEFActionConstants.DIRECT_EDIT));
			sharedKeyHandler.put(/* CTRL + '=' */
					KeyStroke.getPressed('+', 0x3d, SWT.CTRL),
					getActionRegistry().getAction(GEFActionConstants.ZOOM_IN));

		}
		return sharedKeyHandler;
	}

	@Override
	protected void createGraphicalViewer(Composite parent) {
		rulerComp = new FordiacRulerComposite(parent, SWT.NONE);
		super.createGraphicalViewer(rulerComp);
		rulerComp.setGraphicalViewer((ScrollingGraphicalViewer) getGraphicalViewer());
	}

	public List<String> getTestConfigurations() {
		ArrayList<String> configurations = new ArrayList<String>();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint("org.eclipse.fordiac.ide.fbtypeeditor.fbtester.fbTesterConfiguration");
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IExtension extension = extensions[i];
			IConfigurationElement[] elements = extension.getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				IConfigurationElement element = elements[j];
				try {
					Object obj = element.createExecutableExtension("class");
					if (obj instanceof IFBTestConfiguratonCreator) {
						String label = element.getAttribute("label");
						configurations.add(label);
					}
				} catch (Exception e) {
					// nothing to do
				}
			}
		}
		return configurations;
	}

	public void createConfigurations(final Composite parent) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint("org.eclipse.fordiac.ide.fbtypeeditor.fbtester.fbTesterConfiguration");
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IExtension extension = extensions[i];
			IConfigurationElement[] elements = extension.getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				IConfigurationElement element = elements[j];
				try {
					Object obj = element.createExecutableExtension("class");
					if (obj instanceof IFBTestConfiguratonCreator) {
						((IFBTestConfiguratonCreator) obj).setType(type);
						((IFBTestConfiguratonCreator) obj).setGroup(palette.getRootGroup());

						IFBTestConfiguration configuration = ((IFBTestConfiguratonCreator) obj)
								.createConfigurationPage(parent);
						String lang = element.getAttribute("label");
						configurations.put(lang, configuration);
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}
	
	void addTest(){
		String dataLine = "Testname;";
		dataLine += type.getName();
		dataLine += ";";
		if (type.getInterfaceList().getEventInputs().size() > 0) {
			Event event = type.getInterfaceList().getEventInputs().get(0);
			dataLine += event.getWith().size() + ";"; // nr of inputs are the number of connected withs
			for (With with : event.getWith()){
				VarDeclaration var = with.getVariables();
				dataLine += var.getName() + ";" + "val" + ";";
			}
			dataLine += event.getName() + ";";
		}
		if (type.getInterfaceList().getEventOutputs().size() > 0) {
			dataLine += "1;";
			dataLine += type.getInterfaceList().getEventOutputs().get(0).getName();
			dataLine += ";";
		} else {
			dataLine += "0;";
		}
		if (type.getInterfaceList().getOutputVars().size() > 0) {
			dataLine += type.getInterfaceList().getOutputVars().size();
			dataLine += ";";
			for (VarDeclaration var : type.getInterfaceList().getOutputVars()) {
				dataLine += var.getName() + ";" + "val" + ";";
			}
		}
		TestData testData = createTestData(dataLine);
		testDataCollection.add(testData);
		testDataViewer.refresh();
	}
	

	private class SaveTestDataAction extends Action {
		public SaveTestDataAction() {
			super("Save Testdata");
		}

		@Override
		public void run() {
			saveTestData();
		}
	}

	private class NewTestDataAction extends Action {
		public NewTestDataAction() {
			super("New Test");
			setImageDescriptor(
					PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
		}

		@Override
		public void run() {
			addTest();
		}
	}

	@Override
	public boolean outlineSelectionChanged(Object selectedElement) {
		return false;
	}

	@Override
	public void setCommonCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
	}
}
