package jerry.jerrynews;

/**
 * Created by Administrator on 2017/5/22.
 */

public class News {
    private String urlImg;
    private String title;
    private String date;
    private String author_name;

    public News(String author_name, String date, String title, String urlImg) {
        this.author_name = author_name;
        this.date = date;
        this.title = title;
        this.urlImg = urlImg;
    }

    public News() {
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
