/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH, IBH Systems,
 * 				 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Martijn Rooker, Alois Zoitl, Monika Wenger, Jens Reimann,
 *  Waldemar Eisenmenger, Gerd Kainz
 *    - initial API and implementation and/or initial documentation
 *  Martin Melik-Merkumians
 *    - adds convenience methods
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteFactory;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.dataimport.ADPImporter;
import org.eclipse.fordiac.ide.model.dataimport.DEVImporter;
import org.eclipse.fordiac.ide.model.dataimport.FBTImporter;
import org.eclipse.fordiac.ide.model.dataimport.ImportUtils;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.ui.controls.ReferenceChooserDialog;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public final class TypeLibrary implements TypeLibraryTags{
	

	/** The palette. */
	private Palette palette = PaletteFactory.eINSTANCE.createPalette();

	/** The palette group. */
	private final PaletteGroup paletteGroup = PaletteFactory.eINSTANCE
			.createPaletteGroup();
	
	/** An array of palette entry creators	 */
	private static IPaletteEntryCreator paletteCreators[] =  null;

	/** The instance. */
	private static TypeLibrary instance;

	public static String getTypeNameFromFile(IFile element) {
		return getTypeNameFromFileName(element.getName());
	}

	public static String getTypeNameFromFileName(final String fileName){
		String name = fileName;
		int index = fileName.lastIndexOf('.'); 
		if(-1 != index){
			name = fileName.substring(0, index);
		}				
		return name;
	}

	public static boolean isIEC61499TypeFile(String filename){
		return ((filename.toUpperCase().endsWith(FB_TYPE_FILE_ENDING_WITH_DOT)) || 
				(filename.toUpperCase().endsWith(TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING_WITH_DOT)) ||
				(filename.toUpperCase().endsWith(TypeLibraryTags.DEVICE_TYPE_FILE_ENDING_WITH_DOT)) ||
				(filename.toUpperCase().endsWith(TypeLibraryTags.RESOURCE_TYPE_FILE_ENDING_WITH_DOT)) ||
				(filename.toUpperCase().endsWith(TypeLibraryTags.SEGMENT_TYPE_FILE_ENDING_WITH_DOT)));
	}

	/** Find the corresponding palette group for the given IContainer
	 *
	 * If the group is not found it will not be created.
	 */
	public static PaletteGroup getPaletteGroup(Palette palette, IContainer groupDst) {		
		String[] groups = groupDst.getProjectRelativePath().toString().split("/"); //$NON-NLS-1$
		if((0 == groups.length) || ((1 == groups.length) && (0 == groups[0].length()))){
			return palette.getRootGroup();
		}		
		ArrayList<String> groupList = new ArrayList<String>();
		groupList.addAll(Arrays.asList(groups));
		if(TypeLibraryTags.TOOL_LIBRARY_PROJECT_NAME.equals(groupList.get(0))){
			groupList.remove(0);
		}
		return palette.findGroup(groupList);
	}

	/** find the corresponding palette group for the give IContainer
	 * If the group is not found it will be added 
	 * @param rootGroup
	 * @param groupDst
	 * @return
	 */
	public static PaletteGroup getPaletteGroupWithAdd(Palette palette, IContainer groupDst) {		
		String[] groups = groupDst.getProjectRelativePath().toString().split("/"); //$NON-NLS-1$
		if((0 == groups.length) || ((1 == groups.length) && (0 == groups[0].length()))){
			return palette.getRootGroup();
		}		
		ArrayList<String> groupList = new ArrayList<String>();
		groupList.addAll(Arrays.asList(groups));
		if(TypeLibraryTags.TOOL_LIBRARY_PROJECT_NAME.equals(groupList.get(0))){
			groupList.remove(0);
		}
		return palette.getGroup(groupList, false);
	}

	/**Find the corresponding palette entry for the given IFile
	 * 
	 * @param rootGroup the rooot group in which the type should be searched for (e.g., FB types group)
	 * @param entryTypeFile the IFile representing the type
	 * @return the palette entry or null
	 */
	public static PaletteEntry getPaletteEntry(final Palette palette, final IFile entryTypeFile) {
		PaletteEntry retVal = null;		
		PaletteGroup group = getPaletteGroup(palette, entryTypeFile.getParent());
		if(null != group){
			retVal = group.getEntry(getTypeNameFromFile(entryTypeFile));
		}
		return retVal;
	}


	/**
	 * Instantiates a new fB type library.
	 */
	private TypeLibrary() {
		// private constructor for singleton

	}

	/**
	 * Gets the single instance of FBTypeLibrary.
	 * 
	 * @return single instance of FBTypeLibrary
	 */
	public static TypeLibrary getInstance() {
		if (instance == null) {
			TypeLibrary newTypeLib = new TypeLibrary();

			IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace()
					.getRoot();
			IProject toolLibProject = myWorkspaceRoot.getProject(TOOL_LIBRARY_PROJECT_NAME);

			if (toolLibProject.exists()) {
				try {
					toolLibProject.delete(true, new NullProgressMonitor());
				} catch (CoreException e) {
					Activator.getDefault().logError(e.getMessage(), e);
				}
			}

			newTypeLib.loadPalette();

			instance = newTypeLib;
		}
		return instance;
	}


	/**
	 * Gets the palette.
	 * 
	 * @return the palette
	 */
	public Palette getPalette() {
		if (palette.getRootGroup() == null) {
			palette.setRootGroup(paletteGroup); // if something went
			// wrong on loading
		}
		if (palette.getRootGroup().getLabel() == null) {
			palette.getRootGroup().setLabel(TYPE_LIBRARY);
		}
		return palette;
	}


	/** The pack. */
	@SuppressWarnings("unused")
	private static PalettePackage pack = PalettePackage.eINSTANCE;

	/**
	 * Load palette.
	 * 
	 * @return the palette
	 */
	public Palette loadPalette() {

		IFolder toolLibFolder = getToolLibFolder();

		palette = loadPalette(toolLibFolder);
		return palette;
	}

	public static Palette loadPalette(IContainer container) {
		Palette newPalette = PaletteFactory.eINSTANCE.createPalette();
		loadPaletteGroupMembers(newPalette, container, newPalette.getRootGroup());

		return newPalette;
	}


	private static void loadPaletteGroupMembers(Palette palette,
			IContainer container, PaletteGroup group) {
		IResource[] members;
		try {
			if(!ResourcesPlugin.getWorkspace().isTreeLocked()){
				container.refreshLocal(IResource.DEPTH_ONE, null);
			}
			members = container.members();
			
			for (IResource iResource : members) {
				if (iResource instanceof IFolder) {
					createPaletteGroup(palette, group, (IFolder) iResource);
				}
				if (iResource instanceof IFile) {
					createPaleteEntry(palette, group, (IFile) iResource);
				}			
			}
		} catch (CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param palette
	 * @param group
	 * @param file
	 * @return
	 */
	public static PaletteEntry createPaleteEntry(Palette palette, PaletteGroup group,
			IFile file) {
		PaletteEntry entry = null ;
		IPaletteEntryCreator creators[] = getPaletteCreators();

		for(IPaletteEntryCreator in : creators){
			if(in.canHandle(file)){
				entry = in.createPaletteEntry();
				configurePaletteEntry(entry, file, group);
			}
		}
		return entry;
	}

	/**
	 * 
	 */
	private static void setPaletteCreators(){
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elems = registry.getConfigurationElementsFor(org.eclipse.fordiac.ide.model.Activator.PLUGIN_ID, "PaletteEntryCreator"); //$NON-NLS-1$
		int countPaletteCreater = 0;
		paletteCreators = new IPaletteEntryCreator[elems.length];
		
		for(int i = 0; i < elems.length; i++){
			IConfigurationElement elem = elems[i];
			try{
				Object object = elem.createExecutableExtension("class"); //$NON-NLS-1$
				if(object instanceof IPaletteEntryCreator){	
					paletteCreators[countPaletteCreater] = (IPaletteEntryCreator) object;
					countPaletteCreater++;
				}
			}catch(CoreException e){
				Activator.getDefault().logError(e.getMessage(), e);
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	private static IPaletteEntryCreator[] getPaletteCreators(){
		if(null == paletteCreators){
			setPaletteCreators();
		}
		return paletteCreators;
	}

	/**
	 * 
	 * @param entry
	 * @param file
	 * @param parent
	 */
	private static void configurePaletteEntry(PaletteEntry entry, IFile file,
			PaletteGroup parent) {
		entry.setType(null);
		entry.setLabel(TypeLibrary.getTypeNameFromFile(file));
		entry.setFile(file);
		parent.addEntry(entry);
	}


	private static void createPaletteGroup(Palette palette,
			PaletteGroup parent, IFolder groupFolder) {
		PaletteGroup newGroup = palette.createGroup(parent,
				groupFolder.getName());
		loadPaletteGroupMembers(palette, groupFolder, newGroup);
		if (newGroup.isEmpty()) {
			// remove group if they don't have an entry
			parent.getSubGroups().remove(newGroup);
		}
	}

	public static void refreshPalette(Palette palette){
		IContainer container = TypeLibrary.getLibPath(palette);

		try {
			container.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}


		checkDeletionsForGroup(palette, container, palette.getRootGroup());
		checkAdditions(palette, container, palette.getRootGroup());
	}

	private static void checkDeletionsForGroup(Palette palette, IContainer container, PaletteGroup group){

		for(int i = 0; i < group.getEntries().size(); ){
			PaletteEntry paletteEntry = group.getEntries().get(i);					
			if(!paletteEntry.getFile().exists()){
				group.getEntries().remove(i);
			}
			else{
				i++;
			}
		}

		for(int i = 0; i < group.getSubGroups().size();) {
			PaletteGroup subGroup = group.getSubGroups().get(i);
			IFolder  folder = container.getFolder(new Path(subGroup.getLabel()));
			if(!folder.exists()){
				group.getSubGroups().remove(i);
			}
			else{
				checkDeletionsForGroup(palette, folder, subGroup);
				i++;
			}
		}
	}


	private static void checkAdditions(Palette palette, IContainer container, PaletteGroup group) {
		try {
			IResource[] members = container.members();

			for (IResource iResource : members) {
				if (iResource instanceof IFolder) {
					PaletteGroup subgroup = group.getGroup(((IFolder)iResource).getName()); 
					if( null == subgroup){
						createPaletteGroup(palette, group, (IFolder) iResource);
					}
					else{
						checkAdditions(palette, (IFolder)iResource, subgroup);
					}
				}
				if (iResource instanceof IFile) {
					if(null == group.getEntry(TypeLibrary.getTypeNameFromFile((IFile) iResource))){
						//only add new entry if it does not exist
						createPaleteEntry(palette, group, (IFile)iResource);
					}
				}
			}
		} catch (CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}

	}	

	public static IContainer getLibPath(Palette palette) {
		IContainer libPath = null;
		
		AutomationSystem system = palette.getAutomationSystem();

		if (system == null) {
			libPath = TypeLibrary.getToolLibFolder();
		}else {
			libPath = system.getProject();
		}
		return libPath;
	}
	
	/**
	 * Returns the tool library project.
	 * @return the tool library project of the 4diac-ide instance
	 */
	public static IProject getToolLibProject() {
		IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		
		return myWorkspaceRoot.getProject(TOOL_LIBRARY_PROJECT_NAME);
	}

	public static IFolder getToolLibFolder() {

		IProject toolLibProject = getToolLibProject();

		if (!toolLibProject.exists()) {
			createToolLibProject(toolLibProject);
		}

		IFolder toolLibFolder = toolLibProject.getFolder(TOOL_LIBRARY_PROJECT_NAME);
		if (!toolLibFolder.exists()) {
			createToolLibLink(toolLibProject);
			toolLibFolder = toolLibProject.getFolder(TOOL_LIBRARY_PROJECT_NAME);
		}

		return toolLibFolder;
	}

	private static void createToolLibProject(IProject toolLibProject) {
		IProgressMonitor progressMonitor = new NullProgressMonitor();

		try {
			toolLibProject.create(progressMonitor);
			toolLibProject.open(progressMonitor);
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}

		createToolLibLink(toolLibProject);
	}

	private static void createToolLibLink(IProject toolLibProject) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		IFolder link = toolLibProject.getFolder(TOOL_LIBRARY_PROJECT_NAME);

		final String typeLibPath = System.getProperty("4diac.typelib.path"); //$NON-NLS-1$

		final IPath location;

		if ( typeLibPath != null && !typeLibPath.isEmpty() ) {
			location = new Path(typeLibPath);
		}
		else {
			location = new Path(Platform.getInstallLocation().getURL().getFile() + TypeLibraryTags.TYPE_LIBRARY);
		}
		if (workspace.validateLinkLocation(link, location).isOK()) {
			try {
				link.createLink(location, IResource.NONE, null);
			} catch (Exception e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
		} else {
			// invalid location, throw an exception or warn user
		}
	}

	/**
	 * Load type.
	 * 
	 * @param entry
	 *            the entry
	 */
	public static void loadAdapterType(final PaletteEntry entry,
			final Palette palette) {
		if (((AdapterTypePaletteEntry) entry).getType() == null) {
			AdapterType type = null;

			if (ADAPTER_TYPE_FILE_ENDING.equalsIgnoreCase(entry.getFile().getFileExtension())) {
				type = ADPImporter.importAdapterType(entry.getFile());
				((AdapterTypePaletteEntry) entry).setType(type);
			}
		}
	}

	public static boolean importTypeFile(File srcFile, Palette palette, String relativeDestionationPath, boolean overWrite, Set<String> selectedFiles, Shell shell){
		boolean retval = false;
		if (!srcFile.isDirectory()) {
			try {
				PaletteGroup group = performTypeSpecificImportPreparation(srcFile, palette, relativeDestionationPath, selectedFiles, shell);

				IFile dstFile = TypeLibrary.getLibPath(palette).getFile(new Path(relativeDestionationPath));

				int retVal = SWT.YES; // set retval to SWT.YES -> block is
				// overwritten, if the messagebox is
				// not shown
				if (dstFile.exists() && !overWrite) {
					MessageBox box = new MessageBox(Display.getDefault().getActiveShell(), SWT.YES
							| SWT.NO);
					String msg = MessageFormat.format(
							Messages.TypeLibrary_OverwriteMessage,
							new Object[] { dstFile.getFullPath().toOSString() });
					box.setMessage(msg);
					retVal = box.open();
					if(retVal == SWT.YES){
						if(null != group){
							group.removeEntryNamed(TypeLibrary.getTypeNameFromFile(dstFile));
						}
					}
				}
				if (retVal == SWT.YES) {
					ImportUtils.copyFile(srcFile, dstFile);		
					retval = true;
				} else {
					String msg = MessageFormat.format(
							Messages.TypeLibrary_ImportAbortByUser,
							new Object[] { srcFile.getName() });
					org.eclipse.fordiac.ide.model.Activator.getDefault().logError(
							msg,
							new TypeImportException(
									Messages.TypeLibrary_FBTImportException));
				}		
			}catch (Exception e) {
				org.eclipse.fordiac.ide.model.Activator.getDefault().logError("Exception occured during type import", e);
			}
		}
		return retval;
	}

	private static PaletteGroup performTypeSpecificImportPreparation(
			File srcFile, Palette palette, String relativeDestionationPath, Set<String> selectedFiles, Shell shell) throws TypeImportException, CoreException  {
		PaletteGroup group = null;


		if (srcFile.getName().toUpperCase().endsWith(TypeLibraryTags.FB_TYPE_FILE_ENDING)){ 
			DataTypeLibrary.loadReferencedDataTypes(srcFile, shell);				
			group = getTargetGroup(relativeDestionationPath, palette, FB_TYPE_FILE_ENDING_WITH_DOT);
			loadReferencedFBTypes(srcFile, palette, group, relativeDestionationPath, selectedFiles, shell);

		} else if (srcFile.getName().toUpperCase().endsWith(TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING)) {
			DataTypeLibrary.loadReferencedDataTypes(srcFile, shell);				

			group = getTargetGroup(relativeDestionationPath, palette, ADAPTER_TYPE_FILE_ENDING_WITH_DOT);

		} else if (srcFile.getName().toUpperCase().endsWith(TypeLibraryTags.DEVICE_TYPE_FILE_ENDING)) {
			DataTypeLibrary.loadReferencedDataTypes(srcFile, shell);	

			group = getTargetGroup(relativeDestionationPath, palette, DEVICE_TYPE_FILE_ENDING_WITH_DOT);
			loadReferencedRESTypes(srcFile, palette, group,  relativeDestionationPath, selectedFiles, shell);

		} else if (srcFile.getName().toUpperCase().endsWith(TypeLibraryTags.RESOURCE_TYPE_FILE_ENDING)) {
			DataTypeLibrary.loadReferencedDataTypes(srcFile, shell);				

			group = getTargetGroup(relativeDestionationPath, palette, RESOURCE_TYPE_FILE_ENDING_WITH_DOT);

			loadReferencedFBTypes(srcFile, palette, group, relativeDestionationPath, selectedFiles, shell);

		} else if (srcFile.getName().toUpperCase().endsWith(TypeLibraryTags.SEGMENT_TYPE_FILE_ENDING)) {
			group = getTargetGroup(relativeDestionationPath, palette, SEGMENT_TYPE_FILE_ENDING_WITH_DOT);
		} else if (srcFile.getName().toUpperCase().endsWith(TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING)) {
			DataTypeLibrary.loadReferencedDataTypes(srcFile, shell);				

			group = getTargetGroup(relativeDestionationPath, palette, SUBAPP_TYPE_FILE_ENDING_WITH_DOT);

			loadReferencedFBTypes(srcFile, palette, group, relativeDestionationPath, selectedFiles, shell);

		}


		return group;
	}


	public static void loadReferencedFBTypes(File srcFile, Palette palette,
			PaletteGroup group, String relativeDestionationPath, Set<String> selectedFiles, Shell shell) throws TypeImportException {
		List<String> referencedFBTypes = FBTImporter.getReferencedFBTypes(srcFile);

		for (String typeName : referencedFBTypes) {
			if(!checkForReferencedTypeInPalette(palette, typeName, shell)){
				if(!checkForReferencedTypeInImportList(typeName, selectedFiles)){				
					// if none found -> ask user to select the required
					// ones
					// show dialog where the user can select the fbt
					// file in a filebrowser
					if (shell == null) {
						shell = Display.getCurrent().getActiveShell();
					}
					FileDialog fd = new FileDialog(shell);
					String msg = MessageFormat.format(
							Messages.TypeLibrary_LoadReferencedFile_DialogTitle,
							new Object[] { typeName });
					fd.setText(msg);
					fd.setFilterExtensions(new String[] { "*.fbt" }); //$NON-NLS-1$
					String typeFile = fd.open();
					if (typeFile != null) {
						importTypeFile(new File(typeFile), palette, relativeDestionationPath, false, selectedFiles, shell);
					} else {
						throw new TypeImportException(
								Messages.TypeLibrary_ERROR_ReferencedTypeNotFound);
					}

					List<PaletteEntry> currentlyImported =  palette.getTypeEntries(typeName);
					if (currentlyImported.size() != 1) {
						// exception ! as the type was imported
						// currently with the
						// file chooser dialog
						throw new TypeImportException(
								Messages.TypeLibrary_ERROR_ReferencedTypeNotFound);
					}
				}
			}
		}

	}

	private static boolean checkForReferencedTypeInImportList(
			String typeName, Set<String> selectedFiles) {
		for (Iterator<String> iterator = selectedFiles.iterator(); iterator
				.hasNext();) {
			String name = iterator.next();

			String path[] = name.split(ImportUtils.getSeperatorRegex());

			String fbName = path[path.length - 1];
			String[] splitFBName = fbName.split("\\."); //$NON-NLS-1$

			if (splitFBName.length == 2) {

				// add to list only if name contains typename and it is not
				// already
				// imported!
				if (splitFBName[0].equals(typeName)){
					return true;
				}
			}
		}
		return false;
	}

	private static boolean checkForReferencedTypeInPalette(
			Palette palette, String typeName, Shell shell) {

		final List<PaletteEntry> fbTypes = palette.getTypeEntries(typeName);


		if (fbTypes.size() != 1) {
			// if more than one type exist -> ask user which one
			// is the correct!
			if (fbTypes.size() > 1) {

				if (shell == null) {
					shell = Display.getCurrent().getActiveShell();
				}

				ReferenceChooserDialog referenceChooser = new ReferenceChooserDialog(
						new IStructuredContentProvider() {

							@Override
							public Object[] getElements(Object inputElement) {
								return fbTypes.toArray();
							}

							@Override
							public void dispose() {
								// nothing to do
							}

							@Override
							public void inputChanged(Viewer viewer,
									Object oldInput, Object newInput) {
								// nothing to do

							}

						}, new ITableLabelProvider() {

							@Override
							public String getColumnText(Object element,
									int columnIndex) {
								return element.toString();
							}

							@Override
							public Image getColumnImage(Object element,
									int columnIndex) {
								return null;
							}

							@Override
							public void addListener(
									ILabelProviderListener listener) {
								// nothing to do
							}

							@Override
							public void dispose() {
								// nothing to do
							}

							@Override
							public boolean isLabelProperty(Object element,
									String property) {
								return false;
							}

							@Override
							public void removeListener(
									ILabelProviderListener listener) {
								// nothing to do
							}

						}, shell);

				int retVal = referenceChooser.open();
				if (retVal != Window.OK) {
					// the user could not decide on the type
					return false;
				}
			}
			else{
				return false;
			}
		}
		return true;
	}

	public static PaletteGroup getTargetGroup(String groupPath, Palette palette, String typeFileEnding) throws CoreException {
		String[] path = groupPath.split(ImportUtils.getSeperatorRegex());

		ArrayList<String> buf = new ArrayList<String>(Arrays.asList(path));
		if(path[0].length() == 0){
			buf.remove(0);
		}

		if(buf.get(buf.size() - 1).toUpperCase().endsWith(typeFileEnding.toUpperCase())){
			buf.remove(buf.size() - 1);
			buf.size();
		}
		return palette.getGroup(buf, true);
	}

	public static void loadReferencedRESTypes(File srcFile, Palette palette,
			PaletteGroup group, String relativeDestPath, Set<String> selectedFiles,
			Shell shell) throws TypeImportException {
		List<String> referencedTypes = DEVImporter.getReferencedTypes(srcFile);

		for (String typeName : referencedTypes) {
			if(!checkForReferencedTypeInPalette(palette, typeName, shell)){
				if(!checkForReferencedTypeInImportList(typeName, selectedFiles)){				
					// if none found -> ask user to select the required
					// ones
					// show dialog where the user can select the fbt
					// file in a filebrowser
					if (shell == null) {
						shell = Display.getCurrent().getActiveShell();
					}
					FileDialog fd = new FileDialog(shell);
					String msg = MessageFormat.format(
							Messages.TypeLibrary_LoadReferencedFile_DialogTitle,
							new Object[] { typeName });
					fd.setText(msg);
					fd.setFilterExtensions(new String[] { "*.fbt" }); //$NON-NLS-1$
					String typeFile = fd.open();
					if (typeFile != null) {
						importTypeFile(new File(typeFile), palette, relativeDestPath, false, selectedFiles, shell);
					} else {
						throw new TypeImportException(
								Messages.TypeLibrary_ERROR_ReferencedTypeNotFound);
					}

					List<PaletteEntry> currentlyImported =  palette.getTypeEntries(typeName);
					if (currentlyImported.size() != 1) {
						// exception ! as the type was imported
						// currently with the
						// file chooser dialog
						throw new TypeImportException(
								Messages.TypeLibrary_ERROR_ReferencedTypeNotFound);
					}
				}
			}
		}
	}
}
