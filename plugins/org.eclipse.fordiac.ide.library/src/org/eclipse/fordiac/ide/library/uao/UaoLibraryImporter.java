package org.eclipse.fordiac.ide.library.uao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.LibraryPermission;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
// UAO generated model (adjust if needed)
import org.tempuri.library.mgmt.LibraryType;
import org.tempuri.library.mgmt.ManifestType;

public final class UaoLibraryImporter {

	private static final java.net.URI WORKSPACE_LIB_URI = java.net.URI
			.create("WORKSPACE_LOC/" + LibraryManager.EXTRACTED_LIB_DIRECTORY); //$NON-NLS-1$

	private UaoLibraryImporter() {
		// util
	}

	public static java.net.URI convertAndInstall(final Path uaoFolder, final IProject project, final boolean autoImport,
			final boolean resolve) {

		final ManifestType uaoManifest = UaoEmfManifestLoader.loadManifestType(uaoFolder).orElse(null);
		if (uaoManifest == null || uaoManifest.getLibrary() == null) {
			FordiacLogHelper.logWarning(
					MessageFormat.format("UAO Manifest.mf could not be loaded as EMF model from {0}", uaoFolder)); //$NON-NLS-1$
			return null;
		}

		final LibraryType lib = uaoManifest.getLibrary();
		final String libName = sanitize(lib.getName(), "unknown"); //$NON-NLS-1$
		final String libVersion = sanitize(lib.getVersion(), "0.0.0"); //$NON-NLS-1$
		final String folderName = libName + "-" + libVersion; //$NON-NLS-1$

		final Path workspaceRoot = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toPath();
		final Path libRoot = workspaceRoot.resolve(LibraryManager.EXTRACTED_LIB_DIRECTORY);
		final Path dest = libRoot.resolve(folderName);

		try {
			Files.createDirectories(libRoot);
			deleteFolderIfExists(dest);
			Files.createDirectories(dest);

			// Copy/convert types into typelib
			final Path destTypelib = dest.resolve(LibraryManager.LIB_TYPELIB_FOLDER_NAME);
			Files.createDirectories(destTypelib);
			copyAndConvertSources(uaoFolder, lib, destTypelib);

			// Convert UAO EMF manifest -> 4diac manifest model and store MANIFEST.MF
			final Manifest fordManifest = UaoTo4diacManifestConverter.convert(uaoManifest);
			write4diacManifest(dest.resolve(LibraryManager.MANIFEST), fordManifest);

		} catch (final Exception e) {
			FordiacLogHelper.logError("Cannot install UAO library into workspace .lib folder", e); //$NON-NLS-1$
			return null;
		}

		LibraryManager.INSTANCE.getExtractedLibraries();

		final java.net.URI importUri = URIUtil.append(WORKSPACE_LIB_URI, folderName);
		if (autoImport && project != null) {
			LibraryManager.INSTANCE.importLibrary(project, importUri, true, resolve);
		}
		return importUri;
	}

	private static void write4diacManifest(final Path manifestPath, final Manifest m) throws IOException {
		final URI emfUri = URI.createURI(manifestPath.toUri().toString());
		final Resource res = ManifestHelper.createResource(emfUri);
		res.getContents().add(m);
		res.save(Map.of());

		LibraryPermission.setPathReadOnly(manifestPath);
	}

	private static void copyAndConvertSources(final Path uaoFolder, final LibraryType lib, final Path destTypelib)
			throws IOException {

		// Prefer explicit <Sources><Path>..</Path></Sources>, else default to "src"
		final List<String> sourcePaths = (lib.getSources() != null && lib.getSources().getPath() != null
				&& !lib.getSources().getPath().isEmpty()) ? lib.getSources().getPath() : List.of("src"); //$NON-NLS-1$

		final Set<Path> sourceFiles = new HashSet<>();
		for (final String sp : sourcePaths) {
			final String norm = normalizeRelPathOrNull(sp);
			if (norm == null) {
				continue;
			}
			final Path resolved = uaoFolder.resolve(norm);
			if (Files.isDirectory(resolved)) {
				collectTypeCandidates(resolved, sourceFiles);
			} else if (Files.isRegularFile(resolved)) {
				sourceFiles.add(resolved);
			}
		}

		// Also support modules that already contain "typelib/"
		final Path existingTypelib = uaoFolder.resolve(LibraryManager.LIB_TYPELIB_FOLDER_NAME);
		if (Files.isDirectory(existingTypelib)) {
			collectTypeCandidates(existingTypelib, sourceFiles);
		}

		for (final Path file : sourceFiles) {
			final String fileName = file.getFileName().toString();
			final String ext = getExtension(fileName);

			String targetEnding = null;
			String targetBaseName = stripExtension(fileName);

			if (LibraryManager.TYPE_ENDINGS.contains(ext)) {
				targetEnding = ext;
			} else if ("xml".equalsIgnoreCase(ext)) { //$NON-NLS-1$
				final DetectedType dt = detectIec61499RootType(file);
				if (dt != null && dt.ending != null) {
					targetEnding = dt.ending;
					if (dt.typeName != null && !dt.typeName.isBlank()) {
						targetBaseName = dt.typeName.trim();
					}
				}
			}

			if (targetEnding == null) {
				continue;
			}

			final Path rel = uaoFolder.relativize(file);
			final Path relParent = rel.getParent() == null ? Path.of("") : rel.getParent(); //$NON-NLS-1$

			final Path target = destTypelib.resolve(relParent).resolve(targetBaseName + "." + targetEnding); //$NON-NLS-1$
			Files.createDirectories(target.getParent());
			Files.copy(file, target);

			LibraryPermission.setPathReadOnly(target);
		}
	}

