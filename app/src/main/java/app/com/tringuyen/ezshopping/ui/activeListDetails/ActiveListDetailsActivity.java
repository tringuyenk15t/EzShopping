package app.com.tringuyen.ezshopping.ui.activeListDetails;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import app.com.tringuyen.ezshopping.R;
import app.com.tringuyen.ezshopping.model.ShoppingList;
import app.com.tringuyen.ezshopping.ui.EzShoppingBaseActivity;
import app.com.tringuyen.ezshopping.uti.Constants;
import app.com.tringuyen.ezshopping.uti.FirebaseHelper;

public class ActiveListDetailsActivity extends EzShoppingBaseActivity {
    private ListView mListView;
    private ShoppingList mShoppingList;
    private String listName;
    private Toolbar toolbar;

    private static DatabaseReference listDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_list_details);

         //Link layout elements from XML and setup the toolbar
        initializeScreen();
    }

    /**
     * Link layout elements from XML and setup the toolbar
     */
    private void initializeScreen() {
        mListView = (ListView) findViewById(R.id.list_view_shopping_list_items);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        /* Common toolbar setup */
        setSupportActionBar(toolbar);

        listDB = FirebaseHelper.getIntance().getDataCollection(Constants.ACTLIST);
        //get shopping list from firebase
        listDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mShoppingList = new ShoppingList(dataSnapshot);

                if (mShoppingList != null)
                {
                    toolbar.setTitle(mShoppingList.getListName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /* Add back button to the action bar */
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_details,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_edit_list_name:
                //Show edit list dialog when the edit action is selected
                showEditListNameDialog();
                break;
            case R.id.action_remove_list:
                //removeList() when the remove action is selected
                removeList();
                break;
            case R.id.action_archive:
                //archiveList() when the archive action is selected
                archiveList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Archive current list when user selects "Archive" menu item
     */
    private void archiveList() {

    }

    /**
     * Remove current shopping list and its items from all nodes
     */
    private void removeList() {

    }

    /**
     * Show edit list name dialog when user selects "Edit list name" menu item
     */
    private void showEditListNameDialog() {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = EditListNameDialogFragment.newInstance(mShoppingList);
        dialog.show(this.getFragmentManager(), "EditListNameDialogFragment");
    }

    public void setToolbarTitle(String title)
    {
        toolbar.setTitle(listName);
    }


}
