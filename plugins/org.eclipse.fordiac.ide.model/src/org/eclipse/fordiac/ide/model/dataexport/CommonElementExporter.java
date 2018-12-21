/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor Gmbh, TU Wien ACIN, fortiss GmbH
 * 				 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 *  Alois Zoitl - Refactored class hierarchy of xml exporters  
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

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
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

abstract class CommonElementExporter {
	
	private final Document dom;
	
	protected CommonElementExporter(){
		dom = createDomElement();
	}
	
	protected CommonElementExporter(Document dom) {
		this.dom = dom;
	}
	
	protected Document getDom() {
		return dom;
	}
	
	protected Element createElement(String name) {
		return getDom().createElement(name);
	}

	private static Document createDomElement() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;

		try {
			db = dbf.newDocumentBuilder();
			return db.newDocument();
		} catch (ParserConfigurationException e) {
			Activator.getDefault().logError(e.getMessage(), e);
			return null;
		}
	}
	
	
	protected void addColorAttributeElement(final Element parent, final ColorizableElement colElement){
		String colorValue = colElement.getColor().getRed() + "," + colElement.getColor().getGreen() + "," + colElement.getColor().getBlue();  //$NON-NLS-1$ //$NON-NLS-2$
		Element colorAttribute = createAttributeElement(LibraryElementTags.COLOR, "STRING", colorValue, "color");		 //$NON-NLS-1$ //$NON-NLS-2$
		parent.appendChild(colorAttribute);
	}

	protected Element createAttributeElement(String name, String type, String value, String comment){
		Element attributeElement = createElement(LibraryElementTags.ATTRIBUTE_ELEMENT);
		attributeElement.setAttribute(LibraryElementTags.NAME_ATTRIBUTE, name);
		attributeElement.setAttribute(LibraryElementTags.TYPE_ATTRIBUTE, type);
		attributeElement.setAttribute(LibraryElementTags.VALUE_ATTRIBUTE, value);
		attributeElement.setAttribute(LibraryElementTags.COMMENT_ATTRIBUTE, comment);
		return attributeElement;
	}

	protected static Transformer createXMLTransformer()
			throws TransformerFactoryConfigurationError, TransformerConfigurationException {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		tFactory.setAttribute("indent-number", Integer.valueOf(2)); //$NON-NLS-1$
		Transformer transformer = tFactory.newTransformer();
		transformer.setOutputProperty(
				javax.xml.transform.OutputKeys.DOCTYPE_SYSTEM,"http://www.holobloc.com/xml/LibraryElement.dtd"); //$NON-NLS-1$
		transformer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, "UTF-8"); //$NON-NLS-1$
		transformer.setOutputProperty(javax.xml.transform.OutputKeys.VERSION, "1.0"); //$NON-NLS-1$
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2"); //$NON-NLS-1$ //$NON-NLS-2$
		transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes"); //$NON-NLS-1$
		transformer.setOutputProperty(javax.xml.transform.OutputKeys.STANDALONE, "no"); //$NON-NLS-1$
		transformer.setOutputProperty(javax.xml.transform.OutputKeys.METHOD, "xml"); //$NON-NLS-1$
		return transformer;
	}
	
	protected Element createRootElement(final INamedElement namedElement, String rootElemName) {
		Element rootElement = createElement(rootElemName);
		setNameAndCommentAttribute(rootElement, namedElement);
		dom.appendChild(rootElement);
		return rootElement;
	}

	
	protected void writeToFile(IFile iFile)  {
		try {
			StringWriter stringWriter = new StringWriter();
			Result result = new StreamResult(stringWriter);
			Transformer transformer = createXMLTransformer();
			Source source = new DOMSource(getDom()); // Document to be transformed transformed
			transformer.transform(source, result);
			if (iFile.exists()) {				
				iFile.setContents(new ByteArrayInputStream(stringWriter.toString().getBytes("UTF-8")), //$NON-NLS-1$ 
						IResource.KEEP_HISTORY | IResource.FORCE, null);
			} else {
				checkAndCreateFolderHierarchy(iFile);
				iFile.create(new ByteArrayInputStream(stringWriter.toString().getBytes("UTF-8")), //$NON-NLS-1$ 
						IResource.KEEP_HISTORY | IResource.FORCE, null);
			}

			iFile.getParent().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			
		} catch (CoreException |UnsupportedEncodingException|TransformerException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		} 
	}

	/** Check if the folders in the file's path exist and if not create them accordingly
	 * 
	 * @param file for which the path should be checked
	 * @throws CoreException
	 */
	private static void checkAndCreateFolderHierarchy(IFile file) throws CoreException {
		IPath path = file.getProjectRelativePath().removeLastSegments(1);
		
		if(!path.isEmpty()) {
			IFolder folder = file.getProject().getFolder(path);
			if (!folder.exists()) {
				folder.create(true, true, null);
				folder.refreshLocal(IResource.DEPTH_ZERO, null);
			}
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
	public void addIdentification(final Element parentElement, final LibraryElement libraryelement) {
		if (libraryelement.getIdentification() != null) {
			Element identification = createElement(LibraryElementTags.IDENTIFICATION_ELEMENT);
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
	public void addVersionInfo(final Element rootEle, final LibraryElement libraryelement) {
		if (!libraryelement.getVersionInfo().isEmpty()) {
			for (Iterator<VersionInfo> iter = libraryelement.getVersionInfo()
					.iterator(); iter.hasNext();) {
				VersionInfo info = iter.next();
				Element versionInfo = createElement(LibraryElementTags.VERSION_INFO_ELEMENT);
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

	void addParamsConfig(Element fbElement, EList<VarDeclaration> inputVars) {
		
		for (VarDeclaration var : inputVars) {
			if (var.getValue() != null
					&& var.getValue().getValue() != null
					&& !var.getValue().getValue().equals("")) { //$NON-NLS-1$
				Element parameterElement = createElement(LibraryElementTags.PARAMETER_ELEMENT);
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
