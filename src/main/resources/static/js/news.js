function fetchNewsAjax(filename) {
	$.ajax({
		url: "/api/news/getAjax",
		data: {filename: filename},
		success: function(ajaxData) {
			$("#ajax-content")[0].innerHTML = ajaxData;
			if(filename === "news-list.html") {
				$("#ajax-title")[0].innerHTML = "NEWS LIST";
			} else {
				$("#ajax-title")[0].innerHTML = "CREATE NEWS";
			}
		},
		error: function(xhr) {
			alert("error at /api/news/getAjax, error code: " + xhr.status);
		}
	});
}

// Function to search news
function newsSearch(pageNumber) {
    const searchInput = document.getElementById('searchInput').value;
    const entries = document.getElementById('show').value;

    // Call API to fetch filtered news
    fetch(`/api/news/search?query=${searchInput}&page=${pageNumber}&size=${entries}`)
        .then(response => response.json())
        .then(data => {
            const newsListContent = document.getElementById('news-list-content');
            newsListContent.innerHTML = ''; // Clear current content

            // Loop through news and populate table
            data.news.forEach(news => {
                newsListContent.innerHTML += `
                    <tr>
                        <td class="text-center"><input type="checkbox" value="${news.id}"></td>
                        <td>${news.title}</td>
                        <td>${news.content}</td>
                        <td>${new Date(news.postDate).toLocaleDateString()}</td>
                    </tr>
                `;
            });

            // Update pagination and entries info
            document.getElementById('start-entry').innerText = data.start;
            document.getElementById('end-entry').innerText = data.end;
            document.getElementById('total-entries').innerText = data.total;
            updatePageNews(data.totalPages, pageNumber);
        })
        .catch(error => console.error('Error fetching news:', error));
}

// Function to handle pagination
function updatePageNews(totalPages, currentPage) {
    const pageButtons = document.getElementById('page-buttons');
    pageButtons.innerHTML = ''; // Clear current pagination

    // Add pagination buttons
    for (let i = 0; i < totalPages; i++) {
        pageButtons.innerHTML += `
            <button onclick="search(${i})" class="btn btn-sm ${i === currentPage ? 'btn-primary' : 'btn-secondary'}">
                ${i + 1}
            </button>
        `;
    }
}

// Function to create news
// function createNews() {
//     const title = prompt('Enter news title:');
//     const content = prompt('Enter news content:');
//
//     if (title && content) {
//         fetch('/api/news', {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json',
//             },
//             body: JSON.stringify({ title, content }),
//         })
//         .then(response => {
//             if (response.ok) {
//                 alert('News created successfully!');
//                 search(0); // Reload the news list
//             } else {
//                 alert('Failed to create news.');
//             }
//         })
//         .catch(error => console.error('Error creating news:', error));
//     }
// }

function createNews() {
    const title = prompt('Enter news title:');
    const content = prompt('Enter news content:');

    if (title && content) {
        const newsData = {
            title: title,
            content: content
        };

        $.ajax({
            url: "/api/news",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(newsData),
            success: function(response) {
                alert('News created successfully!');
                search(0); // Reload the news list
            },
            error: function(xhr) {
                if(xhr.status === 400) {
                    const error = JSON.parse(xhr.responseText);
                    let validationMessage = "";
                    let i = 0;
                    error.errors.forEach(error => {
                        validationMessage += ++i + "." + error.defaultMessage + "\n";
                    });
                    alert(error.message + " -->\n" + validationMessage);
                }
                else {
                    alert("an expected error occurred at /api/new, error code: " + xhr.status);
                }
            }
        });
    } else {
        alert("Title and content are required.");
    }
}


// Function to update news
function updateNews() {
    const selectedNews = getSelectedNews();
    if (!selectedNews) {
        alert('Please select news to update.');
        return;
    }

    const newTitle = prompt('Update title:', selectedNews.title);
    const newContent = prompt('Update content:', selectedNews.content);

    if (newTitle && newContent) {
        fetch(`/api/news/${selectedNews.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ title: newTitle, content: newContent }),
        })
        .then(response => {
            if (response.ok) {
                alert('News updated successfully!');
                search(0); // Reload the news list
            } else {
                alert('Failed to update news.');
            }
        })
        .catch(error => console.error('Error updating news:', error));
    }
}

// Function to delete news
function deleteNews() {
    const selectedNews = getSelectedNews();
    if (!selectedNews) {
        alert('Please select news to delete.');
        return;
    }

    const confirmDelete = confirm(`Are you sure you want to delete news "${selectedNews.title}"?`);
    if (confirmDelete) {
        fetch(`/api/news/${selectedNews.id}`, {
            method: 'DELETE',
        })
        .then(response => {
            if (response.ok) {
                alert('News deleted successfully!');
                search(0); // Reload the news list
            } else {
                alert('Failed to delete news.');
            }
        })
        .catch(error => console.error('Error deleting news:', error));
    }
}

// Utility function to get selected news
function getSelectedNews() {
    const checkboxes = document.querySelectorAll('#news-list-content input[type="checkbox"]:checked');
    if (checkboxes.length !== 1) {
        alert('Please select exactly one news item.');
        return null;
    }

    const newsId = checkboxes[0].value;
    const selectedRow = checkboxes[0].closest('tr');
    const title = selectedRow.children[1].innerText;
    const content = selectedRow.children[2].innerText;

    return { id: newsId, title, content };
}

// Attach event listeners to buttons

function saveNews() {
    // Lấy giá trị từ form
    const title = document.getElementById('title').value.trim();
    const preview = document.getElementById('preview').value.trim();
    const content = document.getElementById('content').value.trim();

    // Kiểm tra nếu các trường bắt buộc có giá trị không
    if (!title || !preview || !content) {
        alert("Please fill all required fields.");
        return;
    }

    // Đối tượng tin tức mới
    const news = {
        title: title,
        preview: preview,
        content: content
    };

    // Gửi request POST để lưu tin tức
    fetch('/news/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(news),
    })
    .then(response => response.json())
    .then(apiResponse => {
        if (apiResponse.code === 200) {
            alert("News saved successfully!");
            window.location.href = 'news-list.html'; // Điều hướng về trang danh sách tin tức
        } else {
            alert("Error saving news: " + apiResponse.description);
        }
    })
    .catch(error => {
        console.error('Error saving news:', error);
        alert("An error occurred while saving news.");
    });
}

function resetReportInput() {
    document.getElementById('title').value = '';
    document.getElementById('preview').value = '';
    document.getElementById('content').value = '';
}

