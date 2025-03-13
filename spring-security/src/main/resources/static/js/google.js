$(document).ready(() => {
    $('.google-login').click(() => {
        window.location.href = '/oauth2/authorization/google';
    })

    $('.google-token').click(() => {
        $.ajax({
            type: 'POST',
            url: '/google',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: (response) => {
                alert('구글 로그인 성공!');
                localStorage.setItem("accessToken", response.token);
                window.location.href = "/";
            },
            error: (error) => {
                console.log('오류 발생:', error);
                alert('로그인 중 오류가 발생했습니다.');
            }
        });
    });

});