Product line engineering and model driven development are two approaches to software development that are becoming more and more important. The combination of the two approaches is promising. There are various ways of how those two approaches can be combined. One way is to use feature modeling to define variants of models created with domain specific languages.

The tools in this project are in implementation of this approach. For textual DSLs built with openArchitectureWare 4.3.1 Xtext this project contains tools to define variability. This means, that feature dependencies can be annotated to arbitrary grammar elements. A model processor removes all the elements from the model after it has been loaded whose features are not selected in the current configuration. Tool integration exists with pure::variants. Code completion from the DSL into the feature model is implemented, as well as traceability from the feature back to the models.

For completeness this project also contains a text processor that can customize text files according to features selections.

The download page contains documentation of how these tools should be used. To understand the background, please refer to the following two documents. The first one explains the general approach of using textual DSLs for architecture description. The second one specifically addresses variability.

  * http://www.voelter.de/data/articles/ArchitectureAsLanguage-PDF.pdf
  * http://www.voelter.de/data/articles/ArchitectureAsLanguage-Part2-PDF.pdf

If you have any problems getting the example described in the users guide running, please feel free to contact andy.rytina@googlemail.com