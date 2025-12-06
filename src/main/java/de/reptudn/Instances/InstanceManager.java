package de.reptudn.Instances;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.InstanceContainer;

public class InstanceManager {
	private static final Map<String, InstanceContainer> instances = new HashMap<>();

	public static Map<String, InstanceContainer> getInstances() {
		return instances;
	}

	public static InstanceContainer getInstanceById(String id) {
		return instances.get(id);
	}

    public static InstanceContainer getInstanceByUUID(UUID uuid) {
        for (InstanceContainer instance : instances.values()) {
            if (instance.getUuid().toString().equals(uuid.toString())) {
                return instance;
            }
        }
        return null;
    }

    public static String getInstanceNameByUUID(UUID uuid) {
        for (Map.Entry<String, InstanceContainer> entry : instances.entrySet()) {
            if (entry.getValue().getUuid().toString().equals(uuid.toString())) {
                return entry.getKey();
            }
        }
        return null;
    }

	public static InstanceContainer createInstance(String id) {
		InstanceContainer instance = MinecraftServer.getInstanceManager().createInstanceContainer();
		instances.put(id, instance);
		return instance;
	}
}
