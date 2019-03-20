package com.skyhope.materialtagview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.skyhope.materialtagview.adapter.TagViewAdapter;
import com.skyhope.materialtagview.enums.TagSeparator;
import com.skyhope.materialtagview.interfaces.TagClickListener;
import com.skyhope.materialtagview.interfaces.TagItemListener;
import com.skyhope.materialtagview.model.TagModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
 *  ****************************************************************************
 *  * Created by : Md Tariqul Islam on 3/18/2019 at 3:36 PM.
 *  * Email : tariqul@w3engineers.com
 *  *
 *  * Purpose:
 *  *
 *  * Last edited by : Md Tariqul Islam on 3/18/2019.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */


public class TagView extends FlexboxLayout implements TagClickListener {

    private List<TagModel> mTagList = new ArrayList<>();
    private List<View> mTagViewList = new ArrayList<>();

    private EditText editText;
    private RecyclerView recyclerView;
    private TextView textViewAdd;

    private TagViewAdapter mAdapter;

    private final String EMPTY_STRING = "";
    private final String DEFAULT_TAG_LIMIT_MESSAGE = "You reach maximum tags";

    /**
     * This is Default Tab separator.
     * User can add different tab separator
     * <p>
     * Tag separator must be a single character.
     * <p>
     * And the Tag separator will be special character
     */
    private String tagSeparator = TagSeparator.valueOf(TagSeparator.COMMA_SEPARATOR.name()).getValue();


    /**
     * The tagLimit define how many tag will show
     * Default it take maximum value.
     * It not Take 0/Zero value
     */
    private int tagLimit = Integer.MAX_VALUE;

    /**
     * Tag view Background color
     */
    private int tagBackgroundColor;

    private int mTagTextColor;

    private String mTagLimitMessage;

    /**
     * The both drawable and bitmap enable only when
     * other is null.
     * N.B: Both can be null
     */
    private Drawable mCrossDrawable;
    private Bitmap mCrossBitmap;

    private TagItemListener mTagItemListener;

    private List<String> mTagItemList = new ArrayList<>();

    public TagView(Context context) {
        super(context);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setFlexWrap(FlexWrap.WRAP);

        setJustifyContent(JustifyContent.FLEX_START);

        tagBackgroundColor = getResources().getColor(R.color.tag_bg);

        mTagTextColor = getResources().getColor(R.color.white);

        mAdapter = new TagViewAdapter(mTagTextColor, tagBackgroundColor);

        addEditText();

        init(attrs);

    }

