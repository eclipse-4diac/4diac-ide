/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.model.eval.variable;

import static org.eclipse.fordiac.ide.model.eval.value.BoolValue.toBoolValue;
import static org.eclipse.fordiac.ide.model.eval.value.DIntValue.toDIntValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.st.ECTransitionEvaluator;
import org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable;
import org.eclipse.fordiac.ide.model.eval.variable.StructVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.test.model.eval.AbstractEvaluatorTest;
import org.eclipse.fordiac.ide.test.model.typelibrary.DataTypeEntryMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method", "nls" })
class VariableOperationExplicitInitialValueTest extends AbstractEvaluatorTest {

	private VarDeclaration nestedArrayDeclaration;

	@BeforeEach
	void setup() {
		final StructuredType innerInnerType = newInnerInnerBoolStructType("TestExplicitInnerInnerStruct");
		final StructuredType innerType = newInnerBoolStructType("TestExplicitInnerStruct", innerInnerType);
		nestedArrayDeclaration = newArrayInstanceDeclaration("TestExplicitNestedArrayFB",
				"TestExplicitNestedArrayInstance", "DI_ARRAY", innerType, "0..1", null);
	}

	@Test
	void testCollectsExplicitNestedArrayMember() {
		final Set<Variable<?>> explicitlyInitialized = newIdentitySet();
		final Variable<?> variable = VariableOperations.newVariable(nestedArrayDeclaration,
				"[(VAR15 := (VAR8 := TRUE))]", explicitlyInitialized);
		final Variable<?> explicitMember = getMember(getMember(getElement(variable, 0), "VAR15"), "VAR8");

		assertEquals(toBoolValue(true), explicitMember.getValue());
		assertEquals(toBoolValue(false), getMember(getMember(getElement(variable, 0), "VAR15"), "VAR9").getValue());
		assertEquals(toBoolValue(false), getMember(getMember(getElement(variable, 1), "VAR15"), "VAR8").getValue());
		assertTrue(explicitlyInitialized.contains(explicitMember));
	}

	@Test
	void testCollectsExplicitFalseNestedArrayMemberEqualToBaseValue() {
		final Set<Variable<?>> explicitlyInitialized = newIdentitySet();
		final Variable<?> variable = VariableOperations.newVariable(nestedArrayDeclaration,
				"[(VAR15 := (VAR8 := FALSE))]", explicitlyInitialized);
		final Variable<?> member = getMember(getMember(getElement(variable, 0), "VAR15"), "VAR8");

		assertEquals(toBoolValue(false), member.getValue());
		assertTrue(explicitlyInitialized.contains(member));
	}

	@Test
	void testCollectsExplicitStructMember() {
		final StructuredType structType = newStructuredType("TestExplicitStruct",
				List.of(newVarDeclaration("a", ElementaryTypes.DINT, false, "17"),
						newVarDeclaration("b", ElementaryTypes.DINT, false, "4")));
		final VarDeclaration declaration = newVarDeclaration("DI", structType, true, "(a := 17)");
		final Set<Variable<?>> explicitlyInitialized = newIdentitySet();

		final Variable<?> variable = VariableOperations.newVariable(declaration, "(a := 17)", explicitlyInitialized);
		final Variable<?> memberA = getMember(variable, "a");
		final Variable<?> memberB = getMember(variable, "b");

		assertEquals(toDIntValue(17), memberA.getValue());
		assertTrue(explicitlyInitialized.contains(memberA));
		assertFalse(explicitlyInitialized.contains(memberB));
	}

	@Test
	void testInheritedInitialValueDoesNotBecomeExplicit() {
		final StructuredType structType = newResolvableStructuredType("TestInheritedStruct",
				List.of(newVarDeclaration("a", ElementaryTypes.DINT, false),
						newVarDeclaration("b", ElementaryTypes.DINT, false)));
		final VarDeclaration typeDeclaration = newVarDeclaration("DI", structType, true, "(a := 17)");
		final VarDeclaration instanceDeclaration = newInstanceDeclaration("TestInheritedFB", "TestInheritedInstance",
				typeDeclaration);
		final Set<Variable<?>> explicitlyInitialized = newIdentitySet();

		final Variable<?> variable = VariableOperations.newVariable(instanceDeclaration, null, explicitlyInitialized);

		assertEquals(toDIntValue(17), getMember(variable, "a").getValue());
		assertTrue(explicitlyInitialized.isEmpty());
	}

