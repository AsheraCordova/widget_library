package com.ashera.widget;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.ashera.attributedtext.AttributedString;
import com.ashera.converter.IConverter;
import com.ashera.core.IFragment;
import com.ashera.model.FontMetricsDescriptor;
import com.ashera.plugin.IPlugin;
import com.ashera.plugin.PluginManager;

public class PluginInvoker {
	//start - converter
	public static Object convertFrom(IConverter converter,Map<String,Object> dependentAttributesMap,Object value,IFragment fragment) {
		IPlugin plugin = PluginManager.get("converter");
		return plugin.invoke("convertFrom", converter,dependentAttributesMap,value,fragment);
	}

	public static Object convertTo(IConverter converter,Object objValue,IFragment fragment) {
		IPlugin plugin = PluginManager.get("converter");
		return plugin.invoke("convertTo", converter,objValue,fragment);
	}

	public static IConverter getConverter(String name) {
		IPlugin plugin = PluginManager.get("converter");
		return (IConverter)plugin.invoke("getConverter", name);
	}

	public static java.util.List<String> getDependentAttributes(IConverter converter) {
		IPlugin plugin = PluginManager.get("converter");
		return (java.util.List<String>)plugin.invoke("getDependentAttributes", converter);
	}

	public static Object getColor(String color) {
		IPlugin plugin = PluginManager.get("converter");
		return plugin.invoke("getColor", color);
	}

	public static float convertDpToPixel(String dimen) {
		IPlugin plugin = PluginManager.get("converter");
		return (float)plugin.invoke("convertDpToPixel", dimen);
	}

	public static float convertSpToPixel(String dimen) {
		IPlugin plugin = PluginManager.get("converter");
		return (float)plugin.invoke("convertSpToPixel", dimen);
	}

	public static String convertPixelToDp(Object dimen,boolean isInt) {
		IPlugin plugin = PluginManager.get("converter");
		return (String)plugin.invoke("convertPixelToDp", dimen,isInt);
	}

	public static String convertPixelToSp(Object dimen,boolean isInt) {
		IPlugin plugin = PluginManager.get("converter");
		return (String)plugin.invoke("convertPixelToSp", dimen,isInt);
	}

	//end - converter

	//start - jsonadapter
	public static Map<String,Object> getMap(Object payLoad) {
		IPlugin plugin = PluginManager.get("jsonadapter");
		return (Map<String,Object>)plugin.invoke("getMap", payLoad);
	}

	public static Object getNativeMap(Map<String,Object> payLoad) {
		IPlugin plugin = PluginManager.get("jsonadapter");
		return plugin.invoke("getNativeMap", payLoad);
	}

	public static void putJSONSafeObjectIntoMap(Map<String,Object> map,String key,Object value) {
		IPlugin plugin = PluginManager.get("jsonadapter");
		plugin.invoke("putJSONSafeObjectIntoMap", map,key,value);
	}

	public static Object[] getArray(Object payLoad) {
		IPlugin plugin = PluginManager.get("jsonadapter");
		return (Object[])plugin.invoke("getArray", payLoad);
	}

	public static List<Object> getList(Object payLoad) {
		IPlugin plugin = PluginManager.get("jsonadapter");
		return (List<Object>)plugin.invoke("getList", payLoad);
	}

	public static boolean isBoolean(Object obj) {
		IPlugin plugin = PluginManager.get("jsonadapter");
		return (boolean)plugin.invoke("isBoolean", obj);
	}

	public static Boolean getBoolean(Object payLoad) {
		IPlugin plugin = PluginManager.get("jsonadapter");
		return (Boolean)plugin.invoke("getBoolean", payLoad);
	}

	public static String getString(Object object) {
		IPlugin plugin = PluginManager.get("jsonadapter");
		return (String)plugin.invoke("getString", object);
	}

	public static Integer getInt(Object object) {
		IPlugin plugin = PluginManager.get("jsonadapter");
		return (Integer)plugin.invoke("getInt", object);
	}

	public static Float getFloat(Object object) {
		IPlugin plugin = PluginManager.get("jsonadapter");
		return (Float)plugin.invoke("getFloat", object);
	}

	public static Double getDouble(Object object) {
		IPlugin plugin = PluginManager.get("jsonadapter");
		return (Double)plugin.invoke("getDouble", object);
	}

