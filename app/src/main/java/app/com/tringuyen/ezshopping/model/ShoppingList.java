package app.com.tringuyen.ezshopping.model;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Tri Nguyen on 10/9/2016.
 */

public class ShoppingList {
    private String listName;
    private String owner;

    public ShoppingList ()
    {

    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ShoppingList(DataSnapshot dataSnapshot)
    {
        
    }

    public ShoppingList(String listName, String owner) {
        this.listName = listName;
        this.owner = owner;
    }

    public String getListName() {
        return listName;
    }

    public String getOwner() {
        return owner;
    }
}
