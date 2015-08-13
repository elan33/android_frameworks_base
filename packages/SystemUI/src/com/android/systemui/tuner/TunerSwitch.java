package com.android.systemui.tuner;

import android.content.Context;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.util.AttributeSet;

import com.android.systemui.tuner.TunerService.Tunable;

public class TunerSwitch extends SwitchPreference implements Tunable {

    public TunerSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToActivity() {
        super.onAttachedToActivity();
        TunerService.get(getContext()).addTunable(this, getKey());
    }

    @Override
    protected void onDetachedFromActivity() {
        TunerService.get(getContext()).removeTunable(this);
        super.onDetachedFromActivity();
    }

    @Override
    public void onTuningChanged(String key, String newValue) {
        setChecked(newValue != null && Integer.parseInt(newValue) != 0);
    }

    @Override
    protected boolean persistBoolean(boolean value) {
        Settings.Secure.putString(getContext().getContentResolver(), getKey(), value ? "1" : "0");
        return true;
    }

}
