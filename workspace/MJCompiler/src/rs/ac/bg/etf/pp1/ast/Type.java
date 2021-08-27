// generated with ast extension for cup
// version 0.8
// 22/5/2021 13:46:44


package rs.ac.bg.etf.pp1.ast;

public class Type implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private String typeIdent;

    public Type (String typeIdent) {
        this.typeIdent=typeIdent;
    }

    public String getTypeIdent() {
        return typeIdent;
    }

    public void setTypeIdent(String typeIdent) {
        this.typeIdent=typeIdent;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Type(\n");

        buffer.append(" "+tab+typeIdent);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Type]");
        return buffer.toString();
    }
}
