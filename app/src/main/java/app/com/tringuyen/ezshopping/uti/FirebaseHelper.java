package app.com.tringuyen.ezshopping.uti;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Tri Nguyen on 10/4/2016.
 */

public class FirebaseHelper {
    private DatabaseReference cliendDB;
    private static FirebaseHelper instance;
    private FirebaseHelper()
    {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        cliendDB = FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseHelper getIntance()
    {
        if(instance == null)
        {
            instance = new FirebaseHelper();
        }
        return instance;
    }

    public String saveChatItem (String listName)
    {
        DatabaseReference chatCollection = cliendDB.child(Constants.LSTNAME);

        chatCollection.push();
        chatCollection.setValue(listName);
        return chatCollection.getKey();
//        DatabaseReference newChatItem = chatCollection.push();
//        newChatItem.setValue(listName);
//        return newChatItem.getKey();

    }

    public DatabaseReference getDataCollection(String childName)
    {
        return cliendDB.child(childName);
    }
//    public DatabaseReference getCliendDB() {
//        return cliendDB;
//    }
//
//    public DatabaseReference getChatCollection()
//    {
//        return  cliendDB.child("ChatDB");
//    }
}
