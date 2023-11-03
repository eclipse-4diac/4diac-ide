/*******************************************************************************
 * Copyright (c) 2023 Fabio Gandolfi
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class ServiceSequenceSaveAndLoadHelper {

	private static final String FILE_EXTENSION = ".EvntMngr.opsem"; //$NON-NLS-1$
	private static final String FOLDER_NAME = "traces"; //$NON-NLS-1$

	public static void saveServiceSequence(final FBType fbtype, final ServiceSequence seq) {
		saveServiceSequences(fbtype, new ArrayList<>(Arrays.asList(seq)));
	}

	public static void saveServiceSequences(final FBType fbtype, final ArrayList<ServiceSequence> seqs) {
		final IProject project = fbtype.getTypeEntry().getFile().getProject();
		final IFolder folder = project.getFolder(FOLDER_NAME);
		if (!folder.exists()) {
			try {
				folder.create(false, false, null);
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage());
			}
		}
		final IFile file = folder.getFile(fbtype.getQualifiedName() + FILE_EXTENSION);
		if (file.exists()) {
			final ArrayList<ServiceSequence> seqsFromFile = loadServiceSequencesFromFile(fbtype);
			seqs.forEach(seq -> {
				if (!seqsFromFile.contains(seq)) {
					seqsFromFile.add(seq);
				}
			});
		}

		saveServiceSequenceToFile(fbtype, seqs);
	}

	private static void saveServiceSequenceToFile(final FBType fbtype, final ArrayList<ServiceSequence> seqs) {
		final ResourceSet reset = new ResourceSetImpl();
		final IProject project = fbtype.getTypeEntry().getFile().getProject();
		final IFolder folder = project.getFolder(FOLDER_NAME);
		if (!folder.exists()) {
			try {
				folder.create(false, false, null);
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage());
			}
		}
		final IFile file = folder.getFile(fbtype.getQualifiedName() + FILE_EXTENSION);
		final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		final Resource res = reset.createResource(uri);

		res.getContents().addAll(seqs);
		reset.getResources().add(fbtype.eResource());
		try {
			res.save(Collections.emptyMap());
		} catch (final IOException e) {
			FordiacLogHelper.logError(e.getMessage());
		}
	}

	public static ArrayList<ServiceSequence> loadServiceSequencesFromFile(final FBType fbtype) {
		final ResourceSet reset = new ResourceSetImpl();
		final IProject project = fbtype.getTypeEntry().getFile().getProject();
		final IFolder folder = project.getFolder(FOLDER_NAME);
		final ArrayList<ServiceSequence> serviceSequences = new ArrayList<>();
		if (folder.exists()) {
			final IFile file = folder.getFile(fbtype.getQualifiedName() + FILE_EXTENSION);
			if (file.exists()) {
				final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
				final Resource res = reset.getResource(uri, true);

				res.getContents().forEach(con -> {
					if (con instanceof final ServiceSequence seqs) {
						serviceSequences.add(seqs);
					}
				});
			}
		}
		return serviceSequences;
	}

	public static EventManager loadEventManagerFromServiceSequenceFile(final FBType fbtype,
			final ServiceSequence seqToFind) {
		final ArrayList<ServiceSequence> seqs = loadServiceSequencesFromFile(fbtype);

		for (final ServiceSequence seq : seqs) {
			if (seq.getName().equals(seqToFind.getName()) && seq.getEventManager() != null
					&& seq.getEventManager() instanceof final EventManager evntMngr) {
				return evntMngr;
			}
		}

		return null;
	}

	public static void removeServiceSequenceFromFile(final FBType fbtype, final ServiceSequence seq) {
		final ArrayList<ServiceSequence> serviceSequences = loadServiceSequencesFromFile(fbtype);
		serviceSequences.removeIf(s -> s.getName().equals(seq.getName()));
		saveServiceSequenceToFile(fbtype, serviceSequences);
	}
}
