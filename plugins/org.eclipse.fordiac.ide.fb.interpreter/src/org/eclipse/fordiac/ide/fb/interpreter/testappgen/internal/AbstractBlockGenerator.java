/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Melanie Winter - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public abstract class AbstractBlockGenerator {
	protected FBType sourceType;
	protected TypeEntry entry;

	protected AbstractBlockGenerator(final FBType sourceType) {
		this.sourceType = sourceType;
	}

	protected void configureBlock(final FBType fb) {
		final Identification id = LibraryElementFactory.eINSTANCE.createIdentification();
		fb.setIdentification(id);
		id.setStandard("IEC 61499"); //$NON-NLS-1$

		fb.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		fb.setName(getTypeName());
		fb.setService(LibraryElementFactory.eINSTANCE.createService());
	}

	protected void createFile(final FBType fb) {
		final IProject project = sourceType.getTypeLibrary().getProject();
		final String s = sourceType.getTypeEntry().getFile().getFullPath().toString();
		final IFolder folder = project.getFolder(s.substring(project.getName().length() + 2, s.lastIndexOf('/')));
		final IFile destfile = folder.getFile(getTypeName() + ".fbt"); //$NON-NLS-1$

		entry = sourceType.getTypeLibrary().createTypeEntry(destfile);
		entry.setType(fb);
	}

	public TypeEntry getTypeEntry() {
		return entry;
	}

	public static void addPosition(final PositionableElement el, final double x, final double y) {
		final Position p0 = CoordinateConverter.INSTANCE.createPosFromScreenCoordinates((int) x, (int) y);
		el.setPosition(p0);
	}

	protected static Event createEvent(final String name, final boolean isInput) {
		final Event newEv = LibraryElementFactory.eINSTANCE.createEvent();
		newEv.setIsInput(isInput);
		newEv.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));
		newEv.setName(name);
		return newEv;
	}

	protected static VarDeclaration createVarDeclaration(final DataType type, final String name,
			final boolean isInput) {
		final VarDeclaration varDecl = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		varDecl.setName(name);
		varDecl.setIsInput(isInput);
		varDecl.setType(type);
		varDecl.setValue(LibraryElementFactory.eINSTANCE.createValue());
		varDecl.getValue().setValue(""); //$NON-NLS-1$
		varDecl.setComment(""); //$NON-NLS-1$
		return varDecl;
	}

	protected static With createWith(final VarDeclaration varD) {
		final With w = LibraryElementFactory.eINSTANCE.createWith();
		w.setVariables(varD);
		return w;
	}

	protected abstract String getTypeName();
}
