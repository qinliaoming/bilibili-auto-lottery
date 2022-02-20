package org.carcinus.tools.conf.loader;

import java.util.Map;

public interface ConfigurationLoader {
    Map<String, String> loadConfiguration();
}
