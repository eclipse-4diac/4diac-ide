/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor Gmbh, TU Wien ACIN, fortiss GmbH
 * 				 2018 - 2020 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Refactored class hierarchy of xml exporters
 *   			 - fixed coordinate system resolution conversion in in- and export
 *               - changed exporting the Saxx cursor api
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;

abstract class CommonElementExporter {

	public static final String LINE_END = "\n"; //$NON-NLS-1$
	public static final String TAB = "\t"; //$NON-NLS-1$

	private final XMLStreamWriter writer;
	private ByteArrayOutputStream outputStream;

	private int tabCount = 0;

	protected CommonElementExporter() {
		writer = createEventWriter();
	}

	/**
	 * Constructor for chaing several exporters together (e.g., for the
	 * FBNetworkExporter)
	 *
	 * @param parent the calling exporter
	 */
	protected CommonElementExporter(CommonElementExporter parent) {
		writer = parent.writer;
		tabCount = parent.tabCount;
	}

	protected XMLStreamWriter getWriter() {
		return writer;
	}

	protected void addStartElement(String name) throws XMLStreamException {
		addTabs();
		writer.writeStartElement(name);
		tabCount++;
	}

	protected void addEmptyStartElement(String name) throws XMLStreamException {
		addTabs();
		writer.writeEmptyElement(name);
	}

	private void addTabs() throws XMLStreamException {
		writer.writeCharacters(LINE_END);
		for (int i = 0; i < tabCount; i++) {
			writer.writeCharacters(TAB);
		}
	}

	protected void addEndElement() throws XMLStreamException {
		tabCount--;
		addTabs();
		writer.writeEndElement();
	}

	private XMLStreamWriter createEventWriter() {
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

		outputStream = new ByteArrayOutputStream();
		try {
			XMLStreamWriter newWriter = outputFactory.createXMLStreamWriter(outputStream,
					StandardCharsets.UTF_8.name());
			newWriter.writeStartDocument(StandardCharsets.UTF_8.name(), "1.0");
			return newWriter;
		} catch (XMLStreamException e) {
			Activator.getDefault().logError(e.getMessage(), e);
			return null;
		}
	}

