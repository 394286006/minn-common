package p.minn.common.aop.ds;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
public class MyDataSourceRegister implements EnvironmentAware {

	private static final Object DEFAULT_DATASOURCE_TYPE = "org.apache.tomcat.jdbc.pool.DataSource";

	private Map<String, DataSource> dataSources = new HashMap<String, DataSource>();

	private DataSource initDataSource(Map<String, Object> property) {
		try {
			Object sourceType = property.get("type");
			DataSource ds = null;
			Class<? extends DataSource> dataSourceType = null;
			if (sourceType == null) {
				sourceType = DEFAULT_DATASOURCE_TYPE;
			}
			dataSourceType = (Class<? extends DataSource>) Class.forName((String) sourceType);
			DataSourceBuilder factory = DataSourceBuilder.create().type(dataSourceType);
			if (sourceType.toString().equals("org.apache.tomcat.jdbc.pool.DataSource")) {
				org.apache.tomcat.jdbc.pool.DataSource dps = (org.apache.tomcat.jdbc.pool.DataSource) factory.build();
				PoolProperties p = new PoolProperties();
				this.propertyInvoke(p, property);
				dps.setPoolProperties(p);
				ds = dps;
			}
			if (sourceType.toString().equals("com.mchange.v2.c3p0.ComboPooledDataSource")) {
				ComboPooledDataSource pool = new ComboPooledDataSource();
				this.methodInvoke(pool, property);
				ds = pool;
			}
			return ds;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("init DataSource Excepton:" + e.getMessage());
		}
	}

	private String getStringValue(Map<String, Object> map, String key) {
		return map.get(key).toString();
	}

	private Integer getIntValue(Map<String, Object> map, String key) {
		return Integer.valueOf(map.get(key).toString());
	}

	private void methodInvoke(Object p, Map<String, Object> property) {
		Method[] methods = p.getClass().getDeclaredMethods();
		for (Method method : methods) {
			String name = method.getName();
			if (name.indexOf("set") != 0) {
				continue;
			}
			Class<?>[] types = method.getParameterTypes();
			if (types.length == 1) {
				String field = name.substring(3, 4).toLowerCase() + name.substring(4);
				if (StringUtils.isEmpty(property.get(field))) {
					continue;
				}
				if (types[0].getTypeName().equals("java.lang.String")) {
					try {
						Method m = p.getClass().getMethod(name, String.class);
						m.invoke(p, this.getStringValue(property, field));
					} catch (Exception e) {
					}
				}
				if (types[0].getTypeName().equals("java.lang.Integer") || types[0].getTypeName().equals("int")) {
					try {
						Method m = p.getClass().getMethod(name, Integer.class);
						m.invoke(p, this.getIntValue(property, field));
					} catch (Exception e) {
					}
				}
				if (types[0].getTypeName().equals("java.lang.Boolean") || types[0].getTypeName().equals("boolean")) {
					try {
						Method m = p.getClass().getMethod(name, Boolean.class);
						m.invoke(p, this.getBooleanValue(property, field));
					} catch (Exception e) {
					}
				}
			}

		}
	}

	private void propertyInvoke(Object p, Map<String, Object> property) throws Exception {
		Field[] fields = p.getClass().getDeclaredFields();
		for (int j = 0; j < fields.length; j++) {
			String fleld = fields[j].getName();
			if (!StringUtils.isEmpty(property.get(fleld))) {
				String name = fleld.substring(0, 1).toUpperCase() + fleld.substring(1);
				String type = fields[j].getGenericType().toString();
				if (type.equals("class java.lang.String")) {
					Method m = p.getClass().getMethod("set" + name, String.class);
					m.invoke(p, this.getStringValue(property, fleld));
				}
				if (type.equals("class java.lang.Integer")) {
					Method m = p.getClass().getMethod("set" + name, Integer.class);
					m.invoke(p, this.getIntValue(property, fleld));
				}
				if (type.equals("class java.lang.Boolean")) {
					Method m = p.getClass().getMethod("set" + name, Boolean.class);
					m.invoke(p, this.getBooleanValue(property, fleld));
				}
			}
		}
	}

	private boolean getBooleanValue(Map<String, Object> map, String key) {
		return Boolean.valueOf(map.get(key).toString());
	}

	@Override
	public void setEnvironment(Environment env) {
		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "custom.datasource.");
		String names = propertyResolver.getProperty("names");
		for (String name : names.split(",")) {
			Map<String, Object> property = propertyResolver.getSubProperties(name + ".");
			DataSource ds = initDataSource(property);
			dataSources.put(name, ds);
		}
	}

	public DataSource getDataSource(String key) {
		return dataSources.get(key);
	}

}