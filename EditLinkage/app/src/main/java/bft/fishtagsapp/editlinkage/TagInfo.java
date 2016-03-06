package bft.fishtagsapp.editlinkage;

/**
 * Created by jamiecho on 3/6/16.
 */
public class TagInfo {
    String photo; //photo file path, if it exists.
    String tag; //tag info file path, if it exists
    String description; //concise, one-liner description of this info

    public TagInfo(String photo, String tag, String description){
        this.photo=photo;
        this.tag=tag;
        this.description=description;
    }
}
