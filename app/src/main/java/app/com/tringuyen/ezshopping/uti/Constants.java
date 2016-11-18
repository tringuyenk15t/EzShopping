package app.com.tringuyen.ezshopping.uti;

import java.text.SimpleDateFormat;

/**
 * Created by Tri Nguyen on 10/9/2016.
 * Constants class store most important strings and paths of the app
 */

public class Constants {

    public static final String LIST_NAME = "listName";
    public static final String ITEM_NAME = "itemName";
    public static final String ACTLIST = "activeList";
    public static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";
    public static final String DATE_CREATED = "dateCreated";
    public static final String DATE_EDITED = "dateEdited";

    public static final String LIST_DETAIL_KEY = "ListKey";
    public static final String ITEM_KEY = "ItemKey";
    public static final String SHOPPING_LIST_ITEM = "shoppingListItem";

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    /**
     * Constants for bundles, extras and shared preferences keys
     */
    public static final String KEY_LAYOUT_RESOURCE = "LAYOUT_RESOURCE";

    public static final String ERROR_EMAIL_ALREADY_IN_USE = "ERROR_EMAIL_ALREADY_IN_USE";
}
