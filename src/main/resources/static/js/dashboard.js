 // function to fetch ajax data
 function fetchEmployee(filename) {
     fetch(`/employee/getAjax?filename=${filename}`)
         .then(response => response.text())
         .then(data => {
             document.getElementById('ajax-content').innerHTML = data;
         })
         .catch(error => console.error('Error fetching document:', error));
 }

// function to fetch email
//   document.addEventListener('DOMContentLoaded', function() {
//    fetch('/getEmail')
//       .then(response => response.text())
//       .then(data => {
//          document.getElementById('email').textContent = data;
//      })
//       .catch(error => console.error('Error fetching email:', error));
//	});