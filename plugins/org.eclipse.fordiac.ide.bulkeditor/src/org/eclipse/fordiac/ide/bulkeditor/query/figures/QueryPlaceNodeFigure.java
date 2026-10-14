/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.query.figures;

import java.util.List;
import java.util.function.Predicate;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.Messages;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;
import org.eclipse.fordiac.ide.gef.annotation.ProblemAnnotationStyler;
import org.eclipse.osgi.util.NLS;

/** Node of the query place with its scope and the linked libraries toggle. */
public class QueryPlaceNodeFigure extends QueryNodeFigure {

	private final QueryButton ignoreLinkedLibrariesToggle = QueryButton
			.createToggle(Messages.QueryScope_IgnoreLinkedLibraries);
	private final QueryButton projectToggle = QueryButton.createToggle(Messages.QueryScope_Project);
	private final QueryButton subappHierarchyToggle = QueryButton.createToggle(Messages.QueryScope_SubappHierarchy);
	private final QueryButton selectSubappHierarchyButton = QueryButton
			.createPushButton(Messages.QueryScope_SelectSubappHierarchy);
	private final Figure optionFigure = createVerticalFigure(0);
	private final Figure subappHierarchyFigure = createVerticalFigure(1);
	private Runnable subappHierarchyButtonAction = () -> {
		// the selection is opened by the edit part
	};
	private Predicate<String> subappHierarchyElementExists = _ -> true;

	public QueryPlaceNodeFigure(final EObject place) {
		super(place);
		ignoreLinkedLibrariesToggle.addActionListener(_ -> changeIgnoreLinkedLibraries());
		projectToggle.addActionListener(_ -> changeScope(false));
		subappHierarchyToggle.addActionListener(_ -> changeScope(true));
		selectSubappHierarchyButton.addActionListener(_ -> subappHierarchyButtonAction.run());

		final Figure scopeToggles = createHorizontalFigure(2);
		scopeToggles.add(projectToggle);
		scopeToggles.add(subappHierarchyToggle);

		final Figure body = createVerticalFigure(2);
		body.setBorder(new MarginBorder(2, 6, 4, 6));
		body.add(scopeToggles);
		body.add(optionFigure);
		body.add(subappHierarchyFigure);
		add(body);
	}

	public void setSubappHierarchyButtonAction(final Runnable subappHierarchyButtonAction) {
		this.subappHierarchyButtonAction = subappHierarchyButtonAction;
	}

	public void setSubappHierarchyElementExists(final Predicate<String> subappHierarchyElementExists) {
		this.subappHierarchyElementExists = subappHierarchyElementExists;
	}

	@Override
	public void refresh() {
		super.refresh();
		final boolean isSubappHierarchyScope = QueryModelHelper.isSubappHierarchyScope(getElement());
		projectToggle.setSelected(!isSubappHierarchyScope);
		subappHierarchyToggle.setSelected(isSubappHierarchyScope);
		ignoreLinkedLibrariesToggle.setSelected(
				QueryModelHelper.getBooleanFeature(getElement(), QueryModelHelper.FEATURE_IGNORE_LINKED_LIBRARIES));
		showOption(isSubappHierarchyScope ? selectSubappHierarchyButton : ignoreLinkedLibrariesToggle);
		refreshSubappHierarchy(isSubappHierarchyScope);
	}

	private void showOption(final IFigure option) {
		if (option.getParent() != optionFigure) {
			optionFigure.removeAll();
			optionFigure.add(option);
		}
	}

	private void refreshSubappHierarchy(final boolean shown) {
		subappHierarchyFigure.removeAll();
		if (!shown) {
			return;
		}
		final List<String> hierarchy = QueryModelHelper.getSubappHierarchy(getElement());
		if (hierarchy.isEmpty()) {
			subappHierarchyFigure.add(createElementLabel(Messages.QueryScope_NoSubappHierarchy, true));
		}
		for (final String name : hierarchy) {
			subappHierarchyFigure.add(subappHierarchyElementExists.test(name) ? createElementLabel(name, false)
					: createElementLabel(NLS.bind(Messages.QueryScope_MissingElement, name), true));
		}
	}

	private static Label createElementLabel(final String text, final boolean error) {
		final Label label = new Label(text);
		label.setBorder(new MarginBorder(0, 4, 0, 0));
		if (error) {
			label.setForegroundColor(ProblemAnnotationStyler.getErrorAnnotationColor());
		}
		return label;
	}

	private static Figure createVerticalFigure(final int spacing) {
		return createFigure(false, spacing);
	}

	private static Figure createHorizontalFigure(final int spacing) {
		return createFigure(true, spacing);
	}

	private static Figure createFigure(final boolean horizontal, final int spacing) {
		final Figure figure = new Figure();
		final ToolbarLayout layout = new ToolbarLayout(horizontal);
		layout.setSpacing(spacing);
		figure.setLayoutManager(layout);
		return figure;
	}

	private void changeIgnoreLinkedLibraries() {
		changeFeature(getElement(), QueryModelHelper.FEATURE_IGNORE_LINKED_LIBRARIES,
				Boolean.valueOf(ignoreLinkedLibrariesToggle.isSelected()));
	}

	private void changeScope(final boolean subappHierarchy) {
		changeFeature(getElement(), QueryModelHelper.FEATURE_SUBAPP_HIERARCHY, Boolean.valueOf(subappHierarchy));
		refresh();
	}
}
