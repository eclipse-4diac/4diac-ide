/*******************************************************************************
 * Copyright (c) 2024 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contractSpec.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.fordiac.ide.contractSpec.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage
 * @generated
 */
public class ContractSpecSwitch<T> extends Switch<T>
{
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static ContractSpecPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ContractSpecSwitch()
  {
    if (modelPackage == null)
    {
      modelPackage = ContractSpecPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage)
  {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject)
  {
    switch (classifierID)
    {
      case ContractSpecPackage.MODEL:
      {
        Model model = (Model)theEObject;
        T result = caseModel(model);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContractSpecPackage.TIME_SPEC:
      {
        TimeSpec timeSpec = (TimeSpec)theEObject;
        T result = caseTimeSpec(timeSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContractSpecPackage.SINGLE_EVENT:
      {
        SingleEvent singleEvent = (SingleEvent)theEObject;
        T result = caseSingleEvent(singleEvent);
        if (result == null) result = caseTimeSpec(singleEvent);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContractSpecPackage.REPETITION:
      {
        Repetition repetition = (Repetition)theEObject;
        T result = caseRepetition(repetition);
        if (result == null) result = caseTimeSpec(repetition);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContractSpecPackage.REPETITION_OPTIONS:
      {
        RepetitionOptions repetitionOptions = (RepetitionOptions)theEObject;
        T result = caseRepetitionOptions(repetitionOptions);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContractSpecPackage.JITTER:
      {
        Jitter jitter = (Jitter)theEObject;
        T result = caseJitter(jitter);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContractSpecPackage.OFFSET:
      {
        Offset offset = (Offset)theEObject;
        T result = caseOffset(offset);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContractSpecPackage.REACTION:
      {
        Reaction reaction = (Reaction)theEObject;
        T result = caseReaction(reaction);
        if (result == null) result = caseTimeSpec(reaction);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContractSpecPackage.AGE:
      {
        Age age = (Age)theEObject;
        T result = caseAge(age);
        if (result == null) result = caseTimeSpec(age);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContractSpecPackage.CAUSAL_REACTION:
      {
        CausalReaction causalReaction = (CausalReaction)theEObject;
        T result = caseCausalReaction(causalReaction);
        if (result == null) result = caseTimeSpec(causalReaction);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContractSpecPackage.CAUSAL_AGE:
      {
        CausalAge causalAge = (CausalAge)theEObject;
        T result = caseCausalAge(causalAge);
        if (result == null) result = caseTimeSpec(causalAge);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContractSpecPackage.EVENT_EXPR:
      {
        EventExpr eventExpr = (EventExpr)theEObject;
        T result = caseEventExpr(eventExpr);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContractSpecPackage.EVENT_LIST:
      {
        EventList eventList = (EventList)theEObject;
        T result = caseEventList(eventList);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContractSpecPackage.EVENT_SPEC:
      {
        EventSpec eventSpec = (EventSpec)theEObject;
        T result = caseEventSpec(eventSpec);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContractSpecPackage.PORT:
      {
        Port port = (Port)theEObject;
        T result = casePort(port);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContractSpecPackage.INTERVAL:
      {
        Interval interval = (Interval)theEObject;
        T result = caseInterval(interval);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContractSpecPackage.TIME_EXPR:
      {
        TimeExpr timeExpr = (TimeExpr)theEObject;
        T result = caseTimeExpr(timeExpr);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContractSpecPackage.CAUSAL_FUNC_DECL:
      {
        CausalFuncDecl causalFuncDecl = (CausalFuncDecl)theEObject;
        T result = caseCausalFuncDecl(causalFuncDecl);
        if (result == null) result = caseTimeSpec(causalFuncDecl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContractSpecPackage.CLOCK_DEFINITION:
      {
        ClockDefinition clockDefinition = (ClockDefinition)theEObject;
        T result = caseClockDefinition(clockDefinition);
        if (result == null) result = caseTimeSpec(clockDefinition);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Model</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Model</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseModel(Model object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Time Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Time Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTimeSpec(TimeSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Single Event</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Single Event</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSingleEvent(SingleEvent object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Repetition</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Repetition</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRepetition(Repetition object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Repetition Options</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Repetition Options</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRepetitionOptions(RepetitionOptions object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Jitter</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Jitter</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJitter(Jitter object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Offset</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Offset</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseOffset(Offset object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Reaction</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Reaction</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseReaction(Reaction object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Age</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Age</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAge(Age object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Causal Reaction</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Causal Reaction</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCausalReaction(CausalReaction object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Causal Age</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Causal Age</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCausalAge(CausalAge object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Event Expr</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Event Expr</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEventExpr(EventExpr object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Event List</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Event List</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEventList(EventList object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Event Spec</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Event Spec</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEventSpec(EventSpec object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Port</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Port</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePort(Port object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Interval</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Interval</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInterval(Interval object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Time Expr</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Time Expr</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTimeExpr(TimeExpr object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Causal Func Decl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Causal Func Decl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCausalFuncDecl(CausalFuncDecl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Clock Definition</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Clock Definition</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseClockDefinition(ClockDefinition object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object)
  {
    return null;
  }

} //ContractSpecSwitch
