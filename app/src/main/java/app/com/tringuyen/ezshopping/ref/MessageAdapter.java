package app.com.tringuyen.ezshopping.ref;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.tringuyen.ezshopping.model.ChatItem;
import app.com.tringuyen.ezshopping.R;

/**
 * Created by Tri Nguyen on 10/4/2016.
 */
public class MessageAdapter extends RecyclerView.Adapter {
    List<ChatItem> listMessage = new ArrayList<>();

    public MessageAdapter(List<ChatItem> listMessage)
    {
        this.listMessage = listMessage;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,parent,false);
        MessageViewHolder vh = new MessageViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MessageViewHolder)holder).messageTxt.setText(listMessage.get(position).getMessage());
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTxt;
        public MessageViewHolder(View itemView) {
            super(itemView);
            messageTxt = (TextView) itemView.findViewById(R.id.messageTxt);
        }
    }
    public int getItemCount() {
        return listMessage.size();
    }
}
