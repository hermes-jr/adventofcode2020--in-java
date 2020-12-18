package level_18;

import common.Level;

import java.util.List;
import java.util.Stack;

public class Level18 extends Level {
    static final boolean VERBOSE = false;

    /* Shunting yard */
    String toRPN(String line, boolean samePrecedence) {
        line = line.replaceAll("\\s+", "");
        Stack<String> stack = new Stack<>();
        StringBuilder output = new StringBuilder();

        for (String token : line.split("")) {
            if ("(".equals(token)) {
                stack.push(token);
            } else if (")".equals(token)) {
                while (!stack.empty()) {
                    if (!"(".equals(stack.peek())) {
                        output.append(stack.pop());
                    } else {
                        break;
                    }
                }
                if (!stack.empty()) {
                    stack.pop(); // discard remaining brackets
                }
            } else if (isOperator(token)) {
                while (!stack.empty() && isOperator(stack.peek()) && (samePrecedence || ("*".equals(token) && "+".equals(stack.peek())))) {
                    output.append(stack.pop());
                }
                stack.push(token);
            } else {
                output.append(token);
            }
        }

        while (!stack.empty()) {
            output.append(stack.pop()); // flush remaining stuff
        }

        String rpn = output.toString();
        if (VERBOSE) System.out.println(line + " => " + rpn);

        return rpn;
    }

    public double eval(String line, boolean samePrecedence) {
        String rpn = toRPN(line, samePrecedence);

        Stack<Long> stack = new Stack<>();
        for (String token : rpn.split("")) {
            if (!isOperator(token)) {
                stack.push(Long.parseLong(token));
            } else {
                if (stack.size() > 1) {
                    long op1 = stack.pop();
                    long op2 = stack.pop();
                    switch (token) {
                        case "+":
                            stack.push(op2 + op1);
                            break;
                        case "*":
                            stack.push(op2 * op1);
                            break;
                        default:
                            throw new RuntimeException("Unknown token" + token);
                    }
                }
            }
        }

        return stack.pop();
    }

    private boolean isOperator(String token) {
        return "+".equals(token) || "*".equals(token);
    }

    long p1(List<String> input) {
        long result = 0L;
        for (String s : input) {
            result += eval(s, true);
        }
        return result;
    }

    long p2(List<String> input) {
        long result = 0L;
        for (String s : input) {
            result += eval(s, false);
        }
        return result;
    }

    public static void main(String[] args) {
        Level18 l = new Level18();
        List<String> input = l.readResources("input");
        System.out.println("Part1: " + l.p1(input));
        System.out.println("Part2: " + l.p2(input));
    }

}