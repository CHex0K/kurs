# train_mnist_model.py
import tensorflow as tf
from tensorflow.keras.datasets import mnist
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Flatten
import numpy as np

# Загрузка данных
(x_train, y_train), (x_test, y_test) = mnist.load_data()

# Нормализация данных
x_train, x_test = x_train / 255.0, x_test / 255.0

# Создание модели
model = Sequential([
    Flatten(input_shape=(28, 28)),
    Dense(128, activation='relu'),
    Dense(10, activation='softmax')
])

# Компиляция модели
model.compile(optimizer='adam',
              loss='sparse_categorical_crossentropy',
              metrics=['accuracy'])

# Обучение модели
model.fit(x_train, y_train, epochs=5)

# Сохранение модели
model.save('mnist_model.h5')
