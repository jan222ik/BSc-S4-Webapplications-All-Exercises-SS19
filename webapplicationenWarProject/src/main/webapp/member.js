
var string = "";

function submitCheck() {
    function orOverArray(access) {
        let checked = false;
        for (let i = 0; i < access.length; i++) {
            checked = checked || access[i];
        }
        return checked;
    }

    function isEmpty(node) {
        return node.value == null || node.value === "";
    }

    function validatePasswords(passwordFirst, passwordRepeat) {
        if (validatePassword(passwordFirst.value)) {
            if (validatePassword(passwordRepeat.value)) {
                if (passwordRepeat.value === passwordFirst.value) {
                    return true;
                } else {
                    errorString("passwords are not the same");
                }
            } else {
                errorString("password repeat does not adheres to the rules");
            }
        } else {
            errorString("password does not adheres to the rules");
            if (!validatePassword(passwordRepeat.value)) {
                errorString("password repeat does not adheres to the rules");
            }
        }
        return false;
    }

    let fname = document.querySelector('#fname');
    let lname = document.querySelector('#lname');
    let address = document.querySelector('#address');

    let username = document.querySelector('#username');

    let email = document.querySelector('#email');

    let passwordFirst = document.querySelector('#password');
    let passwordRepeat = document.querySelector('#passwordRepeat');

    let genderMale = document.querySelector('#male');
    let genderFemale = document.querySelector('#female');
    let genderOther = document.querySelector('#other');
    let genderValues = [genderMale.checked, genderFemale.checked, genderOther.checked];

    let accessDocent = document.querySelector('#shooter');
    let accessStudent = document.querySelector('#leader');
    let accessAdmin = document.querySelector('#admin');
    let accessValues = [accessDocent.checked, accessStudent.checked, accessAdmin.checked];

    if (isEmpty(fname)) {
        errorString("field for first name must not be empty");
    }

    if (isEmpty(lname)) {
        errorString("field for last name must not be empty");
    }

    if (isEmpty(address)) {
        errorString("field for address must not be empty");
    }

    if (!orOverArray(genderValues)) {
        errorString("no gender selected");
    }

    if (!validateUsername(username)) {
        errorString("username does not adheres to the rules")
    }

    validatePasswords(passwordFirst, passwordRepeat);

    if (!validateEmail(email.value)) {
        errorString("email does not adheres to the rules")
    }

    if (!orOverArray(accessValues)) {
        errorString("no access level selected")
    }

    if (string !== "") {
        alert(string);
    } else {
        alert("All successful");
        let form = document.querySelector('#usrform');
        form.submit();
    }
    string = "";


}

function validateUsername(username) {
    const usernamePattern = /^[A-Za-z_]*$/;
    if (username.value.length >= 4 && username.value.length <= 10) {
        if (username.value.match(usernamePattern)) {
            return true;
        }
    }
    return false;
}

function validatePassword(str) {
    const pwPattern = /^[A-Za-z][A-Za-z0-9_]*$/;
    if (str.length >= 7 && str.length <= 10) {
        if (str.match(pwPattern)) {
            return true;
        }
    }
    return false;
}

function validateEmail(str) {
    const emailRegex = /^([A-Za-z]([A-Za-z0-9_\-]|\.[A-Za-z0-9_\-])*)@(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\-]*[a-zA-Z0-9])\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\-]*[A-Za-z0-9])$/;
    return str.match(emailRegex);
}

function errorString(str) {
    string += (str + "\n");
}

