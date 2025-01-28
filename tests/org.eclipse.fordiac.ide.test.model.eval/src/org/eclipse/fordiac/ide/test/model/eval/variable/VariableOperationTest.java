/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.model.eval.variable;

import static org.eclipse.fordiac.ide.model.eval.value.DIntValue.toDIntValue;
import static org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable.newArrayType;
import static org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable.newSubrange;
import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.getDeclaredInitialValue;
import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.getInheritedInitialValue;
import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.getInitialValue;
import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.hasDeclaredInitialValue;
import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.hasInheritedInitialValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.EnumeratedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.HelperTypes;
import org.eclipse.fordiac.ide.model.eval.EvaluatorInitializerException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorPrepareException;
import org.eclipse.fordiac.ide.model.eval.value.ArrayValue;
import org.eclipse.fordiac.ide.model.eval.value.EnumValue;
import org.eclipse.fordiac.ide.model.eval.value.FBValue;
import org.eclipse.fordiac.ide.model.eval.value.StructValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.test.model.eval.AbstractEvaluatorTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method", "nls" })
class VariableOperationTest extends AbstractEvaluatorTest {

	private ArrayType arrayType;
	private StructuredType structType;
	private EnumeratedType enumType;

	private DirectlyDerivedType directlyDerivedType1;
	private DirectlyDerivedType directlyDerivedType2;
	private DirectlyDerivedType directlyDerivedType3;
	private DirectlyDerivedType directlyDerivedType4;

	private SimpleFBType type;
	private VarDeclaration typeVarDeclaration1;
	private VarDeclaration typeVarDeclaration2;
	private VarDeclaration typeVarDeclaration3;

	private FB instance;
	private VarDeclaration instanceVarDeclaration1;
	private VarDeclaration instanceVarDeclaration2;
	private VarDeclaration instanceVarDeclaration3;

	private AttributeDeclaration attributeDeclaration1;
	private AttributeDeclaration attributeDeclaration2;

	private Attribute attribute1;
	private Attribute attribute2;
	private Attribute attribute3;
	private Attribute attribute4;

	@BeforeEach
	void setup() {
		arrayType = newArrayType(ElementaryTypes.DINT, newSubrange(0, 1));
		structType = newStructuredType("TestStruct", List.of(newVarDeclaration("a", ElementaryTypes.DINT, false),
				newVarDeclaration("b", ElementaryTypes.DINT, false)));
		enumType = newEnumeratedType("TestEnum", List.of("red", "green", "blue"));

		directlyDerivedType1 = newDirectlyDerivedType("TestDerivedType1", ElementaryTypes.DINT, null);
		directlyDerivedType2 = newDirectlyDerivedType("TestDerivedType2", ElementaryTypes.DINT, "17");
		directlyDerivedType3 = newDirectlyDerivedType("TestDerivedType3", ElementaryTypes.DINT, "17+4");
		directlyDerivedType4 = newDirectlyDerivedType("TestDerivedType4", ElementaryTypes.DINT, "error");

		typeVarDeclaration1 = newVarDeclaration("DI1", ElementaryTypes.DINT, true);
		typeVarDeclaration2 = newVarDeclaration("DI2", ElementaryTypes.DINT, true, "17");
		typeVarDeclaration3 = newVarDeclaration("DI3", ElementaryTypes.DINT, true, "4");
		type = newSimpleFBType("TestSimple", List.of(typeVarDeclaration1, typeVarDeclaration2, typeVarDeclaration3));

		instance = newFB("TestSimpleInstance", type);
		instanceVarDeclaration1 = instance.getInterface().getVariable("DI1");
		instanceVarDeclaration2 = instance.getInterface().getVariable("DI2");
		instanceVarDeclaration3 = instance.getInterface().getVariable("DI3");
		instanceVarDeclaration3.setValue(newValue("21"));

		attributeDeclaration1 = newAttributeDeclaration("TestStructAttribute",
				List.of(newVarDeclaration("a", ElementaryTypes.DINT, false, "17"),
						newVarDeclaration("b", ElementaryTypes.DINT, false, "4")));
		attributeDeclaration2 = newAttributeDeclaration("TestDerivedAttribute", ElementaryTypes.DINT, "17");

		attribute1 = newAttribute(attributeDeclaration1);
		attribute2 = newAttribute(attributeDeclaration1, "(a:=21, b:=42)");
		attribute3 = newAttribute(attributeDeclaration2);
		attribute4 = newAttribute(attributeDeclaration2, "4");
	}

