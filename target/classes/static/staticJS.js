//показывать или не показавать плеер
var num_movi = false;

function as() {
    var a = "Показать видеоплеер";
    var b = "Скрыть видеоплееп";

    if (num_movi != false) {
        return b;
    } else {
        return a;
    }
}

var a = !null;
$("#btn_movi").on("click", function () {
    if (a) {
        $("#movi").append(a);
        a = null;
    } else {
        a = $("#iframe_player").detach();
    }
    if (num_movi == false) {
        num_movi = true;
    } else if (num_movi == true) {
        num_movi = false;
    }
    $("#btn_movi").text(as());
});

//появляющая форма при авторизации через api

//переменная которая будет руководить, показывать окно или нет
var userApi = {"api_user" : "true"};
//запрос на проверку есть ли аккаунт у нового api клиента, если нет то отображаем окно для создание нового пользователя
function visibleWindow() {
    $.ajax({
        type: "GET",
        url: "api",
        data: userApi,
        crossDomain: true
    }).done(function (data) {
        if(data == "false") {
            $('.popup, .overlay').css({'opacity': 1, 'visibility': 'visible'});
        }else{
            $(".popup, .overlay").css({'opacity': 0, 'visibility': 'hidden'});
        }
    }).fail(function (data) {
        console.log("Ошибка")
    });
}

//здесь будет проверятся в поле имя юзера
function nameUser(name){
    var nameApi = {"name": name};
    $.ajax({
        type: "GET",
        url: "api",
        data: nameApi,
        crossDomain: true
    }).done(function (data) {
        if(data == "true"){
            $("#name_api").addClass("is-invalid");
            $("#btn_api_window").attr("disabled", true);
            $("#error_name_api").css({'opacity': 1, 'visibility': 'visible'});

        }else{
            $("#name_api").removeClass("is-invalid");
            $("#btn_api_window").removeAttr("disabled");
            $("#error_name_api").css({'opacity': 0, 'visibility': 'hidden'});
        }
    })
}


//закрывает окно создане пользователя api
$(".popup .close_window, .overlay").click(function () {
    $(".popup, .overlay").css({'opacity': 0, 'visibility': 'hidden'});
});


$("#name_api").keyup(function () {
    var name_api =$("#name_api").val();
    nameUser(name_api);
});

$(document).ready(function () {
    visibleWindow();
});







