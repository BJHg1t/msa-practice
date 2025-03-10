$(document).ready(() => {

    $('#signin').click(() => {

        let userId = $('#user_id').val();
        let password = $('#password').val();

        let formData = {
            username : userId,
            password : password
        }

        $.ajax({
            type : "POST",
            url : "/login",
            data : $.param(formData),
            contentType :  'application/x-www-form-urlencoded; charset=utf-8',
            dataType : 'json',
            success: (response) => {
                console.log(response);
                window.location.href = 'http://localhost:1234/member/api'
            },
            error: (error) => {
                console.log('오류발생 : ', error);
                console.log('오류발생 : ', error.responseJSON.success);
                alert('아이디 혹은 비밀번호를 다시 확인해주세요.');
            }
        });

    });

});