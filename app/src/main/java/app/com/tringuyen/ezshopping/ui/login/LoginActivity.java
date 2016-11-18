package app.com.tringuyen.ezshopping.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import app.com.tringuyen.ezshopping.R;
import app.com.tringuyen.ezshopping.ui.EzShoppingBaseActivity;

public class LoginActivity extends EzShoppingBaseActivity {
    private static final String LOG_TAG = LoginActivity.class.getSimpleName();
    /* A dialog that is presented until the Firebase authentication finished. */
    private ProgressDialog mAuthProgressDialog;
    private EditText mEditTextEmailInput, mEditTextPasswordInput;

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

        /**
         * Link layout elements from XML and setup progress dialog
         */
        initializeScreen();

    }

    private void initializeScreen() {
        mEditTextEmailInput = (EditText) findViewById(R.id.edit_text_email);
        mEditTextPasswordInput = (EditText) findViewById(R.id.edit_text_password);

        ScrollView scrollViewLoginActivity = (ScrollView) findViewById(R.id.activity_login);
        initializeBackground(scrollViewLoginActivity);
    }
    //navigate to create account activity
    public void onSignUpPressed(View view)
    {
        Intent signupImtent = new Intent(this,CreateAccountActivity.class);
        startActivity(signupImtent);
    }
}
