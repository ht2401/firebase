package com.example.CSDL.Firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

@SpringBootApplication
public class CRURRunner {
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = CRURRunner.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResources("serviceAccountKey.json")).nextElement().getFile());

        FileInputStream serviceAccount = new FileInputStream(file.getAbsoluteFile());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://fir-tutorial-db-48ea8-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .build();
        FirebaseApp.initializeApp(options);

        SpringApplication.run(CRURRunner.class, args);
    }
}
