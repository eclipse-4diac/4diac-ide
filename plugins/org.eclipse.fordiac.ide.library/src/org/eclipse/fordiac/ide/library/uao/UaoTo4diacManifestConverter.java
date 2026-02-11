package org.eclipse.fordiac.ide.library.uao;

import java.util.StringJoiner;

import org.eclipse.fordiac.ide.library.model.library.Attribute;
import org.eclipse.fordiac.ide.library.model.library.LibraryFactory;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.osgi.framework.Version;
// UAO generated model (adjust if needed)
import org.tempuri.library.mgmt.DependencyType;
import org.tempuri.library.mgmt.LibraryType;
import org.tempuri.library.mgmt.ManifestType;

public final class UaoTo4diacManifestConverter {

	private UaoTo4diacManifestConverter() {
		// util
	}

	public static Manifest convert(final ManifestType uaoManifest) {
		final Manifest m = ManifestHelper.createManifest("Library"); //$NON-NLS-1$

		final LibraryType lib = uaoManifest.getLibrary();
		if (lib != null && m.getProduct() != null) {
			final String symbolicName = safe(lib.getName(), "unknown.library"); //$NON-NLS-1$
			final String displayName = safe(lib.getDisplayName(), symbolicName);
			String version = lib.getVersion();

			try {
				new Version(lib.getVersion());
			} catch (final Exception e) {
				version = "0.0.0";
			}

			m.getProduct().setSymbolicName(symbolicName);
			m.getProduct().setName(displayName);
			m.getProduct().setComment(lib.getComment());
			m.getProduct().getVersionInfo().setVersion(version);

			addAttr(m, "UAO.Vendor", lib.getVendor()); //$NON-NLS-1$
			if (lib.getReadMe() != null) {
				addAttr(m, "UAO.ReadMe.Path", lib.getReadMe().getPath()); //$NON-NLS-1$
			}
			if (lib.getLicense() != null) {
				addAttr(m, "UAO.License.Identifier", lib.getLicense().getIdentifier()); //$NON-NLS-1$
				addAttr(m, "UAO.License.Path", lib.getLicense().getPath()); //$NON-NLS-1$
			}
			if (lib.getSources() != null && lib.getSources().getPath() != null
					&& !lib.getSources().getPath().isEmpty()) {
				final StringJoiner sj = new StringJoiner(";"); //$NON-NLS-1$
				lib.getSources().getPath().forEach(p -> {
					if (p != null && !p.isBlank()) {
						sj.add(p.trim());
					}
				});
				addAttr(m, "UAO.Sources", sj.toString()); //$NON-NLS-1$
			}
		}

		// dependencies
		if (uaoManifest.getDependencies() != null && uaoManifest.getDependencies().getDependency() != null) {
			for (final DependencyType d : uaoManifest.getDependencies().getDependency()) {
				if (d == null || isBlank(d.getName()) || isBlank(d.getVersion())) {
					continue;
				}
				ManifestHelper.addDependency(m,
						ManifestHelper.createRequired(d.getName().trim(), d.getVersion().trim()));
			}
		}

		return m;
	}

	private static void addAttr(final Manifest m, final String name, final String value) {
		if (isBlank(value) || m.getProduct() == null) {
			return;
		}
		final Attribute a = LibraryFactory.eINSTANCE.createAttribute();
		a.setName(name);
		a.setType("String"); //$NON-NLS-1$
		a.setValue(value.trim());
		m.getProduct().getAttribute().add(a);
	}

	private static boolean isBlank(final String s) {
		return s == null || s.trim().isEmpty();
	}

	private static String safe(final String s, final String fallback) {

		try {
			Integer.parseInt(s);
		} catch (final NumberFormatException e) {
			// return fallback;
		}

		return isBlank(s) ? fallback : s.trim();
	}
}
