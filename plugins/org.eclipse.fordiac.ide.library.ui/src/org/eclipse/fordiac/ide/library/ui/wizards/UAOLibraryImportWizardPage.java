package org.eclipse.fordiac.ide.library.ui.wizards;

import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.library.uao.UaoEmfManifestLoader;
import org.eclipse.fordiac.ide.library.uao.UaoLibraryImporter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.tempuri.library.mgmt.ManifestType;

public class UAOLibraryImportWizardPage extends WizardPage {

	private final IProject selectedProject;

	private Text folderText;
	private Label info;
	private Path selectedFolder;

	protected UAOLibraryImportWizardPage(final String pageName, final IProject selectedProject) {
		super(pageName);
		this.selectedProject = selectedProject;
		setTitle("UAO LIUB");
		setDescription(
				"Select a UAO module folder (must contain Manifest.mf). It will be loaded as UAO EMF model and converted to a 4diac library."); //$NON-NLS-1$
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(3, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		new Label(container, SWT.NONE).setText("UAO module folder:"); //$NON-NLS-1$

		folderText = new Text(container, SWT.BORDER);
		folderText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Button browse = new Button(container, SWT.PUSH);
		browse.setText("Browse..."); //$NON-NLS-1$

		info = new Label(container, SWT.WRAP);
		final GridData infoGd = new GridData(SWT.FILL, SWT.TOP, true, false);
		infoGd.horizontalSpan = 3;
		info.setLayoutData(infoGd);
		info.setText("No folder selected."); //$NON-NLS-1$

		final ModifyListener ml = (final ModifyEvent e) -> validate();
		folderText.addModifyListener(ml);

		browse.addListener(SWT.Selection, ev -> {
			final DirectoryDialog dd = new DirectoryDialog(getShell());
			dd.setText("Select UAO Module Folder"); //$NON-NLS-1$
			final String chosen = dd.open();
			if (chosen != null && !chosen.isBlank()) {
				folderText.setText(chosen);
			}
		});

		setControl(container);
		setPageComplete(false);
	}

	public boolean convertAndImport() {
		if (!isComplete()) {
			return false;
		}

		final java.net.URI uri = UaoLibraryImporter.convertAndInstall(selectedFolder, selectedProject, true, true);

		return true;
	}

	private void validate() {
		final String text = folderText.getText();
		selectedFolder = (text == null || text.isBlank()) ? null : Path.of(text);
		setPageComplete(isComplete());
	}

	private boolean isComplete() {
		if (selectedFolder == null || !Files.isDirectory(selectedFolder)) {
			setErrorMessage("Please select an existing folder."); //$NON-NLS-1$
			info.setText("No folder selected."); //$NON-NLS-1$
			return false;
		}

		final ManifestType mt = UaoEmfManifestLoader.loadManifestType(selectedFolder).orElse(null);
		if (mt == null || mt.getLibrary() == null) {
			setErrorMessage("Manifest.mf could not be loaded as UAO EMF model."); //$NON-NLS-1$
			info.setText("Expected: folder contains Manifest.mf matching your UAO Ecore model."); //$NON-NLS-1$
			return false;
		}

		setErrorMessage(null);
		info.setText("Loaded UAO manifest: " + mt.getLibrary().getName() + " (v" + mt.getLibrary().getVersion() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return true;
	}
}
