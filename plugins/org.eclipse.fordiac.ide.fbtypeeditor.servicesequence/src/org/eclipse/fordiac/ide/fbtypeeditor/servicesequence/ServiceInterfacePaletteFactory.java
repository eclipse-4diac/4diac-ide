/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence;

import org.eclipse.fordiac.ide.gef.utilities.TemplateCreationFactory;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.jface.resource.ImageDescriptor;

public final class ServiceInterfacePaletteFactory {
	public static final String SERVICE_SEQUENCE = "ServiceSequence"; //$NON-NLS-1$
	public static final String SERVICE_TRANSACTION = "ServiceTransaction"; //$NON-NLS-1$
	public static final String RIGHT_OUTPUT_PRIMITIVE = "RightOutputPrimitive"; //$NON-NLS-1$
	public static final String RIGHT_INPUT_PRIMITIVE = "RightInputPrimitive"; //$NON-NLS-1$
	public static final String LEFT_OUTPUT_PRIMITIVE = "LeftOutputPrimitive"; //$NON-NLS-1$
	public static final String LEFT_INPUT_PRIMITIVE = "LeftInputPrimitive"; //$NON-NLS-1$
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
		final PaletteDrawer drawer = new PaletteDrawer(Messages.ServiceInterfacePaletteFactory_DrawerName);

		ImageDescriptor desc = FordiacImage.ICON_LEFT_OUTPUT_PRIMITIVE.getImageDescriptor();
		final CombinedTemplateCreationEntry leftOPrimitiveEntry = new CombinedTemplateCreationEntry(
				Messages.ServiceInterfacePaletteFactory_OutputPrimitive,
				Messages.ServiceInterfacePaletteFactory_OutputPrimitive_Desc,
				new TemplateCreationFactory(LEFT_OUTPUT_PRIMITIVE), desc, desc);
		drawer.add(leftOPrimitiveEntry);

		desc = FordiacImage.ICON_TRANSACTION.getImageDescriptor();
		final CombinedTemplateCreationEntry transactionEntry = new CombinedTemplateCreationEntry(
				Messages.ServiceInterfacePaletteFactory_ServiceTransaction,
				Messages.ServiceInterfacePaletteFactory_ServiceTransaction_Desc,
				new TemplateCreationFactory(SERVICE_TRANSACTION), desc, desc);
		drawer.add(transactionEntry);

		desc = FordiacImage.ICON_SERVICE_SEQUENCE.getImageDescriptor();
		final CombinedTemplateCreationEntry sequenceEntry = new CombinedTemplateCreationEntry(
				Messages.ServiceSequenceSection_ServiceSequence, Messages.ServiceSequenceSection_ServiceSequence,
				new TemplateCreationFactory(SERVICE_SEQUENCE), desc, desc);
		drawer.add(sequenceEntry);
		palette.add(drawer);
	}

	private ServiceInterfacePaletteFactory() {
		throw new UnsupportedOperationException(
				"ServiceInterfacePaletteFactory utility class should not be instantiated!"); //$NON-NLS-1$
	}
}