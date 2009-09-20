package org.openarchitectureware.var.featureaccess.emf;

import java.io.InputStream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.mwe.core.resources.ResourceLoaderFactory;
import org.eclipse.emf.mwe.utils.StandaloneSetup;
import org.eclipse.xtend.expression.ExecutionContext;
import org.eclipse.xtend.expression.ExecutionContextImpl;
import org.eclipse.xtend.expression.ExpressionFacade;
import org.eclipse.xtend.expression.Resource;
import org.eclipse.xtend.expression.Variable;
import org.eclipse.xtend.typesystem.emf.EmfRegistryMetaModel;

public class ExpressionWrapper {

	private String filename;
	private ExecutionContext executionContext;
	private EObject configModel;

	public ExpressionWrapper( String filename, EObject configModel ) {
		this.filename = filename;
		this.configModel = configModel;
		init();
	}

	public boolean fileExists() {
        final InputStream in = getExtFileIS();
        return in != null;
	}

	public void init() {
		try {
			StandaloneSetup s = new StandaloneSetup();
			s.addRegisterEcoreFile("platform:/resource/org.openarchitectureware.var.featureaccess/src/org/openarchitectureware/var/featureaccess/emf/featureconfig.ecore");
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		ExecutionContextImpl ecTemp = new ExecutionContextImpl();
		EmfRegistryMetaModel mm = new EmfRegistryMetaModel();
		mm.setUseSingleGlobalResourceSet(true);
		ecTemp.registerMetaModel( mm );
		executionContext = ecTemp.cloneWithResource(new Resource() {
            private String name = "EMFFeatureConfigurationModel expressions";

            public String getFullyQualifiedName() {
                return name;
            }

            public void setFullyQualifiedName(final String fqn) {
                name = fqn;
            }

            public String[] getImportedNamespaces() {
                return new String[0];
            }

            public String[] getImportedExtensions() {
                return new String[] { filename };
            }
        });
		executionContext = executionContext.cloneWithVariable( new Variable ("config", configModel ) );
	}
	
	public Object evaluateExpression( String expression ) {
        final Object result = new ExpressionFacade(executionContext).evaluate(expression);
        return result;
	}
	
	public Object evaluate( String expressionName ) {
		return evaluateExpression(expressionName+"(config)");
	}
	
    private InputStream getExtFileIS() {
        final InputStream in = ResourceLoaderFactory.createResourceLoader().getResourceAsStream(
        		filename.replace("::", "/")+".ext");
        return in;
    }

	
}
