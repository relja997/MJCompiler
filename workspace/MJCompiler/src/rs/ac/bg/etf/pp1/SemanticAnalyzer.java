package rs.ac.bg.etf.pp1;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.ac.bg.etf.pp1.test.*;
import rs.ac.bg.etf.pp1.test.CompilerError.CompilerErrorType;

public class SemanticAnalyzer extends VisitorAdaptor {
	
	Obj boolType = null;
	Obj currentMethodObj = Tab.noObj;
	Obj currentTypeObj = Tab.noObj;
	Struct varType = Tab.noType;
	boolean mainFound = false;
	boolean errorDetected = false;
	int nVars;
	public static List<CompilerError> semantickeGreske = new LinkedList<CompilerError>();
	
	public SemanticAnalyzer() {
		Tab.init();
		boolType = Tab.insert(Obj.Type, "bool", new Struct(Struct.Bool));
		Tab.currentScope.addToLocals(boolType);
	}

	Logger log = Logger.getLogger(getClass());
	
	int printCallCount = 0;
	int varDeclCount = 0;
	
	private boolean assignableTo(Struct srcType, Struct dstType) {
		if (srcType != null && dstType != null)
			if (srcType.assignableTo(dstType)) {
				return true;
			}

		return false;
	}
	
	public boolean passed() {
		return !errorDetected;
	}
	
    
    // ERROR REPORTING AND LOGGING INFO
    

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		semantickeGreske.add(new CompilerError(line, message, CompilerErrorType.SEMANTIC_ERROR));
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
    
    // PROGRAM SEGMENT
    
    public void visit(ProgName progName) {
    	progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
    	Tab.openScope();
    	report_info("ProgramName obidjeno i scope otvoren!", null);
    }
    
    public void visit(Program program) {
    	if(!mainFound) {
			report_error("U programu nije definisana main funkcija!", program);
		}
    	
    	nVars = Tab.currentScope.getnVars();
    	Tab.chainLocalSymbols(program.getProgName().obj);
    	Tab.closeScope();
    	report_info("Program obidjen, simboli uvezani i scope zatvoren! Ukupno uvezanih simbola: " + nVars, null);
    }
    
   /*  public void visit(DeclList decList) {
    	report_info("DeclarationList obilazak zavrsen!", null);
    }
    
    public void visit(NoDeclarations noDecl) {
    	report_info("Nema vise deklaracija!", null);
    }
    
    public void visit(ConstDeclaration constDecl) {
    	report_info("Deklaracija konstante zavrsena!", null);
    }
    
    public void visit(NoMoreNumConst nmnConst) {
		report_info("Nema vise deklarisanih konstanti!" , nmnConst);
	} */
	
    // TYPE SEGMENT
    
    public void visit(Type type) { 
    	Obj typeNode = Tab.find(type.getTypeIdent());
    	if(typeNode == Tab.noObj) {
    		report_error("Nije pronadjen tip " + type.getTypeIdent() + " u tabeli simbola!", type);
    		type.struct = Tab.noType;
    		currentTypeObj = Tab.noObj;
    	} else {
    		if(typeNode.getKind() == Obj.Type) {
    			type.struct = typeNode.getType();
    			currentTypeObj = typeNode;
    		} else {
    			report_error("Greska: Ime " + type.getTypeIdent() + " ne predstavlja tip!", type);
    			type.struct = Tab.noType;
    			currentTypeObj = Tab.noObj;
    		}
    	}
    }
    
    // VARIABLE SEGMENT
    
    public void visit(VarDeclaration VarDeclaration) { 
    	varDeclCount++;
    	
    	if(currentTypeObj == Tab.noObj) {
    		report_error("Tip promjenljive nije ispravan", VarDeclaration);
    		return;
    	}
    	
    	if(Tab.find(VarDeclaration.getVarIdent()) != Tab.noObj) {
    		report_error("Promjenljiva pod nazivom " + VarDeclaration.getVarIdent() + " je vec deklarisana na liniji " + VarDeclaration.getLine(), VarDeclaration);
    	} else {
    		OptArray optA = VarDeclaration.getOptArray();
    		if(optA instanceof ArrayBrackets) {
    			Obj newArr = Tab.insert(Obj.Var, VarDeclaration.getVarIdent(), new Struct(Struct.Array, currentTypeObj.getType()));
    			report_info("Deklarisan niz " + VarDeclaration.getVarIdent() + "[]", VarDeclaration);
    		} else {
    			Obj newVar = Tab.insert(Obj.Var, VarDeclaration.getVarIdent(), currentTypeObj.getType());
    			report_info("Deklarisana promjenljiva " + VarDeclaration.getVarIdent(), VarDeclaration);
    		}
    	}
    	
    	report_info("VarDecl cvor posjecen", null);
    }
    
