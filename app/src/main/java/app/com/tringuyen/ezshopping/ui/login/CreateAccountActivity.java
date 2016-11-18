package app.com.tringuyen.ezshopping.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.com.tringuyen.ezshopping.R;
import app.com.tringuyen.ezshopping.ui.EzShoppingBaseActivity;
import app.com.tringuyen.ezshopping.uti.Constants;
import app.com.tringuyen.ezshopping.uti.FirebaseHelper;

public class CreateAccountActivity extends EzShoppingBaseActivity {
    private static final String LOG_TAG = CreateAccountActivity.class.getSimpleName();
    private ProgressDialog mAuthProgressDialog;
    private EditText mEditTextUsernameCreate, mEditTextEmailCreate, mEditTextPasswordCreate;
    private String userEmail,userName,userPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        initializeScreen();
    }

    private void initializeScreen() {
        mEditTextUsernameCreate = (EditText) findViewById(R.id.edit_text_username_create);
        mEditTextEmailCreate = (EditText) findViewById(R.id.edit_text_email_create);
        mEditTextPasswordCreate = (EditText) findViewById(R.id.edit_text_password_create);

        ScrollView scrollViewCreateAccountActivity = (ScrollView) findViewById(R.id.activity_create_account);
        initializeBackground(scrollViewCreateAccountActivity);

        /* Setup the progress dialog that is displayed later when authenticating with Firebase */
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle(getResources().getString(R.string.progress_dialog_loading));
        mAuthProgressDialog.setMessage(getResources().getString(R.string.progress_dialog_creating_user_with_firebase));
        mAuthProgressDialog.setCancelable(false);

        mAuth = FirebaseAuth.getInstance();
    }

    //navigate to SignIn activity
    public void onSignInPressed(View view)
    {
        Intent signInIntent = new Intent(this,LoginActivity.class);
        signInIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(signInIntent);
        finish();
    }

    /**
     * Create new account using Firebase email/password provider
     */
    public void onCreateAccountPressed(View view) {
        userEmail = mEditTextEmailCreate.getText().toString();
        userName = mEditTextUsernameCreate.getText().toString();
        userPassword = mEditTextPasswordCreate.getText().toString();

        mAuth = FirebaseAuth.getInstance();

        if (!isEmailValid(userEmail) || !isUserNameValid(userName) || !isPasswordValid(userPassword))
        return;

        /**
         * If everything was valid show the progress dialog
         * to indicate that account creation has started
         */
        mAuthProgressDialog.show();

        //create new account
        mAuth.createUserWithEmailAndPassword(userEmail,userPassword)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mAuthProgressDialog.dismiss();
                if (!task.isSuccessful()) {
                    try{
                       throw task.getException();
                    }
                    catch (FirebaseAuthException exception)
                    {
                        if(exception.getErrorCode().equals(Constants.ERROR_EMAIL_ALREADY_IN_USE))
                        {
                            mEditTextEmailCreate.setError(getString(R.string.error_email_taken));
                            mEditTextEmailCreate.requestFocus();
                        }
                        else
                        {
                            showErrorToast(exception.toString());
                        }
                    }
                    catch (Exception e) {
                        showErrorToast(e.toString());
                    }
                }
                else
                {
                    showErrorToast(getString(R.string.log_message_auth_successful));
                }
            }
        });

    }

    /**
     * Creates a new user in Firebase from the Java POJO
     */
    private void createUserInFirebaseHelper(final String encodedEmail) {
    }

    //validate email in client side
    private boolean isEmailValid(String email) {
        boolean check = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (!check || email == null)
        {
            mEditTextEmailCreate.setError(getResources().getString(R.string.error_invalid_email_not_valid));
            return false;
        }
        return check;
    }

    private boolean isUserNameValid(String userName) {
        if (userName.equals("") )
        {
            mEditTextUsernameCreate.setError(getResources().getString(R.string.error_cannot_be_empty));
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 6)
        {
            mEditTextPasswordCreate.setError(getResources().getString(R.string.error_invalid_password_not_valid));
            return false;
        }
        return true;
    }
    /**
     * Show error toast to users
     */
    private void showErrorToast(String message) {
        Toast.makeText(CreateAccountActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
