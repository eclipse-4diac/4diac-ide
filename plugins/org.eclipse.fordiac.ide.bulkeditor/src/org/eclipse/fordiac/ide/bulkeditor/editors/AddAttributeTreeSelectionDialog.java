/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.bulkeditor.editors;

import java.util.Collection;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.search.types.ISearchChildrenProvider;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.widgets.DataTypeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.TypeSelectionProposalProvider;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;

public class AddAttributeTreeSelectionDialog extends CheckedTreeSelectionDialog {

	private final String attributeDeclName;
	private final IProject project;
	private final Set<Object> disabledElements;

	private Text nameText = null;
	private Text typeText = null;
	private Text commentText = null;
	private Text valueText = null;

	private String name = null;
	private String type = null;
	private String comment = ""; //$NON-NLS-1$
	private String value = ""; //$NON-NLS-1$

	public AddAttributeTreeSelectionDialog(final Shell shell, final Collection<LibraryElement> initial,
			final ISearchChildrenProvider childProv, final String attributeDeclName, final IProject project,
			final Set<Object> disabledElements) {
		super(shell, new DisabledElementsLabelProvider(disabledElements),
				new AttributeTreeContentProvider(childProv, attributeDeclName, disabledElements));
		this.setInput(initial.toArray(LibraryElement[]::new));
		this.attributeDeclName = attributeDeclName;
		this.project = project;
		this.disabledElements = disabledElements;
	}

	@Override
	protected CheckboxTreeViewer createTreeViewer(final Composite parent) {
		final CheckboxTreeViewer treeViewer = super.createTreeViewer(parent);
		treeViewer.addCheckStateListener(event -> {
			if (disabledElements.contains(event.getElement())) {
				treeViewer.setChecked(event.getElement(), !event.getChecked());
			}
		});
		return treeViewer;
	}

