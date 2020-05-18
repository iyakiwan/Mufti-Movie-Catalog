package com.muftialies.made.finalsubmission.notification;

import com.muftialies.made.finalsubmission.model.ShowItems;

import java.util.ArrayList;

public interface ResultCallback {
    void onSuccess(ArrayList<ShowItems> showItems);
    void onFailure(boolean answer);
}
