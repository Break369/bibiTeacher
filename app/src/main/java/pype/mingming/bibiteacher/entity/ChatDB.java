package pype.mingming.bibiteacher.entity;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by mk on 2016/11/16.
 */
public class ChatDB extends BmobObject{
    private User friendID;
    private User userID;
    private List<ChatBmob> chatBmobList;
    private String userName;
    private String friendName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public User getFriendID() {
        return friendID;
    }

    public void setFriend(User friendID) {
        this.friendID = friendID;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public List<ChatBmob> getChatBmobList() {
        return chatBmobList;
    }

    public void setChatBmobList(List<ChatBmob> chatBmobList) {
        this.chatBmobList = chatBmobList;
    }
}
