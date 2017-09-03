$(document).ready(function(){

    $( init );

    function init() {
       $( ".droppable-area1, .droppable-area2, .droppable-area3" ).sortable({
           connectWith: ".connected-sortable",
           stack: '.connected-sortable div'
        }).disableSelection();
    }

 //MAYBE ADD AN IF STATEMENT.......IF DROPPABLE-AREA2 CONTAINS NEW CHILD, SEND A POST TO BACKEND TO CHANGE THE STATUS

});

