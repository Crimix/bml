package com.black_dog20.bml.utils.player;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractPlayerPermissions {

    public interface IPermission {
        String getName();
    }

    private String uuid;
    private String displayName;
    private Set<String> permissions;

    public AbstractPlayerPermissions(String uuid, String displayName, IPermission... permissions) {
        this.uuid = uuid;
        this.displayName = displayName;
        this.permissions = new HashSet<>(Arrays.asList(permissions)).stream()
                .map(IPermission::getName)
                .collect(Collectors.toSet());
    }

    public String getUuid() {
        return uuid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void grant(IPermission permission) {
        permissions.add(permission.getName());
    }

    public void revoke(IPermission permission) {
        permissions.remove(permission.getName());
    }

    public boolean hasPermission(IPermission permission) {
        return permissions.contains(permission.getName());
    }

    public abstract void onReceiveClientMessage();
}
