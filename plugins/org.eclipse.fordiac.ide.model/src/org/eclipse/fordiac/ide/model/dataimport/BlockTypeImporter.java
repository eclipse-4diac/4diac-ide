/********************************************************************************
 * Copyright (c) 2008, 2025  Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                           TU Wien/ACIN, Johannes Kepler University, Linz,
 *                           Primetals Technologies Austria GmbH,
 *                           Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Ingo Hegny, Alois Zoitl, Martin Jobst
 *               - initial API and implementation and/or initial documentation
 *  Peter Gsellmann - incorporating simple fb
 *  Alois Zoitl  - Changed XML parsing to Staxx cursor interface for improved
 *  			   parsing performance
 *  Martin Melik Merkumians - added import of internal FBs
 *  Martin Jobst - refactor marker handling
 *  Alois Zoitl  - updated for new adapter FB handling
 *  Martin Jobst - extract interface importer
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public abstract class BlockTypeImporter extends TypeImporter {

	private InterfaceListImporter interfaceListImporter;

	protected BlockTypeImporter(final IFile file) {
		super(file);
	}

	protected BlockTypeImporter(final InputStream inputStream, final TypeLibrary typeLibrary) {
		super(inputStream, typeLibrary);
	}

	protected BlockTypeImporter(final CommonElementImporter importer) {
		super(importer);
	}

	public FBType loadInterface() throws IOException, XMLStreamException, TypeImportException {
		setElement(createRootModelElement());
		try (ImporterStreams streams = createInputStreams(getInputStream())) {
			proceedToStartElementNamed(getStartElementName());
			readNameCommentAttributes(getElement());

			while (getReader().hasNext()) {
				final int event = getReader().next();
				if (XMLStreamConstants.START_ELEMENT == event) {
					final String localName = getReader().getLocalName();
					if (LibraryElementTags.COMPILER_INFO_ELEMENT.equals(localName)) {
						getElement().setCompilerInfo(parseCompilerInfo());
					} else if (getInterfaceListElementName().equals(localName)) {
						getElement().setInterfaceList(
								getInterfaceListImporter().parseInterfaceList(getInterfaceListElementName()));
						// stop at the first interface we get.
						break;
					}
				}
			}
		}

		if (getElement().getCompilerInfo() == null) {
			// in case we could not parse a compiler info set an empty one
			getElement().setCompilerInfo(LibraryElementFactory.eINSTANCE.createCompilerInfo());
		}

		return getElement();
	}

	@SuppressWarnings("static-method") // allow subclasse to provide a different name (e.g., SubTypeImporter)
	protected String getInterfaceListElementName() {
		return LibraryElementTags.INTERFACE_LIST_ELEMENT;
	}

	/**
	 * This method parses the DTD of a ServiceInterfaceFBType.
	 *
	 * @param type - The ServiceInterfaceFBType that is being parsed
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	protected void parseService(final FBType type) throws TypeImportException, XMLStreamException {

		final String rightInterface = getAttributeValue(LibraryElementTags.RIGHT_INTERFACE_ATTRIBUTE);
		if (null == rightInterface) {
			throw new TypeImportException(Messages.FBTImporter_SERVICE_INTERFACE_RIGHTINTERFACE_EXCEPTION);
		}
		final ServiceInterface rightInter = LibraryElementFactory.eINSTANCE.createServiceInterface();
		rightInter.setName(rightInterface);
		type.getService().setRightInterface(rightInter);
		final String leftInterface = getAttributeValue(LibraryElementTags.LEFT_INTERFACE_ATTRIBUTE);
		if (null == leftInterface) {
			throw new TypeImportException(Messages.FBTImporter_SERVICE_INTERFACE_LEFTINTERFACE_EXCEPTION);
		}
		final ServiceInterface leftInter = LibraryElementFactory.eINSTANCE.createServiceInterface();
		leftInter.setName(leftInterface);
		type.getService().setLeftInterface(leftInter);
		final String comment = getAttributeValue(LibraryElementTags.COMMENT_ATTRIBUTE);
		if (null != comment) {
			type.getService().setComment(comment);
		}

		processChildren(LibraryElementTags.SERVICE_ELEMENT, name -> {
			if (LibraryElementTags.SERVICE_SEQUENCE_ELEMENT.equals(name)) {
				parseServiceSequence(type);
				return true;
			}
			return false;
		});
	}

	/**
	 * This method parses the ServiceSequence of a ServiceInterfaceFBType.
	 *
	 * @param type - The ServiceInterfaceFBType from which the ServiceSequence will
	 *             be parsed
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private void parseServiceSequence(final FBType type) throws TypeImportException, XMLStreamException {
		final ServiceSequence serviceSequence = LibraryElementFactory.eINSTANCE.createServiceSequence();
		readNameCommentAttributes(serviceSequence);

		processChildren(LibraryElementTags.SERVICE_SEQUENCE_ELEMENT, name -> {
			if (LibraryElementTags.SERVICE_TRANSACTION_ELEMENT.equals(name)) {
				parseServiceTransaction(serviceSequence, type);
				return true;
			}
			if (LibraryElementTags.ATTRIBUTE_ELEMENT.equals(name)) {
				parseGenericAttributeNode(serviceSequence);
				proceedToEndElementNamed(name);
				return true;
			}
			return false;
		});
		processServiceAttributes(serviceSequence);
		type.getService().getServiceSequence().add(serviceSequence);
	}

	private static void processServiceAttributes(final ServiceSequence serviceSequence) {
		final EList<Attribute> attrs = serviceSequence.getAttributes();
		final List<Attribute> processed = new ArrayList<>();
		for (final Attribute attr : attrs) {
			if (attr.getName().equals(LibraryElementTags.START_STATE_ATTRIBUTE)) {
				serviceSequence.setStartState(attr.getValue());
				processed.add(attr);
			} else if (attr.getName().equals(LibraryElementTags.SERVICE_SEQUENCE_TYPE_ATTRIBUTE)) {
				serviceSequence.setServiceSequenceType(attr.getValue());
				processed.add(attr);
			}
		}
		attrs.removeAll(processed);
	}

	/**
	 * This method parses the ServiceTransaction of a ServiceSequence.
	 *
	 * @param serviceSequence - The serviceSequence containing the
	 *                        serviceTransaction that is being parsed
	 * @param type            - The serviceInterfaceFBType containing the
	 *                        serviceTransaction
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private void parseServiceTransaction(final ServiceSequence serviceSequence, final FBType type)
			throws TypeImportException, XMLStreamException {
		final ServiceTransaction serviceTransaction = LibraryElementFactory.eINSTANCE.createServiceTransaction();

		processChildren(LibraryElementTags.SERVICE_TRANSACTION_ELEMENT, name -> {
			switch (name) {
			case LibraryElementTags.INPUT_PRIMITIVE_ELEMENT:
				parseInputPrimitive(serviceTransaction, type);
				break;
			case LibraryElementTags.OUTPUT_PRIMITIVE_ELEMENT:
				parseOutputPrimitive(serviceTransaction, type);
				break;
			default:
				return false;
			}
			return true;
		});

		serviceSequence.getServiceTransaction().add(serviceTransaction);
	}

	/**
	 * This method parses the OutputPrimitive of a ServiceTransaction.
	 *
	 * @param serviceTransaction - The serviceTransaction containing the
	 *                           OutputPrimitive that is being parsed
	 * @param type               - the serviceInterfaceFBType containing the
	 *                           OutputPrimitive
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private void parseOutputPrimitive(final ServiceTransaction serviceTransaction, final FBType type)
			throws TypeImportException, XMLStreamException {
		final OutputPrimitive outputPrimitive = LibraryElementFactory.eINSTANCE.createOutputPrimitive();

		parsePrimitive(type, outputPrimitive);
		proceedToEndElementNamed(LibraryElementTags.OUTPUT_PRIMITIVE_ELEMENT);
		serviceTransaction.getOutputPrimitive().add(outputPrimitive);
	}

	private void parsePrimitive(final FBType type, final Primitive outputPrimitive) throws TypeImportException {
		final String interFace = getAttributeValue(LibraryElementTags.INTERFACE_ATTRIBUTE);
		if (null == interFace) {
			throw new TypeImportException(Messages.FBTImporter_OUTPUT_PRIMITIVE_EXCEPTION);
		}
		if (interFace.equals(type.getService().getLeftInterface().getName())) {
			outputPrimitive.setInterface(type.getService().getLeftInterface());
		} else if (interFace.equals(type.getService().getRightInterface().getName())) {
			outputPrimitive.setInterface(type.getService().getRightInterface());
		}
		final String event = getAttributeValue(getEventElement());
		if (null == event) {
			throw new TypeImportException(Messages.FBTImporter_OUTPUT_PRIMITIVE_EVENT_EXCEPTION);
		}
		outputPrimitive.setEvent(event);
		final String parameters = getAttributeValue(LibraryElementTags.PARAMETERS_ATTRIBUTE);
		if (null != parameters) {
			outputPrimitive.setParameters(parameters);
		}
	}

	/**
	 * This method parses the InputPrimitive of a ServiceTransaction.
	 *
	 * @param serviceTransaction - The serviceTransaction containing the
	 *                           InputPrimitive that is being parsed
	 * @param type               - the serviceInterfaceFBType containing the
	 *                           InputPrimitive
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private void parseInputPrimitive(final ServiceTransaction serviceTransaction, final FBType type)
			throws TypeImportException, XMLStreamException {
		final InputPrimitive inputPrimitive = LibraryElementFactory.eINSTANCE.createInputPrimitive();

		parsePrimitive(type, inputPrimitive);
		proceedToEndElementNamed(LibraryElementTags.INPUT_PRIMITIVE_ELEMENT);
		serviceTransaction.setInputPrimitive(inputPrimitive);
	}

	protected final InterfaceListImporter getInterfaceListImporter() {
		if (interfaceListImporter == null) {
			interfaceListImporter = createInterfaceListImporter();
		}
		return interfaceListImporter;
	}

	protected InterfaceListImporter createInterfaceListImporter() {
		return new InterfaceListImporter(this);
	}

	@SuppressWarnings("static-method")
	protected String getEventElement() {
		return LibraryElementTags.EVENT_ELEMENT;
	}

	@Override
	public FBType getElement() {
		return (FBType) super.getElement();
	}
}
