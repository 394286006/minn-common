package p.minn.common.aop.ds;

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

	private DataSource initDataSource(Map<String, Object> proterty) {
		try {
			Object type = proterty.get("type");
			DataSource ds = null;
			String driverClassName = proterty.get("driver-class-name").toString();
			String url = proterty.get("url").toString();
			String username = proterty.get("username").toString();
			String password = proterty.get("password").toString();
			String tesetable = StringUtils.isEmpty(proterty.get("testtable")) ? "select 1"
					: proterty.get("testtable").toString();
			Class<? extends DataSource> dataSourceType = null;
			if (type == null) {
				type = DEFAULT_DATASOURCE_TYPE;
			}
			dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
			DataSourceBuilder factory = DataSourceBuilder.create().type(dataSourceType);
			if (type.toString().equals("org.apache.tomcat.jdbc.pool.DataSource")) {
				org.apache.tomcat.jdbc.pool.DataSource dps = (org.apache.tomcat.jdbc.pool.DataSource) factory.build();
				PoolProperties p = new PoolProperties();
				p.setUrl(url);
				p.setDriverClassName(driverClassName);
				p.setUsername(username);
				p.setPassword(password);
				p.setValidationQuery(tesetable);
				if (!StringUtils.isEmpty(proterty.get("jmxEnabled"))) {
					p.setJmxEnabled(this.getBooleanValue(proterty, "jmxEnabled"));
				}
				if (!StringUtils.isEmpty(proterty.get("testWhileIdle"))) {
					p.setTestWhileIdle(this.getBooleanValue(proterty, "testWhileIdle"));
				}
				if (!StringUtils.isEmpty(proterty.get("testOnBorrow"))) {
					p.setTestOnBorrow(this.getBooleanValue(proterty, "testOnBorrow"));
				}
				if (!StringUtils.isEmpty(proterty.get("testOnReturn"))) {
					p.setTestOnReturn(this.getBooleanValue(proterty, "testOnReturn"));
				}
				if (!StringUtils.isEmpty(proterty.get("validationInterval"))) {
					p.setValidationInterval(this.getIntValue(proterty, "validationInterval"));
				}
				if (!StringUtils.isEmpty(proterty.get("timeBetweenEvictionRunsMillis"))) {
					p.setTimeBetweenEvictionRunsMillis(this.getIntValue(proterty, "timeBetweenEvictionRunsMillis"));
				}
				if (!StringUtils.isEmpty(proterty.get("maxActive"))) {
					p.setMaxActive(this.getIntValue(proterty, "maxActive"));
				}
				if (!StringUtils.isEmpty(proterty.get("initialSize"))) {
					p.setInitialSize(this.getIntValue(proterty, "initialSize"));
				}
				if (!StringUtils.isEmpty(proterty.get("maxWait"))) {
					p.setMaxWait(this.getIntValue(proterty, "maxWait"));
				}
				if (!StringUtils.isEmpty(proterty.get("removeAbandonedTimeout"))) {
					p.setRemoveAbandonedTimeout(this.getIntValue(proterty, "removeAbandonedTimeout"));
				}
				if (!StringUtils.isEmpty(proterty.get("minEvictableIdleTimeMillis"))) {
					p.setMinEvictableIdleTimeMillis(this.getIntValue(proterty, "minEvictableIdleTimeMillis"));
				}
				if (!StringUtils.isEmpty(proterty.get("minIdle"))) {
					p.setMinIdle(this.getIntValue(proterty, "minIdle"));
				}
				if (!StringUtils.isEmpty(proterty.get("logAbandoned"))) {
					p.setLogAbandoned(this.getBooleanValue(proterty, "logAbandoned"));
				}
				if (!StringUtils.isEmpty(proterty.get("removeAbandoned"))) {
					p.setRemoveAbandoned(this.getBooleanValue(proterty, "removeAbandoned"));
				}
				p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
						+ "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
				dps.setPoolProperties(p);
				ds = dps;
			}
			if (type.toString().equals("com.mchange.v2.c3p0.ComboPooledDataSource")) {
				ComboPooledDataSource pool = new ComboPooledDataSource();

				pool.setDriverClass(driverClassName);
				pool.setJdbcUrl(url);
				pool.setUser(username);
				pool.setPassword(password);
				pool.setAutomaticTestTable(tesetable);
				if (!StringUtils.isEmpty(proterty.get("maxIdleTime"))) {
					pool.setMaxIdleTime(this.getIntValue(proterty, "maxIdleTime"));
				}
				if (!StringUtils.isEmpty(proterty.get("minPoolSize"))) {
					pool.setMinPoolSize(this.getIntValue(proterty, "minPoolSize"));
				}
				if (!StringUtils.isEmpty(proterty.get("maxPoolSize"))) {
					pool.setMaxPoolSize(this.getIntValue(proterty, "maxPoolSize"));
				}
				if (!StringUtils.isEmpty(proterty.get("loginTimeout"))) {
					pool.setLoginTimeout(this.getIntValue(proterty, "loginTimeout"));
				}
				if (!StringUtils.isEmpty(proterty.get("acquireIncrement"))) {
					pool.setAcquireIncrement(this.getIntValue(proterty, "acquireIncrement"));
				}
				if (!StringUtils.isEmpty(proterty.get("acquireRetryAttempts"))) {
					pool.setAcquireRetryAttempts(this.getIntValue(proterty, "acquireRetryAttempts"));
				}
				if (!StringUtils.isEmpty(proterty.get("acquireRetryDelay"))) {
					pool.setAcquireRetryDelay(this.getIntValue(proterty, "acquireRetryDelay"));
				}
				if (!StringUtils.isEmpty(proterty.get("breakAfterAcquireFailure"))) {
					pool.setBreakAfterAcquireFailure(this.getBooleanValue(proterty, "breakAfterAcquireFailure"));
				}
				if (!StringUtils.isEmpty(proterty.get("checkoutTimeout"))) {
					pool.setCheckoutTimeout(this.getIntValue(proterty, "checkoutTimeout"));
				}
				if (!StringUtils.isEmpty(proterty.get("idleConnectionTestPeriod"))) {
					pool.setIdleConnectionTestPeriod(this.getIntValue(proterty, "idleConnectionTestPeriod"));
				}
				if (!StringUtils.isEmpty(proterty.get("maxStatements"))) {
					pool.setMaxStatements(this.getIntValue(proterty, "maxStatements"));
				}
				if (!StringUtils.isEmpty(proterty.get("maxStatementsPerConnection"))) {
					pool.setMaxStatementsPerConnection(this.getIntValue(proterty, "maxStatementsPerConnection"));
				}
				ds = pool;
			}
			return ds;
		} catch (Exception e) {
			throw new RuntimeException("init DataSource Excepton:" + e.getMessage());
		}
	}

	private String getStringValue(Map<String, Object> map, String key) {
		return map.get(key).toString();
	}

	private Integer getIntValue(Map<String, Object> map, String key) {
		return Integer.valueOf(map.get(key).toString());
	}

	private boolean getBooleanValue(Map<String, Object> map, String key) {
		return Boolean.valueOf(map.get(key).toString());
	}

	@Override
	public void setEnvironment(Environment env) {
		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "custom.datasource.");
		String names = propertyResolver.getProperty("names");
		for (String name : names.split(",")) {
			Map<String, Object> proterty = propertyResolver.getSubProperties(name + ".");
			DataSource ds = initDataSource(proterty);
			dataSources.put(name, ds);
		}
	}

	public DataSource getDataSource(String key) {
		return dataSources.get(key);
	}

}