	@Test
	void testNewVariable() {
		assertVariableEquals("Test", toDIntValue(0), VariableOperations.newVariable("Test", ElementaryTypes.DINT));
		assertVariableEquals("Test", toDIntValue(0), VariableOperations.newVariable("Test", GenericTypes.ANY));
		assertVariableEquals("Test", new ArrayValue(arrayType), VariableOperations.newVariable("Test", arrayType));
		assertVariableEquals("Test", new StructValue(structType), VariableOperations.newVariable("Test", structType));
		assertVariableEquals("Test", new EnumValue(enumType), VariableOperations.newVariable("Test", enumType));
		assertVariableEquals("Test", toDIntValue(0), VariableOperations.newVariable("Test", directlyDerivedType1));
		assertVariableEquals("Test", toDIntValue(17), VariableOperations.newVariable("Test", directlyDerivedType2));
		assertVariableEquals("Test", toDIntValue(21), VariableOperations.newVariable("Test", directlyDerivedType3));
		assertEquals("Error initializing member 'TestDerivedType4': The variable error is undefined",
				assertThrows(EvaluatorInitializerException.class,
						() -> VariableOperations.newVariable("Test", directlyDerivedType4)).getMessage());
		assertVariableEquals("Test", new FBValue(type), VariableOperations.newVariable("Test", type));
		assertThrows(NullPointerException.class, () -> VariableOperations.newVariable("Test", (INamedElement) null));
		assertThrows(UnsupportedOperationException.class,
				() -> VariableOperations.newVariable("Test", HelperTypes.CDATA));
	}

	@Test
	void testNewVariableWithString() {
		assertVariableEquals("Test", toDIntValue(17),
				VariableOperations.newVariable("Test", ElementaryTypes.DINT, "17"));
		assertVariableEquals("Test", toDIntValue(17),
				VariableOperations.newVariable("Test", GenericTypes.ANY, "DINT#17"));
		assertVariableEquals("Test", new ArrayValue(arrayType, List.of(toDIntValue(17), toDIntValue(4))),
				VariableOperations.newVariable("Test", arrayType, "[17,4]"));
		assertVariableEquals("Test", new StructValue(structType, Map.of("a", toDIntValue(17), "b", toDIntValue(4))),
				VariableOperations.newVariable("Test", structType, "(a:=17,b:=4)"));
		assertVariableEquals("Test", new EnumValue(enumType.getEnumeratedValues().get(1)),
				VariableOperations.newVariable("Test", enumType, "TestEnum#green"));
		assertVariableEquals("Test", toDIntValue(42),
				VariableOperations.newVariable("Test", directlyDerivedType1, "42"));
		assertThrows(NullPointerException.class,
				() -> VariableOperations.newVariable("Test", (INamedElement) null, ""));
		assertThrows(UnsupportedOperationException.class,
				() -> VariableOperations.newVariable("Test", HelperTypes.CDATA, ""));
	}

