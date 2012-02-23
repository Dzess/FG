package pl.alife.fg.ui.printing;

import pl.alife.fg.core.gp.functions.BinaryOperation;
import pl.alife.fg.core.gp.functions.NullaryOperation;
import pl.alife.fg.core.gp.functions.UnaryOperation;
import pl.alife.fg.core.gp.functions.real.Max;
import pl.alife.fg.core.gp.functions.real.Min;
import pl.alife.fg.core.gp.functions.real.Pow;
import pl.alife.fg.core.gp.functions.real.ProtectedDiv;
import ec.gp.GPNode;
import ec.gp.GPTree;

/**
 * Helper class which makes from the {@linkplain GPTree} the string with in the
 * proper format.
 * 
 * 
 */
public class TreeToStringTranslator {

    /**
     * Gets the tree and makes from it string used for regression
     */
    static public String translateTree(GPTree tree) {
        StringBuilder builder = new StringBuilder();
        processNode(builder, tree.child);
        return builder.toString();
    }

    static private void processNode(StringBuilder builder, GPNode node) {
        if (node instanceof BinaryOperation) {
            if (node instanceof ProtectedDiv || node instanceof Pow || node instanceof Min || node instanceof Max
                    || node instanceof pl.alife.fg.core.gp.functions.integer.ProtectedDiv
                    || node instanceof pl.alife.fg.core.gp.functions.integer.Min
                    || node instanceof pl.alife.fg.core.gp.functions.integer.Max) {
                builder.append(node.toString());
                builder.append('(');
                processNode(builder, node.children[0]);
                builder.append(", ");
                processNode(builder, node.children[1]);
                builder.append(')');
            } else {
                builder.append('(');
                processNode(builder, node.children[0]);
                builder.append(") ");
                builder.append(node.toString());
                builder.append(" (");
                processNode(builder, node.children[1]);
                builder.append(')');
            }
        } else if (node instanceof UnaryOperation) {
            builder.append(node.toString());
            builder.append('(');
            processNode(builder, node.children[0]);
            builder.append(')');
        } else if (node instanceof NullaryOperation) {
            builder.append(node.toString());
        } else {
            throw new RuntimeException("Unknown node type: " + node.getClass().getName());
        }
    }
}
