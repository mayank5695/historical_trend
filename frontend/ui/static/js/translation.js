
//______________________________________________________________________
// dictionary with language pairs.
// Content:
//    language-key: [display name in from-box, display name in to-box, [possible target languages]
// where the language key is the language as displayed in the URL




//______________________________________________________________________
// the search button has been clicked, go to another page

function search()
{
    // collect user values
    var term = document.getElementById("search_box").value;
    var searchValue=document.getElementById("list").value;


    // visit result page
    window.location.href = "/" + searchValue + "/" + term +".html";
}

function getSelectValue()
{
    var selectedValue=document.getElementById("list").value;
    console.log(selectedValue);
}
//______________________________________________________________________
// initialise the event listener for the enter key in the search box

document.getElementById("search_box").addEventListener("keyup", function (e) {
    if (e.keyCode === 13) {  //checks whether the pressed key is "Enter"
        search();
    }
});