	@Test
	void testNewVariableWithValue() {
		assertVariableEquals("Test", toDIntValue(17),
				VariableOperations.newVariable("Test", ElementaryTypes.DINT, toDIntValue(17)));
		assertVariableEquals("Test", toDIntValue(17),
				VariableOperations.newVariable("Test", GenericTypes.ANY, toDIntValue(17)));
		assertVariableEquals("Test", new ArrayValue(arrayType, List.of(toDIntValue(17), toDIntValue(4))),
				VariableOperations.newVariable("Test", arrayType,
						new ArrayValue(arrayType, List.of(toDIntValue(17), toDIntValue(4)))));
		assertVariableEquals("Test", new StructValue(structType, Map.of("a", toDIntValue(17), "b", toDIntValue(4))),
				VariableOperations.newVariable("Test", structType,
						new StructValue(structType, Map.of("a", toDIntValue(17), "b", toDIntValue(4)))));
		assertVariableEquals("Test", new EnumValue(enumType.getEnumeratedValues().get(2)),
				VariableOperations.newVariable("Test", new EnumValue(enumType.getEnumeratedValues().get(2))));
		assertVariableEquals("Test", toDIntValue(42),
				VariableOperations.newVariable("Test", directlyDerivedType1, toDIntValue(42)));
		assertVariableEquals("Test",
				new FBValue(type, Map.of("DI1", toDIntValue(17), "DI2", toDIntValue(4), "DI3", toDIntValue(21))),
				VariableOperations.newVariable("Test", type, new FBValue(type,
						Map.of("DI1", toDIntValue(17), "DI2", toDIntValue(4), "DI3", toDIntValue(21)))));
		assertThrows(NullPointerException.class,
				() -> VariableOperations.newVariable("Test", (INamedElement) null, toDIntValue(0)));
		assertThrows(UnsupportedOperationException.class,
				() -> VariableOperations.newVariable("Test", HelperTypes.CDATA, toDIntValue(0)));
	}

	@Test
	void testNewVariableForVarDeclaration() {
		assertVariableEquals("DI1", toDIntValue(0), VariableOperations.newVariable(typeVarDeclaration1));
		assertVariableEquals("DI2", toDIntValue(17), VariableOperations.newVariable(typeVarDeclaration2));
		assertVariableEquals("DI3", toDIntValue(4), VariableOperations.newVariable(typeVarDeclaration3));

		assertVariableEquals("DI1", toDIntValue(0), VariableOperations.newVariable(instanceVarDeclaration1));
		assertVariableEquals("DI2", toDIntValue(17), VariableOperations.newVariable(instanceVarDeclaration2));
		assertVariableEquals("DI3", toDIntValue(21), VariableOperations.newVariable(instanceVarDeclaration3));
	}

	@Test
	void testNewVariableForVarDeclarationWithString() {
		assertVariableEquals("DI1", toDIntValue(1), VariableOperations.newVariable(typeVarDeclaration1, "1"));
		assertVariableEquals("DI2", toDIntValue(2), VariableOperations.newVariable(typeVarDeclaration2, "2"));
		assertVariableEquals("DI3", toDIntValue(3), VariableOperations.newVariable(typeVarDeclaration3, "3"));

		assertVariableEquals("DI1", toDIntValue(1), VariableOperations.newVariable(instanceVarDeclaration1, "1"));
		assertVariableEquals("DI2", toDIntValue(2), VariableOperations.newVariable(instanceVarDeclaration2, "2"));
		assertVariableEquals("DI3", toDIntValue(3), VariableOperations.newVariable(instanceVarDeclaration3, "3"));
	}

	@Test
	void testNewVariableForVarDeclarationWithValue() {
		assertVariableEquals("DI1", toDIntValue(1),
				VariableOperations.newVariable(typeVarDeclaration1, toDIntValue(1)));
		assertVariableEquals("DI2", toDIntValue(2),
				VariableOperations.newVariable(typeVarDeclaration2, toDIntValue(2)));
		assertVariableEquals("DI3", toDIntValue(3),
				VariableOperations.newVariable(typeVarDeclaration3, toDIntValue(3)));

		assertVariableEquals("DI1", toDIntValue(1),
				VariableOperations.newVariable(instanceVarDeclaration1, toDIntValue(1)));
		assertVariableEquals("DI2", toDIntValue(2),
				VariableOperations.newVariable(instanceVarDeclaration2, toDIntValue(2)));
		assertVariableEquals("DI3", toDIntValue(3),
				VariableOperations.newVariable(instanceVarDeclaration3, toDIntValue(3)));
	}

	@Test
	void testNewVariableForFB() {
		final FBVariable variable = VariableOperations.newVariable(instance);
		assertVariableEquals("DI1", toDIntValue(0), variable.getValue().get("DI1"));
		assertVariableEquals("DI2", toDIntValue(17), variable.getValue().get("DI2"));
		assertVariableEquals("DI3", toDIntValue(21), variable.getValue().get("DI3"));
	}

