package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.Stack;

import org.apache.log4j.Logger;

public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;
	private int fix3;
	private boolean thenInd = false;
	private int fixOR;
	private Stack<Integer> stackAND = new Stack<>();
	private Stack<Integer> stackOR = new Stack<>();
	
	public int getMainPc() {
		return mainPc;
	}
	
	public void fixStackAND(Stack<Integer> s) {
		   
	    // If stack is empty
	    if (s.empty())
	        return;
	   
	    // Extract top of the stack
	    int x = s.peek();
	   
	    // Pop the top element
	    s.pop();
	   
	    // Fix the top adr
	    // of the stack i.e., x
	    Code.fixup(x);
	   
	    // Proceed to print
	    // remaining stack
	    fixStackAND(s);
	   
	    // Push the element back
	    s.push(x);
	}
	
	Logger log = Logger.getLogger(getClass());
	
	// ERROR REPORTING
	public void report_error(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	// DEFAULT FUNCTIONS
	
	// Chr, Ord i Len metode
//	public void visit(ProgName progName) {
//		Obj chrObj = Tab.find("chr");
//		chrObj.setAdr(Code.pc);
//		Code.put(Code.enter);
//		Code.put(1);
//		Code.put(1);
//		Code.put(Code.exit);
//		Code.put(Code.return_);
//		
//		Obj ordObj = Tab.find("ord");
//		ordObj.setAdr(Code.pc);
//		Code.put(Code.enter);
//		Code.put(1);
//		Code.put(1);
//		Code.put(Code.exit);
//		Code.put(Code.return_);
//		
//		Obj lenObj = Tab.find("len");
//		lenObj.setAdr(Code.pc);
//		Code.put(Code.enter);
//		Code.put(1);
//		Code.put(1);
//		Code.put(Code.arraylength);
//		Code.put(Code.exit);
//		Code.put(Code.return_);
//		
//	}
	
	// PRINT STATEMENT
	
	public void visit(PrintStatement printStmt) {
		int printNum = 1;
		
		if(printStmt.getPrintExpr() instanceof PrintExprOnly) {
			if(printStmt.getPrintExpr().struct == Tab.intType) {
				Code.loadConst(5);
				Code.put(Code.print);
			} else if (printStmt.getPrintExpr().struct == Tab.charType) {
				Code.loadConst(1);
				Code.put(Code.bprint);
			} else {
				Code.loadConst(5);
				Code.put(Code.print);
			}
		}
		

		if(printStmt.getPrintExpr() instanceof PrintExprWithOptional) {
			printNum = ((PrintExprWithOptional) printStmt.getPrintExpr()).getN1();
			
			if(printStmt.getPrintExpr().struct == Tab.intType || printStmt.getPrintExpr().struct.getKind() == Struct.Bool) {
				for(int i = 0; i < printNum; i++) {
					Code.put(Code.dup);
					Code.loadConst(5);
					Code.put(Code.print);
				}
			} else if (printStmt.getPrintExpr().struct == Tab.charType) {
				for(int i = 0; i < printNum; i++) {
					Code.put(Code.dup);
					Code.loadConst(1);
					Code.put(Code.bprint);
				}
			} else {
				for(int i = 0; i < printNum; i++) {
					Code.put(Code.dup);
					Code.loadConst(5);
					Code.put(Code.print);
				}
			}
			Code.put(Code.pop);
		} 
	}
	
	// READ STATEMENT
	
	public void visit(ReadStatement readStmt) {
		Obj designator = readStmt.getDesignator().obj;
		Struct type = designator.getType();
		
		if(designator.getType().getKind() == Struct.Array) {
			if(type.getElemType() == Tab.charType) {
				Code.put(Code.bread);
			} else {
				Code.put(Code.read);
			}
			Code.put(Code.astore);			
		} else {
			if(type == Tab.charType) {
				Code.put(Code.bread);
				Code.store(designator);
			} else {
				Code.put(Code.read);
				Code.store(designator);
			}
		}
		
		
		
	}
	
	// CONST STATEMENTS
	
	public void visit(FactorNUMC fctNum) {
		Code.loadConst(fctNum.getN1().intValue());
	}
	
	public void visit(FactorCHARC fctChar) {
		Code.loadConst(fctChar.getC1().charValue());
	}
	
	public void visit(FactorBOOLC fctBool) {
		if(fctBool.getB1()) {
			Code.loadConst(1);
		} else {
			Code.loadConst(0);
		}
	}
	
	// METHOD DECLARATION
	
	public void visit(MethodTypeName methTypeName) {
		if(methTypeName.getMethodIdent().equalsIgnoreCase("main")) {
			mainPc = Code.pc;
		}
		
		methTypeName.obj.setAdr(Code.pc);
		
		// Collect args and local variables of function
		SyntaxNode methodNode = methTypeName.getParent();
		
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		
		// Generate the entry
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());
	}
	
	public void visit(MethodDeclaration methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	// DESIGNATOR STATEMENTS
	
	public void visit(DesignatorAssign desAssign) {
		Code.store(desAssign.getDesignator().obj);
	}
	
	public void visit(DesignatorDeclaration desDecl) {
		SyntaxNode parent = desDecl.getParent();
		
		if(DesignatorAssign.class != parent.getClass() 
		   && FactorWithActPars.class != parent.getClass() 
		   && FactorWithoutArguments.class != parent.getClass()
		   && DesignatorOptActPars.class != parent.getClass()
		   && DesignatorIncrement.class != parent.getClass()
		   && DesignatorDecrement.class != parent.getClass()) 
		{
			Code.load(desDecl.obj);
		}
	}
	
	public void visit(DesArrName desArrName) {
		Code.load(desArrName.obj);
	}
	
	public void visit(ArrayDesignator arrayDes) {
		SyntaxNode parent = arrayDes.getParent();
		// report_info("ArrayDesignator", null);
		
		if (parent.getClass() == FactorDeclaration.class) {
			if (arrayDes.obj.getType() == Tab.charType) {
				Code.put(Code.baload);
			} else {
				Code.put(Code.aload);
			}
		}
	}
	
	public void visit(DesignatorIncrement desInc) {
		if(desInc.getDesignator().getClass() == ArrayDesignator.class) {
			Code.put(Code.dup2);
			Code.put(Code.aload);
			Code.loadConst(1);
			Code.put(Code.add);
			Code.put(Code.astore);
		} else {
			Code.load(desInc.getDesignator().obj);
			Code.loadConst(1);
			Code.put(Code.add);
			Code.store(desInc.getDesignator().obj);
		}
	}
	
	public void visit(DesignatorDecrement desDecr) {
		if(desDecr.getDesignator().getClass() == ArrayDesignator.class) {
			Code.put(Code.dup2);
			Code.put(Code.aload);
			Code.loadConst(1);
			Code.put(Code.sub);
			Code.put(Code.astore);
		} else {
			Code.load(desDecr.getDesignator().obj);
			Code.loadConst(1);
			Code.put(Code.sub);
			Code.store(desDecr.getDesignator().obj);
		}
	}
	
	public void visit(ExprAddopTerm exprAddopTerm) {
		if(exprAddopTerm.getAddop() instanceof Plus) {
			Code.put(Code.add);
		} else {
			Code.put(Code.sub);
		}
	}
	
	public void visit(TermDecl termDecl) {
		if(termDecl.getMulop() instanceof MulOperator) {
			Code.put(Code.mul);
		} else if(termDecl.getMulop() instanceof DivOperator) {
			Code.put(Code.div);
		} else {
			Code.put(Code.rem);
		}
	}
	
	// IF-THEN-ELSE STATEMENT
	
	public void visit(CondFactDecl cfDecl) {
		if(cfDecl.getOptRelopExpr() instanceof WithRelop) {
			Relop rel = ((WithRelop) cfDecl.getOptRelopExpr()).getRelop();
			int op;
			if(rel instanceof EqualTo) {
				op = Code.eq;
			}
			else if(rel instanceof NotEqualTo) {
				op = Code.ne;
			}
			else if(rel instanceof GreaterThan) {
				op = Code.gt;
			}
			else if(rel instanceof GreaterOrEqual) {
				op = Code.ge;
			}
			else if(rel instanceof LessThan) {
				op = Code.lt;
			}
			else {
				op = Code.le;
			}
			
			Code.putFalseJump(op, 0);
			//fix1 = Code.pc - 2;
			stackAND.push(Code.pc - 2);
			return;
		} else {
			if (cfDecl.getParent().getClass() == SingleCondFact.class) {
				Code.loadConst(0);
				Code.putFalseJump(Code.gt, 0);
				//fix1 = Code.pc - 2;
				stackAND.push(Code.pc - 2);
				return;
			}
			else {
				Code.loadConst(0);
				Code.putFalseJump(Code.gt, 0);
				//fix1 = Code.pc - 2;
				stackAND.push(Code.pc - 2);
				return;
			}
		}
	}
	
	public void visit(MatchedStmt mtchdStmt) {
		if(mtchdStmt.getParent().getClass() == ElsePartStatement.class) {
			if(thenInd) {
				Code.fixup(fix3);
				while(!stackAND.empty()) {
					Code.fixup(stackAND.pop());
				}
				thenInd = false;
				return;
			} else {
				while(!stackAND.empty()) {
					Code.fixup(stackAND.pop());
				}
				thenInd = false;
				return;
			}
		}
		if (mtchdStmt.getParent().getClass() == IfStatement.class) {
			fixStackAND(stackAND);
			while(!stackAND.empty()) {
				stackAND.pop();
			}
			return;
		}// else if (mtchdStmt.getParent().getClass() == IfElseStatement.class) {
//			//Code.putJump(0);
//			fix2 = Code.pc - 2;
//			report_info("ss"+fix2, null);
//			return;
//		}
	}
	
	public void visit(ThenStmt thenStmt) {
		//fixStackAND(stackOR);
		thenInd = true;
		
		return;
	}
	
	public void visit(ElsePartOfIf elsePart) {
		//fixStackAND(stackAND);
		//fixStackAND(stackOR);
		Code.putJump(0);
		//Code.fixup(fix3);
		fix3 = Code.pc - 2;
		
		while(!stackAND.empty()) {
			Code.fixup(stackAND.pop());
		}
		return;
	}	
	
	
	public void visit(OrOperator orOp) {
		Code.putJump(0);
		stackOR.push(Code.pc - 2);
		
		while(!stackAND.empty()) {
			Code.fixup(stackAND.pop());
		}
	}
	
	public void visit(ConditionDecl conDecl) {
		while(!stackOR.empty()) {
			Code.fixup(stackOR.pop());
		}
	}
	
	/*public void visit(SingleCondTerm scTerm) {
		Code.putJump(0);
		stackOR.push(Code.pc - 2);
		
		fixStackAND(stackAND);
	}*/
	
	// FACTOR NEW
	
	public void visit(FactorNEW factNew) {
		if(factNew.getOptExpr() instanceof WithOptionalExpr) {
			Code.put(Code.newarray);
			if(factNew.getType().struct.equals(Tab.charType)) {
				Code.put(0);
			} else {
				Code.put(1);
			}
		}
	}
	
	public void visit(Expr1Negative expr) {
		Code.put(Code.neg);
	}
}
