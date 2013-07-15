package com.ht.scada.data.rs;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: 薄成文 13-7-13 下午3:16
 * To change this template use File | Settings | File Templates.
 */
@Path("/myresource")
public interface IMyResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    String getIt();

    @Path("/m1")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    //@Produces({"application/json", "application/xml"})
    String m1(@QueryParam("say") String say);

    @Path("/m2/{id}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    String m2(@PathParam("id") String id);

    @Path("/m3")
    @GET
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    String m3(@QueryParam("id") String id);

    @Produces(MediaType.TEXT_PLAIN)
    String yk(@QueryParam("channelIndex") int channelIndex,
              @QueryParam("endCode") String endCode,
              @QueryParam("varName") String varName,
              @QueryParam("value") boolean value);

    @Produces(MediaType.TEXT_PLAIN)
    String yt(@QueryParam("channelIndex") int channelIndex,
              @QueryParam("endCode") String endCode,
              @QueryParam("varName") String varName,
              @QueryParam("value") int value);
}
