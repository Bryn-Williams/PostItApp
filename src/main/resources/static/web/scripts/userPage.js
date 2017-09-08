$(document).ready(function(){

    var theUrl = getInfoFromUrl();
    var theUser = paramObj(theUrl);

    $.get("/api/messages/" + theUser, function(data){
        fillInUserPage(data, theUser);
    });

    //EVENT LISTENERS
    $("#logOutBut").click(function(){
        logOut();
    });

    $("#saveNewMsg").click(function(){
        saveMessage(theUser);
    });
});

    function getInfoFromUrl() {

        var userFromUrl = location.search;
        return userFromUrl;
    }

    function paramObj(search) {
      var obj = {};
      var reg = /(?:[?&]([^?&#=]+)(?:=([^&#]*))?)(?:#.*)?/g;

      search.replace(reg, function(match, param, val) {
        obj[decodeURIComponent(param)] = val === undefined ? "" : decodeURIComponent(val);
      });

      obj = obj.user;
      return obj;
    }

    function fillInUserPage(data, elUser){

            $("html").addClass("back");
            $("body").addClass("back");
            $("#name").html(elUser + "'s");

            for(x = 0; x < data.length; x++){

                var theNoteId = data[x].postItID;

                var postItDiv = $("<div id='" + theNoteId + "' class='center draggable-item'></div>");
                var message = data[x].theMessage;
                var theDate = new Date(data[x].theDate);
                var deleteBut = $("<button class='deleteBut button' onclick='deleteMsg(" + theNoteId + ")'><img id='trash' src='styles/delete.png'></button>");

                postItDiv.append("<h3>" + theDate.toDateString() + "</h3>");
                postItDiv.append("<p>" + message + "</p>");
                postItDiv.append(deleteBut)

                if(data[x].status == "toDo"){

                    postItDiv.addClass("tooDo");
                    var toDoDiv = $("#toDoCol");
                    toDoDiv.append(postItDiv);
                }

                if(data[x].status == "inProcess"){

                    postItDiv.addClass("inProcess");
                    var inProcessDiv = $("#inProcessCol");
                    inProcessDiv.append(postItDiv);
                }

                if(data[x].status == "done"){

                    postItDiv.addClass("done");
                    var doneDiv = $("#doneCol");
                    doneDiv.append(postItDiv);
                }
            }
    }

    function logOut(){

        $.post("/appp/logout").done(function() {
            console.log("logged out");
            window.open("/theMainPage.html","_self")
        })
    }

    function saveMessage(theUser){

        var theMsg = $("#textArea").val();

        $.post("/api/saveMessage", { theMessage: theMsg, loggedUser: theUser })
        .done(function(dto){

                var data = [];
                data.push(dto);

                fillInUserPage(data, theUser);
        });
    };

    function deleteMsg(noteIdd){

        $.post("/api/deleteMessage", { noteId : noteIdd })
        .done(function(){
            $("#" + noteIdd).remove();
        });

    };

