package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

import org.apache.log4j.*;


public class RuleVisitor extends VisitorAdaptor {
	
	Logger log = Logger.getLogger(getClass());
	int printCallCount = 0;
	int varDeclCount = 0;
	
    public void visit(PrintStatement PrintStatement) { 
    	printCallCount++;
    }
    
    public void visit(VarDeclaration VarDeclaration) { 
    	varDeclCount++;
    }
    
	
}
