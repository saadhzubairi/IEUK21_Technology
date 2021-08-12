package com.google;

public class nowPlaying {
    private Video video;
    private boolean isPlaying; //PAUSE FALSE PLAY TRUE

    public nowPlaying(Video video, boolean isPlaying) {
        this.video = video;
        this.isPlaying = isPlaying;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    @Override
    public String toString() {
        return "nowPlaying{" +
                "video=" + video +
                ", isPlaying=" + isPlaying +
                '}';
    }
}
