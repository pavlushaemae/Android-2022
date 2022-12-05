// IMediaAidlInterface.aidl
package com.itis.androidcourse;

// Declare any non-default types here with import statements

import com.itis.androidcourse.model.Song;

interface IMediaAidlInterface {
    void playMusic();
    void pauseMusic();
    int setMusic(in Song song) ;
    void setMusicFromBundle(in Bundle bundle);
    int getCurrentPosition();
    void seekTo(int pos);
    void playPrev();
    void playNext();
    void stopMusic();
}