	public static String marshal(Object object) {
		IPlugin plugin = PluginManager.get("jsonadapter");
		return (String)plugin.invoke("marshal", object);
	}

	public static Object toJsonTree(Object object) {
		IPlugin plugin = PluginManager.get("jsonadapter");
		return plugin.invoke("toJsonTree", object);
	}

	public static <T>T unmarshal(String json,Class<T> clazz) {
		IPlugin plugin = PluginManager.get("jsonadapter");
		return (T)plugin.invoke("unmarshal", json,clazz);
	}

	public static Map<String,Object> getJSONCompatMap() {
		IPlugin plugin = PluginManager.get("jsonadapter");
		return (Map<String,Object>)plugin.invoke("getJSONCompatMap");
	}

	public static Object getJSONSafeObj(Object obj) {
		IPlugin plugin = PluginManager.get("jsonadapter");
		return plugin.invoke("getJSONSafeObj", obj);
	}

	public static boolean isNull(Object obj) {
		IPlugin plugin = PluginManager.get("jsonadapter");
		return (boolean)plugin.invoke("isNull", obj);
	}

	//end - jsonadapter
	
	//start - navigator
	public static void navigate(String actionId,String varExpression,Object payload,IFragment fragment) {
		IPlugin plugin = PluginManager.get("navigator");
		plugin.invoke("navigate", actionId,varExpression,payload,fragment);
	}

	//end - navigator	
	//start - htmlparser
	public static IWidget parse(String html,boolean template,IFragment fragment) {
		IPlugin plugin = PluginManager.get("htmlparser");
		return (IWidget)plugin.invoke("parse", html,template,fragment);
	}

	public static IWidget parseWithParent(String html,boolean template,HasWidgets parent,IFragment fragment) {
		IPlugin plugin = PluginManager.get("htmlparser");
		return (IWidget)plugin.invoke("parseWithParent", html,template,parent,fragment);
	}

	public static IWidget parseFile(String fileName,boolean template,IFragment fragment) {
		IPlugin plugin = PluginManager.get("htmlparser");
		return (IWidget)plugin.invoke("parseFile", fileName,template,fragment);
	}

	public static IWidget parseFragment(String fileName,boolean template,IFragment fragment) {
		IPlugin plugin = PluginManager.get("htmlparser");
		return (IWidget)plugin.invoke("parseFragment", fileName,template,fragment);
	}

	public static void parseInclude(HasWidgets parent,String fileName,String componentId,boolean template,IFragment fragment) {
		IPlugin plugin = PluginManager.get("htmlparser");
		plugin.invoke("parseInclude", parent,fileName,componentId,template,fragment);
	}

	public static Object getHandler(HasWidgets parent,int index,IFragment fragment) {
		IPlugin plugin = PluginManager.get("htmlparser");
		return plugin.invoke("getHandler", parent,index,fragment);
	}

	public static IWidget handlerStart(Object handler,IWidget widget,int index) {
		IPlugin plugin = PluginManager.get("htmlparser");
		return (IWidget)plugin.invoke("handlerStart", handler,widget,index);
	}

	public static void handlerEnd(Object handler,IWidget widget) {
		IPlugin plugin = PluginManager.get("htmlparser");
		plugin.invoke("handlerEnd", handler,widget);
	}

	public static void addToCurrentParent(Object handler,IWidget widget) {
		IPlugin plugin = PluginManager.get("htmlparser");
		plugin.invoke("addToCurrentParent", handler,widget);
	}

	public static String xml2json(String xml,IFragment fragment) {
		IPlugin plugin = PluginManager.get("htmlparser");
		return (String)plugin.invoke("xml2json", xml,fragment);
	}

	//end - htmlparser

	//start - core
	public static String getAssetMode(IFragment fragment) {
		IPlugin plugin = PluginManager.get("core");
		return (String)plugin.invoke("getAssetMode", fragment);
	}

	public static String getDevServerIp(IFragment fragment) {
		IPlugin plugin = PluginManager.get("core");
		return (String)plugin.invoke("getDevServerIp", fragment);
	}

	public static String getOrientation() {
		IPlugin plugin = PluginManager.get("core");
		return (String)plugin.invoke("getOrientation");
	}

	public static int getScreenWidth() {
		IPlugin plugin = PluginManager.get("core");
		return (int)plugin.invoke("getScreenWidth");
	}

	public static int getScreenHeight() {
		IPlugin plugin = PluginManager.get("core");
		return (int)plugin.invoke("getScreenHeight");
	}

