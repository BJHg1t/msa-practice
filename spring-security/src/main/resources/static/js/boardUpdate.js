$(document).ready(() => {
    checkToken();
    setupAjax();
    getUserInfo().then((userInfo) => {
        $('#hiddenUserId').val(userInfo.userId);
        $('#hiddenUserName').val(userInfo.userName);
        $('#userId').val(userInfo.userId);
        loadBoardDetail(userInfo.userId);
    }).catch((error) => {
        console.error('error : ', error)
    });

    $('#file').on('change', (event) => {
        selectedFile = event.target.files[0];
        updateFileList();
    });

    $('#submitBtn').click((event) => {
        event.preventDefault();

        let formData = new FormData($('#writeForm')[0]);

        $.ajax({
            type: 'PUT',
            url: '/api/board',
            data: formData,
            contentType: false,
            processData: false,
            success: (response) => {
                alert('게시글이 성공적으로 수정되었습니다!');
                window.location.href = '/'
            },
            error: (error) => {
                console.log('오류발생 : ', error);
                alert('게시글 수정 중 오류가 발생했습니다.');
            }
        })
    });
})

let loadBoardDetail = () => {
    let hId = $('#hiddenId').val();

    $.ajax({
        type: 'GET',
        url: '/api/board/' + hId,
        success: (response) => {
            $('#title').val(response.title);
            $('#content').val(response.content);
            $('#userId').val(response.userId);

            if (response.filePath && response.filePath.length > 0) {
                let filePath = response.filePath;
                $('#hiddenFilePath').val(filePath);
                let fileName = filePath.substring(filePath.lastIndexOf('\\') + 1);

                let fileElement =
                    `<li>
                        ${selectedFile.name} <button type="button" class="remove-btn">X</button>
                    </li>`
                $('#fileList').append(fileElement);
            }
        },
        error: (error) => {
            console.error('board detail error :: ', error)
        }
    })

let updateFileList = () => {
    $('#fileList').empty();

    if (selectedFile) {
        $('#fileList').append(
            `<li>
                ${selectedFile.name} <button type="button" class="remove-btn">X</button>
            </li>`
        );

        $('.remove-btn').click(() => {
            selectedFile = null;
            $('#file').val('')
            updateFileList();
        });
    }
}

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