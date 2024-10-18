package com.nxtr.easymng.view;

import com.ngeneration.furthergui.FComponent;
import com.ngeneration.furthergui.graphics.Color;
import com.ngeneration.furthergui.graphics.Graphics;
import com.ngeneration.furthergui.layout.Layout;
import com.ngeneration.furthergui.math.Dimension;
import com.ngeneration.miengine.math.MathUtils;
import com.nxtr.easymng.View;

class ViewSplit extends FComponent {

	private ViewSplit left, right;
	private Object center;
	private final int HORIZONTAL = 1;
	private final static int VERTICAL = 2;
	private int orientation = HORIZONTAL;

	public ViewSplit() {
		setLayout(new InternalLayout());
	}

	public int getOrientation() {
		return orientation;
	}

	private void setRight(ViewSplit component) {
		if (right != null)
			remove(right);
		right = component;
		add(right);
	}

	private void setLeft(ViewSplit component) {
		if (left != null)
			remove(left);
		left = component;
		add(left);
	}

	private void setCenter(Object component) {
		// DefaultViewContainer?
		FComponent oldCenter = null;
		if (this.center instanceof DefaultViewContainer)
			oldCenter = ((DefaultViewContainer) this.center).getComponent();
		else
			oldCenter = (FComponent) this.center;
		if (oldCenter != null)
			remove(oldCenter);
		this.center = component;
		if (this.center != null) {
			add(this.center instanceof DefaultViewContainer ? ((DefaultViewContainer) this.center).getComponent()
					: ((FComponent) this.center));
		}
	}

