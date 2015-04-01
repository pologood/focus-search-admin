package cn.focus.search.admin.es;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

import java.util.Set;

/**
 * 返回jest客户端实例
 */
@SuppressWarnings("rawtypes")
public class ESJestClient implements FactoryBean<Object> {

    private final Logger log = LoggerFactory.getLogger(ESJestClient.class);

    private boolean isMultiThreaded = true;

    private Set<String> urls;

    public JestClient getObject() {
       JestClientFactory factory = new JestClientFactory();
        HttpClientConfig config = new HttpClientConfig.Builder(urls)
                .maxTotalConnection(100)
                .defaultMaxTotalConnectionPerRoute(1000)
                .multiThreaded(isMultiThreaded)
                .connTimeout(3000)
                .readTimeout(5000)
                .build();
        factory.setHttpClientConfig(config);
        JestClient client = factory.getObject();
        return client;
    }

    public Class<?> getObjectType() {
        return JestClient.class;
    }

    public boolean isSingleton() {
        return false;
    }

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }

}
