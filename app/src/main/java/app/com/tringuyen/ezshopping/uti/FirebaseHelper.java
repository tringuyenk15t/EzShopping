package app.com.tringuyen.ezshopping.uti;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

import app.com.tringuyen.ezshopping.model.ShoppingList;
import app.com.tringuyen.ezshopping.model.ShoppingListItem;

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
        DatabaseReference newListItem = dataCollection.push();
        newListItem.setValue(shoppingList);
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

    public DatabaseReference getDatabase()
    {
        return cliendDB;
    }

    /**
     * Update list name
     * @param name - new list name
     * @param key - id of shopping list
     */
    public void updateListName(String name, String key)
    {
        DatabaseReference newListObject = cliendDB.child(Constants.ACTLIST).child(key);
        HashMap<String, Object> newListName = new HashMap<String, Object>();
        HashMap<String, Object> lastTimeChanged = new HashMap<String, Object>();
        lastTimeChanged.put(Constants.FIREBASE_PROPERTY_TIMESTAMP,ServerValue.TIMESTAMP);

        newListName.put(Constants.LSTNAME,name);
        newListName.put(Constants.DATE_EDITED,lastTimeChanged);
        newListObject.updateChildren(newListName, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                //TODO handle update error
            }
        });
    }

    /**
     * Add new item for a shopping list
     * @param item - new item need to be added
     * @param key - id of shopping list
     */
    public void addShoppingListItem(ShoppingListItem item, String key)
    {
        DatabaseReference itemDateBaseRef = cliendDB.child(Constants.SHOPPING_LIST_ITEM).child(key);
        DatabaseReference newItem = itemDateBaseRef.push();
        newItem.setValue(item);
    }
}
