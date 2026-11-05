/**
 * *******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.bulkeditor.query.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.fordiac.ide.bulkeditor.query.model.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class QueryFactoryImpl extends EFactoryImpl implements QueryFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static QueryFactory init() {
		try {
			QueryFactory theQueryFactory = (QueryFactory)EPackage.Registry.INSTANCE.getEFactory(QueryPackage.eNS_URI);
			if (theQueryFactory != null) {
				return theQueryFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new QueryFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QueryFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case QueryPackage.PROPERTY_CONSTRAINT: return createPropertyConstraint();
			case QueryPackage.FIELD_CONSTRAINT: return createFieldConstraint();
			case QueryPackage.ATTRIBUTE_CONSTRAINT: return createAttributeConstraint();
			case QueryPackage.APPLICATION_OCCURRENCE: return createApplicationOccurrence();
			case QueryPackage.COMPOSITE_FB_OCCURRENCE: return createCompositeFBOccurrence();
			case QueryPackage.TYPED_SUBAPP_OCCURRENCE: return createTypedSubappOccurrence();
			case QueryPackage.QUERY: return createQuery();
			case QueryPackage.PLACEHOLDER: return createPlaceholder();
			case QueryPackage.TARGET: return createTarget();
			case QueryPackage.PLACE: return createPlace();
			case QueryPackage.SIMPLE_TYPE: return createSimpleType();
			case QueryPackage.BASIC_TYPE: return createBasicType();
			case QueryPackage.COMPOSITE_TYPE: return createCompositeType();
			case QueryPackage.SERVICE_INTERFACE_TYPE: return createServiceInterfaceType();
			case QueryPackage.FUNCTION_TYPE: return createFunctionType();
			case QueryPackage.SUBAPP_TYPE: return createSubappType();
			case QueryPackage.STRUCT_TYPE: return createStructType();
			case QueryPackage.ATTRIBUTE_TYPE: return createAttributeType();
			case QueryPackage.SIMPLE_FB: return createSimpleFB();
			case QueryPackage.BASIC_FB: return createBasicFB();
			case QueryPackage.COMPOSITE_FB: return createCompositeFB();
			case QueryPackage.SERVICE_INTERFACE_FB: return createServiceInterfaceFB();
			case QueryPackage.FUNCTION_FB: return createFunctionFB();
			case QueryPackage.TYPED_SUBAPP: return createTypedSubapp();
			case QueryPackage.UNTYPED_SUBAPP: return createUntypedSubapp();
			case QueryPackage.CONNECTION: return createConnection();
			case QueryPackage.PIN: return createPIN();
			case QueryPackage.PIN_TARGET: return createPinTarget();
			case QueryPackage.ATTRIBUTE: return createAttribute();
			case QueryPackage.ATTRIBUTE_DECLARATION: return createAttributeDeclaration();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PropertyConstraint createPropertyConstraint() {
		PropertyConstraintImpl propertyConstraint = new PropertyConstraintImpl();
		return propertyConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FieldConstraint createFieldConstraint() {
		FieldConstraintImpl fieldConstraint = new FieldConstraintImpl();
		return fieldConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AttributeConstraint createAttributeConstraint() {
		AttributeConstraintImpl attributeConstraint = new AttributeConstraintImpl();
		return attributeConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ApplicationOccurrence createApplicationOccurrence() {
		ApplicationOccurrenceImpl applicationOccurrence = new ApplicationOccurrenceImpl();
		return applicationOccurrence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompositeFBOccurrence createCompositeFBOccurrence() {
		CompositeFBOccurrenceImpl compositeFBOccurrence = new CompositeFBOccurrenceImpl();
		return compositeFBOccurrence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypedSubappOccurrence createTypedSubappOccurrence() {
		TypedSubappOccurrenceImpl typedSubappOccurrence = new TypedSubappOccurrenceImpl();
		return typedSubappOccurrence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Query createQuery() {
		QueryImpl query = new QueryImpl();
		return query;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Placeholder createPlaceholder() {
		PlaceholderImpl placeholder = new PlaceholderImpl();
		return placeholder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Target createTarget() {
		TargetImpl target = new TargetImpl();
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Place createPlace() {
		PlaceImpl place = new PlaceImpl();
		return place;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SimpleType createSimpleType() {
		SimpleTypeImpl simpleType = new SimpleTypeImpl();
		return simpleType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BasicType createBasicType() {
		BasicTypeImpl basicType = new BasicTypeImpl();
		return basicType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompositeType createCompositeType() {
		CompositeTypeImpl compositeType = new CompositeTypeImpl();
		return compositeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ServiceInterfaceType createServiceInterfaceType() {
		ServiceInterfaceTypeImpl serviceInterfaceType = new ServiceInterfaceTypeImpl();
		return serviceInterfaceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FunctionType createFunctionType() {
		FunctionTypeImpl functionType = new FunctionTypeImpl();
		return functionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SubappType createSubappType() {
		SubappTypeImpl subappType = new SubappTypeImpl();
		return subappType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StructType createStructType() {
		StructTypeImpl structType = new StructTypeImpl();
		return structType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AttributeType createAttributeType() {
		AttributeTypeImpl attributeType = new AttributeTypeImpl();
		return attributeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SimpleFB createSimpleFB() {
		SimpleFBImpl simpleFB = new SimpleFBImpl();
		return simpleFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BasicFB createBasicFB() {
		BasicFBImpl basicFB = new BasicFBImpl();
		return basicFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompositeFB createCompositeFB() {
		CompositeFBImpl compositeFB = new CompositeFBImpl();
		return compositeFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ServiceInterfaceFB createServiceInterfaceFB() {
		ServiceInterfaceFBImpl serviceInterfaceFB = new ServiceInterfaceFBImpl();
		return serviceInterfaceFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FunctionFB createFunctionFB() {
		FunctionFBImpl functionFB = new FunctionFBImpl();
		return functionFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypedSubapp createTypedSubapp() {
		TypedSubappImpl typedSubapp = new TypedSubappImpl();
		return typedSubapp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UntypedSubapp createUntypedSubapp() {
		UntypedSubappImpl untypedSubapp = new UntypedSubappImpl();
		return untypedSubapp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Connection createConnection() {
		ConnectionImpl connection = new ConnectionImpl();
		return connection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PIN createPIN() {
		PINImpl pin = new PINImpl();
		return pin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PinTarget createPinTarget() {
		PinTargetImpl pinTarget = new PinTargetImpl();
		return pinTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Attribute createAttribute() {
		AttributeImpl attribute = new AttributeImpl();
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AttributeDeclaration createAttributeDeclaration() {
		AttributeDeclarationImpl attributeDeclaration = new AttributeDeclarationImpl();
		return attributeDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public QueryPackage getQueryPackage() {
		return (QueryPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static QueryPackage getPackage() {
		return QueryPackage.eINSTANCE;
	}

} //QueryFactoryImpl
