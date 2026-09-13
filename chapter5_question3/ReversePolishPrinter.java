package chapter5_question3;

// Converts an expression tree into reverse polish notation.
class ReversePolishPrinter implements Expr.Visitor<String> {

    // Begins converting the expression.
    String convert(Expr tree) {
        return tree.accept(this);
    }

    @Override
    public String visitBinaryExpr(Expr.Binary node) {
        // Place the operator after both values.
        return convert(node.left) + " " + convert(node.right)
                + " " + node.operator.lexeme;
    }
    
    @Override
    public String visitGroupingExpr(Expr.Grouping node) {
        // RPN does not require parentheses.
        return convert(node.expression);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal node) {
        return node.value == null ? "nil" : node.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary node) {
        // Distinguish unary negation from subtraction.
        String symbol = node.operator.type == TokenType.MINUS
                ? "~"
                : node.operator.lexeme;

        return convert(node.right) + " " + symbol;
    }

    public static void main(String[] args) {
        // Creates the expression: 1 + 2
        Expr sample = new Expr.Binary(
                new Expr.Literal(1),
                new Token(TokenType.PLUS, "+", null, 1),
                new Expr.Literal(2));

        // Prints: 1 2 +
        ReversePolishPrinter converter = new ReversePolishPrinter();
        System.out.println(converter.convert(sample));
    }
}