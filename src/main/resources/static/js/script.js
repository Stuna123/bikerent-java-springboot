const form = document.getElementById("contactForm");

form.addEventListener('submit', async function(e) {
    // Empêche le rechargement
    e.preventDefault();

    const data = new FormData(form);

    try {
        const response = await fetch(form.action, {
            method: form.method,
            body: data,
            headers: {
                'Accept': 'application/json'
            }
        });

        if(response.ok) {
            alert("Message envoyé avec succès !");

            // On vide le formulaire
            form.reset();
        } else {
            alert("Erreur lors de l'envoi, veuillez réessayer.");
        }
    } catch (error) {
        alert("Erreur réseau.");
    }
})

