package app.com.tringuyen.ezshopping.uti;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import app.com.tringuyen.ezshopping.model.ShoppingList;

/**
 * Created by Tri Nguyen on 10/4/2016.
 */

public class FirebaseHelper {
    private static FirebaseHelper instance;
    private DatabaseReference cliendDB;
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

    public void saveShoppingList(ShoppingList shoppingList)
    {
        DatabaseReference chatCollection = cliendDB.child(Constants.ACTLIST);
        chatCollection.push();
        chatCollection.setValue(shoppingList);
//        DatabaseReference newChatItem = chatCollection.push();
//        newChatItem.setValue(listName);
//        return newChatItem.getKey();
    }

    public DatabaseReference getDataCollection(String childName)
    {
        return cliendDB.child(childName);
    }
}
