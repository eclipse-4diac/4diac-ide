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
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Method;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.OtherMethod;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECState;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.TextMethod;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

/** Managing class for importing *.fbt files */

public class FBTImporter extends BlockTypeImporter {

	/** The algorithm name ec action mapping. */
	private final Map<String, List<ECAction>> algorithmNameECActionMapping = new HashMap<>();

	/** The ec states. */
	private final Map<String, ECState> ecStates = new HashMap<>();

	public FBTImporter(final IFile typeFile) {
		super(typeFile);
	}

	public FBTImporter(final InputStream inputStream, final TypeLibrary typeLibrary) {
		super(inputStream, typeLibrary);
	}

	protected FBTImporter(final CommonElementImporter importer) {
		super(importer);
	}

	@Override
	protected LibraryElement createRootModelElement() {
		final FBType newType = LibraryElementFactory.eINSTANCE.createServiceInterfaceFBType();
		newType.setService(LibraryElementFactory.eINSTANCE.createService());
		return newType;
	}

	@Override
	protected String getStartElementName() {
		return LibraryElementTags.FBTYPE_ELEMENT;
	}

	@Override
	protected IChildHandler getBaseChildrenHandler() {
		return name -> {
			switch (name) {
			case LibraryElementTags.IDENTIFICATION_ELEMENT -> parseIdentification(getElement());
			case LibraryElementTags.VERSION_INFO_ELEMENT -> parseVersionInfo(getElement());
			case LibraryElementTags.COMPILER_INFO_ELEMENT -> getElement().setCompilerInfo(parseCompilerInfo());
			case LibraryElementTags.INTERFACE_LIST_ELEMENT -> getElement().setInterfaceList(
					getInterfaceListImporter().parseInterfaceList(LibraryElementTags.INTERFACE_LIST_ELEMENT));
			case LibraryElementTags.BASIC_F_B_ELEMENT -> {
				setElement(convertToBasicType(getElement()));
				parseBasicFB((BasicFBType) getElement());
			}
			case LibraryElementTags.SIMPLE_F_B_ELEMENT -> {
				setElement(convertToSimpleType(getElement()));
				parseSimpleFB((SimpleFBType) getElement());
			}
			case LibraryElementTags.FBNETWORK_ELEMENT -> {
				// parse the composite FBs as last
				setElement(convertToCompositeType(getElement()));
				parseFBNetwork((CompositeFBType) getElement());
			}
			case LibraryElementTags.SERVICE_ELEMENT -> parseService(getElement());
			case LibraryElementTags.ATTRIBUTE_ELEMENT -> {
				parseGenericAttributeNode(getElement());
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
			}
			default -> {
				return false;
			}
			}

			return true;
		};
	}

	private static void copyGeneralTypeInformation(final FBType dstType, final FBType srcType) {
		dstType.setName(srcType.getName());
		dstType.setComment(srcType.getComment());
		dstType.setCompilerInfo(srcType.getCompilerInfo());
		dstType.setInterfaceList(srcType.getInterfaceList());
		dstType.setIdentification(srcType.getIdentification());
		dstType.getVersionInfo().addAll(srcType.getVersionInfo());
		dstType.setService(srcType.getService());
		dstType.getAttributes().addAll(srcType.getAttributes());
	}

	/**
	 * This method parses a compositeFBType.
	 *
	 * @param type - the CompositeFBType that is being parsed
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private void parseFBNetwork(final CompositeFBType type) throws TypeImportException, XMLStreamException {
		final FBNetwork fbNetwork = LibraryElementFactory.eINSTANCE.createFBNetwork();
		type.setFBNetwork(fbNetwork);
		addAdaptersToFBNetwork(fbNetwork);
		final FBNetworkImporter fbnInmporter = new FBNetworkImporter(this, fbNetwork, type.getInterfaceList());
		fbnInmporter.parseFBNetwork(LibraryElementTags.FBNETWORK_ELEMENT);
	}

	private void addAdaptersToFBNetwork(final FBNetwork fbNetwork) {
		getElement().getInterfaceList().getPlugs().forEach(plg -> addAdapterFBToNetwork(fbNetwork, plg));
		getElement().getInterfaceList().getSockets().forEach(sct -> addAdapterFBToNetwork(fbNetwork, sct));
	}

	private static void addAdapterFBToNetwork(final FBNetwork fbNetwork, final AdapterDeclaration adapterDecl) {
		final AdapterFB adapterFB = adapterDecl.getAdapterFB();
		fbNetwork.getNetworkElements().add(adapterFB);
	}

	/**
	 * This method converts a FBType to a CompositeFBType.
	 *
	 * @param type - The FBType that is being converted to CompositeFBType
	 *
	 * @return - A FBType that is converted
	 */
	private static FBType convertToCompositeType(final FBType type) {
		final CompositeFBType compositeType = LibraryElementFactory.eINSTANCE.createCompositeFBType();
		copyGeneralTypeInformation(compositeType, type);
		return compositeType;
	}

