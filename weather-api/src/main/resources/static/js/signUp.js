$(document).ready(() => {

    $("#signup").click(() => {

        let userId = $("#user_id").val();
        let password = $("#password").val();
        let userName = $("#user_name").val();

        let formData = {
            userId : userId,
            password : password,
            userName : userName
        }

        $.ajax({
            type : "POST",
            url : "/join",
            data : JSON.stringify(formData),
            contentType : 'application/json; charset=utf-8',
            dataType : 'json',
            success: (response) => {
                alert('회원가입이 성공했습니다!\n로그인해주세요.')
                window.location.href = 'http://localhost:1234/member/login'
            },
            error: (error) => {
                console.log('오류발생 : ', error);
                alert('회원가입 중 오류가 발생하였습니다.');
            }
        });

    });

});