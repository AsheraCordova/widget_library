//start - license
/*
 * Copyright (c) 2025 Ashera Cordova
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
//end - license
package com.ashera.utils;

import java.util.HashMap;
import java.util.Map;

public class ColorUtil {
	private static Map<String, String> colorToHexMap = new HashMap<String, String>();
	static {
		colorToHexMap.put("aliceblue", "#f0f8ff");
		colorToHexMap.put("antiquewhite","#faebd7");
		colorToHexMap.put("aqua","#00ffff");
		colorToHexMap.put("aquamarine","#7fffd4");
		colorToHexMap.put("azure","#f0ffff");
		colorToHexMap.put("beige","#f5f5dc");
		colorToHexMap.put("bisque","#ffe4c4");
		colorToHexMap.put("black","#000000");
		colorToHexMap.put("blanchedalmond","#ffebcd");
		colorToHexMap.put("blue","#0000ff");
		colorToHexMap.put("blueviolet","#8a2be2");
		colorToHexMap.put("brown","#a52a2a");
		colorToHexMap.put("burlywood","#deb887");
		colorToHexMap.put("cadetblue","#5f9ea0");
		colorToHexMap.put("chartreuse","#7fff00");
		colorToHexMap.put("chocolate","#d2691e");
		colorToHexMap.put("coral","#ff7f50");
		colorToHexMap.put("cornflowerblue","#6495ed");
		colorToHexMap.put("cornsilk","#fff8dc");
		colorToHexMap.put("crimson","#dc143c");
		colorToHexMap.put("cyan","#00ffff");
		colorToHexMap.put("darkblue","#00008b");
		colorToHexMap.put("darkcyan","#008b8b");
		colorToHexMap.put("darkgoldenrod","#b8860b");
		colorToHexMap.put("darkgray","#a9a9a9");
		colorToHexMap.put("darkgreen","#006400");
		colorToHexMap.put("darkkhaki","#bdb76b");
		colorToHexMap.put("darkmagenta","#8b008b");
		colorToHexMap.put("darkolivegreen","#556b2f");
		colorToHexMap.put("darkorange", "#ff8c00");
		colorToHexMap.put("darkorchid", "#9932cc");
		colorToHexMap.put("darkred", "#8b0000");
		colorToHexMap.put("darksalmon", "#e9967a");
		colorToHexMap.put("darkseagreen", "#8fbc8f");
		colorToHexMap.put("darkslateblue", "#483d8b");
		colorToHexMap.put("darkslategray", "#2f4f4f");
		colorToHexMap.put("darkturquoise", "#00ced1");
		colorToHexMap.put("darkviolet", "#9400d3");
		colorToHexMap.put("deeppink", "#ff1493");
		colorToHexMap.put("deepskyblue", "#00bfff");
		colorToHexMap.put("dimgray", "#696969");
		colorToHexMap.put("dodgerblue", "#1e90ff");
		colorToHexMap.put("firebrick", "#b22222");
		colorToHexMap.put("floralwhite", "#fffaf0");
		colorToHexMap.put("forestgreen", "#228b22");
		colorToHexMap.put("fuchsia", "#ff00ff");
		colorToHexMap.put("gainsboro", "#dcdcdc");
		colorToHexMap.put("ghostwhite", "#f8f8ff");
		colorToHexMap.put("gold", "#ffd700");
		colorToHexMap.put("goldenrod", "#daa520");
		colorToHexMap.put("gray", "#808080");
		colorToHexMap.put("green", "#008000");
		colorToHexMap.put("greenyellow", "#adff2f");
		colorToHexMap.put("honeydew", "#f0fff0");
		colorToHexMap.put("hotpink", "#ff69b4");
		colorToHexMap.put("indianred ", "#cd5c5c");
		colorToHexMap.put("indigo", "#4b0082");
		colorToHexMap.put("ivory", "#fffff0");
		colorToHexMap.put("khaki", "#f0e68c");
		colorToHexMap.put("lavender", "#e6e6fa");
		colorToHexMap.put("lavenderblush", "#fff0f5");
		colorToHexMap.put("lawngreen", "#7cfc00");
		colorToHexMap.put("lemonchiffon", "#fffacd");
		colorToHexMap.put("lightblue", "#add8e6");
		colorToHexMap.put("lightcoral", "#f08080");
		colorToHexMap.put("lightcyan", "#e0ffff");
		colorToHexMap.put("lightgoldenrodyellow", "#fafad2");
		colorToHexMap.put("lightgrey", "#d3d3d3");
		colorToHexMap.put("lightgreen", "#90ee90");
		colorToHexMap.put("lightpink", "#ffb6c1");
		colorToHexMap.put("lightsalmon", "#ffa07a");
		colorToHexMap.put("lightseagreen", "#20b2aa");
		colorToHexMap.put("lightskyblue", "#87cefa");
		colorToHexMap.put("lightslategray", "#778899");
		colorToHexMap.put("lightsteelblue", "#b0c4de");
		colorToHexMap.put("lightyellow", "#ffffe0");
		colorToHexMap.put("lime", "#00ff00");
		colorToHexMap.put("limegreen", "#32cd32");
		colorToHexMap.put("linen", "#faf0e6");
		colorToHexMap.put("magenta", "#ff00ff");
		colorToHexMap.put("maroon", "#800000");
		colorToHexMap.put("mediumaquamarine", "#66cdaa");
		colorToHexMap.put("mediumblue", "#0000cd");
		colorToHexMap.put("mediumorchid", "#ba55d3");
		colorToHexMap.put("mediumpurple", "#9370d8");
		colorToHexMap.put("mediumseagreen", "#3cb371");
		colorToHexMap.put("mediumslateblue", "#7b68ee");
		colorToHexMap.put("mediumspringgreen", "#00fa9a");
		colorToHexMap.put("mediumturquoise", "#48d1cc");
		colorToHexMap.put("mediumvioletred", "#c71585");
		colorToHexMap.put("midnightblue", "#191970");
		colorToHexMap.put("mintcream", "#f5fffa");
		colorToHexMap.put("mistyrose", "#ffe4e1");
		colorToHexMap.put("moccasin", "#ffe4b5");
		colorToHexMap.put("navajowhite", "#ffdead");
		colorToHexMap.put("navy", "#000080");
		colorToHexMap.put("oldlace", "#fdf5e6");
		colorToHexMap.put("olive", "#808000");
		colorToHexMap.put("olivedrab", "#6b8e23");
		colorToHexMap.put("orange", "#ffa500");
		colorToHexMap.put("orangered", "#ff4500");
		colorToHexMap.put("orchid", "#da70d6");
		colorToHexMap.put("palegoldenrod", "#eee8aa");
		colorToHexMap.put("palegreen", "#98fb98");
		colorToHexMap.put("paleturquoise", "#afeeee");
		colorToHexMap.put("palevioletred", "#d87093");
		colorToHexMap.put("papayawhip", "#ffefd5");
		colorToHexMap.put("peachpuff", "#ffdab9");
		colorToHexMap.put("peru", "#cd853f");
		colorToHexMap.put("pink", "#ffc0cb");
		colorToHexMap.put("plum", "#dda0dd");
		colorToHexMap.put("powderblue", "#b0e0e6");
		colorToHexMap.put("purple", "#800080");
		colorToHexMap.put("red", "#ff0000");
		colorToHexMap.put("rosybrown", "#bc8f8f");
		colorToHexMap.put("royalblue", "#4169e1");
		colorToHexMap.put("saddlebrown", "#8b4513");
		colorToHexMap.put("salmon", "#fa8072");
		colorToHexMap.put("sandybrown", "#f4a460");
		colorToHexMap.put("seagreen", "#2e8b57");
		colorToHexMap.put("seashell", "#fff5ee");
		colorToHexMap.put("sienna", "#a0522d");
		colorToHexMap.put("silver", "#c0c0c0");
		colorToHexMap.put("skyblue", "#87ceeb");
		colorToHexMap.put("slateblue", "#6a5acd");
		colorToHexMap.put("slategray", "#708090");
		colorToHexMap.put("snow", "#fffafa");
		colorToHexMap.put("springgreen", "#00ff7f");
		colorToHexMap.put("steelblue", "#4682b4");
		colorToHexMap.put("tan", "#d2b48c");
		colorToHexMap.put("teal", "#008080");
		colorToHexMap.put("thistle", "#d8bfd8");
		colorToHexMap.put("tomato", "#ff6347");
		colorToHexMap.put("turquoise", "#40e0d0");
		colorToHexMap.put("violet", "#ee82ee");
		colorToHexMap.put("wheat", "#f5deb3");
		colorToHexMap.put("white", "#ffffff");
		colorToHexMap.put("whitesmoke", "#f5f5f5");
		colorToHexMap.put("yellow", "#ffff00");
		colorToHexMap.put("yellowgreen", "#9acd32");
	}

	public static String colorToHex(String color) {
		if (colorToHexMap.containsKey(color.toLowerCase())) {
			return colorToHexMap.get(color.toLowerCase());
		}
		if (color.startsWith("#")) {
			if (color.length() == 4) {
				return "#" + color.charAt(1) + "" + color.charAt(1) + "" +
						color.charAt(2) + "" + color.charAt(2) + "" +
						color.charAt(3) + "" + color.charAt(3) + "";
			}
		}
		return color;
	}
	
	

	/**
	 * 
	 * @param colorStr e.g. "#FFFFFF"
	 * @return 
	 */
	public static int[] hexStr2Int(String colorStr) {
		colorStr = colorToHex(colorStr);
		if (colorStr.length() == 7) {
			return new int[] {
		            Integer.valueOf( colorStr.substring( 1, 7 ), 16 ), 255
			};
		} else if (colorStr.length() == 9) {
			return new int[] { 
				Integer.valueOf(colorStr.substring(3), 16),
				Integer.valueOf(colorStr.substring(1, 3), 16) 
			};
		} else {
			throw new IllegalArgumentException("Unknown color");
		}
	}



	public static String getColorString(int intColor) {
		return String.format("#%06X", 0xFFFFFF & intColor);
	}
	
	public static String getColorString(int red, int blue, int green) {
        int rgb = ((red&0x0ff)<<16)|((green&0x0ff)<<8)|(blue&0x0ff);
        return getColorString(rgb);
	}
}
