/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl     - initial API and implementation and/or initial documentation
 *   Prankur Agarwal - update the figure to look like a sticky note
 *   Alois Zoitl     - improved and modernized comment figure drawing
 *                   - reworked background color handling
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.application.figures.InstanceCommentFigure;
import org.eclipse.fordiac.ide.gef.editparts.AbstractPositionableElementEditPart;
import org.eclipse.fordiac.ide.gef.editparts.FigureCellEditorLocator;
import org.eclipse.fordiac.ide.gef.editparts.TextDirectEditManager;
import org.eclipse.fordiac.ide.gef.policies.AbstractViewRenameEditPolicy;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Comment;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

public class CommentEditPart extends AbstractPositionableElementEditPart {

	private static final int DOG_EAR_SIZE = 13;

	public static final class StickyNoteCommentFigure extends Figure {

		private static final int[] STICKY_NOTE_SHAPE_POINTS = new int[10];
		private static final int[] STICKY_NOTE_DOG_EAR_POINTS = new int[6];
		private static Color borderColor;
		private final TextFlow textFlow;

		public StickyNoteCommentFigure() {
			setupFigure();
			setupRootLayout();

			textFlow = new TextFlow();
			textFlow.setLayoutManager(new ParagraphTextLayout(textFlow, ParagraphTextLayout.WORD_WRAP_SOFT));

			final FlowPage flowPage = new FlowPage();
			flowPage.setCursor(Cursors.SIZEALL);
			flowPage.add(textFlow);

			add(flowPage, new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
					| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL));
		}

		private void setupFigure() {
			setOpaque(true);
			setBackgroundColor(UIPreferenceConstants.getStickyNoteBGColor());
			setForegroundColor(UIPreferenceConstants.getStickyNoteFGColor());
		}

		private void setupRootLayout() {
			final GridLayout mainLayout = new GridLayout(1, true);
			mainLayout.marginHeight = 0;
			mainLayout.verticalSpacing = 0;
			mainLayout.horizontalSpacing = 0;
			setLayoutManager(mainLayout);
		}

		public void setCommentText(final String newCommentTest) {
			textFlow.setText(newCommentTest);
		}

		@Override
		public void paintFigure(final Graphics g) {
			final Rectangle r = getBounds();

			updateStickNoteShapePoints(r);
			g.fillPolygon(STICKY_NOTE_SHAPE_POINTS);

			updateDogEarPoints(r);
			g.setBackgroundColor(getBorderBolor());
			g.fillPolygon(STICKY_NOTE_DOG_EAR_POINTS);

			g.setForegroundColor(getBorderBolor());
			g.drawPolygon(STICKY_NOTE_SHAPE_POINTS);
		}

		private static void updateStickNoteShapePoints(final Rectangle r) {
			STICKY_NOTE_SHAPE_POINTS[0] = r.x;
			STICKY_NOTE_SHAPE_POINTS[1] = r.y;

			STICKY_NOTE_SHAPE_POINTS[2] = r.x + r.width - DOG_EAR_SIZE - 1;
			STICKY_NOTE_SHAPE_POINTS[3] = r.y;

			STICKY_NOTE_SHAPE_POINTS[4] = r.x + r.width - 1;
			STICKY_NOTE_SHAPE_POINTS[5] = r.y + DOG_EAR_SIZE;

			STICKY_NOTE_SHAPE_POINTS[6] = r.x + r.width - 1;
			STICKY_NOTE_SHAPE_POINTS[7] = r.y + r.height - 1;

			STICKY_NOTE_SHAPE_POINTS[8] = r.x;
			STICKY_NOTE_SHAPE_POINTS[9] = r.y + r.height - 1;
		}

