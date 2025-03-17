$(document).ready(() => {

    $('.google-login').click(() => {
        window.location.href = '/oauth2/authorization/google';
    });

    const urlParams = new URLSearchParams(window.location.search);
    const accessToken = urlParams.get('accessToken');

    if (accessToken) {
        localStorage.setItem('accessToken', accessToken);
        window.location.href = '/';
    }
});
