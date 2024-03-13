/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.NamedElementComparator;
import org.eclipse.fordiac.ide.model.data.AnyElementaryType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STContinue;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExit;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.AccessMode;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.resource.XtextLiveScopeResourceSetProvider;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.TextRegion;

import com.google.inject.Inject;

public class ExtractCallableRefactoring extends Refactoring {

	@Inject
	private XtextLiveScopeResourceSetProvider resourceSetProvider;

	@Inject
	private ILocationInFileProvider locationInFileProvider;

	private String name = Messages.ExtractCallableRefactoring_Name;

	private XtextEditor editor;
	private ITextSelection selection;

	private XtextResource resourceCopy;
	private List<EObject> selectedSemanticElements;
	private ITextRegion selectedSemanticElementsRegion;
	private Optional<String> selectedSemanticElementsText = Optional.empty();

	private Optional<ICallable> callable = Optional.empty();
	private Optional<STSource> source = Optional.empty();
	private String callableType = "FUNCTION"; //$NON-NLS-1$
	private String callableName = "CALLABLE"; //$NON-NLS-1$
	private AccessMode referencedReturnVariable = AccessMode.NONE;
	private Map<STVarDeclaration, AccessMode> referencedLocalVariables = Collections.emptyMap();
	private Optional<INamedElement> returnType = Optional.empty();
	private List<STVarDeclaration> inputParameters = Collections.emptyList();
	private List<STVarDeclaration> outputParameters = Collections.emptyList();
	private List<STVarDeclaration> inoutParameters = Collections.emptyList();

	public void initialize(final XtextEditor editor, final ITextSelection selection) {
		this.editor = editor;
		this.selection = selection;

		resourceCopy = editor.getDocument().priorityReadOnly(this::copyResource);
		selectedSemanticElements = findSelectedSemanticObjects();
		selectedSemanticElementsRegion = calculateSelectedSemanticElementsRegion();
		selectedSemanticElementsText = calculateSelectedSemanticElementsText();

		callable = calculateCallable();
		source = calculateSource();
		callableName = calculateCallableName();
		referencedReturnVariable = calculateReferencedReturnVariable();
		referencedLocalVariables = calculateReferencedLocalVariables();
		returnType = calculateReturnType();
		inputParameters = calculateParameters(AccessMode.READ);
		outputParameters = calculateParameters(AccessMode.WRITE);
		inoutParameters = calculateParameters(AccessMode.READ_WRITE);
	}

	protected XtextResource copyResource(final XtextResource resource) {
		final IProject project = getProject(resource.getURI());
		final ResourceSet resourceSet = resourceSetProvider.get(project);
		return (XtextResource) resourceSet.getResource(resource.getURI(), true);
	}

	protected List<EObject> findSelectedSemanticObjects() {
		final ITextSelection trimmedSelection = STCoreRefactoringUtil.trimSelection(selection);
		final EObject semanticObject = STCoreRefactoringUtil.findSelectedSemanticObject(resourceCopy, trimmedSelection);
		if (semanticObject instanceof STExpression) {
			return List.of(semanticObject);
		}
		if (semanticObject != null) {
			return STCoreRefactoringUtil.findSelectedChildSemanticObjectsOfType(semanticObject, trimmedSelection,
					STStatement.class);
		}
		return Collections.emptyList();
	}

	protected ITextRegion calculateSelectedSemanticElementsRegion() {
		final ICompositeNode rootNode = resourceCopy.getParseResult().getRootNode();
		final ITextSelection trimmedSelection = STCoreRefactoringUtil.trimSelection(selection);
		final ITextRegion trimmedSelectedRegion = new TextRegion(trimmedSelection.getOffset(),
				trimmedSelection.getLength());
		final ITextRegion alignedSelectedRegion = STCoreRefactoringUtil.alignRegion(trimmedSelectedRegion, rootNode);
		return selectedSemanticElements.stream().map(NodeModelUtils::findActualNodeFor).map(INode::getTextRegion)
				.reduce(ITextRegion::merge).map(region -> region.merge(alignedSelectedRegion))
				.map(region -> STCoreRefactoringUtil.trimRegion(region, rootNode)).orElse(ITextRegion.EMPTY_REGION);
	}

