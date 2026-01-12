package at.fhtw.tourplanner.infrastructure.rest;

import com.azure.identity.AzureCliCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KeyVaultService {

    private final SecretClient secretClient;

    public KeyVaultService() {
        this.secretClient = new SecretClientBuilder()
                .vaultUrl("https://tourplanner-kv.vault.azure.net/")
                .credential(new AzureCliCredentialBuilder().build())
                .buildClient();
    }

    public String getSecret(String secretName) {
        try {
            KeyVaultSecret secret = secretClient.getSecret(secretName);
            return secret.getValue();
        } catch (Exception e) {
            log.error("Failed to retrieve secret {} from Key Vault: {}",
                    secretName, e.getMessage());
            throw new IllegalStateException("Cannot read secret from Key Vault", e);
        }
    }
}
