/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
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
 *   Martin Melik Merkumians - Adds validator to ensure global const are marked constant
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.validation;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.globalconstantseditor.Messages;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.GlobalConstantsPackage;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstants;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstsSource;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock;
import org.eclipse.fordiac.ide.globalconstantseditor.resource.GlobalConstantsResource;
import org.eclipse.fordiac.ide.globalconstantseditor.services.GlobalConstantsGrammarAccess;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreImportValidator;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreTypeUsageCollector;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.Check;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * This class contains custom validation rules.
 *
 * See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
public class GlobalConstantsValidator extends AbstractGlobalConstantsValidator {

	public static final String ISSUE_CODE_PREFIX = "org.eclipse.fordiac.ide.globalconstseditor."; //$NON-NLS-1$
	public static final String GLOBAL_CONSTANTS_NAME_MISMATCH = ISSUE_CODE_PREFIX + "globalConstantsNameMismatch"; //$NON-NLS-1$
	public static final String GLOBAL_VARS_NOT_MARKED_CONSTANT = ISSUE_CODE_PREFIX + "globalVarsNotMarkedConstant"; //$NON-NLS-1$

	@Inject
	GlobalConstantsGrammarAccess grammarAccess;

	@Inject
	private Provider<STCoreTypeUsageCollector> typeUsageCollectorProvider;

	@Inject
	private STCoreImportValidator importValidator;

	@Check
	public void checkPackageDeclaration(final STGlobalConstsSource source) {
		checkPackageDeclaration(source, GlobalConstantsPackage.Literals.ST_GLOBAL_CONSTS_SOURCE__NAME,
				source.getName());
	}

	@Check
	public void checkImports(final STGlobalConstsSource source) {
		if (!source.getImports().isEmpty()) {
			importValidator.validateImports(source.getName(), source.getImports(),
					typeUsageCollectorProvider.get().collectUsedTypes(source), this);
		}
	}

	@Check
	public void checkGlobalConstantsMatchesTypeName(final STGlobalConstants globalConstants) {
		if (globalConstants.eResource() instanceof final GlobalConstantsResource resource
				&& resource.getInternalLibraryElement() instanceof final GlobalConstants globalConstantsType
				&& !Objects.equal(globalConstantsType.getName(), globalConstants.getName())) {
			error(MessageFormat.format(Messages.GlobalConstValidator_GlobalConstantsNameMismatch,
					globalConstants.getName(), globalConstantsType.getName()),
					LibraryElementPackage.Literals.INAMED_ELEMENT__NAME, GLOBAL_CONSTANTS_NAME_MISMATCH,
					globalConstants.getName(), globalConstantsType.getName());

		}
	}

	@Check
	public void checkVarConfigBlockIsMarkedConstant(final STVarGlobalDeclarationBlock declarationBlock) {
		if (!declarationBlock.isConstant()) {
			final var node = NodeModelUtils.findActualNodeFor(declarationBlock);
			for (final var n : node.getAsTreeIterable()) {
				final var grammarElement = n.getGrammarElement();
				if (grammarElement instanceof Keyword && grammarElement == grammarAccess
						.getSTVarGlobalDeclarationBlockAccess().getVAR_GLOBALKeyword_1()) {
					getMessageAcceptor().acceptError(Messages.GlobalConstValidator_GlobalConstBlockNotMarkedConstant,
							declarationBlock, n.getOffset(), n.getLength(), GLOBAL_VARS_NOT_MARKED_CONSTANT);
				}
			}
		}
	}
}
