package Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wikinav.R;

import beans.Link;

public class NavigHistoryActivity extends AppCompatActivity implements View.OnClickListener {

     private Button bt_PlayAgain;
     private TextView tv_NavHist,tv_linksclicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navig_history);


        tv_NavHist =  (TextView) findViewById(R.id.tv_NavHist);
        tv_linksclicked =  (TextView) findViewById(R.id.tv_linksclicked);
        bt_PlayAgain= (Button) findViewById(R.id.bt_PlayAgain);
        bt_PlayAgain.setOnClickListener(this);


       tv_linksclicked.setText(Link.getInstance().getNbClicks()+"");
        for(String item: Link.getInstance().getHistNavigation())
        {
            tv_NavHist.append(item);
        }
    }


    @Override
    public void onClick(View v) {
        Link.getInstance().resetHistNavigation();
        Link.getInstance().resetNbCliks();
        Link.getInstance().setAA(null);
        Link.getInstance().setBB(null);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
