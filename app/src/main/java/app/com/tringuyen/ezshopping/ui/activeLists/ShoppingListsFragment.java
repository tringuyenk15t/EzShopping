package app.com.tringuyen.ezshopping.ui.activeLists;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.com.tringuyen.ezshopping.R;
import app.com.tringuyen.ezshopping.model.ShoppingList;
import app.com.tringuyen.ezshopping.ui.activeListDetails.ActiveListDetailsActivity;
import app.com.tringuyen.ezshopping.uti.Constants;
import app.com.tringuyen.ezshopping.uti.FirebaseHelper;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass that shows a list of all shopping lists a user can see.
 * Use the {@link ShoppingListsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingListsFragment extends Fragment {
    //list view version
    private ActiveListAdapter adapter;
    private ListView mListView;

    public ShoppingListsFragment ()
    {
        /* Required empty public constructor */
    }

    /**
     * Create fragment and pass bundle with data as it's arguments
     * Right now there are not arguments...but eventually there will be.
     */
    public static ShoppingListsFragment newInstance()
    {
        ShoppingListsFragment fragment = new ShoppingListsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Initalize UI elements
         */
        final View rootView = inflater.inflate(R.layout.fragment_shopping_lists, container, false);
        initializeScreen(rootView);

        adapter = new ActiveListAdapter(getActivity(), ShoppingList.class,  R.layout.single_active_list,
                FirebaseHelper.getIntance().getDataCollection(Constants.ACTLIST));
        mListView.setAdapter(adapter);

        /**
         * Set interactive bits, such as click events and adapters
         */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseReference listItemRef = adapter.getRef(position);
                String key = listItemRef.getKey();
                Intent intent = new Intent(getContext(),ActiveListDetailsActivity.class);
                intent.putExtra(Constants.LIST_DETAIL_KEY,key);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.cleanup();
    }

    /**
     * Link layout elements from XML
     */
    private void initializeScreen(View rootView) {
        //TODO change it to recyclerview in future update
        mListView = (ListView) rootView.findViewById(R.id.list_view_active_lists);
    }
}
