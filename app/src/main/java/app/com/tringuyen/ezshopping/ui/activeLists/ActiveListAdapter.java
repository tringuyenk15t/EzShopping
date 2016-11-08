package app.com.tringuyen.ezshopping.ui.activeLists;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;

import app.com.tringuyen.ezshopping.R;
import app.com.tringuyen.ezshopping.model.ShoppingList;
import app.com.tringuyen.ezshopping.uti.Utils;

/**
 * Created by Tri Nguyen on 10/30/2016.
 */

public class ActiveListAdapter extends FirebaseListAdapter<ShoppingList>
{

    public ActiveListAdapter(Activity activity, Class<ShoppingList> modelClass, int modelLayout, DatabaseReference ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    @Override
    protected void populateView(View v, ShoppingList model, int position) {
        TextView tv_listName = (TextView) v.findViewById(R.id.text_view_list_name);
        TextView tv_owner = (TextView) v.findViewById(R.id.text_view_created_by_user);
        TextView tv_editTime = (TextView) v.findViewById(R.id.text_view_edit_time);

        tv_listName.setText(model.getListName());
        tv_owner.setText(model.getOwner());
//        tv_editTime.setText(Utils.convertTimeStamp(model.getDateCreated()));
    }
}