    private void init(AttributeSet attributeSet) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.TagView);

        mTagTextColor = typedArray.getColor(R.styleable.TagView_tag_text_color, getResources().getColor(R.color.white));

        tagBackgroundColor = typedArray.getColor(R.styleable.TagView_tag_background_color, getResources().getColor(R.color.white));

        tagLimit = typedArray.getInt(R.styleable.TagView_tag_limit, 1);

        mCrossDrawable = typedArray.getDrawable(R.styleable.TagView_close_icon);

        mTagLimitMessage = typedArray.getString(R.styleable.TagView_limit_error_text);


        int separator = typedArray.getInt(R.styleable.TagView_tag_separator, 0);

        tagSeparator = convertSeparator(separator);

        typedArray.recycle();
    }

    /**
     * Adding a tag view in Main view
     */
    private void addTagInView() {
        final TagModel model = mTagList.get(mTagList.size() - 1);

        final LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View tagView = inflater.inflate(R.layout.tag_layout, null);

        TextView tagTextView = tagView.findViewById(R.id.text_view_tag);

        ImageView imageView = tagView.findViewById(R.id.image_view_cross);

        LinearLayout linearLayout = tagView.findViewById(R.id.tag_container);

        GradientDrawable drawable = (GradientDrawable) linearLayout.getBackground();

        drawable.setColor(tagBackgroundColor);

        tagTextView.setTextColor(mTagTextColor);

        if (mCrossBitmap != null) {
            imageView.setImageBitmap(mCrossBitmap);
        } else if (mCrossDrawable != null) {
            imageView.setImageDrawable(mCrossDrawable);
        }

        /*
         * Here we remove the tag
         * */
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView(tagView);
                mTagList.remove(model);

                if (model.isFromList()) {
                    mAdapter.addItem(model.getTagText());
                }

                if (mTagItemListener != null) {
                    mTagItemListener.onGetRemovedItem(model);
                }

                invalidate();
            }
        });

        tagTextView.setText(model.getTagText());

        this.addView(tagView, mTagList.indexOf(model));

        if (mTagItemListener != null) {
            mTagItemListener.onGetAddedItem(model);
        }

        // mTagViewList.add(view);

        invalidate();

    }

    /**
     * First add an Edit text to show edit form where user can create a tag
     */
    private void addEditText() {
        editText = new EditText(getContext());

        recyclerView = new RecyclerView(getContext());

        textViewAdd = new TextView(getContext());

        editText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().contains(tagSeparator)) {
                    if (mTagList.size() < tagLimit) {
                        String tagText = editable.toString();
                        tagText = tagText.replace(tagSeparator, EMPTY_STRING);

                        addTag(tagText, false);

                        editText.setText("");
                    } else {
                        showTagLimitMessage();
                    }

                } else {
                    mAdapter.getFilter().filter(editable.toString());
                    mAdapter.notifyDataSetChanged();

                    if (mAdapter.getItemCount() == 0) {
                        textViewAdd.setVisibility(VISIBLE);
                        recyclerView.setVisibility(GONE);
                        textViewAdd.setText(editable.toString());
                    } else {
                        textViewAdd.setVisibility(GONE);
                        recyclerView.setVisibility(VISIBLE);
                    }
                }

                if (editable.toString().isEmpty()) {
                    textViewAdd.setVisibility(GONE);
                    recyclerView.setVisibility(VISIBLE);
                }
            }
        });


    }

    private String convertSeparator(int separator) {
        String tagSeparator = "";
        switch (separator) {
            case 1:
                tagSeparator = TagSeparator.valueOf(TagSeparator.COMMA_SEPARATOR.name()).getValue();
                break;
            case 2:
                tagSeparator = TagSeparator.valueOf(TagSeparator.PLUS_SEPARATOR.name()).getValue();
                break;
            case 3:
                tagSeparator = TagSeparator.valueOf(TagSeparator.MINUS_SEPARATOR.name()).getValue();
                break;
            case 4:
                tagSeparator = TagSeparator.valueOf(TagSeparator.SPACE_SEPARATOR.name()).getValue();
                break;
            case 5:
                tagSeparator = TagSeparator.valueOf(TagSeparator.AT_SEPARATOR.name()).getValue();
                break;
            case 6:
                tagSeparator = TagSeparator.valueOf(TagSeparator.HASH_SEPARATOR.name()).getValue();
                break;
        }
        return tagSeparator;
    }

    /**
     * Add a Tag or Create a Tag.
     *
     * @param text It takes String and create a mode {@link TagModel}
     */
    private void addTag(String text, boolean isFromList) {
        if (text == null) {
            text = EMPTY_STRING;
        }

        TagModel model = new TagModel();
        model.setTagText(text);
        model.setFromList(isFromList);

        mTagList.add(model);

        addTagInView();
    }

    /**
     * Add recycler view if item list available.
     * first the item will be invisible.
     * Latter the item will add according user input.
     * If not matching item, the editor text will add in recycler view item temporary to select or add
     * Tag
     */
    private void addRecyclerView() {
        recyclerView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        textViewAdd.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textViewAdd.setGravity(Gravity.CENTER);
        textViewAdd.setPadding(4, 0, 4, 0);
        textViewAdd.setBackground(getResources().getDrawable(R.drawable.drawable_tag));

        GradientDrawable drawable = (GradientDrawable) textViewAdd.getBackground();
        drawable.setColor(tagBackgroundColor);

        textViewAdd.setTextColor(mTagTextColor);

        textViewAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTagList.size() < tagLimit) {
                    addTag(textViewAdd.getText().toString(), false);
                    textViewAdd.setText("");
                    textViewAdd.setVisibility(GONE);
                    recyclerView.setVisibility(VISIBLE);
                    editText.setText("");
                } else {
                    showTagLimitMessage();
                }

            }
        });

        this.addView(editText);
        if (mTagItemList != null) {
            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());

            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
            layoutManager.setFlexWrap(FlexWrap.WRAP);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(mAdapter);

            // set adapter click listener
            mAdapter.setTagClickListener(this);

            mAdapter.addItems(mTagItemList);

        }

        this.addView(recyclerView);

        this.addView(textViewAdd);
        textViewAdd.setVisibility(GONE);


        invalidate();
    }

    private void showTagLimitMessage() {
        if (mTagLimitMessage != null) {
            Toast.makeText(getContext(), mTagLimitMessage, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), DEFAULT_TAG_LIMIT_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetSelectTag(int position, String tagText) {
        // We get tag item. we have to add tag

        addTag(tagText, true);

        mAdapter.removeTagItem(position, tagText);
    }


    /*
     * User setter method
     * */


    /**
     * User can set hint of edit text
     *
     * @param hint This take only String. If it get null it will convert null to Empty String
     */
    public void setHint(String hint) {
        if (hint == null) {
            hint = EMPTY_STRING;
        }
        editText.setHint(hint);
    }

    /**
     * Add Tag separator within Defined Separator
     *
     * @param separator This is Tag separator. It contains only
     *                  few separator {@link TagSeparator}
     */
    public void addTagSeparator(Enum separator) {

        TagSeparator tagSeparator = TagSeparator.valueOf(separator.name());

        this.tagSeparator = tagSeparator.getValue();
        Log.d("TagTest", tagSeparator.getValue());

    }

    /**
     * User can set tag limit but never set zero
     *
     * @param limit Integer value
     */
    public void addTagLimit(int limit) {
        if (limit == 0) {
            limit = 1;
        }
        tagLimit = limit;
    }

    /**
     * User can set Integer color
     *
     * @param color Integer Color
     */
    public void setTagBackgroundColor(int color) {
        tagBackgroundColor = color;

        mAdapter.addTagBackgroundColor(tagBackgroundColor);
    }

    /**
     * User can set background as String color
     *
     * @param color String color code
     */
    public void setTagBackgroundColor(String color) {
        tagBackgroundColor = Color.parseColor(color);

        mAdapter.addTagBackgroundColor(tagBackgroundColor);
    }

    /**
     * User can set/ change tag text color
     *
     * @param color {@link Integer}
     */
    public void setTagTextColor(int color) {
        mTagTextColor = color;

        mAdapter.addTagTextColor(mTagTextColor);
    }

    /**
     * User can set/ change tag text color
     *
     * @param color {@link String}
     */
    public void setTagTextColor(String color) {
        mTagTextColor = Color.parseColor(color);

        mAdapter.addTagTextColor(mTagTextColor);
    }

    /**
     * User can modify default tag limit message
     *
     * @param message User defined message
     */
    public void setMaximumTagLimitMessage(String message) {
        if (TextUtils.isEmpty(message)) {
            mTagLimitMessage = DEFAULT_TAG_LIMIT_MESSAGE;
        } else {
            mTagLimitMessage = message;
        }
    }

    /**
     * User can change cross button as drawable
     *
     * @param crossDrawable It takes resources drawable
     */
    public void setCrossButton(Drawable crossDrawable) {
        mCrossDrawable = crossDrawable;
        mCrossBitmap = null;
    }

    /**
     * User can change cross button as bitmap
     *
     * @param crossBitmap It takes Bitmap
     */
    public void setCrossButton(Bitmap crossBitmap) {
        mCrossDrawable = null;
        mCrossBitmap = crossBitmap;
    }

    /**
     * User can notify when Tag item added or removed
     *
     * @param listener {@link TagItemListener}
     */
    public void initTagListener(TagItemListener listener) {
        mTagItemListener = listener;
    }

    /**
     * This is the main challenging part of
     * TagView. Tag list will show when user will write in editText
     * and user click will add a tag
     *
     * @param tagList List of String
     */
    public void setTagList(List<String> tagList) {
        if (tagList == null) {
            tagList = new ArrayList<>();
        }
        mTagItemList = tagList;

        addRecyclerView();
    }

    /**
     * User Can provide tag list as String array
     *
     * @param tagList It is string array
     */
    public void setTagList(String... tagList) {
        List<String> tempList = Arrays.asList(tagList);

        mTagItemList.addAll(tempList);

        addRecyclerView();
    }

    /**
     * User can get all selected Tag list
     *
     * @return {@link TagModel} of List
     */
    public List<TagModel> getSelectedTags() {
        return mTagList;
    }

}
