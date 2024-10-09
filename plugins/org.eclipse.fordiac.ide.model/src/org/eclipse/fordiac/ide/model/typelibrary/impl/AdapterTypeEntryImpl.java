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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataexport.AdapterExporter;
import org.eclipse.fordiac.ide.model.dataimport.ADPImporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;

public class AdapterTypeEntryImpl extends AbstractCheckedTypeEntryImpl<AdapterType> implements AdapterTypeEntry {

	public AdapterTypeEntryImpl() {
		super(AdapterType.class);
	}

	@Override
	protected CommonElementImporter getImporter() {
		return new ADPImporter(getFile());
	}

	@Override
	protected AbstractTypeExporter getTypeExporter(final AdapterType type) {
		return new AdapterExporter(type);
	}

	@Override
	public EClass getTypeEClass() {
		return LibraryElementPackage.Literals.ADAPTER_TYPE;
	}

	@Override
	protected synchronized NotificationChain basicSetType(final LibraryElement type,
			final NotificationChain notifications) {
		if (type instanceof final AdapterType adpType) {
			// ensure that the plug is the mirror of the socket
			adpType.setPlugType(createPlugType(adpType));
		}
		return super.basicSetType(type, notifications);
	}

	public static AdapterType createPlugType(final AdapterType adapterType) {
		final AdapterType temp = EcoreUtil.copy(adapterType);
		// fetch the interface to invert it
		final List<Event> inputEvents = new ArrayList<>(temp.getInterfaceList().getEventOutputs());
		final List<VarDeclaration> inputVars = new ArrayList<>(temp.getInterfaceList().getOutputVars());
		Stream.concat(inputEvents.stream(), inputVars.stream()).forEach(element -> element.setIsInput(true));

		final List<Event> outputEvents = new ArrayList<>(temp.getInterfaceList().getEventInputs());
		final List<VarDeclaration> outputVars = new ArrayList<>(temp.getInterfaceList().getInputVars());
		Stream.concat(outputEvents.stream(), outputVars.stream()).forEach(event -> event.setIsInput(false));

		temp.getInterfaceList().getEventInputs().clear();
		temp.getInterfaceList().getEventOutputs().clear();
		temp.getInterfaceList().getInputVars().clear();
		temp.getInterfaceList().getOutputVars().clear();
		temp.getInterfaceList().getEventInputs().addAll(inputEvents);
		temp.getInterfaceList().getEventOutputs().addAll(outputEvents);
		temp.getInterfaceList().getInputVars().addAll(inputVars);
		temp.getInterfaceList().getOutputVars().addAll(outputVars);
		return temp;
	}
}
