package functiongenerator.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Creates the {@linkplain Map} from the {@linkplain String} (which is in the
 * proper format)
 * 
 * <h5>Format</h5> The elements has to be stored: {key1=value1,key2=value2}
 * 
 * @author Piotr Jessa
 * 
 */
public class MapHelper {

    /**
     * 
     * @param input
     *            : the string containing map in the format
     * @return the proper map
     */
    static public Map<String, String> parse(String input) {

        if (input == null)
            throw new IllegalArgumentException("Input cannot be null");

        Map<String, String> map = new HashMap<String, String>();

        if (!input.startsWith("{")) {
            throw new IllegalArgumentException("The opening bracket is missing");
        }

        if (!input.endsWith("}")) {
            throw new IllegalArgumentException("The closing bracket is missing");
        }

        String content = input.substring(1, input.length() - 1);

        if ((!content.equals("")) && content != null) {
            String[] pairs = content.split(", ");

            for (String pair : pairs) {

                String[] v = pair.split("=");

                assert v.length == 2;

                String key = v[0];
                String value = v[1];

                map.put(key, value);
            }
        }

        return map;
    }
}
