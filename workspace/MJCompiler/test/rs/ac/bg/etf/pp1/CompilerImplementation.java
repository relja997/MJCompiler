package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.test.Compiler;
import rs.ac.bg.etf.pp1.test.CompilerError;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

public class CompilerImplementation implements Compiler {
	
	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}

	@Override
	public List<CompilerError> compile(String sourceFilePath, String outputFilePath) {
		// TODO Auto-generated method stub
		Logger log = Logger.getLogger(MJParserTest.class);
		List<CompilerError> greske = new LinkedList<CompilerError>();
		List<CompilerError> semantickeGreske = new LinkedList<CompilerError>();
		List<CompilerError> leksickeGreske = new LinkedList<CompilerError>();
		List<CompilerError> sintaksneGreske = new LinkedList<CompilerError>();
		Reader br = null;
		try {
			File sourceCode = new File(sourceFilePath);
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			leksickeGreske = lexer.leksickeGreske;
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  //pocetak parsiranja
	        sintaksneGreske = p.sintaksneGreske;
	        
	        
	        Program prog = (Program)(s.value); 
			// ispis sintaksnog stabla
			log.info(prog.toString(""));
			log.info("===================================");

			// ispis prepoznatih programskih konstrukcija
			SemanticAnalyzer v = new SemanticAnalyzer();
			semantickeGreske = v.semantickeGreske;
			prog.traverseBottomUp(v); 
	      
			//log.info("Print count calls = " + v.printCallCount);
			
			//log.info("Variable declaration count = " + v.varDeclCount);
			log.info("===================================");
			Tab.dump();
			
			if(!p.errorDetected && v.passed()) {
				File objFile = new File(outputFilePath);
				if(objFile.exists()) objFile.delete();
				
				CodeGenerator codeGenerator = new CodeGenerator();
				prog.traverseBottomUp(codeGenerator);
				Code.dataSize = v.nVars;
				Code.mainPc = codeGenerator.getMainPc();
				Code.write(new FileOutputStream(objFile));
				log.info("Parsiranje uspjesno zavrseno!");
			} else {
				log.info("Parsiranje nije uspjesno zavrseno!");
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}
		
		greske.addAll(leksickeGreske);
		greske.addAll(semantickeGreske);
		greske.addAll(sintaksneGreske);
		System.out.println("Gresaka: " + greske.size());
		if(greske.size() > 0) return greske;
		return null;
	}

}
