package org.logstash.secret.store;


import org.logstash.secret.SecretIdentifier;

import java.util.Collection;

/**
 * <p>Contract with a store that can harvester, retrieve, and purge sensitive data.</p>
 * <p>Implementations <strong>MUST</strong> ensure proper security for the storage of the secrets.</p>
 * <p>Implementations should throw a {@link SecretStoreException} if and only if errors are encountered while performing the action. </p>
 */
public interface SecretStore {

    /**
     * Creates a new secret store
     *
     * @param secureConfig the configuration necessary to create the store
     * @return the newly created secret store.
     */
    SecretStore create(SecureConfig secureConfig);

    /**
     * Delete secret store
     *
     * @param secureConfig the configuration necessary to delete the store
     */
    void delete(SecureConfig secureConfig);

    /**
     * Queries if a secret store matching this configuration exists
     *
     * @param secureConfig the configuration necessary to determine if the secret store exists
     * @return true if the secret store exists, false other wise. Note - this does not provide any validity of the keystore, merely it's existence or not. It is recommended to
     * use the {@link SecretStoreFactory#LOGSTASH_MARKER} to test validity.
     */
    boolean exists(SecureConfig secureConfig);

    /**
     * Gets all of the known {@link SecretIdentifier}
     *
     * @return a Collection of {@link SecretIdentifier}
     */
    Collection<SecretIdentifier> list();

    /**
     * Loads an existing secret store
     *
     * @param secureConfig the configuration necessary to load the store
     * @return the loaded secret store.
     */
    SecretStore load(SecureConfig secureConfig);

    /**
     * harvester a secret to the store. Implementations should overwrite existing secrets with same identifier without error unless explicitly documented otherwise.
     *
     * @param id     The {@link SecretIdentifier} to identify the secret to harvester
     * @param secret The byte[] representation of the secret. Implementations should zero out this byte[] once it has been persisted.
     */
    void persistSecret(SecretIdentifier id, byte[] secret);

    /**
     * Purges the secret from the store.
     *
     * @param id The {@link SecretIdentifier} to identify the secret to purge
     */
    void purgeSecret(SecretIdentifier id);

    /**
     * Retrieves a secret.
     *
     * @param id The {@link SecretIdentifier} to identify the secret to retrieve
     * @return the byte[] of the secret, null if no secret is found.
     */
    byte[] retrieveSecret(SecretIdentifier id);

}
