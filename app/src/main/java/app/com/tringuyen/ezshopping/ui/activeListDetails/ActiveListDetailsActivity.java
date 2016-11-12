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

import app.com.tringuyen.ezshopping.R;
import app.com.tringuyen.ezshopping.model.ShoppingList;
import app.com.tringuyen.ezshopping.model.ShoppingListItem;
import app.com.tringuyen.ezshopping.ui.EzShoppingBaseActivity;
import app.com.tringuyen.ezshopping.uti.Constants;
import app.com.tringuyen.ezshopping.uti.FirebaseHelper;

public class ActiveListDetailsActivity extends EzShoppingBaseActivity {
    private static final String LOG_TAG = ActiveListDetailsActivity.class.getSimpleName();
    private ListView mListView;
    private FloatingActionButton mAddItemButton;
    private FirebaseListAdapter<ShoppingListItem> mListItemAdapter;
    private DatabaseReference mShoppingListRef;
    private ShoppingList mShoppingList;
    private Toolbar mToolbar;
    private String mListID;
    private ValueEventListener mShoppingListListener;

    private static DatabaseReference listDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_list_details);

        Intent intent = getIntent();
        mListID = intent.getStringExtra(Constants.LIST_DETAIL_KEY);
        if (mListID == null) {
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
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        mAddItemButton = (FloatingActionButton) findViewById(R.id.fab_detail_add_item);

        //add item button listener
        mAddItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddListItemDialog();
            }
        });
        setUpToolBar();
        setupItemList();

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });
    }


    /**
     * setup tool bar and shoppinglist item
     */
    private void setUpToolBar()
    {
        mShoppingListRef = FirebaseHelper.getIntance().getDataCollection(Constants.ACTLIST).child(mListID);
        mShoppingListRef.addValueEventListener(mShoppingListListener = new ValueEventListener() {
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
                mToolbar.setTitle(mShoppingList.getListName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(LOG_TAG,
                        getString(R.string.log_error_the_read_failed) +
                                databaseError.getMessage());
            }
        });

        /* Common toolbar setup */
        setSupportActionBar(mToolbar);

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
        DatabaseReference shoppingListRef = FirebaseHelper.getIntance().getDataCollection(Constants.SHOPPING_LIST_ITEM).child(mListID);
        mListItemAdapter = new ActiveListItemAdapter(this,ShoppingListItem.class,R.layout.single_active_list_item,shoppingListRef);
        mListView.setAdapter(mListItemAdapter);
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
        AddListItemDialogFragment dialog = AddListItemDialogFragment.newInstance(mListID);
        dialog.show(this.getFragmentManager(),"AddItemDialogFragment");
    }

    /**
     * Remove current shopping list and its items from all nodes
     */
    private void removeList() {
        DialogFragment dialog = RemoveListDialogFragment.newInstance(mShoppingList, mListID);
        dialog.show(this.getFragmentManager(),"RemoveListDialogFragment");
    }

    /**
     * Show edit list name dialog when user selects "Edit list name" menu item
     */
    private void showEditListNameDialog() {
        /* Create an instance of the dialog fragment and show it */
        DialogFragment dialog = EditListNameDialogFragment.newInstance(mShoppingList, mListID);
        dialog.show(this.getFragmentManager(), "EditListNameDialogFragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //remove all event listeners
        mListItemAdapter.cleanup();
        mShoppingListRef.removeEventListener(mShoppingListListener);
    }
}
