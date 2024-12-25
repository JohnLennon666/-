package com.rlue.storesystem.service;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.rlue.storesystem.R;

public class MusicService extends Service {

    private final IBinder binder = new MusicBinder();
    private MediaPlayer mediaPlayer;
    private boolean isMusicPlaying;

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer = MediaPlayer.create(this, R.raw.children);
        mediaPlayer.setOnCompletionListener(mp -> stopSelf());
        mediaPlayer.start();
        isMusicPlaying = true;
        return START_STICKY;
    }


    public void toggleMusicPlayback() {
        if (isMusicPlaying) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
        isMusicPlaying = !isMusicPlaying;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}