document.addEventListener('DOMContentLoaded', () => {
    const loginRedirectButton = document.getElementById('loginRedirectButton');
    const registerRedirectButton = document.getElementById('registerRedirectButton');

    if (loginRedirectButton) {
        loginRedirectButton.addEventListener('click', () => {
            window.location.href = 'kusplogin.html';
        });
    } else {
        console.warn('loginRedirectButton not found in DOM');
    }

    if (registerRedirectButton) {
        registerRedirectButton.addEventListener('click', () => {
            window.location.href = 'kuspregister.html';
        });
    } else {
        console.warn('registerRedirectButton not found in DOM');
    }
});