	@Override
	protected void computeResult() {
		super.computeResult();
		if (nameText != null) {
			name = nameText.getText();
		}
		if (typeText != null) {
			type = typeText.getText();
		}

		comment = commentText.getText();
		value = valueText.getText();
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite composite = (Composite) super.createDialogArea(parent);

		final Composite attributeComposite = WidgetFactory.composite(SWT.NONE).layout(new GridLayout(2, false))
				.layoutData(new GridData(SWT.FILL, SWT.CENTER, true, false)).create(composite);

		if (attributeDeclName != null) {
			WidgetFactory.label(0).text(FordiacMessages.Name).create(attributeComposite);
			WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.CENTER, true, true)).enabled(false)
					.text(attributeDeclName).create(attributeComposite);
		} else {
			WidgetFactory.label(0).text(FordiacMessages.Name).create(attributeComposite);
			nameText = WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.CENTER, true, true))
					.create(attributeComposite);

			WidgetFactory.label(0).text(FordiacMessages.Type).create(attributeComposite);
			typeText = WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.CENTER, true, true))
					.create(attributeComposite);

			final IContentProposalProvider proposalProvider = new TypeSelectionProposalProvider(
					() -> TypeLibraryManager.INSTANCE.getTypeLibrary(project),
					DataTypeSelectionContentProvider.INSTANCE);
			final ContentProposalAdapter adapter = new ContentProposalAdapter(typeText, new TextContentAdapter(),
					proposalProvider, KeyStroke.getInstance(SWT.CTRL, SWT.SPACE),
					NatTableWidgetFactory.getActivationChars());
			adapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		}
		WidgetFactory.label(0).text(FordiacMessages.Comment).create(attributeComposite);
		commentText = WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.CENTER, true, true))
				.create(attributeComposite);

		WidgetFactory.label(0).text(FordiacMessages.InitialValue).create(attributeComposite);
		valueText = WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.CENTER, true, true))
				.create(attributeComposite);
		return composite;
	}

	public String getAttributeName() {
		return name;
	}

	public String getAttributeType() {
		return type;
	}

	public String getAttributeComment() {
		return comment;
	}

	public String getAttributeValue() {
		return value;
	}

	private static class DisabledElementsLabelProvider extends LabelProvider implements IColorProvider {
		public final Set<Object> disabledElements;

		public DisabledElementsLabelProvider(final Set<Object> disabledElements) {
			this.disabledElements = disabledElements;
		}

		@Override
		public String getText(final Object element) {
			if (element instanceof final INamedElement namedElement) {
				return namedElement.getName();
			}
			if (element instanceof final Connection conn) {
				return conn.getSource().getName() + " -> " + conn.getDestination().getName(); //$NON-NLS-1$
			}
			return element.toString();
		}

		@Override
		public Image getImage(final Object element) {
			return switch (element) {
			case final UntypedSubApp o -> FordiacImage.ICON_SUB_APP.getImage();
			case final TypedSubApp o -> FordiacImage.ICON_SUB_APP_TYPE.getImage();
			case final SubAppType o -> FordiacImage.ICON_SUB_APP_TYPE.getImage();
			case final FunctionFBType o -> FordiacImage.ICON_FUNCTION.getImage();
			case final StructManipulator o -> FordiacImage.ICON_SIFB.getImage();
			case final IInterfaceElement o -> FordiacImage.ICON_INTERFACE_LIST.getImage();
			case final Connection o -> FordiacImage.ICON_HIDE_DATA.getImage();
			case final AutomationSystem o -> FordiacImage.ICON_SYSTEM.getImage();
			case final Application o -> FordiacImage.ICON_APPLICATION.getImage();
			case final CFBInstance o -> FordiacImage.ICON_COMPOSITE_FB.getImage();
			case final FB o -> FordiacImage.ICON_FB.getImage();
			case final SimpleFBType o -> FordiacImage.ICON_SIMPLE_FB.getImage();
			case final BasicFBType o -> FordiacImage.ICON_BASIC_FB.getImage();
			case final FBType o -> FordiacImage.ICON_FB_TYPE.getImage();
			case final AttributeDeclaration o -> FordiacImage.ICON_ATTRIBUTE_DECLARATION.getImage();
			case final DataType o -> FordiacImage.ICON_DATA_TYPE.getImage();
			default -> super.getImage(element);
			};
		}

		@Override
		public Color getForeground(final Object element) {
			if (disabledElements.contains(element)) {
				return Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
			}
			return null;
		}

		@Override
		public Color getBackground(final Object element) {
			return null;
		}
	}

	private static class AttributeTreeContentProvider implements ITreeContentProvider {
		private final ISearchChildrenProvider childProv;
		private final String attributeDeclName;
		private final Set<Object> disabledElements;

		public AttributeTreeContentProvider(final ISearchChildrenProvider childProv, final String attributeDeclName,
				final Set<Object> disabledElements) {
			this.childProv = childProv;
			this.attributeDeclName = attributeDeclName;
			this.disabledElements = disabledElements;
		}

		@Override
		public boolean hasChildren(final Object element) {
			if (element instanceof DirectlyDerivedType) {
				return false;
			}
			if (element instanceof IInterfaceElement) {
				return false;
			}
			if (element instanceof final UntypedSubApp untypedSubApp
					&& untypedSubApp.getSubAppNetwork().getNetworkElements().isEmpty()
					&& untypedSubApp.getInterface().getAllInterfaceElements().findAny().isEmpty()) {
				return false;
			}
			if (element instanceof final EObject eObject) {
				return childProv.hasChildren(eObject);
			}
			return false;
		}

		@Override
		public Object getParent(final Object element) {
			return switch (element) {
			case final EObject eobj -> {
				EObject current = eobj.eContainer();
				while (!(current instanceof ConfigurableObject)) {
					current = current.eContainer();
				}
				yield current;
			}
			default -> null;
			};
		}

		@Override
		public Object[] getElements(final Object inputElement) {
			for (final Object object : (Object[]) inputElement) {
				if (object instanceof final ConfigurableObject configElement) {
					configElement.getAttributes().stream().filter(asd -> asd.getAttributeDeclaration() != null)
							.forEach(asdfasdfsadf -> {
								final AttributeDeclaration attdecl = asdfasdfsadf.getAttributeDeclaration();
								if (PackageNameHelper.getFullTypeName(attdecl).equals(attributeDeclName)) {
									disabledElements.add(object);
								}
							});
				} else {
					disabledElements.add(object);
				}
			}
			return (Object[]) inputElement;
		}

		@Override
		public Object[] getChildren(final Object parentElement) {
			if (parentElement instanceof final EObject eObject) {
				final var configElements = childProv.getChildren(eObject).filter(ConfigurableObject.class::isInstance)
						.toArray(ConfigurableObject[]::new);

				for (final ConfigurableObject configElement : configElements) {
					configElement.getAttributes().stream().filter(asd -> asd.getAttributeDeclaration() != null)
							.forEach(asdfasdfsadf -> {
								final AttributeDeclaration attdecl = asdfasdfsadf.getAttributeDeclaration();
								if (PackageNameHelper.getFullTypeName(attdecl).equals(attributeDeclName)) {
									disabledElements.add(configElement);
								}
							});
				}

				return configElements;
			}
			return new Object[0];
		}
	}
}
