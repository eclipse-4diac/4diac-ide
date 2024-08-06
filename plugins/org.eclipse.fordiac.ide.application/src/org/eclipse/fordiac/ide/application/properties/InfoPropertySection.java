/*******************************************************************************
 * Copyright (c) 2024 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Stemmer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.gef.filters.AttributeFilter;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class InfoPropertySection extends AbstractSection {

	private ConfigurableObject obj;
	private boolean isSubApp = false;

	private Composite parent;

	private Label numbConVal;
	private Label usedTypesVal;
	private Label subAppsVal;
	private Label skillFBVal;
	private Label instancesVal;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		this.parent = parent;
		final Color backgroundColor = new Color(255, 255, 255);

		parent.setBackground(backgroundColor);
		final Group group = new Group(parent, SWT.NONE);
		group.setText("System Information"); //$NON-NLS-1$
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		group.setBackground(backgroundColor);

		createLabel(group, "Number of Connections:", backgroundColor); //$NON-NLS-1$
		numbConVal = createValueLabel(group, backgroundColor);

		createLabel(group, "Number of used Types:", backgroundColor); //$NON-NLS-1$
		usedTypesVal = createValueLabel(group, backgroundColor);

		createLabel(group, "Number of untyped Subapps:", backgroundColor); //$NON-NLS-1$
		subAppsVal = createValueLabel(group, backgroundColor);

		createLabel(group, "Number of SkillFB instances:", backgroundColor); //$NON-NLS-1$
		skillFBVal = createValueLabel(group, backgroundColor);

		createLabel(group, "Number of all instances:", backgroundColor); //$NON-NLS-1$
		instancesVal = createValueLabel(group, backgroundColor);
	}

	private static Label createValueLabel(final Composite parent, final Color c) {
		final Label label = new Label(parent, SWT.NONE);
		final GridData gridData = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		label.setLayoutData(gridData);
		gridData.widthHint = 50;
		gridData.horizontalIndent = 20;
		label.setBackground(c);
		return label;
	}

	private static Label createLabel(final Composite parent, final String text, final Color c) {
		final Label label = new Label(parent, SWT.NONE);
		label.setBackground(c);
		label.setText(text);
		final FontData[] fontData = label.getFont().getFontData();
		for (final FontData fd : fontData) {
			fd.setStyle(SWT.BOLD);
		}
		label.setFont(new Font(parent.getDisplay(), fontData));
		return label;
	}

	@Override
	protected EObject getType() {
		return type instanceof final ConfigurableObject configurableObject ? configurableObject : null;

	}

	@Override
	protected Object getInputType(final Object input) {
		final EObject parsedObject = (EObject) AttributeFilter.parseObject(input);
		if (parsedObject instanceof final SubApp configurableObject) {
			isSubApp = true;
			obj = configurableObject;
			return configurableObject;
		}
		if (parsedObject instanceof final Application configurableObject2) {
			obj = configurableObject2;
			return configurableObject2;
		}
		return null;
	}

	@Override
	protected void setInputCode() {
		// DO NOTHING
	}

	@Override
	protected void setInputInit() {
		formatPage();
	}

	@Override
	protected void performRefresh() {
		// DO NOTHING
	}

	private void formatPage() {
		final HashMap<String, Integer> fbs = new HashMap<>();
		int eventConnections;
		int dataConnections;
		int adapterConnections;
		int types;
		int subapps;
		int instanceCount;
		int skillFBCount;

		if (isSubApp) {
			final SubApp subApp = (SubApp) obj;
			eventConnections = subApp.getSubAppNetwork().getEventConnections().size();
			dataConnections = subApp.getSubAppNetwork().getDataConnections().size();
			adapterConnections = subApp.getSubAppNetwork().getAdapterConnections().size();

			skillFBCount = countSkillFBInstances(subApp.getSubAppNetwork().getNetworkElements());

			processFBS(subApp.getSubAppNetwork().getNetworkElements(), fbs);
			subapps = processSubappElements(subApp.getSubAppNetwork().getNetworkElements(), fbs);

			isSubApp = false;
		} else {
			final Application application = (Application) obj;
			eventConnections = application.getFBNetwork().getEventConnections().size();
			dataConnections = application.getFBNetwork().getDataConnections().size();
			adapterConnections = application.getFBNetwork().getAdapterConnections().size();

			skillFBCount = countSkillFBInstances(application.getFBNetwork().getNetworkElements());

			processFBS(application.getFBNetwork().getNetworkElements(), fbs);
			subapps = processSubappElements(application.getFBNetwork().getNetworkElements(), fbs);
		}

		instanceCount = fbs.values().stream().mapToInt(Integer::intValue).sum() + subapps;
		types = fbs.size();
		final int noc = eventConnections + dataConnections + adapterConnections;

		updateLabels(noc, types, skillFBCount, subapps, instanceCount);
	}

	private static int countSkillFBInstances(final Iterable<FBNetworkElement> networkElements) {
		int skillFBCount = 0;
		for (final FBNetworkElement fe : networkElements) {
			if (fe.getType() instanceof SimpleFBType || fe.getType() instanceof SubAppType
					|| fe.getType() instanceof CompositeFBType || fe.getType() instanceof BasicFBType) {
				skillFBCount++;
			}
		}
		return skillFBCount;
	}

	private static int processSubappElements(final Iterable<FBNetworkElement> elements,
			final Map<String, Integer> fbs) {
		int subapps = 0;
		for (final FBNetworkElement fe : elements) {
			if (fe instanceof final SubApp se && isUntypedSubapp(se)) {
				subapps++;
			}
		}
		return subapps;
	}

	@SuppressWarnings("boxing")
	private static void processFBS(final Iterable<FBNetworkElement> elements, final Map<String, Integer> fbs) {
		for (final FBNetworkElement fe : elements) {
			if (fe.getType() != null) {
				fbs.merge(fe.getTypeName(), 1, Integer::sum);
			}
		}
	}

	private void updateLabels(final int noc, final int types, final int skillfb, final int subapps,
			final int instanceCount) {
		numbConVal.setText(String.valueOf(noc));
		usedTypesVal.setText(String.valueOf(types));
		subAppsVal.setText(String.valueOf(subapps));
		skillFBVal.setText(String.valueOf(skillfb));
		instancesVal.setText(String.valueOf(instanceCount));

		parent.layout(true, true);
	}

	private static boolean isUntypedSubapp(final SubApp subapp) {
		return !(subapp.isTyped() || subapp.isContainedInTypedInstance());
	}
}
