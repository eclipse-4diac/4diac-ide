/********************************************************************************
 * Copyright (c) 2008, 2020 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                          Johannes Kepler University, Linz, Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Monika Wenger, Alois Zoitl, Waldemar Eisenmenger
 *    - initial API and implementation and/or initial documentation
 *  Alois Zoitl - fixed coordinate system resolution conversion in in- and export
 *              - Changed XML parsing to Staxx cursor interface for improved
 *  			  parsing performance
 *              - extension for connection error markers
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.helpers.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.CompilableType;
import org.eclipse.fordiac.ide.model.libraryElement.Compiler;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.Language;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

/**
 * The Class CommonElementImporter.
 */
abstract class CommonElementImporter {

	private static class ImporterStreams implements AutoCloseable {
		private final InputStream inputStream;
		private final XMLStreamReader reader;

		public ImporterStreams(final InputStream inputStream, final XMLStreamReader reader) {
			this.inputStream = inputStream;
			this.reader = reader;
		}

		@Override
		public void close() throws Exception {
			reader.close();
			inputStream.close();
		}
	}

	private XMLStreamReader reader;
	private final IFile file;
	private final TypeLibrary typeLibrary;
	private LibraryElement element;

	protected IFile getFile() {
		return file;
	}

	protected TypeLibrary getTypeLibrary() {
		return typeLibrary;
	}

	protected Palette getPalette() {
		return getTypeLibrary().getBlockTypeLib();
	}

	protected DataTypeLibrary getDataTypeLibrary() {
		return getTypeLibrary().getDataTypeLibrary();
	}

	public LibraryElement getElement() {
		return element;
	}

	protected void setElement(final LibraryElement element) {
		this.element = element;
	}

	protected CommonElementImporter(final IFile file) {
		Assert.isNotNull(file);
		this.file = file;
		typeLibrary = TypeLibrary.getTypeLibrary(file.getProject());
	}

	protected CommonElementImporter(final CommonElementImporter importer) {
		Assert.isNotNull(importer);
		reader = importer.reader;
		file = importer.file;
		typeLibrary = importer.typeLibrary;
	}

	public void loadElement() {
		element = createRootModelElement();
		try (ImporterStreams streams = createInputStreams(file.getContents())) {
			deleteMarkers();
			proceedToStartElementNamed(getStartElementName());
			readNameCommentAttributes(element);
			processChildren(getStartElementName(), getBaseChildrenHandler());
		} catch (final Exception e) {
			Activator.getDefault().logWarning("Type Loading issue", e);
			createErrorMarker(e.getMessage());
		}
	}

	protected void createErrorMarker(final String message) {
		final Map<String, String> attrs = new HashMap<>();
		attrs.put(IMarker.MESSAGE, message);
		createErrorMarker(attrs);
	}

	protected void createErrorMarker(final String message, final INamedElement errorLocation) {
		final Map<String, String> attrs = new HashMap<>();
		attrs.put(IMarker.MESSAGE, message);
		FordiacMarkerHelper.addLocation(errorLocation, attrs);
		FordiacMarkerHelper.addTargetIdentifier(errorLocation, attrs);
		createErrorMarker(attrs);
	}

