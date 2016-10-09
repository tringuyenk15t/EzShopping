package app.com.tringuyen.ezshopping.model;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Tri Nguyen on 10/4/2016.
 */
public class ChatItem {
    private String message;

    public ChatItem()
    {

    }
    public ChatItem(DataSnapshot dataSnapshot)
    {
        ChatItem newChat = dataSnapshot.getValue(ChatItem.class);
        this.setMessage(newChat.getMessage());
    }

    public ChatItem(String message)
    {
        this.message = message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
