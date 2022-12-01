package com.ashera.utils;

import java.util.List;

public class StringUtils {
	/**
	 * Unescapes a string that contains standard Java escape sequences.
	 * <ul>
	 * <li><strong>&#92;b &#92;f &#92;n &#92;r &#92;t &#92;" &#92;'</strong> :
	 * BS, FF, NL, CR, TAB, double and single quote.</li>
	 * <li><strong>&#92;X &#92;XX &#92;XXX</strong> : Octal character
	 * specification (0 - 377, 0x00 - 0xFF).</li>
	 * <li><strong>&#92;uXXXX</strong> : Hexadecimal based Unicode character.</li>
	 * </ul>
	 * 
	 * @param st
	 *            A string optionally containing standard java escape sequences.
	 * @return The translated string.
	 */
	public static String unescapeJavaString(String st) {
		if (st == null) {
			return null;
		}
	    StringBuilder sb = new StringBuilder(st.length());
	    
	    for (int i = 0; i < st.length(); i++) {
	        char ch = st.charAt(i);
	        if (ch == '\\') {
	            char nextChar = (i == st.length() - 1) ? '\\' : st
	                    .charAt(i + 1);
	            // Octal escape?
	            if (nextChar >= '0' && nextChar <= '7') {
	                String code = "" + nextChar;
	                i++;
	                if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
	                        && st.charAt(i + 1) <= '7') {
	                    code += st.charAt(i + 1);
	                    i++;
	                    if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
	                            && st.charAt(i + 1) <= '7') {
	                        code += st.charAt(i + 1);
	                        i++;
	                    }
	                }
	                sb.append((char) Integer.parseInt(code, 8));
	                continue;
	            }
	            switch (nextChar) {
	            case '\\':
	                ch = '\\';
	                break;
	            case 'b':
	                ch = '\b';
	                break;
	            case 'f':
	                ch = '\f';
	                break;
	            case 'n':
	                ch = '\n';
	                break;
	            case 'r':
	                ch = '\r';
	                break;
	            case 't':
	                ch = '\t';
	                break;
	            case '\"':
	                ch = '\"';
	                break;
	            case '\'':
	                ch = '\'';
	                break;
	            // Hex Unicode: u????
	            case 'u':
	                if (i >= st.length() - 5) {
	                    ch = 'u';
	                    break;
	                }
	                int code = Integer.parseInt(
	                        "" + st.charAt(i + 2) + st.charAt(i + 3)
	                                + st.charAt(i + 4) + st.charAt(i + 5), 16);
	                sb.append(Character.toChars(code));
	                i += 5;
	                continue;
	            }
	            i++;
	        }
	        sb.append(ch);
	    }
	    return sb.toString();
	}
	public static String trimString(String myString) {
		if (myString == null) {
			return null;
		}
		String allRemoved = myString.replaceAll("^\\s+|\\s+$", "");
		return allRemoved;
	}
	

	public static String floatToString(float f) {
		int i = (int) f;
	    return (f == i ? String.valueOf(i) : String.valueOf(f));
	}
	
	public static String join(String separator, List<String> input) {
        if (input == null || input.size() <= 0) return "";

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < input.size(); i++) {

            sb.append(input.get(i));

            // if not the last item
            if (i != input.size() - 1) {
                sb.append(separator);
            }

        }

        return sb.toString();

    }
	
	public static Boolean getBoolean(Object payLoad) {
		if (payLoad == null) {
			return Boolean.FALSE;
		}
		
		if (payLoad instanceof Boolean) {
			return (Boolean) payLoad;
		}
		
		String string = payLoad.toString();
		Boolean result = convertToBoolean(string);
		return result;
	}
	private static  Boolean convertToBoolean(String value) {
		Boolean returnValue = Boolean.FALSE;
	    if ("1".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value) || 
	        "true".equalsIgnoreCase(value) || "on".equalsIgnoreCase(value))
	        returnValue = Boolean.TRUE;
	    return returnValue;
	}
	public static String[] split(String formGroupId, String seperator) {
		return formGroupId.split(seperator);
	}
	
	
}
