package functiongenerator.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Creates the {@linkplain Map} from the {@linkplain String} (which is in the proper
 * format)
 * 
 * <h5>Format</h5> 
 * The elements has to be stored:   {key1=value1,key2=value2} 
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
		Map<String, String> map = new HashMap<String, String>();

		return map;
	}
}
