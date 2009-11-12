package org.openarchitectureware.var.featureaccess;

import java.util.ArrayList;

import org.eclipse.emf.mwe.core.ConfigurationException;
import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.WorkflowContextDefaultImpl;
import org.eclipse.emf.mwe.core.WorkflowInterruptedException;
import org.eclipse.emf.mwe.core.config.FeatureComponent;
import org.eclipse.emf.mwe.core.config.GlobalConfigurationHolder;
import org.eclipse.emf.mwe.core.container.ConditionEvaluationException;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.issues.IssuesImpl;
import org.eclipse.emf.mwe.core.monitor.NullProgressMonitor;
import org.eclipse.xtend.expression.ExecutionContext;
import org.eclipse.xtend.expression.ExecutionContextImpl;
import org.eclipse.xtend.expression.ExpressionFacade;
import org.eclipse.xtend.expression.Resource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.openarchitectureware.var.featureaccess.ext.FeatureSupport;
import org.openarchitectureware.var.featureaccess.fmp.FMPModelIterator;
import org.openarchitectureware.var.featureaccess.pv.PVModelIterator;
import org.openarchitectureware.var.featureaccess.text.TextModelIterator;

public class FeatureAccessFromWorkflowTest implements TestData {

	@Test
	public void testFeatureAccessFromWorkflowForPV() {
		executeFeatureAccessFromWorkflowTest(TestData.pvConfigurationModelURI);
	}

	@Test
	public void testFeatureAccessFromWorkflowForFMP() {
		executeFeatureAccessFromWorkflowTest(TestData.fmpModelURI);
	}

	@Test
	public void testFeatureAccessFromWorkflowForText() {
		executeFeatureAccessFromWorkflowTest(TestData.textFeatureModelURI);
	}

	@Test
	public void testFeatureAccessFromWorkflowWithNullFile() {
		try {
			executeFeatureAccessFromWorkflowTest(null);
			Assert
					.fail("Should have thrown a configuration exception as no model is set.");
		} catch (ConfigurationException ce) {
			Assert.assertEquals("No configuration model file is set.", ce
					.getMessage());
		}
	}

	@Test
	public void testFeatureAccessFromWorkflowWithEmptyFileString() {
		try {
			executeFeatureAccessFromWorkflowTest("");
			Assert.fail("Should have thrown a configuration exception "
					+ "as the provided path string is empty.");
		} catch (ConfigurationException ce) {
			Assert.assertEquals("The given path to the "
					+ "configuration model file is an empty string.", ce
					.getMessage());
		}
	}

	@Test
	public void testFeatureAccessFromWorkflowWithNotExistingFile() {
		try {
			executeFeatureAccessFromWorkflowTest("illegalpath/nonexisting.file");
			Assert.fail("Should have thrown a configuration exception as the "
					+ "given path points to a not existing file.");
		} catch (ConfigurationException ce) {
			Assert.assertEquals("The given configuration model path "
					+ "points to a non existing file.", ce.getMessage());
		}
	}

	@Test
	public void testFeatureAccessFromWorkflowWithNotSupportedFileExtension() {
		try {
			executeFeatureAccessFromWorkflowTest("model/example.xmi");
			Assert.fail("Should have thrown a configuration exception "
					+ "as the model is not supported.");
		} catch (ConfigurationException ce) {
			Assert.assertEquals(
					"The provided configuration model is not supported.", ce
							.getMessage());
		}
	}

	@Test
	public void testFeatureAccessFromWorkflowWithFaultyPVConfigurationModel() {
		try {
			executeFeatureAccessFromWorkflowTest("model/faulty.vdm.xmi");
			Assert.fail("Should have thrown a workflow interrupted exception "
					+ "as a the given model should cause a parsing error.");
		} catch (WorkflowInterruptedException wie) {
			Assert.assertTrue(wie.getMessage()
					.startsWith("Error when parsing "));
		}
	}

	@Test
	public void testFeatureAccessFromWorkflowWithEmptyPVConfigurationModel() {
		try {
			executeFeatureAccessFromWorkflowTest("model/empty.vdm.xmi");
			Assert
					.fail("Should have thrown condition evaluation exception "
							+ "as a the given model does not contain the expected element.");
		} catch (ConditionEvaluationException cee) {
			Assert.assertTrue(cee.getMessage().endsWith(
					" is not defined in the configuration model."));
		}
	}

	@Test
	public void testFeatureAccessFromWorkflowWithFaultyFmpConfigurationModel() {
		try {
			executeFeatureAccessFromWorkflowTest("model/faulty.fmp");
			Assert.fail("Should have thrown a workflow interrupted exception "
					+ "as a the given model should cause a parsing error.");
		} catch (WorkflowInterruptedException wie) {
			Assert.assertTrue(wie.getMessage()
					.startsWith("Error when parsing "));
		}
	}

	@Test
	public void testFeatureAccessFromWorkflowWithEmptyFmpConfigurationModel() {
		try {
			executeFeatureAccessFromWorkflowTest("model/empty.fmp");
			Assert
					.fail("Should have thrown condition evaluation exception "
							+ "as a the given model does not contain the expected element.");
		} catch (WorkflowInterruptedException wie) {
			Assert.assertEquals("Given FMP configuration model is invalid, "
					+ "as no model specification could be found "
					+ "in the model file.", wie.getMessage());
		}
	}

