package id.co.bigtek.sqlite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText username, password;
    Button btnLogin;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 12345;
    ProgressDialog progressDialog;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);

        progressDialog = new ProgressDialog(this);

        callbackManager = CallbackManager.Factory.create();
        this.setTitle("Login");
        LoginButton loginButton = findViewById(R.id.loginButton);
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };

        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

            }
        };

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e("aa", "" + response.toString());
                                try {
                                    Toast.makeText(getApplicationContext(), "Hi, " + object.getString("name"), Toast.LENGTH_LONG).show();
                                    LoginActivity.this.startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                    LoginActivity.this.finish();
                                } catch (JSONException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                //Toast.makeText(getApplication(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {

            }

        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usernameKey = username.getText().toString();
                String passwordKey = password.getText().toString();

                if (usernameKey.equals("admin") && passwordKey.equals("123")){
                    //jika login berhasil
                    Toast.makeText(getApplicationContext(), "LOGIN SUKSES",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    LoginActivity.this.startActivity(intent);
                    finish();
                }else {
                    //jika login gagal
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Username atau Password Anda salah!")
                            .setNegativeButton("Retry", null).create().show();
                }
            }

        });

        GoogleSignInOptions gso = new
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

//        mGoogleApiClient.connect();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //findViewById(R.id.signOut);
        SignInButton sign = findViewById(R.id.signinButton);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    private void login() {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> completeTask =
                    GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(completeTask);
//            try {
//                connectFirebaseWithGoogle(result);
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Login failed!", Toast.LENGTH_SHORT).show();
//                if (progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
//            }
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completeTask) {
        try {
            GoogleSignInAccount account = completeTask.getResult(ApiException.class);
            progressDialog.dismiss();
            // Signed in successfully, show authenticated UI.
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            progressDialog.dismiss();
//            updateUI(null);
        }
    }



}
