package org.eclipse.fordiac.ide.export.forte_ng.util;

import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class Hasher {

	public static String getINamedElementHash(final INamedElement type, final String version) {
		final List<IHasher> hashers = TypeLibraryManager.listExtensions(
				"org.eclipse.fordiac.ide.export.forte_ng.util.LibraryElementHasherExtension", //$NON-NLS-1$
				IHasher.class);
		if (type instanceof final LibraryElement libelem) {
			return hashers.getFirst().getLibraryElementHash(libelem, version);
		}
		return ""; // TODO error here //$NON-NLS-1$
	}

	public static String getINamedElementHash(final INamedElement type) {
		final List<IHasher> hashers = TypeLibraryManager.listExtensions(
				"org.eclipse.fordiac.ide.export.forte_ng.util.LibraryElementHasherExtension", //$NON-NLS-1$
				IHasher.class);
		if (type instanceof final LibraryElement libelem) {
			return hashers.getFirst().getLibraryElementHash(libelem);
		}
		return ""; // TODO error here //$NON-NLS-1$
	}

	public static String getHashType() {
		final List<IHasher> hashers = TypeLibraryManager.listExtensions(
				"org.eclipse.fordiac.ide.export.forte_ng.util.LibraryElementHasherExtension", //$NON-NLS-1$
				IHasher.class);
		return hashers.getFirst().getHashType();

	}
}