    public void visit(VarExtension varExtension) {
    	String varName = varExtension.getVarIdent();
    	if(Tab.find(varName) != Tab.noObj) {
    		report_error("Promjenljiva pod nazivom " + varName + " je vec deklarisana na liniji " + varExtension.getLine(), varExtension);
    	}
    	
    	OptArray optA = varExtension.getOptArray();
		if(optA instanceof ArrayBrackets) {
			Obj newArr = Tab.insert(Obj.Var, varName, new Struct(Struct.Array, currentTypeObj.getType()));
			report_info("Deklarisan niz " + varName + "[]", varExtension);
		} else {
			Obj newVar = Tab.insert(Obj.Var, varName, currentTypeObj.getType());
			report_info("Deklarisana promjenljiva " + varName, varExtension);
		}
    }  
    
    // CONST SEGMENT
    
    	// NUM CONST SEGMENT
	public void visit(NumCDecl numCDecl) {
		if(currentTypeObj == Tab.noObj) {
    		report_error("Tip konstante nije ispravan", numCDecl);
    		return;
    	}
		
		String numcName = numCDecl.getNumcIdent();
		
		if(currentTypeObj.getType() != Tab.intType) {
			report_error("Nekompatibilan tip vrijednosti dodjeljen promjenljivoj na liniji " + numCDecl.getLine(), numCDecl);
			return;
		}
		
		if(Tab.find(numcName) != Tab.noObj) {
			report_error("Konstanta pod nazivom " + numcName + " je vec deklarisana na liniji " + numCDecl.getLine(), numCDecl);
		} else {
			if(!(currentTypeObj.getType().assignableTo(Tab.intType))) {
				report_error("Nekompatibilan tip vrijednosti dodjeljen promjenljivoj na liniji " + numCDecl.getLine(), numCDecl);
				return;
			}
			Obj numConstObj = Tab.insert(Obj.Con, numcName, Tab.intType);
			numConstObj.setAdr(numCDecl.getNumVal());
			report_info("Konstanta " + numcName + " = " + numCDecl.getNumVal() + " deklarisana na liniji " + numCDecl.getLine(), numCDecl);
		}
	}
	
	public void visit(NumCDeclExtend numCDeclExtend) {
		String numcName = numCDeclExtend.getNumcIdent();
		
		if(currentTypeObj.getType() != Tab.intType) {
			report_error("Nekompatibilan tip vrijednosti dodjeljen promjenljivoj na liniji " + numCDeclExtend.getLine(), numCDeclExtend);
			return;
		}
		
		if(Tab.find(numcName) != Tab.noObj) {
			report_error("Konstanta pod nazivom " + numcName + " je vec deklarisana na liniji " + numCDeclExtend.getLine(), numCDeclExtend);
		} else {
			if(!(currentTypeObj.getType().assignableTo(Tab.intType))) {
				report_error("Nekompatibilan tip vrijednosti dodjeljen promjenljivoj na liniji " + numCDeclExtend.getLine(), numCDeclExtend);
				return;
			}
			Obj numConstObj = Tab.insert(Obj.Con, numcName, Tab.intType);
			numConstObj.setAdr(numCDeclExtend.getNumVal());
			report_info("Konstanta " + numcName + " = " + numCDeclExtend.getNumVal() + " deklarisana na liniji " + numCDeclExtend.getLine(), numCDeclExtend);
		}
	}
	
		// BOOL CONST SEGMENT
	
