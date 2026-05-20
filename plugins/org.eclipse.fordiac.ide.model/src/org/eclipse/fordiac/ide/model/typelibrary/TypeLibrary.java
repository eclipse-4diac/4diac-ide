/********************************************************************************
 * Copyright (c) 2008, 2023 Profactor GmbH, TU Wien ACIN, fortiss GmbH, IBH Systems
 * 		            		Johannes Kepler University,
 *                          Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Martijn Rooker, Alois Zoitl, Monika Wenger, Jens Reimann,
 *  Waldemar Eisenmenger, Gerd Kainz
 *    - initial API and implementation and/or initial documentation
 *  Martin Melik-Merkumians - adds convenience methods
 *  Alois Zoitl - Changed to a per project Type and Data TypeLibrary
 *              - Added support for project renameing
 *              - Changed TypeLibrary from palette model to TypeEntry POJO classes
 *  Martin Jobst - migrate system handling to typelib
 *               - add function FB type
 *               - add global constants
 *  Sebastian Hollersbacher - add attribute
 *  Fabio Gandolfi - add loading of type library via manifest file
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import java.text.Collator;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.common.notify.impl.SingletonAdapterImpl;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.ConcurrentNotifierImpl;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.buildpath.Buildpath;
import org.eclipse.fordiac.ide.model.buildpath.BuildpathAttributes;
import org.eclipse.fordiac.ide.model.buildpath.SourceFolder;
import org.eclipse.fordiac.ide.model.buildpath.util.BuildpathUtil;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.impl.TypeEntryFactory;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class TypeLibrary extends ConcurrentNotifierImpl {

	public static final String TYPE_ENTRY_NAME_REFERENCES_FEATURE = "TYPE_ENTRY_NAME_REFERENCES_FEATURE"; //$NON-NLS-1$

	public static final int TYPE_ENTRY_NAME_REFERENCES_FEATURE_ID = 1;

	private IProject project;
	private Buildpath buildpath;
	private final DataTypeLibrary dataTypeLib = new DataTypeLibrary(this);
	private final Map<String, AdapterTypeEntry> adapterTypes = new ConcurrentHashMap<>();
	private final Map<String, AttributeTypeEntry> attributeTypes = new ConcurrentHashMap<>();
	private final Map<String, DeviceTypeEntry> deviceTypes = new ConcurrentHashMap<>();
	private final Map<String, FBTypeEntry> fbTypes = new ConcurrentHashMap<>();
	private final Map<String, ResourceTypeEntry> resourceTypes = new ConcurrentHashMap<>();
	private final Map<String, SegmentTypeEntry> segmentTypes = new ConcurrentHashMap<>();
	private final Map<String, SubAppTypeEntry> subAppTypes = new ConcurrentHashMap<>();
	private final Map<String, SystemEntry> systems = new ConcurrentHashMap<>();
	private final Map<String, GlobalConstantsEntry> globalConstants = new ConcurrentHashMap<>();
	private final Map<String, TypeEntry> programTypes = new ConcurrentHashMap<>();
	private final Map<IFile, TypeEntry> fileMap = new ConcurrentHashMap<>();
	private final Map<String, AtomicInteger> packages = new ConcurrentHashMap<>();
	private final Queue<TypeEntry> duplicates = new ConcurrentLinkedQueue<>();
	private final NavigableSet<IProject> referencedProjects = new ConcurrentSkipListSet<>(
			Comparator.comparing(IProject::getName));
	private final TypeLibraryAdapter typeLibraryAdapter = new TypeLibraryAdapter();

	public Stream<AdapterTypeEntry> getAdapterTypes() {
		return adapterTypes.values().stream().filter(Predicate.not(TypeEntry::hasError));
	}

	public Stream<AdapterTypeEntry> getAdapterTypesSorted() {
		return adapterTypes.values().stream().filter(Predicate.not(TypeEntry::hasError))
				.sorted((o1, o2) -> Collator.getInstance().compare(o1.getFullTypeName(), o2.getFullTypeName()));
	}

	public Stream<AttributeTypeEntry> getAttributeTypes() {
		return attributeTypes.values().stream().filter(Predicate.not(TypeEntry::hasError));
	}

	public Stream<DeviceTypeEntry> getDeviceTypes() {
		return deviceTypes.values().stream().filter(Predicate.not(TypeEntry::hasError));
	}

	public Stream<FBTypeEntry> getFbTypes() {
		return fbTypes.values().stream().filter(Predicate.not(TypeEntry::hasError));
	}

	public Stream<ResourceTypeEntry> getResourceTypes() {
		return resourceTypes.values().stream().filter(Predicate.not(TypeEntry::hasError));
	}

	public Stream<SegmentTypeEntry> getSegmentTypes() {
		return segmentTypes.values().stream().filter(Predicate.not(TypeEntry::hasError));
	}

	public Stream<SubAppTypeEntry> getSubAppTypes() {
		return subAppTypes.values().stream().filter(Predicate.not(TypeEntry::hasError));
	}

	public Stream<SystemEntry> getSystems() {
		return systems.values().stream().filter(Predicate.not(TypeEntry::hasError));
	}

	public Stream<GlobalConstantsEntry> getGlobalConstants() {
		return globalConstants.values().stream().filter(Predicate.not(TypeEntry::hasError));
	}

	public Stream<TypeEntry> getProgramTypes() {
		return programTypes.values().stream().filter(Predicate.not(TypeEntry::hasError));
	}

	public Set<String> getPackages() {
		return Collections.unmodifiableSet(packages.keySet());
	}

	public Stream<TypeEntry> getAllTypes() {
		return fileMap.values().stream().filter(Predicate.not(TypeEntry::hasError));
	}

	public Stream<FBTypeEntry> getCompositeFBTypes() {
		return fbTypes.values().stream().filter(Predicate.not(TypeEntry::hasError))
				.filter(entry -> LibraryElementPackage.Literals.COMPOSITE_FB_TYPE.equals(entry.getTypeEClass()));
	}

	public AdapterTypeEntry getAdapterTypeEntry(final String typeName) {
		return adapterTypes.get(typeName.toLowerCase());
	}

	public AttributeTypeEntry getAttributeTypeEntry(final String typeName) {
		return attributeTypes.get(typeName.toLowerCase());
	}

	public DeviceTypeEntry getDeviceTypeEntry(final String typeName) {
		return deviceTypes.get(typeName.toLowerCase());
	}

	public FBTypeEntry getFBTypeEntry(final String typeName) {
		return fbTypes.get(typeName.toLowerCase());
	}

	public ResourceTypeEntry getResourceTypeEntry(final String typeName) {
		return resourceTypes.get(typeName.toLowerCase());
	}

	public SegmentTypeEntry getSegmentTypeEntry(final String typeName) {
		return segmentTypes.get(typeName.toLowerCase());
	}

	public SubAppTypeEntry getSubAppTypeEntry(final String typeName) {
		return subAppTypes.get(typeName.toLowerCase());
	}

	public SystemEntry getSystemEntry(final String name) {
		return systems.get(name.toLowerCase());
	}

	public GlobalConstantsEntry getGlobalConstantsEntry(final String name) {
		return globalConstants.get(name.toLowerCase());
	}

	public TypeEntry getFBOrSubAppType(final String typeName) {
		final TypeEntry fb = getFBTypeEntry(typeName);
		return fb != null ? fb : getSubAppTypeEntry(typeName);
	}

	public TypeEntry getTypeEntry(final IFile typeFile) {
		return fileMap.get(typeFile);
	}

	public DataTypeLibrary getDataTypeLibrary() {
		return dataTypeLib;
	}

	public IProject getProject() {
		return project;
	}

	public Buildpath getBuildpath() {
		return buildpath;
	}

	public NavigableSet<IProject> getReferencedProjects() {
		return Collections.unmodifiableNavigableSet(referencedProjects);
	}

	/** Instantiates a new fB type library. */
	TypeLibrary(final IProject typeLibraryProject) {
		project = typeLibraryProject;
		buildpath = BuildpathUtil.loadBuildpath(typeLibraryProject);
		if (typeLibraryProject != null && typeLibraryProject.isAccessible()) {
			checkAdditions();
		}
	}

	public TypeEntry createTypeEntry(final IFile file) {
		if (file == null || BuildpathUtil.findSourceFolder(buildpath, file).isEmpty()) {
			return null;
		}

		return fileMap.computeIfAbsent(file, f -> {
			final TypeEntry entry = TypeEntryFactory.INSTANCE.createTypeEntry(file);
			if (entry != null) {
				final Optional<String> message = IdentifierVerifier.verifyIdentifier(entry.getTypeName());
				if (message.isEmpty()) {
					// add is adding the entry to file map and all other containers required
					entry.setTypeLibrary(this);
					addTypeEntryNameReference(entry);
					return entry;
				}
				createTypeLibraryMarker(file, message.get());
			}
			return null;
		});
	}

	public TypeEntry createErrorTypeEntry(final String typeName, final EClass typeClass) {
		final TypeEntry entry = TypeEntryFactory.INSTANCE.createTypeEntry(typeClass);
		if (entry == null) {
			throw new IllegalArgumentException("Unknown type class " + typeClass.getName()); //$NON-NLS-1$
		}
		final LibraryElement libraryElement = entry.getType();
		PackageNameHelper.setFullTypeName(libraryElement, typeName);
		entry.setType(libraryElement); // update type name in entry
		entry.setTypeLibrary(this);
		final TypeEntry oldEntry = putBlockTypeEntryIfAbsent(entry);
		return oldEntry != null ? oldEntry : entry;
	}

	private TypeEntry removeErrorTypeEntry(final TypeEntry entry) {
		TypeEntry oldEntry = getBlockTypeEntry(entry);
		while (oldEntry != null && oldEntry.getFile() == null) {
			if (removeBlockTypeEntry(oldEntry)) {
				return oldEntry;
			}
			oldEntry = getBlockTypeEntry(entry);
		}
		return null;
	}

	public void addTypeEntry(final TypeEntry entry) {
		if (entry.getTypeLibrary() != null) {
			entry.getTypeLibrary().removeTypeEntry(entry);
		}
		entry.setTypeLibrary(this);
		if (entry.getFile() != null) {
			fileMap.put(entry.getFile(), entry);
		}
		addTypeEntryNameReference(entry);
	}

	public void addTypeEntryNameReference(final TypeEntry entry) {
		boolean typeEntryAdded;
		if (entry instanceof final DataTypeEntry dtEntry) {
			typeEntryAdded = dataTypeLib.addTypeEntry(dtEntry);
			if (!typeEntryAdded) {
				handleDuplicateTypeName(entry);
			}
		} else {
			// remove stale error marker data type
			final TypeEntry oldEntry = removeErrorTypeEntry(entry);
			// add new type entry
			typeEntryAdded = addBlockTypeEntry(entry);
			if (!typeEntryAdded) {
				handleDuplicateTypeName(entry);
			}
			// trigger transitive refresh after new entry has been added
			if (oldEntry != null) {
				oldEntry.setTypeLibrary(null);
			}
		}
		boolean programTypeEntryAdded = true;
		if (isProgramTypeEntry(entry)) {
			programTypeEntryAdded = addProgramTypeEntry(entry);
			if (!programTypeEntryAdded) {
				handleDuplicateTypeName(entry);
			}
		}
		if (typeEntryAdded && programTypeEntryAdded) {
			deleteTypeLibraryMarkers(entry.getFile());
		}
		addPackageNameReference(PackageNameHelper.extractPackageName(entry.getFullTypeName()));
		if (isPublicNameReference(entry)) {
			notifyTypeEntryNameReferenceAdded(entry);
		}
	}

	private void handleDuplicateTypeName(final TypeEntry entry) {
		if (entry.getFile() != null && entry.getFile().exists()) {
			duplicates.add(entry);
			createTypeLibraryMarker(entry.getFile(),
					MessageFormat.format(Messages.TypeLibrary_TypeExists, entry.getFullTypeName()));
		} else {
			FordiacLogHelper.logWarning(MessageFormat.format(Messages.TypeLibrary_TypeExists, entry.getFullTypeName()));
		}
	}

	public void removeTypeEntry(final TypeEntry entry) {
		removeTypeEntryNameReference(entry);
		if (entry.getFile() != null) {
			fileMap.remove(entry.getFile());
		}
		entry.setTypeLibrary(null);
	}

	public void removeTypeEntryNameReference(final TypeEntry entry) {
		if (entry instanceof final DataTypeEntry dtEntry) {
			dataTypeLib.removeTypeEntry(dtEntry);
		} else {
			removeBlockTypeEntry(entry);
		}
		if (isProgramTypeEntry(entry)) {
			removeProgramTypeEntry(entry);
		}
		removePackageNameReference(PackageNameHelper.extractPackageName(entry.getFullTypeName()));
		deleteTypeLibraryMarkers(entry.getFile());
		if (isPublicNameReference(entry)) {
			notifyTypeEntryNameReferenceRemoved(entry);
		}
		retryDuplicates();
	}

	private boolean isPublicNameReference(final TypeEntry entry) {
		return entry.getTypeLibrary() == this && Boolean.parseBoolean(BuildpathUtil
				.findSourceFolder(buildpath, entry.getFile()).map(SourceFolder::getAttributes)
				.map(attrs -> BuildpathAttributes.getAttributeValue(attrs, BuildpathAttributes.EXPORT)).orElse(null));
	}

	private void retryDuplicates() {
		duplicates.removeIf(this::retryDuplicate);
	}

	private boolean retryDuplicate(final TypeEntry entry) {
		if (!isDuplicateRetryCandidate(entry)) {
			return true;
		}
		if (entry instanceof final DataTypeEntry dtEntry) {
			if (dataTypeLib.addTypeEntry(dtEntry)) {
				deleteTypeLibraryMarkers(entry.getFile());
				if (isPublicTypeEntryNameReference(entry)) {
					notifyTypeEntryNameReferenceAdded(entry);
				}
				return true;
			}
		} else if (addBlockTypeEntry(entry)) {
			deleteTypeLibraryMarkers(entry.getFile());
			if (isPublicTypeEntryNameReference(entry)) {
				notifyTypeEntryNameReferenceAdded(entry);
			}
			return true;
		}
		return false;
	}

	private boolean isDuplicateRetryCandidate(final TypeEntry entry) {
		final TypeLibrary entryTypeLibrary = entry.getTypeLibrary();
		if (entryTypeLibrary == this) {
			return exists(entry);
		}
		return isReferencedPublicTypeEntryNameReference(entry, entryTypeLibrary);
	}

	private boolean isPublicTypeEntryNameReference(final TypeEntry entry) {
		final TypeLibrary entryTypeLibrary = entry.getTypeLibrary();
		return entryTypeLibrary == this ? isPublicNameReference(entry)
				: isReferencedPublicTypeEntryNameReference(entry, entryTypeLibrary);
	}

	private boolean isReferencedPublicTypeEntryNameReference(final TypeEntry entry,
			final TypeLibrary referencedTypeLibrary) {
		return referencedTypeLibrary != null && referencedTypeLibrary != this
				&& referencedTypeLibrary.getProject() != null
				&& referencedProjects.contains(referencedTypeLibrary.getProject())
				&& referencedTypeLibrary.exists(entry) && referencedTypeLibrary.containsTypeEntry(entry)
				&& referencedTypeLibrary.isPublicNameReference(entry);
	}

	private boolean containsTypeEntry(final TypeEntry entry) {
		return entry.getFile() != null && getTypeEntry(entry.getFile()) == entry;
	}

	protected void addPackageNameReference(final String packageName) {
		if (packageName != null && !packageName.isEmpty()) {
			packages.computeIfAbsent(packageName.toLowerCase(), key -> new AtomicInteger()).incrementAndGet();
		}
	}

	protected void removePackageNameReference(final String packageName) {
		if (packageName != null && !packageName.isEmpty()) {
			packages.computeIfPresent(packageName.toLowerCase(),
					(key, value) -> value.decrementAndGet() > 0 ? value : null);
		}
	}

	protected boolean addProgramTypeEntry(final TypeEntry entry) {
		final String fullTypeName = entry.getFullTypeName().toLowerCase();
		return programTypes.putIfAbsent(fullTypeName, entry) == null;
	}

	protected boolean removeProgramTypeEntry(final TypeEntry entry) {
		final String fullTypeName = entry.getFullTypeName().toLowerCase();
		return programTypes.remove(fullTypeName, entry);
	}

	public void refresh() {
		buildpath = BuildpathUtil.loadBuildpath(project);
		checkDeletions();
		checkAdditions();
	}

	private void checkDeletions() {
		checkReferencedProjectDeletions();
		checkDeletionsForTypeGroup(adapterTypes);
		checkDeletionsForTypeGroup(attributeTypes);
		checkDeletionsForTypeGroup(deviceTypes);
		checkDeletionsForTypeGroup(fbTypes);
		checkDeletionsForTypeGroup(resourceTypes);
		checkDeletionsForTypeGroup(segmentTypes);
		checkDeletionsForTypeGroup(subAppTypes);
		checkDeletionsForTypeGroup(systems);
		checkDeletionsForTypeGroup(globalConstants);
		checkDeletionsForTypeGroup(dataTypeLib.getDerivedDataTypes());
		fileMap.values().removeIf(Predicate.not(this::exists));
	}

	private void checkDeletionsForTypeGroup(final Map<String, ? extends TypeEntry> typeEntries) {
		checkDeletionsForTypeGroup(typeEntries.values().stream());
	}

	private void checkDeletionsForTypeGroup(final Stream<? extends TypeEntry> typeEntries) {
		typeEntries.filter(Predicate.not(this::exists)).forEachOrdered(this::removeTypeEntry);
	}

	private boolean exists(final TypeEntry entry) {
		return entry.getFile() != null && entry.getFile().exists()
				&& BuildpathUtil.findSourceFolder(buildpath, entry.getFile()).isPresent();
	}

	private void checkAdditions() {
		try {
			checkReferencedProjectsAdditions();
			checkAdditions(project);
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	private void checkAdditions(final IContainer container) throws CoreException {
		final IResource[] members = container.members();

		for (final IResource resource : members) {
			if (resource instanceof final IFolder folder) {
				checkAdditions(folder);
			}
			if (resource instanceof final IFile file && !containsType(file)) {
				// only add new entry if it does not exist
				createTypeEntry(file);
			}
		}
	}

	private void checkReferencedProjectsAdditions() throws CoreException {
		for (final IProject referencedProject : project.getReferencedProjects()) {
			addReferencedProject(referencedProject);
		}
	}

	private void checkReferencedProjectDeletions() {
		final Set<IProject> currentReferencedProjects = new HashSet<>();
		try {
			currentReferencedProjects.addAll(Arrays.asList(project.getReferencedProjects()));
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
		for (final IProject referencedProject : referencedProjects) {
			if (!currentReferencedProjects.contains(referencedProject)) {
				removeReferencedProject(referencedProject);
			}
		}
	}

	public boolean containsType(final IFile file) {
		return (null != getTypeEntry(file));
	}

	public TypeEntry find(final String name) {
		return programTypes.get(name.toLowerCase());
	}

	public List<TypeEntry> findUnqualified(final String name) {
		final String unqualifiedName = PackageNameHelper.extractPlainTypeName(name);
		return programTypes.values().stream().filter(entry -> unqualifiedName.equalsIgnoreCase(entry.getTypeName()))
				.toList();
	}

	private boolean addBlockTypeEntry(final TypeEntry entry) {
		return putBlockTypeEntryIfAbsent(entry) == null;
	}

	private TypeEntry putBlockTypeEntryIfAbsent(final TypeEntry entry) {
		final String fullTypeName = entry.getFullTypeName().toLowerCase();
		return switch (entry) {
		case final AdapterTypeEntry adpEntry -> adapterTypes.putIfAbsent(fullTypeName, adpEntry);
		case final AttributeTypeEntry atpEntry -> attributeTypes.putIfAbsent(fullTypeName, atpEntry);
		case final DeviceTypeEntry devEntry -> deviceTypes.putIfAbsent(fullTypeName, devEntry);
		case final FBTypeEntry fbtEntry -> fbTypes.putIfAbsent(fullTypeName, fbtEntry);
		case final ResourceTypeEntry resEntry -> resourceTypes.putIfAbsent(fullTypeName, resEntry);
		case final SegmentTypeEntry segEntry -> segmentTypes.putIfAbsent(fullTypeName, segEntry);
		case final SubAppTypeEntry subAppEntry -> subAppTypes.putIfAbsent(fullTypeName, subAppEntry);
		case final SystemEntry sysEntry -> systems.putIfAbsent(fullTypeName, sysEntry);
		case final GlobalConstantsEntry globalConstEntry -> globalConstants.putIfAbsent(fullTypeName, globalConstEntry);
		default -> {
			FordiacLogHelper.logError("Unknown type entry to be added to library: " + entry.getClass().getName()); //$NON-NLS-1$
			yield null;
		}
		};
	}

	private boolean removeBlockTypeEntry(final TypeEntry entry) {
		final String fullTypeName = entry.getFullTypeName().toLowerCase();
		return switch (entry) {
		case final AdapterTypeEntry adpEntry -> adapterTypes.remove(fullTypeName, adpEntry);
		case final AttributeTypeEntry atpEntry -> attributeTypes.remove(fullTypeName, atpEntry);
		case final DeviceTypeEntry devEntry -> deviceTypes.remove(fullTypeName, devEntry);
		case final FBTypeEntry fbtEntry -> fbTypes.remove(fullTypeName, fbtEntry);
		case final ResourceTypeEntry resEntry -> resourceTypes.remove(fullTypeName, resEntry);
		case final SegmentTypeEntry segEntry -> segmentTypes.remove(fullTypeName, segEntry);
		case final SubAppTypeEntry subAppEntry -> subAppTypes.remove(fullTypeName, subAppEntry);
		case final SystemEntry sysEntry -> systems.remove(fullTypeName, sysEntry);
		case final GlobalConstantsEntry globalConstEntry -> globalConstants.remove(fullTypeName, globalConstEntry);
		default -> {
			FordiacLogHelper.logError("Unknown type entry to be removed from library: " + entry.getClass().getName()); //$NON-NLS-1$
			yield true;
		}
		};
	}

	private TypeEntry getBlockTypeEntry(final TypeEntry entry) {
		final String fullTypeName = entry.getFullTypeName().toLowerCase();
		return switch (entry) {
		case final AdapterTypeEntry adpEntry -> adapterTypes.get(fullTypeName);
		case final AttributeTypeEntry atpEntry -> attributeTypes.get(fullTypeName);
		case final DeviceTypeEntry devEntry -> deviceTypes.get(fullTypeName);
		case final FBTypeEntry fbtEntry -> fbTypes.get(fullTypeName);
		case final ResourceTypeEntry resEntry -> resourceTypes.get(fullTypeName);
		case final SegmentTypeEntry segEntry -> segmentTypes.get(fullTypeName);
		case final SubAppTypeEntry subAppEntry -> subAppTypes.get(fullTypeName);
		case final SystemEntry sysEntry -> systems.get(fullTypeName);
		case final GlobalConstantsEntry globalConstEntry -> globalConstants.get(fullTypeName);
		default -> {
			FordiacLogHelper.logError("Unknown type entry to be retrieved from library: " + entry.getClass().getName()); //$NON-NLS-1$
			yield null;
		}
		};
	}

	private boolean addReferencedProject(final IProject referencedProject) {
		if (!referencedProjects.add(referencedProject)) {
			return false;
		}
		final TypeLibrary referencedTypeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(referencedProject);
		referencedTypeLibrary.eAdapters().add(typeLibraryAdapter);
		referencedTypeLibrary.getAllPublicTypes().forEachOrdered(this::addTypeEntryNameReference);
		return true;
	}

	private boolean removeReferencedProject(final IProject referencedProject) {
		if (!referencedProjects.remove(referencedProject)) {
			return false;
		}
		final TypeLibrary referencedTypeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(referencedProject);
		referencedTypeLibrary.eAdapters().remove(typeLibraryAdapter);
		removeDuplicateTypeEntriesFromProject(referencedProject);
		removeReferencedTypeEntryNameReferences(referencedTypeLibrary);
		return true;
	}

	private Stream<TypeEntry> getAllPublicTypes() {
		return fileMap.values().stream().filter(this::isPublicNameReference);
	}

	private void removeReferencedTypeEntryNameReferences(final TypeLibrary referencedTypeLibrary) {
		final var referencedTypes = referencedTypeLibrary.getAllTypes().iterator();
		while (referencedTypes.hasNext()) {
			removeReferencedProjectTypeEntryNameReference(referencedTypes.next());
		}
	}

	private void removeDuplicateTypeEntriesFromProject(final IProject referencedProject) {
		duplicates.removeIf(entry -> isTypeEntryFromProject(entry, referencedProject));
	}

	private void removeReferencedProjectTypeEntryNameReference(final TypeEntry entry) {
		if (hasTypeEntryNameReference(entry)) {
			removeTypeEntryNameReference(entry);
			notifyTypeEntryNameReferenceRemoved(entry);
		}
	}

	private boolean hasTypeEntryNameReference(final TypeEntry entry) {
		return switch (entry) {
		case final DataTypeEntry dtEntry ->
			hasDataTypeEntryNameReference(dtEntry) || hasProgramTypeEntryNameReference(entry);
		default -> getBlockTypeEntry(entry) == entry || hasProgramTypeEntryNameReference(entry);
		};
	}

	private boolean hasDataTypeEntryNameReference(final DataTypeEntry entry) {
		return dataTypeLib.getDerivedTypeEntry(entry.getFullTypeName()) == entry;
	}

	private boolean hasProgramTypeEntryNameReference(final TypeEntry entry) {
		return isProgramTypeEntry(entry) && programTypes.get(entry.getFullTypeName().toLowerCase()) == entry;
	}

	private void notifyTypeEntryNameReferenceAdded(final TypeEntry entry) {
		if (eNotificationRequired()) {
			eNotify(new TypeLibraryNotificationImpl(this, Notification.ADD, TYPE_ENTRY_NAME_REFERENCES_FEATURE,
					TYPE_ENTRY_NAME_REFERENCES_FEATURE_ID, null, entry));
		}
	}

	private void notifyTypeEntryNameReferenceRemoved(final TypeEntry entry) {
		if (eNotificationRequired()) {
			eNotify(new TypeLibraryNotificationImpl(this, Notification.REMOVE, TYPE_ENTRY_NAME_REFERENCES_FEATURE,
					TYPE_ENTRY_NAME_REFERENCES_FEATURE_ID, entry, null));
		}
	}

	private static boolean isTypeEntryFromProject(final TypeEntry entry, final IProject project) {
		return entry.getFile() != null && project.equals(entry.getFile().getProject());
	}

	private static boolean isProgramTypeEntry(final TypeEntry entry) {
		return entry instanceof FBTypeEntry || entry instanceof SubAppTypeEntry || entry instanceof DataTypeEntry;
	}

	private void createTypeLibraryMarker(final IResource resource, final String message) {
		if (resource != null && project.equals(resource.getProject())) {
			FordiacMarkerHelper.createMarkers(resource, List.of(ErrorMarkerBuilder.createErrorMarkerBuilder(message)
					.setType(FordiacErrorMarker.TYPE_LIBRARY_MARKER)));
		}
	}

	private void deleteTypeLibraryMarkers(final IResource resource) {
		if (resource != null && project.equals(resource.getProject())) {
			FordiacMarkerHelper.updateMarkers(resource, FordiacErrorMarker.TYPE_LIBRARY_MARKER, Collections.emptyList(),
					true);
		}
	}

	void setProject(final IProject newProject) {
		project = newProject;
	}

	private static class TypeLibraryNotificationImpl extends NotificationImpl {
		private final TypeLibrary notifier;
		private final String feature;
		private final int featureID;

		public TypeLibraryNotificationImpl(final TypeLibrary notifier, final int eventType, final String feature,
				final int featureID, final Object oldValue, final Object newValue) {
			super(eventType, oldValue, newValue, NO_INDEX);
			this.notifier = notifier;
			this.feature = feature;
			this.featureID = featureID;
		}

		@Override
		public TypeLibrary getNotifier() {
			return notifier;
		}

		@Override
		public Object getFeature() {
			return feature;
		}

		@Override
		public int getFeatureID(final Class<?> expectedClass) {
			return featureID;
		}
	}

	private class TypeLibraryAdapter extends SingletonAdapterImpl {

		@Override
		public void notifyChanged(final Notification msg) {
			super.notifyChanged(msg);
			if (msg.getNotifier() instanceof final TypeLibrary typeLibrary
					&& referencedProjects.contains(typeLibrary.getProject())) {
				switch (msg.getEventType()) {
				case Notification.ADD -> addTypeEntryNameReference((TypeEntry) msg.getNewValue());
				case Notification.ADD_MANY -> ((Collection<?>) msg.getNewValue()).stream().map(TypeEntry.class::cast)
						.forEachOrdered(TypeLibrary.this::addTypeEntryNameReference);
				case Notification.REMOVE -> removeTypeEntryNameReference((TypeEntry) msg.getOldValue());
				case Notification.REMOVE_MANY -> ((Collection<?>) msg.getOldValue()).stream().map(TypeEntry.class::cast)
						.forEachOrdered(TypeLibrary.this::removeTypeEntryNameReference);
				default -> {
					// do nothing
				}
				}
			}
		}
	}
}
