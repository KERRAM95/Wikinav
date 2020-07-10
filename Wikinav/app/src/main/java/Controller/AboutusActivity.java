package Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wikinav.R;

public class AboutusActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_about;
    private Button bt_start;
    private String about="M2-TTT-Groupe Reseaux\n\nMembre de Groupe de projet:\n" +
            "\t> KERRAM Mourad\n\t> KHIAT Hayet\n\t>MBODJI Arame";
    private String about1="\n\n\tpropos du projet Wikinav\n\n";
    private String about3="\t\t\t\tLe projet Wikinav consiste à" +
            " implanter un jeu sous Android dont le principe réside dans la découverte " +
            "de plus courts chemins afin de se rendre d'un article Wikipedia à un autre" +
            " article Wikipedia en suivant des liens hypertextes.\n";


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        tv_about = (TextView) findViewById(R.id.tv_aboutus);
        bt_start =(Button) findViewById(R.id.bt_start);
        bt_start.setOnClickListener(this);


        tv_about.setText(about+about1+about3);

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