	public void visit(BoolCDecl boolCDecl) {
		if(currentTypeObj == Tab.noObj) {
    		report_error("Tip konstante nije ispravan", boolCDecl);
    		return;
    	}
		
		String boolcName = boolCDecl.getBoolcIdent();
		
		if(currentTypeObj.getType() != Tab.find("bool").getType()) {
			report_error("Nekompatibilan tip vrijednosti dodjeljen promjenljivoj na liniji " + boolCDecl.getLine(), boolCDecl);
			return;
		}
		
		if(Tab.find(boolcName) != Tab.noObj) {
			report_error("Konstanta pod nazivom " + boolcName + " je vec deklarisana na liniji " + boolCDecl.getLine(), boolCDecl);
		} else {
			if(!(boolCDecl.getBoolVal() instanceof Boolean)) {
				report_error("Nekompatibilan tip vrijednosti dodjeljen promjenljivoj na liniji " + boolCDecl.getLine(), boolCDecl);
				return;
			}
			Obj boolConstObj = Tab.insert(Obj.Con, boolcName, currentTypeObj.getType());
			if(boolCDecl.getBoolVal())
				boolConstObj.setAdr(1);
			else
				boolConstObj.setAdr(0);
			
			report_info("Konstanta " + boolcName + " = " + boolCDecl.getBoolVal() + " deklarisana na liniji " + boolCDecl.getLine(), boolCDecl);
		}
	}
	
	public void visit(BoolCDeclExtend boolCDeclExtend) {
		String boolcName = boolCDeclExtend.getBoolcIdent();
		
		if(currentTypeObj.getType() != Tab.find("bool").getType()) {
			report_error("Nekompatibilan tip vrijednosti dodjeljen promjenljivoj na liniji " + boolCDeclExtend.getLine(), boolCDeclExtend);
			return;
		}
		
		if(Tab.find(boolcName) != Tab.noObj) {
			report_error("Konstanta pod nazivom " + boolcName + " je vec deklarisana na liniji " + boolCDeclExtend.getLine(), boolCDeclExtend);
		} else {
			if(!(boolCDeclExtend.getBoolVal() instanceof Boolean)) {
				report_error("Nekompatibilan tip vrijednosti dodjeljen promjenljivoj na liniji " + boolCDeclExtend.getLine(), boolCDeclExtend);
				return;
			}
			Obj boolConstObj = Tab.insert(Obj.Con, boolcName, currentTypeObj.getType());
			if(boolCDeclExtend.getBoolVal())
				boolConstObj.setAdr(1);
			else
				boolConstObj.setAdr(0);
			
			report_info("Konstanta " + boolcName + " = " + boolCDeclExtend.getBoolVal() + " deklarisana na liniji " + boolCDeclExtend.getLine(), boolCDeclExtend);
		}
	}
	
		// CHAR CONST SEGMENT
	
	public void visit(CharCDecl charCDecl) {
		if(currentTypeObj == Tab.noObj) {
			report_error("Tip konstante nije ispravan", charCDecl);
    		return;
		}
		
		String charcName = charCDecl.getCharcIdent();
		
		if(currentTypeObj.getType() != Tab.charType) {
			report_error("Nekompatibilan tip vrijednosti dodjeljen promjenljivoj na liniji " + charCDecl.getLine(), charCDecl);
			return;
		}
		
		if(Tab.find(charcName) != Tab.noObj) {
			report_error("Konstanta pod nazivom " + charcName + " je vec deklarisana na liniji " + charCDecl.getLine(), charCDecl);
		} else {
			if(!(charCDecl.getCharVal() instanceof Character)){
				report_error("Nekompatibilan tip vrijednosti dodjeljen promjenljivoj na liniji " + charCDecl.getLine(), charCDecl);
				return;
			}
			Obj charConstObj = Tab.insert(Obj.Con, charcName, Tab.charType);
			charConstObj.setAdr(charCDecl.getCharVal());
			
			report_info("Konstanta " + charcName + " = " + charCDecl.getCharVal() + " deklarisana na liniji " + charCDecl.getLine(), charCDecl);
		}
	}
	
