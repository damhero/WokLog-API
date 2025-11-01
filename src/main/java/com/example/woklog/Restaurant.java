package com.example.woklog;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String cuisine;

    @Lob
    private byte[] imageData;

    private Double rating;
    private Integer foodScore;
    private Integer serviceScore;
    private Integer ambianceScore;
    private Integer valueScore;

    @ElementCollection
    private List<String> favoriteDishes;

    @Column(length = 1000)
    private String notes;

    public void calculateAndUpdateRating(){
        if (foodScore != null && serviceScore != null && ambianceScore != null && ambianceScore != null){
            this.rating = (foodScore + serviceScore + ambianceScore + valueScore) / 4.0;
        }else {
            this.rating = null;
        }
    }
}