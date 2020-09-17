/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
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
 *******************************************************************************/

package org.eclipse.fordiac.ide.export.forte_ng.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.fordiac.ide.export.ExportException;
import org.eclipse.fordiac.ide.export.IExportTemplate;
import org.eclipse.fordiac.ide.export.forte_ng.ForteLibraryElementTemplate;
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportFilter;
import org.eclipse.fordiac.ide.export.forte_ng.st.STAlgorithmFilter;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompilableType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.structuredtext.StructuredTextStandaloneSetup;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.xtext.fbt.FBTypeStandaloneSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public abstract class ForteNgTestBase<T extends FBType> {

	static final String ALGORITHM_NAME = "algorithm"; //$NON-NLS-1$

	static final String EXPORTED_ALGORITHM_NAME = "alg_" + ALGORITHM_NAME; //$NON-NLS-1$

	static final String VARIABLE_NAME = "variable"; //$NON-NLS-1$
	static final String VARIABLE2_NAME = "variable2"; //$NON-NLS-1$

	static final String EXPORTED_VARIABLE_NAME = addExportPrefix(VARIABLE_NAME);
	static final String EXPORTED_VARIABLE2_NAME = addExportPrefix(VARIABLE2_NAME);

	static final String FUNCTIONBLOCK_NAME = "functionblock"; //$NON-NLS-1$
	static final String EXPORTED_FUNCTIONBLOCK_NAME = "FORTE_" + FUNCTIONBLOCK_NAME; //$NON-NLS-1$

	static final int INDEX_START = 0;
	static final int SIZE_LWORD = 64;
	static final int SIZE_DWORD = 32;
	static final int SIZE_WORD = 16;
	static final int SIZE_BYTE = 8;
	static final int SIZE_BOOL = 1;

	private static final DataTypeLibrary dataTypeLib = new DataTypeLibrary();
	private final STAlgorithmFilter stAlgorithmFilter = new STAlgorithmFilter();
	protected T functionBlock;
	private List<String> errors;

	/**
	 * generate code from an algorithm stored in a function block
	 *
	 * @param fb            reference to the function block
	 * @param algorithmName name of the algorithm stored in the function block
	 * @param errorList     reference to List where error messages are stored
	 *
	 * @return the generated code or null on error
	 */
	public CharSequence generateAlgorithm(FBType fb, String algorithmName, List<String> errorList) {
		return stAlgorithmFilter.generate(castAlgorithm(((BasicFBType) fb).getAlgorithmNamed(algorithmName)),
				errorList);
	}

	/**
	 * generate code from an expression with variables attached to a functionblock
	 *
	 * @param fb         reference to the function block
	 * @param expression expression to generate from
	 * @param errorList  reference to List where error messages are stored
	 *
	 * @return the generated code or null on error
	 */
	public CharSequence generateExpression(FBType fb, String expression, List<String> errorList) {
		return stAlgorithmFilter.generate(expression, ((BasicFBType) fb), errorList);
	}

	class FileObject {
		private final String name;
		private final CharSequence data;
		private final List<String> errors;
		private final List<String> warnings;
		private final List<String> infos;

		FileObject(String name, CharSequence data, List<String> errors, List<String> warnings, List<String> infos) {
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

	/**
	 * generate code from a functionblock
	 *
	 * @param fb reference to the function block
	 *
	 * @return the generated code or null on error
	 */
	public List<ForteNgTestBase<T>.FileObject> generateFunctionBlock(CompilableType fb) {

		final Set<? extends IExportTemplate> templates = (new ForteNgExportFilter() {
			Set<? extends IExportTemplate> getTemplateSet(LibraryElement type) {
				return getTemplates(type);
			}
		}).getTemplateSet(functionBlock);

		List<FileObject> result = new ArrayList<>(2);

		for (final IExportTemplate template : templates) {
			try {
				result.add(new FileObject(template.getName(), template.generate(), template.getErrors(),
						template.getWarnings(), template.getInfos()));
			} catch (ExportException e) {
				result.add(new FileObject(template.getName(), e.getMessage(), template.getErrors(),
						template.getWarnings(), template.getInfos()));
			}
		}

		return result;
	}

	/**
	 * retrieve a reference to the function block
	 *
	 * @return function block object
	 */
	public T getFunctionBlock() {
		return functionBlock;
	}

	/**
	 * retrieve a reference to an error list
	 *
	 * @return error list object
	 */
	public List<String> getErrors() {
		return errors;
	}

	@BeforeAll
	/**
	 * initialize the Equinox extension registry substitute
	 *
	 */
	public static void doSetup() {
		FBTypeStandaloneSetup.doSetup();
		StructuredTextStandaloneSetup.doSetup();
	}

	@BeforeEach
	/**
	 * clear all the variables that are specific to a single test
	 *
	 */
	public void clearEnvironment() {
		// prepare a function block object including an interface list
		setupFunctionBlock();

		// clear the errors-list
		errors = new ArrayList<>();
	}

	abstract void setupFunctionBlock();

	protected FBTypePaletteEntry preparePaletteWithTypeLib() {
		FBTypePaletteEntry pallEntry = PaletteFactory.eINSTANCE.createFBTypePaletteEntry();
		TypeLibrary typelib = TypeLibrary.getTypeLibrary(null);
		pallEntry.setPalette(typelib.getBlockTypeLib());
		return pallEntry;
	}

	/**
	 * create a VarDeclaration with given name and data-type
	 *
	 * @param variableName name of the variable to be created
	 * @param dataType     data-type of the variable to be created
	 *
	 * @return the created variable-object
	 */
	protected VarDeclaration createVarDeclaration(String variableName, String dataType) {
		VarDeclaration variable = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		variable.setName(variableName);
		variable.setType(dataTypeLib.getType(dataType));
		return variable;
	}

	/**
	 * check if an error-list is empty and raise an assertion if not empty
	 *
	 * @param errors list of errormessages
	 */
	protected static void assertNoErrors(List<String> errors) {
		assertTrue(errors.isEmpty(),
				(MessageFormat.format("No error messages expected. First error message received: {0}.", //$NON-NLS-1$
						(!errors.isEmpty() ? errors.get(0) : "")) //$NON-NLS-1$
				));
	}

	/**
	 * check if an error-list is not empty and raise an assertion if empty
	 *
	 * @param errors list of errormessages
	 */
	protected static void assertErrors(List<String> errors) {
		assertFalse(errors.isEmpty(), "Error messages expected."); //$NON-NLS-1$
	}

	/**
	 * create a STAlgorithm with given name and content
	 *
	 * @param algorithmName name of the algorithm to be created
	 * @param algorithmText content of the algorithm to be created
	 *
	 * @return the created algorithm-object
	 */
	protected STAlgorithm createSTAlgorithm(String algorithmName, String algorithmText) {
		STAlgorithm stAlg = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		stAlg.setName(algorithmName);
		stAlg.setText(algorithmText);
		return stAlg;
	}

	/**
	 * create a OtherAlgorithm with given name, language and content
	 *
	 * @param algorithmName     name of the algorithm to be created
	 * @param algorithmText     content of the algorithm to be created
	 * @param algorithmLanguage language of the algorithm to be created
	 *
	 * @return the created algorithm-object
	 */
	protected OtherAlgorithm createOtherAlgorithm(String algorithmName, String algorithmText,
			String algorithmLanguage) {
		final OtherAlgorithm alg = LibraryElementFactory.eINSTANCE.createOtherAlgorithm();
		alg.setName(algorithmName);
		alg.setText(algorithmText);
		alg.setLanguage(algorithmLanguage);
		return alg;
	}

	/**
	 * cast a given algorithm to STAlgorithm, but check first if it's the right type
	 * of object
	 *
	 * @param algorithm
	 *
	 * @return the cast algorithm-object
	 */
	protected STAlgorithm castAlgorithm(Algorithm algorithm) {
		assert (algorithm instanceof STAlgorithm);
		return (STAlgorithm) algorithm;
	}

	/**
	 * check if an error-list contains a set of error messages; raise an assertion
	 * if emtpy
	 *
	 * @param errors   list of errormessages
	 * @param messages list of messages to check for
	 */
	protected static void assertErrorMessages(List<String> errors, String... messages) {
		for (String message : messages) {
			Boolean contained = false;
			for (String error : errors) {
				contained = contained || error.contains(message);
			}
			assertTrue(contained, MessageFormat.format("Missing error message: {0}", message)); //$NON-NLS-1$
		}
	}

	/**
	 * add export prefix to name
	 *
	 * @param name name to add the prefix to
	 * @return name with prefix added
	 */
	protected static String addExportPrefix(String name) {
		return ForteLibraryElementTemplate.EXPORT_PREFIX + name;
	}

	/**
	 * retrieve the size of datatypes with defined bit representation
	 *
	 * @param type name of the datatype
	 * @return number of bits in the representation
	 */
	static int getSize(String type) {
		switch (type) {
		case FordiacKeywords.LWORD:
			return SIZE_LWORD;
		case FordiacKeywords.DWORD:
			return SIZE_DWORD;
		case FordiacKeywords.WORD:
			return SIZE_WORD;
		case FordiacKeywords.BYTE:
			return SIZE_BYTE;
		case FordiacKeywords.BOOL:
			return SIZE_BOOL;
		default:
			return 0;
		}
	}

	/**
	 * compute the number of elements of a datatype with defined bit representation
	 * can fit inside another datatype with defined bit representation
	 *
	 * @param name of the source-datatype
	 * @param name of the accessing datatype
	 * @return number elements
	 */
	static int indexStop(String sourceType, String accessType) {
		int sourceSize = getSize(sourceType);
		int accessSize = getSize(accessType);
		assert (accessSize != 0);
		return (sourceSize / accessSize) - 1;
	}

}