	protected Optional<String> calculateSelectedSemanticElementsText() {
		try {
			return Optional.of(editor.getDocument().get(selectedSemanticElementsRegion.getOffset(),
					selectedSemanticElementsRegion.getLength()));
		} catch (final BadLocationException e) {
			return Optional.empty();
		}
	}

	@Override
	public RefactoringStatus checkInitialConditions(final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus result = new RefactoringStatus();
		if (selectedSemanticElements.isEmpty()) {
			result.merge(
					RefactoringStatus.createFatalErrorStatus(Messages.ExtractCallableRefactoring_InvalidSelection));
		} else if (selectedSemanticElementsRegion.getLength() == 0) {
			result.merge(RefactoringStatus.createFatalErrorStatus(Messages.ExtractCallableRefactoring_RegionNotFound));
		} else if (selectedSemanticElementsText.isEmpty()) {
			result.merge(RefactoringStatus.createFatalErrorStatus(Messages.ExtractCallableRefactoring_TextNotFound));
		} else if (callable.isEmpty()) {
			result.merge(
					RefactoringStatus.createFatalErrorStatus(Messages.ExtractCallableRefactoring_CallableNotFound));
		} else if (source.isEmpty()) {
			result.merge(RefactoringStatus.createFatalErrorStatus(Messages.ExtractCallableRefactoring_SourceNotFound));
		}
		result.merge(checkReturnType());
		result.merge(checkReturnVariableAccess());
		result.merge(checkControlFlowStatements());
		return result;
	}

	protected RefactoringStatus checkReturnType() {
		final RefactoringStatus result = new RefactoringStatus();
		if (getSelectedSingleExpression().isPresent() && returnType.isEmpty()) {
			result.merge(
					RefactoringStatus.createFatalErrorStatus(Messages.ExtractCallableRefactoring_ReturnTypeNotPresent));
		} else if (!returnType.stream().allMatch(AnyElementaryType.class::isInstance)) {
			result.merge(
					RefactoringStatus.createFatalErrorStatus(Messages.ExtractCallableRefactoring_InvalidReturnType));
		}
		return result;
	}

	protected RefactoringStatus checkReturnVariableAccess() {
		final RefactoringStatus result = new RefactoringStatus();
		switch (referencedReturnVariable) {
		case READ, READ_WRITE:
			result.merge(RefactoringStatus
					.createFatalErrorStatus(Messages.ExtractCallableRefactoring_InvalidReturnVariableAccess));
			break;
		case WRITE:
			result.merge(RefactoringStatus
					.createWarningStatus(Messages.ExtractCallableRefactoring_ReturnVariableAccessMayChangeSemantics));
			break;
		case NONE:
		default:
			break;
		}
		return result;
	}

	protected RefactoringStatus checkControlFlowStatements() {
		final RefactoringStatus result = new RefactoringStatus();
		if (!isCallableControlFlowContained() || !isLoopControlFlowContained()) {
			result.merge(
					RefactoringStatus.createFatalErrorStatus(Messages.ExtractCallableRefactoring_ControlFlowError));
		}
		return result;
	}

	protected boolean isCallableControlFlowContained() {
		final TreeIterator<EObject> iterator = EcoreUtil.getAllProperContents(selectedSemanticElements, true);
		while (iterator.hasNext()) {
			final EObject elem = iterator.next();
			if (elem instanceof STReturn) {
				return false;
			}
		}
		return true;
	}

	protected boolean isLoopControlFlowContained() {
		final TreeIterator<EObject> iterator = EcoreUtil.getAllProperContents(selectedSemanticElements, true);
		while (iterator.hasNext()) {
			final EObject elem = iterator.next();
			if (elem instanceof STForStatement || elem instanceof STRepeatStatement
					|| elem instanceof STWhileStatement) {
				iterator.prune();
			} else if (elem instanceof STContinue || elem instanceof STExit) {
				return false;
			}
		}
		return true;
	}

