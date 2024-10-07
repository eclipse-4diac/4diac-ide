/**
 * Copyright (c) 2021, 2024 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *       - adds implementations for STVarDeclaration and STFunction text
 *   Martin Erich Jobst
 *       - externalize strings
 *       - move implementation for STFunction to STFunctionLabelProvider
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui.labeling;

import java.text.MessageFormat;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarPlainDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.inject.Inject;

@SuppressWarnings({ "static-method", "java:S1172" })
public class STCoreLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	public STCoreLabelProvider(final AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	public String text(final STSource element) {
		if (element.eResource() instanceof final LibraryElementXtextResource libResource
				&& libResource.getInternalLibraryElement() != null) {
			return libResource.getInternalLibraryElement().getName();
		}
		return null;
	}

	public ImageDescriptor image(final STSource element) {
		return FordiacImage.ICON_ALGORITHM.getImageDescriptor();
	}

	public String text(final VarDeclaration element) {
		return MessageFormat.format(Messages.STCoreLabelProvider_VarDeclarationText, element.getName(),
				element.getFullTypeName());
	}

	public String text(final FB element) {
		return MessageFormat.format(Messages.STCoreLabelProvider_FBText, element.getName(), element.getFullTypeName());
	}

	public String text(final STVarDeclaration element) {
		return MessageFormat.format(Messages.STCoreLabelProvider_VarDeclarationText, element.getName(),
				element.getType() != null ? element.getType().getName() : null);
	}

	public String text(final STVarPlainDeclarationBlock element) {
		if (element.isConstant()) {
			return Messages.STCoreLabelProvider_VarPlainDeclarationBlockConstantText;
		}
		return Messages.STCoreLabelProvider_VarPlainDeclarationBlockText;
	}

	public ImageDescriptor image(final STVarPlainDeclarationBlock element) {
		return FordiacImage.ICON_DATA.getImageDescriptor();
	}

	public String text(final STVarTempDeclarationBlock element) {
		if (element.isConstant()) {
			return Messages.STCoreLabelProvider_VarTempDeclarationBlockConstantText;
		}
		return Messages.STCoreLabelProvider_VarTempDeclarationBlockText;
	}

	public ImageDescriptor image(final STVarTempDeclarationBlock element) {
		return FordiacImage.ICON_DATA.getImageDescriptor();
	}

	public String text(final STVarInputDeclarationBlock element) {
		return Messages.STCoreLabelProvider_VarInputDeclarationBlockText;
	}

	public ImageDescriptor image(final STVarInputDeclarationBlock element) {
		return FordiacImage.ICON_DATA_INPUT.getImageDescriptor();
	}

	public String text(final STVarInOutDeclarationBlock element) {
		return Messages.STCoreLabelProvider_VarInOutDeclarationBlockText;
	}

	public ImageDescriptor image(final STVarInOutDeclarationBlock element) {
		return FordiacImage.ICON_DATA_INPUT.getImageDescriptor();
	}

	public String text(final STVarOutputDeclarationBlock element) {
		return Messages.STCoreLabelProvider_VarOutputDeclarationBlockText;
	}

	public ImageDescriptor image(final STVarOutputDeclarationBlock element) {
		return FordiacImage.ICON_DATA_OUTPUT.getImageDescriptor();
	}
}
