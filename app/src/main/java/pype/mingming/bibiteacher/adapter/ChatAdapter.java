package pype.mingming.bibiteacher.adapter;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pype.mingming.bibiteacher.R;
import pype.mingming.bibiteacher.activity.BaseActivity;
import pype.mingming.bibiteacher.entity.ChatBmob;
import pype.mingming.bibiteacher.entity.User;
import pype.mingming.bibiteacher.ui.CircleImageView;

/**
 * Created by mk on 2016/11/14.
 */
public class ChatAdapter extends BaseAdapter {

    private List<ChatBmob> chatBmobList = new ArrayList<ChatBmob>();
    private LinearLayout layout;
    private Context context;
    private User userID;

    public ChatAdapter(Context context,User userID,List<ChatBmob> chatBmobList){
        this.context = context;
        this.userID = userID;
        this.chatBmobList = chatBmobList;
    }
    @Override
    public int getCount() {
        return chatBmobList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatBmobList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        layout = (LinearLayout) inflater.inflate(R.layout.activity_chat,null);
        CircleImageView leftImageView = (CircleImageView) layout.findViewById(R.id.chatLeftImage);
        CircleImageView rightImageView = (CircleImageView) layout.findViewById(R.id.chatRightImage);
        TextView tvLeftContent = (TextView) layout.findViewById(R.id.tvLeftContent);
        TextView tvRightContent = (TextView) layout.findViewById(R.id.tvRightContent);
        LinearLayout leftLayout = (LinearLayout) layout.findViewById(R.id.friendLayout);
        LinearLayout rightLayout = (LinearLayout) layout.findViewById(R.id.userLayout);

        ChatBmob chatBmob = chatBmobList.get(position);
        if (!chatBmob.getUserID().getUsername().equals(userID.getUsername())){
            Log.i("发言人",chatBmob.getUserID().getUsername()+"用户id1"+userID.getUsername());
            leftLayout.setVisibility(View.VISIBLE);
            rightLayout.setVisibility(View.GONE);
            leftImageView.setImageResource(R.drawable.girl1);
            tvLeftContent.setText(chatBmob.getContent());
        }else {
            Log.i("发言人",chatBmob.getUserID().getUsername()+"用户id2"+userID.getUsername());
            rightLayout.setVisibility(View.VISIBLE);
            leftLayout.setVisibility(View.GONE);
            tvRightContent.setText(chatBmob.getContent());
            rightImageView.setImageResource(R.drawable.gril3);
        }

        return layout;
    }
}
