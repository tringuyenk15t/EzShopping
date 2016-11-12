package app.com.tringuyen.ezshopping.ui.activeListDetails;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import app.com.tringuyen.ezshopping.R;
import app.com.tringuyen.ezshopping.model.ShoppingListItem;
import app.com.tringuyen.ezshopping.uti.Constants;
import app.com.tringuyen.ezshopping.uti.FirebaseHelper;

/**
 * Created by Tri Nguyen on 11/2/2016.
 */

public class AddListItemDialogFragment extends DialogFragment {
    private String mItemID;
    private EditText mItemNameEditText;
    /**
     * Public static constructor that creates fragment and passes a bundle with data into it when adapter is created
     */
    public static AddListItemDialogFragment newInstance(String key)
    {
        AddListItemDialogFragment addListItemDialogFragment = new AddListItemDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.LIST_DETAIL_KEY,key);
        addListItemDialogFragment.setArguments(bundle);
        return addListItemDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemID = getArguments().getString(Constants.LIST_DETAIL_KEY);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder buider = new AlertDialog.Builder(getActivity(),R.style.CustomTheme_Dialog);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_add_item,null);
        mItemNameEditText = (EditText) v.findViewById(R.id.edit_text_list_dialog);
        buider.setView(v)
                .setPositiveButton(R.string.positive_button_add_list_item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String itemName = mItemNameEditText.getText().toString();
                        createNewItem(itemName);
                    }
                })
                .setNegativeButton(R.string.negative_button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        return buider.create();
    }

    private void createNewItem(String itemName)
    {
        if (itemName.length() > 0)
        {
            ShoppingListItem item = new ShoppingListItem(itemName);
            FirebaseHelper.getIntance().addShoppingListItem(item, mItemID);
        }
    }
}
