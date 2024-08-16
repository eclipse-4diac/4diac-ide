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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.gef.filters.AttributeFilter;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class InfoPropertySection extends AbstractSection {

	private ConfigurableObject obj;
	private boolean checked = false;

	private final static int horizontalIndent = 10;

	private Composite parent;

	private Button checkBox;

	private Label numbConVal;
	private Label usedTypesVal;
	private Label subAppsVal;
	private Label instancesVal;

	private Group fbGroup;
	private final java.util.List<Label> fbTypeLabels = new ArrayList<>();
	private final java.util.List<Label> fbCountLabels = new ArrayList<>();

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		this.parent = parent;
		final Color backgroundColor = new Color(255, 255, 255);
		parent.setBackground(backgroundColor);

		final GridLayout parentLayout = new GridLayout(2, true);
		parent.setLayout(parentLayout);

		final Group group = createGroup(parent, "System Information", backgroundColor); //$NON-NLS-1$

		numbConVal = createLabelPair(group, "Number of Connections:", backgroundColor); //$NON-NLS-1$
		usedTypesVal = createLabelPair(group, "Number of used Types:", backgroundColor); //$NON-NLS-1$
		subAppsVal = createLabelPair(group, "Number of untyped Subapps:", backgroundColor); //$NON-NLS-1$
		instancesVal = createLabelPair(group, "Number of all instances:", backgroundColor); //$NON-NLS-1$

		final Label checkLabl = createLabel(group, "Count instances of typed Subapps/CFB Networks", backgroundColor, //$NON-NLS-1$
				false);
		checkBox = createCheckBox(group);

		fbGroup = createGroup(parent, "FB Types and Counts", backgroundColor); //$NON-NLS-1$
		fbGroup.setVisible(false);
	}

	private Button createCheckBox(final Group g) {
		final Button check = new Button(g, SWT.CHECK);

		final GridData checkGridData = new GridData(SWT.LEFT, SWT.TOP, false, false);
		checkGridData.horizontalIndent = horizontalIndent;
		check.setLayoutData(checkGridData);

		check.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (check.getSelection()) {
					checked = true;
				} else {
					checked = false;
				}
				setInputInit();
			}
		});
		return check;
	}

	private static Group createGroup(final Composite parent, final String string, final Color backgroundColor) {
		final Group group = new Group(parent, SWT.NONE);
		group.setText(string);
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true));
		group.setBackground(backgroundColor);
		return group;
	}

	private static Label createLabelPair(final Composite parent, final String string, final Color backgroundColor) {
		createLabel(parent, string, backgroundColor, true);
		return createValueLabel(parent, backgroundColor);
	}

	private static Label createValueLabel(final Composite parent, final Color c) {
		final Label label = new Label(parent, SWT.NONE);
		final GridData gridData = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		label.setLayoutData(gridData);
		gridData.horizontalIndent = horizontalIndent;
		label.setBackground(c);
		return label;
	}

	private static Label createLabel(final Composite parent, final String text, final Color c, final boolean bold) {
		final Label label = new Label(parent, SWT.NONE);
		label.setBackground(c);
		label.setText(text);
		if (bold) {
			final FontData[] fontData = label.getFont().getFontData();
			for (final FontData fd : fontData) {
				fd.setStyle(SWT.BOLD);
			}
			label.setFont(new Font(parent.getDisplay(), fontData));
		}
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
		HashMap<String, Integer> fbs = new HashMap<>();
		final int types;
		final int subapps;
		final int instanceCount;
		final int noc;

		if (obj instanceof final SubApp subApp) {
			final FBNetwork network = subApp.getSubAppNetwork();
			noc = numbOfCon(network, fbs);
			fbs = countFBInstances(network.getNetworkElements(), fbs);
			subapps = processSubappElements(network.getNetworkElements(), fbs);
		} else if (obj instanceof final Application application) {
			final FBNetwork network = application.getFBNetwork();
			noc = numbOfCon(network, fbs);
			fbs = countFBInstances(network.getNetworkElements(), fbs);
			subapps = processSubappElements(network.getNetworkElements(), fbs);
		} else {
			throw new IllegalStateException("Unexpected object type: " + obj.getClass().getName()); //$NON-NLS-1$
		}
		instanceCount = fbs.values().stream().mapToInt(Integer::intValue).sum();
		types = fbs.size();

		updateLabels(noc, types, fbs, subapps, instanceCount);
	}

	@SuppressWarnings("boxing")
	private HashMap<String, Integer> countFBInstances(final Iterable<FBNetworkElement> networkElements,
			final HashMap<String, Integer> fbs) {
		for (final FBNetworkElement fe : networkElements) {
			if (null != fe.getType()) {
				fbs.merge(fe.getTypeName(), 1, Integer::sum);
			}
			if (fe instanceof final UntypedSubApp sa) {
				if (sa.getSubAppNetwork() != null) {
					countFBInstances(sa.getSubAppNetwork().getNetworkElements(), fbs);
				}
			} else if (fe instanceof final TypedSubApp sb && checked) {
				if (sb.getSubAppNetwork() == null) {
					sb.loadSubAppNetwork();
				}
				countFBInstances(sb.getSubAppNetwork().getNetworkElements(), fbs);
			}
		}
		return fbs;
	}

	private int numbOfCon(final FBNetwork fbNetwork, final HashMap<String, Integer> fbs) {
		int con = 0;
		if (fbNetwork != null) {
			con += fbNetwork.getAdapterConnections().size() + fbNetwork.getDataConnections().size()
					+ fbNetwork.getEventConnections().size();
			for (final FBNetworkElement fe : fbNetwork.getNetworkElements()) {
				if (fe instanceof final UntypedSubApp sa) {
					con += numbOfCon(sa.getSubAppNetwork(), fbs);
				} else if (fe instanceof final UntypedSubApp sa && checked) {
					con += numbOfCon(sa.getSubAppNetwork(), fbs);
				}
			}
		}
		return con;
	}

	private int processSubappElements(final Iterable<FBNetworkElement> elements, final Map<String, Integer> fbs) {
		int subapps = 0;
		for (final FBNetworkElement fe : elements) {
			if (fe instanceof final UntypedSubApp se) {
				subapps++;
				subapps += processSubappElements(se.getSubAppNetwork().getNetworkElements(), fbs);
			} else if (fe instanceof final TypedSubApp se && checked) {
				subapps++;
				subapps += processSubappElements(se.loadSubAppNetwork().getNetworkElements(), fbs);
			}
		}
		return subapps;
	}

	private void updateLabels(final int noc, final int types, final HashMap<String, Integer> fbs, final int subapps,
			final int instanceCount) {
		numbConVal.setText(String.valueOf(noc));
		usedTypesVal.setText(String.valueOf(types));
		subAppsVal.setText(String.valueOf(subapps));
		instancesVal.setText(String.valueOf(instanceCount));

		parent.setRedraw(false);

		resetLabel(fbTypeLabels);
		resetLabel(fbCountLabels);

		if (!fbs.isEmpty()) {
			fbGroup.setVisible(true);
			for (final Entry<String, Integer> entry : fbs.entrySet()) {
				final Label fbTypeLabel = createLabel(fbGroup, entry.getKey() + ":", parent.getBackground(), true); //$NON-NLS-1$
				fbTypeLabels.add(fbTypeLabel);

				final Label fbCountLabel = createValueLabel(fbGroup, parent.getBackground());
				fbCountLabel.setText(String.valueOf(entry.getValue()));
				fbCountLabels.add(fbCountLabel);
			}
		} else {
			fbGroup.setVisible(false);
		}

		parent.setRedraw(true);
		parent.layout(true, true);
	}

	private static void resetLabel(final List<Label> list) {
		for (final Label l : list) {
			l.dispose();
		}
		list.clear();
	}
}
