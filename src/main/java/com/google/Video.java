package com.google;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video. */
class Video {

  private final String title;
  private final String videoId;
  private final List<String> tags;
  private String flag;
  private boolean isFlag;

  Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);
    this.isFlag = false;
  }

  /** Returns the title of the video. */
  String getTitle() {
    return title;
  }

  /** Returns the video id of the video. */
  String getVideoId() {
    return videoId;
  }

  /** Returns a readonly collection of the tags of the video. */
  List<String> getTags() {
    return tags;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public boolean isFlag() {
    return isFlag;
  }

  public void set_isFlag(boolean flag) {
    isFlag = flag;
  }

  public void flag_it(String flag){
    this.setFlag(flag);
    this.set_isFlag(true);
  }

  public void flag_it(){
    this.setFlag("Not supplied");
    this.set_isFlag(true);
  }

  public void de_flag_it(){
    this.setFlag("");
    this.set_isFlag(true);
  }

  @Override
  public String toString() {
    StringBuilder tags = new StringBuilder();
    for (String t:this.getTags() ) {
      tags.append(t).append(" ");
    }
    String ret;
    if(this.isFlag){
      ret = this.getTitle() + " (" + this.getVideoId()+ ") [" + tags.toString().stripTrailing()+ "] - FLAGGED (reason: " + this.getFlag() + ")";
    }
    else{
      ret = this.getTitle() + " (" + this.getVideoId()+ ") [" + tags.toString().stripTrailing()+ "]";
    }
    return ret;
  }
}