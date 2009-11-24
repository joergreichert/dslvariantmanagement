package org.openarchitectureware.var.featureaccess.ext;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parsetree.SyntaxError;
import org.openarchitectureware.var.features.FeaturesStandaloneSetup;
import org.openarchitectureware.var.features.parser.antlr.FeaturesParser;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * This singleton class is used for following purposes:
 * <ul>
 * <li>injecting and calling feature parser
 * <li>converting string to <em>EObject</em>
 * <li>calling FeatureSupport on EObject for evaluating the feature
 * </ul>
 * 
 */
public final class FeatureStringSupport {
	
	public static Log log = LogFactory.getLog(FeatureStringSupport.class);

	@Inject
	private FeaturesParser parser;

	private static FeatureStringSupport instance = null;

	public boolean isFeatureClauseTrue(String featureClause) {
		InputStream featureClauseIS = new ByteArrayInputStream(featureClause
				.getBytes());

		IParseResult result = parser.parse("FeatureClause", featureClauseIS);
		if(result.getParseErrors().size() > 0){
			String message = "";
			for (SyntaxError err : result.getParseErrors()) 
				message += err.getMessage() + "\n";
			Exception e = new RuntimeException("parse error: " + message);
			log.error("error while parsing feature clause", e);
		}
		EObject eFeatureClause = result.getRootASTElement();
		return FeatureSupport.isFeatureClauseTrue(eFeatureClause);
	}

	public synchronized static FeatureStringSupport getInstance() {
		if (instance == null) {
			FeaturesStandaloneSetup.doSetup();
			Injector injector = Guice
					.createInjector(new org.openarchitectureware.var.features.FeaturesRuntimeModule());

			instance = injector.getInstance(FeatureStringSupport.class);
		}
		return instance;
	}
}
