package com.nxtr.easymng;

import java.util.Stack;

import com.ngeneration.furthergui.text.FTextComponent;

public class UndoRedoManager {

	private Stack<Edit> undo = new Stack<>();
	private Stack<Edit> redo = new Stack<>();
	private Edit lastEdit;
	private boolean cancel;

	public Edit getLastEdit() {
		return lastEdit;
	}

	public void addEdit(Edit edit) {
		if (cancel)
			return;
		if (lastEdit != null && lastEdit.merge(edit))
			return;
		this.lastEdit = edit;
		redo.clear();
		undo.add(edit);
	}

	public void undo() {
		lastEdit = null;
		Edit edit = undo.pop();
		cancel = true;
		edit.undo();
		cancel = false;
		redo.push(edit);
	}

	public void redo() {
		lastEdit = null;
		Edit edit = redo.pop();
		cancel = true;
		edit.redo();
		cancel = false;
		undo.push(edit);
	}

	public void clear() {
		undo.clear();
		redo.clear();
	}

	public boolean canUndo() {
		return !undo.isEmpty();
	}

	public boolean canRedo() {
		return !redo.isEmpty();
	}

	public static interface Edit {
		void undo();

		void redo();

		boolean merge(Edit edit);
	}

	public static class TextEdit implements Edit {

		private int from;
		private String text;
		public static int INSERT = 1;
		public static int DELETE = 2;
		public static int mode = INSERT;

		private FTextComponent editor;

		public TextEdit(FTextComponent component, int mode, int offset, String text) {
			this.mode = mode;
			this.from = offset;
			this.text = text;
			this.editor = component;
		}

		@Override
		public void undo() {
			execute(true);
		}

		@Override
		public void redo() {
			execute(false);
		}

		private void execute(boolean undo) {
			try {
				if ((undo && mode == INSERT) || (!undo && mode == DELETE)) {
					editor.setCaretPosition(from);
					editor.remove(from, text.length());
				} else {
					editor.insertString(from, text, null);
					editor.setCaretPosition(from + text.length());
					if (undo && mode == DELETE) {
						editor.setSelectionStart(from);
						editor.setSelectionEnd(from + text.length());
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public boolean merge(Edit edit) {
			boolean merged = false;
			if (edit instanceof TextEdit) {
				TextEdit newEdit = (TextEdit) edit;
				if (merged = (mode == INSERT && newEdit.mode == INSERT && newEdit.from == from + text.length()))
					text += newEdit.text;
				else if (merged = (mode == DELETE && newEdit.mode == DELETE
						&& newEdit.from + newEdit.text.length() == from)) {
					from = newEdit.from;
					text = newEdit.text + text;
				}
			}
			return merged;
		}

	}

	public static class TextEditReplace implements Edit {

		private FTextComponent editor;
		private int offset;
		private String text;
		private String replace;

		public TextEditReplace(FTextComponent editor, int offset, String text, String replace) {
			this.editor = editor;
			this.offset = offset;
			this.text = text;
			this.replace = replace;
		}

		@Override
		public void undo() {
			try {
				editor.remove(offset, replace.length());
				editor.insertString(offset, text, null);
				editor.setSelectionStart(offset);
				editor.setSelectionEnd(offset + text.length());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void redo() {
			try {
				editor.remove(offset, text.length());
				editor.insertString(offset, replace, null);
				editor.setCaretPosition(offset + replace.length());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public boolean merge(Edit edit) {
			return false;
		}

	}

}
