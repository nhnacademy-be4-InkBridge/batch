package com.nhnacademy.inkbridge.batch.config;

import com.nhnacademy.inkbridge.batch.dto.keymanager.KeyResponseDto;
import com.nhnacademy.inkbridge.batch.exception.KeyManagerException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Objects;
import javax.net.ssl.SSLContext;
import lombok.RequiredArgsConstructor;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * class: 키매니저 설정.
 *
 * @author choijaehun
 * @version 2024/02/27
 */

@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "secure-key-manager")
public class KeyConfig {

    private final String password;
    private final String url;
    private final String path;
    private final String appKey;

    public String keyStore(String keyId) {
        try {
            KeyStore clientStore = KeyStore.getInstance("PKCS12");
            InputStream result = new ClassPathResource("inkBridge.p12").getInputStream();
            clientStore.load(result, password.toCharArray());

            SSLContext sslContext = SSLContextBuilder.create()
                .setProtocol("TLS")
                .loadKeyMaterial(clientStore, password.toCharArray())
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();

            SSLConnectionSocketFactory sslConnectionSocketFactory =
                new SSLConnectionSocketFactory(sslContext);
            CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build();

            HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            RestTemplate restTemplate = new RestTemplate(requestFactory);

            URI uri = UriComponentsBuilder
                .fromUriString(url)
                .path(path)
                .encode()
                .build()
                .expand(appKey, keyId)
                .toUri();
            return Objects.requireNonNull(restTemplate.exchange(uri,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        KeyResponseDto.class)
                    .getBody())
                .getBody()
                .getSecret();
        } catch (KeyStoreException | IOException | CertificateException
                 | NoSuchAlgorithmException
                 | UnrecoverableKeyException
                 | KeyManagementException e) {
            throw new KeyManagerException(e.getMessage());
        }
    }
}

