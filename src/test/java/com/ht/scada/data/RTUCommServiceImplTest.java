package com.ht.scada.data;

import com.ht.scada.data.rs.IMyResource;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: 薄成文 13-7-11 下午4:02
 * To change this template use File | Settings | File Templates.
 */
public class RTUCommServiceImplTest {

    private static final String ADDRESS = "http://localhost:8080/comm/services";

    @Test
    public void testYk() throws Exception {
        //Client c = ClientBuilder.newClient();

        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//        factory.setServiceClass(RTUCommService.class);
//        factory.setAddress(ADDRESS + "/rtu");
//        RTUCommService client = (RTUCommService)factory.create();
//        String s = client.hello("world");
//        System.out.println(s);
//        assert s.equals("hello,world");
    }

    @Test
    public void testRS() {
//        Client c = ClientBuilder.newClient();
//        WebTarget target = c.target("http://127.0.0.1:8080").path("myresource");
//        String message = target.request().get(String.class);
//        System.out.println(message);
//        assert message.equals("Got it!");
        System.out.println("##############");
//        WebClient client = WebClient.create(ADDRESS + "/rs");
//        String message = client.path("/myresource/m1", "h").accept(MediaType.TEXT_XML_TYPE).type(MediaType.TEXT_PLAIN_TYPE).get(String.class);
//        System.out.println(message);

        IMyResource store = JAXRSClientFactory.create(ADDRESS + "/rs", IMyResource.class);
        System.out.println(store.getIt());
        System.out.println("--------------");
        System.out.println(store.m1("中文1"));
        System.out.println(store.m2("中文2"));
        System.out.println(store.m3("中文3"));
        System.out.println("##############================");
    }
}
