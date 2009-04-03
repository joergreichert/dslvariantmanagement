package org.openarchitectureware.demo.archdsl.language.editor;

import org.openarchitectureware.demo.archdsl.language.AdslEditorPlugin;
import org.openarchitectureware.xtext.AbstractXtextEditorPlugin;
import org.openarchitectureware.xtext.editor.AbstractXtextEditor;



public class AdslEditor extends AbstractXtextEditor {

   public AbstractXtextEditorPlugin getPlugin() {
      return AdslEditorPlugin.getDefault();
   }
}
