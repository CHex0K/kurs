# flask_api.py
from flask import Flask, request, jsonify
import tensorflow as tf
import numpy as np

# Загрузка модели
model = tf.keras.models.load_model('mnist_model.h5')

app = Flask(__name__)

def predict(matrix):
    # Преобразование входного массива в нужную форму 
    matrix = np.array(matrix, dtype=np.float32)
    # Инвертирование цветов + нормализация
    matrix = (255 - matrix) /255
    matrix = np.expand_dims(matrix, axis=0)  # Добавление размерности батча
    # Предсказание модели
    result = model.predict(matrix)
    # Предположим, что у вас есть функция для обработки результата предсказания
    predicted_digit = np.argmax(result)
    return str(predicted_digit)

@app.route('/predict', methods=['POST'])
def predict_digit():
    # Получение данных из запроса
    data = request.json
    print('0-----------')
    matrix = np.array(data['matrix'])
    print('1-----------')
    result = predict(matrix)
    print(result)
    return jsonify({'prediction': result})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)#, debug=True)
