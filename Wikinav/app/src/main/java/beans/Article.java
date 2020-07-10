package beans;
/*
* Cette class modulise le contenu de fichier qui contient
* des articles, ces derniers ont tous la même format,
* donc cette à pour but de récuperer ces article sous forme des objets
* */
import java.io.Serializable;
import java.util.TreeSet;
public class Article implements Serializable {

    private String titre; // titre présent le titre de l'article
    private String abstract1; // abstract1 représent l'abstract de l'article
    private int rang;  // rang représent le rang de chaque article
    private TreeSet<String> links; // links  représent l'ensmpble des liens présents dans chaque article
   // le constructeur
    public Article(String titre, String abstract1, int rang, TreeSet<String> links) {
        this.titre = titre;
        this.abstract1 = abstract1;
        this.rang = rang;
        this.links = links;
    }
 // l'ensmble des getters et des setters
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAbstract1() {
        return abstract1;
    }

    public void setAbstract1(String abstract1) {
        this.abstract1 = abstract1;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }


    public TreeSet<String> getLinks() {
        return links;
    }


    public void setLinks(TreeSet<String> links) {
        this.links = links;
    }



}

