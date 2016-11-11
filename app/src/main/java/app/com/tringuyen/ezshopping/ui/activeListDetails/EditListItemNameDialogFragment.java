package app.com.tringuyen.ezshopping.ui.activeListDetails;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import app.com.tringuyen.ezshopping.R;
import app.com.tringuyen.ezshopping.uti.Constants;

/**
 * Created by Tri Nguyen on 11/8/2016.
 */

public class EditListItemNameDialogFragment extends DialogFragment {
    private String listID,itemID;
    private int resource;
    private EditText edt_itemName;

    public static EditListItemNameDialogFragment newInstance(String listID, String itemID)
    {
        EditListItemNameDialogFragment itemNameFragment = new EditListItemNameDialogFragment();
        Bundle bundle = new Bundle();

        bundle.putString(Constants.LIST_DETAIL_KEY,listID);
        bundle.putString(Constants.ITEM_KEY,itemID);
        itemNameFragment.setArguments(bundle);
        return itemNameFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        this.listID = bundle.getString(Constants.LIST_DETAIL_KEY);
        this.itemID = bundle.getString(Constants.ITEM_KEY);

        resource = R.layout.dialog_edit_list;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);

        return builder.create();
    }
}
