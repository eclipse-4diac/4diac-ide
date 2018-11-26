/********************************************************************************
 * Copyright (c) 2008, 2010 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.Palette.impl;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.libraryElement.CompilableType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteGroupImpl#getEntries <em>Entries</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteGroupImpl#getSubGroups <em>Sub Groups</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.Palette.impl.PaletteGroupImpl#getLabel <em>Label</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PaletteGroupImpl extends EObjectImpl implements PaletteGroup {
	/**
	 * The cached value of the '{@link #getEntries() <em>Entries</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getEntries()
	 * @generated
	 * @ordered
	 */
	protected EList<PaletteEntry> entries;

	/**
	 * The cached value of the '{@link #getSubGroups() <em>Sub Groups</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSubGroups()
	 * @generated
	 * @ordered
	 */
	protected EList<PaletteGroup> subGroups;

	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected PaletteGroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PalettePackage.Literals.PALETTE_GROUP;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PaletteEntry> getEntries() {
		if (entries == null) {
			entries = new EObjectContainmentEList.Resolving<PaletteEntry>(PaletteEntry.class, this, PalettePackage.PALETTE_GROUP__ENTRIES);
		}
		return entries;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PaletteGroup> getSubGroups() {
		if (subGroups == null) {
			subGroups = new EObjectContainmentEList.Resolving<PaletteGroup>(PaletteGroup.class, this, PalettePackage.PALETTE_GROUP__SUB_GROUPS);
		}
		return subGroups;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.PALETTE_GROUP__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PalettePackage.PALETTE_GROUP__ENTRIES:
				return ((InternalEList<?>)getEntries()).basicRemove(otherEnd, msgs);
			case PalettePackage.PALETTE_GROUP__SUB_GROUPS:
				return ((InternalEList<?>)getSubGroups()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PalettePackage.PALETTE_GROUP__ENTRIES:
				return getEntries();
			case PalettePackage.PALETTE_GROUP__SUB_GROUPS:
				return getSubGroups();
			case PalettePackage.PALETTE_GROUP__LABEL:
				return getLabel();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PalettePackage.PALETTE_GROUP__ENTRIES:
				getEntries().clear();
				getEntries().addAll((Collection<? extends PaletteEntry>)newValue);
				return;
			case PalettePackage.PALETTE_GROUP__SUB_GROUPS:
				getSubGroups().clear();
				getSubGroups().addAll((Collection<? extends PaletteGroup>)newValue);
				return;
			case PalettePackage.PALETTE_GROUP__LABEL:
				setLabel((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case PalettePackage.PALETTE_GROUP__ENTRIES:
				getEntries().clear();
				return;
			case PalettePackage.PALETTE_GROUP__SUB_GROUPS:
				getSubGroups().clear();
				return;
			case PalettePackage.PALETTE_GROUP__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PalettePackage.PALETTE_GROUP__ENTRIES:
				return entries != null && !entries.isEmpty();
			case PalettePackage.PALETTE_GROUP__SUB_GROUPS:
				return subGroups != null && !subGroups.isEmpty();
			case PalettePackage.PALETTE_GROUP__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (label: "); //$NON-NLS-1$
		result.append(label);
		result.append(')');
		return result.toString();
	}

	@Override
	public CompilableType getTypeforName(String name) {
		for (Iterator<PaletteEntry> iterator = getEntries().iterator(); iterator
				.hasNext();) {
			PaletteEntry entry = iterator.next();
			if (entry.getLabel().equals(name)) {
				if(entry.getType() instanceof CompilableType){
					return (CompilableType)entry.getType();
				}
			}
		}
		return null;
	}
	
	/**
	 * This method adds an entry to a paletteGroup
	 * 
	 * @param entry
	 *          - the entry that will be added to the paletteGroup
	 */
	@Override
	public void addEntry(final PaletteEntry entry) {
		if (entry != null) {
			getEntries().add(entry);
		}
	}
	
	@Override
	public boolean isEmpty(){
		return (getSubGroups().isEmpty() && getEntries().isEmpty());
	}
	
	@Override
	public PaletteGroup getGroup(String label){
		PaletteGroup retVal = null;
		EList<PaletteGroup> groups = getSubGroups();
		
		for (PaletteGroup paletteGroup : groups) {
			if(paletteGroup.getLabel().equals(label)){
				retVal = paletteGroup;
				break;
			}
		}
		
		return retVal;
	}
	
	@Override
	public Palette getPallete(){
		Palette retval = null;
		
		EObject runner = eContainer();
		while (null != runner){
			if(runner instanceof Palette){
				retval = (Palette) runner;
				break;
			}
			runner = runner.eContainer();
		}
		
		return retval;
	}

	@Override
	public void removeEntryNamed(String entryName) {		
		PaletteEntry entry = getEntry(entryName);
		if(null != entry){
			EList<PaletteEntry> entries = getEntries();
			entries.remove(entry);
		}
		
	}
	
	@Override
	public PaletteEntry getEntry(String entryName){
		PaletteEntry retVal = null;
		if(null != getEntries()){
			for (PaletteEntry paletteEntry : getEntries()) {
				if(paletteEntry.getLabel().equals(entryName)){
					retVal = paletteEntry;
					break;
				}
			}
		}
		return retVal;
	}

	@Override
	public PaletteGroup getParentGroup() {
		if(null != eContainer()){
			if(eContainer() instanceof PaletteGroup){
				return (PaletteGroup)eContainer();
			}
		}
		return null;
	}

} // PaletteGroupImpl
