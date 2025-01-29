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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.buildpath.Buildpath;
import org.eclipse.fordiac.ide.model.buildpath.util.BuildpathUtil;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.impl.ErrorAdapterTypeEntryImpl;
import org.eclipse.fordiac.ide.model.typelibrary.impl.ErrorAttributeTypeEntryImpl;
import org.eclipse.fordiac.ide.model.typelibrary.impl.ErrorFBTypeEntryImpl;
import org.eclipse.fordiac.ide.model.typelibrary.impl.ErrorSubAppTypeEntryImpl;
import org.eclipse.fordiac.ide.model.typelibrary.impl.TypeEntryFactory;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class TypeLibrary {

	private IProject project;
	private Buildpath buildpath;
	private final DataTypeLibrary dataTypeLib = new DataTypeLibrary();
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
	private final Map<String, TypeEntry> errorTypes = new ConcurrentHashMap<>();
	private final Map<IFile, TypeEntry> fileMap = new ConcurrentHashMap<>();
	private final Map<String, AtomicInteger> packages = new ConcurrentHashMap<>();
	private final Queue<TypeEntry> duplicates = new ConcurrentLinkedQueue<>();

	public Collection<AdapterTypeEntry> getAdapterTypes() {
		return Collections.unmodifiableCollection(adapterTypes.values());
	}

	public List<AdapterTypeEntry> getAdapterTypesSorted() {
		return adapterTypes.values().stream()
				.sorted((o1, o2) -> Collator.getInstance().compare(o1.getFullTypeName(), o2.getFullTypeName()))
				.toList();
	}

	public Collection<AttributeTypeEntry> getAttributeTypes() {
		return Collections.unmodifiableCollection(attributeTypes.values());
	}

	public Collection<DeviceTypeEntry> getDeviceTypes() {
		return Collections.unmodifiableCollection(deviceTypes.values());
	}

	public Collection<FBTypeEntry> getFbTypes() {
		return Collections.unmodifiableCollection(fbTypes.values());
	}

	public Collection<ResourceTypeEntry> getResourceTypes() {
		return Collections.unmodifiableCollection(resourceTypes.values());
	}

	public Collection<SegmentTypeEntry> getSegmentTypes() {
		return Collections.unmodifiableCollection(segmentTypes.values());
	}

	public Collection<SubAppTypeEntry> getSubAppTypes() {
		return Collections.unmodifiableCollection(subAppTypes.values());
	}

	public Collection<SystemEntry> getSystems() {
		return Collections.unmodifiableCollection(systems.values());
	}

	public Collection<GlobalConstantsEntry> getGlobalConstants() {
		return Collections.unmodifiableCollection(globalConstants.values());
	}

	public Collection<TypeEntry> getProgramTypes() {
		return Collections.unmodifiableCollection(programTypes.values());
	}

	public Set<String> getPackages() {
		return Collections.unmodifiableSet(packages.keySet());
	}

	public Collection<TypeEntry> getAllTypes() {
		return Collections.unmodifiableCollection(fileMap.values());
	}

	public Stream<FBTypeEntry> getCompositeFBTypes() {
		return fbTypes.values().stream()
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

	public void reload() {
		adapterTypes.clear();
		attributeTypes.clear();
		deviceTypes.clear();
		fbTypes.clear();
		resourceTypes.clear();
		segmentTypes.clear();
		subAppTypes.clear();
		systems.clear();
		globalConstants.clear();
		dataTypeLib.clear();
		programTypes.clear();
		fileMap.clear();
		packages.clear();
		deleteTypeLibraryMarkers(project);
		buildpath = BuildpathUtil.loadBuildpath(project);
		checkAdditions(project);
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

	/** Instantiates a new fB type library. */
	TypeLibrary(final IProject project) {
		this.project = project;
		if (project != null && project.isAccessible()) {
			buildpath = BuildpathUtil.loadBuildpath(project);
			checkAdditions(project);
		}
	}

	public TypeEntry createTypeEntry(final IFile file) {
		if (BuildpathUtil.findSourceFolder(buildpath, file).isEmpty()) {
			return null;
		}
		final TypeEntry entry = fileMap.computeIfAbsent(file, TypeEntryFactory.INSTANCE::createTypeEntry);
		if (entry != null) {
			final Optional<String> message = IdentifierVerifier.verifyIdentifier(entry.getTypeName());
			if (message.isEmpty()) {
				addTypeEntry(entry);
			} else {
				createTypeLibraryMarker(file, message.get());
			}
		}
		return entry;
	}

	public TypeEntry createErrorTypeEntry(final String typeName, final EClass typeClass) {
		final String resolvedTypeName = typeName == null ? "" : typeName; //$NON-NLS-1$
		return errorTypes.computeIfAbsent(resolvedTypeName.toLowerCase(), name -> {
			final LibraryElement libraryElement = createErrorType(resolvedTypeName, typeClass);
			final TypeEntry entry = createErrorTypeEntry(libraryElement);
			entry.setType(libraryElement);
			entry.setTypeLibrary(this);
			return entry;
		});
	}

	private static LibraryElement createErrorType(final String typeName, final EClass typeClass) {
		final LibraryElement libraryElement = (LibraryElement) LibraryElementFactory.eINSTANCE.create(typeClass);
		PackageNameHelper.setFullTypeName(libraryElement, typeName);
		if (libraryElement instanceof final FBType fbType) {
			fbType.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		} else if (libraryElement instanceof final AttributeDeclaration attributeDeclaration) {
			final DirectlyDerivedType type = DataFactory.eINSTANCE.createDirectlyDerivedType();
			type.setName(libraryElement.getName());
			type.setBaseType(ElementaryTypes.STRING);
			attributeDeclaration.setType(type);
		}
		return libraryElement;
	}

	private static TypeEntry createErrorTypeEntry(final LibraryElement libraryElement) {
		if (libraryElement instanceof AdapterType) {
			return new ErrorAdapterTypeEntryImpl();
		}
		if (libraryElement instanceof SubAppType) {
			return new ErrorSubAppTypeEntryImpl();
		}
		if (libraryElement instanceof FBType) {
			return new ErrorFBTypeEntryImpl();
		}
		if (libraryElement instanceof AttributeDeclaration) {
			return new ErrorAttributeTypeEntryImpl();
		}
		throw new UnsupportedOperationException(
				"Cannot create error type entry for " + libraryElement.eClass().getName()); //$NON-NLS-1$
	}

	private void removeErrorTypeEntry(final String typeName) {
		final TypeEntry entry = errorTypes.remove(typeName.toLowerCase());
		if (entry != null) {
			entry.setTypeLibrary(null);
		}
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
		if (entry instanceof final DataTypeEntry dtEntry) {
			if (!dataTypeLib.addTypeEntry(dtEntry)) {
				handleDuplicateTypeName(entry);
			}
		} else {
			removeErrorTypeEntry(entry.getFullTypeName());
			if (!addBlockTypeEntry(entry)) {
				handleDuplicateTypeName(entry);
			}
		}
		if (isProgramTypeEntry(entry) && !addProgramTypeEntry(entry)) {
			handleDuplicateTypeName(entry);
		}
		addPackageNameReference(PackageNameHelper.extractPackageName(entry.getFullTypeName()));
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
		retryDuplicates();
	}

	private void retryDuplicates() {
		duplicates.removeIf(this::retryDuplicate);
	}

	private boolean retryDuplicate(final TypeEntry entry) {
		if (!exists(entry)) {
			return true;
		}
		if (entry instanceof final DataTypeEntry dtEntry) {
			if (dataTypeLib.addTypeEntry(dtEntry)) {
				deleteTypeLibraryMarkers(entry.getFile());
				return true;
			}
		} else if (addBlockTypeEntry(entry)) {
			deleteTypeLibraryMarkers(entry.getFile());
			return true;
		}
		return false;
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

	void refresh() {
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}

		buildpath = BuildpathUtil.loadBuildpath(project);
		checkDeletions();
		checkAdditions(project);
	}

	private void checkDeletions() {
		checkDeletionsForTypeGroup(adapterTypes.values());
		checkDeletionsForTypeGroup(attributeTypes.values());
		checkDeletionsForTypeGroup(deviceTypes.values());
		checkDeletionsForTypeGroup(fbTypes.values());
		checkDeletionsForTypeGroup(resourceTypes.values());
		checkDeletionsForTypeGroup(segmentTypes.values());
		checkDeletionsForTypeGroup(subAppTypes.values());
		checkDeletionsForTypeGroup(systems.values());
		checkDeletionsForTypeGroup(globalConstants.values());
		checkDeletionsForTypeGroup(dataTypeLib.getDerivedDataTypes());
		fileMap.values().removeIf(Predicate.not(this::exists));
	}

	private void checkDeletionsForTypeGroup(final Collection<? extends TypeEntry> typeEntries) {
		typeEntries.stream().filter(Predicate.not(this::exists)).forEachOrdered(this::removeTypeEntry);
	}

	private boolean exists(final TypeEntry entry) {
		return entry.getFile() != null && entry.getFile().exists()
				&& BuildpathUtil.findSourceFolder(buildpath, entry.getFile()).isPresent();
	}

	private void checkAdditions(final IContainer container) {
		try {
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
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
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
		final String fullTypeName = entry.getFullTypeName().toLowerCase();
		return switch (entry) {
		case final AdapterTypeEntry adpEntry -> adapterTypes.putIfAbsent(fullTypeName, adpEntry) == null;
		case final AttributeTypeEntry atpEntry -> attributeTypes.putIfAbsent(fullTypeName, atpEntry) == null;
		case final DeviceTypeEntry devEntry -> deviceTypes.putIfAbsent(fullTypeName, devEntry) == null;
		case final FBTypeEntry fbtEntry -> fbTypes.putIfAbsent(fullTypeName, fbtEntry) == null;
		case final ResourceTypeEntry resEntry -> resourceTypes.putIfAbsent(fullTypeName, resEntry) == null;
		case final SegmentTypeEntry segEntry -> segmentTypes.putIfAbsent(fullTypeName, segEntry) == null;
		case final SubAppTypeEntry subAppEntry -> subAppTypes.putIfAbsent(fullTypeName, subAppEntry) == null;
		case final SystemEntry sysEntry -> systems.putIfAbsent(fullTypeName, sysEntry) == null;
		case final GlobalConstantsEntry globalConstEntry ->
			globalConstants.putIfAbsent(fullTypeName, globalConstEntry) == null;
		default -> {
			FordiacLogHelper.logError("Unknown type entry to be added to library: " + entry.getClass().getName()); //$NON-NLS-1$
			yield true;
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

	private static boolean isProgramTypeEntry(final TypeEntry entry) {
		return entry instanceof FBTypeEntry || entry instanceof SubAppTypeEntry || entry instanceof DataTypeEntry;
	}

	private static void createTypeLibraryMarker(final IResource resource, final String message) {
		FordiacMarkerHelper.createMarkers(resource, List.of(
				ErrorMarkerBuilder.createErrorMarkerBuilder(message).setType(FordiacErrorMarker.TYPE_LIBRARY_MARKER)));
	}

	private static void deleteTypeLibraryMarkers(final IResource resource) {
		FordiacMarkerHelper.updateMarkers(resource, FordiacErrorMarker.TYPE_LIBRARY_MARKER, Collections.emptyList(),
				true);
	}

	void setProject(final IProject newProject) {
		project = newProject;
		reload();
	}
}
