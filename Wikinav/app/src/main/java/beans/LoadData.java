package beans;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeSet;

public class LoadData {

    public static BufferedReader createBufferedReader(Context context, String path) throws IOException {
        return new BufferedReader(new InputStreamReader(context.getAssets().open(path), "UTF-8"));
    }
    public static List<Article> loadData(Context context, String path) throws IOException {
        List<Article> ContenuList = new ArrayList<Article>(); //List de type Article qui comporte tous les article de fichier
        // Déclaration des variables
        String titre=null;
        String abstrac=null;
        int rang=0;
        TreeSet<String> links = new TreeSet<>();
        //Le chemin vers la source des articles
        BufferedReader data=null ;

                try {
                    data= createBufferedReader(context, path);
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
                            if (words[0].equals("l"))  links.add(words[1]);

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
    }// fin de la méthode de loadDate


    /*
    * la méthode getAllLinks nous renvoie tous les liens hypertexts
    * présents dans tous les articles afin que nous puissons poroposer des les articles
    * pour l'utilisateurs
    * */
    public static  ArrayList<String> getAllTitles(List<Article> articles){
        // on recupère tous les lien sous forme d'un tableau ArryList
        ArrayList<String>allTitre= new ArrayList<>();

        //On récupère un ListIterator
        ListIterator<Article> it = articles.listIterator();
        // parcourir tous les articles et extrairir les liens dans chaque article

        while(it.hasNext()){
            Article str = it.next();
            allTitre.add(str.getTitre());
        }
        return allTitre;
    }

}
