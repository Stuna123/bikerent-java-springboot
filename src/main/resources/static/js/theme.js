document.addEventListener("DOMContentLoaded", function () {
    const themeToggle = document.getElementById("themeToggle");
    const body = document.getElementById("pageBody");

    if (!body || !themeToggle) return;

    // Charger le thème enregistré
    const savedTheme = localStorage.getItem("theme") || "light";

    body.classList.remove("light-mode", "dark-mode");
    body.classList.add(savedTheme + "-mode");

    updateThemeButton(savedTheme);

    themeToggle.addEventListener("click", () => {
        if (body.classList.contains("light-mode")) {
            body.classList.remove("light-mode");
            body.classList.add("dark-mode");
            localStorage.setItem("theme", "dark");
            updateThemeButton("dark");
        } else {
            body.classList.remove("dark-mode");
            body.classList.add("light-mode");
            localStorage.setItem("theme", "light");
            updateThemeButton("light");
        }
    });

    function updateThemeButton(theme) {
        if (theme === "dark") {
            themeToggle.textContent = "☀️ Mode clair";
            themeToggle.classList.remove("btn-outline-secondary");
            themeToggle.classList.add("btn-outline-light");
        } else {
            themeToggle.textContent = "🌙 Mode sombre";
            themeToggle.classList.remove("btn-outline-light");
            themeToggle.classList.add("btn-outline-secondary");
        }
    }
});