	@Test
	void testNewVariableForAttribute() {
		assertVariableEquals("TestStructAttribute", new StructValue((StructuredType) attributeDeclaration1.getType(),
				Map.of("a", toDIntValue(17), "b", toDIntValue(4))), VariableOperations.newVariable(attribute1));
		assertVariableEquals("TestStructAttribute",
				new StructValue((StructuredType) attributeDeclaration1.getType(),
						Map.of("a", toDIntValue(21), "b", toDIntValue(42))),
				VariableOperations.newVariable(attribute2));
		assertVariableEquals("TestDerivedAttribute", toDIntValue(17), VariableOperations.newVariable(attribute3));
		assertVariableEquals("TestDerivedAttribute", toDIntValue(4), VariableOperations.newVariable(attribute4));
	}

	@Test
	void testNewVariableForAttributeWithValue() {
		assertVariableEquals("TestStructAttribute",
				new StructValue((StructuredType) attributeDeclaration1.getType(),
						Map.of("a", toDIntValue(1), "b", toDIntValue(2))),
				VariableOperations.newVariable(attribute1, "(a:=1,b:=2)"));
		assertVariableEquals("TestStructAttribute",
				new StructValue((StructuredType) attributeDeclaration1.getType(),
						Map.of("a", toDIntValue(1), "b", toDIntValue(2))),
				VariableOperations.newVariable(attribute2, "(a:=1,b:=2)"));
		assertVariableEquals("TestDerivedAttribute", toDIntValue(1), VariableOperations.newVariable(attribute3, "1"));
		assertVariableEquals("TestDerivedAttribute", toDIntValue(2), VariableOperations.newVariable(attribute4, "2"));
	}

	@Test
	void testNewVariableForDirectlyDerivedType() {
		assertVariableEquals("TestDerivedType1", toDIntValue(0), VariableOperations.newVariable(directlyDerivedType1));
		assertVariableEquals("TestDerivedType2", toDIntValue(17), VariableOperations.newVariable(directlyDerivedType2));
		assertVariableEquals("TestDerivedType3", toDIntValue(21), VariableOperations.newVariable(directlyDerivedType3));
		assertEquals("The variable error is undefined", assertThrows(EvaluatorPrepareException.class,
				() -> VariableOperations.newVariable(directlyDerivedType4)).getMessage());
	}

	@Test
	void testNewVariableForDirectlyDerivedTypeWithValue() {
		assertVariableEquals("TestDerivedType1", toDIntValue(1),
				VariableOperations.newVariable(directlyDerivedType1, "1"));
		assertEquals("The variable error is undefined", assertThrows(EvaluatorPrepareException.class,
				() -> VariableOperations.newVariable(directlyDerivedType1, "error")).getMessage());
	}

	@Test
	void testEvaluateResultType() {
		assertEquals(ElementaryTypes.DINT, VariableOperations
				.evaluateResultType(newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false)));

