/*******************************************************************************
 * Copyright (c) 2024, 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Roithmayr - initial API and implementation and/or initial documentation
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.util;

import java.io.IOException;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.HashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.fordiac.ide.model.emf.HashMetaData;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public final class LibraryElementHasher {
	public static final String DEFAULT_HASH_ALG = "SHA3-512"; //$NON-NLS-1$
	public static final String HASH_VERSION = "v1"; //$NON-NLS-1$ // increment this whenever anything is changed in the
													// hashing algorithm
	public static final String FULL_HASH_STRING = HASH_VERSION + ":" + DEFAULT_HASH_ALG; //$NON-NLS-1$
	private static final String XMI_EXTENSION = "xmi"; //$NON-NLS-1$
	private static final String TOHASH_XMI_URI = "tohash.xmi"; //$NON-NLS-1$

	public static String getHashType() {
		return FULL_HASH_STRING;
	}

	public static String getLibraryElementHash(final INamedElement libelem, final String version)
			throws LibraryElementHashException {
		final String[] versionParts = version.split(":"); //$NON-NLS-1$
		if (versionParts.length != 2) {
			throw new LibraryElementHashException("Library hash version in wrong format"); //$NON-NLS-1$
		}
		if (!versionParts[0].equals(HASH_VERSION)) {
			throw new LibraryElementHashException(
					MessageFormat.format("Wrong library hash version: {0}", versionParts[0])); //$NON-NLS-1$
		}
		return hash(libelem, versionParts[1]);
	}

	private static String hash(final EObject eobj, final String version) throws LibraryElementHashException {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(version);
		} catch (final NoSuchAlgorithmException e) {
			throw new LibraryElementHashException("could not aquire hashing algorithm", e); //$NON-NLS-1$
		}

		final ResourceSetImpl xmiResourceSet = new ResourceSetImpl();
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().putIfAbsent(XMI_EXTENSION,
				new XMIResourceFactoryImpl());
		final Resource xmiResource = xmiResourceSet.createResource(URI.createFileURI(TOHASH_XMI_URI));
		xmiResource.getContents().add(copyLibraryElementForHashing(eobj));

		final StringBuilder sb = new StringBuilder();

		try (OutputStream nullOut = OutputStream.nullOutputStream();
				DigestOutputStream dos = new DigestOutputStream(nullOut, digest)

		) {
			final HashMap<String, Object> options = new HashMap<>();
			options.put(XMLResource.OPTION_PROCESS_DANGLING_HREF, XMLResource.OPTION_PROCESS_DANGLING_HREF_DISCARD);
			options.put(XMLResource.OPTION_SKIP_ESCAPE_URI, Boolean.FALSE);

			xmiResource.save(dos, options);
			for (final byte b : digest.digest()) {
				sb.append(String.format("%02x", b)); //$NON-NLS-1$
			}

		} catch (final IOException e) {
			throw new LibraryElementHashException("Problem with generating library element hash", e); //$NON-NLS-1$
		}

		return sb.toString();
	}

	public static String getLibraryElementHash(final INamedElement libelem) throws LibraryElementHashException {
		return hash(libelem, DEFAULT_HASH_ALG);
	}

	private static EObject copyLibraryElementForHashing(final EObject libraryElement) {
		final Copier copier = new HashCopier();
		final EObject result = copier.copy(libraryElement);
		copier.copyReferences();
		return result;
	}

	private static class HashCopier extends EcoreUtil.Copier {
		private static final long serialVersionUID = 1L;

		@Override
		public EObject copy(final EObject eObject) {
			if (eObject == null) {
				return null;
			}
			if (!HashMetaData.isIgnored(eObject.eClass())) {
				return super.copy(eObject);
			}
			return null;
		}

		@Override
		protected void copyAttribute(final EAttribute eAttribute, final EObject eObject, final EObject copyEObject) {
			if (!HashMetaData.isIgnored(eAttribute)) {
				super.copyAttribute(eAttribute, eObject, copyEObject);
			}
		}

		@Override
		protected void copyAttributeValue(final EAttribute eAttribute, final EObject eObject, final Object value,
				final EStructuralFeature.Setting setting) {
			super.copyAttributeValue(eAttribute, eObject, HashMetaData.transform(eAttribute, value), setting);
		}
	}

	private LibraryElementHasher() {
		throw new UnsupportedOperationException("Utility class shall not be instantiated"); //$NON-NLS-1$
	}
}
