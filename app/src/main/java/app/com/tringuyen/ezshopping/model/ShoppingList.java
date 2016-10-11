package app.com.tringuyen.ezshopping.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ServerValue;
import java.util.HashMap;
import java.util.Map;

import app.com.tringuyen.ezshopping.uti.Constants;
/**
 * Created by Tri Nguyen on 10/9/2016.
 */

public class ShoppingList {
    private String listName;
    private String owner;
    private HashMap<String,Object> lastChangedDate;

    public ShoppingList ()
    {

    }

    public ShoppingList(String listName, String owner) {
        this.listName = listName;
        this.owner = owner;
        //Date last changed will always be set to ServerValue.TIMESTAMP
        HashMap<String, Object> dateLastChangedObj = new HashMap<String, Object>();
        dateLastChangedObj.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
        this.lastChangedDate = dateLastChangedObj;
    }

    public ShoppingList(DataSnapshot dataSnapshot)
    {
        Map<String, Object> data =  (Map<String, Object>) dataSnapshot.getValue();

        this.listName = data.get("listName").toString();
        this.owner = data.get("owner").toString();
        this.lastChangedDate = (HashMap<String, Object>) data.get("dateLastChanged");;
    }

    public String getListName() {
        return listName;
    }

    public String getOwner() {
        return owner;
    }

    public HashMap<String, Object> getDateLastChanged() {
        return lastChangedDate;
    }
    @JsonIgnore
    public long getDateLastChangedLong() {
        return (long) lastChangedDate.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }
}
