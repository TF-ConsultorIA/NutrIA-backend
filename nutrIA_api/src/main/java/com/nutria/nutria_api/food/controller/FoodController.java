package com.nutria.nutria_api.food.controller;

import com.nutria.nutria_api.food.dto.FoodRequestDTO;
import com.nutria.nutria_api.food.dto.FoodResponseDTO;
import com.nutria.nutria_api.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping
    public ResponseEntity<List<FoodResponseDTO>> getAll() {
        return ResponseEntity.ok(foodService.getAllFoods());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(foodService.getFoodById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<FoodResponseDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(foodService.searchByName(name));
    }

    @GetMapping("/type")
    public ResponseEntity<List<FoodResponseDTO>> getByType(@RequestParam String type) {
        return ResponseEntity.ok(foodService.getByType(type));
    }

    @PostMapping
    public ResponseEntity<FoodResponseDTO> create(@RequestBody FoodRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(foodService.createFood(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodResponseDTO> update(@PathVariable Long id,
                                                  @RequestBody FoodRequestDTO request) {
        return ResponseEntity.ok(foodService.updateFood(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        foodService.deleteFood(id);
        return ResponseEntity.noContent().build();
    }
}