	public void visit(CharCDeclExtend charCDeclExtend) {
		String charcName = charCDeclExtend.getCharcIdent();
		
		if(currentTypeObj.getType() != Tab.charType) {
			report_error("Nekompatibilan tip vrijednosti dodjeljen promjenljivoj na liniji " + charCDeclExtend.getLine(), charCDeclExtend);
			return;
		}
		
		if(Tab.find(charcName) != Tab.noObj) {
			report_error("Konstanta pod nazivom " + charcName + " je vec deklarisana na liniji " + charCDeclExtend.getLine(), charCDeclExtend);
		} else {
			if(!(charCDeclExtend.getCharVal() instanceof Character)){
				report_error("Nekompatibilan tip vrijednosti dodjeljen promjenljivoj na liniji " + charCDeclExtend.getLine(), charCDeclExtend);
				return;
			}
			Obj charConstObj = Tab.insert(Obj.Con, charcName, Tab.charType);
			charConstObj.setAdr(charCDeclExtend.getCharVal());
			
			report_info("Konstanta " + charcName + " = " + charCDeclExtend.getCharVal() + " deklarisana na liniji " + charCDeclExtend.getLine(), charCDeclExtend);
		}
	}
	
	/* public void visit(NoMoreCharConst nmnConst) {
		report_info("Nema vise deklarisanih konstanti!" , nmnConst);
	} */
	
	// METHOD SEGMENT
	
	public void visit(MethodTypeName methodTypeName) {
		String methodName = methodTypeName.getMethodIdent();
		
		if(methodName.equals("main")) {
			if(mainFound == false) {
				mainFound = true;
			} else {
				report_error("Main metoda je vec definisana, na liniji " + methodTypeName.getLine(), methodTypeName);
				return;
			}
		}
		
		if(Tab.find(methodName) != Tab.noObj) {
			report_error("Funkcija sa nazivom " + methodName + " je vec definisana, na liniji " + methodTypeName.getLine(), methodTypeName);
			return;
		}
		
		MethodType metType = methodTypeName.getMethodType();
		
		if(methodName.equals("main") && !(metType instanceof VoidMethod)) {
			report_error("Main metoda mora da bude tipa void, na liniji " + methodTypeName.getLine(), methodTypeName);
			return;
		}
		
		if(metType instanceof VoidMethod) {
			currentMethodObj = Tab.insert(Obj.Meth, methodName, Tab.noType);
		} else {
			currentMethodObj = Tab.insert(Obj.Meth, methodName, currentTypeObj.getType());
		}
		
		methodTypeName.obj = currentMethodObj;
		
		currentMethodObj.setLevel(0);
		
		Tab.openScope();
		
		report_info("Funkcija " + methodName + " se obradjuje", methodTypeName);
		
	}
	
	public void visit(MethodDeclaration methodDecl) {
		Tab.chainLocalSymbols(currentMethodObj);
		Tab.closeScope();
		currentMethodObj = Tab.noObj;
	}
	
	// DESIGNATOR SEGMENT
	
	public void visit(DesignatorDeclaration designatorDeclaration) {
		String desName = designatorDeclaration.getDesignatorIdent();
		Obj des = Tab.find(desName);
		
		if(des == Tab.noObj) {
			report_error("Identifikator sa nazivom " + desName + " nije deklarisan, na liniji " + designatorDeclaration.getLine(),  designatorDeclaration);
			return;
		}
		
		designatorDeclaration.obj = des;
	}
	
	public void visit(ArrayDesignator arrayDesignator) {
		String desName = arrayDesignator.getDesArrName().getDesArrayName();
		Obj arrayDes = Tab.find(desName);
		
		if(arrayDes == Tab.noObj) {
			report_error("Identifikator sa nazivom " + desName + " nije deklarisan, na liniji " + arrayDesignator.getLine(),  arrayDesignator);
			return;
		}
		
		if(arrayDes.getKind() != Obj.Var) {
			report_error("Identifikator " + desName + " ne predstavlja promjenljivu, na liniji " + arrayDesignator.getLine(), arrayDesignator);
			return;
		}
		
		if(arrayDes.getType().getKind() != Struct.Array) {
			report_error("Identifikator " + desName + " ne predstavlja ime niza, na liniji " + arrayDesignator.getLine(), arrayDesignator);
			return;
		}
		
		if(arrayDesignator.getExpr().struct != Tab.intType) {
			report_error("Izraz u zagradi nije tipa integer, na liniji " + arrayDesignator.getLine(), arrayDesignator);
			return;
		}
		
		
		arrayDesignator.obj = new Obj(Obj.Elem, arrayDes.getName(), arrayDes.getType().getElemType());
	}
	
