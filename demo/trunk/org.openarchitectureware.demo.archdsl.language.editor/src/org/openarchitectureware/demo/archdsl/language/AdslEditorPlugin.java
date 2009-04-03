package org.openarchitectureware.demo.archdsl.language;

import org.openarchitectureware.demo.archdsl.language.AdslUtilities;
import org.openarchitectureware.xtext.AbstractXtextEditorPlugin;
import org.openarchitectureware.xtext.LanguageUtilities;
import org.osgi.framework.BundleContext;


public class AdslEditorPlugin extends AbstractXtextEditorPlugin {
   private static AdslEditorPlugin plugin;
   public static AdslEditorPlugin getDefault() {
      return plugin;
   }

   private AdslUtilities utilities = new AdslUtilities();
   public LanguageUtilities getUtilities() {
      return utilities;
   }

   public AdslEditorPlugin() {
      plugin = this;
   }

   public void stop(BundleContext context) throws Exception {
      super.stop(context);
      plugin = null;
   }
}
