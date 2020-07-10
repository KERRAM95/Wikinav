package Controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wikinav.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import beans.Article;
import beans.Link;
import beans.LoadData;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Error.ExampleDialogListener, Success.SuccessDialogListener, Exceeded.ExceededDialogListener {




    //Outil
       private TextView tv_linksCliked;
       private EditText ed_ArticleA;
       private EditText ed_ArticleB;
       private Button bt_fdarticleA,bt_fdArticleB,bt_allLinks;
       private TextView tv_hist,tv_title,tv_abst;

       private static final int ID_ITEM1 = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Link.getInstance().isStat()==false) {
            Intent intent1 = new Intent(this, ManuelActivity.class);
            startActivity(intent1);
        }

         //TextView
        tv_linksCliked = (TextView) findViewById(R.id.tv_linksCliked);
        tv_hist = (TextView) findViewById(R.id.tv_hist);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_abst = (TextView) findViewById(R.id.tv_abst);
        /*********************************************************/
        bt_allLinks = (Button) findViewById(R.id.bt_allLinks);
        bt_allLinks.setOnClickListener(this);
        /********************************************************/
        bt_fdarticleA = (Button) findViewById(R.id.bt_fdarticleA);
        bt_fdarticleA.setOnClickListener(this);
        bt_fdArticleB = (Button) findViewById(R.id.bt_fdArticleB);
        bt_fdArticleB.setOnClickListener(this);
        /********************************************************/
        ed_ArticleA = (EditText) findViewById(R.id.ed_ArticleA);
        ed_ArticleB = (EditText) findViewById(R.id.ed_ArticleB);
        /*********************************************************/
        ed_ArticleA.addTextChangedListener(new TextWatcher() {
            /*
            * addTextChangedListener this method listen if there is any change state of
            * Editable field
            * if true we use TextWatcher anonymous class to detect any change
            * */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                             //we show article not found
                             ArticleDetails("&","");
                } else {
                         String stat= feachFor(ed_ArticleA.getText().toString().trim()+"");
                         if(stat!="-1"){
                             //we show article found
                             Link.getInstance().setAA(ed_ArticleA.getText()+"");
                             ArticleDetails("",ed_ArticleA.getText()+"");}
                         else{

                             //we show article not found
                             ArticleDetails("&","");
                             Link.getInstance().setAA(null);
                         }
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        /*************************** check if article B is valid ******************************/


        ed_ArticleB.addTextChangedListener(new TextWatcher() {
            /*
             * addTextChangedListener this method listen if there is any change state of
             * Editable field
             * if true we use TextWatcher anonymous class to detect any change
             * */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    //we show article not found
                   articleNotVlid("","1");
                }
                else{
                    Link.getInstance().setBB(ed_ArticleB.getText()+"");

                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        /**************************************************************************************/



        //execution en arrière plan le chergement des article
        if(Link.getInstance().getArticles()==null) {
            affiche();
            DataTraitement dataTraitement = new DataTraitement();
            dataTraitement.execute("");
          }

           /***************************************************************/
           if(Link.getInstance().getAA()!=null){ed_ArticleA.setText(Link.getInstance().getAA());

           };
           if(Link.getInstance().getBB()!=null) ed_ArticleB.setText(Link.getInstance().getBB());
           /*
           ***************************** we refresh screen when we click on new link*******************************/
           if(Link.getInstance().getNbClicks()!=0){
                rafraichirEcran();
               ArticleDetails("",Link.getInstance().getLinkClicked());

           }

           /***************< if article has found we open sucessdialog >*******************/

           if(Link.getInstance().getBB()!=null) {
               if (Link.getInstance().getBB().equals(Link.getInstance().getLinkClicked()))
               {
                     openSuccessDialog();
               }

           }
           /************************* when number of click exceeded  ***************/
           if(Link.getInstance().getNbClicks()>10){
               ExceededDialog();
           }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, ID_ITEM1, 0, "Menu 1").setIcon(R.drawable.ic_action_aboutus).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == ID_ITEM1) {
            Intent intent = new Intent(this, AboutusActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }


    private void articlevalid() {
        Toast.makeText(this, "Article found !", Toast.LENGTH_SHORT).show();
    }

    private void articleNotVlid(String... args) {
        if(args[1]=="2"){
            Toast.makeText(this, "Article Not found!", Toast.LENGTH_SHORT).show();}
        if(args[1]=="1"){Toast.makeText(this, "Please enter an article!", Toast.LENGTH_SHORT).show();}

    }


    //rafraichirEcran de l'historique de navigation
    public void rafraichirEcran() {
              List<String> hist =Link.getInstance().getHistNavigation();
              for(String it:hist){tv_hist.append(it);}
              tv_linksCliked.setText(Link.getInstance().getNbClicks()+"");
    }

 /******rafraichir les détails de l'article (Titre et Abstract)******/

    public void ArticleDetails(String... articleID) {
        /**
         * this methode is used to refersh screen
         *
         * */
        if(articleID[0]=="&"){
            tv_title.setText("Article not found");
            tv_abst.setText(Html.fromHtml(""));
        }
        else if(articleID[0]=="@"){
            //openErrorDialog();
            tv_title.setText("Article not found");
            tv_abst.setText(Html.fromHtml(""));
        }
        else if(articleID[0]==""){

            String index = feachFor(articleID[1]);
            if (index.trim() != "-1") {
                tv_title.setText(Link.getInstance().getArticles().get(Integer.parseInt(index.trim())).getTitre());
                tv_abst.setText(Html.fromHtml(Link.getInstance().getArticles().get(Integer.parseInt(index.trim())).getAbstract1()));
            }
            else{ openErrorDialog();}


        }
        else {

            String index = feachFor(articleID[1]);
            if (index.trim() != "-1") {
                tv_title.setText(Link.getInstance().getArticles().get(Integer.parseInt(index.trim())).getTitre());
                tv_abst.setText(Html.fromHtml(Link.getInstance().getArticles().get(Integer.parseInt(index.trim())).getAbstract1()));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private String feachFor(String articleID) {
        /**
         * feashFor: this methode is used
         * to ckeck if there is article with title "articleID"
         * if true then return his index of this article
         * if false then return "-1"
         * */
       List<Article> articles = Link.getInstance().getArticles();

       for(Article it:articles)
       {
            if(it.getTitre().equals(articleID)) {
                return  articles.indexOf(it)+"";
            }
       }

       return "-1";
    }

    /*******************intercept of clicks********************/
    @Override
    public void onClick(View v) {

        if(v==bt_fdarticleA) {
            /*
            * bt_fdarticleA: button for find article A
            * we ckeck first if data is loaded then
            * we show the full list of articles
            * if not yet then the user wait when data is loaded
            *
            * */
            if (Link.getInstance().getAllTitles()==null) {
                Toast.makeText(this, "Wait to Load Data!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, ArticleAActivity.class);
                startActivity(intent);
            }

           }
        /*******************************************************/
        if(v==bt_fdArticleB){
            /*
            * bt_fdArticleB: button for find article B
            * we check first if article A is valid
            * then we show the full list
            *
            * */

            if (Link.getInstance().getAllTitles()==null) {
                Toast.makeText(this, "Wait to Load Data!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, ArticleBActivity.class);
                startActivity(intent);
            }
        }
        /*******************************************************/
        if(v==bt_allLinks){

            /*
            * bt_allLinks: button to show all links in the article allready selected
            * then we check if article B is valid
            * and then we show list of links found in this article
            */
     if(Link.getInstance().getAA()!=null) {

           if (Link.getInstance().getBB()==null) {
               Toast.makeText(this, "Select Article B!", Toast.LENGTH_SHORT).show();


           }else{
                  String stat =feachFor(Link.getInstance().getBB());
                  if(stat=="-1"){Toast.makeText(this, "Article B don't exist!", Toast.LENGTH_SHORT).show();
                  }

                  else {
                      if (!tv_title.getText().equals("")) {
                          Link.getInstance().setAllArticleLinks(getArticleLiens(tv_title.getText().toString()));
                          if (Link.getInstance().getAllArticleLinks() != null) {

                              Intent intent = new Intent(this, LinkesActivity.class);
                              startActivity(intent);
                          }
                      }
                  }
                }
            }else{ Toast.makeText(this, "Select Article A!", Toast.LENGTH_SHORT).show();}


        }

          /*****************************************************/

    }
/**
 * *****************************************************************/
/*cette methode à pour objectif de récupérer tout les liens dans un article
*/
    private ArrayList<String> getArticleLiens(String text) {
         /*
          * getArticleLiens: this method return
          * all links found in article selected
          */
        List<Article> articles = Link.getInstance().getArticles();

        ArrayList<String> List = new ArrayList<>();


        for(Article it:articles)
        {
            if(it.getTitre().equals(text.trim())) {
                Log.w("Indice",articles.indexOf(it)+"");
                       List.addAll(it.getLinks());
                       Log.e("TAG",List+"");
                       return List;
            }
        }
            return null;
    }



    /********************************************************/
  //thread de chargement des articles
    public class DataTraitement extends AsyncTask{

        /*
        * DataTraitement: thread used to load Data in buckground
        *
        */

      List<Article> articl= null;
        Exception exception = null;
        String[] files = null;
      double timeBefore=System.currentTimeMillis();
      double timeAfeter;

      @Override
        protected Object doInBackground(Object[] objects) {

            try {
                files = getAssets().list("Datas");
               Log.e("f", files[0].toString());
                articl = loadData("Datas/"+files[0]);
                Log.e("TTTTTTT",articl.get(0).getLinks()+"");
            } catch (IOException ex) {
                exception = ex;
                Log.e("ex",exception.getMessage());
            }
            return null;
        }


        @Override
        protected void onPostExecute(Object o){
            super.onPostExecute(o);

            if(exception !=null ){
                Log.e("EX",exception.getMessage());
            }
            else{
                //articles.clear();
               Link.getInstance().setArticles(articl);
               timeAfeter=System.currentTimeMillis();
                affiche(articl.size(),(timeAfeter-timeBefore));
                if(Link.getInstance().getArticles()!=null){
                    GetAllTitles getAllTitles = new GetAllTitles();
                    getAllTitles.execute();
                }
            }
        }
    } // Asyntask pour le chargement des article
//affichage des toasts de demmarage de l'application
    private void affiche(double... args) {

        if(args.length==2) {
            Toast.makeText(this, args[0] + " Article Founded! in " + args[1] / 1000 + " S", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,  "Wait to load Data", Toast.LENGTH_SHORT).show();
        }

        }
    /***********************************************************************/
    public class GetAllTitles extends AsyncTask{
        /*
        * GetAllTitles : thread used to feach for titles
        * in all Atricles
        */

            ArrayList<String> AllTitle =null;
            Exception exception=null;
            @Override
            protected Object doInBackground(Object[] objects) {
                AllTitle =LoadData.getAllTitles(Link.getInstance().getArticles());
                //on récupère tous les liens de tous les articles
                return null;
            }


            @Override
            protected void onPostExecute(Object o){
                super.onPostExecute(o);

                if(exception !=null ){

                }
                else{
                    Link.getInstance().setAllTitles(AllTitle);
                    Log.e("allLinks", Link.getInstance().getAllTitles().size()+"");
                }


            }
        }

    /*******************Loaded method Data *********/
    public List<Article> loadData(String path) throws IOException {
        List<Article> ContenuList = new ArrayList<Article>();
        /*
        * loadData: this methode read data form file
        * and return a list of all article found in its
        */
        // Déclaration des variables
        String titre=null;
        String abstrac=null;
        int rang=0;
        TreeSet<String> links = new TreeSet<>();
        //Le chemin vers la source des articles
        BufferedReader data=null ;

        try {
            data= new BufferedReader(new InputStreamReader(MainActivity.this.getAssets().open(path), "UTF-8"));
            for (String line= data.readLine(); line!=null; line=data.readLine()) {

                String[] words = line.split("=",2); //spliter les ligns en deux champs par un séparateur "="
                /*
                 * on recupère le contenue de la ligne
                 * selon son identifiant
                 * dans notre fichier nous trouerons principlement quatre types d'identificateurs
                 * "t" pour le titre
                 * "a" pour l'abstract
                 * "r" pur le rang
                 * "l" pour le lien hypertext
                 * " " ligne vide pour séparer entre deux article
                 */

                if (!words[0].trim().isEmpty() && !words[1].trim().isEmpty()) {
                    if (words[0].equals("t")) titre =words[1].trim();
                    if (words[0].equals("a")) abstrac = words[1].trim();
                    if (words[0].equals("r")) rang = Integer.parseInt(words[1].trim());
                    if (words[0].equals("l"))  links.add(words[1].trim());

                }
                else {

                    ContenuList.add(new Article(titre,abstrac,rang,new TreeSet<>(links))); // ceation d'un objet de type article
                    links.clear(); // reintialisé la list
                }

            }

        }catch (IOException e){

            Log.e("long","error while loading"+path, e);
            return null;

        }finally {
            try {

                data.close();
            }catch (Exception e){}

        }
        return ContenuList;  // return la liste des article avec la ferméture implicite de fichier
    }
    /***************************end of methode*******************************/

    /************************* OpenErrorDialog Methode ***********************/

    public void openErrorDialog() {
        Error error = new Error();
        error.show(getSupportFragmentManager(), "example dialog");
    }

    /*************************** OpenSuccessDialog Methode *******************************/
    public void openSuccessDialog(){
        /******openSuccessDialog***
         * we apply this methode when we found article
         * terget and then we show dialog message for tell UI that they have successeeded
         * and we offre for fim two otion
         * first get navigation history that mean we apply NavigationHistoryActivity
         * second PlayAgin if they want play again
         * */
        Success success = new Success();
        success.show(getSupportFragmentManager(), " Success dialog");
    }

    /************************** article terget is found with success ******************************/
    @Override
    public void playAgain() {
        /********PlayAgain**********
         * we have to delete data of previous
         * game and then we start a new game
         */

        Link.getInstance().resetNbCliks();
        Link.getInstance().resetHistNavigation();
        Link.getInstance().setAA(null);
        Link.getInstance().setBB(null);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void getHistNavigation() {
        /********** we show navigation history by applying NavigHistoryActivity*********/
        Intent intent = new Intent(this, NavigHistoryActivity.class);
        startActivity(intent);
    }
/************************ Error article not found ************************/
    @Override
    public void Restart() {
        /***** Restart for play again
         * we delete fist all data lik history of navigation
         * and reset numbre of clicks
         * and then we apply MainActivity
         */
        Link.getInstance().resetNbCliks();
        Link.getInstance().resetHistNavigation();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }



    /***************************Exceeded numbre of attempts************************************/
    public void ExceededDialog() {
        Exceeded exceeded = new Exceeded();
        exceeded.show(getSupportFragmentManager(), "Exceeded dialog");
    }
    @Override
    public void Replay() {
        Link.getInstance().resetNbCliks();
        Link.getInstance().resetHistNavigation();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }



    /****************************************************************/

}
