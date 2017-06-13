package pype.mingming.bibiteacher.entity;

import android.provider.ContactsContract;

import java.util.Date;

/**
 * Created by mk on 2016/11/14.
 */
public class ChatBmob {
    private String content;
    private Date time;
    private User userID;//信息发送者

    public ChatBmob(User userID,String content,Date time){
        this.content = content;
        this.time = time;
        this.userID = userID;

    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
