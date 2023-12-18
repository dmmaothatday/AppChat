const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

signUpButton.addEventListener('click', () => {
    container.classList.add('right-panel-active');
});

signInButton.addEventListener('click', () => {
    container.classList.remove('right-panel-active');
});

function validate() {
    let userAccount = $("#user-account").val();
    if (userAccount.length==0 || userAccount == null) {
        alertErrorMessage("Tài khoản không được để trống");
        return false;
    }
    let password = $("#password").val();
    if (password.length==0 || password == null) {
        alertErrorMessage("Mật khẩu không được để trống");
        return false;
    }
    let rePassword = $("#re-password").val();
    if (rePassword.length==0 || rePassword == null) {
        alertErrorMessage("Nhập Lại Mật khẩu không được để trống");
        return false;
    }
    if (rePassword !== password) {
        alertErrorMessage("Nhập Lại Mật Khẩu sai");
        return false;
    }


    return true;
}
function alertErrorMessage(message) {
    Swal.fire({
        position: 'middle',
        icon: 'warning',
        title: 'Thông báo',
        text: message
    });
}
function alertMessage(message) {
    Swal.fire({
        position: 'middle',
        icon: 'info',
        title: 'Thông báo',
        text: message
    });
}

function checkDuplicate() {
    let userAccount = $("#user-account").val();
    $.ajax({
        type:'POST',
        contentType : "application/json",
        url : "/checkDuplicate/"+userAccount,
        success : function() {
            console.log("request SUCCESS");
            /*location.reload();*/

        },
        error : function() {
            console.log("request ERROR");
        }
    })
}