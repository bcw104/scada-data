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

    private String commUrl = "http://localhost:8080/comm/services/rs";

    /**
     * nosql数据库地址和端口
     */
    private String[] kvHostPort;
    /**
     * nosql数据库kvStore名称
     */
    private String kvStoreName;

    private Config() {
        try {
            config = new PropertiesConfiguration(Config.class.getResource("/config.properties").toURI().toURL());
            //config = new PropertiesConfiguration(configPath);
            config.setAutoSave(true);

            commUrl = config.getString("commUrl", commUrl);

            kvHostPort = config.getStringArray("kv.hostPort");
            kvStoreName = config.getString("kv.storeName");

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

    public String getCommUrl() {
        return commUrl;
    }
}
