package org.openarchitectureware.var.tailor.code;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.mwe.utils.StandaloneSetup;
import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;
import org.openarchitectureware.var.featureaccess.FeatureModelWrapper;

public class WeaverComponent extends BaseComponent {
	
	private static final String JOINPOINT_SECTION_BEGIN = ">";
	private static final String JOINPOINT_SECTION_END = "~>";
	
	private static final String ADVICE_SECTION_BEGIN = "+";
	private static final String ADVICE_SECTION_END = "~+";

	private String joinpointBeginToken() {
		return singleLineCommentLiteral()+JOINPOINT_SECTION_BEGIN;
	}
	
	private String joinpointEndToken() {
		return singleLineCommentLiteral()+JOINPOINT_SECTION_END;
	}

	private String adviceBeginToken() {
		return singleLineCommentLiteral()+ADVICE_SECTION_BEGIN;
	}
	
	private String adviceEndToken() {
		return singleLineCommentLiteral()+ADVICE_SECTION_END;
	}


	@Override
	protected void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor, Issues issues) {
		this.issues = issues;
		String root = StandaloneSetup.getPlatformRootPath();
		fullSourcePath = normalizePath( pathAppend( root, sourcePath ) );
		fullGenPath = normalizePath( pathAppend( root, genPath ) );
		File srcDir = new File(fullSourcePath);
		List<File> files = new ArrayList<File>();
		collect( srcDir, files);
		for (File file : files) {
			try {
				processWeaving( file, issues );
			} catch (IOException e) {
				issues.addError("cannot process file "+file.getAbsolutePath()+". Reason: "+e.getMessage());
			} 
		}
	}

	private void processWeaving(File file, Issues issues) throws IOException {
		String targetFilename = buildTargetFileName( file.getAbsoluteFile() );
		List<String> targetLines = loadFile(new File(targetFilename));
		List<String> lines = loadFile(file);
		List<String> newLines = weave( targetLines, lines, issues, file );
		writeFile( newLines, targetFilename);
	}

	private List<String> weave(List<String> targetLines, List<String> lines, Issues issues, File file) {
		List<Aspect> aspects = extractAspects(lines, issues, file);
		for (Iterator iterator = aspects.iterator(); iterator.hasNext();) {
			Aspect aspect = (Aspect) iterator.next();
			int state = OUTSIDE;
			int c = -1;
			int beginLine = 0;
			int endLine = 0;
			while( c < targetLines.size()-1 )  {
				c++;
				String l = targetLines.get(c);
				if ( state == OUTSIDE ) {
					if ( l.trim().startsWith(joinpointBeginToken())) {
						String[] t = l.trim().split(" ");
						if ( t.length != 2 ) {
							issues.addError("Wrong joinpoint syntax at "+file.getName()+":"+c);
							return targetLines;
						}
						if ( t[1].equals(aspect.joinpoint)) {
							beginLine = c;
							state = INSIDE;
							continue;
						}
					}
				}
				if ( state == INSIDE ) {
					if ( l.trim().startsWith(joinpointBeginToken())) {
						issues.addError("Wrong joinpoint begin/end sequence at "+file.getName()+":"+c);
						return targetLines;
					}
					
					if ( l.trim().startsWith(joinpointEndToken())) {
						endLine = c;
						if ( aspect.where.equals("before")) {
							targetLines.addAll(beginLine+1, aspect.lines);
							c += aspect.lines.size();
						}
						if ( aspect.where.equals("after")) {
							targetLines.addAll(endLine, aspect.lines);
							c += aspect.lines.size();
						}
						if ( aspect.where.equals("instead")) {
							for ( int j=endLine-1; j>= beginLine+1; j-- ) {
								targetLines.remove(j);
								c--;
							}
							targetLines.addAll(beginLine+1, aspect.lines);
							c += aspect.lines.size();
						}
						state = OUTSIDE;
						continue;
					}
				}
				
			}
		}
		return targetLines;
	}
	
	public static final int OUTSIDE = 0;
	public static final int INSIDE = 1;
	
	private List<Aspect> extractAspects(List<String> lines, Issues issues, File file) {
		List<Aspect> aspects = new ArrayList<Aspect>();
		int state = OUTSIDE;
		int c = 01;
		Aspect a = null;
		for (Iterator iterator = lines.iterator(); iterator.hasNext();) {
			String l = (String) iterator.next();
			c++;
			if ( state == OUTSIDE ) {
				if ( l.startsWith(adviceBeginToken())) {
					a = new Aspect();
					String[] t = l.split(" ");
					if ( t.length != 2 ) {
						issues.addError("wrong advice syntax at "+file.getName()+":"+c+". Expected "+adviceBeginToken()+" {before|after|instead}:{JoinpointName}");
						return aspects;
					}
					t = t[1].split(":");
					if ( t.length != 2 ) {
						issues.addError("wrong advice syntax at "+file.getName()+":"+c+". Expected "+adviceBeginToken()+" {before|after|instead}:{JoinpointName}");
						return aspects;
					}
					a.joinpoint = t[1];
					a.where = t[0];
					state = INSIDE;
					continue;
				}
				if ( l.startsWith(adviceEndToken())) {
					issues.addError("wrong sequence of advice begins/ends at "+file.getName()+":"+c);
					return aspects;
				}
			}
			if ( state == INSIDE ) {
				if ( l.startsWith(adviceBeginToken())) {
					issues.addError("wrong sequence of advice begins/ends at "+file.getName()+":"+c);
					return aspects;
				} else if ( l.startsWith(adviceEndToken())) {
					aspects.add(a);
					state = OUTSIDE;
					continue;
				} else {
					a.lines.add( l );
				}
			}
		}
		return aspects;
	}

	public static class Aspect {
		public String joinpoint;
		public String where;
		public List<String> lines;		
		public Aspect() {
			lines = new ArrayList<String>();
		}
	}

	protected void writeFile(List<String> processedLines, String targetFileName) throws IOException {
		FileWriter w = new FileWriter(targetFileName);
		for (String line : processedLines) {
			w.write(line+"\n");
		}
		w.close();
	}
	
	


}
