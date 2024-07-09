package org.eclipse.fordiac.ide.typemanagement.refactoring;



import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.helpers.InterfaceListCopier;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.preferences.TypeManagementPreferencesHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.handlers.HandlerUtil;

public class ConnectionsToStructHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final CommandStack commandStack = editor.getAdapter(CommandStack.class);

		System.out.println(HandlerUtil.getCurrentSelection(event));
		window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		// final CommandStack commandStack = editor.getAdapter(CommandStack.class);
		// final FBNetworkElement fb = getNetworkElementFromSelectedPins(sel);

		if (window != null) {
			IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
			Object firstElement = selection.getFirstElement();
			List<String> sourceVarNames = selection.stream().map(t -> ((Connection)((EditPart) t).getModel()).getSource().getName()).toList();

			List<String> destinationVarNames = selection.stream().map(t -> ((Connection)((EditPart) t).getModel()).getDestination().getName()).toList();
			

			List<VarDeclaration> varDecls = selection.stream()
					.map(t -> (VarDeclaration) ((Connection)((EditPart) t).getModel()).getSource()).toList();
			List<String> varWiths = varDecls.stream().map(t -> t.getWiths()).flatMap(List::stream).map(t -> ((Event)t.eContainer()).getName()).distinct().toList();

			List<VarDeclaration> varDecld = selection.stream()
					.map(t -> (VarDeclaration) ((Connection)((EditPart) t).getModel()).getDestination()).toList();
			List<String> varWithd = varDecld.stream().map(t -> t.getWiths()).flatMap(List::stream).map(t -> ((Event)t.eContainer()).getName()).distinct().toList();

			List<Connection> connections = selection.stream()
					.filter(EditPart.class::isInstance)
					.map(EditPart.class::cast)
					.map(t -> t.getModel())
					.filter(Connection.class::isInstance)
					.map(Connection.class::cast)
					.toList();
			FBType sourceType = connections.get(0).getSourceElement().getType();
			FBType destinationType = connections.get(0).getDestinationElement().getType();			
			
			//TODO: check if cast to VarDeclaration is valid
			List<VarDeclaration> sourceVarDeclarations = connections.stream()
					.map(con -> (VarDeclaration)con.getSource()).toList();
			List<String> sourceWiths = varDecls.stream()
					.map(var -> var.getWiths())
					.flatMap(List::stream)
					.map(width -> ((Event)width.eContainer()).getName()).distinct().toList();

			List<VarDeclaration> destinationVarDeclarations = connections.stream()
					.map(con -> (VarDeclaration)con.getDestination()).toList();
			List<String> destinationWiths = varDecld.stream()
					.map(var -> var.getWiths())
					.flatMap(List::stream)
					.map(width -> ((Event)width.eContainer()).getName()).distinct().toList();
			
//			
//			ExtractStructTypeWizard structWizard = new ExtractStructTypeWizard(varDecls,
//					getProject(((ConnectionEditPart) firstElement).getModel().getSourceElement()),
//					Messages.ConvertToStructHandler_Title);
//			final WizardDialog structDialog = new WizardDialog(editor.getSite().getShell(), structWizard);
//			structDialog.create();
//			structDialog.open();
			
			
			
			
			
			class ConnectionsToStructPage extends WizardNewFileCreationPage {
				
				private Text sourceNameText;
				private Text destinationNameText;
				private Button conflictButton;
				
				protected ConnectionsToStructPage(final String pageName, IStructuredSelection selection) {
					super(pageName, selection);
					this.setTitle("Convert Connections to Structured Type");
					this.setDescription("Store new Type in Library. Select the name of the In/Output Vars. Choose how to resolve conflicts");
				}

				@Override
				public void createControl(final Composite parent) {
					super.createControl(parent);
					final Composite composite = (Composite) getControl();
					setErrorMessage(null);
					setMessage(null);
					setControl(composite);
				}
				
				@Override
				protected void createAdvancedControls(Composite parent) {
					final Composite container = new Composite(parent, SWT.NONE);
					GridData containerData = new GridData();
					containerData.horizontalAlignment = GridData.FILL;
					containerData.grabExcessHorizontalSpace = true;
					container.setLayoutData(containerData);
					
					GridLayout gridl = new GridLayout();
					gridl.numColumns = 2;
					gridl.marginWidth = 0;
					container.setLayout(gridl);
					
					final Label sourceNameLabel = new Label(container, NONE);
					sourceNameLabel.setText("Function Block Output Name" + ":");
					sourceNameText = new Text(container, SWT.SINGLE | SWT.BORDER);
					//TODO: wrong ID?
					sourceNameText.addListener(SWT.ERROR, this);
					
					final Label destinationNameLabel = new Label(container, NONE);
					destinationNameLabel.setText("Function Block Input Name" + ":");
					destinationNameText = new Text(container, SWT.BORDER);
					destinationNameText.addListener(SWT.ERROR, this);
					
					GridData textGridData = new GridData();
					textGridData.horizontalAlignment = GridData.FILL;
					textGridData.grabExcessHorizontalSpace = true;
					sourceNameText.setLayoutData(textGridData);
					destinationNameText.setLayoutData(textGridData);
					
					conflictButton = new Button(container, SWT.CHECK);
					conflictButton.setText("Solve conflicts with Multiplexer/Demultiplexer");
					conflictButton.setSelection(true);
					GridData checkboxGridData = new GridData();
					checkboxGridData.horizontalSpan = 2;
					conflictButton.setLayoutData(checkboxGridData);
					super.createAdvancedControls(parent);
				};
				
				public String getSourceName() {
					return sourceNameText.getText();
				}
				
				public String getDestinationName() {
					return destinationNameText.getText();
				}
				
				public boolean getConflictResolution() {
					return conflictButton.getSelection();
				}
				
				protected boolean validatePage() {
					return super.validatePage() && !sourceNameText.getText().isBlank() && !destinationNameText.getText().isBlank();
				};

				public boolean isPageComplete() {
					return super.isPageComplete();
				};

			}
			
			class testwizard extends Wizard { //abstractsaveaswizard
				private ConnectionsToStructPage choosePage;
				private DataType createdType;
				private String sourceName;
				private String destinationName;
				private boolean conflictResolution;
				
				@Override
				public boolean performFinish() {
					final IFile targetFile = getTargetTypeFile();
					final TypeEntry entry = createTypeEntry(targetFile);

					final StructuredType type = DataFactory.eINSTANCE.createStructuredType();
					
					List<VarDeclaration> varDecl = selection.stream()
							.filter(EditPart.class::isInstance)
							.map(EditPart.class::cast)
							.map(EditPart::getModel)
							.filter(Connection.class::isInstance)
							.map(Connection.class::cast)
							.map(Connection::getSource)
							.filter(VarDeclaration.class::isInstance)
							.map(VarDeclaration.class::cast).toList();
					
					InterfaceListCopier.copyVarList(type.getMemberVariables(), varDecl, true);

					TypeManagementPreferencesHelper.setupVersionInfo(type);
					String datatypeName = TypeEntry.getTypeNameFromFile(targetFile);
					type.setName(datatypeName);
					try {
						entry.save(type, new NullProgressMonitor());
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					createdType = type;
					sourceName = choosePage.getSourceName();
					destinationName = choosePage.getDestinationName();
					conflictResolution = choosePage.getConflictResolution();
					return true;
				}
				
				public void addPages() {
					choosePage = new ConnectionsToStructPage("Connections to Struct", selection);
					addPage(choosePage);
				};
				
				public DataType getCreatedDataType() {
					return createdType;
				}
				
				public String getSourceName() {
					return sourceName;
				}
				
				public String getDestinationName() {
					return destinationName;
				}
				
				public boolean getConflictResolution() {
					return conflictResolution;
				}
				
				private TypeEntry createTypeEntry(final IFile targetTypeFile) {
//					final EObject root = EcoreUtil.getRootContainer((EObject)selection.getFirstElement());
//					if (root instanceof final LibraryElement libEl) {
//						
//						return TypeLibraryManager.INSTANCE.getTypeLibrary(libEl.getTypeLibrary().getProject()).createTypeEntry(targetTypeFile);
//					}
//					return null;
					return TypeLibraryManager.INSTANCE.getTypeLibrary(((Connection)((EditPart)selection.getFirstElement()).getModel()).getSourceElement().getTypeLibrary().getProject()).createTypeEntry(targetTypeFile);
				}


//				@Override
//				protected boolean askOverwrite() {
//					// TODO Auto-generated method stub
//					return false;
//				}

				//@Override
				public IFile getTargetTypeFile() {
					final Path path = new Path(choosePage.getContainerFullPath() + File.separator + choosePage.getFileName()
							+ TypeLibraryTags.DATA_TYPE_FILE_ENDING_WITH_DOT);
					return ResourcesPlugin.getWorkspace().getRoot().getFile(path);
				}
				
			}
			
			testwizard wizard = new testwizard();
			final WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().getActiveEditor().getSite().getShell(), wizard);
			final ActionListener alist = e -> dialog.updateButtons();
			wizard.setWindowTitle("Connections to Struct");
			dialog.create();


			if (dialog.open() == 0) {
				
				
				Map<String, String> replacableConMap = new HashMap<String, String>();
				connections.stream().forEach(con -> replacableConMap.put(con.getSource().getName(), con.getDestination().getName()));

				commandStack.execute(new ConnectionsToStructCommand(sourceType, destinationType, wizard.getCreatedDataType(), wizard.getSourceName(), wizard.getDestinationName(), replacableConMap, wizard.getConflictResolution()));
			}
		}
		
		return null;
	}

	private static IProject getProject(final FBNetworkElement fb) {
		final EObject root = EcoreUtil.getRootContainer(fb);
		if (root instanceof final LibraryElement libEl) {
			return libEl.getTypeLibrary().getProject();
		}
		return null;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final IWorkbenchPart part = (IWorkbenchPart) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_EDITOR_NAME);
		setBaseEnabled(isSelectedConnection());
		// setBaseEnabled((part != null) && (part.getAdapter(FBNetwork.class) != null));

	}

	private static boolean isSelectedConnection() {
		// final ISelection selection = HandlerUtil.selection
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			IInterfaceElement src;
			IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
			Optional opt = selection.stream().filter(sel -> !(sel instanceof EditPart con && con.getModel() instanceof DataConnection)).findAny();
			if (opt.isEmpty()) {
				// selection.stream().forEach(t ->
				// ((ConnectionEditPart)t).getModel().setBrokenConnection(true));
				return selection.stream().map(t -> ((Connection)((EditPart) t).getModel()).getSourceElement()).distinct()
						.count() == 1
						&& selection.stream().map(t -> ((Connection)((EditPart) t).getModel()).getDestinationElement())
								.distinct().count() == 1;
			}
		}
		return false;
	}
}
