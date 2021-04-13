package com.wdd.myplatform.config;

//@Configuration
public class CuratorConfig {

    /*@Value("${zookeeper.address}")
    private String address;

    @Bean(initMethod="start",destroyMethod = "close")
    public CuratorFramework getCuratorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(address, retryPolicy);
        return client;
    }*/
}