	@Test
	public void testFeatureAccessFromWorkflowForFaultyText() {
		try {
			executeFeatureAccessFromWorkflowTest("model/faulty.txt");
			Assert
					.fail("Should have thrown a workflow interrupted exception "
							+ "as a the given model does not contain the expected element.");
		} catch (WorkflowInterruptedException wie) {
			Assert
					.assertEquals(
							"The given text configuration model is invalid, "
									+ "as in the model file every line have to start with + or -.",
							wie.getMessage());
		}
	}

	@Test
	public void testFeatureAccessFromWorkflowForEmptyText() {
		try {
			executeFeatureAccessFromWorkflowTest("model/empty.txt");
			Assert
					.fail("Should have thrown workflow interrupted evaluation exception "
							+ "as a the given model does not contain anything.");
		} catch (WorkflowInterruptedException wie) {
			Assert.assertEquals(
					"The given text configuration model is invalid, "
							+ "as no entries could be found "
							+ "in the model file.", wie.getMessage());
		}
	}

	private void executeFeatureAccessFromWorkflowTest(String file)
			throws ConfigurationException, WorkflowInterruptedException,
			ConditionEvaluationException {
		Issues issues = new IssuesImpl();
		ConfigurationModelReader reader = new ConfigurationModelReader();
		reader.setConfigurationModelFile(file);
		WorkflowContext ctx = new WorkflowContextDefaultImpl();
		reader.checkConfiguration(issues);
		reader.invoke(ctx, new NullProgressMonitor(), issues);
		FeatureComponent featureComponent = new FeatureComponent();
		featureComponent.setIsSelected("Security");
		boolean succesful = featureComponent.evaluate();
		Assert.assertTrue("feature should be selected in " + file, succesful);
	}

	@Test
	public void testTextFeatureAccessFromXtendTest()
			throws ConfigurationException, WorkflowInterruptedException,
			ConditionEvaluationException {
		String configurationModelFile = "model/example.txt";
		String expectedFeatureList = "[CollaborationSystemFeatures, "
				+ "AccessManagement, Security, Authentication, "
				+ "Encryption, AsynchronousConferencing, "
				+ "SynchronousConferencing, DocumentRepository, "
				+ "Forum, MicroBlogging, Text, Audio, Video, Data, "
				+ "TelephoneIntegration, VoiceOverIP, VNC, X11]";
		executeFeatureAccessFromXtendTest(configurationModelFile,
				expectedFeatureList);
	}

	@Test
	public void testPVFeatureAccessFromXtendTest()
			throws ConfigurationException, WorkflowInterruptedException,
			ConditionEvaluationException {
		String configurationModelFile = "model/org.openarchitectureware.demo.fm.vdm.xmi";
		String expectedFeatureList = "[CollaborationSystemFeatures, "
				+ "AccessManagement, Security, Authentication, "
				+ "Encryption, AsynchronousConferencing, "
				+ "SynchronousConferencing, DocumentRepository, "
				+ "Forum, MicroBlogging, Text, Audio, Video, Data, "
				+ "TelephoneIntegration, VoiceOverIP, VNC, X11]";
		executeFeatureAccessFromXtendTest(configurationModelFile,
				expectedFeatureList);
	}

	@Test
	public void testFmpFeatureAccessFromXtendTest()
			throws ConfigurationException, WorkflowInterruptedException,
			ConditionEvaluationException {
		String configurationModelFile = "model/example.fmp";
		String expectedFeatureList = "[CollaborationSystemFeatures, "
				+ "AccessManagement, Security, Authentication, "
				+ "Encryption, AsynchronousConferencing, "
				+ "SynchronousConferencing, DocumentRepository, "
				+ "Forum, MicroBlogging, Text, Audio, Video, Data, "
				+ "TelephoneIntegration, VoiceOverIP, VNC, X11]";
		executeFeatureAccessFromXtendTest(configurationModelFile,
				expectedFeatureList);
	}

	private void executeFeatureAccessFromXtendTest(
			String configurationModelFile, String expectedFeatureList)
			throws ConfigurationException, WorkflowInterruptedException,
			ConditionEvaluationException {
		Issues issues = new IssuesImpl();
		ConfigurationModelReader reader = new ConfigurationModelReader();
		reader.setConfigurationModelFile(configurationModelFile);
		WorkflowContext ctx = new WorkflowContextDefaultImpl();
		reader.checkConfiguration(issues);
		reader.invoke(ctx, new NullProgressMonitor(), issues);
		ExecutionContext execCtx = new ExecutionContextImpl()
				.cloneWithResource(new Resource() {
					private String name = "EMFFeatureConfigurationModel expressions";
					private String extensionImport = "org::openarchitectureware::"
							+ "var::featureaccess::ext::utils";

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
						return new String[] { extensionImport };
					}
				});
		ExpressionFacade facade = new ExpressionFacade(execCtx);
		Object object = facade.evaluate("getAllFeatures(\""
				+ configurationModelFile + "\")");
		Assert.assertTrue("getAllFeatures returns array list expected",
				object instanceof ArrayList<?>);
		ArrayList<String> features = convertToArrayListOfStrings(object);
		Assert.assertEquals("the fetched features list should "
				+ "equal to the expected one", expectedFeatureList.toString(),
				features.toString());
	}

	@SuppressWarnings("unchecked")
	private ArrayList<String> convertToArrayListOfStrings(Object object) {
		return (ArrayList<String>) object;
	}

	@After
	public void tearDown() {
		GlobalConfigurationHolder.setConfigurationModel(null, new IssuesImpl());
		FeatureSupport.setConfigurationModelWrapper(null);
		PVModelIterator.reset();
		FMPModelIterator.reset();
		TextModelIterator.reset();
	}
}
