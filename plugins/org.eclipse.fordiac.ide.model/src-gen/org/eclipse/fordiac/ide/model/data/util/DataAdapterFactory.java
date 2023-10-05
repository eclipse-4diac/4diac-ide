/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.data.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.fordiac.ide.model.data.*;

import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.model.data.DataPackage
 * @generated
 */
public class DataAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DataPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DataPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DataSwitch<Adapter> modelSwitch =
		new DataSwitch<Adapter>() {
			@Override
			public Adapter caseAnyDerivedType(AnyDerivedType object) {
				return createAnyDerivedTypeAdapter();
			}
			@Override
			public Adapter caseArrayType(ArrayType object) {
				return createArrayTypeAdapter();
			}
			@Override
			public Adapter caseDataType(DataType object) {
				return createDataTypeAdapter();
			}
			@Override
			public Adapter caseDirectlyDerivedType(DirectlyDerivedType object) {
				return createDirectlyDerivedTypeAdapter();
			}
			@Override
			public Adapter caseEnumeratedType(EnumeratedType object) {
				return createEnumeratedTypeAdapter();
			}
			@Override
			public Adapter caseEnumeratedValue(EnumeratedValue object) {
				return createEnumeratedValueAdapter();
			}
			@Override
			public Adapter caseStructuredType(StructuredType object) {
				return createStructuredTypeAdapter();
			}
			@Override
			public Adapter caseSubrange(Subrange object) {
				return createSubrangeAdapter();
			}
			@Override
			public Adapter caseSubrangeType(SubrangeType object) {
				return createSubrangeTypeAdapter();
			}
			@Override
			public Adapter caseValueType(ValueType object) {
				return createValueTypeAdapter();
			}
			@Override
			public Adapter caseElementaryType(ElementaryType object) {
				return createElementaryTypeAdapter();
			}
			@Override
			public Adapter caseDerivedType(DerivedType object) {
				return createDerivedTypeAdapter();
			}
			@Override
			public Adapter caseEventType(EventType object) {
				return createEventTypeAdapter();
			}
			@Override
			public Adapter caseAnyType(AnyType object) {
				return createAnyTypeAdapter();
			}
			@Override
			public Adapter caseAnyElementaryType(AnyElementaryType object) {
				return createAnyElementaryTypeAdapter();
			}
			@Override
			public Adapter caseAnyMagnitudeType(AnyMagnitudeType object) {
				return createAnyMagnitudeTypeAdapter();
			}
			@Override
			public Adapter caseAnyNumType(AnyNumType object) {
				return createAnyNumTypeAdapter();
			}
			@Override
			public Adapter caseAnyRealType(AnyRealType object) {
				return createAnyRealTypeAdapter();
			}
			@Override
			public Adapter caseRealType(RealType object) {
				return createRealTypeAdapter();
			}
			@Override
			public Adapter caseLrealType(LrealType object) {
				return createLrealTypeAdapter();
			}
			@Override
			public Adapter caseAnyIntType(AnyIntType object) {
				return createAnyIntTypeAdapter();
			}
			@Override
			public Adapter caseAnyUnsignedType(AnyUnsignedType object) {
				return createAnyUnsignedTypeAdapter();
			}
			@Override
			public Adapter caseUsintType(UsintType object) {
				return createUsintTypeAdapter();
			}
			@Override
			public Adapter caseUintType(UintType object) {
				return createUintTypeAdapter();
			}
			@Override
			public Adapter caseUdintType(UdintType object) {
				return createUdintTypeAdapter();
			}
			@Override
			public Adapter caseUlintType(UlintType object) {
				return createUlintTypeAdapter();
			}
			@Override
			public Adapter caseAnySignedType(AnySignedType object) {
				return createAnySignedTypeAdapter();
			}
			@Override
			public Adapter caseSintType(SintType object) {
				return createSintTypeAdapter();
			}
			@Override
			public Adapter caseIntType(IntType object) {
				return createIntTypeAdapter();
			}
			@Override
			public Adapter caseDintType(DintType object) {
				return createDintTypeAdapter();
			}
			@Override
			public Adapter caseLintType(LintType object) {
				return createLintTypeAdapter();
			}
			@Override
			public Adapter caseAnyDurationType(AnyDurationType object) {
				return createAnyDurationTypeAdapter();
			}
			@Override
			public Adapter caseTimeType(TimeType object) {
				return createTimeTypeAdapter();
			}
			@Override
			public Adapter caseLtimeType(LtimeType object) {
				return createLtimeTypeAdapter();
			}
			@Override
			public Adapter caseAnyBitType(AnyBitType object) {
				return createAnyBitTypeAdapter();
			}
			@Override
			public Adapter caseBoolType(BoolType object) {
				return createBoolTypeAdapter();
			}
			@Override
			public Adapter caseByteType(ByteType object) {
				return createByteTypeAdapter();
			}
			@Override
			public Adapter caseWordType(WordType object) {
				return createWordTypeAdapter();
			}
			@Override
			public Adapter caseDwordType(DwordType object) {
				return createDwordTypeAdapter();
			}
			@Override
			public Adapter caseLwordType(LwordType object) {
				return createLwordTypeAdapter();
			}
			@Override
			public Adapter caseAnyCharsType(AnyCharsType object) {
				return createAnyCharsTypeAdapter();
			}
			@Override
			public Adapter caseAnySCharsType(AnySCharsType object) {
				return createAnySCharsTypeAdapter();
			}
			@Override
			public Adapter caseAnyWCharsType(AnyWCharsType object) {
				return createAnyWCharsTypeAdapter();
			}
			@Override
			public Adapter caseAnyStringType(AnyStringType object) {
				return createAnyStringTypeAdapter();
			}
			@Override
			public Adapter caseStringType(StringType object) {
				return createStringTypeAdapter();
			}
			@Override
			public Adapter caseWstringType(WstringType object) {
				return createWstringTypeAdapter();
			}
			@Override
			public Adapter caseAnyCharType(AnyCharType object) {
				return createAnyCharTypeAdapter();
			}
			@Override
			public Adapter caseCharType(CharType object) {
				return createCharTypeAdapter();
			}
			@Override
			public Adapter caseWcharType(WcharType object) {
				return createWcharTypeAdapter();
			}
			@Override
			public Adapter caseAnyDateType(AnyDateType object) {
				return createAnyDateTypeAdapter();
			}
			@Override
			public Adapter caseDateAndTimeType(DateAndTimeType object) {
				return createDateAndTimeTypeAdapter();
			}
			@Override
			public Adapter caseLdtType(LdtType object) {
				return createLdtTypeAdapter();
			}
			@Override
			public Adapter caseDateType(DateType object) {
				return createDateTypeAdapter();
			}
			@Override
			public Adapter caseTimeOfDayType(TimeOfDayType object) {
				return createTimeOfDayTypeAdapter();
			}
			@Override
			public Adapter caseLtodType(LtodType object) {
				return createLtodTypeAdapter();
			}
			@Override
			public Adapter caseLdateType(LdateType object) {
				return createLdateTypeAdapter();
			}
			@Override
			public Adapter caseINamedElement(INamedElement object) {
				return createINamedElementAdapter();
			}
			@Override
			public Adapter caseConfigurableObject(ConfigurableObject object) {
				return createConfigurableObjectAdapter();
			}
			@Override
			public Adapter caseLibraryElement(LibraryElement object) {
				return createLibraryElementAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.AnyDerivedType <em>Any Derived Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.AnyDerivedType
	 * @generated
	 */
	public Adapter createAnyDerivedTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.ArrayType <em>Array Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.ArrayType
	 * @generated
	 */
	public Adapter createArrayTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.DataType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.DataType
	 * @generated
	 */
	public Adapter createDataTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.DirectlyDerivedType <em>Directly Derived Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.DirectlyDerivedType
	 * @generated
	 */
	public Adapter createDirectlyDerivedTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.EnumeratedType <em>Enumerated Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.EnumeratedType
	 * @generated
	 */
	public Adapter createEnumeratedTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.EnumeratedValue <em>Enumerated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.EnumeratedValue
	 * @generated
	 */
	public Adapter createEnumeratedValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.StructuredType <em>Structured Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.StructuredType
	 * @generated
	 */
	public Adapter createStructuredTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.Subrange <em>Subrange</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.Subrange
	 * @generated
	 */
	public Adapter createSubrangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.SubrangeType <em>Subrange Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.SubrangeType
	 * @generated
	 */
	public Adapter createSubrangeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.ValueType <em>Value Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.ValueType
	 * @generated
	 */
	public Adapter createValueTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.ElementaryType <em>Elementary Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.ElementaryType
	 * @generated
	 */
	public Adapter createElementaryTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.DerivedType <em>Derived Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.DerivedType
	 * @generated
	 */
	public Adapter createDerivedTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.EventType <em>Event Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.EventType
	 * @generated
	 */
	public Adapter createEventTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.AnyType <em>Any Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.AnyType
	 * @generated
	 */
	public Adapter createAnyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.AnyElementaryType <em>Any Elementary Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.AnyElementaryType
	 * @generated
	 */
	public Adapter createAnyElementaryTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.AnyMagnitudeType <em>Any Magnitude Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.AnyMagnitudeType
	 * @generated
	 */
	public Adapter createAnyMagnitudeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.AnyNumType <em>Any Num Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.AnyNumType
	 * @generated
	 */
	public Adapter createAnyNumTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.AnyRealType <em>Any Real Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.AnyRealType
	 * @generated
	 */
	public Adapter createAnyRealTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.RealType <em>Real Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.RealType
	 * @generated
	 */
	public Adapter createRealTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.LrealType <em>Lreal Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.LrealType
	 * @generated
	 */
	public Adapter createLrealTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.AnyIntType <em>Any Int Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.AnyIntType
	 * @generated
	 */
	public Adapter createAnyIntTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.AnyUnsignedType <em>Any Unsigned Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.AnyUnsignedType
	 * @generated
	 */
	public Adapter createAnyUnsignedTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.UsintType <em>Usint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.UsintType
	 * @generated
	 */
	public Adapter createUsintTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.UintType <em>Uint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.UintType
	 * @generated
	 */
	public Adapter createUintTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.UdintType <em>Udint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.UdintType
	 * @generated
	 */
	public Adapter createUdintTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.UlintType <em>Ulint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.UlintType
	 * @generated
	 */
	public Adapter createUlintTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.AnySignedType <em>Any Signed Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.AnySignedType
	 * @generated
	 */
	public Adapter createAnySignedTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.SintType <em>Sint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.SintType
	 * @generated
	 */
	public Adapter createSintTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.IntType <em>Int Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.IntType
	 * @generated
	 */
	public Adapter createIntTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.DintType <em>Dint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.DintType
	 * @generated
	 */
	public Adapter createDintTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.LintType <em>Lint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.LintType
	 * @generated
	 */
	public Adapter createLintTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.AnyDurationType <em>Any Duration Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.AnyDurationType
	 * @generated
	 */
	public Adapter createAnyDurationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.TimeType <em>Time Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.TimeType
	 * @generated
	 */
	public Adapter createTimeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.LtimeType <em>Ltime Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.LtimeType
	 * @generated
	 */
	public Adapter createLtimeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.AnyBitType <em>Any Bit Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.AnyBitType
	 * @generated
	 */
	public Adapter createAnyBitTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.BoolType <em>Bool Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.BoolType
	 * @generated
	 */
	public Adapter createBoolTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.ByteType <em>Byte Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.ByteType
	 * @generated
	 */
	public Adapter createByteTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.WordType <em>Word Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.WordType
	 * @generated
	 */
	public Adapter createWordTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.DwordType <em>Dword Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.DwordType
	 * @generated
	 */
	public Adapter createDwordTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.LwordType <em>Lword Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.LwordType
	 * @generated
	 */
	public Adapter createLwordTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.AnyCharsType <em>Any Chars Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.AnyCharsType
	 * @generated
	 */
	public Adapter createAnyCharsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.AnySCharsType <em>Any SChars Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.AnySCharsType
	 * @generated
	 */
	public Adapter createAnySCharsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.AnyWCharsType <em>Any WChars Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.AnyWCharsType
	 * @generated
	 */
	public Adapter createAnyWCharsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.AnyStringType <em>Any String Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.AnyStringType
	 * @generated
	 */
	public Adapter createAnyStringTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.StringType <em>String Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.StringType
	 * @generated
	 */
	public Adapter createStringTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.WstringType <em>Wstring Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.WstringType
	 * @generated
	 */
	public Adapter createWstringTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.AnyCharType <em>Any Char Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.AnyCharType
	 * @generated
	 */
	public Adapter createAnyCharTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.CharType <em>Char Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.CharType
	 * @generated
	 */
	public Adapter createCharTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.WcharType <em>Wchar Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.WcharType
	 * @generated
	 */
	public Adapter createWcharTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.AnyDateType <em>Any Date Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.AnyDateType
	 * @generated
	 */
	public Adapter createAnyDateTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.DateAndTimeType <em>Date And Time Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.DateAndTimeType
	 * @generated
	 */
	public Adapter createDateAndTimeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.LdtType <em>Ldt Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.LdtType
	 * @generated
	 */
	public Adapter createLdtTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.DateType <em>Date Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.DateType
	 * @generated
	 */
	public Adapter createDateTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.TimeOfDayType <em>Time Of Day Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.TimeOfDayType
	 * @generated
	 */
	public Adapter createTimeOfDayTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.LtodType <em>Ltod Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.LtodType
	 * @generated
	 */
	public Adapter createLtodTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.LdateType <em>Ldate Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.LdateType
	 * @generated
	 */
	public Adapter createLdateTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.INamedElement <em>INamed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.INamedElement
	 * @generated
	 */
	public Adapter createINamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject <em>Configurable Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject
	 * @generated
	 */
	public Adapter createConfigurableObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.LibraryElement <em>Library Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElement
	 * @generated
	 */
	public Adapter createLibraryElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //DataAdapterFactory
