document.addEventListener("DOMContentLoaded", () => {
    const grid = document.getElementById('grid');
    let isDrawing = false;

    // Создание ячеек сетки
    for (let i = 0; i < 28 * 28; i++) {
        const cell = document.createElement('div');
        cell.classList.add('cell');
        grid.appendChild(cell);
    }

    // Функция закрашивания клетки
    function paintCell(cell) {
        cell.style.backgroundColor = 'black';
    }

    // Обработчики событий
    grid.addEventListener('mousedown', (e) => {
        isDrawing = true;
        if (e.target.classList.contains('cell')) {
            paintCell(e.target);
        }
    });

    grid.addEventListener('mousemove', (e) => {
        if (isDrawing && e.target.classList.contains('cell')) {
            paintCell(e.target);
        }
    });

    grid.addEventListener('mouseup', () => {
        isDrawing = false;
    });

    // Остановить рисование, если мышь покинула область сетки
    grid.addEventListener('mouseleave', () => {
        isDrawing = false;
    });
});

function submitDrawing() {
    const cells = document.querySelectorAll('.cell');
    const pixelData = Array.from(cells).map(cell => cell.style.backgroundColor === 'black' ? 0 : 255);
    const matrix = [];
    while (pixelData.length) matrix.push(pixelData.splice(0, 28));

    fetch('/submit', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ matrix })
    }).then(response => response.json())
        .then(data => {
            if (data.prediction) {
                document.getElementById('result').innerText = 'Prediction: ' + data.prediction;
            } else {
                document.getElementById('result').innerText = 'Failed to get prediction: ' + data;
            }
        }).catch(error => {
        document.getElementById('result').innerText = 'Error: ' + error;
    });
}