	public static int getScreenWidthDp() {
		IPlugin plugin = PluginManager.get("core");
		return (int)plugin.invoke("getScreenWidthDp");
	}

	public static int getScreenHeightDp() {
		IPlugin plugin = PluginManager.get("core");
		return (int)plugin.invoke("getScreenHeightDp");
	}

	public static String getOS() {
		IPlugin plugin = PluginManager.get("core");
		return (String)plugin.invoke("getOS");
	}

	public static String getFileAsset(String path,IFragment fragment) {
		IPlugin plugin = PluginManager.get("core");
		return (String)plugin.invoke("getFileAsset", path,fragment);
	}

	public static String getDensityName() {
		IPlugin plugin = PluginManager.get("core");
		return (String)plugin.invoke("getDensityName");
	}

	public static int getDensity() {
		IPlugin plugin = PluginManager.get("core");
		return (int)plugin.invoke("getDensity");
	}

	public static Object postDelayed(Runnable mTickRunnable,int delay) {
		IPlugin plugin = PluginManager.get("core");
		return plugin.invoke("postDelayed", mTickRunnable,delay);
	}

	public static void removeCallbacks(Object handler,Runnable mTickRunnable) {
		IPlugin plugin = PluginManager.get("core");
		plugin.invoke("removeCallbacks", handler,mTickRunnable);
	}

	public static void putObjectToBundle(Object bundle,String key,Object data) {
		IPlugin plugin = PluginManager.get("core");
		plugin.invoke("putObjectToBundle", bundle,key,data);
	}

	public static void releaseNativeResources(List<Object> object) {
		IPlugin plugin = PluginManager.get("core");
		plugin.invoke("releaseNativeResources", object);
	}

	public static FontMetricsDescriptor getFontMetrics(Object font) {
		IPlugin plugin = PluginManager.get("core");
		return (FontMetricsDescriptor)plugin.invoke("getFontMetrics", font);
	}

	public static Object createDrawable(String type) {
		IPlugin plugin = PluginManager.get("core");
		return plugin.invoke("createDrawable", type);
	}

	public static AttributedString createAttributedString(IFragment fragment,String text) {
		IPlugin plugin = PluginManager.get("core");
		return (AttributedString)plugin.invoke("createAttributedString", fragment,text);
	}

	public static float getDisplayMetricDensity() {
		IPlugin plugin = PluginManager.get("core");
		return (float)plugin.invoke("getDisplayMetricDensity");
	}

	public static String getAttributedBulletHtml() {
		IPlugin plugin = PluginManager.get("core");
		return (String)plugin.invoke("getAttributedBulletHtml");
	}

	public static File getExternalFilesDir(Object context) {
		IPlugin plugin = PluginManager.get("core");
		return (File)plugin.invoke("getExternalFilesDir", context);
	}

	public static int getMaxMemory(Object context) {
		IPlugin plugin = PluginManager.get("core");
		return (int)plugin.invoke("getMaxMemory", context);
	}

	public static Object decodeBitmapStream(InputStream stream,Object options) {
		IPlugin plugin = PluginManager.get("core");
		return plugin.invoke("decodeBitmapStream", stream,options);
	}

	public static void runOnMainThread(Runnable runnable) {
		IPlugin plugin = PluginManager.get("core");
		plugin.invoke("runOnMainThread", runnable);
	}

	public static void enqueueTaskForEventLoop(Runnable runnable,long delay) {
		IPlugin plugin = PluginManager.get("core");
		plugin.invoke("enqueueTaskForEventLoop", runnable,delay);
	}

	public static String resolveCDVFileLocation(String cdvUrl,IFragment fragment) {
		IPlugin plugin = PluginManager.get("core");
		return (String)plugin.invoke("resolveCDVFileLocation", cdvUrl,fragment);
	}

	public static String readCdvDataAsString(String directoryName,String fileName,IFragment fragment) {
		IPlugin plugin = PluginManager.get("core");
		return (String)plugin.invoke("readCdvDataAsString", directoryName,fileName,fragment);
	}

	//end - core
	public static void registerFont(String fontFamily, String src,
			String fontStyle, String fontWeight, Map<String, Object> metadata) {
		IPlugin plugin = PluginManager.get("font");
		plugin.invoke("registerFont", fontFamily, src, fontStyle, fontWeight, metadata);		
	}

}
