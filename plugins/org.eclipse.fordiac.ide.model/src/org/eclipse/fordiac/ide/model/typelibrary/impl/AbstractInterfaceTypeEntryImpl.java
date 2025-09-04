/*********************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Alois Zoitl - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary.impl;

import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.dataimport.FBTImporter;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.InterfaceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public abstract class AbstractInterfaceTypeEntryImpl<T extends FBType> extends AbstractCheckedTypeEntryImpl<T>
		implements InterfaceTypeEntry {

	private SoftReference<FBType> interfaceTypeRef;
	private final AtomicLong lastInterfaceModificationTimestamp = new AtomicLong(IResource.NULL_STAMP);
	private final AtomicReference<Set<TypeEntry>> ifDependencies = new AtomicReference<>(Collections.emptySet());

	protected AbstractInterfaceTypeEntryImpl(final Class<T> typeClass) {
		super(typeClass);
	}

	private InterfaceList basicGetInterface() {
		final SoftReference<FBType> interfaceRefCached = interfaceTypeRef;
		if (interfaceRefCached != null) {
			final FBType interfaceTypeCache = interfaceRefCached.get();
			if (interfaceTypeCache != null) {
				return interfaceTypeCache.getInterfaceList();
			}
		}
		return null;
	}

	@Override
	protected FBType basicGetType() {
		return (FBType) super.basicGetType();
	}

	protected synchronized NotificationChain basicSetInterface(final FBType newIfType,
			NotificationChain notifications) {
		final FBType oldIfType = (interfaceTypeRef != null) ? interfaceTypeRef.get() : null;
		if (newIfType != null) {
			encloseInResource(newIfType);
			newIfType.setTypeEntry(this);
			interfaceTypeRef = new SoftReference<>(newIfType);
		} else {
			interfaceTypeRef = null;
		}
		if (eNotificationRequired()) {
			notifications = chainNotification(notifications,
					new TypeEntryNotificationImpl(this, Notification.SET, TypeEntry.TYPE_ENTRY_INTERFACE_FEATURE,
							TypeEntry.TYPE_ENTRY_INTERFACE_FEATURE_ID, oldIfType, newIfType));
		}
		return notifications;
	}

	private FBType createIntefaceTypeFromTypeCache(final FBType fbTypeCache) {
		final FBType interfaceType = (FBType) EcoreUtil.create(fbTypeCache.eClass());
		interfaceType.setName(fbTypeCache.getName());
		interfaceType.setComment(fbTypeCache.getComment());
		interfaceType.setCompilerInfo(EcoreUtil.copy(fbTypeCache.getCompilerInfo()));
		interfaceType.setInterfaceList(fbTypeCache.getInterfaceList().copy());
		updateInterfaceDependencies(StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(interfaceType.eAllContents(), 0), false)
				.map(EObject::eCrossReferences).flatMap(Collection::stream).filter(LibraryElement.class::isInstance)
				.map(LibraryElement.class::cast).map(LibraryElement::getTypeEntry).filter(Objects::nonNull)
				.collect(Collectors.toUnmodifiableSet()));
		return interfaceType;
	}

	@Override
	public InterfaceList getInterface() {
		// check if interface is present and current
		InterfaceList interfaceList = basicGetInterface();
		if (interfaceList != null) {
			return interfaceList; // simple, non-contended case
		}

		// the hard way
		NotificationChain notifications = null;
		synchronized (this) {
			// check again
			interfaceList = basicGetInterface();
			if (interfaceList != null) {
				return interfaceList;
			}
			// _we_ need to (re-)load the interface

			// get and check file
			final IFile fileCached = getFile();
			if (fileCached == null) {
				return null; // no file, no type
			}

			// read modification stamp at the beginning to ensure the loaded interface is at
			// least as recent as the read modification stamp
			final long modificationStamp = fileCached.getModificationStamp();

			final FBType newInterfaceType = loadInterface();
			if (newInterfaceType == null) {
				return null;
			}

			interfaceList = newInterfaceType.getInterfaceList();

			notifications = basicSetInterface(newInterfaceType, notifications);

			// update the last modification stamp _after_ setting the interface to ensure
			// other readers see the new stamp only together with the new type
			lastInterfaceModificationTimestamp.set(modificationStamp);
		}
		// dispatch notifications
		if (notifications != null) {
			notifications.dispatch();
		}
		return interfaceList;
	}

	private final boolean isInterfaceFileContentChanged() {
		final IFile fileCached = getFile();
		if (fileCached != null) {
			final long modificationStamp = fileCached.getModificationStamp();
			return modificationStamp != IResource.NULL_STAMP
					&& modificationStamp != lastInterfaceModificationTimestamp.get();
		}
		return false;
	}

	private FBType loadInterface() {
		// check if we have type cached to be used for the interface
		final FBType fbTypeCache = basicGetType();
		if (fbTypeCache != null) {
			return createIntefaceTypeFromTypeCache(fbTypeCache);
		}

		try {
			final FBTImporter importer = (FBTImporter) getImporter();
			final FBType interfaceType = importer.loadInterface();
			updateInterfaceDependencies(importer.getDependencies());
			return interfaceType;
		} catch (final Exception e) {
			FordiacLogHelper.logWarning("Error loading type " + getFile().getName() + ": " + e.getMessage(), e); //$NON-NLS-1$ //$NON-NLS-2$
			return null;
		}
	}

	@Override
	public void notifyChanged(final Notification notification) {
		if ((notification.getFeature() == TypeEntry.TYPE_ENTRY_TYPE_FEATURE
				|| notification.getFeature() == TypeEntry.TYPE_ENTRY_TYPE_LIBRARY_FEATURE)
				&& ifDependencies.get().contains(notification.getNotifier())) {

			NotificationChain notifications = null;
			synchronized (this) {
				notifications = basicSetInterface(null, notifications);
			}

			if (notifications != null) {
				notifications.dispatch();
			}
		}
		super.notifyChanged(notification);
	}

	@Override
	protected NotificationChain performTypeRefresh(NotificationChain notifications) {
		if (isInterfaceFileContentChanged()) {
			// clear cached type
			notifications = basicSetInterface(null, notifications);
		}
		return super.performTypeRefresh(notifications);
	}

	private void updateInterfaceDependencies(final Set<TypeEntry> dependencies) {
		final Set<TypeEntry> oldDependencies = this.ifDependencies.getAndSet(Set.copyOf(dependencies));
		oldDependencies.stream().filter(Predicate.not(dependencies::contains))
				.forEachOrdered(entry -> entry.eAdapters().remove(this));
		dependencies.stream().filter(Predicate.not(oldDependencies::contains))
				.forEachOrdered(entry -> entry.eAdapters().add(this));
	}

	@Override
	protected NotificationChain updateTypeOnSave(final LibraryElement savedType, NotificationChain notifications) {
		final FBType newInterfaceType = createIntefaceTypeFromTypeCache((FBType) savedType);
		final long modificationStamp = getFile().getModificationStamp();
		notifications = basicSetInterface(newInterfaceType, notifications);
		lastInterfaceModificationTimestamp.set(modificationStamp);

		return super.updateTypeOnSave(savedType, notifications);
	}

}
