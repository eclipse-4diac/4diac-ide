/*******************************************************************************
 * Copyright (c) 2022, 2024 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Hesam Rezaee
 *       - initial API and implementation and/or initial documentation
 *   Martin Erich Jobst
 *       - add block label and image
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.ui.labeling;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstsSource;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock;
import org.eclipse.fordiac.ide.globalconstantseditor.ui.Messages;
import org.eclipse.fordiac.ide.structuredtextcore.ui.labeling.STCoreLabelProvider;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.google.inject.Inject;

@SuppressWarnings({ "static-method", "java:S2177", "java:S1172" })
public class GlobalConstantsLabelProvider extends STCoreLabelProvider {

	@Inject
	public GlobalConstantsLabelProvider(final AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	public ImageDescriptor image(final STGlobalConstsSource element) {
		return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
	}

	public String text(final STVarGlobalDeclarationBlock element) {
		if (element.isConstant()) {
			return Messages.GlobalConstantsOutlineTreeProvider_VarGlobalDeclarationBlockConstantText;
		}
		return Messages.GlobalConstantsOutlineTreeProvider_VarGlobalDeclarationBlockText;
	}

	public ImageDescriptor image(final STVarGlobalDeclarationBlock element) {
		return FordiacImage.ICON_DATA.getImageDescriptor();
	}
}
