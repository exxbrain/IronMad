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
    if(a){
        $("#movi").append(a);
        a = null;
    }else {
        a = $("#iframe_player").detach();
    }
    if (num_movi == false) {
        num_movi = true;
    } else if (num_movi == true) {
        num_movi = false;
    }
    $("#btn_movi").text(as());



});