	/**
	 * This method parses a BasicFBType.
	 *
	 * @param type - the basicFBType that is being parsed
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private void parseBasicFB(final BasicFBType type) throws TypeImportException, XMLStreamException {
		processChildren(LibraryElementTags.BASIC_F_B_ELEMENT, name -> handleBasicFBChildren(type, name));

	}

	private boolean handleBasicFBChildren(final BasicFBType type, final String name)
			throws TypeImportException, XMLStreamException {
		if (LibraryElementTags.ECC_ELEMENT.equals(name)) {
			parseECC(type);
			return true;
		}
		return handleBaseFBChildren(type, name);
	}

	/**
	 * This method parses a SimpleFBType.
	 *
	 * @param type - the simpleFBType that is being parsed
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private void parseSimpleFB(final SimpleFBType type) throws TypeImportException, XMLStreamException {
		processChildren(LibraryElementTags.SIMPLE_F_B_ELEMENT, name -> handleSimpleFBChildren(type, name));
		// create SimpleECSTate Elements if needed
		// (this implicitly converts old type files)
		final var inEvents = type.getInterfaceList().getEventInputs();
		if (inEvents.size() > type.getSimpleECStates().size()) {
			final var outEvents = type.getInterfaceList().getEventOutputs();
			final Event stdOutEvent = !outEvents.isEmpty() ? outEvents.get(0) : null;
			final var inEventSet = new LinkedHashSet<>(inEvents);
			for (final var state : type.getSimpleECStates()) {
				inEventSet.remove(state.getInputEvent());
			}
			for (final Event inEvent : inEventSet) {
				final var state = LibraryElementFactory.eINSTANCE.createSimpleECState();
				state.setName(inEvent.getName());
				state.setInputEvent(inEvent);
				final var action = LibraryElementFactory.eINSTANCE.createSimpleECAction();
				action.setAlgorithm(inEvent.getName());
				action.setOutput(stdOutEvent);
				state.getSimpleECActions().add(action);
				type.getSimpleECStates().add(state);
			}
		}

	}

	private boolean handleSimpleFBChildren(final SimpleFBType type, final String name)
			throws TypeImportException, XMLStreamException {
		if (LibraryElementTags.ECSTATE_ELEMENT.equals(name)) {
			parseSimpleECState(type);
			return true;
		}
		return handleBaseFBChildren(type, name);
	}

	private boolean handleBaseFBChildren(final BaseFBType type, final String name)
			throws TypeImportException, XMLStreamException {
		switch (name) {
		case LibraryElementTags.INTERNAL_VARS_ELEMENT:
			parseInternalVars(type);
			break;
		case LibraryElementTags.INTERNAL_CONST_VARS_ELEMENT:
			parseInternalConstVars(type);
			break;
		case LibraryElementTags.ALGORITHM_ELEMENT:
			final Algorithm alg = parseAlgorithm();
			if (null != alg) {
				type.getCallables().add(alg);
				final List<ECAction> list = algorithmNameECActionMapping.get(alg.getName());
				if (null != list) {
					for (final ECAction action : list) {
						action.setAlgorithm(alg.getName());
					}
				}
			}
			break;
		case LibraryElementTags.METHOD_ELEMENT:
			final Method method = parseMethod();
			if (null != method) {
				type.getCallables().add(method);
			}
			break;
		case LibraryElementTags.ATTRIBUTE_ELEMENT:
			parseGenericAttributeNode(getElement());
			proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
			break;
		default:
			return false;
		}
		return true;
	}

	/**
	 * This method parses an Algorithm.
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private Algorithm parseAlgorithm() throws TypeImportException, XMLStreamException {
		final String name = getAttributeValue(LibraryElementTags.NAME_ATTRIBUTE);
		final String comment = getAttributeValue(LibraryElementTags.COMMENT_ATTRIBUTE);

		Algorithm retVal = null;
		while (getReader().hasNext()) {
			final int event = getReader().next();
			if (XMLStreamConstants.START_ELEMENT == event) {

				switch (getReader().getLocalName()) {
				case LibraryElementTags.FBD_ELEMENT, LibraryElementTags.LD_ELEMENT ->
					throw new TypeImportException("Algorithm: Unsupported Algorithmtype (only ST and Other possible)!"); //$NON-NLS-1$
				case LibraryElementTags.ST_ELEMENT -> {
					retVal = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
					parseST((STAlgorithm) retVal);
				}
				case LibraryElementTags.OTHER_ELEMENT -> {
					retVal = LibraryElementFactory.eINSTANCE.createOtherAlgorithm();
					parseOtherAlg((OtherAlgorithm) retVal);
				}
				default -> throw unknownXMLChildException();
				}

			} else if (XMLStreamConstants.END_ELEMENT == event) {
				if (!getReader().getLocalName().equals(LibraryElementTags.ALGORITHM_ELEMENT)) {
					throw new XMLStreamException("Unexpected xml end tag found in " //$NON-NLS-1$
							+ LibraryElementTags.ALGORITHM_ELEMENT + ": " + getReader().getLocalName()); //$NON-NLS-1$
				}
				// we came to the end
				break;
			}
		}

		if (null != retVal) {
			retVal.setName(name);
			retVal.setComment(comment);
		}
		return retVal;
	}

	/**
	 * Parses the other alg.
	 *
	 * @param alg the other
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private void parseOtherAlg(final OtherAlgorithm alg) throws TypeImportException, XMLStreamException {
		final String language = getAttributeValue(LibraryElementTags.LANGUAGE_ATTRIBUTE);
		if (null == language) {
			throw new TypeImportException(Messages.FBTImporter_OTHER_ALG_MISSING_LANG_EXCEPTION);
		}
		alg.setLanguage(language);

		parseAlgorithmText(alg);
		proceedToEndElementNamed(LibraryElementTags.OTHER_ELEMENT);
	}

	/**
	 * This method parses a STAlgorithm.
	 *
	 * @param st - the STAlgorithm being parsed
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private void parseST(final STAlgorithm st) throws XMLStreamException {
		parseAlgorithmText(st);
		proceedToEndElementNamed(LibraryElementTags.ST_ELEMENT);
	}

	private void parseAlgorithmText(final TextAlgorithm alg) throws XMLStreamException {
		final String text = getAttributeValue(LibraryElementTags.TEXT_ATTRIBUTE);
		if (null != text) {
			alg.setText(text);
		} else {
			alg.setText(readCDataSection());
		}
	}

	/**
	 * This method parses a Method.
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private Method parseMethod() throws TypeImportException, XMLStreamException {
		final String name = getAttributeValue(LibraryElementTags.NAME_ATTRIBUTE);
		final String comment = getAttributeValue(LibraryElementTags.COMMENT_ATTRIBUTE);

		DataType type = null;
		final String typeName = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		if (null != typeName && !typeName.isEmpty()) {
			type = getDataType(typeName);
		}

		Method retVal = null;
		while (getReader().hasNext()) {
			final int event = getReader().next();
			if (XMLStreamConstants.START_ELEMENT == event) {

				switch (getReader().getLocalName()) {
				case LibraryElementTags.FBD_ELEMENT, LibraryElementTags.LD_ELEMENT:
					throw new TypeImportException("Method: Unsupported type (only ST and Other possible)!"); //$NON-NLS-1$
				case LibraryElementTags.ST_ELEMENT:
					retVal = LibraryElementFactory.eINSTANCE.createSTMethod();
					parseSTMethod((STMethod) retVal);
					break;
				case LibraryElementTags.OTHER_ELEMENT:
					retVal = LibraryElementFactory.eINSTANCE.createOtherMethod();
					parseOtherMethod((OtherMethod) retVal);
					break;
				case LibraryElementTags.INPUT_VARS_ELEMENT:
					if (retVal == null) {
						throw unknownXMLChildException();
					}
					parseParameterList(LibraryElementTags.INPUT_VARS_ELEMENT, retVal.getInputParameters(), true);
					break;
				case LibraryElementTags.OUTPUT_VARS_ELEMENT:
					if (retVal == null) {
						throw unknownXMLChildException();
					}
					parseParameterList(LibraryElementTags.OUTPUT_VARS_ELEMENT, retVal.getOutputParameters(), false);
					break;
				case LibraryElementTags.INOUT_VARS_ELEMENT:
					if (retVal == null) {
						throw unknownXMLChildException();
					}
					parseParameterList(LibraryElementTags.INOUT_VARS_ELEMENT, retVal.getInOutParameters(), true);
					break;
				// legacy element (future parameters should be in one of the lists above)
				case LibraryElementTags.VAR_DECLARATION_ELEMENT:
					if (retVal == null) {
						throw unknownXMLChildException();
					}
					final VarDeclaration declaration = parseVarDeclaration();
					retVal.getInputParameters().add(declaration);
					break;
				default:
					throw unknownXMLChildException();
				}

			} else if (XMLStreamConstants.END_ELEMENT == event) {
				if (!getReader().getLocalName().equals(LibraryElementTags.METHOD_ELEMENT)) {
					throw new XMLStreamException("Unexpected xml end tag found in " //$NON-NLS-1$
							+ LibraryElementTags.METHOD_ELEMENT + ": " + getReader().getLocalName()); //$NON-NLS-1$
				}
				// we came to the end
				break;
			}
		}

		if (null != retVal) {
			retVal.setName(name);
			retVal.setComment(comment);
		}
		if (retVal instanceof final TextMethod textMethod) {
			textMethod.setReturnType(type);
		}
		return retVal;
	}

	private void parseParameterList(final String nodeName, final EList<? super VarDeclaration> varList,
			final boolean input) throws TypeImportException, XMLStreamException {
		processChildren(nodeName, name -> {
			if (name.equals(LibraryElementTags.VAR_DECLARATION_ELEMENT)) {
				final VarDeclaration v = parseVarDeclaration();
				varList.add(v);
				v.setIsInput(input);
				return true;
			}
			return false;
		});
	}

	protected XMLStreamException unknownXMLChildException() {
		return new XMLStreamException("Unexpected xml child (" + getReader().getLocalName() + ") found!"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Parses the other method.
	 *
	 * @param method the other
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private void parseOtherMethod(final OtherMethod method) throws TypeImportException, XMLStreamException {
		final String language = getAttributeValue(LibraryElementTags.LANGUAGE_ATTRIBUTE);
		if (null == language) {
			throw new TypeImportException(Messages.FBTImporter_OTHER_METHOD_MISSING_LANG_EXCEPTION);
		}
		method.setLanguage(language);

		parseMethodText(method);
		proceedToEndElementNamed(LibraryElementTags.OTHER_ELEMENT);
	}

	/**
	 * This method parses a STMethod.
	 *
	 * @param st - the STMethod being parsed
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private void parseSTMethod(final STMethod method) throws XMLStreamException {
		parseMethodText(method);
		proceedToEndElementNamed(LibraryElementTags.ST_ELEMENT);
	}

	private void parseMethodText(final TextMethod method) throws XMLStreamException {
		final String text = getAttributeValue(LibraryElementTags.TEXT_ATTRIBUTE);
		if (null != text) {
			method.setText(text);
		} else {
			method.setText(readCDataSection());
		}
	}

	/**
	 * This method parses an ECC.
	 *
	 * @param type - the BasicFBType containing the ECC being parsed
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private void parseECC(final BasicFBType type) throws TypeImportException, XMLStreamException {
		final ECC ecc = LibraryElementFactory.eINSTANCE.createECC();

		processChildren(LibraryElementTags.ECC_ELEMENT, name -> {
			switch (name) {
			case LibraryElementTags.ECSTATE_ELEMENT -> parseECState(ecc); // IEC 61499 ->
			case LibraryElementTags.ECTRANSITION_ELEMENT -> parseECTransition(ecc);
			default -> {
				return false;
			}
			}

			return true;
		});
		type.setECC(ecc);
	}

	/**
	 * This method parses an ECTransition.
	 *
	 * @param ecc - the ECC containing the ECTransition being parsed
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private void parseECTransition(final ECC ecc) throws TypeImportException, XMLStreamException {
		final ECTransition ecTransition = LibraryElementFactory.eINSTANCE.createECTransition();
		final String source = getAttributeValue(LibraryElementTags.SOURCE_ATTRIBUTE);
		if (null != source) {
			final ECState state = ecStates.get(source);
			if (state != null) {
				ecTransition.setSource(state);
			}
		} else {
			throw new TypeImportException(Messages.FBTImporter_ECTRANSITION_SOURCE_EXCEPTION);
		}
		final String destination = getAttributeValue(LibraryElementTags.DESTINATION_ATTRIBUTE);
		if (null == destination) {
			throw new TypeImportException(Messages.FBTImporter_ECTRANSITION_DEST_EXCEPTION);
		}
		final ECState state = ecStates.get(destination);
		if (state != null) {
			ecTransition.setDestination(state);
		}
		final String condition = getAttributeValue(LibraryElementTags.CONDITION_ATTRIBUTE);
		if (null == condition) {
			throw new TypeImportException(Messages.FBTImporter_ECTRANASITION_CONDITION_EXCEPTION);
		}
		validateTransitionCondition(ecTransition, condition);
		readCommentAttribute().ifPresent(ecTransition::setComment);
		getXandY(ecTransition);
		proceedToEndElementNamed(LibraryElementTags.ECTRANSITION_ELEMENT);
		ecc.getECTransition().add(ecTransition);
	}

	private void validateTransitionCondition(final ECTransition ecTransition, final String condition) {
		Event event;
		String expression;

		// first, try splitting according to 1st edition
		String[] split = condition.split("&", 2); //$NON-NLS-1$
		event = getInterfaceListImporter().getInputEvents().get(split[0].trim());
		if (event != null) {
			// remainder is expression
			expression = split.length > 1 ? split[1].trim() : ""; //$NON-NLS-1$
		} else { // otherwise, try splitting according to 2nd edition
			split = condition.split("\\[", 2); //$NON-NLS-1$
			event = getInterfaceListImporter().getInputEvents().get(split[0].trim());
			if (event != null) {
				// remainder is expression (except trailing ']')
				expression = split.length > 1 ? split[1].substring(0, split[1].lastIndexOf(']')).trim() : ""; //$NON-NLS-1$
			} else // no match (all is expression)
			if (condition.startsWith("[")) { //$NON-NLS-1$
				expression = condition.substring(1, condition.lastIndexOf(']'));
			} else {
				expression = condition;
			}
		}

		ecTransition.setConditionEvent(event);
		ecTransition.setConditionExpression(expression);
	}

	/**
	 * This method parses an ECState.
	 *
	 * @param ecc - the ECC containing the ECState being parsed
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private void parseECState(final ECC ecc) throws TypeImportException, XMLStreamException {
		final ECState state = LibraryElementFactory.eINSTANCE.createECState();
		readNameCommentAttributes(state);
		getXandY(state);

		processChildren(LibraryElementTags.ECSTATE_ELEMENT, name -> {
			if (LibraryElementTags.ECACTION_ELEMENT.equals(name)) {
				parseECAction(state);
				return true;
			}
			return false;
		});

		if (ecc.getECState().isEmpty()) {
			ecc.setStart(state);
		}
		ecc.getECState().add(state);
		ecStates.put(state.getName(), state);
	}

	/**
	 * This method parses an ECAction.
	 *
	 * @param type - the ECState belonging to the ECAction being parsed
	 * @throws XMLStreamException
	 */
	private void parseECAction(final ECState type) throws XMLStreamException {
		final ECAction ecAction = LibraryElementFactory.eINSTANCE.createECAction();
		final String algorithm = getAttributeValue(LibraryElementTags.ALGORITHM_ELEMENT);
		if (null != algorithm) {
			if (algorithmNameECActionMapping.containsKey(algorithm)) {
				algorithmNameECActionMapping.get(algorithm).add(ecAction);
			} else {
				final List<ECAction> temp = new ArrayList<>();
				temp.add(ecAction);
				algorithmNameECActionMapping.put(algorithm, temp);
			}
		}
		final String output = getAttributeValue(LibraryElementTags.OUTPUT_ATTRIBUTE);
		if (null != output) {
			final Event outp = getInterfaceListImporter().getOutputEvents().get(output);
			if (null != outp) {
				ecAction.setOutput(outp);
			}
		}
		proceedToEndElementNamed(LibraryElementTags.ECACTION_ELEMENT);
		type.getECAction().add(ecAction);
	}

