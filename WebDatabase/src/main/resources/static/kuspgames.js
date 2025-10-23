document.addEventListener('DOMContentLoaded', () => {
const loginButton = document.getElementById('loginbutton');
const registerButton = document.getElementById('registerbutton');
const usernameInput = document.getElementById('usernameInput');
const passwordInput = document.getElementById('passwordInput');
const errorMessage = document.getElementById('errorMessage');

    if (!errorMessage) console.warn('errorMessage element not found');
    if (!usernameInput) console.warn('usernameInput element not found');
    if (!passwordInput) console.warn('passwordInput element not found');

//Register function
registerButton.addEventListener('click', async (e) => {
    e.preventDefault(); 

    const username = usernameInput.value.trim();
    const password = passwordInput.value.trim();

    if (!username || !password ) {
        errorMessage.textContent = 'Please fill in all fields.';
        return;
    }

    try {
        const response = await fetch('http://13.60.252.87:80/api/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            errorMessage.style.color = 'green';
            errorMessage.textContent = 'Registration successful!';
            
            
            window.location.href = 'kusplogin.html';
        } else {
            const errorMsg = await response.text();
            errorMessage.textContent =  errorMsg;
        }
    } catch (error) {
        errorMessage.textContent =  error.message;
    }
});

//Login function
loginButton.addEventListener('click', async (e) => {
    e.preventDefault();

    const username = usernameInput.value.trim();
    const password = passwordInput.value.trim();

    if (!username || !password) {
        errorMessage.textContent = 'Please fill in all fields.';
        return;
    }

    try {
        const response = await fetch('http://13.60.252.87:80/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            
            window.location.href = 'template.html';
        } else {
            const errorMsg = await response.text();
            errorMessage.textContent =  errorMsg;
        }
    } catch (error) {
        errorMessage.textContent =  error.message;
    }
});
});