$(document).ready(() => {
    loadBoardUpdate();
});

let loadBoardUpdate = () => {
    let hId = $('#hiddenId').val();

    $.ajax({
        type: 'GET',
        url: '/api/board/update/' + hId,
        success: (response) => {
            $('#title').val(response.title);
            $('#content').val(response.content);
            $('#userId').val(response.userId);
        },
        error: (error) => {
            console.error('board detail error :: ', error)
        }
    })
}