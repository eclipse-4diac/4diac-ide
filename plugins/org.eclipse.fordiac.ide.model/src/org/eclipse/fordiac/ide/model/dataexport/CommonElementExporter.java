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
 *  Hesam Rezaee - add export option for Variable configuration and visibility
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.PreferenceConstants;
import org.eclipse.fordiac.ide.model.data.BaseType1;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class CommonElementExporter {

	protected static class ByteBufferInputStream extends InputStream {

		private final Iterator<ByteBuffer> bufferIterator;
		private ByteBuffer currentDataBuffer;
		private final List<ByteBuffer> dataBuffers;

		public ByteBufferInputStream(final List<ByteBuffer> dataBuffers) {
			this.dataBuffers = dataBuffers;
			dataBuffers.forEach(ByteBuffer::flip);
			bufferIterator = dataBuffers.iterator();
			currentDataBuffer = bufferIterator.next();
		}

		@Override
		public int read() throws IOException {
			if (currentDataBuffer.hasRemaining()) {
				return currentDataBuffer.get() & 0xFF;
			}
			if (bufferIterator.hasNext()) {
				currentDataBuffer = bufferIterator.next();
				return currentDataBuffer.get() & 0xFF;
			}
			return -1;
		}

		@Override
		public int read(final byte[] b) throws IOException {
			if (currentDataBuffer.hasRemaining()) {
				return super.read(b);
			}
			if (bufferIterator.hasNext()) {
				currentDataBuffer = bufferIterator.next();
				return super.read(b);
			}
			return -1;
		}

		@Override
		public int read(final byte[] b, final int off, final int len) throws IOException {
			if (len == 0) {
				return 0;
			}
			if (available() == 0) {
				return -1;
			}
			final int readLen = Math.min(available(), len);
			currentDataBuffer.get(b, off, readLen);
			return readLen;
		}

		@Override
		public int available() throws IOException {
			return currentDataBuffer.remaining();
		}

		@Override
		public void close() throws IOException {
			dataBuffers.clear();
			super.close();
		}

	}

	protected static class ByteBufferOutputStream extends OutputStream {
		private static final int SI_PREFIX_KI = 1024;
		private static final int SI_PREFIX_MI = SI_PREFIX_KI * SI_PREFIX_KI;
		private static final int SINGLE_DATA_BUFFER_CAPACITY = getSingleDataBufCap();
		private static final String PLUGIN_ID = "org.eclipse.fordiac.ide.model"; //$NON-NLS-1$

		private static int getSingleDataBufCap() {
			final IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(PLUGIN_ID);
			return preferences.getInt(PreferenceConstants.P_ALLOCATION_SIZE,
					PreferenceConstants.P_ALLOCATION_SIZE_DEFAULT_VALUE) * SI_PREFIX_MI;
		}

		private List<ByteBuffer> dataBuffers = new ArrayList<>(5); // give it an initial capacity of 5 to reduce
		// reallocation
		private ByteBuffer currentDataBuffer;

		public ByteBufferOutputStream() {
			addNewDataBuffer();
		}

		public List<ByteBuffer> transferDataBuffers() {
			final List<ByteBuffer> tmp = dataBuffers;
			dataBuffers = null;
			return tmp;
		}

		private void addNewDataBuffer() {
			currentDataBuffer = ByteBuffer.allocateDirect(SINGLE_DATA_BUFFER_CAPACITY);
			dataBuffers.add(currentDataBuffer);
		}

		@Override
		public void write(final int arg0) throws IOException {
			if (!currentDataBuffer.hasRemaining()) {
				addNewDataBuffer();
			}
			currentDataBuffer.put((byte) arg0);
		}

		@Override
		public void write(final byte[] b, final int off, final int len) throws IOException {
			if (currentDataBuffer.remaining() < len) {
				addNewDataBuffer();
			}
			currentDataBuffer.put(b, off, len);
		}

	}

	public static final String LINE_END = "\n"; //$NON-NLS-1$
	public static final String TAB = "\t"; //$NON-NLS-1$
	private static final Pattern CDATA_END_PATTERN = Pattern.compile("\\]\\]>"); //$NON-NLS-1$

	private final XMLStreamWriter writer;
	private ByteBufferOutputStream outputStream;

	private int tabCount = 0;

	protected CommonElementExporter() {
		writer = createEventWriter();
	}

	/** Constructor for chaining several exporters together (e.g., for the FBNetworkExporter)
	 *
	 * @param parent the calling exporter */
	protected CommonElementExporter(final CommonElementExporter parent) {
		writer = parent.writer;
		tabCount = parent.tabCount;
	}

	protected XMLStreamWriter getWriter() {
		return writer;
	}

	protected ByteBufferOutputStream getOutputStream() {
		return outputStream;
	}

	protected void addStartElement(final String name) throws XMLStreamException {
		addTabs();
		writer.writeStartElement(name);
		tabCount++;
	}

	protected void addEmptyStartElement(final String name) throws XMLStreamException {
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

	protected void addInlineEndElement() throws XMLStreamException {
		tabCount--;
		writer.writeEndElement();
	}

	private XMLStreamWriter createEventWriter() {
		final XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

		outputStream = new ByteBufferOutputStream();
		try {
			final XMLStreamWriter newWriter = outputFactory.createXMLStreamWriter(outputStream,
					StandardCharsets.UTF_8.name());
			newWriter.writeStartDocument(StandardCharsets.UTF_8.name(), "1.0"); //$NON-NLS-1$
			return newWriter;
		} catch (final XMLStreamException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			return null;
		}
	}

	protected void addColorAttributeElement(final ColorizableElement colElement) throws XMLStreamException {
		if (null != colElement.getColor()) {
			final String colorValue = colElement.getColor().getRed() + "," + colElement.getColor().getGreen() + "," //$NON-NLS-1$ //$NON-NLS-2$
					+ colElement.getColor().getBlue();
			addAttributeElement(LibraryElementTags.COLOR, IecTypes.ElementaryTypes.STRING.getName(), colorValue, null);
		}
	}

	protected void addAttributeElement(final String name, final String type, final String value, final String comment)
			throws XMLStreamException {
		if (BaseType1.WSTRING.getName().equals(type)) {
			addStartElement(LibraryElementTags.ATTRIBUTE_ELEMENT);
		} else {
			addEmptyStartElement(LibraryElementTags.ATTRIBUTE_ELEMENT);
		}
		getWriter().writeAttribute(LibraryElementTags.NAME_ATTRIBUTE, name);
		getWriter().writeAttribute(LibraryElementTags.TYPE_ATTRIBUTE, type);
		if (!BaseType1.WSTRING.getName().equals(type)) {
			getWriter().writeAttribute(LibraryElementTags.VALUE_ATTRIBUTE, value);
		}
		if ((null != comment) && (!comment.isBlank())) {
			getWriter().writeAttribute(LibraryElementTags.COMMENT_ATTRIBUTE, comment);
		}
		if (BaseType1.WSTRING.getName().equals(type)) {
			writeCDataSection(value);
			addInlineEndElement();
		}
	}

	protected void createNamedElementEntry(final INamedElement namedElement, final String elemName)
			throws XMLStreamException {
		addStartElement(elemName);
		addNameAndCommentAttribute(namedElement);
	}

	/** Adds the identification.
	 *
	 * @param libraryelement the libraryelement
	 * @throws XMLStreamException */
	protected void addIdentification(final LibraryElement libraryelement) throws XMLStreamException {
		if (null != libraryelement.getIdentification()) {
			addStartElement(LibraryElementTags.IDENTIFICATION_ELEMENT);
			final Identification ident = libraryelement.getIdentification();
			if ((null != ident.getStandard()) && !ident.getStandard().equals("")) { //$NON-NLS-1$
				writer.writeAttribute(LibraryElementTags.STANDARD_ATTRIBUTE, ident.getStandard());
			}
			if ((null != ident.getClassification()) && !ident.getClassification().equals("")) { //$NON-NLS-1$
				writer.writeAttribute(LibraryElementTags.CLASSIFICATION_ATTRIBUTE, ident.getClassification());
			}
			if ((null != ident.getApplicationDomain()) && !ident.getApplicationDomain().equals("")) { //$NON-NLS-1$
				writer.writeAttribute(LibraryElementTags.APPLICATION_DOMAIN_ATTRIBUTE, ident.getApplicationDomain());
			}
			if ((null != ident.getFunction()) && !ident.getFunction().equals("")) { //$NON-NLS-1$
				writer.writeAttribute(LibraryElementTags.FUNCTION_ELEMENT, ident.getFunction());
			}
			if ((null != ident.getType()) && !ident.getType().equals("")) { //$NON-NLS-1$
				writer.writeAttribute(LibraryElementTags.TYPE_ATTRIBUTE, ident.getType());
			}
			if ((null != ident.getDescription()) && !ident.getDescription().equals("")) { //$NON-NLS-1$
				writeAttributeRaw(LibraryElementTags.DESCRIPTION_ELEMENT, fullyEscapeValue(ident.getDescription()));
			}
			addEndElement();
		}
	}

	/** Adds the version info.
	 *
	 * @param libraryelement the libraryelement
	 * @throws XMLStreamException */
	protected void addVersionInfo(final LibraryElement libraryelement) throws XMLStreamException {
		if (!libraryelement.getVersionInfo().isEmpty()) {
			for (final VersionInfo info : libraryelement.getVersionInfo()) {
				addStartElement(LibraryElementTags.VERSION_INFO_ELEMENT);

				if ((null != info.getOrganization()) && !info.getOrganization().equals("")) { //$NON-NLS-1$
					writer.writeAttribute(LibraryElementTags.ORGANIZATION_ATTRIBUTE, info.getOrganization());
				}
				if ((null != info.getVersion()) && !info.getVersion().equals("")) { //$NON-NLS-1$
					writer.writeAttribute(LibraryElementTags.VERSION_ATTRIBUTE, info.getVersion());
				}
				if ((null != info.getAuthor()) && !info.getAuthor().equals("")) { //$NON-NLS-1$
					writer.writeAttribute(LibraryElementTags.AUTHOR_ATTRIBUTE, info.getAuthor());
				}
				if ((null != info.getDate()) && !info.getDate().equals("")) { //$NON-NLS-1$
					writer.writeAttribute(LibraryElementTags.DATE_ATTRIBUTE, info.getDate());

				}
				if ((null != info.getRemarks()) && !info.getRemarks().equals("")) { //$NON-NLS-1$
					writer.writeAttribute(LibraryElementTags.REMARKS_ATTRIBUTE, info.getRemarks());
				}

				addEndElement();
			}

		}
	}

	protected void addCommentAttribute(final String comment) throws XMLStreamException {
		if (null != comment) {
			writer.writeAttribute(LibraryElementTags.COMMENT_ATTRIBUTE, fullyEscapeValue(comment));
		}
	}

	protected void addNameAndCommentAttribute(final INamedElement namedElement) throws XMLStreamException {
		addNameAttribute(namedElement.getName());
		addCommentAttribute(namedElement.getComment());
	}

	protected void addNameTypeCommentAttribute(final INamedElement namedElement, final LibraryElement type)
			throws XMLStreamException {
		addNameAttribute(namedElement.getName());
		addTypeAttribute(type);
		addCommentAttribute(namedElement.getComment());
	}

	protected void addAttributes(final EList<Attribute> attributes) throws XMLStreamException {
		for (final Attribute attribute : attributes) {
			addAttributeElement(attribute.getName(), attribute.getType().getName(), attribute.getValue(),
					attribute.getComment());
		}
	}

	/** Writhe an XML attribute directly to the output stream
	 *
	 * @param attributeName  the name of the attribute
	 * @param attributeValue the value of the attribute
	 * @throws XMLStreamException */
	protected void writeAttributeRaw(final String attributeName, final String attributeValue)
			throws XMLStreamException {
		try (Writer osWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
			osWriter.write(" "); //$NON-NLS-1$
			osWriter.write(attributeName);
			osWriter.write("=\""); //$NON-NLS-1$
			osWriter.write(attributeValue);
			osWriter.write("\" "); //$NON-NLS-1$
		} catch (final IOException e) {
			throw new XMLStreamException("Could not write raw attribute", e);
		}
	}

	/** Take the given string and escape all &, <, >, ", ', newlines, and tabs with the according XML escaped
	 * characters.
	 *
	 * @param value the string to escape
	 * @return the escaped string */
	protected static String fullyEscapeValue(final String value) {
		String escapedValue = value.replace("&", "&amp;"); //$NON-NLS-1$ //$NON-NLS-2$
		escapedValue = escapedValue.replace("<", "&lt;"); //$NON-NLS-1$ //$NON-NLS-2$
		escapedValue = escapedValue.replace(">", "&gt;"); //$NON-NLS-1$ //$NON-NLS-2$
		escapedValue = escapedValue.replace("\"", "&quot;"); //$NON-NLS-1$ //$NON-NLS-2$
		escapedValue = escapedValue.replace("\'", "&apos;"); //$NON-NLS-1$ //$NON-NLS-2$
		escapedValue = escapedValue.replace("\n", "&#10;"); //$NON-NLS-1$ //$NON-NLS-2$
		escapedValue = escapedValue.replace("\t", "&#9;"); //$NON-NLS-1$ //$NON-NLS-2$
		return escapedValue;
	}

	protected void addTypeAttribute(final LibraryElement type) throws XMLStreamException {
		addTypeAttribute(PackageNameHelper.getFullTypeName(type));
	}

	protected void addTypeAttribute(final String type) throws XMLStreamException {
		writer.writeAttribute(LibraryElementTags.TYPE_ATTRIBUTE, (null != type) ? type : ""); //$NON-NLS-1$
	}

	protected void addNameAttribute(final String name) throws XMLStreamException {
		writer.writeAttribute(LibraryElementTags.NAME_ATTRIBUTE, (null != name) ? name : ""); //$NON-NLS-1$
	}

	protected void addParamsConfig(final EList<VarDeclaration> inputVars) throws XMLStreamException {
		for (final VarDeclaration inVar : inputVars) {
			addParam(inVar.getName(), inVar.getValue());
		}
	}

	protected void addErrorMarkerParamsConfig(final EList<ErrorMarkerInterface> errorPins) throws XMLStreamException {
		for (final ErrorMarkerInterface ep : errorPins) {
			addParam(ep.getName(), ep.getValue());
		}
	}

	private void addParam(final String pinName, final Value value) throws XMLStreamException {
		if ((value != null) && (value.getValue() != null) && !value.getValue().isBlank()) {
			addEmptyStartElement(LibraryElementTags.PARAMETER_ELEMENT);
			addNameAttribute(pinName);
			writer.writeAttribute(LibraryElementTags.VALUE_ATTRIBUTE, value.getValue());
		}
	}

	protected void addXYAttributes(final PositionableElement fb) throws XMLStreamException {
		addXYAttributes(fb.getPosition().getX(), fb.getPosition().getY());
	}

	protected void addXYAttributes(final int x, final int y) throws XMLStreamException {
		writer.writeAttribute(LibraryElementTags.X_ATTRIBUTE, CoordinateConverter.INSTANCE.convertTo1499XML(x));
		writer.writeAttribute(LibraryElementTags.Y_ATTRIBUTE, CoordinateConverter.INSTANCE.convertTo1499XML(y));
	}

	protected void writeCDataSection(final String cdataText) throws XMLStreamException {
		final Matcher endPatternMatcher = CDATA_END_PATTERN.matcher(cdataText);
		int currentPosition = 0;
		if (endPatternMatcher.find()) { // Check if we have at least one CData end pattern in the string
			do {
				getWriter().writeCData(cdataText.substring(currentPosition, endPatternMatcher.start()));
				getWriter().writeCharacters("]]>"); //$NON-NLS-1$
				currentPosition = endPatternMatcher.end();
			} while (endPatternMatcher.find());

			if (currentPosition < cdataText.length()) {
				// there is some text after the last CData end pattern
				getWriter().writeCData(cdataText.substring(currentPosition));
			}
		} else {
			// no CData end pattern write the algorithm text as whole
			getWriter().writeCData(cdataText);
		}
	}

}
