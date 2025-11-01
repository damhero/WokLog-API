package com.example.woklog;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService service;

    //Retrieving restaurants
    @GetMapping
    public List<Restaurant> getAll(){
        return service.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public Restaurant getById(@PathVariable Long id){
        return service.getRestaurantById(id);
    }

    @PostMapping
    public Restaurant create(@RequestBody Restaurant restaurant){
        return service.addRestaurant(restaurant);
    }

    @PutMapping("/{id}")
    public Restaurant update(@PathVariable Long id, @RequestBody Restaurant restaurant){
        return service.updateRestaurant(id, restaurant);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.deleteRestaurant(id);
    }

    @GetMapping("/top3")
    public List<Restaurant> getTop3(){
        return service.getTop3();
    }

    //Retrieving images
    @PostMapping("/{id}/image")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("image")MultipartFile file) throws IOException {
        service.saveImage(id, file.getBytes());
        return ResponseEntity.ok("Image uploaded");
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id){
        byte[] image = service.getImage(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }
}