	@Override
	public RefactoringStatus checkFinalConditions(final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus result = new RefactoringStatus();
		result.merge(checkCallableName());
		return result;
	}

	protected RefactoringStatus checkCallableName() {
		final RefactoringStatus result = new RefactoringStatus();
		if (callableName.isBlank()) {
			result.merge(RefactoringStatus.createFatalErrorStatus(Messages.ExtractCallableRefactoring_NameNotProvided));
		} else if (IdentifierVerifier.verifyIdentifier(callableName).isPresent()) {
			result.merge(RefactoringStatus.createFatalErrorStatus(Messages.ExtractCallableRefactoring_InvalidName));
		} else if (source.stream().flatMap(s -> EcoreUtil2.getAllContentsOfType(s, ICallable.class).stream())
				.anyMatch(c -> Objects.equals(c.getName(), callableName))) {
			result.merge(RefactoringStatus.createFatalErrorStatus(Messages.ExtractCallableRefactoring_NameNotUnique));
		}
		return result;
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final ProviderDocumentChange change = new ProviderDocumentChange(getName(),
				(IFileEditorInput) editor.getEditorInput(), editor.getDocumentProvider(), false);
		change.setEdit(createTextEdit());
		change.setTextType(editor.getDocument().getResourceURI().fileExtension());
		return change;
	}

	protected TextEdit createTextEdit() {
		final MultiTextEdit textEdit = new MultiTextEdit();
		textEdit.addChild(createCallTextEdit());
		textEdit.addChild(createCallableTextEdit());
		return textEdit;
	}

	protected TextEdit createCallTextEdit() {
		final StringBuilder callExpression = new StringBuilder();
		if (getSelectedSingleExpression().isEmpty() && returnType.isPresent()) {
			callExpression.append(callable.map(ICallable::getName).orElse("")); //$NON-NLS-1$
			callExpression.append(" := "); //$NON-NLS-1$
		}
		callExpression.append(getCallableName());
		callExpression.append("("); //$NON-NLS-1$
		callExpression.append(Stream.concat(
				// inputs & inouts
				Stream.concat(inputParameters.stream(), inoutParameters.stream())
						.map(parameter -> parameter.getName() + " := " + parameter.getName()), //$NON-NLS-1$
				// outputs
				outputParameters.stream().map(parameter -> parameter.getName() + " => " + parameter.getName()) //$NON-NLS-1$
		).collect(Collectors.joining(", "))); //$NON-NLS-1$
		callExpression.append(")"); //$NON-NLS-1$
		if (getSelectedSingleExpression().isEmpty()) {
			callExpression.append(";"); //$NON-NLS-1$
		}
		return new ReplaceEdit(selectedSemanticElementsRegion.getOffset(), selectedSemanticElementsRegion.getLength(),
				callExpression.toString());
	}

	protected TextEdit createCallableTextEdit() {
		final StringBuilder callableText = new StringBuilder();
		callableText.append(System.lineSeparator());
		generateCallableHeader(callableText);
		generateCallableBody(callableText);
		generateCallableFooter(callableText);
		return new ReplaceEdit(calculateInsertPosition(), 0, callableText.toString());
	}

	@SuppressWarnings("boxing")
	protected int calculateInsertPosition() {
		return callable.map(locationInFileProvider::getFullTextRegion)
				.map(region -> region.getOffset() + region.getLength()).orElse(editor.getDocument().getLength());
	}

	protected void generateCallableHeader(final StringBuilder builder) {
		builder.append(System.lineSeparator());
		builder.append(getCallableType());
		builder.append(" "); //$NON-NLS-1$
		builder.append(getCallableName());
		if (returnType.isPresent()) {
			builder.append(": "); //$NON-NLS-1$
			builder.append(returnType.get().getName());
		}
		builder.append(System.lineSeparator());
		generateCallableParameters("INPUT", inputParameters, builder); //$NON-NLS-1$
		generateCallableParameters("IN_OUT", inoutParameters, builder); //$NON-NLS-1$
		generateCallableParameters("OUTPUT", outputParameters, builder); //$NON-NLS-1$
	}

