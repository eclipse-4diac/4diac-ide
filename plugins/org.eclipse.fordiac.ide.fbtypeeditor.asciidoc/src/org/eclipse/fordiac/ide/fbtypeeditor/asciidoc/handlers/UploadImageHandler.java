/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Andrea Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.Messages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

public class UploadImageHandler extends AbstractHandler {

	private static String lastSourceDirectory = null;
	private static final String[] fileExtensions = { "*.png", "*.jpg", "*.jpeg", "*.gif", "*.svg" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
		if (activeEditor == null) {
			return null;
		}

		final ITextEditor textEditor = Adapters.adapt(activeEditor, ITextEditor.class);
		if (textEditor == null) {
			return null;
		}

		final IContainer assetsFolder = resolveAssetsFolder(textEditor);
		if (assetsFolder == null || !assetsFolder.exists()) {
			throw new ExecutionException("Assets folder does not exist."); //$NON-NLS-1$
		}

		final Shell shell = HandlerUtil.getActiveShell(event);
		final File localFile = selectLocalImage(shell, assetsFolder);
		if (localFile == null) {
			return null; // User canceled the file selection dialog
		}

		// Store path context for subsequent usage
		setLastSourceDirectory(localFile.getParent());

		final boolean uploadSuccessful = uploadImage(shell, assetsFolder, localFile);
		if (uploadSuccessful) {
			final String safeFileName = getSafeFileName(localFile.getName());
			insertImageMarkup(textEditor, safeFileName);
		}

		return null;
	}

	/**
	 * Resolves the corresponding workspace assets folder based on the currently
	 * open editor file.
	 */
	private static IContainer resolveAssetsFolder(final ITextEditor textEditor) {
		final IEditorInput editorInput = textEditor.getEditorInput();
		if (editorInput instanceof final IFileEditorInput fileEditorInput) {
			final IFile docFile = fileEditorInput.getFile();
			return docFile.getParent(); // The folder container enclosing the current "type.adoc"
		}
		return null;
	}

	/**
	 * Prompts the user with an SWT file selector dialog, respecting previous path
	 * history.
	 */
	private static File selectLocalImage(final Shell shell, final IContainer assetsFolder) {
		final FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
		fileDialog.setText(Messages.Toolbar_UploadSelectImage);

		if (lastSourceDirectory != null) {
			fileDialog.setFilterPath(lastSourceDirectory);
		} else if (assetsFolder.getLocation() != null) {
			fileDialog.setFilterPath(assetsFolder.getLocation().toOSString()); // Fallback default path
		}

		fileDialog.setFilterExtensions(fileExtensions);

		final String selectedPath = fileDialog.open();
		return (selectedPath != null) ? new File(selectedPath) : null;
	}

	/**
	 * Handles copying/saving the local file system resource into the hidden assets
	 * folder of the Type Library.
	 */
	private static boolean uploadImage(final Shell shell, final IContainer assetsFolder, final File localFile)
			throws ExecutionException {
		if (!(assetsFolder instanceof final org.eclipse.core.resources.IFolder folder)) {
			return false;
		}

		final String safeFileName = getSafeFileName(localFile.getName());
		final IFile targetFile = folder.getFile(safeFileName);

		// Refresh target resource before existence and overwrite checks
		try {
			targetFile.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
		} catch (final org.eclipse.core.runtime.CoreException e) {
			throw new ExecutionException(e.getLocalizedMessage(), e);
		}

		if (targetFile.exists()) {
			// Safety Check: Avoid self-overwriting within assets folder to prevent file
			// truncation/corruption
			if (isSameFile(localFile, targetFile)) {
				return true; // Bypass copy operation but proceed with inserting the markup
			}

			final int result = promptForExistingFile(shell, safeFileName);

			if (result == 0) { // Overwrite
				try (InputStream inputStream = new FileInputStream(localFile)) {
					targetFile.setContents(inputStream, IResource.FORCE, new NullProgressMonitor());
					assetsFolder.refreshLocal(IResource.DEPTH_ONE, new NullProgressMonitor());
					return true;
				} catch (final Exception e) {
					throw new ExecutionException("Failed to upload image: " + e.getMessage(), e); //$NON-NLS-1$
				}
			}
			if (result == 1) { // Use Existing Image
				return true; // Bypass copying stream but return true to trigger markup insertion
			}
			return false; // Abort operation gracefully
		}

		// File does not exist yet; upload it directly
		try (InputStream inputStream = new FileInputStream(localFile)) {
			targetFile.create(inputStream, true, new NullProgressMonitor());
			assetsFolder.refreshLocal(IResource.DEPTH_ONE, new NullProgressMonitor());
			return true;
		} catch (final Exception e) {
			throw new ExecutionException("Failed to upload image: " + e.getMessage(), e); //$NON-NLS-1$
		}
	}

