const triggerDetailEmployee = document.getElementById("triggerDetailEmployee");
const notFoundWrapper = document.getElementById("data-not-found");
const tableContentWrapper = document.getElementById("table-content");
const load = () => {
  $.ajax({
    url: "/api/employee/manager/list-staff",
    method: "GET",
    dataType: "JSON",
    contentType: "application/json",
    success: (results) => {
      const tableWrapper = document.getElementById("table-wrapper");
      let i = 0;
      if (results.length !== 0) {
        tableContentWrapper.classList.remove("d-none");
        notFoundWrapper.classList.add("d-none");
        results.forEach((e) => {
          i += 1;
          tableWrapper.innerHTML += `<tr>
                          <td>${i}</td>
                          <td>${e.first_name}</td>
                          <td>${e.last_name}</td>
                          <td>${e.email}</td>
                          <td>${e.phone_number}</td>
                        </tr>`;
        });
      } else {
        tableContentWrapper.classList.add("d-none");
        notFoundWrapper.classList.remove("d-none");
      }
    },
    error: function (xhr, ajaxOptions, thrownError) {
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: "Something went wrong!",
      });
      console.log({ xhr, ajaxOptions, thrownError });
    },
  });
};

window.addEventListener("DOMContentLoaded", load);
