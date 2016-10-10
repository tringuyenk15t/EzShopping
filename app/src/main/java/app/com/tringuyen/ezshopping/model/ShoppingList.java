package app.com.tringuyen.ezshopping.model;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Tri Nguyen on 10/9/2016.
 */

public class ShoppingList {
    private String listName;
    private String owner;
    private HashMap<String, Object> lastChangedDate;

    public ShoppingList ()
    {

    }

    public ShoppingList(String listName, String owner) {
        this.listName = listName;
        this.owner = owner;
        //Date last changed will always be set to ServerValue.TIMESTAMP
        HashMap<String, Object> dateLastChangedObj = new HashMap<String, Object>();
        dateLastChangedObj.put("date", ServerValue.TIMESTAMP);
        this.lastChangedDate = dateLastChangedObj;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public HashMap<String, Object> getDateLastChanged() {
        return lastChangedDate;
    }

    public long getDateLastChangedLong() {

        return (long) lastChangedDate.get("date");
    }
}
