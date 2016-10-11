package app.com.tringuyen.ezshopping.ui.activeListDetails;

import android.os.Bundle;

import app.com.tringuyen.ezshopping.R;
import app.com.tringuyen.ezshopping.ui.EzShoppingBaseActivity;

/**
 * Created by Tri Nguyen on 10/10/2016.
 */

public class ActiveListDetailsActivity extends EzShoppingBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_list_details);

        /**
         * Link layout elements from XML and setup the toolbar
         */
        initializeScreen();
    }

    private void initializeScreen() {

    }
}
