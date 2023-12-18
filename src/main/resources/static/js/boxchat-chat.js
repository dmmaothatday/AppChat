const url = 'http://localhost:8080';
let stompClient;
let selectedUser;
let messageId;


function connectToChat(userName) {
    let socket = new SockJS(url + '/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe("/topic/messages/" + userName, function (response) {
            let data = JSON.parse(response.body);
            render(data.messageBody, data.messageSendId, data.typeMessage);
            console.log("type message la: " + data.typeMessage);
        });
    });
}

function sendMsg(from, messageBody, to) {
    selectedUser = $('#selectedUser').val();
    let inputFileMessage = $("#input-file-message").val();

    to = selectedUser;
    if (inputFileMessage.length == 0) {
        console.log("text");
        stompClient.send("/app/chat/" + selectedUser, {}, JSON.stringify({
            fromLogin: from,
            messageSendId: from,
            messageReceiverId: to,
            messageBody: messageBody,
            typeMessage: 0
        }));
    } else {
        console.log("file");
        console.log(inputFileMessage);

        var formData = new FormData();

        formData.append('messageSendId', from);
        formData.append('messageReceiverId', to);
        formData.append('file', $('#input-file-message')[0].files[0]);
        formData.append('typeMessage', 1);

        $.ajax({
            url: "/send-multipartFile/chat/"+ to,
            data: formData,
            type: 'POST',
            contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
            processData: false, // NEEDED, DON'T OMIT THIS
            // ... Other options like success and etc
            success : function(data) {
                console.log("request SUCCESS");
                messageId = data.messageBody
                console.log(messageId);
                console.log("message id trong js la: " + messageId);
                var template = Handlebars.compile($("#message-template-file").html());
                var context = {
                    messageOutput: '/image/display/message-file/' + messageId,
                    time: getCurrentTime(),
                    toUserName: selectedUser,
                    image: '/image/display/' + from

                };

                $chatHistoryList.append(template(context));
                scrollToBottom();
                $textarea.val('');
                $("#input-file-message").val('');

            },
            error : function() {
                console.log("ERROR");
            }
        });

    }
}


window.onload = function () {
    scrollToBottom();
    let userName = document.getElementById("userId").value;
    connectToChat(userName);

};