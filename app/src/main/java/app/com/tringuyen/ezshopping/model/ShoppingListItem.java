package app.com.tringuyen.ezshopping.model;

/**
 * Created by Tri Nguyen on 11/2/2016.
 */

public class ShoppingListItem {
    private String name,owner;

    public ShoppingListItem()
    {

    }

    public ShoppingListItem(String name)
    {
        this.name = name;
        this.owner = "Anonymous onwer";
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }
}
