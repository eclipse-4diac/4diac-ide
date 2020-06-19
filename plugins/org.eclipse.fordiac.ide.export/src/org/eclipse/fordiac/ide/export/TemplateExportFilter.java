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
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.export.utils.CompareEditorOpenerUtil;
import org.eclipse.fordiac.ide.export.utils.DelayedFiles;
import org.eclipse.fordiac.ide.export.utils.DelayedFiles.StoredFiles;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.widgets.Display;

public abstract class TemplateExportFilter extends ExportFilter {

	// Prepare the button labels
	private static final String MERGE_LABEL_STRING = "Merge";
	private static final String OVERWRITE_LABEL_STRING = "Overwrite";
	private static final String[] BUTTON_LABELS = new String[] { //
			OVERWRITE_LABEL_STRING, //
			MERGE_LABEL_STRING, //
			JFaceResources.getString(IDialogLabelKeys.CANCEL_LABEL_KEY)//
	};

	// extract the button ids from the label-array (avoid magic numbers)
	private static final int BUTTON_OVERWRITE = Arrays.asList(BUTTON_LABELS).indexOf(OVERWRITE_LABEL_STRING);
	private static final int BUTTON_MERGE = Arrays.asList(BUTTON_LABELS).indexOf(MERGE_LABEL_STRING);

	public TemplateExportFilter() {
	}

	private static final String PREFIX_ERRORMESSAGE_WITH_TYPENAME = "{0}: {1}";

	private static List<String> reformat(LibraryElement type, List<String> messages) {
		return messages.stream().map(
				v -> (null != type) ? (MessageFormat.format(PREFIX_ERRORMESSAGE_WITH_TYPENAME, type.getName(), v)) : v)
				.collect(Collectors.toList());
	}

	@Override
	public final void export(IFile typeFile, String destination, boolean forceOverwrite) throws ExportException {
		this.export(typeFile, destination, forceOverwrite, null);
	}

	private String stringsToTextualList(List<String> list) {
		String textualList = "";
		if (list.size() == 1) {
			textualList = MessageFormat.format("{0}", list.get(0));
		} else if (list.size() == 2) {
			textualList = MessageFormat.format("{0} and {1}", list.get(0), list.get(1));
		} else if (list.size() == 3) {
			textualList = MessageFormat.format("{0}, {1} and {2}", list.get(0), list.get(1), list.get(2));
		} else if (list.size() > 3) {
			textualList = MessageFormat.format("{0}, {1}, {2}, ...", list.get(0), list.get(1), list.get(2));
		}
		return textualList;
	}

	@Override
	public void export(IFile typeFile, String destination, boolean forceOverwrite, LibraryElement type)
			throws ExportException {
		try {

			DelayedFiles files = generateFileContent(destination, type);

			// set a default value for the result of the MessageDialog that does not
			// conflict with the current state
			int res = BUTTON_OVERWRITE;

			// check if any of the files to be written are already existent
			boolean filesExisted = files.exist();

			if (!forceOverwrite && filesExisted) {
				// create a message dialog to ask about merging if forceOverwrite is not set
				String msg = MessageFormat.format(
						"Overwrite {0}?\nMerge will create a backup of the original file and open an editor to merge the files manually!",
						stringsToTextualList(files.getFilenames()));
				MessageDialog msgDiag = new MessageDialog(Display.getDefault().getActiveShell(), "File Exists", null,
						msg, MessageDialog.QUESTION_WITH_CANCEL, BUTTON_LABELS, 0);

				res = msgDiag.open();
			}

			// from here on forceOverwrite and the BUTTON_OVERWRITE can be handled the same
			// way
			boolean overwrite = forceOverwrite || (BUTTON_OVERWRITE == res);

			if (overwrite || (BUTTON_MERGE == res)) {
				// write the files that were prepared
				Iterable<StoredFiles> writtenFiles = files.write(overwrite);

				// check differences of the files using the compare editor
				if (!overwrite) {
					openMergeEditor(writtenFiles);
				}
			}
		} catch (final Exception t) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 1,
					Messages.TemplateExportFilter_ErrorDuringTemplateGeneration, t));
			this.getErrors().add(t.getMessage() != null ? t.getMessage()
					: Messages.TemplateExportFilter_ErrorDuringTemplateGeneration);
		}
	}

	private DelayedFiles generateFileContent(String destination, LibraryElement type) throws ExportException {
		DelayedFiles files = new DelayedFiles();

		final Path destinationPath = Paths.get(destination);
		final Set<? extends IExportTemplate> templates = this.getTemplates(type);
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
		return files;
	}

	private static void openMergeEditor(Iterable<StoredFiles> writtenFiles) throws ExportException {
		boolean diffs = false;
		ICompareEditorOpener opener = CompareEditorOpenerUtil.getOpener();
		if (null == opener) {
			throw new ExportException("Unable to create merge editor. Files have been written to disk.");
		}
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

		if (!diffs) {
			// there were no differences - inform the user
			MessageDialog.openInformation(Display.getDefault().getActiveShell(), "No Differences",
					"There where no differences between the orignal file and the newly generated one!");
		}
	}

	protected abstract Set<? extends IExportTemplate> getTemplates(LibraryElement type);

}
