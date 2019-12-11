package com.example.recipe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.example.recipe.NotificationsHub.sendNotification;

public class EndOfRecepieReciver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String recpieName=intent.getStringExtra("r_name");
        sendNotification(context, recpieName+" is over", "Open RecipeApp to learn more it");
    }
}