	public void visit(DesArrName desArrName) {
		desArrName.obj = Tab.find(desArrName.getDesArrayName());
	}
	
    // DESIGNATOR STATEMENT SEGMENT

    public void visit(DesignatorAssign designatorAssign) { 
    	Obj localDes = designatorAssign.getDesignator().obj;
    	String desName = localDes.getName();
    	
    	if(Tab.find(desName) == Tab.noObj) {
    		report_error("Ime promjenljive " + desName + " nije deklarisano u programu, na liniji " + designatorAssign.getLine(), designatorAssign);
    		return;
    	}
    	
    	if((localDes.getKind() != Obj.Var) && (localDes.getKind() != Obj.Fld) && (localDes.getKind() != Obj.Elem)) {
    		report_error("Ime " + desName + " ne predstavlja ime promjenljive, niza ili polja, na liniji " + designatorAssign.getLine(), designatorAssign);
    	}

    	//report_info("Lijeva strana dodjele vrijednosti " + designatorAssign.getDesignator(), designatorAssign);
    	
    	if(!assignableTo(designatorAssign.getExpr().struct, localDes.getType())) {
    		report_error("Tipovi pri dodjeli vrijednosti su nekompatibilni, na liniji " + designatorAssign.getLine(), designatorAssign);
    	}
    	else {
    		report_info("Dodjela vrijednosti obavljena uspjesno, na liniji " + designatorAssign.getLine(), designatorAssign);
    	}
    	
    }
    
    public void visit(DesignatorIncrement designatorIncrement) {
    	Obj localDes = designatorIncrement.getDesignator().obj;
    	String desName = localDes.getName();
    	
    	
    	if(Tab.find(desName) == Tab.noObj) {
    		report_error("Ime promjenljive " + desName + " nije deklarisano u programu, na liniji " + designatorIncrement.getLine(), designatorIncrement);
    		return;
    	}
    	
    	if((localDes.getKind() != Obj.Var) && (localDes.getKind() != Obj.Fld) && (localDes.getKind() != Obj.Elem)) {
    		report_error("Ime " + desName + " ne predstavlja ime promjenljive, niza ili polja, na liniji " + designatorIncrement.getLine(), designatorIncrement);
    	}
    	
    	if(!localDes.getType().equals(Tab.intType)) {
    		report_error("Tip promjenljive nije int, na liniji " + designatorIncrement.getLine(), designatorIncrement);
    	} else {
    		report_info("Inkrementiranje promjenljive obavljeno uspjesno, na liniji " + designatorIncrement.getLine(), designatorIncrement);
    	}
    }
	
    public void visit(DesignatorDecrement designatorDecrement) {
    	Obj localDes = designatorDecrement.getDesignator().obj;
    	String desName = localDes.getName();
    	
    	
    	if(Tab.find(desName) == Tab.noObj) {
    		report_error("Ime promjenljive " + desName + " nije deklarisano u programu, na liniji " + designatorDecrement.getLine(), designatorDecrement);
    		return;
    	}
    	
    	if((localDes.getKind() != Obj.Var) && (localDes.getKind() != Obj.Fld) && (localDes.getKind() != Obj.Elem)) {
    		report_error("Ime " + desName + " ne predstavlja ime promjenljive, niza ili polja, na liniji " + designatorDecrement.getLine(), designatorDecrement);
    	}
    	
    	if(!localDes.getType().equals(Tab.intType)) {
    		report_error("Tip promjenljive nije int, na liniji " + designatorDecrement.getLine(), designatorDecrement);
    	} else {
    		report_info("Dekrementiranje promjenljive obavljeno uspjesno, na liniji " + designatorDecrement.getLine(), designatorDecrement);
    	}
    	
    }
    
    // STATEMENTS SEGMENT
    
