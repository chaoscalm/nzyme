package app.nzyme.core.connect;

import app.nzyme.plugin.EncryptedRegistryKey;
import app.nzyme.plugin.RegistryKey;
import app.nzyme.plugin.rest.configuration.ConfigurationEntryConstraint;

import java.util.ArrayList;
import java.util.Optional;

public class ConnectRegistryKeys {

    public static final RegistryKey CONNECT_ENABLED = RegistryKey.create(
            "connect_enabled",
            Optional.of(new ArrayList<>() {{
                add(ConfigurationEntryConstraint.createSimpleBooleanConstraint());
            }}),
            Optional.of("false"),
            false
    );

    public static EncryptedRegistryKey CONNECT_API_KEY = EncryptedRegistryKey.create(
            "connect_api_key",
            Optional.of(new ArrayList<>() {{
                add(ConfigurationEntryConstraint.createStringLengthConstraint(64, 64));
            }}),
            false
    );

}
