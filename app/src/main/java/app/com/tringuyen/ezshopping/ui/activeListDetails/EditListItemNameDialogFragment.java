package app.com.tringuyen.ezshopping.ui.activeListDetails;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Objects;

import app.com.tringuyen.ezshopping.R;
import app.com.tringuyen.ezshopping.model.ShoppingListItem;
import app.com.tringuyen.ezshopping.uti.Constants;
import app.com.tringuyen.ezshopping.uti.FirebaseHelper;

/**
 * Created by Tri Nguyen on 11/8/2016.
 */

public class EditListItemNameDialogFragment extends DialogFragment {
    private String listID,itemID,itemName;
    private int resource;
    private EditText ed_editItemName;

    public static EditListItemNameDialogFragment newInstance(ShoppingListItem item, String listID, String itemID)
    {
        EditListItemNameDialogFragment itemNameFragment = new EditListItemNameDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ITEM_NAME,item.getItemName());
        bundle.putString(Constants.LIST_DETAIL_KEY,listID);
        bundle.putString(Constants.ITEM_KEY,itemID);
        itemNameFragment.setArguments(bundle);
        return itemNameFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        this.itemName = bundle.getString(Constants.ITEM_NAME);
        this.listID = bundle.getString(Constants.LIST_DETAIL_KEY);
        this.itemID = bundle.getString(Constants.ITEM_KEY);
        resource = R.layout.dialog_edit_item;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);

        View rootView = getActivity().getLayoutInflater().inflate(resource,null);
        ed_editItemName = (EditText) rootView.findViewById(R.id.edit_text_item_dialog);

        ed_editItemName.setText(itemName);
        //move cursor to the end of itemName
        ed_editItemName.setSelection(itemName.length());

        ed_editItemName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    doListEditItem();
                    /**
                     * Close the dialog fragment when done
                     */
                    EditListItemNameDialogFragment.this.getDialog().cancel();
                }
                return true;
            }

        });

        builder.setView(rootView)
                .setTitle(R.string.positive_button_edit_item)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doListEditItem();
                        /**
                         * Close the dialog fragment when done
                         */
                        EditListItemNameDialogFragment.this.getDialog().cancel();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /**
                         * Close the dialog fragment
                         */
                        EditListItemNameDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void doListEditItem()
    {
        String newListItemName = ed_editItemName.getText().toString();
        if (newListItemName.length() > 0 && newListItemName != itemName)
        {
            DatabaseReference dbRef = FirebaseHelper.getIntance().getDatabase();

            HashMap<String,Object> itemToUpdate = new HashMap<>();
            HashMap<String,Object> itemNameToUpdate = new HashMap<>();
            HashMap<String,Object> timestampToUpdate = new HashMap<>();

            // add new item name
//            itemNameToUpdate.put(Constants.ITEM_NAME,newListItemName);
            itemToUpdate.put(
                    Constants.SHOPPING_LIST_ITEM + "/" + listID + "/" + itemID + "/" + Constants.ITEM_NAME
                    ,newListItemName);

            // add new timestamp
            timestampToUpdate.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
            itemToUpdate.put(
                    Constants.ACTLIST + "/" + listID + "/" + Constants.DATE_EDITED
                    ,timestampToUpdate);

            // save to firebase database
            dbRef.updateChildren(itemToUpdate);
        }
    }
}