	/**
	 * This method parses an SimpleECState.
	 *
	 * @param type - the SimpleFBType containing the SimpleECState being parsed
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private void parseSimpleECState(final SimpleFBType type) throws TypeImportException, XMLStreamException {
		final SimpleECState state = LibraryElementFactory.eINSTANCE.createSimpleECState();
		readNameCommentAttributes(state);

		state.setInputEvent(type.getInterfaceList().getEvent(state.getName()));

		processChildren(LibraryElementTags.ECSTATE_ELEMENT, name -> {
			if (LibraryElementTags.ECACTION_ELEMENT.equals(name)) {
				parseSimpleECAction(state);
				return true;
			}
			return false;
		});

		type.getSimpleECStates().add(state);
	}

	/**
	 * This method parses an SimpleECAction.
	 *
	 * @param type - the SimpleECState belonging to the SimpleECAction being parsed
	 * @throws XMLStreamException
	 */
	private void parseSimpleECAction(final SimpleECState type) throws XMLStreamException {
		final SimpleECAction ecAction = LibraryElementFactory.eINSTANCE.createSimpleECAction();
		ecAction.setAlgorithm(getAttributeValue(LibraryElementTags.ALGORITHM_ELEMENT));
		final String output = getAttributeValue(LibraryElementTags.OUTPUT_ATTRIBUTE);
		if (null != output) {
			final Event outp = getInterfaceListImporter().getOutputEvents().get(output);
			if (null != outp) {
				ecAction.setOutput(outp);
			}
		}
		proceedToEndElementNamed(LibraryElementTags.ECACTION_ELEMENT);
		type.getSimpleECActions().add(ecAction);
	}

