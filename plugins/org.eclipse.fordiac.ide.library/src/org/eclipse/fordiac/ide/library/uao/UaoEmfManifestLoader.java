package org.eclipse.fordiac.ide.library.uao;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.tempuri.library.mgmt.DocumentRoot;
import org.tempuri.library.mgmt.ManifestType;
import org.tempuri.library.mgmt.MgmtPackage;

public final class UaoEmfManifestLoader {

	private static final String MANIFEST_FILE = "Manifest.mf"; //$NON-NLS-1$

	private UaoEmfManifestLoader() {
		// util
	}

	public static Optional<ManifestType> loadManifestType(final Path uaoFolder) {
		return loadRoot(uaoFolder).flatMap(UaoEmfManifestLoader::extractManifestType);
	}

	public static Optional<EObject> loadRoot(final Path uaoFolder) {
		if (uaoFolder == null || !Files.isDirectory(uaoFolder)) {
			return Optional.empty();
		}
		final Path mf = findManifestFile(uaoFolder);
		if (mf == null) {
			return Optional.empty();
		}

		// ensure package is initialized (important if model plugin isn't activated yet)
		MgmtPackage.eINSTANCE.eClass();

		final ResourceSet rs = new ResourceSetImpl();

		// register package + resource factory for ".mf"
		rs.getPackageRegistry().put(MgmtPackage.eNS_URI, MgmtPackage.eINSTANCE);
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("mf", new XMLResourceFactoryImpl()); //$NON-NLS-1$

		final URI uri = URI.createFileURI(mf.toAbsolutePath().toString());
		final Resource res = rs.createResource(uri);

		try {
			res.load(Map.of(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE,
					XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE,
					XMLResource.OPTION_USE_LEXICAL_HANDLER, Boolean.TRUE));
		} catch (final Exception e) {
			return Optional.empty();
		}

		if (res.getContents().isEmpty()) {
			return Optional.empty();
		}
		return Optional.ofNullable(res.getContents().get(0));
	}

	private static Optional<ManifestType> extractManifestType(final EObject root) {
		if (root instanceof final ManifestType mt) {
			return Optional.of(mt);
		}
		if (root instanceof final DocumentRoot dr) {
			return Optional.ofNullable(dr.getManifest());
		}
		// fallback: scan contents
		for (final EObject o : root.eContents()) {
			if (o instanceof final ManifestType mt) {
				return Optional.of(mt);
			}
		}
		return Optional.empty();
	}

	private static Path findManifestFile(final Path folder) {
		final Path p = folder.resolve(MANIFEST_FILE);
		if (Files.isRegularFile(p)) {
			return p;
		}
		// case-insensitive fallback
		try (var ds = Files.newDirectoryStream(folder)) {
			for (final Path f : ds) {
				if (Files.isRegularFile(f) && MANIFEST_FILE.equalsIgnoreCase(f.getFileName().toString())) {
					return f;
				}
			}
		} catch (final Exception e) {
			// ignore
		}
		return null;
	}
}
