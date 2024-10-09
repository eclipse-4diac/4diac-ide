/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 * 				 2020 Andrea Zoitl
 *               2020 Johannes Kepler University Linz
 *               2022 Martin Erich Jobst
 *               2024 Primetals Technologies Austria GmbH
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
 *     - refactor for arbitrary types in export
 *   Andrea Zoitl
 *     - externalized all translatable strings
 *   Ernst Blecha
 *     - improved error handling and handling of forceOverwrite
 *     - Add "Overwrite All" and "Cancel All"
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
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.fordiac.ide.export.utils.CompareEditorOpenerUtil;
import org.eclipse.fordiac.ide.export.utils.DelayedFiles;
import org.eclipse.fordiac.ide.export.utils.DelayedFiles.StoredFiles;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.widgets.Display;

public abstract class TemplateExportFilter extends ExportFilter {

	// Prepare the button labels
	private static final String[] BUTTON_LABELS = new String[] { //
			Messages.TemplateExportFilter_OVERWRITE_LABEL_STRING, //
			Messages.TemplateExportFilter_OVERWRITE_ALL_LABEL_STRING, //
			Messages.TemplateExportFilter_MERGE_LABEL_STRING, //
			Messages.TemplateExportFilter_CANCEL_ALL_LABEL_STRING, //
			JFaceResources.getString(IDialogLabelKeys.CANCEL_LABEL_KEY)//
	};

	// extract the button ids from the label-array (avoid magic numbers)
	private static final int BUTTON_OVERWRITE = Arrays.asList(BUTTON_LABELS)
			.indexOf(Messages.TemplateExportFilter_OVERWRITE_LABEL_STRING);
	private static final int BUTTON_OVERWRITE_ALL = Arrays.asList(BUTTON_LABELS)
			.indexOf(Messages.TemplateExportFilter_OVERWRITE_ALL_LABEL_STRING);
	private static final int BUTTON_MERGE = Arrays.asList(BUTTON_LABELS)
			.indexOf(Messages.TemplateExportFilter_MERGE_LABEL_STRING);
	private static final int BUTTON_CANCEL_ALL = Arrays.asList(BUTTON_LABELS)
			.indexOf(Messages.TemplateExportFilter_CANCEL_ALL_LABEL_STRING);

	protected TemplateExportFilter() {
	}

	private static List<String> reformat(final String name, final List<String> messages) {
		return name != null
				? messages.stream()
						.map(v -> MessageFormat.format(Messages.TemplateExportFilter_PREFIX_ERRORMESSAGE_WITH_TYPENAME,
								name, v))
						.toList()
				: messages;
	}

	@Override
	public final void export(final IFile typeFile, final String destination, final boolean forceOverwrite)
			throws ExportException.UserInteraction {
		this.export(typeFile, destination, forceOverwrite, null);
	}

	@SuppressWarnings("squid:S109")
	private static String stringsToTextualList(final List<String> list) {
		String textualList = ""; //$NON-NLS-1$
		if (list.size() == 1) {
			textualList = MessageFormat.format(Messages.TemplateExportFilter_LIST_ONE_ELEMENT, list.get(0));
		} else if (list.size() == 2) {
			textualList = MessageFormat.format(Messages.TemplateExportFilter_LIST_TWO_ELEMENTS, list.get(0),
					list.get(1));
		} else if (list.size() == 3) {
			textualList = MessageFormat.format(Messages.TemplateExportFilter_LIST_THREE_ELEMENTS, list.get(0),
					list.get(1), list.get(2));
		} else if (list.size() > 3) {
			textualList = MessageFormat.format(Messages.TemplateExportFilter_LIST_FOUR_OR_MORE_ELEMENTS, list.get(0),
					list.get(1), list.get(2));
		}
		return textualList;
	}

