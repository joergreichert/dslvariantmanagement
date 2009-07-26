/**************************************************************************************
 * Copyright (c) 2005, 2006 Generative Software Development Lab, University of Waterloo
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   1. Generative Software Development Lab, University of Waterloo,
 *      http://gp.uwaterloo.ca  - initial API and implementation
 **************************************************************************************/

// $ANTLR 2.7.5 (20050201): "t5.g" -> "BooleanExpressionParser.java"$

package ca.uwaterloo.gp.fmp.constraints.JavaBDDConfigurator;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.AST;
import antlr.collections.impl.BitSet;

public class BooleanExpressionParser extends antlr.LLkParser       implements BooleanExpressionParserTokenTypes
 {

protected BooleanExpressionParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public BooleanExpressionParser(TokenBuffer tokenBuf) {
  this(tokenBuf,3);
}

protected BooleanExpressionParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public BooleanExpressionParser(TokenStream lexer) {
  this(lexer,3);
}

public BooleanExpressionParser(ParserSharedInputState state) {
  super(state,3);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void primitiveElement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST primitiveElement_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LPAREN:
			case IDENT:
			{
				variableReference();
				astFactory.addASTChild(currentAST, returnAST);
				{
				_loop4:
				do {
					if ((LA(1)==EQUALS)) {
						AST tmp9_AST = null;
						tmp9_AST = astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp9_AST);
						match(EQUALS);
						{
						switch ( LA(1)) {
						case TRUE:
						{
							AST tmp10_AST = null;
							tmp10_AST = astFactory.create(LT(1));
							astFactory.addASTChild(currentAST, tmp10_AST);
							match(TRUE);
							break;
						}
						case FALSE:
						{
							AST tmp11_AST = null;
							tmp11_AST = astFactory.create(LT(1));
							astFactory.addASTChild(currentAST, tmp11_AST);
							match(FALSE);
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
					}
					else {
						break _loop4;
					}
					
				} while (true);
				}
				primitiveElement_AST = currentAST.root;
				break;
			}
			case TRUE:
			{
				AST tmp12_AST = null;
				tmp12_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp12_AST);
				match(TRUE);
				primitiveElement_AST = currentAST.root;
				break;
			}
			case FALSE:
			{
				AST tmp13_AST = null;
				tmp13_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp13_AST);
				match(FALSE);
				primitiveElement_AST = currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
		returnAST = primitiveElement_AST;
	}
	
	public final void variableReference() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST variableReference_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case IDENT:
			{
				atom();
				astFactory.addASTChild(currentAST, returnAST);
				variableReference_AST = currentAST.root;
				break;
			}
			case LPAREN:
			{
				AST tmp14_AST = null;
				tmp14_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp14_AST);
				match(LPAREN);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				match(RPAREN);
				variableReference_AST = currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
		returnAST = variableReference_AST;
	}
	
	public final void booleanNegationExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST booleanNegationExpression_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case NOT:
			{
				AST tmp16_AST = null;
				tmp16_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp16_AST);
				match(NOT);
				break;
			}
			case TRUE:
			case FALSE:
			case LPAREN:
			case IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			primitiveElement();
			astFactory.addASTChild(currentAST, returnAST);
			booleanNegationExpression_AST = currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
		returnAST = booleanNegationExpression_AST;
	}
	
	public final void expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_AST = null;
		
		try {      // for error handling
			booleanExpression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop10:
			do {
				if ((LA(1)==IMPLIES)) {
					{
					AST tmp17_AST = null;
					tmp17_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp17_AST);
					match(IMPLIES);
					}
					booleanExpression();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop10;
				}
				
			} while (true);
			}
			expression_AST = currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
		returnAST = expression_AST;
	}
	
	public final void booleanExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST booleanExpression_AST = null;
		
		try {      // for error handling
			booleanNegationExpression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop14:
			do {
				if ((LA(1)==AND||LA(1)==OR)) {
					{
					switch ( LA(1)) {
					case AND:
					{
						AST tmp18_AST = null;
						tmp18_AST = astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp18_AST);
						match(AND);
						break;
					}
					case OR:
					{
						AST tmp19_AST = null;
						tmp19_AST = astFactory.create(LT(1));
						astFactory.makeASTRoot(currentAST, tmp19_AST);
						match(OR);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					booleanNegationExpression();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop14;
				}
				
			} while (true);
			}
			booleanExpression_AST = currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
		returnAST = booleanExpression_AST;
	}
	
	public final void atom() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST atom_AST = null;
		
		try {      // for error handling
			AST tmp20_AST = null;
			tmp20_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp20_AST);
			match(IDENT);
			atom_AST = currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
		returnAST = atom_AST;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"\"and\"",
		"\"or\"",
		"\"not\"",
		"\"true\"",
		"\"false\"",
		"\"for all\"",
		"\"exists\"",
		"EQUALS",
		"IMPLIES",
		"LPAREN",
		"RPAREN",
		"IDENT",
		"WS"
	};
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 20528L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 22576L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 16384L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 20480L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	
	}
