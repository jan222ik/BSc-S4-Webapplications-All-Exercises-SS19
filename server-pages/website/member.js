console.log("On Load");
function submitCheck() {
    var fname = document.querySelector('#fname');
    var lname = document.querySelector('#lname');
    var address = document.querySelector('#address');
    var username = document.querySelector('#username');
    console.log(fname.value);
    console.log(lname.value);
    console.log(address.value);
    console.log(username.value);
}

function checkPassword() {
    var password = document.querySelector('#password');
    console.log(password.value)
}

function checkPasswordRepeat() {
    var password = document.querySelector('#password').value;
    var passwordRepeat = document.querySelector('#passwordRepeat').value;
    if (password === passwordRepeat) {
        console.log("Passwords equal");
    } else {
        console.log("Passwords different length");
    }
}

