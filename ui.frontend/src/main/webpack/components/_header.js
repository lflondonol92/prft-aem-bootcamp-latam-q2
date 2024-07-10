document.addEventListener("DOMContentLoaded", function () {
    console.log(document.getElementById("hamburger"))
  document.getElementById("hamburger").addEventListener("click", function () {
    document
      .getElementById("hamburger-close")
      .classList.add("cmp-header__navigation-close__open");

    document
      .getElementById("header-cmp-navigation")
      .classList.add("cmp-header__navigation__nav__open");
  });

  document
    .getElementById("hamburger-close")
    .addEventListener("click", function () {
      document
        .getElementById("hamburger-close")
        .classList.remove("cmp-header__navigation-close__open");

      document
        .getElementById("header-cmp-navigation")
        .classList.remove("cmp-header__navigation__nav__open");
    });
});
