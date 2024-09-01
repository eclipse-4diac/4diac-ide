/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.tools.ant.BuildException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.library.model.library.Library;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class ExportProjectLibrary extends AbstractExportFBs {
	private static String starPattern = "(_[a-zA-Z0-9]|[a-zA-Z])(_?[a-zA-Z0-9])*"; //$NON-NLS-1$
	private static String doubleStarPattern = starPattern + "(::" + starPattern + ")*"; //$NON-NLS-1$ //$NON-NLS-2$
	private static Pattern replacePattern = Pattern.compile("\\*\\*|\\*"); //$NON-NLS-1$

	private String symbolicName;
	private List<Pattern> includePatterns;
	private List<Pattern> excludePatterns;
	private TypeLibrary typelib;

	public void setSymbolicName(final String symbolicName) {
		this.symbolicName = symbolicName;
	}

	@Override
	protected File getDirectory() {
		return new File(getFordiacProject().getLocationURI());
	}

	@Override
	protected String getSingleFBName() {
		return null;
	}

	@Override
	protected List<String> getExcludeSubfolder() {
		return new ArrayList<>();
	}

	@Override
	protected void checkFordiacProject() {
		super.checkFordiacProject();
		final Manifest manifest = ManifestHelper.getContainerManifest(getFordiacProject());
		if (manifest == null) {
			throw new BuildException("Project named '" + getProjectNameString() + "' lacks a manifest file"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (manifest.getExports() == null) {
			throw new BuildException("No exports present in manifest file"); //$NON-NLS-1$
		}
		final Library library = manifest.getExports().getLibrary().stream()
				.filter(lib -> lib.getSymbolicName().equals(symbolicName)).findFirst().orElse(null);
		if (library == null) {
			throw new BuildException("Library export named '" + symbolicName + "' not present in manifest file"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		setExportDirectory(exportDirectory + File.separator + symbolicName);

		includePatterns = new ArrayList<>();
		excludePatterns = new ArrayList<>();
		typelib = TypeLibraryManager.INSTANCE.getTypeLibrary(getFordiacProject());

		library.getIncludes().getLibraryElement().forEach(elem -> includePatterns.add(createPattern(elem.getValue())));
		library.getExcludes().getLibraryElement().forEach(elem -> excludePatterns.add(createPattern(elem.getValue())));
	}

	@Override
	protected List<File> getFBsFiles(final List<File> files, final File dir, final String singleFBName,
			final List<String> excludeSubfolder) {

		if (!dir.isDirectory() && isFilteredFiletype(dir)) {
			final IPath location = Path.fromOSString(dir.getAbsolutePath());
			final IFile ifile = workspace.getRoot().getFileForLocation(location);
			final String typename = typelib.getTypeEntry(ifile).getFullTypeName();
			if (includePatterns.stream().anyMatch(p -> p.matcher(typename).matches())
					&& excludePatterns.stream().noneMatch(p -> p.matcher(typename).matches())) {
				files.add(dir);
			}
			return files;
		}
		if (dir.listFiles() != null) {
			for (final File file : dir.listFiles()) {
				getFBsFiles(files, file, singleFBName, excludeSubfolder);
			}
		}

		return files;
	}

	private static Pattern createPattern(final String raw) {
		final String p = replacePattern.matcher(raw).replaceAll(match -> {
			if (match.end() - match.start() > 1) {
				return doubleStarPattern;
			}
			return starPattern;
		});
		return Pattern.compile(p);
	}
}
