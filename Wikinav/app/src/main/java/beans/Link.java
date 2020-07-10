package beans;
import java.util.ArrayList;
import java.util.List;


public class Link {
     private String AA; //article A
     private String BB; // //article B
     private boolean stat;

    public boolean isStat() {
        return stat;
    }

    public void setStat(boolean stat) {
        this.stat = stat;
    }

    //tous les liens
    private ArrayList<String> allTitles;
    private ArrayList<String> allArticleLinks;
    private List<String> histNavigation = new ArrayList<>();// Historique de navigation;
    private int nbClicks=0; //Combien de fois on clique sur un lien
    private String linkClicked;

    public String getLinkClicked() {
        return linkClicked;
    }

    public void setLinkClicked(String linkClicked) {
        this.linkClicked = linkClicked;
    }

    public int getNbClicks() {
        return nbClicks;
    }

    public void setNbClicks(int nbClicks) {
        this.nbClicks = nbClicks+this.getNbClicks();
    }
    public void resetHistNavigation(){
        histNavigation.clear();
    }

    /******************** Incrémenter NbClicks  *******/
    public void incNbClicks(){this.nbClicks++;}

    /**********reinstialiser nombre de clicks********/
    public void resetNbCliks(){
        nbClicks=0;
    }

    public List<String> getHistNavigation() {
        return histNavigation;
    }

    public void setOnHistNavigation(String histNavigation) {
        this.histNavigation.add(histNavigation);
    }



    public ArrayList<String> getAllArticleLinks() {
        return allArticleLinks;
    }

    public void setAllArticleLinks(ArrayList<String> allArticleLinks) {
        this.allArticleLinks = allArticleLinks;
    }

    //tout les articles
    private List<Article> articles;

    public ArrayList<String> getAllTitles() {
        return allTitles;
    }

    public void setAllTitles(ArrayList<String> allTitles) {
        this.allTitles = allTitles;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    private  static  Link link;


    public static Link getInstance(){
       if(link==null) link = new Link();
        return link;
    }

    public String getAA() {
        return AA;
    }

    public void setAA(String AA) {
        this.AA = AA;
    }

    public String getBB() {
        return BB;
    }

    public void setBB(String BB) {
        this.BB = BB;
    }
}
