package com.example.jetpackcomponentsapp.repository;

import static androidx.core.app.ActivityCompat.startIntentSenderForResult;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import com.example.jetpackcomponentsapp.model.CustomModel;
import com.example.jetpackcomponentsapp.model.PrimitiveModel;
import com.example.jetpackcomponentsapp.utils.Constants;
import com.example.jetpackcomponentsapp.utils.ConvertList;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Executor;

public class CustomRepository implements BaseRepository {

    public static final String TAG = CustomRepository.class.getSimpleName();
    private static CustomRepository INSTANCE;
    //region Firebase
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private final StorageReference imageReference, videosReference;
    //endregion
    //region Google
    public static final int GOOGLE_SIGN_IN = 100;
    private SignInClient oneTapClient;
    //endregion
    //region Facebook
    private final CallbackManager callbackManager = CallbackManager.Factory.create();
    private final LoginManager loginManager = LoginManager.getInstance();
    //endregion
    public static CustomRepository getInstance() {
        if (INSTANCE == null) INSTANCE = new CustomRepository();
        return INSTANCE;
    }

    private CustomRepository() {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        imageReference = firebaseStorage.getReference().child("images/");
        videosReference = firebaseStorage.getReference().child("videos/");
    }
    //region Object Methods
    @Override
    public void createObject(Map<String, Object> data) {
        firebaseFirestore.collection(Constants.OBJECT).add(data);
    }

