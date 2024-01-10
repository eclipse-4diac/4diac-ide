/*******************************************************************************
 * Copyright (c) 2020, 2021 Johannes Kepler University Linz, fortiss GmbH.
 *               2022 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - test for forte_ng
 *   Kirill Dorofeev
 *     - tests for lua exporter
 *   Martin Jobst
 *     - adopt new ST language support
 *******************************************************************************/

package org.eclipse.fordiac.ide.test.export;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.export.ExportException;
import org.eclipse.fordiac.ide.export.IExportTemplate;
import org.eclipse.fordiac.ide.export.forte_lua.ForteLuaExportFilter;
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportFilter;
import org.eclipse.fordiac.ide.export.forte_ng.algorithm.OtherAlgorithmSupportFactory;
import org.eclipse.fordiac.ide.export.forte_ng.st.StructuredTextSupportFactory;
import org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil;
import org.eclipse.fordiac.ide.export.language.ILanguageSupport;
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory;
import org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstantsStandaloneSetup;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmStandaloneSetup;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunctionStandaloneSetup;
import org.eclipse.fordiac.ide.test.model.typelibrary.FBTypeEntryMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public abstract class ExporterTestBase<T extends FBType> {

	protected static final String ALGORITHM_NAME = "ALG"; //$NON-NLS-1$

	protected static final String EXPORTED_ALGORITHM_NAME = "alg_" + ALGORITHM_NAME; //$NON-NLS-1$

	protected static final String VARIABLE_NAME = "variable"; //$NON-NLS-1$
	protected static final String VARIABLE2_NAME = "variable2"; //$NON-NLS-1$
	protected static final String VARIABLE3_NAME = "variable3"; //$NON-NLS-1$

	protected static final String EVENT_INPUT_NAME = "EI1"; //$NON-NLS-1$
	protected static final String EVENT_OUTPUT_NAME = "EO1"; //$NON-NLS-1$
	protected static final String DATA_INPUT_NAME = "DI1"; //$NON-NLS-1$
	protected static final String DATA_OUTPUT_NAME = "DO1"; //$NON-NLS-1$
	protected static final String ADAPTER_EVENT_INPUT_NAME = "AEI1"; //$NON-NLS-1$
	protected static final String ADAPTER_EVENT_OUTPUT_NAME = "AEO1"; //$NON-NLS-1$
	protected static final String ADAPTER_DATA_INPUT_NAME = "ADI1"; //$NON-NLS-1$
	protected static final String ADAPTER_DATA_OUTPUT_NAME = "ADO1"; //$NON-NLS-1$
	protected static final String ADAPTER_SOCKET_NAME = "AdapterSocket"; //$NON-NLS-1$
	protected static final String ADAPTER_PLUG_NAME = "AdapterPlug"; //$NON-NLS-1$

	protected static final String EXPORTED_VARIABLE_NAME = addExportPrefix(VARIABLE_NAME);
	protected static final String EXPORTED_VARIABLE2_NAME = addExportPrefix(VARIABLE2_NAME);

	protected static final String BASICFUNCTIONBLOCK_NAME = "functionblock"; //$NON-NLS-1$
	protected static final String COMPOSITEFUNCTIONBLOCK_NAME = "compositefunctionblock"; //$NON-NLS-1$
	protected static final String ADAPTERFUNCTIONBLOCK_NAME = "TEST_ADAPTER"; //$NON-NLS-1$
	protected static final String EXPORTED_FUNCTIONBLOCK_NAME = "FORTE_" + BASICFUNCTIONBLOCK_NAME; //$NON-NLS-1$

	protected static final int INDEX_START = 0;
	protected static final int SIZE_LWORD = 64;
	protected static final int SIZE_DWORD = 32;
	protected static final int SIZE_WORD = 16;
	protected static final int SIZE_BYTE = 8;
	protected static final int SIZE_BOOL = 1;

	private static final DataTypeLibrary dataTypeLib = new DataTypeLibrary();
	protected T functionBlock;
	protected Event inputEvent;
	protected Event outputEvent;
	protected VarDeclaration inputData;
	protected VarDeclaration outputData;
	private List<String> errors;

	@SuppressWarnings("unused")
	@BeforeAll
	public static void setup() {
		new DataTypeLibrary();
		GlobalConstantsStandaloneSetup.doSetup();
		STFunctionStandaloneSetup.doSetup();
		STAlgorithmStandaloneSetup.doSetup();
		OtherAlgorithmSupportFactory.register();
		StructuredTextSupportFactory.register();
	}

	/** generate code from an algorithm stored in a function block
	 *
	 * @param fb            reference to the function block
	 * @param algorithmName name of the algorithm stored in the function block
	 * @param errorList     reference to List where error messages are stored
	 *
	 * @return the generated code or null on error */
	@SuppressWarnings("static-method")
	public CharSequence generateAlgorithm(final FBType fb, final String algorithmName, final List<String> errorList) {
		CharSequence result = null;
		final ILanguageSupport languageSupport = ILanguageSupportFactory.createLanguageSupport("forte_ng", //$NON-NLS-1$
				((BaseFBType) fb).getAlgorithmNamed(algorithmName));
		try {
			result = languageSupport.generate(Collections.emptyMap());
			errorList.addAll(languageSupport.getErrors());
		} catch (final ExportException e) {
			errorList.add(e.getMessage());
		}
		// remove #line directives
		if (result != null) {
			result = result.toString().replaceAll("(?m)^\\h*#line.*$\\R", ""); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return result;
	}

	/** generate code from an expression with variables attached to a functionblock
	 *
	 * @param fb         reference to the function block
	 * @param expression expression to generate from
	 * @param errorList  reference to List where error messages are stored
	 *
	 * @return the generated code or null on error */
	public CharSequence generateExpression(final FBType fb, final String expression, final List<String> errorList) {
		CharSequence result = null;
		final ECC ecc = ((BasicFBType) functionBlock).getECC();
		final ECTransition transition = LibraryElementFactory.eINSTANCE.createECTransition();
		transition.setConditionExpression(expression);
		transition.setSource(ecc.getStart());
		transition.setDestination(ecc.getStart());
		ecc.getECTransition().add(transition);
		final ILanguageSupport languageSupport = ILanguageSupportFactory.createLanguageSupport("forte_ng", transition); //$NON-NLS-1$
		try {
			result = languageSupport.generate(Collections.emptyMap());
			errorList.addAll(languageSupport.getErrors());
		} catch (final ExportException e) {
			errorList.add(e.getMessage());
		}
		return result;
	}

	protected static class FileObject {
		private final String name;
		private final CharSequence data;
		private final List<String> errors;
		private final List<String> warnings;
		private final List<String> infos;

		FileObject(final String name, final CharSequence data, final List<String> errors, final List<String> warnings,
				final List<String> infos) {
			this.name = name;
			this.data = data;
			this.errors = errors;
			this.warnings = warnings;
			this.infos = infos;
		}

		public String getName() {
			return name;
		}

		public CharSequence getData() {
			return data;
		}

		public List<String> getErrors() {
			return errors;
		}

		public List<String> getWarnings() {
			return warnings;
		}

		public List<String> getInfos() {
			return infos;
		}
	}

	/** generate code from a functionblock using ng generator
	 *
	 * @param fb reference to the function block
	 *
	 * @return the generated code or null on error */
	public List<FileObject> generateFunctionBlock(final LibraryElement fb) {
		final Set<IExportTemplate> templates = (new ForteNgExportFilter() {
			Set<IExportTemplate> getTemplateSet(final LibraryElement type) {
				return getTemplates(type.getName(), type);
			}
		}).getTemplateSet(functionBlock);

		final List<FileObject> result = new ArrayList<>(2);

		for (final IExportTemplate template : templates) {
			try {
				CharSequence content = template.generate();
				if (content != null) {
					content = content.toString().lines().map(String::stripTrailing)
							.collect(Collectors.joining(System.lineSeparator()));
				}
				result.add(new FileObject(template.getName(), content, template.getErrors(), template.getWarnings(),
						template.getInfos()));
			} catch (final ExportException e) {
				result.add(new FileObject(template.getName(), e.getMessage(), template.getErrors(),
						template.getWarnings(), template.getInfos()));
			}
		}

		return result;
	}

	/** generates lua code from a function block
	 *
	 * @param fb reference to the function block
	 *
	 * @return the generated code or null on error */
	public static String generateLuaString(final LibraryElement fb) {
		return new ForteLuaExportFilter().createLUA(fb);
	}

	/** retrieve a reference to the function block
	 *
	 * @return function block object */
	public T getFunctionBlock() {
		return functionBlock;
	}

	/** retrieve a reference to an error list
	 *
	 * @return error list object */
	public List<String> getErrors() {
		return errors;
	}

	@BeforeEach
	/** clear all the variables that are specific to a single test */
	public void clearEnvironment() {
		// prepare a function block object including an interface list
		setupFunctionBlock();

		// clear the errors-list
		errors = new ArrayList<>();
	}

	abstract void setupFunctionBlock();

	protected static FBTypeEntry prepareTypeEntryWithTypeLib() {
		return new FBTypeEntryMock(null, TypeLibraryManager.INSTANCE.getTypeLibrary(null), null);
	}

	/** create a VarDeclaration with given name and data-type
	 *
	 * @param variableName name of the variable to be created
	 * @param dataType     data-type of the variable to be created
	 *
	 * @return the created variable-object */
	protected static VarDeclaration createVarDeclaration(final String variableName, final String dataType) {
		final VarDeclaration variable = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		variable.setName(variableName);
		variable.setType(dataTypeLib.getType(dataType));
		return variable;
	}

	/** check if an error-list is empty and raise an assertion if not empty
	 *
	 * @param errors list of errormessages */
	protected static void assertNoErrors(final List<String> errors) {
		assertTrue(errors.isEmpty(),
				(MessageFormat.format("No error messages expected. First error message received: {0}.", //$NON-NLS-1$
						(!errors.isEmpty() ? errors.get(0) : "")) //$NON-NLS-1$
				));
	}

	/** check if an error-list is not empty and raise an assertion if empty
	 *
	 * @param errors list of errormessages */
	protected static void assertErrors(final List<String> errors) {
		assertFalse(errors.isEmpty(), "Error messages expected."); //$NON-NLS-1$
	}

	/** create a STAlgorithm with given name and content
	 *
	 * @param algorithmName name of the algorithm to be created
	 * @param algorithmText content of the algorithm to be created
	 *
	 * @return the created algorithm-object */
	protected static STAlgorithm createSTAlgorithm(final String algorithmName, final String algorithmText) {
		final STAlgorithm stAlg = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		stAlg.setName(algorithmName);
		stAlg.setText(algorithmText);
		return stAlg;
	}

	/** create a OtherAlgorithm with given name, language and content
	 *
	 * @param algorithmName     name of the algorithm to be created
	 * @param algorithmText     content of the algorithm to be created
	 * @param algorithmLanguage language of the algorithm to be created
	 *
	 * @return the created algorithm-object */
	protected static OtherAlgorithm createOtherAlgorithm(final String algorithmName, final String algorithmText,
			final String algorithmLanguage) {
		final OtherAlgorithm alg = LibraryElementFactory.eINSTANCE.createOtherAlgorithm();
		alg.setName(algorithmName);
		alg.setText(algorithmText);
		alg.setLanguage(algorithmLanguage);
		return alg;
	}

	/** cast a given algorithm to STAlgorithm, but check first if it's the right type of object
	 *
	 * @param algorithm
	 *
	 * @return the cast algorithm-object */
	protected static STAlgorithm castAlgorithm(final Algorithm algorithm) {
		assertTrue(algorithm instanceof STAlgorithm);
		return (STAlgorithm) algorithm;
	}

	/** check if an error-list contains a set of error messages; raise an assertion if emtpy
	 *
	 * @param errors   list of errormessages
	 * @param messages list of messages to check for */
	protected static void assertErrorMessages(final List<String> errors, final String... messages) {
		for (final String message : messages) {
			boolean contained = false;
			for (final String error : errors) {
				contained = contained || error.contains(message);
			}
			assertTrue(contained, MessageFormat.format("Missing error message: {0}", message)); //$NON-NLS-1$
		}
	}

	/** add export prefix to name
	 *
	 * @param name name to add the prefix to
	 * @return name with prefix added */
	protected static String addExportPrefix(final String name) {
		return ForteNgExportUtil.VARIABLE_EXPORT_PREFIX + name;
	}

	/** syntactic sugar: create a Object[] for a test case
	 *
	 * @param obj... vararg to create a Object[]
	 * @return the created Object[] */
	protected static Object[] testCase(final Object... a) {
		return a;
	}

	/** retrieve the size of datatypes with defined bit representation
	 *
	 * @param type name of the datatype
	 * @return number of bits in the representation */
	static int getSize(final String type) {
		return switch (type) {
		case FordiacKeywords.LWORD -> SIZE_LWORD;
		case FordiacKeywords.DWORD -> SIZE_DWORD;
		case FordiacKeywords.WORD -> SIZE_WORD;
		case FordiacKeywords.BYTE -> SIZE_BYTE;
		case FordiacKeywords.BOOL -> SIZE_BOOL;
		default -> 0;
		};
	}

	/** compute the number of elements of a datatype with defined bit representation can fit inside another datatype
	 * with defined bit representation
	 *
	 * @param name of the source-datatype
	 * @param name of the accessing datatype
	 * @return number elements */
	protected static int indexStop(final String sourceType, final String accessType) {
		final int sourceSize = getSize(sourceType);
		final int accessSize = getSize(accessType);
		assert (accessSize != 0);
		return (sourceSize / accessSize) - 1;
	}

	protected void setupAdvancedInterface() {
		inputEvent = LibraryElementFactory.eINSTANCE.createEvent();
		inputEvent.setName(EVENT_INPUT_NAME);
		functionBlock.getInterfaceList().getEventInputs().add(inputEvent);
		outputEvent = LibraryElementFactory.eINSTANCE.createEvent();
		outputEvent.setName(EVENT_OUTPUT_NAME);
		functionBlock.getInterfaceList().getEventOutputs().add(outputEvent);
		inputData = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		inputData.setType(new DataTypeLibrary().getType(FordiacKeywords.INT));
		inputData.setName(DATA_INPUT_NAME);
		functionBlock.getInterfaceList().getInputVars().add(inputData);
		outputData = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		outputData.setType(new DataTypeLibrary().getType(FordiacKeywords.INT));
		outputData.setName(DATA_OUTPUT_NAME);
		functionBlock.getInterfaceList().getOutputVars().add(outputData);

		final AdapterDeclaration adapterSocketDecl = LibraryElementFactory.eINSTANCE.createAdapterDeclaration();
		final AdapterType adapterType = LibraryElementFactory.eINSTANCE.createAdapterType();
		final ExporterTestAdapterFBType adapterFBType = new ExporterTestAdapterFBType();
		adapterFBType.setupFunctionBlock();
		adapterType.setAdapterFBType(adapterFBType.getFunctionBlock());
		adapterType.setName(adapterFBType.getFunctionBlock().getName());
		adapterSocketDecl.setType(adapterType);
		adapterSocketDecl.setName(ADAPTER_SOCKET_NAME);
		adapterSocketDecl.setIsInput(true);
		functionBlock.getInterfaceList().getSockets().add(adapterSocketDecl);
		final AdapterDeclaration adapterPlugDecl = LibraryElementFactory.eINSTANCE.createAdapterDeclaration();
		adapterPlugDecl.setType(adapterType);
		adapterPlugDecl.setName(ADAPTER_PLUG_NAME);
		functionBlock.getInterfaceList().getPlugs().add(adapterPlugDecl);

	}
}
