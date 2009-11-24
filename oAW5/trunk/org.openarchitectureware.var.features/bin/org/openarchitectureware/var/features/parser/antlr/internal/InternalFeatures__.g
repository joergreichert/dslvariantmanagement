lexer grammar InternalFeatures;
@header {
package org.openarchitectureware.var.features.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

T11 : 'featureAndList' ;
T12 : 'retain' ;
T13 : '(' ;
T14 : ',' ;
T15 : ')' ;
T16 : 'featureOrList' ;
T17 : 'featureExp' ;
T18 : 'feature' ;
T19 : 'or' ;
T20 : 'and' ;
T21 : 'not' ;

// $ANTLR src "../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g" 736
RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

// $ANTLR src "../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g" 738
RULE_INT : ('0'..'9')+;

// $ANTLR src "../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g" 740
RULE_STRING : ('"' ('\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')|~(('\\'|'"')))* '"'|'\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\'');

// $ANTLR src "../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g" 742
RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

// $ANTLR src "../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g" 744
RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

// $ANTLR src "../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g" 746
RULE_WS : (' '|'\t'|'\r'|'\n')+;

// $ANTLR src "../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g" 748
RULE_ANY_OTHER : .;


