$(document).ready(function(){

    $( init );

    function init() {
       $( ".droppable-area1, .droppable-area2, .droppable-area3" ).sortable({
           connectWith: ".connected-sortable",
           stack: '.connected-sortable div',
           receive: function(g){
                changeTheColours(g);
           }
        }).disableSelection();
    }
});

function changeTheColours(g){

      var theColumn = g.target.id;

      if(theColumn == "toDoCol"){

          var theNewStatus = "toDo";

          for(x = 1; x < g.target.childNodes.length; x++){
             for(z = 0; z < g.target.childNodes[x].classList.length; z++){

                 var theIdofTheMovedDiv = g.target.childNodes[x].id;

                 if(g.target.childNodes[x].classList[z] == "done"){

                     sendColourChangeToBackEnd(theIdofTheMovedDiv, theNewStatus);
                 }

                 if(g.target.childNodes[x].classList[z] == "inProcess"){

                     sendColourChangeToBackEnd(theIdofTheMovedDiv, theNewStatus);
                 }
             }
          }
      }

      //IF A POST IT HAS BEEN PLACED IN THE INPROCESS COLUMN
      if(theColumn == "inProcessCol"){

          var theNewStatus = "inProcess";

          for(x = 1; x < g.target.childNodes.length; x++){
             for(z = 0; z < g.target.childNodes[x].classList.length; z++){

                 var theIdofTheMovedDiv = g.target.childNodes[x].id;

                 if(g.target.childNodes[x].classList[z] == "done"){

                     sendColourChangeToBackEnd(theIdofTheMovedDiv, theNewStatus);
                 }

                 if(g.target.childNodes[x].classList[z] == "tooDo"){

                     sendColourChangeToBackEnd(theIdofTheMovedDiv, theNewStatus);
                 }
             }
          }
      }
      //IF A POST IT HAS BEEN PLACED IN THE DONE COLUMN
      if(theColumn == "doneCol"){

          var theNewStatus = "done";

          for(x = 1; x < g.target.childNodes.length; x++){
             for(z = 0; z < g.target.childNodes[x].classList.length; z++){

                 var theIdofTheMovedDiv = g.target.childNodes[x].id;
                    console.log(theIdofTheMovedDiv);
                    console.log(g);

                 if(g.target.childNodes[x].classList[z] == "tooDo"){

                     sendColourChangeToBackEnd(theIdofTheMovedDiv, theNewStatus);
                 }

                 if(g.target.childNodes[x].classList[z] == "inProcess"){

                     sendColourChangeToBackEnd(theIdofTheMovedDiv, theNewStatus);
                 }
             }
          }
      }
};

function sendColourChangeToBackEnd(theIdofTheMovedDiv, theNewStatus){

console.log(theIdofTheMovedDiv);
//WE ARENT GETTING THE IDOFTHEMOVEDDIV AFTER THE SECOND MOVE!!!!!!
console.log(theNewStatus);

     $.post("/api/changeTheStatus", { noteId: theIdofTheMovedDiv, status: theNewStatus })
     .done(function(returnedData){

       // window.location.reload();

        $("#"+theIdofTheMovedDiv).remove();

        var goo = [];
        goo.push(returnedData);

        var theUser = returnedData.theUser;

        //console.log(returnedData);
        fillInUserPage(goo, theUser);


     });
}