	protected void createErrorMarker(final Map<String, String> attrs) {
		final int lineNumber = reader.getLocation().getLineNumber();
		final WorkspaceJob job = new WorkspaceJob("Add error marker to file: " + file.getName()) {
			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) {
				try {
					final IMarker marker = file.createMarker(IMarker.PROBLEM);
					if (marker.exists()) {
						marker.setAttributes(attrs);
						marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
						marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
						marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
					}
				} catch (final CoreException e) {
					Activator.getDefault().logError("could not create error marker", e); //$NON-NLS-1$
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(file.getProject());
		job.schedule();
	}

	protected abstract LibraryElement createRootModelElement();

	protected abstract String getStartElementName();

	protected abstract IChildHandler getBaseChildrenHandler();

	protected void deleteMarkers() {
		final WorkspaceJob job = new WorkspaceJob("Remove error markers from file: " + file.getName()) {
			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) {
				try {
					file.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
				} catch (final CoreException e) {
					Activator.getDefault().logError("Could not delete error marker", e); //$NON-NLS-1$
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(file.getProject());
		job.schedule();
	}

	private ImporterStreams createInputStreams(final InputStream fileInputStream) throws XMLStreamException {
		final XMLInputFactory factory = XMLInputFactory.newInstance();
		factory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
		factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
		reader = factory.createXMLStreamReader(fileInputStream);
		return new ImporterStreams(fileInputStream, reader);
	}

	protected XMLStreamReader getReader() {
		return reader;
	}

	protected void proceedToStartElementNamed(final String elementName) throws XMLStreamException {
		while (reader.hasNext()) {
			final int event = reader.next();
			if ((XMLStreamConstants.START_ELEMENT == event) && (reader.getLocalName().equals(elementName))) {
				// we found it
				return;
			}
		}
		throw new XMLStreamException("Could not find start element named: " + elementName); //$NON-NLS-1$
	}

	protected void proceedToEndElementNamed(final String elementName) throws XMLStreamException {
		do {
			if ((XMLStreamConstants.END_ELEMENT == reader.getEventType())
					&& (reader.getLocalName().equals(elementName))) {
				// we found it
				return;
			}
		} while (reader.hasNext() && (0 != reader.next()));
		throw new XMLStreamException("Could not find end element named: " + elementName); //$NON-NLS-1$
	}

	protected interface IChildHandler {

		boolean checkChild(String localName) throws XMLStreamException, TypeImportException;

	}

	protected void processChildren(final String elementName, final IChildHandler childHandler)
			throws XMLStreamException, TypeImportException {
		while (getReader().hasNext()) {
			final int event = getReader().next();
			if (XMLStreamConstants.START_ELEMENT == event) {
				if (!childHandler.checkChild(getReader().getLocalName())) {
					throw new XMLStreamException(
							"Unexpected xml child (" + getReader().getLocalName() + ") found in " + elementName); //$NON-NLS-1$ //$NON-NLS-2$
				}
			} else if (XMLStreamConstants.END_ELEMENT == event) {
				if (!getReader().getLocalName().equals(elementName)) {
					throw new XMLStreamException(
							"Unexpected xml end tag found in " + elementName + ": " + getReader().getLocalName()); //$NON-NLS-1$ //$NON-NLS-2$
				}
				// we came to the end
				break;
			}
		}
	}

	/**
	 * Parses the identification.
	 *
	 * @param elem the elem
	 * @param node the node
	 *
	 * @return the identification
	 * @throws XMLStreamException
	 */
	protected void parseIdentification(final LibraryElement elem) throws XMLStreamException {
		final Identification ident = LibraryElementFactory.eINSTANCE.createIdentification();
		final String standard = getAttributeValue(LibraryElementTags.STANDARD_ATTRIBUTE);
		if (null != standard) {
			ident.setStandard(standard);
		}
		final String classification = getAttributeValue(LibraryElementTags.CLASSIFICATION_ATTRIBUTE);
		if (null != classification) {
			ident.setClassification(classification);
		}
		final String applicationDomain = getAttributeValue(LibraryElementTags.APPLICATION_DOMAIN_ATTRIBUTE);
		if (null != applicationDomain) {
			ident.setApplicationDomain(applicationDomain);
		}
		final String function = getAttributeValue(LibraryElementTags.FUNCTION_ELEMENT);
		if (null != function) {
			ident.setFunction(function);
		}
		final String type = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		if (null != type) {
			ident.setType(type);
		}
		final String description = getAttributeValue(LibraryElementTags.DESCRIPTION_ELEMENT);
		if (null != description) {
			ident.setDescription(description);
		}
		elem.setIdentification(ident);
		proceedToEndElementNamed(LibraryElementTags.IDENTIFICATION_ELEMENT);
	}

	/** Parses the version info.
	 *
	 * @param elem the library element the version info should be added to.
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException */
	protected void parseVersionInfo(final LibraryElement elem) throws TypeImportException, XMLStreamException {
		final VersionInfo versionInfo = LibraryElementFactory.eINSTANCE.createVersionInfo();

		final String organization = getReader().getAttributeValue("", LibraryElementTags.ORGANIZATION_ATTRIBUTE); //$NON-NLS-1$
		if (null != organization) {
			versionInfo.setOrganization(organization);
		}

		final String version = getReader().getAttributeValue("", LibraryElementTags.VERSION_ATTRIBUTE); //$NON-NLS-1$
		if (null != version) {
			versionInfo.setVersion(version);
		} else {
			throw new TypeImportException(Messages.CommonElementImporter_ERROR_MissingVersionInfo);

		}
		final String author = getReader().getAttributeValue("", LibraryElementTags.AUTHOR_ATTRIBUTE); //$NON-NLS-1$
		if (null != author) {
			versionInfo.setAuthor(author);
		} else {
			throw new TypeImportException(Messages.CommonElementImporter_ERROR_MissingAuthorInfo);
		}

		final String date = getReader().getAttributeValue("", LibraryElementTags.DATE_ATTRIBUTE); //$NON-NLS-1$
		if (null != date) {
			versionInfo.setDate(date);
			// TODO: check whether it is better to change type to Date
		}

		final String remarks = getReader().getAttributeValue("", LibraryElementTags.REMARKS_ATTRIBUTE); //$NON-NLS-1$
		versionInfo.setRemarks((null != remarks) ? remarks : ""); //$NON-NLS-1$

		elem.getVersionInfo().add(versionInfo);

		proceedToEndElementNamed(LibraryElementTags.VERSION_INFO_ELEMENT);
	}

	/**
	 * Gets the xand y.
	 *
	 * @param positionableElement the positionable element where the parsed
	 *                            coordinates should be set to
	 *
	 * @throws TypeImportException the FBT import exception
	 */
	public void getXandY(final PositionableElement positionableElement) throws TypeImportException {
		try {
			final String x = getAttributeValue(LibraryElementTags.X_ATTRIBUTE);
			if (null != x) {
				positionableElement.setX(CoordinateConverter.INSTANCE.convertFrom1499XML(x));
			}
			final String y = getAttributeValue(LibraryElementTags.Y_ATTRIBUTE);
			if (null != y) {
				positionableElement.setY(CoordinateConverter.INSTANCE.convertFrom1499XML(y));
			}
		} catch (final NumberFormatException nfe) {
			throw new TypeImportException(Messages.FBTImporter_POSITION_EXCEPTION, nfe);
		}
	}

	protected void readNameCommentAttributes(final INamedElement namedElement) throws TypeImportException {
		readNameAttribute(namedElement);
		readCommentAttribute(namedElement);
	}

	private void readNameAttribute(final INamedElement namedElement) throws TypeImportException {
		final String name = getAttributeValue(LibraryElementTags.NAME_ATTRIBUTE);
		if (null != name) {
			namedElement.setName(name.trim());
		} else {
			throw new TypeImportException(Messages.Import_ERROR_NameNotDefined);
		}
	}

	private void readCommentAttribute(final INamedElement namedElement) {
		final String comment = getAttributeValue(LibraryElementTags.COMMENT_ATTRIBUTE);
		if (null != comment) {
			namedElement.setComment(comment);
		}
	}

	protected void parseGenericAttributeNode(final ConfigurableObject confObject) {
		final String name = getAttributeValue(LibraryElementTags.NAME_ATTRIBUTE);
		final String type = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		final String value = getAttributeValue(LibraryElementTags.VALUE_ATTRIBUTE);
		final String comment = getAttributeValue(LibraryElementTags.COMMENT_ATTRIBUTE);
		if (null != name && null != value) {
			confObject.setAttribute(name, null == type ? "STRING" : type, //$NON-NLS-1$
					value, comment);
		}
	}

	protected VarDeclaration parseParameter() throws TypeImportException, XMLStreamException {
		final VarDeclaration var = LibraryElementFactory.eINSTANCE.createVarDeclaration();

		final String name = getAttributeValue(LibraryElementTags.NAME_ATTRIBUTE);
		if (null != name) {
			var.setName(name);
		} else {
			throw new TypeImportException(Messages.ImportUtils_ERROR_ParameterNotSet);
		}

		final String value = getAttributeValue(LibraryElementTags.VALUE_ATTRIBUTE);
		if (null != value) {
			final Value val = LibraryElementFactory.eINSTANCE.createValue();
			val.setValue(value);
			var.setValue(val);
		} else {
			throw new TypeImportException(Messages.ImportUtils_ERROR_ParameterValueNotSet);
		}
		final String comment = getAttributeValue(LibraryElementTags.COMMENT_ATTRIBUTE);
		if (null != comment) {
			var.setComment(comment);
		}
		proceedToEndElementNamed(LibraryElementTags.PARAMETER_ELEMENT);
		return var;
	}

	protected String getAttributeValue(final String attributeName) {
		return getReader().getAttributeValue("", attributeName); //$NON-NLS-1$
	}

	protected void parseCompilerInfo(final CompilableType ctype) throws TypeImportException, XMLStreamException {
		final CompilerInfo compilerInfo = LibraryElementFactory.eINSTANCE.createCompilerInfo();

		final String header = getAttributeValue(LibraryElementTags.HEADER_ATTRIBUTE);
		if (null != header) {
			compilerInfo.setHeader(header);
		}
		final String classdef = getAttributeValue(LibraryElementTags.CLASSDEF_ATTRIBUTE);
		if (null != classdef) {
			compilerInfo.setClassdef(classdef);
		}

		processChildren(LibraryElementTags.COMPILER_INFO_ELEMENT, name -> {
			if (LibraryElementTags.COMPILER_ELEMENT.equals(name)) {
				parseCompiler(compilerInfo);
				return true;
			}
			return false;
		});
		ctype.setCompilerInfo(compilerInfo);
	}

	private void parseCompiler(final CompilerInfo compilerInfo) throws TypeImportException, XMLStreamException {
		final Compiler comp = LibraryElementFactory.eINSTANCE.createCompiler();
		final String language = getAttributeValue(LibraryElementTags.LANGUAGE_ATTRIBUTE);

		if (null != language) {
			switch (language.toUpperCase()) {
			case "C": //$NON-NLS-1$
				comp.setLanguage(Language.C);
				break;
			case "CPP": //$NON-NLS-1$
				comp.setLanguage(Language.CPP);
				break;
			case "JAVA": //$NON-NLS-1$
				comp.setLanguage(Language.JAVA);
				break;
			case "OTHER": //$NON-NLS-1$
				comp.setLanguage(Language.OTHER);
				break;
			default:
				throw new TypeImportException(Messages.CompilableElementImporter_ERROR_UnsupportedLanguage);
			}
		}

		final String vendor = getAttributeValue(LibraryElementTags.VENDOR_ATTRIBUTE);
		if (null != vendor) {
			comp.setVendor(vendor);
		} else {
			throw new TypeImportException(Messages.CompilableElementImporter_ERROR_VendorNotSet);
		}

		final String product = getAttributeValue(LibraryElementTags.PRODUCT_ATTRIBUTE);
		if (null != product) {
			comp.setProduct(product);
		} else {
			throw new TypeImportException(Messages.CompilableElementImporter_ERROR_ProductNotSet);
		}

		final String version = getAttributeValue(LibraryElementTags.VERSION_ATTRIBUTE);
		if (null != version) {
			comp.setVersion(version);
		} else {
			throw new TypeImportException(Messages.CompilableElementImporter_ERROR_VersionNotSet);
		}
		proceedToEndElementNamed(LibraryElementTags.COMPILER_ELEMENT);
		compilerInfo.getCompiler().add(comp);
	}

	protected boolean isProfileAttribute() {
		final String name = getAttributeValue(LibraryElementTags.NAME_ATTRIBUTE);
		return (null != name) && LibraryElementTags.DEVICE_PROFILE.equals(name);
	}

	protected void parseProfile(final Device device) {
		final String value = getAttributeValue(LibraryElementTags.VALUE_ATTRIBUTE);
		if (null != value) {
			device.setProfile(value);
		}
	}

}
