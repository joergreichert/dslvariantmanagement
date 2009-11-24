package org.openarchitectureware.var.tailor.code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.mwe.core.issues.Issues;
import org.openarchitectureware.var.featureaccess.FeatureDependentComponent;

public abstract class BaseComponent extends FeatureDependentComponent {

	protected static final int NONE_FOUND = -1;
	protected static final String SINGLE_LINE_COMMENT_DEFAULT = "//";

	protected static final String FILENAME_DELIMITER = "#";

	protected static final String PLATFORM_RESOURCE = "platform:/resource/";
	protected static final String SOURCE_EXT = ".v";
	protected String sourcePath;
	protected String genPath;
	protected String fullSourcePath;
	protected String fullGenPath;
	protected Issues issues;
	protected String singleLineCommentsLiteral;

	public String getLogMessage() {
		return "processing variability in files in '" + sourcePath + "' to '"
				+ genPath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public void setGenPath(String genPath) {
		this.genPath = genPath;
	}

	public void setSingleLineCommentsLiteral(String literal) {
		this.singleLineCommentsLiteral = literal;
	}

	protected String singleLineCommentLiteral() {
		if (singleLineCommentsLiteral != null) {
			return singleLineCommentsLiteral;
		} else {
			return SINGLE_LINE_COMMENT_DEFAULT;
		}
	}

	public void checkConfigurationInternal(Issues issues) {
		super.checkConfigurationInternal(issues);
		if (sourcePath == null)
			issues.addError("no sourcePath specified");
		if (genPath == null)
			issues.addError("no genPath specified");
		if (!isPlatformRelative(sourcePath))
			issues
					.addError("sourcePath must be platform relative (platform:/resource/...");
		if (!isPlatformRelative(genPath))
			issues
					.addError("genPath must be platform relative (platform:/resource/...");
	}

	protected boolean isPlatformRelative(String p) {
		return p.startsWith(PLATFORM_RESOURCE);
	}

	protected String normalizePath(String p) {
		return p.replace('\\', '/');
	}

	protected List<String> loadFile(File file) throws FileNotFoundException,
			IOException {
		List<String> lines = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		while (br.ready()) {
			String line = br.readLine();
			lines.add(line);
		}
		br.close();
		return lines;
	}

	protected void collect(File dir, List<File> files) {
		collect(dir, files, SOURCE_EXT);
	}
	
	protected void collect(File dir, List<File> files, String ext) {
		File[] contents = dir.listFiles();
		if (contents == null)
			return;
		for (File file : contents) {
			if (file.isDirectory()) {
				collect(file, files, ext);
			}
			if (file.getName().endsWith(ext)) {
				files.add(file);
			}
		}
	}

	protected String pathAppend(String platformRoot, String platformRelativePath) {
		if (platformRelativePath.startsWith(PLATFORM_RESOURCE))
			platformRelativePath = platformRelativePath
					.substring(PLATFORM_RESOURCE.length());
		if (platformRoot.endsWith("\\") || platformRoot.endsWith("/"))
			return platformRoot + platformRelativePath;
		return platformRoot + "/" + platformRelativePath;
	}

	protected String buildTargetFileName(File file) {
		String name = normalizePath(file.getAbsolutePath());
		if (name.endsWith(SOURCE_EXT))
			name = name.substring(0, name.length() - SOURCE_EXT.length());
		if (name.startsWith(fullSourcePath))
			name = fullGenPath + name.substring(fullSourcePath.length());
		if (name.contains(FILENAME_DELIMITER)) {
			int hashPos = name.indexOf(FILENAME_DELIMITER);
			int dotPos = name.indexOf(".", hashPos);
			name = name.substring(0, hashPos) + name.substring(dotPos);
		}
		return normalizePath(name);
	}

}
