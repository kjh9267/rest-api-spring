package me.jun.restapispring.events;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import com.fasterxml.jackson.annotation.JsonUnwrapped;
//import org.springframework.hateoas.RepresentationModel;

// Auto Json Unwrapped
public class EventResource extends EntityModel<Event> {

    public EventResource(Event event, Link... links) {
        super(event, links);
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }
}


//// Object Mapper -> Bean Serializer
//public class EventResource extends RepresentationModel {
//
//    @JsonUnwrapped
//    private Event event;
//
//    public EventResource(Event event) {
//        this.event = event;
//    }
//
//    public Event getEvent() {
//        return this.event;
//    }
//}