	protected static void generateCallableParameters(final String type, final List<STVarDeclaration> parameters,
			final StringBuilder builder) {
		if (!parameters.isEmpty()) {
			builder.append("VAR_"); //$NON-NLS-1$
			builder.append(type);
			builder.append(System.lineSeparator());
			for (final var param : parameters) {
				builder.append("    "); //$NON-NLS-1$
				builder.append(param.getName());
				builder.append(": "); //$NON-NLS-1$
				builder.append(STCoreUtil.getFeatureType(param).getName());
				builder.append(";"); //$NON-NLS-1$
				builder.append(System.lineSeparator());
			}
			builder.append("END_VAR"); //$NON-NLS-1$
			builder.append(System.lineSeparator());
		}
	}

	protected void generateCallableBody(final StringBuilder builder) {
		final Optional<STExpression> singleExpression = getSelectedSingleExpression();
		if (singleExpression.isPresent()) {
			builder.append(getCallableName());
			builder.append(" := "); //$NON-NLS-1$
			builder.append(getRefactoredSelectedSemanticElementsText().orElse("")); //$NON-NLS-1$
			builder.append(";"); //$NON-NLS-1$
			builder.append(System.lineSeparator());
		} else {
			builder.append(getRefactoredSelectedSemanticElementsText().orElse("")); //$NON-NLS-1$
			builder.append(System.lineSeparator());
		}
	}

	protected Optional<String> getRefactoredSelectedSemanticElementsText() {
		return selectedSemanticElementsText.map(this::performSelectedSemanticElementsReplacements);
	}

	protected String performSelectedSemanticElementsReplacements(final String text) {
		final StringBuilder result = new StringBuilder(text);
		getSelectedSemanticElementsReplacements().stream()
				.sorted((a, b) -> Integer.compare(b.getOffset(), a.getOffset()))
				.forEachOrdered(repl -> repl.applyTo(result));
		return result.toString();
	}

	protected List<ReplaceRegion> getSelectedSemanticElementsReplacements() {
		final List<ReplaceRegion> result = new ArrayList<>();
		selectedSemanticElements.stream()
				.flatMap(elem -> EcoreUtil2.getAllContentsOfType(elem, STFeatureExpression.class).stream())
				.filter(this::isReturnVariableReference).map(NodeModelUtils::findActualNodeFor)
				.map(INode::getTextRegion)
				.map(region -> new ReplaceRegion(region.getOffset() - selectedSemanticElementsRegion.getOffset(),
						region.getLength(), callableName))
				.forEachOrdered(result::add);
		return result;
	}

	protected void generateCallableFooter(final StringBuilder builder) {
		builder.append("END_"); //$NON-NLS-1$
		builder.append(getCallableType());
		builder.append(System.lineSeparator());
	}

	protected Optional<ICallable> calculateCallable() {
		if (!selectedSemanticElements.isEmpty()) {
			return Optional.ofNullable(EcoreUtil2.getContainerOfType(selectedSemanticElements.get(0), ICallable.class));
		}
		return Optional.empty();
	}

	protected Optional<STSource> calculateSource() {
		return callable.flatMap(c -> Optional.ofNullable(EcoreUtil2.getContainerOfType(c, STSource.class)));
	}

	protected String calculateCallableName() {
		final Set<String> usedNames = source.stream()
				.flatMap(s -> EcoreUtil2.getAllContentsOfType(s, ICallable.class).stream()).map(ICallable::getName)
				.collect(Collectors.toSet());
		final String name = callable.map(ICallable::getName).orElse(callableType);
		return IntStream.range(1, Integer.MAX_VALUE).mapToObj(i -> name + "_" + i) //$NON-NLS-1$
				.filter(Predicate.not(usedNames::contains)).findFirst().orElse(""); //$NON-NLS-1$
	}

	protected AccessMode calculateReferencedReturnVariable() {
		return selectedSemanticElements.stream()
				.flatMap(elem -> EcoreUtil2.getAllContentsOfType(elem, STFeatureExpression.class).stream())
				.filter(this::isReturnVariableReference).map(STCoreUtil::getAccessMode).reduce(AccessMode::merge)
				.orElse(AccessMode.NONE);
	}

