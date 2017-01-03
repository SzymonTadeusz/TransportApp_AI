package pl.edu.wat.wcy.isi.ai.transportapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by jakub on 02.01.17.
 */
public class DriverRankingRow {
    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("avergeRating")
    private Double avergeRating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAvergeRating() {
        return avergeRating;
    }

    public void setAvergeRating(Double avergeRating) {
        this.avergeRating = avergeRating;
    }

    public DriverRankingRow(String id, String name, Double avergeRating) {
        this.id = id;
        this.name = name;
        this.avergeRating = avergeRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriverRankingRow that = (DriverRankingRow) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return avergeRating != null ? avergeRating.equals(that.avergeRating) : that.avergeRating == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (avergeRating != null ? avergeRating.hashCode() : 0);
        return result;
    }
}
