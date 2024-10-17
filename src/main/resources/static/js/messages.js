document.getElementById('messageForm').addEventListener('submit', function(event) {
    event.preventDefault();
    var formData = new FormData(event.target);
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/send', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            window.location.reload();
        } else {
            console.error('Ошибка при отправке сообщения:', xhr.statusText);
        }
    };
    xhr.send(new URLSearchParams(formData).toString());
});
