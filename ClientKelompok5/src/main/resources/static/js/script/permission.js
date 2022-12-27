const loadedData = () => {
  document
    .getElementById("trigger-addPermission")
    .addEventListener("click", preAddEmployee);
};

const URL = "http://localhost:8081/api/permission";

const preAddEmployee = () => {
  const inputNote = document.getElementById("input-note");
  const permissionType = document.getElementById("input-permissionType");
  permissionType.addEventListener("change", (event) => {
    const value = event.target.value;
    if (value === "1") {
      inputNote.setAttribute("disabled", "");
    } else {
      inputNote.removeAttribute("disabled");
    }
  });

  document.getElementById("submit-permission").addEventListener("click", () => {
    const start_leave = document.getElementById("input-date-start").value;
    const end_leave = document.getElementById("input-date-end").value;
    const manager = document.getElementById("input-manager").value;
    const leave_type = permissionType.value;
    const note = inputNote.value;

    $.ajax({
      url: URL,
      method: "POST",
      dataType: "JSON",
      data: JSON.stringify({
        leave_type: leave_type === "1",
        start_leave,
        end_leave,
        note,
        manager,
      }),
      contentType: "application/json",
      success: (result) => {
        console.log(result);
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
  });
};

window.addEventListener("load", loadedData);