	private static void collectTypeCandidates(final Path root, final Set<Path> out) throws IOException {
		Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) {
				final String n = file.getFileName().toString().toLowerCase();
				if (n.endsWith(".xml")) { //$NON-NLS-1$
					out.add(file);
				} else {
					final int dot = n.lastIndexOf('.');
					if (dot > 0) {
						final String ext = n.substring(dot + 1);
						if (LibraryManager.TYPE_ENDINGS.contains(ext)) {
							out.add(file);
						}
					}
				}
				return FileVisitResult.CONTINUE;
			}
		});
	}

	private static DetectedType detectIec61499RootType(final Path xmlFile) {
		try {
			final String head = readPrefix(xmlFile, 16 * 1024);

			final String[][] map = { { "FBType", "fbt" }, //$NON-NLS-1$ //$NON-NLS-2$
					{ "AdapterType", "adp" }, //$NON-NLS-1$ //$NON-NLS-2$
					{ "SubAppType", "sub" }, //$NON-NLS-1$ //$NON-NLS-2$
					{ "DataType", "dtp" }, //$NON-NLS-1$ //$NON-NLS-2$
					{ "ResourceType", "res" }, //$NON-NLS-1$ //$NON-NLS-2$
					{ "DeviceType", "dev" }, //$NON-NLS-1$ //$NON-NLS-2$
					{ "SegmentType", "seg" }, //$NON-NLS-1$ //$NON-NLS-2$
					{ "System", "sys" } //$NON-NLS-1$ //$NON-NLS-2$
			};

			for (final String[] e : map) {
				final String tag = e[0];
				final int idx = indexOfStartTag(head, tag);
				if (idx >= 0) {
					final String name = extractAttrValueFromTag(head.substring(idx), "Name"); //$NON-NLS-1$
					return new DetectedType(e[1], name);
				}
			}
		} catch (final IOException e) {
			// ignore
		}
		return null;
	}

	private static int indexOfStartTag(final String text, final String localName) {
		final String needle1 = "<" + localName; //$NON-NLS-1$
		int i = text.indexOf(needle1);
		if (i >= 0) {
			return i;
		}
		i = text.indexOf(":" + localName); //$NON-NLS-1$
		if (i < 0) {
			return -1;
		}
		return text.lastIndexOf('<', i);
	}

	private static String extractAttrValueFromTag(final String tagAndRest, final String attr) {
		final String a = attr + "=\""; //$NON-NLS-1$
		final int i = tagAndRest.indexOf(a);
		if (i < 0) {
			return null;
		}
		final int s = i + a.length();
		final int e = tagAndRest.indexOf('"', s);
		return e > s ? tagAndRest.substring(s, e) : null;
	}

	private static String readPrefix(final Path file, final int maxBytes) throws IOException {
		final byte[] b = Files.readAllBytes(file);
		final int len = Math.min(b.length, maxBytes);
		return new String(b, 0, len, StandardCharsets.UTF_8);
	}

	private static String getExtension(final String name) {
		final int dot = name.lastIndexOf('.');
		return dot >= 0 ? name.substring(dot + 1) : ""; //$NON-NLS-1$
	}

	private static String stripExtension(final String name) {
		final int dot = name.lastIndexOf('.');
		return dot >= 0 ? name.substring(0, dot) : name;
	}

	private static String sanitize(final String s, final String fallback) {
		if (s == null || s.isBlank()) {
			return fallback;
		}
		return s.trim().replaceAll("\\s+", "_") //$NON-NLS-1$ //$NON-NLS-2$
				.replaceAll("[^0-9A-Za-z._-]", "_"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static String normalizeRelPathOrNull(final String s) {
		if (s == null || s.isBlank()) {
			return null;
		}
		String v = s.trim().replace('\\', '/');
		while (v.startsWith("./")) { //$NON-NLS-1$
			v = v.substring(2);
		}
		if (v.startsWith("/") || v.contains(":/")) { //$NON-NLS-1$ //$NON-NLS-2$
			return null;
		}
		if (v.contains("../") || v.startsWith("..")) { //$NON-NLS-1$ //$NON-NLS-2$
			return null;
		}
		return v;
	}

	private static void deleteFolderIfExists(final Path folder) throws IOException {
		if (!Files.exists(folder)) {
			return;
		}
		Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
				LibraryPermission.setPathEditable(file);
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
				LibraryPermission.setPathEditable(dir);
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	private static final class DetectedType {
		final String ending;
		final String typeName;

		DetectedType(final String ending, final String typeName) {
			this.ending = ending;
			this.typeName = typeName;
		}
	}
}