    	// READ STATEMENT
    public void visit(ReadStatement readStmt) {
    	Obj localDes = readStmt.getDesignator().obj;
    	String desName = localDes.getName();
    	
    	if(localDes.getKind() != Obj.Var && localDes.getKind() != Obj.Fld && localDes.getKind() != Obj.Elem) {
    		report_error("Ime " + desName + " ne predstavlja ime promjenljive, niza ili polja, na liniji " + readStmt.getLine(), readStmt);
    	}
    	
    	if(!localDes.getType().equals(Tab.intType) && !localDes.getType().equals(Tab.charType) && !localDes.getType().equals(Tab.find("bool").getType())) {
    		report_error("Identifikator " + desName + " ne predstavlja identifikator tipa int, char ili bool, na liniji " + readStmt.getLine(), readStmt);
    	}
    	
    	report_info("Read odradjen: ", readStmt);
    }
    
    	// PRINT STATEMENT
    public void visit(PrintStatement printStmt) {
    	printCallCount++;
    	Struct type;
    	
    	if(printStmt.getPrintExpr() instanceof PrintExprOnly) {
    		Expr pex = ((PrintExprOnly) printStmt.getPrintExpr()).getExpr();
    		type = pex.struct;
    	} else {
    		Expr pex = ((PrintExprWithOptional) printStmt.getPrintExpr()).getExpr();
    		type = pex.struct;
    	}
    	
    	if(!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(Tab.find("bool").getType())) {
    		report_error("Parametar print f-je nije tipa int, char ili bool , na liniji " + printStmt.getLine(), printStmt);
    	}    	
    	
    	report_info("Print metoda posjecena na liniji " + printStmt.getLine(), printStmt);
    }
    
    public void visit(PrintExprOnly pex) {
    	pex.struct = ((PrintExprOnly) pex).getExpr().struct;
    	
    	report_info("Tip print izraza je: " + pex.struct.getKind(), null);
    }
    
    public void visit(PrintExprWithOptional pex) {
    	pex.struct = ((PrintExprWithOptional) pex).getExpr().struct;
    	
    	report_info("Tip print izraza je: " + pex.struct.getKind(), null);
    }
    
    // IF-THEN-ELSE STATEMENT
    
    public void visit(IfHeaderStatement ifHeaderStmt) {
    	report_info("IfHeaderVisited", null);
    }
    
    public void visit(IfStatement ifStmt) {
    	report_info("IF statement visited" , null);
    }
    
    public void visit(IfElseStatement ifElseStmt) {
    	report_info("IF-ELSE statement visited", null);
    }
    
    // EXPR SEGMENT
    
    public void visit(Expr1Positive exp1pos) {
    	exp1pos.struct = exp1pos.getTerm().struct;
    }
    
    public void visit(Expr1Negative exp1neg) {
    	Struct str = exp1neg.getTerm().struct;
    	if(str != Tab.intType) {
    		report_error("Negativna vrijednost se moze dodijeliti samo int tipu promjenljive, na liniji " + exp1neg.getLine(), exp1neg);
    		return;
    	}
    	exp1neg.struct = str;
    }
    
    public void visit(ExprAddopTerm expAddopTerm) {
    	Struct exprstr = expAddopTerm.getExpr1().struct;
    	Struct termstr = expAddopTerm.getTerm().struct;
    	
    	if(!exprstr.equals(Tab.intType) || !termstr.equals(Tab.intType)) {
    		report_error("Tipovi u izrazu nisu int, na liniji " + expAddopTerm.getLine(), expAddopTerm);
    	}
    	
    	if(!exprstr.compatibleWith(termstr)) {
    		report_error("Tipovi u izrazu nisu kompatibilni, na liniji " + expAddopTerm.getLine(), expAddopTerm);
    	}
    	
    	expAddopTerm.struct = Tab.intType;
    }
    
    public void visit(ExprDeclAddop exp) {
    	exp.struct = exp.getExpr1().struct;
    }
    
    // TERM SEGMENT
    
    public void visit(TermDecl termDecl) {
    	Struct termstr = termDecl.getTerm().struct;
    	Struct factstr = termDecl.getFactor().struct;
    	
    	if(!termstr.equals(Tab.intType) || !factstr.equals(Tab.intType)) {
    		report_error("Tipovi u izrazu moraju biti int tipa, na liniji " + termDecl.getLine(), termDecl);
    	}
    	
    	termDecl.struct = termstr;
    }
    
