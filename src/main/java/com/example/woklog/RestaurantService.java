package com.example.woklog;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository  repository;

    public List<Restaurant> getAllRestaurants() {
        return repository.findAll();
    }

    public Restaurant getRestaurantById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
    }

    public List<Restaurant> getTop3(){
        return repository.findTop3ByOrderByRatingDesc();
    }

    public Restaurant addRestaurant(Restaurant restaurant){
        return repository.save(restaurant);
    }

    public Restaurant updateRestaurant(Long id, Restaurant updated){
        return repository.findById(id)
                .map(r -> {
                    r.setName(updated.getName());
                    r.setAddress(updated.getAddress());
                    r.setCuisine(updated.getCuisine());
                    if (updated.getImageData() != null) {r.setImageData(updated.getImageData());}
                    r.setFoodScore(updated.getFoodScore());
                    r.setServiceScore(updated.getServiceScore());
                    r.setAmbianceScore(updated.getAmbianceScore());
                    r.setValueScore(updated.getValueScore());
                    r.setFavoriteDishes(updated.getFavoriteDishes());
                    r.setNotes(updated.getNotes());

                    r.calculateAndUpdateRating();

                    return repository.save(r);
                })
                .orElseThrow(() -> new RuntimeException("Restaurant Not found"));
    }

    public void deleteRestaurant(Long id){
        repository.deleteById(id);
    }

    public void saveImage(Long id, byte[] imageData){
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: "+ id));
        restaurant.setImageData(imageData);
        repository.save(restaurant);
    }

    public byte[] getImage(Long id){
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
        if (restaurant.getImageData() == null){
            throw new RuntimeException("Image not found for restaurant: " + id);
        }
        return restaurant.getImageData();
    }

}
