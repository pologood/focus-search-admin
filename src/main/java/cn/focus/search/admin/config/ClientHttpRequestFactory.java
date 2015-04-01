package cn.focus.search.admin.config;

import java.net.URI;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;




public class ClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {

    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 300;

    private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = (60 * 1000);

    public ClientHttpRequestFactory(int maxConnectionsPerRoute) {
        super();
        PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager();
        connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(maxConnectionsPerRoute);
        HttpClient httpClient = new DefaultHttpClient(connectionManager);
        super.setHttpClient(httpClient);
        setReadTimeout(DEFAULT_READ_TIMEOUT_MILLISECONDS);
    }

    @Override
    protected void postProcessHttpRequest(HttpUriRequest request) {
        // TODO Auto-generated method stub
        URI url = request.getURI();
        String query = url.getQuery();
        
        if ("GET".equals(request.getMethod().toUpperCase()))    // 只在get方式的请求中使用如下操作
            request.addHeader("sign", genSignParam(query));
        super.postProcessHttpRequest(request);
    }
    
    /**
     * 通过url地址的参数加密
     * 
     * @param queryStr
     * @return
     */
    private String genSignParam(String queryStr) {
        if (StringUtils.isBlank(queryStr)) {
            return DigestUtils.md5Hex(Constants.BUILDING_SECRET_KEY);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(StringUtils.chomp(queryStr, "&")).append(Constants.BUILDING_SECRET_KEY);
            return DigestUtils.md5Hex(sb.toString());
        }

    }

}
