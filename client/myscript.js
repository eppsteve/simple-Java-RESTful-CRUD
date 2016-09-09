var baseURL = "http://localhost:8080/movies";
var selected_movie = 0; //holds the id of the selected movie

//get all movies when the application loads
getAllMovies();


$(document).ready(function(){
	$(document).on('click', "#leftBar a", function(){
		selected_movie = $(this).data('identity');
		getMovieById(selected_movie);
		$('#btnDelete').show();
	});
	
	$('#btnDelete').click(function(){
		deleteMovie(selected_movie);
		reloadMovieList();
		$(this).hide();
	});
	
	$('#btnNew').click(function(){
		if ($(this).html() == 'New'){
			clearTextboxes();
			selected_movie = 0;
			$('#btnDelete').hide();
			$(this).html('Cancel');
		} else {
			reloadMovieList();
			$(this).html('New');
		}
	});
	
	$('#btnSave').click(function(){
		if ($('#name').val() == '' || $('#category').val() == '' || $('#description').val() == '' || $('#year').val() == ''){
			alert('All fields are required!');
		} else{
			if (selected_movie == 0){ //if no movie is selected, add a new movie
				addMovie();
				$('#btnNew').html('New');
			} else{	// if a movie is selected, update this movie
				updateMovie();
			}
			reloadMovieList();
			
		}
	});
	
	$('#btnDelete').hide();
	
	//year textbox accepts only letters
	jQuery('#year').keyup(function () { 
		this.value = this.value.replace(/[^0-9\.]/g,'');
	});
});


/*
* retrieves all movies from the database
*/
function getAllMovies(){
	$.ajax({
		type: 'GET',
		url: baseURL, 
		dataType: 'json', 
		success: function(data){
			$.each(data, function(index, movie){
				// we store the id of each movie in a data attribute
				$('#movieList').append('<li><a href="#" data-identity="' + movie.id +'">' +movie.name+'</a></li>');
			});
		}
	});
}

/*
* gets the specified movie from the database
*/
function getMovieById(id){
	$.ajax({
		type: 'GET', 
		url: baseURL + '/' + id, 
		dataType: 'json', 
		success: function(movie){
			$('#name').val(movie.name);
			$('#category').val(movie.category);
			$('#description').val(movie.description);
			$('#year').val(movie.year);
		}  
	});
}

/*
* deletes the specified movie from the database
*/
function deleteMovie(id){
	$.ajax({
		type: 'DELETE', 
		url: baseURL + '/' + id, 
		success: function(){
			alert('Movie deleted!');
		},
		error: function(){
			alert('Unable to delete.');
		}
	});
}

/*
* adds a new movie to the database
*/
function addMovie(){
	$.ajax({
		type: 'POST', 
		contentType: 'application/json', 
		url: baseURL, 
		dataType:"json", 
		data: makeJson('create'), 
		success: function(data){
			alert('A new movie has been added!');
		},
		error: function(){
			alert('Failed to add movie');
			console.log(makeJson());
		}
	});
}

/* 
* updates an existing movie with new data
*/
function updateMovie(){
	$.ajax({
		type: 'PUT', 
		contentType: 'application/json', 
		url: baseURL + '/' + selected_movie, 
		dataType: 'json', 
		data: makeJson('update'), 
		success: function(){
			alert('Movie updated!');
		}, error: function (){
			alert('Failed to update movie.');
		}
	});
}

/*
* Helper function to create json from textboxes values
*/
function makeJson(verb){
	if (verb == 'create'){
		return JSON.stringify({
			"name":	$('#name').val(), 
			"category": $('#category').val(), 
			"description": $('#description').val(), 
			"year": $('#year').val()
		});
	}else if(verb == 'update'){
		return JSON.stringify({
			"id": selected_movie, 
			"name":	$('#name').val(), 
			"category": $('#category').val(), 
			"description": $('#description').val(), 
			"year": $('#year').val()
	});
	}
}

/*
* Helper function to clear the values of textboxes
*/	
function clearTextboxes(){
	$('#name').val(null);
	$('#category').val(null);
	$('#description').val(null);
	$('#year').val(null);
}

/*
* Helper function to reload all movies from the webservice
* to the #movieList
*/
function reloadMovieList(){
		$('#movieList').empty();
		clearTextboxes();
		selected_movie = 0;
		getAllMovies();
		$("btnDelete").hide();
		$("btnNew").html('New');
}

