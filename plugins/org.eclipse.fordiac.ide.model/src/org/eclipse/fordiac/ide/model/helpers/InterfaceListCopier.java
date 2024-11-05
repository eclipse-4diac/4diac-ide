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

import static org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper.getArraySize;
import static org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper.setArraySize;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
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

	/**
	 * Create a new copy of the source interface list
	 *
	 * @param src        source interface list
	 * @param copyValues flag indicating if initial values should be copied or not
	 * @return
	 */
	public static InterfaceList copy(final InterfaceList src, final boolean copyValues, final boolean copyComments) {
		final InterfaceList copy = LibraryElementFactory.eINSTANCE.createInterfaceList();

		// variables will be copied before the events so that the event copy can used
		// the copied vars for the with creation
		copyVarList(copy.getInputVars(), src.getInputVars(), copyValues, copyComments);
		copyVarList(copy.getOutputVars(), src.getOutputVars(), copyValues, copyComments);

		copyVarList(copy.getInOutVars(), src.getInOutVars(), copyValues, copyComments);

		copyEventList(copy.getEventInputs(), copy.getInputVars(), copy.getInOutVars(), src.getEventInputs(),
				src.getInputVars(), src.getInOutVars(), copyComments);
		copyEventList(copy.getEventOutputs(), copy.getOutputVars(), copy.getOutMappedInOutVars(), src.getEventOutputs(),
				src.getOutputVars(), src.getOutMappedInOutVars(), copyComments);

		copyAdapterList(copy.getPlugs(), src.getPlugs(), copyComments);
		copyAdapterList(copy.getSockets(), src.getSockets(), copyComments);

		copyErrorMarkerList(copy.getErrorMarker(), src.getErrorMarker(), copyValues, copyComments);

		return copy;
	}

	private static void copyErrorMarkerList(final EList<ErrorMarkerInterface> copy,
			final EList<ErrorMarkerInterface> src, final boolean copyValues, final boolean copyComments) {
		src.forEach(c -> copy.add(copyMarker(c, copyValues, copyComments)));
	}

	private static ErrorMarkerInterface copyMarker(final ErrorMarkerInterface src, final boolean copyValues,
			final boolean copyComments) {
		final ErrorMarkerInterface copy = LibraryElementFactory.eINSTANCE.createErrorMarkerInterface();
		copyInterfaceElement(src, copy, copyComments);

		if ((copyValues) && (null != src.getValue())) {
			final Value varInitialization = LibraryElementFactory.eINSTANCE.createValue();
			varInitialization.setValue(src.getValue().getValue());
			copy.setValue(varInitialization);
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
		setArraySize(copy, getArraySize(variable));

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
	}

	/**
	 * copy a list of events with the associated with constructs
	 *
	 * @param destEvents    the list of the copied events
	 * @param copyVars      the list of the data points for the new withs
	 * @param copyInOutVars
	 * @param srcEvents     the source event list
	 * @param srcVars       the source vars used in the withs
	 * @param srcVarInOuts
	 */
	private static void copyEventList(final EList<Event> destEvents, final EList<VarDeclaration> copyVars,
			final EList<VarDeclaration> copyInOutVars, final EList<Event> srcEvents,
			final EList<VarDeclaration> srcVars, final EList<VarDeclaration> srcVarInOuts, final boolean copyComments) {
		srcEvents.forEach(srcEvent -> {
			final Event copy = copyEvent(srcEvent, copyComments);
			copyWiths(copy, srcEvent, copyVars, copyInOutVars, srcVars, srcVarInOuts);
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
			final EList<VarDeclaration> copyInOutVars, final EList<VarDeclaration> srcVars,
			final EList<VarDeclaration> srcVarInOuts) {
		for (final With with : srcEvent.getWith()) {
			final With withCopy = LibraryElementFactory.eINSTANCE.createWith();
			withCopy.setVariables(getCopiedVar(copyVars, copyInOutVars, srcVars, srcVarInOuts, with));
			copy.getWith().add(withCopy);
		}
	}

	private static VarDeclaration getCopiedVar(final EList<VarDeclaration> copyVars,
			final EList<VarDeclaration> copyInOutVars, final EList<VarDeclaration> srcVars,
			final EList<VarDeclaration> srcVarInOuts, final With with) {
		final VarDeclaration withVar = with.getVariables();
		if (withVar.isInOutVar()) {
			return copyInOutVars.get(srcVarInOuts.indexOf(withVar));
		}
		return copyVars.get(srcVars.indexOf(withVar));
	}

	private static void copyAdapterList(final EList<AdapterDeclaration> destAdapters,
			final EList<AdapterDeclaration> srcAdapters, final boolean copyComments) {
		srcAdapters.forEach(adapter -> destAdapters.add(copyAdapter(adapter, copyComments)));
	}

	public static AdapterDeclaration copyAdapter(final AdapterDeclaration adapter, final boolean copyComments) {
		final AdapterDeclaration copy = LibraryElementFactory.eINSTANCE.createAdapterDeclaration();
		copyInterfaceElement(adapter, copy, copyComments);
		copy.setAdapterFB(EcoreUtil.copy(adapter.getAdapterFB()));
		copy.getAdapterFB().setAdapterDecl(copy);
		copy.setInterfaceOnlyAdapterFB(copy.getAdapterFB());
		return copy;
	}

	private InterfaceListCopier() {
		throw new IllegalStateException("InterfaceListCopier is a utility class that can not be instantiated"); //$NON-NLS-1$
	}

}
