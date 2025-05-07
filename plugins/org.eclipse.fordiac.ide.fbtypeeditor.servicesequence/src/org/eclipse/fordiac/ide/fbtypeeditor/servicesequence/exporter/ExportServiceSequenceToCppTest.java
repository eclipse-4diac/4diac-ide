/*******************************************************************************
 * Copyright (c) 2023 Fabio Gandolfi
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.exporter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.IntType;
import org.eclipse.fordiac.ide.model.data.StringType;
import org.eclipse.fordiac.ide.model.data.UintType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

@SuppressWarnings("nls")
public class ExportServiceSequenceToCppTest {

	private List<ServiceSequence> serviceSeq;
	BasicFBType fb;

	public void exportServiceSequenceToCppTest(final List<ServiceSequence> serviceSeq, final FBType fb) {
		this.serviceSeq = serviceSeq;
		this.fb = (BasicFBType) fb;

		final IPath projectPath = ResourcesPlugin.getWorkspace().getRoot().getLocation();
		final String exportedTestDir = "ServiceSequenceTests";

		final File directory = new File(projectPath.toString() + File.separator + exportedTestDir);
		if (!directory.exists()) {
			directory.mkdir();
		}

		final Path path = Paths.get(directory + File.separator + fb.getName() + "_ServiceSeq.cpp"); //$NON-NLS-1$
		if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
			try {
				Files.createFile(path);
			} catch (final IOException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			addImports(writer);
			addTesterGen(writer);
			addDataStruct(writer);
			addTestSuite(writer);
		} catch (final IOException ex) {
			FordiacLogHelper.logError(ex.getMessage(), ex);
		}
	}

	private void addTestSuite(final BufferedWriter writer) throws IOException {
		writer.append(MessageFormat.format(CppBoostTestConstants.TEST_SUITE_NAME, fb.getName()));
		writer.newLine();
		for (final ServiceSequence sequence : serviceSeq) {
			writer.append(MessageFormat.format(CppBoostTestConstants.TEST_CASE, sequence.getName()));
			writer.newLine();
			final int stateID = fb.getECC().getECState().indexOf(fb.getECC().getECState().stream()
					.filter(state -> state.getName().equals(sequence.getStartState())).findFirst().orElse(null));
			if (stateID > 0) {
				writer.append(MessageFormat.format(CppBoostTestConstants.SET_ECC_STATE, Integer.toString(stateID)));
				writer.newLine();
			}
			for (final ServiceTransaction transaction : sequence.getServiceTransaction()) {
				parseAndAddParameters(writer, transaction.getInputPrimitive().getParameters());

				final int eventID = fb.getInterfaceList().getEventInputs()
						.indexOf(fb.getInterfaceList().getEventInputs().stream()
								.filter(event -> event.getName().equals(transaction.getInputPrimitive().getEvent()))
								.findFirst().orElse(null));
				writer.append(MessageFormat.format(CppBoostTestConstants.TRIGGER_EVENT, Integer.toString(eventID)));
				writer.newLine();
				parseAndAddOutputAsserts(writer, transaction.getOutputPrimitive());
			}
			writer.append("}");
			writer.newLine();
		}
		writer.append(CppBoostTestConstants.TEST_SUITE_END);

	}

	private static void parseAndAddOutputAsserts(final BufferedWriter writer, final List<OutputPrimitive> parameters)
			throws IOException {
		for (final OutputPrimitive primitive : parameters) {
			if (primitive.getParameters() != null && primitive.getParameters().contains("\n")) {
				final List<String> paramStrings = Arrays.asList(primitive.getParameters().split("\n"));
				for (String param : paramStrings) {
					param = param.replaceFirst("#", "(");
					param = param.replaceFirst(";", ")");
					param = param.replace(param.subSequence(param.indexOf("=") + 1, param.indexOf("(")),
							getForteDataTypeFromString(
									param.subSequence(param.indexOf("=") + 1, param.indexOf("(")).toString()));
					writer.append(MessageFormat.format(CppBoostTestConstants.BOOS_ASSERT_EQUAL,
							param.subSequence(0, param.indexOf(":")),
							param.subSequence(param.indexOf("=") + 1, param.length()).toString().replace("'", "\"")));
					writer.newLine();
				}
			}
		}
	}

	private static void parseAndAddParameters(final BufferedWriter writer, final String parameters) throws IOException {
		if (parameters.contains("\n")) {
			final List<String> paramStrings = Arrays.asList(parameters.split("\n"));
			for (String param : paramStrings) {
				param = param.replaceFirst(":=", "=");
				param = param.replaceFirst("#", "(");
				param = param.replaceFirst(";", ");");
				param = param.replace(param.subSequence(param.indexOf("=") + 1, param.indexOf("(")),
						getForteDataTypeFromString(
								param.subSequence(param.indexOf("=") + 1, param.indexOf("(")).toString()));
				writer.append(param);
				writer.newLine();
			}
		}
	}

	private static void addImports(final BufferedWriter writer) throws IOException {
		writer.write(CppBoostTestConstants.TEST_INCLUDE_STRING);
	}

	private void addTesterGen(final BufferedWriter writer) throws IOException {
		writer.write(MessageFormat.format(CppBoostTestConstants.TESTER_GEN_STRING, fb.getName()));
		writer.newLine();
	}

	private void addDataStruct(final BufferedWriter writer) throws IOException {
		final var outputData = fb.getOutputParameters();
		final var inputData = fb.getInputParameters();

		writer.append(MessageFormat.format(CppBoostTestConstants.TEST_FIXTURE_STRUCT, fb.getName()));
		writer.newLine();
		writer.append(MessageFormat.format(CppBoostTestConstants.TEST_FICTURE_BASE, fb.getName()));
		writer.newLine();
		writer.append(CppBoostTestConstants.SET_INPUT_DATA_START);
		if (!inputData.isEmpty()) {
			writer.append(inputData.stream().map(INamedElement::getName).collect(Collectors.joining(",&", "&", "")));
		}
		writer.append(CppBoostTestConstants.SET_INPUT_DATA_END);
		writer.newLine();
		writer.append(CppBoostTestConstants.SET_OUTPUT_DATA_START);
		if (!outputData.isEmpty()) {
			writer.append(outputData.stream().map(INamedElement::getName).collect(Collectors.joining(",&", "&", "")));
		}
		writer.append(CppBoostTestConstants.SET_OUTPUT_DATA_END);
		writer.newLine();
		writer.append(CppBoostTestConstants.TEST_FICTURE_SETUP);
		writer.newLine();

		for (final INamedElement varDec : inputData) {
			writer.append(getForteDataType(((VarDeclaration) varDec).getType()) + " " + varDec.getName() + ";");
			writer.newLine();
		}
		for (final INamedElement varDec : outputData) {
			writer.append(getForteDataType(((VarDeclaration) varDec).getType()) + " " + varDec.getName() + ";");
			writer.newLine();
		}
		writer.append("};");
		writer.newLine();
	}

	private static String getForteDataType(final Object dataType) {
		if (dataType instanceof BoolType) {
			return "CIEC_BOOL";
		}
		if (dataType instanceof IntType) {
			return "CIEC_INT";
		}
		if (dataType instanceof UintType) {
			return "CIEC_UINT";
		}
		if (dataType instanceof StringType) {
			return "CIEC_STRING";
		}
		return "";
	}

	private static String getForteDataTypeFromString(final String dataType) {
		return switch (dataType.toUpperCase()) {
		case "BOOL" -> "CIEC_BOOL";
		case "UINT" -> "CIEC_UINT";
		case "INT" -> "CIEC_INT";
		case "STRING" -> "CIEC_STRING";
		default -> "";
		};
	}
}
