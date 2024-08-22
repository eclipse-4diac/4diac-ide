/*******************************************************************************
 * Copyright (c) 2020, 2023, 2024 Johannes Kepler University Linz
 *                                Martin Erich Jobst
 *                                Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *   Martin Jobst
 *     - create parent directories on demand
 *   Ernst Blecha
 *     - do not overwrite files that did not change
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.fordiac.ide.util.Utils;

/**
 * Class DelayedFiles
 *
 * Handle multiple files that should only be written if all files were prepared
 * without error. In case the files already existed keep the existing version in
 * a backup file.
 */
public class DelayedFiles {

	/**
	 * internal class FileObject
	 *
	 * encapsulates path and data of a file in memory (before writing)
	 */
	private static record FileObject(Path path, CharSequence data) {
		public FileObject {
			Objects.requireNonNull(path);
			Objects.requireNonNull(data);
		}
	}

	/**
	 * class StoredFiles
	 *
	 * encapsulates file objects for old and new file an iterable of StoredFiles is
	 * returned after all files were written
	 */

	public static final class StoredFiles {
		private final File oldFile;
		private final File newFile;

		public StoredFiles(final File oldFile, final File newFile) {
			this.oldFile = oldFile;
			this.newFile = newFile;
		}

		public File getOldFile() {
			return oldFile;
		}

		public File getNewFile() {
			return newFile;
		}
	}

	private final List<FileObject> storage;

	/**
	 * constructor for class DelayedFiles
	 *
	 * prepares the storage and sets capacity to 2 elements since in the typical
	 * usecase a .cpp and a .h file will be written
	 */
	public DelayedFiles() {
		storage = new ArrayList<>(2);
	}

	/**
	 * store file data in memory for writing to disk later
	 *
	 * @param path  file path to be written to
	 * @param bytes data to be written as a CharSequence
	 *
	 * @return path to be written to to be compatible with java.nio.file.Files
	 */
	public Path write(final Path path, final CharSequence data) {
		if (hasChangedContent(path, data)) {
			storage.add(new FileObject(path, data));
		}
		return path;
	}

	/**
	 * store file data in memory for writing to disk later
	 *
	 * if any of the files to write is already present on disk a backup file of the
	 * existing file will be created.
	 *
	 * @param forceOverwrite if set no backupfile will be created
	 *
	 * @return Iterable of StoredFiles; contains File-Objects for old and new
	 *         versions of the file. in case no backupfile is created
	 *         old-File-Object will be null
	 */
	public Iterable<StoredFiles> write(final boolean forceOverwrite) throws IOException {
		final ArrayList<StoredFiles> ret = new ArrayList<>(storage.size());

		for (final FileObject fo : storage) {
			File o = null;
			final File f = fo.path().toFile();
			if (!forceOverwrite && f.exists()) {
				o = Utils.createBakFile(f);
			}
			if (fo.path().getParent() != null) {
				Files.createDirectories(fo.path().getParent());
			}
			Files.write(fo.path(), Collections.singleton(fo.data()), StandardCharsets.UTF_8);
			ret.add(new StoredFiles(o, f));
		}

		clear();
		return ret;
	}

	/**
	 * check if any of the files to write is already present on disk
	 *
	 * @return true if any of the files already exist, false otherwise
	 */
	public boolean exist() {
		return storage.stream().anyMatch((final FileObject fo) -> fo.path().toFile().exists());
	}

	/** remove all of the data currently prepared in memory */
	public void clear() {
		storage.clear();
	}

	/** retrieve an iterable of all filenames currently ready for writing */
	public List<String> getFilenames() {
		return storage.stream().map(item -> item.path().getFileName().toString()).toList();
	}

	/**
	 * compare if the generated data is already written to disk
	 *
	 * @param path  file path to be written to
	 * @param bytes data to be written as a CharSequence
	 *
	 * @return data on disk does not match the generated data
	 */
	private static boolean hasChangedContent(final Path p, final CharSequence data) {
		try {
			return !Files.readString(p).equals(data);
		} catch (final IOException e) {
			return true;
		}
	}

}
