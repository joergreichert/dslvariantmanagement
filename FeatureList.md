You can add your ideas or feature requests to this table:

# Usability #
| **Feature Description** | **Implementer** |
|:------------------------|:----------------|
|Ability to navigate from a feature condition in the source or model code directly to the related feature in the feature model | Andreas |
|with a Xtext editor for [.v-files](DotVFile.md) it should be possible to conveniently handle variability in source code |  |
|as a alternative to [.v-file](DotVFile.md) it should be possible to define variability conditions through annotations directly in java files |  |
|implement a eclipse action to assign feature dependencies to a [.v-file](DotVFile.md) |  |
|implement a eclipse action to assign feature dependencies to complete regions within [.v-file](DotVFile.md) |  |
|eclipse action to rename a [.v-file](DotVFile.md) to the original file extension |  |


# Testability #
| **Feature Description** | **Implementer** |
|:------------------------|:----------------|
|define a oAW-check, that verifies the import statement for feature models (i.e. the imported file must end with ".xfm.xmi")  |  |
|When a cross reference points to an element with a feature dependency, at least the same feature dependency must also be defined for that reference. The reference can have additional features concatenated with "or". |  |
|When a xtext rule has a grammar part for feature constraints all other rules that refer to this rule must also have a grammar part for feature constraints so you can satisfy the above rule. |  |
|implement the following oAW-Check for feature dependencies. When there are more variants of the same element the dependencies of these variants must be mutually exclusive. In this way duplicate elements are prevented after the tailoring process |  |
|Validate the resulting artifacts of all configurations |  |


# Visualization #
| **Feature Description** | **Implementer** |
|:------------------------|:----------------|
|visualize feature constraints in the architectural model |  |