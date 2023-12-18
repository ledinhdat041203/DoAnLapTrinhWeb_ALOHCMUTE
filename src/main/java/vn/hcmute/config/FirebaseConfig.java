package vn.hcmute.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.core.io.ClassPathResource;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {

    // @Bean
    // public FirebaseApp firebaseApp() throws IOException {
    //     List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
    //     FirebaseApp app;

    //     if (firebaseApps.isEmpty()) {
    //         FileInputStream serviceAccount = new FileInputStream("src/main/resources/static/chatrealtime-7e7e8-firebase-adminsdk-512k7-6a7ab2885d.json");
    //         FirebaseOptions options = FirebaseOptions.builder()
    //                 .setCredentials(GoogleCredentials.fromStream(serviceAccount))
    //                 .setDatabaseUrl("https://chatrealtime-7e7e8-default-rtdb.firebaseio.com")
    //                 .build();

    //         app = FirebaseApp.initializeApp(options);
    //     } else {
    //         app = FirebaseApp.getInstance();
    //     }

    //     return app;
    // }



    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
        FirebaseApp app;

        if (firebaseApps.isEmpty()) {
            try (InputStream serviceAccount = new ClassPathResource("static/chatrealtime-7e7e8-firebase-adminsdk-512k7-6a7ab2885d.json").getInputStream()) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://chatrealtime-7e7e8-default-rtdb.firebaseio.com")
                        .build();

                app = FirebaseApp.initializeApp(options);
            }
        } else {
            app = FirebaseApp.getInstance();
        }

        return app;
    }
    
    @Bean
    public Storage storage() {
        return StorageOptions.getDefaultInstance().getService();
    }
}
