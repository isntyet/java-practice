package com.isntyet.java.practice;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.EncryptRequest;
import com.amazonaws.services.kms.model.EncryptResult;
import com.amazonaws.services.kms.model.EncryptionAlgorithmSpec;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@ActiveProfiles("test")
public class KmsTest {
    private static final String KEY_ID = "36be3acd-acfc-499a-af8a-32fa04bcb156";

    @Test
    void encrypt() {
        final String plaintext = "jdbc:mysql://localhost:3306/jojae?serverTimezone=UTC&characterEncoding=UTF-8";

        try {
            AWSKMS kmsClient = AWSKMSClientBuilder.standard()
                    .withRegion(Regions.AP_NORTHEAST_2)
                    .build();

            EncryptRequest request = new EncryptRequest();
            request.withKeyId(KEY_ID);
            request.withPlaintext(ByteBuffer.wrap(plaintext.getBytes(StandardCharsets.UTF_8)));
            request.withEncryptionAlgorithm(EncryptionAlgorithmSpec.RSAES_OAEP_SHA_256);

            EncryptResult result = kmsClient.encrypt(request);
            ByteBuffer ciphertextBlob = result.getCiphertextBlob();

            System.out.println("ciphertextBlob: " + new String(Base64.encodeBase64(ciphertextBlob.array())));
        } catch (Exception e) {
            System.out.println("encrypt fail: " + e.getMessage());
        }
    }

    @Test
    void decrypt() {
        final String encriptedText = "ZikJZzJvzB2mIdbLHr2iXwZ8tGx/p65NPKUuVhtwvHJh0ngBOrT+1HT8+QKBmeWis9yicGMNyULRrrz5Kl6bvAqTwaCBiyzb6huZyW111Izpyc5PkOJfLe5oUjOB3PzOKV228VUEW6SV3W6XOxH/fWiC/S2CdCWghJm0LaQNcX7cb6uH6VFz8ViwoFvsfz7Aobu8j3lcU2Pw9V544ZJmeSF+Qw+AbKOk79RnrVSmjokYZfGTpMYY5iKnCFrosamHYis4rZM7W7ETdUHkkB9C1mm9EcMatnTrifzGHAC6HzhnfkFagi3bPAbzGF+TuprQACOmOyMFPns/5ouiZcnN1/fk1SJ/b45KIG1jET7NhOh9Y+DnPwa9GzigX5BXnfKnsyXPUapsae1MluimnBxZiLJT01ePQ9iI0I1I4qiCQvW0e716cgykuzkUxpVCtHBpdzS/6gQQsvqwp1Pu0r+8xTKIs7kXmZwJqoQ1VHneWYWOGjdke+INVCYtu9xxl+dNJYhKq5WvhF1kx2zZ1KeqkNzZFAbwGGagL6FTdefSdoVwGhEcNiE7H4GMP6KB1QUprb9VsJeDOQNO77c3S55LiANNawOSJth2Nyj9xhjSSJKwFQtlALg2n4InBuO95PhCMk7Y0EXoLgQNu/DdcqNCW7kswqkYWFVyh0MfQpOQSL0=";

        try {
            AWSKMS kmsClient = AWSKMSClientBuilder.standard()
                    .withRegion(Regions.AP_NORTHEAST_2)
                    .build();

            DecryptRequest request = new DecryptRequest();
            request.withCiphertextBlob(ByteBuffer.wrap(Base64.decodeBase64(encriptedText)));
            request.withKeyId(KEY_ID);
            request.withEncryptionAlgorithm(EncryptionAlgorithmSpec.RSAES_OAEP_SHA_256);
            ByteBuffer plainText = kmsClient.decrypt(request).getPlaintext();

            System.out.println("plainText: " + new String(plainText.array()));
        } catch (Exception e) {
            System.out.println("decrypt fail: " + e.getMessage());
        }
    }
}