	protected boolean isReturnVariableReference(final STFeatureExpression expression) {
		return expression.getFeature() instanceof ICallable && !expression.isCall()
				&& EcoreUtil2.getContainerOfType(expression.getFeature(), ICallable.class) == callable.orElse(null);
	}

	protected Map<STVarDeclaration, AccessMode> calculateReferencedLocalVariables() {
		return selectedSemanticElements.stream()
				.flatMap(elem -> EcoreUtil2.getAllContentsOfType(elem, STFeatureExpression.class).stream())
				.filter(this::isLocalVariableReference).collect(Collectors.toMap(
						expr -> (STVarDeclaration) expr.getFeature(), STCoreUtil::getAccessMode, AccessMode::merge));
	}

	protected boolean isLocalVariableReference(final STFeatureExpression expression) {
		return expression.getFeature() instanceof STVarDeclaration
				&& EcoreUtil2.getContainerOfType(expression.getFeature(), ICallable.class) == callable.orElse(null);
	}

	protected Optional<INamedElement> calculateReturnType() {
		if (referencedReturnVariable != AccessMode.NONE) {
			return callable.map(ICallable::getReturnType);
		}
		return getSelectedSingleExpression().map(STExpression::getResultType);
	}

	protected List<STVarDeclaration> calculateParameters(final AccessMode mode) {
		return referencedLocalVariables.entrySet().stream().filter(entry -> entry.getValue() == mode).map(Entry::getKey)
				.sorted(NamedElementComparator.INSTANCE).toList();
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public XtextEditor getEditor() {
		return editor;
	}

	public ITextSelection getSelection() {
		return selection;
	}

	protected XtextResource getResourceCopy() {
		return resourceCopy;
	}

	protected List<EObject> getSelectedSemanticElements() {
		return selectedSemanticElements;
	}

	protected ITextRegion getSelectedSemanticElementsRegion() {
		return selectedSemanticElementsRegion;
	}

	protected Optional<String> getSelectedSemanticElementsText() {
		return selectedSemanticElementsText;
	}

	protected Optional<STExpression> getSelectedSingleExpression() {
		if (selectedSemanticElements.size() == 1) {
			return selectedSemanticElements.stream().filter(STExpression.class::isInstance)
					.map(STExpression.class::cast).filter(expr -> STCoreUtil.getAccessMode(expr) != AccessMode.NONE)
					.findAny();
		}
		return Optional.empty();
	}

	protected Stream<STStatement> getSelectedStatements() {
		return selectedSemanticElements.stream().filter(STStatement.class::isInstance).map(STStatement.class::cast);
	}

	protected Optional<ICallable> getCallable() {
		return callable;
	}

	protected Optional<STSource> getSource() {
		return source;
	}

	public String getCallableType() {
		return callableType;
	}

	public void setCallableType(final String callableType) {
		this.callableType = callableType;
	}

	public String getCallableName() {
		return callableName;
	}

	public void setCallableName(final String callableName) {
		this.callableName = callableName;
	}

	protected AccessMode getReferencedReturnVariable() {
		return referencedReturnVariable;
	}

	protected Map<STVarDeclaration, AccessMode> getReferencedLocalVariables() {
		return referencedLocalVariables;
	}

	public Optional<INamedElement> getReturnType() {
		return returnType;
	}

	public void setReturnType(final Optional<INamedElement> returnType) {
		this.returnType = returnType;
	}

	public List<STVarDeclaration> getInputParameters() {
		return inputParameters;
	}

	public List<STVarDeclaration> getOutputParameters() {
		return outputParameters;
	}

	public List<STVarDeclaration> getInoutParameters() {
		return inoutParameters;
	}

	protected static IProject getProject(final URI uri) {
		final IFile file = getFile(uri);
		if (file != null && file.exists()) {
			return file.getProject();
		}
		return null;
	}

	protected static IFile getFile(final URI uri) {
		if (uri.isPlatformResource()) {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true)));
		}
		return null;
	}
}
