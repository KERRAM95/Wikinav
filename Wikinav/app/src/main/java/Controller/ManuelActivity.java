package Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.example.wikinav.R;

import beans.Link;

public class ManuelActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_start;
    private TextView tv_explication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manuel);

        bt_start=(Button)findViewById(R.id.bt_start);
        bt_start.setOnClickListener(this);
        tv_explication=(TextView) findViewById(R.id.tv_explication);

        String inst1="    <div style=\"max-width:55em; background:lightyellow\">\n" +
                "      <div style=\"background:DarkKhaki\">\n" +
                "         <h1>&nbsp;Wikinav</h1>\n" +
                "      </div>\n" +
                "      <div style=\"margin:20\">\n" +
                "      <p>\n" +
                "         This game is easy and fun:\n" +
                "         <ul>\n" +
                "            <li> you choose two Wikipedia articles.</li>\n" +
                "            <li> Starting from the first article, your goal is to reach the second one, exclusively by following links in the articles you encounter. (For the articles you are given this is always possible.)</li>\n" +
                "            <li> Links you can take are colored like <a>this</a>.</li>\n" +
                "            <li> Of course, it's more fun if you try to be as quick as possible...</li>\n" +
                "            <li> Next to wasting some precious time and learning interesting yet useless Wikipedia facts </li>\n" +
                "         </ul>\n" +
                "      </p>\n" +
                "      </div>\n" +
                "   </div>\n";



        tv_explication.setText(Html.fromHtml(inst1));


    }

    @Override
    public void onClick(View v) {

        Link.getInstance().setStat(true);
        finish();
    }
}
