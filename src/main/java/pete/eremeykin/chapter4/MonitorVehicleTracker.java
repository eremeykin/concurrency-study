package pete.eremeykin.chapter4;

import pete.eremeykin.Listing;
import pete.eremeykin.ThreadSafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Listing("4.4")
@ThreadSafe
class MonitorVehicleTracker {
    private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
        this.locations = deepCopy(locations);
    }


    public synchronized Map<String, MutablePoint> getLocations() {
        return deepCopy(locations);
    }

    public synchronized MutablePoint getLocation(String id) {
        MutablePoint loc = locations.get(id);
        return loc == null ? null : new MutablePoint(loc);
    }

    public synchronized void setLocation(String id, int x, int y) {
        MutablePoint loc = locations.get(id);
        if (loc == null) {
            throw new IllegalArgumentException("No such ID:" + id);
        }
        loc.x = x;
        loc.y = y;
    }

    private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m) {
        Map<String, MutablePoint> result = new HashMap<>();
        for (String id : m.keySet()) {
            result.put(id, new MutablePoint(m.get(id)));
        }
        return Collections.unmodifiableMap(result);
    }

    @Listing("4.5")
    static class MutablePoint {
        public int x, y;

        public MutablePoint() {
            x = 0;
            y = 0;
        }

        public MutablePoint(MutablePoint point) {
            this.x = point.x;
            this.y = point.y;
        }
    }
}
