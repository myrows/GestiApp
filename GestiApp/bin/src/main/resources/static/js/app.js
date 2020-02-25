$(document).ready(function() {
	changePageAndSize();
	changePageAndSizeProfesores();
	changePageAndSizeUsuario();
	changePageAndSizePlataforma();
	changePageAndSizeCompra();
});

$.urlParam = function(name){
	var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	if (results==null){
	       return null;
	    }
	    else{
	       return results[1] || 0;
	    }
	
}

//Permite recargar la página cada vez que cambia el tamaño de página
function changePageAndSize() {
$('#pageSizeSelect').change(function(evt) {
		
		// Url base
		var urlBase = "/productosbuscados/?";
		// Establece el tamaño de página recién seleccionado
		var pageSize = "pageSize=" + this.value;
		// Siempre que se cambia el tamaño de página, nos vamos a la página 1
		var page = "&page=1";
		// Comprobamos si se ha realizado una búsqueda, verificando
		// si en la url hay un parámetro llamado nombre. Si lo hay
		// lo volvemos a incluir en la url como parámetro, y si no
		// no hacemos nada
		var nombre = $.urlParam('nombre');
		var strnombre = "";
		if (nombre != null)
			strnombre = "&nombre="+nombre;
		
		window.location.replace(urlBase+pageSize+page+strnombre);
		
		
		
	});
}

// Permite recargar la página cada vez que cambia el tamaño de página
function changePageAndSizeJuego() {
	$('#pageSizeSelect').change(function(evt) {
		window.location.replace("/admin/juego/?pageSize=" + this.value + "&page=1");
	});
};

function changePageAndSizeProfesores() {
	$('#pageSizeSelectUsuario').change(function(evt) {
		window.location.replace("/admin/validate/?pageSize=" + this.value + "&page=1");
	});
};

function changePageAndSizePlataforma() {
	$('#pageSizeSelectPlataforma').change(function(evt) {
		window.location.replace("/admin/plataforma/?pageSize=" + this.value + "&page=1");
	});
}

function changePageAndSizeCompra() {
	$('#pageSizeSelectCompra').change(function(evt) {
		window.location.replace("/admin/compra/?pageSize=" + this.value + "&page=1");
	});
}