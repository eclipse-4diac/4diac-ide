/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor Gmbh, TU Wien ACIN, fortiss GmbH
 * 				  2018 Johannes Keppler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 *  Alois Zoitl - extracted this helper class from the CommonElementExporter  
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import java.util.Iterator;
import java.util.List;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.CompilableType;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AbstractTypeExporter extends CommonElementExporter {
	
	final FBType type;
	
	AbstractTypeExporter(FBType type) {
		this.type = type;
	}
	
	AbstractTypeExporter(Document dom) {
		super(dom);
		type = null;
	}

	protected FBType getType() {
		return type;
	}
	
	public static void saveType(PaletteEntry entry){
		AbstractTypeExporter exporter = null;
		
		if(entry instanceof FBTypePaletteEntry){
			exporter = new FbtExporter((FBTypePaletteEntry)entry);
		}else if (entry instanceof AdapterTypePaletteEntry){
			exporter = new AdapterExporter((AdapterTypePaletteEntry)entry);
		}else if (entry instanceof SubApplicationTypePaletteEntry){
			exporter = new SubApplicationTypeExporter((SubApplicationTypePaletteEntry)entry);
		}
		
		if(null != exporter){
			exporter.createXMLEntries();			
			exporter.writeToFile(entry.getFile());
			// "reset" the modification timestamp in the PaletteEntry
			// to avoid reload - as for this timestamp it is not necessary
			// as the data is in memory
			entry.setLastModificationTimestamp(entry.getFile().getModificationStamp());
		}
	}
	
	
	
	private void createXMLEntries() {
		Element rootElement = createRootElement(getType(), getRootTag());
		
		addCompileAbleTypeData(rootElement, getType());		
		addInterfaceList(rootElement, getType().getInterfaceList());
		createTypeSpecificXMLEntries(rootElement);
		addService(rootElement, getType());
	}
	
	protected abstract String getRootTag();

	protected abstract void createTypeSpecificXMLEntries(Element rootElement);
	
	private void addCompileAbleTypeData(final Element rootElement, final CompilableType type){
		addIdentification(rootElement, type);
		addVersionInfo(rootElement, type);
		addCompilerInfo(rootElement, type.getCompilerInfo());
	}
	
	private void addCompilerInfo(final Element rootEle, final CompilerInfo compilerInfo) {
		if (compilerInfo != null) {
			Element compilerInfoElement = createElement(LibraryElementTags.COMPILER_INFO_ELEMENT);
			if (compilerInfo.getHeader() != null && !compilerInfo.getHeader().equals("")) { //$NON-NLS-1$
				compilerInfoElement.setAttribute(LibraryElementTags.HEADER_ATTRIBUTE, compilerInfo.getHeader());
			}
			if (compilerInfo.getClassdef() != null && !compilerInfo.getClassdef().equals("")) { //$NON-NLS-1$
				compilerInfoElement.setAttribute(LibraryElementTags.CLASSDEF_ATTRIBUTE, compilerInfo.getClassdef());
			}
			compilerInfo.getCompiler().forEach(compiler -> addCompiler(compilerInfoElement, compiler));
			rootEle.appendChild(compilerInfoElement);

		}
	}
	
	/**
	 * Adds the compiler.
	 * 
	 * @param dom
	 *            the dom
	 * @param compilerInfo
	 *            the compiler info
	 * @param compiler
	 *            the compiler
	 */
	private void addCompiler(final Element compilerInfo, final org.eclipse.fordiac.ide.model.libraryElement.Compiler compiler) {
		Element compilerElement = createElement(LibraryElementTags.COMPILER_ELEMENT);
		if (compiler.getLanguage() != null) {
			compilerElement.setAttribute(LibraryElementTags.LANGUAGE_ATTRIBUTE, compiler.getLanguage()
					.getName());
		} else {
			compilerElement.setAttribute(LibraryElementTags.LANGUAGE_ATTRIBUTE, ""); //$NON-NLS-1$
		}
		if (compiler.getVendor() != null) {
			compilerElement.setAttribute(LibraryElementTags.VENDOR_ATTRIBUTE, compiler.getVendor());
		} else {
			compilerElement.setAttribute(LibraryElementTags.VENDOR_ATTRIBUTE, ""); //$NON-NLS-1$
		}
		if (compiler.getProduct() != null) {
			compilerElement.setAttribute(LibraryElementTags.PRODUCT_ATTRIBUTE, compiler.getProduct());
		} else {
			compilerElement.setAttribute(LibraryElementTags.PRODUCT_ATTRIBUTE, ""); //$NON-NLS-1$
		}
		if (compiler.getVersion() != null) {
			compilerElement.setAttribute(LibraryElementTags.VERSION_ATTRIBUTE, compiler.getVersion());
		} else {
			compilerElement.setAttribute(LibraryElementTags.VERSION_ATTRIBUTE, ""); //$NON-NLS-1$
		}

		compilerInfo.appendChild(compilerElement);
	}
	
	/**
	 * Adds the interface list.
	 * 
	 * @param rootEle
	 *            the root ele
	 * @param fb
	 *            the fb
	 */
	protected void addInterfaceList(final Element rootEle, final InterfaceList interfaceList) {
		Element interfaceListElement = createElement(getInterfaceListElementName());

		addEventList(interfaceListElement, interfaceList.getEventInputs(), getEventInputsElementName());
		addEventList(interfaceListElement, interfaceList.getEventOutputs(), getEventOutputsElementName());
		addVarList(interfaceListElement, interfaceList.getInputVars(), LibraryElementTags.INPUT_VARS_ELEMENT);
		addVarList(interfaceListElement, interfaceList.getOutputVars(), LibraryElementTags.OUTPUT_VARS_ELEMENT);
		createAdapterList(interfaceListElement, interfaceList.getPlugs(), LibraryElementTags.PLUGS_ELEMENT);
		createAdapterList(interfaceListElement, interfaceList.getSockets(), LibraryElementTags.SOCKETS_ELEMENT);
		
		rootEle.appendChild(interfaceListElement);
	}


	@SuppressWarnings("static-method")
	protected String getInterfaceListElementName() {
		return LibraryElementTags.INTERFACE_LIST_ELEMENT;
	}

	private void createAdapterList(final Element parentElement, final List<AdapterDeclaration> adapterList, final String elementName) {
		if (!adapterList.isEmpty()) {
			Element adpaterListElement = createElement(elementName);
			adapterList.forEach(adapter -> addAdapterDeclaration(adpaterListElement, adapter));
			parentElement.appendChild(adpaterListElement);
		}
	}

	private void addAdapterDeclaration(final Element parentElement, final AdapterDeclaration adapterDecl) {
		Element adapterElement = createElement(LibraryElementTags.ADAPTER_DECLARATION_ELEMENT);

		setNameTypeCommentAttribute(adapterElement, adapterDecl, adapterDecl.getType());

		if (null != adapterDecl.getAdapterFB()) {
			exportXandY(adapterDecl.getAdapterFB(), adapterElement);
		}
		parentElement.appendChild(adapterElement);
	}

	/**
	 * Adds a var list (i.e., input or output data) to the dom. If the given var list is empty no element will be created. 
	 * 
	 * @param parentElement the parent element to insert the varlist
	 * @param varList	   the list of vars to create the entries for	
	 * @param elementName the name of the xml element holding the event list
	 */
	protected void addVarList(final Element parentElement, final List<VarDeclaration> varList, final String elementName) {		
		if(!varList.isEmpty()) {		
			Element varListElement = createElement(elementName);
			varList.forEach( varDecl -> {
				if (!(varDecl instanceof AdapterDeclaration)) {
					addVariable(varListElement, varDecl);
				}
			});
			parentElement.appendChild(varListElement);
		}
	}

	/**
	 * Adds the variable.
	 * 
	 * @param parentElement
	 *            the parent element
	 * @param varDecl
	 *            the var decl
	 */
	protected void addVariable(final Element parentElement, final VarDeclaration varDecl) {
		Element variableElement = createElement(LibraryElementTags.VAR_DECLARATION_ELEMENT);
		setNameTypeCommentAttribute(variableElement, varDecl, varDecl.getType());

		if (varDecl.isArray()) {
			variableElement.setAttribute(LibraryElementTags.ARRAYSIZE_ATTRIBUTE, Integer.toString(varDecl.getArraySize()));
		}
		if (varDecl.getValue() != null
				&& varDecl.getValue().getValue() != null) {
			variableElement.setAttribute(LibraryElementTags.INITIALVALUE_ATTRIBUTE, varDecl.getValue().getValue());
		}
		parentElement.appendChild(variableElement);
	}

	/**
	 * Adds the an event list (i.e., input or output) to the dom. If the given event list is empty no element will be created.
	 * 
	 * @param parentElement the parent element to insert the event list
	 * @param eventList		the list of events to create the entry for
	 * @param elementName	the name of the xml element holding the event list
	 */
	private void addEventList(final Element parentElement, final List<Event> eventList, final String elementName) {
		if(!eventList.isEmpty()) {
			Element eventListElement = createElement(elementName);
			eventList.forEach(event -> addEvent(eventListElement, event));
			parentElement.appendChild(eventListElement);
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
	 * @param parentElement
	 *            the parent element
	 * @param event
	 *            the event
	 */
	private void addEvent(final Element parentElement, final Event event) {
		Element eventElem = createElement(getEventElementName());
		
		setNameAttribute(eventElem, event.getName());
		eventElem.setAttribute(LibraryElementTags.TYPE_ATTRIBUTE, "Event"); //$NON-NLS-1$
		setCommentAttribute(eventElem, event);
		
		addWith(eventElem, event);
		parentElement.appendChild(eventElem);
	}


	@SuppressWarnings("static-method")
	protected String getEventElementName() {
		return LibraryElementTags.EVENT_ELEMENT;
	}

	/**
	 * Adds the with.
	 * 
	 * @param parentElement
	 *            the parent element
	 * @param event
	 *            the event
	 */
	private void addWith(final Element parentElement, final Event event) {
		for (With with : event.getWith()) {
			Element withElement = createElement(LibraryElementTags.WITH_ELEMENT);

			VarDeclaration varDecl = with.getVariables();

			if (varDecl.getName() != null) {
				withElement.setAttribute(LibraryElementTags.VAR_ATTRIBUTE, varDecl.getName());
			} else {
				withElement.setAttribute(LibraryElementTags.VAR_ATTRIBUTE, ""); //$NON-NLS-1$
			}
			parentElement.appendChild(withElement);
		}
	}
	

	/**
	 * Adds the service.
	 * 
	 * @param rootEle
	 *            the root ele
	 * @param fb
	 *            the fb
	 */
	private void addService(final Element rootEle,final FBType sfb) {
		
		if (null != sfb.getService() && sfb.getService().getRightInterface() != null && sfb.getService().getLeftInterface() != null) {

			Element serviceElement = createElement(LibraryElementTags.SERVICE_ELEMENT);
			serviceElement.setAttribute(LibraryElementTags.RIGHT_INTERFACE_ATTRIBUTE, sfb
					.getService().getRightInterface().getName());
			serviceElement.setAttribute(LibraryElementTags.LEFT_INTERFACE_ATTRIBUTE, sfb.getService().getLeftInterface()
					.getName());
			
			setCommentAttribute(serviceElement, sfb);
			
			addServiceSequences(serviceElement, sfb.getService().getServiceSequence());

			rootEle.appendChild(serviceElement);
		}
	}

	/**
	 * Adds the service sequences.
	 * 
	 * @param serviceElement
	 *            the service element
	 * @param sequences
	 *            the sequences
	 */
	private void addServiceSequences(final Element serviceElement, final List<ServiceSequence> sequences) {
		for (ServiceSequence seq : sequences) {
			Element seqElement = createElement(LibraryElementTags.SERVICE_SEQUENCE_ELEMENT);
			
			setNameAttribute(seqElement, seq.getName());
			setCommentAttribute(seqElement, seq);
			addServiceTransactions(seqElement, seq.getServiceTransaction());
			serviceElement.appendChild(seqElement);
		}
	}

	/**
	 * Adds the service transactions.
	 * 
	 * @param seqElement
	 *            the seq element
	 * @param transactions
	 *            the transactions
	 */
	private void addServiceTransactions(final Element seqElement,final List<ServiceTransaction> transactions) {
		for (Iterator<ServiceTransaction> iter = transactions.iterator(); iter
				.hasNext();) {
			Element serviceTransaction = createElement(LibraryElementTags.SERVICE_TRANSACTION_ELEMENT);
			ServiceTransaction transaction = iter.next();

			if (transaction.getInputPrimitive() != null) {
				addInputPrimitive(serviceTransaction, transaction);
			}
			if (!transaction.getOutputPrimitive().isEmpty()) {
				addOutputPrimitives(serviceTransaction, transaction);
			}
			seqElement.appendChild(serviceTransaction);

		}
	}

	/**
	 * Adds the input primitive.
	 * 
	 * @param serviceTransaction
	 *            the service transaction
	 * @param transaction
	 *            the transaction
	 */
	private void addInputPrimitive(final Element serviceTransaction,
			final ServiceTransaction transaction) {
		Element inputPrimitive = createElement(LibraryElementTags.INPUT_PRIMITIVE_ELEMENT);
		if (transaction.getInputPrimitive().getInterface() != null
				&& transaction.getInputPrimitive().getInterface().getName() != null) {
			inputPrimitive.setAttribute(LibraryElementTags.INTERFACE_ATTRIBUTE, transaction
					.getInputPrimitive().getInterface().getName());
		} else {
			inputPrimitive.setAttribute(LibraryElementTags.INTERFACE_ATTRIBUTE, ""); //$NON-NLS-1$
		}
		if (transaction.getInputPrimitive().getEvent() != null) {
			inputPrimitive.setAttribute(LibraryElementTags.EVENT_ELEMENT, transaction
					.getInputPrimitive().getEvent());
		} else {
			inputPrimitive.setAttribute(LibraryElementTags.EVENT_ELEMENT, ""); //$NON-NLS-1$
		}
		if (transaction.getInputPrimitive().getParameters() != null
				&& !transaction.getInputPrimitive().getParameters().equals(" ")) { //$NON-NLS-1$
			inputPrimitive.setAttribute(LibraryElementTags.PARAMETERS_ATTRIBUTE, transaction
					.getInputPrimitive().getParameters());
		}
		serviceTransaction.appendChild(inputPrimitive);

	}

	/**
	 * Adds the output primitives.
	 * 
	 * @param serviceTransaction
	 *            the service transaction
	 * @param transaction
	 *            the transaction
	 */
	private void addOutputPrimitives(final Element serviceTransaction, final ServiceTransaction transaction) {
		for (Iterator<OutputPrimitive> iter = transaction.getOutputPrimitive().iterator(); iter.hasNext();) {
			Element outputPrimitive = createElement(LibraryElementTags.OUTPUT_PRIMITIVE_ELEMENT);
			OutputPrimitive primitive = iter.next();
			outputPrimitive.setAttribute(LibraryElementTags.INTERFACE_ATTRIBUTE, primitive.getInterface().getName());

			if (primitive.getEvent() != null) {
				outputPrimitive.setAttribute(LibraryElementTags.EVENT_ELEMENT, primitive.getEvent());
			} else {
				outputPrimitive.setAttribute(LibraryElementTags.EVENT_ELEMENT, ""); //$NON-NLS-1$
			}

			if (primitive.getParameters() != null && !primitive.getParameters().equals(" ")) { //$NON-NLS-1$
				outputPrimitive.setAttribute(LibraryElementTags.PARAMETERS_ATTRIBUTE, primitive.getParameters());
			}
			serviceTransaction.appendChild(outputPrimitive);
		}
	}

}
