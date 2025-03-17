let checkToken = () => {
    let token = localStorage.getItem('accessToken');
    if (token == null || token.trim() === '') {
        window.location.href = '/member/login'
    }
}

let setupAjax = () => {
    $.ajaxSetup({
        beforeSend: (xhr) => {
            let token = localStorage.getItem('accessToken');
            if (token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + token)
            }
        }
    })
}

let getUserInfo = () => {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: 'GET',
            url: '/user/info',
            success: (response) => {
                resolve(response);
            },
            error: (xhr) => {
                console.log('xhr : ', xhr);
                if (xhr.status === 401) {
                    handleTokenExpiration();
                } else {
                    reject(xhr);
                }
            }
        })
    })
}

let handleTokenExpiration = () => {
    $.ajax({
        type: 'POST',
        url: '/refresh-token',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: (response) => {
            console.log('new access :: ', response);
            localStorage.setItem("accessToken", response.token)
        },
        error: () => {
            alert('로그인이 필요합니다. 다시 로그인해주세요.');
            window.location.href = '/member/login'
        }
    });
}