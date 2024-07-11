package org.eclipse.fordiac.ide.typemanagement.wizards;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.helpers.InterfaceListCopier;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.preferences.TypeManagementPreferencesHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;

public class ConnectionsToStructWizard extends Wizard { // abstractsaveaswizard
	private ConnectionsToStructWizardPage choosePage;
	private DataType createdType;
	private String sourceName;
	private String destinationName;
	private boolean conflictResolution;
	private final IStructuredSelection selection;

	public ConnectionsToStructWizard(final IStructuredSelection selection) {
		this.selection = selection;
		setWindowTitle("Connections to Struct");
	}

	@Override
	public boolean performFinish() {
		final IFile targetFile = getTargetTypeFile();
//		protected boolean perform() {
//			newFilePage.saveWidgetValues();
//
//			final IFile targetFile = getTargetTypeFile();
		if (targetFile.exists() && !MessageDialog.openConfirm(getShell(), "Datatype already exists",
				"Overwrite existing datatype file?")) {
			return false;
		}
//			return true;
//		}
		final TypeEntry entry = createTypeEntry(targetFile);

		final StructuredType type = DataFactory.eINSTANCE.createStructuredType();

		final List<VarDeclaration> varDecl = selection.stream().filter(EditPart.class::isInstance)
				.map(EditPart.class::cast).map(EditPart::getModel).filter(Connection.class::isInstance)
				.map(Connection.class::cast).map(Connection::getSource).filter(VarDeclaration.class::isInstance)
				.map(VarDeclaration.class::cast).toList();

		InterfaceListCopier.copyVarList(type.getMemberVariables(), varDecl, true);

		TypeManagementPreferencesHelper.setupVersionInfo(type);
		final String datatypeName = TypeEntry.getTypeNameFromFile(targetFile);
		type.setName(datatypeName);
		try {
			entry.save(type, new NullProgressMonitor());
		} catch (final CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createdType = type;
		sourceName = choosePage.getSourceName();
		destinationName = choosePage.getDestinationName();
		conflictResolution = choosePage.getConflictResolution();
		return true;
	}

	@Override
	public void addPages() {
		choosePage = new ConnectionsToStructWizardPage("Connections to Struct", selection);
		addPage(choosePage);
	}

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
//		final EObject root = EcoreUtil.getRootContainer((EObject)selection.getFirstElement());
//		if (root instanceof final LibraryElement libEl) {
//
//			return TypeLibraryManager.INSTANCE.getTypeLibrary(libEl.getTypeLibrary().getProject()).createTypeEntry(targetTypeFile);
//		}
//		return null;
		return TypeLibraryManager.INSTANCE
				.getTypeLibrary(((Connection) ((EditPart) selection.getFirstElement()).getModel()).getSourceElement()
						.getTypeLibrary().getProject())
				.createTypeEntry(targetTypeFile);
	}

	// @Override
	public IFile getTargetTypeFile() {
		final Path path = new Path(choosePage.getContainerFullPath() + File.separator + choosePage.getFileName()
				+ TypeLibraryTags.DATA_TYPE_FILE_ENDING_WITH_DOT);
		return ResourcesPlugin.getWorkspace().getRoot().getFile(path);
	}

}