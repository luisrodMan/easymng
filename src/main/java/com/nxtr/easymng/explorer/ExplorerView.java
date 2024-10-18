package com.nxtr.easymng.explorer;

import java.io.IOException;
import java.util.List;

import com.ngeneration.furthergui.FComponent;
import com.ngeneration.furthergui.FPanel;
import com.ngeneration.furthergui.FPopupMenu;
import com.ngeneration.furthergui.FScrollPane;
import com.ngeneration.furthergui.FTree;
import com.ngeneration.furthergui.TreeCellRenderer;
import com.ngeneration.furthergui.TreePath;
import com.ngeneration.furthergui.event.MouseAdapter;
import com.ngeneration.furthergui.event.MouseEvent;
import com.ngeneration.furthergui.layout.BorderLayout;
import com.nxtr.easymng.Application;
import com.nxtr.easymng.ApplicationListener;
import com.nxtr.easymng.ViewAdapter;
import com.nxtr.easymng.util.ContextMenuUtil;
import com.nxtr.easymng.util.ContextMenuUtil.ContextMenuDef;
import com.nxtr.easymng.workspace.IWorkspace;
import com.nxtr.easymng.workspace.WorkspaceInfo;

public abstract class ExplorerView extends ViewAdapter {

	private FPanel container = new FPanel(new BorderLayout());
	private FTree tree;
	private ExplorerItemUnit root;

	ExplorerItemListener itemListener = new ExplorerItemListener() {
		@Override
		public void onItemsRemoved(List<IExplorerItemUnit> path, List<IExplorerItemUnit> units) {
			tree.revalidate();
		}

		@Override
		public void onItemsAdded(List<IExplorerItemUnit> path, int index, List<IExplorerItemUnit> units) {
			tree.revalidate();
		}

		@Override
		public void onExpand(List<IExplorerItemUnit> path) {
		}

		@Override
		public void onCollapse(List<IExplorerItemUnit> path) {
		}
	};
	private ExplorerCellRenderer cellRenderer = new DefaultExplorerCellRenderer();
	private TreeCellRenderer treeRenderer = new TreeCellRenderer() {
		@Override
		public FComponent getTreeCellRendererComponent(FTree tree, Object value, boolean selected, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			return cellRenderer.getExplorerCellRendererComponent(ExplorerView.this, (ExplorerItemUnit) value, selected,
					expanded, leaf, row, hasFocus);
		}
	};

	public ExplorerView(Application application) {

		application.addApplicationListener(new ApplicationListener() {

			@Override
			public void onWorkspaceChanged(Application application, WorkspaceInfo oldWorkspace) {
				setupCurrentWorkspace(application);
			}

		});
		setupCurrentWorkspace(application);
	}

	public List<IExplorerItemUnit> getSelected() {
		return List.of(tree.getSelectionPaths()).stream().map(TreePath::getLastPathComponent)
				.map(IExplorerItemUnit.class::cast).toList();
	}

	private void setupCurrentWorkspace(Application application) {
		container.removeAll();
		if (application.getWorkspace() != null) {
			tree = new FTree(root = setupWorkspace(application.getWorkspace()));
			tree.setCellRenderer(treeRenderer);
			tree.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent event) {
					if (event.getClickCount() == 2 && event.getButton() == MouseEvent.BUTTON1) {
						var node = tree.viewToModel(event.getX(), event.getY());
						if (node != null) {
							application.getViewManager().addViewAndSetActive(((ExplorerItemUnit) node).getModel());
						}
					}
				}
			});
//			tree.setRooVisible(false);

			container.add(new FScrollPane(tree));
			root.addListener(itemListener);
			FPopupMenu popupMenu = new FPopupMenu();
			tree.setComponentPopupMenu(popupMenu);
			try {
				ContextMenuDef menuDef = ContextMenuUtil.setup(tree.getPopupMenu(),
						application.getClass().getResourceAsStream("/context-menu.json"), application.getActions());
				tree.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent event) {
						if (event.getButton() == MouseEvent.BUTTON2) {
							event.consume();
							var paths = tree.getSelectionPaths();
							if (paths != null && paths.length > 0) {
								tree.getPopupMenu().getItems().stream().forEach(item -> {
									var found = menuDef.getItems().stream()
											.filter(f -> f.getText().equalsIgnoreCase(item.getText())).findAny();
									if (found.isPresent()) {
										found.get().getFilters().forEach(System.out::println);

										found.get().getFilters().forEach(f -> {
											var datas = f.split("\\s+");
											var filter = datas[1];
											var tags = List.of(paths).stream().flatMap(p -> List
													.of(((ExplorerItemUnit) p.getEnd()).getTags().split("\\s*,\\s*"))
													.stream());
											switch (datas[0].toLowerCase()) {
											case "one":
												item.setEnabled(
														paths.length == 1 && tags.anyMatch(t -> t.matches(filter)));
												break;
											case "all":
												item.setEnabled(tags.allMatch(t -> t.matches(filter)));
												break;
											}
										});
									}
								});

								tree.getPopupMenu().showVisible(tree, event.getX(), event.getY());
							}
						}
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public abstract ExplorerItemUnit setupWorkspace(IWorkspace iWorkspace);

	@Override
	public String getTitle() {
		return "Explorer";
	}

	@Override
	public FComponent getComponent() {
		return container;
	}

	public ExplorerCellRenderer getCellRenderer() {
		return cellRenderer;
	}

	public void setCellRenderer(ExplorerCellRenderer renderer) {
		this.cellRenderer = renderer;
	}

	FTree getTree() {
		return tree;
	}

}
