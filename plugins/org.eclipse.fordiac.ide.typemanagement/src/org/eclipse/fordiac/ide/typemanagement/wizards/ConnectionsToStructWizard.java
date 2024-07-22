package org.eclipse.fordiac.ide.typemanagement.wizards;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ReplaceStructRefactoring;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

public class ConnectionsToStructWizard extends RefactoringWizard { // abstractsaveaswizard
	private ConnectionsToStructWizardPage choosePage;
	private DataType createdType;
	private String sourceName;
	private String destinationName;
	private boolean conflictResolution;
	private final IStructuredSelection selection;

	public ConnectionsToStructWizard(final IStructuredSelection selection, final ReplaceStructRefactoring refactoring) {
		super(refactoring, DIALOG_BASED_USER_INTERFACE | PREVIEW_EXPAND_FIRST_NODE);
		this.selection = selection;
		setWindowTitle("Connections to Struct");
	}

//	@Override
//	public boolean performFinish() {
//		final IFile targetFile = null; // = getTargetTypeFile();
////		protected boolean perform() {
////			newFilePage.saveWidgetValues();
////
////			final IFile targetFile = getTargetTypeFile();
////		if (targetFile.exists() && !MessageDialog.openConfirm(getShell(), "Datatype already exists",
////				"Overwrite existing datatype file?")) {
////			return false;
////		}
////			return true;
////		}
//		final TypeEntry entry = createTypeEntry(targetFile);
//
//		final StructuredType type = DataFactory.eINSTANCE.createStructuredType();
//
//		final List<VarDeclaration> varDecl = selection.stream().filter(EditPart.class::isInstance)
//				.map(EditPart.class::cast).map(EditPart::getModel).filter(Connection.class::isInstance)
//				.map(Connection.class::cast).map(Connection::getSource).filter(VarDeclaration.class::isInstance)
//				.map(VarDeclaration.class::cast).toList();
//
//		InterfaceListCopier.copyVarList(type.getMemberVariables(), varDecl, true);
//
//		TypeManagementPreferencesHelper.setupVersionInfo(type);
//		final String datatypeName = TypeEntry.getTypeNameFromFile(targetFile);
//		type.setName(datatypeName);
//		try {
//			entry.save(type, new NullProgressMonitor());
//		} catch (final CoreException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		createdType = type;
//		sourceName = choosePage.getSourceName();
//		destinationName = choosePage.getDestinationName();
//		conflictResolution = choosePage.getConflictResolution();
//		return true;
//	}

	@Override
	protected void addUserInputPages() {
		choosePage = new ConnectionsToStructWizardPage("Connections to Struct", selection);
		addPage(choosePage);
	}

//	public DataType getCreatedDataType() {
//		return createdType;
//	}
//
//	public String getSourceName() {
//		return sourceName;
//	}
//
//	public String getDestinationName() {
//		return destinationName;
//	}
//
//	public boolean getConflictResolution() {
//		return conflictResolution;
//	}
//
//	private TypeEntry createTypeEntry(final IFile targetTypeFile) {
////		final EObject root = EcoreUtil.getRootContainer((EObject)selection.getFirstElement());
////		if (root instanceof final LibraryElement libEl) {
////
////			return TypeLibraryManager.INSTANCE.getTypeLibrary(libEl.getTypeLibrary().getProject()).createTypeEntry(targetTypeFile);
////		}
////		return null;
//		return TypeLibraryManager.INSTANCE
//				.getTypeLibrary(((Connection) ((EditPart) selection.getFirstElement()).getModel()).getSourceElement()
//						.getTypeLibrary().getProject())
//				.createTypeEntry(targetTypeFile);
//	}

//	// @Override
//	public IFile getTargetTypeFile() {
//		final Path path = new Path(choosePage.getContainerFullPath() + File.separator + choosePage.getFileName()
//				+ TypeLibraryTags.DATA_TYPE_FILE_ENDING_WITH_DOT);
//		return ResourcesPlugin.getWorkspace().getRoot().getFile(path);
//	}

}