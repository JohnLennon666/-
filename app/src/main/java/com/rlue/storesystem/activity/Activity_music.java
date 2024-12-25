package com.rlue.storesystem.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rlue.storesystem.R;
import com.rlue.storesystem.service.MusicService;

public class Activity_music extends AppCompatActivity {

    private MusicService musicService;
    private boolean isServiceBound = false;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            musicService = binder.getService();
            isServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
            isServiceBound = false;
        }
    };

    private TextView textViewMusicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Button buttonStart = findViewById(R.id.button_start);
        Button buttonPause = findViewById(R.id.button_pause);
        Button buttonStop = findViewById(R.id.button_stop);
        textViewMusicName = findViewById(R.id.textview_song);

        buttonStart.setOnClickListener(view -> startMusicService());
        buttonPause.setOnClickListener(view -> pauseMusic());
        buttonStop.setOnClickListener(view -> stopMusicService());
    }

    private void startMusicService() {
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        textViewMusicName.setText("正在播放：流浪的小孩");
    }

    private void pauseMusic() {
        if (musicService != null) {
            musicService.toggleMusicPlayback();
        }
        textViewMusicName.setText("暂停播放：流浪的小孩");
    }

    private void stopMusicService() {
        if (isServiceBound) {
            unbindService(serviceConnection);
            isServiceBound = false;
        }
        Intent intent = new Intent(this, MusicService.class);
        stopService(intent);
        textViewMusicName.setText("停止播放：流浪的小孩");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMusicService();
    }
}