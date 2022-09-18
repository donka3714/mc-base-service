package org.acme.controller;

import org.acme.service.MessageService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/producer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProducerController {

    @Inject
    MessageService messageService;

    @POST
    @Path("/kafka")
    public void sendToTopic() {
        messageService.publisMessage("test-quarkustopic-one", "hello");
    }
}