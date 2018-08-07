/*******************************************************************************
 * Copyright (c) 2011 - 2017 TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.navigator;

import java.util.Scanner;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.Activator;
import org.eclipse.fordiac.ide.typemanagement.util.FBTypeUtils;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.navigator.IDescriptionProvider;

public class FBTypeLabelProvider extends AdapterFactoryLabelProvider implements IDescriptionProvider {

	public FBTypeLabelProvider() {
		super(FBTypeComposedAdapterFactory.getAdapterFactory());
	}

	
	@Override
	public Image getImage(Object element) {
		if(element instanceof IFile){
			return getImageForFBFile((IFile)element);
		}
		return super.getImage(element);
	}

	private static Image getImageForFBFile(IFile element) {
		Image image = null;
		
		if(TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())){
			image = FordiacImage.ICON_Adapter.getImage();
		} else if(TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())){
			image = FordiacImage.ICON_SubApp.getImage();
		} else if(TypeLibraryTags.FB_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())){
			Palette palette = FBTypeUtils.getPalletteForFBTypeFile(element);
			if(palette != null){
				PaletteEntry entry = TypeLibrary.getPaletteEntry(palette, element);
				
				if(null != entry){								
					FBType type = ((FBTypePaletteEntry)entry).getFBType(); 
					if(type instanceof BasicFBType){
						image = FordiacImage.ICON_BasicFB.getImage();
					}
					else if(type instanceof CompositeFBType){
						image = FordiacImage.ICON_CompositeFB.getImage();
					}
					else if(type instanceof ServiceInterfaceFBType){
						image = FordiacImage.ICON_SIFB.getImage();
					}
				}
				else{
					//partly load file to determine type
					image = checkUnloadedFBType(element);
				}
			}				
		}
		
		if(null != image) {
			if(fileHasProblems(element)){
				return FordiacImage.getErrorOverlayImage(image);
			}
		}
		
		return image;
	}
	
	private static boolean fileHasProblems(IFile element) {
		IMarker problems[] = null;
		try {
			problems = element.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		return ((null != problems) && (0 < problems.length));
	}


	private static Image checkUnloadedFBType(IFile element) {
		Image image = null;
		Scanner scanner = null;
		try{
			scanner =  new Scanner(element.getContents());
			if(null != scanner.findWithinHorizon("BasicFB", 0)){ //$NON-NLS-1$
				image = FordiacImage.ICON_BasicFB.getImage();
				
			}
			else{
				scanner.reset();
				if(null != scanner.findWithinHorizon("FBNetwork", 0)){ //$NON-NLS-1$
					image = FordiacImage.ICON_CompositeFB.getImage();
					
				}else{
					image = FordiacImage.ICON_SIFB.getImage();
				}
			}
		}
		catch(Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
	    }finally{
	    	if(null != scanner){
	    		scanner.close();
	    	}
	    }
		
		return image;	
	}

	@Override
	public String getText(Object element) {
		if(element instanceof IFile){
			return getTextForFBFile((IFile)element);
		}
		return super.getText(element);
	}

	private static String getTextForFBFile(IFile element) {
		
		String text = null;
		
		
		if(TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())|| 
		   TypeLibraryTags.FB_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())||
		   TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING.equalsIgnoreCase(element.getFileExtension())){
			text = TypeLibrary.getTypeNameFromFile(element);
		}
		return text;
	}


	@Override
	public String getDescription(Object anElement) {
		if(anElement instanceof IFile){
			return getDescriptionForFBFile((IFile)anElement);
		}
		return null;
	}


	private static String getDescriptionForFBFile(IFile fbtFile) {
		FBType type = null;
		if(TypeLibraryTags.FB_TYPE_FILE_ENDING.equalsIgnoreCase(fbtFile.getFileExtension())){
			Palette palette = FBTypeUtils.getPalletteForFBTypeFile(fbtFile);
			if(palette != null){
				PaletteEntry entry = TypeLibrary.getPaletteEntry(palette, fbtFile);
				
				if(null != entry){								
					type = ((FBTypePaletteEntry)entry).getFBType();					
				}
			}
		}
		else{
			if(TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING.equalsIgnoreCase(fbtFile.getFileExtension())){
				Palette palette = FBTypeUtils.getPalletteForFBTypeFile(fbtFile);
				if(palette != null){
					PaletteEntry entry = TypeLibrary.getPaletteEntry(palette, fbtFile);
					if(null != entry){
						type = ((AdapterTypePaletteEntry)entry).getType().getAdapterFBType();
					}
				}
			}
		}
		
		if(null != type){
			return type.getName() + ": " + ((null == type.getComment()) ? "" : type.getComment()); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		return null;
	}

}
