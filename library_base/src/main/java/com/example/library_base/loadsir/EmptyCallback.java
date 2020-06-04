package com.example.library_base.loadsir;


import com.example.library_base.R;
import com.kingja.loadsir.callback.Callback;

public class EmptyCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.base_layout_empty;
    }
}