		final VarDeclaration arrayVarDeclaration = newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false);
		ArraySizeHelper.setArraySize(arrayVarDeclaration, "0..1,-10..10");
		final ArrayType arrayType = (ArrayType) VariableOperations.evaluateResultType(arrayVarDeclaration);
		assertEquals(ElementaryTypes.DINT, arrayType.getBaseType());
		assertEquals(0, arrayType.getSubranges().get(0).getLowerLimit());
		assertEquals(1, arrayType.getSubranges().get(0).getUpperLimit());
		assertEquals(-10, arrayType.getSubranges().get(1).getLowerLimit());
		assertEquals(10, arrayType.getSubranges().get(1).getUpperLimit());

		final VarDeclaration arrayVarDeclaration2 = newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT,
				false);
		ArraySizeHelper.setArraySize(arrayVarDeclaration2, "0..17+4,21..21*2");
		final ArrayType arrayType2 = (ArrayType) VariableOperations.evaluateResultType(arrayVarDeclaration2);
		assertEquals(ElementaryTypes.DINT, arrayType2.getBaseType());
		assertEquals(0, arrayType2.getSubranges().get(0).getLowerLimit());
		assertEquals(21, arrayType2.getSubranges().get(0).getUpperLimit());
		assertEquals(21, arrayType2.getSubranges().get(1).getLowerLimit());
		assertEquals(42, arrayType2.getSubranges().get(1).getUpperLimit());

		final VarDeclaration arrayVarDeclaration3 = newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT,
				false);
		ArraySizeHelper.setArraySize(arrayVarDeclaration3, "0..error");
		assertEquals("TestVarDeclaration at 1: The variable error is undefined",
				assertThrows(EvaluatorPrepareException.class,
						() -> VariableOperations.evaluateResultType(arrayVarDeclaration3)).getMessage());
	}

	@Test
	void testValidateValueForVarDeclaration() {
		assertTrue(VariableOperations
				.validateValue(newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false)).isEmpty());
		assertTrue(VariableOperations
				.validateValue(newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false, "17")).isEmpty());
		assertTrue(VariableOperations
				.validateValue(newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false, "17+4")).isEmpty());
		assertEquals("The variable error is undefined", VariableOperations
				.validateValue(newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false, "error")));
	}

	@Test
	void testValidateValueForDirectlyDerivedType() {
		assertTrue(VariableOperations.validateValue(directlyDerivedType1).isEmpty());
		assertTrue(VariableOperations.validateValue(directlyDerivedType2).isEmpty());
		assertTrue(VariableOperations.validateValue(directlyDerivedType3).isEmpty());
		assertEquals("The variable error is undefined", VariableOperations.validateValue(directlyDerivedType4));
	}

	@Test
	void testValidateValueForVarDeclarationWithLists() {
		final List<String> errors = new ArrayList<>();
		final List<String> warnings = new ArrayList<>();
		final List<String> infos = new ArrayList<>();

		assertTrue(VariableOperations.validateValue(
				newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false), errors, warnings, infos));
		assertIterableEquals(List.of(), errors);
		assertIterableEquals(List.of(), warnings);
		assertIterableEquals(List.of(), infos);

		assertTrue(VariableOperations.validateValue(
				newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false, "17"), errors, warnings, infos));
		assertIterableEquals(List.of(), errors);
		assertIterableEquals(List.of(), warnings);
		assertIterableEquals(List.of(), infos);

		assertTrue(VariableOperations.validateValue(
				newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false, "17+4"), errors, warnings, infos));
		assertIterableEquals(List.of(), errors);
		assertIterableEquals(List.of(), warnings);
		assertIterableEquals(List.of(), infos);

		assertFalse(VariableOperations.validateValue(
				newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false, "error"), errors, warnings,
				infos));
		assertIterableEquals(List.of("The variable error is undefined"), errors);
		assertIterableEquals(List.of(), warnings);
		assertIterableEquals(List.of(), infos);
	}

	@Test
	void testValidateType() {
		final List<String> errors = new ArrayList<>();
		final List<String> warnings = new ArrayList<>();
		final List<String> infos = new ArrayList<>();

		assertTrue(VariableOperations.validateType(newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false),
				errors, warnings, infos));
		assertIterableEquals(List.of(), errors);
		assertIterableEquals(List.of(), warnings);
		assertIterableEquals(List.of(), infos);

		final VarDeclaration arrayVarDeclaration = newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false);
		ArraySizeHelper.setArraySize(arrayVarDeclaration, "0..1,-10..10");
		assertTrue(VariableOperations.validateType(arrayVarDeclaration, errors, warnings, infos));
		assertIterableEquals(List.of(), errors);
		assertIterableEquals(List.of(), warnings);
		assertIterableEquals(List.of(), infos);

		final VarDeclaration arrayVarDeclaration2 = newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT,
				false);
		ArraySizeHelper.setArraySize(arrayVarDeclaration2, "0..17+4,21..21*2");
		assertTrue(VariableOperations.validateType(arrayVarDeclaration2, errors, warnings, infos));
		assertIterableEquals(List.of(), errors);
		assertIterableEquals(List.of(), warnings);
		assertIterableEquals(List.of(), infos);

		final VarDeclaration arrayVarDeclaration3 = newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT,
				false);
		ArraySizeHelper.setArraySize(arrayVarDeclaration3, "0..error");
		assertFalse(VariableOperations.validateType(arrayVarDeclaration3, errors, warnings, infos));
		assertIterableEquals(List.of("TestVarDeclaration at 1: The variable error is undefined"), errors);
		assertIterableEquals(List.of(), warnings);
		assertIterableEquals(List.of(), infos);
	}

	@Test
	void testValidateValueForAttribute() {
		final List<String> errors = new ArrayList<>();
		final List<String> warnings = new ArrayList<>();
		final List<String> infos = new ArrayList<>();

		assertTrue(VariableOperations.validateValue(attribute1, errors, warnings, infos));
		assertIterableEquals(List.of(), errors);
		assertIterableEquals(List.of(), warnings);
		assertIterableEquals(List.of(), infos);

		assertTrue(VariableOperations.validateValue(attribute2, errors, warnings, infos));
		assertIterableEquals(List.of(), errors);
		assertIterableEquals(List.of(), warnings);
		assertIterableEquals(List.of(), infos);

		assertTrue(VariableOperations.validateValue(attribute3, errors, warnings, infos));
		assertIterableEquals(List.of(), errors);
		assertIterableEquals(List.of(), warnings);
		assertIterableEquals(List.of(), infos);

		assertTrue(VariableOperations.validateValue(attribute4, errors, warnings, infos));
		assertIterableEquals(List.of(), errors);
		assertIterableEquals(List.of(), warnings);
		assertIterableEquals(List.of(), infos);

		assertFalse(VariableOperations.validateValue(newAttribute(attributeDeclaration2, "error"), errors, warnings,
				infos));
		assertIterableEquals(List.of("The variable error is undefined"), errors);
		assertIterableEquals(List.of(), warnings);
		assertIterableEquals(List.of(), infos);
	}

	@Test
	void testValidateValueForVarDeclarationWithString() {
		assertTrue(VariableOperations
				.validateValue(newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false), "").isEmpty());
		assertTrue(VariableOperations
				.validateValue(newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false), "17").isEmpty());
		assertTrue(VariableOperations
				.validateValue(newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false), "17+4").isEmpty());
		assertEquals("The variable error is undefined", VariableOperations
				.validateValue(newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false), "error"));
	}

	@Test
	void testValidateValueForDataType() {
		assertTrue(VariableOperations.validateValue(ElementaryTypes.DINT, "").isEmpty());
		assertTrue(VariableOperations.validateValue(ElementaryTypes.DINT, "17").isEmpty());
		assertTrue(VariableOperations.validateValue(ElementaryTypes.DINT, "17+4").isEmpty());
		assertEquals("The variable error is undefined",
				VariableOperations.validateValue(ElementaryTypes.DINT, "error"));
	}

	@Test
	void testEvaluateValueForDataType() {
		assertEquals(toDIntValue(0), VariableOperations.evaluateValue(ElementaryTypes.DINT, ""));
		assertEquals(toDIntValue(17), VariableOperations.evaluateValue(ElementaryTypes.DINT, "17"));
		assertEquals(toDIntValue(21), VariableOperations.evaluateValue(ElementaryTypes.DINT, "17+4"));
		assertEquals("The variable error is undefined", assertThrows(EvaluatorPrepareException.class,
				() -> VariableOperations.evaluateValue(ElementaryTypes.DINT, "error")).getMessage());
	}

	@Test
	void testGetDependenciesForVarDeclaration() {
		assertEquals(Set.of("DINT"), VariableOperations
				.getDependencies(newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false)));
		assertEquals(Set.of("DINT"), VariableOperations
				.getDependencies(newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false, "17")));
		assertEquals(Set.of("DINT"), VariableOperations
				.getDependencies(newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false, "17+4")));

		final VarDeclaration arrayVarDeclaration = newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT, false);
		ArraySizeHelper.setArraySize(arrayVarDeclaration, "0..1,-10..10");
		assertEquals(Set.of("DINT"), VariableOperations.getDependencies(arrayVarDeclaration));

		final VarDeclaration arrayVarDeclaration2 = newVarDeclaration("TestVarDeclaration", ElementaryTypes.DINT,
				false);
		ArraySizeHelper.setArraySize(arrayVarDeclaration2, "0..17+4,21..21*2");
		assertEquals(Set.of("DINT"), VariableOperations.getDependencies(arrayVarDeclaration2));
	}

	@Test
	void testGetDependenciesForAttribute() {
		assertEquals(Set.of("TestStructAttribute"), VariableOperations.getDependencies(attribute1));
		assertEquals(Set.of("TestStructAttribute"), VariableOperations.getDependencies(attribute2));
		assertEquals(Set.of("TestDerivedAttribute"), VariableOperations.getDependencies(attribute3));
		assertEquals(Set.of("TestDerivedAttribute"), VariableOperations.getDependencies(attribute4));
	}

	@Test
	void testGetAllDependencies() {
		assertEquals(Set.of("DINT"), VariableOperations.getAllDependencies(type));
		assertEquals(Set.of("DINT"), VariableOperations.getAllDependencies(instance));
	}

	@Test
	void testHasDeclaredInitialValue() {
		assertFalse(hasDeclaredInitialValue(typeVarDeclaration1));
		assertTrue(hasDeclaredInitialValue(typeVarDeclaration2));
		assertTrue(hasDeclaredInitialValue(typeVarDeclaration3));

		assertFalse(hasDeclaredInitialValue(instanceVarDeclaration1));
		assertFalse(hasDeclaredInitialValue(instanceVarDeclaration2));
		assertTrue(hasDeclaredInitialValue(instanceVarDeclaration3));
	}

	@Test
	void testGetDeclaredInitialValue() {
		assertNull(getDeclaredInitialValue(typeVarDeclaration1));
		assertEquals("17", getDeclaredInitialValue(typeVarDeclaration2));
		assertEquals("4", getDeclaredInitialValue(typeVarDeclaration3));

		assertNull(getDeclaredInitialValue(instanceVarDeclaration1));
		assertNull(getDeclaredInitialValue(instanceVarDeclaration2));
		assertEquals("21", getDeclaredInitialValue(instanceVarDeclaration3));
	}

	@Test
	void testHasInheritedInitialValue() {
		assertFalse(hasInheritedInitialValue(typeVarDeclaration1));
		assertFalse(hasInheritedInitialValue(typeVarDeclaration2));
		assertFalse(hasInheritedInitialValue(typeVarDeclaration3));

		assertFalse(hasInheritedInitialValue(instanceVarDeclaration1));
		assertTrue(hasInheritedInitialValue(instanceVarDeclaration2));
		assertTrue(hasInheritedInitialValue(instanceVarDeclaration3));
	}

	@Test
	void testGetInheritedInitialValue() {
		assertNull(getInheritedInitialValue(typeVarDeclaration1));
		assertNull(getInheritedInitialValue(typeVarDeclaration2));
		assertNull(getInheritedInitialValue(typeVarDeclaration3));

		assertNull(getInheritedInitialValue(instanceVarDeclaration1));
		assertEquals("17", getInheritedInitialValue(instanceVarDeclaration2));
		assertEquals("4", getInheritedInitialValue(instanceVarDeclaration3));
	}

	@Test
	void testGetInitialValue() {
		assertNull(getInitialValue(typeVarDeclaration1));
		assertEquals("17", getInitialValue(typeVarDeclaration2));
		assertEquals("4", getInitialValue(typeVarDeclaration3));

		assertNull(getInitialValue(instanceVarDeclaration1));
		assertEquals("17", getInitialValue(instanceVarDeclaration2));
		assertEquals("21", getInitialValue(instanceVarDeclaration3));
	}

	private static void assertVariableEquals(final String expectedName, final Value expectedValue,
			final Variable<?> variable) {
		assertEquals(expectedName, variable.getName(), "name differs");
		assertEquals(expectedValue, variable.getValue(), "value differs");
	}
}
