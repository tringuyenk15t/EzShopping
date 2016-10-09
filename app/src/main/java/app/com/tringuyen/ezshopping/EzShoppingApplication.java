package app.com.tringuyen.ezshopping;

import android.app.Application;

import app.com.tringuyen.ezshopping.uti.FirebaseHelper;

/**
 * Created by Tri Nguyen on 10/4/2016.
 */

public class EzShoppingApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseHelper.getIntance();
    }
}
