package god.prakhar.com.god;

/**
 * Created by Pewds on 20-Nov-15.
 */
public class FeedItem {
    //    private int id;
    private String name, title, profilePic, date, content, comments;
    private int type;

    public FeedItem() {
    }


    public FeedItem(String name, String title,
                    String profilePic, String date, String content, int type, String comments) {
        super();
//        this.id = id;
        this.name = name;
        this.title = title;
        this.profilePic = profilePic;
        this.date = date;
        this.content = content;
        this.type = type;
        this.comments = comments;


    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {return type;}

    public void setType(int type) {
        this.type = type;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}