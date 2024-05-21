package com.example.kursov.Controller;

import org.springframework.web.bind.annotation.*;
//Контроллер обрабатывает HTTP запросы, связанные с отправкой рисунка.
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/submit")
public class DrawingController {

    @PostMapping
    public ResponseEntity<?> submitDrawing(@RequestBody DrawingRequest drawingRequest) {
        int[][] matrix = drawingRequest.getMatrix();
        // Вывод матрицы в консоль
        System.out.println("Received matrix: ");
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }

        // Отправка матрицы на Flask API для получения предсказания
        RestTemplate restTemplate = new RestTemplate();
        String flaskUrl = "http://localhost:5000/predict";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<DrawingRequest> request = new HttpEntity<>(drawingRequest, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(flaskUrl, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Prediction: " + response.getBody());
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("Failed to get prediction from Flask API: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception occurred while getting prediction from Flask API: " + e.getMessage());
        }
    }
}


//DrawingRequest: Класс, представляющий структуру данных, отправляемых в запросе. Содержит матрицу 28x28.
class DrawingRequest {
    private int[][] matrix;

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
}