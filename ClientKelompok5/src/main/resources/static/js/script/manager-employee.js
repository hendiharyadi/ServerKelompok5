const triggerDetailEmployee = document.getElementById("triggerDetailEmployee");

const load = () => {
  $.ajax({
    url: "/api/employee/manager/list-staff",
    method: "GET",
    dataType: "JSON",
    contentType: "application/json",
    success: (results) => {
      const tableWrapper = document.getElementById("table-wrapper");
      let i = 0;
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