	@Override
	public void export(final IFile typeFile, final String destination, final boolean forceOverwrite, EObject source)
			throws ExportException.UserInteraction {
		if (source == null && typeFile != null && TypeLibraryManager.INSTANCE.getTypeEntryForFile(typeFile) == null) {
			getWarnings().add(MessageFormat.format(Messages.TemplateExportFilter_PREFIX_ERRORMESSAGE_WITH_TYPENAME,
					typeFile.getFullPath(), Messages.TemplateExportFilter_FILE_IGNORED));
			return; // Do not export files passed to the export that are not in the TypeLibrary
		}
		try {
			if (source == null && typeFile != null) {
				final ResourceSet resourceSet = new ResourceSetImpl();
				final Resource resource = resourceSet
						.getResource(URI.createPlatformResourceURI(typeFile.getFullPath().toString(), true), true);
				source = resource.getContents().get(0);
			}

			String name = "anonymous"; //$NON-NLS-1$
			if (source instanceof final INamedElement ne) {
				name = ne.getName();
			} else if (typeFile != null) {
				name = typeFile.getFullPath().removeFileExtension().lastSegment();
			}

			final DelayedFiles files = generateFileContent(destination, name, source);

			// set a default value for the result of the MessageDialog that does not
			// conflict with the current state
			int res = BUTTON_OVERWRITE;

			// check if any of the files to be written are already existent
			final boolean filesExisted = files.exist();

			if (!forceOverwrite && filesExisted) {
				// create a message dialog to ask about merging if forceOverwrite is not set
				final String msg = MessageFormat.format(Messages.TemplateExportFilter_OVERWRITE_REQUEST,
						stringsToTextualList(files.getFilenames()));

				res = Display.getDefault().syncCall(() -> {
					final MessageDialog msgDiag = new MessageDialog(Display.getDefault().getActiveShell(),
							Messages.TemplateExportFilter_FILE_EXISTS, null, msg, MessageDialog.QUESTION_WITH_CANCEL,
							BUTTON_LABELS, 0);
					return Integer.valueOf(msgDiag.open());
				}).intValue();

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
			} else if (res == BUTTON_OVERWRITE_ALL) {
				throw (new ExportException.OverwriteAll());
			} else if (res == BUTTON_CANCEL_ALL) {
				throw (new ExportException.CancelAll());
			} else { // the cancel button was selected / ESC pressed
				getWarnings().add(MessageFormat.format(Messages.TemplateExportFilter_PREFIX_ERRORMESSAGE_WITH_TYPENAME,
						typeFile != null ? typeFile.getFullPath() : name,
						Messages.TemplateExportFilter_EXPORT_CANCELED));
			}

		} catch (final ExportException.UserInteraction e) {
			throw (e);
		} catch (final Exception t) {
			FordiacLogHelper.logError(Messages.TemplateExportFilter_ErrorDuringTemplateGeneration, t);
			this.getErrors().add(t.getMessage() != null ? t.getMessage()
					: Messages.TemplateExportFilter_ErrorDuringTemplateGeneration);
		}
	}

	private DelayedFiles generateFileContent(final String destination, final String name, final EObject source)
			throws ExportException {
		final DelayedFiles files = new DelayedFiles();

		final Path destinationPath = Paths.get(destination);
		final Set<? extends IExportTemplate> templates = this.getTemplates(name, source);
		for (final IExportTemplate template : templates) {
			final CharSequence content = template.generate();
			getErrors().addAll(reformat(name, template.getErrors()));
			getWarnings().addAll(reformat(name, template.getWarnings()));
			getInfos().addAll(reformat(name, template.getInfos()));
			if (content == null || !template.getErrors().isEmpty()) {
				files.clear();
				break;
			}
			final String processed = content.toString().lines().map(String::stripTrailing)
					.collect(Collectors.joining(System.lineSeparator()));
			final Path templatePath = destinationPath.resolve(template.getPath());
			files.write(templatePath, processed);
		}
		return files;
	}

	private static void openMergeEditor(final Iterable<StoredFiles> writtenFiles) throws ExportException {
		for (final StoredFiles sf : writtenFiles) {
			if ((null != sf.newFile()) && (null != sf.oldFile())) {
				final ICompareEditorOpener opener = CompareEditorOpenerUtil.getOpener();
				if (null == opener) {
					throw new ExportException(Messages.TemplateExportFilter_MERGE_EDITOR_FAILED);
				}
				opener.setName(sf.newFile().getName());
				opener.setTitle(sf.newFile().getName());
				opener.setNewFile(sf.newFile());
				opener.setOriginalFile(sf.oldFile());
				if (opener.hasDifferences()) {
					Display.getDefault().asyncExec(opener::openCompareEditor);
				}
			}
		}
	}

	protected abstract Set<IExportTemplate> getTemplates(String name, EObject source);

}
