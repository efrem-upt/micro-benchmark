package edu.poli.efrem.microbenchmark.services;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.api.core.ApiFuture;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FirebaseService {
    public static void updateResults(double cpuTime) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference results = dbFirestore.collection("results").document("egC99Gc0L7QgWWpGwvW2");
        results.update("cpuTimeAverage", FieldValue.arrayUnion(cpuTime));
    }

    public static ArrayList<Double> returnResults() {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference results = dbFirestore.collection("results").document("egC99Gc0L7QgWWpGwvW2");
        DocumentSnapshot doc = null;
        try {
            doc = results.get().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ArrayList<Double> arrList;
        arrList = (ArrayList) doc.get("cpuTimeAverage");
        return arrList;
    }
}
