# Migration of the PLE feature from oAW 4.3.1 to oAW 5.0 Add-Ons #

## In oAW 4.3.1 ##

### Workflow ###

#### Configuration Model Reader ####

##### EMF Configuration Model Reader #####

```
<workflow>
   ...
   <component class="org.openarchitectureware.util.featureconfig.emf.EMFConfigurationReader">
      <uri value="path/to/emf_feature_model_file.xmi" />
      <expressions value="path/to/expressions_file.ext" />
   </component>
   ...
</workflow>
```

###### EMF feature model ######
![http://dslvariantmanagement.googlecode.com/svn/wiki/images/featureconfig.png](http://dslvariantmanagement.googlecode.com/svn/wiki/images/featureconfig.png)

###### EMF configuration model ######
![http://dslvariantmanagement.googlecode.com/svn/wiki/images/featureconfig_model.png](http://dslvariantmanagement.googlecode.com/svn/wiki/images/featureconfig_model.png)

###### expressions\_file.ext ######
```
extension org::openarchitectureware::util::featureconfig::features;

boolean customFeatureSelectionMechanism() : isFeatureSelected("a") && !isFeatureSelected("b");
```

The expression file may define functions that can be used for building expressions
to be passed to functions like isFeatureSelected(...) instead only the feature name.

```
isFeatureSelected(customFeatureSelectionMechanism())
```

##### Textual Configuration #####

```
<workflow>
   ...
   <component class="org.openarchitectureware.util.featureconfig.text.TextConfigurationReader">
      <configFile value="path/to/text_file.txt" />
   </component>
   ...
</workflow>
```

###### text\_file.txt ######
```
+CollaborationSystemFeatures
+AccessManagement 
+Security
+Authentication 
+Encryption 
+AsynchronousConferencing
+SynchronousConferencing 
+DocumentRepository 
+Forum
-MicroBlogging 
+Text 
+Audio
+Video 
+Data
-TelephoneIntegration 
+VoiceOverIP 
-VNC 
+X11
```

#### Usage of Configuration Model in Workflow ####

```
<workflow>
   ...
   <component class="*Reader">
      ...
   </component>

   <feature isSelected="...">
      <component class="...">
         ...
      </component>
   </feature>
   ...
</workflow>
```

### Template ###

```
«IMPORT ...»
«EXTENSION org::openarchitectureware::util::featureconfig::features»

«DEFINE ... FOR ...»
   ...
   «IF isFeatureSelected("...")»
      ...
   «ENDIF»
«ENDDEFINE»
```

### Extension ###

```
import ...;
extension org::openarchitectureware::util::featureconfig::features;

String ...() :
     isFeatureSelected("...") ? ... : ...
;
```

### Check ###

```
import ...;
extension org::openarchitectureware::util::featureconfig::features;

context ...
     if isFeatureSelected("...")
     WARNING "..."
     ...
;
```

## The existing work-arounds ##
  * [Preparing Migration oaw4 –> oaw5 (PLE – featureconfig)](http://ekkescorner.wordpress.com/2009/07/17/galileo-preparing-migration-oaw4-oaw5-ple-featureconfig/)
  * [oAW 4 migration with cartridge adaption (scroll down to the PLE related section)](http://kthoms.wordpress.com/2009/07/16/oaw-4-migration-with-cartridge-adaption/)

## in oAW Var 5 ##

### Workflow ###

#### Configuration Model Reader ####

##### Global Configuration Model Reader #####

dispatches via file extension of configuration model file

```
<workflow>
   ...
   <component class="org.openarchitectureware.var.featureaccess.ConfigurationModelReader">
      <configurationModelFile value="..." />
   </component>
   ...
</workflow>
```

  * .txt -> Textual configuration
  * .vdm.xmi -> pure::variants configuration model
  * .fmp -> FMP model containing feature and configuration definition

##### Configure Global Configuration Reader with Textual Configuration #####

see oAW 4.3.1

##### Configure Global Configuration Reader with pure::variants Configuration Model #####

###### pure::variants feature model ######
![http://dslvariantmanagement.googlecode.com/svn/wiki/images/pv_metamodel.png](http://dslvariantmanagement.googlecode.com/svn/wiki/images/pv_metamodel.png)

###### pure::variants configuration model ######
![http://dslvariantmanagement.googlecode.com/svn/wiki/images/pv_model.png](http://dslvariantmanagement.googlecode.com/svn/wiki/images/pv_model.png)

##### Configure Global Configuration Reader with FMP Configuration Model #####

###### FMP feature meta model ######
![http://dslvariantmanagement.googlecode.com/svn/wiki/images/fmp_metametamodel.png](http://dslvariantmanagement.googlecode.com/svn/wiki/images/fmp_metametamodel.png)

###### FMP feature model ######
![http://dslvariantmanagement.googlecode.com/svn/wiki/images/fmp_metamodel.png](http://dslvariantmanagement.googlecode.com/svn/wiki/images/fmp_metamodel.png)

###### FMP configuration model ######
![http://dslvariantmanagement.googlecode.com/svn/wiki/images/fmp_model.png](http://dslvariantmanagement.googlecode.com/svn/wiki/images/fmp_model.png)

##### EMF Configuration Model #####

Migrated the old reader to org.openarchitectureware.var.featureaccess.emf.EMFConfigurationReader.

#### Usage of Configuration Model in Workflow ####

```
<workflow>
   ...
   <component class="*Reader">
      ...
   </component>

   <feature isSelected="...">
      <component class="...">
         ...
      </component>
   </feature>
   ...
</workflow>
```

### Template ###

```
«IMPORT ...»
«EXTENSION org::openarchitectureware::var::featureaccess::ext::utils»

«DEFINE ... FOR ...»
   ...
   «IF isFeatureSelected("...")»
      ...
   «ENDIF»
«ENDDEFINE»
```

### Extension ###

```
import ...;
extension org::openarchitectureware::var::featureaccess::ext::utils;

String ...() :
     isFeatureSelected("...") ? ... : ...
;
```

### Check ###

```
import ...;
extension org::openarchitectureware::var::featureaccess::ext::utils;

context ...
     if isFeatureSelected("...")
     WARNING "..."
     ...
;
```

### Complete example ###

  * example project: http://dslvariantmanagement.googlecode.com/svn/trunk/oAW5/trunk/org.openarchitectureware.demo.featureaccess
  * runtime
    * MWE, Xpand, Xtend 0.72
    * pure::variants 3.0.7
    * oAW PLE plug-in: http://dslvariantmanagement.googlecode.com/svn/trunk/oAW5/trunk/org.openarchitectureware.var.featureaccess

### Known limitations ###
  * there is currently no expressions support in global configuration reader, so no expressions could be evaluated for textual, pure::variants and FMP configuration models
  * only the existence and selection of features could be queried currently, feature constraints, navigation in a feature hierarchy, cardinalities is not supported yet (have to read in the feature model for pure::variants and make it global for that)

### TODOs ###
  * enabled attributes in EMF Feature Model
  * enable getAllFeatures for FMP, pure::variants and EMF (registration of the packages is needed for that)