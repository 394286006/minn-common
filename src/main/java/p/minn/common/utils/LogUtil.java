package p.minn.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import p.minn.common.annotation.LogAnnotation;

/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
public class LogUtil {

	public static boolean checkLog(Object obj) {
		LogAnnotation lca = obj.getClass().getAnnotation(LogAnnotation.class);
		if (lca == null) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean checkDaoLog(Method method) {
		LogAnnotation lca = method.getDeclaringClass().getAnnotation(
				LogAnnotation.class);
		if (lca == null) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean checkLog(Method method) {
		LogAnnotation lma = method.getAnnotation(LogAnnotation.class);
		if (lma == null) {
			return false;
		} else {
			return true;
		}
	}

	public static String getResourceKey(Method method) {
		LogAnnotation lma = method.getAnnotation(LogAnnotation.class);
		return lma.resourceKey();
	}

	public static String getDaoResourceKey(Method method) {
		LogAnnotation lma = method.getDeclaringClass().getAnnotation(
				LogAnnotation.class);
		return lma.resourceKey();
	}

	public static List<Map<String, String>> getBeanMap(Object bean) {
		List<Map<String, String>> operatorlogdetails = new ArrayList<Map<String, String>>();

		if (bean instanceof LogArrayList) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<Object> list = (List) bean;
			for (Object obj : list) {
				getObValue(operatorlogdetails, obj);
			}
		} else {

			getObValue(operatorlogdetails, bean);

		}
		return operatorlogdetails;
	}

	private static void getObValue(
			List<Map<String, String>> operatorlogdetails, Object obj) {

		try {

			if (!checkLog(obj)) {
				return;
			}

			Map<String, String> keys = new HashMap<String, String>();

			getKeys(obj.getClass().getDeclaredMethods(), keys);

			getKeys(obj.getClass().getSuperclass().getDeclaredMethods(), keys);

			getFields(obj.getClass().getDeclaredFields(), obj,
					operatorlogdetails, keys);

			getFields(obj.getClass().getSuperclass().getDeclaredFields(), obj,
					operatorlogdetails, keys);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void getKeys(Method[] ms, Map<String, String> keys) {
		for (int i = 0; i < ms.length; i++) {
			String m = ms[i].getName();
			if (m.indexOf("get") != -1) {
				m = m.substring(3, m.length()).toLowerCase();
				keys.put(m, m);
			}
		}
	}

	private static void getFields(Field[] fs, Object obj,
			List<Map<String, String>> operatorlogdetails,
			Map<String, String> keys) throws Exception {
		Map<String, String> operatorlogdetail = null;
		for (Field f : fs) {
			if (keys.containsKey(f.getName().toLowerCase())) {
				f.setAccessible(true);
				LogAnnotation lfa = f.getAnnotation(LogAnnotation.class);
				if (lfa == null) {
					continue;
				}
				operatorlogdetail = new HashMap<String, String>();
				operatorlogdetail.put("mkey", lfa.mkey());
				operatorlogdetail.put("name", lfa.resourceKey());
				String val = null;
				if (f.get(obj) != null) {
					val = f.get(obj).toString();
				}
				operatorlogdetail.put("val", val);

				operatorlogdetails.add(operatorlogdetail);
			}
		}
	}
}
