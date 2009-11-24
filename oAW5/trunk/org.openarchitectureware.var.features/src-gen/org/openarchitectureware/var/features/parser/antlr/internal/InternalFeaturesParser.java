package org.openarchitectureware.var.features.parser.antlr.internal; 

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.xtext.parsetree.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.openarchitectureware.var.features.services.FeaturesGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class InternalFeaturesParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'featureAndList'", "'retain'", "'('", "','", "')'", "'featureOrList'", "'featureExp'", "'feature'", "'or'", "'and'", "'not'"
    };
    public static final int RULE_ID=4;
    public static final int RULE_STRING=6;
    public static final int RULE_ANY_OTHER=10;
    public static final int RULE_INT=5;
    public static final int RULE_WS=9;
    public static final int RULE_SL_COMMENT=8;
    public static final int EOF=-1;
    public static final int RULE_ML_COMMENT=7;

        public InternalFeaturesParser(TokenStream input) {
            super(input);
        }
        

    public String[] getTokenNames() { return tokenNames; }
    public String getGrammarFileName() { return "../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g"; }


     
     	private FeaturesGrammarAccess grammarAccess;
     	
        public InternalFeaturesParser(TokenStream input, IAstFactory factory, FeaturesGrammarAccess grammarAccess) {
            this(input);
            this.factory = factory;
            registerRules(grammarAccess.getGrammar());
            this.grammarAccess = grammarAccess;
        }
        
        @Override
        protected InputStream getTokenFile() {
        	ClassLoader classLoader = getClass().getClassLoader();
        	return classLoader.getResourceAsStream("org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.tokens");
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "FeatureClause";	
       	} 



    // $ANTLR start entryRuleFeatureClause
    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:72:1: entryRuleFeatureClause returns [EObject current=null] : iv_ruleFeatureClause= ruleFeatureClause EOF ;
    public final EObject entryRuleFeatureClause() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFeatureClause = null;


        try {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:72:55: (iv_ruleFeatureClause= ruleFeatureClause EOF )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:73:2: iv_ruleFeatureClause= ruleFeatureClause EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFeatureClauseRule(), currentNode); 
            pushFollow(FOLLOW_ruleFeatureClause_in_entryRuleFeatureClause73);
            iv_ruleFeatureClause=ruleFeatureClause();
            _fsp--;

             current =iv_ruleFeatureClause; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFeatureClause83); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleFeatureClause


    // $ANTLR start ruleFeatureClause
    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:80:1: ruleFeatureClause returns [EObject current=null] : (this_FeatureAndList_0= ruleFeatureAndList | this_FeatureOrList_1= ruleFeatureOrList | this_FeatureExpression_2= ruleFeatureExpression | this_Feature_3= ruleFeature ) ;
    public final EObject ruleFeatureClause() throws RecognitionException {
        EObject current = null;

        EObject this_FeatureAndList_0 = null;

        EObject this_FeatureOrList_1 = null;

        EObject this_FeatureExpression_2 = null;

        EObject this_Feature_3 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:85:6: ( (this_FeatureAndList_0= ruleFeatureAndList | this_FeatureOrList_1= ruleFeatureOrList | this_FeatureExpression_2= ruleFeatureExpression | this_Feature_3= ruleFeature ) )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:86:1: (this_FeatureAndList_0= ruleFeatureAndList | this_FeatureOrList_1= ruleFeatureOrList | this_FeatureExpression_2= ruleFeatureExpression | this_Feature_3= ruleFeature )
            {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:86:1: (this_FeatureAndList_0= ruleFeatureAndList | this_FeatureOrList_1= ruleFeatureOrList | this_FeatureExpression_2= ruleFeatureExpression | this_Feature_3= ruleFeature )
            int alt1=4;
            switch ( input.LA(1) ) {
            case 11:
                {
                alt1=1;
                }
                break;
            case 16:
                {
                alt1=2;
                }
                break;
            case 17:
                {
                alt1=3;
                }
                break;
            case 18:
                {
                alt1=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("86:1: (this_FeatureAndList_0= ruleFeatureAndList | this_FeatureOrList_1= ruleFeatureOrList | this_FeatureExpression_2= ruleFeatureExpression | this_Feature_3= ruleFeature )", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:87:5: this_FeatureAndList_0= ruleFeatureAndList
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getFeatureClauseAccess().getFeatureAndListParserRuleCall_0(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleFeatureAndList_in_ruleFeatureClause130);
                    this_FeatureAndList_0=ruleFeatureAndList();
                    _fsp--;

                     
                            current = this_FeatureAndList_0; 
                            currentNode = currentNode.getParent();
                        

                    }
                    break;
                case 2 :
                    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:97:5: this_FeatureOrList_1= ruleFeatureOrList
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getFeatureClauseAccess().getFeatureOrListParserRuleCall_1(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleFeatureOrList_in_ruleFeatureClause157);
                    this_FeatureOrList_1=ruleFeatureOrList();
                    _fsp--;

                     
                            current = this_FeatureOrList_1; 
                            currentNode = currentNode.getParent();
                        

                    }
                    break;
                case 3 :
                    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:107:5: this_FeatureExpression_2= ruleFeatureExpression
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getFeatureClauseAccess().getFeatureExpressionParserRuleCall_2(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleFeatureExpression_in_ruleFeatureClause184);
                    this_FeatureExpression_2=ruleFeatureExpression();
                    _fsp--;

                     
                            current = this_FeatureExpression_2; 
                            currentNode = currentNode.getParent();
                        

                    }
                    break;
                case 4 :
                    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:117:5: this_Feature_3= ruleFeature
                    {
                     
                            currentNode=createCompositeNode(grammarAccess.getFeatureClauseAccess().getFeatureParserRuleCall_3(), currentNode); 
                        
                    pushFollow(FOLLOW_ruleFeature_in_ruleFeatureClause211);
                    this_Feature_3=ruleFeature();
                    _fsp--;

                     
                            current = this_Feature_3; 
                            currentNode = currentNode.getParent();
                        

                    }
                    break;

            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleFeatureClause


    // $ANTLR start entryRuleFeatureAndList
    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:132:1: entryRuleFeatureAndList returns [EObject current=null] : iv_ruleFeatureAndList= ruleFeatureAndList EOF ;
    public final EObject entryRuleFeatureAndList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFeatureAndList = null;


        try {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:132:56: (iv_ruleFeatureAndList= ruleFeatureAndList EOF )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:133:2: iv_ruleFeatureAndList= ruleFeatureAndList EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFeatureAndListRule(), currentNode); 
            pushFollow(FOLLOW_ruleFeatureAndList_in_entryRuleFeatureAndList243);
            iv_ruleFeatureAndList=ruleFeatureAndList();
            _fsp--;

             current =iv_ruleFeatureAndList; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFeatureAndList253); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleFeatureAndList


    // $ANTLR start ruleFeatureAndList
    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:140:1: ruleFeatureAndList returns [EObject current=null] : ( 'featureAndList' (lv_retained_1= 'retain' )? '(' (lv_featureList_3= RULE_ID ) ( ',' (lv_featureList_5= RULE_ID ) )* ')' ) ;
    public final EObject ruleFeatureAndList() throws RecognitionException {
        EObject current = null;

        Token lv_retained_1=null;
        Token lv_featureList_3=null;
        Token lv_featureList_5=null;

         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:145:6: ( ( 'featureAndList' (lv_retained_1= 'retain' )? '(' (lv_featureList_3= RULE_ID ) ( ',' (lv_featureList_5= RULE_ID ) )* ')' ) )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:146:1: ( 'featureAndList' (lv_retained_1= 'retain' )? '(' (lv_featureList_3= RULE_ID ) ( ',' (lv_featureList_5= RULE_ID ) )* ')' )
            {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:146:1: ( 'featureAndList' (lv_retained_1= 'retain' )? '(' (lv_featureList_3= RULE_ID ) ( ',' (lv_featureList_5= RULE_ID ) )* ')' )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:146:2: 'featureAndList' (lv_retained_1= 'retain' )? '(' (lv_featureList_3= RULE_ID ) ( ',' (lv_featureList_5= RULE_ID ) )* ')'
            {
            match(input,11,FOLLOW_11_in_ruleFeatureAndList287); 

                    createLeafNode(grammarAccess.getFeatureAndListAccess().getFeatureAndListKeyword_0(), null); 
                
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:150:1: (lv_retained_1= 'retain' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==12) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:152:6: lv_retained_1= 'retain'
                    {
                    lv_retained_1=(Token)input.LT(1);
                    match(input,12,FOLLOW_12_in_ruleFeatureAndList308); 

                            createLeafNode(grammarAccess.getFeatureAndListAccess().getRetainedRetainKeyword_1_0(), "retained"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getFeatureAndListRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		set(current, "retained", true, "retain", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }
                    break;

            }

            match(input,13,FOLLOW_13_in_ruleFeatureAndList331); 

                    createLeafNode(grammarAccess.getFeatureAndListAccess().getLeftParenthesisKeyword_2(), null); 
                
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:175:1: (lv_featureList_3= RULE_ID )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:177:6: lv_featureList_3= RULE_ID
            {
            lv_featureList_3=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFeatureAndList353); 

            		createLeafNode(grammarAccess.getFeatureAndListAccess().getFeatureListIDTerminalRuleCall_3_0(), "featureList"); 
            	

            	        if (current==null) {
            	            current = factory.create(grammarAccess.getFeatureAndListRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode, current);
            	        }
            	        
            	        try {
            	       		add(current, "featureList", lv_featureList_3, "ID", lastConsumedNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	    

            }

            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:195:2: ( ',' (lv_featureList_5= RULE_ID ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==14) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:195:3: ',' (lv_featureList_5= RULE_ID )
            	    {
            	    match(input,14,FOLLOW_14_in_ruleFeatureAndList371); 

            	            createLeafNode(grammarAccess.getFeatureAndListAccess().getCommaKeyword_4_0(), null); 
            	        
            	    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:199:1: (lv_featureList_5= RULE_ID )
            	    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:201:6: lv_featureList_5= RULE_ID
            	    {
            	    lv_featureList_5=(Token)input.LT(1);
            	    match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFeatureAndList393); 

            	    		createLeafNode(grammarAccess.getFeatureAndListAccess().getFeatureListIDTerminalRuleCall_4_1_0(), "featureList"); 
            	    	

            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getFeatureAndListRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode, current);
            	    	        }
            	    	        
            	    	        try {
            	    	       		add(current, "featureList", lv_featureList_5, "ID", lastConsumedNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            match(input,15,FOLLOW_15_in_ruleFeatureAndList412); 

                    createLeafNode(grammarAccess.getFeatureAndListAccess().getRightParenthesisKeyword_5(), null); 
                

            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleFeatureAndList


    // $ANTLR start entryRuleFeatureOrList
    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:230:1: entryRuleFeatureOrList returns [EObject current=null] : iv_ruleFeatureOrList= ruleFeatureOrList EOF ;
    public final EObject entryRuleFeatureOrList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFeatureOrList = null;


        try {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:230:55: (iv_ruleFeatureOrList= ruleFeatureOrList EOF )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:231:2: iv_ruleFeatureOrList= ruleFeatureOrList EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFeatureOrListRule(), currentNode); 
            pushFollow(FOLLOW_ruleFeatureOrList_in_entryRuleFeatureOrList445);
            iv_ruleFeatureOrList=ruleFeatureOrList();
            _fsp--;

             current =iv_ruleFeatureOrList; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFeatureOrList455); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleFeatureOrList


    // $ANTLR start ruleFeatureOrList
    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:238:1: ruleFeatureOrList returns [EObject current=null] : ( 'featureOrList' (lv_retained_1= 'retain' )? '(' (lv_featureList_3= RULE_ID ) ( ',' (lv_featureList_5= RULE_ID ) )* ')' ) ;
    public final EObject ruleFeatureOrList() throws RecognitionException {
        EObject current = null;

        Token lv_retained_1=null;
        Token lv_featureList_3=null;
        Token lv_featureList_5=null;

         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:243:6: ( ( 'featureOrList' (lv_retained_1= 'retain' )? '(' (lv_featureList_3= RULE_ID ) ( ',' (lv_featureList_5= RULE_ID ) )* ')' ) )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:244:1: ( 'featureOrList' (lv_retained_1= 'retain' )? '(' (lv_featureList_3= RULE_ID ) ( ',' (lv_featureList_5= RULE_ID ) )* ')' )
            {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:244:1: ( 'featureOrList' (lv_retained_1= 'retain' )? '(' (lv_featureList_3= RULE_ID ) ( ',' (lv_featureList_5= RULE_ID ) )* ')' )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:244:2: 'featureOrList' (lv_retained_1= 'retain' )? '(' (lv_featureList_3= RULE_ID ) ( ',' (lv_featureList_5= RULE_ID ) )* ')'
            {
            match(input,16,FOLLOW_16_in_ruleFeatureOrList489); 

                    createLeafNode(grammarAccess.getFeatureOrListAccess().getFeatureOrListKeyword_0(), null); 
                
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:248:1: (lv_retained_1= 'retain' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==12) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:250:6: lv_retained_1= 'retain'
                    {
                    lv_retained_1=(Token)input.LT(1);
                    match(input,12,FOLLOW_12_in_ruleFeatureOrList510); 

                            createLeafNode(grammarAccess.getFeatureOrListAccess().getRetainedRetainKeyword_1_0(), "retained"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getFeatureOrListRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		set(current, "retained", true, "retain", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }
                    break;

            }

            match(input,13,FOLLOW_13_in_ruleFeatureOrList533); 

                    createLeafNode(grammarAccess.getFeatureOrListAccess().getLeftParenthesisKeyword_2(), null); 
                
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:273:1: (lv_featureList_3= RULE_ID )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:275:6: lv_featureList_3= RULE_ID
            {
            lv_featureList_3=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFeatureOrList555); 

            		createLeafNode(grammarAccess.getFeatureOrListAccess().getFeatureListIDTerminalRuleCall_3_0(), "featureList"); 
            	

            	        if (current==null) {
            	            current = factory.create(grammarAccess.getFeatureOrListRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode, current);
            	        }
            	        
            	        try {
            	       		add(current, "featureList", lv_featureList_3, "ID", lastConsumedNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	    

            }

            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:293:2: ( ',' (lv_featureList_5= RULE_ID ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==14) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:293:3: ',' (lv_featureList_5= RULE_ID )
            	    {
            	    match(input,14,FOLLOW_14_in_ruleFeatureOrList573); 

            	            createLeafNode(grammarAccess.getFeatureOrListAccess().getCommaKeyword_4_0(), null); 
            	        
            	    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:297:1: (lv_featureList_5= RULE_ID )
            	    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:299:6: lv_featureList_5= RULE_ID
            	    {
            	    lv_featureList_5=(Token)input.LT(1);
            	    match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFeatureOrList595); 

            	    		createLeafNode(grammarAccess.getFeatureOrListAccess().getFeatureListIDTerminalRuleCall_4_1_0(), "featureList"); 
            	    	

            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getFeatureOrListRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode, current);
            	    	        }
            	    	        
            	    	        try {
            	    	       		add(current, "featureList", lv_featureList_5, "ID", lastConsumedNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            match(input,15,FOLLOW_15_in_ruleFeatureOrList614); 

                    createLeafNode(grammarAccess.getFeatureOrListAccess().getRightParenthesisKeyword_5(), null); 
                

            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleFeatureOrList


    // $ANTLR start entryRuleFeatureExpression
    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:328:1: entryRuleFeatureExpression returns [EObject current=null] : iv_ruleFeatureExpression= ruleFeatureExpression EOF ;
    public final EObject entryRuleFeatureExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFeatureExpression = null;


        try {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:328:59: (iv_ruleFeatureExpression= ruleFeatureExpression EOF )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:329:2: iv_ruleFeatureExpression= ruleFeatureExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFeatureExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleFeatureExpression_in_entryRuleFeatureExpression647);
            iv_ruleFeatureExpression=ruleFeatureExpression();
            _fsp--;

             current =iv_ruleFeatureExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFeatureExpression657); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleFeatureExpression


    // $ANTLR start ruleFeatureExpression
    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:336:1: ruleFeatureExpression returns [EObject current=null] : ( 'featureExp' (lv_retained_1= 'retain' )? '(' (lv_expression_3= ruleOrExpression ) ')' ) ;
    public final EObject ruleFeatureExpression() throws RecognitionException {
        EObject current = null;

        Token lv_retained_1=null;
        EObject lv_expression_3 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:341:6: ( ( 'featureExp' (lv_retained_1= 'retain' )? '(' (lv_expression_3= ruleOrExpression ) ')' ) )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:342:1: ( 'featureExp' (lv_retained_1= 'retain' )? '(' (lv_expression_3= ruleOrExpression ) ')' )
            {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:342:1: ( 'featureExp' (lv_retained_1= 'retain' )? '(' (lv_expression_3= ruleOrExpression ) ')' )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:342:2: 'featureExp' (lv_retained_1= 'retain' )? '(' (lv_expression_3= ruleOrExpression ) ')'
            {
            match(input,17,FOLLOW_17_in_ruleFeatureExpression691); 

                    createLeafNode(grammarAccess.getFeatureExpressionAccess().getFeatureExpKeyword_0(), null); 
                
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:346:1: (lv_retained_1= 'retain' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==12) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:348:6: lv_retained_1= 'retain'
                    {
                    lv_retained_1=(Token)input.LT(1);
                    match(input,12,FOLLOW_12_in_ruleFeatureExpression712); 

                            createLeafNode(grammarAccess.getFeatureExpressionAccess().getRetainedRetainKeyword_1_0(), "retained"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getFeatureExpressionRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		set(current, "retained", true, "retain", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }
                    break;

            }

            match(input,13,FOLLOW_13_in_ruleFeatureExpression735); 

                    createLeafNode(grammarAccess.getFeatureExpressionAccess().getLeftParenthesisKeyword_2(), null); 
                
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:371:1: (lv_expression_3= ruleOrExpression )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:374:6: lv_expression_3= ruleOrExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getFeatureExpressionAccess().getExpressionOrExpressionParserRuleCall_3_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleOrExpression_in_ruleFeatureExpression769);
            lv_expression_3=ruleOrExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getFeatureExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        
            	        try {
            	       		set(current, "expression", lv_expression_3, "OrExpression", currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }

            match(input,15,FOLLOW_15_in_ruleFeatureExpression782); 

                    createLeafNode(grammarAccess.getFeatureExpressionAccess().getRightParenthesisKeyword_4(), null); 
                

            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleFeatureExpression


    // $ANTLR start entryRuleFeature
    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:403:1: entryRuleFeature returns [EObject current=null] : iv_ruleFeature= ruleFeature EOF ;
    public final EObject entryRuleFeature() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFeature = null;


        try {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:403:49: (iv_ruleFeature= ruleFeature EOF )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:404:2: iv_ruleFeature= ruleFeature EOF
            {
             currentNode = createCompositeNode(grammarAccess.getFeatureRule(), currentNode); 
            pushFollow(FOLLOW_ruleFeature_in_entryRuleFeature815);
            iv_ruleFeature=ruleFeature();
            _fsp--;

             current =iv_ruleFeature; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleFeature825); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleFeature


    // $ANTLR start ruleFeature
    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:411:1: ruleFeature returns [EObject current=null] : ( 'feature' (lv_retained_1= 'retain' )? (lv_feature_2= RULE_ID ) ) ;
    public final EObject ruleFeature() throws RecognitionException {
        EObject current = null;

        Token lv_retained_1=null;
        Token lv_feature_2=null;

         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:416:6: ( ( 'feature' (lv_retained_1= 'retain' )? (lv_feature_2= RULE_ID ) ) )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:417:1: ( 'feature' (lv_retained_1= 'retain' )? (lv_feature_2= RULE_ID ) )
            {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:417:1: ( 'feature' (lv_retained_1= 'retain' )? (lv_feature_2= RULE_ID ) )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:417:2: 'feature' (lv_retained_1= 'retain' )? (lv_feature_2= RULE_ID )
            {
            match(input,18,FOLLOW_18_in_ruleFeature859); 

                    createLeafNode(grammarAccess.getFeatureAccess().getFeatureKeyword_0(), null); 
                
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:421:1: (lv_retained_1= 'retain' )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==12) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:423:6: lv_retained_1= 'retain'
                    {
                    lv_retained_1=(Token)input.LT(1);
                    match(input,12,FOLLOW_12_in_ruleFeature880); 

                            createLeafNode(grammarAccess.getFeatureAccess().getRetainedRetainKeyword_1_0(), "retained"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getFeatureRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		set(current, "retained", true, "retain", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }
                    break;

            }

            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:442:3: (lv_feature_2= RULE_ID )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:444:6: lv_feature_2= RULE_ID
            {
            lv_feature_2=(Token)input.LT(1);
            match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleFeature916); 

            		createLeafNode(grammarAccess.getFeatureAccess().getFeatureIDTerminalRuleCall_2_0(), "feature"); 
            	

            	        if (current==null) {
            	            current = factory.create(grammarAccess.getFeatureRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode, current);
            	        }
            	        
            	        try {
            	       		set(current, "feature", lv_feature_2, "ID", lastConsumedNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	    

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleFeature


    // $ANTLR start entryRuleOrExpression
    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:469:1: entryRuleOrExpression returns [EObject current=null] : iv_ruleOrExpression= ruleOrExpression EOF ;
    public final EObject entryRuleOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrExpression = null;


        try {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:469:54: (iv_ruleOrExpression= ruleOrExpression EOF )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:470:2: iv_ruleOrExpression= ruleOrExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getOrExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleOrExpression_in_entryRuleOrExpression957);
            iv_ruleOrExpression=ruleOrExpression();
            _fsp--;

             current =iv_ruleOrExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOrExpression967); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleOrExpression


    // $ANTLR start ruleOrExpression
    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:477:1: ruleOrExpression returns [EObject current=null] : ( (lv_operands_0= ruleAndExpression ) ( 'or' (lv_operands_2= ruleAndExpression ) )* ) ;
    public final EObject ruleOrExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_operands_0 = null;

        EObject lv_operands_2 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:482:6: ( ( (lv_operands_0= ruleAndExpression ) ( 'or' (lv_operands_2= ruleAndExpression ) )* ) )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:483:1: ( (lv_operands_0= ruleAndExpression ) ( 'or' (lv_operands_2= ruleAndExpression ) )* )
            {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:483:1: ( (lv_operands_0= ruleAndExpression ) ( 'or' (lv_operands_2= ruleAndExpression ) )* )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:483:2: (lv_operands_0= ruleAndExpression ) ( 'or' (lv_operands_2= ruleAndExpression ) )*
            {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:483:2: (lv_operands_0= ruleAndExpression )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:486:6: lv_operands_0= ruleAndExpression
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getOrExpressionAccess().getOperandsAndExpressionParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression1026);
            lv_operands_0=ruleAndExpression();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getOrExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        
            	        try {
            	       		add(current, "operands", lv_operands_0, "AndExpression", currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }

            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:504:2: ( 'or' (lv_operands_2= ruleAndExpression ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==19) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:504:3: 'or' (lv_operands_2= ruleAndExpression )
            	    {
            	    match(input,19,FOLLOW_19_in_ruleOrExpression1040); 

            	            createLeafNode(grammarAccess.getOrExpressionAccess().getOrKeyword_1_0(), null); 
            	        
            	    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:508:1: (lv_operands_2= ruleAndExpression )
            	    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:511:6: lv_operands_2= ruleAndExpression
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getOrExpressionAccess().getOperandsAndExpressionParserRuleCall_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleAndExpression_in_ruleOrExpression1074);
            	    lv_operands_2=ruleAndExpression();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getOrExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        
            	    	        try {
            	    	       		add(current, "operands", lv_operands_2, "AndExpression", currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleOrExpression


    // $ANTLR start entryRuleAndExpression
    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:536:1: entryRuleAndExpression returns [EObject current=null] : iv_ruleAndExpression= ruleAndExpression EOF ;
    public final EObject entryRuleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAndExpression = null;


        try {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:536:55: (iv_ruleAndExpression= ruleAndExpression EOF )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:537:2: iv_ruleAndExpression= ruleAndExpression EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAndExpressionRule(), currentNode); 
            pushFollow(FOLLOW_ruleAndExpression_in_entryRuleAndExpression1113);
            iv_ruleAndExpression=ruleAndExpression();
            _fsp--;

             current =iv_ruleAndExpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAndExpression1123); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleAndExpression


    // $ANTLR start ruleAndExpression
    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:544:1: ruleAndExpression returns [EObject current=null] : ( (lv_operands_0= ruleOperand ) ( 'and' (lv_operands_2= ruleOperand ) )* ) ;
    public final EObject ruleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_operands_0 = null;

        EObject lv_operands_2 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:549:6: ( ( (lv_operands_0= ruleOperand ) ( 'and' (lv_operands_2= ruleOperand ) )* ) )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:550:1: ( (lv_operands_0= ruleOperand ) ( 'and' (lv_operands_2= ruleOperand ) )* )
            {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:550:1: ( (lv_operands_0= ruleOperand ) ( 'and' (lv_operands_2= ruleOperand ) )* )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:550:2: (lv_operands_0= ruleOperand ) ( 'and' (lv_operands_2= ruleOperand ) )*
            {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:550:2: (lv_operands_0= ruleOperand )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:553:6: lv_operands_0= ruleOperand
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getAndExpressionAccess().getOperandsOperandParserRuleCall_0_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleOperand_in_ruleAndExpression1182);
            lv_operands_0=ruleOperand();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getAndExpressionRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        
            	        try {
            	       		add(current, "operands", lv_operands_0, "Operand", currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }

            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:571:2: ( 'and' (lv_operands_2= ruleOperand ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==20) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:571:3: 'and' (lv_operands_2= ruleOperand )
            	    {
            	    match(input,20,FOLLOW_20_in_ruleAndExpression1196); 

            	            createLeafNode(grammarAccess.getAndExpressionAccess().getAndKeyword_1_0(), null); 
            	        
            	    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:575:1: (lv_operands_2= ruleOperand )
            	    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:578:6: lv_operands_2= ruleOperand
            	    {
            	     
            	    	        currentNode=createCompositeNode(grammarAccess.getAndExpressionAccess().getOperandsOperandParserRuleCall_1_1_0(), currentNode); 
            	    	    
            	    pushFollow(FOLLOW_ruleOperand_in_ruleAndExpression1230);
            	    lv_operands_2=ruleOperand();
            	    _fsp--;


            	    	        if (current==null) {
            	    	            current = factory.create(grammarAccess.getAndExpressionRule().getType().getClassifier());
            	    	            associateNodeWithAstElement(currentNode.getParent(), current);
            	    	        }
            	    	        
            	    	        try {
            	    	       		add(current, "operands", lv_operands_2, "Operand", currentNode);
            	    	        } catch (ValueConverterException vce) {
            	    				handleValueConverterException(vce);
            	    	        }
            	    	        currentNode = currentNode.getParent();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleAndExpression


    // $ANTLR start entryRuleOperand
    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:603:1: entryRuleOperand returns [EObject current=null] : iv_ruleOperand= ruleOperand EOF ;
    public final EObject entryRuleOperand() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOperand = null;


        try {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:603:49: (iv_ruleOperand= ruleOperand EOF )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:604:2: iv_ruleOperand= ruleOperand EOF
            {
             currentNode = createCompositeNode(grammarAccess.getOperandRule(), currentNode); 
            pushFollow(FOLLOW_ruleOperand_in_entryRuleOperand1269);
            iv_ruleOperand=ruleOperand();
            _fsp--;

             current =iv_ruleOperand; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleOperand1279); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleOperand


    // $ANTLR start ruleOperand
    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:611:1: ruleOperand returns [EObject current=null] : ( (lv_isNot_0= 'not' )? (lv_expression_1= ruleAtom ) ) ;
    public final EObject ruleOperand() throws RecognitionException {
        EObject current = null;

        Token lv_isNot_0=null;
        EObject lv_expression_1 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:616:6: ( ( (lv_isNot_0= 'not' )? (lv_expression_1= ruleAtom ) ) )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:617:1: ( (lv_isNot_0= 'not' )? (lv_expression_1= ruleAtom ) )
            {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:617:1: ( (lv_isNot_0= 'not' )? (lv_expression_1= ruleAtom ) )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:617:2: (lv_isNot_0= 'not' )? (lv_expression_1= ruleAtom )
            {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:617:2: (lv_isNot_0= 'not' )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==21) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:619:6: lv_isNot_0= 'not'
                    {
                    lv_isNot_0=(Token)input.LT(1);
                    match(input,21,FOLLOW_21_in_ruleOperand1325); 

                            createLeafNode(grammarAccess.getOperandAccess().getIsNotNotKeyword_0_0(), "isNot"); 
                        

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getOperandRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		set(current, "isNot", true, "not", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }
                    break;

            }

            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:638:3: (lv_expression_1= ruleAtom )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:641:6: lv_expression_1= ruleAtom
            {
             
            	        currentNode=createCompositeNode(grammarAccess.getOperandAccess().getExpressionAtomParserRuleCall_1_0(), currentNode); 
            	    
            pushFollow(FOLLOW_ruleAtom_in_ruleOperand1373);
            lv_expression_1=ruleAtom();
            _fsp--;


            	        if (current==null) {
            	            current = factory.create(grammarAccess.getOperandRule().getType().getClassifier());
            	            associateNodeWithAstElement(currentNode.getParent(), current);
            	        }
            	        
            	        try {
            	       		set(current, "expression", lv_expression_1, "Atom", currentNode);
            	        } catch (ValueConverterException vce) {
            				handleValueConverterException(vce);
            	        }
            	        currentNode = currentNode.getParent();
            	    

            }


            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleOperand


    // $ANTLR start entryRuleAtom
    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:666:1: entryRuleAtom returns [EObject current=null] : iv_ruleAtom= ruleAtom EOF ;
    public final EObject entryRuleAtom() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAtom = null;


        try {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:666:46: (iv_ruleAtom= ruleAtom EOF )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:667:2: iv_ruleAtom= ruleAtom EOF
            {
             currentNode = createCompositeNode(grammarAccess.getAtomRule(), currentNode); 
            pushFollow(FOLLOW_ruleAtom_in_entryRuleAtom1410);
            iv_ruleAtom=ruleAtom();
            _fsp--;

             current =iv_ruleAtom; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAtom1420); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end entryRuleAtom


    // $ANTLR start ruleAtom
    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:674:1: ruleAtom returns [EObject current=null] : ( (lv_feature_0= RULE_ID ) | ( '(' (lv_expression_2= ruleOrExpression ) ')' ) ) ;
    public final EObject ruleAtom() throws RecognitionException {
        EObject current = null;

        Token lv_feature_0=null;
        EObject lv_expression_2 = null;


         EObject temp=null; setCurrentLookahead(); resetLookahead(); 
            
        try {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:679:6: ( ( (lv_feature_0= RULE_ID ) | ( '(' (lv_expression_2= ruleOrExpression ) ')' ) ) )
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:680:1: ( (lv_feature_0= RULE_ID ) | ( '(' (lv_expression_2= ruleOrExpression ) ')' ) )
            {
            // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:680:1: ( (lv_feature_0= RULE_ID ) | ( '(' (lv_expression_2= ruleOrExpression ) ')' ) )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==RULE_ID) ) {
                alt11=1;
            }
            else if ( (LA11_0==13) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("680:1: ( (lv_feature_0= RULE_ID ) | ( '(' (lv_expression_2= ruleOrExpression ) ')' ) )", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:680:2: (lv_feature_0= RULE_ID )
                    {
                    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:680:2: (lv_feature_0= RULE_ID )
                    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:682:6: lv_feature_0= RULE_ID
                    {
                    lv_feature_0=(Token)input.LT(1);
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleAtom1467); 

                    		createLeafNode(grammarAccess.getAtomAccess().getFeatureIDTerminalRuleCall_0_0(), "feature"); 
                    	

                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getAtomRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode, current);
                    	        }
                    	        
                    	        try {
                    	       		set(current, "feature", lv_feature_0, "ID", lastConsumedNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	    

                    }


                    }
                    break;
                case 2 :
                    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:701:6: ( '(' (lv_expression_2= ruleOrExpression ) ')' )
                    {
                    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:701:6: ( '(' (lv_expression_2= ruleOrExpression ) ')' )
                    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:701:7: '(' (lv_expression_2= ruleOrExpression ) ')'
                    {
                    match(input,13,FOLLOW_13_in_ruleAtom1491); 

                            createLeafNode(grammarAccess.getAtomAccess().getLeftParenthesisKeyword_1_0(), null); 
                        
                    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:705:1: (lv_expression_2= ruleOrExpression )
                    // ../org.openarchitectureware.var.features/src-gen/org/openarchitectureware/var/features/parser/antlr/internal/InternalFeatures.g:708:6: lv_expression_2= ruleOrExpression
                    {
                     
                    	        currentNode=createCompositeNode(grammarAccess.getAtomAccess().getExpressionOrExpressionParserRuleCall_1_1_0(), currentNode); 
                    	    
                    pushFollow(FOLLOW_ruleOrExpression_in_ruleAtom1525);
                    lv_expression_2=ruleOrExpression();
                    _fsp--;


                    	        if (current==null) {
                    	            current = factory.create(grammarAccess.getAtomRule().getType().getClassifier());
                    	            associateNodeWithAstElement(currentNode.getParent(), current);
                    	        }
                    	        
                    	        try {
                    	       		set(current, "expression", lv_expression_2, "OrExpression", currentNode);
                    	        } catch (ValueConverterException vce) {
                    				handleValueConverterException(vce);
                    	        }
                    	        currentNode = currentNode.getParent();
                    	    

                    }

                    match(input,15,FOLLOW_15_in_ruleAtom1538); 

                            createLeafNode(grammarAccess.getAtomAccess().getRightParenthesisKeyword_1_2(), null); 
                        

                    }


                    }
                    break;

            }


            }

             resetLookahead(); 
                	lastConsumedNode = currentNode;
                
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end ruleAtom


 

    public static final BitSet FOLLOW_ruleFeatureClause_in_entryRuleFeatureClause73 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFeatureClause83 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFeatureAndList_in_ruleFeatureClause130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFeatureOrList_in_ruleFeatureClause157 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFeatureExpression_in_ruleFeatureClause184 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFeature_in_ruleFeatureClause211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFeatureAndList_in_entryRuleFeatureAndList243 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFeatureAndList253 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_ruleFeatureAndList287 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_12_in_ruleFeatureAndList308 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_ruleFeatureAndList331 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFeatureAndList353 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_14_in_ruleFeatureAndList371 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFeatureAndList393 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_15_in_ruleFeatureAndList412 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFeatureOrList_in_entryRuleFeatureOrList445 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFeatureOrList455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_ruleFeatureOrList489 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_12_in_ruleFeatureOrList510 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_ruleFeatureOrList533 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFeatureOrList555 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_14_in_ruleFeatureOrList573 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFeatureOrList595 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_15_in_ruleFeatureOrList614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFeatureExpression_in_entryRuleFeatureExpression647 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFeatureExpression657 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_ruleFeatureExpression691 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_12_in_ruleFeatureExpression712 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_ruleFeatureExpression735 = new BitSet(new long[]{0x0000000000202010L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleFeatureExpression769 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleFeatureExpression782 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFeature_in_entryRuleFeature815 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFeature825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_ruleFeature859 = new BitSet(new long[]{0x0000000000001010L});
    public static final BitSet FOLLOW_12_in_ruleFeature880 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleFeature916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOrExpression_in_entryRuleOrExpression957 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOrExpression967 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression1026 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_19_in_ruleOrExpression1040 = new BitSet(new long[]{0x0000000000202010L});
    public static final BitSet FOLLOW_ruleAndExpression_in_ruleOrExpression1074 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_ruleAndExpression_in_entryRuleAndExpression1113 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAndExpression1123 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOperand_in_ruleAndExpression1182 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_20_in_ruleAndExpression1196 = new BitSet(new long[]{0x0000000000202010L});
    public static final BitSet FOLLOW_ruleOperand_in_ruleAndExpression1230 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_ruleOperand_in_entryRuleOperand1269 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOperand1279 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_ruleOperand1325 = new BitSet(new long[]{0x0000000000002010L});
    public static final BitSet FOLLOW_ruleAtom_in_ruleOperand1373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAtom_in_entryRuleAtom1410 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAtom1420 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleAtom1467 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_ruleAtom1491 = new BitSet(new long[]{0x0000000000202010L});
    public static final BitSet FOLLOW_ruleOrExpression_in_ruleAtom1525 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleAtom1538 = new BitSet(new long[]{0x0000000000000002L});

}