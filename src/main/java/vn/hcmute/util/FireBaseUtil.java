package vn.hcmute.util;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class FireBaseUtil {

    public interface NodeExistenceCallback {
        void onNodeExists(boolean exists);
    }

    public static void isNodeExists(DatabaseReference reference, NodeExistenceCallback callback) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    callback.onNodeExists(true);
                } else {
                    callback.onNodeExists(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Error checking if node exists: " + databaseError.getMessage());
                callback.onNodeExists(false); // Handle error by assuming the node doesn't exist
            }
        });
    }
}

