package app.com.tringuyen.ezshopping.ui.activeListDetails;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Objects;

import app.com.tringuyen.ezshopping.R;
import app.com.tringuyen.ezshopping.model.ShoppingList;
import app.com.tringuyen.ezshopping.model.ShoppingListItem;
import app.com.tringuyen.ezshopping.uti.Constants;
import app.com.tringuyen.ezshopping.uti.FirebaseHelper;

/**
 * Created by Tri Nguyen on 11/2/2016.
 */

public class ActiveListItemAdapter extends FirebaseListAdapter<ShoppingListItem> {
    private ShoppingList mShoppingList;
    private String mListID;

    public ActiveListItemAdapter(Activity activity, Class<ShoppingListItem> modelClass, int modelLayout, DatabaseReference ref) {
        super(activity, modelClass, modelLayout, ref);
        this.mListID = ref.getKey();
    }

    /**
     * Public method that is used to pass shoppingList object when it is loaded in ValueEventListener
     */
    public void setShoppingList(ShoppingList shoppingList) {
        this.mShoppingList = shoppingList;
        this.notifyDataSetChanged();
    }

    @Override
    protected void populateView(View v, final ShoppingListItem model, final int position) {
        TextView tv_itemName = (TextView) v.findViewById(R.id.text_view_active_list_item_name);
        ImageButton bt_remove = (ImageButton) v.findViewById(R.id.button_remove_item);

        final String itemID = getRef(position).getKey();

        tv_itemName.setText(model.getName());
        //set edit item listener
        tv_itemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Gets the id of the item to remove */

                EditListItemNameDialogFragment eidtItemDialog = EditListItemNameDialogFragment.newInstance(model, mListID,itemID);
                eidtItemDialog.show(mActivity.getFragmentManager(),"EditItemFragment");
            }
        });
        // set remove item listener
        bt_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, R.style.CustomTheme_Dialog);
                builder.setTitle(mActivity.getString(R.string.remove_item_option))
                        .setMessage(mActivity.getString(R.string.dialog_message_are_you_sure_remove_item))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeItem(itemID);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void removeItem(String itemID)
    {
        DatabaseReference dbRef = FirebaseHelper.getIntance().getDatabase();

        HashMap<String,Object> updateRemoveItem = new HashMap<>();
        //remove item
        updateRemoveItem.put(Constants.SHOPPING_LIST_ITEM + "/" + mListID + "/" + itemID,null);

        HashMap<String,Object> timeStamp = new HashMap<>();
        timeStamp.put(Constants.FIREBASE_PROPERTY_TIMESTAMP,ServerValue.TIMESTAMP);

        updateRemoveItem.put(Constants.ACTLIST + "/" + mListID + "/" + Constants.DATE_EDITED,timeStamp);

        dbRef.updateChildren(updateRemoveItem);
    }
}
