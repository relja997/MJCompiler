// generated with ast extension for cup
// version 0.8
// 22/5/2021 13:46:44


package rs.ac.bg.etf.pp1.ast;

public class DesArrName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String desArrayName;

    public DesArrName (String desArrayName) {
        this.desArrayName=desArrayName;
    }

    public String getDesArrayName() {
        return desArrayName;
    }

    public void setDesArrayName(String desArrayName) {
        this.desArrayName=desArrayName;
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
        buffer.append("DesArrName(\n");

        buffer.append(" "+tab+desArrayName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesArrName]");
        return buffer.toString();
    }
}
