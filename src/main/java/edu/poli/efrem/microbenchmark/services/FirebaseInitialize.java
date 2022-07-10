package edu.poli.efrem.microbenchmark.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FirebaseInitialize {

        public void initialize()  {
            FileInputStream serviceAccount =
                    null;
            try {
                serviceAccount = new FileInputStream("src/main/java/edu/poli/efrem/microbenchmark/services/serviceAccountKey.json");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            FirebaseOptions options = null;
            try {
                options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
            }

            FirebaseApp.initializeApp(options);

        }
}
