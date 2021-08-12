package com.google;

import java.lang.reflect.Array;
import java.util.*;
import java.math.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private nowPlaying nowplay = null;
  private HashMap<String, Playlist> playlists = new HashMap<>();

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    HashMap<String, Video> vids = videoLibrary.getLib();
    ArrayList<String> keys = new ArrayList<>(vids.keySet());
    Collections.sort(keys);
    System.out.println("Here's a list of all available videos:");
    for (int i=0; i<vids.size(); i++ ) {
      Video vid = vids.get(keys.get(i));
      StringBuilder tags = new StringBuilder();
      for (String t:vid.getTags() ) {
        tags.append(t).append(" ");
      }
      System.out.println(vid);
    }
  }

  public void playVideo(String videoId) {

    HashMap<String, Video> vids = videoLibrary.getLib();
    ArrayList<String> keys = new ArrayList<>(vids.keySet());


    if (keys.contains(videoId)){

      if(videoLibrary.getVideo(videoId).isFlag()){
        System.out.println("Cannot play video: Video is currently flagged (reason: "+videoLibrary.getVideo(videoId).getFlag()+")");
      }
      else{
        if (nowplay == null){
          nowplay = new nowPlaying(vids.get(videoId), true);
          System.out.println("Playing video: " + nowplay.getVideo().getTitle());
        }
        else{
          System.out.println("Stopping video: " + nowplay.getVideo().getTitle());
          nowplay = new nowPlaying(vids.get(videoId),true);
          System.out.println("Playing video: " + nowplay.getVideo().getTitle());
        }
      }
    }
    else{
      System.out.println("Cannot play video: Video does not exist");
    }
  }

  public void stopVideo() {
    if (nowplay == null){
      System.out.println("Cannot stop video: No video is currently playing");
    }
    else {
      System.out.println("Stopping video: " + nowplay.getVideo().getTitle());
      nowplay = null;
    }
  }

  public void playRandomVideo() {
    boolean allFlag = true;
    for(Video vid : videoLibrary.getVideos()){
      if(!vid.isFlag()){
        allFlag = false;
        break;
      }
    }
    if(allFlag){
      System.out.println("No videos available");
    }
    else{
      while (true){
        int a = (int)(Math.random()*videoLibrary.getVideos().size());
        HashMap<String, Video> vids = videoLibrary.getLib();
        ArrayList<String> keys = new ArrayList<>(vids.keySet());
        String id = keys.get(a);
        if(!videoLibrary.getVideo(id).isFlag()){
          playVideo(id);
          break;
        }
      }
    }
  }

  public void pauseVideo() {
    if (nowplay == null){
      System.out.println("Cannot pause video: No video is currently playing");
    }
    else{
      if (nowplay.isPlaying()){
        System.out.println("Pausing video: " + nowplay.getVideo().getTitle());
        nowplay.setPlaying(false);
      }
      else{
        System.out.println("Video already paused: " + nowplay.getVideo().getTitle());
      }
    }
  }

  public void continueVideo() {
    if (nowplay == null){
      System.out.println("Cannot continue video: No video is currently playing");
    }
    else{
      if(nowplay.isPlaying()){
        System.out.println("Cannot continue video: Video is not paused");
      }
      else{
        System.out.println("Continuing video: Amazing Cats");
        nowplay.setPlaying(true);
      }
    }
  }

  public void showPlaying() {
    if(nowplay == null){
      System.out.println("No video is currently playing");
    }
    else{
      if(nowplay.isPlaying()){
        System.out.println("Currently playing: " + nowplay.getVideo().toString());
      }
      else{
        System.out.println("Currently playing: " + nowplay.getVideo().toString() + " - PAUSED");
      }
    }
  }

  public void createPlaylist(String playlistName) {
    boolean go = true;
    for(String comp: playlists.keySet()){
      String s = comp.toLowerCase();
      if(s.equals(playlistName.toLowerCase())) {
        System.out.println("Cannot create playlist: A playlist with the same name already exists");
        go = false;
        break;
      }
    }
    if(go){
      System.out.println("Successfully created new playlist: " + playlistName);
      playlists.put(playlistName.toLowerCase(),new Playlist(playlistName.toLowerCase()));
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId)
  {
    if(playlists.containsKey(playlistName.toLowerCase())){
      if(videoLibrary.getLib().containsKey(videoId)){
        if(!videoLibrary.getVideo(videoId).isFlag()){
          if(!playlists.get(playlistName.toLowerCase()).getVideosMap().containsKey(videoId)){
            playlists.get(playlistName.toLowerCase()).addVid(videoLibrary.getVideo(videoId));
            System.out.println("Added video to " + playlistName + ": " + playlists.get(playlistName.toLowerCase()).getVideosMap().get(videoId).getTitle());}
          else{System.out.println("Cannot add video to "+ playlistName +": Video already added"); } }
        else { System.out.println("Cannot add video to "+ playlistName +": Video is currently flagged (reason: "+videoLibrary.getVideo(videoId).getFlag()+")"); } }
      else{System.out.println("Cannot add video to "+ playlistName +": Video does not exist");} }
    else{System.out.println("Cannot add video to "+ playlistName +": Playlist does not exist");}
  }

  public void showAllPlaylists() {
    if(playlists.size()==0){
      System.out.println("No playlists exist yet");
    }
    else{
      System.out.println("Showing all playlists:");
      ArrayList<String> pls = new ArrayList<>(playlists.keySet());
      Collections.sort(pls);
      for (String i: pls) {
        System.out.println(i);
      }
    }
  }

  public void showPlaylist(String playlistName) {
    boolean ispl = false;
    for(String i: playlists.keySet()){
      if(i.toLowerCase().equals(playlistName.toLowerCase())){
        ispl = true;
        break;
      }
    }

    if(ispl){
      System.out.println(playlists.get(playlistName.toLowerCase()).toString(playlistName));
    }

    else {
      System.out.println("Cannot show playlist "+playlistName+": Playlist does not exist");
    }

  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    //if pl exists:
    if(playlists.containsKey(playlistName.toLowerCase())){
      if(videoLibrary.getLib().containsKey(videoId)){
        if(playlists.get(playlistName.toLowerCase()).getVideosMap().containsKey(videoId)){
          playlists.get(playlistName.toLowerCase()).removeVid(videoId);
          System.out.println("Removed video from "+playlistName+": " + videoLibrary.getVideo(videoId).getTitle());}
        else{System.out.println("Cannot remove video from "+ playlistName +": Video is not in playlist"); } }
      else{System.out.println("Cannot remove video from "+ playlistName +": Video does not exist");} }
    else{System.out.println("Cannot remove video from "+ playlistName +": Playlist does not exist");}
  }

  public void clearPlaylist(String playlistName) {

    if(playlists.containsKey(playlistName.toLowerCase())){
      if(playlists.get(playlistName.toLowerCase()).getVideos().size()>0) {
        for (String i : playlists.get(playlistName.toLowerCase()).getVideosMap().keySet()) {
          playlists.get(playlistName.toLowerCase()).getVideosMap().remove(i);
        }
        for (int i = 0; i < playlists.get(playlistName.toLowerCase()).getVideos().size(); i++) {
          playlists.get(playlistName.toLowerCase()).getVideos().remove(i);
        }
      }
      System.out.println("Successfully removed all videos from " + playlistName);
    }
    else System.out.println("Cannot clear playlist "+ playlistName +": Playlist does not exist");

  }

  public void deletePlaylist(String playlistName) {
    boolean f = false;
    for(String i: playlists.keySet()){
      if(i.toLowerCase().equals(playlistName.toLowerCase())){
        playlists.remove(playlistName);
        System.out.println("Deleted playlist: "+playlistName);
        f = true;
      }
    }
    if(!f) System.out.println("Cannot delete playlist "+ playlistName +": Playlist does not exist");
  }

  public void searchVideos(String searchTerm) {
    List<Video> search_results = new ArrayList<>();

    for (Video video: videoLibrary.getVideos()) {
      if(video.getTitle().toLowerCase().contains(searchTerm.toLowerCase())){
        if(!video.isFlag()){
          search_results.add(video);
        }
      }
    }
    if(search_results.size()>0){
      Collections.sort(search_results, Comparator.comparing(Video::getTitle));
      int i = 1;
      System.out.println("Here are the results for "+searchTerm+":");
      for (Video video: search_results)
      {
        System.out.println(i+") " + video);
        i++;
      }
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
      System.out.println("If your answer is not a valid number, we will assume it's a no.");
      Scanner scanner = new Scanner(System.in);
      String in = scanner.nextLine();
      try {
        playVideo(search_results.get(Integer.parseInt(in)-1).getVideoId());
      }
      catch (Exception e){
        return;
      }
    }
    else System.out.println("No search results for " + searchTerm);

  }

  public void searchVideosWithTag(String videoTag) {
    List<Video> search_results = new ArrayList<>();
    for (Video win: videoLibrary.getVideos()) {
      for (String s: win.getTags()) {
        if(s.equals(videoTag.toLowerCase())){
          if(!win.isFlag()) search_results.add(win);
        }
        break;
      }
    }

    if(search_results.size()>0){
      search_results.sort(Comparator.comparing(Video::getTitle));
      int i = 1;
      System.out.println("Here are the results for "+videoTag+":");
      for (Video video: search_results)
      {
        System.out.println(i+") " + video);
        i++;
      }
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
      System.out.println("If your answer is not a valid number, we will assume it's a no.");

      Scanner scanner = new Scanner(System.in);
      String in = scanner.nextLine();
      try {
        playVideo(search_results.get(Integer.parseInt(in)-1).getVideoId());
      }
      catch (Exception e){
        return;
      }

    }
    else System.out.println("No search results for " + videoTag);
  }

  public void flagVideo(String videoId) {

    flagg(1,videoId,"");
  }

  public void flagVideo(String videoId, String reason) {
    flagg(2,videoId,reason);
  }

  public void allowVideo(String videoId) {
    if(videoLibrary.getLib().containsKey(videoId)){
      if(videoLibrary.getVideo(videoId).isFlag()) {
        videoLibrary.getVideo(videoId).de_flag_it();
        System.out.println("Successfully removed flag from video: "+videoLibrary.getVideo(videoId).getTitle());
      }
      else System.out.println("Cannot remove flag from video: Video is not flagged"); }
    else System.out.println("Cannot remove flag from video: Video does not exist");
  }

  public void flagg(int cond, String videoId, String reason){
    if(!(nowplay == null)){
      if(nowplay.getVideo().getVideoId().equals(videoId)){
        stopVideo();
      }
    }
    if(videoLibrary.getLib().containsKey(videoId)){
      if(!videoLibrary.getVideo(videoId).isFlag()){
        if(cond==1){
          videoLibrary.getVideo(videoId).flag_it();
          System.out.println("Successfully flagged video: "+videoLibrary.getVideo(videoId).getTitle()+" (reason: Not supplied)");
        }
        else if (cond ==2){
          videoLibrary.getVideo(videoId).flag_it(reason);
          System.out.println("Successfully flagged video: "+videoLibrary.getVideo(videoId).getTitle()+" (reason: "+reason+")");
        }
      }

      else{
        System.out.println("Cannot flag video: Video is already flagged");
      }
    }
    else{
      System.out.println("Cannot flag video: Video does not exist");
    }
  }
}
