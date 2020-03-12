/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 * 				 2020 Andrea Zoitl
 *               2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *   Andrea Zoitl
 *     - externalized all translatable strings
 *   Ernst Blecha
 *     - improved error handling and handling of forceOverwrite
 *******************************************************************************/
package org.eclipse.fordiac.ide.export;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.export.DelayedFiles.StoredFiles;
import org.eclipse.fordiac.ide.export.utils.CompareEditorOpenerUtil;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.widgets.Display;

public abstract class TemplateExportFilter extends ExportFilter {

	public TemplateExportFilter() {
	}

	private List<String> reformat(LibraryElement type, List<String> messages) {
		return messages.stream().map(v -> (null != type) ? (type.getName() + ": " + v) : v) //$NON-NLS-1$
				.collect(Collectors.toList());
	}

	@Override
	public final void export(IFile typeFile, String destination, boolean forceOverwrite) throws ExportException {
		this.export(typeFile, destination, forceOverwrite, null);
	}

	@Override
	public void export(IFile typeFile, String destination, boolean forceOverwrite, LibraryElement type)
			throws ExportException {
		try {
			final Path destinationPath = Paths.get(destination);
			final Set<? extends IExportTemplate> templates = this.getTemplates(type);

			DelayedFiles files = new DelayedFiles();

			for (final IExportTemplate template : templates) {
				final CharSequence content = template.generate();
				getErrors().addAll(reformat(type, template.getErrors()));
				getWarnings().addAll(reformat(type, template.getWarnings()));
				getInfos().addAll(reformat(type, template.getInfos()));
				if (template.getErrors().isEmpty()) {
					final Path templatePath = destinationPath.resolve(template.getPath());
					files.write(templatePath, content);
				} else {
					files.clear();
					break;
				}
			}

			ICompareEditorOpener opener = CompareEditorOpenerUtil.getOpener();

			// Prepare the button labels
			final String MERGE_LABEL_STRING = "Merge";
			final String[] buttonLabels = new String[] { //
					JFaceResources.getString(IDialogLabelKeys.YES_LABEL_KEY), //
					MERGE_LABEL_STRING, //
					JFaceResources.getString(IDialogLabelKeys.CANCEL_LABEL_KEY)//
			};

			// extract the button ids from the label-array (avoid magic numbers)
			final int BUTTON_OVERWRITE = Arrays.asList(buttonLabels)
					.indexOf(JFaceResources.getString(IDialogLabelKeys.YES_LABEL_KEY));
			final int BUTTON_MERGE = Arrays.asList(buttonLabels).indexOf(MERGE_LABEL_STRING);

			// set a default value for the result of the MessageDialog that does not
			// conflict with the current state
			int res = BUTTON_OVERWRITE;

			// check if any of the files to be written are already existent
			boolean filesExisted = files.exist();

			if (!forceOverwrite && filesExisted) {
				// create a message dialog to ask about merging if forceOverwrite is not set
				String msg = "Overwrite " + type.getName() + ".cpp" + " and " + type.getName() + ".h. "
						+ ((opener != null)
								? "\nMerge will create a backup of the original File and open an editor to merge the files manually!"
								: ""); //$NON-NLS-1$

				MessageDialog msgDiag = new MessageDialog(Display.getDefault().getActiveShell(), "File Exists", null,
						msg, MessageDialog.QUESTION_WITH_CANCEL, buttonLabels, 0);

				res = msgDiag.open();
			}

			// from here on forceOverwrite and the BUTTON_OVERWRITE can be handled the same
			// way
			boolean overwrite = forceOverwrite || (BUTTON_OVERWRITE == res);

			if (overwrite || (BUTTON_MERGE == res)) {
				// write the files that were prepared
				Iterable<StoredFiles> writtenFiles = files.write(overwrite);

				// check differences of the files using the compare editor
				boolean diffs = false;
				if (!overwrite && (null != opener)) {
					for (StoredFiles sf : writtenFiles) {
						if ((null != sf.getNewFile()) && (null != sf.getOldFile())) {
							opener.setName(sf.getNewFile().getName());
							opener.setTitle(sf.getNewFile().getName());
							opener.setNewFile(sf.getNewFile());
							opener.setOriginalFile(sf.getOldFile());
							if (opener.hasDifferences()) {
								opener.openCompareEditor();
								diffs = true;
							}
						}
					}
				}

				if (!diffs && filesExisted && !forceOverwrite) {
					// there were no differences - inform the user
					MessageDialog.openInformation(Display.getDefault().getActiveShell(), "No Differences",
							"There where no differences between the orignal file and the newly generated one!");
				}
			}
		} catch (final Exception t) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 1,
					Messages.TemplateExportFilter_ErrorDuringTemplateGeneration, t));
			this.getErrors().add(t.getMessage() != null ? t.getMessage()
					: Messages.TemplateExportFilter_ErrorDuringTemplateGeneration);
		}
	}

	protected abstract Set<? extends IExportTemplate> getTemplates(LibraryElement type);

}
