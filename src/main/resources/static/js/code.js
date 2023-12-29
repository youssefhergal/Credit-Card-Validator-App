document.addEventListener("DOMContentLoaded", function () {
    let save = document.querySelector("#save");
    let valide = document.querySelector("#valide");
    let verifiercarte = document.querySelector("#verfiercarte");

    save.addEventListener("click", function () {
        loadContentFromFile('ajouter_card.html');
    });

    valide.addEventListener("click", function () {
        loadContentFromFile('validate.html');
    });

    verifiercarte.addEventListener("click", function () {
        loadContentFromFile('database.html');
    });
});

function loadContentFromFile(file) {
    var contentContainer = document.getElementById('contentContainer');
    contentContainer.innerHTML = ''; // Clear previous content

    fetch(file)
        .then(response => response.text())
        .then(data => {
            contentContainer.innerHTML = data;
        })
        .catch(error => {
            console.error('Erreur lors du chargement du fichier :', error);
        });
}

function showContent(contentType) {
    var contentContainer = document.getElementById('contentContainer');
    switch (contentType) {
        case 'form':
            contentContainer.innerHTML = '<h2>Formulaire de création de carte</h2><p>Insérez le formulaire de création de carte ici.</p>';
            break;
        case 'validate':
            contentContainer.innerHTML = '<h2>Vérification de la validité de la carte</h2><p>Insérez le formulaire de vérification ici.</p>';
            break;
        case 'database':
            contentContainer.innerHTML = '<h2>Vérification de la base de données</h2><p>Insérez le contenu de vérification de la base de données ici.</p>';
            break;
        default:
            contentContainer.innerHTML = 'Aucun contenu par défaut';
    }
}
