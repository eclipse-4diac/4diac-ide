/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 *    Alois Zoitl  - turned the Palette model into POJOs
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary.impl;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataexport.FbtExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.FBTImporter;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class FBTypeEntryImpl extends AbstractCheckedTypeEntryImpl<FBType> implements FBTypeEntry {

	private static final Pattern TYPE_CLASS_PATTERN = Pattern.compile("<" + //$NON-NLS-1$
			LibraryElementTags.BASIC_F_B_ELEMENT + ">|<" //$NON-NLS-1$
			+ LibraryElementTags.FBNETWORK_ELEMENT + ">|<" //$NON-NLS-1$
			+ LibraryElementTags.SIMPLE_F_B_ELEMENT + ">"); //$NON-NLS-1$

	private final AtomicReference<EClass> typeEClass = new AtomicReference<>();

	public FBTypeEntryImpl() {
		super(FBType.class);
	}

	@Override
	protected CommonElementImporter getImporter() {
		return new FBTImporter(getFile());
	}

	@Override
	protected AbstractTypeExporter getTypeExporter(final FBType type) {
		return new FbtExporter(type);
	}

	@Override
	protected synchronized NotificationChain basicSetType(final LibraryElement type,
			final NotificationChain notifications) {
		if (type instanceof FBType) {
			typeEClass.set(type.eClass());
		}
		return super.basicSetType(type, notifications);
	}

	@Override
	public EClass getTypeEClass() {
		EClass result = typeEClass.get();
		if (result == null) {
			result = loadTypeEClassFromFile();
			typeEClass.compareAndSet(null, result); // only set if still null
			// we do not care about a (slightly) stale result here
		}
		return result;
	}

	private EClass loadTypeEClassFromFile() {
		final IFile file = getFile();
		if (file != null && file.exists()) {
			try (Scanner scanner = new Scanner(file.getContents())) {
				final String match = scanner.findWithinHorizon(TYPE_CLASS_PATTERN, 0);
				if (match != null) {
					final String typeClassString = match.substring(1, match.length() - 1);
					if (LibraryElementTags.BASIC_F_B_ELEMENT.equals(typeClassString)) {
						return LibraryElementPackage.Literals.BASIC_FB_TYPE;
					}
					if (LibraryElementTags.FBNETWORK_ELEMENT.equals(typeClassString)) {
						return LibraryElementPackage.Literals.COMPOSITE_FB_TYPE;
					}
					if (LibraryElementTags.SIMPLE_F_B_ELEMENT.equals(typeClassString)) {
						return LibraryElementPackage.Literals.SIMPLE_FB_TYPE;
					}
				}
				return LibraryElementPackage.Literals.SERVICE_INTERFACE_FB_TYPE;
			} catch (final Exception e) {
				FordiacLogHelper.logWarning(e.getMessage(), e);
			}
		}
		return LibraryElementPackage.Literals.FB_TYPE;
	}
}
