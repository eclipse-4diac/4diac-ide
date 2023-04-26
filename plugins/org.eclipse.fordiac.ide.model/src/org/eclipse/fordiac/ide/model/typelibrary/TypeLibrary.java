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
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import java.text.Collator;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.impl.ErrorFBTypeEntryImpl;
import org.eclipse.fordiac.ide.model.typelibrary.impl.ErrorSubAppTypeEntryImpl;
import org.eclipse.fordiac.ide.model.typelibrary.impl.TypeEntryFactory;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class TypeLibrary {

	private IProject project;
	private final DataTypeLibrary dataTypeLib = new DataTypeLibrary();
	private final Map<String, AdapterTypeEntry> adapterTypes = new HashMap<>();
	private final Map<String, DeviceTypeEntry> deviceTypes = new HashMap<>();
	private final Map<String, FBTypeEntry> fbTypes = new HashMap<>();
	private final Map<String, ResourceTypeEntry> resourceTypes = new HashMap<>();
	private final Map<String, SegmentTypeEntry> segmentTypes = new HashMap<>();
	private final Map<String, SubAppTypeEntry> subAppTypes = new HashMap<>();
	private final Map<String, SystemEntry> systems = new HashMap<>();
	private final Map<String, TypeEntry> errorTypes = new HashMap<>();

	public Map<String, AdapterTypeEntry> getAdapterTypes() {
		return adapterTypes;
	}

	public List<AdapterTypeEntry> getAdapterTypesSorted() {
		return getAdapterTypes().values().stream()
				.sorted((o1, o2) -> Collator.getInstance().compare(o1.getTypeName(), o2.getTypeName())).toList();
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

	public List<CompositeFBType> getCompositeFBTypes() {
		return getFbTypes().values().stream().filter(e -> e.getTypeEditable() instanceof CompositeFBType)
				.map(e -> (CompositeFBType) e.getTypeEditable()).toList();
	}

	public AdapterTypeEntry getAdapterTypeEntry(final String typeName) {
		return getAdapterTypes().get(typeName);
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

	public TypeEntry getTypeEntry(final IFile typeFile) {
		if (isDataTypeFile(typeFile)) {
			return dataTypeLib.getDerivedDataTypes().get(TypeEntry.getTypeNameFromFile(typeFile));
		}
		final Map<String, ? extends TypeEntry> typeEntryList = getTypeList(typeFile);
		if (null != typeEntryList) {
			return typeEntryList.get(TypeEntry.getTypeNameFromFile(typeFile));
		}
		return null;
	}

	public void reload() {
		getAdapterTypes().clear();
		getDeviceTypes().clear();
		getFbTypes().clear();
		getResourceTypes().clear();
		getSegmentTypes().clear();
		getSubAppTypes().clear();
		getSystems().clear();
		dataTypeLib.getDerivedDataTypes().clear();
		checkAdditions(project);
	}

	private static boolean isDataTypeFile(final IFile typeFile) {
		return TypeLibraryTags.DATA_TYPE_FILE_ENDING.equalsIgnoreCase(typeFile.getFileExtension());
	}

	public DataTypeLibrary getDataTypeLibrary() {
		return dataTypeLib;
	}

	public IProject getProject() {
		return project;
	}

	private Map<String, ? extends TypeEntry> getTypeList(final IFile typeFile) {
		final String extension = typeFile.getFileExtension();
		if (null != extension) {
			switch (extension.toUpperCase()) {
			case TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING:
				return getAdapterTypes();
			case TypeLibraryTags.DEVICE_TYPE_FILE_ENDING:
				return getDeviceTypes();
			case TypeLibraryTags.FB_TYPE_FILE_ENDING:
				return getFbTypes();
			case TypeLibraryTags.RESOURCE_TYPE_FILE_ENDING:
				return getResourceTypes();
			case TypeLibraryTags.SEGMENT_TYPE_FILE_ENDING:
				return getSegmentTypes();
			case TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING:
				return getSubAppTypes();
			case TypeLibraryTags.SYSTEM_TYPE_FILE_ENDING:
				return getSystems();
			default:
				break;
			}
		}
		return Collections.emptyMap();
	}

	/**
	 * Instantiates a new fB type library.
	 */
	TypeLibrary(final IProject project) {
		this.project = project;
		if (project != null && project.exists()) {
			checkAdditions(project);
		}
	}

	public TypeEntry createTypeEntry(final IFile file) {
		final TypeEntry entry = TypeEntryFactory.INSTANCE.createTypeEntry(file);
		if (null != entry) {
			if (!FordiacKeywords.isReservedKeyword(entry.getTypeName())) {
				addTypeEntry(entry);
			} else {
				FordiacMarkerHelper.createMarkers(file, List.of(ErrorMarkerBuilder.createErrorMarkerBuilder(
						MessageFormat.format(Messages.NameRepository_NameReservedKeyWord, entry.getTypeName()))));
			}
		}
		return entry;
	}

	public TypeEntry createErrorTypeEntry(final String typeFbElement, final FBType fbType) {
		final TypeEntry entry = createErrorTypeEntry(fbType);
		entry.setType(fbType);
		fbType.setName(typeFbElement);
		fbType.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		addErrorTypeEntry(entry);
		return entry;
	}

	public void addErrorTypeEntry(final TypeEntry entry) {
		errorTypes.put(entry.getTypeName(), entry);
	}

	public void removeErrorTypeEntry(final TypeEntry entry) {
		errorTypes.remove(entry.getTypeName());
	}

	private static TypeEntry createErrorTypeEntry(final FBType fbType) {
		if(fbType instanceof SubAppType) {
			return new ErrorSubAppTypeEntryImpl();
		}
		return new ErrorFBTypeEntryImpl();
	}

	public void addTypeEntry(final TypeEntry entry) {
		final TypeEntry errorEntry = errorTypes.get(entry.getTypeName());
		if (errorEntry != null) {
			removeErrorTypeEntry(errorEntry);
		}
		entry.setTypeLibrary(this);
		if (entry instanceof final DataTypeEntry dtEntry) {
			dataTypeLib.addTypeEntry(dtEntry);
		} else {
			addBlockTypeEntry(entry);
		}
	}

	public void removeTypeEntry(final TypeEntry entry) {
		if (entry instanceof final DataTypeEntry dtEntry) {
			dataTypeLib.removeTypeEntry(dtEntry);
		} else {
			removeBlockTypeEntry(entry);
		}
	}

	void refresh() {
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}

		checkDeletions();
		checkAdditions(project);
	}

	private void checkDeletions() {
		checkDeletionsForTypeGroup(getAdapterTypes().values());
		checkDeletionsForTypeGroup(getDeviceTypes().values());
		checkDeletionsForTypeGroup(getFbTypes().values());
		checkDeletionsForTypeGroup(getResourceTypes().values());
		checkDeletionsForTypeGroup(getSegmentTypes().values());
		checkDeletionsForTypeGroup(getSubAppTypes().values());
		checkDeletionsForTypeGroup(getSystems().values());
		checkDeletionsForTypeGroup(dataTypeLib.getDerivedDataTypes().values());
	}

	private static void checkDeletionsForTypeGroup(final Collection<? extends TypeEntry> typeEntries) {
		typeEntries.removeIf(e -> (!e.getFile().exists()));
	}

	private void checkAdditions(final IContainer container) {
		try {
			final IResource[] members = container.members();

			for (final IResource resource : members) {
				if (resource instanceof final IFolder folder) {
					checkAdditions(folder);
				}
				if ((resource instanceof final IFile file) && (!containsType(file))) {
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
		TypeEntry entry = getSubAppTypeEntry(name);
		if (entry != null) {
			return entry;
		}

		entry = getFBTypeEntry(name);
		if (entry != null) {
			return entry;
		}

		entry = dataTypeLib.getDerivedDataTypes().get(name);
		if (entry != null) {
			return entry;
		}

		return getAdapterTypeEntry(name);
	}

	private void addBlockTypeEntry(final TypeEntry entry) {
		if (entry instanceof final AdapterTypeEntry adpEntry) {
			getAdapterTypes().put(entry.getTypeName(), adpEntry);
		} else if (entry instanceof final DeviceTypeEntry devEntry) {
			getDeviceTypes().put(entry.getTypeName(), devEntry);
		} else if (entry instanceof final FBTypeEntry fbtEntry) {
			getFbTypes().put(entry.getTypeName(), fbtEntry);
		} else if (entry instanceof final ResourceTypeEntry resEntry) {
			getResourceTypes().put(entry.getTypeName(), resEntry);
		} else if (entry instanceof final SegmentTypeEntry segEntry) {
			getSegmentTypes().put(entry.getTypeName(), segEntry);
		} else if (entry instanceof final SubAppTypeEntry subAppEntry) {
			getSubAppTypes().put(entry.getTypeName(), subAppEntry);
		} else if (entry instanceof final SystemEntry sysEntry) {
			getSystems().put(entry.getTypeName(), sysEntry);
		} else {
			FordiacLogHelper.logError("Unknown type entry to be added to library: " + entry.getClass().getName()); //$NON-NLS-1$
		}
	}

	private void removeBlockTypeEntry(final TypeEntry entry) {
		if (entry instanceof AdapterTypeEntry) {
			getAdapterTypes().remove(entry.getTypeName());
		} else if (entry instanceof DeviceTypeEntry) {
			getDeviceTypes().remove(entry.getTypeName());
		} else if (entry instanceof FBTypeEntry) {
			getFbTypes().remove(entry.getTypeName());
		} else if (entry instanceof ResourceTypeEntry) {
			getResourceTypes().remove(entry.getTypeName());
		} else if (entry instanceof SegmentTypeEntry) {
			getSegmentTypes().remove(entry.getTypeName());
		} else if (entry instanceof SubAppTypeEntry) {
			getSubAppTypes().remove(entry.getTypeName());
		} else if (entry instanceof SystemEntry) {
			getSystems().remove(entry.getTypeName());
		} else {
			FordiacLogHelper.logError("Unknown type entry to be removed from library: " + entry.getClass().getName()); //$NON-NLS-1$
		}
	}

	void setProject(final IProject newProject) {
		project = newProject;
	}

}
