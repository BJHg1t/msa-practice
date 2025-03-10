$(document).ready(() => {

    $('#getWeather').click(() => {

        let serviceKey = $("#serviceKey").val();
        let pageNo = $("#pageNo").val();
        let numOfRows = $("#numOfRows").val();
        let dataType = $("#dataType").val();
        let base_date = $("#base_date").val();
        let base_time = $("#base_time").val();
        let nx = $("#nx").val();
        let ny = $("#ny").val();

        let formData = {
            serviceKey : serviceKey,
            pageNo : pageNo,
            numOfRows : numOfRows,
            dataType : dataType,
            base_date : base_date,
            base_time : base_time,
            nx : nx,
            ny : ny,

        }

        $.ajax({
            type : "GET",
            url : "/weather",
            data : $.param(formData),
            dataType : 'json',
            success: (response) => {
                console.log(response);
                $("#result").text(JSON.stringify(response));
            },
            error: (error) => {
                console.log('오류발생 : ', error);
                alert('오류발생');
            }
        });

    });

});