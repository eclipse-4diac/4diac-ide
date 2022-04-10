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
	public static InterfaceList copy(final InterfaceList src, final boolean copyValues, final boolean copyComments) {
		final InterfaceList copy = LibraryElementFactory.eINSTANCE.createInterfaceList();

		// variables will be copied before the events so that the event copy can used
		// the copied vars for the with creation
		copyVarList(copy.getInputVars(), src.getInputVars(), copyValues, copyComments);
		copyVarList(copy.getOutputVars(), src.getOutputVars(), copyValues, copyComments);

		copyEventList(copy.getEventInputs(), copy.getInputVars(), src.getEventInputs(), src.getInputVars(),
				copyComments);
		copyEventList(copy.getEventOutputs(), copy.getOutputVars(), src.getEventOutputs(), src.getOutputVars(),
				copyComments);

		copyAdapterList(copy.getPlugs(), src.getPlugs(), copyComments);
		copyAdapterList(copy.getSockets(), src.getSockets(), copyComments);

		copyErrorMarkerList(copy.getErrorMarker(), src.getErrorMarker(), copyValues, copyComments);

		return copy;
	}

	private static void copyErrorMarkerList(final EList<IInterfaceElement> copy, final EList<IInterfaceElement> src,
			final boolean copyValues, final boolean copyComments) {
		src.forEach(c -> copy.add(copyMarker((ErrorMarkerInterface) c, copyComments)));
	}

	private static ErrorMarkerInterface copyMarker(final ErrorMarkerInterface src, final boolean copyComments) {
		final ErrorMarkerInterface copy = LibraryElementFactory.eINSTANCE.createErrorMarkerInterface();
		copyInterfaceElement(src, copy, copyComments);
		final IInterfaceElement repairedEndpoint = copy.getRepairedEndpoint();
		if (repairedEndpoint != null) {
			copy.setRepairedEndpoint(repairedEndpoint);
		}
		return copy;

	}

	public static InterfaceList copy(final InterfaceList src) {
		return copy(src, false, false);
	}

	public static void copyVarList(final Collection<VarDeclaration> destVars, final Collection<VarDeclaration> srcVars,
			final boolean copyComments) {
		srcVars.forEach(variable -> destVars.add(copyVar(variable, false, copyComments)));
	}

	private static void copyVarList(final EList<VarDeclaration> destVars, final EList<VarDeclaration> srcVars,
			final boolean copyValues, final boolean copyComments) {
		srcVars.forEach(variable -> destVars.add(copyVar(variable, copyValues, copyComments)));
	}

	public static VarDeclaration copyVar(final VarDeclaration variable, final boolean copyValues,
			final boolean copyComments) {
		final VarDeclaration copy = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		copy.setArraySize(variable.getArraySize());

		copyInterfaceElement(variable, copy, copyComments);

		final Value varInitialization = LibraryElementFactory.eINSTANCE.createValue();
		if ((copyValues) && (null != variable.getValue())) {
			varInitialization.setValue(variable.getValue().getValue());
		}
		copy.setValue(varInitialization); // ensure that all vars have a value, reduces null checks

		return copy;
	}

	private static void copyInterfaceElement(final IInterfaceElement src, final IInterfaceElement dst,
			final boolean copyComments) {
		if (copyComments) {
			dst.setComment(src.getComment());
		}
		dst.setIsInput(src.isIsInput());
		dst.setName(src.getName());
		dst.setType(src.getType());
		dst.setTypeName(src.getTypeName());
	}

	/** copy a list of events with the associated with constructs
	 *
	 * @param destEvents the list of the copied events
	 * @param copyVars   the list of the data points for the new withs
	 * @param srcEvents  the source event list
	 * @param srcVars    the source vars used in the withs */
	private static void copyEventList(final EList<Event> destEvents, final EList<VarDeclaration> copyVars,
			final EList<Event> srcEvents, final EList<VarDeclaration> srcVars, final boolean copyComments) {
		srcEvents.forEach(srcEvent -> {
			final Event copy = copyEvent(srcEvent, copyComments);
			copyWiths(copy, srcEvent, copyVars, srcVars);
			destEvents.add(copy);
		});

	}

	public static Event copyEvent(final Event srcEvent, final boolean copyComments) {
		final Event copy = LibraryElementFactory.eINSTANCE.createEvent();
		copyInterfaceElement(srcEvent, copy, copyComments);
		if (copyComments) {
			copy.setComment(srcEvent.getComment());
		}
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

	private static void copyAdapterList(final EList<AdapterDeclaration> destAdapters,
			final EList<AdapterDeclaration> srcAdapters, final boolean copyComments) {
		srcAdapters.forEach(adapter -> destAdapters.add(copyAdapter(adapter, copyComments)));
	}

	public static AdapterDeclaration copyAdapter(final AdapterDeclaration adapter, final boolean copyComments) {
		final AdapterDeclaration copy = LibraryElementFactory.eINSTANCE.createAdapterDeclaration();
		copyInterfaceElement(adapter, copy, copyComments);
		copy.setTypeEntry(adapter.getTypeEntry());
		return copy;
	}

	private InterfaceListCopier() {
		throw new IllegalStateException("InterfaceListCopier is a utility class that can not be instantiated"); //$NON-NLS-1$
	}

}
