/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor Gmbh, TU Wien ACIN, fortiss GmbH
 * 				 2018, 2020 Johannes Keppler University
 * 				 2021 Primetals Technologies Austria GmbH
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
 *  Martin Melik Merkumians - adds export of interal FBs as part of interval variables
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.ServiceSequenceTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;

public abstract class AbstractBlockTypeExporter extends AbstractTypeExporter {

	protected AbstractBlockTypeExporter(final FBType type) {
		super(type);
	}

	protected AbstractBlockTypeExporter(final CommonElementExporter parent) {
		super(parent);
	}

	@Override
	public FBType getType() {
		return (FBType) super.getType();
	}

	@Override
	protected void createTypeSpecificXMLEntries() throws XMLStreamException {
		addCompilerInfo(getType().getCompilerInfo());
		addInterfaceList(getType().getInterfaceList());
		createBlockTypeSpecificXMLEntries();
		addService(getType());
		addAttributes(getType().getAttributes());
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
		addVarList(interfaceList.getInOutVars(), LibraryElementTags.INOUT_VARS_ELEMENT);
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
			for (final AdapterDeclaration adapter : adapterList) {
				addAdapterDeclaration(adapter);
			}
			addEndElement();
		}
	}

	private void addAdapterDeclaration(final AdapterDeclaration adapterDecl) throws XMLStreamException {
		final boolean hasAttributes = !adapterDecl.getAttributes().isEmpty();
		if (hasAttributes) {
			addStartElement(LibraryElementTags.ADAPTER_DECLARATION_ELEMENT);
		} else {
			addEmptyStartElement(LibraryElementTags.ADAPTER_DECLARATION_ELEMENT);
		}

		addNameTypeCommentAttribute(adapterDecl, adapterDecl.getType());
		if (null != adapterDecl.getAdapterNetworkFB()) {
			addXYAttributes(adapterDecl.getAdapterNetworkFB());
		}

		if (hasAttributes) {
			addAttributes(adapterDecl.getAttributes());
			addEndElement();
		}
	}

	/**
	 * Adds a var list (i.e., input or output data) to the dom. If the given var
	 * list is empty no element will be created.
	 *
	 * @param varList     the list of vars to create the entries for
	 * @param elementName the name of the xml element holding the var list
	 * @throws XMLStreamException
	 */
	protected void addVarList(final List<? extends VarDeclaration> varList, final String elementName)
			throws XMLStreamException {
		if (!varList.isEmpty()) {
			addStartElement(elementName);
			for (final VarDeclaration varDecl : varList) {
				addVarDeclaration(varDecl);
			}
			addEndElement();
		}
	}

	/**
	 * Adds a internal var list (i.e., internal vars and internal FBs) to the dom.
	 * If the given var list is empty no element will be created.
	 *
	 * @param varList     the list of vars to create the entries for
	 * @param elementName the name of the xml element holding the var list
	 * @throws XMLStreamException
	 */
	protected void addInternalVarList(final List<VarDeclaration> varList, final List<FB> fbList,
			final String elementName) throws XMLStreamException {
		if (!varList.isEmpty() || !fbList.isEmpty()) {
			addStartElement(elementName);
			for (final VarDeclaration varDecl : varList) {
				addVarDeclaration(varDecl);
			}
			for (final FB fb : fbList) {
				addStartElement(LibraryElementTags.FB_ELEMENT);
				addNameAttribute(fb.getName());
				if (null != fb.getType()) {
					addTypeAttribute(fb.getType());
				}
				addCommentAttribute(fb.getComment());

				addAttributes(fb.getAttributes());
				addEndElement();
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
			for (final Event event : eventList) {
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
		getWriter().writeAttribute(LibraryElementTags.TYPE_ATTRIBUTE, event.getTypeName());
		addCommentAttribute(event.getComment());
		addWith(event);
		addAttributes(event.getAttributes());
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
		for (final With with : event.getWith()) {
			addEmptyStartElement(LibraryElementTags.WITH_ELEMENT);
			final VarDeclaration varDecl = with.getVariables();
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
			addCommentAttribute(sfb.getComment());

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
		for (final ServiceSequence seq : sequences) {
			addStartElement(LibraryElementTags.SERVICE_SEQUENCE_ELEMENT);

			addNameAndCommentAttribute(seq);
			if (seq.getStartState() != null) {
				addAttributeElement(LibraryElementTags.START_STATE_ATTRIBUTE, IecTypes.ElementaryTypes.STRING,
						seq.getStartState(), null);
			}
			if (!seq.getServiceSequenceType().equals(ServiceSequenceTypes.DEFAULT)) {
				addAttributeElement(LibraryElementTags.SERVICE_SEQUENCE_TYPE_ATTRIBUTE, IecTypes.ElementaryTypes.STRING,
						seq.getServiceSequenceType(), null);
			}
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
		for (final ServiceTransaction transaction : transactions) {
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
		for (final OutputPrimitive primitive : transaction.getOutputPrimitive()) {
			addPrimitive(primitive, LibraryElementTags.OUTPUT_PRIMITIVE_ELEMENT);
		}
	}

}
