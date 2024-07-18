document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('subscribe-form');
    console.log(form.dataset.redirect);
    form.addEventListener('submit', function (event) {
        event.preventDefault();
        const redirectUrl = new URL(form.dataset.redirect, window.location.origin);
        window.location.href = redirectUrl.href;
    });
});