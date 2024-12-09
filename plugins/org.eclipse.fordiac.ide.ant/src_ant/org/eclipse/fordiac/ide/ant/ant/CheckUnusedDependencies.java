package org.eclipse.fordiac.ide.ant.ant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

public class CheckUnusedDependencies extends Task {
	private String projectName;

	public void setProjectName(final String value) {
		projectName = value;
	}

	@Override
	public void execute() throws BuildException {
		if (projectName == null) {
			throw new BuildException("Project name not specified!"); //$NON-NLS-1$
		}
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject fordiacProject = workspace.getRoot().getProject(projectName);

		final Manifest manifest = ManifestHelper.getContainerManifest(fordiacProject);
		if (manifest == null) {
			throw new BuildException("Project named '" + projectName + "' lacks a manifest file"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (manifest.getDependencies() == null || manifest.getDependencies().getRequired().isEmpty()) {
			log("No dependencies present in manifest file"); //$NON-NLS-1$
			return;
		}

		final List<String> symb = new ArrayList<>();
		final Set<URI> libs = new HashSet<>();
		manifest.getDependencies().getRequired().forEach(req -> symb.add(req.getSymbolicName()));

		final Set<URI> unused = new HashSet<>();
		findLibs(symb, libs, unused, fordiacProject.getFolder(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME));
		findLibs(symb, libs, unused, fordiacProject.getFolder(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME));

		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(fordiacProject);

		for (final var typeEntry : typeLibrary.getAllTypes()) {
			if (unused.isEmpty()) {
				break;
			}

			if (libs.stream().anyMatch(l -> uriContained(l, typeEntry.getURI()))) {
				continue; // don't check references contained inside dependencies
			}

			typeEntry.getDependencies().forEach(dep -> {
				final var found = unused.stream().filter(u -> !uriContained(u, typeEntry.getURI()))
						.filter(u -> uriContained(u, dep.getURI())).toList();
				if (!found.isEmpty()) {
					unused.removeAll(found);
				}
			});
		}

		if (!unused.isEmpty()) {
			throw new BuildException("Unused dependencies detected: " //$NON-NLS-1$
					+ String.join(", ", unused.stream().map(URI::lastSegment).toList())); //$NON-NLS-1$
		}
		log("No unused dependencies detected"); //$NON-NLS-1$
	}

	private static void findLibs(final List<String> symb, final Set<URI> libs, final Set<URI> unused,
			final IFolder libFolder) {
		final URI libURI = URI.createPlatformResourceURI(libFolder.getFullPath().toString(), true);

		try {
			for (final var res : libFolder.members()) {
				if (res instanceof final IFolder folder && folder.isLinked()) {
					final Manifest libManifest = ManifestHelper.getContainerManifest(folder);
					if (libManifest != null && libManifest.getDependencies() != null) {
						libManifest.getDependencies().getRequired().forEach(dep -> {
							libs.add(libURI.appendSegment(dep.getSymbolicName()));
							symb.remove(dep.getSymbolicName());
						});
					}
				}
			}
		} catch (final CoreException e) {
			throw new BuildException(e);
		}

		symb.forEach(s -> unused.add(libURI.appendSegment(s)));
	}

	private static boolean uriContained(final URI base, final URI other) {
		final String[] baseSegments = base.segments();
		final String[] otherSegments = other.segments();

		if (baseSegments.length > otherSegments.length) {
			return false;
		}

		for (int i = 0; i < baseSegments.length; i++) {
			if (!baseSegments[i].equals(otherSegments[i])) {
				return false;
			}
		}

		return true;
	}
}
