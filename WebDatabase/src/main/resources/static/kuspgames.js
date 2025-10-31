const loginButton = document.getElementById('loginbutton');
const registerButton = document.getElementById('registerbutton');
const usernameInput = document.getElementById('usernameInput');
const passwordInput = document.getElementById('passwordInput');
const emaildInput = document.getElementById('emailInput');

//Register function
registerButton.addEventListener('click', async (e) => {
    e.preventDefault(); 

    const username = usernameInput.value.trim();
    const password = passwordInput.value.trim();
    const email = passwordInput.value.trim();

    if (!username || !password ) {
        alert('Please fill in all fields.');
        return;
    }

    try {
        const response = await fetch('http://localhost:9090/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username,email, password })
        });

        if (response.ok) {
            alert('Registration successful!');
        } else {
            const errorMsg = await response.text();
            alert('Registration failed: ' + errorMsg);
        }
    } catch (error) {
        alert('Network error: ' + error.message);
    }
});

//Login function
loginButton.addEventListener('click', async (e) => {
    e.preventDefault();

    const username = usernameInput.value.trim();
    const password = passwordInput.value.trim();

    if (!username || !password) {
        alert('Please fill in all fields.');
        return;
    }

    try {
        const response = await fetch('http://localhost:9090/login', {
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
            alert('Login failed: ' + errorMsg);
        }
    } catch (error) {
        alert('Network error: ' + error.message);
    }
});
