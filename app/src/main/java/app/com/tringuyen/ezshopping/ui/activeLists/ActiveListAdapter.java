package app.com.tringuyen.ezshopping.ui.activeLists;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;

import app.com.tringuyen.ezshopping.R;
import app.com.tringuyen.ezshopping.model.ShoppingList;

/**
 * Created by Tri Nguyen on 10/30/2016.
 */

//public class ActiveListAdapter extends RecyclerView.Adapter {
//
//    private List<ShoppingList> activeList= new ArrayList<>();
//
//    public  ActiveListAdapter (List<ShoppingList> activeList)
//    {
//        this.activeList = activeList;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_active_list,parent,false);
//        ActiveListViewHolder holder = new ActiveListViewHolder(v);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        ((ActiveListViewHolder)holder).tv_listName.setText(activeList.get(position).getListName());
//        ((ActiveListViewHolder)holder).tv_owner.setText(activeList.get(position).getOwner());
//        ((ActiveListViewHolder)holder).tv_editTime.setText(Utils.convertTimeStamp(activeList.get(position)));
//    }
//
//    @Override
//    public int getItemCount() {
//        return activeList.size();
//    }
//
//    private static class ActiveListViewHolder extends RecyclerView.ViewHolder
//    {
//        public TextView tv_listName,tv_owner,tv_editTime;
//        public ActiveListViewHolder(View itemView) {
//            super(itemView);
//            tv_listName = (TextView) itemView.findViewById(R.id.text_view_list_name);
//            tv_owner = (TextView) itemView.findViewById(R.id.text_view_created_by_user);
//            tv_editTime = (TextView) itemView.findViewById(R.id.text_view_edit_time);
//        }
//    }
//
//    public void setActiveList(List<ShoppingList> activeList) {
//        this.activeList = activeList;
//    }
//}

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
//        tv_editTime.setText(Utils.convertTimeStamp(model));
    }
}