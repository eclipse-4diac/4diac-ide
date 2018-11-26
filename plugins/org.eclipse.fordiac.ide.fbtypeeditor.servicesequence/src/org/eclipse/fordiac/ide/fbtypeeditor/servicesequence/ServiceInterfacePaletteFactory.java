/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence;

import org.eclipse.fordiac.ide.gef.utilities.TemplateCreationFactory;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.jface.resource.ImageDescriptor;

public final class ServiceInterfacePaletteFactory {
	private static final String SERVICE_TRANSACTION = "ServiceTransaction";
	public static final String RIGHT_OUTPUT_PRIMITIVE = "RightOutputPrimitive";
	public static final String RIGHT_INPUT_PRIMITIVE = "RightInputPrimitive";
	public static final String LEFT_OUTPUT_PRIMITIVE = "LeftOutputPrimitive";
	public static final String LEFT_INPUT_PRIMITIVE = "LeftInputPrimitive";
	private static final String PALETTE_DOCK_LOCATION = "ServiceInterfacePaletteFactory.Location"; //$NON-NLS-1$
	private static final String PALETTE_SIZE = "ServiceInterfacePaletteFactory.Size"; //$NON-NLS-1$
	private static final String PALETTE_STATE = "ServiceInterfacePaletteFactory.State"; //$NON-NLS-1$

	public static FlyoutPreferences createPalettePreferences() {
		return new FlyoutPreferences() {

			@Override
			public int getDockLocation() {
				return Activator.getDefault().getPreferenceStore().getInt(PALETTE_DOCK_LOCATION);
			}

			@Override
			public int getPaletteState() {
				return Activator.getDefault().getPreferenceStore().getInt(PALETTE_STATE);
			}

			@Override
			public int getPaletteWidth() {
				return Activator.getDefault().getPreferenceStore().getInt(PALETTE_SIZE);
			}

			@Override
			public void setDockLocation(final int location) {
				Activator.getDefault().getPreferenceStore().setValue(PALETTE_DOCK_LOCATION, location);
			}

			@Override
			public void setPaletteState(final int state) {
				Activator.getDefault().getPreferenceStore().setValue(PALETTE_STATE, state);
			}

			@Override
			public void setPaletteWidth(final int width) {
				Activator.getDefault().getPreferenceStore().setValue(PALETTE_SIZE, width);
			}
		};
	}

	public static PaletteRoot createPalette() {
		final PaletteRoot palette = new PaletteRoot();
		fillPalette(palette);
		return palette;
	}

	private static void fillPalette(final PaletteRoot palette) {
		PaletteDrawer drawer = new PaletteDrawer("Left Interface");
		ImageDescriptor desc = FordiacImage.ICON_LeftInputPrimitive.getImageDescriptor();
		CombinedTemplateCreationEntry entry = new CombinedTemplateCreationEntry("Input Primitive", "Input Primitive",
				new TemplateCreationFactory(LEFT_INPUT_PRIMITIVE), desc, desc);
		drawer.add(entry);
		desc = FordiacImage.ICON_LeftOutputPrimitive.getImageDescriptor();
		CombinedTemplateCreationEntry entry2 = new CombinedTemplateCreationEntry("Output Primitive", "Output Primitive",
				new TemplateCreationFactory(LEFT_OUTPUT_PRIMITIVE), desc, desc);
		drawer.add(entry2);
		palette.add(drawer);
		drawer = new PaletteDrawer("Right Interface");
		desc = FordiacImage.ICON_RigthInputPrimitive.getImageDescriptor();
		CombinedTemplateCreationEntry entry3 = new CombinedTemplateCreationEntry("Input Primitive", "Input Primitive",
				new TemplateCreationFactory(RIGHT_INPUT_PRIMITIVE), desc, desc);
		drawer.add(entry3);
		desc = FordiacImage.ICON_RigthOutputPrimitive.getImageDescriptor();
		CombinedTemplateCreationEntry entry4 = new CombinedTemplateCreationEntry("Output Primitive", "Output Primitive",
				new TemplateCreationFactory(RIGHT_OUTPUT_PRIMITIVE), desc, desc);
		drawer.add(entry4);
		palette.add(drawer);
		drawer = new PaletteDrawer("Service Transaction");
		desc = FordiacImage.ICON_Transaction.getImageDescriptor();
		CombinedTemplateCreationEntry entry5 = new CombinedTemplateCreationEntry("Service Transaction",
				"Service Transaction", new TemplateCreationFactory(SERVICE_TRANSACTION), desc, desc);
		drawer.add(entry5);
		palette.add(drawer);
	}
}