		private static void updateDogEarPoints(final Rectangle r) {
			STICKY_NOTE_DOG_EAR_POINTS[0] = r.x + r.width - DOG_EAR_SIZE - 1;
			STICKY_NOTE_DOG_EAR_POINTS[1] = r.y;

			STICKY_NOTE_DOG_EAR_POINTS[2] = r.x + r.width - DOG_EAR_SIZE - 1;
			STICKY_NOTE_DOG_EAR_POINTS[3] = r.y + DOG_EAR_SIZE;

			STICKY_NOTE_DOG_EAR_POINTS[4] = r.x + r.width - 1;
			STICKY_NOTE_DOG_EAR_POINTS[5] = r.y + DOG_EAR_SIZE;
		}

		private static Color getBorderBolor() {
			if (borderColor == null) {
				borderColor = UIPreferenceConstants.getStickyNoteBorderColor();
			}
			return borderColor;
		}
	}

	private class CommentRenameEditPolicy extends AbstractViewRenameEditPolicy {
		@Override
		protected Command getDirectEditCommand(final DirectEditRequest request) {
			if (getHost() instanceof final CommentEditPart ep) {
				final String str = (String) request.getCellEditor().getValue();
				if (!InstanceCommentFigure.EMPTY_COMMENT.equals(str)) {
					return new ChangeCommentCommand(ep.getModel(), str);
				}
			}
			return null;
		}

		@Override
		protected void showCurrentEditValue(final DirectEditRequest request) {
			final String value = (String) request.getCellEditor().getValue();
			getFigure().setCommentText(value);
		}

		@Override
		protected void revertOldEditValue(final DirectEditRequest request) {
			refreshComment();
		}
	}

	@Override
	public Comment getModel() {
		return (Comment) super.getModel();
	}

	@Override
	protected PositionableElement getPositionableElement() {
		return getModel();
	}

	@Override
	public INamedElement getINamedElement() {
		return getModel();
	}

	@Override
	public Label getNameLabel() {
		// we don't have a name label in groups
		return null;
	}

	@Override
	protected void refreshName() {
		// as we don't have a name label we don't want to do anything here
	}

	@Override
	protected IFigure createFigure() {
		final StickyNoteCommentFigure mainFigure = new StickyNoteCommentFigure();
		mainFigure.setCommentText(getModel().getComment());
		return mainFigure;
	}

	@Override
	public StickyNoteCommentFigure getFigure() {
		return (StickyNoteCommentFigure) super.getFigure();
	}

	@Override
	protected void performDirectEdit() {
		new TextDirectEditManager(this, new FigureCellEditorLocator(getFigure())) {
			@Override
			protected CellEditor createCellEditorOn(final Composite composite) {
				return new TextCellEditor(composite, SWT.MULTI | SWT.WRAP);
			}

			@Override
			protected void initCellEditor() {
				super.initCellEditor();
				getCellEditor().setValue(getModel().getComment());
			}
		}.show();
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {
			@Override
			protected Command createDeleteCommand(final GroupRequest request) {
				return new DeleteFBNetworkElementCommand((Comment) getHost().getModel());
			}
		});
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new CommentRenameEditPolicy());
	}

	@Override
	protected void refreshPosition() {
		if (getParent() != null) {
			final Position position = getModel().getPosition();
			final Rectangle bounds = new Rectangle(position.toScreenPoint(), getCommentSize());
			((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
		}
	}

	@Override
	protected void refreshComment() {
		getFigure().setCommentText(getModel().getComment());
	}

	@Override
	public <T> T getAdapter(final Class<T> key) {
		if (key == Comment.class) {
			return key.cast(getModel());
		}
		return super.getAdapter(key);
	}

	private Dimension getCommentSize() {
		return new Dimension(CoordinateConverter.INSTANCE.iec61499ToScreen(getModel().getWidth()),
				CoordinateConverter.INSTANCE.iec61499ToScreen(getModel().getHeight()));
	}

	@Override
	protected Adapter createContentAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(final Notification notification) {
				super.notifyChanged(notification);
				final Object feature = notification.getFeature();
				if (LibraryElementPackage.eINSTANCE.getComment_Width().equals(feature)
						|| LibraryElementPackage.eINSTANCE.getComment_Height().equals(feature)) {
					refreshPosition();
				}
			}
		};
	}

}