    public void visit(SingleFactor singleFact) {
    	singleFact.struct = singleFact.getFactor().struct;
    }
    
    // FACTOR SEGMENT
    
    public void visit(FactorDeclaration factorDecl) {
    	factorDecl.struct = factorDecl.getDesignator().obj.getType();
    }
    
    public void visit(FactorNUMC factnumc) {
    	factnumc.struct = Tab.intType;
    }
    
    public void visit(FactorCHARC factcharc) {
    	factcharc.struct = Tab.charType;
    }
    
    public void visit(FactorBOOLC factboolc) {
    	factboolc.struct = Tab.find("bool").getType();
    }
    
    public void visit(FactorNEW factnew) {
    	factnew.struct = new Struct(Struct.Array, factnew.getType().struct);
    	if(factnew.getOptExpr() instanceof WithOptionalExpr) {
    		WithOptionalExpr woe = (WithOptionalExpr)factnew.getOptExpr();
    		if(!woe.getExpr().struct.equals(Tab.intType)) {
    			report_error("Tip izraza u zagradi mora biti int tipa, na liniji " + factnew.getLine(), factnew);
    		}
    	} 
    }
    
    public void visit(FactorExpr factexpr) {
    	factexpr.struct = factexpr.getExpr().struct;
    }
    
    // CONDITION AND CONDFACT
    
    public void visit(ConditionDecl condDecl) {
    	if((condDecl.getCondTerm().struct.getKind() != Struct.Bool) || (condDecl.getCondition().struct.getKind() != Struct.Bool)) {
    		report_error("Operandi OR operatora moraju biti tipa bool, na liniji" + condDecl.getLine(), condDecl);
    	}
    	condDecl.struct = Tab.find("bool").getType();
    }
    
    public void visit(SingleCondTerm singleCondTerm) {
    	if(singleCondTerm.getCondTerm().struct.getKind() != Struct.Bool) {
    		report_error("Izraz nije bool tipa, na liniji" + singleCondTerm.getLine(), singleCondTerm);
    	}
    	singleCondTerm.struct = singleCondTerm.getCondTerm().struct;
    }
    
    public void visit(CondTermDecl condTermDecl) {
    	if((condTermDecl.getCondFact().struct.getKind() != Struct.Bool) || (condTermDecl.getCondTerm().struct.getKind() != Struct.Bool)) {
    		report_error("Operandi AND operatora moraju biti tipa bool, na liniji" + condTermDecl.getLine(), condTermDecl);
    	}
    	condTermDecl.struct = Tab.find("bool").getType();
    }
    
    public void visit(SingleCondFact singleCondFact) {
    	if(singleCondFact.getCondFact().struct.getKind() != Struct.Bool) {
    		report_error("Izraz nije bool tipa, na liniji" + singleCondFact.getLine(), singleCondFact);
    	}
    	
    	singleCondFact.struct = Tab.find("bool").getType();
    }
    
    public void visit(CondFactDecl condFactDecl) {
    	if(condFactDecl.getOptRelopExpr() instanceof WithoutRelop) {
    		condFactDecl.struct = condFactDecl.getExpr().struct;
    	} else {
    		Struct strEx1 = condFactDecl.getExpr().struct;
    		Struct strEx2 = ((WithRelop)condFactDecl.getOptRelopExpr()).getExpr().struct;
    		if(condFactDecl.getExpr().struct.getKind() == Struct.Array) {
    			Relop rOp = ((WithRelop)condFactDecl.getOptRelopExpr()).getRelop();
    			if(!(rOp instanceof EqualTo) && !(rOp instanceof NotEqualTo)) {
    				report_error("Za promjenljive nizovskog tipa mogu se koristiti samo == ili != operatori, na liniji " + condFactDecl.getLine(), condFactDecl);
    				return;
    			}
    		}
    		if(!strEx1.compatibleWith(strEx2)) {
    			report_error("Nekompatibilni tipovi, na liniji " + condFactDecl.getLine(), condFactDecl);
    		}
    		condFactDecl.struct = Tab.find("bool").getType();
    	}
    }
    
}
