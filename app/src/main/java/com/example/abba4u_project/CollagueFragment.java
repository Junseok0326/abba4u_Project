/*
    Student : 1544021 서준석 - 스티커뷰와 포토뷰 라이브러리를 이용하여 콜라주 프레그먼트 구현
 */
package com.example.abba4u_project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.DeleteIconEvent;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.FlipHorizontallyEvent;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.TextSticker;
import com.xiaopo.flying.sticker.ZoomIconEvent;

import java.util.Arrays;

import static com.example.abba4u_project.Module.staticData.CutBitmap;
import static com.example.abba4u_project.Module.staticData.MainBitmap;
import static com.example.abba4u_project.Module.staticData.SelectBitmap;

public class CollagueFragment extends Fragment implements View.OnClickListener{
    public CollagueFragment(){}
    public static StickerView stickerView;
    TextSticker sticker;
    private Button btnReset,btnReplace,btnRemove,btnRemoveAll,btnLock, btnLoad, btnModify,btnAdd,btnSave;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.collague_fragment,container,false);
        stickerView = (StickerView) layout.findViewById(R.id.sticker_view);
        BitmapStickerIcon deleteIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getActivity(),
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_close_white_18dp),
                BitmapStickerIcon.LEFT_TOP);
        deleteIcon.setIconEvent(new DeleteIconEvent());

        BitmapStickerIcon zoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getActivity(),
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_scale_white_18dp),
                BitmapStickerIcon.RIGHT_BOTOM);
        zoomIcon.setIconEvent(new ZoomIconEvent());

        BitmapStickerIcon flipIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getActivity(),
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_flip_white_18dp),
                BitmapStickerIcon.RIGHT_TOP);
        flipIcon.setIconEvent(new FlipHorizontallyEvent());
        BitmapStickerIcon heartIcon =
                new BitmapStickerIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_favorite_white_24dp),
                        BitmapStickerIcon.LEFT_BOTTOM);
        heartIcon.setIconEvent(new HelloIconEvent());
        stickerView.setIcons(Arrays.asList(deleteIcon, zoomIcon, flipIcon, heartIcon));

        stickerView.setBackgroundColor(Color.WHITE);
        stickerView.setLocked(false);
        stickerView.setConstrained(true);

        sticker = new TextSticker(getActivity());

        sticker.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.sticker_transparent_background));
        sticker.setText("Hello, world!");
        sticker.setTextColor(Color.BLACK);
        sticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        sticker.resizeText();

        stickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerAdded(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerClicked(@NonNull Sticker sticker) {
                if (sticker instanceof TextSticker) {
                    ((TextSticker) sticker).setTextColor(Color.RED);
                    stickerView.replace(sticker);
                    stickerView.invalidate();
                }
                SelectBitmap=((BitmapDrawable)stickerView.getCurrentSticker().getDrawable()).getBitmap();
            }

            @Override
            public void onStickerDeleted(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerDragFinished(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerZoomFinished(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerFlipped(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerDoubleTapped(@NonNull Sticker sticker) {

            }
        });
        setBtn(layout);
        return layout;
    }

    private void loadSticker() {
        if(CutBitmap!=null) {
            Drawable drawable = new BitmapDrawable(CutBitmap);
            stickerView.addSticker(new DrawableSticker(drawable));
        }
    }
    public void testReplace(View view) {
        if (stickerView.replace(sticker)) {
            Toast.makeText(getActivity(), "Replace Sticker successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Replace Sticker failed!", Toast.LENGTH_SHORT).show();
        }
    }

    public void testLock(View view) {
        stickerView.setLocked(!stickerView.isLocked());
    }

    public void testRemove(View view) {
        if (stickerView.removeCurrentSticker()) {
            Log.i("선택된 스티커", String.valueOf(stickerView.removeCurrentSticker()));
            Toast.makeText(getActivity(), "Remove current Sticker successfully!", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(getActivity(), "Remove current Sticker failed!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void testRemoveAll(View view) {
        stickerView.removeAllStickers();
    }


    public void testLoad(View view) {
//        final TextSticker sticker = new TextSticker(getActivity());
//        sticker.setText("Hello, world!");
//        sticker.setTextColor(Color.BLUE);
//        sticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
//        sticker.resizeText();
//
//        stickerView.addSticker(sticker);
        Intent intent = new Intent(getActivity().getApplicationContext(),GetImageActivity.class);

        startActivity(intent);
    }
    public void testModify(View view){
        if(SelectBitmap == null){
            Toast.makeText(getActivity(), "선택된 스티커가 없습니다.", Toast.LENGTH_SHORT).show();
        }else{
            testLoad(view);
        }
    }
    public void stickerAdd(View view){
        if(SelectBitmap == null){
            Toast.makeText(getActivity(), "선택된 스티커가 없습니다.", Toast.LENGTH_SHORT).show();
        }else{

            stickerView.addSticker(new DrawableSticker(new BitmapDrawable(SelectBitmap)));

        }
    }

    public void SaveImage(View v)
    {
        //결과를 저장하는 비트맵
        Bitmap bitmap=stickerView.createBitmap();
    }
    private  void setBtn(View v){

        btnLoad = v.findViewById(R.id.btnAdd2);
        btnReplace = v.findViewById(R.id.btnReplace);
        btnRemove = v.findViewById(R.id.btnRemove);
        btnRemoveAll = v.findViewById(R.id.btnRemoveAll);
        btnLock = v.findViewById(R.id.btnLock);
        btnModify = v.findViewById(R.id.btnModify);
        btnAdd = v.findViewById(R.id.btnAdd);
        btnSave=v.findViewById(R.id.btnSave);

        btnModify.setOnClickListener(this);
        btnLoad.setOnClickListener(this);
        btnLock.setOnClickListener(this);
        btnLoad.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        btnRemoveAll.setOnClickListener(this);
        btnReplace.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLoad:
                loadSticker();
                break;
            case R.id.btnAdd2:
                testLoad(v);
                break;
            case R.id.btnRemove:
                testRemove(v);
                break;
            case R.id.btnRemoveAll:
                testRemoveAll(v);
                break;
            case R.id.btnReplace:
                testReplace(v);
                break;
            case R.id.btnLock:
                testLock(v);
                break;
            case R.id.btnModify:
                testModify(v);
                break;
            case R.id.btnAdd:
                stickerAdd(v);
                break;
            case R.id.btnSave:
                SaveImage(v);
                break;
        }
    }

}
