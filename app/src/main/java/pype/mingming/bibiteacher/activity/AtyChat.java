package pype.mingming.bibiteacher.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.ValueEventListener;
import pype.mingming.bibiteacher.R;
import pype.mingming.bibiteacher.adapter.ChatAdapter;
import pype.mingming.bibiteacher.adapter.MKOrderAdapter;
import pype.mingming.bibiteacher.entity.BmobData;
import pype.mingming.bibiteacher.entity.ChatBmob;
import pype.mingming.bibiteacher.entity.ChatDB;
import pype.mingming.bibiteacher.entity.PluralistBmob;
import pype.mingming.bibiteacher.entity.UnOrderBmob;
import pype.mingming.bibiteacher.entity.User;
import pype.mingming.bibiteacher.myhomepage.TopBar;

/**聊天功能
 * Created by mk on 2016/11/14.
 */
public class AtyChat extends Activity{
    private ChatAdapter adapter;
    private ListView lv;
    private User userID;
    private User friendID;
    private Button btnChatSend;
    private EditText etChat;
    private BmobData bmobData = new BmobData();
    private ChatBmob chatBmob;
    private TopBar myChatTopBar;
    private BmobRealTimeData data = new BmobRealTimeData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);
        userID = (User) getIntent().getSerializableExtra("userID");
        friendID = (User) getIntent().getSerializableExtra("friendID");
        lv = (ListView) findViewById(R.id.chatList);
        btnChatSend = (Button) findViewById(R.id.btnChatSend);
        etChat = (EditText) findViewById(R.id.etChat);
        myChatTopBar = (TopBar) findViewById(R.id.myChatTopBar);
        //初始化界面
        myChatTopBar.setTvTitle(friendID.getUsername());
        bmobData.setChatOnChlickListener(this, userID, friendID, new BmobData.chatOnClickListener() {
            @Override
            public void getChatList(List<ChatDB> list) {
                if (list.size()!=0){
                    adapter = new ChatAdapter(AtyChat.this,userID,list.get(0).getChatBmobList());
                    lv.setAdapter(adapter);
                    lv.setSelection(lv.getCount()-1);//默认滚到最后
                }
            }
        });
        //标题监听
        myChatTopBar.setOnTopBarClickListener(new TopBar.topbarClickListener() {
            @Override
            public void leftButtonOnClick() {
                onBackPressed();
            }

            @Override
            public void rightButtonOnClick() {
                Toast.makeText(AtyChat.this,"待完善。。",Toast.LENGTH_SHORT).show();
            }
        });

        //消息发送
        btnChatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String content = etChat.getText().toString();
                Date date = new Date();
                chatBmob = new ChatBmob(userID,content,date);
                if (!content.equals("")){
                    //提交处理，进行数据的上传
                    bmobData.setChatOnChlickListener(AtyChat.this, userID, friendID, new BmobData.chatOnClickListener() {
                        @Override
                        public void getChatList(List<ChatDB> list) {
                            if (list.size()==0){
                                final ChatDB chatDB = new ChatDB();
                                chatDB.setUserID(userID);
                                chatDB.setFriend(friendID);
                                chatDB.setUserName(userID.getUsername());
                                chatDB.setFriendName(friendID.getUsername());
                                List<ChatBmob> chatBmobList = new ArrayList<ChatBmob>();
                                chatBmobList.add(chatBmob);
                                chatDB.setChatBmobList(chatBmobList);
                                chatDB.save(AtyChat.this, new SaveListener() {
                                    @Override
                                    public void onSuccess() {
                                        bmobData.setChatOnChlickListener(AtyChat.this, userID, friendID, new BmobData.chatOnClickListener() {
                                            @Override
                                            public void getChatList(List<ChatDB> list) {
                                                if (list.size()!=0){
                                                    adapter = new ChatAdapter(AtyChat.this,userID,list.get(0).getChatBmobList());
                                                    lv.setAdapter(adapter);
                                                    lv.setSelection(lv.getCount()-1);//默认滚到最后
                                                }
                                            }
                                        });
                                        UnOrderBmob unOrderBmob = new UnOrderBmob();
                                        unOrderBmob.setEmployer(userID);
                                        unOrderBmob.setEmployee(friendID);
                                        unOrderBmob.setChatDB(chatDB);
                                        unOrderBmob.save(AtyChat.this, new SaveListener() {
                                            @Override
                                            public void onSuccess() {
                                                Log.i("数据更新","数据保存成功");
                                            }

                                            @Override
                                            public void onFailure(int i, String s) {
                                                Toast.makeText(AtyChat.this,"保存失败",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        etChat.setText("");
                                    }
                                    @Override
                                    public void onFailure(int i, String s) {
                                        Toast.makeText(AtyChat.this,"保存失败",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else {
                                list.get(0).add("chatBmobList",chatBmob);
                                list.get(0).update(AtyChat.this, new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        bmobData.setChatOnChlickListener(AtyChat.this, userID, friendID, new BmobData.chatOnClickListener() {
                                            @Override
                                            public void getChatList(List<ChatDB> list) {
                                                if (list.size()!=0){
                                                    adapter = new ChatAdapter(AtyChat.this,userID,list.get(0).getChatBmobList());
                                                    lv.setAdapter(adapter);
                                                    lv.setSelection(lv.getCount()-1);//默认滚到最后

                                                }
                                            }
                                        });
                                        etChat.setText("");
                                        Log.i("数据更新","这是etchat");
                                    }
                                    @Override
                                    public void onFailure(int i, String s) {
                                        Toast.makeText(AtyChat.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }else {
                    Snackbar.make(v,"发送内容不能为空",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        init();
    }
    //数据库实时同步
    public void init(){
        data.start(this, new ValueEventListener() {
            @Override
            public void onConnectCompleted() {
                if(data.isConnected()){
                    data.subTableUpdate("ChatDB");
                }
            }
            @Override
            public void onDataChange(JSONObject jsonObject) {
                if (BmobRealTimeData.ACTION_UPDATETABLE.equals(jsonObject.optString("action"))) {
                    JSONObject data = jsonObject.optJSONObject("data");
                    String userName = data.optString("userName");
                    String friendName = data.optString("friendName");
                    if (userName.equals(userID.getUsername()) && friendName.equals(friendID.getUsername())) {
                        myChatTopBar.setTvTitle(friendID.getUsername());
                        bmobData.setChatOnChlickListener(AtyChat.this, userID, friendID, new BmobData.chatOnClickListener() {
                            @Override
                            public void getChatList(List<ChatDB> list) {
                                if (list.size()!=0){
                                    adapter = new ChatAdapter(AtyChat.this,userID,list.get(0).getChatBmobList());
                                    lv.setAdapter(adapter);
                                    lv.setSelection(lv.getCount()-1);//默认滚到最后
                                }
                            }
                        });
                        Log.i("数据更新","更新了"+userName+friendName);
                    }
                }
            }
        });
    }
}
