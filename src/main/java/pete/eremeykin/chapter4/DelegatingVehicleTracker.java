package pete.eremeykin.chapter4;

import pete.eremeykin.Listing;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Listing("4.7")
class DelegatingVehicleTracker {
    private final ConcurrentMap<String, Point> locations;
    private final Map<String, Point> unmodifiableMap;

    public DelegatingVehicleTracker(Map<String, Point> points) {
        this.locations = new ConcurrentHashMap<>(points);
        this.unmodifiableMap = Collections.unmodifiableMap(locations);
    }

    public Map<String, Point> getLocations() {
        return unmodifiableMap;
    }

    public Point getLocation(String id) {
        return locations.get(id);
    }

    public void setLocations(String id, int x, int y) {
        if (locations.replace(id, new Point(x, y)) == null)
            throw new IllegalArgumentException("invalid vehicle name: " + id);
    }


    @Listing("4.6")
    static class Point {
        public final int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class  CopingDelegatingVehicleTracker extends DelegatingVehicleTracker {

        public CopingDelegatingVehicleTracker(Map<String, Point> points) {
            super(points);
        }

        @Override
        public Map<String, Point> getLocations() {
            return Collections.unmodifiableMap(new HashMap<>(this.getLocations()));
        }
    }
}
