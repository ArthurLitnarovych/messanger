// login.js

document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    const messageContainer = document.getElementById('messageContainer');

    loginForm.addEventListener('submit', (event) => {
        event.preventDefault(); // предотвращаем стандартное поведение формы

        const username = usernameInput.value.trim();
        const password = passwordInput.value.trim();

        // Проверка на пустые поля
        if (!username || !password) {
            displayMessage('Логин и пароль не могут быть пустыми.', 'error');
            return;
        }

        // Здесь может быть AJAX-запрос для проверки существования пользователя
        // Если пользователь не существует:
        // displayMessage('Неправильный логин или пароль.', 'error');

        // Если всё хорошо, отправляем форму
        loginForm.submit();
    });

    function displayMessage(message, type) {
        messageContainer.textContent = message;
        messageContainer.className = type; // можно задать стили для 'error' и 'success'
    }
});
