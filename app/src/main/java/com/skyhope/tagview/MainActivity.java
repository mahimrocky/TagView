package com.skyhope.tagview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.skyhope.materialtagview.TagView;
import com.skyhope.materialtagview.enums.TagSeparator;


public class MainActivity extends AppCompatActivity {

    TagView tagView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tagView = findViewById(R.id.tag_view_test);

        tagView.setHint("Add your skill");

        //tagView.addTagSeparator(TagSeparator.AT_SEPARATOR);

        tagView.addTagLimit(5);

       // tagView.setTagBackgroundColor(Color.RED);

        String[] tagList = new String[]{"C++", "Java", "PHP"};
        tagView.setTagList(tagList);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
