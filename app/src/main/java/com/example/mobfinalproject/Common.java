package com.example.mobfinalproject;

import java.util.ArrayList;
import java.util.List;

//store global variables as reference during playtime

/*notes:
    * there are 12 categories
    * there are 10 questions per category
 */

public class Common {
    public static String categoryID; //1 - 12, based on which category is clicked
    public static User currentUser; //the logged in user
    public static List<Question> questionList = new ArrayList<>(); //the list of questions corresponding to category
}
