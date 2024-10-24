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
	private static final String[] BUTTON_LABELS = new String[] { //
			Messages.TemplateExportFilter_OVERWRITE_LABEL_STRING, //
			Messages.TemplateExportFilter_MERGE_LABEL_STRING, //
			JFaceResources.getString(IDialogLabelKeys.CANCEL_LABEL_KEY)//
	};

	// extract the button ids from the label-array (avoid magic numbers)
	private static final int BUTTON_OVERWRITE = Arrays.asList(BUTTON_LABELS)
			.indexOf(Messages.TemplateExportFilter_OVERWRITE_LABEL_STRING);
	private static final int BUTTON_MERGE = Arrays.asList(BUTTON_LABELS)
			.indexOf(Messages.TemplateExportFilter_MERGE_LABEL_STRING);

	protected TemplateExportFilter() {
	}

	private static List<String> reformat(final LibraryElement type, final List<String> messages) {
		return messages.stream().map(
				v -> (null != type)
				? (MessageFormat.format(Messages.TemplateExportFilter_PREFIX_ERRORMESSAGE_WITH_TYPENAME,
						type.getName(), v))
						: v)
				.collect(Collectors.toList());
	}

	@Override
	public final void export(final IFile typeFile, final String destination, final boolean forceOverwrite) throws ExportException {
		this.export(typeFile, destination, forceOverwrite, null);
	}

	@SuppressWarnings("squid:S109")
	private static String stringsToTextualList(final List<String> list) {
		String textualList = ""; //$NON-NLS-1$
		if (list.size() == 1) {
			textualList = MessageFormat.format(Messages.TemplateExportFilter_LIST_ONE_ELEMENT, list.get(0));
		} else if (list.size() == 2) {
			textualList = MessageFormat.format(Messages.TemplateExportFilter_LIST_TWO_ELEMENTS, list.get(0), list.get(1));
		} else if (list.size() == 3) {
			textualList = MessageFormat.format(Messages.TemplateExportFilter_LIST_THREE_ELEMENTS, list.get(0), list.get(1), list.get(2));
		} else if (list.size() > 3) {
			textualList = MessageFormat.format(Messages.TemplateExportFilter_LIST_FOUR_OR_MORE_ELEMENTS, list.get(0), list.get(1), list.get(2));
		}
		return textualList;
	}

	@Override
	public void export(final IFile typeFile, final String destination, final boolean forceOverwrite, final LibraryElement type)
			throws ExportException {
		try {

			final DelayedFiles files = generateFileContent(destination, type);

			// set a default value for the result of the MessageDialog that does not
			// conflict with the current state
			int res = BUTTON_OVERWRITE;

			// check if any of the files to be written are already existent
			final boolean filesExisted = files.exist();

			if (!forceOverwrite && filesExisted) {
				// create a message dialog to ask about merging if forceOverwrite is not set
				final String msg = MessageFormat.format(
						Messages.TemplateExportFilter_OVERWRITE_REQUEST,
						stringsToTextualList(files.getFilenames()));
				final MessageDialog msgDiag = new MessageDialog(Display.getDefault().getActiveShell(), Messages.TemplateExportFilter_FILE_EXISTS, null,
						msg, MessageDialog.QUESTION_WITH_CANCEL, BUTTON_LABELS, 0);

				res = msgDiag.open();
			}

			// from here on forceOverwrite and the BUTTON_OVERWRITE can be handled the same
			// way
			final boolean overwrite = forceOverwrite || (BUTTON_OVERWRITE == res);

			if (overwrite || (BUTTON_MERGE == res)) {
				// write the files that were prepared
				final Iterable<StoredFiles> writtenFiles = files.write(overwrite);

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

	private DelayedFiles generateFileContent(final String destination, final LibraryElement type) throws ExportException {
		final DelayedFiles files = new DelayedFiles();

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

	private static void openMergeEditor(final Iterable<StoredFiles> writtenFiles) throws ExportException {
		final ICompareEditorOpener opener = CompareEditorOpenerUtil.getOpener();
		if (null == opener) {
			throw new ExportException(Messages.TemplateExportFilter_MERGE_EDITOR_FAILED);
		}
		boolean diffs = false;
		for (final StoredFiles sf : writtenFiles) {
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
			MessageDialog.openInformation(Display.getDefault().getActiveShell(), Messages.TemplateExportFilter_NO_DIFFERENCES_TITLE,
					Messages.TemplateExportFilter_NO_DIFFERENCES_MESSAGE);
		}
	}

	protected abstract Set<IExportTemplate> getTemplates(LibraryElement type);

}
