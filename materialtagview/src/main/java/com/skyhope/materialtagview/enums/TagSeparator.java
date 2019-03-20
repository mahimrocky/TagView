package com.skyhope.materialtagview.enums;

/*
 *  ****************************************************************************
 *  * Created by : Md Tariqul Islam on 3/18/2019 at 6:05 PM.
 *  * Email : tariqul@w3engineers.com
 *  *
 *  * Purpose:
 *  *
 *  * Last edited by : Md Tariqul Islam on 3/18/2019.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

public enum TagSeparator {

    COMMA_SEPARATOR(","),
    PLUS_SEPARATOR("+"),
    MINUS_SEPARATOR("-"),
    SPACE_SEPARATOR(" "),
    AT_SEPARATOR("@"),
    HASH_SEPARATOR("#");

    private final String name;

    TagSeparator(String s) {
        name = s;
    }

    public String getValue() {
        return name;
    }
}
