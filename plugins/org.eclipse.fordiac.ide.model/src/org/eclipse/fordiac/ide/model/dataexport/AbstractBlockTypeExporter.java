/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor Gmbh, TU Wien ACIN, fortiss GmbH
 * 				 2018, 2020 Johannes Keppler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 *  Alois Zoitl - extracted this helper class from the CommonElementExporter
 *              - changed exporting the Saxx cursor api
 *  Alois Zoitl, Bianca Wiesmayr - extracted code to common base class
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;

public abstract class AbstractBlockTypeExporter extends AbstractTypeExporter {

	protected AbstractBlockTypeExporter(FBType type) {
		super(type);
	}

	protected AbstractBlockTypeExporter(CommonElementExporter parent) {
		super(parent);
	}

	@Override
	protected FBType getType() {
		return (FBType) super.getType();
	}

	public static void saveType(PaletteEntry entry) {
		AbstractBlockTypeExporter exporter = null;

		if (entry instanceof FBTypePaletteEntry) {
			exporter = new FbtExporter((FBTypePaletteEntry) entry);
		} else if (entry instanceof AdapterTypePaletteEntry) {
			exporter = new AdapterExporter((AdapterTypePaletteEntry) entry);
		} else if (entry instanceof SubApplicationTypePaletteEntry) {
			exporter = new SubApplicationTypeExporter((SubApplicationTypePaletteEntry) entry);
		}

		if (null != exporter) {
			try {
				exporter.createXMLEntries();
			} catch (XMLStreamException e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
			exporter.writeToFile(entry.getFile());
			// "reset" the modification timestamp in the PaletteEntry
			// to avoid reload - as for this timestamp it is not necessary
			// as the data is in memory
			entry.setLastModificationTimestamp(entry.getFile().getModificationStamp());
		}
	}

	@Override
	protected void createTypeSpecificXMLEntries() throws XMLStreamException {
		addCompilerInfo(getType().getCompilerInfo());
		addInterfaceList(getType().getInterfaceList());
		createBlockTypeSpecificXMLEntries();
		addService(getType());
	}

	protected abstract void createBlockTypeSpecificXMLEntries() throws XMLStreamException;


	/**
	 * Adds the interface list.
	 *
	 * @param fb the fb
	 * @throws XMLStreamException
	 */
	protected void addInterfaceList(final InterfaceList interfaceList) throws XMLStreamException {
		addStartElement(getInterfaceListElementName());
		addEventList(interfaceList.getEventInputs(), getEventInputsElementName());
		addEventList(interfaceList.getEventOutputs(), getEventOutputsElementName());
		addVarList(interfaceList.getInputVars(), LibraryElementTags.INPUT_VARS_ELEMENT);
		addVarList(interfaceList.getOutputVars(), LibraryElementTags.OUTPUT_VARS_ELEMENT);
		createAdapterList(interfaceList.getPlugs(), LibraryElementTags.PLUGS_ELEMENT);
		createAdapterList(interfaceList.getSockets(), LibraryElementTags.SOCKETS_ELEMENT);
		addEndElement();
	}

	@SuppressWarnings("static-method")
	protected String getInterfaceListElementName() {
		return LibraryElementTags.INTERFACE_LIST_ELEMENT;
	}

	private void createAdapterList(final List<AdapterDeclaration> adapterList, final String elementName)
			throws XMLStreamException {
		if (!adapterList.isEmpty()) {
			addStartElement(elementName);
			for (AdapterDeclaration adapter : adapterList) {
				addAdapterDeclaration(adapter);
			}
			addEndElement();
		}
	}

	private void addAdapterDeclaration(final AdapterDeclaration adapterDecl) throws XMLStreamException {
		addEmptyStartElement(LibraryElementTags.ADAPTER_DECLARATION_ELEMENT);
		addNameTypeCommentAttribute(adapterDecl, adapterDecl.getType());
		if (null != adapterDecl.getAdapterFB()) {
			addXYAttributes(adapterDecl.getAdapterFB());
		}
	}

	/**
	 * Adds a var list (i.e., input or output data) to the dom. If the given var
	 * list is empty no element will be created.
	 *
	 * @param varList     the list of vars to create the entries for
	 * @param elementName the name of the xml element holding the event list
	 * @throws XMLStreamException
	 */
	protected void addVarList(final List<VarDeclaration> varList, final String elementName) throws XMLStreamException {
		if (!varList.isEmpty()) {
			addStartElement(elementName);
			for (VarDeclaration varDecl : varList) {
				if (!(varDecl instanceof AdapterDeclaration)) {
					addVarDeclaration(varDecl);
				}
			}
			addEndElement();
		}
	}

	/**
	 * Adds the an event list (i.e., input or output) to the dom. If the given event
	 * list is empty no element will be created.
	 *
	 * @param eventList   the list of events to create the entry for
	 * @param elementName the name of the xml element holding the event list
	 * @throws XMLStreamException
	 */
	private void addEventList(final List<Event> eventList, final String elementName) throws XMLStreamException {
		if (!eventList.isEmpty()) {
			addStartElement(elementName);
			for (Event event : eventList) {
				addEvent(event);
			}
			addEndElement();
		}
	}

	@SuppressWarnings("static-method")
	protected String getEventOutputsElementName() {
		return LibraryElementTags.EVENT_OUTPUTS;
	}

	@SuppressWarnings("static-method")
	protected String getEventInputsElementName() {
		return LibraryElementTags.EVENT_INPUTS_ELEMENT;
	}

	/**
	 * Adds the event.
	 *
	 * @param event the event
	 * @throws XMLStreamException
	 */
	private void addEvent(final Event event) throws XMLStreamException {
		addStartElement(getEventElementName());
		addNameAttribute(event.getName());
		getWriter().writeAttribute(LibraryElementTags.TYPE_ATTRIBUTE, "Event"); //$NON-NLS-1$
		addCommentAttribute(event);
		addWith(event);
		addEndElement();
	}

	@SuppressWarnings("static-method")
	protected String getEventElementName() {
		return LibraryElementTags.EVENT_ELEMENT;
	}

	/**
	 * Adds the with.
	 *
	 * @param event the event
	 * @throws XMLStreamException
	 */
	private void addWith(final Event event) throws XMLStreamException {
		for (With with : event.getWith()) {
			addEmptyStartElement(LibraryElementTags.WITH_ELEMENT);
			VarDeclaration varDecl = with.getVariables();
			getWriter().writeAttribute(LibraryElementTags.VAR_ATTRIBUTE,
					(null != varDecl.getName()) ? varDecl.getName() : ""); //$NON-NLS-1$
		}
	}

	/**
	 * Adds the service.
	 *
	 * @param fb the fb
	 * @throws XMLStreamException
	 */
	private void addService(final FBType sfb) throws XMLStreamException {

		if ((null != sfb.getService()) && (null != sfb.getService().getRightInterface())
				&& (null != sfb.getService().getLeftInterface())) {
			addStartElement(LibraryElementTags.SERVICE_ELEMENT);

			getWriter().writeAttribute(LibraryElementTags.RIGHT_INTERFACE_ATTRIBUTE,
					sfb.getService().getRightInterface().getName());
			getWriter().writeAttribute(LibraryElementTags.LEFT_INTERFACE_ATTRIBUTE,
					sfb.getService().getLeftInterface().getName());
			addCommentAttribute(sfb);

			addServiceSequences(sfb.getService().getServiceSequence());

			addEndElement();
		}
	}

	/**
	 * Adds the service sequences.
	 *
	 * @param sequences the sequences
	 * @throws XMLStreamException
	 */
	private void addServiceSequences(final List<ServiceSequence> sequences) throws XMLStreamException {
		for (ServiceSequence seq : sequences) {
			addStartElement(LibraryElementTags.SERVICE_SEQUENCE_ELEMENT);

			addNameAttribute(seq.getName());
			addCommentAttribute(seq);
			addServiceTransactions(seq.getServiceTransaction());
			addEndElement();
		}
	}

	/**
	 * Adds the service transactions.
	 *
	 * @param transactions the transactions
	 * @throws XMLStreamException
	 */
	private void addServiceTransactions(final List<ServiceTransaction> transactions) throws XMLStreamException {
		for (ServiceTransaction transaction : transactions) {
			addStartElement(LibraryElementTags.SERVICE_TRANSACTION_ELEMENT);

			if (transaction.getInputPrimitive() != null) {
				addPrimitive(transaction.getInputPrimitive(), LibraryElementTags.INPUT_PRIMITIVE_ELEMENT);
			}
			if (!transaction.getOutputPrimitive().isEmpty()) {
				addOutputPrimitives(transaction);
			}
			addEndElement();
		}
	}

	private void addPrimitive(final Primitive prim, final String primNodeName) throws XMLStreamException {
		addEmptyStartElement(primNodeName);
		getWriter().writeAttribute(LibraryElementTags.INTERFACE_ATTRIBUTE,
				((null != prim.getInterface()) && (null != prim.getInterface().getName()))
				? prim.getInterface().getName()
						: ""); //$NON-NLS-1$
		getWriter().writeAttribute(LibraryElementTags.EVENT_ELEMENT, (null != prim.getEvent()) ? prim.getEvent() : ""); //$NON-NLS-1$
		if ((null != prim.getParameters()) && (!prim.getParameters().equals(" "))) { //$NON-NLS-1$
			getWriter().writeAttribute(LibraryElementTags.PARAMETERS_ATTRIBUTE, prim.getParameters());
		}

	}

	/**
	 * Adds the output primitives.
	 *
	 * @param transaction the transaction
	 * @throws XMLStreamException
	 */
	private void addOutputPrimitives(final ServiceTransaction transaction) throws XMLStreamException {
		for (OutputPrimitive primitive : transaction.getOutputPrimitive()) {
			addPrimitive(primitive, LibraryElementTags.OUTPUT_PRIMITIVE_ELEMENT);
		}
	}

}
