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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;

public class InterfaceListImporter extends TypeImporter {

	/** The variables. */
	private final Map<String, VarDeclaration> variables = new HashMap<>();

	/** The input events. */
	private final Map<String, Event> inputEvents = new HashMap<>();

	/** The output events. */
	private final Map<String, Event> outputEvents = new HashMap<>();

	private final Map<Event, List<String>> withList = new HashMap<>();

	public InterfaceListImporter(final CommonElementImporter importer) {
		super(importer);
	}

	/**
	 * This method parses the InterfaceList of a FBType.
	 *
	 * @param interfaceListName
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	protected InterfaceList parseInterfaceList(final String interfaceListName)
			throws TypeImportException, XMLStreamException {
		final InterfaceList interfaceList = LibraryElementFactory.eINSTANCE.createInterfaceList();
		final String inputEventListName = getEventInputElement();
		final String outputEventListName = getEventOutputElement();

		processChildren(interfaceListName, name -> {
			if (inputEventListName.equals(name)) {
				parseEventList(interfaceList.getEventInputs(), inputEventListName, true);
			} else if (outputEventListName.equals(name)) {
				parseEventList(interfaceList.getEventOutputs(), outputEventListName, false);
			} else {
				switch (name) {
				case LibraryElementTags.INPUT_VARS_ELEMENT:
					parseVariableList(LibraryElementTags.INPUT_VARS_ELEMENT, interfaceList.getInputVars(), true);
					break;
				case LibraryElementTags.OUTPUT_VARS_ELEMENT:
					parseVariableList(LibraryElementTags.OUTPUT_VARS_ELEMENT, interfaceList.getOutputVars(), false);
					break;
				case LibraryElementTags.SOCKETS_ELEMENT:
					parseAdapterList(interfaceList.getSockets(), LibraryElementTags.SOCKETS_ELEMENT, true);
					break;
				case LibraryElementTags.PLUGS_ELEMENT:
					parseAdapterList(interfaceList.getPlugs(), LibraryElementTags.PLUGS_ELEMENT, false);
					break;
				case LibraryElementTags.INOUT_VARS_ELEMENT:
					parseVariableList(LibraryElementTags.INOUT_VARS_ELEMENT, interfaceList.getInOutVars(), true);
					break;
				default:
					return false;
				}
			}
			return true;
		});

		processWiths();
		return interfaceList;
	}

	@SuppressWarnings("static-method")
	protected String getEventOutputElement() {
		return LibraryElementTags.EVENT_OUTPUTS;
	}

	@SuppressWarnings("static-method")
	protected String getEventInputElement() {
		return LibraryElementTags.EVENT_INPUTS_ELEMENT;
	}

	private void parseVariableList(final String nodeName, final EList<? super VarDeclaration> varList,
			final boolean input) throws TypeImportException, XMLStreamException {
		processChildren(nodeName, name -> {
			if (name.equals(LibraryElementTags.VAR_DECLARATION_ELEMENT)) {
				final VarDeclaration v = parseVarDeclaration();
				varList.add(v);
				variables.put(v.getName(), v);
				v.setIsInput(input);
				return true;
			}
			return false;
		});
	}

	/**
	 * This method parses Plugs of a FBType.
	 *
	 * @param adpaterListName
	 * @param isInput
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private void parseAdapterList(final EList<AdapterDeclaration> adpaterList, final String adpaterListName,
			final boolean isInput) throws TypeImportException, XMLStreamException {
		processChildren(adpaterListName, name -> {
			if (LibraryElementTags.ADAPTER_DECLARATION_ELEMENT.equals(name)) {
				final AdapterDeclaration a = parseAdapterDeclaration(isInput);
				adpaterList.add(a);
				addAdapterEvents(a);
				return true;
			}
			return false;
		});
	}

	private void addAdapterEvents(final AdapterDeclaration a) {
		final InterfaceList adapterInterfaceList = a.getAdapterFB().getInterface();
		final String prefix = a.getName() + "."; //$NON-NLS-1$
		if ((null != a.getType()) && (null != adapterInterfaceList)) {
			adapterInterfaceList.getEventOutputs().forEach(ae -> inputEvents.put(prefix + ae.getName(), ae));
			adapterInterfaceList.getEventInputs().forEach(ae -> outputEvents.put(prefix + ae.getName(), ae));
		}
	}

	/**
	 * This method parses AdapterDeclaration.
	 *
	 * @param input - flag indicating if it is an in or output of our fb
	 *
	 * @return a - the AdapterDeclaration
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private AdapterDeclaration parseAdapterDeclaration(final boolean input)
			throws TypeImportException, XMLStreamException {
		final AdapterDeclaration a = LibraryElementFactory.eINSTANCE.createAdapterDeclaration();
		readNameCommentAttributes(a);
		// set input needs be done right after name and comment so that interface
		// creation below creates the right
		// socket or plug interface
		a.setIsInput(input);
		final String typeName = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		if (null == typeName) {
			throw new TypeImportException(Messages.FBTImporter_ADAPTER_DECLARATION_TYPE_EXCEPTION);
		}
		AdapterTypeEntry entry = addDependency(getTypeLibrary().getAdapterTypeEntry(typeName));
		if (entry == null) {
			entry = (AdapterTypeEntry) addDependency(
					getTypeLibrary().createErrorTypeEntry(typeName, LibraryElementPackage.Literals.ADAPTER_TYPE));
		}
		a.setType(entry.getType());

		createAdapterFB(a);
		getXandY(a.getAdapterFB());

		processChildren(LibraryElementTags.ADAPTER_DECLARATION_ELEMENT, name -> {
			if (LibraryElementTags.ATTRIBUTE_ELEMENT.equals(name)) {
				parseGenericAttributeNode(a);
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
				return true;
			}
			return false;
		});

		proceedToEndElementNamed(LibraryElementTags.ADAPTER_DECLARATION_ELEMENT);
		return a;
	}

	private static void createAdapterFB(final AdapterDeclaration adapter) {
		final AdapterFB aFB = LibraryElementFactory.eINSTANCE.createAdapterFB();
		aFB.setTypeEntry(adapter.getType().getTypeEntry());
		aFB.setAdapterDecl(adapter);
		adapter.setAdapterFB(aFB);
		adapter.setInterfaceOnlyAdapterFB(aFB);
		aFB.setName(adapter.getName());

		if (null != aFB.getType() && null != aFB.getType().getInterfaceList()) {
			aFB.setInterface(aFB.getType().getInterfaceList().copy());
		} else {
			// if we don't have a type or interface list set an empty interface list to
			// adapter
			aFB.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());
		}
	}

	protected void processWiths() {
		withList.entrySet().forEach(entry -> {
			final Event e = entry.getKey();
			entry.getValue().forEach(varName -> {
				final VarDeclaration v = getWithedVar(varName, e);
				if (null != v) {
					e.getWith().add(createWith(v));
				}
			});
		});
	}

	private VarDeclaration getWithedVar(final String varName, final Event ev) {
		final VarDeclaration varDecl = variables.get(varName);
		if (varDecl != null && varDecl.isInOutVar() && !ev.isIsInput()) {
			// we need to get the mirrored var in out
			return varDecl.getInOutVarOpposite();
		}
		return varDecl;
	}

	private static With createWith(final VarDeclaration v) {
		final With withConstruct = LibraryElementFactory.eINSTANCE.createWith();
		withConstruct.setVariables(v);
		return withConstruct;
	}

	/**
	 * This method parses EventInputs of FBTypes.
	 *
	 * @param isInput
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private void parseEventList(final EList<Event> eventList, final String eventListName, final boolean isInput)
			throws TypeImportException, XMLStreamException {
		final String eventName = getEventElement();

		processChildren(eventListName, name -> {
			if (eventName.equals(name)) {
				final Event e = parseEvent(eventName);
				e.setIsInput(isInput);
				if (isInput) {
					inputEvents.put(e.getName(), e);
				} else {
					outputEvents.put(e.getName(), e);
				}
				eventList.add(e);
				return true;
			}
			return false;
		});
	}

	@SuppressWarnings("static-method")
	protected String getEventElement() {
		return LibraryElementTags.EVENT_ELEMENT;
	}

	private Event parseEvent(final String eventName) throws TypeImportException, XMLStreamException {
		final Event e = LibraryElementFactory.eINSTANCE.createEvent();
		final String type = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		e.setType(EventTypeLibrary.getInstance().getType(type));
		readNameCommentAttributes(e);
		final List<String> withVars = new ArrayList<>();

		processChildren(eventName, name -> {
			switch (name) {
			case LibraryElementTags.WITH_ELEMENT:
				final String val = getAttributeValue(LibraryElementTags.VAR_ATTRIBUTE);
				if (null != val) {
					withVars.add(val);
				}
				proceedToEndElementNamed(LibraryElementTags.WITH_ELEMENT);
				return true;
			case LibraryElementTags.ATTRIBUTE_ELEMENT:
				parseGenericAttributeNode(e);
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
				return true;
			default:
				return false;
			}
		});

		if (!withVars.isEmpty()) {
			withList.put(e, withVars);
		}

		return e;
	}

	public Map<String, Event> getInputEvents() {
		return inputEvents;
	}

	public Map<String, Event> getOutputEvents() {
		return outputEvents;
	}

	@Override
	protected LibraryElement createRootModelElement() {
		throw new UnsupportedOperationException("Cannot parse only interface"); //$NON-NLS-1$
	}

	@Override
	protected String getStartElementName() {
		throw new UnsupportedOperationException("Cannot parse only interface"); //$NON-NLS-1$
	}

	@Override
	protected IChildHandler getBaseChildrenHandler() {
		throw new UnsupportedOperationException("Cannot parse only interface"); //$NON-NLS-1$
	}
}
