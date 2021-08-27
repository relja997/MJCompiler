// generated with ast extension for cup
// version 0.8
// 22/5/2021 13:46:44


package rs.ac.bg.etf.pp1.ast;

public class ReturnStatement extends Matched {

    private OptExpression OptExpression;

    public ReturnStatement (OptExpression OptExpression) {
        this.OptExpression=OptExpression;
        if(OptExpression!=null) OptExpression.setParent(this);
    }

    public OptExpression getOptExpression() {
        return OptExpression;
    }

    public void setOptExpression(OptExpression OptExpression) {
        this.OptExpression=OptExpression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OptExpression!=null) OptExpression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptExpression!=null) OptExpression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptExpression!=null) OptExpression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ReturnStatement(\n");

        if(OptExpression!=null)
            buffer.append(OptExpression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ReturnStatement]");
        return buffer.toString();
    }
}
