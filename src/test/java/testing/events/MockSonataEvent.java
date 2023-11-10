package testing.events;

import com.odeyalo.sonata.suite.brokers.events.AbstractEvent;

public class MockSonataEvent extends AbstractEvent<String> {
    public MockSonataEvent(String body) {
        super(body);
    }

    public MockSonataEvent(String id, long creationTime, String body) {
        super(id, creationTime, body);
    }
}
