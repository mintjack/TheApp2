package com.mintellany.theapp2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.mintellany.theapp2.WakeLock.WakeLockException;


public class ReceiverAlarm extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent recvIntent) {
    Uri alarmUri = recvIntent.getData();
    long alarmId = AlarmUtil.alarmUriToId(alarmUri);

    try {
      WakeLock.acquire(context, alarmId);
    } catch (WakeLockException e) {
      if (AppSettings.isDebugMode(context)) {
        throw new IllegalStateException(e.getMessage());
      }
    }

    Intent notifyService = new Intent(context, NotificationService.class);
    notifyService.setData(alarmUri);

    context.startService(notifyService);
  }
}
