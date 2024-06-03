package org.eclipse.fordiac.ide.typemanagement.repair;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typemanagement.util.TypeCreator;
import org.eclipse.fordiac.ide.typemanagement.util.TypeFromTemplateCreator;
import org.eclipse.ui.IMarkerResolution;

public class MissingTypeQuickFix implements IMarkerResolution {
	String label;

	MissingTypeQuickFix(final String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void run(final IMarker marker) {

		try {
			final EObject target = FordiacErrorMarker.getTarget(marker);

			final File template = new File(TypeCreator.TEMPLATE_PATH + File.separatorChar + "Struct.dtp");

			if (target instanceof VarDeclaration) {

				final EObject rootContainer = EcoreUtil.getRootContainer(target);

				IPath fullPath = null;

				if (rootContainer instanceof final AutomationSystem system) {
					fullPath = system.getTypeEntry().getTypeLibrary().getProject().getFullPath();
				}
				if (rootContainer instanceof final FBType fbType) {
					fullPath = fbType.getTypeEntry().getTypeLibrary().getProject().getFullPath();
				}

				final DataType type = ((VarDeclaration) target).getType();

				if (type instanceof final ErrorMarkerDataType errorType) {
					final TypeEntry typeEntry = errorType.getTypeEntry();

					final IFile file = typeEntry.getFile();
					final int k = 0;

					// final IFile targetTypeFile = getTargetFile();

					// final TypeFromTemplateCreator creator = new
					// TypeFromTemplateCreator(getTargetFile(), template,
					// packageName);

					final IFile targetFile = getTargetFile(type.getName(), fullPath);
					final TypeFromTemplateCreator creator = new TypeFromTemplateCreator(targetFile, template, "");
					creator.createTypeFromTemplate(new NullProgressMonitor());
				}

			}

		} catch (IllegalArgumentException | CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// MessageDialog.openInformation(null, "MissingTypeQuickFix Demo", "This
		// quick-fix
		// is not yet implemented");

		// Display.getDefault().asyncExec(() -> {
		// final CustomWizard wizard = new CustomWizard(marker);
		// final WizardDialog dialog = new WizardDialog(
		// PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
		// dialog.open();
		// dialog.open();
//		});

	}

	private static IFile getTargetFile(final String typeName, final IPath path) {
		return ResourcesPlugin.getWorkspace().getRoot()
				.getFile(new Path(path + File.separator + "Type Library" + File.separator + typeName + ".dtp"));

	}

}