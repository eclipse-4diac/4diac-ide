/********************************************************************************
 * Copyright (c) 2008, 2020 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                          Johannes Kepler University, Linz
 *               2020, 2021  Primetals Technologies Austria GmbH
 *               2023 Martin Erich Jobst
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
 *  Hesam Rezaee - add import option for Variable configuration and visibility
 *  Martin Jobst - refactor marker handling
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarkerInterfaceHelper;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.Compiler;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.IVarElement;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Language;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.DeviceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SegmentTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

/** The Class CommonElementImporter. */
public abstract class CommonElementImporter {

	private static final boolean IS_VISIBLE = false;
	private static final boolean IS_VAR_CONFIGED = true;

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
	private IFile file = null;
	private InputStream inputStream = null;
	private final TypeLibrary typeLibrary;
	private LibraryElement element;
	protected final List<ErrorMarkerBuilder> errorMarkerBuilders;

	protected IFile getFile() {
		return file;
	}

	protected TypeLibrary getTypeLibrary() {
		return typeLibrary;
	}

	protected FBTypeEntry getTypeEntry(final String typeFbElement) {
		if (null != typeFbElement) {
			return getTypeLibrary().getFBTypeEntry(typeFbElement);
		}
		return null;
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

	protected CommonElementImporter(final InputStream inputStream, final TypeLibrary typeLibrary) {
		Assert.isNotNull(inputStream);
		this.inputStream = inputStream;
		this.typeLibrary = typeLibrary;
		errorMarkerBuilders = new ArrayList<>();
	}

	protected CommonElementImporter(final IFile file) {
		Assert.isNotNull(file);
		this.file = file;
		typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(file.getProject());
		errorMarkerBuilders = new ArrayList<>();
	}

	protected CommonElementImporter(final CommonElementImporter importer) {
		Assert.isNotNull(importer);
		reader = importer.reader;
		file = importer.file;
		typeLibrary = importer.typeLibrary;
		errorMarkerBuilders = importer.errorMarkerBuilders;
	}

	public void loadElement() {
		element = createRootModelElement();
		try (ImporterStreams streams = createInputStreams(getInputStream())) {
			proceedToStartElementNamed(getStartElementName());
			readNameCommentAttributes(element);
			processChildren(getStartElementName(), getBaseChildrenHandler());
			restorePersistedErrorMessages();
		} catch (final Exception e) {
			FordiacLogHelper.logWarning("Type Loading issue", e);//$NON-NLS-1$
			createErrorMarker(e.getMessage());
		} finally {
			FordiacMarkerHelper.updateMarkers(file, errorMarkerBuilders);
		}
	}

	protected InputStream getInputStream() throws Exception {
		if (inputStream != null) {
			return inputStream;
		}
		return file.getContents();
	}

	protected void createErrorMarker(final String message) {
		errorMarkerBuilders.add(ErrorMarkerBuilder.createErrorMarkerBuilder(message).setLineNumber(getLineNumber()));
	}

	protected void restorePersistedErrorMessages() {
		FordiacMarkerHelper.findAllMarkers(file, element, FordiacErrorMarker.INITIAL_VALUE_MARKER,
				CommonElementImporter::restorePersistedErrorMessage);
		FordiacMarkerHelper.findAllMarkers(file, element, FordiacErrorMarker.TYPE_DECLARATION_MARKER,
				CommonElementImporter::restorePersistedErrorMessage);
	}

	protected static void restorePersistedErrorMessage(final EObject target, final IMarker marker) {
		if (target instanceof final ErrorMarkerRef errorMarkerRef
				&& marker.getAttribute(IMarker.SEVERITY, 0) == IMarker.SEVERITY_ERROR) {
			final String message = marker.getAttribute(IMarker.MESSAGE, null);
			errorMarkerRef.setErrorMessage(Stream.of(errorMarkerRef.getErrorMessage(), message).filter(Objects::nonNull)
					.filter(Predicate.not(String::isBlank)).collect(Collectors.joining(", "))); //$NON-NLS-1$
		}
	}

	protected abstract LibraryElement createRootModelElement();

	protected abstract String getStartElementName();

	protected abstract IChildHandler getBaseChildrenHandler();

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

	public int getLineNumber() {
		if (reader != null && reader.getLocation() != null) {
			return reader.getLocation().getLineNumber();
		}
		return -1; // we don't have a parse position
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
							"Unexpected xml child (" + getReader().getLocalName() + ") found in " + elementName //$NON-NLS-1$ //$NON-NLS-2$
									+ getParseLocation());
				}
			} else if (XMLStreamConstants.END_ELEMENT == event) {
				if (!getReader().getLocalName().equals(elementName)) {
					throw new XMLStreamException(
							"Unexpected xml end tag found in " + elementName + ": " + getReader().getLocalName() //$NON-NLS-1$ //$NON-NLS-2$
									+ getParseLocation());
				}
				// we came to the end
				break;
			}
		}
	}

	private String getParseLocation() {
		return " in file: " + getFile() + " location: " + getReader().getLocation(); //$NON-NLS-1$ //$NON-NLS-2$
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

	/**
	 * Parses the version info.
	 *
	 * @param elem the library element the version info should be added to.
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	protected void parseVersionInfo(final LibraryElement elem) throws TypeImportException, XMLStreamException {
		final VersionInfo versionInfo = LibraryElementFactory.eINSTANCE.createVersionInfo();

		final String organization = getReader().getAttributeValue("", LibraryElementTags.ORGANIZATION_ATTRIBUTE); //$NON-NLS-1$
		if (null != organization) {
			versionInfo.setOrganization(organization);
		}

		final String version = getReader().getAttributeValue("", LibraryElementTags.VERSION_ATTRIBUTE); //$NON-NLS-1$
		if (null == version) {
			throw new TypeImportException(Messages.CommonElementImporter_ERROR_MissingVersionInfo);

		}
		versionInfo.setVersion(version);
		final String author = getReader().getAttributeValue("", LibraryElementTags.AUTHOR_ATTRIBUTE); //$NON-NLS-1$
		if (null == author) {
			throw new TypeImportException(Messages.CommonElementImporter_ERROR_MissingAuthorInfo);
		}
		versionInfo.setAuthor(author);

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
	 * Gets the x and y.
	 *
	 * @param positionableElement the positionable element where the parsed
	 *                            coordinates should be set to
	 *
	 * @throws TypeImportException the FBT import exception
	 */
	public void getXandY(final PositionableElement positionableElement) throws TypeImportException {
		try {
			final String x = getAttributeValue(LibraryElementTags.X_ATTRIBUTE);
			final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
			if (null != x) {
				pos.setX(CoordinateConverter.INSTANCE.convertFrom1499XML(x));
			}
			final String y = getAttributeValue(LibraryElementTags.Y_ATTRIBUTE);
			if (null != y) {
				pos.setY(CoordinateConverter.INSTANCE.convertFrom1499XML(y));
			}
			positionableElement.setPosition(pos);
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
		if (null == name) {
			throw new TypeImportException(Messages.Import_ERROR_NameNotDefined);
		}
		namedElement.setName(name.trim());
	}

	protected void readCommentAttribute(final INamedElement namedElement) {
		final String comment = getAttributeValue(LibraryElementTags.COMMENT_ATTRIBUTE);
		if (null != comment) {
			namedElement.setComment(fullyUnEscapeValue(comment));
		}
	}

	protected void parseGenericAttributeNode(final ConfigurableObject confObject) throws XMLStreamException {
		final String name = getAttributeValue(LibraryElementTags.NAME_ATTRIBUTE);
		final String type = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		String value = getAttributeValue(LibraryElementTags.VALUE_ATTRIBUTE);
		final String comment = getAttributeValue(LibraryElementTags.COMMENT_ATTRIBUTE);

		if (null == value) {
			// we don't have a value attribute check for a CData value
			value = readCDataSection();
		}

		if ((null != name) && (null != value)) {
			confObject.setAttribute(name, null == type ? IecTypes.ElementaryTypes.STRING.getName() : type, value,
					comment);
		}

		if (confObject instanceof StructManipulator) {
			checkStructAttribute((StructManipulator) confObject, name);
		}

	}

	private void checkStructAttribute(final StructManipulator fb, final String name) {
		if (LibraryElementTags.STRUCTURED_TYPE_ELEMENT.equals(name)) {
			final Attribute attr = fb.getAttribute(LibraryElementTags.STRUCTURED_TYPE_ELEMENT); // $NON-NLS-1$
			final StructuredType structType = getTypeLibrary().getDataTypeLibrary().getStructuredType(attr.getValue());
			fb.setStructTypeElementsAtInterface(structType);
		} else if (LibraryElementTags.DEMUX_VISIBLE_CHILDREN.equals(name)) {
			// reset type to get visible children configured
			fb.setStructTypeElementsAtInterface(fb.getStructType());
		}
	}

	protected VarDeclaration parseParameter() throws TypeImportException, XMLStreamException {
		final VarDeclaration variable = LibraryElementFactory.eINSTANCE.createVarDeclaration();

		final String name = getAttributeValue(LibraryElementTags.NAME_ATTRIBUTE);
		if (null == name) {
			throw new TypeImportException(Messages.ImportUtils_ERROR_ParameterNotSet);
		}
		variable.setName(name);

		final String value = getAttributeValue(LibraryElementTags.VALUE_ATTRIBUTE);
		if (null == value) {
			throw new TypeImportException(Messages.ImportUtils_ERROR_ParameterValueNotSet);
		}
		final Value val = LibraryElementFactory.eINSTANCE.createValue();
		val.setValue(value);
		variable.setValue(val);
		final String comment = getAttributeValue(LibraryElementTags.COMMENT_ATTRIBUTE);
		if (null != comment) {
			variable.setComment(comment);
		}
		proceedToEndElementNamed(LibraryElementTags.PARAMETER_ELEMENT);
		return variable;
	}

	private boolean isPinVisibilityAttribute() {
		final String name = getAttributeValue(LibraryElementTags.NAME_ATTRIBUTE);
		final String pinNameAndVisibility = getAttributeValue(LibraryElementTags.VALUE_ATTRIBUTE);
		return name.equals(LibraryElementTags.ELEMENT_VISIBLE) && pinNameAndVisibility != null
				&& pinNameAndVisibility.contains(":"); //$NON-NLS-1$
	}

	protected void parsePinVisibilityAttribute(final FBNetworkElement block) {
		final String pinNameAndVisibility = getAttributeValue(LibraryElementTags.VALUE_ATTRIBUTE);
		final String[] temp = pinNameAndVisibility.split(":"); //$NON-NLS-1$
		final IInterfaceElement ie = block.getInterfaceElement(temp[0]);
		ie.setVisible(IS_VISIBLE); // I know it's false since we save only hidden pins
	}

	private boolean isPinVarConfigAttribute() {
		final String name = getAttributeValue(LibraryElementTags.NAME_ATTRIBUTE);
		final String pinNameAndVarConfig = getAttributeValue(LibraryElementTags.VALUE_ATTRIBUTE);
		return name.equals(LibraryElementTags.VAR_CONFIG) && pinNameAndVarConfig != null
				&& pinNameAndVarConfig.contains(":"); //$NON-NLS-1$
	}

	protected void parsePinVarConfigAttribute(final FBNetworkElement block) {
		final String pinNameAndVarConfig = getAttributeValue(LibraryElementTags.VALUE_ATTRIBUTE);
		final String[] temp = pinNameAndVarConfig.split(":"); //$NON-NLS-1$
		final VarDeclaration inVar = block.getInterface().getVariable(temp[0]);
		if (inVar != null) {
			inVar.setVarConfig(IS_VAR_CONFIGED);
		}
	}

	protected String getAttributeValue(final String attributeName) {
		return getReader().getAttributeValue("", attributeName); //$NON-NLS-1$
	}

	protected CompilerInfo parseCompilerInfo() throws TypeImportException, XMLStreamException {
		final CompilerInfo compilerInfo = LibraryElementFactory.eINSTANCE.createCompilerInfo();

		final String header = getAttributeValue(LibraryElementTags.HEADER_ATTRIBUTE);
		if (null != header) {
			compilerInfo.setHeader(header);
		}
		final String classdef = getAttributeValue(LibraryElementTags.CLASSDEF_ATTRIBUTE);
		if (null != classdef) {
			compilerInfo.setClassdef(classdef);
		}
		final String packageName = getAttributeValue(LibraryElementTags.PACKAGE_NAME_ATTRIBUTE);
		if (null != packageName) {
			compilerInfo.setPackageName(packageName);
		}

		processChildren(LibraryElementTags.COMPILER_INFO_ELEMENT, name -> {
			if (LibraryElementTags.COMPILER_ELEMENT.equals(name)) {
				parseCompiler(compilerInfo);
				return true;
			}
			if (LibraryElementTags.IMPORT_ELEMENT.equals(name)) {
				parseImport(compilerInfo);
				return true;
			}
			return false;
		});
		return compilerInfo;
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
		if (null == vendor) {
			throw new TypeImportException(Messages.CompilableElementImporter_ERROR_VendorNotSet);
		}
		comp.setVendor(vendor);

		final String product = getAttributeValue(LibraryElementTags.PRODUCT_ATTRIBUTE);
		if (null == product) {
			throw new TypeImportException(Messages.CompilableElementImporter_ERROR_ProductNotSet);
		}
		comp.setProduct(product);

		final String version = getAttributeValue(LibraryElementTags.VERSION_ATTRIBUTE);
		if (null == version) {
			throw new TypeImportException(Messages.CompilableElementImporter_ERROR_VersionNotSet);
		}
		comp.setVersion(version);
		proceedToEndElementNamed(LibraryElementTags.COMPILER_ELEMENT);
		compilerInfo.getCompiler().add(comp);
	}

	private void parseImport(final CompilerInfo compilerInfo) throws TypeImportException, XMLStreamException {
		final Import imp = LibraryElementFactory.eINSTANCE.createImport();

		final String declaration = getAttributeValue(LibraryElementTags.DECLARATION_ATTRIBUTE);
		if (null == declaration) {
			throw new TypeImportException(Messages.CommonElementImporter_ERROR_DeclarationNotSet);
		}
		imp.setImportedNamespace(declaration);

		proceedToEndElementNamed(LibraryElementTags.IMPORT_ELEMENT);
		compilerInfo.getImports().add(imp);
	}

	protected void parseFBChildren(final FBNetworkElement block, final String parentNodeName)
			throws TypeImportException, XMLStreamException {
		processChildren(parentNodeName, name -> (switch (name) {
		case LibraryElementTags.PARAMETER_ELEMENT -> {
			parseParameter(block);
			yield true;
		}
		case LibraryElementTags.ATTRIBUTE_ELEMENT -> {
			handleFBAttributeChild(block);
			yield true;
		}
		default -> false;
		}));
	}

	public void handleFBAttributeChild(final FBNetworkElement block) throws XMLStreamException {
		if (isPinCommentAttribute()) {
			parsePinComment(block);
		} else if (isPinVisibilityAttribute()) {
			parsePinVisibilityAttribute(block);
		} else if (isPinVarConfigAttribute()) {
			parsePinVarConfigAttribute(block);
		} else {
			parseGenericAttributeNode(block);
		}
		proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
	}

	private boolean isPinCommentAttribute() {
		final String name = getAttributeValue(LibraryElementTags.NAME_ATTRIBUTE);
		return LibraryElementTags.PIN_COMMENT.equals(name);
	}

	private void parsePinComment(final FBNetworkElement block) {
		final String value = getAttributeValue(LibraryElementTags.VALUE_ATTRIBUTE);
		if (value != null) {
			final int splitPos = value.indexOf(':');
			if (splitPos != -1) {
				final String name = value.substring(0, splitPos);
				final IInterfaceElement ie = block.getInterfaceElement(name);
				if (ie != null) {
					final String comment = value.substring(splitPos + 1);
					ie.setComment(comment);
				}
			}
		}
	}

	protected void parseParameter(final FBNetworkElement block) throws TypeImportException, XMLStreamException {
		final String name = getAttributeValue(LibraryElementTags.NAME_ATTRIBUTE);
		if (null == name) {
			throw new TypeImportException(Messages.ImportUtils_ERROR_ParameterNotSet);
		}

		final String value = getAttributeValue(LibraryElementTags.VALUE_ATTRIBUTE);
		if (null == value) {
			throw new TypeImportException(Messages.ImportUtils_ERROR_ParameterValueNotSet);
		}
		final Value val = LibraryElementFactory.eINSTANCE.createValue();
		val.setValue(value);

		final IInterfaceElement ie = block.getInterfaceElement(name);
		if (null == ie) {
			createParameterErrorMarker(block, name, val);
			return;
		}
		if (ie instanceof final VarDeclaration varDecl) {
			varDecl.setValue(val);
		}
		final String comment = getAttributeValue(LibraryElementTags.COMMENT_ATTRIBUTE);
		if (null != comment) {
			ie.setComment(comment);
		}

		processChildren(LibraryElementTags.PARAMETER_ELEMENT, tagName -> {
			if (LibraryElementTags.ATTRIBUTE_ELEMENT.equals(tagName)) {
				parseGenericAttributeNode(ie);
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
				return true;
			}
			return false;
		});
	}

	protected void createParameterErrorMarker(final FBNetworkElement block, final String name, final Value value) {
		final String errorMessage = MessageFormat.format(Messages.CommonElementImporter_ERROR_MissingPinForParameter,
				name, block.getName());
		final ErrorMarkerInterface errorMarkerInterface = FordiacErrorMarkerInterfaceHelper
				.createErrorMarkerInterface(IecTypes.GenericTypes.ANY, name, true, block.getInterface(), errorMessage);
		errorMarkerInterface.setValue(value);
		errorMarkerBuilders.add(ErrorMarkerBuilder.createErrorMarkerBuilder(errorMessage)
				.setTarget(errorMarkerInterface).setLineNumber(getLineNumber()));
	}

	protected boolean isProfileAttribute() {
		final String name = getAttributeValue(LibraryElementTags.NAME_ATTRIBUTE);
		return LibraryElementTags.DEVICE_PROFILE.equals(name);
	}

	protected void parseProfile(final Device device) {
		final String value = getAttributeValue(LibraryElementTags.VALUE_ATTRIBUTE);
		if (null != value) {
			device.setProfile(value);
		}
	}

	protected Resource parseResource() throws TypeImportException, XMLStreamException {
		final Resource resource = LibraryElementFactory.eINSTANCE.createResource();
		resource.setDeviceTypeResource(false); // TODO model refactoring - check if a resource of given name is already
		// in the list then it would be a device type resource
		readNameCommentAttributes(resource);
		parseResourceType(resource);
		final FBNetwork fbNetwork = createResourceTypeNetwork(resource);
		resource.setFBNetwork(fbNetwork);

		processChildren(LibraryElementTags.RESOURCE_ELEMENT, name -> {
			switch (name) {
			case LibraryElementTags.FBNETWORK_ELEMENT:
				new ResDevFBNetworkImporter(this, fbNetwork, resource.getVarDeclarations())
						.parseFBNetwork(LibraryElementTags.FBNETWORK_ELEMENT);
				break;
			case LibraryElementTags.ATTRIBUTE_ELEMENT:
				parseGenericAttributeNode(resource);
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
				break;
			case LibraryElementTags.PARAMETER_ELEMENT:
				final VarDeclaration parameter = parseParameter();
				if (null != parameter) {
					final VarDeclaration devParam = getParamter(resource.getVarDeclarations(), parameter.getName());
					if (null != devParam) {
						devParam.setValue(parameter.getValue());
					} else {
						parameter.setIsInput(true);
						resource.getVarDeclarations().add(parameter);
					}
				}
				break;
			default:
				return false;
			}
			return true;
		});
		return resource;
	}

	private void parseResourceType(final Resource resource) {
		final String typeName = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		if (typeName != null) {
			final ResourceTypeEntry entry = getTypeLibrary().getResourceTypeEntry(typeName);
			if (null != entry) {
				resource.setTypeEntry(entry);
				createParameters(resource);
			}
		}
	}

	protected String readCDataSection() throws XMLStreamException {
		final StringBuilder algText = new StringBuilder();
		// the parser my split the content of several parts therefore this while loop
		while ((getReader().hasNext()) && (XMLStreamConstants.CHARACTERS == getReader().next())) {
			algText.append(getReader().getText());
		}
		return algText.toString();
	}

	/** Creates the values. */
	public static void createParameters(final IVarElement element) {
		if (element instanceof Device) {
			element.getVarDeclarations()
					.addAll(EcoreUtil.copyAll(((DeviceTypeEntry) ((TypedConfigureableObject) element).getTypeEntry())
							.getType().getVarDeclaration()));
		}
		if (element instanceof Resource) {
			element.getVarDeclarations()
					.addAll(EcoreUtil.copyAll(((ResourceTypeEntry) ((TypedConfigureableObject) element).getTypeEntry())
							.getType().getVarDeclaration()));
		}
		if (element instanceof Segment) {
			element.getVarDeclarations()
					.addAll(EcoreUtil.copyAll(((SegmentTypeEntry) ((TypedConfigureableObject) element).getTypeEntry())
							.getType().getVarDeclaration()));
		}
		for (final VarDeclaration varDecl : element.getVarDeclarations()) {
			final Value value = LibraryElementFactory.eINSTANCE.createValue();
			varDecl.setValue(value);
			final VarDeclaration typeVar = getTypeVariable(varDecl);
			if (null != typeVar && null != typeVar.getValue()) {
				value.setValue(typeVar.getValue().getValue());
			}
		}
	}

	private static VarDeclaration getTypeVariable(final VarDeclaration variable) {
		EList<VarDeclaration> varList = null;
		if (variable.eContainer() instanceof Device) {
			final Device dev = (Device) variable.eContainer();
			if (null != dev.getType()) {
				varList = dev.getType().getVarDeclaration();
			}
		} else if (variable.eContainer() instanceof Resource) {
			final Resource res = (Resource) variable.eContainer();
			if (null != res.getType()) {
				varList = res.getType().getVarDeclaration();
			}
		}

		if (null != varList) {
			return getParamter(varList, variable.getName());
		}
		return null;
	}

	protected static VarDeclaration getParamter(final EList<VarDeclaration> paramList, final String name) {
		for (final VarDeclaration varDecl : paramList) {
			if (varDecl.getName().equals(name)) {
				return varDecl;
			}
		}
		return null;
	}

	private static FBNetwork createResourceTypeNetwork(final Resource resource) {
		FBNetwork resourceFBNetwork = null;

		if (resource.getType() != null && resource.getType().getFBNetwork() != null) {
			// create a dummy interface list so that we can use the copyFBNetwork method
			final InterfaceList il = LibraryElementFactory.eINSTANCE.createInterfaceList();
			il.getInputVars().addAll(resource.getVarDeclarations());
			resourceFBNetwork = FBNetworkHelper.createResourceFBNetwork(resource.getType().getFBNetwork(), il);
			resource.getVarDeclarations().addAll(il.getInputVars()); // ensure that the data inputs are back with us.
		} else {
			resourceFBNetwork = LibraryElementFactory.eINSTANCE.createFBNetwork();
		}
		return resourceFBNetwork;
	}

	/**
	 * Take the given string and unescape all &, <, >, ", ', newlines, and tabs with
	 * the according XML unsescaped characters.
	 *
	 * @param value the string to unescape
	 * @return the unescaped string
	 */
	protected static String fullyUnEscapeValue(final String value) {
		String escapedValue = value.replace("&amp;", "&"); //$NON-NLS-1$ //$NON-NLS-2$
		escapedValue = escapedValue.replace("&lt;", "<"); //$NON-NLS-1$ //$NON-NLS-2$
		escapedValue = escapedValue.replace("&gt;", ">"); //$NON-NLS-1$ //$NON-NLS-2$
		escapedValue = escapedValue.replace("&quot;", "\""); //$NON-NLS-1$ //$NON-NLS-2$
		escapedValue = escapedValue.replace("&apos;", "\'"); //$NON-NLS-1$ //$NON-NLS-2$
		escapedValue = escapedValue.replace("&#10;", "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		escapedValue = escapedValue.replace("&#9;", "\t"); //$NON-NLS-1$ //$NON-NLS-2$
		return escapedValue;
	}

}
