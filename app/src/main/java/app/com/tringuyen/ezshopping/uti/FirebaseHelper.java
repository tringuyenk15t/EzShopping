package app.com.tringuyen.ezshopping.uti;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

import app.com.tringuyen.ezshopping.model.ShoppingList;

/**
 * Created by Tri Nguyen on 10/4/2016.
 * Firebase singleton handle firebase database interaction
 */

public class FirebaseHelper {
    private static FirebaseHelper instance;
    private DatabaseReference cliendDB;

    /**
     * constructor
     */
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

    /**
     * Save shpping list to firebase database
     * @param shoppingList
     */
    public void saveShoppingList(ShoppingList shoppingList)
    {
        DatabaseReference dataCollection = cliendDB.child(Constants.ACTLIST);
        dataCollection.setValue(shoppingList);
    }

    /**
     * Get DatabaseReference of a child name
     * @param childName - child note name
     * @return - Child note corresponding to child name
     */
    public DatabaseReference getDataCollection(String childName)
    {
        return cliendDB.child(childName);
    }

    /**
     * Update list name
     * @param name - new list name
     */
    public void updateListName(String name)
    {
        DatabaseReference newListObject = cliendDB.child(Constants.ACTLIST);
        HashMap<String, Object> newListName = new HashMap<String, Object>();
        HashMap<String, Object> lastTimeChanged = new HashMap<String, Object>();
        lastTimeChanged.put(Constants.FIREBASE_PROPERTY_TIMESTAMP,ServerValue.TIMESTAMP);

        newListName.put(Constants.LSTNAME,name);
        newListName.put(Constants.LIST_LAST_CHANGE_DATE,lastTimeChanged);
        newListObject.updateChildren(newListName);
    }
}
