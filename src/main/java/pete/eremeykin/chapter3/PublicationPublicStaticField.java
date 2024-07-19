package pete.eremeykin.chapter3;

import pete.eremeykin.Listing;

import java.util.HashSet;
import java.util.Set;

@Listing("3.5")
class PublicationPublicStaticField {
    public static Set<Secret> knownSecrets;

    public void initialize() {
        knownSecrets = new HashSet<>();
    }

    public static class Secret {
        private final String value;

        public Secret(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
