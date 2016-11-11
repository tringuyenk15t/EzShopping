package app.com.tringuyen.ezshopping.ui.activeListDetails;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

import app.com.tringuyen.ezshopping.R;
import app.com.tringuyen.ezshopping.model.ShoppingList;
import app.com.tringuyen.ezshopping.model.ShoppingListItem;
import app.com.tringuyen.ezshopping.ui.EzShoppingBaseActivity;
import app.com.tringuyen.ezshopping.uti.Constants;
import app.com.tringuyen.ezshopping.uti.FirebaseHelper;

public class ActiveListDetailsActivity extends EzShoppingBaseActivity {
    private static final String LOG_TAG = ActiveListDetailsActivity.class.getSimpleName();
    private ListView mListView;
    private FloatingActionButton bt_addItem;
    private FirebaseListAdapter<ShoppingListItem> listItemAdapter;
    private DatabaseReference shoppingListRef;
    private ShoppingList mShoppingList;
    private Toolbar toolbar;
    private String key;
    private ValueEventListener shoppingListListener;

    private static DatabaseReference listDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_list_details);

        Intent intent = getIntent();
        key = intent.getStringExtra(Constants.LIST_DETAIL_KEY);
        if (key == null) {
             /* No point in continuing without a valid ID. */
            finish();
            return;
        }
         //Link layout elements from XML and setup the toolbar
        initializeScreen();
    }

    /**
     * Link layout elements from XML and setup the toolbar
     */
    private void initializeScreen() {
        //initialize layout components
        mListView = (ListView) findViewById(R.id.list_view_shopping_list_items);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        bt_addItem = (FloatingActionButton) findViewById(R.id.fab_detail_add_item);

        //add item button listener
        bt_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddListItemDialog();
            }
        });
        setUpToolBar();
        setupItemList();
    }


    /**
     * setup tool bar and shoppinglist item
     */
    private void setUpToolBar()
    {
        shoppingListRef = FirebaseHelper.getIntance().getDataCollection(Constants.ACTLIST).child(key);
        shoppingListRef.addValueEventListener(shoppingListListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null)
                {
                    finish();
                    /**
                     * Make sure to call return, otherwise the rest of the method will execute,
                     * even after calling finish.
                     */
                    return;
                }
                mShoppingList = new ShoppingList(dataSnapshot);
                toolbar.setTitle(mShoppingList.getListName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(LOG_TAG,
                        getString(R.string.log_error_the_read_failed) +
                                databaseError.getMessage());
            }
        });

        /* Common toolbar setup */
        setSupportActionBar(toolbar);

        /* Add back button to the action bar */
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * set up initial item list
     */
    private void setupItemList()
    {
        DatabaseReference shoppingListRef = FirebaseHelper.getIntance().getDataCollection(Constants.SHOPPING_LIST_ITEM).child(key);
        listItemAdapter = new ActiveListItemAdapter(this,ShoppingListItem.class,R.layout.single_active_list_item,shoppingListRef);
        mListView.setAdapter(listItemAdapter);
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
     * Create a new item for shopping list
     */
    private void showAddListItemDialog()
    {
        AddListItemDialogFragment dialog = AddListItemDialogFragment.newInstance(key);
        dialog.show(this.getFragmentManager(),"AddItemDialogFragment");
    }

    /**
     * Remove current shopping list and its items from all nodes
     */
    private void removeList() {
        DialogFragment dialog = RemoveListDialogFragment.newInstance(mShoppingList,key);
        dialog.show(this.getFragmentManager(),"RemoveListDialogFragment");
    }

    /**
     * Show edit list name dialog when user selects "Edit list name" menu item
     */
    private void showEditListNameDialog() {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = EditListNameDialogFragment.newInstance(mShoppingList,key);
        dialog.show(this.getFragmentManager(), "EditListNameDialogFragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //remove all event listeners
        listItemAdapter.cleanup();
        shoppingListRef.removeEventListener(shoppingListListener);
    }
}
