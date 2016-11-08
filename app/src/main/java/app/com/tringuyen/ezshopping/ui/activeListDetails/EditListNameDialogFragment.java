package app.com.tringuyen.ezshopping.ui.activeListDetails;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;

import app.com.tringuyen.ezshopping.R;
import app.com.tringuyen.ezshopping.model.ShoppingList;
import app.com.tringuyen.ezshopping.uti.Constants;
import app.com.tringuyen.ezshopping.uti.FirebaseHelper;

/**
 * Created by Tri Nguyen on 10/14/2016.
 */
public class EditListNameDialogFragment extends EditListDialogFragment {

    private static final String LOG_TAG = ActiveListDetailsActivity.class.getSimpleName();
    private String listName;
    private String key;
    /**
     * Public static constructor that creates fragment and passes a bundle with data into it when adapter is created
     */
    public static EditListNameDialogFragment newInstance(ShoppingList shoppingList, String listID) {
        EditListNameDialogFragment editListNameDialogFragment = new EditListNameDialogFragment();
        Bundle bundle = EditListDialogFragment.newInstanceHelper(shoppingList, R.layout.dialog_edit_list,listID);
        bundle.putString(Constants.LSTNAME,shoppingList.getListName());
        editListNameDialogFragment.setArguments(bundle);
        return editListNameDialogFragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        listName = bundle.getString(Constants.LSTNAME);
        key  = bundle.getString(Constants.LIST_DETAIL_KEY);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        /** {@link EditListDialogFragment#createDialogHelper(int)} is a
         * superclass method that creates the dialog
         **/
        Dialog dialog = super.createDialogHelper(R.string.positive_button_edit_item);
        helpSetDefaultValueEditText(listName);
        return dialog;
    }

    /**
     * Changes the list name in all copies of the current list
     */
    protected void doListEdit() {
        // get input name
        String newListName = mEditTextForList.getText().toString();
        // ensure new name is not null and does not duplicate with the old one
        if(!newListName.equals(""))
        {
            if (listName != null && key != null)
            {
                if (!newListName.equals(listName))
                {
                    FirebaseHelper.getIntance().updateListName(newListName,key);
                }
            }
        }
    }

}
