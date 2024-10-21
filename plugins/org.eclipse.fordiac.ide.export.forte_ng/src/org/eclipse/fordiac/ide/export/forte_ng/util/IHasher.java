package org.eclipse.fordiac.ide.export.forte_ng.util;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

public interface IHasher {

	String getLibraryElementHash(final LibraryElement libelem, final String version);

	String getLibraryElementHash(final LibraryElement libelem);

	String getHashType();
}
