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
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.buildpath.Buildpath;
import org.eclipse.fordiac.ide.model.buildpath.util.BuildpathUtil;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.impl.ErrorAdapterTypeEntryImpl;
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

	public Map<String, AdapterTypeEntry> getAdapterTypes() {
		return adapterTypes;
	}

	public List<AdapterTypeEntry> getAdapterTypesSorted() {
		return getAdapterTypes().values().stream()
				.sorted((o1, o2) -> Collator.getInstance().compare(o1.getFullTypeName(), o2.getFullTypeName()))
				.toList();
	}

	public Map<String, AttributeTypeEntry> getAttributeTypes() {
		return attributeTypes;
	}

	public Map<String, DeviceTypeEntry> getDeviceTypes() {
		return deviceTypes;
	}

	public Map<String, FBTypeEntry> getFbTypes() {
		return fbTypes;
	}

	public Map<String, ResourceTypeEntry> getResourceTypes() {
		return resourceTypes;
	}

	public Map<String, SegmentTypeEntry> getSegmentTypes() {
		return segmentTypes;
	}

	public Map<String, SubAppTypeEntry> getSubAppTypes() {
		return subAppTypes;
	}

	public Map<String, SystemEntry> getSystems() {
		return systems;
	}

	public Map<String, GlobalConstantsEntry> getGlobalConstants() {
		return globalConstants;
	}

	public Map<String, TypeEntry> getProgramTypes() {
		return Collections.unmodifiableMap(programTypes);
	}

	public Set<String> getPackages() {
		return Collections.unmodifiableSet(packages.keySet());
	}

	public Collection<TypeEntry> getAllTypes() {
		return Collections.unmodifiableCollection(fileMap.values());
	}

	public List<CompositeFBType> getCompositeFBTypes() {
		return getFbTypes().values().stream().filter(e -> e.getTypeEditable() instanceof CompositeFBType)
				.map(e -> (CompositeFBType) e.getTypeEditable()).toList();
	}

	public AdapterTypeEntry getAdapterTypeEntry(final String typeName) {
		return getAdapterTypes().get(typeName);
	}

	public AttributeTypeEntry getAttributeTypeEntry(final String typeName) {
		return getAttributeTypes().get(typeName);
	}

	public DeviceTypeEntry getDeviceTypeEntry(final String typeName) {
		return getDeviceTypes().get(typeName);
	}

	public FBTypeEntry getFBTypeEntry(final String typeName) {
		return getFbTypes().get(typeName);
	}

	public ResourceTypeEntry getResourceTypeEntry(final String typeName) {
		return getResourceTypes().get(typeName);
	}

	public SegmentTypeEntry getSegmentTypeEntry(final String typeName) {
		return getSegmentTypes().get(typeName);
	}

	public SubAppTypeEntry getSubAppTypeEntry(final String typeName) {
		return getSubAppTypes().get(typeName);
	}

	public SystemEntry getSystemEntry(final String name) {
		return getSystems().get(name);
	}

	public GlobalConstantsEntry getGlobalConstantsEntry(final String name) {
		return getGlobalConstants().get(name);
	}

	public TypeEntry getFBOrSubAppType(final String typeName) {
		final TypeEntry fb = getFBTypeEntry(typeName);
		return fb != null ? fb : getSubAppTypeEntry(typeName);
	}

	public TypeEntry getTypeEntry(final IFile typeFile) {
		return fileMap.get(typeFile);
	}

	public void reload() {
		getAdapterTypes().clear();
		getAttributeTypes().clear();
		getDeviceTypes().clear();
		getFbTypes().clear();
		getResourceTypes().clear();
		getSegmentTypes().clear();
		getSubAppTypes().clear();
		getSystems().clear();
		getGlobalConstants().clear();
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
		if (null != entry) {
			if (!FordiacKeywords.isReservedKeyword(entry.getTypeName())) {
				addTypeEntry(entry);
			} else {
				createTypeLibraryMarker(file,
						MessageFormat.format(Messages.NameRepository_NameReservedKeyWord, entry.getTypeName()));
			}
		}
		return entry;
	}

	public TypeEntry createErrorTypeEntry(final String typeName, final EClass typeClass) {
		return errorTypes.computeIfAbsent(typeName, name -> {
			final FBType fbType = (FBType) LibraryElementFactory.eINSTANCE.create(typeClass);
			PackageNameHelper.setFullTypeName(fbType, name);
			fbType.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
			final TypeEntry entry = createErrorTypeEntry(fbType);
			entry.setType(fbType);
			entry.setTypeLibrary(this);
			return entry;
		});
	}

	private static TypeEntry createErrorTypeEntry(final FBType fbType) {
		if (fbType instanceof AdapterType) {
			return new ErrorAdapterTypeEntryImpl();
		}
		if (fbType instanceof SubAppType) {
			return new ErrorSubAppTypeEntryImpl();
		}
		return new ErrorFBTypeEntryImpl();
	}

	private void removeErrorTypeEntry(final String typeName) {
		final TypeEntry entry = errorTypes.remove(typeName);
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
		if (isProgramTypeEntry(entry) && programTypes.putIfAbsent(entry.getFullTypeName(), entry) != null) {
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
			programTypes.remove(entry.getFullTypeName(), entry);
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
			packages.computeIfAbsent(packageName, key -> new AtomicInteger()).incrementAndGet();
		}
	}

	protected void removePackageNameReference(final String packageName) {
		if (packageName != null && !packageName.isEmpty()) {
			packages.computeIfPresent(packageName, (key, value) -> value.decrementAndGet() > 0 ? value : null);
		}
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
		checkDeletionsForTypeGroup(getAdapterTypes().values());
		checkDeletionsForTypeGroup(getAttributeTypes().values());
		checkDeletionsForTypeGroup(getDeviceTypes().values());
		checkDeletionsForTypeGroup(getFbTypes().values());
		checkDeletionsForTypeGroup(getResourceTypes().values());
		checkDeletionsForTypeGroup(getSegmentTypes().values());
		checkDeletionsForTypeGroup(getSubAppTypes().values());
		checkDeletionsForTypeGroup(getSystems().values());
		checkDeletionsForTypeGroup(getGlobalConstants().values());
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
		return programTypes.get(name);
	}

	public List<TypeEntry> findUnqualified(final String name) {
		final String unqualifiedName = PackageNameHelper.extractPlainTypeName(name);
		return programTypes.values().stream().filter(entry -> unqualifiedName.equalsIgnoreCase(entry.getTypeName()))
				.toList();
	}

	private boolean addBlockTypeEntry(final TypeEntry entry) {
		if (entry instanceof final AdapterTypeEntry adpEntry) {
			return getAdapterTypes().putIfAbsent(entry.getFullTypeName(), adpEntry) == null;
		}
		if (entry instanceof final AttributeTypeEntry atpEntry) {
			return getAttributeTypes().putIfAbsent(entry.getFullTypeName(), atpEntry) == null;
		}
		if (entry instanceof final DeviceTypeEntry devEntry) {
			return getDeviceTypes().putIfAbsent(entry.getFullTypeName(), devEntry) == null;
		}
		if (entry instanceof final FBTypeEntry fbtEntry) {
			return getFbTypes().putIfAbsent(entry.getFullTypeName(), fbtEntry) == null;
		}
		if (entry instanceof final ResourceTypeEntry resEntry) {
			return getResourceTypes().putIfAbsent(entry.getFullTypeName(), resEntry) == null;
		}
		if (entry instanceof final SegmentTypeEntry segEntry) {
			return getSegmentTypes().putIfAbsent(entry.getFullTypeName(), segEntry) == null;
		}
		if (entry instanceof final SubAppTypeEntry subAppEntry) {
			return getSubAppTypes().putIfAbsent(entry.getFullTypeName(), subAppEntry) == null;
		}
		if (entry instanceof final SystemEntry sysEntry) {
			return getSystems().putIfAbsent(entry.getFullTypeName(), sysEntry) == null;
		}
		if (entry instanceof final GlobalConstantsEntry globalConstEntry) {
			return getGlobalConstants().putIfAbsent(entry.getFullTypeName(), globalConstEntry) == null;
		}
		FordiacLogHelper.logError("Unknown type entry to be added to library: " + entry.getClass().getName()); //$NON-NLS-1$
		return true;
	}

	private void removeBlockTypeEntry(final TypeEntry entry) {
		if (entry instanceof AdapterTypeEntry) {
			getAdapterTypes().remove(entry.getFullTypeName(), entry);
		} else if (entry instanceof AttributeTypeEntry) {
			getAttributeTypes().remove(entry.getFullTypeName(), entry);
		} else if (entry instanceof DeviceTypeEntry) {
			getDeviceTypes().remove(entry.getFullTypeName(), entry);
		} else if (entry instanceof FBTypeEntry) {
			getFbTypes().remove(entry.getFullTypeName(), entry);
		} else if (entry instanceof ResourceTypeEntry) {
			getResourceTypes().remove(entry.getFullTypeName(), entry);
		} else if (entry instanceof SegmentTypeEntry) {
			getSegmentTypes().remove(entry.getFullTypeName(), entry);
		} else if (entry instanceof SubAppTypeEntry) {
			getSubAppTypes().remove(entry.getFullTypeName(), entry);
		} else if (entry instanceof SystemEntry) {
			getSystems().remove(entry.getFullTypeName(), entry);
		} else if (entry instanceof GlobalConstantsEntry) {
			getGlobalConstants().remove(entry.getFullTypeName(), entry);
		} else {
			FordiacLogHelper.logError("Unknown type entry to be removed from library: " + entry.getClass().getName()); //$NON-NLS-1$
		}
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
