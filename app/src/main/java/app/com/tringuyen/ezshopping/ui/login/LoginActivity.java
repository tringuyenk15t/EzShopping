package app.com.tringuyen.ezshopping.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import app.com.tringuyen.ezshopping.R;
import app.com.tringuyen.ezshopping.ui.EzShoppingBaseActivity;
import app.com.tringuyen.ezshopping.ui.MainActivity;
import app.com.tringuyen.ezshopping.uti.Constants;

public class LoginActivity extends EzShoppingBaseActivity {
    private static final String LOG_TAG = LoginActivity.class.getSimpleName();
    /* A dialog that is presented until the Firebase authentication finished. */
    private ProgressDialog mAuthProgressDialog;
    private EditText mEditTextEmailInput, mEditTextPasswordInput;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    /**
     * Variables related to Google Login
     */
    /* A flag indicating that a PendingIntent is in progress and prevents us from starting further intents. */
    private boolean mGoogleIntentInProgress;
    /* Request code used to invoke sign in user interactions for Google+ */
    public static final int RC_GOOGLE_LOGIN = 1;
    /* A Google account object that is populated if the user signs in with Google */
    GoogleSignInAccount mGoogleAccount;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // initiate firebase auth
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        //initiate auth listener
        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("SignIn", "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    // clear back stack ?
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    // User is signed out
                    Log.d("SignIn", "onAuthStateChanged:signed_out");
                }
            }
        };

        /**
         * Link layout elements from XML and setup progress dialog
         */
        initializeScreen();

        /**
         * Call signInPassword() when user taps "Done" keyboard action
         */
        mEditTextPasswordInput.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    signInPassword();
                }
                    return true;
            }
        });
    }

    private void initializeScreen() {
        mEditTextEmailInput = (EditText) findViewById(R.id.edit_text_email);
        mEditTextPasswordInput = (EditText) findViewById(R.id.edit_text_password);
        ScrollView scrollViewLoginActivity = (ScrollView) findViewById(R.id.activity_login);
        initializeBackground(scrollViewLoginActivity);

        /* Setup the progress dialog that is displayed later when authenticating with Firebase */
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle(getString(R.string.progress_dialog_loading));
        mAuthProgressDialog.setMessage(getString(R.string.progress_dialog_authenticating_with_firebase));
        mAuthProgressDialog.setCancelable(false);

        /* Setup Google Sign In */
        //TODO update later
//        setupGoogleSignIn();
    }

    /**
     * Sign in with Password provider when user clicks sign in button
     */
    public void onSignInPressed(View view)
    {
        signInPassword();
    }

    /**
     * Sign in with Password provider when user clicks sign in button
     */
    public void signInPassword()
    {
        //TODO the exercise 3.03
        String email = mEditTextEmailInput.getText().toString();
        String password = mEditTextPasswordInput.getText().toString();


        if(email.equals("")) {
            mEditTextEmailInput.setError(getString(R.string.error_cannot_be_empty));
            return;
        }
        if(password.equals("")) {
            mEditTextPasswordInput.setError(getString(R.string.error_cannot_be_empty));
            return;
        }

        mAuthProgressDialog.show();
        //login with username and password
        mAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mAuthProgressDialog.dismiss();
                if(!task.isSuccessful())
                {
                    handlingSignError(task.getException());
                }
                else
                {
                    showErrorToast(getString(R.string.log_message_auth_successful));
                }
            }
        });
    }

    /**
     * handle exception when login is unsuccessful
     * @param exception
     */
    private void handlingSignError(Exception exception) {
        //TODO this method should be better
        try
        {
            throw exception;
        }
        catch (FirebaseAuthException e)
        {
            switch (e.getErrorCode())
            {
                case Constants.ERROR_INVALID_EMAIL:
                    mEditTextEmailInput.setError(getString(R.string.error_invalid_email_not_valid));
                    break;
                case Constants.ERROR_USER_NOT_FOUND:
                    mEditTextEmailInput.setError(getString(R.string.error_user_not_found));
                    break;
                case Constants.ERROR_WRONG_PASSWORD:
                    mEditTextPasswordInput.setError(getString(R.string.error_wrong_password));
                    break;
            }
        }
        catch (Exception e)
        {
            showErrorToast(e.getMessage());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

//    /**
//     * Helper method that makes sure a user is created if the user
//     * logs in with Firebase's email/password provider.
//     * @param authData AuthData object returned from onAuthenticated
//     */
//    private void setAuthenticatedUserPasswordProvider(AuthData authData) {
//    }
//
//    /**
//     * Helper method that makes sure a user is created if the user
//     * logs in with Firebase's Google login provider.
//     * @param authData AuthData object returned from onAuthenticated
//     */
//    private void setAuthenticatedUserGoogle(AuthData authData){
//
//    }

    /**
     * Show error toast to users
     */
    private void showErrorToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }


    /**
     * Signs you into ShoppingList++ using the Google Login Provider
     * @param token A Google OAuth access token returned from Google
     */
    private void loginWithGoogle(String token) {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    /**
     * Override onCreateOptionsMenu to inflate nothing
     *
     * @param menu The menu with which nothing will happen
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /**
     * GOOGLE SIGN IN CODE
     */

    /* Sets up the Google Sign In Button :
     *https://developers.google.com/android/reference/com/google/android/gms/common/SignInButton */
    private void setupGoogleSignIn() {
        SignInButton signInButton = (SignInButton)findViewById(R.id.login_with_google);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignInGooglePressed(v);
            }
        });
    }
    /**
     * Sign in with Google plus when user clicks "Sign in with Google" textView (button)
     */
    public void onSignInGooglePressed(View v)
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GOOGLE_LOGIN);
        mAuthProgressDialog.show();
    }

    //navigate to create account activity
    public void onSignUpPressed(View view)
    {
        Intent signupImtent = new Intent(this,CreateAccountActivity.class);
    startActivity(signupImtent);
}
}
