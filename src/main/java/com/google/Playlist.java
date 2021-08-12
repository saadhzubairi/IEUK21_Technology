package com.google;

import java.util.ArrayList;
import java.util.HashMap;

public class Playlist {
    private HashMap<String,Video> videosMap = new HashMap<>();
    private ArrayList<Video> videos = new ArrayList<Video>();
    private String name;

    public Playlist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void addVid(Video add){
        this.videos.add(add);
        this.videosMap.put(add.getVideoId(),add);
    }

    public void removeVid(String videoid){
        videos.removeIf(i -> i.getVideoId().equals(videoid));
        videosMap.remove(videoid);
    }

    public void clear(){
        if(videos.size()>0) {
            for (String i : this.videosMap.keySet()) {
                videosMap.remove(i);
            }
            for (int i = 0; i < this.videos.size(); i++) {
                videos.remove(i);
            }
        }
    }

    public HashMap<String, Video> getVideosMap() {
        return videosMap;
    }

    @Override
    public String toString() {
        String str = "";
        if(videos.size()==0){
            str = "No videos here yet";
        }
        else{
            for(int i=0; i<videos.size();i++){
                if(i==videos.size()-1){
                    str = str + videos.get(i);
                }
                else{
                    str = str + videos.get(i) + "\n";
                }
            }
        }
        return "Showing playlist: " + name + "\n"
                + str;
    }

    public String toString(String pln) {
        String str = "";
        if(videos.size()==0){
            str = "No videos here yet";
        }
        else{
            for(int i=0; i<videos.size();i++){
                if(i==videos.size()-1){
                    str = str + videos.get(i);
                }
                else{
                    str = str + videos.get(i) + "\n";
                }
            }
        }
        return "Showing playlist: " + pln + "\n"
                + str;
    }
}
