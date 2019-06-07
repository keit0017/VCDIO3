function getUserInfo(){
    var name = document.getElementById('username').value;

    var ajaxRequest = new XMLHttpRequest();
    ajaxRequest.onreadystatechange = function(){
        if(ajaxRequest.readyState == 4){
            if(ajaxRequest.status == 200){
                var person = JSON.parse(ajaxRequest.responseText);

                document.getElementById('firstname').value = person.firstname;
                document.getElementById('initials').value = person.initials;
                document.getElementById('CPR').value = person.CPR;
                document.getElementById('password').value = person.password;
            }
        }
    }

    ajaxRequest.open('GET', 'http://localhost:8080/people/' + name);
    ajaxRequest.send();

    function setPersonInfo(){
        var name = document.getElementById('name').value;
        var firstname = document.getElementById('firstname').value;
        var initials = document.getElementById('initials').value;
        var CPR = document.getElementById('CPR').value;
        var password = document.getElementById('password').value;

        var postData = 'name=' + name;
        postData += '&firstname=' + encodeURIComponent(firstname);
        postData += '&initials=' + encodeURIComponent(initials);
        postData += '&CPR=' + encodeURIComponent(CPR);
        postData += '&password=' + encodeURIComponent(password);

        var ajaxRequest = new XMLHttpRequest();
        ajaxRequest.open('POST', 'http://localhost:8080/people/' + name);
        ajaxRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        ajaxRequest.send(postData);
    }
}