	/**
	 * Sanitizes a filename to ensure it is safe for AsciiDoc image targets.
	 * Replaces spaces, brackets, parentheses, and other special characters with
	 * underscores.
	 */
	private static String getSafeFileName(final String originalName) {
		if (originalName == null) {
			return ""; //$NON-NLS-1$
		}
		// Replace any character that is NOT a-z, A-Z, 0-9, dot (.), hyphen (-), or
		// underscore (_)
		final String safeName = originalName.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_"); //$NON-NLS-1$ //$NON-NLS-2$

		// Collapse consecutive underscores into a single underscore for cleaner names
		return safeName.replaceAll("_+", "_"); //$NON-NLS-1$//$NON-NLS-2$
	}

	/**
	 * Checks if the selected local file points to the exact same location on disk
	 * as the target workspace file.
	 */
	private static boolean isSameFile(final File localFile, final IFile targetFile) {
		if (targetFile.getLocation() == null) {
			return false;
		}
		try {
			final File targetLocalFile = targetFile.getLocation().toFile();
			return java.nio.file.Files.isSameFile(localFile.toPath(), targetLocalFile.toPath());
		} catch (final Exception e) {
			// Fallback case-insensitive path comparison (ideal on Windows/macOS)
			return localFile.getAbsolutePath().equalsIgnoreCase(targetFile.getLocation().toOSString());
		}
	}

	/**
	 * Prompts the user when a file already exists in the assets folder, offering to
	 * overwrite, use the existing image, or cancel [^1].
	 *
	 * @return the selected index: 0 = Overwrite, 1 = Use Existing, 2 (or -1) =
	 *         Cancel
	 */
	private static int promptForExistingFile(final Shell shell, final String fileName) {
		final String formattedMessage = NLS.bind(Messages.Toolbar_ErrorMessage, fileName);

		final MessageDialog dialog = new MessageDialog(shell, Messages.Toolbar_FileAlreadyExists, null,
				formattedMessage, MessageDialog.QUESTION,
				new String[] { Messages.Toolbar_ButtonOverwrite, Messages.Toolbar_UseExistingImage,
						Messages.Toolbar_ButtonCancel },
				// Default selection highlights "Cancel" (index 2) for safety
				2);
		return dialog.open();
	}

	/**
	 * Inserts the formatted AsciiDoc image block at the active cursor selection,
	 * dynamically prepending/appending line delimiters if necessary to keep the
	 * block stand-alone.
	 */
	private static void insertImageMarkup(final ITextEditor textEditor, final String fileName)
			throws ExecutionException {
		final IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
		final ISelection selection = textEditor.getSelectionProvider().getSelection();

		if (document != null && selection instanceof final ITextSelection textSelection) {
			final int offset = textSelection.getOffset();
			final int length = textSelection.getLength();

			try {
				// Retrieve the native line delimiter used by this document (e.g., \r\n or \n)
				final String lineDelim = org.eclipse.jface.text.TextUtilities.getDefaultLineDelimiter(document);
				final StringBuilder markupBuilder = new StringBuilder();

				// Prepend delimiter if preceding text is on the same line
				if (checkIfPrecedingNewLineNeeded(document, offset)) {
					markupBuilder.append(lineDelim);
				}

				markupBuilder.append("image::").append(fileName).append("[]"); //$NON-NLS-1$ //$NON-NLS-2$

				// Append delimiter if succeeding text is on the same line
				if (checkIfSucceedingNewLineNeeded(document, offset, length)) {
					markupBuilder.append(lineDelim);
				}

				document.replace(offset, length, markupBuilder.toString());
			} catch (final Exception e) {
				throw new ExecutionException("Failed to insert image markup", e); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Checks if a newline delimiter is needed before the block macro insertion.
	 */
	private static boolean checkIfPrecedingNewLineNeeded(final IDocument document, final int offset)
			throws BadLocationException {
		if (offset <= 0) {
			return false; // Already at the very start of the document
		}
		final char prevChar = document.getChar(offset - 1);
		return prevChar != '\n' && prevChar != '\r';
	}

	/**
	 * Checks if a newline delimiter is needed after the block macro insertion.
	 */
	private static boolean checkIfSucceedingNewLineNeeded(final IDocument document, final int offset, final int length)
			throws BadLocationException {
		if (offset + length >= document.getLength()) {
			return false; // Already at the very end of the document
		}
		final char nextChar = document.getChar(offset + length);
		return nextChar != '\n' && nextChar != '\r';
	}

	private static synchronized void setLastSourceDirectory(final String directory) {
		lastSourceDirectory = directory;
	}
}
