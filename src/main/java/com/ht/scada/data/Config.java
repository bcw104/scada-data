package com.ht.scada.data;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * Created with IntelliJ IDEA.
 * User: bcw
 * Date: 13-4-26
 * Time: 下午8:40
 * To change this template use File | Settings | File Templates.
 */
public enum Config {
    INSTANCE;

    private PropertiesConfiguration config;

    /**
     * nosql数据库地址和端口
     */
    private String[] kvHostPort;
    /**
     * nosql数据库kvStore名称
     */
    private String kvStoreName;

    private String redisHost = "localhost";
    private int redisPort = 6379;
    private int redisMaxActive = 20;
    private int redisMaxIdle = 60000;
    private int redisMaxWait = 10000;
    private String redisPassword;
    private int redisTimeout;

    private Config() {
        try {
            config = new PropertiesConfiguration(Config.class.getResource("/config.properties").toURI().toURL());
            //config = new PropertiesConfiguration(configPath);
            config.setAutoSave(true);

            kvHostPort = config.getStringArray("kv.hostPort");
            kvStoreName = config.getString("kv.storeName");

            redisHost = config.getString("redis.host");
            redisPort = config.getInt("redis.port",6379);
            redisMaxActive = config.getInt("redis.maxActive", 20);
            redisMaxIdle = config.getInt("redis.maxIdle", 60000);
            redisMaxWait = config.getInt("redis.maxWait", 10000);
            redisTimeout = config.getInt("redis.timeout", 2000);
            redisPassword = config.getString("redis.password");

        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public PropertiesConfiguration getConfig() {
        return config;
    }

    public String[] getKvHostPort() {
        return kvHostPort;
    }

    public void setKvHostPort(String[] kvHostPort) {
        this.kvHostPort = kvHostPort;
        config.setProperty("kvHostPort", kvHostPort);
    }

    public String getKvStoreName() {
        return kvStoreName;
    }

    public void setKvStoreName(String kvStoreName) {
        this.kvStoreName = kvStoreName;
        config.setProperty("kv.storeName", kvStoreName);
    }

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
        config.setProperty("redis.host", redisHost);
    }

    public int getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
        config.setProperty("redis.port", redisPort);
    }

    public int getRedisMaxActive() {
        return redisMaxActive;
    }

    public void setRedisMaxActive(int redisMaxActive) {
        this.redisMaxActive = redisMaxActive;
        config.setProperty("redis.maxActive", redisMaxActive);
    }

    public int getRedisMaxIdle() {
        return redisMaxIdle;
    }

    public void setRedisMaxIdle(int redisMaxIdle) {
        this.redisMaxIdle = redisMaxIdle;
        config.setProperty("redis.maxIdle", redisMaxIdle);
    }

    public int getRedisMaxWait() {
        return redisMaxWait;
    }

    public void setRedisMaxWait(int redisMaxWait) {
        this.redisMaxWait = redisMaxWait;
        config.setProperty("redis.maxWait", redisMaxWait);
    }

    public int getRedisTimeout() {
        return redisTimeout;
    }

    public void setRedisTimeout(int redisTimeout) {
        this.redisTimeout = redisTimeout;
        config.setProperty("redis.timeout", redisTimeout);
    }

    public String getRedisPassword() {
        return redisPassword;
    }

    public void setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
        config.setProperty("redis.password", redisPassword);
    }
}
