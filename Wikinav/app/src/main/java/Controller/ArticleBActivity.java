package Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.wikinav.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import beans.Link;
import view.BHollder;


public class ArticleBActivity extends AppCompatActivity implements BHollder.OnlinkListener {
    /******************************************************/
    private ArrayList<String> allTitlesB, AllTitleB;
    /************************************************/
    private RecyclerView rv_articleB;
    /*******************************/
    private BHollder bHollder;
    /*******************************/
    private EditText ed_searchB;
    /******************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articleb);


        /************************************************************/
        rv_articleB = (RecyclerView)findViewById( R.id.rv_articleB );
        ed_searchB = (EditText) findViewById( R.id.ed_searchB );
        /*************************************************************/
        allTitlesB =new ArrayList<>();
        AllTitleB =new ArrayList<>();
        /**********************************************************/
        bHollder = new BHollder(AllTitleB,this);
        rv_articleB.setLayoutManager(new LinearLayoutManager(this));
        rv_articleB.setAdapter(bHollder);
        /*********************************************************/

        ed_searchB.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                   RafraichirEcran rafraichirEcran = new RafraichirEcran();
                    rafraichirEcran.execute("");
                } else {
                  RafraichirEcran rafraichirEcran = new RafraichirEcran();
                    rafraichirEcran.execute(ed_searchB.getText().toString());
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        /*********************************************/

        allTitlesB.addAll(Link.getInstance().getAllTitles());
       RafraichirEcran rafraichirEcran = new RafraichirEcran();
        rafraichirEcran.execute("");
    }

    @Override
    public void onClick(String articleB) {
        Link.getInstance().setBB(articleB);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }


    // le traitement des données en arriere plan avec un thread séparé

    public class RafraichirEcran extends AsyncTask<String, String, String> {

        ArrayList<String> allLinkClone =new ArrayList<>();


        @Override
        public String doInBackground(String... args)  {
            String text = args[0];

            if( text.length()!=0) {

                for(String it: allTitlesB){
                    if (it.contains(text)) {
                        allLinkClone.add(it);
                    }

                }
                Collections.sort(allLinkClone, new ComparatorStr(text));
            }
            else{

                allLinkClone= allTitlesB;
            }
            return null;
        }

        @Override
        public void onPostExecute(String result){
            AllTitleB.clear();
            AllTitleB.addAll(allLinkClone);
            bHollder.notifyDataSetChanged();
        }
    }

    // class Comparator
    public class ComparatorStr implements Comparator<String> {

        private final String StringRefrence;

        public ComparatorStr(String StringRefrence) {
            this.StringRefrence = StringRefrence;
        }

        @Override
        public int compare(String o1, String o2) {
            double d1 = o1.length();
            double d2 = o2.length();
            return Double.compare(d1, d2);

        }
    }








}
