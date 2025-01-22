package com.example.trinhnghenhac.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.trinhnghenhac.constants.FirebaseCollection;
import com.example.trinhnghenhac.constants.FirebaseField;
import com.example.trinhnghenhac.models.SavedStarredItem;
import com.example.trinhnghenhac.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirebaseUtils {
    public static User currentUser;

    public static void initialize(Context context) {
        FirebaseApp.initializeApp(context);
    }

    private static FirebaseAuth auth() {
        return FirebaseAuth.getInstance();
    }

    private static FirebaseFirestore db() {
        return FirebaseFirestore.getInstance();
    }

    public static Task<List<SavedStarredItem>> getSaved(User user) {
        return db().collection(FirebaseCollection.COLLECTION_SAVED)
                .whereEqualTo(FirebaseField.FIELD_SAVED_ACCOUNTID, user.getId())
                .get()
                .onSuccessTask(new SuccessContinuation<QuerySnapshot, List<SavedStarredItem>>() {
                    @NonNull
                    @Override
                    public Task<List<SavedStarredItem>> then(QuerySnapshot queryDocumentSnapshots) throws Exception {
                        return Tasks.forResult(queryDocumentSnapshots.toObjects(SavedStarredItem.class));
                    }
                });
    }

    public static Task<DocumentReference> addSaved(SavedStarredItem savedItem) {
        return db().collection(FirebaseCollection.COLLECTION_SAVED)
                .add(savedItem);
    }

    public static Task<Void> deleteSaved(SavedStarredItem savedItem) {
        SuccessContinuation<QuerySnapshot, Void> onFirestoreSuccess = new SuccessContinuation<QuerySnapshot, Void>() {
            @NonNull
            @Override
            public Task<Void> then(QuerySnapshot queryDocumentSnapshots) throws Exception {
                if (queryDocumentSnapshots.isEmpty())
                    throw new FirebaseFirestoreException("Item not found", FirebaseFirestoreException.Code.NOT_FOUND);
                return queryDocumentSnapshots.getDocuments().get(0).getReference().delete();
            }
        };
        return db().collection(FirebaseCollection.COLLECTION_SAVED)
                .whereEqualTo(FirebaseField.FIELD_SAVED_ACCOUNTID, savedItem.getAccountId())
                .whereEqualTo(FirebaseField.FIELD_SAVED_ITEMID, savedItem.getItemId())
                .get()
                .onSuccessTask(onFirestoreSuccess);
    }

    public static Task<User> getUser(String email, String password) {
        SuccessContinuation<QuerySnapshot, User> onFirestoreSuccess = new SuccessContinuation<QuerySnapshot, User>() {
            @NonNull
            @Override
            public Task<User> then(QuerySnapshot queryDocumentSnapshots) throws Exception {
                if (queryDocumentSnapshots.isEmpty())
                    throw new FirebaseFirestoreException("User not found in database", FirebaseFirestoreException.Code.NOT_FOUND);
                User result = queryDocumentSnapshots.getDocuments()
                        .get(0)
                        .toObject(User.class);
                currentUser = result;
                return Tasks.forResult(result);
            }
        };
        SuccessContinuation<AuthResult, QuerySnapshot> onAuthSuccess = new SuccessContinuation<AuthResult, QuerySnapshot>() {
            @NonNull
            @Override
            public Task<QuerySnapshot> then(AuthResult authResult) throws Exception {
                return db().collection(FirebaseCollection.COLLECTION_ACCOUNTS)
                        .whereEqualTo(FirebaseField.FIELD_ACCOUNTS_EMAIL, email.toLowerCase())
                        .get();
            }
        };
        return auth().signInWithEmailAndPassword(email, password)
                .onSuccessTask(onAuthSuccess)
                .onSuccessTask(onFirestoreSuccess)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FirebaseUtils.signOut();
                    }
                });
    }

    public static Task<FirebaseFirestoreException.Code> addUser(User user) {
        SuccessContinuation<DocumentReference, FirebaseFirestoreException.Code> onCreateFirestoreDocumentSuccess = new SuccessContinuation<DocumentReference, FirebaseFirestoreException.Code>() {
            @NonNull
            @Override
            public Task<FirebaseFirestoreException.Code> then(DocumentReference documentReference) throws Exception {
                return Tasks.forResult(FirebaseFirestoreException.Code.OK);
            }
        };
        SuccessContinuation<AuthResult, DocumentReference> onCreateAuthUserSuccess = new SuccessContinuation<AuthResult, DocumentReference>() {
            @NonNull
            @Override
            public Task<DocumentReference> then(AuthResult authResult) throws Exception {
                FirebaseUser authUser = authResult.getUser();
                User newUser = user.withId(authUser.getUid());
                return db().collection(FirebaseCollection.COLLECTION_ACCOUNTS)
                        .add(newUser);
            }
        };
        return auth().createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .onSuccessTask(onCreateAuthUserSuccess)
                .onSuccessTask(onCreateFirestoreDocumentSuccess);
    }

    public static void signOut() {
        auth().signOut();
        currentUser = null;
    }
}
