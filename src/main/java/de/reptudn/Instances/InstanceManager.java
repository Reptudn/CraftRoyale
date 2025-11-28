package de.reptudn.Instances;

import java.util.HashMap;
import java.util.Map;

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

	public static InstanceContainer createInstance(String id) {
		InstanceContainer instance = MinecraftServer.getInstanceManager().createInstanceContainer();
		instances.put(id, instance);
		return instance;
	}
}
