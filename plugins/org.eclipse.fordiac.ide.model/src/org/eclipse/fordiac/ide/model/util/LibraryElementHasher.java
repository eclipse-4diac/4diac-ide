/*******************************************************************************
 * Copyright (c) 2024, 2025 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Roithmayr - initial API and implementation and/or initial documentation
 *   Martin Erich Jobst - code cleanup
 *                      - use base64 encoding for hashes
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.util;

import java.io.IOException;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Base64;
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

public final class LibraryElementHasher {
	/**
	 * The current hash version
	 *
	 * @implNote increment this whenever anything is changed in the hashing
	 *           algorithm
	 */
	public static final String CURRENT_HASH_VERSION = "v1"; //$NON-NLS-1$

	/**
	 * The default hash algorithm
	 */
	public static final String DEFAULT_HASH_ALGORITHM = "SHA3-512"; //$NON-NLS-1$

	private static final String XMI_EXTENSION = "xmi"; //$NON-NLS-1$
	private static final String TOHASH_XMI_URI = "tohash.xmi"; //$NON-NLS-1$

	/**
	 * Calculate hash with default parameters for the given object
	 *
	 * @param eObject The object to hash
	 * @return The hash string
	 * @throws LibraryElementHashException if an error occurs
	 */
	public static String hash(final EObject eObject) throws LibraryElementHashException {
		return hash(eObject, CURRENT_HASH_VERSION, DEFAULT_HASH_ALGORITHM);
	}

	/**
	 * Calculate hash with given parameters for the given object
	 *
	 * @param eObject   The object to hash
	 * @param version   The hash version
	 * @param algorithm The hash algorithm
	 * @return The hash string
	 * @throws LibraryElementHashException if an error occurs
	 */
	public static String hash(final EObject eObject, final String version, final String algorithm)
			throws LibraryElementHashException {
		if (!CURRENT_HASH_VERSION.equals(version)) {
			throw new LibraryElementHashException(MessageFormat.format("Wrong library hash version: {0}", version)); //$NON-NLS-1$
		}
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new LibraryElementHashException("could not aquire hashing algorithm", e); //$NON-NLS-1$
		}

		final ResourceSetImpl xmiResourceSet = new ResourceSetImpl();
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().putIfAbsent(XMI_EXTENSION,
				new XMIResourceFactoryImpl());
		final Resource xmiResource = xmiResourceSet.createResource(URI.createFileURI(TOHASH_XMI_URI));
		xmiResource.getContents().add(copyForHashing(eObject));

		final StringBuilder sb = new StringBuilder(version);
		sb.append(':');
		sb.append(algorithm);
		sb.append(':');

		try (OutputStream nullOut = OutputStream.nullOutputStream();
				DigestOutputStream dos = new DigestOutputStream(nullOut, digest)

		) {
			final HashMap<String, Object> options = new HashMap<>();
			options.put(XMLResource.OPTION_PROCESS_DANGLING_HREF, XMLResource.OPTION_PROCESS_DANGLING_HREF_DISCARD);
			options.put(XMLResource.OPTION_SKIP_ESCAPE_URI, Boolean.FALSE);

			xmiResource.save(dos, options);
			sb.append(Base64.getUrlEncoder().encodeToString(digest.digest()));
		} catch (final IOException e) {
			throw new LibraryElementHashException("Problem with generating library element hash", e); //$NON-NLS-1$
		}

		return sb.toString();
	}

	private static EObject copyForHashing(final EObject eObject) {
		final Copier copier = new HashCopier();
		final EObject result = copier.copy(eObject);
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
