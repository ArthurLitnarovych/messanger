// registration.js

document.addEventListener('DOMContentLoaded', () => {
    const registrationForm = document.getElementById('registrationForm');
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    const messageContainer = document.getElementById('messageContainer');

    registrationForm.addEventListener('submit', (event) => {
        event.preventDefault(); // предотвращаем стандартное поведение формы

        const username = usernameInput.value.trim();
        const password = passwordInput.value.trim();

        // Проверка на пустые поля
        if (!username || !password) {
            displayMessage('Логин и пароль не могут быть пустыми.', 'error');
            return;
        }

        // Здесь может быть AJAX-запрос для проверки уникальности логина
        // Если логин занят:
        // displayMessage('Логин уже занят. Пожалуйста, выберите другой.', 'error');

        // Если всё хорошо, отправляем форму
        registrationForm.submit();
    });

    function displayMessage(message, type) {
        messageContainer.textContent = message;
        messageContainer.className = type; // можно задать стили для 'error' и 'success'
    }
});