	@Test
	void testCollectsExplicitAttributeStructMember() {
		final Attribute attribute = newAttribute(
				newAttributeDeclaration("TestExplicitStructAttribute",
						List.of(newVarDeclaration("a", ElementaryTypes.DINT, false),
								newVarDeclaration("b", ElementaryTypes.DINT, false))),
				"(a := 17)");
		final Set<Variable<?>> explicitlyInitialized = newIdentitySet();

		final Variable<?> variable = VariableOperations.newVariable(attribute, "(a := 17)", explicitlyInitialized);

		assertEquals(toDIntValue(17), getMember(variable, "a").getValue());
		assertTrue(explicitlyInitialized.contains(getMember(variable, "a")));
		assertFalse(explicitlyInitialized.contains(getMember(variable, "b")));
	}

	@Test
	void testCollectsExplicitDirectlyDerivedValue() {
		final DirectlyDerivedType derivedType = newDirectlyDerivedType("TestExplicitDerivedType",
				ElementaryTypes.DINT, null);
		final Set<Variable<?>> explicitlyInitialized = newIdentitySet();

		final Variable<?> variable = VariableOperations.newVariable(derivedType, "17", explicitlyInitialized);

		assertEquals(toDIntValue(17), variable.getValue());
		assertTrue(explicitlyInitialized.contains(variable));
	}

	@Test
	void testECTransitionLeavesExplicitlyInitializedVariablesUnchanged()
			throws EvaluatorException, InterruptedException {
		final Set<Variable<?>> explicitlyInitialized = newIdentitySet();
		final Variable<?> initializedVariable = VariableOperations.newVariable("initialized", ElementaryTypes.BOOL);
		explicitlyInitialized.add(initializedVariable);
		final ECTransitionEvaluator evaluator = new ECTransitionEvaluator(
				LibraryElementFactory.eINSTANCE.createECTransition(), null, Collections.emptySet(), null);

		evaluator.evaluateVariable(explicitlyInitialized);

		assertEquals(1, explicitlyInitialized.size());
		assertTrue(explicitlyInitialized.contains(initializedVariable));
	}

	private static VarDeclaration newArrayInstanceDeclaration(final String fbTypeName, final String instanceName,
			final String variableName, final StructuredType elementType, final String arraySize,
			final String initialValue) {
		final VarDeclaration typeDeclaration = initialValue == null ? newVarDeclaration(variableName, elementType, true)
				: newVarDeclaration(variableName, elementType, true, initialValue);
		ArraySizeHelper.setArraySize(typeDeclaration, arraySize);
		return newInstanceDeclaration(fbTypeName, instanceName, typeDeclaration);
	}

	private static VarDeclaration newInstanceDeclaration(final String fbTypeName, final String instanceName,
			final VarDeclaration typeDeclaration) {
		final SimpleFBType fbType = newSimpleFBType(fbTypeName, List.of(typeDeclaration));
		final FB fb = newResourceBackedFB(instanceName, fbType);
		return fb.getInterface().getVariable(typeDeclaration.getName());
	}

	private static StructuredType newResolvableStructuredType(final String name, final List<VarDeclaration> vars) {
		final StructuredType structType = DataFactory.eINSTANCE.createStructuredType();
		structType.setName(name);
		structType.getMemberVariables().addAll(vars);
		final DataTypeEntryMock typeEntry = new DataTypeEntryMock(structType, typeLib,
				project.getFile(name + TypeLibraryTags.DATA_TYPE_FILE_ENDING_WITH_DOT));
		structType.setTypeEntry(typeEntry);
		typeLib.addTypeEntry(typeEntry);
		new ResourceImpl(typeEntry.getURI()).getContents().add(structType);
		return structType;
	}

	private static FB newResourceBackedFB(final String instanceName, final SimpleFBType instanceType) {
		final FB fb = newFB(instanceName, instanceType);
		if (instanceType.eResource() != null) {
			new ResourceImpl(instanceType.eResource().getURI()).getContents().add(fb);
		}
		return fb;
	}

	private static Variable<?> getMember(final Variable<?> variable, final String name) {
		return ((StructVariable) variable).getMembers().get(name);
	}

	private static Variable<?> getElement(final Variable<?> variable, final int index) {
		return ((ArrayVariable) variable).getValue().get(index);
	}

	private static Set<Variable<?>> newIdentitySet() {
		return Collections.newSetFromMap(new IdentityHashMap<>());
	}

	private static StructuredType newInnerInnerBoolStructType(final String name) {
		final List<VarDeclaration> members = new ArrayList<>();
		for (int i = 1; i <= 13; i++) {
			members.add(newVarDeclaration("VAR" + i, ElementaryTypes.BOOL, false));
		}
		return newResolvableStructuredType(name, members);
	}

	private static StructuredType newInnerBoolStructType(final String name, final StructuredType innerInnerType) {
		final List<VarDeclaration> members = new ArrayList<>();
		for (int i = 1; i <= 23; i++) {
			members.add(i == 15 ? newVarDeclaration("VAR15", innerInnerType, false)
					: newVarDeclaration("VAR" + i, ElementaryTypes.BOOL, false));
		}
		return newResolvableStructuredType(name, members);
	}
}
