package ru.SnowVolf.pcompiler.patch;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import ru.SnowVolf.pcompiler.App;
import ru.SnowVolf.pcompiler.R;
import ru.SnowVolf.pcompiler.util.Constants;

/**
 * Created by Snow Volf on 24.10.2017, 19:18
 */

public class PatchArray extends ArrayList<String>{
    private PatchBuilder.OnBuildListener mBuildListener;

    PatchArray(){
        mBuildListener = new PatchBuilder.OnBuildListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(App.getContext(), R.string.message_saved, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int index) {
                Toast.makeText(App.getContext(), String.format(Locale.ENGLISH, App.injectString(R.string.index_of_bounds_error), index, size()), Toast.LENGTH_LONG).show();
            }
        };
    }

    public void removeItemAt(int index){
        try {
            if (size() >= index) {
                Log.i(Constants.TAG, "Trying to remove item at index: " + index);
                remove(index);
            }
        } catch (PatchException ignored){}
    }

    public void addItemAt(int index, String s){
        try {
            if (size() >= index) {
                Log.i(Constants.TAG, "Trying add item at index: " + index);
                add(index, s);
                mBuildListener.onSuccess();
            } else mBuildListener.onError(index);
        } catch (Exception e){
            Toast.makeText(App.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}