	/**
	 * This method parses Internal Variables of a BaseFBType.
	 *
	 * @param type - the BaseFBType of which the Internal Variables will be parsed
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private void parseInternalVars(final BaseFBType type) throws TypeImportException, XMLStreamException {
		processChildren(LibraryElementTags.INTERNAL_VARS_ELEMENT, name -> {
			if (LibraryElementTags.VAR_DECLARATION_ELEMENT.equals(name)) {
				final VarDeclaration v = parseVarDeclaration();
				type.getInternalVars().add(v);
				return true;
			}
			if (LibraryElementTags.FB_ELEMENT.equals(name)) {
				parseInternalFB(type);
				return true;
			}
			return false;
		});
	}

	/**
	 * This method parses Internal Constant Variables of a BaseFBType.
	 *
	 * @param type - the BaseFBType of which the Internal Constant Variables will be
	 *             parsed
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	private void parseInternalConstVars(final BaseFBType type) throws TypeImportException, XMLStreamException {
		processChildren(LibraryElementTags.INTERNAL_CONST_VARS_ELEMENT, name -> {
			if (LibraryElementTags.VAR_DECLARATION_ELEMENT.equals(name)) {
				type.getInternalConstVars().add(parseVarDeclaration());
				return true;
			}
			return false;
		});
	}

	private void parseInternalFB(final BaseFBType type) throws TypeImportException, XMLStreamException {
		final FB fb = LibraryElementFactory.eINSTANCE.createFB();
		readNameCommentAttributes(fb);
		final String typeFbElement = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		final FBTypeEntry entry = getTypeEntry(typeFbElement, getTypeLibrary()::getFBTypeEntry,
				LibraryElementPackage.Literals.FB_TYPE);
		fb.setTypeEntry(entry);
		fb.setInterface(entry.getInterface().instanceCopy());
		type.getInternalFbs().add(fb);
		parseFBChildren(fb, LibraryElementTags.FB_ELEMENT);
	}

	/**
	 * This method parses a FBType to a BasicFBType.
	 *
	 * @param type - the FBType being parsed to BasicFBType
	 *
	 * @return the basicFBType
	 */
	private static FBType convertToBasicType(final FBType type) {
		final BasicFBType basicType = LibraryElementFactory.eINSTANCE.createBasicFBType();
		copyGeneralTypeInformation(basicType, type);
		return basicType;
	}

	/**
	 * This method parses a FBType to a SimpleFBType.
	 *
	 * @param type - the FBType being parsed to SimpleFBType
	 *
	 * @return the simpleFBType
	 */
	private static FBType convertToSimpleType(final FBType type) {
		final SimpleFBType simpleType = LibraryElementFactory.eINSTANCE.createSimpleFBType();
		copyGeneralTypeInformation(simpleType, type);
		return simpleType;
	}
}
