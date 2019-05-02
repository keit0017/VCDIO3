/**
 * Metoder til at vise og gemme elementer på siden.
 */

$("#loginButton").click(function(){
    $("#loginPage").hide();
    $("#adminPage").show();
});

$("#logOut").click(function(){
    $("#adminPage").hide();
    $("#loginPage").show();
});

$("#createUser").click(function(){
    $("#adminPage").hide();
    $("#editPage").show();
});

/**
 * Metode til at hente data fra serveren.
 */

$("#loginButton").click(function () {
   $("#userTable").load("URL");
});

/**
 * Metode til at sende data til serveren når der skal oprettes bruger.
 */

$("#createUserButton").click(function () {
    $.post("URL");
});