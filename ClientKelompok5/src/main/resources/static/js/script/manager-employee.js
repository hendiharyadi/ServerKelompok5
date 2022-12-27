const triggerDetailEmployee = document.getElementById("triggerDetailEmployee");

const load = () => {
  triggerDetailEmployee.addEventListener("click", () => {
    /*
     * Fetch Ajax
     *
     * */

    const DEFname = document.getElementById("detail-fName");
    const DELname = document.getElementById("detail-lName");
    const DEmail = document.getElementById("detail-email");
    const DEPhone = document.getElementById("detail-phone");

    DEFname.value = "John";
    DELname.value = "Doe";
    DEmail.value = "john@gmail.com";
    DEPhone.value = "01902910291";
  });
};

window.addEventListener("DOMContentLoaded", load);