    @Override
    public void getObjects(Consumer<QuerySnapshot> onSuccess, Consumer<Exception> onFailure) {
        final Task<QuerySnapshot> response = firebaseFirestore.collection(Constants.OBJECT).get();
        response.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                onSuccess.accept(queryDocumentSnapshots);
            }
        } ). addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                onFailure.accept(exception);
            }
        } );
    }

    @Override
    public void updateObject(CustomModel model, Consumer<Void> onSuccess, Consumer<Exception> onFailure) {
        firebaseFirestore.collection(Constants.OBJECT).document(model.id).update( ConvertList.toMap(model) )
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                onSuccess.accept(unused);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                onFailure.accept(exception);
            }
        });
    }

    @Override
    public void updateObject(String newName, Uri newUri, CustomModel model, Consumer<Void> onSuccess, Consumer<Exception> onFailure) {
        updateFile(newName, newUri, model.file,
            newFile -> {
                updateObject (
                    new CustomModel (model.id, model.name, newFile.toString(), newName),
                    onSuccess,
                    onFailure
                );
            }, exception -> {
                onFailure.accept(exception);
            }
        );
    }

    @Override
    public void deleteObject(CustomModel model) throws Exception {
        if(model != null) {
            firebaseFirestore
                .collection(Constants.OBJECT)
                .document(model.id)
                .delete();
        } else {
            throw new Exception("Error deleting model");
        }
    }
    //endregion
    //region Primitive Methods
    @Override
    public void createPrimitive(Map<String, Object> data) {
        firebaseFirestore.collection(Constants.PRIMITIVE).add(data);
    }

    @Override
    public void updatePrimitive(PrimitiveModel model) throws Exception {
        if (model != null) {
            firebaseFirestore
                .collection(Constants.PRIMITIVE)
                .document(model.id)
                .update( ConvertList.toMap(model) );
        } else {
            throw new Exception("PrimitiveModel is Nil");
        }
    }

    @Override
    public void getPrimitives(Consumer<QuerySnapshot> onSuccess, Consumer<Exception> onFailure) {
        final Task<QuerySnapshot> response = firebaseFirestore.collection(Constants.PRIMITIVE).get();
        response.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                onSuccess.accept(queryDocumentSnapshots);
            }
        } ). addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                onFailure.accept(exception);
            }
        } );
    }
    //endregion
    //region File Methods
    @Override
    public void uploadFile(String name, Uri uri, Consumer<Uri> onSuccess, Consumer<Exception> onFailure) {
        if (StringUtils.isBlank(name)) onFailure.accept(new Exception("File name is Nil"));
        else if (uri == null) onFailure.accept(new Exception("File is Nil"));
        else {
            //imageReference.child(name).putData(bytes);
            imageReference.child(name).putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploadedFile(taskSnapshot, onSuccess, onFailure);
                    }
                } ).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        onFailure.accept(exception);
                }
            });
        }
    }

    private void uploadedFile(UploadTask.TaskSnapshot taskSnapshot, Consumer<Uri> onSuccess, Consumer<Exception> onFailure) {
        taskSnapshot.getMetadata().getReference().getDownloadUrl()
        .addOnSuccessListener(new OnSuccessListener<Uri>() {
              @Override
              public void onSuccess(Uri uri) {
                  onSuccess.accept(uri);
              }
          }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                onFailure.accept(exception);
            }
        });
    }

    @Override
    public void updateFile(String newName, Uri newUri, String oldName, Consumer<Uri> onSuccess, Consumer<Exception> onFailure) {
        if (StringUtils.isBlank(newName)) onFailure.accept(new Exception("File name is Nil"));
        else if (newName == null) onFailure.accept(new Exception("File is Nil"));
        else {
            imageReference.child(newName).putFile(newUri)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    uploadedFile(taskSnapshot, onSuccess, onFailure);
                    try {
                        deleteImage(oldName);
                    } catch (Exception exception) {
                        onFailure.accept(exception);
                    }
                }
            } ).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    onFailure.accept(exception);
                }
            });
        }
    }

    @Override
    public void deleteImage(String name) throws Exception {
        if (name == null) throw new Exception("Image is Nil");
        imageReference.child(name).delete();
    }

    public void deleteImages(Consumer<ListResult> onSuccess, Consumer<Exception> onFailure) {
        imageReference.listAll()
            .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {
                    onSuccess.accept(listResult);
                    /*
                    for (StorageReference reference : listResult.getItems()) {
                        reference.delete();
                    }
                    */
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    onFailure.accept(exception);
                }
            });
    }
    //endregion
    //region Authentication Methods
    @Override
    public void registerCredential(String email, String password, Consumer<FirebaseUser> onSuccess, Consumer<Throwable> onFailure) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    try {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            onSuccess.accept(user);
                        } else {
                            throw new Exception("Registration Invalid");
                        }
                    } catch (Exception exception) {
                        onFailure.accept(exception);
                    }
                }
            } );
    }
    //region Google Login Methods
    public void loginGoogle(Activity activity, String firebaseWebClientId) {
        //oneTapClient = Identity.getSignInClient(this)
        oneTapClient.beginSignIn(buildSignInRequest(firebaseWebClientId))
            .addOnSuccessListener(activity, new OnSuccessListener<BeginSignInResult>() {
                @Override
                public void onSuccess(BeginSignInResult result) {
                    try {
                        startIntentSenderForResult(activity, result.getPendingIntent().getIntentSender(), GOOGLE_SIGN_IN, null, 0, 0, 0, null);
                    } catch (IntentSender.SendIntentException e) {
                        Log.e(TAG, "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                    }
                }
            })
            .addOnFailureListener((Executor) this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // No saved credentials found. Launch the One Tap sign-up flow, or
                    // do nothing and continue presenting the signed-out UI.
                    Log.d(TAG, e.getLocalizedMessage());
                }
            });
    }

    private BeginSignInRequest buildSignInRequest(String firebaseWebClientId) {
        return BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                .setSupported(true)
                                .setFilterByAuthorizedAccounts(false)
                                //.setServerClientId(context.getString(R.string.firebase_web_client_id))
                                .setServerClientId(firebaseWebClientId)
                                .build()
                )
                .setAutoSelectEnabled(true)
                .build();
    }

    public void googleOnActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == GOOGLE_SIGN_IN && resultCode == Activity.RESULT_OK) {
            try {
                SignInCredential googleCredential = oneTapClient.getSignInCredentialFromIntent(data);
                String idToken = googleCredential.getGoogleIdToken();
                if (idToken !=  null) {
                    // Got an ID token from Google. Use it to authenticate
                    // with Firebase.
                    Log.d(TAG, "Got ID token " + idToken);
                    // Got an ID token from Google. Use it to authenticate
                    // with Firebase.
                    AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
                    firebaseAuth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithCredential:success");
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        //updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                                        //updateUI(null);
                                    }
                                }
                            });
                }
            } catch (ApiException exception) {
                Log.e(TAG, "ApiException " + exception);
            }

        } else if (requestCode == GOOGLE_SIGN_IN && resultCode != Activity.RESULT_OK) {
            Log.w(TAG, "failed, user denied OR no network OR jks SHA1 not configure yet at play console android project");
        }
    }
    //endregion
    //region Facebook Login Methods
    public void loginFacebook(Activity activity) {
        loginManager.registerCallback(callbackManager, facebookCallback());
        loginManager.logInWithReadPermissions(activity, Arrays.asList("email", "public_profile"));
    }

    private FacebookCallback<LoginResult> facebookCallback() {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        };
    }

    public void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        //updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        //updateUI(null);
                    }
                }
            });
    }
    //endregion
    @Override
    public void checkCredential(String email, String password, Consumer<FirebaseUser> onSuccess, Consumer<Throwable> onFailure) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    try {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            onSuccess.accept(user);
                        } else {
                            throw new Exception("Credential Invalid");
                        }
                    } catch (Exception exception) {
                        onFailure.accept(exception);
                    }
                }
            });
    }

    @Override
    public FirebaseUser getUser() {
        return firebaseAuth.getCurrentUser();
    }

    @Override
    public boolean isUserSignedIn() {
        return getUser() != null ? true : false;
    }

    @Override
    public void signOut() {
        firebaseAuth.signOut();
    }
    //endregion
}