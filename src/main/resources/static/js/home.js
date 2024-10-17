document.getElementById('nameForm').addEventListener('submit', function(event) {
            event.preventDefault();
            var senderName = document.getElementById('senderName').value;
            var formData = new FormData();
            formData.set('senderName', senderName);
            var xhr = new XMLHttpRequest();
            xhr.open('POST', '/saveSenderName', true);
            xhr.send(formData);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    window.location.href = '/messages';
                }
            };
        });