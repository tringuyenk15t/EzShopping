package app.com.tringuyen.ezshopping.model;

/**
 * Created by Tri Nguyen on 11/2/2016.
 */

public class ShoppingListItem {
    private String itemName,owner;

    public ShoppingListItem()
    {

    }

    public ShoppingListItem(String itemName)
    {
        this.itemName = itemName;
        this.owner = "Anonymous onwer";
    }

    public String getItemName() {
        return itemName;
    }

    public String getOwner() {
        return owner;
    }
}
