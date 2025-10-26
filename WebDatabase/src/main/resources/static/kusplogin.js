document.addEventListener('DOMContentLoaded', () => {
const loginButton = document.getElementById('loginbutton');
const registerButton = document.getElementById('registerbutton');
const usernameInput = document.getElementById('usernameInput');
const passwordInput = document.getElementById('passwordInput');
const errorMessage = document.getElementById('errorMessage');

    if (!errorMessage) console.warn('errorMessage element not found');
    if (!usernameInput) console.warn('usernameInput element not found');
    if (!passwordInput) console.warn('passwordInput element not found');

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
        const response = await fetch('http://kusp.games/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            
            window.location.href = 'https://play.unity.com/en/games/9f8c8e03-e6fd-4685-bce4-ec67931fa74a/better-google-dino';
        } else {
            const errorMsg = await response.text();
            errorMessage.textContent =  errorMsg;
        }
    } catch (error) {
        errorMessage.textContent =  error.message;
    }
});
});