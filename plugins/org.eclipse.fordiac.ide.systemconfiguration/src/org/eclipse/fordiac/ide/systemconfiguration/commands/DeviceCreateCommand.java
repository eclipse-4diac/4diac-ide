/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz, Monika Wenger, Kiril Dorofeev
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.dataimport.SystemImporter;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.systemconfiguration.Messages;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.eclipse.fordiac.ide.util.Activator;
import org.eclipse.fordiac.ide.util.ColorHelper;
import org.eclipse.fordiac.ide.util.YUV;
import org.eclipse.fordiac.ide.util.preferences.PreferenceConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IEditorPart;

public class DeviceCreateCommand extends Command {
	private static final String CREATE_DEVICE_LABEL = Messages.DeviceCreateCommand_LABEL_CreateDevice;
	private final DeviceTypePaletteEntry entry;
	private final SystemConfiguration parent;
	private final Rectangle bounds;
	private Device device;
	private IEditorPart editor;

	public Device getDevice() {
		return device;
	}

	@Override
	public boolean canUndo() {
		return editor.equals(Abstract4DIACUIPlugin.getCurrentActiveEditor());
	}

	public DeviceCreateCommand(final DeviceTypePaletteEntry entry, final SystemConfiguration parent,
			final Rectangle bounds) {
		this.entry = entry;
		this.parent = parent;
		this.bounds = bounds;
		setLabel(CREATE_DEVICE_LABEL);
	}

	@Override
	public boolean canExecute() {
		return entry != null && bounds != null && (parent != null);
	}

	@Override
	public void execute() {
		editor = Abstract4DIACUIPlugin.getCurrentActiveEditor();
		setLabel(getLabel() + "(" + (editor != null ? editor.getTitle() : "") + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		if (parent != null) {
			createDevice();
			device.setPaletteEntry(entry);
			SystemImporter.createParamters(device);
			setDeviceProfile();
			device.setX(bounds.x);
			device.setY(bounds.y);
			parent.getDevices().add(device);
			// the name needs to be set after the device is added to the network
			// so that name checking works correctly
			device.setName(NameRepository.createUniqueName(device, entry.getDeviceType().getName()));
			setDeviceAttributes();
			createResource();		
			SystemManager.INSTANCE.notifyListeners();
		}
	}
	
	private void setDeviceAttributes() {
		for(AttributeDeclaration attributeDeclaration : entry.getDeviceType().getAttributeDeclarations()) {
			Attribute attribute = LibraryElementFactory.eINSTANCE.createAttribute();
			attribute.setName(attributeDeclaration.getName());
			attribute.setComment(attributeDeclaration.getComment());
			attribute.setValue(attributeDeclaration.getInitialValue());
			attribute.setAttributeDeclaration(attributeDeclaration);
			device.getAttributes().add(attribute);
		}
	}

	private void setDeviceProfile() {
		String profile;
		if(null != device.getType().getProfile() && !"".equals(device.getType().getProfile())){ //$NON-NLS-1$
			profile = device.getType().getProfile();
		}else{ 
			profile = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_DEFAULT_COMPLIANCE_PROFILE);
		}
		device.setProfile(profile);
	}

	protected void createDevice() {
		device = LibraryElementFactory.eINSTANCE.createDevice();
		device.setColor(createRandomDeviceColor());
	}

	private void createResource() {
		for (Resource res : entry.getDeviceType().getResource()) {
			ResourceCreateCommand cmd = null;
			if (res.getPaletteEntry() != null) {
				cmd = new ResourceCreateCommand((ResourceTypeEntry) res.getPaletteEntry(), device, true);
				cmd.execute();
				Resource copy = cmd.getResource();
				copy.setName(res.getName());
			} else {
				org.eclipse.fordiac.ide.systemconfiguration.Activator.getDefault().logInfo("Referenced Resource Type: "
						+ (res.getName() != null ? res.getName() : "N/A")
						+ (res.getPaletteEntry() != null ? " (" + res.getTypeName() + ") " : "(N/A)")
						+ " not found. Please check whether your palette contains that type and add it manually to your device!");
			}
		}		
		createDefaultResource();
	}

	private void createDefaultResource() {
		ResourceTypeEntry type = null;
		if (device.getType().getName().contains("FBRT") //$NON-NLS-1$
				|| device.getType().getName().contains("FRAME")) { //$NON-NLS-1$
			type = getResourceType("PANEL_RESOURCE"); //$NON-NLS-1$
		} else {
			type = getResourceType("EMB_RES"); //$NON-NLS-1$ 
		}
		if(null != type) {
			ResourceCreateCommand cmd = new ResourceCreateCommand(type, device, false);	
			cmd.execute();
		}
	}

	private ResourceTypeEntry getResourceType(String resTypeName) {
		List<PaletteEntry> typeEntries = device.getPaletteEntry().getGroup().getPallete().getTypeEntries(resTypeName);
		if(!typeEntries.isEmpty() && typeEntries.get(0) instanceof ResourceTypeEntry) {
			return (ResourceTypeEntry)typeEntries.get(0);
		}
		return null;
	}

	private Color createRandomDeviceColor() {
		Color randomColor;
		boolean exist;
		List<YUV> existingColors = new ArrayList<>();
		for (Device dev : parent.getDevices()) {
			Color devcolor = dev.getColor();
			existingColors.add(new YUV(new RGB(devcolor.getRed(), devcolor.getGreen(), devcolor.getBlue())));
		}
		do {
			randomColor = ColorHelper.createRandomColor();
			YUV randYUV = new YUV(new RGB(randomColor.getRed(), randomColor.getGreen(), randomColor.getBlue()));
			exist = false;
			for (YUV yuv : existingColors) {
				if (randYUV.nearbyColor(yuv)) {
					exist = true;
					break;
				}
			}
		} while (exist);
		return randomColor;
	}

	@Override
	public void redo() {
		if (parent != null) {
			parent.getDevices().add(device);
			SystemManager.INSTANCE.notifyListeners();
		}
	}

	@Override
	public void undo() {
		if (parent != null) {
			parent.getDevices().remove(device);
			SystemManager.INSTANCE.notifyListeners();
		}
	}
}