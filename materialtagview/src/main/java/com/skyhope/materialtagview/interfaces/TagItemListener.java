package com.skyhope.materialtagview.interfaces;

import com.skyhope.materialtagview.model.TagModel;
/*
 *  ****************************************************************************
 *  * Created by : Md Tariqul Islam on 3/18/2019 at 7:18 PM.
 *  * Email : tariqul@w3engineers.com
 *  *
 *  * Purpose:
 *  *
 *  * Last edited by : Md Tariqul Islam on 3/18/2019.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */


public interface TagItemListener {
    void onGetAddedItem(TagModel tagModel);

    void onGetRemovedItem(TagModel model);
}
