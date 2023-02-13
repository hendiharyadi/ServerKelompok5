// Url api
const URL = "/api/employee";
const triggerAddBtn = document.getElementById("trigger-add-button");
const loadData = async () => {
  getAllEmployees();
  triggerAddBtn.addEventListener("click", () => {
    const inputSubmit = document.getElementById("input-submit");
    const selectRole = document.getElementById("input-role");
    const contentAddManager = document.getElementById("content-add-manager");
    // selectRole.addEventListener("change", (event) => {
    //   const value = event.target.value;
    //   if (value === "1") {
    //     contentAddManager.classList.remove("d-none");
    //   } else {
    //     contentAddManager.classList.add("d-none");
    //   }
    // });
    getManagers();
    inputSubmit.addEventListener("click", postData);
  });
};

const getRole = async () => {
  const response = await fetch("/api/role");
  return await response.json();
};

const getManagers = () => {
  $.ajax({
    url: URL + "/managers",
    method: "GET",
    dataType: "JSON",
    contentType: "application/json",
    success: (results) => {
      const inputManager = document.getElementById("input-manager");
      inputManager.innerHTML = "";
      inputManager.add(generateOption("0", "Select Manager", true));
      results.forEach((emp) =>
        inputManager.add(generateOption(emp.id, emp.first_name, false))
      );
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

const getManagerForEdit = (id) => {
  $.ajax({
    url: URL + "/managers",
    method: "GET",
    dataType: "JSON",
    contentType: "application/json",
    success: (results) => {
      const inputManager = document.getElementById("edit-manager");
      inputManager.innerHTML = "";
      if (typeof id === "undefined") {
        inputManager.add(generateOption(0, "Select Manager", true));
      }
      results.forEach((emp) => {
        inputManager.add(generateOption(emp.id, emp.first_name, emp.id === id));
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

const resetInput = () => {
  $("#input-fName").val("");
  $("#input-lName").val("");
  $("#input-email").val("");
  $("#input-password").val("");
  $("#input-phone").val("");
  $("#input-username").val("");
};
const postData = async () => {
  const btnSpinner = document.getElementById("spinner-button");
  const btnClose = document.getElementById("btn-close");
  const btnSubmit = document.getElementById("input-submit");
  btnSubmit.classList.add("d-none");
  btnSpinner.classList.remove("d-none");
  btnClose.setAttribute("disabled", "");
  const first_name = $("#input-fName").val();
  const last_name = $("#input-lName").val();
  const email = $("#input-email").val();
  const password = $("#input-password").val();
  const phone_number = $("#input-phone").val();
  const username = $("#input-username").val();
  let role_id = $("#input-role").find(":selected").val();
  let manager_id = $("#input-manager").find(":selected").val();
  // if (role_id === "2" || role_id === "3") {
  //   manager_id = null;
  // }

  if (manager_id === "0" || typeof manager_id === "undefined") {
    manager_id = null;
  }

  if (role_id === null || role_id === "0") {
    role_id = "2";
  }

  console.log({
    first_name,
    last_name,
    email,
    password,
    username,
    phone_number,
    role_id,
    manager_id,
  });

  $.ajax({
    url: URL,
    method: "POST",
    dataType: "JSON",
    beforeSend: addCsrfToken(),
    data: JSON.stringify({
      first_name,
      last_name,
      email,
      password,
      username,
      phone_number,
      role_id,
      manager_id,
    }),
    contentType: "application/json",
    success: (result) => {
      Swal.fire("Saved!", "", "success");
      $("#modalAddEmployee").modal("hide");
      btnSpinner.classList.add("d-none");
      btnClose.removeAttribute("disabled");
      btnSubmit.classList.remove("d-none");
      resetInput();
      getAllEmployees();
    },
    error: function (xhr, ajaxOptions, thrownError) {
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: "Something went wrong!",
      });
      btnSpinner.classList.add("d-none");
      btnClose.removeAttribute("disabled");
      btnSubmit.classList.remove("d-none");
      console.log({ xhr, ajaxOptions, thrownError });
    },
  });
};

const getAllEmployees = () => {
  const tableWrapper = document.getElementById("table-wrapper");
  tableWrapper.innerHTML = "";
  $.ajax({
    url: URL,
    method: "GET",
    dataType: "JSON",
    contentType: "application/json",
    success: (results) => {
      let i = 0;
      const filteredUser = results
        .filter((e) => e.user.userRole[0].id !== 3)
        .sort((a, b) => b.id - a.id);
      filteredUser.forEach((emp) => {
        i += 1;
        tableWrapper.innerHTML += `<tr>
                          <td>${i}</td>
                          <td>${emp.user.username}</td>
                          <td>${emp.first_name}</td>
                          <td>${emp.last_name}</td>
                          <td>${emp.email}</td>
                          <td>${
                            emp.manager === null ? "-" : emp.manager.first_name
                          }</td>
                          <td>
                            <button
                              class="btn btn-sm btn-primary"
                              data-bs-toggle="modal"
                              onclick="detailEmployee(${emp.id})"
                              data-bs-target="#modalDetailEmployee"
                            >
                              <i class="mdi mdi-looks"></i>
                              Detail
                            </button>
                            <button
                              class="btn btn-sm btn-orange text-white"
                              data-bs-toggle="modal"
                              data-bs-target="#modalEditEmployee"
                             onclick="prepareUpdate(${emp.id})"
                              id="trigger-edit"
                            >
                              <i class="mdi mdi-account-edit"></i>
                              Edit
                            </button>
                            
                          </td>
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

const generateOption = (id, name, isSelected) => {
  const option = document.createElement("option");
  option.value = id;
  option.text = name;
  option.selected = isSelected;
  return option;
};

const detailEmployee = (id) => {
  $.ajax({
    url: `${URL}/${id}`,
    method: "GET",
    contentType: "application/json",
    success: (result) => {
      console.log(result);
      $("#detail-fName").val(result.first_name);
      $("#detail-lName").val(result.last_name);
      $("#detail-email").val(result.email);
      $("#detail-phone").val(result.phone_number);
      document
        .getElementById("detail-role")
        .add(
          generateOption(
            result.user.userRole[0].id,
            result.user.userRole[0].name,
            true
          )
        );
      if (result.manager !== null) {
        document
          .getElementById("detail-manager-emp")
          .classList.remove("d-none");
        document
          .getElementById("detail-manager")
          .add(
            generateOption(result.manager.id, result.manager.first_name, true)
          );
      } else {
        document.getElementById("detail-manager-emp").classList.add("d-none");
      }
    },
    error: function (xhr, ajaxOptions, thrownError) {
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: "Something went wrong!",
      }).then((result) => {
        if (result.isConfirmed) {
          window.location.href = "";
        }
      });
    },
  });
};

const prepareUpdate = async (id) => {
  $("#edit-id").val(id);
  $.ajax({
    url: `${URL}/${id}`,
    method: "GET",
    contentType: "application/json",
    success: async (result) => {
      $("#edit-fName").val(result.first_name);
      $("#edit-lName").val(result.last_name);
      $("#edit-email").val(result.email);
      $("#edit-phone").val(result.phone_number);
      const editRole = document.getElementById("edit-role");
      editRole.innerHTML = "";
      getManagerForEdit(result?.manager?.id);
      const roles = await getRole();
      roles.forEach((r) => {
        editRole.add(
          generateOption(
            r.roleId,
            r.roleName,
            result.user.userRole[0].id == r.roleId
          )
        );
      });
    },
    error: function (xhr, ajaxOptions, thrownError) {
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: "Something went wrong!",
      }).then((result) => {
        /*if (result.isConfirmed) {
          window.location.href = "";
        }*/
      });
    },
  });
};

const updateEmployee = () => {
  const id = $("#edit-id").val();
  const first_name = $("#edit-fName").val();
  const last_name = $("#edit-lName").val();
  const email = $("#edit-email").val();
  const phone_number = $("#edit-phone").val();
  const role_id = $("#edit-role").find(":selected").val();
  const manager_id = $("#edit-manager").find(":selected").val();
  console.log({
    first_name,
    last_name,
    email,
    username: first_name,
    password: "123",
    phone_number,
    role_id,
    manager_id: manager_id === "0" ? null : manager_id,
  });
  $.ajax({
    url: URL + "/" + id,
    method: "PUT",
    dataType: "JSON",
    beforeSend: addCsrfToken(),
    data: JSON.stringify({
      first_name,
      last_name,
      email,
      username: first_name,
      password: "123",
      phone_number,
      role_id,
      manager_id: manager_id === "0" ? null : manager_id,
    }),
    contentType: "application/json",
    success: (result) => {
      Swal.fire("Saved!", "", "success");
      console.log(result);
      $("#modalEditEmployee").modal("hide");
      getAllEmployees();
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

const deleteEmployee = (id) => {
  const swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: "btn btn-success",
      cancelButton: "btn btn-danger",
    },
    buttonsStyling: true,
  });

  swalWithBootstrapButtons
    .fire({
      title: "Are you sure?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Yes, delete it!",
      cancelButtonText: "No, cancel!",
      reverseButtons: true,
    })
    .then((result) => {
      if (result.isConfirmed) {
        $.ajax({
          url: `${URL}/${id}`,
          method: "DELETE",
          contentType: "application/json",
          beforeSend: addCsrfToken(),
          success: (result) => {
            getAllEmployees();
            swalWithBootstrapButtons.fire(
              "Deleted!",
              "Your file has been deleted.",
              "success"
            );
          },
          error: function (xhr, ajaxOptions, thrownError) {
            Swal.fire({
              icon: "error",
              title: "Oops...",
              text: "Something went wrong!",
            }).then((result) => {
              if (result.isConfirmed) {
              }
            });
          },
        });
      } else if (
        /* Read more about handling dismissals below */
        result.dismiss === Swal.DismissReason.cancel
      ) {
        swalWithBootstrapButtons.fire(
          "Cancelled",
          "Your imaginary file is safe :)",
          "error"
        );
      }
    });
};

window.addEventListener("load", loadData);
