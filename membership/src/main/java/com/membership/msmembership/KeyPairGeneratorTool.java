package com.membership.msmembership;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

public final class KeyPairGeneratorTool {

    private KeyPairGeneratorTool() {}

    public static void main(String[] args) throws Exception {
        String membershipModulePath = "C:\\Users\\hamza\\IdeaProjects\\Badger\\app\\services\\tp-orchestration\\membership";

        File keysDir = new File(membershipModulePath, "src/main/resources/keys");
        if (!keysDir.exists() && !keysDir.mkdirs()) {
            throw new IllegalStateException("Unable to create directory: " + keysDir.getAbsolutePath());
        }

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();

        String privatePem = toPem("PRIVATE KEY", kp.getPrivate().getEncoded());
        String publicPem = toPem("PUBLIC KEY", kp.getPublic().getEncoded());

        Files.write(new File(keysDir, "private_key.pem").toPath(), privatePem.getBytes(StandardCharsets.US_ASCII));
        Files.write(new File(keysDir, "public_key.pem").toPath(), publicPem.getBytes(StandardCharsets.US_ASCII));

        System.out.println(keysDir.getAbsolutePath());
    }

    private static String toPem(String type, byte[] derBytes) {
        String base64 = Base64.getMimeEncoder(64, "\n".getBytes(StandardCharsets.US_ASCII))
                .encodeToString(derBytes);
        return "-----BEGIN " + type + "-----\n" + base64 + "\n-----END " + type + "-----\n";
    }
}
