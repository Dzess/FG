package functiongenerator.ui.printing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import ec.gp.GPIndividual;
import functiongenerator.core.ProblemType;
import functiongenerator.core.Settings;

/**
 * Facade class for accessing generating code out of the
 * {@linkplain GPIndividual} solution
 * 
 */
public class JavaSourceCodeGenerator {

    /**
     * Generates the Java Code Template for {@linkplain ResultsDialog} from the
     * symbolic regression tree.
     * 
     * @param ind
     *            : the best found solution
     */
    public static String generateClassTemplate(Settings settings, GPIndividual ind, int numberOfArguments)
            throws IOException {

        String func = TreeToStringTranslator.translateTree(ind.trees[0]);
        String comment = ind.fitness.fitnessToStringForHumans();

        try {
            InputStream template = JavaSourceCodeGenerator.class.getResourceAsStream("ClassTemplate.java.tpl");
            BufferedReader reader = new BufferedReader(new InputStreamReader(template));
            StringBuilder builder = new StringBuilder();

            for (String line; (line = reader.readLine()) != null;) {
                builder.append(line);
                builder.append('\n');
            }

            int idx = builder.indexOf("/*functionCode*/");
            if (idx < 0)
                throw new RuntimeException("/*functionCode*/ not found in class template!");
            builder.replace(idx, idx + "/*functionCode*/".length(), func);

            idx = builder.indexOf("/*comment*/");
            if (idx < 0)
                throw new RuntimeException("/*comment*/ not found in class template!");
            builder.replace(idx, idx + "/*comment*/".length(), comment);

            String type = settings.getProblemType() == ProblemType.DOUBLE ? "double" : "int";

            while ((idx = builder.indexOf("/*type*/")) >= 0) {
                builder.replace(idx, idx + "/*type*/".length(), type);
            }

            StringBuilder argumentsBuilder = new StringBuilder();
            for (int i = 0; i < numberOfArguments; ++i) {
                argumentsBuilder.append(type + " x");
                argumentsBuilder.append(i);
                argumentsBuilder.append(',');
            }

            argumentsBuilder.deleteCharAt(argumentsBuilder.length() - 1);
            idx = builder.indexOf("/*arguments*/");
            if (idx < 0)
                throw new RuntimeException("/*arguments*/ not found in class template!");
            builder.replace(idx, idx + "/*arguments*/".length(), argumentsBuilder.toString());

            return builder.toString();
        } catch (UnsupportedEncodingException ex) {
            return null;
        }
    }
}
