
$("#profile-img").click(function () {
    $("#status-options").toggleClass("active");
});

$(".expand-button").click(function () {
    $("#profile").toggleClass("expanded");
    $("#contacts").toggleClass("expanded");
});

$("#status-options ul li").click(function () {
    $("#profile-img").removeClass();
    $("#status-online").removeClass("active");
    $("#status-away").removeClass("active");
    $("#status-busy").removeClass("active");
    $("#status-offline").removeClass("active");
    $(this).addClass("active");

    if ($("#status-online").hasClass("active")) {
        $("#profile-img").addClass("online");
    } else if ($("#status-away").hasClass("active")) {
        $("#profile-img").addClass("away");
    } else if ($("#status-busy").hasClass("active")) {
        $("#profile-img").addClass("busy");
    } else if ($("#status-offline").hasClass("active")) {
        $("#profile-img").addClass("offline");
    } else {
        $("#profile-img").removeClass();
    }
    ;

    $("#status-options").removeClass("active");
});


$(document).ready(function () {
    $("#create-group_button").click(function () {
        $("#create-group").slideToggle(500, function () {
            if ($("#create-group_button").val() == "close") {
                $("#create-group_button").val("show table");
            } else {
                $("#create-group_button").val("close");
            }
        });
    });
    $("#add-member_button").click(function () {
        $("#add-member").slideToggle(500, function () {
            if ($("#add-member_button").val() == "close") {
                $("#add-member_button").val("show table");
            } else {
                $("#add-member_button").val("close");
            }
        });
    });
});

function renderValueFromInputFileToInputText() {
    console.log($("#input-file-message").val());
    let inputFileMessage = $("#input-file-message").val();
    $("#message-to-send").val(inputFileMessage);
    if (inputFileMessage == null) {
        console.log("text");
    } else {
        console.log("file");
    }
}