package pete.eremeykin.chapter3;


import pete.eremeykin.Listing;

import java.util.ArrayList;
import java.util.List;

@Listing("3.7")
class ThisEscape {
    public ThisEscape(EventSource source) {
        source.registerListener(
                new EventListener() {
                    @Override
                    public void onEvent(Event e) {
                        System.out.println("Event: " + e);
                    }
                }
        );
    }
}

class EventSource {
    private final List<EventListener> listeners = new ArrayList<>();

    public void registerListener(EventListener eventListener) {
        listeners.add(eventListener);
    }
}

interface EventListener {
    void onEvent(Event e);

    class Event {

    }
}

