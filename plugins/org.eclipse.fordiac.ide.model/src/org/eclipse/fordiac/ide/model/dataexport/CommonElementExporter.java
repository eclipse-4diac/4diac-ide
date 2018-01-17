/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor Gmbh, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.fordiac.ide.model.libraryElement.CompilableType;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class CommonElementExporter {
	
	private static FbtExporter fbTypeExporter = new FbtExporter();
	
	private static AdapterExporter adapterTypeExporter = new AdapterExporter();
	
	private static SubApplicationTypeExporter subAppTypeExporter = new SubApplicationTypeExporter();
	
	
	public static void saveType(PaletteEntry entry){
		CommonElementExporter exporter = null;
		
		if(entry instanceof FBTypePaletteEntry){
			exporter = fbTypeExporter;
		}else if (entry instanceof AdapterTypePaletteEntry){
			exporter = adapterTypeExporter;
		}else if (entry instanceof SubApplicationTypePaletteEntry){
			exporter = subAppTypeExporter;
		}
		
		if(null != exporter){
			exporter.performSave(entry);
			// "reset" the modification timestamp in the PaletteEntry
			// to avoid reload - as for this timestamp it is not necessary
			// as the data is in memory
			entry.setLastModificationTimestamp(entry.getFile().getModificationStamp());
		}
	}
	
	
	public void performSave(PaletteEntry entry){
			//final LibraryElement libraryElement, final IFile iFile) {
		try {
			// write the dom to the file
			Document dom = getDocument(getType(entry));
			Source source = new DOMSource(dom); // Document to be transformed

			writeToFile(source, entry.getFile());
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}
	
	public static void addColorAttributeElement(final Document dom, final Element parent, final ColorizableElement colElement){
		String colorValue = colElement.getColor().getRed() + "," + colElement.getColor().getGreen() + "," + colElement.getColor().getBlue();  //$NON-NLS-1$ //$NON-NLS-2$
		Element colorAttribute = createAttributeElement(dom, LibraryElementTags.COLOR, "STRING", colorValue, "color");		
		parent.appendChild(colorAttribute);
	}

	public static Element createAttributeElement(final Document dom, String name, String type, String value, String comment){
		Element attributeElement = dom.createElement(LibraryElementTags.ATTRIBUTE_ELEMENT);
		attributeElement.setAttribute(LibraryElementTags.NAME_ATTRIBUTE, name);
		attributeElement.setAttribute(LibraryElementTags.TYPE_ATTRIBUTE, type);
		attributeElement.setAttribute(LibraryElementTags.VALUE_ATTRIBUTE, value);
		attributeElement.setAttribute(LibraryElementTags.COMMENT_ATTRIBUTE, comment);
		return attributeElement;
	}

	private Transformer createXMLTransformer()
			throws TransformerFactoryConfigurationError, TransformerConfigurationException {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		tFactory.setAttribute("indent-number", new Integer(2)); //$NON-NLS-1$
		Transformer transformer = tFactory.newTransformer();

		transformer.setOutputProperty(
				javax.xml.transform.OutputKeys.DOCTYPE_SYSTEM,
				"http://www.holobloc.com/xml/LibraryElement.dtd"); //$NON-NLS-1$
		transformer.setOutputProperty(
				javax.xml.transform.OutputKeys.ENCODING, "UTF-8"); //$NON-NLS-1$
		transformer.setOutputProperty(
				javax.xml.transform.OutputKeys.VERSION, "1.0"); //$NON-NLS-1$
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", "2"); //$NON-NLS-1$ //$NON-NLS-2$
		transformer.setOutputProperty(
				javax.xml.transform.OutputKeys.INDENT, "yes"); //$NON-NLS-1$
		return transformer;
	}
	
	
	protected abstract FBType getType(PaletteEntry entry);


	/**
	 * Gets the document.
	 * 
	 * @param libraryElement
	 *            the fb
	 * 
	 * @return the document
	 */
	public Document getDocument(final FBType fbType) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;

		try {
			db = dbf.newDocumentBuilder();
			Document dom = db.newDocument();

			addType(dom, fbType);
			return dom;
		} catch (ParserConfigurationException e) {
			Activator.getDefault().logError(e.getMessage(), e);
			return null;
		}
	}
	
	protected abstract void addType(final Document dom, final FBType fbType);
	
	protected Element createRootElement(final Document dom, final INamedElement namedElement, String rootElemName) {
		Element rootElement = dom.createElement(rootElemName);
		
		setNameAttribute(rootElement, namedElement.getName());
		setCommentAttribute(rootElement, namedElement);
		
		dom.appendChild(rootElement);
		return rootElement;
	}

	
	protected void addCompileAbleTypeData(final Document dom, final Element rootElement, final CompilableType type){
		addIdentification(dom, rootElement, type);
		addVersionInfo(dom, rootElement, type);
		addCompilerInfo(dom, rootElement, type.getCompilerInfo());
	}
	
	public void writeToFile(Source source,
			IFile iFile) throws UnsupportedEncodingException,
			FileNotFoundException, TransformerException, IOException {
		new StreamResult(new OutputStreamWriter(new ByteArrayOutputStream(),
				"UTF-8")); //$NON-NLS-1$
		StringWriter stringWriter = new StringWriter();
		Result result = new StreamResult(stringWriter);
		Transformer transformer = createXMLTransformer();
		transformer.transform(source, result);
		try {
			if (iFile.exists()) {				
				iFile.setContents(new ByteArrayInputStream(stringWriter.toString().getBytes("UTF-8")), //$NON-NLS-1$ 
						IFile.KEEP_HISTORY | IFile.FORCE, null);
			} else {
				IFolder folder = iFile.getProject().getFolder(iFile.getProjectRelativePath().removeLastSegments(1));
				if (!folder.exists()) {
					folder.create(true, true, null);
					folder.refreshLocal(IFolder.DEPTH_ZERO, null);
				}
				iFile.create(new ByteArrayInputStream(result.toString().getBytes("UTF-8")), //$NON-NLS-1$ 
						IFile.KEEP_HISTORY | IFile.FORCE, null);
			}

			iFile.getParent().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			
		} catch (CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}
	
	/*
	 * <!ELEMENT Identification EMPTY>
	 * 
	 * <!ATTLIST Identification Standard CDATA #IMPLIED Classification CDATA
	 * #IMPLIED ApplicationDomain CDATA #IMPLIED Function CDATA #IMPLIED Type
	 * CDATA #IMPLIED Description CDATA #IMPLIED >
	 */
	/**
	 * Adds the identification.
	 * 
	 * @param dom
	 *            the dom
	 * @param parentElement
	 *            the parent element
	 * @param libraryelement
	 *            the libraryelement
	 */
	public static void addIdentification(final Document dom,
			final Element parentElement, final LibraryElement libraryelement) {
		if (libraryelement.getIdentification() != null) {
			Element identification = dom.createElement(LibraryElementTags.IDENTIFICATION_ELEMENT);
			Identification ident = libraryelement.getIdentification();
			if (ident.getStandard() != null && !ident.getStandard().equals("")) { //$NON-NLS-1$
				identification.setAttribute(LibraryElementTags.STANDARD_ATTRIBUTE, ident.getStandard());
			}
			if (ident.getClassification() != null
					&& !ident.getClassification().equals("")) { //$NON-NLS-1$
				identification.setAttribute(LibraryElementTags.CLASSIFICATION_ATTRIBUTE,
						ident.getClassification());
			}
			if (ident.getApplicationDomain() != null
					&& !ident.getApplicationDomain().equals("")) { //$NON-NLS-1$
				identification.setAttribute(LibraryElementTags.APPLICATION_DOMAIN_ATTRIBUTE,
						ident.getApplicationDomain());
			}
			if (ident.getFunction() != null && !ident.getFunction().equals("")) { //$NON-NLS-1$
				identification.setAttribute(LibraryElementTags.FUNCTION_ELEMENT, ident.getFunction());
			}
			if (ident.getType() != null && !ident.getType().equals("")) { //$NON-NLS-1$
				identification.setAttribute(LibraryElementTags.TYPE_ATTRIBUTE, ident.getType());
			}
			if (ident.getDescription() != null
					&& !ident.getDescription().equals("")) { //$NON-NLS-1$
				identification.setAttribute(LibraryElementTags.DESCRIPTION_ELEMENT,
						ident.getDescription());
			}

			parentElement.appendChild(identification);
		}
	}

	/*
	 * <!ELEMENT VersionInfo EMPTY>
	 * 
	 * <!ATTLIST VersionInfo Organization CDATA #REQUIRED Version CDATA
	 * #REQUIRED Author CDATA #REQUIRED Date CDATA #REQUIRED Remarks CDATA
	 * #IMPLIED >
	 */
	/**
	 * Adds the version info.
	 * 
	 * @param dom
	 *            the dom
	 * @param rootEle
	 *            the root ele
	 * @param libraryelement
	 *            the libraryelement
	 */
	public static void addVersionInfo(final Document dom,
			final Element rootEle, final LibraryElement libraryelement) {
		if (libraryelement.getVersionInfo().size() > 0) {
			for (Iterator<VersionInfo> iter = libraryelement.getVersionInfo()
					.iterator(); iter.hasNext();) {
				VersionInfo info = iter.next();
				Element versionInfo = dom.createElement(LibraryElementTags.VERSION_INFO_ELEMENT);
				if (info.getOrganization() != null
						&& !info.getOrganization().equals("")) { //$NON-NLS-1$
					versionInfo.setAttribute(LibraryElementTags.ORGANIZATION_ATTRIBUTE,
							info.getOrganization());
				}
				if (info.getVersion() != null && !info.getVersion().equals("")) { //$NON-NLS-1$
					versionInfo.setAttribute(LibraryElementTags.VERSION_ATTRIBUTE, info.getVersion());
				}
				if (info.getAuthor() != null && !info.getAuthor().equals("")) { //$NON-NLS-1$
					versionInfo.setAttribute(LibraryElementTags.AUTHOR_ATTRIBUTE, info.getAuthor());
				}
				if (info.getDate() != null && !info.getDate().equals("")) { //$NON-NLS-1$
					// SimpleDateFormat dateFormat = new SimpleDateFormat(
					// "yyyy-MM-dd");
					// versionInfo.setAttribute("Date", dateFormat.format(info
					// .getDate()));
					versionInfo.setAttribute(LibraryElementTags.DATE_ATTRIBUTE, info.getDate());

				}
				if (info.getRemarks() != null && !info.getRemarks().equals("")) { //$NON-NLS-1$
					versionInfo.setAttribute(LibraryElementTags.REMARKS_ATTRIBUTE, info.getRemarks());
				}

				rootEle.appendChild(versionInfo);
			}

		}
	}

	public static void addCompilerInfo(final Document dom,
			final Element rootEle, final CompilerInfo compilerInfo) {
		if (compilerInfo != null) {
			Element compilerInfoElement = dom.createElement(LibraryElementTags.COMPILER_INFO_ELEMENT);
			if (compilerInfo.getHeader() != null
					&& !compilerInfo.getHeader().equals("")) { //$NON-NLS-1$
				compilerInfoElement.setAttribute(LibraryElementTags.HEADER_ATTRIBUTE, compilerInfo
						.getHeader().toString());
			}
			if (compilerInfo.getClassdef() != null
					&& !compilerInfo.getClassdef().equals("")) { //$NON-NLS-1$
				compilerInfoElement.setAttribute(LibraryElementTags.CLASSDEF_ATTRIBUTE,
						compilerInfo.getClassdef());
			}

			for (Iterator<org.eclipse.fordiac.ide.model.libraryElement.Compiler> iter = compilerInfo
					.getCompiler().iterator(); iter.hasNext();) {
				org.eclipse.fordiac.ide.model.libraryElement.Compiler compiler = iter
						.next();
				addCompiler(dom, compilerInfoElement, compiler);
			}
			rootEle.appendChild(compilerInfoElement);

		}
	}

	/**
	 * Adds the interface list.
	 * 
	 * @param dom
	 *            the dom
	 * @param rootEle
	 *            the root ele
	 * @param fb
	 *            the fb
	 */
	protected void addInterfaceList(final Document dom,
			final Element rootEle, final InterfaceList interfaceList) {
		Element interfaceListElement = dom.createElement(getInterfaceListElementName());

		addEvents(dom, interfaceListElement, interfaceList);
		addVars(dom, interfaceListElement, interfaceList);

		createAdapterList(dom, interfaceListElement, interfaceList.getPlugs(), LibraryElementTags.PLUGS_ELEMENT);
		createAdapterList(dom, interfaceListElement, interfaceList.getSockets(), LibraryElementTags.SOCKETS_ELEMENT);
		
		rootEle.appendChild(interfaceListElement);
	}


	protected String getInterfaceListElementName() {
		return LibraryElementTags.INTERFACE_LIST_ELEMENT;
	}

	private void createAdapterList(final Document dom,
			final Element parentElement, final List<AdapterDeclaration> adapterList, final String elementName) {
		if (adapterList.size() > 0) {
			Element adpaterListElement = dom.createElement(elementName);
			for (AdapterDeclaration adpDecl : adapterList) {			
				addAdapterDeclaration(dom, adpaterListElement, adpDecl);
			}
			parentElement.appendChild(adpaterListElement);
		}
	}

	private void addAdapterDeclaration(final Document dom,
			final Element parentElement, final AdapterDeclaration adapterDecl) {
		Element adatperElement = dom.createElement(LibraryElementTags.ADAPTER_DECLARATION_ELEMENT);

		setNameTypeCommentAttribute(adatperElement, adapterDecl, adapterDecl.getType());

		if (null != adapterDecl.getAdapterFB()) {
			exportXandY(adapterDecl.getAdapterFB(), adatperElement);
		}
		parentElement.appendChild(adatperElement);
	}

	/*
	 * <!ELEMENT Compiler EMPTY>
	 * 
	 * <!ATTLIST Compiler Language (Java | Cpp | C | Other) #REQUIRED Vendor
	 * CDATA #REQUIRED Product CDATA #REQUIRED Version CDATA #REQUIRED >
	 */
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
	private static void addCompiler(final Document dom,
			final Element compilerInfo,
			final org.eclipse.fordiac.ide.model.libraryElement.Compiler compiler) {
		Element compilerElement = dom.createElement(LibraryElementTags.COMPILER_ELEMENT);
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
	 * Adds the vars.
	 * 
	 * @param dom
	 *            the dom
	 * @param parentElement
	 *            the parent element
	 * @param fb
	 *            the fb
	 */
	private void addVars(final Document dom,
			final Element parentElement, final InterfaceList interfaceList) {
		Element inputVars = dom.createElement(LibraryElementTags.INPUT_VARS_ELEMENT);
		Element outputVars = dom.createElement(LibraryElementTags.OUTPUT_VARS_ELEMENT);

		for (Iterator<VarDeclaration> iter = interfaceList.getInputVars()
				.iterator(); iter.hasNext();) {
			VarDeclaration varDecl = iter.next();
			if (!(varDecl instanceof AdapterDeclaration)) {
				addVariable(dom, inputVars, varDecl);
			}
		}
		for (Iterator<VarDeclaration> iter = interfaceList.getOutputVars()
				.iterator(); iter.hasNext();) {
			VarDeclaration varDecl = iter.next();
			if (!(varDecl instanceof AdapterDeclaration)) {
				addVariable(dom, outputVars, varDecl);
			}
		}
		parentElement.appendChild(inputVars);
		parentElement.appendChild(outputVars);

	}

	/*
	 * <!ELEMENT VarDeclaration EMPTY>
	 * 
	 * <!ATTLIST VarDeclaration Name CDATA #REQUIRED Type CDATA #REQUIRED
	 * ArraySize CDATA #IMPLIED InitialValue CDATA #IMPLIED Comment CDATA
	 * #IMPLIED >
	 */
	/**
	 * Adds the variable.
	 * 
	 * @param dom
	 *            the dom
	 * @param parentElement
	 *            the parent element
	 * @param varDecl
	 *            the var decl
	 */
	public void addVariable(final Document dom,
			final Element parentElement, final VarDeclaration varDecl) {
		Element variableElement = dom.createElement(LibraryElementTags.VAR_DECLARATION_ELEMENT);
		setNameTypeCommentAttribute(variableElement, varDecl, varDecl.getType());

		if (varDecl.isArray()) {
			variableElement.setAttribute(LibraryElementTags.ARRAYSIZE_ATTRIBUTE,
					new Integer(varDecl.getArraySize()).toString());
		}
		if (varDecl.getVarInitialization() != null
				&& varDecl.getVarInitialization().getInitialValue() != null) {
			variableElement.setAttribute(LibraryElementTags.INITIALVALUE_ATTRIBUTE, varDecl
					.getVarInitialization().getInitialValue());
		}

		parentElement.appendChild(variableElement);

	}

	/**
	 * Adds the events.
	 * 
	 * @param dom
	 *            the dom
	 * @param parentElement
	 *            the parent element
	 * @param interfaceList
	 *            the interface list
	 */
	private void addEvents(final Document dom,
			final Element parentElement, final InterfaceList interfaceList) {
		Element eventInputs = dom.createElement(getEventInputsElementName());
		Element eventOutputs = dom.createElement(getEventOutputsElementName());

		for (Iterator<Event> iter = interfaceList.getEventInputs().iterator(); iter
				.hasNext();) {
			Event event = iter.next();
			addEvent(dom, eventInputs, event);
		}
		for (Iterator<Event> iter = interfaceList.getEventOutputs().iterator(); iter
				.hasNext();) {
			Event event = iter.next();
			addEvent(dom, eventOutputs, event);
		}

		parentElement.appendChild(eventInputs);
		parentElement.appendChild(eventOutputs);
	}


	protected String getEventOutputsElementName() {
		return LibraryElementTags.EVENT_OUTPUTS;
	}


	protected String getEventInputsElementName() {
		return LibraryElementTags.EVENT_INPUTS_ELEMENT;
	}

	/*
	 * <!ELEMENT Event (With)>
	 * 
	 * <!ATTLIST Event Name CDATA #REQUIRED Type CDATA #IMPLIED Comment CDATA
	 * #IMPLIED >
	 */
	/**
	 * Adds the event.
	 * 
	 * @param dom
	 *            the dom
	 * @param parentElement
	 *            the parent element
	 * @param event
	 *            the event
	 */
	private void addEvent(final Document dom,
			final Element parentElement, final Event event) {
		Element eventElem = dom.createElement(getEventElementName());
		
		setNameAttribute(eventElem, event.getName());
		
		// if (event.getType() != null && event.getType() != null) {
		eventElem.setAttribute(LibraryElementTags.TYPE_ATTRIBUTE, "Event"); //$NON-NLS-1$
		// }
		setCommentAttribute(eventElem, event);
		
		addWith(dom, eventElem, event);
		parentElement.appendChild(eventElem);
	}


	protected String getEventElementName() {
		return LibraryElementTags.EVENT_ELEMENT;
	}

	/**
	 * Adds the with.
	 * 
	 * @param dom
	 *            the dom
	 * @param parentElement
	 *            the parent element
	 * @param event
	 *            the event
	 */
	private static void addWith(final Document dom,
			final Element parentElement, final Event event) {
		for (Iterator<With> iterator = event.getWith().iterator(); iterator
				.hasNext();) {
			Element with = dom.createElement(LibraryElementTags.WITH_ELEMENT);
			With withElement = iterator.next();

			VarDeclaration varDecl = withElement.getVariables();

			if (varDecl.getName() != null) {
				with.setAttribute(LibraryElementTags.VAR_ATTRIBUTE, varDecl.getName());
			} else {
				with.setAttribute(LibraryElementTags.VAR_ATTRIBUTE, ""); //$NON-NLS-1$
			}
			parentElement.appendChild(with);
		}
	}
	
	protected static void setCommentAttribute(Element element, INamedElement namedElement) {
		if (namedElement.getComment() != null) {
			element.setAttribute(LibraryElementTags.COMMENT_ATTRIBUTE, namedElement.getComment());
		}
	}
	
	static void setNameAndCommentAttribute(Element element, INamedElement namedElement){
		setNameAttribute(element, namedElement.getName());
		setCommentAttribute(element, namedElement);
	}
	
	static void setNameTypeCommentAttribute(Element element, INamedElement namedElement, INamedElement type){
		setNameAttribute(element, namedElement.getName());
		setTypeAttribute(element, type);
		setCommentAttribute(element, namedElement);
	}

	static void setTypeAttribute(Element element, INamedElement type) {
		setTypeAttribute(element, ((null != type) && (null != type.getName())) ? type.getName() : ""); //$NON-NLS-1$
	}

	protected static void setTypeAttribute(Element element, String type) {
		element.setAttribute(LibraryElementTags.TYPE_ATTRIBUTE, (null != type) ? type : ""); //$NON-NLS-1$
	}
	
	static void setNameAttribute(Element element, String name) {
		element.setAttribute(LibraryElementTags.NAME_ATTRIBUTE, (null != name) ? name : ""); //$NON-NLS-1$
	}

	static void addParamsConfig(Document dom, Element fbElement,
			EList<VarDeclaration> inputVars) {
		
		for (VarDeclaration var : inputVars) {
			if (var.getValue() != null
					&& var.getValue().getValue() != null
					&& !var.getValue().getValue().equals("")) { //$NON-NLS-1$
				Element parameterElement = dom.createElement(LibraryElementTags.PARAMETER_ELEMENT);
				setNameAttribute(parameterElement, var.getName());
				parameterElement.setAttribute(LibraryElementTags.VALUE_ATTRIBUTE, var.getValue()
						.getValue());
				fbElement.appendChild(parameterElement);
			}
		}
	}


	static void exportXandY(PositionableElement fb, Element fbElement) {
		setXYAttributes(fbElement, fb.getX(), fb.getY());
	}
	
	

	static void setXYAttributes(Element element, int x, int y) {
		element.setAttribute(LibraryElementTags.X_ATTRIBUTE, reConvertCoordinate(x).toString());
		element.setAttribute(LibraryElementTags.Y_ATTRIBUTE, reConvertCoordinate(y).toString());
	}


	

	/**
	 * Adds the service.
	 * 
	 * @param dom
	 *            the dom
	 * @param rootEle
	 *            the root ele
	 * @param fb
	 *            the fb
	 */
	public void addService(final Document dom, final Element rootEle,
			final FBType sfb) {
		
		if (null != sfb.getService() && sfb.getService().getRightInterface() != null && sfb.getService().getLeftInterface() != null) {

			Element serviceElement = dom.createElement(LibraryElementTags.SERVICE_ELEMENT);
			serviceElement.setAttribute(LibraryElementTags.RIGHT_INTERFACE_ATTRIBUTE, sfb
					.getService().getRightInterface().getName());
			serviceElement.setAttribute(LibraryElementTags.LEFT_INTERFACE_ATTRIBUTE, sfb.getService().getLeftInterface()
					.getName());
			
			setCommentAttribute(serviceElement, sfb);
			
			addServiceSequences(dom, serviceElement, sfb.getService().getServiceSequence());

			rootEle.appendChild(serviceElement);
		}
	}

	/**
	 * Adds the service sequences.
	 * 
	 * @param dom
	 *            the dom
	 * @param serviceElement
	 *            the service element
	 * @param sequences
	 *            the sequences
	 */
	private void addServiceSequences(final Document dom,
			final Element serviceElement, final List<ServiceSequence> sequences) {
		for (Iterator<ServiceSequence> iter = sequences.iterator(); iter
				.hasNext();) {
			ServiceSequence seq = iter.next();
			Element seqElement = dom.createElement(LibraryElementTags.SERVICE_SEQUENCE_ELEMENT);
			
			setNameAttribute(seqElement, seq.getName());
			
			setCommentAttribute(seqElement, seq);

			addServiceTransactions(dom, seqElement, seq.getServiceTransaction());

			serviceElement.appendChild(seqElement);
		}
	}

	/**
	 * Adds the service transactions.
	 * 
	 * @param dom
	 *            the dom
	 * @param seqElement
	 *            the seq element
	 * @param transactions
	 *            the transactions
	 */
	private static void addServiceTransactions(final Document dom,
			final Element seqElement,
			final List<ServiceTransaction> transactions) {
		for (Iterator<ServiceTransaction> iter = transactions.iterator(); iter
				.hasNext();) {
			Element serviceTransaction = dom
					.createElement(LibraryElementTags.SERVICE_TRANSACTION_ELEMENT);
			ServiceTransaction transaction = iter.next();

			if (transaction.getInputPrimitive() != null) {
				addInputPrimitive(dom, serviceTransaction, transaction);
			}
			if (transaction.getOutputPrimitive().size() > 0) {
				addOutputPrimitives(dom, serviceTransaction, transaction);
			}
			seqElement.appendChild(serviceTransaction);

		}
	}

	/**
	 * Adds the input primitive.
	 * 
	 * @param dom
	 *            the dom
	 * @param serviceTransaction
	 *            the service transaction
	 * @param transaction
	 *            the transaction
	 */
	private static void addInputPrimitive(final Document dom,
			final Element serviceTransaction,
			final ServiceTransaction transaction) {
		Element inputPrimitive = dom.createElement(LibraryElementTags.INPUT_PRIMITIVE_ELEMENT);
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
	 * @param dom
	 *            the dom
	 * @param serviceTransaction
	 *            the service transaction
	 * @param transaction
	 *            the transaction
	 */
	private static void addOutputPrimitives(final Document dom, final Element serviceTransaction, final ServiceTransaction transaction) {
		for (Iterator<OutputPrimitive> iter = transaction.getOutputPrimitive().iterator(); iter.hasNext();) {
			Element outputPrimitive = dom.createElement(LibraryElementTags.OUTPUT_PRIMITIVE_ELEMENT);
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

	/**
	 * Convert coordinate.
	 * 
	 * @param value
	 *            the value
	 * 
	 * @return the double
	 * @since 0.1
	 */
	public static Double reConvertCoordinate(final int value) {
		double lineHeight = 20;
		return (value * 100.0 / lineHeight);
	}

	

}
