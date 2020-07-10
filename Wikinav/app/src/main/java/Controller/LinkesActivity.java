package Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.example.wikinav.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import beans.Link;
import view.LinksHollder;

public class LinkesActivity extends AppCompatActivity implements LinksHollder.OnlinkListener {

    /******************************************************/
    private ArrayList<String> allLinks,AllLink;
    /***************************************************/

    private RecyclerView rv_links;
    private LinksHollder linksHollder;
    private EditText ed_linksReaserch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkes);

        /************************************************************/
        rv_links = (RecyclerView)findViewById( R.id.rv_links );
        ed_linksReaserch = (EditText) findViewById( R.id.ed_linksReaserch );
        /*************************************************************/

        /*************************************************************/
        AllLink =new ArrayList<>();
        allLinks = new ArrayList<>();
        /**************************************************************/
        linksHollder = new LinksHollder(AllLink,this);
        rv_links.setLayoutManager(new LinearLayoutManager(this));
        rv_links.setAdapter(linksHollder);
        /******************************************************/
        ed_linksReaserch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    RafraichirEcran rafraichirEcran = new RafraichirEcran();
                    rafraichirEcran.execute("");
                } else {
                    RafraichirEcran rafraichirEcran = new  RafraichirEcran();
                    rafraichirEcran.execute(ed_linksReaserch.getText().toString());
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


        allLinks.addAll(Link.getInstance().getAllArticleLinks());
        RafraichirEcran rafraichirEcran = new RafraichirEcran();
        rafraichirEcran.execute("");

    }



    @Override
    public void onClick(String article) {
        Link.getInstance().setNbClicks(1);
        Link.getInstance().setOnHistNavigation(article+ " > ");
        Link.getInstance().setLinkClicked(article);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /******** le traitement des données en arriere plan avec un thread séparé *******/

    public class RafraichirEcran extends AsyncTask<String, String, String> {

        ArrayList<String> allLinkClone =new ArrayList<>();


        @Override
        public String doInBackground(String... args)  {
            String text = args[0];

            if( text.length()!=0) {

                for(String it:allLinks){
                    if (it.contains(text)) {
                        allLinkClone.add(it);
                    }
                }
                Collections.sort(allLinkClone, new ComparatorStr(text));
            }
            else{

                allLinkClone=allLinks;
            }
            return null;
        }
        @Override
        public void onPostExecute(String result){
            AllLink.clear();
            AllLink.addAll(allLinkClone);
            linksHollder.notifyDataSetChanged();
        }
    }


    /******************** class Comparator ****************/
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
