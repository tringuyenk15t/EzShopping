package app.com.tringuyen.ezshopping.model;
import android.os.Parcel;
import android.os.Parcelable;

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

public class ShoppingList implements Parcelable {
    private String listName;
    private String owner;
    private HashMap<String,Object> dateEdited;
    private HashMap<String, Object> dateCreated;

    public static final Parcelable.Creator<ShoppingList> CREATOR = new Creator<ShoppingList>() {
        @Override
        public ShoppingList createFromParcel(Parcel source) {
            return new ShoppingList(source);
        }

        @Override
        public ShoppingList[] newArray(int size) {
            return new ShoppingList[0];
        }
    };

    public ShoppingList ()
    {

    }

    public ShoppingList(Parcel parcel)
    {
        this.listName = parcel.readString();
        this.owner = parcel.readString();
        this.dateEdited = parcel.readHashMap(HashMap.class.getClassLoader());
        this.dateCreated = parcel.readHashMap(HashMap.class.getClassLoader());
    }

    public ShoppingList(String listName, String owner, HashMap<String, Object> dateCreated) {
        this.listName = listName;
        this.owner = owner;
        this.dateCreated = dateCreated;
        //Date last changed will always be set to ServerValue.TIMESTAMP
        HashMap<String, Object> dateLastChangedObj = new HashMap<String, Object>();
        dateLastChangedObj.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
        this.dateEdited = dateLastChangedObj;
    }

    public ShoppingList(DataSnapshot dataSnapshot)
    {
        Map<String, Object> data =  (Map<String, Object>) dataSnapshot.getValue();
        this.listName = data.get("listName").toString();
        this.owner = data.get("owner").toString();
        this.dateCreated = (HashMap<String, Object>) data.get(Constants.DATE_CREATED);
        this.dateEdited = (HashMap<String, Object>) data.get(Constants.DATE_EDITED);
    }

    public String getListName() {
        return listName;
    }

    public String getOwner() {
        return owner;
    }

//    public HashMap<String, Object> getDateLastChanged() {
//        return lastChangedDate;
//    }


    public HashMap<String, Object> getDateEdited() {
        return dateEdited;
    }

    public HashMap<String, Object> getDateCreated() {
        return dateCreated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(listName);
        dest.writeString(owner);
        dest.writeMap(dateEdited);
        dest.writeMap(dateCreated);
    }
}
