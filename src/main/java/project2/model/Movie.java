package project2.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Steve on 04-Sep-16.
 */

@XmlRootElement(name = "Movie")
public class Movie {

    private int id;
    private String name;
    private String category;
    private int year;
    private String description;

    public Movie(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
