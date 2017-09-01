$(document).ready(function(){

    //LOGIN
    $("#theButton").click(function(){

        var elUserName = $("#userInput").val();
        var elPassword = $("#userPassword").val();

        logIn(elUserName, elPassword);

    });

    //CREATE PLAYER
    $("#signUpButton").click(function(){

        var elUserName = $("#userInput").val();
        var elPassword = $("#userPassword").val();
        elPassword = elPassword.toString();

        var newUser = { userName: elUserName, thePassword: elPassword};
        console.log(newUser);

        $.post("/api/createUser", { userName: elUserName, thePassword: elPassword })
        .done(function(){
             console.log("posted");
             logIn(elUserName, elPassword);
        });

    });

});


    function logIn(elUserName, elPassword){

        $.post("/appp/login", { userName: elUserName, thePassword: elPassword })
        .done(function(){

                console.log("logged innnnnnnnnnnnn!");
                window.open("/web/userPage.html?user=" + elUserName,"_self")
        });
}
