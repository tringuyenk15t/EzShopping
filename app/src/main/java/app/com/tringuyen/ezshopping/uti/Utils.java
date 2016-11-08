package app.com.tringuyen.ezshopping.uti;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import app.com.tringuyen.ezshopping.model.ShoppingList;

/**
 * Utility class
 */
public class Utils {
    //convert timestamp from HashMap to String.
    public static String convertTimeStamp(HashMap<String,Object> date)
    {
        if (date.get(Constants.FIREBASE_PROPERTY_TIMESTAMP) instanceof Long)
            return Constants.SIMPLE_DATE_FORMAT.format((long)date.get(Constants.FIREBASE_PROPERTY_TIMESTAMP));
        else
            return"";
    }
}
