# Introduction #
Variability in the model driven software development process can be found at the several stages. In the following for each level regarding the possible variability as well as how tooling could be supportive managing that variability is discussed. As this project is dedicated to develop tools expressing variability in Xtext-based DSLs these thoughts will be enriched for that use case in future.

In the meanwhile there is is a new proposed open source project under the Eclipse Modeling Framework Technology Project (EMFT), called **[EMF Feature Model](http://www.eclipse.org/proposals/feature-model/)**. The intent is to provide a uniform representation for variability information for tools based on the Eclipse Modeling Framework. This will allow easy and consistent access to variability-related information, such as variation points and variant decisions, in DSLs, M2M transformations, and other contexts where variability information is produced or consumed. You are invited to comment on and/or join the project. Please send all feedback to the [EMFT newsgroup](http://www.eclipse.org/newsportal/thread.php?group=eclipse.technology.emft). **Update:** The project has been approved. See the [Creation review (pdf)](http://www.eclipse.org/proposals/feature-model/emffm%20creation%20review.pdf) that states the next steps (roadmap).

# Variability at the meta model level #
  * supported (domain) meta models: EMF, UML2, tool specific formats?
  * how to integrate/link feature models with the domain meta model (if at all)
    * by model to model transformation (interweave feature model and domain specific meta model to a new merged model)
    * by references between feature model and domain specific meta model
    * by having a dedicated link model that draws connections between feature model and domain specific meta model
    * the feature model is defined as UML profile to be later applicable to the domain specific model
  * distinguish between the scope of features
    * features that can be associated with elements of the domain meta model(s) (platform independent meta model(s))
    * features that can be associated with elements of the technical meta model(s) (platform specific meta model(s))
    * features that
  * evaluation of constraints
    * constraints that may be associated with features, might using properties of the meta model elements to calculate its values
    * constraints may be able to deactivate meta model elements / parts of the meta model
  * coping with the versioning / branching of
    * feature models
    * meta model variants

# Variability at the model level #
  * how to integrate/link feature models with the domain model (if at all)
    * model to model transformation of domain model and configuration model on basis of the merged or linked meta models (feature model + domain meta model)
    * applying the feature model as profile to the domain specific model marking model elements to belong to a certain feature as definied in the feature model
    * the integration only happens later in the model to model / model to text transformation description, where the transformation is processed on basis of the configuration model (model elements left out, choosing different transformation steps and so on)
  * evaluation of constraints
    * constraints that may be associated with features, might using properties of the model elements to calculate its values
    * constraints may be able to deactivate model elements / parts of the model
  * coping with the versioning / branching of
    * feature models
    * model variants (also in relation with versions of its meta model)

## Variability in model validations ##
  * the feature selection / configuration may influence the model semantics whose consistence have to preserved by appropriate model checks (constraints that are not expressed in the model) e.g. range of values of on property depending on the value of another property, existence checks and so on
  * model checks may also be used to
    * validate configuration models
    * validate domain models against configuration models

# Variability at the transformation description / generation level #
## Variability in the transformation / generation workflow ##
  * the feature configuration may
    * influence the selection and (order of) application of process steps (e.g. workflow cartridges in openArchitectureWare)
    * influence the configuration of process steps (changing existing global parameters, used within the process steps)
    * inject changes to transformation and generation descriptions (e.g. applying aspect oriented templates and extensions in openArchitectureWare)

## Variability in the transformation / generation description ##
### Variability when writing the description ###
  * auto completion support does not not show types whose associated meta model elements where deactivated by the feature configuration model
  * to support additional aspect oriented transformation descriptions (templates and extensions in case of openArchitectureWare) the base description have to be structured accordingly
  * find a good way for versioning and branching base and additional descriptions

### Variability when executing the description ###
  * The execution of parts of the transformation description depends on the feature selection in the configuration model
    * some parts will be ony executed if a certain feature is selected
    * if a certain feature is not selected an alternative transformation is applied (e.g. in openArchitecture a different template method is called)
    * parameters for transformation descriptions may change

### How variability information may be provided ###
  * global parameters for global model independent configurations (e.g. if no platform specific model is used but instead a global parameter decides if the templates for generating the business tier in EJB3 or the templates for generating the tier in Spring are applied)
  * configuration information is provided as annotation to model elements (e.g. tagged values in an applied UML profile) that can be used when procesing a model element in transformation description
  * association between features and model elements without using annotations but complex rule logic, that evaluate certain properties of certain model elements to decide if an feature is applicable

### Variability in model to model transformations ###
  * the generation / transformation process may consists of several model to model transformation till eventuell the model to platform transformation is done
  * the transformation from platform independent to platform specific model is a typical use case
  * platform specific models transformation may also be staged (just to cope with complexity that would arise when all the transformation would be done at once, also reuse of transformed model between stages for other transformations might be possible)
  * some feature modeling tools also support stepwise refinement of configuration models so that at every step a new intermediate feature configuration model is created that leaves less choice between feature (narrowing the configuration space)
  * according the the refinements in the configuration model the model transformations have to be selected and configured, so that in the end different configuration refinements cause diffent model to model transformation chainings

## Variability at code level ##
  * tracing of applied features by in the code generated annotations
  * even if variation is done at transformation / generation level it could be reasonable to separate code artifacts by feature (weaving in feature associated code snippets like [AspectJ](http://www.eclipse.org/aspectj/)). This is especially useful, when feature associated code needs additional manual written code.
  * the separation of generated and manual written code gains some more complexity when additional the separation by features must be considered

## Variability at platform level ##
  * actually at this level no variability should occur
  * programming libraries to use at the platform may depend on the feature configuration, this variability is handled at the generation level
  * as there the generation process copies the needed libraries to the platfom or
  * when using [Maven](http://maven.apache.org/) the configuration of the needed libraries can be generated in the pom.xml

# Integration of product line engineering capabilities in openArchitectureWare #
## Introduction ##
  * since of openArchitectureWare 4 there exists some direct and indirect support of product line engineering (PLE) or at least handling of variability at the several stages (meta model, model, transformation, code as discussed tool independent before)
  * beside the main scope of this project, developing tools for Xtext-based textual DSLs, also the migration of the existing PLE features to the current release openArchitectureWare 5 (the sum of the Eclipse projects [MWE](http://www.eclipse.org/modeling/emft/?project=mwe), [Xpand](http://www.eclipse.org/modeling/m2t/?project=xpand), [Xtext](http://www.eclipse.org/modeling/tmf/?project=xtext) and the several plugin projects) should be enforced
  * not only the migration of the existing PLE tooling but also (and foremost) the enhancement is our goal

## support of expression positive and negative variability ##
  * a certain configuration is considered as default, to vary that configuration means to remove elements from or to add new elements to the existing configuration
  * some of the features that openArchitectureWare provides were discussed in the AMPLE project videos series for which a [short textual transcription](http://sourceforge.net/apps/mediawiki/dreirad/index.php?title=Seite450) exists
  * negative variability (model tailoring)
    * the configuration model is applied to the domain model erases certain model elements (that still preserves a meta model conform model), so that they do not appear anymore when the model is processed by the generator (see [Users Guide 0.7 (pdf)](http://dslvariantmanagement.googlecode.com/files/UsersGuide-0.7.pdf)) from this project
    * XVar (not documented in the reference documentation of oAW 4.3.1) (see [Expressing Feature-Based Variability in Structural Models (pdf)](http://www.voelter.de/data/workshops/MVSPL_GroherVoelter.pdf))
    * in the xText grammar there is the key word 'retain' introduced to get access to feature information provided ar actually erased model elements
  * positive variability (aspects)
    * model aspects (model weaving with [XWeave](http://www.openarchitectureware.org/pub/documentation/4.3.1/html/contents/ch16.html)
    * [aspect orientation with workflows](http://www.openarchitectureware.org/pub/documentation/4.3.1/html/contents/workflow_reference.html#d0e2670)
    * [aspect oriented programming in Xpand2](http://www.openarchitectureware.org/pub/documentation/4.3.1/html/contents/core_reference.html#xpand_reference_aspect-oriented_programming_in_xpand)
    * [aspect oriented programming in Xtend](http://www.openarchitectureware.org/pub/documentation/4.3.1/html/contents/core_reference.html#d0e4654)
    * [examples](http://www.openarchitectureware.org/pub/documentation/4.3.1/html/contents/core_reference.html#aop_template_introduction)

## Retrieving feature model data from feature models (orthogonal variability) ##
  * global features (i.e. model element independent)
    * sources
      * [Ekkes Corner oAW Migration Guide - oAW PLE feature config](http://ekkescorner.wordpress.com/2009/07/17/galileo-preparing-migration-oaw4-oaw5-ple-featureconfig/)
      * [Karsten Thom's migration guide (scroll down to the PLE feature headline)](http://kthoms.wordpress.com/2009/07/16/oaw-4-migration-with-cartridge-adaption/)
    * definition in the workflow
      * provide the attribute configFile of the workflow component org.openarchitectureware.util.featureconfig.text.TextConfigurationReader with text file, that contains line wise +featurename and/or -featurename
      * '+' means active feature and '-' non active feature
    * usage in the workflow
      * till oAW 4.3.1:
```
<feature isSelected="FEATURENAME">...</feature> 
```
      * in MWE 0.7 (as workaround until this feature is migrated):
```
<if cond="${FEATURENAME}">...</if>
```
    * usage in templates and extensions
      * define the extension
```
«EXTENSION org::openarchitectureware::util::featureconfig::features» 
```
      * it can be switched between different template logic to process with using
```
isFeatureSelected("featurename") 
```
      * to access the feature model you have to define the extension
```
org::openarchitectureware::var::featureaccess::ext::utils 
```
  * pure::variants connector
    * indexer, pure:.variants relations view, to associate features with xText models
    * to create feature related code snippets (like AspectJ) use the DOTV builder and the indexer
    * see [Users Guide (pdf)](http://dslvariantmanagement.googlecode.com/files/UsersGuide-0.6.pdf) from this project for further information
  * future plans:
    * writing connectors for further tools (see [Project plan](http://code.google.com/p/dslvariantmanagement/wiki/Plan)) (consider also the license of the tools before deciding to write a connector)
      * FMP (in progress)
      * XFeature
      * Feature Mapper
      * Compositional Variability Management (CVM)
      * other
    * visualize feature mapping (maybe with help of the [EMF Model Visualizer](http://code.google.com/p/emfmodelvisualizer/))

# Links / references #
  * Related projects: Aspect orientation in DSLs
    * in the [AUTOSAR](http://en.wikipedia.org/wiki/AUTOSAR) domain
      * [ARText - Driving Developments with Xtext](http://www.slideshare.net/sebastianbenz/artext-driving-developments-with-xtext): ARText, a textual language for AUTOSAR, is available for free for those registered at [AUTOSAR Tool Platform (Artop)](http://www.artop.org/)
      * [axBench](http://axbench.isst.fraunhofer.de/): [axLang](http://axbench.isst.fraunhofer.de/?page_id=82) enables the specification of [textual feature models](http://axbench.isst.fraunhofer.de/?page_id=324)
    * [EMF Customizer](http://code.google.com/p/emf-customizer/) is a model (Ecore based) customization/styling/refining CSS like textual DSL (Domain Specific Language) targeting MDSD tool chains end users.
  * Domain specific languages
    * articles
      * [Blog to the conference Domain Specific Languages ('09)](http://dsl09.blogspot.com/) containing resources to the hold presentations
    * tools
      * [Domain-Specific Language (DSL) Testing Studio](http://www.cis.uab.edu/softcom/DDF/): assistance for debugging and testing a program written in a DSL
        * [Publications](http://students.cis.uab.edu/wuh/ddf/publications.html), referencing e.g. an article about AspectG, introducing aspect orientation in a grammar to be able to insert debug information (Debugging Aspect)
        * [Unit Testing DSLs](http://dsl09.blogspot.com/2009/07/unit-testing-dsls.html), containing also some information about AspectG, a domain-specific aspect language for language grammars written in ANTLR, at page 26 of the pdf linked by this site
      * [LISA](http://labraj.uni-mb.si/lisa/): compiler-compiler, or a system that generates automatically a compiler/interpreter from formal attribute grammar-based language specifications.
        * [VisualLISA](http://www.di.uminho.pt/~gepl/VisualLISA/) is a visual programming environment for Attribute Grammar specifications. See also the [researcher's site](http://www.ipb.pt/~mjoao/researchindex.html)
        * AspectLISA
          * [Domain-Speciﬁc Aspect Languages for Modularizing Crosscutting Concerns in Grammars (pdf)](http://www.cis.uab.edu/gray/pubs/iet-dsal-2008.pdf)
          * [Specifying languages using aspect-oriented approach: AspectLISA (pdf)](http://marcel.uni-mb.si/lisa/publications/AspectLISA-ITI2006.pdf)
          * [AspectLISA: an aspect-oriented compiler construction system based on attribute grammars (pdf)](http://wiki.di.uminho.pt/twiki/pub/Research/Voda/WebHome/Rebernak-ENTCS.pdf)
  * SPLE
    * conferences / papers
      * [conference paper (pdf)](http://www.feasiple.de/public/proceedings-mdple2009.pdf) from the [1st International Workshop on Model-Driven Product Line Engineering in Twente, June 24th, 2009](http://www.feasiple.de/workshop_en.html)
      * [Software Product Line Conferences (SPLC)](http://www.splc.net/)
      * [1st International Workshop on Model-driven Approaches in Software Product Line Engineering (MAPLE 2009)](http://www.lero.ie/maple2009/)
      * [Variability Modelling of Software-intensive Systems (VaMoS)](http://www.vamos-workshop.net)
      * [Practical Product Lines 2009](http://www.practicalproductlines.org/ppl2009/)
      * [Comparison and Versioning of Software Models (CVSM 2009)](http://pi.informatik.uni-siegen.de/CVSM09/)
      * [Workshop on Modeling in Software Engineering (MiSE 2009)](http://wikiserver.sse.cs.tu-bs.de/mise09/index.php/Main_Page)
      * [International Conference on Generative Programming and Component Engineering](http://www.hope.cs.rice.edu/twiki/bin/view/Gpce/WebHome)
      * [Produktlinien im Kontext: Technologie, Prozesse, Business und Organisation (PIK2010)](http://www.se2010.upb.de/workshops.html#PIK2010) (workshop about product lines at the conference Software Engineering 2010)
    * articles
      * [An Overview of Feature-Oriented Software Development (pdf)](http://wwwiti.cs.uni-magdeburg.de/~ckaestne/JOT09_OverviewFOSD.pdf)
      * [Variabilität in der Domänen-Objekt-Modellierung für Software-Produktlinien (German, pdf) (hey, this is the thesis of Andy, one of the committers of this project :)](http://www.htw-aalen.de/img/downloads/studienarbeiten/228_2008-Diplomarbeit-Rytina.pdf)
      * [Feature-Diagramme und Variabilität (deutsch)](http://www.stefan-marr.de/pages/feature-diagramme-und-variabilitat/)
      * [On the Relationship between Feature Models and Ontologies (pdf)](http://www.cs.utexas.edu/~chpkim/chpkim-masc-thesis.pdf)
      * [Feature Models are Views on Ontologies (pdf)](http://www.swen.uwaterloo.ca/~kczarnec/feature_models_as_viewes_on_ontology_splc06.pdf)
      * [Procuct lines blog](http://productlines.wordpress.com/)
        * [Feature Models and Features – What’s this?](http://productlines.wordpress.com/2008/07/21/feature-models-and-features-whats-this/)
      * [Software Product Line Engineering with Feature Models (pdf)](http://www.pure-systems.com/fileadmin/downloads/pure-variants/tutorials/SPLWithFeatureModelling.pdf)
      * [Variabilities - Representing Variability In Software Systems (pdf)](http://www.voelter.de/data/presentations/variantmgt.pdf)
      * [Variantenmanagement im  Kontext von MDSD (pdf, deutsch)](http://www.voelter.de/data/articles/MDSDAndVariants.pdf)
      * [Product Line Implementation using Aspect-Oriented and Model-Driven Software Development (pdf)](http://www.voelter.de/data/pub/VoelterGroher_SPLEwithAOandMDD.pdf)
      * [Using domain specific languages for product line engineering (pdf) (this presentation refers also to this project)](http://www.voelter.de/data/presentations/MDD-PLE_SPLC-PRINT.pdf)
      * [Model-Driven Development in the context of Software Product Lines](http://www.slideshare.net/schogglad/modeldriven-development-in-the-context-of-software-product-lines)
      * [Best Practices for DSLs and Model-Driven Development](http://www.jot.fm/issues/issue_2009_09/column6/index.html)
      * [A Model-driven Product Line Approach For HMI Development (pdf)](http://2009.pik-konf.de/docs/PIK2009_Ekine.pdf)
      * [Agile Software Product Lines, Deconstructed](http://www.jot.fm/issues/issue_2008_11/column1/index.html)
      * [Abbildung von produktlinienorientierten Featurediagrammen auf aspektorientierte Implementierungsmodule (pdf)](http://www.objectteams.org/publications/Diplom_Marko_Feistkorn.pdf)
      * [Feature Oriented Programming (Wikipedia)](http://en.wikipedia.org/wiki/Feature_Oriented_Programming)
      * [Produktlinienentwicklung (Software) (German Wikipedia article)](http://de.wikipedia.org/wiki/Produktlinienentwicklung_(Software))
      * [Produktlinie (Software) (German Wikipedia article)](http://de.wikipedia.org/wiki/Produktlinie_(Software))
      * [A model-driven traceability framework for software product lines](http://www.springerlink.com/content/wvm4hv8r78117785/)
      * [FeatureDrivenDevelopment.com](http://www.featuredrivendevelopment.com/): place for all to discuss Feature Driven Development (FDD), ask questions, learn more about it, and discuss their own experiences and their own FDD-inspired approaches.
      * [Jan Bosch about Software Product Lines](http://www.janbosch.com/Jan_Bosch/Product_lines.html)
    * tools
      * [Eclipse project proposal for a new EMF feature model](http://www.eclipse.org/proposals/feature-model/)
      * [FMP](https://sourceforge.net/projects/fmp/) (licensed under EPL)
      * [Ecore.fmp](http://gsd.uwaterloo.ca/category/projects/ecorefmp/) (no release found, but metamodel could recontructed from the article below)
        * [Ecore.fmp (pdf)](http://gp.uwaterloo.ca/tr/2008-stephan-ecore-fmp.pdf)
        * [Michał Antkiewicz, Ph.D.](http://swen.uwaterloo.ca/~mantkiew/)
      * [XFeature](http://www.pnp-software.com/XFeature/)? (licensed under GPL)
      * [FeatureMapper](http://www.featuremapper.org/)? (licensed under LGPL 3.0)
      * [Compositional Variability Management (CVM)](http://www.cvm-framework.org/index.html) (not released yet)
        * [Technical Report (pdf)](http://www.eecs.tu-berlin.de/fileadmin/f4/TechReports/2009/tr-2009-16.pdf): describes the grammar and its concept for a textual feature modeling language with some special features, prominently the entity concept
          * feature models as well (partly preconfigured) configuration models are encapsulated by entities
          * entities may instanciate feature models contained in itself or from another entity (resulting in a configuration model)
          * an entity may reference configuration models from other entities
          * an entity can contain a configuration model semantic link definition that defines the propagates of the (de-)selection of features in other configuration models in dependence of the selection in the current configuration model (so the configuration models do not need to know each other)
          * an entity exposes only certain configuration abilities to other entities and encapsulates the knowledge used to partly pre configure the contained configuration model
          * configurations of feature models can be chained
          * advantage: higher reusability as there is no more one global feature and configuration model
        * [Tutorial](http://www.cvm-framework.org/tutorial/index.html) providing a quick overview of the core functionality of CVM without going into great detail. It also shows how the different parts of CVM - the model editor, diagram editor and the VSL editor - fit together.
      * [Feature-Oriented Software Development Research](http://www.fosd.de/), see also [here](http://www.infosun.fim.uni-passau.de/cl/staff/apel/#projekte) and [An Overview of Feature-Oriented Software Development (pdf)](http://wwwiti.cs.uni-magdeburg.de/~ckaestne/JOT09_OverviewFOSD.pdf)
        * [FeatureIDE](http://wwwiti.cs.uni-magdeburg.de/iti_db/research/featureide/) (licensed under GPL)
        * [FeatureHouse](http://www.infosun.fim.uni-passau.de/cl/staff/apel/FH/)
        * [FeatureC++](http://wwwiti.cs.uni-magdeburg.de/iti_db/forschung/fop/featurec/)
        * [Colored Integrated Development Environment (CIDE)](http://wwwiti.cs.uni-magdeburg.de/iti_db/research/cide/)
        * [An Homogeneous Feature-based Approach](http://modalis.polytech.unice.fr/softwares/hfm) (is based on the FeatureIDE)
      * [pure::variants](http://www.pure-systems.com/Products.products.0.html), see also [this blog](http://productlines.wordpress.com/) and this [[article (pdf)](http://www.pure-systems.com/fileadmin/downloads/pure-variants/tutorials/SPLWithFeatureModelling.pdf)]
      * [Link collection at AMPLE - tools that support variability](http://ample.holos.pt/pageview.aspx?pageid=20&langid=1)
      * [FAMA Tool Suite](http://www.isa.us.es/fama/): Framework for automated analyses of feature models integrating some of the most commonly used logic representations and solvers proposed in the literature
        * [FAMA Tool Suite at Google Code](http://code.google.com/p/famats/)
        * [Researcher Site](http://www.lsi.us.es/~dbc/en/?Research) containing various publications about the FAMA tool suite
    * research projects
      * [Forschungsprojekt Interoperabilität und Feature-Tracing für Werkzeugketten in der modellgetriebenen Entwicklung (IF-ModE)](http://www.offis.de/projekte/e/244/if-mode.php), see also [here](http://www.d-s-t-g.com/neu/pages/pagesger/common/news_nletter_nl2009_nr61_e61t1_frmset.htm)
      * [Verteilte Entwicklung und Integration von Automotive-Produktlinien (VEIA)](http://veia.isst.fraunhofer.de/): [Publications](http://veia.isst.fraunhofer.de/index.php?option=com_content&task=view&id=16&Itemid=35)
    * [Link collection to software product lines](http://www.mister-wong.de/user/hanwong/softwareproduktlinien/)
  * aspect orientation
    * [AspectJ](http://www.eclipse.org/aspectj/)
      * [Hands-on Programming with AspectJ](http://www.eclipse.org/ajdt/EclipseCon2006/)
    * [AOSD-Europe Project](http://www.aosd-europe.net)
    * [Aspects of Domain Model Management](http://www.infoq.com/articles/aspects-of-domain-model-mgmt)
    * [AOP@Work article series](http://www.ibm.com/developerworks/views/java/libraryview.jsp?search_by=AOP@work:)
    * [Reuseware](http://www.reuseware.org/index.php/Main_Page)
      * [Extending Grammars and Metamodels for Reuse The Reuseware Approach (pdf)](http://www.steffen-zschaler.de/publications/iet_reuseware.pdf)
  * openArchitectureWare 5
    * general links
      * [MWE](http://www.eclipse.org/modeling/emft/?project=mwe),
      * [Xpand](http://www.eclipse.org/modeling/m2t/?project=xpand),
      * [Xtext](http://www.eclipse.org/modeling/tmf/?project=xtext)
    * PLE and aspect orientation
      * [short textual transcript of aspect orientation and PLE support in oAW 4.2](http://dreirad.wiki.sourceforge.net/Seite450)
      * [Users Guide (pdf)](http://dslvariantmanagement.googlecode.com/files/UsersGuide-0.6.pdf)) from this project
    * variability at model level
      * [Expressing Feature-Based Variability in Structural Models (pdf)](http://www.voelter.de/data/workshops/MVSPL_GroherVoelter.pdf) (XVar)
      * [XWeave](http://www.openarchitectureware.org/pub/documentation/4.3.1/html/contents/ch16.html)
    * aspect orientation at generator level
      * [aspect orientation with workflows](http://www.openarchitectureware.org/pub/documentation/4.3.1/html/contents/workflow_reference.html#d0e2670)
      * [aspect oriented programming in Xpand2](http://www.openarchitectureware.org/pub/documentation/4.3.1/html/contents/core_reference.html#xpand_reference_aspect-oriented_programming_in_xpand)
      * [aspect oriented programming in Xtend](http://www.openarchitectureware.org/pub/documentation/4.3.1/html/contents/core_reference.html#d0e4654)
      * [examples](http://www.openarchitectureware.org/pub/documentation/4.3.1/html/contents/core_reference.html#aop_template_introduction)
    * migration guides concerning PLE
      * [Ekkes Corner's oAW Migration Guide for PLE FeatureConfig](http://ekkescorner.wordpress.com/2009/07/17/galileo-preparing-migration-oaw4-oaw5-ple-featureconfig/)
      * [Karsten Thom's migration guide (scroll down to the PLE feature headline)](http://kthoms.wordpress.com/2009/07/16/oaw-4-migration-with-cartridge-adaption/)