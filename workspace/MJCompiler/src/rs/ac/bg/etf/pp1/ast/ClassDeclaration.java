// generated with ast extension for cup
// version 0.8
// 22/5/2021 13:46:44


package rs.ac.bg.etf.pp1.ast;

public class ClassDeclaration extends ClassDecl {

    private String classIdent;
    private OptExtends OptExtends;
    private VarDeclList VarDeclList;
    private OptMethodDecl OptMethodDecl;

    public ClassDeclaration (String classIdent, OptExtends OptExtends, VarDeclList VarDeclList, OptMethodDecl OptMethodDecl) {
        this.classIdent=classIdent;
        this.OptExtends=OptExtends;
        if(OptExtends!=null) OptExtends.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
        this.OptMethodDecl=OptMethodDecl;
        if(OptMethodDecl!=null) OptMethodDecl.setParent(this);
    }

    public String getClassIdent() {
        return classIdent;
    }

    public void setClassIdent(String classIdent) {
        this.classIdent=classIdent;
    }

    public OptExtends getOptExtends() {
        return OptExtends;
    }

    public void setOptExtends(OptExtends OptExtends) {
        this.OptExtends=OptExtends;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public OptMethodDecl getOptMethodDecl() {
        return OptMethodDecl;
    }

    public void setOptMethodDecl(OptMethodDecl OptMethodDecl) {
        this.OptMethodDecl=OptMethodDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OptExtends!=null) OptExtends.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
        if(OptMethodDecl!=null) OptMethodDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptExtends!=null) OptExtends.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
        if(OptMethodDecl!=null) OptMethodDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptExtends!=null) OptExtends.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        if(OptMethodDecl!=null) OptMethodDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDeclaration(\n");

        buffer.append(" "+tab+classIdent);
        buffer.append("\n");

        if(OptExtends!=null)
            buffer.append(OptExtends.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptMethodDecl!=null)
            buffer.append(OptMethodDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDeclaration]");
        return buffer.toString();
    }
}
