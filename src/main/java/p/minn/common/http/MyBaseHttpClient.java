package p.minn.common.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import p.minn.common.exception.HttpClientException;
import p.minn.common.utils.ConstantCommon;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.InputStream;

/**
 * 
 * @author minn
 * @QQ:3942986006
 */
public abstract class MyBaseHttpClient {

	protected HttpConnectionManager connectionManager = null;
	protected HttpConnectionManagerParams connectionManagerParams = null;
	protected int maxTotalConnections = 100;
	protected int defaultMaxConnectionsPerHost = 100;
	protected boolean staleCheckingEnabled = true;
	protected int defaultTimeOut = 3000;
	private int dataType = 1;

	public MyBaseHttpClient(Integer dataType) {
		super();
		this.dataType = dataType;
	}

	public void initConnectionManager() {
		if (connectionManager == null) {
			connectionManager = new MultiThreadedHttpConnectionManager();
			connectionManagerParams = new HttpConnectionManagerParams();
			connectionManagerParams.setMaxTotalConnections(maxTotalConnections);
			connectionManagerParams.setDefaultMaxConnectionsPerHost(defaultMaxConnectionsPerHost);
			connectionManagerParams.setStaleCheckingEnabled(staleCheckingEnabled);
			connectionManager.setParams(connectionManagerParams);
		}
	}

	public JsonNode request(String methodstr, String queryString) throws HttpClientException {
		return request(methodstr, queryString, defaultTimeOut, defaultTimeOut, null);
	}

	public JsonNode request(String methodstr, String queryString, int connectionTimeOut, int soTimeOut)
			throws Exception {
		return request(methodstr, queryString, connectionTimeOut, soTimeOut, null);
	}

	public JsonNode request(String methodstr, String queryString, int connectionTimeOut, int soTimeOut, Header header)
			throws HttpClientException {
		JsonNode json = null;
		HttpMethod method = null;
		try {
			initConnectionManager();
			HttpClientParams params = new HttpClientParams();
			params.setConnectionManagerTimeout(connectionTimeOut);
			params.setSoTimeout(soTimeOut);
			params.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
			HttpClient client = new HttpClient(connectionManager);
			client.setConnectionTimeout(connectionTimeOut);
			client.setTimeout(soTimeOut);
			client.setParams(params);
			method = getMethod(methodstr, queryString, header);
			int statusCode = client.executeMethod(method);
			if (statusCode == HttpStatus.SC_OK) {
				json = conver2Json(method.getResponseBodyAsStream());
			} else {
				throw new HttpClientException("back statusCode error:" + statusCode);
			}

		} catch (Exception e) {
			throw new HttpClientException(e.getMessage());
		} finally {
			if (method != null)
				method.releaseConnection();
		}
		return json;
	}

	public abstract HttpMethod getMethod(String methodstr, String queryString, Header header) throws Exception;

	public JsonNode conver2Json(InputStream in) throws Exception {

		if (ConstantCommon.HTTP_CLIENT_XML == this.dataType) {
			XmlMapper xmlMapper = new XmlMapper();
			return xmlMapper.readValue(in, JsonNode.class);
		} else if (ConstantCommon.HTTP_CLIENT_JSON == this.dataType) {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readTree(in);
		} else {
			throw new HttpClientException("dataType not definition:" + this.dataType);
		}
	}

}