	void add(String[] path, int index, View component) {
		String p = index == path.length ? "C" : path[index];
//		System.out.println("add " + p + " " + (index == path.length));
		orientation = (p.equals("T") || p.equals("B")) ? VERTICAL : HORIZONTAL;
		if (p.equals("L") || p.equals("T")) {
			if (left == null)
				setLeft(new ViewSplit());
			left.add(path, index + 1, component);
		} else if (p.equals("R") || p.equals("B")) {
			if (right == null)
				setRight(new ViewSplit());
			right.add(path, index + 1, component);
		} else if (p.equals("C")) {
			if (index == path.length) {
				if (center == null) {
					setCenter(new DefaultViewContainer());
					((DefaultViewContainer) center).addView(component);
				} else if (center instanceof DefaultViewContainer) {
					((DefaultViewContainer) center).addView(component);
				} else {
					ViewSplit old = (ViewSplit) center;
					ViewSplit newCenter = new ViewSplit();
					newCenter.setLeft(old);
					newCenter.setCenter(new ViewSplit());
					((ViewSplit) newCenter.center).setCenter(component.getComponent());
					setCenter(newCenter);
				}
			} else {
				if (center == null)
					setCenter(new ViewSplit());
				else if (center instanceof DefaultViewContainer) {
					DefaultViewContainer c = (DefaultViewContainer) center;
					setCenter(new ViewSplit());
					((ViewSplit) center).setCenter(c);
				}
				((ViewSplit) center).add(path, index + 1, component);
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	public String getPath(View v) {
		FComponent component = getContainer(v);
//		FComponent component = mng.viewMap.entrySet().stream().filter(es -> es.getValue().contains(v)).findAny()
//				.orElse(null).getKey();
		String path = "";
//		// views always on center remove center from path
		component = component.getParent();
		while (component.getParent() instanceof ViewSplit parent) {
//			ViewSplit parent = component.getParent();
//			path = 
			if (parent.getOrientation() == ViewSplit.VERTICAL)
				path = (parent.left == component ? "T" : (parent.right == component ? "B" : "C")) + path;
			else
				path = (parent.left == component ? "L" : (parent.right == component ? "R" : "C")) + path;
			component = parent;
		}
		return path;
	}

	private FComponent getContainer(View v) {
		FComponent path = null;
		if (left != null)
			path = left.getContainer(v);
		if (path == null && right != null)
			path = right.getContainer(v);
		if (path == null) {
			if (center instanceof ViewSplit)
				path = ((ViewSplit) center).getContainer(v);
			else if (center instanceof DefaultViewContainer) {
				if (((DefaultViewContainer) center).contains(v))
					path = ((DefaultViewContainer) center).getComponent();
			}
		}
		return path;
	}

	public void removeView(View view) {

	}

	private class InternalLayout implements Layout {

		private int pivot1 = -1;
		private int pivot2 = -1;
		private int pivotSize = 5;

		@Override
		public void addComponent(FComponent component, Object constraints) {
		}

		@Override
		public Dimension getPrefferedDimension(FComponent container) {
			return new Dimension();
		}

		@Override
		public void layout(FComponent container) {

			int componentCount = getComponentCount();

			if (componentCount == 0)
				return;

			if (orientation == HORIZONTAL) {
				if (componentCount == 1) {
					getFirstComponent().setBounds(0, 0, container.getWidth(), container.getHeight());
				} else if (componentCount == 2) {
					int pivot1 = getPivot1();
					getFirstComponent().setBounds(0, 0, pivot1, container.getHeight());
					getSecondComponent().setBounds(pivot1 + pivotSize, 0, container.getWidth() - pivot1 - pivotSize,
							container.getHeight());
				} else {
					int pivot1 = getPivot1();
					int pivot2 = getPivot2();
					getFirstComponent().setBounds(0, 0, pivot1, container.getHeight());
					getSecondComponent().setBounds(pivot1 + pivotSize, 0, pivot2 - pivot1 - pivotSize,
							container.getHeight());
					right.setBounds(pivot2 + pivotSize, 0, container.getWidth() - pivot2 - pivotSize,
							container.getHeight());
				}
			} else {
				if (componentCount == 1) {
					getFirstComponent().setBounds(0, 0, container.getWidth(), container.getHeight());
				} else if (componentCount == 2) {
					int pivot1 = getPivot1();
					getFirstComponent().setBounds(0, 0, container.getWidth(), pivot1);
					getSecondComponent().setBounds(0, pivot1 + pivotSize, container.getWidth(),
							container.getHeight() - pivot1 - pivotSize);
				} else {
					int pivot1 = getPivot1();
					int pivot2 = getPivot2();
					getFirstComponent().setBounds(0, 0, container.getWidth(), pivot1);
					getSecondComponent().setBounds(0, pivot1 + pivotSize, container.getWidth(),
							pivot2 - pivot1 - pivotSize);
					right.setBounds(0, pivot2 + pivotSize, container.getWidth(),
							container.getHeight() - pivot2 - pivotSize);
				}
			}

		}

		private int getComponentCount() {
			int componentCount = left != null ? 1 : 0;
			componentCount += right != null ? 1 : 0;
			componentCount += center != null ? 1 : 0;
			return componentCount;
		}

		private int getPivot1() {
			int c = getComponentCount();
			int length = orientation == HORIZONTAL ? getWidth() : getHeight();
			if (pivot1 == -1)
				return (c < 2 ? 0 : length / c);
			return c < 2 ? 0 : MathUtils.clamp(pivot1, 0, (length - (c - 1) * pivotSize));
		}

		private int getPivot2() {
			int c = getComponentCount();
			int length = orientation == HORIZONTAL ? getWidth() : getHeight();
			if (pivot2 == -1)
				return (c < 3 ? 0 : length / c * 2);
			return c < 3 ? 0 : MathUtils.clamp(pivot2, 0, length - pivotSize);
		}

		private FComponent getFirstComponent() {
			FComponent c = getCenterComponent();
			return left != null ? left : (c != null ? c : right);
		}

		private FComponent getSecondComponent() {
			FComponent c = getCenterComponent();
			return c != null ? c : right;
		}

		private FComponent getCenterComponent() {
			return center instanceof DefaultViewContainer ? ((DefaultViewContainer) center).getComponent()
					: (FComponent) center;
		}

		@Override
		public void remove(FComponent component) {

		}

	}

}
