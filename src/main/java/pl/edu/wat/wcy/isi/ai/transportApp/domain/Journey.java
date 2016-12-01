package pl.edu.wat.wcy.isi.ai.transportApp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Journey.
 */

@Document(collection = "journey")
public class Journey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("date")
    private LocalDate date;

    @Min(value = 0)
    @Max(value = 5)
    @Field("rating")
    private Integer rating;

    @Field("taxi")
    private String taxi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Journey date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getRating() {
        return rating;
    }

    public Journey rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getTaxi() {
        return taxi;
    }

    public Journey taxi(String taxi) {
        this.taxi = taxi;
        return this;
    }

    public void setTaxi(String taxi) {
        this.taxi = taxi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Journey journey = (Journey) o;
        if(journey.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, journey.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Journey{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", rating='" + rating + "'" +
            ", taxi='" + taxi + "'" +
            '}';
    }
}
