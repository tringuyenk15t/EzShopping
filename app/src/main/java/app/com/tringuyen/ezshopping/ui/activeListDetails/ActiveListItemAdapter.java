package app.com.tringuyen.ezshopping.ui.activeListDetails;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import app.com.tringuyen.ezshopping.R;
import app.com.tringuyen.ezshopping.model.ShoppingListItem;

/**
 * Created by Tri Nguyen on 11/2/2016.
 */

public class ActiveListItemAdapter extends FirebaseListAdapter<ShoppingListItem> {

    public ActiveListItemAdapter(Activity activity, Class<ShoppingListItem> modelClass, int modelLayout, DatabaseReference ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    @Override
    protected void populateView(View v,ShoppingListItem model, final int position) {
        TextView tv_itemName = (TextView) v.findViewById(R.id.text_view_active_list_item_name);
        ImageButton bt_remove = (ImageButton) v.findViewById(R.id.button_remove_item);

        tv_itemName.setText(model.getName());

        tv_itemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO create edit item name dialog here

            }
        });
        bt_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, R.style.CustomTheme_Dialog);
                builder.setTitle(mActivity.getString(R.string.remove_item_option))
                        .setMessage(mActivity.getString(R.string.dialog_message_are_you_sure_remove_item))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                removeItem();
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

    private void removeItem()
    {

    }

}
