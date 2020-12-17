package org.eclipse.fordiac.ide.application.wizards;

import java.io.File;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.dataexport.DataTypeExporter;
import org.eclipse.fordiac.ide.model.helpers.InterfaceListCopier;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.model.ui.widgets.OpenStructMenu;
import org.eclipse.fordiac.ide.typemanagement.preferences.TypeManagementPreferencesHelper;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;

// TODO: code copied from SaveAsSubappWizard
// TODO: create SaveAsStructWizardPage

public class SaveAsStructWizard extends Wizard {
	private List<VarDeclaration> varDecl;
	private IProject project;
	private String datatypeName;

	private boolean replaceSource;

	private static final String STRUCT_SECTION = "STRUCT_SECTION"; //$NON-NLS-1$

	private SaveAsStructWizardPage newFilePage;

	public SaveAsStructWizard(List<VarDeclaration> varDecl, IProject project) {
		setWindowTitle("Save as Structured Type");
		this.varDecl = varDecl;
		this.project = project;
		setupDiagramSettings();
	}

	@Override
	public boolean performFinish() {
		boolean perform = true;
		newFilePage.saveWidgetValues();

		IFile targetFile = getTargetTypeFile();
		if (targetFile.exists()) {
			perform = askOverwrite();
		}

		if (perform) {
			StructuredType type = DataFactory.eINSTANCE.createStructuredType();
			InterfaceListCopier.copyVarList(type.getMemberVariables(), varDecl);

			TypeManagementPreferencesHelper.setupVersionInfo(type);

			datatypeName = TypeLibrary.getTypeNameFromFile(targetFile);
			type.setName(datatypeName);
			DataTypeExporter exporter = new DataTypeExporter(type);
			try {
				exporter.saveType(targetFile);
			} catch (XMLStreamException e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}

			if (newFilePage.getOpenType()) {
				OpenStructMenu.openStructEditor(targetFile);
			}

			if (newFilePage.getReplaceSource()) {
				replaceSource = true;
			}

		}
		return true;
	}


	public boolean replaceSource() {
		return replaceSource;
	}

	public String getDatatypeName() {
		return datatypeName;
	}

	private boolean askOverwrite() {
		return MessageDialog.openConfirm(getShell(), "Datatype already exists", "Overwrite existing datatype file?");
	}

	private IFile getTargetTypeFile() {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(newFilePage.getContainerFullPath()
				+ File.separator + newFilePage.getFileName() + TypeLibraryTags.DATA_TYPE_FILE_ENDING_WITH_DOT));
	}

	private void setupDiagramSettings() {
		IDialogSettings settings = Activator.getDefault().getDialogSettings();

		if (null == settings.getSection(STRUCT_SECTION)) {
			// section does not exist create a section
			settings.addNewSection(STRUCT_SECTION);
		}
		setDialogSettings(settings);
	}

	@Override
	public void addPages() {
		StructuredSelection selection = new StructuredSelection(project); // select the current project
		newFilePage = new SaveAsStructWizardPage("Save as Struct Type", selection);
		newFilePage.setFileName(varDecl.get(0).getName());
		addPage(newFilePage);
	}
}