	protected void addColorAttributeElement(final ColorizableElement colElement) throws XMLStreamException {
		String colorValue = colElement.getColor().getRed() + "," + colElement.getColor().getGreen() + "," //$NON-NLS-1$ //$NON-NLS-2$
				+ colElement.getColor().getBlue();
		addAttributeElement(LibraryElementTags.COLOR, "STRING", colorValue, "color"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected void addAttributeElement(String name, String type, String value, String comment)
			throws XMLStreamException {
		addEmptyStartElement(LibraryElementTags.ATTRIBUTE_ELEMENT);
		getWriter().writeAttribute(LibraryElementTags.NAME_ATTRIBUTE, name);
		getWriter().writeAttribute(LibraryElementTags.TYPE_ATTRIBUTE, type);
		getWriter().writeAttribute(LibraryElementTags.VALUE_ATTRIBUTE, value);
		getWriter().writeAttribute(LibraryElementTags.COMMENT_ATTRIBUTE, comment);
	}

	protected void createNamedElementEntry(final INamedElement namedElement, final String elemName)
			throws XMLStreamException {
		addStartElement(elemName);
		addNameAndCommentAttribute(namedElement);
	}

	protected void writeToFile(IFile iFile) {
		long startTime = System.currentTimeMillis();
		try {
			writer.writeEndDocument();
			writer.close();
			outputStream.flush();
			outputStream.close();
			ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
			if (iFile.exists()) {
				iFile.setContents(inputStream, IResource.KEEP_HISTORY | IResource.FORCE, null);
			} else {
				checkAndCreateFolderHierarchy(iFile);
				iFile.create(inputStream, IResource.KEEP_HISTORY | IResource.FORCE, null);
			}
		} catch (CoreException | IOException | XMLStreamException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Saving time for System: " + (endTime - startTime) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$

	}

	/**
	 * Check if the folders in the file's path exist and if not create them
	 * accordingly
	 *
	 * @param file for which the path should be checked
	 * @throws CoreException
	 */
	private static void checkAndCreateFolderHierarchy(IFile file) throws CoreException {
		IPath path = file.getProjectRelativePath().removeLastSegments(1);

		if (!path.isEmpty()) {
			IFolder folder = file.getProject().getFolder(path);
			if (!folder.exists()) {
				folder.create(true, true, null);
				folder.refreshLocal(IResource.DEPTH_ZERO, null);
			}
		}
	}

	/**
	 * Adds the identification.
	 *
	 * @param libraryelement the libraryelement
	 * @throws XMLStreamException
	 */
	protected void addIdentification(final LibraryElement libraryelement) throws XMLStreamException {
		if (null != libraryelement.getIdentification()) {
			addStartElement(LibraryElementTags.IDENTIFICATION_ELEMENT);
			Identification ident = libraryelement.getIdentification();
			if (null != ident.getStandard() && !ident.getStandard().equals("")) { //$NON-NLS-1$
				writer.writeAttribute(LibraryElementTags.STANDARD_ATTRIBUTE, ident.getStandard());
			}
			if (null != ident.getClassification() && !ident.getClassification().equals("")) { //$NON-NLS-1$
				writer.writeAttribute(LibraryElementTags.CLASSIFICATION_ATTRIBUTE, ident.getClassification());
			}
			if (null != ident.getApplicationDomain() && !ident.getApplicationDomain().equals("")) { //$NON-NLS-1$
				writer.writeAttribute(LibraryElementTags.APPLICATION_DOMAIN_ATTRIBUTE, ident.getApplicationDomain());
			}
			if (null != ident.getFunction() && !ident.getFunction().equals("")) { //$NON-NLS-1$
				writer.writeAttribute(LibraryElementTags.FUNCTION_ELEMENT, ident.getFunction());
			}
			if (null != ident.getType() && !ident.getType().equals("")) { //$NON-NLS-1$
				writer.writeAttribute(LibraryElementTags.TYPE_ATTRIBUTE, ident.getType());
			}
			if (null != ident.getDescription() && !ident.getDescription().equals("")) { //$NON-NLS-1$
				writer.writeAttribute(LibraryElementTags.DESCRIPTION_ELEMENT, ident.getDescription());
			}
			addEndElement();
		}
	}

	/**
	 * Adds the version info.
	 *
	 * @param libraryelement the libraryelement
	 * @throws XMLStreamException
	 */
	public void addVersionInfo(final LibraryElement libraryelement) throws XMLStreamException {
		if (!libraryelement.getVersionInfo().isEmpty()) {
			for (VersionInfo info : libraryelement.getVersionInfo()) {
				addStartElement(LibraryElementTags.VERSION_INFO_ELEMENT);

				if (null != info.getOrganization() && !info.getOrganization().equals("")) { //$NON-NLS-1$
					writer.writeAttribute(LibraryElementTags.ORGANIZATION_ATTRIBUTE, info.getOrganization());
				}
				if (null != info.getVersion() && !info.getVersion().equals("")) { //$NON-NLS-1$
					writer.writeAttribute(LibraryElementTags.VERSION_ATTRIBUTE, info.getVersion());
				}
				if (null != info.getAuthor() && !info.getAuthor().equals("")) { //$NON-NLS-1$
					writer.writeAttribute(LibraryElementTags.AUTHOR_ATTRIBUTE, info.getAuthor());
				}
				if (null != info.getDate() && !info.getDate().equals("")) { //$NON-NLS-1$
					writer.writeAttribute(LibraryElementTags.DATE_ATTRIBUTE, info.getDate());

				}
				if (null != info.getRemarks() && !info.getRemarks().equals("")) { //$NON-NLS-1$
					writer.writeAttribute(LibraryElementTags.REMARKS_ATTRIBUTE, info.getRemarks());
				}

				addEndElement();
			}

		}
	}

	protected void addCommentAttribute(INamedElement namedElement) throws XMLStreamException {
		if (null != namedElement.getComment()) {
			writer.writeAttribute(LibraryElementTags.COMMENT_ATTRIBUTE, namedElement.getComment());
		}
	}

	protected void addNameAndCommentAttribute(INamedElement namedElement) throws XMLStreamException {
		addNameAttribute(namedElement.getName());
		addCommentAttribute(namedElement);
	}

	protected void addNameTypeCommentAttribute(INamedElement namedElement, INamedElement type)
			throws XMLStreamException {
		addNameAttribute(namedElement.getName());
		addTypeAttribute(type);
		addCommentAttribute(namedElement);
	}

	protected void addTypeAttribute(INamedElement type) throws XMLStreamException {
		addTypeAttribute(((null != type) && (null != type.getName())) ? type.getName() : ""); //$NON-NLS-1$
	}

	protected void addTypeAttribute(String type) throws XMLStreamException {
		writer.writeAttribute(LibraryElementTags.TYPE_ATTRIBUTE, (null != type) ? type : ""); //$NON-NLS-1$
	}

	protected void addNameAttribute(String name) throws XMLStreamException {
		writer.writeAttribute(LibraryElementTags.NAME_ATTRIBUTE, (null != name) ? name : ""); //$NON-NLS-1$
	}

	protected void addParamsConfig(EList<VarDeclaration> inputVars) throws XMLStreamException {
		for (VarDeclaration var : inputVars) {
			if (null != var.getValue() && null != var.getValue().getValue() && !var.getValue().getValue().equals("")) { //$NON-NLS-1$
				addEmptyStartElement(LibraryElementTags.PARAMETER_ELEMENT);
				addNameAttribute(var.getName());
				writer.writeAttribute(LibraryElementTags.VALUE_ATTRIBUTE, var.getValue().getValue());
			}
		}
	}

	protected void addXYAttributes(PositionableElement fb) throws XMLStreamException {
		addXYAttributes(fb.getX(), fb.getY());
	}

	protected void addXYAttributes(int x, int y) throws XMLStreamException {
		writer.writeAttribute(LibraryElementTags.X_ATTRIBUTE, CoordinateConverter.INSTANCE.convertTo1499XML(x));
		writer.writeAttribute(LibraryElementTags.Y_ATTRIBUTE, CoordinateConverter.INSTANCE.convertTo1499XML(y));
	}

}
