package gpijanski.myandroidmvp.model;


import io.realm.RealmObject;

public class Note extends RealmObject {

    private String date;

    private String title;

    private String text;

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
