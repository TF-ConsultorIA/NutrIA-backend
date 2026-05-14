package com.nutria.nutria_api.food.controller;

import com.nutria.nutria_api.food.dto.FoodRequestDTO;
import com.nutria.nutria_api.food.dto.FoodResponseDTO;
import com.nutria.nutria_api.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping
    public ResponseEntity<Page<FoodResponseDTO>> getAll(
            @PageableDefault(size = 10, sort = "foodName") Pageable pageable) {
        return ResponseEntity.ok(foodService.getAllFoods(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(foodService.getFoodById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<FoodResponseDTO>> searchByName(
            @RequestParam String name,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(foodService.searchByName(name, pageable));
    }

    @GetMapping("/type")
    public ResponseEntity<Page<FoodResponseDTO>> getByType(
            @RequestParam String type,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(foodService.getByType(type, pageable));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FoodResponseDTO> create(@RequestBody FoodRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(foodService.createFood(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FoodResponseDTO> update(@PathVariable Long id,
                                                  @RequestBody FoodRequestDTO request) {
        return ResponseEntity.ok(foodService.updateFood(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        foodService.deleteFood(id);
        return ResponseEntity.noContent().build();
    }
}