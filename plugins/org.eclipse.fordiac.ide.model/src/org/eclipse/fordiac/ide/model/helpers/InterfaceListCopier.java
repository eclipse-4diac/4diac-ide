/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;

public final class InterfaceListCopier {

	/** Create a new copy of the source interface list
	 *
	 * @param src        source interface list
	 * @param copyValues flag indicating if initial values should be copied or not
	 * @return */
	public static InterfaceList copy(final InterfaceList src, final boolean copyValues) {
		final InterfaceList copy = LibraryElementFactory.eINSTANCE.createInterfaceList();

		// variables will be copied before the events so that the event copy can used
		// the copied vars for the with creation
		copyVarList(copy.getInputVars(), src.getInputVars(), copyValues);
		copyVarList(copy.getOutputVars(), src.getOutputVars(), copyValues);

		copyEventList(copy.getEventInputs(), copy.getInputVars(), src.getEventInputs(), src.getInputVars());
		copyEventList(copy.getEventOutputs(), copy.getOutputVars(), src.getEventOutputs(), src.getOutputVars());

		copyAdapterList(copy.getPlugs(), src.getPlugs());
		copyAdapterList(copy.getSockets(), src.getSockets());

		copyErrorMarkerList(copy.getErrorMarker(), src.getErrorMarker(), copyValues);

		return copy;
	}

	private static void copyErrorMarkerList(final EList<IInterfaceElement> copy, final EList<IInterfaceElement> src,
			final boolean copyValues) {
		src.forEach(c -> copy.add(copyMarker((ErrorMarkerInterface) c)));
	}

	private static ErrorMarkerInterface copyMarker(final ErrorMarkerInterface src) {
		final ErrorMarkerInterface copy = LibraryElementFactory.eINSTANCE.createErrorMarkerInterface();
		copy.setComment(src.getComment());
		copy.setName(src.getName());
		copy.setIsInput(src.isIsInput());
		copy.setType(src.getType());
		final IInterfaceElement repairedEndpoint = copy.getRepairedEndpoint();
		if (repairedEndpoint != null) {
			copy.setRepairedEndpoint(repairedEndpoint);
		}
		return copy;

	}

	public static InterfaceList copy(final InterfaceList src) {
		return copy(src, false);
	}

	public static void copyVarList(final Collection<VarDeclaration> destVars, final Collection<VarDeclaration> srcVars) {
		srcVars.forEach(var -> destVars.add(copyVar(var, false)));
	}

	private static void copyVarList(final EList<VarDeclaration> destVars, final EList<VarDeclaration> srcVars,
			final boolean copyValues) {
		srcVars.forEach(var -> destVars.add(copyVar(var, copyValues)));
	}

	public static VarDeclaration copyVar(final VarDeclaration var, final boolean copyValues) {
		final VarDeclaration copy = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		copy.setArraySize(var.getArraySize());
		copy.setComment(var.getComment());
		copy.setIsInput(var.isIsInput());
		copy.setName(var.getName());
		copy.setType(var.getType());
		copy.setTypeName(var.getTypeName());

		final Value varInitialization = LibraryElementFactory.eINSTANCE.createValue();
		if ((copyValues) && (null != var.getValue())) {
			varInitialization.setValue(var.getValue().getValue());
		}
		copy.setValue(varInitialization); // ensure that all vars have a value, reduces null checks

		return copy;
	}

	/**
	 * copy a list of events with the associated with constructs
	 *
	 * @param destEvents the list of the copied events
	 * @param copyVars   the list of the data points for the new withs
	 * @param srcEvents  the source event list
	 * @param srcVars    the source vars used in the withs
	 */
	private static void copyEventList(final EList<Event> destEvents, final EList<VarDeclaration> copyVars,
			final EList<Event> srcEvents, final EList<VarDeclaration> srcVars) {
		srcEvents.forEach(srcEvent -> {
			final Event copy = copyEvent(srcEvent);
			copyWiths(copy, srcEvent, copyVars, srcVars);
			destEvents.add(copy);
		});

	}

	private static Event copyEvent(final Event srcEvent) {
		final Event copy = LibraryElementFactory.eINSTANCE.createEvent();
		copy.setComment(srcEvent.getComment());
		copy.setIsInput(srcEvent.isIsInput());
		copy.setName(srcEvent.getName());
		copy.setType(srcEvent.getType());
		copy.setTypeName(srcEvent.getTypeName());
		return copy;
	}

	private static void copyWiths(final Event copy, final Event srcEvent, final EList<VarDeclaration> copyVars,
			final EList<VarDeclaration> srcVars) {
		for (final With with : srcEvent.getWith()) {
			final With withCopy = LibraryElementFactory.eINSTANCE.createWith();
			withCopy.setVariables(copyVars.get(srcVars.indexOf(with.getVariables())));
			copy.getWith().add(withCopy);
		}
	}

	private static void copyAdapterList(final EList<AdapterDeclaration> destAdapters, final EList<AdapterDeclaration> srcAdapters) {
		srcAdapters.forEach(adapter -> {
			final AdapterDeclaration copy = copyAdapter(adapter);
			destAdapters.add(copy);
		});

	}

	protected static AdapterDeclaration copyAdapter(final AdapterDeclaration adapter) {
		final AdapterDeclaration copy = LibraryElementFactory.eINSTANCE.createAdapterDeclaration();
		copy.setComment(adapter.getComment());
		copy.setIsInput(adapter.isIsInput());
		copy.setName(adapter.getName());
		copy.setPaletteEntry(adapter.getPaletteEntry());
		copy.setType(adapter.getType());
		copy.setTypeName(adapter.getTypeName());
		return copy;
	}

	private InterfaceListCopier() {
		throw new IllegalStateException("InterfaceListCopier is a utility class that can not be instantiated"); //$NON-NLS-1$
	}

}
