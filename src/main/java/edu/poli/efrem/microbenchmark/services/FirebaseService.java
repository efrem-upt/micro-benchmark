package edu.poli.efrem.microbenchmark.services;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.api.core.ApiFuture;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FirebaseService {
    public static void updateResults(double cpuTime) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference results = dbFirestore.collection("results").document("results-document");
        results.update("cpuTimeAverage", FieldValue.arrayUnion(cpuTime));
    }

    public static ArrayList<Double> returnResults() {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference results = dbFirestore.collection("results").document("results-document");
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

    //checks for connection to the internet through dummy request
    public static boolean isInternetReachable()
    {
        try {
            //make a URL to a known source
            URL url = new URL("http://www.google.com");

            //open a connection to that source
            HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();

            //trying to retrieve data from the source. If there
            //is no connection, this line will fail
            Object objData = urlConnect.getContent();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }
}